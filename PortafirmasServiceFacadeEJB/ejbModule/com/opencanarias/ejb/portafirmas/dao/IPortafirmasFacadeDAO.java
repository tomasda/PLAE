package com.opencanarias.ejb.portafirmas.dao;

import java.util.HashMap;
import java.util.List;

import com.opencanarias.exceptions.PortafirmasFacadeException;

import es.apt.ae.facade.entities.BackOffice;
import es.apt.ae.facade.entities.CatPropiedadesConfiguracion;
import es.apt.ae.facade.entities.portafirmas.Accion;
import es.apt.ae.facade.entities.portafirmas.Ausencia;
import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.DataBaseUser;
import es.apt.ae.facade.entities.portafirmas.Departamento;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.DocumentoWSDL;
import es.apt.ae.facade.entities.portafirmas.EstadoDocumento;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;
import es.apt.ae.facade.entities.portafirmas.TipoCircuito;
import es.apt.ae.facade.entities.portafirmas.TipoGrupo;

public interface IPortafirmasFacadeDAO {	
	public CircuitoEntity getCircuitoById(String idCircuito);
	public <T> T save(T entidad);
	public <T> T saveList(List<T> listaEntidad);
	public <T> void remove(T entidad);
	public void removeListDocumento(List<DocumentoPortafirmas> listDocumentos);
	public Persona getPersonaByDNI(String DNI);
	public Persona getPersonaById(Long Id);
	public List<Persona> getListPersona();
	public List<Persona> getListFirmante();
	public List<Persona> getListValidador();
	public List<CircuitoEntity> getListCircuitosByCodigo(String codigo, String dni, Long id);
	public TipoGrupo getTipoGrupobyCod(String codigo);
	public TipoCircuito getTipoCircuitobyCod(String codigo);
	public EstadoDocumento getEstadoDocumentobyCod(String codigo);
	public CircuitoEntity getCircuitoByUri(String uri);
	public DocumentoPortafirmas getDocumentoPortafirmasbyId(String id);
	public List<DocumentoPortafirmas> getDocumentosPendientesbyDNI(String usuario, String usuarioARevisar);
	public List<DocumentoPortafirmas> getDocumentosPendientesColaboradorbyDNI(String usuario);
	public List<DocumentoPortafirmas> getDocumentosEnviadosbyDNI(String usuario);
	public List<DocumentoPortafirmas> getDocumentosPersonalesbyDNI(String usuario);
	public List<DocumentoPortafirmas> getDocumentosTramitadosbyDNI(String usuario);
	public List<ProcesoFirma> getProcesoByUri(String uri);
	public DocumentoPortafirmas getDocumentoPortafirmasByUri(String uri);
	public HashMap<String, List<ProcesoFirma>> saveProcesoCircuitoTerminado(ProcesoFirma procesoFirma);
	public HashMap<String, List<ProcesoFirma>> saveProcesoCircuito(ProcesoFirma procesoFirma);
	public HashMap<String, List<ProcesoFirma>> rechazarListDocumento(List<ProcesoFirma> listProcesoFirma) throws PortafirmasFacadeException;
	public void eliminarCircuitoDocumento(DocumentoPortafirmas documento, EstadoDocumento estadoDocumento) throws PortafirmasFacadeException;
	public Accion getAccionByCodigo(String codigo);
	public DocumentoPortafirmas getAllStructureDocumentoPortafirmasbyUri(String uri);
	public List<Accion> getlistAccion();
	public List<EstadoDocumento> getlistEstadoDocumento();
	public List<TipoCircuito> getlistTipoCircuito();
	public List<TipoGrupo> getlistTipoGrupo();
	public List<CircuitoEntity> getListCircuitoByCodigoTipo(String tipo);
	public List<DocumentoPortafirmas> getListDocumentoPortafirmasByURI(List<String> uris);
	public Persona getPersona(String dni);
	public Ausencia getPersonaAusencia(String dni);
	public List<Persona> getRevisoresByDNI(String dni);
	public Persona saveRevision(Persona persona);
	public List<Persona> saveRevision(Persona persona, List<Persona> listColaboradores);
	public List<Persona> getListRevisoresDe(String dni);
	public List<Persona> getSustitucionByDNI(String dni);
	public List<DocumentoPortafirmas> getDocumentosPersonalesbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros);
	public List<DocumentoPortafirmas> getDocumentosTramitadosbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros);
	public List<DocumentoPortafirmas> getDocumentosPendientesbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros);
	public List<DocumentoPortafirmas> getDocumentosPendientesColaboradorbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros);
	public List<DocumentoPortafirmas> getDocumentosEnviadosbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros);
	public Ausencia saveAusencia(Ausencia ausencia);
	public List<DocumentoWSDL> getDocumentoWSDLByUri(String uri);
	public BackOffice getBackOfficeByUsername(String username);
	public List<ProcesoFirma> getProcesoFirmadoByUri(String uri);
	public List<Long> pendienteSustituto(String usuario);
	public List<String> findWhiteListByURI(String uri);
	public List<Persona> getListFirmanteByDNISolicitante(String dni, boolean esUsuarioPruebas);
	public List<BackOffice> getAllBackoffice();
	public List<CatPropiedadesConfiguracion> getPropiedadesConfiguracion();
	public Departamento getDepartamentoById(String Id);	
	public List<BackOffice> consultarDescripcionesBackoffices();
	public List<DataBaseUser> obtenerUsuarios(List<String> departamentos);
	public Object doLogin(String username, String password);
}
