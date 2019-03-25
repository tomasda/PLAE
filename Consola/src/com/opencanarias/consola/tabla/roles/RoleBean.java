/**
 * 
 */
package com.opencanarias.consola.tabla.roles;

import java.io.Serializable;

/**
 * 
 * @author Tomás Delgado
 *
 */
public class RoleBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id_;
	private String alfGroup_;
	private int decretable_;
	private String description_;
	private int habilitado_;
	private int decretable_registro_;
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public String getAlfGroup_() {
		return alfGroup_;
	}
	public void setAlfGroup_(String alfGroup_) {
		this.alfGroup_ = alfGroup_;
	}
	public int getDecretable_() {
		return decretable_;
	}
	public void setDecretable_(int decretable_) {
		this.decretable_ = decretable_;
	}
	public String getDescription_() {
		return description_;
	}
	public void setDescription_(String description_) {
		this.description_ = description_;
	}
	public int getHabilitado_() {
		return habilitado_;
	}
	public void setHabilitado_(int habilitado_) {
		this.habilitado_ = habilitado_;
	}
	public int getDecretable_registro_() {
		return decretable_registro_;
	}
	public void setDecretable_registro_(int decretable_registro_) {
		this.decretable_registro_ = decretable_registro_;
	}
	@Override
	public String toString() {
		return "RoleBean [id_=" + id_ + ", alfGroup_=" + alfGroup_ + ", decretable_=" + decretable_ + ", description_="
				+ description_ + ", habilitado_=" + habilitado_ + ", decretable_registro_=" + decretable_registro_
				+ "]";
	}
		
}
