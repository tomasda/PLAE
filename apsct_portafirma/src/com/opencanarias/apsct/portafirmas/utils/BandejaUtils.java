package com.opencanarias.apsct.portafirmas.utils;

import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.plae.dto.Document;
import com.opencanarias.apsct.portafirmas.bean.DocumentosBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.exception.ElementNotFoundException;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;

public class BandejaUtils {

	protected static final Logger logger = Logger.getLogger(BandejaUtils.class);
	
	public static void refresh(){
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		documentosBean.setDocumentosList(Services.getSrvPortafirmasFacadeRemote().buscarPorBandeja(usuarioBean.getNumIdentificacion(),
				documentosBean.getTitulo().toUpperCase(), null));
	}
	
	public static DocumentoPortafirmas setDocumentoSeleccionado(DocumentosBean documentosBean){
		String uri = FacesUtils.getParam(Constantes.URI);

		if (uri != null && documentosBean.getDocumentosList() != null) {
			for (DocumentoPortafirmas docAux : documentosBean.getDocumentosList()) {
				if (docAux.getUri().equals(uri)) {
					UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
					LoggerUtils.showInfo(logger, "Se identifica el documento seleccionado", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
					documentosBean.setDocumentoSeleccionado(docAux);
					return docAux;
				}
			}
		}
		documentosBean.setDocumentoSeleccionado(null);
		return null;
	}
	
	public static Document searchDocumentoItemInList(List<Document> listDocumentoItem, String uri) throws ElementNotFoundException{
		
		for (Document documentoItem : listDocumentoItem){
			if (uri.equals(documentoItem.getUri())){
				return documentoItem;
			}
		}
		
		throw new ElementNotFoundException();
	}
	
	public static Boolean isDocumentoItemInList(List<Document> listDocumentoItem, String uri) throws ElementNotFoundException{
		
		for (Document documentoItem : listDocumentoItem){
			if (uri.equals(documentoItem.getUri())){
				return true;
			}
		}
		
		return false;
	}
	
}
