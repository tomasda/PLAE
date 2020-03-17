package com.opencanarias.ejb.portafirmas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConfigurationException;

import com.opencanarias.exceptions.PortafirmasFacadeException;

import es.apt.ae.facade.entities.BackOffice;
import es.apt.ae.facade.entities.CatPropiedadesConfiguracion;
import es.apt.ae.facade.entities.portafirmas.Accion;
import es.apt.ae.facade.entities.portafirmas.Ausencia;
import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.EstadoDocumento;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;
import es.apt.ae.facade.entities.portafirmas.TipoCircuito;
import es.apt.ae.facade.entities.portafirmas.TipoGrupo;
import es.apt.ae.facade.portafirmas.dto.RespuestaVisualizacionDocumento;
import es.apt.ae.facade.ws.params.commons.out.Respuesta;
import es.apt.ae.facade.ws.params.portafirmas.in.Parametros;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaCircuitos;
import es.apt.ae.facade.ws.params.portafirmas.out.Resultado;

public interface IPortafirmasFacade {
	
	/*Basicos*/
	public String getVersion() throws PortafirmasFacadeException;
	public String enviarPortafirmas(String parametros) throws PortafirmasFacadeException;
	public String recuperarPortafirmas(String parametros) throws PortafirmasFacadeException;
	public String consultarCircuitos(@WebParam(name = "parametros") String parametros) throws PortafirmasFacadeException;
	public String consultarFirmantes(@WebParam(name = "parametros") String parametros) throws PortafirmasFacadeException;
	public Map<String, String> consultarDescripcionesBackoffices();
	public Map<String, String> consultarUsuariosDepartamentosMap(List<String> departamentos, boolean incluirUsuariosPrueba);
	
	/*EJB-PERSONAS*/
	public Persona getPersona(String dni) throws PortafirmasFacadeException;
	
	/*EJB-DOCUMENTOS*/
	public List<DocumentoPortafirmas> buscarPorBandeja(String usuario, String tipoBandeja, HashMap<String, Object> filtros);
	public List<ProcesoFirma> getHistorialDocumento(String usuario, String uri) throws PortafirmasFacadeException;
	public List<EstadoDocumento> getListEstadoDocumento() throws PortafirmasFacadeException;
	public HashMap<String,Object> almacenarFirmaXADES(String usuarioSolicitante, HashMap<String, List<String>> solicitud) throws PortafirmasFacadeException;
	public HashMap<String,Object> almacenarFirmaPADES(String usuarioSolicitante, HashMap<String, List<String>> solicitud) throws PortafirmasFacadeException;
	public void actualizarDocumento(String usuario, DocumentoPortafirmas documentoPortafirmas) throws PortafirmasFacadeException;
	public HashMap<String, Object> getDocumentoByUri(String usuarioSolicitante, String uri);
	public HashMap<String, Object> permitirVisualizarEstado(String uri, String usuarioSolicitante);

	/*EJB-DOCUMENTOS-GESTOR DOCUMENTAL*/
	public es.apt.ae.facade.dto.Resultado recuperarDocumentosFirmar(List<DocumentoPortafirmas> listDocumentos, String usuarioSolicitante, Persona persona, boolean clienteFirmaWeb) throws PortafirmasFacadeException;
	public Boolean altaDocumento(String usuario, DocumentoPortafirmas documento, byte[] binaries, String contentType) throws PortafirmasFacadeException;
	public RespuestaVisualizacionDocumento obtenerVisualizacionDocumento(String usuarioSolicitante, String uri) throws PortafirmasFacadeException;
	public HashMap<String,Object> actuarSobreListaDocumento(String usuario,List<DocumentoPortafirmas> listDocumentos, String accion, String descripcion) throws PortafirmasFacadeException;
	
	/*EJB-CIRCUITOS*/
	public CircuitoEntity consultarCircuito(Long idCircuito) throws PortafirmasFacadeException;
	public Respuesta consultaCircuitosEJB(Resultado resultado, Respuesta respuesta, Parametros params, ListaCircuitos listaCircuitos) throws DatatypeConfigurationException;
	public Respuesta recuperarPortafirmasEJB(Resultado resultado, Respuesta respuesta, Parametros params);
	public Respuesta enviarPortafirmasEJB(Resultado resultado, Respuesta respuesta,	Parametros params) throws PortafirmasFacadeException;
	public List<Persona> consultarFirmantesEJB(String dni, boolean esUsuarioPruebas) throws PortafirmasFacadeException;
	public CircuitoEntity consultarCircuitoByUri(String uri) throws PortafirmasFacadeException;
	public List<TipoCircuito> getListTipoCircuito() throws PortafirmasFacadeException;
	public List<TipoGrupo> getListTipoGrupo() throws PortafirmasFacadeException;
	public List<CircuitoEntity> consultaCircuitosPorTipo(String tipo) throws PortafirmasFacadeException;
	public List<CircuitoEntity> consultaCircuitosPorTipoYSolicitante(String tipo, String dniSolicitante) throws PortafirmasFacadeException;
	public List<Accion> getListAcciones() throws PortafirmasFacadeException;
	
	/*EJB-AUSENCIAS*/
	public List<Persona> getSustitucionByDNI(String dni) throws PortafirmasFacadeException;
	public Ausencia getPersonaAusencia(String dni) throws PortafirmasFacadeException;
	public Ausencia saveAusencia(Ausencia ausencia) throws PortafirmasFacadeException;
	public List<Persona> consultarPersonasEJB() throws PortafirmasFacadeException;
	
	/*EJB-COLABORADORES*/
	public HashMap<String,Object> saveColaboradores(Persona persona, List<Persona> listColaboradores) throws PortafirmasFacadeException;
	public List<Persona> getListRevisoresDe(String dni) throws PortafirmasFacadeException;
	public List<Persona> consultarRevisoresByDNI(String dni) throws PortafirmasFacadeException;
	public List<Persona> consultarValidadoresEJB() throws PortafirmasFacadeException;
	
	/*EJB-BACKOFFICE*/
	public List<BackOffice> getAllBackoffice();
	
	/*EJB-PROPIEDADES DE CONFIGURACION*/
	public List<CatPropiedadesConfiguracion> getPropiedadesConfiguracion();
	HashMap<String, HashMap<String, Object>> getUsersAE(List<String> departamentos);
	
}
