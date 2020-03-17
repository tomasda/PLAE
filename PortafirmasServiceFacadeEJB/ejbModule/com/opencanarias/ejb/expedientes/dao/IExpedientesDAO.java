package com.opencanarias.ejb.expedientes.dao;

//import java.util.List;
//import java.util.Map;
//
import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//
//import com.opencanarias.ejb.expedientes.dto.ContadorRequerimientosSede;
//import com.opencanarias.ejb.expedientes.dto.FiltrosBandejaDocumentos;
//import com.opencanarias.ejb.expedientes.dto.FiltrosBandejaTareas;
//import com.opencanarias.ejb.expedientes.dto.FiltrosConsulta;
import com.opencanarias.exceptions.ExpedientesFacadeException;
//import com.opencanarias.exceptions.GenericFacadeException;
//
//import es.apt.ae.facade.dto.EstadoNotificacionDocumento;
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
//import es.apt.ae.facade.entities.Rol;
//import es.apt.ae.facade.entities.TerceroExp;
//import es.apt.ae.facade.entities.Track;
//import es.apt.ae.facade.entities.Transicion;
//import es.apt.ae.facade.entities.actuaciones.Actuacion;
//import es.apt.ae.facade.entities.actuaciones.ActuacionesLeidasInfo;
//import es.apt.ae.facade.entities.actuaciones.CatAccionesExpedientes;
//import es.apt.ae.facade.entities.earegistro.Tercero;
//import es.apt.ae.facade.entities.notificaciones.NotificacionDocumento;
//import es.apt.ae.facade.entities.notificaciones.NotificacionPapelDocumento;
//import es.apt.ae.facade.expedientes.dto.DocumentoBandejaItem;
//import es.apt.ae.facade.expedientes.dto.ExpedienteConsultaItem;
//import es.apt.ae.facade.expedientes.dto.TareaBandejaItem;
//import es.apt.ae.facade.ws.params.expedientes.in.notificacion.InfoNotifDocumento;
//import es.apt.ae.facade.ws.params.gestor.documental.commons.inout.Metadato;



public interface IExpedientesDAO {
	
	public EntityManager getEntityManager();

	public <T> void persist(T entidad) throws ExpedientesFacadeException;
	
	public <T> T merge(T entidad) throws ExpedientesFacadeException;
	
