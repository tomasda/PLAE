package com.opencanarias.consola.portafirmas.CatalogoFirmantesPersonas;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.ldap.LDAPManager;
import com.opencanarias.consola.ldap.LDAPPersonaBean;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.portafirmas.CatalogoFirmantes.CatalogoFirmanteDAO;
import com.opencanarias.consola.portafirmas.CatalogoFirmantes.FirmanteBean;
import com.opencanarias.consola.tabla.roles.CatalogoRoleManagedBean;
import com.opencanarias.consola.tabla.roles.RoleBean;

public class CatalogoFirmantesPersonasManagedBean implements Serializable,ManagedBeanInterface{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CatalogoFirmantesPersonasManagedBean.class);
	private MenuSessionOption ms  = null;
	private CatalogoFirmanteDAO cfdao = null;
	private CatalogoFirmantePersonaDAO cfpdao = null;
	private CatalogoRoleManagedBean crmb = null;
	private LDAPManager ldapmb = null;
	private List<FirmanteBean> listOfFirmantes = null;
	private List<RoleBean> listRole = null;
	private FirmanteBean firmante = null;
	private String selectedRole = "999999999";
	private List<LDAPPersonaBean> listOfPersonas = null;
	private String[] selectedPersons = null;
	private String environment;

	public CatalogoFirmantesPersonasManagedBean() {
		this.cfdao = new CatalogoFirmanteDAO();
		this.cfpdao = new CatalogoFirmantePersonaDAO();
		this.ldapmb = new LDAPManager();
		this.firmante = null;
		this.setSelectedRole("999999999");
		this.setListOfPersonas(null);
		this.setSelectedPersons(null);
		this.ms  = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		this.environment = ms.getEnvironmentSelected();
	}


	public void findAction() {
		try {
			setListOfFirmantes(cfdao.findFirmantes(ms.getSearchOption(), ms.getSearchOption2(), ms.getSearchOption3(), ms.getSearchOption4()));
			setListOfFirmantes(cfpdao.getPersonas(this.listOfFirmantes));
			setListOfFirmantes(cfpdao.getTodosLosFirmantes(this.listOfFirmantes));
		} catch (Exception e) {
			logger.error(e);
		}
	}
	public void editAction(Object idFirmante){
		ms.clearSerarchOptions();
		ms.setMenuEnabledForm("Ed");
		for (FirmanteBean firmanteBean : listOfFirmantes) {
			if (firmanteBean.getIdFirmante()== (int)idFirmante) {
				firmante = new FirmanteBean();
				firmante = firmanteBean;
			}
		}
	}
	
	public String getRoleDescription(String name) {
		String description = null;
		if (listRole==null) {
			listRole = new ArrayList<>();
		}
		boolean isOnList = false;
		for (RoleBean role : listRole) {
			if (role.getId_().equals(name)) {
				description = role.getDescription_();
				isOnList = true;
			}
		}
		if (!isOnList) {
			crmb = new CatalogoRoleManagedBean();
			RoleBean role =  crmb.getRole(name);
			if (role != null) {
				listRole.add(role);
				description = role.getDescription_();
			}else {
				description =  "No existe "+name+" en la Tabla ROLE_";
			}
		}
		return description;
	}
	
	public void findPersonaAction() {
		StringBuilder strBusqueda=new StringBuilder();
		if (!ms.getSearchOption().equals("")) {
			strBusqueda.append("(cn=").append(ms.getSearchOption()).append("*)");
		}
		if (!ms.getSearchOption2().equals("")){
			if (strBusqueda.length()!=0) {
				strBusqueda.append(",");
			}
			strBusqueda.append("(carLicense=").append(ms.getSearchOption2()).append("*)");
		}
		if (!selectedRole.equals("999999999")) {
			RoleBean rol = crmb.getRole(selectedRole);
			if (strBusqueda.length()!=0) {
				strBusqueda.append(",");
			}
			strBusqueda.append("businessCategory=").append(rol.getId_());
		}else {
			if (strBusqueda.length()!=0) {
				strBusqueda.append(",");
			}
			strBusqueda.append("businessCategory=*");
		}
		try {
			listOfPersonas = ldapmb.findLDAPUsers(strBusqueda.toString());
			Collections.sort(listOfPersonas, new Comparator<LDAPPersonaBean>() {
				@Override
				public int compare (LDAPPersonaBean a, LDAPPersonaBean b) {
					return a.getCn().toUpperCase().compareTo(b.getCn().toUpperCase());
				}
			});
		} catch (ClassNotFoundException e) {
			logger.warn("Error - Clase no encontrada :"+e.getMessage());
		} catch (IOException e) {
			logger.warn("Error E/S :"+e.getMessage());
		} catch (SQLException e) {
			logger.warn("Error SQL :"+e.getMessage());
		}
	}
	
	public void deletePersonaAction(String carLicense, String idFirmante) {
		logger.info("Eliminación de la relación Firmante ID = "+idFirmante+" -- Persona DNI = "+carLicense);
		if (cfpdao.deleteFirmantePersonaRelation(idFirmante,carLicense)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha eliminado la relación correctamente.", ""));
			refreshEdition();
			firmante.setListOfPersons(null);
			firmante.setListOfPersons(cfpdao.getFirmantesPersonaListOfPersonas(firmante));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se pudo eliminar la Relación ", "!!!!!!!"));
		}
	}
	
	public void addPersonasAction() {
		System.out.println(firmante.getNombre());
		System.out.println(firmante.getIdFirmante());
		for (String carLicense : selectedPersons) {
			System.out.println(carLicense);
			/*
			 * Valida si el usuario ya tiene permisos de envío a todos los firmantes.
			 */
			if (cfpdao.isFirmantePersonaInTable(carLicense,0)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario "+carLicense+" ya tiene permisos en para el envío a la firma a Todos los firmantes. ", "No se añadirá!!!!"));
			}else{
				if (firmante.getIdFirmante()==0) {
					/*
					 * Valida si el usuario ya tiene permisos con otros firmantes
					 */
					if (cfpdao.isFirmantePersonaInTable(carLicense,1)) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario "+carLicense+" ya existe con permisos en otros firmantes ", "No se añadirá!!!!"));
					}else {
						/*
						 * Añade una persona para Todos los firmantes.
						 */
						cfpdao.addFirmantePersonaRelation(null, carLicense);
						firmante.setListOfPersons(null);
						firmante.setListOfPersons(cfpdao.getFirmantesPersonaListOfPersonas(firmante));
					}
				}else {
					/*
					 * Añade una persona a un firmante.
					 */
					cfpdao.addFirmantePersonaRelation(String.valueOf(firmante.getIdFirmante()), carLicense);
					firmante.setListOfPersons(null);
					firmante.setListOfPersons(cfpdao.getFirmantesPersonaListOfPersonas(firmante));
				}
			}
		}
	}

	public void returnAction() {
		ms.setMenuEnabledForm("I");
		listOfPersonas = null;
		selectedPersons = null;
	}
 
	public void newAction() {
	}

	public void saveAction() {
	}

	public void deleteAction(Object identification) {
	}
	public void refresh(){
		try {
			setListOfFirmantes(cfdao.findFirmantes(ms.getSearchOption(), ms.getSearchOption2(), ms.getSearchOption3(), ms.getSearchOption4()));
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void refreshEdition() {
		
	}
	
	public void clearTemp() {
		this.firmante = null;
		this.setSelectedRole("999999999");
		this.setListOfPersonas(null);
		this.setSelectedPersons(null);
	}

	/*
	 * Functions
	 */

	/*
	 * Getters & Setters
	 */
	public List<FirmanteBean> getListOfFirmantes() {
		// VERIFICA EL CAMBIO DE ENTORNO || SI HA CAMBIADO LIMPIA EL LISTADO DE RESULTADOS
		if (!environment.equals(ms.getEnvironmentSelected())) {
			environment=ms.getEnvironmentSelected();
			listOfFirmantes=null;
		}
		return listOfFirmantes;
	}

	public void setListOfFirmantes(List<FirmanteBean> listOfFirmantes) {
		this.listOfFirmantes = listOfFirmantes;
	}


	public FirmanteBean getFirmante() {
		return firmante;
	}


	public void setFirmante(FirmanteBean firmante) {
		this.firmante = firmante;
	}


	public String getSelectedRole() {
		return selectedRole;
	}


	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}


	public List<LDAPPersonaBean> getListOfPersonas() {
		return listOfPersonas;
	}


	public void setListOfPersonas(List<LDAPPersonaBean> listOfPersonas) {
		this.listOfPersonas = listOfPersonas;
	}


	public String[] getSelectedPersons() {
		return selectedPersons;
	}


	public void setSelectedPersons(String[] selectedPersons) {
		this.selectedPersons = selectedPersons;
	}
}
