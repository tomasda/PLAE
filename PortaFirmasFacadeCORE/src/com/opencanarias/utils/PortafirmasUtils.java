package com.opencanarias.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import es.apt.ae.facade.dto.DocumentoItem;
import es.apt.ae.facade.dto.HtmlTextItem;
import es.apt.ae.facade.dto.PeticionesItem;
import es.apt.ae.facade.entities.GestionPortafirmasDocumento;
//import es.apt.ae.facade.entities.actuaciones.CatAccionesExpedientes;
import es.apt.ae.facade.entities.utils.EntitiesUtils;


@SuppressWarnings("unused")
public class PortafirmasUtils {
	
	public static final String BASIC_STYLE 				= "cursor:hand;font-size:12px;font-weight:bold;white-space:pre-line;color:";
	public static final String COLOR_ROJO_HEXD 			= "#FF0000";
	public static final String COLOR_NARANJA_HEXD 		= "#FF7700";
	public static final String COLOR_NEGRO_HEXD 		= "#000000";
	public static final String COLOR_VERDE_HEXD 		= "#74DF00";
	

	public static HashMap<String, PeticionesItem> calcularNumPeticionesPortafirmas(HashMap<String, List<GestionPortafirmasDocumento>> peticionesPortafirmasMap) {
		HashMap<String, PeticionesItem> peticionesPortafirmasInfoMap = new HashMap<String, PeticionesItem>();
		
		if (peticionesPortafirmasMap != null && !peticionesPortafirmasMap.isEmpty()) {
			Iterator<Entry<String, List<GestionPortafirmasDocumento>>> it = peticionesPortafirmasMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, List<GestionPortafirmasDocumento>> entry = it.next();
				List<GestionPortafirmasDocumento> peticionesPortafirmas = entry.getValue();
				Integer numPortafirmasTotal = peticionesPortafirmas.size();
				Integer numPortafirmasPendientes = 0;
				Integer numPortafirmasAceptadas = 0;
				Integer numPortafirmasRechazadas = 0;
				for (GestionPortafirmasDocumento peticion:peticionesPortafirmas) {
					if (peticion.getEstadoFirma().equals(ExpedientesConstants.DOC_ESTADO_PENDIENTE_PORTAFIRMAS)) {
						numPortafirmasPendientes++;
					} else if (peticion.getEstadoFirma().equals(ExpedientesConstants.DOC_ESTADO_FIRMADO_PORTAFIRMAS)) {
						numPortafirmasAceptadas++;
					} else if (peticion.getEstadoFirma().equals(ExpedientesConstants.DOC_ESTADO_RECHAZADO_PORTAFIRMAS)) {
						numPortafirmasRechazadas++;
					}
				}
				Integer numPortafirmasFinalizadas = numPortafirmasAceptadas + numPortafirmasRechazadas;

				PeticionesItem numPeticionesPortafirmas = new PeticionesItem(numPortafirmasTotal, numPortafirmasPendientes, 
						numPortafirmasAceptadas, numPortafirmasRechazadas, numPortafirmasFinalizadas);
				peticionesPortafirmasInfoMap.put(entry.getKey(), numPeticionesPortafirmas);
			}
		}
		
