package com.opencanarias.ejb.portafirmas.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import com.opencanarias.ejb.portafirmas.dao.IPortafirmasFacadeDAO;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.security.authentication.AuthenticationHelper;
import com.opencanarias.utils.Constantes;

import es.apt.ae.facade.entities.BackOffice;
import es.apt.ae.facade.entities.portafirmas.Departamento;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.DocumentoWSDL;
import es.apt.ae.facade.repository.dto.DocumentoRepositorioItem;
import es.apt.ae.facade.ws.params.portafirmas.common.InformacionSolicitante;
import es.apt.ae.facade.ws.params.portafirmas.in.Parametros;
import es.apt.ae.facade.ws.params.portafirmas.in.alta.AltaDocsPortafirmas;
import es.apt.ae.facade.ws.params.portafirmas.in.alta.TipoFirmaEnum;

public class AltaDocumentosCore {
	
	private IPortafirmasFacadeDAO portafirmasDao;
	
	public AltaDocumentosCore(IPortafirmasFacadeDAO portafirmasDao) {
		this.portafirmasDao = portafirmasDao;
	}
	
	
	public  DocumentoPortafirmas crearModeloDocumento(Parametros params, List<DocumentoPortafirmas> listDocumentoPortafirmas, BackOffice backoffice){
		DocumentoPortafirmas documentoPortafirmas = new DocumentoPortafirmas();
		AltaDocsPortafirmas parametrosAlta = params.getParametrosAlta();
		InformacionSolicitante informacionSolicitante = parametrosAlta.getUsuarioSolicitante().getInformacionSolicitante();
		if (listDocumentoPortafirmas.isEmpty()) {// Creamos un modelo de documento
			StringBuilder nombreApellidos = new StringBuilder();
			nombreApellidos.append(informacionSolicitante.getNombre());
			nombreApellidos.append((informacionSolicitante.getApellido1() == null) ? "" : (" " + informacionSolicitante.getApellido1()));
			nombreApellidos.append((informacionSolicitante.getApellido2() == null) ? "" : (" " + informacionSolicitante.getApellido2()));
			documentoPortafirmas.setSubidoPorNombre(nombreApellidos.toString());
			documentoPortafirmas.setSubidoPorDNI(informacionSolicitante.getDNINIE());
			documentoPortafirmas.setSistemaOrigen(backoffice);
			documentoPortafirmas.setFechaSubidaPortafirmas(new Date());
			documentoPortafirmas.setListUrl(new ArrayList<DocumentoWSDL>());
			documentoPortafirmas.setMailCreador(informacionSolicitante.getCorreoNotificacion());
			for (String url : params.getParametrosAlta().getListaWSDLCallback().getUrl()) {
				DocumentoWSDL documentoWsdl = new DocumentoWSDL();
				documentoWsdl.setUrl(url);
				documentoWsdl.setDocumento(documentoPortafirmas);
				documentoWsdl.setInformarEstadoIntermedio(backoffice!=null?backoffice.getInformarEstadoIntermedioPF():false);
				documentoPortafirmas.getListUrl().add(documentoWsdl);
			}
			setOrigen(documentoPortafirmas, backoffice, parametrosAlta.getDepartamento());
			documentoPortafirmas.setAsunto(parametrosAlta.getAsunto());
			documentoPortafirmas.setDocRelacionada(parametrosAlta.getObservaciones());
			documentoPortafirmas.setUrlDetalleOrigen(parametrosAlta.getUrlDetalleOrigen());
			documentoPortafirmas.setTipoFirma(parametrosAlta.getTipoFirmaEnum().toString());
		} else {// Modificamos la fecha de subida por la fecha de envio //Seteamos el origen del envio
			for (DocumentoPortafirmas doc : listDocumentoPortafirmas) {
				doc.setFechaSubidaPortafirmas(new Date());
				doc.setTipoFirma(parametrosAlta.getTipoFirmaEnum().toString());// TODO: solo si el documento es un pdf
				setOrigen(doc, backoffice, parametrosAlta.getDepartamento());
				//setOrigen(doc, backoffice, parametrosAlta.getUrlDetalleOrigen());
			}
		}
		return documentoPortafirmas;
	}
	
	public  InformacionSolicitante construirSolicitante(Parametros params) throws PortafirmasFacadeException {
			try {
				String usuario = params.getCredencial().getUsuario();
				String password = params.getCredencial().getPassword();
				String username = params.getParametrosAlta().getUsuarioSolicitante().getUsername();
					InformacionSolicitante informacionSolicitante = AuthenticationHelper.getInformationByDisplayName(usuario, password, username);
				return informacionSolicitante;
			} catch (GenericFacadeException e) {
				throw new PortafirmasFacadeException(Constantes.FWS_COD_ERR_AUTHENTICATION, e);
			} catch (NamingException e) {
				throw new PortafirmasFacadeException(Constantes.FWS_COD_ERR_AUTHENTICATION, e);
			} catch (Exception e) {
				throw new PortafirmasFacadeException(Constantes.FWS_COD_ERR_AUTHENTICATION, e);
			}
	}
	
	public void setOrigen(DocumentoPortafirmas documento, BackOffice backoffice, String idDpto) {
		documento.setSistemaOrigen(backoffice);
		if (idDpto != null) {
			Departamento departamento = portafirmasDao.getDepartamentoById(idDpto);
			documento.setDepartamento(departamento);
		}
	}
	

	public static boolean comprobarFormatoDocumentosPDF(List<DocumentoRepositorioItem> documentosRepositorio, TipoFirmaEnum tipoFirmaEnum) {
		if (tipoFirmaEnum.equals(TipoFirmaEnum.PADES)) {
			for (DocumentoRepositorioItem docRepositorio:documentosRepositorio) {
				String nombreDoc = docRepositorio.getNombre();
				if (!nombreDoc.endsWith(".pdf")) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean comprobarFormato(List<DocumentoRepositorioItem> documentosRepositorio) {
		for (DocumentoRepositorioItem docRepositorio:documentosRepositorio) {
			String nombreDoc = docRepositorio.getNombre();
			if (!(nombreDoc.endsWith(".pdf") || nombreDoc.endsWith(".odt") || nombreDoc.endsWith(".doc") || nombreDoc.endsWith(".docx"))) {
				return false;
			}
			break;
		}
		return true;
	}
	
}
