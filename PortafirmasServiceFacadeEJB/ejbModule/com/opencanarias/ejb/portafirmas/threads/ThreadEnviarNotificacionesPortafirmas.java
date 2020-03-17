package com.opencanarias.ejb.portafirmas.threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJBException;
import javax.xml.bind.JAXBException;

import org.jboss.logging.Logger;

import com.opencan.office.exceptions.OfficeServiceException;
import com.opencan.office.implementations.OfficeService;
import com.opencan.office.interfaces.IOfficeService;
import com.opencanarias.apsct.modulo.callback.ejb.ICallback;
import com.opencanarias.apsct.modulo.callback.exception.CallbackServiceException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.ejb.portafirmas.core.DocumentosManager;
import com.opencanarias.ejb.portafirmas.dao.IPortafirmasFacadeDAO;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.utils.ConfigUtils;
import com.opencanarias.utils.Constantes;
import com.opencanarias.utils.FileUtils;
//import es.ap.alertas.exceptions.AlertasServiceException;
//import es.ap.alertas.params.common.ParametroType;
//import es.ap.alertas.params.common.ParametrosType;
//import es.ap.alertas.params.in.creacion.AdjuntoType;
//import es.ap.alertas.params.in.creacion.AdjuntosType;
//import es.ap.alertas.params.in.creacion.DatosCreacionAlertaType;
//import es.ap.alertas.params.in.creacion.DestinatarioAlertaType;
//import es.ap.alertas.params.in.creacion.DestinatariosAlertaType;
//import es.ap.alertas.params.in.creacion.IdentificadorEnvioType;
//import es.ap.alertas.params.in.creacion.IdentificadoresEnvioType;
//import es.ap.alertas.params.out.creacion.ResCreacionModAlertasType;
import es.apt.ae.facade.entities.portafirmas.Accion;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.DocumentoWSDL;
import es.apt.ae.facade.entities.portafirmas.Grupo;
import es.apt.ae.facade.entities.portafirmas.GrupoPersona;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;
import es.apt.ae.facade.portafirmas.dto.RespuestaVisualizacionDocumento;
//import es.apt.ae.facade.utils.ejb.clients.EJBServices;

public class ThreadEnviarNotificacionesPortafirmas extends Thread {

	private static Logger logger = Logger.getLogger(ThreadEnviarNotificacionesPortafirmas.class);
	
	private DocumentosManager manejadorDocumentos;
	private IPortafirmasFacadeDAO portafirmasDao;
	private ICallback srvCbFirma;
	private HashMap<String, List<ProcesoFirma>> resultadoActuar;
	private DocumentoPortafirmas documento;
	private String tipoAlerta;
	private String backofficeUserId;
	private String usuarioSolicitante;
	
	
	public ThreadEnviarNotificacionesPortafirmas(DocumentosManager manejadorDocumentos, IPortafirmasFacadeDAO portafirmasDaoParam, 
			ICallback srvCbFirmaParam, HashMap<String, List<ProcesoFirma>> resultadoActuar) {
		this.manejadorDocumentos = manejadorDocumentos;
		this.portafirmasDao = portafirmasDaoParam;
		this.srvCbFirma = srvCbFirmaParam;
		this.resultadoActuar = resultadoActuar;
	}
	
	public ThreadEnviarNotificacionesPortafirmas(DocumentosManager manejadorDocumentos, IPortafirmasFacadeDAO portafirmasDaoParam, 
			ICallback srvCbFirmaParam, DocumentoPortafirmas documento, String tipoAlerta, String backofficeUserId, String usuarioSolicitante) {
		this.manejadorDocumentos = manejadorDocumentos;
		this.portafirmasDao = portafirmasDaoParam;
		this.srvCbFirma = srvCbFirmaParam;
		this.documento = documento;
		this.tipoAlerta = tipoAlerta;
		this.backofficeUserId = backofficeUserId;
		this.usuarioSolicitante = usuarioSolicitante;
	}
	
