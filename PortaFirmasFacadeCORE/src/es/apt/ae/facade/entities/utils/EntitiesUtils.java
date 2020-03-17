package es.apt.ae.facade.entities.utils;

import java.math.BigDecimal;
//import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
//import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.utils.DateUtils;
import com.opencanarias.utils.ExpedientesConstants;
import com.opencanarias.utils.StringUtils;

import es.apt.ae.facade.dto.EstadoNotificacionDocumento;
import es.apt.ae.facade.dto.PeticionesItem;
import es.apt.ae.facade.entities.BackOffice;
import es.apt.ae.facade.entities.CatContenidoEstaticoEtiqueta;
import es.apt.ae.facade.entities.CatPermisoDispositivo;
import es.apt.ae.facade.entities.CatPlantilla;
import es.apt.ae.facade.entities.CatProcedimientoCircuito;
import es.apt.ae.facade.entities.CatProcedimientoRol;
import es.apt.ae.facade.entities.CatPropiedadesConfiguracion;
import es.apt.ae.facade.entities.CatTipoDocumento;
import es.apt.ae.facade.entities.Documento;
//import es.apt.ae.facade.entities.Expediente;
import es.apt.ae.facade.entities.Familia;
import es.apt.ae.facade.entities.FirmaManuscritaDocumento;
import es.apt.ae.facade.entities.FirmaManuscritaDocumentoDestinatario;
import es.apt.ae.facade.entities.FirmaManuscritaDocumentoWSDL;
import es.apt.ae.facade.entities.GestionPortafirmasDocumento;
import es.apt.ae.facade.entities.IdsExpedientes;
import es.apt.ae.facade.entities.IndiceCapituloDocumento;
import es.apt.ae.facade.entities.Procedimiento;
import es.apt.ae.facade.entities.Proceso;
import es.apt.ae.facade.entities.Rol;
import es.apt.ae.facade.entities.Track;
import es.apt.ae.facade.entities.TrackPk;
import es.apt.ae.facade.entities.Transicion;
//import es.apt.ae.facade.entities.actuaciones.Actuacion;
//import es.apt.ae.facade.entities.actuaciones.ActuacionesLeidasInfo;
//import es.apt.ae.facade.entities.actuaciones.CatAccionesExpedientes;
//import es.apt.ae.facade.entities.actuaciones.CatActuaciones;
//import es.apt.ae.facade.entities.earegistro.Asiento;
//import es.apt.ae.facade.entities.earegistro.CatEntidadRegistral;
//import es.apt.ae.facade.entities.earegistro.CatEstadoDecreto;
//import es.apt.ae.facade.entities.earegistro.CatEstadoRegistro;
//import es.apt.ae.facade.entities.earegistro.CatOrigenAsiento;
//import es.apt.ae.facade.entities.earegistro.CatRelacionDecreto;
//import es.apt.ae.facade.entities.earegistro.CatRelacionExpediente;
//import es.apt.ae.facade.entities.earegistro.CatSubtipoRegistro;
//import es.apt.ae.facade.entities.earegistro.CatTipoTransporteEntrada;
//import es.apt.ae.facade.entities.earegistro.DocumentoAsiento;
//import es.apt.ae.facade.entities.earegistro.IdAsiento;
//import es.apt.ae.facade.entities.earegistro.Tercero;
//import es.apt.ae.facade.entities.earegistro.TerceroAsiento;
//import es.apt.ae.facade.entities.notificaciones.AdjuntoNotificacion;
//import es.apt.ae.facade.entities.notificaciones.AdjuntoNotificacionExpediente;
//import es.apt.ae.facade.entities.notificaciones.NotificacionBuzon;
//import es.apt.ae.facade.entities.notificaciones.NotificacionDocumento;
//import es.apt.ae.facade.entities.notificaciones.NotificacionPapelDocumento;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.Persona;
//import es.apt.ae.facade.entities.requerimientos.EstadoRequerimientoEntity;
//import es.apt.ae.facade.entities.requerimientos.RequerimientoEntity;
//import es.apt.ae.facade.entities.workflow.Act_ru_task;
//import es.apt.ae.facade.expedientes.dto.ContadorRequerimientoExpediente;

public class EntitiesUtils {

	private static Logger logger = Logger.getLogger(EntitiesUtils.class);
	
	private static final int MAX_ELEMS_IN = 1000;
	
	public static <T> void persist(EntityManager em, T entidad) throws GenericFacadeException {
		em.persist(entidad);
	}	
	
	public static <T> T merge(EntityManager em, T entidad) throws GenericFacadeException {
		T resultado = em.merge(entidad);
		return resultado;
	}

	public static <T> void remove(EntityManager em, T entidad) throws GenericFacadeException {
		em.remove(entidad);
	}
	
//	public static Expediente getExpedienteById(EntityManager em, String numExpediente) {
//		Expediente expediente = null;
//		
//		try {
//			Query q = em.createNamedQuery(Expediente.findById);
//			q.setParameter("id", numExpediente);
//			expediente = (Expediente) q.getSingleResult();		
//		} catch (NoResultException e) {
//			logger.info("No existe el expediente con id " + numExpediente + " en la base de datos.");			
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el expediente " + numExpediente + " de la base de datos", e);
//		}
//	
//		return expediente;
//	}
	
	/**
	 * Comprueba si existe en BD un determinado expediente.
	 * @param numExpediente Identificador del expediente
	 * @return Un objeto de tipo Expediente en el caso de que exista y null en caso contrario.
	 */	
//	public static Expediente getExpedienteById(EntityManager em, String numExpediente, Boolean isActivo) {
//		Expediente expediente = null;
//		
//		try {
//			Query q = null;
//			if (isActivo == null || !isActivo) {
//				q = em.createNamedQuery(Expediente.findById);
//			} else  {
//				q = em.createNamedQuery(Expediente.findByIdAndActivo);
//			}
//			q.setParameter("id", numExpediente);
//			expediente = (Expediente) q.getSingleResult();			
//		} catch (NoResultException e) {
//			logger.info("No existe el expediente con id " + numExpediente + " en la base de datos. Activo: " + isActivo);
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el expediente con id " + numExpediente + " de la base de datos. Activo: " + isActivo, e);
//		}
//	
//		return expediente;
//	}
	
	public static IdsExpedientes getIdExpediente(EntityManager em, String codigo) {
		IdsExpedientes idExpediente = null;
		
		try {
			idExpediente = em.find(IdsExpedientes.class, codigo, LockModeType.PESSIMISTIC_WRITE);			
//			Query q = em.createNamedQuery("IdsExpedientes.findByCodigo");
//			q.setParameter("codigo", codigo);
//			idExpediente = (IdsExpedientes) q.getSingleResult();			
		} catch (NoResultException e) {
			logger.info("No existe id para la generacion del numero de expediente con codigo " + codigo);
		} catch (Exception e) {
			logger.error("Error al consultar id para la generacion del numero de expediente con codigo " + codigo, e);
		}
	
		return idExpediente;
	}
	
	/**
	 * Comprueba si existe en BD una determinada actuación.
	 * @param idActuacion Identificador de la actuación
	 * @return Un objeto de tipo Actaucion en el caso de que exista y null en caso contrario.
	 */	
//	public static Actuacion getActuacionById(EntityManager em, String idActuacion, Boolean isActivo) {
//		Actuacion actuacion = null;
//		
//		try {
//			Query q = null;
//			if (isActivo == null || !isActivo) {
//				q = em.createNamedQuery("Actuacion.findById");
//			} else  {
//				q = em.createNamedQuery("Actuacion.findByIdAndActivo");
//			}
//			q.setParameter("id", idActuacion);
//			actuacion = (Actuacion) q.getSingleResult();			
//		} catch (NoResultException e) {
//			logger.info("No existe la actuacion con id " + idActuacion + " en la base de datos. Activa: " + isActivo);
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar la actuacion con id " + idActuacion + " de la base de datos. Activa: " + isActivo, e);
//		}
//	
//		return actuacion;
//	}
	
	public static Proceso getProcesoMaxVersion(EntityManager em, String wf) {
		Proceso workflow = null;
		try {
			Query q = em.createNamedQuery("Proceso.findByMaxVersion");
			q.setParameter("id", wf + ":%"); // Los dos puntos se le añaden por ser un flujo de ACTIVITI.
			workflow = (Proceso) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No se ha encontrado la ultima version del flujo " + wf);			
		} catch (Exception e) {
			logger.error("No se ha podido recuperar la ultima version del flujo " + wf, e);
		}		
	
		return workflow;
	}	
	
	public static Proceso getProcesoById(EntityManager em, String wfId) {
		Proceso workflow = null;
		try {
			Query q = em.createNamedQuery("Proceso.findById");
			q.setParameter("id", wfId);
			workflow = (Proceso) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe la definicion del proceso con id " + wfId + " en la base de datos.");
		} catch (Exception e) {
			logger.error("No se ha podido recuperar la definicion del proceso id " + wfId + " de la base de datos.", e);
		}			
	
		return workflow;
	}
	
	public static CatContenidoEstaticoEtiqueta getContenidoEstaticoEtiqueta(EntityManager em, Integer plantillaId, Integer etiquetaId) {
		CatContenidoEstaticoEtiqueta contenidoEstaticoEtiqueta = null;
		
		try {
			Query q = em.createNamedQuery("CatContenidoEstaticoEtiqueta.findByPlantillaIdAndEtiquetaId");
			q.setParameter("plantillaId", plantillaId);
			q.setParameter("etiquetaId", etiquetaId);
			contenidoEstaticoEtiqueta = (CatContenidoEstaticoEtiqueta) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe contenido estatico habilitado. " + 
					"* Id. Plantilla: " + plantillaId + ", * Id. Etiqueta: " + etiquetaId);
		} catch (Exception e) {
			logger.error("Error al consultar el contenido estatico habilitado." +  
					"* Id. Plantilla: " + plantillaId + ", * Id. Etiqueta: " + etiquetaId, e);
		}
	
		return contenidoEstaticoEtiqueta;
	}
	
//	public static NotificacionDocumento getNotificacionDocumento(EntityManager em, String administrativeFileId, String documentId) {
//		NotificacionDocumento notificacionDocumento = null;
//		
//		try {
//			Query q = em.createNamedQuery(NotificacionDocumento.findByDocumentIdAndAdministrativeFileId);
//			q.setParameter("documentId", documentId);
//			q.setParameter("administrativeFileId", administrativeFileId);
//			notificacionDocumento = (NotificacionDocumento) q.getSingleResult();	
//		} catch (NoResultException e) {
//			logger.info("No existe informacion de notificacion electronica del documento de la base de datos. " + 
//					"* Num. expediente: " + administrativeFileId + ", * Nombre documento: " + documentId);
//		} catch (Exception e) {
//			logger.error("Error al consultar la informacion de notificacion electronica del documento de la base de datos. " +
//					"* Num. expediente: " + administrativeFileId + ", * Nombre documento: " + documentId, e);
//		}				
//	
//		return notificacionDocumento;
//	}
	
//	public static NotificacionDocumento getNotificacionDocumentoByUri(EntityManager em, String uri) {
//		NotificacionDocumento notificacionDocumento = null;
//		
//		try {
//			Query q = em.createNamedQuery(NotificacionDocumento.findByUri);
//			q.setParameter("uri", uri);
//			notificacionDocumento = (NotificacionDocumento) q.getSingleResult();	
//		} catch (NoResultException e) {
//			logger.info("No existe informacion de notificacion electronica del documento de la base de datos. " + 
//					"* Uri: " + uri);
//		} catch (Exception e) {
//			logger.error("Error al consultar la informacion de notificacion electronica del documento de la base de datos. " + 
//					"* Uri: " + uri);
//		}			
//	
//		return notificacionDocumento;
//	}
	
//	public static NotificacionDocumento getNotificacionDocumentoByNumNotificacion(EntityManager em, String numNotificacion) {
//		NotificacionDocumento notificacionDocumento = null;
//		
//		try {
//			Query q = em.createNamedQuery(NotificacionDocumento.findByNumNotificacion);
//			q.setParameter("notificationId", numNotificacion);
//			notificacionDocumento = (NotificacionDocumento) q.getSingleResult();	
//		} catch (NoResultException e) {
//			logger.info("No existe informacion de notificacion electronica del documento de la base de datos. " + 
//					"* Num. notificacion: " + numNotificacion);
//		} catch (Exception e) {
//			logger.error("Error al consultar la informacion de notificacion electronica del documento de la base de datos. " + 
//					"* Num. notificacion: " + numNotificacion);
//		}			
//	
//		return notificacionDocumento;
//	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> getDocumentosExpedienteNotificados(EntityManager em, String administrativeFileId, List<Documento> documentos) {
		List<Documento> documentosNotificados = null;

		try {
			String docsList = "";
			for (int i = 0; i < documentos.size(); i++) {
				Documento doc = documentos.get(i);
				if (i > 0) {
					docsList += ", ";
				}
				docsList += "'" + doc.getPk().getId() + "'";
			}
			String queryStr = "SELECT d FROM NotificacionDocumento n JOIN n.document d WHERE " + 
								"d.pk.expediente = '" + administrativeFileId + "' AND d.pk.id IN (" + docsList + ")";
			Query q = em.createQuery(queryStr);
			documentosNotificados = (List<Documento>) q.getResultList();			
		} catch (Exception e) {
			logger.error("Error al consultar informacion de documentos notificados electronicamente para el expediente " + administrativeFileId, e);
		}
	
		return documentosNotificados;
	}
	
	public static HashMap<String, String> getNotificacionesIdsDocumentosExpediente(EntityManager em, String administrativeFileId, List<Documento> documentos) {
		HashMap<String, String> notificacionesIdsMap = new HashMap<String, String>();

		try {
			String docsList = "";
			for (int i = 0; i < documentos.size(); i++) {
				Documento doc = documentos.get(i);
				if (i > 0) {
					docsList += ", ";
				}
				docsList += "'" + doc.getPk().getId() + "'";
			}
			String queryStr = "SELECT n FROM NotificacionDocumento n JOIN n.document d WHERE " + 
								"d.pk.expediente = '" + administrativeFileId + "' AND d.pk.id IN (" + docsList + ")";
			Query q = em.createQuery(queryStr);
//			List<NotificacionDocumento> notificacionesDocumentos = (List<NotificacionDocumento>) q.getResultList();	
//			if (notificacionesDocumentos != null && !notificacionesDocumentos.isEmpty()) {
//				for (NotificacionDocumento notifDoc:notificacionesDocumentos) {
//					notificacionesIdsMap.put(notifDoc.getDocument().getPk().getId(), notifDoc.getNotificationId());
//				}
//			}
		} catch (Exception e) {
			logger.error("Error al consultar informacion de documentos notificados electronicamente para el expediente " + administrativeFileId, e);
		}
	
		return notificacionesIdsMap;
	}
	
