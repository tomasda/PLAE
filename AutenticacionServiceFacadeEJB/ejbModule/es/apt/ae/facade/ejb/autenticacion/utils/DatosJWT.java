package es.apt.ae.facade.ejb.autenticacion.utils;

public class DatosJWT {
	private String id;
	private String usuario;
	private String emisor;
	private String fechaExpiracion;
	private String roles;
	private String passwordCifrado;
	private String algoritmoCifradoPassword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(String fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPasswordCifrado() {
		return passwordCifrado;
	}

	public void setPasswordCifrado(String passwordCifrado) {
		this.passwordCifrado = passwordCifrado;
	}

	public String getAlgoritmoCifradoPassword() {
		return algoritmoCifradoPassword;
	}

	public void setAlgoritmoCifradoPassword(String algoritmoCifradoPassword) {
		this.algoritmoCifradoPassword = algoritmoCifradoPassword;
	}
}
