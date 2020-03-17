package com.opencanarias.ejb.expedientes.dao;

//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;

import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.xml.datatype.DatatypeConfigurationException;

import org.jboss.logging.Logger;
//
//import com.opencan.repository.custom.model.AlfRepositoryConstants;
//import com.opencan.repository.exceptions.RepositoryException;
//import com.opencan.repository.implementations.alfresco.AlfRepositoryService;
//import com.opencan.repository.interfaces.IRepositoryService;
//import com.opencan.repository.objects.Parameters;
//import com.opencanarias.api.security.utils.LoginModuleConstants;
import com.opencanarias.ejb.common.FacadeBean;
//import com.opencanarias.ejb.expedientes.ExpedientesFacadeBeanHelper;
//import com.opencanarias.ejb.expedientes.dto.ContadorRequerimientosSede;
//import com.opencanarias.ejb.expedientes.dto.FiltrosBandejaDocumentos;
//import com.opencanarias.ejb.expedientes.dto.FiltrosBandejaTareas;
//import com.opencanarias.ejb.expedientes.dto.FiltrosConsulta;
//import com.opencanarias.ejb.expedientes.dto.FiltrosExpedientes;
import com.opencanarias.exceptions.ExpedientesFacadeException;
//import com.opencanarias.exceptions.GenericFacadeException;
//import com.opencanarias.security.authentication.AuthenticationHelper;
//import com.opencanarias.utils.ConfigUtils;
//import com.opencanarias.utils.Constantes;
//import com.opencanarias.utils.DateUtils;
//import com.opencanarias.utils.DocumentosExpedientesUtils;
//import com.opencanarias.utils.ExpedientesConstants;
import com.opencanarias.utils.StringUtils;
//import com.opencanarias.utils.constants.ConstantesGestorDocumental;
//
//import es.apt.ae.facade.dto.DepartamentoItem;
//import es.apt.ae.facade.dto.EstadoNotificacionDocumento;
//import es.apt.ae.facade.dto.PeticionesItem;
//import es.apt.ae.facade.entities.Actividad;
//import es.apt.ae.facade.entities.CatContenidoEstaticoEtiqueta;
//import es.apt.ae.facade.entities.CatPlantilla;
//import es.apt.ae.facade.entities.CatProcedimientoCircuito;
//import es.apt.ae.facade.entities.CatProcedimientoPermiso;
//import es.apt.ae.facade.entities.Documento;
//import es.apt.ae.facade.entities.Expediente;
//import es.apt.ae.facade.entities.Familia;
//import es.apt.ae.facade.entities.FirmaManuscritaDocumento;
//import es.apt.ae.facade.entities.GestionPortafirmasDocumento;
//import es.apt.ae.facade.entities.IndiceCapituloDocumento;
//import es.apt.ae.facade.entities.Procedimiento;
//import es.apt.ae.facade.entities.Proceso;
//import es.apt.ae.facade.entities.RelacionExpedientes;
//import es.apt.ae.facade.entities.Rol;
//import es.apt.ae.facade.entities.TerceroExp;
//import es.apt.ae.facade.entities.Track;
//import es.apt.ae.facade.entities.Transicion;
//import es.apt.ae.facade.entities.actuaciones.Actuacion;
//import es.apt.ae.facade.entities.actuaciones.ActuacionesLeidasInfo;
//import es.apt.ae.facade.entities.actuaciones.CatAccionesExpedientes;
//import es.apt.ae.facade.entities.actuaciones.DocumentoReferenciaActuacion;
//import es.apt.ae.facade.entities.earegistro.Tercero;
//import es.apt.ae.facade.entities.notificaciones.AdjuntoNotificacionExpediente;
//import es.apt.ae.facade.entities.notificaciones.NotificacionDocumento;
//import es.apt.ae.facade.entities.notificaciones.NotificacionPapelDocumento;
//import es.apt.ae.facade.entities.utils.EntitiesUtils;
//import es.apt.ae.facade.expedientes.dto.ContadorRequerimientoExpediente;
//import es.apt.ae.facade.expedientes.dto.DocumentoBandejaItem;
//import es.apt.ae.facade.expedientes.dto.ExpedienteConsultaItem;
//import es.apt.ae.facade.expedientes.dto.TareaBandejaItem;
//import es.apt.ae.facade.expedientes.dto.TareaItem;
//import es.apt.ae.facade.gestor.documental.transform.ConstantesMetadatos;
//import es.apt.ae.facade.ws.params.commons.in.TipoDocumentoPersonalEnum;
//import es.apt.ae.facade.ws.params.commons.in.TipoTerceroEnum;
//import es.apt.ae.facade.ws.params.expedientes.in.notificacion.InfoNotifDocumento;
//import es.apt.ae.facade.ws.params.expedientes.out.busqueda.DocumentoDatosFirma;
//import es.apt.ae.facade.ws.params.expedientes.out.busqueda.DocumentoDatosNotificacion;
//import es.apt.ae.facade.ws.params.expedientes.out.busqueda.DocumentoDatosRegistro;
//import es.apt.ae.facade.ws.params.gestor.documental.commons.inout.Metadato;


/**
 * @author Open Canarias S.L.
 */
