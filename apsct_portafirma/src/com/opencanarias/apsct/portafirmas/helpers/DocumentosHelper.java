package com.opencanarias.apsct.portafirmas.helpers;

import java.util.Collections;
import java.util.Comparator;

import com.opencanarias.apsct.portafirmas.bean.DocumentosBean;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;

import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;

public class DocumentosHelper {
	public static void ordenarListaDocumentos(String campo){
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		if (campo == null || campo.equals(Constantes.ORDER_FECHA)){
//			FechaComparator comparator = new FechaComparator();
			FechaAccionComparator comparator = new FechaAccionComparator();
			Collections.sort(documentosBean.getDocumentosList(),comparator);
			
			if (!documentosBean.getTipoOrden().equals(Constantes.ORDER_DESC) && 
					!documentosBean.getTipoOrden().equals(Constantes.ORDER_ASC)	){
				Collections.reverse(documentosBean.getDocumentosList());
			}
		} else if (campo.equals(Constantes.ORDER_ASUNTO)){
			AsuntoComparator comaprator = new AsuntoComparator();
			Collections.sort(documentosBean.getDocumentosList(),comaprator);
		} else if (campo.equals(Constantes.ORDER_ESTADO)){
			EstadoComparator comaprator = new EstadoComparator();
			Collections.sort(documentosBean.getDocumentosList(),comaprator);
		} else if (campo.equals(Constantes.ORDER_NOMBRE_DOC)){
			NombreComparator comaprator = new NombreComparator();
			Collections.sort(documentosBean.getDocumentosList(),comaprator);
		} else if (campo.equals(Constantes.ORDER_SIST_ORIGEN)){
			SistemaComparator comaprator = new SistemaComparator();
			Collections.sort(documentosBean.getDocumentosList(),comaprator);
		} else if (campo.equals(Constantes.ORDER_USUARIO)){
			UsuarioComparator comaprator = new UsuarioComparator();
			Collections.sort(documentosBean.getDocumentosList(),comaprator);
		}
		
		if (documentosBean.getTipoOrden().equals(Constantes.ORDER_DESC)){
			Collections.reverse(documentosBean.getDocumentosList());
		}
	}

}

class FechaComparator implements Comparator<DocumentoPortafirmas> {
	@Override
	public int compare(DocumentoPortafirmas doc1, DocumentoPortafirmas doc2) {
		return doc1.getFechaSubidaPortafirmas().getTime() < doc2.getFechaSubidaPortafirmas().getTime() ? -1 : doc1.getFechaSubidaPortafirmas().getTime() == doc2.getFechaSubidaPortafirmas().getTime() ? 0 : 1;
	}
}

class AsuntoComparator implements Comparator<DocumentoPortafirmas>{
	@Override
	public int compare(DocumentoPortafirmas doc1, DocumentoPortafirmas doc2) {			
		return doc1.getAsunto().toUpperCase().compareTo(doc2.getAsunto().toUpperCase()) <  0 ? -1 : doc1.getAsunto().toUpperCase().equals(doc2.getAsunto().toUpperCase()) ? 0 : 1;
	}
}

class EstadoComparator implements Comparator<DocumentoPortafirmas>{
	@Override
	public int compare(DocumentoPortafirmas doc1, DocumentoPortafirmas doc2) {			
		return doc1.getEstadoDocumento().getCodigo().toUpperCase().compareTo(doc2.getEstadoDocumento().getCodigo().toUpperCase()) <  0 ? -1 : doc1.getEstadoDocumento().getCodigo().toUpperCase().equals(doc2.getEstadoDocumento().getCodigo().toUpperCase()) ? 0 : 1;
	}
}

class NombreComparator implements Comparator<DocumentoPortafirmas>{
	@Override
	public int compare(DocumentoPortafirmas doc1, DocumentoPortafirmas doc2) {
		return doc1.getNombre().toUpperCase().compareTo(doc2.getNombre().toUpperCase()) <  0 ? -1 : doc1.getNombre().toUpperCase().equals(doc2.getNombre().toUpperCase()) ? 0 : 1;
	}
}

class SistemaComparator implements Comparator<DocumentoPortafirmas>{
	@Override
	public int compare(DocumentoPortafirmas doc1, DocumentoPortafirmas doc2) {
		return doc1.getSistemaOrigen().getCodigo().toUpperCase().compareTo(doc2.getSistemaOrigen().getCodigo().toUpperCase()) <  0 ? -1 : doc1.getSistemaOrigen().getCodigo().toUpperCase().equals(doc2.getSistemaOrigen().getCodigo().toUpperCase()) ? 0 : 1;
	}
}

class UsuarioComparator implements Comparator<DocumentoPortafirmas>{
	@Override
	public int compare(DocumentoPortafirmas doc1, DocumentoPortafirmas doc2) {
		return doc1.getSubidoPorNombre().toUpperCase().compareTo(doc2.getSubidoPorNombre().toUpperCase()) <  0 ? -1 : doc1.getSubidoPorNombre().toUpperCase().equals(doc2.getSubidoPorNombre().toUpperCase()) ? 0 : 1;
	}
}
