package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoListadoDocumentos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> documentosOk;
	private List<String> documentosError;
	
	
	public ResultadoListadoDocumentos() {
		documentosOk = new ArrayList<String>();
		documentosError = new ArrayList<String>();
	}

	
	public List<String> getDocumentosOk() {
		return documentosOk;
	}
	public void setDocumentosOk(List<String> documentosOk) {
		this.documentosOk = documentosOk;
	}
	public List<String> getDocumentosError() {
		return documentosError;
	}
	public void setDocumentosError(List<String> documentosError) {
		this.documentosError = documentosError;
	}
	
}