	/**
	 * Método que llama a la creación de notificaciones
	 */
	public void run() {
		try {
			LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] INICIO DEL PROCESO DE ENVIO DE NOTIFICACIONES PARA EL PORTAFIRMAS...", 
					backofficeUserId, usuarioSolicitante);
			if (resultadoActuar != null) {
				enviarNotificaciones(resultadoActuar);
			}
			if (documento != null && tipoAlerta != null) {
				enviarNotificacion(documento, tipoAlerta);
			}
			LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] FIN DEL PROCESO DE ENVIO DE NOTIFICACIONES PARA EL PORTAFIRMAS", 
					backofficeUserId, usuarioSolicitante);
		} catch (Exception e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Capturada excepción enviando notificaciones para el Portafirmas", 
					e, backofficeUserId, usuarioSolicitante);
		}
	}
	
	public void enviarNotificaciones(HashMap<String, List<ProcesoFirma>> resultadoActuar) {
		try {
			for (ProcesoFirma procesoFirma : resultadoActuar.get("listaProcesosCierreGrupo")) {
				DocumentoPortafirmas doc = procesoFirma.getDocumento();
				LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Documento: " + doc.getNombre() + ", " + doc.getUri(), 
						backofficeUserId, usuarioSolicitante);
				if (doc.getCircuito() == null) {// Si no tiene circuito es que ha terminado
					notificarFinProcesoCallback(doc, procesoFirma.getObservaciones());
					enviarNotificacion(doc, Constantes.FWS_PF_NOTIFICACION_FIN_CIRCUITO);
				} else {// Notificacion al nuevo grupo activo
					notificarEstadoIntermedioProcesoCallback(doc, procesoFirma.getAccion(), procesoFirma.getPersona(), 
							procesoFirma.getObservaciones());
					enviarNotificacion(doc, Constantes.FWS_PF_NOTIFICACION_CAMBIO_GRUPO);
				}
			}
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se han podido enviar las notificaciones", e, 
					backofficeUserId, usuarioSolicitante);
		} catch (GenericFacadeException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se han podido enviar las notificaciones", e, 
					backofficeUserId, usuarioSolicitante);
		}
	}
	
	private void notificarEstadoIntermedioProcesoCallback(DocumentoPortafirmas doc, Accion accion,
			Persona persona, String observacion) {
		try {
			if (ConfigUtils.getParametro("portafirmas.notificaciones.callback.activo").equals("true")) {
				try {
					String correoNotificacionError = ConfigUtils.getParametro(Constantes.CB_FIRMA_TIMER_MAX_INTENTOS_EMAIL);
					if (doc.getHash() != null) {
						List<DocumentoWSDL> listaDocumentoWSDL = portafirmasDao.getDocumentoWSDLByUri(doc.getUri());
						for (DocumentoWSDL wsdl : listaDocumentoWSDL) {
							if (Boolean.TRUE.equals(wsdl.getInformarEstadoIntermedio())) {
								String estadoIntermedioFirma = null;
								if (accion.getCodigo().equals("FIRM")) {
									estadoIntermedioFirma = "FIRMADO";
								} else if (accion.getCodigo().equals("VALD")) {
									estadoIntermedioFirma = "VALIDADO";									
								} else if (accion.getCodigo().equals("RECH")) {
									estadoIntermedioFirma = "RECHAZADO";
								} else if (accion.getCodigo().equals("RECP")) {
									estadoIntermedioFirma = "RECUPERADO";
								} else if (accion.getCodigo().equals("BORR")) {
									estadoIntermedioFirma = "BORRADO";
								}
								String actorDNI = (persona != null?persona.getNumIdentificacion():null);
//								srvCbFirma.invokeCbFirma(doc.getUri(), doc.getHash(), correoNotificacionError, 
//										estadoIntermedioFirma, true, observacion, new Date(), wsdl.getUrl(), doc.getSubidoPorDNI(), actorDNI,
//										Constantes.CB_TIPO_FIRMA);
							}
						}
					}
//				} catch (CallbackServiceException e) {
//					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error en el proceso de notificacion", e, 
//							backofficeUserId, usuarioSolicitante);
				} catch (GenericFacadeException e) {
					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error en el proceso de notificacion", e, 
							backofficeUserId, usuarioSolicitante);
				}
			}
		} catch (GenericFacadeException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Propiedad de envio a callback no encontrada, no se hace nada", e, 
					backofficeUserId, usuarioSolicitante);
		}
	}
	
	private void notificarFinProcesoCallback(DocumentoPortafirmas doc, String observacion) {
		try {
			if (ConfigUtils.getParametro("portafirmas.notificaciones.callback.activo").equals("true")) {
				try {
					String correoNotificacionError = ConfigUtils.getParametro(Constantes.CB_FIRMA_TIMER_MAX_INTENTOS_EMAIL);
					if (doc.getHash() != null) {
						List<DocumentoWSDL> listaDocumentoWSDL = portafirmasDao.getDocumentoWSDLByUri(doc.getUri());
						for (DocumentoWSDL wsdl : listaDocumentoWSDL) {
							String estadoCbFirma = (doc.getEstadoDocumento().getCodigo().equals("FIRM") ? "FIRMADO" : "RECHAZADO");
//							srvCbFirma.invokeCbFirma(doc.getUri(), doc.getHash(), correoNotificacionError, 
//									estadoCbFirma, false, observacion, new Date(), wsdl.getUrl(), doc.getSubidoPorDNI(), null, 
//									Constantes.CB_TIPO_FIRMA);
						}
					}
//				} catch (CallbackServiceException e) {
//					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error en el proceso de notificacion", e, 
//							backofficeUserId, usuarioSolicitante);
				} catch (GenericFacadeException e) {
					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error en el proceso de notificacion", e, 
							backofficeUserId, usuarioSolicitante);
				}
			}
		} catch (GenericFacadeException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Propiedad de envio a callback no encontrada, no se hace nada", e, 
					backofficeUserId, usuarioSolicitante);
		}
	}
	
	public void enviarNotificacion(DocumentoPortafirmas documento, String tipoAlerta) throws JAXBException, GenericFacadeException {
		
		if (ConfigUtils.getParametro("portafirmas.notificaciones.alertas.activo").equals("true")) {
//			DatosCreacionAlertaType datosCreacion = new DatosCreacionAlertaType();
//			datosCreacion.setTipoAlerta(tipoAlerta);
//			DestinatariosAlertaType destinatarios = new DestinatariosAlertaType();
//			datosCreacion.setDestinatarios(destinatarios);
//			List<DestinatarioAlertaType> listDestinatarios = destinatarios.getDestinatario();
	
			// Se incluirá el adjunto si la persona lo tiene configurado en la BD.
			boolean incluirAdjunto = false;
	
			if (Constantes.FWS_PF_NOTIFICACION_FIN_CIRCUITO.equals(tipoAlerta)) {
//				DestinatarioAlertaType destinatario = new DestinatarioAlertaType();
//				destinatario.setIdentificador(documento.getSubidoPorDNI());
//				IdentificadoresEnvioType identificadoresEnvio = new IdentificadoresEnvioType();
//				destinatario.setIdentificadoresEnvio(identificadoresEnvio);
//				List<IdentificadorEnvioType> listIdentificadoresEnvio = identificadoresEnvio.getIdentificadorEnvio();
//				IdentificadorEnvioType identificadorEnvio = new IdentificadorEnvioType();
//				identificadorEnvio.setNombre("EMAIL");
//				identificadorEnvio.setValor(documento.getMailCreador());
//				listIdentificadoresEnvio.add(identificadorEnvio);
//				listDestinatarios.add(destinatario);
			} else if (Constantes.FWS_PF_NOTIFICACION_CAMBIO_GRUPO.equals(tipoAlerta)) {
				for (Grupo grupo : documento.getCircuito().getListGrupo()){
					if(grupo.getOrden() == documento.getCircuito().getOrdenActivo()){
						for(GrupoPersona grupoPersona : grupo.getGrupoPersona()){
//							DestinatarioAlertaType destinatario = new DestinatarioAlertaType();
							Persona persona = grupoPersona.getPersona();
							if (Boolean.TRUE.equals(persona.getAdjuntosAlertas())) {
								incluirAdjunto = true;
							}
//							destinatario.setIdentificador(persona.getNumIdentificacion());
//							IdentificadoresEnvioType identificadoresEnvio = new IdentificadoresEnvioType();
//							destinatario.setIdentificadoresEnvio(identificadoresEnvio);
//							List<IdentificadorEnvioType> listIdentificadoresEnvio = identificadoresEnvio.getIdentificadorEnvio();
//							IdentificadorEnvioType identificadorEnvio = new IdentificadorEnvioType();
//							identificadorEnvio.setNombre("EMAIL");
//							identificadorEnvio.setValor(persona.getMail());
//							listIdentificadoresEnvio.add(identificadorEnvio);
//							listDestinatarios.add(destinatario);
						}
						break;
					}
				}
			}
			
//			if (!listDestinatarios.isEmpty()) {
//				// Parametros y adjuntos
//				insertParamsNotificacion(documento, datosCreacion, tipoAlerta);
//				if (incluirAdjunto) {
//					insertarAdjuntoNotificacion(documento, datosCreacion, tipoAlerta);
//				}
//				
//				try {
//					ResCreacionModAlertasType resultadoCreacionAlerta = null;
//					try {
//						resultadoCreacionAlerta = EJBServices.getSrvAlertasRemote().crearAlertaEJB(datosCreacion);
//					}catch(EJBException e) {
//						resultadoCreacionAlerta = EJBServices.getSrvAlertasRemote().crearAlertaEJB(datosCreacion);
//					}
//					if (resultadoCreacionAlerta.getCodRespuesta().getCodigo().equalsIgnoreCase("0: Alertas creadas correctamente")) {
//						LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Se ha devuelto el siguiente codigo de Alertas: " + 
//								resultadoCreacionAlerta.getCodRespuesta().getCodigo(), backofficeUserId, usuarioSolicitante);
//					} else {
//						LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Se ha devuelto el siguiente codigo de Alertas: " 
//								+ resultadoCreacionAlerta.getCodRespuesta().getCodigo(), null, backofficeUserId, usuarioSolicitante);
//					}
//				} catch (AlertasServiceException e) {
//					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Se produjo el siguiente error en la ejecucion: " + e.getMessage(), e, 
//							backofficeUserId, usuarioSolicitante);
//				}
//			} else {
//				LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] No se envia la alerta puesto que no se ha podido identificar ningun destinatario.", 
//						backofficeUserId, usuarioSolicitante);
//			}
		}
	}
	
	/**
	 * @param documento
	 * @param listParametros
	 */