@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class ExpedientesDAOBean extends FacadeBean implements ExpedientesDAORemote, ExpedientesDAOLocal {
	private static Logger logger = Logger.getLogger(ExpedientesDAOBean.class);
	
	
	@PersistenceContext (unitName="facade-pu")
	private EntityManager em;
	
	/**
	 * Constructor por defecto
	 */
	public ExpedientesDAOBean() {
		logger.info("[SRV-EXP] Instanciando el EJB DAO para Expedientes ...");
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	public <T> void persist(T entidad) throws ExpedientesFacadeException {
		em.persist(entidad);
	}	
	
	public <T> T merge(T entidad) throws ExpedientesFacadeException {
		T resultado = em.merge(entidad);
		return resultado;
	}

	public <T> void remove(T entidad) throws ExpedientesFacadeException {
		em.remove(entidad);
	}
	
//	public Procedimiento getProcedimientoById(String procId) {
//		return EntitiesUtils.getProcedimientoById(em, procId);
//	}
//
//	public List<Procedimiento> getProcedimientos() {
//		return EntitiesUtils.getProcedimientos(em);
//	}	
//	
//	public List<Procedimiento> getProcedimientosByFamilia(String familia) {
//		return EntitiesUtils.getProcedimientosByFamilia(em, familia);
//	}
//	
//	public List<Procedimiento> getProcedimientosActivosRegistro() {
//		return EntitiesUtils.getProcedimientosActivosRegistro(em);
//	}	
//	
//	public List<Procedimiento> getProcedimientosActivosRegistroByFamilia(String familia) {
//		return EntitiesUtils.getProcedimientosActivosRegistroByFamilia(em, familia);
//	}	
//	
//	public Map<String, Procedimiento> getProcedimientosInicio(List<String> departamentos, String familia) {
//		return EntitiesUtils.getProcedimientosInicio(em, departamentos, familia, "OFICIO");
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<CatProcedimientoPermiso> getPermisosProcedimiento(String procId) {
//		List<CatProcedimientoPermiso> permisosProcedimiento = null;
//		
//		try {
//			Query q = em.createNamedQuery("CatProcedimientoPermiso.findByProcedimientoId");
//			q.setParameter("procId", procId);
//			permisosProcedimiento = (List<CatProcedimientoPermiso>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar los departamentos con permisos sobre el procedimiento " + procId, e);
//		}
//	
//		return permisosProcedimiento;
//	}
//
//	public List<Familia> getFamilias() {
//		return EntitiesUtils.getFamilias(em);
//	}
	/**
	 * Comprueba si existe en BD un determinado expediente.
	 * @param numExpediente Identificador del expediente
	 * @return Un objeto de tipo Expediente en el caso de que exista y null en caso contrario.
	 */	
//	public Expediente getExpedienteById(String numExpediente, Boolean esActivo) {
//		return EntitiesUtils.getExpedienteById(em, numExpediente, esActivo);
//	}	
	
//	@SuppressWarnings("unchecked")
//	public Expediente getExpedienteByNumRegistro(String numRegistro) {
//		Expediente expediente = null;
//		
//		try {
//			Query q = em.createNamedQuery(Expediente.findActivesByNumRegistro);
//			q.setParameter("numRegistro", numRegistro);
//			List<Expediente> listaExpedientes = (List<Expediente>) q.getResultList();
//			if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
//				expediente = listaExpedientes.get(0);
//			}
//		} catch (Exception e) {
//			logger.info("No existe ningun expediente asociado al asiento '" + numRegistro + "'.", e);
//		}
//	
//		return expediente;
//	}
	
//	public Actuacion getActuacionById(String idActuacion, Boolean esActivo) {
//		return EntitiesUtils.getActuacionById(em, idActuacion, esActivo);
//	}	
//	
//	@SuppressWarnings("unchecked")
//	public List<String> getActuacionesIdsDocumentoNotificacion(String numExp, String nombreDoc) {
//		List<String> actuacionesIds = null;
//		
//		try {
//			Query q = em.createNamedQuery("DocumentoReferenciaActuacion.findByDocumentoNotificacion");
//			q.setParameter("numExpediente", numExp);
//			q.setParameter("nombreDocumento", nombreDoc);
//			List<DocumentoReferenciaActuacion> listaReferencias = (List<DocumentoReferenciaActuacion>) q.getResultList();
//			if (listaReferencias != null && !listaReferencias.isEmpty()) {
//				actuacionesIds = new ArrayList<String>();
//				for (DocumentoReferenciaActuacion docRef:listaReferencias) {
//					actuacionesIds.add(docRef.getActuacionId());
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Error al consultar documentos de notificacion de referencia en actuaciones. * Id. Exp: " + numExp + 
//					" | * Nombre Documento: " + nombreDoc, e);
//		}
//	
//		return actuacionesIds;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<ActuacionesLeidasInfo> getActuacionesLeidasInfo(List<String> actuacionesIds) {
//		List<ActuacionesLeidasInfo> actuacionesLeidasInfo = null;
//		
//		try {
//			Query q = em.createNamedQuery("ActuacionesLeidasInfo.findByActuacionesIds");
//			q.setParameter("actuacionesIds", actuacionesIds);
//			actuacionesLeidasInfo = (List<ActuacionesLeidasInfo>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar la informacion de lectura para las actuaciones indicadas. * Actuaciones Ids.: " + 
//					org.apache.commons.lang3.StringUtils.join(actuacionesIds, ","), e);
//		}
//	
//		return actuacionesLeidasInfo;
//	}	
//	
//	public Proceso getProcesoMaxVersion(String proceso) {
//		return EntitiesUtils.getProcesoMaxVersion(em, proceso);
//	}
//	
//	public Proceso getProcesoById(String procesoId) {
//		return EntitiesUtils.getProcesoById(em,  procesoId);
//	}
//	
//	public Actividad getActividadById(String actividadId, String procesoId, String procesoVersion) {
//		Actividad actividad = null;
//		
//		try {
//			Query q = em.createNamedQuery("Actividad.findByPk");
//			q.setParameter("id", actividadId);
//			q.setParameter("wfId", procesoId);
//			q.setParameter("wfVersion", procesoVersion);
//			actividad = (Actividad) q.getSingleResult();
//		} catch (NoResultException e) {
//			logger.info("No existe la actividad. * Id. Actividad: " + actividadId + " | * Id. Proceso: " + procesoId + " | * Version Proceso: " + procesoVersion);
//		} catch (Exception e) {
//			logger.error("Error al intentar recuperar la actividad. * Id. Actividad: " + actividadId + " | * Id. Proceso: " + procesoId + 
//					" | * Version Proceso: " + procesoVersion, e);
//		}
//	
//		return actividad;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<CatPlantilla> getPlantillasActividad(String actividadId, String procedimientoId, Boolean automaticas) {
//		List<CatPlantilla> plantillasActividad = null;
//		
//		try {
//			Query q = null; 
//			if (automaticas == null) {
//				q = em.createNamedQuery("CatPlantilla.findByActividad");
//				q.setParameter("procedimientoId", procedimientoId);
//				q.setParameter("actividadId", actividadId);
//			} else {
//				q = em.createNamedQuery("CatPlantilla.findByActividadAndAuto");
//				q.setParameter("procedimientoId", procedimientoId);
//				q.setParameter("actividadId", actividadId);
//				q.setParameter("auto", automaticas);
//			}
//			plantillasActividad = (List<CatPlantilla>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar las plantillas asociadas a la actividad. * Id. Procedimiento: " + procedimientoId + 
//					" | * Id. Actividad: " + actividadId + " | * Automaticas: " + automaticas, e);
//		}
//	
//		return plantillasActividad;
//	}
//	
//	public CatPlantilla getPlantillaById(Integer plantillaId) {
//		return EntitiesUtils.getPlantillaById(em, plantillaId);
//	}
//	
//	public CatContenidoEstaticoEtiqueta getContenidoEstaticoEtiqueta(Integer plantillaId, Integer etiquetaId) {
//		return EntitiesUtils.getContenidoEstaticoEtiqueta(em, plantillaId, etiquetaId);
//	}
//	
//	public Transicion getTransicion(String wfId, String actividadOrigenId, String actividadDestinoId, 
//			short numTransicion) {
//		return EntitiesUtils.getTransicion(em, wfId, actividadOrigenId, actividadDestinoId, numTransicion);
//	}
//	
//	public List<Documento> getDocumentosExpediente(String numExpediente) {
//		return EntitiesUtils.getDocumentosExp(em, numExpediente);
//	}
//	
//	public List<Documento> getDocumentosExpedienteNoCancelados(String numExpediente) {
//		return EntitiesUtils.getDocumentosExpNoCancelados(em, numExpediente);
//	}
//	
//	public Map<String, Documento> getDocumentosExpedienteByUris(String numExpediente, String idActuacion, List<String> uris) {
//		return EntitiesUtils.getDocumentosExpedienteByUris(em, numExpediente, idActuacion, uris);
//	}
//	
//	public Map<String, Documento> getDocumentosExpedienteRegistro(String numExpediente, String numRegistro) {
//		return EntitiesUtils.getDocumentosExpedienteRegistro(em, numExpediente, numRegistro);
//	}
//	
//	public Documento getDocumentoExpedienteByUri(String uriDoc) {
//		return EntitiesUtils.getDocumentoExpedienteByUri(em, uriDoc);
//	}
//	
//	public Documento getDocumentoExpedienteById(String numExpediente, String nombreDoc) {
//		return EntitiesUtils.getDocumentoExpedienteById(em, numExpediente, nombreDoc);
//	}
//	
//	public List<Documento> getDocumentosFirma(String numExpediente, String nombreDoc) {
//		return EntitiesUtils.getDocumentosFirma(em, numExpediente, nombreDoc);
//	}
//	
//	public List<Documento> getDocumentoYFirmas(String numExpediente, String nombreDoc) {
//		return EntitiesUtils.getDocumentoYFirmas(em, numExpediente, nombreDoc);
//	}
//	
//	public Documento getDocumentoAutomatico(String nombreDoc, String numExpediente, String actividadId) {
//		return EntitiesUtils.getDocumentoAutomatico(em, nombreDoc, numExpediente, actividadId);
//	}
//	
//	public NotificacionDocumento getNotificacionDocumento(String administrativeFileId, String documentId) {
//		return EntitiesUtils.getNotificacionDocumento(em, administrativeFileId, documentId);
//	}
//	
//	public NotificacionDocumento getNotificacionDocumentoByUri(String uri) {
//		return EntitiesUtils.getNotificacionDocumentoByUri(em, uri);
//	}
//	
//	public NotificacionDocumento getNotificacionDocumentoByNumNotificacion(String numNotificacion) {
//		return EntitiesUtils.getNotificacionDocumentoByNumNotificacion(em, numNotificacion);
//	}
//	
//	public List<Documento> getDocumentosExpedienteNotificados(String administrativeFileId, List<Documento> documentos) {
//		return EntitiesUtils.getDocumentosExpedienteNotificados(em, administrativeFileId, documentos);
//	}
//	
//	public Map<String, String> getNotificacionesIdsDocumentosExpediente(String administrativeFileId, List<Documento> documentos) { 
//		return EntitiesUtils.getNotificacionesIdsDocumentosExpediente(em, administrativeFileId, documentos);
//	}
//	
//	public Map<String, NotificacionPapelDocumento> getNotificacionesPapelDocumentos(String adminFileId, List<Documento> documentos) {
//		return EntitiesUtils.getNotificacionesPapelDocumentos(em, adminFileId, documentos);
//	}
//	
//	public NotificacionPapelDocumento getNotifPresencialDocumento(String adminFileId, String documentId, boolean electronic) {
//		return EntitiesUtils.getNotifPresencialDocumento(em, adminFileId, documentId, electronic);
//	}
//	
//	public Map<String, EstadoNotificacionDocumento> getEstadosDocumentosNotificados(String adminFileId) {
//		return EntitiesUtils.getEstadosDocumentosExpedienteNotificados(em, adminFileId);
//	}	
//
//	public GestionPortafirmasDocumento getGestionPortafirmasDocByUri(String uri) {
//		return EntitiesUtils.getGestionPortafirmasDocByUri(em, uri);
//	}
//	
//	public FirmaManuscritaDocumento getFirmaManuscritaDocByUri(String uri) {
//		return EntitiesUtils.getFirmaManuscritaDocByUri(em, uri);
//	}
//	
//	public Map<String, String> getTiposDocumentos() {
//		return EntitiesUtils.getTiposDocumentos(em);
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Expediente> getExpedientesByQuery(String queryStr) {
//		// Se ejecuta la query
//		try {
//			Query q = em.createQuery(queryStr);
//			return (List<Expediente>) q.getResultList();
//		} catch(Exception e) {
//			logger.error("Error al intentar recuperar el listado de expedientes para la consulta [" + queryStr + "]", e);
//		}
//		return null;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<Expediente> getExpedientesByWfInstancias(List<String> wfInstancias, boolean soloActivos) {
//		List<Expediente> expedientes = null;
//		
//		try {
//			Query q = null;
//			if (soloActivos) {
//				q = em.createNamedQuery(Expediente.findActivesByWfInstances);
//			} else {
//				q = em.createNamedQuery(Expediente.findByWfInstances);
//			}			
//			q.setParameter("instances", wfInstancias);
//			expedientes = (List<Expediente>) q.getResultList();
//		} catch (Exception e) {
//			logger.error("Error al consultar expedientes con los parametros indicados. * Instancias: " + 
//					org.apache.commons.lang3.StringUtils.join(wfInstancias, ",") + " | Solo Activos: " + soloActivos, e);
//		}
//	
//		return expedientes;
//	}
//	
//	public List<CatAccionesExpedientes> getActuacionesAutomaticasPorActividad(String wfId, String actividadId, String autoDisparador) {
//		return EntitiesUtils.getActuacionesAutomaticasPorActividad(em, wfId, actividadId, autoDisparador);
//	}
//	
//	public List<CatAccionesExpedientes> getCondicionesDespacharExpAuto(String wfId, String actividadId, String tipoActuacionId) {
//		return EntitiesUtils.getCondicionesDespacharExpAuto(em, wfId, actividadId, tipoActuacionId);
//	}
//	
//	public List<CatProcedimientoCircuito> getCatProcedimientoCircuitos(String idProcedimiento, String idTramite, String idDepartamento ){
//		return EntitiesUtils.getCatProcedimientoCircuitos(em, idProcedimiento, idTramite, idDepartamento);
//	}
//	
//	public Rol getRolByNombre(String nombre) {
//		return EntitiesUtils.getRolById(em, nombre);
//	}
//	
//	public Map<String, Rol> getDepartamentosMap() {
//		Map<String, Rol> rolesMap = null;
//		
//		List<Rol> roles = EntitiesUtils.getDepartamentos(em);
//		if (roles != null) {
//			rolesMap = new LinkedHashMap<String, Rol>();
//			for (Rol rol:roles) {
//				rolesMap.put(rol.getId(), rol);
//			}
//		}
//		
//		return rolesMap;
//	}
//	
//	public Map<String, Rol> getRolesDecretablesMap() {
//		Map<String, Rol> rolesDecretablesMap = null;
//		
//		List<Rol> rolesDecretables = EntitiesUtils.getRolesDecretables(em);
//		if (rolesDecretables != null) {
//			rolesDecretablesMap = new HashMap<String, Rol>();
//			for (Rol rol:rolesDecretables) {
//				rolesDecretablesMap.put(rol.getId(), rol);
//			}
//		}
//		
//		return rolesDecretablesMap;
//	}
//	
//	public Map<String, Rol> getRolesMap() {
//		Map<String, Rol> rolesMap = null;
//		
//		List<Rol> rolesList = EntitiesUtils.getRoles(em);
//		if (rolesList != null) {
//			rolesMap = new HashMap<String, Rol>();
//			for (Rol rol:rolesList) {
//				rolesMap.put(rol.getId(), rol);
//			}
//		}
//		
//		return rolesMap;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public boolean existeRelacion(String numExp1, String numExp2, Integer tipoRelacion) throws ExpedientesFacadeException {		
//		try {
//			Query q = em.createNamedQuery("RelacionExpedientes.findByExpsAndRelacion");
//			q.setParameter("numExp1", numExp1);
//			q.setParameter("numExp2", numExp2);
//			q.setParameter("tipoRelacion", tipoRelacion);
//			List<RelacionExpedientes> relaciones = (List<RelacionExpedientes>) q.getResultList();
//			if (relaciones != null && !relaciones.isEmpty()) {
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("Error al consultar relaciones entre expedientes con los parametros indicados. * Num. Expediente 1: " + numExp1 +
//					" |	* Num. Expediente 2" + numExp2 + "  | * Tipo Relacion: " + tipoRelacion, e);
//		}
//	
//		return false;
//	}
//	
//	public List<IndiceCapituloDocumento> getIndiceCapituloDocumentos(String adminFileId, String documentId, Integer capituloId) {
//		return EntitiesUtils.getIndiceCapituloDocumentos(em, adminFileId, documentId, capituloId);
//	}
//	
//	public List<Documento> getDocumentosRefActuacion(String adminFileId) {
//		return EntitiesUtils.getDocumentosRefActuacion(em, adminFileId);
//	}
//	
//	public IndiceCapituloDocumento removeDocumentoIndice(Integer capituloId, String documentId) throws ExpedientesFacadeException {		
//		IndiceCapituloDocumento docCapituloRemoved = null;
//		
//		if (capituloId != null) {
//			try {
//				List<IndiceCapituloDocumento> docsCapitulo = getIndiceCapituloDocumentos(null, null, capituloId);
//				for (IndiceCapituloDocumento docCapitulo:docsCapitulo) {
//					if (documentId.equals(docCapitulo.getPk().getIdDocumento())) {
//						remove(docCapitulo);
//						docCapituloRemoved = docCapitulo;
//					} else if (docCapituloRemoved != null) {
//						docCapitulo.setOrdenEnCapitulo((short)(docCapitulo.getOrdenEnCapitulo()-1));
//						merge(docCapitulo);
//					}
//				}
//			} catch (Exception e) {
//				throw new ExpedientesFacadeException(e);
//			}
//		}
//		
//		return docCapituloRemoved;
//	}
//	
//	public Track getUltimoPasoHistorico(String numExpediente) {
//		try {
//			List<Track> historico = EntitiesUtils.getHistoricoEntidad(em, numExpediente);
//			if (historico != null && !historico.isEmpty()) {
//				return historico.get(historico.size()-1);
//			}
//		} catch(Exception e) {
//			logger.error("Error al consultar el historico del expediente " + numExpediente, e);
//		}
//		return null;
//	}
//	
//	public Map<Integer, Tercero> getTercerosMap(List<es.apt.ae.facade.ws.params.commons.in.Tercero> terceros) 
//			throws ExpedientesFacadeException {
//		String msg = "";
//		
//		Map<Integer, Tercero> tercerosBDMap = new HashMap<Integer, Tercero>();
//		for (es.apt.ae.facade.ws.params.commons.in.Tercero tercero:terceros) {
//			String numDocumento = tercero.getNumeroDocumento();
//			TipoDocumentoPersonalEnum tipoDocumentoEnum = tercero.getTipoDocumento();
//			String tipoDocumento = null;
//			if (tipoDocumentoEnum != null) {
//				if (tipoDocumentoEnum == TipoDocumentoPersonalEnum.NIF) {
//					tipoDocumento = "N";
//				} else if (tipoDocumentoEnum == TipoDocumentoPersonalEnum.CIF) {
//					tipoDocumento = "C";
//				} else if (tipoDocumentoEnum == TipoDocumentoPersonalEnum.PASAPORTE) {
//					tipoDocumento = "P";
//				} else if (tipoDocumentoEnum == TipoDocumentoPersonalEnum.NIE) {
//					tipoDocumento = "E";
//				} else if (tipoDocumentoEnum == TipoDocumentoPersonalEnum.OTROS_PERSONA_FISICA) {
//					tipoDocumento = "X";
//				} else if (tipoDocumentoEnum == TipoDocumentoPersonalEnum.CODIGO_DE_ORIGEN) {
//					tipoDocumento = "O";
//				}
//			}
//			TipoTerceroEnum tipoTercero = tercero.getTipoTercero();
//			if (!StringUtils.isNullOrEmpty(numDocumento) && tipoTercero != null) {
//				Tercero terceroBD = null;
//				boolean terceroDuplicado = false;
//				try {
//					terceroBD = EntitiesUtils.getTerceroByIdentificacion(em, numDocumento, tipoDocumento);
//				} catch (NoResultException e) {
//				} catch (NonUniqueResultException e) {
//					terceroDuplicado = true; 
//				}
//				if (terceroBD == null) {
//					if (terceroDuplicado) {
//						msg += Constantes.FWS_DESC_ERROR_TERCERO_DUPLICADO.replaceFirst("<NUM_DOCUMENTO>", numDocumento);
//					} else {
//						if (tipoDocumento == null) {
//							msg += Constantes.FWS_DESC_ERROR_TERCERO_NO_EXISTE.replaceFirst("<NUM_DOCUMENTO>", numDocumento);
//						} else {
//							msg += Constantes.FWS_DESC_ERROR_TERCERO_NO_EXISTE_2.replaceFirst("<NUM_DOCUMENTO>", numDocumento)
//									.replaceFirst("<TIPO_IDENTIFICACION>", tipoDocumento);
//						}
//					}
//				} else {
//					if (tipoTercero.ordinal() == TipoTerceroEnum.INTERESADO.ordinal()) {
//						tercerosBDMap.put(Constantes.TIPO_TERCERO_INTERESADO, terceroBD);
//					} else if (tipoTercero.ordinal() == TipoTerceroEnum.REPRESENTANTE.ordinal()) {
//						tercerosBDMap.put(Constantes.TIPO_TERCERO_REPRESENTANTE, terceroBD);
//					}
//				}
//			}
//		}
//		if (!msg.equals("")) {
//			throw new ExpedientesFacadeException(msg);
//		}
//		return tercerosBDMap;
//	}
//	
//	public Map<Integer, Tercero> getTercerosExpedienteMap(List<TerceroExp> terceros) throws ExpedientesFacadeException {
//		String msg = "";
//		
//		Map<Integer, Tercero> tercerosBDMap = new HashMap<Integer, Tercero>();
//		for (TerceroExp tercero:terceros) {
//			if (tercero.getCancelado() == null || !tercero.getCancelado()) {
//				String numDocumento = tercero.getIdentificacion();
//				String tipoDocumento = tercero.getTipoDocumento();
//				String tipoRelacion = tercero.getTipoRelacion();
//				if (!StringUtils.isNullOrEmpty(numDocumento) && !StringUtils.isNullOrEmpty(tipoRelacion)) {
//					Tercero terceroBD = null;
//					boolean terceroDuplicado = false;
//					try {
//						terceroBD = EntitiesUtils.getTerceroByIdentificacion(em, numDocumento, tipoDocumento);
//					} catch (NoResultException e) {
//					} catch (NonUniqueResultException e) {
//						terceroDuplicado = true; 
//					}
//					if (terceroBD == null) {
//						if (terceroDuplicado) {
//							msg += Constantes.FWS_DESC_ERROR_TERCERO_DUPLICADO.replaceFirst("<NUM_DOCUMENTO>", numDocumento);
//						} else {
//							if (tipoDocumento == null) {
//								msg += Constantes.FWS_DESC_ERROR_TERCERO_NO_EXISTE.replaceFirst("<NUM_DOCUMENTO>", numDocumento);
//							} else {
//								msg += Constantes.FWS_DESC_ERROR_TERCERO_NO_EXISTE_2.replaceFirst("<NUM_DOCUMENTO>", numDocumento)
//										.replaceFirst("<TIPO_IDENTIFICACION>", tipoDocumento);
//							}
//						}
//					} else {
//						if (TerceroExp.INTERESADO.equals(tipoRelacion)) {
//							tercerosBDMap.put(Constantes.TIPO_TERCERO_INTERESADO, terceroBD);
//						} else if (TerceroExp.REPRESENTANTE.equals(tipoRelacion)) {
//							tercerosBDMap.put(Constantes.TIPO_TERCERO_REPRESENTANTE, terceroBD);
//						}
//					}
//				}
//			}
//		}
//		if (!msg.equals("")) {
//			throw new ExpedientesFacadeException(msg);
//		}
//		return tercerosBDMap;		
//	}
//	
//	// En el caso de que el expediente ya tenga un tercero con el tipo de relación especificada, se cancela el existente y se añade el nuevo.
//	// En caso contrario se añade el nuevo simplemente.
//	public void actualizarTerceroExpediente(Expediente expediente, String tipoRelacion, Tercero datosTerceroNuevo) 
//			throws ExpedientesFacadeException {
//
//		boolean persistirExp = false;
//
//		if (datosTerceroNuevo != null) {
//			// Si el expediente ya tiene algun tercero del tipo indicado, cancelarlo
//			for (TerceroExp tercero:expediente.getTerceros()) {
//				if (tercero.getTipoRelacion().equals(tipoRelacion) && (tercero.getCancelado() == null || !tercero.getCancelado())) {
//					tercero.setCancelado(Boolean.TRUE);
//					tercero.setFechaCancelacion(new Date());
//				}
//			}
//			
//			// Añadir el nuevo tercero
//			TerceroExp terceroNuevo = ExpedientesFacadeBeanHelper.getTerceroExpediente(expediente.getId(), datosTerceroNuevo, tipoRelacion);
//			
//			try {
//				em.persist(terceroNuevo);
//				expediente.getTerceros().add(terceroNuevo);
//				// Actualizar la información en el expediente
//				expediente.setDniInteresado(terceroNuevo.getIdentificacion());
//				expediente.setNombreInteresado(datosTerceroNuevo.getNombreCompleto());
//				// Actualizar los campos de notificaciones con los correspondientes al nuevo tercero.
//				expediente.setNotificacionesRecaudatorias(terceroNuevo.getNotificacionesRecaudatorias());
//				expediente.setTipoNotificacion(terceroNuevo.getTipoNotificacion());
//				persistirExp = true;
//			} catch(Exception e) {
//				persistirExp = false;
//				logger.error("Error al intentar actualizar el tercero de tipo " + tipoRelacion + " para el expediente " + expediente.getId(), e);
//				throw new ExpedientesFacadeException(Constantes.FWS_EXP_COD_ERR_ACTUALIZAR_TERCERO, null, 
//						Constantes.FWS_EXP_DESC_ERR_ACTUALIZAR_TERCERO);
//			}
//		}
//
//		if (persistirExp) {
//			try {
//				em.persist(expediente);
//			} catch(Exception e) {
//				logger.error("Error al intentar actualizar el expediente " + expediente.getId() + " tras modificar el tercero de tipo " + tipoRelacion, e);
//				throw new ExpedientesFacadeException(Constantes.FWS_EXP_COD_ERR_ACTUALIZAR_TERCERO, null, 
//						Constantes.FWS_EXP_DESC_ERR_ACTUALIZAR_TERCERO);
//			}
//		}
//	}
//	
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public Documento saveDocumento(es.apt.ae.facade.entities.Documento documento) {
//		documento = em.merge(documento);
//		em.flush();
//		return documento;
//	}
//	
//	public NotificacionDocumento saveNotificacionDocumento(es.apt.ae.facade.entities.Documento documentoPrincipal, List<es.apt.ae.facade.entities.Documento> adjuntos,
//			String usuarioSolicitanteId, String wsdl) throws ExpedientesFacadeException {
//		try {
//			NotificacionDocumento notificacionDocumento = new NotificacionDocumento();
//			notificacionDocumento.setDocument(documentoPrincipal);
//			notificacionDocumento.setNotificationSendDate(new Date());
//			notificacionDocumento.setNotificationSendUser(usuarioSolicitanteId);
//			notificacionDocumento.setWsdl(wsdl);
//			notificacionDocumento.setNotificationType(new Integer(
//					ExpedientesFacadeBeanHelper.getNotificationTypeDocumentoExpediente(documentoPrincipal.getDocumentType())).toString());
//			notificacionDocumento = em.merge(notificacionDocumento);
//			if (null != adjuntos && !adjuntos.isEmpty()) {
//				notificacionDocumento.setAttachments(new ArrayList<AdjuntoNotificacionExpediente>());
//				for (es.apt.ae.facade.entities.Documento adjunto:adjuntos) {
//					AdjuntoNotificacionExpediente notifDocAdjunto = new AdjuntoNotificacionExpediente();
//					notifDocAdjunto.setDocument(adjunto);
//					notifDocAdjunto.setNotificacion(notificacionDocumento);
//					notificacionDocumento.getAttachments().add(em.merge(notifDocAdjunto));
//				}
//			}
//			
//			return notificacionDocumento;
//		} catch(Exception e) {
//			throw new ExpedientesFacadeException(e);
//		}
//	}
//	
//	public Map<String, ContadorRequerimientosSede> getContadorRequerimientosByExpediente(List<String> adminFileIds) {
//		List<ContadorRequerimientoExpediente> result = EntitiesUtils.getNumeroRequerimientosByExpediente(em, adminFileIds);
//		Map<String, ContadorRequerimientosSede> mapResult = new HashMap<String, ContadorRequerimientosSede>();
//		
//		for (ContadorRequerimientoExpediente item : result) {
//			ContadorRequerimientosSede contador = new ContadorRequerimientosSede();
//			contador.setResueltos(item.getRespondidos());
//			contador.setTotales(item.getTotal());
//			mapResult.put(item.getIdExpediente(), contador);
//		}
//		return mapResult;
//	}
//	
//	public void saveInfoNotifManualDocumento(NotificacionPapelDocumento notifDocumento, InfoNotifDocumento infoNotifDocWS) 
//				throws ExpedientesFacadeException {
//		try {
//			if (infoNotifDocWS.getFechaPrimerIntento() != null) { // Comunicacion y Notificacion
//				notifDocumento.setFechaPrimerIntento(DateUtils.xmlGregorianCalendarToDate(infoNotifDocWS.getFechaPrimerIntento()));
//			}
//			if (notifDocumento.getTipoNotificacion() == 3) { // Notificacion
//				if (infoNotifDocWS.getResultadoPrimerIntento() != null) {
//					notifDocumento.setResultadoPrimerIntento(NotificacionPapelDocumento.NOTIFICACION_PAPEL_RESULTADOS.get(infoNotifDocWS.getResultadoPrimerIntento().ordinal()));
//				}
//				if (infoNotifDocWS.getFechaSegundoIntento() != null) {
//					notifDocumento.setFechaSegundoIntento(DateUtils.xmlGregorianCalendarToDate(infoNotifDocWS.getFechaSegundoIntento()));
//				}
//				if (infoNotifDocWS.getResultadoSegundoIntento() != null) {
//					notifDocumento.setResultadoSegundoIntento(NotificacionPapelDocumento.NOTIFICACION_PAPEL_RESULTADOS.get(infoNotifDocWS.getResultadoSegundoIntento().ordinal()));
//				}
//				if (infoNotifDocWS.getFechaPublicacion() != null) {
//					notifDocumento.setFechaPublicacion(DateUtils.xmlGregorianCalendarToDate(infoNotifDocWS.getFechaPublicacion()));
//				}
//			}
//			
//			// Guardar en BD.
//			if (notifDocumento.getId() == null) {
//				em.persist(notifDocumento);
//			} else {
//				em.merge(notifDocumento);
//			}
//		} catch(Exception e) {
//			throw new ExpedientesFacadeException(e);
//		}
//	}
//	
//	/**
//	 * Traduce de las propiedades de un nodo, adaptado para SIDOPU, a la estructura de datos que habla la fachada de WS.
//	 * @param parameters Parametros que se pasarÃ¡n a la API Alfresco-OpenCanarias
//	 * @param propiedades Propiedades de un nodo, adaptado para SIDOPU
//	 * @throws DatatypeConfigurationException 
//	 */
//	public String mapearMetadatosADocumento (List<Metadato> metadatos, es.apt.ae.facade.ws.params.expedientes.out.busqueda.Documento doc) throws Exception {
//		StringBuilder errores = new StringBuilder();
//		
//		try {
//			IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
//
//			// Transformamos la lista de Metadatos a un HashMap para optimizar las busquedas.
//			HashMap <String, List<String>> metadatosMap = new HashMap<String, List<String>>();
//			for (Metadato metadato: metadatos) {
//				metadatosMap.put(metadato.getNombre(), metadato.getValor());
//			}
//			
//			List<String> metadataDescripcionList = (metadatosMap.get(ars.getPropertyNameInAPI(ConstantesMetadatos.DESCRIPTION)));
//			if (metadataDescripcionList != null && !metadataDescripcionList.isEmpty()) {
//				doc.setDescripcion(metadataDescripcionList.get(0));
//			}
//			List<String> metadataNombreList = (metadatosMap.get(ars.getPropertyNameInAPI(ConstantesMetadatos.NAME)));
//			if (metadataNombreList != null && !metadataNombreList.isEmpty()) {
//				doc.setNombre(metadataNombreList.get(0));
//			}
//			List<String> metadataTipoList = (metadatosMap.get(ars.getPropertyNameInAPI(ConstantesMetadatos.DOC_TIPODOCUMENTAL)));
//			if (metadataTipoList != null && !metadataTipoList.isEmpty()) {
//				// Nos quedamos solo con el id pues puede ocurrir que el tipo venga de la manera "ID_TIPO - DESC_TIPO";
//				String metadataTipo = metadataTipoList.get(0).split("\\-")[0].trim();
//				doc.setTipo(metadataTipo);
//			}
//			// Se extrae el tipo mime del metadato content, que es donde el gestor documental lo almacena.
//			List<String> metadataContentList = (metadatosMap.get(ars.getPropertyNameInAPI(ConstantesMetadatos.MIMETYPE)));
//			if (metadataContentList != null && !metadataContentList.isEmpty()) {
//				doc.setContenType(metadataContentList.get(0));
//			}
//			// Fecha de captura del documento
//			List<String> metadataFechaCapturaList = (metadatosMap.get(ars.getPropertyNameInAPI(ConstantesMetadatos.DOC_FECHACAPTURA)));
//			if (metadataFechaCapturaList != null && !metadataFechaCapturaList.isEmpty()) {
//				String fecha = metadataFechaCapturaList.get(0); 
//				try {
//					doc.setFechaAportacion(DateUtils.stringToXMLGregorianCalendar(fecha, DateUtils.xmlGCPattern));
//				} catch (Exception e) {
//					logger.warn("[SRV-EXP] Se ha producido un error al intentar parsear la fecha [" + fecha + "] del documento [" + doc.getUri() + "]");
//				}
//			}
//		} catch (Exception e) {
//			logger.warn("[SRV-EXP] Se ha poducido un error al intentar extraer la informacion de los metadatos para el documento [" + doc.getUri() + "]");
//			errores.append("Se ha poducido un error al intentar extraer la informacion de los metadatos para el documento [" + doc.getUri() + "]" + "\n");
//		}
//		
//		// Informacion de BD
//		Documento docBD = EntitiesUtils.getDocumentoExpedienteByUri(em, doc.getUri());
//		if (docBD != null) {
//			String numExpediente = docBD.getPk().getExpediente();
//			String docNombre = docBD.getPk().getId();
//
//			// Registro de ES
//			if (docBD.getRecordNumber() != null && !docBD.getRecordNumber().trim().equals("")) {
//				try {
//					DocumentoDatosRegistro datosRegistro = new DocumentoDatosRegistro();
//					datosRegistro.setNumero(docBD.getRecordNumber());					
//					try {
//						datosRegistro.setFecha(DateUtils.dateToXMLGregorianCalendar(docBD.getRecordDate()));
//					} catch (DatatypeConfigurationException e) {
//						logger.error("[SRV-EXP] Error al convertir la fecha de registro a XMLGregorianCalendar para el documento: " + doc.getUri());
//					}
//					if (docBD.getRecordType() != null) {
//						if (docBD.getRecordType().equals("exit")) {
//							datosRegistro.setTipo("SALIDA");
//						} else if (docBD.getRecordType().equals("entry")) {
//							datosRegistro.setTipo("ENTRADA");
//						}
//					}
//					doc.setDatosRegistro(datosRegistro);
//				} catch (Exception e) {
//					logger.warn("[SRV-EXP] Se ha poducido un error al intentar extraer la informacion de registro para el documento [" + doc.getUri() + "]");
//					errores.append("Se ha poducido un error al intentar extraer la informacion de registro para el documento [" + doc.getUri() + "]" + "\n");
//				}
//			}
//			// Tramite en el que se adjunto el documento al expediente (si proviene de un asiento de entrada no hay tramite asignado).
//			if (docBD.getActivity() != null && !docBD.getActivity().trim().equals("")) {
//				try {
//					Expediente expBD = getExpedienteById(numExpediente, null);
//					Actividad actividad = getActividadById(docBD.getActivity(), expBD.getIdWf(), expBD.getWorkflowVersion());
//					doc.setTramite(actividad.getDescripcion());
//				} catch (Exception e) {
//					logger.warn("[SRV-EXP] Se ha poducido un error al intentar extraer el tramite en el que se adjunto el documento [" + doc.getUri() + "]");
//					errores.append("Se ha poducido un error al intentar extraer el tramite en el que se adjunto el documento [" + doc.getUri() + "]" + "\n");
//				}
//			}
//			// Informacion de firma
//			DocumentoDatosFirma datosFirma = new DocumentoDatosFirma();
//			datosFirma.setEstado((docBD.getState() != null)?docBD.getState().toUpperCase():ExpedientesConstants.DOC_ESTADO_SIN_FIRMA.toUpperCase());
//			datosFirma.setFecha(null);
//			doc.setDatosFirma(datosFirma);			
//			// Informacion de notificacion
//			DocumentoDatosNotificacion datosNotificacion = new DocumentoDatosNotificacion();
//			Map<String, EstadoNotificacionDocumento> estadosDocsNotificados = getEstadosDocumentosNotificados(numExpediente);
//			datosNotificacion.setEstado(ExpedientesConstants.DOC_ESTADO_SIN_NOTIFICAR);
//			datosNotificacion.setFecha(null);
//			if (estadosDocsNotificados != null && estadosDocsNotificados.containsKey(docNombre)) {
//				EstadoNotificacionDocumento estadoNotif = estadosDocsNotificados.get(docNombre);
//				datosNotificacion.setEstado(estadoNotif.getEstado());
//				datosNotificacion.setFecha(DateUtils.dateToXMLGregorianCalendar(estadoNotif.getFecha()));
//			}
//			doc.setDatosNotificacion(datosNotificacion);
//			// Actuacion a la que pertenece el documento si procede.
//			doc.setActuacion(docBD.getActuacionId());	
//			// Num de expediente
//			doc.setNumeroExpediente(docBD.getPk().getExpediente());
//		} else {
//			logger.warn("[SRV-EXP] Se ha poducido un error al intentar extraer la informacion de base de datos para el documento [" + doc.getUri() + "]");
//			errores.append("Se ha poducido un error al intentar extraer la informacion  de base de datos para el documento [" + doc.getUri() + "]" + "\n");
//		}
//
//		return errores.toString();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<TareaBandejaItem> consultarTareasFiltros(FiltrosBandejaTareas filtros) throws NoResultException {
//		List<TareaBandejaItem> tareasExpsItemsTmpList = new ArrayList<TareaBandejaItem>();
//		HashMap<String, List<Integer>> numExpedientesMap = new HashMap<String, List<Integer>>();
//		
//		String query = prepararConsultaTareas(filtros);
//		Query q = em.createNativeQuery(query);
//		List<Object[]> results = q.getResultList();
//		
//		if (results != null && !results.isEmpty()) {
//			HashMap<String, Procedimiento> procedimientosMap = EntitiesUtils.getProcedimientosMap(em);
//			HashMap<String, HashMap<String, Object>> usuariosInfoMap = null;
//			try {
//				usuariosInfoMap = AuthenticationHelper.getUsersAE(null);
//			} catch (Exception e) {
//				logger.warn("Error al recuperar el listado de usuarios de AE del LDAP", e);
//			}
//			int pos = 0;
//			for (Object[] objects : results) {
//				int i = 0;
//				String numExpediente = (String) objects[i++];
//				String asuntoExpediente = (String) objects[i++];
//				Date fechaCreacionExp = (Date) objects[i++];
//				Date fechaInicioExp = (Date) objects[i++];
//				Long numDiasActivoL = DateUtils.getDiasTranscurridos(fechaCreacionExp);
//				Integer numDiasActivo = (numDiasActivoL == null?null:numDiasActivoL.intValue());
//				String procId = (String) objects[i++];
//				String procDesc = ((procedimientosMap!=null && procedimientosMap.containsKey(procId))?procedimientosMap.get(procId).getDescripcion():null);
//				String nombreInteresado = (String) objects[i++];
//				String numRegEnt = (String) objects[i++];
//				Short origen = StringUtils.isNullOrEmpty(numRegEnt)?new Short("0"):new Short("1");
//				String tramiteId = (String) objects[i++];
//				String tramiteDesc = (String) objects[i++];
//				Date fechaInicioTarea = (Date) objects[i++];
//				Long numDiasTramiteActualL = DateUtils.getDiasTranscurridos(fechaInicioTarea);
//				Integer numDiasTramiteActual = (numDiasTramiteActualL == null?null:numDiasTramiteActualL.intValue());
//				String procesoId = (String) objects[i++];
//				String instancia = (String) objects[i++];
//				String tramitadorId = (String) objects[i++];
//				String tramitadorNombreYApellidos = ((!StringUtils.isNullOrEmpty(tramitadorId) && usuariosInfoMap!=null && usuariosInfoMap.containsKey(tramitadorId))?
//						(String)usuariosInfoMap.get(tramitadorId).get(LoginModuleConstants.PRINCIPAL_NOMBRE):tramitadorId);
//				String departamentoId = (String) objects[i++];
//
//		        TareaBandejaItem tareaExpItem = new TareaBandejaItem(numExpediente, asuntoExpediente, fechaCreacionExp, fechaInicioExp, procId, procDesc, 
//		        	nombreInteresado, numRegEnt, tramiteId, tramiteDesc, fechaInicioTarea, procesoId, instancia, departamentoId, tramitadorId, 
//		        	tramitadorNombreYApellidos, numDiasActivo, numDiasTramiteActual, origen);
//		        tareasExpsItemsTmpList.add(tareaExpItem);
//		        List<Integer> posiciones = null;
//		        if (!numExpedientesMap.containsKey(numExpediente)) {
//		        	 posiciones = new ArrayList<Integer>();
//		        } else {
//		        	posiciones = numExpedientesMap.get(numExpediente);
//		        }
//		        posiciones.add(pos);
//		        numExpedientesMap.put(numExpediente, posiciones);
//		        pos++;
//			}
//		}
//		
//		return getTareasFiltradasPorPeticiones(filtros, numExpedientesMap, tareasExpsItemsTmpList);
//	}
//	
//	
//	private String prepararConsultaTareas(FiltrosBandejaTareas filtros) {
//		StringBuilder filtrosBuilder = new StringBuilder();
//
//		filtrosBuilder.append("SELECT af.id_, af.asunto_, af.creation_date_, af.start_date_");
//		filtrosBuilder.append(", af.pending_workflow_id_, af.interesado_, af.record_number_, art.task_def_key_");
//		filtrosBuilder.append(", art.name_, art.create_time_, art.proc_def_id_, art.proc_inst_id_, art.assignee_, aril.group_id_");
//		filtrosBuilder.append(FROM);
//		filtrosBuilder.append("OC3F.administrative_file_ af, EAACTIVITI.act_ru_task art, EAACTIVITI.act_ru_identitylink aril");
//		filtrosBuilder.append(WHERE);
//		filtrosBuilder.append("af.wf_instance_ = art.proc_inst_id_");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("art.id_ = aril.task_id_");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("aril.group_id_ IN (");
//		for (int i = 0; i < filtros.getListDepartamentos().size(); i++) {
//			String dptoId = filtros.getListDepartamentos().get(i);
//			if (i > 0) {
//				filtrosBuilder.append(", ");
//			}
//			filtrosBuilder.append("'" + dptoId + "'");
//		}
//		filtrosBuilder.append(")");
//		if ((FiltrosBandejaTareas.BANDEJA_MIS_TAREAS.equals(filtros.getTipoBandeja()) || FiltrosBandejaTareas.BANDEJAS_TAREAS_DPTO.equals(filtros.getTipoBandeja())) 
//				&& !StringUtils.isNullOrEmpty(filtros.getTramitadorId())) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("art.assignee_ = ");
//			filtrosBuilder.append("'" + filtros.getTramitadorId() + "'");
//		}
//		if (FiltrosBandejaTareas.BANDEJA_TAREAS_PENDIENTES.equals(filtros.getTipoBandeja()) || 
//				(FiltrosBandejaTareas.BANDEJAS_TAREAS_DPTO.equals(filtros.getTipoBandeja())) && FiltrosBandejaTareas.ESTADO_TAREAS_PENDIENTES.equals(filtros.getEstadoTarea())) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("art.assignee_ IS NULL");
//		} 
//		if (FiltrosBandejaTareas.BANDEJAS_TAREAS_DPTO.equals(filtros.getTipoBandeja()) && FiltrosBandejaTareas.ESTADO_TAREAS_ASIGNADAS.equals(filtros.getEstadoTarea())) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("art.assignee_ IS NOT NULL");
//		}
//		if (!StringUtils.isNullOrEmpty(filtros.getInstancia())) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("art.proc_inst_id_ = ");
//			filtrosBuilder.append("'"+filtros.getInstancia()+"'");
//		}		
//		if (!StringUtils.isNullOrEmpty(filtros.getTramiteId())) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("art.task_def_key_ = ");
//			filtrosBuilder.append("'"+filtros.getTramiteId()+"'");
//		}
//		if (!StringUtils.isNullOrEmpty(filtros.getTramiteDesc())) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("UPPER(art.name_) LIKE ");
//			filtrosBuilder.append("'%"+filtros.getTramiteDesc().toUpperCase()+"%'");
//		}
//		
//		// FILTROS DE EXPEDIENTES.
//			// Sólo expedientes activos.
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("af.status_ = 0");
//			// Sólo expediente de Activiti
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("af.wf_id_ like '%:%'");
//		aniadirCondicionesExpedientes(filtrosBuilder, filtros);
//
//		aniadirOrdenacion(filtrosBuilder, "af.creation_date_");
//
//		
//		return filtrosBuilder.toString();		
//	}
//	
//	private List<TareaBandejaItem> getTareasFiltradasPorPeticiones(FiltrosBandejaTareas filtros, HashMap<String, List<Integer>> numExpedientesMap, 
//			List<TareaBandejaItem> tareasExpsItemsTmpList) {
//		
//		List<TareaBandejaItem> tareasExpsItemsList = new ArrayList<TareaBandejaItem>();
//
//		if (!numExpedientesMap.isEmpty()) {
//			tareasExpsItemsList.addAll(tareasExpsItemsTmpList);
//			List<String> numExpedientesList = new ArrayList<String>(numExpedientesMap.keySet());
//			HashMap<String, Integer> numPeticionesPdtesExpsMap = new HashMap<String, Integer>();
//			HashMap<String, Boolean> peticionesFinalizadasRangoExpsMap = new HashMap<String, Boolean>();
//					
//			Boolean comprobarFinalizadas = filtros.getPeticionesFinalizadas();
//			Date peticionesFinalizadasDesde = null;
//			Date peticionesFinalizadasHasta = null;
//			if (Boolean.TRUE.equals(comprobarFinalizadas)) {
//				peticionesFinalizadasDesde = filtros.getPeticionesFinalizadasDesde();
//				peticionesFinalizadasHasta = filtros.getPeticionesFinalizadasHasta();
//			}
//			
//			// PETICIONES PORTAFIRMAS
//			HashMap<String, PeticionesItem> peticionesPortafirmasMap = EntitiesUtils.getPeticionesPortafirmas(em, numExpedientesList,
//					comprobarFinalizadas, peticionesFinalizadasDesde, peticionesFinalizadasHasta, false);
//			if (peticionesPortafirmasMap != null && !peticionesPortafirmasMap.isEmpty()) {
//				Iterator<Entry<String, PeticionesItem>> it = peticionesPortafirmasMap.entrySet().iterator();
//				while (it.hasNext()) {
//					Entry<String, PeticionesItem> entry = it.next();
//					String numExpediente = entry.getKey();
//					PeticionesItem peticionesPortafirmas = entry.getValue();
//					if (FiltrosBandejaTareas.PETICIONES_PENDIENTES_NINGUNA.equals(filtros.getPeticionesPendientes()) &&
//							peticionesPortafirmas.getNumPeticionesPendientes() > 0) {
//						numExpedientesList.remove(numExpediente);
//						for (Integer index:numExpedientesMap.get(numExpediente)) {
//							tareasExpsItemsList.remove(tareasExpsItemsTmpList.get(index));
//						}
//					} else {
//						if (peticionesPortafirmas.getNumPeticionesTotal() > 0) {
//							for (Integer index:numExpedientesMap.get(numExpediente)) {
//								int indexFinalList = tareasExpsItemsList.indexOf(tareasExpsItemsTmpList.get(index));
//								tareasExpsItemsList.get(indexFinalList).setPeticionesPortafirmas(peticionesPortafirmas);
//							}
//						}
//					}
//					int numPeticionesPdtesExp = 0;
//					if (numPeticionesPdtesExpsMap.containsKey(numExpediente)) {
//						numPeticionesPdtesExp = numPeticionesPdtesExpsMap.get(numExpediente);
//					}
//					numPeticionesPdtesExp += peticionesPortafirmas.getNumPeticionesPendientes();
//					numPeticionesPdtesExpsMap.put(numExpediente, numPeticionesPdtesExp);
//					if (!peticionesFinalizadasRangoExpsMap.containsKey(numExpediente) || !Boolean.TRUE.equals(peticionesFinalizadasRangoExpsMap.get(numExpediente))) {
//						peticionesFinalizadasRangoExpsMap.put(numExpediente, peticionesPortafirmas.getPeticionesFinalizadasRango());
//					}
//				}
//			}
//
//			// PETICIONES ACTUACIONES
//			HashMap<String, PeticionesItem> peticionesActuacionesMap = EntitiesUtils.getPeticionesActuaciones(em, numExpedientesList,
//					comprobarFinalizadas, peticionesFinalizadasDesde, peticionesFinalizadasHasta);
//			if (peticionesActuacionesMap != null && !peticionesActuacionesMap.isEmpty()) {
//				Iterator<Entry<String, PeticionesItem>> it = peticionesActuacionesMap.entrySet().iterator();
//				while (it.hasNext()) {
//					Entry<String, PeticionesItem> entry = it.next();
//					String numExpediente = entry.getKey();
//					PeticionesItem peticionesActuaciones = entry.getValue();
//					if (FiltrosBandejaTareas.PETICIONES_PENDIENTES_NINGUNA.equals(filtros.getPeticionesPendientes()) &&
//							peticionesActuaciones.getNumPeticionesPendientes() > 0) {
//						numExpedientesList.remove(numExpediente);
//						for (Integer index:numExpedientesMap.get(numExpediente)) {
//							tareasExpsItemsList.remove(tareasExpsItemsTmpList.get(index));
//						}
//					} else {
//						if (peticionesActuaciones.getNumPeticionesTotal() > 0) {
//							for (Integer index:numExpedientesMap.get(numExpediente)) {
//								int indexFinalList = tareasExpsItemsList.indexOf(tareasExpsItemsTmpList.get(index));
//								tareasExpsItemsList.get(indexFinalList).setPeticionesActuaciones(peticionesActuaciones);
//							}
//						}
//					}
//					int numPeticionesPdtesExp = 0;
//					if (numPeticionesPdtesExpsMap.containsKey(numExpediente)) {
//						numPeticionesPdtesExp = numPeticionesPdtesExpsMap.get(numExpediente);
//					}
//					numPeticionesPdtesExp += peticionesActuaciones.getNumPeticionesPendientes();
//					numPeticionesPdtesExpsMap.put(numExpediente, numPeticionesPdtesExp);
//					if (!peticionesFinalizadasRangoExpsMap.containsKey(numExpediente) || !Boolean.TRUE.equals(peticionesFinalizadasRangoExpsMap.get(numExpediente))) {
//						peticionesFinalizadasRangoExpsMap.put(numExpediente, peticionesActuaciones.getPeticionesFinalizadasRango());
//					}
//				}
//			}
//			
//			// PETICIONES REQUERIMIENTOS
//			HashMap<String, PeticionesItem> peticionesRequerimientosMap = EntitiesUtils.getPeticionesRequerimientos(em, numExpedientesList,
//					comprobarFinalizadas, peticionesFinalizadasDesde, peticionesFinalizadasHasta);
//			if (peticionesRequerimientosMap != null && !peticionesRequerimientosMap.isEmpty()) {
//				Iterator<Entry<String, PeticionesItem>> it = peticionesRequerimientosMap.entrySet().iterator();
//				while (it.hasNext()) {
//					Entry<String, PeticionesItem> entry = it.next();
//					String numExpediente = entry.getKey();
//					PeticionesItem peticionesRequerimientos = entry.getValue();
//					if (FiltrosBandejaTareas.PETICIONES_PENDIENTES_NINGUNA.equals(filtros.getPeticionesPendientes()) &&
//							peticionesRequerimientos.getNumPeticionesPendientes() > 0) {
//						numExpedientesList.remove(numExpediente);
//						for (Integer index:numExpedientesMap.get(numExpediente)) {
//							tareasExpsItemsList.remove(tareasExpsItemsTmpList.get(index));
//						}
//					} else {
//						if (peticionesRequerimientos.getNumPeticionesTotal() > 0) {
//							for (Integer index:numExpedientesMap.get(numExpediente)) {
//								int indexFinalList = tareasExpsItemsList.indexOf(tareasExpsItemsTmpList.get(index));
//								tareasExpsItemsList.get(indexFinalList).setPeticionesRequerimientos(peticionesRequerimientos);
//							}
//						}
//					}
//					int numPeticionesPdtesExp = 0;
//					if (numPeticionesPdtesExpsMap.containsKey(numExpediente)) {
//						numPeticionesPdtesExp = numPeticionesPdtesExpsMap.get(numExpediente);
//					}
//					numPeticionesPdtesExp += peticionesRequerimientos.getNumPeticionesPendientes();
//					numPeticionesPdtesExpsMap.put(numExpediente, numPeticionesPdtesExp);
//					if (!peticionesFinalizadasRangoExpsMap.containsKey(numExpediente) || !Boolean.TRUE.equals(peticionesFinalizadasRangoExpsMap.get(numExpediente))) {
//						peticionesFinalizadasRangoExpsMap.put(numExpediente, peticionesRequerimientos.getPeticionesFinalizadasRango());
//					}					
//				}
//			}	
//			
//			if (FiltrosBandejaTareas.PETICIONES_PENDIENTES_ALGUNA.equals(filtros.getPeticionesPendientes()) || Boolean.TRUE.equals(comprobarFinalizadas)) {
//				List<TareaBandejaItem> tareasToRemoveList = new ArrayList<TareaBandejaItem>();
//				for (TareaBandejaItem tareaExpItem:tareasExpsItemsList) {
//					String numExpediente = tareaExpItem.getNumExpediente();
//					if (FiltrosBandejaTareas.PETICIONES_PENDIENTES_ALGUNA.equals(filtros.getPeticionesPendientes()) && 
//							(!numPeticionesPdtesExpsMap.containsKey(numExpediente) || numPeticionesPdtesExpsMap.get(numExpediente) == 0)) {
//						tareasToRemoveList.add(tareaExpItem);
//					} else if (Boolean.TRUE.equals(comprobarFinalizadas) && 
//							(!numPeticionesPdtesExpsMap.containsKey(numExpediente) || (!Boolean.TRUE.equals(peticionesFinalizadasRangoExpsMap.get(numExpediente))))) {
//						tareasToRemoveList.add(tareaExpItem);
//					}
//				}
//				tareasExpsItemsList.removeAll(tareasToRemoveList);
//			}
//			
//		}
//		
//		return tareasExpsItemsList;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<DocumentoBandejaItem> consultarDocumentosTareasFiltros(FiltrosBandejaDocumentos filtros) throws NoResultException {
//		Map<String, DocumentoBandejaItem> docsBandejaItemsMap = new LinkedHashMap<String, DocumentoBandejaItem>();
//		
//		String query = prepararConsultaDocumentosTareas(filtros);
//		Query q = em.createNativeQuery(query);
//		List<Object[]> results = q.getResultList();
//		List<String> docsUris = new ArrayList<String>();
//		List<String> docsPortafirmasUris = new ArrayList<String>();
//		
//		if (results != null && !results.isEmpty()) {
//			HashMap<String, Procedimiento> procedimientosMap = EntitiesUtils.getProcedimientosMap(em);
//			HashMap<String, String> tiposDocumentosMap = EntitiesUtils.getTiposDocumentos(em);
//			for (Object[] objects : results) {
//				int i = 0;
//				String uriDoc = (String) objects[i++];
//				docsUris.add(uriDoc);
//				String nombreDoc = (String) objects[i++];
//				String descripcionDoc = (String) objects[i++];
//				String tipoDocId = (String) objects[i++];
//				String tipoDocDesc = ((tiposDocumentosMap!=null && tiposDocumentosMap.containsKey(tipoDocId))?tiposDocumentosMap.get(tipoDocId):null);
//				String origenDoc = (String) objects[i++];
//				String tipoContenidoDoc = (String) objects[i++];
//				Date fechaElaboracionDoc = (Date) objects[i++];
//				String uriDocOriginal = (String) objects[i++];
//				Double escalaDoc = ((BigDecimal) objects[i++]).doubleValue();
//				Boolean invertirMarcaAguaDoc = ((((BigDecimal) objects[i++]).intValue()==1)?Boolean.TRUE:Boolean.FALSE);
//				Integer rotacionDoc = ((BigDecimal) objects[i++]).intValue();
//				String csvDoc = (String) objects[i++];
//				String estadoFirmaDocDesc = (String) objects[i++];
//				if (StringUtils.isNullOrEmpty(estadoFirmaDocDesc)) {
//					estadoFirmaDocDesc = ExpedientesConstants.DOC_ESTADO_SIN_FIRMA;
//				}
//				if (ExpedientesConstants.DOC_ESTADO_PENDIENTE_PORTAFIRMAS.equals(estadoFirmaDocDesc) || 
//						ExpedientesConstants.DOC_ESTADO_FIRMADO_PORTAFIRMAS.equals(estadoFirmaDocDesc) ||
//						ExpedientesConstants.DOC_ESTADO_RECHAZADO_PORTAFIRMAS.equals(estadoFirmaDocDesc)) {
//					docsPortafirmasUris.add(uriDoc);
//				}
//				String numRegDoc = (String) objects[i++];
//				Date fechaRegDoc = (Date) objects[i++];
//				String numExpediente = (String) objects[i++];
//				String asuntoExpediente = (String) objects[i++];
//				Date fechaCreacionExp = (Date) objects[i++];
//				Date fechaInicioExp = (Date) objects[i++];
//				String procId = (String) objects[i++];
//				String procDesc = ((procedimientosMap!=null && procedimientosMap.containsKey(procId))?procedimientosMap.get(procId).getDescripcion():null);
//				String canalNotifPreferente = (String) objects[i++];
//				String instancia = (String) objects[i++];
//				String tramiteId = (String) objects[i++];
//				String departamentoId = (String) objects[i++];
//				String estadoNotificacion = ExpedientesConstants.DOC_ESTADO_PLAE_SIN_NOTIFICACION;
//				Short estadoNotificacionId = (short)ExpedientesConstants.DOC_ESTADOS_NOTIFICACION_PLAE.indexOf(estadoNotificacion);
//				EstadoNotificacionDocumento estadoNotificacionDoc = new EstadoNotificacionDocumento(estadoNotificacionId, estadoNotificacion, null, null, null);
//				Boolean notificacionesActividad = ((((BigDecimal) objects[i++]).intValue()==1)?Boolean.TRUE:Boolean.FALSE);
//				Boolean notifElectronicasHabilitadas = ExpedientesConstants.EXP_TIPO_NOTIFICACION_TELEMATICA.equals(canalNotifPreferente) && notificacionesActividad;
//
//				DocumentoBandejaItem docBandejaItem = new DocumentoBandejaItem(numExpediente, asuntoExpediente, fechaCreacionExp, fechaInicioExp, 
//		        		procId, procDesc, instancia, tramiteId, departamentoId, nombreDoc, descripcionDoc, tipoDocId, tipoDocDesc, origenDoc, tipoContenidoDoc,
//		        		fechaElaboracionDoc, uriDoc, uriDocOriginal, escalaDoc, invertirMarcaAguaDoc, rotacionDoc, csvDoc, Boolean.FALSE, estadoFirmaDocDesc, numRegDoc, 
//		        		fechaRegDoc, estadoNotificacionDoc, notifElectronicasHabilitadas);
//				docsBandejaItemsMap.put(uriDoc, docBandejaItem);
//			}
//
//			try {
//				// DETERMINAR SI LOS DOCUMENTOS SE GESTIONARON MEDIANTE EL PORTAFIRMAS CORPORATIVO
//				if (!docsPortafirmasUris.isEmpty()) {
//					HashMap<String, Boolean> documentosPortafirmasMap = EntitiesUtils.getDocumentosPortafirmas(em, docsPortafirmasUris);
//					Iterator<Entry<String, Boolean>> it1 = documentosPortafirmasMap.entrySet().iterator();
//					while (it1.hasNext()) {
//						Entry<String, Boolean> entry = it1.next();
//						String docUri = entry.getKey();
//						Boolean pfCorporativo = entry.getValue();
//						DocumentoBandejaItem docBandejaItem = docsBandejaItemsMap.get(docUri);
//						docBandejaItem.setPfCorporativoDoc(pfCorporativo);
//					}
//				}
//			} catch (Exception e) {
//				logger.warn("No se pudo obtener la informacion de documentos tramitados con el Portafirmas antiguo.", e);
//			}
//			
//			// FILTRO Y RECUPERACION DE ESTADO DE NOTIFICACION
//			HashMap<String, EstadoNotificacionDocumento> estadosNotificacionDocsMap = EntitiesUtils.getEstadosNotificacionDocumentos(em, docsUris);
//			List<String> docsUrisConEstadoNotif = new ArrayList<String>(estadosNotificacionDocsMap.keySet());
//			Short filtroEstadoNotificacion = filtros.getEstadoNotificacionDoc();
//			// Se eliminan los documentos con estado "Sin notificación".
//			if (filtroEstadoNotificacion != null && !FiltrosBandejaDocumentos.ESTADO_DOC_SIN_NOTIFICACION.equals(filtroEstadoNotificacion)) {
//				List<String> docsUrisSinEstadoNotif = new ArrayList<String>(docsUris);
//				docsUrisSinEstadoNotif.removeAll(docsUrisConEstadoNotif);
//				for (String docUri:docsUrisSinEstadoNotif) {
//					docsBandejaItemsMap.remove(docUri);
//				}
//			}
//			Iterator<Entry<String, EstadoNotificacionDocumento>> it2 = estadosNotificacionDocsMap.entrySet().iterator();
//			while (it2.hasNext()) {
//				Entry<String, EstadoNotificacionDocumento> entry = it2.next();
//				String docUri = entry.getKey();
//				EstadoNotificacionDocumento estadoNotifDoc = entry.getValue();
//				Short estadoNotifDocId = estadoNotifDoc.getEstadoId();
//				if (filtroEstadoNotificacion != null && !filtroEstadoNotificacion.equals(estadoNotifDocId)) {
//					docsBandejaItemsMap.remove(docUri);
//				} else {
//					DocumentoBandejaItem docBandejaItem = docsBandejaItemsMap.get(docUri);
//					docBandejaItem.setEstadoNotificacionDoc(estadoNotifDoc);
//				}
//			}
//			
//		}
//		
//		return new ArrayList<DocumentoBandejaItem>(docsBandejaItemsMap.values());
//	}
//	
//	private String prepararConsultaDocumentosTareas(FiltrosBandejaDocumentos filtros) {
//		StringBuilder filtrosBuilder = new StringBuilder();
//
//		filtrosBuilder.append("SELECT distinct(d.repository_uri_), d.id_ as doc_id, d.description_, d.document_type, d.type_, d.content_type_");
//		filtrosBuilder.append(", d.elaboration_date_, d.doc_original_uri, d.scale_, d.invert_watermark_, d.rotation_, d.sign_reference_, d.state_");
//		filtrosBuilder.append(", d.record_number_, d.record_date_");		
//		filtrosBuilder.append(", af.id_, af.asunto_, af.creation_date_, af.start_date_, af.pending_workflow_id_, af.notification_type_");
//		filtrosBuilder.append(", art.proc_inst_id_, art.task_def_key_, aril.group_id_");
//		filtrosBuilder.append(", ac.notifications_");
//		filtrosBuilder.append(FROM);
//		filtrosBuilder.append("OC3F.administrative_file_ af, EAACTIVITI.act_ru_task art, EAACTIVITI.act_ru_identitylink aril");
//		filtrosBuilder.append(", OC3F.document_ d, OC3F.activity_ ac, OC3F.actuacion act");
//		filtrosBuilder.append(WHERE);
//		filtrosBuilder.append("af.wf_instance_ = art.proc_inst_id_");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("art.id_ = aril.task_id_");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("aril.group_id_ IN (");
//		for (int i = 0; i < filtros.getListDepartamentos().size(); i++) {
//			String dptoId = filtros.getListDepartamentos().get(i);
//			if (i > 0) {
//				filtrosBuilder.append(", ");
//			}
//			filtrosBuilder.append("'" + dptoId + "'");
//		}
//		filtrosBuilder.append(")");
//		if (!StringUtils.isNullOrEmpty(filtros.getTramitadorId())) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("art.assignee_ = ");
//			filtrosBuilder.append("'" + filtros.getTramitadorId() + "'");
//		}
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("art.task_def_key_ = ac.id_");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("af.wf_id_ = ac.wf_id_");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("af.id_ = d.administrative_file_id_");
//		
//		// FILTROS DE EXPEDIENTES.
//			// Sólo expedientes activos.
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("af.status_ = 0");
//			// Sólo expediente de Activiti
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("af.wf_id_ like '%:%'");
//		aniadirCondicionesExpedientes(filtrosBuilder, filtros);
//		aniadirCondicionesDocumentos(filtrosBuilder, filtros);
//
//		aniadirOrdenacion(filtrosBuilder, "d.elaboration_date_");
//
//		
//		return filtrosBuilder.toString();		
//	}
//	
//	private void aniadirCondicionesDocumentos(StringBuilder filtrosBuilder, FiltrosBandejaDocumentos filtros) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("d.id_ NOT LIKE ");
//		filtrosBuilder.append( "'%" + DocumentosExpedientesUtils.SIGNATURE_SUFFIX + "%'");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("(");
//		filtrosBuilder.append("d.type_ = ");
//		filtrosBuilder.append( "'" + DocumentosExpedientesUtils.DOC_TYPE_ATTACHMENT + "'");
//		filtrosBuilder.append(OR);
//		filtrosBuilder.append("d.type_ = ");
//		filtrosBuilder.append( "'" + DocumentosExpedientesUtils.DOC_TYPE_REGISTRO + "'");
//		filtrosBuilder.append(OR);
//		filtrosBuilder.append("d.type_ = ");
//		filtrosBuilder.append( "'" + DocumentosExpedientesUtils.DOC_TYPE_AUTO + "'");
//		filtrosBuilder.append(")");
//		
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("(");
//		filtrosBuilder.append("d.actuacion_id_ IS NULL");
//		filtrosBuilder.append(OR);
//		filtrosBuilder.append("(");
//		filtrosBuilder.append("d.actuacion_id_ = act.id");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("act.fecha_fin IS NOT NULL");
//		filtrosBuilder.append(")");
//		filtrosBuilder.append(")");
//
//		String separator = AND;
//
//		if (!StringUtils.isNullOrEmpty(filtros.getTipoDocId())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(d.document_type) = ");
//			filtrosBuilder.append( "'" + filtros.getTipoDocId().toUpperCase() + "'");
//			separator = AND;
//		}	
//		if (!StringUtils.isNullOrEmpty(filtros.getNumRegistroDoc())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(d.record_number_) LIKE ");
//			filtrosBuilder.append("'%"+filtros.getNumRegistroDoc().trim().toUpperCase()+"%'");
//			separator = AND;
//		}		
//		if (filtros.getFechaElaboracionDocDesde() != null){
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("TRUNC(d.elaboration_date_) >= ");
//			filtrosBuilder.append("TO_DATE('"+df.format(filtros.getFechaElaboracionDocDesde()) +"', 'YYYY-MM-DD')");
//			separator = AND;
//		}
//		if (filtros.getFechaElaboracionDocHasta() != null){
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("TRUNC(d.elaboration_date_) <= ");
//			filtrosBuilder.append("TO_DATE('"+df.format(filtros.getFechaElaboracionDocHasta())+"', 'YYYY-MM-DD')");
//			separator = AND;
//		}
//		if (filtros.getEstadoFirmaDoc() != null) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(d.state_) = ");
//			filtrosBuilder.append("'"+ExpedientesConstants.DOC_ESTADOS_FIRMA.get(filtros.getEstadoFirmaDoc().intValue()).trim().toUpperCase()+"'");
//			separator = AND;
//		}
//		if (filtros.getEstadoRegistroDoc() != null) {
//			filtrosBuilder.append(separator);
//			if (FiltrosBandejaDocumentos.ESTADO_DOC_SIN_REGISTRO.equals(filtros.getEstadoRegistroDoc())) {
//				filtrosBuilder.append("d.record_number_ IS NULL");
//			} else if (FiltrosBandejaDocumentos.ESTADO_DOC_CON_REGISTRO_SALIDA.equals(filtros.getEstadoRegistroDoc())) {
//				filtrosBuilder.append("UPPER(d.record_number_) LIKE ");
//				filtrosBuilder.append("'S-%'");
//			} else if (FiltrosBandejaDocumentos.ESTADO_DOC_CON_REGISTRO_ENTRADA.equals(filtros.getEstadoRegistroDoc())) {
//				filtrosBuilder.append("UPPER(d.record_number_) LIKE ");
//				filtrosBuilder.append("'E-%'");
//			}
//			separator = AND;
//		}
//
//	}
//
//	public List<ExpedienteConsultaItem> consultarExpedientesFiltros(FiltrosConsulta filtros) throws NoResultException, GenericFacadeException {
//		List<ExpedienteConsultaItem> expsConsultaItemsList = new ArrayList<ExpedienteConsultaItem>();
//
//		List<String> gruposUsuario = getGruposAutorizadosUsuario(filtros);
//		
//		List<Object[]> results = null;
//		
//		String entorno = ConfigUtils.getEntorno();
////		String entorno = "pro"; // TODO: COMENTAR Y DESCOMENTAR LINEA ANTERIOR.
//		// En el entorno de desarrollo no es posible cruzar con las tablas de Alfresco
//		if ("des".equalsIgnoreCase(entorno)) {
//			results = getResultadosExpedientes(filtros);
//		} else {
//			results = getResultadosExpedientesAutorizados(filtros, gruposUsuario);
//		}
//		
//		if (results != null && !results.isEmpty()) {
//			HashMap<String, Procedimiento> procedimientosMap = EntitiesUtils.getProcedimientosMap(em);
//			HashMap<String, Rol> rolesMap = EntitiesUtils.getRolesMap(em);
//			LinkedHashMap<String, ExpedienteConsultaItem> expedientesMap = new LinkedHashMap<String, ExpedienteConsultaItem>();
//			HashMap<String, HashMap<String, Object>> usuariosInfoMap = null;
//			try {
//				usuariosInfoMap = AuthenticationHelper.getUsersAE(null);
//			} catch (Exception e) {
//				logger.error("Error al recuperar el listado de usuarios de AE del LDAP", e);
//			}
//			for (Object[] objects : results) {
//				int i = 1;
//				String uriExpediente = (String) objects[i++];
//				String numExpediente = (String) objects[i++];
//				String asuntoExpediente = (String) objects[i++];
//				Short estadoExpediente = ((BigDecimal) objects[i++]).shortValue();
//				ExpedienteConsultaItem expConsultaItem = null;
//				if (!expedientesMap.containsKey(uriExpediente)) {
//					Date fechaCreacionExp = (Date) objects[i++];
//					Date fechaInicioExp = (Date) objects[i++];
//					Date fechaFinExp = (Date) objects[i++];
//					Long numDiasActivoL = DateUtils.getDiasTranscurridos(fechaCreacionExp);
//					Integer numDiasActivo = (numDiasActivoL == null?null:numDiasActivoL.intValue());
//					String procedimientoId = (String) objects[i++];
//					String procedimientoDesc = ((procedimientosMap!=null && procedimientosMap.containsKey(procedimientoId))
//							?procedimientosMap.get(procedimientoId).getDescripcion():null);
//					String idInteresado = (String) objects[i++];
//					String nombreInteresado = (String) objects[i++];
//					String numRegEnt = (String) objects[i++];
//					String procesoId = (String) objects[i++];
//					String procesoVersion = (String) objects[i++];
//					String instancia = (String) objects[i++];
//					Short origen = StringUtils.isNullOrEmpty(numRegEnt)?new Short("0"):new Short("1");
//					expConsultaItem = new ExpedienteConsultaItem(numExpediente, asuntoExpediente, estadoExpediente, fechaCreacionExp, fechaInicioExp, 
//							fechaFinExp, procesoId, procesoVersion, instancia, procedimientoId, procedimientoDesc, idInteresado, nombreInteresado, 
//							numRegEnt, new ArrayList<TareaItem>(), numDiasActivo, origen);
//					expedientesMap.put(uriExpediente, expConsultaItem);
//					expsConsultaItemsList.add(expConsultaItem);
//				} else {
//					i = 15;
//					expConsultaItem = expedientesMap.get(uriExpediente);
//				}
//				if (FiltrosConsulta.ESTADO_EXPEDIENTE_ACTIVO.equals(estadoExpediente)) {
//					String tramiteId = (String) objects[i++];
//					String tramiteDesc = (String) objects[i++];
//					Date fechaInicioTarea = (Date) objects[i++];
//					Long numDiasTramiteActualL = DateUtils.getDiasTranscurridos(fechaInicioTarea);
//					Integer numDiasTramiteActual = (numDiasTramiteActualL == null?null:numDiasTramiteActualL.intValue());
//					String tramitadorId = (String) objects[i++];
//					String tramitadorNombreYApellidos = ((!StringUtils.isNullOrEmpty(tramitadorId) && usuariosInfoMap!=null && usuariosInfoMap.containsKey(tramitadorId))?
//							(String)usuariosInfoMap.get(tramitadorId).get(LoginModuleConstants.PRINCIPAL_NOMBRE):tramitadorId);
//					String departamentoId = (String) objects[i++];
//					String departamentoDesc = ((rolesMap!=null && rolesMap.containsKey(departamentoId))?rolesMap.get(departamentoId).getDescripcion():departamentoId);
//					Short faseId = ((BigDecimal) objects[i++]).shortValue();
//					String faseDesc = (String) objects[i++];
//	
//			        TareaItem tareaItem = new TareaItem(tramiteId, tramiteDesc, fechaInicioTarea, faseId, faseDesc, departamentoId, departamentoDesc, 
//			        		tramitadorId, tramitadorNombreYApellidos, numDiasTramiteActual);
//			        expConsultaItem.getTareas().add(tareaItem);
//				}
//			}
//
//			if ("des".equalsIgnoreCase(entorno)) {
//				expsConsultaItemsList = new ArrayList<ExpedienteConsultaItem>();
//				TreeMap<Date, ExpedienteConsultaItem> expsAutorizadosOrdenados = new TreeMap<Date, ExpedienteConsultaItem>();
//				try {
//					IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
//					Parameters params = new Parameters();
//					params.setUserConsult(filtros.getUsuarioSolicitante());
//					params.setPermission(Parameters.LECTOR);
//					params.setUuids(new ArrayList<String>(expedientesMap.keySet()));
//					HashMap<String, Boolean> urisAutorizadas = ars.hasPermissions(params);
//					Iterator<Entry<String, Boolean>> it = urisAutorizadas.entrySet().iterator();
//					while (it.hasNext()) {
//						Entry<String, Boolean> entry = it.next();
//						String uri = entry.getKey();
//						Boolean autorizada = entry.getValue();
//						if (Boolean.TRUE.equals(autorizada)) {
//							ExpedienteConsultaItem expConsultaItem = expedientesMap.get(uri);
//							expsAutorizadosOrdenados.put(expConsultaItem.getFechaCreacionExpediente(), expConsultaItem);
//						}
//					}
//					expsConsultaItemsList.addAll(expsAutorizadosOrdenados.values());
//				} catch (RepositoryException e) {
//					logger.error("Error al obtener los expedientes sobre los que el usuario tiene permisos de consulta.", e);
//					throw new NoResultException("No se han podido obtener resultados porque no se han podido determinar los expedientes sobre los que el usuario tiene permisos de consulta");
//				}	
//			}
//		}
//		
//		return expsConsultaItemsList;
//	}
//	
//	private List<String> getGruposAutorizadosUsuario(FiltrosConsulta filtros) {
//		List<String> gruposUsuario = new ArrayList<String>();
//		List<DepartamentoItem> dptosAutorizadosList = filtros.getDepartamentosAutorizados();
//		if (dptosAutorizadosList == null) {
//			try {
//				IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
//				Parameters params = new Parameters();
//				params.setUserConsult(filtros.getUsuarioSolicitante());
//				String[] gruposUsuarioArr = ars.getUserGroups(params);
//				gruposUsuario = Arrays.asList(gruposUsuarioArr);
//			} catch (RepositoryException e) {
//				logger.error("Error al obtener los grupos del usuario.", e);
//				throw new NoResultException("No se han podido obtener resultados porque no se han podido determinar los grupos a los que pertenece el usuario");
//			}
//		} else {
//			for (DepartamentoItem dptItem:dptosAutorizadosList) {
//				gruposUsuario.add(AlfRepositoryConstants.GROUP_AUTHORITY_PREFIX + dptItem.getGrupoAlfresco());
//			}
//		}
//		return gruposUsuario;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private List<Object[]> getResultadosExpedientes(FiltrosConsulta filtros) {
//		StringBuilder filtrosBuilder = new StringBuilder();
//
//		if (filtros.getEstadosExpedientes() == null || filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_ACTIVO)) {
//			filtrosBuilder.append("SELECT af.notification_type_, af.repository_uri_, af.id_, af.asunto_, af.status_, af.creation_date_, af.start_date_");
//			filtrosBuilder.append(", af.end_date_, af.pending_workflow_id_, af.owner_, af.interesado_, af.record_number_, af.wf_id_, af.wf_version_, af.wf_instance_");
//			filtrosBuilder.append(", at.task_def_key_, at.name_, at.create_time_, at.assignee_, ail.group_id_, ph.id_ as phase_id, ph.description_");
//			filtrosBuilder.append(FROM);
//			filtrosBuilder.append("OC3F.administrative_file_ af, OC3F.activity_ ac, OC3F.activity_phase_ ph");
//			filtrosBuilder.append(", EAACTIVITI.act_ru_task at, EAACTIVITI.act_ru_identitylink ail");
//			filtrosBuilder.append(WHERE);
//			filtrosBuilder.append("af.wf_instance_ = at.proc_inst_id_");
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("at.id_ = ail.task_id_");
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("ail.group_id_ IS NOT NULL");
//			filtrosBuilder.append(AND);
//			aniadirCondicionesConsultaExpedientes(filtrosBuilder, filtros, true, false);
//		}
//		
//		if (filtros.getEstadosExpedientes() == null || filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_FINALIZADO) ||
//				filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_CANCELADO)) {
//			if (filtros.getEstadosExpedientes() == null || filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_ACTIVO)) {
//				filtrosBuilder.append(UNION_ALL);
//			}
//			filtrosBuilder.append("SELECT af.notification_type_, af.repository_uri_, af.id_, af.asunto_, af.status_, af.creation_date_, af.start_date_");
//			filtrosBuilder.append(", af.end_date_, af.pending_workflow_id_, af.owner_, af.interesado_, af.record_number_, af.wf_id_, af.wf_version_, af.wf_instance_");
//			filtrosBuilder.append(", NULL, NULL, NULL, NULL, NULL, NULL, NULL"); // Se añaden para poder unir consultas pero no son relevantes
//			filtrosBuilder.append(FROM);
//			filtrosBuilder.append("OC3F.administrative_file_ af, OC3F.activity_ ac, OC3F.activity_phase_ ph");
//			filtrosBuilder.append(WHERE);
//			boolean incluirCancelados = filtros.getEstadosExpedientes() == null || 
//					filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_CANCELADO);
//			aniadirCondicionesConsultaExpedientes(filtrosBuilder, filtros, false, incluirCancelados);
//			aniadirOrdenacion(filtrosBuilder, "6");
//		}
//		
//		Query q = em.createNativeQuery(filtrosBuilder.toString());
//		List<Object[]> results = q.getResultList();
//
//		return results;		
//	}
//	
//	@SuppressWarnings("unchecked")
//	private List<Object[]> getResultadosExpedientesAutorizados(FiltrosConsulta filtros, List<String> gruposUsuario) throws GenericFacadeException {
//		StringBuilder filtrosBuilder = new StringBuilder();
//		
//		if (filtros.getEstadosExpedientes() == null || filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_ACTIVO)) {
//			filtrosBuilder.append("SELECT distinct(n.uuid), af.repository_uri_, af.id_, af.asunto_, af.status_, af.creation_date_");
//			filtrosBuilder.append(", af.start_date_, af.end_date_, af.pending_workflow_id_, af.owner_, af.interesado_, af.record_number_, af.wf_id_, af.wf_version_, af.wf_instance_");
//			filtrosBuilder.append(", at.task_def_key_, at.name_, at.create_time_, at.assignee_, ail.group_id_, ph.id_ as phase_id, ph.description_");
//			filtrosBuilder.append(FROM);
//			filtrosBuilder.append("OC3F.administrative_file_ af, OC3F.activity_ ac, OC3F.activity_phase_ ph");
//			filtrosBuilder.append(", EAACTIVITI.act_ru_task at, EAACTIVITI.act_ru_identitylink ail");
//			filtrosBuilder.append(", ALFRESCO.alf_node n, ALFRESCO.alf_acl_member am, ALFRESCO.alf_access_control_entry ae");
//			filtrosBuilder.append(", ALFRESCO.alf_permission p, ALFRESCO.alf_authority a");
//			filtrosBuilder.append(WHERE);
//			filtrosBuilder.append("af.wf_instance_ = at.proc_inst_id_");
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("at.id_ = ail.task_id_");
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("ail.group_id_ IS NOT NULL");			
//			filtrosBuilder.append(AND);
//			aniadirCondicionesPermisos(filtrosBuilder, gruposUsuario);
//			filtrosBuilder.append(AND);
//			aniadirCondicionesConsultaExpedientes(filtrosBuilder, filtros, true, false);
//		}
//		
//		if (filtros.getEstadosExpedientes() == null || filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_FINALIZADO) ||
//				filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_CANCELADO)) {
//			if (filtros.getEstadosExpedientes() == null || filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_ACTIVO)) {
//				filtrosBuilder.append(UNION_ALL);
//			}
//			filtrosBuilder.append("SELECT distinct(n.uuid), af.repository_uri_, af.id_, af.asunto_, af.status_, af.creation_date_");
//			filtrosBuilder.append(", af.start_date_, af.end_date_, af.pending_workflow_id_, af.owner_, af.interesado_, af.record_number_, af.wf_id_, af.wf_version_, af.wf_instance_");
//			filtrosBuilder.append(", NULL, NULL, NULL, NULL, NULL, NULL, NULL"); // Se añaden para poder unir consultas pero no son relevantes
//			filtrosBuilder.append(FROM);
//			filtrosBuilder.append("OC3F.administrative_file_ af, OC3F.activity_ ac, OC3F.activity_phase_ ph");
//			filtrosBuilder.append(", ALFRESCO.alf_node n, ALFRESCO.alf_acl_member am, ALFRESCO.alf_access_control_entry ae");
//			filtrosBuilder.append(", ALFRESCO.alf_permission p, ALFRESCO.alf_authority a");
//			filtrosBuilder.append(WHERE);
//			aniadirCondicionesPermisos(filtrosBuilder, gruposUsuario);
//			filtrosBuilder.append(AND);
//			boolean incluirCancelados = filtros.getEstadosExpedientes() == null || 
//					filtros.getEstadosExpedientes().contains(FiltrosConsulta.ESTADO_EXPEDIENTE_CANCELADO);
//			aniadirCondicionesConsultaExpedientes(filtrosBuilder, filtros, false, incluirCancelados);
//			aniadirOrdenacion(filtrosBuilder, "6");
//		}
//		
//		Query q = em.createNativeQuery(filtrosBuilder.toString());
//		List<Object[]> results = q.getResultList();
//
//		return results;		
//	}
//	
//	private void aniadirCondicionesPermisos(StringBuilder filtrosBuilder, List<String> gruposUsuario) throws GenericFacadeException {
//		filtrosBuilder.append("af.repository_uri_ = n.uuid");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("n.acl_id = am.acl_id");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("am.ace_id = ae.id"); 
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("ae.permission_id = p.id");
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("p.name IN (");
//		filtrosBuilder.append("'" + AlfRepositoryService.CONSTANT_VALUES_COMMONS_MAP.get(AlfRepositoryConstants.ROL_COORDINATOR) + "', ");
//		filtrosBuilder.append("'" + AlfRepositoryService.CONSTANT_VALUES_COMMONS_MAP.get(AlfRepositoryConstants.ROL_COLLABORATOR) + "', ");
//		filtrosBuilder.append("'" + AlfRepositoryService.CONSTANT_VALUES_COMMONS_MAP.get(AlfRepositoryConstants.ROL_CONTRIBUTOR) + "', ");
//		filtrosBuilder.append("'" + AlfRepositoryService.CONSTANT_VALUES_COMMONS_MAP.get(AlfRepositoryConstants.ROL_EDITOR) + "', ");
//		filtrosBuilder.append("'" + AlfRepositoryService.CONSTANT_VALUES_COMMONS_MAP.get(AlfRepositoryConstants.ROL_CONSUMER) + "'");
//		filtrosBuilder.append(")");
//		if (gruposUsuario != null && !gruposUsuario.isEmpty()) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("ae.authority_id = a.id AND a.authority IN (");
//			for (int i = 0; i < gruposUsuario.size(); i++) {
//				String grupo = gruposUsuario.get(i);
//				if (i > 0) {
//					filtrosBuilder.append(", ");
//				}
//				filtrosBuilder.append("'" + grupo + "'");
//			}
//			filtrosBuilder.append(")");
//		}
//		filtrosBuilder.append(AND);
//		filtrosBuilder.append("n.type_qname_id = ");
//		filtrosBuilder.append(ConfigUtils.getParametro(ConstantesGestorDocumental.CONFIG_GD_ALFRESCO_TIPO_EXPEDIENTE_QNAME)); // PRE: 409, PRO: 523
//	}
//	
//	private void aniadirCondicionesConsultaExpedientes(StringBuilder filtrosBuilder, FiltrosConsulta filtros, boolean activos, 
//			boolean incluirCancelados) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String separator = "";
//		
//		// FILTROS DE EXPEDIENTES.
//		// Sólo expediente de Activiti
//		filtrosBuilder.append("af.wf_id_ like '%:%'");
//
//		if (activos) {
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("af.status_ = ");
//			filtrosBuilder.append(FiltrosConsulta.ESTADO_EXPEDIENTE_ACTIVO);
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("at.task_def_key_ = ac.id_");
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("af.wf_id_ = ac.wf_id_");
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("af.wf_version_ = ac.wf_version_");
//			filtrosBuilder.append(AND);
//			filtrosBuilder.append("ac.phase_ = ph.id_");
//		} else {
//			if (incluirCancelados) {
//				filtrosBuilder.append(AND);
//				filtrosBuilder.append("(af.status_ = ");
//				filtrosBuilder.append(FiltrosConsulta.ESTADO_EXPEDIENTE_FINALIZADO);
//				filtrosBuilder.append(OR);
//				filtrosBuilder.append("af.status_ = ");
//				filtrosBuilder.append(FiltrosConsulta.ESTADO_EXPEDIENTE_CANCELADO);
//				filtrosBuilder.append(")");
//			} else {
//				filtrosBuilder.append(AND);
//				filtrosBuilder.append("af.status_ = ");
//				filtrosBuilder.append(FiltrosConsulta.ESTADO_EXPEDIENTE_FINALIZADO);
//			}
//		}
//		
//		aniadirCondicionesExpedientes(filtrosBuilder, filtros);
//	
//		if (filtros.getFechaFinDesde() != null){
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("TRUNC(af.end_date_) >= ");
//			filtrosBuilder.append("TO_DATE('"+df.format(filtros.getFechaFinDesde()) +"', 'YYYY-MM-DD')");
//			separator = AND;
//		}
//		if (filtros.getFechaFinHasta() != null){
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("TRUNC(af.end_date_) <= ");
//			filtrosBuilder.append("TO_DATE('"+df.format(filtros.getFechaFinHasta())+"', 'YYYY-MM-DD')");
//			separator = AND;
//		}		
//	}
//	
//	private void aniadirCondicionesExpedientes(StringBuilder filtrosBuilder, FiltrosExpedientes filtros) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String separator = AND;
//		
//		if (!StringUtils.isNullOrEmpty(filtros.getNumExpediente())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(af.id_) LIKE ");
//			filtrosBuilder.append("'%" + filtros.getNumExpediente().trim().toUpperCase() + "%'");
//			separator = AND;
//		}
//		if (!StringUtils.isNullOrEmpty(filtros.getAsuntoExpediente())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(af.asunto_) LIKE ");
//			filtrosBuilder.append("'%"+filtros.getAsuntoExpediente().toUpperCase()+"%'");
//			separator = AND;
//		}
//		if (!StringUtils.isNullOrEmpty(filtros.getInteresadoId())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(af.owner_) LIKE ");
//			filtrosBuilder.append("'%"+filtros.getInteresadoId().trim().toUpperCase()+"%'");
//			separator = AND;
//		}
//		if (!StringUtils.isNullOrEmpty(filtros.getInteresadoNombreApellidosORazonSocial())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(af.interesado_) LIKE ");
//			filtrosBuilder.append("'%"+filtros.getInteresadoNombreApellidosORazonSocial().trim().toUpperCase()+"%'");
//			separator = AND;
//		}			
//		if (!StringUtils.isNullOrEmpty(filtros.getFamiliaId())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(af.pending_workflow_id_) LIKE ");
//			filtrosBuilder.append( "'" + filtros.getFamiliaId().toUpperCase() + "%'");
//			separator = AND;
//		}
//		if (!StringUtils.isNullOrEmpty(filtros.getProcedimientoId())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(af.pending_workflow_id_) = ");
//			filtrosBuilder.append( "'" + filtros.getProcedimientoId().toUpperCase() + "'");
//			separator = AND;
//		}	
//		if (!StringUtils.isNullOrEmpty(filtros.getNumRegistroEntrada())) {
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("UPPER(af.record_number_) LIKE ");
//			filtrosBuilder.append("'%"+filtros.getNumRegistroEntrada().trim().toUpperCase()+"%'");
//			separator = AND;
//		}		
//		if (filtros.getFechaCreacionDesde() != null){
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("TRUNC(af.creation_date_) >= ");
//			filtrosBuilder.append("TO_DATE('"+df.format(filtros.getFechaCreacionDesde()) +"', 'YYYY-MM-DD')");
//			separator = AND;
//		}
//		if (filtros.getFechaCreacionHasta() != null){
//			filtrosBuilder.append(separator);
//			filtrosBuilder.append("TRUNC(af.creation_date_) <= ");
//			filtrosBuilder.append("TO_DATE('"+df.format(filtros.getFechaCreacionHasta())+"', 'YYYY-MM-DD')");
//			separator = AND;
//		}
//	}
//
	@SuppressWarnings("unused")
	private void aniadirOrdenacion(StringBuilder filtrosBuilder, String columnaOrden) {
		if (!StringUtils.isNullOrEmpty(columnaOrden)) {
			filtrosBuilder.append(ORDER_BY);
			filtrosBuilder.append(columnaOrden);
			filtrosBuilder.append(ORDER_BY_ASC);
		}
	}
}