		return peticionesPortafirmasInfoMap;
	}
	
	public static HtmlTextItem calcularPeticionesPortafirmasInfo(PeticionesItem peticiones) {
		HtmlTextItem htmlProperties = null;
		
		Integer numPortafirmasTotal = peticiones.getNumPeticionesTotal();
		Integer numPortafirmasPendientes = peticiones.getNumPeticionesPendientes();
		Integer numPortafirmasAceptadas = peticiones.getNumPeticionesAceptadas();
		Integer numPortafirmasRechazadas = peticiones.getNumPeticionesRechazadas();
		Integer numPortafirmasFinalizadas = peticiones.getNumPeticionesFinalizadas();
		
		String label = "PTF: " + numPortafirmasFinalizadas + "/" + numPortafirmasTotal;
		String title = null;
		String style = null;
		String styleClass = null;
		
		if (numPortafirmasTotal == 0) {
			title = "PORTAFIRMAS&#13;   - Ningún documento enviado.";
			style = BASIC_STYLE + COLOR_NEGRO_HEXD;
		} else if (numPortafirmasTotal > 0 && numPortafirmasTotal == numPortafirmasFinalizadas) {
			if (numPortafirmasFinalizadas == numPortafirmasAceptadas) {
				title = "PORTAFIRMAS&#13;   - " + numPortafirmasTotal + 
						(numPortafirmasTotal==1?" petición enviada y firmada/validada.":" peticiones enviadas, todas ellas firmadas/validadas.");
				style = BASIC_STYLE + COLOR_VERDE_HEXD;
			} else {
				title = "PORTAFIRMAS&#13;   - " + numPortafirmasTotal + 
						(numPortafirmasTotal==1?" petición enviada.":" peticiones enviadas.") + 
						((numPortafirmasAceptadas > 0)?("&#13;   - " + numPortafirmasAceptadas + (numPortafirmasAceptadas==1?" petición firmada/validada.":" peticiones firmadas/validadas.")):"") + 
						"&#13;   - " + numPortafirmasRechazadas + (numPortafirmasRechazadas==1?" petición rechazada/recuperada.":" peticiones rechazadas/recuperadas.");
				style = BASIC_STYLE + COLOR_ROJO_HEXD;	
			}
		} else if (numPortafirmasPendientes > 0) {
			title = "PORTAFIRMAS&#13;   - " + numPortafirmasTotal + 
					(numPortafirmasTotal==1?" petición enviada.":" peticiones enviadas.") +
					"&#13;   - " + numPortafirmasPendientes + (numPortafirmasPendientes==1?" petición pendiente.":" peticiones pendientes.") + 
					((numPortafirmasAceptadas > 0)?("&#13;   - " + numPortafirmasAceptadas + (numPortafirmasAceptadas==1?" petición firmada/validada.":" peticiones firmadas/validadas.")):"") +
					((numPortafirmasRechazadas > 0)?("&#13;   - " + numPortafirmasRechazadas + (numPortafirmasRechazadas==1?" petición rechazada/recuperada.":" peticiones rechazadas/recuperadas.")):"");
			style = BASIC_STYLE + COLOR_NARANJA_HEXD;
		}
		
		htmlProperties = new HtmlTextItem(label, title, style, styleClass);
		
		return htmlProperties;
	}	
	
	/**
	 * Metodo encargado de cargar la lista de restricciones y verificar que no existen documentos pendientes
	 * de Portafirmas.
	 * @throws OpencitiesException
	 * @throws Exception
	 */
	public static boolean hayPeticionesPortafirmasPdtes(EntityManager em, String procesoId, String actividadId, 
			List<DocumentoItem> documentos, HashMap<String, String> propiedades) {

		if (documentos != null && !documentos.isEmpty()) {
			if (hayDocumentosPdtesPortafirmas(documentos)) {
//				List<CatAccionesExpedientes> bloqueosActividadActual = 
//						EntitiesUtils.getBloqueosPortafirmas(em, procesoId, actividadId);
//				
//				if (bloqueosActividadActual != null && !bloqueosActividadActual.isEmpty()) {
//					for (CatAccionesExpedientes bloqueoActividadActual:bloqueosActividadActual) {
//						boolean condicionOk = true;
//						String condicion = bloqueoActividadActual.getCondicion();
//						
//						if (condicion != null && !condicion.trim().equals("")) {
//							condicionOk = CondicionesUtils.evaluarCondicion(condicion, propiedades);
//						}
//						if (condicionOk) {
							return true;
//						}
//					}
//				}
			}
		}
		
		return false;
	}
		
	private static boolean hayDocumentosPdtesPortafirmas(List<DocumentoItem> documentos) {
		if (documentos != null && !documentos.isEmpty()) {
			for (DocumentoItem doc:documentos) {
				if (doc.getEstadoFirma().equals(ExpedientesConstants.DOC_ESTADO_PENDIENTE_PORTAFIRMAS)) {
					return true;
				}
			}
		}
		
		return false;
	}

}