//	private void insertParamsNotificacion(DocumentoPortafirmas documento, DatosCreacionAlertaType datosCreacion, String tipoAlerta) {
//		ParametrosType parametrosNotif = new ParametrosType();
//		datosCreacion.setParametros(parametrosNotif);
//		List<ParametroType> listParametros = parametrosNotif.getParam();

		// Parametros comunes
//		try {
//			ParametroType param = new ParametroType();
//			param.setNombre("__FECHA__");
//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			param.setValor(df.format(new Date()));
//			listParametros.add(param);
//			param = new ParametroType();
//			param.setNombre("__NOMBRE_DOCUMENTO__");
//			param.setValor(documento.getNombre());
//			listParametros.add(param);
//			param = new ParametroType();
//			param.setNombre("__ASUNTO__");
//			String asunto = documento.getAsunto();
//			String[] asuntoLineas = asunto.split("\\r\\n");
//			if (asuntoLineas.length > 1) {
//				asunto = asunto.replaceAll("\\r\\n", "<br/>");
//			}
//			asunto = "<br/>" + asunto;
//			param.setValor(asunto);
//			listParametros.add(param);
//			param = new ParametroType();
//			param.setNombre("__ENLACE__");
//			param.setValor((String) ConfigUtils.getParametro("alertas.estado.url") + "?uri=" + documento.getUri());
//			listParametros.add(param);
//			// Parametros especificos del tipo de alerta
//			if (Constantes.FWS_PF_NOTIFICACION_FIN_CIRCUITO.equals(tipoAlerta)) {
//				param = new ParametroType();
//				param.setNombre("__ESTADO__");
//				param.setValor(documento.getEstadoDocumento().getDescripcion());
//				listParametros.add(param);			
//			} else if (Constantes.FWS_PF_NOTIFICACION_CAMBIO_GRUPO.equals(tipoAlerta)) {
//				param = new ParametroType();
//				param.setNombre("__ACCION__");
//				param.setValor(documento.getCircuito().getListGrupo().get(documento.getCircuito().getOrdenActivo() - 1).getTipoGrupo().getDescripcion());
//				listParametros.add(param);
//			}	
//		} catch (Exception e) {
//			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error agregando parametros de la alerta", e, 
//					backofficeUserId, usuarioSolicitante);			
//		}
//	}
	
