package es.apt.ae.facade.dto;

public class FirmasDocumentoInfo {

	private String[] firmas;
	private boolean compulsa;
	private String csv;
	
	
	public FirmasDocumentoInfo(String[] firmas, boolean compulsa) {
		super();
		this.firmas = firmas;
		this.compulsa = compulsa;
	}
	
	public FirmasDocumentoInfo(String[] firmas, boolean compulsa, String csv) {
		super();
		this.firmas = firmas;
		this.compulsa = compulsa;
		this.csv = csv;
	}

	
	public String[] getFirmas() {
		return firmas;
	}
	public void setFirmas(String[] firmas) {
		this.firmas = firmas;
	}
	public boolean isCompulsa() {
		return compulsa;
	}
	public void setCompulsa(boolean compulsa) {
		this.compulsa = compulsa;
	}
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}	
	
}