	public <T> void remove(T entidad) throws ExpedientesFacadeException;
	
//	public Procedimiento getProcedimientoById(String procId);
//		
//	public List<Procedimiento> getProcedimientos();
//	
//	public List<Procedimiento> getProcedimientosByFamilia(String familia);
//	
//	public List<Procedimiento> getProcedimientosActivosRegistro();
//	
//	public List<Procedimiento> getProcedimientosActivosRegistroByFamilia(String familia);	
//	
//	public Map<String, Procedimiento> getProcedimientosInicio(List<String> departamentos, String familia);
//	
//	public List<CatProcedimientoPermiso> getPermisosProcedimiento(String procId);
//	
//	public List<Familia> getFamilias();
//	
//	public Expediente getExpedienteById(String numExpediente, Boolean esActivo);
//	
//	public Expediente getExpedienteByNumRegistro(String numRegistro);
//	
//	public Actuacion getActuacionById(String idActuacion, Boolean esActivo);
//	
//	public List<String> getActuacionesIdsDocumentoNotificacion(String numExp, String nombreDoc);
//	
//	public List<ActuacionesLeidasInfo> getActuacionesLeidasInfo(List<String> actuacionesIds);
//	
//	public Proceso getProcesoMaxVersion(String proceso);
//	
//	public Proceso getProcesoById(String procesoId);
//	
//	public Actividad getActividadById(String actividadId, String procesoId, String procesoVersion);
//	
//	public List<CatPlantilla> getPlantillasActividad(String actividadId, String procedimientoId, Boolean automaticas);
//	
//	public CatPlantilla getPlantillaById(Integer plantillaId);
//	
//	public CatContenidoEstaticoEtiqueta getContenidoEstaticoEtiqueta(Integer plantillaId, Integer etiquetaId);
//	
//	public Transicion getTransicion(String wfId, String actividadOrigenId, String actividadDestinoId, short numTransicion);
//	
//	public List<Documento> getDocumentosExpediente(String numExpediente);
//	
//	public List<Documento> getDocumentosExpedienteNoCancelados(String numExpediente);
//	
//	public Map<String, Documento> getDocumentosExpedienteByUris(String numExpediente, String idActuacion, List<String> uris);
//	
//	public Map<String, Documento> getDocumentosExpedienteRegistro(String numExpediente, String numRegistro);
//	
//	public Documento getDocumentoExpedienteByUri(String uriDoc);
//	
//	public Documento getDocumentoExpedienteById(String numExpediente, String nombreDoc);
//	
//	public List<Documento> getDocumentosFirma(String numExpediente, String docName);
//	
//	public List<Documento> getDocumentoYFirmas(String numExpediente, String docName);
//	
//	public Documento getDocumentoAutomatico(String nombreDoc, String numExpediente, String actividadId);
//	
//	public NotificacionDocumento getNotificacionDocumento(String administrativeFileId, String documentId);
//	
//	public NotificacionDocumento getNotificacionDocumentoByUri(String uri);
//	
//	public NotificacionDocumento getNotificacionDocumentoByNumNotificacion(String numNotificacion);	
//	
//	public List<Documento> getDocumentosExpedienteNotificados(String administrativeFileId, List<Documento> documentos);
//	
//	public Map<String, String> getNotificacionesIdsDocumentosExpediente(String administrativeFileId, List<Documento> documentos);
//
//	public Map<String, NotificacionPapelDocumento> getNotificacionesPapelDocumentos(String adminFileId, List<Documento> documentos);
//	
//	public NotificacionPapelDocumento getNotifPresencialDocumento(String adminFileId, String documentId, boolean electronic);
//	
//	public Map<String, EstadoNotificacionDocumento> getEstadosDocumentosNotificados(String adminFileId);
//
//	public GestionPortafirmasDocumento getGestionPortafirmasDocByUri(String uri);
//	
//	public FirmaManuscritaDocumento getFirmaManuscritaDocByUri(String uri);
//	
//	public Map<String, String> getTiposDocumentos();
//	
//	public List<IndiceCapituloDocumento> getIndiceCapituloDocumentos(String adminFileId, String documentId, Integer capituloId);
//	
//	public List<Documento> getDocumentosRefActuacion(String adminFileId);
//	
//	public IndiceCapituloDocumento removeDocumentoIndice(Integer capituloId, String documentId) throws ExpedientesFacadeException;
//	
//	public Track getUltimoPasoHistorico(String numExpediente);
//	
//	public List<Expediente> getExpedientesByQuery(String queryStr);
//	
//	public List<Expediente> getExpedientesByWfInstancias(List<String> wfInstancias, boolean soloActivos);
//	
//	public List<CatAccionesExpedientes> getActuacionesAutomaticasPorActividad(String wfId, String actividadId, String autoDisparador);
//	
//	public List<CatAccionesExpedientes> getCondicionesDespacharExpAuto(String wfId, String actividadId, String tipoActuacionId);
//
//	public Map<Integer, Tercero> getTercerosMap(List<es.apt.ae.facade.ws.params.commons.in.Tercero> terceros) throws ExpedientesFacadeException;
//
//	public Map<Integer, Tercero> getTercerosExpedienteMap(List<TerceroExp> terceros) throws ExpedientesFacadeException;
//
//	public void actualizarTerceroExpediente(Expediente expediente, String tipoRelacion, Tercero datosTerceroNuevo) throws ExpedientesFacadeException;
//	
//	public Documento saveDocumento(es.apt.ae.facade.entities.Documento documento);
//	
//	public NotificacionDocumento saveNotificacionDocumento(es.apt.ae.facade.entities.Documento documentoPrincipal, List<es.apt.ae.facade.entities.Documento> adjuntos,
//			String usuarioSolicitanteId, String wsdl) throws ExpedientesFacadeException;
//	
//	public void saveInfoNotifManualDocumento(NotificacionPapelDocumento notifDocumento, InfoNotifDocumento infoNotifDoc) throws ExpedientesFacadeException;
//	
//	public List<CatProcedimientoCircuito> getCatProcedimientoCircuitos(String idProcedimiento, String idTramite, String idDepartamento);
//	
//	public Rol getRolByNombre(String nombre);
//	
//	public Map<String, Rol> getDepartamentosMap();
//	
//	public Map<String, Rol> getRolesDecretablesMap();
//	
//	public Map<String, Rol> getRolesMap();
//	
//	public boolean existeRelacion(String numExp1, String numExp2, Integer tipoRelacion) throws ExpedientesFacadeException;
//	
//	public String mapearMetadatosADocumento (List<Metadato> metadatos, es.apt.ae.facade.ws.params.expedientes.out.busqueda.Documento doc) throws Exception;
//	
//	public Map<String, ContadorRequerimientosSede> getContadorRequerimientosByExpediente(List<String> adminFileIds);
//	
//	public List<TareaBandejaItem> consultarTareasFiltros(FiltrosBandejaTareas filtrosBandeja) throws NoResultException;
//	
//	public List<DocumentoBandejaItem> consultarDocumentosTareasFiltros(FiltrosBandejaDocumentos filtros) throws NoResultException;
//	
//	public List<ExpedienteConsultaItem> consultarExpedientesFiltros(FiltrosConsulta filtrosConsulta) throws NoResultException, GenericFacadeException;
}