//	@SuppressWarnings("unchecked")
//	public static HashMap<String, NotificacionPapelDocumento> getNotificacionesPapelDocumentos(EntityManager em, String adminFileId, List<Documento> documentos) {
//		HashMap<String, NotificacionPapelDocumento> notificacionesMap = new HashMap<String, NotificacionPapelDocumento>();
//
//		try {
//			String docsList = "";
//			for (int i = 0; i < documentos.size(); i++) {
//				Documento doc = documentos.get(i);
//				if (i > 0) {
//					docsList += ", ";
//				}
//				docsList += "'" + doc.getPk().getId() + "'";
//			}
//			String queryStr = "SELECT n FROM NotificacionPapelDocumento n WHERE n.expedienteId = '" + adminFileId + "' AND n.documentoId IN (" + docsList + ")";
//			Query q = em.createQuery(queryStr);
//			List<NotificacionPapelDocumento> notificacionesDocumentos = (List<NotificacionPapelDocumento>) q.getResultList();	
//			if (notificacionesDocumentos != null && !notificacionesDocumentos.isEmpty()) {
//				for (NotificacionPapelDocumento notifDoc:notificacionesDocumentos) {
//					notificacionesMap.put(notifDoc.getDocumentoId(), notifDoc);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Error al consultar informacion de documentos con notificacion en papel para el expediente " + adminFileId, e);
//		}
//	
//		return notificacionesMap;
//	}	
	