//	private void insertarAdjuntoNotificacion(DocumentoPortafirmas documento, DatosCreacionAlertaType datosCreacion, String tipoAlerta) {
//		if (Constantes.FWS_PF_NOTIFICACION_CAMBIO_GRUPO.equals(tipoAlerta)) {
//			AdjuntosType adjuntosNotif = new AdjuntosType();
//			datosCreacion.setAdjuntos(adjuntosNotif);
//			List<AdjuntoType> listAdjuntos = adjuntosNotif.getAdjunto();
//			try {
//				AdjuntoType adjuntoType = new AdjuntoType();
//				byte[] data = null;
//				String nombreDocumento = null;
//				String tipoDocumento = null;				
//				if (documento.getContenido() == null) {
//					RespuestaVisualizacionDocumento resultado = manejadorDocumentos.obtenerVisualizacionDocumento(documento.getUri(), "admin", "admin");
//					data = resultado.getContenido();
//					nombreDocumento = resultado.getNombre();
//				} else {
//					data = documento.getContenido().getBytes();
//					nombreDocumento = documento.getNombre();
//					tipoDocumento = FileUtils.APPLICATION_PDF;
//				}
//				if (data != null && nombreDocumento != null && !nombreDocumento.toString().equals("")) {
//					String extensionDocumento = FileUtils.getExtension(nombreDocumento);
//					tipoDocumento = FileUtils.getContentType(nombreDocumento, data);
//					if (extensionDocumento != null && !FileUtils.APPLICATION_PDF.equals(tipoDocumento)) {
//						try {
//							IOfficeService officeService = OfficeService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
//							data = officeService.convertDocument(data, extensionDocumento, "pdf");
//							tipoDocumento = FileUtils.APPLICATION_PDF;
//							nombreDocumento = FileUtils.changeExtension(nombreDocumento, "pdf");
//						} catch (OfficeServiceException e) {
//							LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error al convertir documento a pdf", e, 
//									backofficeUserId, usuarioSolicitante);
//						}						
//					}					
//					adjuntoType.setBinario(data);				
//					adjuntoType.setNombre(nombreDocumento);
//					adjuntoType.setTipoContenido(tipoDocumento);
//					listAdjuntos.add(adjuntoType);
//				}
//			} catch (Exception e){
//				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error agregando adjunto de la alerta", e, 
//						backofficeUserId, usuarioSolicitante);
//			}
//		}
//	}
	
}
