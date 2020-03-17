package com.opencanarias.ejb.portafirmas.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.utils.StringUtils;

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
import es.apt.ae.facade.entities.portafirmas.FirmantePersona;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;
import es.apt.ae.facade.entities.portafirmas.Revision;
import es.apt.ae.facade.entities.portafirmas.Revisor;
import es.apt.ae.facade.entities.portafirmas.TipoCircuito;
import es.apt.ae.facade.entities.portafirmas.TipoGrupo;
import es.apt.ae.facade.entities.utils.EntitiesUtils;

@SuppressWarnings("unchecked")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PortafirmasFacadeDAO implements PortafirmasFacadeDAOLocal, PortafirmasFacadeDAORemote {

	private Logger logger = Logger.getLogger(PortafirmasFacadeDAO.class);
	public static final String FIRM = "FIRM";
	public static final String TRAM = "TRAM";
	public static final String RECH = "RECH";
	public static final String RECP = "RECP";
	public static final String GE = "GE";
	public static final String SIDOPU = "SIDOPU";

	@PersistenceContext(unitName = "facade-pu")
	private EntityManager em;
	

	public CircuitoEntity getCircuitoById(String idCircuito) {
		CircuitoEntity circuito = new CircuitoEntity();

		try {
			 circuito = (CircuitoEntity) em.createNamedQuery("getCircuitoById").setParameter("Id", new Long(idCircuito)).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe el circuito con id " + idCircuito + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el circuito con id " + idCircuito + " de la base de datos", e);
		}

		return circuito;
	}

	public <T> T save(T entidad) {
		T resultado = em.merge(entidad);
		return resultado;
	}

	public <T> T saveList(List<T> listaEntidad) {
		T resultado = null;
		for (T entidad : listaEntidad) {
			resultado = em.merge(entidad);
		}
		return resultado;
	}

	public <T> void remove(T entidad) {
		em.remove(entidad);

	}

	public void removeListDocumento(List<DocumentoPortafirmas> listDocumentos) {
		try {
			for (DocumentoPortafirmas documento : listDocumentos) {
				CircuitoEntity circuito = documento.getCircuito();
				documento.setCircuito(null);
				em.merge(documento);
				if (circuito != null) {
					circuito.setDocumento(null);
					em.remove(em.merge(circuito));
				}
				em.remove(em.merge(documento));
			}
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] Error al eliminar un listado de documentos de la base de datos", e);
		}
	}

	public Persona getPersonaByDNI(String dni) {
		return EntitiesUtils.getPersonaByDNI(em, dni);
	}

	public Persona getPersonaById(Long id) {
		Persona persona = null;

		try {
			persona = (Persona) em.createNamedQuery(Persona.getPersonaById).setParameter("Id", id).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe la persona con id " + id + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar la persona de la base de datos", e);
		}

		return persona;
	}

	public List<Persona> getListPersona() {
		List<Persona> personas = null;

		try {
			personas = (List<Persona>) em.createNamedQuery(Persona.getListPersona).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de firmantes de la base de datos", e);
		}

		return personas;
	}

	public List<Persona> getListFirmante() {
		List<Persona> personas = null;

		try {
			personas = (List<Persona>) em.createNamedQuery(Persona.getListFirmantes).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de firmantes de la base de datos", e);
		}

		return personas;
	}

	public List<Persona> getListFirmanteByDNISolicitante(String dni, boolean esUsuarioPruebas) {
		List<FirmantePersona> firmantesPersona = null;
		List<Persona> personas = null;
		try{
			firmantesPersona = (List<FirmantePersona>) em.createNamedQuery(FirmantePersona.findByDNISolicitante).setParameter("dni", dni).getResultList();
			if (firmantesPersona != null) {
				personas = new ArrayList<Persona>();
				boolean ordenarFirmantes = false;
				Map<String, Persona> personasOrderedMap = new TreeMap<String, Persona>();
				for (FirmantePersona firmantePersona:firmantesPersona) {
					Persona persona = firmantePersona.getPersona();
					if (persona != null && persona.getFirmante()) {
						ordenarFirmantes = true;
						personasOrderedMap.put(persona.getNombre().toUpperCase() + " " + persona.getApellido1().toUpperCase(), persona);
					} else {
						ordenarFirmantes = false;
						if (esUsuarioPruebas) {
							personas = (List<Persona>) em.createNamedQuery(Persona.getListFirmantes).getResultList();
						} else {
							personas = (List<Persona>) em.createNamedQuery(Persona.getListFirmantesNoPrueba).getResultList();
						}
						break;
					}
				}
				if (ordenarFirmantes) {
					personas = new ArrayList<Persona>(personasOrderedMap.values());
				}
			}
		} catch(Exception e){
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar ningun firmante para el solicitante con dni " + dni);
		}
		
		return personas;
	}
	
	
	public List<Persona> getListValidador() {
		List<Persona> personas = null;

		try {
			personas = (List<Persona>) em.createNamedQuery(Persona.getListPersonaVALD).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de Validadores de la base de datos", e);
		}

		return personas;
	}

	public List<CircuitoEntity> getListCircuitosByCodigo(String codigo, String dni, Long id) {
		List<CircuitoEntity> circuitos = null;
		
		try {
			if (!StringUtils.isNullOrEmpty(dni) && null != id) {
				circuitos = (List<CircuitoEntity>) em.createNamedQuery("getCircuitoByTipoAndIdAndSolicitante")
						.setParameter("codigo", codigo).setParameter("dni", dni).setParameter("id", id).getResultList();
			} else if (StringUtils.isNullOrEmpty(dni) && null != id) {
				circuitos = (List<CircuitoEntity>) em.createNamedQuery("getCircuitoByTipoAndId")
						.setParameter("codigo", codigo).setParameter("id", id).getResultList();
			} else if (!StringUtils.isNullOrEmpty(dni) && null == id) {
				circuitos = (List<CircuitoEntity>) em.createNamedQuery("findCircuitosByTipoAndSolicitante")
						.setParameter("codigo", codigo).setParameter("dni", dni).getResultList();
			} else {
				circuitos = (List<CircuitoEntity>) em.createNamedQuery("getListCircuito").setParameter("codigo", "PRDF").getResultList();	
			}
		} catch(Exception e){
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de circuitos de la base de datos", e);
		}
		
		return circuitos;
	}
	
	public TipoGrupo getTipoGrupobyCod(String codigo) {
		TipoGrupo tipoGrupo = new TipoGrupo();

		try {
			tipoGrupo = (TipoGrupo) em.createNamedQuery("findTipoGrupobyCod").setParameter("Codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe un tipo de grupo con codigo " + codigo + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el tipo de grupo codigo " + codigo + " de la base de datos", e);
		}

		return tipoGrupo;
	}

	public TipoCircuito getTipoCircuitobyCod(String codigo) {
		TipoCircuito tipoCircuito = new TipoCircuito();

		try {
			tipoCircuito = (TipoCircuito) em.createNamedQuery("findTipoCircuitobyCod").setParameter("Codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe un tipo de circuito con codigo " + codigo + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el tipo de circuito con codigo " + codigo + " de la base de datos", e);
		}

		return tipoCircuito;
	}

	public EstadoDocumento getEstadoDocumentobyCod(String codigo) {
		EstadoDocumento estadoDocumento = new EstadoDocumento();

		try {
			estadoDocumento = (EstadoDocumento) em.createNamedQuery("findEstadoDocumentobyCod").setParameter("Codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe el estado de documento con codigo " + codigo + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el estado de documento con codigo " + codigo + " de la base de datos", e);
		}

		return estadoDocumento;
	}

	public CircuitoEntity getCircuitoByUri(String uri) {

		CircuitoEntity circuito = new CircuitoEntity();
		try {
			circuito = (CircuitoEntity) em.createNamedQuery("findCircuitobyUri").setParameter("uri", uri).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe el circuito del documento con uri " + uri + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el circuito del documento con uri " + uri + " de la base de datos", e);
		}
		// System.out.println(circuito.getListGrupo().get(0).getGrupoPersona().get(0).getPersona().getNombre());//Prueba
		// para que cargue la estructura entera
		return circuito;
	}

	public DocumentoPortafirmas getDocumentoPortafirmasbyId(String id) {
		DocumentoPortafirmas documentoPortafirmas = new DocumentoPortafirmas();

		try {
			documentoPortafirmas = (DocumentoPortafirmas) em.createNamedQuery("findDocumentoPortafirmasbyId").setParameter("Id", new Long(id)).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe un documento con id " + id + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar un documento con id " + id + " de la base de datos", e);
		}

		return documentoPortafirmas;
	}

	public List<DocumentoPortafirmas> getDocumentosPendientesbyDNI(String usuario, String usuarioARevisar) {
		List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();
		
		Boolean flag = false;
		List<Persona> listRevisores = getRevisoresByDNI(usuario);
		if (usuarioARevisar != null){
			for (Persona persona : listRevisores){
				if (persona.getNumIdentificacion().equals(usuarioARevisar)){
					flag=true;
				}
			}
		}
			
		if (flag || listRevisores.size() == 0){
			try {
				// Mis Documentos
				List<Long> listDocumentosRealizados = (List<Long>) em.createNamedQuery("findDocumentosRealizados").setParameter("usuarioDNI", usuario).getResultList();
				List<Long> listDocumentosPendienteSustituto = pendienteSustituto(usuario);
				List<Long> listDocumentosPendientePersonales = (List<Long>) em.createNamedQuery("findDocumentosPendientesPersonales").setParameter("usuarioDNI", usuario).getResultList();
	
				List<Long> listDocumentosIN = new ArrayList<Long>();
				listDocumentosIN.addAll(listDocumentosPendienteSustituto);
				listDocumentosIN.addAll(listDocumentosPendientePersonales);
	
				if (listDocumentosRealizados.size() == 0) {
					listDocumentosRealizados.add(new Long(-1));
				}
				if (listDocumentosIN.size() == 0) {
					listDocumentosIN.add(new Long(-1));
				}
	
				listDocumentoPortafirmas = (List<DocumentoPortafirmas>) em.createNamedQuery("findDocumentosPendientesBigQuery").setParameter("listDocumentosNotIN", listDocumentosRealizados)
						.setParameter("listDocumentosIN", listDocumentosIN).getResultList();
	
				// Los de quien mi colaborador findRevisoresDeByDNI
				Persona colaborador = null;
				try {
					colaborador = (Persona) em.createNamedQuery(Persona.findRevisoresDeByDNI).setParameter("dni", usuario).getSingleResult();
				} catch (Exception e) {
					logger.info("[FACHADA-SRV_PORTAFIRMAS] La persona indicada no es colaboradora de nadie");
				}
	
				if (colaborador != null) {
					List<DocumentoPortafirmas> listDocumentosDeQuienColaboro = getDocumentosPendientesbyDNI(colaborador.getNumIdentificacion(), usuario);
					// Eliminamos los documentos sobre los que ya hayamos actuado
					for (int i = 0; i < listDocumentosDeQuienColaboro.size(); i++) {
						for (Long id : listDocumentosRealizados) {
							if (listDocumentosDeQuienColaboro.get(i).getId().equals(id)) {
								listDocumentosDeQuienColaboro.remove(listDocumentosDeQuienColaboro.get(i));
								i--;
								break;
							}
						}
					}
					listDocumentoPortafirmas.removeAll(listDocumentosDeQuienColaboro);
					listDocumentoPortafirmas.addAll(listDocumentosDeQuienColaboro);
				}
	
			} catch (Exception e) {
				logger.error("[FACHADA-SRV_PORTAFIRMAS] Error recuperando el listado de documentos de la base de datos", e);
			}
	
			return listDocumentoPortafirmas;
		} else {
			return getDocumentosPendientesColaboradorbyDNI(usuario);
		}
	}

	public List<DocumentoPortafirmas> getDocumentosPendientesColaboradorbyDNI(String usuario) {
		List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();

		try {
			listDocumentoPortafirmas = (List<DocumentoPortafirmas>) em.createNamedQuery("findDocumentosPendientesColaboradoresbyDNI").setParameter("usuarioDNI", usuario).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de documentos pendientes del colaborador con DNI " + usuario + " de la base de datos", e);
		}

		return listDocumentoPortafirmas;
	}

	public List<DocumentoPortafirmas> getDocumentosEnviadosbyDNI(String usuario) {
		List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();

		try {
			listDocumentoPortafirmas = (List<DocumentoPortafirmas>) em.createNamedQuery("findDocumentosEnviadosbyDNI").setParameter("usuarioDNI", usuario).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de documentos enviados por el usuario " + usuario + " de la base de datos", e);
		}

		return listDocumentoPortafirmas;
	}

	public List<DocumentoPortafirmas> getDocumentosPersonalesbyDNI(String usuario) {
		List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();

		try {
			listDocumentoPortafirmas = (List<DocumentoPortafirmas>) em.createNamedQuery("findDocumentosPersonalesbyDNI").setParameter("usuarioDNI", usuario).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de documentos personales del usuario " + usuario + " de la base de datos", e);
		}

		return listDocumentoPortafirmas;
	}

	public List<DocumentoPortafirmas> getDocumentosTramitadosbyDNI(String usuario) {
		List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();

		try {
			listDocumentoPortafirmas = (List<DocumentoPortafirmas>) em.createNamedQuery("findDocumentosTramitadosbyDNI").setParameter("usuarioDNI", usuario).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de documentos tramitados del usuario " + usuario + " de la base de datos", e);
		}

		return listDocumentoPortafirmas;
	}

	public List<ProcesoFirma> getProcesoByUri(String uri) {
		List<ProcesoFirma> listProcesos = new ArrayList<ProcesoFirma>();

		try {
			listProcesos = (List<ProcesoFirma>) em.createNamedQuery("findProcesoByUri").setParameter("uri", uri).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de procesos asociados al documento con uri " + uri + " de la base de datos", e);
		}

		return listProcesos;
	}

	public DocumentoPortafirmas getDocumentoPortafirmasByUri(String uri) {

		DocumentoPortafirmas documentoPortafirmas = new DocumentoPortafirmas();

		try {
			documentoPortafirmas = (DocumentoPortafirmas) em.createNamedQuery("findDocumentoPortafirmasbyUri").setParameter("uri", uri).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe un documento con uri " + uri + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el documento con uri " + uri + " de la base de datos", e);
		}

		return documentoPortafirmas;
	}

	public HashMap<String, List<ProcesoFirma>> saveProcesoCircuitoTerminado(ProcesoFirma procesoFirma) {
		HashMap<String, List<ProcesoFirma>> result = new HashMap<String, List<ProcesoFirma>>();
		EstadoDocumento estadoDocumentoFirmado = getEstadoDocumentobyCod(FIRM);
		DocumentoPortafirmas documento = procesoFirma.getDocumento();
		CircuitoEntity circuito = documento.getCircuito();
		documento.setEstadoDocumento(estadoDocumentoFirmado);
		documento.setCircuito(null);
		em.merge(procesoFirma);
		em.merge(documento);
		circuito.setDocumento(null);
		em.remove(em.merge(circuito));
		return result;
	}

	public HashMap<String, List<ProcesoFirma>> saveProcesoCircuito(ProcesoFirma procesoFirma) {
		HashMap<String, List<ProcesoFirma>> result = new HashMap<String, List<ProcesoFirma>>();
		EstadoDocumento estadoDocumentoFirmado = getEstadoDocumentobyCod(TRAM);
		procesoFirma.getDocumento().setEstadoDocumento(estadoDocumentoFirmado);
		em.merge(procesoFirma);
		em.merge(procesoFirma.getDocumento());
		return result;
	}

	public HashMap<String, List<ProcesoFirma>> rechazarListDocumento(List<ProcesoFirma> listProcesoFirma) throws PortafirmasFacadeException {

		HashMap<String, List<ProcesoFirma>> result = new HashMap<String, List<ProcesoFirma>>();
		List<ProcesoFirma> listaTerminados = new ArrayList<ProcesoFirma>();
		EstadoDocumento estadoDocumento = getEstadoDocumentobyCod(RECH);
		if (estadoDocumento != null) {
			try {
				for (ProcesoFirma procesoFirma : listProcesoFirma) {
					procesoFirma.getDocumento().setEstadoDocumento(estadoDocumento);
					CircuitoEntity circuito = procesoFirma.getDocumento().getCircuito();
					procesoFirma.getDocumento().setCircuito(null);
					em.merge(procesoFirma);
					em.merge(procesoFirma.getDocumento());
					circuito.setDocumento(null);
					// result.put(procesoFirma.getDocumento().getUri(), "Rechazado");
					em.remove(em.merge(circuito));
					listaTerminados.add(procesoFirma);
				}

				// TODO: una vez cancelado se debe modificar
			} catch (Exception e) {
				throw new PortafirmasFacadeException("Error, no se han podido rechazar los documentos correctamente", e);
			}

		} else {
			throw new PortafirmasFacadeException("Error, no se ha podido recuperar el estado firmado");
		}
		result.put("listaProcesosCierreGrupo", listaTerminados);
		return result;
	}

	public void eliminarCircuitoDocumento(DocumentoPortafirmas documento, EstadoDocumento estadoDocumento) throws PortafirmasFacadeException {
		CircuitoEntity circuito = documento.getCircuito();
		documento.setCircuito(null);
		documento.setEstadoDocumento(estadoDocumento);
		try {
			em.merge(documento);
			em.remove(circuito);
		} catch (Exception e) {
			throw new PortafirmasFacadeException("Error, no se ha podido rechazar el documento correctamente", e);
		}
	}

	public Accion getAccionByCodigo(String codigo) {
		return (Accion) em.createNamedQuery("findAccionbyCodigo").setParameter("codigo", codigo).getSingleResult();
	}

	public DocumentoPortafirmas getAllStructureDocumentoPortafirmasbyUri(String uri) {
		return (DocumentoPortafirmas) em.createNamedQuery("findAllStructureDocumentoPortafirmasbyUri").setParameter("uri", uri).getSingleResult();
	}

	public List<Accion> getlistAccion() {
		List<Accion> listAccion = new ArrayList<Accion>();

		try {
			listAccion = (List<Accion>) em.createNamedQuery("findListAccion").getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de acciones de la base de datos", e);
		}

		return listAccion;
	}

	public List<EstadoDocumento> getlistEstadoDocumento() {
		List<EstadoDocumento> listAccion = new ArrayList<EstadoDocumento>();

		try {
			listAccion = (List<EstadoDocumento>) em.createNamedQuery("findListEstadoDocumento").getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de estados de la base de datos", e);
		}

		return listAccion;
	}

	public List<TipoCircuito> getlistTipoCircuito() {
		List<TipoCircuito> listAccion = new ArrayList<TipoCircuito>();

		try {
			listAccion = (List<TipoCircuito>) em.createNamedQuery("findListTipoCircuito").getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de tipo de circuitos de la base de datos", e);
		}

		return listAccion;
	}

	public List<TipoGrupo> getlistTipoGrupo() {
		List<TipoGrupo> listAccion = new ArrayList<TipoGrupo>();

		try {
			listAccion = (List<TipoGrupo>) em.createNamedQuery("findListTipoGrupo").getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de tipo de grupos de la base de datos", e);
		}

		return listAccion;
	}

	public List<CircuitoEntity> getListCircuitoByCodigoTipo(String tipo) {
		List<CircuitoEntity> listCircuito = new ArrayList<CircuitoEntity>();
		try {
			listCircuito = (List<CircuitoEntity>) em.createNamedQuery("getListCircuito").setParameter("codigo", tipo).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se han podido recuperar los circuitos de la base de datos", e);
		}

		return listCircuito;
	}

	public List<DocumentoPortafirmas> getListDocumentoPortafirmasByURI(List<String> uris) {
		return EntitiesUtils.getListDocumentoPortafirmasByURI(em, uris);
	}

	public Persona getPersona(String dni) {
		try {
			return (Persona) em.createNamedQuery(Persona.getPersonaByDNI).setParameter("dni", dni).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe una persona con DNI " + dni + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar la persona con DNI " + dni + " de la base de datos", e);
		}
		return null;
	}

	public Ausencia getPersonaAusencia(String dni) {
		try {
			return (Ausencia) em.createNamedQuery("findAusenciasByDNI").setParameter("dni", dni).getSingleResult();
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe una ausencia asociada al usuario con DNI " + dni + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar un ausencia asociada al usuario con DNI " + dni + " de la base de datos", e);
		}
		return null;
	}

	public List<Persona> getRevisoresByDNI(String dni) {
		List<Persona> listRevisores = new ArrayList<Persona>();
		try {
			listRevisores = (List<Persona>) em.createNamedQuery(Persona.findRevisoresByDNI).setParameter("dni", dni).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de revisores para el usuario con DNI " + dni + " de la base de datos", e);
		}
		return listRevisores;
	}

	public Persona saveRevision(Persona persona) {
		if (persona.getRevision() != null) {
			List<Revisor> listRevisor = (List<Revisor>) em.createNamedQuery("getRevisoresByDNIPersona").setParameter("dni", persona.getNumIdentificacion()).getResultList();

			for (Revisor revisor : listRevisor) {
				em.remove(revisor);
			}
		}
		if (persona.getRevision().getListRevisor().size() == 0) {
			Revision revision = persona.getRevision();
			persona.setRevision(null);
			em.merge(persona);
			em.remove(em.contains(revision) ? revision : em.merge(revision));
		} else {
			Revision revision = em.merge(persona.getRevision());// Damos de alta el nuevo
			persona.setRevision(revision);
		}
		return persona;
	}

	public List<Persona> saveRevision(Persona persona, List<Persona> listColaboradores) {
		List<Persona> personasSinAsignar = new ArrayList<Persona>();
		Revision revision = (persona.getRevision() == null) ? new Revision() : persona.getRevision();// Recuperamos la revisi�n de la persona o en su defecto, creamos una nueva
		revision.setListRevisor(new ArrayList<Revisor>());// Instanciamos la losta de revisores
		revision.setPersona(persona);// Indicamos a que persona le corresponde la revisi�n
		for (Persona persAux : listColaboradores) {// Recorremos la lista de personas que resivimos para construir la lista
			Revision revisionPersona;
			try {
				revisionPersona = (Revision) em.createNamedQuery("getRevision").setParameter("numIdentificacion", persAux.getNumIdentificacion()).getSingleResult();
			} catch (NoResultException e) {
				revisionPersona = null;
			}

			if (revisionPersona == null) {// Si esa persona no tiene revisiones definidas... siempre es nulo?
				Revisor revisor = new Revisor();
				revisor.setPersona(persAux);
				revisor.setRevision(revision);
				revision.getListRevisor().add(revisor);// Lo añade
			} else {
				personasSinAsignar.add(persAux);
			}
		}
		persona.setRevision(revision);// Indicamos que a la persona le corresponde una nueva revisi�n
		if (persona.getRevision() != null) {// Siempre va a ser distinto de null
			// Recorremos los revisores anteriores y los eliminamos
			List<Revisor> listRevisor = (List<Revisor>) em.createNamedQuery("getRevisoresByDNIPersona").setParameter("dni", persona.getNumIdentificacion()).getResultList();

			for (Revisor revisor : listRevisor) {
				em.remove(revisor);
			}
		}
		if (persona.getRevision().getListRevisor().size() == 0) {// Si no tiene revisores, vamos a eliminar la revision
			Revision revision2 = persona.getRevision();
			persona.setRevision(null);
			em.merge(persona);
			em.remove(em.contains(revision2) ? revision2 : em.merge(revision2));
		} else {// Sino guarda la revision nueva
			persona.setRevision(em.merge(persona.getRevision()));// Damos de alta el nuevo
		}
		return personasSinAsignar;
	}

	public List<Persona> getListRevisoresDe(String dni) {
		List<Persona> listaRevisores = new ArrayList<Persona>();
		try {
			listaRevisores = (List<Persona>) em.createNamedQuery(Persona.findRevisoresDeByDNI).setParameter("dni", dni).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de revisores para el usuario con DNI " + dni + " de la base de datos", e);
		}
		return listaRevisores;
	}

	public List<Persona> getSustitucionByDNI(String dni) {
		List<Persona> listaSustituciones = new ArrayList<Persona>();
		try {
			listaSustituciones = (List<Persona>) em.createNamedQuery(Persona.getSustitucionByDNI).setParameter("dni", dni).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de sustituciones para el usuario con DNI " + dni + " de la base de datos", e);
		}
		return listaSustituciones;
	}

	public List<DocumentoPortafirmas> getDocumentosPersonalesbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT d FROM DocumentoPortafirmas d WHERE (d.subidoPorDNI LIKE :usuarioDNI)  AND (d.estadoDocumento.codigo = 'PEND' )");
		addingFilter(query, filtros);
		query.append(" order by d.fechaSubidaPortafirmas desc");

		return executeFiltrosBandejas(query, usuario, filtros, null);
	}

	public List<DocumentoPortafirmas> getDocumentosTramitadosbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT d FROM DocumentoPortafirmas d inner JOIN d.accionSobreDocumento");
		query.append(" a inner JOIN a.persona p WHERE p.numIdentificacion LIKE :usuarioDNI");
		addingFilter(query, filtros);
		query.append(" order by d.fechaSubidaPortafirmas desc)");

		return executeFiltrosBandejas(query, usuario, filtros, null);
	}


	public List<DocumentoPortafirmas> getDocumentosPendientesbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros) {

		List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();

		try {

			// Mis Documentos
			List<Long> listDocumentosRealizados = (List<Long>) em.createNamedQuery("findDocumentosRealizados").setParameter("usuarioDNI", usuario).getResultList();
			List<Long> listDocumentosPendienteSustituto = pendienteSustituto(usuario);
			List<Long> listDocumentosPendientePersonales = (List<Long>) em.createNamedQuery("findDocumentosPendientesPersonales").setParameter("usuarioDNI", usuario).getResultList();

			List<Long> listDocumentosIN = new ArrayList<Long>();
			listDocumentosIN.addAll(listDocumentosPendienteSustituto);
			listDocumentosIN.addAll(listDocumentosPendientePersonales);

			if (listDocumentosRealizados.size() == 0) {
				listDocumentosRealizados.add(new Long(-1));
			}
			if (listDocumentosIN.size() == 0) {
				listDocumentosIN.add(new Long(-1));
			}

			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct d FROM Persona p");
			query.append(" JOIN p.grupoPersona gp JOIN gp.grupo g JOIN g.circuito f JOIN f.documento d");
			query.append(" WHERE (d.id NOT IN (:listDocumentosNotIN))");
			query.append(" AND  (d.id IN (:listDocumentosIN))");
			query.append("");

			filtros.put("listDocumentosIN", listDocumentosIN);
			filtros.put("listDocumentosNotIN", listDocumentosRealizados);

			addingFilter(query, filtros);

			listDocumentoPortafirmas = executeFiltrosBandejas(query, usuario, filtros, false);

			// Los de mi colaborador findRevisoresDeByDNI
			Persona colaborador = null;
			try {
				colaborador = (Persona) em.createNamedQuery(Persona.findRevisoresDeByDNI).setParameter("dni", usuario).getSingleResult();
			} catch (Exception e) {
				logger.info("[FACHADA-SRV_PORTAFIRMAS] No hay colaborador para la persona indicada");
			}

			if (colaborador != null) {
				List<DocumentoPortafirmas> listDocumentosDeQuienColaboro = getDocumentosPendientesbyDNI(colaborador.getNumIdentificacion(), null);
				// Eliminamos los documentos sobre los que ya hayamos actuado
				for (int i = 0; i < listDocumentosDeQuienColaboro.size(); i++) {
					for (Long id : listDocumentosRealizados) {
						if (listDocumentosDeQuienColaboro.get(i).getId().equals(id)) {
							listDocumentosDeQuienColaboro.remove(listDocumentosDeQuienColaboro.get(i));
							i--;
							break;
						}
					}
				}
				listDocumentoPortafirmas.removeAll(listDocumentosDeQuienColaboro);
				listDocumentoPortafirmas.addAll(listDocumentosDeQuienColaboro);
			}

		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] Error recuperando el listado de documentos de la base de datos", e);
		}

		return listDocumentoPortafirmas;

	}

	public List<DocumentoPortafirmas> getDocumentosPendientesColaboradorbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT distinct d FROM Persona p ");
		query.append(" JOIN p.grupoPersona gp");
		query.append(" JOIN gp.grupo g");
		query.append(" JOIN g.circuito f");
		query.append(" JOIN f.documento");
		query.append(" d WHERE ");
		query.append("		(p.numIdentificacion = :usuarioDNI ");
		query.append("		OR p.numIdentificacion =(");// Los del usuario o los de la persona a la que sustituye
		query.append(" 		SELECT pa.numIdentificacion FROM Persona pa ");
		query.append("			JOIN pa.listAusencia a ");
		query.append("			JOIN a.sustituto s ");
		query.append("			JOIN s.persona p");
		query.append(" 		WHERE p.numIdentificacion = :usuarioDNI ");
		query.append("			AND a.fechaFin is null ");
		query.append("			group by pa.numIdentificacion)");// Ausencia no terminada
		query.append("		) ");
		query.append(" AND d.id IN ");
		query.append("		(SELECT d.id FROM Persona p "); // Los que han sido revisados por alguno de mis revisores
		query.append("		JOIN p.revision r JOIN r.listRevisor lr ");
		query.append("		JOIN lr.persona pr JOIN pr.accionSobreDocumento a ");
		query.append("		JOIN a.documento d ");
		query.append("		WHERE p.numIdentificacion = :usuarioDNI) ");// COMPLETAR
		query.append(" AND d.id NOT IN ( ");
		query.append("		SELECT d.id FROM Persona p"); // Y si el documento no esta en la lista de acciones que he realizado
		query.append(" JOIN p.accionSobreDocumento a ");
		query.append("		JOIN a.documento d ");
		query.append("		JOIN d.circuito f ");
		query.append("		WHERE (p.numIdentificacion = :usuarioDNI ");
		query.append("		OR p.numIdentificacion =(");
		query.append(" 		SELECT pa.numIdentificacion FROM Persona pa ");
		query.append("			JOIN pa.listAusencia a ");
		query.append("			JOIN a.sustituto s ");
		query.append("			JOIN s.persona p");
		query.append(" 		WHERE p.numIdentificacion = :usuarioDNI ");
		query.append("			AND a.fechaFin is null group by pa.numIdentificacion)");
		query.append(" 	)");
		query.append(" 	AND f.fechaCreacion < a.fechaAccion) ");
		addingFilter(query, filtros);
		query.append(" AND g.orden = f.ordenActivo order by d.fechaSubidaPortafirmas desc");

		return executeFiltrosBandejas(query, usuario, filtros, null);
	}

	public List<DocumentoPortafirmas> getDocumentosEnviadosbyDNIwithFiltros(String usuario, HashMap<String, Object> filtros) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT d FROM DocumentoPortafirmas d WHERE d.subidoPorDNI LIKE :usuarioDNI AND (d.estadoDocumento.codigo <> 'PEND')");
		addingFilter(query, filtros);
		query.append(" order by d.fechaSubidaPortafirmas desc");
		return executeFiltrosBandejas(query, usuario, filtros, null);
	}

	private StringBuilder addingFilter(StringBuilder query, HashMap<String, Object> filtros) {

		if (filtros.get("usuarioRemitente") != null) {
			query.append(" AND upper(d.subidoPorNombre) LIKE :usuarioRemitente");
		}
		if (filtros.get("fechaSolicitud") != null) {
			query.append(" AND d.fechaSubidaPortafirmas > :fechaSolicitud");
		}
		if (filtros.get("estadoDocumento") != null) {
			query.append(" AND upper(d.estadoDocumento.codigo) = :estadoDocumento");
		}
		if (filtros.get("numeroExpediente") != null) {
			query.append(" AND upper(d.idExpediente) LIKE :numeroExpediente");
		}
		if (filtros.get("nombreDocumento") != null) {
			query.append(" AND upper(d.nombre) LIKE :nombreDocumento");
		}
		if (filtros.get("asuntoDocumento") != null) {
			query.append(" AND upper(d.asunto) LIKE :asuntoDocumento");
		}
		if (filtros.get("sistemaOrigen") != null){
			query.append(" AND upper(d.sistemaOrigen.codigo) LIKE :sistemaOrigen");
		}
		return query;
	}

	private List<DocumentoPortafirmas> executeFiltrosBandejas(StringBuilder query, String usuario, HashMap<String, Object> filtros, Boolean conUser) {

		Query queryString = em.createQuery(query.toString());
		if (conUser == null || conUser) {
			queryString.setParameter("usuarioDNI", usuario);
		}

		if (filtros.get("usuarioRemitente") != null) {
			queryString.setParameter("usuarioRemitente", "%" + filtros.get("usuarioRemitente").toString().toUpperCase() + "%");
		}
		if (filtros.get("fechaSolicitud") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			try {
				Date fecha = sdf.parse((String) filtros.get("fechaSolicitud"));
				queryString.setParameter("fechaSolicitud", fecha);
			} catch (ParseException e) {
				logger.error("[FACHADA-SRV_PORTAFIRMAS] Formato de fecha erronea");
			}
		}
		if (filtros.get("estadoDocumento") != null) {
			queryString.setParameter("estadoDocumento", filtros.get("estadoDocumento").toString().toUpperCase());
		}
		if (filtros.get("numeroExpediente") != null) {
			queryString.setParameter("numeroExpediente", "%" + filtros.get("numeroExpediente").toString().toUpperCase() + "%");
		}
		if (filtros.get("nombreDocumento") != null) {
			queryString.setParameter("nombreDocumento", "%" + filtros.get("nombreDocumento").toString().toUpperCase() + "%");
		}
		if (filtros.get("asuntoDocumento") != null) {
			queryString.setParameter("asuntoDocumento", "%" + filtros.get("asuntoDocumento").toString().toUpperCase() + "%");
		}
		if (filtros.get("sistemaOrigen") != null){
			queryString.setParameter("sistemaOrigen", filtros.get("sistemaOrigen").toString().toUpperCase());
		}
		if (filtros.get("listDocumentosNotIN") != null) {
			queryString.setParameter("listDocumentosNotIN", filtros.get("listDocumentosNotIN"));
		}
		if (filtros.get("listDocumentosIN") != null) {
			queryString.setParameter("listDocumentosIN", filtros.get("listDocumentosIN"));
		}
		
		return queryString.getResultList();
	}

	public Ausencia saveAusencia(Ausencia ausencia) {
		// Obtener lista de ausencias de una persona que están a nulo
		List<Ausencia> listAusencias = (List<Ausencia>) em.createNamedQuery("findAusenciasByDNI").setParameter("dni", ausencia.getAusentado().getNumIdentificacion()).getResultList();

		if (listAusencias.size() > 0) {
			for (Ausencia ausenciaAux : listAusencias) {
				ausenciaAux.setFechaFin(new Date());
				em.merge(ausenciaAux);
			}
		}
		Ausencia resultado = em.merge(ausencia);
		return resultado;
	}

	public List<DocumentoWSDL> getDocumentoWSDLByUri(String uri) {
		List<DocumentoWSDL> listaDocumentoWSDL = new ArrayList<DocumentoWSDL>();
		try {
			listaDocumentoWSDL = (List<DocumentoWSDL>) em.createNamedQuery("DocumentoWSDL.findWSDLByURI").setParameter("uri", uri).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar los WSDLs asociados al documento con uri " + uri + "  de la base de datos", e);
		}
		return listaDocumentoWSDL;
	}

	public BackOffice getBackOfficeByUsername(String username) {
		try {
			return (BackOffice) em.createNamedQuery("findBackofficeByUsername").setParameter("username", username).getSingleResult();			
		} catch (NoResultException e) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] No existe un backoffice con username " + username + " en la base de datos");
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el backoffice con username " + username + "  de la base de datos", e);
		}
		
		return null;
	}

	public List<ProcesoFirma> getProcesoFirmadoByUri(String uri) {
		List<ProcesoFirma> listaProcesoFirmado = new ArrayList<ProcesoFirma>();
		try {
			listaProcesoFirmado = (List<ProcesoFirma>)em.createNamedQuery("findProcesoFirmadoByUri").setParameter("uri", uri).getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de pasos de firma asociados al documento con uri " + uri + "  de la base de datos", e);
		}
		return listaProcesoFirmado;
		
	}

	public List<Long> pendienteSustituto(String usuario) {
		List<Long> resultAllParametes = new ArrayList<Long>();
		List<Persona> listPersona = this.getSustitucionByDNI(usuario);
		if (listPersona.size() > 0) {// Comprobar si estoy sustituyendo a alguien
			List<Long> listIdMerge = new ArrayList<Long>();
			List<DocumentoPortafirmas> docsPendientes = new ArrayList<DocumentoPortafirmas>();
			for (Persona sustituto : listPersona) {
				docsPendientes.addAll(getDocumentosPendientesbyDNI(sustituto.getNumIdentificacion(), usuario));
			}

			for (DocumentoPortafirmas documento : docsPendientes) {
				listIdMerge.add(documento.getId());
			}

			if (listIdMerge.isEmpty()) {
				listIdMerge.add(new Long(-1));
			}
			resultAllParametes = (List<Long>) em.createNamedQuery("findDocumentosPendientesSustituto").setParameter("usuarioDNI", usuario).setParameter("docsSustituto", listIdMerge).getResultList();
			return resultAllParametes;
		}
		return resultAllParametes;
	}
	
	public List<String> findWhiteListByURI(String uri){
		List<String> whiteList = new ArrayList<String>();
		whiteList.addAll((List<String>) em.createNamedQuery(Persona.findPersonasEnCircuitoByURI).setParameter("uri", uri).getResultList());
		whiteList.addAll((List<String>) em.createNamedQuery(Persona.findPersonasEnProcesoByURI).setParameter("uri", uri).getResultList());
		whiteList.addAll((List<String>) em.createNamedQuery(Persona.findPersonasAusenciaEnCircuitoByURI).setParameter("uri", uri).getResultList());
		whiteList.addAll((List<String>) em.createNamedQuery(Persona.findPersonasRevisoresEnCircuitoByURI).setParameter("uri", uri).getResultList());
		DocumentoPortafirmas documento = (DocumentoPortafirmas) em.createNamedQuery("findDocumentoPortafirmasbyUri").setParameter("uri", uri).getSingleResult();
		whiteList.add(documento.getSubidoPorDNI());
		return whiteList;
	}
	
	public List<BackOffice> getAllBackoffice(){
		List<BackOffice> listaBackoffices = new ArrayList<BackOffice>();
		try {
			listaBackoffices = (List<BackOffice>) em.createNamedQuery("findAllBackoffice").getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el listado de Backoffices de la base de datos", e);
		}
		return listaBackoffices;	
	}
	
	public List<CatPropiedadesConfiguracion> getPropiedadesConfiguracion() {
		List<CatPropiedadesConfiguracion> propiedades = null;
		try {
			propiedades = em.createNamedQuery(CatPropiedadesConfiguracion.findByPrefix).setParameter("id", "portafirmas.%").getResultList();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] Error al intentar recuperar propiedades de configuracion del Portafirmas de la base de datos", e);
		}
		return propiedades;
	}
	
	public Departamento getDepartamentoById(String id) {
		Departamento departamento = null;

		try {
			departamento = (Departamento) em.createNamedQuery("Departamento.getDepartamentoById").setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			logger.error("[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el departamento con Id " + id + " de la base de datos", e);
			logger.error("[FACHADA-SRV_PORTAFIRMAS] Incluir en la tabla PF_CAT_DEPARTAMENTO el registro correspondiente a" + id);
		}

		return departamento;		
	}

	public List<BackOffice> consultarDescripcionesBackoffices() {
		return getAllBackoffice();
	}

	@Override
	public List<DataBaseUser> obtenerUsuarios(List<String> departamentos) {
		List<DataBaseUser> users = null;
		if (null!=departamentos) {
			users = (List<DataBaseUser>) em.createNamedQuery(DataBaseUser.findByDepartments).setParameter("businessCategory", departamentos).getResultList();
		}else {
			users = (List<DataBaseUser>) em.createNamedQuery(DataBaseUser.findAllDepartments).getResultList();
		}
		return users;
	}
}
