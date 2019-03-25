package com.opencanarias.consola.gestorExpedientes.catalogoFamilias;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

import com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos.WorkflowBeanExtended;
import com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos.WorkflowManagedBean;
import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.tabla.cat_procedimientos_roles.CatProcedimientosRolesBean;
import com.opencanarias.consola.tabla.cat_procedimientos_roles.CatProcedimientosRolesDAO;

public class FamiliasManagedBean implements Serializable,ManagedBeanInterface{

	private static final long serialVersionUID = 3906413829726246606L;
	private static Logger logger = Logger.getLogger(FamiliasManagedBean.class);
	private FamiliasBean familiaBean;
	private  List<FamiliasBean> famList;
	private List<WorkflowBeanExtended> procedimientosList;
	private List<CatProcedimientosRolesBean> rolesList;
	private List<CatProcedimientosRolesBean> listOfRolesToDelete=null;
	private List<CatProcedimientosRolesBean> listOfRolesToAdd=null;
	private String selectedDepartamento;

	public FamiliasManagedBean() {
		resetFormObjects();
		famList = null;
	}

	public void findAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		try {
			FamiliasDAO f = new FamiliasDAO();
			this.setFamList(f.getList(ms.getSearchOption(),ms.getSearchOption2()));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido obtener los datos de la BBDD." , ""));
			logger.error("No se ha podido obtener los datos de la BBDD");
			e.printStackTrace();
		}
	}

	public void newAction() {
		MenuSessionOption ms =  (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		resetFormObjects();
		ms.setMenuEnabledForm("N");
	}

	public void editAction(Object data) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		FamiliasDAO fd = new FamiliasDAO();
		// Indico al formulario que estamos en la edición de los datos.
		ms.setMenuEnabledForm("Ed");
		// Cargo en el Bean los datos a mostrar en la edición.
		this.familiaBean=fd.getFamilyData((String)data);
		procedimientosListDataOfThisFamily((String)data);
	}

	public void deleteAction(Object data) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		procedimientosListDataOfThisFamily((String)data);
		if (null==this.procedimientosList) {
			/*
			 * Elimina los roles de Familia.
			 * Pero primero hemos de recuperar los roles asignados a dicha familia.
			 */
			CatProcedimientosRolesDAO cprd = new CatProcedimientosRolesDAO();
			List<CatProcedimientosRolesBean> list = cprd.getListOfCatProcedimientosRoles(null, familiaBean.getID(), null);
			if (null!=list) {
				try {
					for (CatProcedimientosRolesBean rol:list) {
					cprd.deleteProcedimeintoRole(rol);
					}
				} catch (SQLException e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido eliminar los CatProcedimientosRoles asociados a la Familia",""));
					e.printStackTrace();
				}
			}
			//Elimina la Familia
			FamiliasDAO fd =  new FamiliasDAO();
			fd.setFamilyData((String)data, "delete",familiaBean);
			fd.updateList(ms.getSearchOption(), ms.getSearchOption2());
		}else {
			// La Familia no se puede eliminar si contiene procedimientos asociados.
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"No se puede eliminar una Familia que contenga Procedimientos.  ","Puede realizar esta tarea desde la Gestión de Procedimientos."));
		}
	}

	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
		resetFormObjects();
	}

	public void saveAction() {
		MenuSessionOption ms =  (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		FamiliasDAO fd = new FamiliasDAO();
		CatProcedimientosRolesDAO cprd = new CatProcedimientosRolesDAO();
		String act = null;
		String setID = null;
		if (null != getFb().ID || !getFb().ID.isEmpty()) {
			if (getFb().ID.length() == 4) {
				if (getFb().DESCRIPTION.length() != 0 ) {
					if (ms.getMenuEnabledForm().equals("N")) {
						act = "new";
						setID = getFb().ID;
					}
					if (ms.getMenuEnabledForm().equals("Ed")) {
						act = "save";
					}
					if (null == act) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha definido el tipo de operación.", "No se realiza la operación."));
					} else {
						fd.setFamilyData(setID, act, familiaBean);
						fd.updateList(ms.getSearchOption(), ms.getSearchOption2());
						/*
						 * Guarda los cambios de permisos ROLES. 
						 */
						if (null!=listOfRolesToDelete) {
							for (CatProcedimientosRolesBean rol : listOfRolesToDelete) {
								System.out.println("Delete "+ rol.toString());
								if (rol.getID_()!=0) {
									try {
										cprd.deleteProcedimeintoRole(rol);
									} catch (SQLException e) {
										FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo elminar el rol" +rol.toString(),""));
										e.printStackTrace();
									}
								}
							}
							listOfRolesToDelete = null;
						}
						if(null!=listOfRolesToAdd) {
							for (CatProcedimientosRolesBean rol : listOfRolesToAdd) {
								System.out.println("Add "+ rol.toString());
								try {
									cprd.insertProcedimeintoRole(new CatProcedimientosRolesBean(0,selectedDepartamento,familiaBean.getID(),null));
								} catch (SQLException e) {
									FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo añadir el rol "+selectedDepartamento+" a "+familiaBean.ID,""));
									e.printStackTrace();
								}
							}
						}
					}
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Los campos Descripción y/o Proceso no puedes estar vacíos.", ""));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El campo ID de Familia ha de tener 4 caracteres.", ""));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El campo ID de Familia no puede estar vacío.", ""));
		}
	}

	public void newFamiliaRoleAction() {
		/*
		 * Verificar los procedimientos asociados a la famlia y retira los permisos al departamento otorgados por procedimiento. 
		 */
		if (null==procedimientosList) {
			procedimientosList = new ArrayList<>();
		}
		for (WorkflowBeanExtended procedimiento : procedimientosList) {
			for (CatProcedimientosRolesBean rol:procedimiento.getListOfCatProcedimientosRoles()) {
				if (rol.getDEPARTAMENTO_ID_().equals(selectedDepartamento)) {
					if (null==listOfRolesToDelete) {
						listOfRolesToDelete = new ArrayList<>();
					}
					listOfRolesToDelete.add(rol);
				}
			}
		}
		/*
		 * Añadir el rol de departamento por familia.  
		 */
		if (null==rolesList) {
			rolesList = new ArrayList<>();
			addRol();
		}else {
			boolean exist=false;
			for (CatProcedimientosRolesBean rol : rolesList) {
				if(rol.getDEPARTAMENTO_ID_().equals(selectedDepartamento)) {
					exist = true;
				}
			}
			if(exist) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El departamento ya está asociado a la Familia.",""));
			}else {
				addRol();
			}
		}
	}
	
	private void addRol() {
		rolesList.add(new CatProcedimientosRolesBean(0,selectedDepartamento,familiaBean.getID(),null));
		if(null==listOfRolesToAdd) {
			listOfRolesToAdd = new ArrayList<>();
		}
		listOfRolesToAdd.add(new CatProcedimientosRolesBean(0,selectedDepartamento,familiaBean.getID(),null));
	}

	public void deleteFamiliaRoleAction(CatProcedimientosRolesBean dpt) {
		System.out.println(dpt.toString());
		if (dpt.getID_()!=0) {
			if(null==listOfRolesToDelete) {
				listOfRolesToDelete = new ArrayList<>();
			}
			listOfRolesToDelete.add(dpt);
		}else {
			for (CatProcedimientosRolesBean rol:listOfRolesToAdd) {
				if(rol.toString().equals(dpt.toString())) {
					listOfRolesToAdd.remove(rol);
					break;
				}
			}
		}
		for (CatProcedimientosRolesBean rol : rolesList) {
			if (rol.toString().equals(dpt.toString())) {
				rolesList.remove(rol);
				break;
			}
		}
	}
	
	private void procedimientosListDataOfThisFamily(String family) {
		WorkflowManagedBean wfmb = new WorkflowManagedBean();
		CatProcedimientosRolesDAO cprd = new CatProcedimientosRolesDAO();
		this.procedimientosList = wfmb.getListOfProcedimientos(family);
		this.procedimientosList = cprd.insertRolesToProcedimientos(this.procedimientosList);
		this.rolesList = cprd.getListOfCatProcedimientosRoles(null, family, null);
	}
	
	private void resetFormObjects() {
		this.familiaBean= new FamiliasBean();
		this.procedimientosList = null;
		this.rolesList=null;
		this.listOfRolesToDelete=null;
		this.listOfRolesToAdd=null;
		this.selectedDepartamento=null;
	}
	/*
	 * Getters Setters
	 */

	public FamiliasBean getFb() {
		return familiaBean;
	}


	public void setFb(FamiliasBean fb) {
		this.familiaBean = fb;
	}

	public List<FamiliasBean> getFamList() {
		return famList;
	}

	public void setFamList(List<FamiliasBean> famList) {
		this.famList = famList;
	}

	public List<WorkflowBeanExtended> getProcedimientosList() {
		return procedimientosList;
	}

	public void setProcedimientosList(List<WorkflowBeanExtended> procedimientosList) {
		this.procedimientosList = procedimientosList;
	}

	public List<CatProcedimientosRolesBean> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<CatProcedimientosRolesBean> rolesList) {
		this.rolesList = rolesList;
	}

	public String getSelectedDepartamento() {
		return selectedDepartamento;
	}

	public void setSelectedDepartamento(String selectedDepartamento) {
		this.selectedDepartamento = selectedDepartamento;
	}


}
