/**
 * 
 */
package com.opencanarias.consola.tabla.roles;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;


/**
 * @author Tomás Delgado
 *
 */
public class CatalogoRoleManagedBean implements Serializable,ManagedBeanInterface{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CatalogoRoleManagedBean.class);
	private MenuSessionOption ms;
	private CatalogoRoleDAO crdao;
	private List<RoleBean> listOfRoles;
	private RoleBean role;

	public CatalogoRoleManagedBean() {
		this.crdao = new CatalogoRoleDAO();
	}

	public void findAction() {
		this.ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (!this.ms.getSearchOption().isEmpty()||!this.ms.getSearchOption2().isEmpty()||!this.ms.getSearchOption3().isEmpty()) {
			this.listOfRoles = crdao.getRoles(this.ms.getSearchOption(),this.ms.getSearchOption2(),this.ms.getSearchOption3());
		}
	}

	public void newAction() {

	}

	public void editAction(Object identification) {

	}

	public void saveAction() {

	}

	public void returnAction() {

	}

	public void deleteAction(Object identification) {

	}
	
	/*
	 * FUNCIONES
	 */
	
	public List<RoleBean> listOfAllRoles(){
		return crdao.getListOfRoles();
	}

	public RoleBean getRole(String ID_) {
		if(null==this.listOfRoles) {
			this.listOfRoles = crdao.getListOfRoles();
		}
		RoleBean role = null;
		for (RoleBean roleBean : listOfRoles) {
			if(roleBean.getId_().equals(ID_)) {
				role=roleBean;
				break;
			}
		}
//		try {
//
//			role = this.crdao.getRole(name);
//		}catch (Exception e) {
//			logger.error(e);
//		}
		return role;
	}

	public String getDescriptionOfRole(String ID_) {
		String description = null;
		if(null==this.listOfRoles) {
			try {
				this.listOfRoles = crdao.getListOfRoles();
			}catch (Exception e) {
				logger.error(e);
			}
		}
		for (RoleBean roleBean : listOfRoles) {
			if(roleBean.getId_().equals(ID_)) {
				description =roleBean.getDescription_();
				break;
			}
		}
//		try {
//			description = this.crdao.getRole(ID_).getDescription_();
//		}catch (Exception e) {
//			logger.error(e);
//		}
		return description;
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public List<RoleBean> getListOfRoles() {
		return listOfRoles;
	}

	public void setListOfRoles(List<RoleBean> listOfRoles) {
		this.listOfRoles = listOfRoles;
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

}