//	public static NotificacionPapelDocumento getNotifPresencialDocumento(EntityManager em, String adminFileId, String documentId, boolean electronic) {
//		NotificacionPapelDocumento notificacionDocumento = null;
//		try {
//			Query q = em.createNamedQuery("NotificacionPapelDocumento.findByDocumentoIdAndExpedienteId");
//			q.setParameter("documentoId", documentId);
//			q.setParameter("expedienteId", adminFileId);
//			q.setParameter("electronica", electronic);
//			notificacionDocumento = (NotificacionPapelDocumento) q.getSingleResult();	
//		} catch (NoResultException e) {
//			logger.info("No existe informacion de notificacion presencial del documento en la base de datos. " + 
//					"* Num. expediente: " + adminFileId + ", * Nombre documento: " + documentId + ", * Electronica: " + electronic);
//		} catch (Exception e) {
//			logger.error("Error al consultar la informacion de notificacion presencial del documento de la base de datos. " +
//					"* Num. expediente: " + adminFileId + ", * Nombre documento: " + documentId + ", * Electronica: " + electronic, e);
//		}
//		
//		return notificacionDocumento;
//	}		
	
	
//	@SuppressWarnings("unchecked")
//	public static HashMap<String, EstadoNotificacionDocumento> getEstadosDocumentosExpedienteNotificados(EntityManager em, String adminFileId) {		
//		HashMap<String, EstadoNotificacionDocumento> estadosDocsNotificados = new HashMap<String, EstadoNotificacionDocumento>();
//		
//		try {
//			Query q = em.createNamedQuery("NotificacionDocumento.findByAdministrativeFileId");
//			q.setParameter("administrativeFileId", adminFileId);
//			List<NotificacionDocumento> notificacionesElectronicas = (List<NotificacionDocumento>) q.getResultList();
//			if (notificacionesElectronicas != null && !notificacionesElectronicas.isEmpty()) {
//				for (NotificacionDocumento notifDoc:notificacionesElectronicas) {
//					EstadoNotificacionDocumento estadoNotifDoc = notifDoc.getEstadoNotificacion();
//					if (estadoNotifDoc != null && !ExpedientesConstants.DOC_ESTADO_SIN_NOTIFICAR.equals(estadoNotifDoc.getEstado())) {
//						estadosDocsNotificados.put(notifDoc.getDocument().getPk().getId(), estadoNotifDoc);
//						if (null != notifDoc.getAttachments() && !notifDoc.getAttachments().isEmpty()) {
//							for (AdjuntoNotificacionExpediente attach:notifDoc.getAttachments()) {
//								estadosDocsNotificados.put(attach.getDocumento().getPk().getId(), estadoNotifDoc);
//							}
//						}
//					}
//				}				
//			}
//			
//			q = em.createNamedQuery("NotificacionPapelDocumento.findByExpedienteId");
//			q.setParameter("expedienteId", adminFileId);
//			List<NotificacionPapelDocumento> notificacionesPapel = (List<NotificacionPapelDocumento>) q.getResultList();
//			if (notificacionesPapel != null && !notificacionesPapel.isEmpty()) {
//				for (NotificacionPapelDocumento notifDoc:notificacionesPapel) {
//					EstadoNotificacionDocumento estadoNotifDoc = notifDoc.getEstadoNotificacion();
//					if (estadoNotifDoc != null && !ExpedientesConstants.DOC_ESTADO_SIN_NOTIFICAR.equals(estadoNotifDoc.getEstado())) {
//						estadosDocsNotificados.put(notifDoc.getDocumentoId(), estadoNotifDoc);
//					}				
//				}				
//			}			
//		} catch (Exception e) {
//			logger.error("Error al consultar informacion de documentos notificados para el expediente " + adminFileId, e);
//		}
//		
//		return estadosDocsNotificados;
//	}
	
	public static Documento getDocumentoExpedienteByUri(EntityManager em, String uri) {
		Documento documento = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findByUri);
			q.setParameter("uri", uri);
			documento = (Documento) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe un documento de expediente con uri " + uri + " en la base de datos.");
		} catch (Exception e) {
			logger.error("Error al consultar el documento de expediente con uri " + uri + " en la base de datos.", e);
		}				
	
		return documento;
	}
	
	public static Documento existeDocumentoExpedienteByUri(EntityManager em, String uri) throws NoResultException {
		Documento documento = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findByUri);
			q.setParameter("uri", uri);
			documento = (Documento) q.getSingleResult();			
		} catch (NoResultException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error al consultar el documento de expediente con uri " + uri + " en la base de datos.", e);
		}
	
		return documento;
	}
	
	public static Documento getDocumentoExpedienteById(EntityManager em, String adminFileId, String documentId) {
		Documento documento = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findByPk);
			q.setParameter("docName", documentId);
			q.setParameter("numExp", adminFileId);
			documento = (Documento) q.getSingleResult();	
		} catch (NoResultException e) {
			logger.info("No existe un documento en la base de datos con la siguiente informacion. " + 
					"* Num. expediente: " + adminFileId + ", * Nombre documento: " + documentId);
		} catch (Exception e) {
			logger.error("Error al consultar el documento de la base de datos. " +
					"* Num. expediente: " + adminFileId + ", * Nombre documento: " + documentId, e);
		}				
	
		return documento;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Documento> getDocumentosExpedienteByUris(EntityManager em, String numExpediente, String idActuacion, List<String> uris) {
		HashMap<String, Documento> documentosMap = null;
		
		try {
			Query q = null;
			if (numExpediente != null && !numExpediente.trim().equals("")) {
				if (idActuacion == null || idActuacion.trim().equals("")) {
					q = em.createNamedQuery(Documento.findDocumentosExpByUris);
					q.setParameter("uris", uris);
					q.setParameter("expediente", numExpediente);
				} else {
					q = em.createNamedQuery(Documento.findDocumentosActuacionByUris);
					q.setParameter("uris", uris);
					q.setParameter("actuacionId", idActuacion);					
				}
			} else {
				q = em.createNamedQuery(Documento.findDocumentosByUris);
				q.setParameter("uris", uris);
			}
			
			List<Documento> documentos = (List<Documento>) q.getResultList();
			if (documentos != null && !documentos.isEmpty()) {
				documentosMap = new HashMap<String, Documento>();
				for (Documento doc:documentos) {
					documentosMap.put(doc.getUri(), doc);
				}
			}
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de documentos con la siguiente informacion. " + 
					"* Num. expediente: " + numExpediente + "| * Num. Actuacion: " + idActuacion + "| * Uris: " + 
							org.apache.commons.lang3.StringUtils.join(uris, ","), e);
		}	
	
		return documentosMap;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Documento> getDocumentosExpedienteRegistro(EntityManager em, String numExpediente, String numRegistro) {
		Map<String, Documento> documentosMap = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findDocumentosExpByRegistro);
			q.setParameter("expediente", numExpediente);
			q.setParameter("numRegistro", numRegistro);					
		
			List<Documento> documentos = (List<Documento>) q.getResultList();
			if (null != documentos && !documentos.isEmpty()) {
				documentosMap = new HashMap<String, Documento>();
				for (Documento doc:documentos) {
					documentosMap.put(doc.getUri(), doc);
				}
			}
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de documentos con la siguiente informacion. " + 
					"* Num. expediente: " + numExpediente + "| * Num. Registro: " + numRegistro, e);
		}	
	
		return documentosMap;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> getDocumentosExp(EntityManager em, String numExp) {
		List<Documento> documentos = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findDocumentosExp);
			q.setParameter("expediente", numExp);
			documentos = (List<Documento>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de documentos para el expediente: " + numExp, e);
		}	
	
		return documentos;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> getDocumentosExpNoCancelados(EntityManager em, String numExp) {
		List<Documento> documentos = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findDocumentosExpNoCancelados);
			q.setParameter("expediente", numExp);
			documentos = (List<Documento>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de documentos no cancelados para el expediente: " + numExp, e);
		}
	
		return documentos;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Documento> getDocumentosExpConRegistroByUris(EntityManager em, List<String> uris, String tipoRegistro) {
		HashMap<String, Documento> documentosMap = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findDocumentosConRegistroByUris);
			q.setParameter("uris", uris);
			q.setParameter("tipoRegistro", tipoRegistro);
			List<Documento> documentos = (List<Documento>) q.getResultList();
			if (documentos != null && !documentos.isEmpty()) {
				documentosMap = new HashMap<String, Documento>();
				for (Documento doc:documentos) {
					documentosMap.put(doc.getUri(), doc);
				}
			}
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de documentos de expediente con registro asociado. " + 
					"* Tipo Registro: " + tipoRegistro + "| * Uris: " + org.apache.commons.lang3.StringUtils.join(uris, ","), e);
		}			
	
		return documentosMap;
	}	
	
	@SuppressWarnings("unchecked")
	public static List<Documento> getDocumentosFirma(EntityManager em, String numExpediente, String docName) {
		List<Documento> documentos = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findDocumentosFirmaById);
			q.setParameter("expediente", numExpediente);
			q.setParameter("signDocName", docName + "_signature%");
			documentos = (List<Documento>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar los documentos de firma de la base de datos. " +
					"* Num. expediente: " + numExpediente + ", * Nombre documento: " + docName, e);
		}	
	
		return documentos;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> getDocumentoYFirmas(EntityManager em, String numExpediente, String docName) {
		List<Documento> documentos = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findDocumentoYFirmasById);
			q.setParameter("expediente", numExpediente);
			q.setParameter("docName", docName);
			q.setParameter("signDocName", docName + "_signature%");
			documentos = (List<Documento>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar el documento y sus documentos de firma de la base de datos. " +
					"* Num. expediente: " + numExpediente + ", * Nombre documento: " + docName, e);
		}				
	
		return documentos;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> getDocumentosActuacion(EntityManager em, String idActuacion) {
		List<Documento> documentos = null;
		
		try {
			Query q = em.createNamedQuery("Documento.findDocumentosActuacion");
			q.setParameter("actuacionId", idActuacion);
			documentos = (List<Documento>) q.getResultList();
		} catch (Exception e) {
			logger.error("No se ha podido recuperar los documentos de la actuacion " + idActuacion + " de la base de datos", e);
		}
	
		return documentos;
	}
	
	public static Documento getJustificanteAsiento(EntityManager em, String numeroAsiento) {
		Documento documento = null;
		
		try {
			Query q = em.createNamedQuery(Documento.findJustificanteByAsiento);
			q.setParameter("recordNumber", numeroAsiento);
			documento = (Documento) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("El asiento " + numeroAsiento + " no tiene documentos");
		} catch (Exception e) {
			logger.error("No se ha podido recuperar los documentos del asiento  " + numeroAsiento + " de la base de datos", e);
		}
	
		return documento;
	}
	
	@SuppressWarnings("unchecked")
	public static GestionPortafirmasDocumento getGestionPortafirmasDocByUri(EntityManager em, String uri) {
		GestionPortafirmasDocumento gestionPortafirmasDoc = null;
		
		try {
			Query q = em.createNamedQuery("GestionPortafirmasDocumento.findByUri");
			q.setParameter("uri", uri);

			List<GestionPortafirmasDocumento> listEnvios = (List<GestionPortafirmasDocumento>) q.getResultList();
			if (listEnvios != null && !listEnvios.isEmpty()) {
				gestionPortafirmasDoc = listEnvios.get(0);
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar la peticion de envio a portafirmas del documento con uri " + uri + " de la base de datos", e);
		}
	
		return gestionPortafirmasDoc;
	}	
	
	@SuppressWarnings("unchecked")
	public static FirmaManuscritaDocumento getFirmaManuscritaDocByUri(EntityManager em, String uri) {
		FirmaManuscritaDocumento firmaManuscritaDoc = null;
		
		try {
			Query q = em.createNamedQuery("FirmaManuscritaDocumento.findByUri");
			q.setParameter("uri", uri);

			List<FirmaManuscritaDocumento> listEnvios = (List<FirmaManuscritaDocumento>) q.getResultList();
			if (listEnvios != null && !listEnvios.isEmpty()) {
				firmaManuscritaDoc = listEnvios.get(0);
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar la peticion de envio a la firma manuscrita del documento con uri " + uri + " de la base de datos", e);
		}
	
		return firmaManuscritaDoc;
	}	
	
	public static FirmaManuscritaDocumento getFirmaManuscritaDocByDocGui(EntityManager em, String docGui) throws NoResultException {
		FirmaManuscritaDocumento firmaManuscritaDoc = null;
		
		try {
			Query q = em.createNamedQuery("FirmaManuscritaDocumento.findByDocGui");
			q.setParameter("docGui", docGui);

			firmaManuscritaDoc = (FirmaManuscritaDocumento) q.getSingleResult();
		} catch (NoResultException e) {
			logger.warn("No existe ninguna peticion de envio a firma manuscrita registrada en base de datos con docGui " + docGui, e);
			throw e;
		} catch (Exception e) {
			logger.error("No se ha podido recuperar la peticion de envio a firma manuscrita de un documento de la base de datos", e);
		}
	
		return firmaManuscritaDoc;
	}
	
	@SuppressWarnings("unchecked")
	public static List<FirmaManuscritaDocumento> getFirmaManuscritaDocumentsByURI(EntityManager em, List<String> uris) {
		List<FirmaManuscritaDocumento> listDocumentos = new ArrayList<FirmaManuscritaDocumento>();
		try {
			listDocumentos = (List<FirmaManuscritaDocumento>) em.createNamedQuery("FirmaManuscritaDocumento.findByUris")
					.setParameter("uris", uris).getResultList();
		} catch (Exception e) {
			logger.error("No se han podido recuperar los documentos enviados a firma manuscrita de la base de datos", e);
		}

		return listDocumentos;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, CatPermisoDispositivo> getDispositivosFirmaManuscrita(EntityManager em, String usuarioId, List<String> departamentosIds) {
		Map<String, CatPermisoDispositivo> mapPermisosDispositivos = new HashMap<String, CatPermisoDispositivo>();
		try {
			List<CatPermisoDispositivo> listPermisosDispositivos = null;
			if (StringUtils.isNullOrEmpty(usuarioId) && (departamentosIds == null || departamentosIds.isEmpty())) {
				listPermisosDispositivos = (List<CatPermisoDispositivo>) em.createNamedQuery("CatPermisoDispositivo.findPermisosDispositivoByTipo")
						.setParameter("permisoTipo", CatPermisoDispositivo.TIPO_PERMISO_FIRMA_MANUSCRITA)
						.getResultList();
			} else {
				listPermisosDispositivos = (List<CatPermisoDispositivo>) em.createNamedQuery("CatPermisoDispositivo.findPermisosDispositivoByDestinatarioAndTipo")
						.setParameter("usuarioId", usuarioId)
						.setParameter("departamentosIds", departamentosIds)
						.setParameter("permisoTipo", CatPermisoDispositivo.TIPO_PERMISO_FIRMA_MANUSCRITA)
						.getResultList();
			}
			if (listPermisosDispositivos != null && !listPermisosDispositivos.isEmpty()) {
				for (CatPermisoDispositivo permisoDispositivo:listPermisosDispositivos) {
					mapPermisosDispositivos.put(permisoDispositivo.getDispositivo().getIdFirmaManuscrita(), permisoDispositivo);
				}
			}
		} catch (Exception e) {
			logger.error("No se han podido recuperar de la base de datos los dispositivos de firma manuscrita teniendo en cuenta los siguientes criterios. " +
					"* Id. usuario: " + usuarioId + " | * Ids. Departamentos: " + org.apache.commons.lang3.StringUtils.join(departamentosIds, ","));
		}

		return mapPermisosDispositivos;
	}
	
	@SuppressWarnings("unchecked")
	public static List<FirmaManuscritaDocumentoWSDL> getWsdlsFirmaManuscritaDoc(EntityManager em, String docUri) {
		List<FirmaManuscritaDocumentoWSDL> listFirmaManuscritaDocWsdls = null;
		try {
			listFirmaManuscritaDocWsdls = (List<FirmaManuscritaDocumentoWSDL>) em.createNamedQuery("FirmaManuscritaDocumentoWSDL.findWSDLByURI")
						.setParameter("uri", docUri)
						.getResultList();
		} catch (Exception e) {
			logger.error("No se han podido recuperar los WDLS de respuesta asociados al documento de firma manuscrita con uri " + docUri, e);
		}

		return listFirmaManuscritaDocWsdls;
	}
	
	@SuppressWarnings("unchecked")
	public static FirmaManuscritaDocumentoDestinatario getFirmaManuscritaDocDest(EntityManager em, String docUri, String identificadorFirmante) {
		FirmaManuscritaDocumentoDestinatario destinatario = null;
		try {
			List<FirmaManuscritaDocumentoDestinatario> listDestinatarios = (List<FirmaManuscritaDocumentoDestinatario>) em.createNamedQuery(
					"FirmaManuscritaDocumentoDestinatario.findByDocUriAndFirmanteId")
						.setParameter("uri", docUri)
						.setParameter("identificadorFirmante", identificadorFirmante)
						.getResultList();
			if (listDestinatarios != null && !listDestinatarios.isEmpty()) {
				return destinatario = listDestinatarios.get(0);
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar de la base de datos el destinatario con identificacion " + identificadorFirmante + " asociado al documento con uri " + docUri, e);
		}

		return destinatario;
	}

	
	@SuppressWarnings("unchecked")
	public static HashMap<String, List<GestionPortafirmasDocumento>> getPeticionesPortafirmas(EntityManager em, List<String> expedientesIds, List<String> actuacionesIds) {
		HashMap<String, List<GestionPortafirmasDocumento>> peticionesPortafirmasMap = new HashMap<String, List<GestionPortafirmasDocumento>>();
		boolean consultarPorIdsExpedientes = (expedientesIds != null && !expedientesIds.isEmpty());
		boolean consultarPorIdsActuaciones = (actuacionesIds != null && !actuacionesIds.isEmpty());
		
		try {
			String queryStr = "FROM GestionPortafirmasDocumento gpd";
			String clausulaWhereStr = "";
			if (consultarPorIdsExpedientes || consultarPorIdsActuaciones) {
				queryStr += " WHERE ";
				if (consultarPorIdsExpedientes) {
					clausulaWhereStr = "gpd.numExpediente IN (:expedientesIds)";
				}
				if (consultarPorIdsActuaciones) {
					clausulaWhereStr += clausulaWhereStr.equals("")?"":" OR ";
					clausulaWhereStr += "gpd.numActuacion IN (:actuacionesIds)";
				}
			}
			Query query = em.createQuery(queryStr + clausulaWhereStr);
			if (consultarPorIdsExpedientes) {
				query.setParameter("expedientesIds", expedientesIds);
			}
			if (consultarPorIdsActuaciones) {
				query.setParameter("actuacionesIds", actuacionesIds);
			}
			List<GestionPortafirmasDocumento> peticionesPortafirmasList = (List<GestionPortafirmasDocumento>) query.getResultList();
			if (peticionesPortafirmasList != null && !peticionesPortafirmasList.isEmpty()) {
				for (GestionPortafirmasDocumento peticionPortafirmas:peticionesPortafirmasList) {
					List<GestionPortafirmasDocumento> peticionesActuales = null;
					if (consultarPorIdsExpedientes) {
						String numExp = peticionPortafirmas.getNumExpediente();
						if (expedientesIds.contains(numExp)) {
							peticionesActuales = new ArrayList<GestionPortafirmasDocumento>();
							if (peticionesPortafirmasMap.containsKey(numExp)) {
								peticionesActuales = peticionesPortafirmasMap.get(numExp);
							}
							peticionesActuales.add(peticionPortafirmas);
							peticionesPortafirmasMap.put(numExp, peticionesActuales);
						}
					}
					if (consultarPorIdsActuaciones) {
						String numActuacion = peticionPortafirmas.getNumActuacion();
						if (numActuacion != null && !numActuacion.isEmpty() && actuacionesIds.contains(numActuacion)) {
							peticionesActuales = new ArrayList<GestionPortafirmasDocumento>();
							if (peticionesPortafirmasMap.containsKey(numActuacion)) {
								peticionesActuales = peticionesPortafirmasMap.get(numActuacion);
							}
							peticionesActuales.add(peticionPortafirmas);
							peticionesPortafirmasMap.put(numActuacion, peticionesActuales);
						}
					}
				}
			}	
		} catch(Exception e){
			logger.error("No se ha podido recuperar las peticiones de portafirmas. * Ids. Expedientes: " + 
					org.apache.commons.lang3.StringUtils.join(expedientesIds, ",") +  " | * Ids. Actuaciones: " + 
					org.apache.commons.lang3.StringUtils.join(actuacionesIds, ","), e);
		}
		
		return peticionesPortafirmasMap;
	}
	
	public static HashMap<String, PeticionesItem> getPeticionesPortafirmas(EntityManager em, List<String> ids, 
			Boolean comprobarFinalizadas, Date peticionesFinalizadasDesde, Date peticionesFinalizadasHasta, boolean portaActuaciones) {
		
		HashMap<String, PeticionesItem> peticionesPortafirmasMap = new HashMap<String, PeticionesItem>();
		
		try {
			List<String> resultFields = null;
			List<String> tablesNames = null;
			String inField = null;
			List<String> additionalConditions = null;
			if (portaActuaciones) {
				resultFields = Arrays.asList("d.actuacion_id_", "gpd.estado_firma", "gpd.fecha_respuesta");
				tablesNames = Arrays.asList("gestion_portafirmas_doc gpd, document_ d");
				inField = "d.actuacion_id_";
				additionalConditions = Arrays.asList("d.repository_uri_ = gpd.uri_doc_repository");
			} else {
				resultFields = Arrays.asList("administrative_file_id", "estado_firma", "fecha_respuesta");
				tablesNames = Arrays.asList("gestion_portafirmas_doc");
				inField = "administrative_file_id";
			}
			List<Object[]> results = getResultadosPeticiones(em, resultFields, tablesNames, inField, ids, additionalConditions);

			if (results != null && !results.isEmpty()) {
				HashMap<String, Boolean> peticionesFinalizadasRangoExpsMap = new HashMap<String, Boolean>();
				for (Object[] result:results) {
					int i = 0;
					String numExp = (String)result[i++];
					String pfEstadoFirma = ((String)result[i++]).trim();
					Date pfFechaRespuesta = (Date)result[i++];
					
					PeticionesItem peticionesPortafirmas = new PeticionesItem();
					if (peticionesPortafirmasMap.containsKey(numExp)) {
						peticionesPortafirmas = peticionesPortafirmasMap.get(numExp);
					}
					if (ExpedientesConstants.DOC_ESTADO_PENDIENTE_PORTAFIRMAS.equalsIgnoreCase(pfEstadoFirma)) {
						peticionesPortafirmas.setNumPeticionesPendientes(peticionesPortafirmas.getNumPeticionesPendientes()+1);
					} else if (ExpedientesConstants.DOC_ESTADO_FIRMADO_PORTAFIRMAS.equalsIgnoreCase(pfEstadoFirma)) {
						peticionesPortafirmas.setNumPeticionesAceptadas(peticionesPortafirmas.getNumPeticionesAceptadas()+1);
					} else if (ExpedientesConstants.DOC_ESTADO_RECHAZADO_PORTAFIRMAS.equalsIgnoreCase(pfEstadoFirma)) {
						peticionesPortafirmas.setNumPeticionesRechazadas(peticionesPortafirmas.getNumPeticionesRechazadas()+1);
					}
					peticionesPortafirmas.setNumPeticionesFinalizadas(peticionesPortafirmas.getNumPeticionesAceptadas() + 
							peticionesPortafirmas.getNumPeticionesRechazadas());
					peticionesPortafirmas.setNumPeticionesTotal(peticionesPortafirmas.getNumPeticionesPendientes() + 
							peticionesPortafirmas.getNumPeticionesFinalizadas());
					if (Boolean.TRUE.equals(comprobarFinalizadas) && (!peticionesFinalizadasRangoExpsMap.containsKey(numExp)
							|| !(Boolean.TRUE.equals(peticionesFinalizadasRangoExpsMap.get(numExp))))) {
						Boolean peticionesFinalizadasRango = null;
						if (pfFechaRespuesta != null) {
							if (peticionesFinalizadasDesde == null && peticionesFinalizadasHasta == null) {
								peticionesFinalizadasRango = Boolean.TRUE;
							} else if (peticionesFinalizadasDesde != null && peticionesFinalizadasHasta == null) {
								peticionesFinalizadasDesde = DateUtils.getDateFirstTime(peticionesFinalizadasDesde);
								peticionesFinalizadasRango = (pfFechaRespuesta.compareTo(peticionesFinalizadasDesde) >= 0);
							} else if (peticionesFinalizadasDesde == null && peticionesFinalizadasHasta != null) {
								peticionesFinalizadasHasta = DateUtils.getDateLastTime(peticionesFinalizadasHasta);
								peticionesFinalizadasRango = (pfFechaRespuesta.compareTo(peticionesFinalizadasHasta) <= 0);
							} else {
								peticionesFinalizadasDesde = DateUtils.getDateFirstTime(peticionesFinalizadasDesde);
								peticionesFinalizadasHasta = DateUtils.getDateLastTime(peticionesFinalizadasHasta);
								peticionesFinalizadasRango = (pfFechaRespuesta.compareTo(peticionesFinalizadasHasta) <= 0 && 
										pfFechaRespuesta.compareTo(peticionesFinalizadasDesde) >= 0);
							}
							peticionesPortafirmas.setPeticionesFinalizadasRango(peticionesFinalizadasRango);
						}
						peticionesFinalizadasRangoExpsMap.put(numExp, peticionesFinalizadasRango);
					}
					peticionesPortafirmasMap.put(numExp, peticionesPortafirmas);
				}
			}	
		} catch(Exception e){
			logger.error("No se ha podido recuperar las peticiones de portafirmas", e);
		}
		
		return peticionesPortafirmasMap;
	}
	
	public static HashMap<String, PeticionesItem> getPeticionesActuaciones(EntityManager em, List<String> expedientesIds,
			Boolean comprobarFinalizadas, Date peticionesFinalizadasDesde, Date peticionesFinalizadasHasta) {
		
		HashMap<String, PeticionesItem> peticionesActuacionesMap = new HashMap<String, PeticionesItem>();
		
		try {
			List<String> resultFields = Arrays.asList("admin_file_id", "tramite_actual", "fecha_fin");
			List<String> tablesNames = Arrays.asList("actuacion");
			String inField = "admin_file_id";
			List<Object[]> results = getResultadosPeticiones(em, resultFields, tablesNames, inField, expedientesIds, null);

			if (results != null && !results.isEmpty()) {
				HashMap<String, Boolean> peticionesFinalizadasRangoExpsMap = new HashMap<String, Boolean>();
				for (Object[] result:results) {
					int i = 0;
					String numExp = (String)result[i++];
					String actTramiteActual = ((String)result[i++]).trim();
					Date actFechaFin = (Date)result[i++];
			
					PeticionesItem peticionesActuaciones = new PeticionesItem();
					if (peticionesActuacionesMap.containsKey(numExp)) {
						peticionesActuaciones = peticionesActuacionesMap.get(numExp);
					}
					if (actFechaFin == null) {
						peticionesActuaciones.setNumPeticionesPendientes(peticionesActuaciones.getNumPeticionesPendientes()+1);
					} else if (actFechaFin != null && ExpedientesConstants.ACTUACION_ESTADO_FINALIZADO.equalsIgnoreCase(actTramiteActual)) {
						peticionesActuaciones.setNumPeticionesAceptadas(peticionesActuaciones.getNumPeticionesAceptadas()+1);
					} else if (actFechaFin != null && ExpedientesConstants.ACTUACION_ESTADO_FINALIZADO.equalsIgnoreCase(actTramiteActual)) {
						peticionesActuaciones.setNumPeticionesRechazadas(peticionesActuaciones.getNumPeticionesRechazadas()+1);
					}
					peticionesActuaciones.setNumPeticionesFinalizadas(peticionesActuaciones.getNumPeticionesAceptadas() + 
							peticionesActuaciones.getNumPeticionesRechazadas());
					peticionesActuaciones.setNumPeticionesTotal(peticionesActuaciones.getNumPeticionesPendientes() + 
							peticionesActuaciones.getNumPeticionesFinalizadas());
					if (Boolean.TRUE.equals(comprobarFinalizadas) && (!peticionesFinalizadasRangoExpsMap.containsKey(numExp)
							|| !(Boolean.TRUE.equals(peticionesFinalizadasRangoExpsMap.get(numExp))))) {
						Boolean peticionesFinalizadasRango = null;
						if (actFechaFin != null) {
							if (peticionesFinalizadasDesde == null && peticionesFinalizadasHasta == null) {
								peticionesFinalizadasRango = Boolean.TRUE;
							} else if (peticionesFinalizadasDesde != null && peticionesFinalizadasHasta == null) {
								peticionesFinalizadasDesde = DateUtils.getDateFirstTime(peticionesFinalizadasDesde);
								peticionesFinalizadasRango = (actFechaFin.compareTo(peticionesFinalizadasDesde) >= 0);
							} else if (peticionesFinalizadasDesde == null && peticionesFinalizadasHasta != null) {
								peticionesFinalizadasHasta = DateUtils.getDateLastTime(peticionesFinalizadasHasta);
								peticionesFinalizadasRango = (actFechaFin.compareTo(peticionesFinalizadasHasta) <= 0);
							} else {
								peticionesFinalizadasDesde = DateUtils.getDateFirstTime(peticionesFinalizadasDesde);
								peticionesFinalizadasHasta = DateUtils.getDateLastTime(peticionesFinalizadasHasta);
								peticionesFinalizadasRango = (actFechaFin.compareTo(peticionesFinalizadasHasta) <= 0 && 
										actFechaFin.compareTo(peticionesFinalizadasDesde) >= 0);
							}
							peticionesActuaciones.setPeticionesFinalizadasRango(peticionesFinalizadasRango);
						}
						peticionesFinalizadasRangoExpsMap.put(numExp, peticionesFinalizadasRango);
					}
					peticionesActuacionesMap.put(numExp, peticionesActuaciones);
				}
			}	
		} catch(Exception e){
			logger.error("No se ha podido recuperar las peticiones de actuaciones", e);
		}
		
		return peticionesActuacionesMap;
	}
	
	public static HashMap<String, PeticionesItem> getPeticionesRequerimientos(EntityManager em, List<String> expedientesIds,
			Boolean comprobarFinalizadas, Date peticionesFinalizadasDesde, Date peticionesFinalizadasHasta) {
		
		HashMap<String, PeticionesItem> peticionesRequerimientosMap = new HashMap<String, PeticionesItem>();
		
		try {
			List<String> resultFields = Arrays.asList("id_expediente", "estado", "fecha_fin");
			List<String> tablesNames = Arrays.asList("requerimientos");
			String inField = "id_expediente";
			List<Object[]> results = getResultadosPeticiones(em, resultFields, tablesNames, inField, expedientesIds, null);
			
			if (results != null && !results.isEmpty()) {
				HashMap<String, Boolean> peticionesFinalizadasRangoExpsMap = new HashMap<String, Boolean>();
				for (Object[] result:results) {
					int i = 0;
					String numExp = (String)result[i++];
					String reqEstado = ((String)result[i++]).trim();
					Date reqFechaFin = (Date)result[i++];
					
					PeticionesItem peticionesRequerimientos = new PeticionesItem();
					if (peticionesRequerimientosMap.containsKey(numExp)) {
						peticionesRequerimientos = peticionesRequerimientosMap.get(numExp);
					}
					if (ExpedientesConstants.REQ_ESTADO_NO_RESPONDIDO.equalsIgnoreCase(reqEstado) ||
							ExpedientesConstants.REQ_ESTADO_NOTIFICADO.equalsIgnoreCase(reqEstado)) {
						peticionesRequerimientos.setNumPeticionesPendientes(peticionesRequerimientos.getNumPeticionesPendientes()+1);
					} else if (ExpedientesConstants.REQ_ESTADO_RESPONDIDO.equalsIgnoreCase(reqEstado) ||
							ExpedientesConstants.REQ_ESTADO_CANCELADO.equalsIgnoreCase(reqEstado)) {
						peticionesRequerimientos.setNumPeticionesAceptadas(peticionesRequerimientos.getNumPeticionesAceptadas()+1);
					} else if (ExpedientesConstants.REQ_ESTADO_CADUCADO.equalsIgnoreCase(reqEstado)) {
						peticionesRequerimientos.setNumPeticionesRechazadas(peticionesRequerimientos.getNumPeticionesRechazadas()+1);
					}
					peticionesRequerimientos.setNumPeticionesFinalizadas(peticionesRequerimientos.getNumPeticionesAceptadas() + 
							peticionesRequerimientos.getNumPeticionesRechazadas());
					peticionesRequerimientos.setNumPeticionesTotal(peticionesRequerimientos.getNumPeticionesPendientes() + 
							peticionesRequerimientos.getNumPeticionesFinalizadas());
					if (Boolean.TRUE.equals(comprobarFinalizadas) && (!peticionesFinalizadasRangoExpsMap.containsKey(numExp)
							|| !(Boolean.TRUE.equals(peticionesFinalizadasRangoExpsMap.get(numExp))))) {
						Boolean peticionesFinalizadasRango = null;
						if (reqFechaFin != null) {
							if (peticionesFinalizadasDesde == null && peticionesFinalizadasHasta == null) {
								peticionesFinalizadasRango = Boolean.TRUE;
							} else if (peticionesFinalizadasDesde != null && peticionesFinalizadasHasta == null) {
								peticionesFinalizadasDesde = DateUtils.getDateFirstTime(peticionesFinalizadasDesde);
								peticionesFinalizadasRango = (reqFechaFin.compareTo(peticionesFinalizadasDesde) >= 0);
							} else if (peticionesFinalizadasDesde == null && peticionesFinalizadasHasta != null) {
								peticionesFinalizadasHasta = DateUtils.getDateLastTime(peticionesFinalizadasHasta);
								peticionesFinalizadasRango = (reqFechaFin.compareTo(peticionesFinalizadasHasta) <= 0);
							} else {
								peticionesFinalizadasDesde = DateUtils.getDateFirstTime(peticionesFinalizadasDesde);
								peticionesFinalizadasHasta = DateUtils.getDateLastTime(peticionesFinalizadasHasta);
								peticionesFinalizadasRango = (reqFechaFin.compareTo(peticionesFinalizadasHasta) <= 0 && 
										reqFechaFin.compareTo(peticionesFinalizadasDesde) >= 0);
							}
							peticionesRequerimientos.setPeticionesFinalizadasRango(peticionesFinalizadasRango);
						}
						peticionesFinalizadasRangoExpsMap.put(numExp, peticionesFinalizadasRango);
					}					
					peticionesRequerimientosMap.put(numExp, peticionesRequerimientos);
				}
			}	
		} catch(Exception e){
			logger.error("No se ha podido recuperar las peticiones de requerimientos", e);
		}
		
		return peticionesRequerimientosMap;
	}
	
	public static HashMap<String, Boolean> getDocumentosPortafirmas(EntityManager em, List<String> docsUris) {
		
		HashMap<String, Boolean> documentosPortafirmasMap = new HashMap<String, Boolean>();
		
		try {
			List<String> resultFields = Arrays.asList("d.repository_uri_, gpd.pf_corporativo");
			List<String> tablesNames = Arrays.asList("document_ d", "gestion_portafirmas_doc gpd");
			String inField = "d.repository_uri_";
			List<String> additionalConditions = Arrays.asList("d.administrative_file_id_ = gpd.administrative_file_id", "d.id_ = gpd.doc_id");
			List<Object[]> results = getResultadosPeticiones(em, resultFields, tablesNames, inField, docsUris, additionalConditions);

			if (results != null && !results.isEmpty()) {
				for (Object[] result:results) {
					int i = 0;
					String uri = (String)result[i++];
					Boolean pfCorporativo = ((((BigDecimal) result[i++]).intValue()==1)?Boolean.TRUE:Boolean.FALSE);
					documentosPortafirmasMap.put(uri,  pfCorporativo);
				}
			}	
		} catch(Exception e){
			logger.error("No se ha podido recuperar las peticiones de portafirmas", e);
		}
		
		return documentosPortafirmasMap;
	}

	
	public static HashMap<String, EstadoNotificacionDocumento> getEstadosNotificacionDocumentos(EntityManager em, List<String> docsUris) {
		
		HashMap<String, EstadoNotificacionDocumento> estadosNotifElectronicasDocsMap = new HashMap<String, EstadoNotificacionDocumento>();
		HashMap<String, EstadoNotificacionDocumento> estadosNotifPapelDocsMap = new HashMap<String, EstadoNotificacionDocumento>();
		
		try {
			List<Object[]> results = new ArrayList<Object[]>();
			// Se busca en la tabla de notificaciones en busca de documentos principales
			List<String> resultFields = Arrays.asList("d.repository_uri_", "n.notification_type_", "n.notification_send_date_", "n.notification_receive_date_");
			List<String> tablesNames = Arrays.asList("notification_document_ n, document_ d");
			String inField = "d.repository_uri_";
			List<String> additionalConditions = Arrays.asList("d.administrative_file_id_ = n.administrative_file_id_", "d.id_ = n.document_id_");
			List<Object[]> results1 = getResultadosPeticiones(em, resultFields, tablesNames, inField, docsUris, additionalConditions);
			if (null != results1 && !results1.isEmpty()) {
				results.addAll(results1);
			}
			// Se busca en la tabla de adjuntos de las notificaciones
			resultFields = Arrays.asList("d.repository_uri_", "n.notification_type_", "n.notification_send_date_", "n.notification_receive_date_");
			tablesNames = Arrays.asList("notification_document_ n, document_ d, notification_doc_attachment_ na");
			inField = "d.repository_uri_";
			additionalConditions = Arrays.asList("d.administrative_file_id_ = na.administrative_file_id_", "d.id_ = na.document_id_", "n.id_ = na.notif_doc_id_");
			List<Object[]> results2 = getResultadosPeticiones(em, resultFields, tablesNames, inField, docsUris, additionalConditions);
			if (null != results2 && !results2.isEmpty()) {
				results.addAll(results2);
			}
			
			if (!results.isEmpty()) {
				for (Object[] result:results) {
					int i = 0;
					String uri = (String)result[i++];
					int tipoNotificacion = ((BigDecimal)result[i++]).intValue();
					Date fechaEnvio = (Date)result[i++];
					Date fechaRespuesta = (Date)result[i++];
					
					if (!estadosNotifElectronicasDocsMap.containsKey(uri) || 
							(tipoNotificacion == 3 && ExpedientesConstants.DOC_ESTADO_PLAE_ENVIADO_NOTIFICAR.equals(estadosNotifElectronicasDocsMap.get(uri).getEstado()))) {
						String estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_SIN_NOTIFICACION;
						Date fecha = null;
						if (tipoNotificacion == 2) { // Comunicación
							estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_COMUNICADO;
							fecha = fechaEnvio;
						} else if (tipoNotificacion == 3) { // Notificación
							if (fechaRespuesta != null) {
								estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_NOTIFICADO;
								fecha = fechaRespuesta;
							} else {
								estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_ENVIADO_NOTIFICAR;
							}
						}
						Short estadoNotificacionId = (short)ExpedientesConstants.DOC_ESTADOS_NOTIFICACION_PLAE.indexOf(estadoNotificacion);
						estadosNotifElectronicasDocsMap.put(uri, new EstadoNotificacionDocumento(estadoNotificacionId, estadoNotificacion, fecha, 
								ExpedientesConstants.EXP_TIPO_NOTIFICACION_TELEMATICA, null));
						logger.info("Estado Notif (" + uri + ": " + estadoNotificacion);
					}
				}
			}	
		} catch(Exception e){
			logger.error("No se ha podido recuperar las notificaciones electronicas asociadas a los documentos con uris " + 
					org.apache.commons.lang3.StringUtils.join(docsUris, ","), e);
		}
		
		// Se eliminan todos los documentos que fueron registrados electrónicamente.
		docsUris.removeAll(estadosNotifElectronicasDocsMap.keySet());
		
		try {
			List<String> resultFields = Arrays.asList("d.repository_uri_", "n.notification_type_", "n.first_attempt_date_", "n.first_attempt_result_", 
					"n.second_attempt_date_", "n.second_attempt_result_", "n.publication_date_"); 
			List<String> tablesNames = Arrays.asList("document_ d", "notification_doc_hardcopy_ n");
			String inField = "d.repository_uri_";
			List<String> additionalConditions = Arrays.asList("d.administrative_file_id_ = n.administrative_file_id_", "d.id_ = n.document_id_");
			List<Object[]> results = getResultadosPeticiones(em, resultFields, tablesNames, inField, docsUris, additionalConditions);

			if (results != null && !results.isEmpty()) {
				for (Object[] result:results) {
					int i = 0;
					String uri = (String)result[i++];
					int tipoNotificacion = ((BigDecimal)result[i++]).intValue();
					Date fechaPrimerIntento = (Date)result[i++];
					String resultadoPrimerIntento = (String)result[i++];
					Date fechaSegundoIntento = (Date)result[i++];
					String resultadoSegundoIntento = (String)result[i++];
					Date fechaPublicacion = (Date)result[i++];
					
					if (!estadosNotifPapelDocsMap.containsKey(uri) || 
							ExpedientesConstants.DOC_ESTADO_PLAE_SIN_NOTIFICACION.equals(estadosNotifElectronicasDocsMap.get(uri).getEstado())) {
						String estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_SIN_NOTIFICACION;
						Date fecha = null;
						if (tipoNotificacion == 2) { // Comunicación
							if (fechaPrimerIntento != null) {
								estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_COMUNICADO;
								fecha = fechaPrimerIntento;
							}			
						} else if (tipoNotificacion == 3) { // Notificación
							Date fechaNotificacionEfectiva = null;
							if (fechaPublicacion != null) {
								fechaNotificacionEfectiva = fechaPublicacion;
							}
							if (fechaSegundoIntento != null && "entregada".equalsIgnoreCase(resultadoSegundoIntento)) {
								fechaNotificacionEfectiva = fechaSegundoIntento;
							}		
							if (fechaPrimerIntento != null && "entregada".equalsIgnoreCase(resultadoPrimerIntento)) {
								fechaNotificacionEfectiva = fechaPrimerIntento;
							}	
							if (fechaNotificacionEfectiva != null) {
								estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_NOTIFICADO;
								fecha = fechaNotificacionEfectiva;
							} else {
								if (fechaPrimerIntento == null && fechaSegundoIntento == null && fechaPublicacion == null) {
									estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_SIN_NOTIFICACION;
								} else {
									estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_ENVIADO_NOTIFICAR;
								}
							} 
						}
						Short estadoNotificacionId = (short)ExpedientesConstants.DOC_ESTADOS_NOTIFICACION_PLAE.indexOf(estadoNotificacion);
						estadosNotifPapelDocsMap.put(uri, new EstadoNotificacionDocumento(estadoNotificacionId, estadoNotificacion, fecha,
								ExpedientesConstants.EXP_TIPO_NOTIFICACION_PAPEL));
						logger.info("Estado Notif (" + uri + ": " + estadoNotificacion);
					}
				}
			}	
		} catch(Exception e){
			logger.error("No se ha podido recuperar las notificaciones en papel asociadas a los documentos con uris " + 
								org.apache.commons.lang3.StringUtils.join(docsUris, ","), e);
		}
		
		HashMap<String, EstadoNotificacionDocumento> estadosNotificacionDocsMap = new HashMap<String, EstadoNotificacionDocumento>();
		estadosNotificacionDocsMap.putAll(estadosNotifElectronicasDocsMap);
		estadosNotificacionDocsMap.putAll(estadosNotifPapelDocsMap);
		
		return estadosNotificacionDocsMap;
	}

	
	@SuppressWarnings("unchecked")
	private static List<Object[]> getResultadosPeticiones(EntityManager em, List<String> resultFields, List<String> tablesNames,
			String inField, List<String> inValues, List<String> additionalConditions) {
		
		StringBuilder queryBuilder = new StringBuilder();
		int numRepeticiones = (inValues.size() / MAX_ELEMS_IN) + 1;
		String resultFieldsStr = resultFields.toString();
		resultFieldsStr = resultFieldsStr.substring(1, resultFieldsStr.length() - 1);
		String tablesNamesStr = tablesNames.toString();
		tablesNamesStr = tablesNamesStr.substring(1, tablesNamesStr.length() - 1);
		String additionalConditionsStr = null;
		if (additionalConditions != null) {
			additionalConditionsStr = additionalConditions.toString();
			additionalConditionsStr = additionalConditionsStr.substring(1, additionalConditionsStr.length() - 1).replace(", ", " AND ");
		}
	
		for (int i = 1; i <= numRepeticiones; i++) {
			queryBuilder.append("SELECT ");
			queryBuilder.append(resultFieldsStr);
			queryBuilder.append(" FROM ");
			queryBuilder.append(tablesNamesStr);
			queryBuilder.append(" WHERE ");
			queryBuilder.append(inField);
			queryBuilder.append(" IN (?");
			queryBuilder.append(i);
			queryBuilder.append(")");
			if (!StringUtils.isNullOrEmpty(additionalConditionsStr)) {
				queryBuilder.append(" AND ");
				queryBuilder.append(additionalConditionsStr);
				
			}
			if (numRepeticiones > 1 && i < numRepeticiones) {
				queryBuilder.append(" UNION ALL ");
			}
		}
		
		Query query = em.createNativeQuery(queryBuilder.toString());
		int indiceInicial = 0;
		int indiceFinal = numRepeticiones==1?inValues.size():MAX_ELEMS_IN;
		for (int i = 1; i <= numRepeticiones; i++) {
			List<String> expedientesIdsSublist = inValues.subList(indiceInicial, indiceFinal);
			query.setParameter(i, expedientesIdsSublist);
			if (numRepeticiones > 1 && i <= (numRepeticiones-1)) {
				indiceInicial += MAX_ELEMS_IN;
				if (i < (numRepeticiones-1)) {
					indiceFinal += MAX_ELEMS_IN;
				} else if (i == (numRepeticiones-1)) {
					indiceFinal = inValues.size();
				}
			}
		}
		
		List<Object[]> resultList = (List<Object[]>)query.getResultList();
		return resultList;
	}
	
//	@SuppressWarnings("unchecked")
//	public static List<CatActuaciones> getListTipoActuacion(EntityManager em) {
//		List<CatActuaciones> listCatActuaciones= null;
//		
//		try {
//			Query q = em.createNamedQuery("CatActuaciones.findAll");
//			listCatActuaciones = (List<CatActuaciones>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar el listado de tipos de actuaciones de la base de datos", e);
//		}
//	
//		return listCatActuaciones;
//	}
	
//	public static CatActuaciones getTipoActuacionById(EntityManager em, String idTipoAct) {
//		CatActuaciones tipoActuacion = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatActuaciones.findById");
//			q.setParameter("id", idTipoAct);
//			tipoActuacion = (CatActuaciones) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe el tipo de actuacion con id " + idTipoAct + " en la base de datos");			
//		} catch (Exception e) {
//			logger.error("Error al consultar el tipo de actuacion con id " + idTipoAct + " de la base de datos", e);
//		}
//	
//		return tipoActuacion;
//	}
//	
//	public static CatActuaciones getTipoActuacionByProcesoId(EntityManager em, String idProceso) {
//		CatActuaciones tipoActuacion = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatActuaciones.findByProcesoId");
//			q.setParameter("procesoId", idProceso);
//			tipoActuacion = (CatActuaciones) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe un tipo de actuacion asociada al proceso " + idProceso + " en la base de datos");			
//		} catch (Exception e) {
//			logger.error("Error al consultar el tipo de actuacion asociada al proceso " + idProceso + " de la base de datos", e);
//		}
//	
//		return tipoActuacion;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static List<Actuacion> getActuacionesByProcesoId(EntityManager em, String idProceso){
//		
//		List<Actuacion> listActuaciones = null;
//		
//		try {
//			Query q = em.createNamedQuery("Actuacion.findByProcedimientoId");
//			q.setParameter("procesoId", idProceso);
//			listActuaciones = (List<Actuacion>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar el listado de actuaciones asociada al proceso " + idProceso + " de la base de datos", e);
//		}
//		return listActuaciones;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static List<Actuacion> getActuacionesExpediente(EntityManager em, String numExpediente, Boolean activas) {
//		
//		List<Actuacion> listActuaciones = null;
//		
//		try {
//			Query q = null;
//			
//			if (activas == null) {
//				q = em.createNamedQuery("Actuacion.findByNumExpediente");
//				q.setParameter("numExpediente", numExpediente);
//			} else if (activas) {
//				q = em.createNamedQuery("Actuacion.findActivasByNumExpediente");
//				q.setParameter("numExpediente", numExpediente);
//			} else {
//				q = em.createNamedQuery("Actuacion.findFinalizadasByNumExpediente");
//				q.setParameter("numExpediente", numExpediente);
//			}
//			listActuaciones = (List<Actuacion>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar actuaciones para el expediente indicado. * Activas: " + activas, e);
//		}
//		return listActuaciones;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static Map<String, Actuacion> getActuacionesByIds(EntityManager em, List<String> idsActuaciones){
//		Map<String, Actuacion> actuacionesMap = new HashMap<String, Actuacion>();
//		
//		try {
//			Query q = em.createNamedQuery("Actuacion.findByIds");
//			q.setParameter("actuacionesIds", idsActuaciones);
//			List<Actuacion> listActuaciones = (List<Actuacion>) q.getResultList();
//			if (!listActuaciones.isEmpty()) {
//				for (Actuacion actuacion:listActuaciones) {
//					actuacionesMap.put(actuacion.getId(), actuacion);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Error al consultar el listado de actuaciones con ids " + org.apache.commons.lang3.StringUtils.join(idsActuaciones, ",") + " de la base de datos", e);
//		}
//		return actuacionesMap;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static List<ActuacionesLeidasInfo> getActuacionesPdtesAcuseRecibo(EntityManager em) {
//		List<ActuacionesLeidasInfo> listActuacionesLeidas = null;
//		
//		try {
//			Query q = em.createNamedQuery("ActuacionesLeidasInfo.findByPtesAcuseRecibo");
//			listActuacionesLeidas = (List<ActuacionesLeidasInfo>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar informacion de actuaciones con documento de acuse de recibo pendiente de generar.", e);
//		}
//		
//		return listActuacionesLeidas;
//	}
//	
//	public static ActuacionesLeidasInfo getInfoLecturaActuacion(EntityManager em, String idActuacion) {
//		ActuacionesLeidasInfo infoLectura = null;
//		
//		try {
//			Query q = em.createNamedQuery("ActuacionesLeidasInfo.findByActuacionId");
//			q.setParameter("actuacionId", idActuacion);
//			infoLectura = (ActuacionesLeidasInfo) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe informacion de lectura para la actuacion " + idActuacion);
//		} catch (Exception e) {
//			logger.error("Error al consultar informacion de lectura para la actuacion " + idActuacion, e);
//		}
//		
//		return infoLectura;
//	}	
//	
//	public static Asiento getAsientoById(EntityManager em, Long idAsiento) {
//		Asiento asiento = null;
//		
//		try {
//			Query q = em.createNamedQuery("Asiento.findById");
//			q.setParameter("id", idAsiento);
//			asiento = (Asiento) q.getSingleResult();	
//		} catch (NoResultException e) {
//			logger.info("No existe el asiento con id " + idAsiento + " en la base de datos.");
//		} catch (Exception e) {
//			logger.error("Error al recuperar el asiento con id " + idAsiento + " de la base de datos.", e);
//		}
//		
//		return asiento;		
//	}
//	
//	public static Asiento getAsientoByIdentificador(EntityManager em, String numAsiento, Character tipoAsiento) {
//		Asiento asiento = null;
//		
//		String clausulaSELECT = "SELECT a ";
//		String clausulaFROM = "FROM Asiento a ";
//		String clausulaWHERE = "WHERE a.prueba = 0 AND a.numRegistro = '" + numAsiento + "'";
//		
//		if (tipoAsiento != null) {
//			clausulaWHERE += " AND a.tipoRegistro = " + tipoAsiento.charValue();
//		}
//		
//		logger.info("La consulta es ==> [" + clausulaSELECT + clausulaFROM + clausulaWHERE + "]");
//		
//		try {
//			Query q = em.createQuery(clausulaSELECT + clausulaFROM + clausulaWHERE);
//			asiento = (Asiento) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe el asiento de tipo " + tipoAsiento + " con identificacion " + numAsiento + " en la base de datos.");
//		} catch (Exception e) {
//			logger.error("Error al recuperar el asiento de tipo " + tipoAsiento + " con identificacion " + numAsiento + " de la base de datos.", e);
//		}
//		
//		return asiento;		
//	}
//	
//	public static DocumentoAsiento getDocumentoAsientoByUri(EntityManager em, String uri, Long asientoId) {
//		DocumentoAsiento documentoAsiento = null;
//		
//		String clausulaSELECT = "SELECT d ";
//		String clausulaFROM = "FROM DocumentoAsiento d ";
//		String clausulaWHERE = "WHERE d.alfrescoUri = '" + uri + "'";
//		
//		if (asientoId != null) {
//			clausulaWHERE += " AND d.asiento.id = " + asientoId;
//		}
//		
//		logger.info("[FACADE-DEBUG] La consulta es ==> [" + clausulaSELECT + clausulaFROM + clausulaWHERE + "]");
//		
//		try {
//			Query q = em.createQuery(clausulaSELECT + clausulaFROM + clausulaWHERE);
//			documentoAsiento = (DocumentoAsiento) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe en la base de datos un documento con uri " + uri + " asociado al asiento con id " + asientoId + ".");
//		} catch (Exception e) {
//			logger.error("Error al recuperar de la base de datos el documento con uri " + uri + " asociado al asiento con id " + asientoId + ".", e);
//		}
//		
//		return documentoAsiento;		
//	}
//	
//	public static DocumentoAsiento existeDocumentoAsientoByUri(EntityManager em, String uri, Long asientoId) throws NoResultException {
//		DocumentoAsiento documentoAsiento = null;
//		
//		String clausulaSELECT = "SELECT d ";
//		String clausulaFROM = "FROM DocumentoAsiento d ";
//		String clausulaWHERE = "WHERE d.alfrescoUri = '" + uri + "'";
//		
//		if (asientoId != null) {
//			clausulaWHERE += " AND d.asiento.id = " + asientoId;
//		}
//		
//		logger.info("La consulta es ==> [" + clausulaSELECT + clausulaFROM + clausulaWHERE + "]");
//		
//		try {
//			Query q = em.createQuery(clausulaSELECT + clausulaFROM + clausulaWHERE);
//			documentoAsiento = (DocumentoAsiento) q.getSingleResult();
//		} catch (NoResultException e) {
//			throw e;
//		} catch (Exception e) {
//			logger.error("Error al recuperar de la base de datos el documento con uri " + uri + " asociado al asiento con id " + asientoId + ".", e);
//		}
//		
//		return documentoAsiento;		
//	}
//	
//	public static DocumentoAsiento getDocumentoAsiento(EntityManager em, String uri, String numAsiento) {
//		DocumentoAsiento documentoAsiento = null;
//		
//		String clausulaSELECT = "SELECT d ";
//		String clausulaFROM = "FROM DocumentoAsiento d ";
//		String clausulaWHERE = "WHERE d.alfrescoUri = '" + uri + "'";
//		
//		if (numAsiento != null) {
//			clausulaWHERE += " AND d.asiento.numRegistro = " + numAsiento;
//		}
//		
//		logger.info("La consulta es ==> [" + clausulaSELECT + clausulaFROM + clausulaWHERE + "]");
//		
//		try {
//			Query q = em.createQuery(clausulaSELECT + clausulaFROM + clausulaWHERE);
//			documentoAsiento = (DocumentoAsiento) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe en la base de datos un documento con uri " + uri + " asociado al asiento con identificacion " + numAsiento + ".");
//		} catch (Exception e) {
//			logger.error("Error al recuperar de la base de datos el documento con uri " + uri + " asociado al asiento con identificacion " + numAsiento + ".", e);
//		}
//		
//		return documentoAsiento;		
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static DocumentoAsiento getDocumentosAsientoByCsv(EntityManager em, String csv) {
//		DocumentoAsiento documentoAsiento = null;
//		
//		String clausulaSELECT = "SELECT d ";
//		String clausulaFROM = "FROM DocumentoAsiento d ";
//		String clausulaWHERE = "WHERE d.csv = '" + csv + "' ORDER BY d.asiento.id DESC";
//		
//		logger.info("[FACADE-DEBUG] La consulta es ==> [" + clausulaSELECT + clausulaFROM + clausulaWHERE + "]");
//		
//		try {
//			Query q = em.createQuery(clausulaSELECT + clausulaFROM + clausulaWHERE);
//			List<DocumentoAsiento> documentosAsientos = (List<DocumentoAsiento>) q.getResultList();
//			if (documentosAsientos != null && !documentosAsientos.isEmpty()) {
//				documentoAsiento = documentosAsientos.get(0);
//			}
//		} catch (Exception e) {
//			logger.error("Error al recuperar de la base de datos el documento de asiento con csv " + csv + ".", e);
//		}
//		
//		return documentoAsiento;		
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static HashMap<String, DocumentoAsiento> getDocumentosAsientoByUris(EntityManager em, Long asientoId, List<String> uris) {
//		HashMap<String, DocumentoAsiento> documentosMap = null;
//		
//		try {
//			Query q = null;
//			if (asientoId != null) {
//				q = em.createNamedQuery("DocumentoAsiento.findDocumentosAsientoByUris");
//				q.setParameter("uris", uris);
//				q.setParameter("asientoId", asientoId);
//			} else {
//				q = em.createNamedQuery("DocumentoAsiento.findDocumentosByUris");
//				q.setParameter("uris", uris);
//			}
//			
//			List<DocumentoAsiento> documentos = (List<DocumentoAsiento>) q.getResultList();
//			if (documentos != null && !documentos.isEmpty()) {
//				documentosMap = new HashMap<String, DocumentoAsiento>();
//				for (DocumentoAsiento doc:documentos) {
//					documentosMap.put(doc.getAlfrescoUri(), doc);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Error al recuperar de la base de datos los documentos con uris " + org.apache.commons.lang3.StringUtils.join(uris, ",") + 
//					" asociados al asiento con id " + asientoId + ".", e);
//		}
//	
//		return documentosMap;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static List<DocumentoAsiento> getDocumentosAsientoByNombre(EntityManager em, String nombre, Long asientoId) {
//		List<DocumentoAsiento> documentosAsiento = null;
//		
//		String clausulaSELECT = "SELECT d ";
//		String clausulaFROM = "FROM DocumentoAsiento d ";
//		String clausulaWHERE = "WHERE d.nombre LIKE '%" + nombre + "%'";
//		clausulaWHERE += " AND d.asiento.id = " + asientoId;
//		
//		logger.info("[FACADE-DEBUG] La consulta es ==> [" + clausulaSELECT + clausulaFROM + clausulaWHERE + "]");
//		
//		try {
//			Query q = em.createQuery(clausulaSELECT + clausulaFROM + clausulaWHERE);
//			documentosAsiento = (List<DocumentoAsiento>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar de la base de datos los documentos cuyo nombre contenga la cadena '" + nombre + 
//					"' asociados al asiento con id " + asientoId + ".", e);
//		}
//		
//		return documentosAsiento;		
//	}
	
	@SuppressWarnings("unchecked")
	public static List<Procedimiento> getProcedimientos(EntityManager em) {
		List<Procedimiento> procedimientos = null;
		
		try {
			Query q = em.createNamedQuery(Procedimiento.findAll);
			procedimientos = (List<Procedimiento>) q.getResultList();
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el listado de procedimientos de la base de datos", e);
		}
	
		return procedimientos;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Procedimiento> getProcedimientosActivosRegistro(EntityManager em) {
		List<Procedimiento> procedimientos = null;
		
		try {
			Query q = em.createNamedQuery(Procedimiento.findAllActivoRegistro);
			procedimientos = (List<Procedimiento>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de procedimientos activos para su inicio a instancia de parte", e);
		}
	
		return procedimientos;
	}		
	
	@SuppressWarnings("unchecked")
	// inicio: OFICIO, INSTANCIA_DE_PARTE
	public static Map<String, Procedimiento> getProcedimientosInicio(EntityManager em, List<String> departamentos, String familia, String inicio) {
		TreeMap<String, Procedimiento> procsMap = new TreeMap<String, Procedimiento>();
				
		List<Integer> tiposInicio = null;
		if ("OFICIO".equals(inicio)) {
			tiposInicio = Arrays.asList(1, 3);
		} else if ("INSTANCIA_DE_PARTE".equals(inicio)) {
			tiposInicio = Arrays.asList(1, 2);
		}
		
		try {
			Query q = null;
			if (StringUtils.isNullOrEmpty(familia)) {
				q = em.createNamedQuery("CatProcedimientoRol.findByDepartamentosIds");
			} else {
				q = em.createNamedQuery("CatProcedimientoRol.findByDepartamentosIdsAndFamiliaId");
				q.setParameter("familiaId", familia);
				q.setParameter("procFamiliaId", familia + "%");
			}
			q.setParameter("departamentosIds", departamentos);
			List<CatProcedimientoRol> listProcedimientosRol = (List<CatProcedimientoRol>) q.getResultList();
			
			if (!listProcedimientosRol.isEmpty()) {
				for (CatProcedimientoRol procRol:listProcedimientosRol) {
					if (procRol.getProcedimiento() == null) {
						List<Procedimiento> procs = null;
						if (StringUtils.isNullOrEmpty(procRol.getFamiliaId())) {
							procs = getProcedimientosByFamiliaAndTiposInicio(em, null, tiposInicio);
						} else {
							if (StringUtils.isNullOrEmpty(familia)) {
								procs = getProcedimientosByFamiliaAndTiposInicio(em, procRol.getFamiliaId(), tiposInicio);
							} else if (procRol.getFamiliaId().equals(familia)) {
								procs = getProcedimientosByFamiliaAndTiposInicio(em, familia, tiposInicio);
							}
						}
						if (procs != null) {
							for (Procedimiento proc:procs) {
								if (procsMap.containsKey(proc.getId())) {
									proc = procsMap.get(proc.getId());
								}
								if (!proc.getDptosInicio().contains(procRol.getDepartamentoId())) {
									proc.getDptosInicio().add(procRol.getDepartamentoId());
								}
								procsMap.put(proc.getId(), proc);
							}
						}
					}
					if (procRol.getProcedimiento() != null) {
						if (tiposInicio.contains(procRol.getProcedimiento().getActivo())) {
							Procedimiento proc = procRol.getProcedimiento();
							if (procsMap.containsKey(proc.getId())) {
								proc = procsMap.get(proc.getId());
							}
							if (!proc.getDptosInicio().contains(procRol.getDepartamentoId())) {
								proc.getDptosInicio().add(procRol.getDepartamentoId());
							}
							procsMap.put(proc.getId(), proc);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el listado de procedimientos de inicio de la base de datos. * Ids. Departamentos: " + 
					org.apache.commons.lang3.StringUtils.join(departamentos, ",") + " | * Familia: " + familia + " | Tipo Inicio: " + tiposInicio, e);
		}
		
		return procsMap;		
	}	
	
	@SuppressWarnings("unchecked")	
	public static List<Procedimiento> getProcedimientosByFamiliaAndTiposInicio(EntityManager em, String familia, List<Integer> tiposInicio) {
		List<Procedimiento> result = null;
		try {
			Query q = null;
			if (!StringUtils.isNullOrEmpty(familia)) {
				q = em.createNamedQuery("Procedimiento.findByFamiliaAndTipoInicio");
				q.setParameter("familiaId", familia);
			} else {
				q = em.createNamedQuery("Procedimiento.findByTipoInicio");
			}
			q.setParameter("tiposInicioIds", tiposInicio);
			result = (List<Procedimiento>) q.getResultList();
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el listado de procedimientos de inicio de la base de datos. * Tipos Inicio: " + 
					org.apache.commons.lang3.StringUtils.join(tiposInicio, ",") + " | * Familia: " + familia, e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Procedimiento> getProcedimientosMap(EntityManager em) {
		HashMap<String, Procedimiento> procedimientosMap = new HashMap<String, Procedimiento>();
		
		try {
			Query q = em.createNamedQuery("Procedimiento.findAll");
			List<Procedimiento> procedimientos = (List<Procedimiento>) q.getResultList();
			if (procedimientos != null && !procedimientos.isEmpty()) {
				for (Procedimiento procedimiento:procedimientos) {
					procedimientosMap.put(procedimiento.getId(), procedimiento);
				}
			}
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de procedimientos", e);
		}
	
		return procedimientosMap;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Familia> getFamilias(EntityManager em) {
		List<Familia> familias = null;
		
		try {
			Query q = em.createNamedQuery("Familia.findAll");
			familias = (List<Familia>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de familias", e);
		}
	
		return familias;
	}	
	
	public static Procedimiento getProcedimientoById(EntityManager em, String procId) {
		Procedimiento procedimiento = null;
		
		try {
			Query q = em.createNamedQuery(Procedimiento.findById);
			q.setParameter("id", procId);
			procedimiento = (Procedimiento) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe el procedimiento con id " + procId + " en la base de datos");
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el procedimiento con id " + procId, e);
		}
	
		return procedimiento;
	}	
	
	@SuppressWarnings("unchecked")
	public static List<Procedimiento> getProcedimientosByFamilia(EntityManager em, String familiaId) {
		List<Procedimiento> procedimientos = null;
		
		try {
			Query q = em.createNamedQuery(Procedimiento.findByFamilia);
			q.setParameter("id", familiaId);
			procedimientos = (List<Procedimiento>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de procedimientos pertenecientes a la familia con id " + familiaId, e);
		}
	
		return procedimientos;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Procedimiento> getProcedimientosActivosRegistroByFamilia(EntityManager em, String familiaId) {
		List<Procedimiento> procedimientos = null;
		
		try {
			Query q = em.createNamedQuery(Procedimiento.findActivoRegistroByFamilia);
			q.setParameter("id", familiaId);
			procedimientos = (List<Procedimiento>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de procedimientos activos para su inicio a instancia de parte pertenecientes a la familia con id " + familiaId, e);
		}
	
		return procedimientos;
	}	
	
//	public static Tercero getTerceroByIdentificacion(EntityManager em, String numDocumento) {
//		Tercero tercero = null;
//		
//		try {
//			Query q = em.createNamedQuery("Tercero.findByIdentificacion");
//			q.setParameter("identificacion", numDocumento);
//			tercero = (Tercero) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe el tercero con el documento " + numDocumento + " en la base de datos", e);
//		} catch (NonUniqueResultException e) {
//			logger.warn("Existe mas de un tercero en la base de datos con el documento " + numDocumento, e);
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el tercero con el documento " + numDocumento + " de la base de datos", e);
//		}
//	
//		return tercero;
//	}
	
//	public static Tercero getTerceroByIdentificacion(EntityManager em, String numDocumento, String tipoIdentificacion) throws NoResultException, NonUniqueResultException {
//		Tercero tercero = null;
//		
//		try {
//			Query q = null;
//			if (tipoIdentificacion != null) {
//				q = em.createNamedQuery("Tercero.findByIdentificacionAndTipo");
//				q.setParameter("identificacion", numDocumento);
//				q.setParameter("tipoIdentificacion", tipoIdentificacion);
//			} else {
//				q = em.createNamedQuery("Tercero.findByIdentificacion");
//				q.setParameter("identificacion", numDocumento);
//			}
//			tercero = (Tercero) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe el tercero con el documento " + numDocumento + " y/o el tipo de identificacion " + tipoIdentificacion + " en la base de datos", e);
//			throw e;			
//		} catch (NonUniqueResultException e) {
//			logger.warn("Existe mas de un tercero en la base de datos con el documento " + numDocumento + " y/o el tipo de identificacion " + tipoIdentificacion, e);
//			throw e;
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el tercero con el documento " + numDocumento + " y/o el tipo de identificacion " + tipoIdentificacion + " de la base de datos", e);
//		}
//	
//		return tercero;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<TerceroAsiento> getTercerosAsiento(EntityManager em, Long idAsiento) {
//		List<TerceroAsiento> tercerosAsiento = null;
//		
//		try {
//			Query q = em.createNamedQuery("TerceroAsiento.findByAsientoId");
//			q.setParameter("asientoId", idAsiento);
//			tercerosAsiento = (List<TerceroAsiento>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el listado de terceros asociados al asiento con id " + idAsiento, e);
//		}
//	
//		return tercerosAsiento;
//	}	
	
//	@SuppressWarnings("unchecked")
//	public static List<Tercero> getTercerosEAdmin(EntityManager em) {
//		List<Tercero> terceros = null;
//		
//		try {
//			Query q = em.createNamedQuery("Tercero.findByEAdmin");
//			terceros = (List<Tercero>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el listado de terceros con algun permiso de acceso a servicios electronicos", e);
//		}
//	
//		return terceros;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<DocumentoAsiento> getDocumentosAsiento(EntityManager em, long idAsiento) {
//		List<DocumentoAsiento> documentosAsiento = null;
//		try {
//			Query q = em.createNamedQuery("DocumentoAsiento.findByAsientoId");
//			q.setParameter("asientoId", idAsiento);
//			documentosAsiento = (List<DocumentoAsiento>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el listado de documentos asociados al asiento con id " + idAsiento, e);
//		}
//	
//		return documentosAsiento;
//	}	
	
//	public static CatEstadoRegistro getEstadoRegistroById(EntityManager em, String estadoId) {
//		CatEstadoRegistro estado = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatEstadoRegistro.findById");
//			q.setParameter("id", estadoId);
//			estado = (CatEstadoRegistro) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.warn("No existe en la base de datos el estado de registro con id " + estadoId);
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el estado de registro con id " + estadoId, e);
//		}
//	
//		return estado;
//	}	
	
//	public static CatOrigenAsiento getOrigenAsientoById(EntityManager em, String origenId) {
//		CatOrigenAsiento origen = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatOrigenAsiento.findById");
//			q.setParameter("id", origenId);
//			origen = (CatOrigenAsiento) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.warn("No existe en la base de datos el origen de asiento con id " + origenId);
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el origen de asiento con id " + origenId, e);
//		}
//	
//		return origen;
//	}	
	
//	@SuppressWarnings("unchecked")
//	public static List<CatOrigenAsiento> getOrigenAsiento(EntityManager em) {
//		List<CatOrigenAsiento> origenes = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatOrigenAsiento.findAll");
//			origenes = (List<CatOrigenAsiento>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el listado de origenes de asientos", e);
//		}
//	
//		return origenes;
//	}
	
//	public static CatTipoTransporteEntrada getTipoTransporteEntradaById(EntityManager em, String tipoId) {
//		CatTipoTransporteEntrada tipoTransporteEntrada = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatTipoTransporteEntrada.findById");
//			q.setParameter("id", tipoId);
//			tipoTransporteEntrada = (CatTipoTransporteEntrada) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.warn("No existe en la base de datos el tipo de transporte de entrada con id " + tipoId);
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el tipo de transporte de entrada con id " + tipoId, e);
//		}
//	
//		return tipoTransporteEntrada;
//	}
	
	@SuppressWarnings("unchecked")
	public static List<Rol> getRoles(EntityManager em) {
		List<Rol> roles = null;
		
		try {
			Query q = em.createNamedQuery(Rol.findAll);
			roles = (List<Rol>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de roles", e);
		}
	
		return roles;
	}	
	
	@SuppressWarnings("unchecked")
	public static List<Rol> getRolesDecretables(EntityManager em) {
		List<Rol> roles = null;
		
		try {
			Query q = em.createNamedQuery(Rol.findDecretables);
			roles = (List<Rol>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de roles decretables", e);
		}
	
		return roles;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Rol> getDepartamentos(EntityManager em) {
		List<Rol> roles = null;
		
		try {
			Query q = em.createNamedQuery(Rol.findHabilitados);
			roles = (List<Rol>) q.getResultList();
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de roles habilitados", e);
		}
	
		return roles;
	}
	
	public static Map<String, Rol> getDepartamentosMap(EntityManager em) {
		Map<String, Rol> departamentosMap = null;
		
		List<Rol> departamentos = getDepartamentos(em);
		if (departamentos != null) {
			departamentosMap = new LinkedHashMap<String, Rol>();
			for (Rol rol:departamentos) {
				departamentosMap.put(rol.getId(), rol);
			}
		}
		return departamentosMap;
	}
	
	public static Rol getRolById(EntityManager em, String id) {
		Rol rol = null;
		
		if (id != null) {
			try {
				Query q = em.createNamedQuery(Rol.findById);
				q.setParameter("id", id);
				rol = (Rol) q.getSingleResult();
			} catch (NoResultException e) {
				logger.error("No existe el rol con nombre '" + id + "' en la base de datos");
			} catch (Exception e) {
				logger.error("Error al consultar el rol con nombre '" + id + "' de la base de datos", e);
			}
		}
	
		return rol;
	}	
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Rol> getRolesMap(EntityManager em) {
		HashMap<String, Rol> rolesMap = new HashMap<String, Rol>();
		
		try {
			Query q = em.createNamedQuery(Rol.findAll);
			List<Rol> roles = (List<Rol>) q.getResultList();
			if (roles != null && !roles.isEmpty()) {
				for (Rol rol:roles) {
					rolesMap.put(rol.getId(), rol);
				}
			}
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de roles", e);
		}
	
		return rolesMap;
	}
	
//	public static CatEntidadRegistral getEntidadRegistralByDescripcion(EntityManager em, String entidadRegDesc) {
//		CatEntidadRegistral entidadRegistral = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatEntidadRegistral.findByDescripcion");
//			q.setParameter("descripcion", entidadRegDesc);
//			entidadRegistral = (CatEntidadRegistral) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.warn("No existe en la base de datos la entidad registral con descripcion " + entidadRegDesc);
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos la entidad registral con descripcion " + entidadRegDesc, e);
//		}
//	
//		return entidadRegistral;
//	}
	
//	public static CatSubtipoRegistro getSubtipoRegistro(EntityManager em, String codigo, Short tipoRegistro) {
//		CatSubtipoRegistro subtipoRegistro = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatSubtipoRegistro.findByCodigoAndTipo");
//			q.setParameter("codigo", codigo);
//			q.setParameter("tipoRegistro", tipoRegistro);
//			subtipoRegistro = (CatSubtipoRegistro) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.warn("No existe en la base de datos el subtipo de registro de tipo " + tipoRegistro + " con codigo " + tipoRegistro);
//		} catch (Exception e) {
//			logger.error("Error al consultar en la base de datos el subtipo de registro de tipo " + tipoRegistro + " con codigo " + tipoRegistro, e);
//		}
//	
//		return subtipoRegistro;		
//	}	
	
//	public static IdAsiento getIdAsiento(EntityManager em, Short tipoRegistro, String anualidad, String registro) {
//		IdAsiento idAsiento = null;
//		
//		try {
//			Query q = em.createNamedQuery("IdAsiento.findIdAsientoActual");
//			q.setParameter("tipoRegistro", tipoRegistro);
//			q.setParameter("anualidad", anualidad);
//			q.setParameter("registro", registro);
//			q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
//			idAsiento = (IdAsiento) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.warn("No existe id para la generacion del numero de asiento. * Tipo registro: " + tipoRegistro + 
//				" | * Anualidad: " + anualidad + " | * Cod. registro: " + registro);
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el id de asiento de la base de datos. * Tipo registro: " + tipoRegistro + 
//				" | * Anualidad: " + anualidad + " | * Cod. registro: " + registro, e);
//		}
//	
//		return idAsiento;
//	}	
	
	/**
	 * Comprueba si existe en BD una trasicion definida entre dos actividades de un proceso.
	 * @param wfId Identificador del proceso
	 * @param actividadOrigenId Identificador de la actividad de origen
	 * @param actividadDestinoId Identificador de la actividad de destino
	 * @param numTransicion Numero de transicion, generalmente, 1.
	 * @return Un objeto de tipo Transicion en el caso de que exista y null en caso contrario.
	 */
	@SuppressWarnings("unchecked")
	public static Transicion getTransicion(EntityManager em, String wfId, String actividadOrigenId, String actividadDestinoId, 
			short numTransicion) {
		Transicion transicion = null;
		List<Transicion> transiciones = null;
		
		try {
			Query q = em.createQuery("SELECT t FROM Transicion t " + 
									 "WHERE (t.pk.wfId like '" + wfId + "%') AND " +
									 "(t.pk.actividadOrigenId = '" + actividadOrigenId + "') AND " +
									 "(t.pk.actividadDestinoId = '" + actividadDestinoId + "') AND " +
									 "(t.pk.numTransicion = 1)");
			transiciones = (List<Transicion>) q.getResultList();
		} catch (Exception e) {
			logger.error("No se ha podido recuperar transiciones de la base de datos", e);
		}

		if(transiciones != null) {
			if (transiciones.size() == 1) {
				transicion = transiciones.get(0);
			} if (transiciones.size() > 1) {
				for (Transicion transicionActual:transiciones) {
					if (transicionActual.getPk().getWfId().equals(wfId)) {
						transicion = transicionActual;
						break;
					}
				}
			}
		}	
		return transicion;
	}	
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getTiposDocumentos(EntityManager em) {
		HashMap<String, String> tiposDocumentosMap = new LinkedHashMap<String, String>();
		try {
			Query q = em.createNamedQuery("CatTipoDocumento.findAll");
			List<CatTipoDocumento> tiposDocumentos = (List<CatTipoDocumento>) q.getResultList();
			if (tiposDocumentos != null) {
				for (CatTipoDocumento tipoDoc:tiposDocumentos) {
					tiposDocumentosMap.put(tipoDoc.getId(), tipoDoc.getDescripcion());
				}
			}
		} catch (Exception e) {
			logger.error("Error al consultar en la base de datos el listado de tipos de documentos", e);
		}

		return tiposDocumentosMap;
	}	
	
	/**
	 * Retorna una lista de peticiones que puedan bloquear en una actividad y un workflow concreto
	 */
//	@SuppressWarnings("unchecked")
//	public static List<CatAccionesExpedientes> getBloqueosPortafirmas(EntityManager em, String wfId, String actividadId) {
//		String[] wfIdPartes = wfId.split("\\:");
//		String wfPrefix = wfIdPartes[0] + ":";
//		
//		List<CatAccionesExpedientes> result = null;
//		try {
//			Query q = em.createNamedQuery("CatAccionesExpedientes.findBloqueosPortafirmas");
//			q.setParameter("idActividad", actividadId);
//			q.setParameter("idWf", wfId);
//			q.setParameter("idWfPrefix", wfPrefix);
//			result = (List<CatAccionesExpedientes>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("No se ha podido comprobar si existe algun bloqueo de Portafirmas para la actividad actual", e);
//		}
//		
//		return result;
//	}	
	
	/**
	 * Retorna una lista de actuaciones que puedan bloquear en una actividad y un workflow concreto
	 */
//	@SuppressWarnings("unchecked")
//	public static List<CatAccionesExpedientes> getBloqueosActuacionesPendientes(EntityManager em, String wfId, String actividadId) {
//		String[] wfIdPartes = wfId.split("\\:");
//		String wfPrefix = wfIdPartes[0] + ":";
//		
//		List<CatAccionesExpedientes> result = null;
//		try {
//			Query q = em.createNamedQuery("CatAccionesExpedientes.findBloqueosActuacionesPdtes");
//			q.setParameter("idActividad", actividadId);
//			q.setParameter("idWf", wfId);
//			q.setParameter("idWfPrefix", wfPrefix);
//			result = (List<CatAccionesExpedientes>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar el listado de bloqueos de Actuaciones pendientes para la actividad actual", e);
//		}
//		
//		return result;
//	}
	
	/**
	 * Retorna la lista de condiciones que determinan que un expediente del proceso wfId va a finalizar.
	 */
//	@SuppressWarnings("unchecked")
//	public static List<CatAccionesExpedientes> getCondicionesFinalizacionExp(EntityManager em, String wfId, String actividadId) {
//		String[] wfIdPartes = wfId.split("\\:");
//		String wfPrefix = wfIdPartes[0] + ":";
//		
//		List<CatAccionesExpedientes> result = null;
//		try {
//			Query q = em.createNamedQuery("CatAccionesExpedientes.findCondicionesFinalizacionExp");
//			q.setParameter("idActividad", actividadId);
//			q.setParameter("idWf", wfId);
//			q.setParameter("idWfPrefix", wfPrefix);
//			result = (List<CatAccionesExpedientes>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar el listado de condiciones de finalizacion de expediente para la actividad actual", e);
//		}
//		
//		return result;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<CatAccionesExpedientes> getActuacionesAutomaticasPorActividad(EntityManager em, String wfId, String actividadId, String autoDisparador) {
//		
//		String[] wfIdPartes = wfId.split("\\:");
//		String wfPrefix = wfIdPartes[0] + ":";
//		
//		List<CatAccionesExpedientes> result = null;
//		try {
//			Query q = em.createNamedQuery("CatAccionesExpedientes.findInicioAutomatico");
//			q.setParameter("idActividad", actividadId);
//			q.setParameter("idWf", wfId);
//			q.setParameter("idWfPrefix", wfPrefix);
//			q.setParameter("disparador", autoDisparador);
//			
//			result = (List<CatAccionesExpedientes>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar el listado de tipos de actuaciones automaticas a crear", e);
//		}
//		
//		return result;
//	}
	
	/**
	 * Retorna la lista de condiciones que determinan que un expediente del proceso wfId debe ser despachado automáticamente.
	 */
//	@SuppressWarnings("unchecked")
//	public static List<CatAccionesExpedientes> getCondicionesDespacharExpAuto(EntityManager em, String wfId, String actividadId,
//			String tipoActuacionId) {
//		String[] wfIdPartes = wfId.split("\\:");
//		String wfPrefix = wfIdPartes[0] + ":";
//		
//		List<CatAccionesExpedientes> result = null;
//		try {
//			Query q = em.createNamedQuery("CatAccionesExpedientes.findCondicionesDespacharExpAuto");
//			q.setParameter("idActividad", actividadId);
//			q.setParameter("idWf", wfId);
//			q.setParameter("idWfPrefix", wfPrefix);
//			q.setParameter("idTipoActuacion", tipoActuacionId);
//			result = (List<CatAccionesExpedientes>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar el listado de condiciones de despachado automatico para la actividad actual", e);
//		}
//		
//		return result;
//	}
	
	@SuppressWarnings("unchecked")
	public static List<CatProcedimientoCircuito> getCatProcedimientoCircuitos(EntityManager em, String procedimientoId, String tramiteId, String departamentoId) {
		List<CatProcedimientoCircuito> procedimientosCircuito = new ArrayList<CatProcedimientoCircuito>();
		
		try {
			Query q = em.createNamedQuery("CatProcedimientoCircuito.findByProcedimientoActividadDepartamentoIds");
			q.setParameter("procedimientoId", procedimientoId);
			q.setParameter("actividadId", tramiteId);
			q.setParameter("departamentoId", departamentoId);
			procedimientosCircuito = (List<CatProcedimientoCircuito> ) q.getResultList();	
		} catch (Exception e) {
			logger.error("Error al recuperar el listado de circuitos de Portafirmas habilitados. * Procedimiento: " + procedimientoId + 
				" | * Tramite: " + tramiteId + " | * Departamento: " + departamentoId, e);
		}			
	
		return procedimientosCircuito;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Track> getHistoricoEntidad(EntityManager em, String idEntidad) {
		List<Track> historicoEntidad = new ArrayList<Track>();
		
		try {
			Query q = em.createNamedQuery("Track.findByIdEntidad");
			q.setParameter("idEntidad", idEntidad);
			historicoEntidad = (List<Track>) q.getResultList();			
		} catch (Exception e) {
			logger.error("Error al consultar el historico de tramitacion asociado a la entidad " + idEntidad, e);
		}
	
		return historicoEntidad;		
	}
	
	public static Track getPasoHistoricoById(EntityManager em, TrackPk id) {
		Track pasoHistorico = null;
		
		try {
			Query q = em.createNamedQuery("Track.findById");
			q.setParameter("idEntidad", id.getIdEntidad());
			q.setParameter("actividadId", id.getActividad());
			q.setParameter("wfId", id.getWfId());
			q.setParameter("wfVersion", id.getWfVersion());
			q.setParameter("iteracion", id.getIteracion());
			pasoHistorico = (Track) q.getSingleResult();		
		} catch (NoResultException e) {
			logger.info("No existe ningun paso de historico de tramitacion para la iteración actual. * Entidad: " + id.getIdEntidad() + 
				" | * Tramite: " + id.getActividad() + " | * Id. Flujo: " + id.getWfId() + " | * Iteracion: " + id.getIteracion());	
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el paso de historico de tramitacion para la iteración actual. * Entidad: " + id.getIdEntidad() + 
				" | * Tramite: " + id.getActividad() + " | * Id. Flujo: " + id.getWfId() + " | * Iteracion: " + id.getIteracion(), e);
		}
	
		return pasoHistorico;	
	}
	
	@SuppressWarnings("unchecked")
	public static Track getPasoHistoricoUltimaIteracion(EntityManager em, TrackPk id) {
		Track pasoHistorico = null;
		
		try {
			Query q = em.createNamedQuery("Track.findIterations");
			q.setParameter("idEntidad", id.getIdEntidad());
			q.setParameter("actividadId", id.getActividad());
			q.setParameter("wfId", id.getWfId());
			q.setParameter("wfVersion", id.getWfVersion());
			List<Track> resultado = (List<Track>) q.getResultList();
			if (resultado != null && !resultado.isEmpty()) {
				pasoHistorico = resultado.get(0);
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el paso de historico de tramitacion para la ultima iteracion. * Entidad: " + id.getIdEntidad() + 
				" | * Tramite: " + id.getActividad() + " | * Id. Flujo: " + id.getWfId(), e);
		}
	
		return pasoHistorico;	
	}
	
	@SuppressWarnings("unchecked")
	public static List<IndiceCapituloDocumento> getIndiceCapituloDocumentos(EntityManager em, String adminFileId, String documentId, Integer capituloId) {
		try {
			Query q = null;
			if (adminFileId != null && !adminFileId.trim().equals("") && documentId != null && !documentId.trim().equals("")) {
				q = em.createNamedQuery("IndiceCapituloDocumento.findByPk");
				q.setParameter("idExpediente", adminFileId);
				q.setParameter("idDocumento", documentId);
			} else if (capituloId != null) {
				q = em.createNamedQuery("IndiceCapituloDocumento.findByIdCapitulo");
				q.setParameter("idCapitulo", capituloId);			
			}
			if (q != null) {
				return (List<IndiceCapituloDocumento>)q.getResultList();
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el listado de documentos asociados a un capitulo de un indice. * Num. Expediente: " + adminFileId + 
				" | * Nombre Documento: " + documentId + " | * Id. Capitulo: " + capituloId, e);	
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> getDocumentosRefActuacion(EntityManager em, String adminFileId) {
		try {
			Query q = em.createNamedQuery("Documento.findDocumentosRefActuacion");
			q.setParameter("numExp", adminFileId);
			return (List<Documento>)q.getResultList();
		} catch (Exception e) {
			logger.error("Se ha producido un error al intentar recuperar los documentos del expediente " + adminFileId + " para incluir como referencia a la actuacion", e);
		}
		
		return null;
	}
	
//	public static RequerimientoEntity getRequerimientoById(EntityManager em, String idRequerimiento) {
//		RequerimientoEntity requerimiento = null;
//		try {
//			Query q = em.createNamedQuery(RequerimientoEntity.findById);
//			q.setParameter("id", idRequerimiento);
//			requerimiento = (RequerimientoEntity)q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe ningun requerimiento con id " + idRequerimiento);	
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el requerimiento con id " + idRequerimiento, e);
//		}
//		
//		return requerimiento;
//	}
	
//	public static RequerimientoEntity getRequerimientoByNumNotificacion(EntityManager em, String numNotificacion) {
//		RequerimientoEntity requerimiento = null;
//		try {
//			Query q = em.createNamedQuery(RequerimientoEntity.findByNumNotificacion);
//			q.setParameter("numNotificacion", numNotificacion);
//			requerimiento = (RequerimientoEntity)q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe ningun requerimiento con numero de notificacion " + numNotificacion);	
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el requerimiento con numero de notificacion " + numNotificacion, e);
//		}
//		
//		return requerimiento;
//	}
	
//	public static RequerimientoEntity getRequerimientoByRegistroSalida(EntityManager em, String registroSalida) {
//		RequerimientoEntity requerimiento = null;
//		try {
//			Query q = em.createNamedQuery(RequerimientoEntity.findByRegistroSalida);
//			q.setParameter("recordNumber", registroSalida);
//			requerimiento = (RequerimientoEntity)q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe ningun requerimiento con registro de salida " + registroSalida);	
//		} catch (Exception e) {
//			logger.error("No se ha podido recuperar el requerimiento con registro de salida " + registroSalida, e);
//		}
//		
//		return requerimiento;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<RequerimientoEntity> getRequerimientosPendientesEnExpediente(EntityManager em, String adminFileId) {
//		try {
//			Query q = em.createNamedQuery(RequerimientoEntity.findPendientesEnExpediente);
//			q.setParameter("idExp", adminFileId);
//			return (List<RequerimientoEntity>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar requerimientos pendientes para el expediente " + adminFileId, e);
//		}
//		
//		return null;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<RequerimientoEntity> getRequerimientosDisponiblesSedeByExpediente(EntityManager em, String adminFileId) {
//		try {
//			Query q = em.createNamedQuery(RequerimientoEntity.findHabilitadosSedeByExpediente);
//			q.setParameter("idExp", adminFileId);
//			return (List<RequerimientoEntity>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar requerimientos disponibles en Sede para el expediente " + adminFileId, e);
//		}
//		
//		return null;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<RequerimientoEntity> getRequerimientosRespondidosByExpediente(EntityManager em, String adminFileId) {
//		try {
//			Query q = em.createNamedQuery(RequerimientoEntity.findRespondidosByExpediente);
//			q.setParameter("idExp", adminFileId);
//			return (List<RequerimientoEntity>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar requerimientos respondidos para el expediente " + adminFileId, e);
//		}
//		
//		return null;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<ContadorRequerimientoExpediente> getNumeroRequerimientosByExpediente(EntityManager em, List<String> adminFileIds) {
//		try {			
//			Query q = em.createQuery("SELECT new es.apt.ae.facade.expedientes.dto.ContadorRequerimientoExpediente(e.id, "
//					+ "( SELECT COUNT(r) FROM RequerimientoEntity r WHERE r.estado.key = 'RESPONDIDO' and  r.expediente.id = e.id ), "
//					+ "( SELECT COUNT(r) FROM RequerimientoEntity r WHERE r.estado.key <> 'NO_RESPONDIDO' and r.expediente.id = e.id )) "
//					+ "FROM Expediente e WHERE e.id in (:list)");
//			q.setParameter("list", adminFileIds);
//			return (List<ContadorRequerimientoExpediente>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al recuperar requerimientos para los expedientes " + org.apache.commons.lang3.StringUtils.join(adminFileIds, ","), e);
//		}
//		return new ArrayList<ContadorRequerimientoExpediente>();
//	}
	
	
	public static CatPlantilla getPlantillaById(EntityManager em, Integer plantillaId) {
		CatPlantilla plantilla = null;
		
		try {
			Query q = null; 
			q = em.createNamedQuery(CatPlantilla.findById);
			q.setParameter("id", plantillaId);
			plantilla = (CatPlantilla) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe la plantilla con id " + plantillaId);
		} catch (Exception e) {
			logger.error("Error al consultar la plantilla con id " + plantillaId, e);
		}
	
		return plantilla;
	}
	
	public static Documento getDocumentoAutomatico(EntityManager em, String nombreDoc, String numExpediente, String actividadId) {
		Documento documento = null;
		
		try {
			Query q = em.createNamedQuery("Documento.findDocumentoAuto");
			q.setParameter("docName", nombreDoc);
			q.setParameter("numExp", numExpediente);
			q.setParameter("activityName", actividadId);
			documento = (Documento) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe ningun documento automatico con la siguiente informacion: * Nombre Documento: " + nombreDoc + 
					" | * Num. Expediente: " + numExpediente + " | * Id. Actividad: " + actividadId);
		} catch (Exception e) {
			logger.error("Error al consultar un documento automatico con la siguiente informacion: * Nombre Documento: " + nombreDoc + 
				" | * Num. Expediente: " + numExpediente + " | * Id. Actividad: " + actividadId, e);
		}
	
		return documento;
	}
	
//	public static RequerimientoEntity marcarRequerimientoNotificado(EntityManager em, RequerimientoEntity requerimiento, Date fechaNotificacion) {
//		try {
//			Query q = em.createNamedQuery(EstadoRequerimientoEntity.findByKey);
//			q.setParameter("key", "NOTIFICADO");
//			
//			requerimiento.setEstado((EstadoRequerimientoEntity)q.getSingleResult());
//			requerimiento.setFechaNotificacion(new Timestamp(fechaNotificacion.getTime()));
//			
//			// Se calcula la fecha de caducidad
////			Calendar cal = Calendar.getInstance();
////			cal.setTime(requerimiento.getFechaNotificacion());
////			cal.add(Calendar.DAY_OF_WEEK, requerimiento.getPlazo());
////			requerimiento.setFechaCaducidad(new Timestamp(cal.getTime().getTime()));
//			
//			Date fechaCaducidad = DateUtils.getDateIncrementedByDays(fechaNotificacion.getTime(), requerimiento.getPlazo());
//			fechaCaducidad = DateUtils.getDate(fechaCaducidad, 24, 0, 0);
//			requerimiento.setFechaCaducidad(new Timestamp(fechaCaducidad.getTime()));
//			
//			requerimiento = merge(em, requerimiento);
//		} catch (Exception e) {
//			logger.error("Error al actualizar el estado del requerimiento " + requerimiento.getIdRequerimiento() + " a notificado", e);
//		}
//	
//		return requerimiento;
//	}

	/**
	 * Retorna la lista de relaciones decretos habilitados.
	 * @param EntityManager
	 * @return List<CatRelacionDecreto>
	 */
//	@SuppressWarnings("unchecked")
//	public static List<CatRelacionDecreto> getRelacionesDecreto(EntityManager em) {
//		
//		List<CatRelacionDecreto> relacionesDecreto = null;
//		
//		try {
//			Query q = em.createNamedQuery(CatRelacionDecreto.find);
//			relacionesDecreto = (List<CatRelacionDecreto>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar el listado de tipos de relaciones de decreto habilitadas", e);
//		}
//	
//		return relacionesDecreto;
//	}
	
	/**
	 * Retorna la lista de relaciones expedientes habilitados.
	 * @param EntityManager
	 * @return List<CatRelacionExpediente>
	 */
//	@SuppressWarnings("unchecked")
//	public static List<CatRelacionExpediente> getRelacionesExpediente(EntityManager em) {
//		
//		List<CatRelacionExpediente> relacionesExpediente = null;
//		
//		try {
//			Query q = em.createNamedQuery(CatRelacionExpediente.find);
//			relacionesExpediente = (List<CatRelacionExpediente>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar el listado de tipos de relaciones de decretos con expedientes habilitadas", e);
//		}
//	
//		return relacionesExpediente;
//	}

//	@SuppressWarnings("unchecked")
//	public static List<CatEstadoDecreto> getEstadosDecreto(EntityManager em) {
//		List<CatEstadoDecreto> estadosDecreto = null;
//		
//		try {
//			Query q = em.createNamedQuery(CatEstadoDecreto.find);
//			estadosDecreto = (List<CatEstadoDecreto>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar el listado de tipos de estados de decretos con expedientes habilitadas", e);
//		}
//	
//		return estadosDecreto;
//	}
	
	public static CatPropiedadesConfiguracion getPropiedadConfiguracion(EntityManager em, String idPropiedad) throws Exception {
		CatPropiedadesConfiguracion propiedadConfiguracion = null;
		
		try {
			Query q = em.createNamedQuery(CatPropiedadesConfiguracion.findById);
			q.setParameter("id", idPropiedad);
			propiedadConfiguracion = (CatPropiedadesConfiguracion) q.getSingleResult();
		} catch (NoResultException e) {
			logger.warn("No se existe la propiedad de configuracion '" + idPropiedad + "'");
			throw e;
		} catch (Exception e) {
			logger.error("No se ha podido recuperar la propiedad de configuracion '" + idPropiedad + "'", e);
			throw e;
		}
	
		return propiedadConfiguracion;
	}
	
	public static String getValorPropiedadConfiguracion(EntityManager em, String idPropiedad) throws Exception {
		String valorPropConfiguracion = null;
		
		try {
			Query q = em.createNamedQuery(CatPropiedadesConfiguracion.findById);
			q.setParameter("id", idPropiedad);
			CatPropiedadesConfiguracion propiedadConfiguracion = (CatPropiedadesConfiguracion) q.getSingleResult();
			if (null != propiedadConfiguracion) {
				valorPropConfiguracion = propiedadConfiguracion.getValor();
			}
		} catch (NoResultException e) {
			logger.warn("No se existe la propiedad de configuracion '" + idPropiedad + "'");
			throw e;
		} catch (Exception e) {
			logger.error("No se ha podido recuperar la propiedad de configuracion '" + idPropiedad + "'", e);
			throw e;
		}
	
		return valorPropConfiguracion;
	}
	
	public static Persona getPersonaByDNI(EntityManager em, String dni) {
		Persona persona = null;
		try {
			persona = (Persona) em.createNamedQuery(Persona.getPersonaByDNI).setParameter("dni", dni).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe en la base de datos del Portafirmas una persona con DNI " + dni);
		} catch (Exception e) {
			logger.error("Error al recuperar de la base de datos del Portafirmas una persona con DNI " + dni);
		}

		return persona;
	}
	
	public static BackOffice getBackOfficeByUsername(EntityManager em, String username) {
		BackOffice backoffice = null;
		try {
			backoffice = (BackOffice) em.createNamedQuery("findBackofficeByUsername").setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No existe en la base de datos un backoffice asociado al usuario " + username);
		} catch (Exception e) {
			logger.error("Error al recuperar de la base de datos un backoffice asociado al usuario " + username);
		}
		
		return backoffice;
	}
	
	@SuppressWarnings("unchecked")
	public static List<BackOffice> getBackOffices(EntityManager em) {
		List<BackOffice> backoffices = null;
		try {
			backoffices = (List<BackOffice>) em.createNamedQuery("findAllBackoffice").getResultList();
		} catch (Exception e) {
			logger.error("Error al recuperar de la base de datos el listado de backoffices");
		}
		
		return backoffices;
	}
	
	public static FirmaManuscritaDocumento getFirmaManuscritaDoc(EntityManager em, String docGui) {
		return (FirmaManuscritaDocumento) em.createNamedQuery("FirmaManuscritaDocumento.findByDocGui").setParameter("docGui", docGui).getSingleResult();
	}
	
//	@SuppressWarnings("unchecked")
//	public static List<Act_ru_task> getTareasInstancia(EntityManager em, String instancia, String tramite) {
//		try {
//			Query q = null;
//			if (StringUtils.isNullOrEmpty(tramite)) {
//				q = em.createNamedQuery(Act_ru_task.findByInstance);
//				q.setParameter("instance", instancia);
//			} else {
//				q = em.createNamedQuery(Act_ru_task.findByInstanceAndActivityId);
//				q.setParameter("instance", instancia);
//				q.setParameter("activityId", tramite);
//			}
//			return (List<Act_ru_task>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("No se han recuperado tareas actividad para la instancia " + instancia + " y/o tramite " + tramite, e);
//		}
//		
//		return null;
//	}
	
	@SuppressWarnings("unchecked")
	public static List<DocumentoPortafirmas> getListDocumentoPortafirmasByURI(EntityManager em, List<String> uris) {
		List<DocumentoPortafirmas> listDocumentos = new ArrayList<DocumentoPortafirmas>();
		try {
			listDocumentos = (List<DocumentoPortafirmas>) em.createNamedQuery("findListDocumentoPortafirmasbyURI").setParameter("uris", uris).getResultList();
		} catch (Exception e) {
			logger.error("Error al intentar recuperar los documentos de Portafirmas de la base de datos", e);
		}

		return listDocumentos;
	}
	
//	public static NotificacionBuzon getNotificacionBuzonById(EntityManager em, Integer notificacionId) {
//		NotificacionBuzon notificacion = null;
//		try {
//			Query q = em.createNamedQuery("NotificacionBuzon.findById");
//			q.setParameter("id", notificacionId);
//			notificacion = (NotificacionBuzon)q.getSingleResult();
//		} catch(NoResultException e) {
//			logger.error("No se ha encontrado la notificacion con id " + notificacionId, e);
//		} catch (Exception e) {
//			logger.error("Error inesperado al obtener la notificacion con id " + notificacionId, e);
//		}
//		return notificacion;
//	}
	
//	public static NotificacionBuzon getNotificacionBuzonByNumDocumento(EntityManager em, String numDocumento) {
//		NotificacionBuzon notificacion = null;
//		try {
//			Query q = em.createNamedQuery("NotificacionBuzon.findByNumDocumento");
//			q.setParameter("numDocumento", numDocumento);
//			notificacion = (NotificacionBuzon)q.getSingleResult();
//		} catch(NoResultException e) {
//			logger.error("No se ha encontrado la notificacion con documentNumber " + numDocumento, e);
//		} catch (Exception e) {
//			logger.error("Error inesperado al obtener la notificacion con documentNumber " + numDocumento, e);
//		}
//		return notificacion;
//	}
	
//	public static NotificacionBuzon getNotificacionBuzonByNumNotificacion(EntityManager em, String numNotificacion) {
//		NotificacionBuzon notificacion = null;
//		try {
//			Query q = em.createNamedQuery("NotificacionBuzon.findByNumNotificacion");
//			q.setParameter("numNotificacion", numNotificacion);
//			notificacion = (NotificacionBuzon)q.getSingleResult();
//		} catch(NoResultException e) {
//			logger.error("No se ha encontrado la notificacion con numero " + numNotificacion, e);
//		} catch (Exception e) {
//			logger.error("Error inesperado al obtener la notificacion con numero " + numNotificacion, e);
//		}
//		return notificacion;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<AdjuntoNotificacion> getAdjuntosNotificacionBuzon(EntityManager em, Integer notificacionId) {
//		List<AdjuntoNotificacion> listAdjuntos = new ArrayList<AdjuntoNotificacion>();
//		try {
//			Query q = em.createNamedQuery(AdjuntoNotificacion.findByIdNotificacion);
//			q.setParameter("notificacionId", notificacionId);
//			listAdjuntos = (List<AdjuntoNotificacion>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error inesperado al obtener los adjuntos para la notificacion con id " + notificacionId, e);
//		}
//		return listAdjuntos;
//	}
	
//	@SuppressWarnings("unchecked")
//	public static List<AdjuntoNotificacion> getAdjuntosNotificacionExpediente(EntityManager em, Integer notificacionId) {
//		List<AdjuntoNotificacion> listAdjuntos = new ArrayList<AdjuntoNotificacion>();
//		try {
//			Query q = em.createNamedQuery(AdjuntoNotificacion.findByIdNotificacion);
//			q.setParameter("notificacionId", notificacionId);
//			listAdjuntos = (List<AdjuntoNotificacion>)q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error inesperado al obtener los adjuntos para la notificacion con id " + notificacionId, e);
//		}
//		return listAdjuntos;
//	}
	
}
