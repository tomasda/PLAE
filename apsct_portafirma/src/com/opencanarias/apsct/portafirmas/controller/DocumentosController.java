package com.opencanarias.apsct.portafirmas.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import java.util.Base64;
import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;

import com.opencanarias.apsct.portafirmas.bean.CircuitoBean;
import com.opencanarias.apsct.portafirmas.bean.CommonBean;
import com.opencanarias.apsct.portafirmas.bean.DocumentosBean;
import com.opencanarias.apsct.portafirmas.bean.EstadoBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.helpers.DocumentosHelper;
import com.opencanarias.apsct.portafirmas.helpers.FechaAccionComparator;
import com.opencanarias.apsct.portafirmas.helpers.PortafirmasHelper;
import com.opencanarias.apsct.portafirmas.utils.BandejaUtils;
import com.opencanarias.apsct.portafirmas.utils.CatalogosUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.PerformanceUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.dto.UsuarioItem;
import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;

@ManagedBean
public class DocumentosController implements Serializable{

	private static final long serialVersionUID = -821972312311050347L;
	protected static final Logger logger = Logger.getLogger(DocumentosController.class);

	public void error(ActionEvent event) throws PortafirmasFacadeException {
		throw new PortafirmasFacadeException();
	}

	public DocumentoPortafirmas setDocumentoSeleccionado(DocumentosBean documentosBean) {
		return BandejaUtils.setDocumentoSeleccionado(documentosBean);
	}

