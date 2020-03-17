package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String identificacion = null;
	private String nombreApellidos = null;
	private String nombre = null;
	private String apellido = null;
	private String username = null;
	private String correoElectronico = null;
	private List<String> listRoles = new ArrayList<String>();
	
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getNombreApellidos() {
		return nombreApellidos;
	}
	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public List<String> getListRoles() {
		return listRoles;
	}
	public void setListRoles(List<String> listRoles) {
		this.listRoles = listRoles;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido1) {
		this.apellido = apellido1;
	}
	
}
