package com.opencanarias.ejb.portafirmas.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.ejb.portafirmas.dao.IPortafirmasFacadeDAO;

import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.Grupo;
import es.apt.ae.facade.entities.portafirmas.GrupoPersona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;

public class PortafirmasFacadeProcesosUtils {

	private static Logger logger = Logger.getLogger(PortafirmasFacadeProcesosUtils.class);
	private static IPortafirmasFacadeDAO portafirmasFacadeDAO;
	
	public static Grupo recuperarGrupo(List<Grupo> listGrupo, int orden){
		for (Grupo grupo : listGrupo){
			if (grupo.getOrden() == orden){
				return grupo;
			}
		}
		return null;
	}
	
	private static HashMap<String, Integer> contadoresCircuito(Grupo grupo){
		HashMap<String, Integer> resultado = new HashMap<String,Integer>();
		
		Integer contadorFirmas = 0;
		Integer contadorObligatorios = 0;
		Integer contadorObligatoriosHechos = 0;
		
		for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
			if (grupoPersona.isObligatorio()) {
				contadorObligatorios++;
				if (grupoPersona.isRealizado()) {
					contadorObligatoriosHechos++;
				}
			}
			if (grupoPersona.isRealizado()) {
				contadorFirmas++;
			}

		}
		resultado.put("contadorFirmas", contadorFirmas);
		resultado.put("contadorObligatorios", contadorObligatorios);
		resultado.put("contadorObligatoriosHechos", contadorObligatoriosHechos);
		return resultado;
	}
	
	public static Boolean cerrarGrupoDeCircuito(CircuitoEntity circuito) {

		Grupo grupo = recuperarGrupo(circuito.getListGrupo(), circuito.getOrdenActivo());
		// Recorremos los grupos para analizar las condiciones
		if (grupo != null){
			HashMap<String, Integer> resultado = contadoresCircuito(grupo);
			Integer contadorFirmas = resultado.get("contadorFirmas");
			Integer contadorObligatorios = resultado.get("contadorObligatorios");
			Integer contadorObligatoriosHechos = resultado.get("contadorObligatoriosHechos");
			// Se valoran las condiciones para cerrar el grupo y pasar el
			// siguiente a activo
			if (contadorObligatorios == contadorObligatoriosHechos
					&& contadorFirmas >= grupo.getFirmantesRequeridos()) {
				// HECHO //TODO: setear orden activo a el siguiente
				grupo.setCerrado(true);
				circuito.setOrdenActivo(circuito.getOrdenActivo() + 1);
				return true;
			}
		} else {//TODO:Si el grupo es null, se debe dar por entendido que se debe cerrar el circuito
			return true;
		}
		return false;

	}
	
	public static boolean terminarCircuito(CircuitoEntity circuito) {
		if (circuito.getOrdenActivo() == (circuito.getListGrupo().size() +1)) {
			//Comprobar si en el ultimo grupo han actuado los requeridos salvo este
			Grupo grupo = recuperarGrupo(circuito.getListGrupo(), circuito.getOrdenActivo() -1);
			HashMap<String, Integer> resultado = contadoresCircuito(grupo);
			Integer contadorFirmas = resultado.get("contadorFirmas");
			Integer contadorObligatorios = resultado.get("contadorObligatorios");
			Integer contadorObligatoriosHechos = resultado.get("contadorObligatoriosHechos");
			if (contadorObligatorios == contadorObligatoriosHechos
					&& contadorFirmas >= grupo.getFirmantesRequeridos()){
				return true;
			} 
		}
		return false;
	}
	
	public static Boolean comprobarSiguienteGrupo(ProcesoFirma procesoFirma){
		Boolean result = false;
		//Buscamos el historial del documento
		CircuitoEntity circuito = procesoFirma.getDocumento().getCircuito();
		Grupo grupo = recuperarGrupo(circuito.getListGrupo(), circuito.getOrdenActivo());
		List<ProcesoFirma> listProcesoFirma = portafirmasFacadeDAO.getProcesoFirmadoByUri(procesoFirma.getDocumento().getUri());//No has persistido en BD
		//Comparamos las personas del historial que han firmado y si están en el siguiente grupo
		listProcesoFirma.add(procesoFirma);//Se setea de forma temporal el proceso actual.
		for (ProcesoFirma procesoFirmaAux : listProcesoFirma){
			for(GrupoPersona grupoPersona : grupo.getGrupoPersona())
				if (procesoFirmaAux.getPersona().equals(grupoPersona.getPersona())){
					grupoPersona.setRealidazo(true);
					circuito.setOrdenActivo(circuito.getOrdenActivo() + 1);
					result = true;
				}
		}
		return result;
		
	}
	
	public static Boolean escribirProceso(ProcesoFirma procesoFirma){
		CircuitoEntity circuito = procesoFirma.getDocumento().getCircuito();
		Boolean cierreGrupo = false;
		if (cerrarGrupoDeCircuito(circuito)){
			cierreGrupo = true;
			if (terminarCircuito(circuito)){
				//FIN -> PERSISTE
				portafirmasFacadeDAO.saveProcesoCircuitoTerminado(procesoFirma);
				logger.info("[FACHADA-SRV_PORTAFIRMAS] Fin por terminar circuito");
			} else{
				if (comprobarSiguienteGrupo(procesoFirma)){
					logger.info("[FACHADA-SRV_PORTAFIRMAS] Llamada recursiva");
					escribirProceso(procesoFirma);//Llamada recursiva para el siguiente grupo
				} else {
					//FIN -> PERSISTE
					portafirmasFacadeDAO.saveProcesoCircuito(procesoFirma);
					logger.info("[FACHADA-SRV_PORTAFIRMAS] Fin por no necesitar comprobar el siguiente");
				}
			}
		} else {
			//FIN -> PERSISTE
			cierreGrupo = false;
			portafirmasFacadeDAO.saveProcesoCircuito(procesoFirma);
			logger.info("[FACHADA-SRV_PORTAFIRMAS] Fin por no cerrar grupo de circuito");
		}
		return cierreGrupo;
	}
	
	public static HashMap<String, List<ProcesoFirma>> escribirListaProceso (List<ProcesoFirma> listProcesoFirma, IPortafirmasFacadeDAO portafirmasDAO){
		portafirmasFacadeDAO = portafirmasDAO;
		HashMap<String, List<ProcesoFirma>> resultadoProceso = new HashMap<String, List<ProcesoFirma>>();
		List<ProcesoFirma> listaProcesosOK = new ArrayList<ProcesoFirma>();
		List<ProcesoFirma> listaProcesosError = new ArrayList<ProcesoFirma>();
		List<ProcesoFirma> listaProcesosCierreGrupo = new ArrayList<ProcesoFirma>();
		resultadoProceso.put("listaProcesosOK", listaProcesosOK);
		resultadoProceso.put("listaProcesosError", listaProcesosError);
		resultadoProceso.put("listaProcesosCierreGrupo", listaProcesosCierreGrupo);
		for (ProcesoFirma procesoFirma : listProcesoFirma){
			try {
				Boolean cierreGrupo = escribirProceso(procesoFirma);
				if (cierreGrupo){
					listaProcesosCierreGrupo.add(procesoFirma);
				}
				listaProcesosOK.add(procesoFirma);
			} catch(Exception e){
				logger.error("[FACHADA-SRV_PORTAFIRMAS] Error escribiendo proceso", e);
				listaProcesosError.add(procesoFirma);
			}
		}
		return resultadoProceso;
	}
	
}