	public String goEstado() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Redireccion a 'Estado'", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		setDocumentoSeleccionado(documentosBean);
		EstadoBean estadoBean = new EstadoBean();
		if (estadoBean.getDocumento() != null) {
			return "estado";
		}
		return null;
	}

	public String goDefinirFlujo() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Redireccion a 'Definir circuito'", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean("circuitoBean");
		Boolean validador = true;

		// Almacenamos en el bean el documento seleccionado, en caso de que lo haya
		setDocumentoSeleccionado(documentosBean);
		List<DocumentoPortafirmas> listAux = new ArrayList<DocumentoPortafirmas>();
		if (documentosBean.getDocumentoSeleccionado() != null) {
			listAux.add(documentosBean.getDocumentoSeleccionado());
			documentosBean.setSelectedDocs(listAux);
		}

		if (documentosBean.getSelectedDocs() == null || documentosBean.getSelectedDocs().size() == 0) {
			String info = "Debe haber seleccionado al menos un documento";
			PortafirmasHelper.mostrarMensajeError(info, logger, null);
			validador = false;
		} else {

			for (DocumentoPortafirmas documento : documentosBean.getSelectedDocs()) {

				if (documento.getCircuito() != null) {
					String info = "El documento " + documento.getNombre() + " ya tiene definido un circuito, por favor, deseleccionelo";
					PortafirmasHelper.mostrarMensajeError(info, logger, null);
					validador = false;
					break;
				} else if (documento.getAsunto() == null || documento.getAsunto().equals("")) {
					String info = "El documento " + documento.getNombre() + " debe tener un asunto asignado, haga click en el e indiquelo";
					PortafirmasHelper.mostrarMensajeError(info, logger, null);
					validador = false;
					break;
				}
			}
		}

		if (validador) {
			if (circuitoBean != null) {
				circuitoBean.setCircuito(new CircuitoEntity());
			}
			return "definirFlujo";
		}
		return null;
	}

	public String goUpload() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Redireccion a 'Subida de documentos'", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		return "upload";
	}

	public String goAusente() throws PortafirmasFacadeException {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Redireccion a 'Gestion de ausencias'", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		if (permitirAccesoFirmantes(usuarioBean)) {
			return Constantes.AUSNECIA;
		}
		return null;
	}

	public String goColaboradores() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Redireccion a 'Gestion de revisores'", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());

		if (permitirAccesoFirmantes(usuarioBean)) {
			try {
				List<Persona> listRevisoresDe = Services.getSrvPortafirmasFacadeRemote().getListRevisoresDe(usuarioBean.getNumIdentificacion());
				if (listRevisoresDe != null && listRevisoresDe.size() > 0) {
					CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
					commonBean.setMessage(Constantes.WARN_AUSENT_YET);
					FacesUtils.verDialog("messageDialog");
				} else {
					return Constantes.COLABORADORES;
				}

			} catch (PortafirmasFacadeException e) {
				CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
				commonBean.setMessage("Lo sentimos, pero usted ya es revisor de una persona");
				FacesUtils.verDialog("messageDialog");
			}
		}
		return null;
	}

	private boolean permitirAccesoFirmantes(UsuarioBean usuarioBean) {
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		if (usuarioBean.getEntidadPersona() == null) {
			String info = "Solo los usuarios con perfil de firmante pueden acceder a esta seccion";
			commonBean.setMessage(info);
			FacesUtils.verDialog("messageDialog");
			return false;
		}
		return true;
	}

	public void upload(FileUploadEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Subiendo documento " + event.getFile().getFileName(), FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		Date start = new Date();
		if (event.getFile() != null) {
	        String paramName = "title["+event.getFile().getFileName()+"]";
	        String asunto = (String)FacesUtils.getRequestParameterValue(paramName);
			
			es.apt.ae.facade.entities.portafirmas.Persona persona = new es.apt.ae.facade.entities.portafirmas.Persona();
			persona.setNumIdentificacion(usuarioBean.getNumIdentificacion());
			persona.setNombre(usuarioBean.getNombre());
			persona.setApellido1(usuarioBean.getApellido1());
			persona.setApellido2(usuarioBean.getApellido2());

			DocumentoPortafirmas documento = new DocumentoPortafirmas();			
			documento.setNombre(event.getFile().getFileName());
			documento.setAsunto(asunto);
			documento.setFechaSubidaPortafirmas(new Date());
			documento.setSubidoPorDNI(((UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN)).getNumIdentificacion());
			documento.setTipoFirma("XAdES");
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(persona.getNombre());
			if (persona.getApellido1() != null) {
				stringBuffer.append(" " + persona.getApellido1());
			}
			if (persona.getApellido2() != null) {
				stringBuffer.append(" " + persona.getApellido2());
			}
			documento.setSubidoPorNombre(stringBuffer.toString());
			try {
				documento.setEstadoDocumento(CatalogosUtils.getEstadoDocumentoByCodigo(Constantes.PEND));
				Services.getSrvPortafirmasFacadeRemote().altaDocumento(usuarioBean.getNumIdentificacion(), documento, 
						Base64.getEncoder().encode(event.getFile().getContents()), event.getFile().getContentType());
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, e.getMessage(), e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
				String message = "Error al subir el documento " + event.getFile().getFileName();
				commonBean.setMessage(message);
				FacesUtils.verDialog("messageDialog");
				LoggerUtils.showError(logger, message, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		}
		LoggerUtils.showInfo(logger, "Tiempo transcurrido desde la primera subida", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		LoggerUtils.showInfo(logger, PerformanceUtils.tiempoTranscurridoDesde(start.getTime()), FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
	}

	public String actualizarDocumento() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Actualizando documento", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		try {
			Services.getSrvPortafirmasFacadeRemote().actualizarDocumento(usuarioBean.getNumIdentificacion(), documentosBean.getDocumentoSeleccionado());
			LoggerUtils.showInfo(logger, "Documento actualizado", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			return Constantes.BACK;
		} catch (PortafirmasFacadeException e) {
			String info = "No se ha podido actualizar el documento";
			LoggerUtils.showError(logger, info, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			commonBean.setMessage(info);
			FacesUtils.verDialog("messageDialog");
		}
		return null;
	}

	public HashMap<String, Object> crearFiltros() {
		HashMap<String, Object> filtros = new HashMap<String, Object>();
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);

		if (documentosBean.getUsuarioRemitente() != null && !documentosBean.getUsuarioRemitente().equals("")) {
			filtros.put(Constantes.USUARIO_REMITENTE, documentosBean.getUsuarioRemitente());
		}
		if (documentosBean.getFechaSolicitud() != null && !documentosBean.getFechaSolicitud().equals("")) {
			Calendar cal = Calendar.getInstance();
			if (documentosBean.getFechaSolicitud().equals(Constantes.DAY)) {
				cal.add(Calendar.DATE, -1);
			} else if (documentosBean.getFechaSolicitud().equals(Constantes.WEEK)) {
				cal.add(Calendar.WEEK_OF_MONTH, -1);
			} else if (documentosBean.getFechaSolicitud().equals(Constantes.MONTH)) {
				cal.add(Calendar.MONTH, -1);
			} else if (documentosBean.getFechaSolicitud().equals(Constantes.QUARTER)) {
				cal.add(Calendar.MONTH, -3);
			} else if (documentosBean.getFechaSolicitud().equals(Constantes.MIDDLE)) {
				cal.add(Calendar.MONTH, -6);
			} else if (documentosBean.getFechaSolicitud().equals(Constantes.YEAR)) {
				cal.add(Calendar.YEAR, -1);
			}
			Date filtroFecha = cal.getTime();
			filtros.put(Constantes.FECHA_SOLICITUD, filtroFecha.toString());
		}
		if (documentosBean.getEstadoDocumento() != null && !documentosBean.getEstadoDocumento().equals("")) {
			filtros.put(Constantes.ESTADO_DOCUMENTO, documentosBean.getEstadoDocumento());
		}
		if (documentosBean.getNumeroExpediente() != null && !documentosBean.getNumeroExpediente().equals("")) {
			filtros.put(Constantes.NUMERO_EXPEDIENTE, documentosBean.getNumeroExpediente());
		}
		if (documentosBean.getNombreDocumento() != null && !documentosBean.getNombreDocumento().equals("")) {
			filtros.put(Constantes.NOMBRE_DOCUMENTO, documentosBean.getNombreDocumento());
		}
		if (documentosBean.getAsuntoDocumento() != null && !documentosBean.getAsuntoDocumento().equals("")) {
			filtros.put(Constantes.ASUNTO_DOCUMENTO, documentosBean.getAsuntoDocumento());
		}
		if (documentosBean.getOrigenDocumento() != null && !documentosBean.getOrigenDocumento().equals("") ){
			filtros.put(Constantes.SISTEMA_ORIGEN, documentosBean.getOrigenDocumento());
		}
		return filtros;
	}

	public String redirectPersonal() {
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		documentosBean.setTitulo(Constantes.PERSONAL);

		List<DocumentoPortafirmas> listaDocumentos = Services.getSrvPortafirmasFacadeRemote().buscarPorBandeja(((UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN)).getNumIdentificacion(),
				Constantes.PERSONAL.toUpperCase(), null);
		documentosBean.setDocumentosList(listaDocumentos);

		if (documentosBean.getSelectedDocs() != null) {
			documentosBean.getSelectedDocs().clear();
			DocumentosHelper.ordenarListaDocumentos(null);
		}
		documentosBean.setDocumentoSeleccionado(null);
		return (Constantes.BACK);
	}

	public void loadBandeja(ActionEvent event) {
		String param = (String) event.getComponent().getAttributes().get("nombreBandeja");
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		documentosBean.setTitulo(param);

		List<DocumentoPortafirmas> listaDocumentos = Services.getSrvPortafirmasFacadeRemote().buscarPorBandeja(((UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN)).getNumIdentificacion(),
				param.toUpperCase(), null);
		documentosBean.setDocumentosList(listaDocumentos);
		LoggerUtils.showInfo(logger, "[" + param.toUpperCase() + "]: " + Constantes.INFO_TOTAL_DOCUMENTS + listaDocumentos.size(), FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		if (documentosBean.getSelectedDocs() != null) {
			documentosBean.getSelectedDocs().clear();
			documentosBean.setDocumentoSeleccionado(null);
			DocumentosHelper.ordenarListaDocumentos(null);
		}
	}
	
	public void loadBandejaPrincipalUsuario() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		try {
			// Recuperar la informacion del usuario seleccionado
			String username = usuarioBean.getUsername();
			UsuarioItem usuarioConsulta = CatalogosUtils.getUsuarioByUsername(username);
			usuarioBean.setUserToConsult(usuarioConsulta);

			// Calcular la bandeja por defecto para el usuario
			String nombreBandeja = null;
			if (usuarioBean.getEntidadPersona() == null) {
				nombreBandeja = Constantes.PERSONAL;
			} else {
				nombreBandeja = Constantes.PENDIENTES;
			}
			documentosBean.setTitulo(nombreBandeja);
			
			// Recuperar el listado de documentos de la bandeja correspondiente
			List<DocumentoPortafirmas> listaDocumentos = Services.getSrvPortafirmasFacadeRemote().buscarPorBandeja(((UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN)).getNumIdentificacion(),
					nombreBandeja.toUpperCase(), null);
			documentosBean.setDocumentosList(listaDocumentos);
			LoggerUtils.showInfo(logger, "[" + nombreBandeja.toUpperCase() + "]: " + Constantes.INFO_TOTAL_DOCUMENTS + listaDocumentos.size(), FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			if (documentosBean.getSelectedDocs() != null) {
				documentosBean.getSelectedDocs().clear();
				documentosBean.setDocumentoSeleccionado(null);
				DocumentosHelper.ordenarListaDocumentos(null);
			}
		} catch (Exception e) {
			LoggerUtils.showError(logger, "No se ha podido recuperar la informacion del usuario a consultar", null, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			PortafirmasHelper.mostrarMensajeError("No se ha podido recuperar la informaci�n del usuario a consultar.", logger, null);
		}
	}
	
	public void refreshBandeja(ActionEvent event) {
		BandejaUtils.refresh();
	}

	public void filtrar(ActionEvent event) {
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		List<DocumentoPortafirmas> listaDocumentos = Services.getSrvPortafirmasFacadeRemote().buscarPorBandeja(((UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN)).getNumIdentificacion(),
				documentosBean.getTitulo().toUpperCase(), crearFiltros());
		documentosBean.setDocumentosList(listaDocumentos);
		if (documentosBean.getSelectedDocs() != null){
			documentosBean.getSelectedDocs().clear();
		}
		documentosBean.setDocumentoSeleccionado(null);

	}

	public void verDocumento() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Accediendo a descarga del documento", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("verDocumento?uri=" + FacesUtils.getParam("verIdURI") + "&usuario=" + usuarioBean.getUsername());
		} catch (IOException e) {
			LoggerUtils.showError(logger, "Error en la descarga del documento", e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
	}

	public void ordenarDocumentosPorCampo(ActionEvent event) {
		DocumentosHelper.ordenarListaDocumentos(null);
	}

	public String cleanFilter(ActionEvent event){
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		documentosBean.setUsuarioRemitente(null);
		documentosBean.setFechaSolicitud(null);
		documentosBean.setEstadoDocumento(null);
		documentosBean.setNombreDocumento(null);
		documentosBean.setAsuntoDocumento(null);
		documentosBean.setOrigenDocumento(null);
		return null;
	}
	
	public String ordenarBandeja(AjaxBehaviorEvent event){
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "He cambiado el valor del campo ordenar", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		DocumentosHelper.ordenarListaDocumentos(documentosBean.getCampoOrden());
		return null;
	}

	@SuppressWarnings("unchecked")
	public int mySort(Object s1, Object s2) {
		//TODO: en ocasiones no devuelve el orden correcto porque el documento no tiene un proceso, valorar asignar una accion subida en el historico.
		List<ProcesoFirma> listProceso1 = (List<ProcesoFirma>) s1;
		List<ProcesoFirma> listProceso2 = (List<ProcesoFirma>) s2;
		if(listProceso1.size() > 0){
			DocumentoPortafirmas doc1 = listProceso1.get(0).getDocumento();
			if (listProceso2.size() > 0){
				DocumentoPortafirmas doc2 = listProceso2.get(0).getDocumento();
				FechaAccionComparator fechaAccionComparator = new FechaAccionComparator();
				
				return fechaAccionComparator.compare(doc1, doc2);
			}
		}
		return -1;
    }
	
}
