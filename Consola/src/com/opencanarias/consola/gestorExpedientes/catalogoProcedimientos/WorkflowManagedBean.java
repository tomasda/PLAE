package com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.SerializationUtils;
import org.jboss.logging.Logger;

import com.opencanarias.consola.gestorExpedientes.catalogoActivity.ActivityBean;
import com.opencanarias.consola.gestorExpedientes.catalogoActivity.ActivityDAO;
import com.opencanarias.consola.gestorExpedientes.catalogoDepartamentos.DepartamentosBean;
import com.opencanarias.consola.gestorExpedientes.catalogoDepartamentos.DepartamentosManagedBean;
import com.opencanarias.consola.gestorExpedientes.catalogoFamilias.FamiliasBean;
import com.opencanarias.consola.gestorExpedientes.catalogoFamilias.FamiliasDAO;
import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.portafirmas.circuitos.PF_FlujosBean;
import com.opencanarias.consola.portafirmas.circuitos.PF_FlujosDAO;
import com.opencanarias.consola.tabla.cat_procedimientos_circuitos.CatProcedimientoCircuitoBean;
import com.opencanarias.consola.tabla.cat_procedimientos_circuitos.CatProcedimientosCircuitosDAO;
import com.opencanarias.consola.tabla.cat_procedimientos_circuitos.CatProcedimientosCircuitosManagedBean;
import com.opencanarias.consola.tabla.cat_procedimientos_roles.CatProcedimientosRolesBean;
import com.opencanarias.consola.tabla.cat_procedimientos_roles.CatProcedimientosRolesDAO;
import com.opencanarias.consola.tabla.cat_procedimientos_roles.CatProcedimientosRolesManagedBean;
import com.opencanarias.consola.tabla.workflow_permision.WorkflowPermissionBean;
import com.opencanarias.consola.tabla.workflow_permision.WorkflowPermissionDAO;
import com.opencanarias.consola.tabla.workflow_permision.WorkflowPermissionManagedBean;

public class WorkflowManagedBean implements Serializable,ManagedBeanInterface {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CatProcedimientoCircuitoBean.class);
	private List<WorkflowBeanExtended> listaProcedimientos;
	private WorkflowBeanExtended procedimientoBean;


	private String tmpID_PROC;
	private List<FamiliasBean> listaFamilias;
	private String familiaSeleccionada;

	private List<InicioBean> listaTiposInicio;
	private boolean enableCatProcedimientosCircuitosForm;
	private boolean enableWorkflowPermissionForm;
	private boolean enableCatProcedimientosRolesForm;
	CatProcedimientosCircuitosDAO pcd;
	private String selectedFlujodeFirma;
	private String selectedFlujodeFirmaActividad;
	private String selectedFlujodeFirmaDepartamento;
	private String selectedRolDepartamento;
	private String selectedWorkflowDepartamento;
	private String selectedWorkflowPermission;
	private List<PF_FlujosBean> listaFlujosFirma;
	private List<ActivityBean> listaActividades;
	private List<DepartamentosBean> listaDepartamentos;
	private boolean newWorkflow;

	public WorkflowManagedBean() {
		this.pcd = new CatProcedimientosCircuitosDAO();
		this.tmpID_PROC = null;
		this.procedimientoBean = null;
		this.enableCatProcedimientosCircuitosForm = false;
		this.enableWorkflowPermissionForm = false;
		this.enableCatProcedimientosRolesForm = false;
		this.listaProcedimientos = null;
		this.listaTiposInicio = null;
		this.listaFamilias = null;
		this.familiaSeleccionada = null;
		this.listaFlujosFirma = null;
		this.listaActividades = null;
		this.listaDepartamentos = null;
		this.newWorkflow = false;
	}


	/*
	 * LISTAS
	 */
	public String descripcionInicio(int var) {
		if (null == this.listaTiposInicio) {
			InicioDAO id = new InicioDAO();
			this.listaTiposInicio = new ArrayList<InicioBean>();
			this.listaTiposInicio = id.getListaTipoInicio();
		}
		return this.listaTiposInicio.get(var).getTipoInicioLabel();
	}


	public List<FamiliasBean> getListOfFamilias() {
		if (null == this.listaFamilias) {
			FamiliasDAO fd = new FamiliasDAO();
			this.listaFamilias = new ArrayList<FamiliasBean>();
			try {
				this.listaFamilias = fd.getList("", "");
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return this.listaFamilias;
	}

	public List<PF_FlujosBean> getlistaFlujosFirma() {
		if (null == this.listaFlujosFirma) {
			PF_FlujosDAO pfcd = new PF_FlujosDAO();
			this.listaFlujosFirma = new ArrayList<PF_FlujosBean>();
			this.listaFlujosFirma = pfcd.getListaFlujosFirma(0,2,0,"");
		}
		return this.listaFlujosFirma;
	}

	public List<ActivityBean> getListaActividades(String procppalID) {
		if (null == this.listaActividades){
			ActivityDAO ad = new ActivityDAO();
			this.listaActividades = new ArrayList<ActivityBean>();
			this.listaActividades = ad.getActivityList(procppalID);
		}
		return this.listaActividades;
	}

	public List<DepartamentosBean> getListaDepartamentos() {
		if (null == this.listaDepartamentos) {
			DepartamentosManagedBean dd = new DepartamentosManagedBean();
			this.listaDepartamentos = new ArrayList<DepartamentosBean>();
			this.listaDepartamentos = dd.getListaDepartamentos("ALF"); // Busco todos los Departamentos con Grupo en Alfresco, porque existen registros de
			// los que no se que hacen aquí.
		}
		return this.listaDepartamentos;
	}

	public List<WorkflowBeanExtended> getListOfProcedimientos() throws Exception {
		// Cuando se accede por primera vez o cuando se vuelve al formulario
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (null == ms.getSearchOption()) {
			this.familiaSeleccionada = ms.getSearchOption();
			this.listaProcedimientos = null;
		}

		if (this.familiaSeleccionada != ms.getSearchOption()) {
			this.familiaSeleccionada = ms.getSearchOption();
			if (ms.getSearchOption().equals("--")) { // Caso en que sea la opción es "Seleccione una Familia"
				this.listaProcedimientos = null;
			} else {
				updateListOfWorkflows(this.familiaSeleccionada);
			}
		}
		return listaProcedimientos;
	}
	public List<WorkflowBeanExtended> getListOfProcedimientos(String familia) {

		WorkflowDAO pd = new WorkflowDAO();
		this.listaProcedimientos = pd.listaProcedimientos(familia);
		return this.listaProcedimientos;
	}
	/*
	 * Tramites
	 */
	public void newAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("N"); 
		descripcionInicio(0);// fuerzo la recarga de la lista de tipos de inicio - pues puede estar vacía.
		this.procedimientoBean = new WorkflowBeanExtended(null, null, null, 0, null, 0, null, null, null, 0, null, null);
		CatProcedimientosRolesDAO cprd = new CatProcedimientosRolesDAO();
		this.procedimientoBean.setListOfCatProcedimientosRoles(cprd.getListOfCatProcedimientosRoles(null, familiaSeleccionada, null));
		setEnableCatProcedimientosCircuitosForm(false);
		setEnableCatProcedimientosRolesForm(false);
		setEnableWorkflowPermissionForm(false);
		this.newWorkflow = true;
	}

	public void newCircuitoAction() {
		List<CatProcedimientoCircuitoBean> circuitoFirmaList = null;
		Boolean addCircuito = true;
		if (null==procedimientoBean.getListOfCatProcedimientoCircuitos()) {
			circuitoFirmaList = new ArrayList<>();
		}else {
			circuitoFirmaList = procedimientoBean.getListOfCatProcedimientoCircuitos();
		}
		CatProcedimientoCircuitoBean circuito = new CatProcedimientoCircuitoBean("new",procedimientoBean.getProcedimiento(),selectedFlujodeFirmaActividad,selectedFlujodeFirmaDepartamento, Integer.valueOf(selectedFlujodeFirma));
		if (selectedFlujodeFirmaActividad.equals("null"))
			circuito.setActividad_ID(null);
		if (selectedFlujodeFirmaDepartamento.equals("null"))
			circuito.setDepartamento_ID(null);
		for (CatProcedimientoCircuitoBean catProcedimientoCircuitoBean : circuitoFirmaList) {
			if (catProcedimientoCircuitoBean.getCircuitoID()==circuito.getCircuitoID()) {
				if (comparator(catProcedimientoCircuitoBean.getActividad_ID(),circuito.getActividad_ID())) {
					if (comparator(catProcedimientoCircuitoBean.getDepartamento_ID(),circuito.getDepartamento_ID())) {
						logger.error("El circuito de firma ya está asociado a este expediente.");
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"El circuito de firma ya está asociado a este expediente. "," Por favor, revise los parámetros."));
						addCircuito= false;
					}
				}
			}
		}
		if (addCircuito) {
			circuitoFirmaList.add(circuito);
			procedimientoBean.setListOfCatProcedimientoCircuitos(circuitoFirmaList);
		}
	}

	public void newProcedimientoRoleAction() {
		List<CatProcedimientosRolesBean> procedimientosRolesList = null;
		Boolean addRole = true;
		if(null==procedimientoBean.getListOfCatProcedimientosRoles()) {
			procedimientosRolesList = new ArrayList<>();
		}else {
			procedimientosRolesList = procedimientoBean.getListOfCatProcedimientosRoles();
		}
		CatProcedimientosRolesBean role = new CatProcedimientosRolesBean(0, selectedRolDepartamento, null, procedimientoBean.getProcedimiento());
		for (CatProcedimientosRolesBean catProcedimientosRolesBean : procedimientosRolesList) {
			if (comparator(catProcedimientosRolesBean.getDEPARTAMENTO_ID_(),role.getDEPARTAMENTO_ID_())) {
				logger.error("El departamento "+catProcedimientosRolesBean.getDEPARTAMENTO_ID_()+" ya está asociado al procedimiento");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"El departamento "+catProcedimientosRolesBean.getDEPARTAMENTO_ID_()+" ya está asociado al procedimiento.", "Seleccione otro departamento."));
				addRole=false;
			}
		}
		if(addRole) {
			procedimientosRolesList.add(role);
			procedimientoBean.setListOfCatProcedimientosRoles(procedimientosRolesList);
		}
	}

	public void newWorkfloPermissionAction() {
		List<WorkflowPermissionBean> workflowPermissionList = null;
		Boolean addPermission = true;
		if(null==procedimientoBean.getListOfWorkflowPermission()) {
			workflowPermissionList = new ArrayList<>();
		}else {
			workflowPermissionList=procedimientoBean.getListOfWorkflowPermission();
		}
		WorkflowPermissionBean workflow = new WorkflowPermissionBean(0, procedimientoBean.getProcedimiento(), Integer.valueOf(selectedWorkflowPermission), selectedWorkflowDepartamento, null);
		for (WorkflowPermissionBean workflowPermissionBean : workflowPermissionList) {
			if (comparator(workflowPermissionBean.getROLE_ID_(),workflow.getROLE_ID_())) {
				logger.error("El departamento "+workflowPermissionBean.getROLE_ID_()+" ya está asociado al procedimiento");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"El departamento "+workflowPermissionBean.getROLE_ID_()+" ya está asociado al procedimiento.", "Seleccione otro departamento."));
				addPermission=false;
			}
		}
		if (addPermission) {
			workflowPermissionList.add(workflow);
			procedimientoBean.setListOfWorkflowPermission(workflowPermissionList);
		}
	}

	@Override
	public void editAction(Object identification) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("Ed");
		setEnableCatProcedimientosCircuitosForm(false);
		setEnableCatProcedimientosRolesForm(false);
		setEnableWorkflowPermissionForm(false);
		this.newWorkflow = false;
		/**
		 * Creamos una copia del procedimiento que vamos a editar por si volvemos sin aplicar cambios.
		 */
		this.procedimientoBean = SerializationUtils.clone((WorkflowBeanExtended)identification);
		this.tmpID_PROC = procedimientoBean.getProcedimiento();
	}

	public void saveAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		WorkflowDAO workflowDAO = new WorkflowDAO();
		CatProcedimientosCircuitosDAO catProcedimientosCircuitosDAO = new CatProcedimientosCircuitosDAO();
		CatProcedimientosRolesDAO catRolesDAO = new CatProcedimientosRolesDAO();
		WorkflowPermissionDAO workflowPermissionDAO = new WorkflowPermissionDAO();
		setEnableCatProcedimientosCircuitosForm(false);
		setEnableCatProcedimientosRolesForm(false);
		setEnableWorkflowPermissionForm(false);
		String action = null;
		if (newWorkflow) {
			action = "new";
		}else{
			action = "save";
		}
		if (null!=procedimientoBean || !procedimientoBean.getProcedimiento().isEmpty()) {
			if (procedimientoBean.getProcedimiento().length() == 7) {
				if (procedimientoBean.getDescripcion().length() != 0 && procedimientoBean.getIdProcppal().length() != 0) {
					workflowDAO.saveWorkflowData(procedimientoBean,tmpID_PROC, action);
					workflowPermissionDAO.saveWorkflowPermission(procedimientoBean);
					catRolesDAO.saveWorkflowData(procedimientoBean,action);
					catProcedimientosCircuitosDAO.saveWorkflowData(procedimientoBean, action);
					ms.setMenuEnabledForm("Ed");
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Los campos Descripción y Proceso Principal no pueden estar vacíos.", ""));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo Procedimiento ha de tener 7 caracteres.", ""));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo Procedimiento no puede estar vacío.", ""));
		}
	}

	public void addCircuitoForm() {
		setEnableCatProcedimientosCircuitosForm(true);
		setEnableCatProcedimientosRolesForm(false);
		setEnableWorkflowPermissionForm(false);
	}
	public void addPermissionForm() {
		setEnableCatProcedimientosCircuitosForm(false);
		setEnableCatProcedimientosRolesForm(false);
		setEnableWorkflowPermissionForm(true);
	}
	public void addRoleForm() {
		setEnableCatProcedimientosCircuitosForm(false);
		setEnableCatProcedimientosRolesForm(true);
		setEnableWorkflowPermissionForm(false);
	}

	@Override
	public void deleteAction(Object procedimiento) {
		boolean transition = true;
		WorkflowDAO pd = new WorkflowDAO();
		this.procedimientoBean = SerializationUtils.clone((WorkflowBeanExtended)procedimiento);
		//Elimina los permisos CatProcedimientosRoles.
		if (null!=this.procedimientoBean.getListOfCatProcedimientosRoles()) {
			CatProcedimientosRolesDAO cprd = new CatProcedimientosRolesDAO();
			for (CatProcedimientosRolesBean rol : this.procedimientoBean.getListOfCatProcedimientosRoles()) {
				//Verificación del Rol - Si está establecido a nivel de Familia, no eliminarlo.
				if (null==rol.getFAMILIA_ID_()) {
					try {
						cprd.deleteProcedimeintoRole(rol);
					} catch (SQLException e) {
						transition= false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido eliminar el rol "+rol.toString(),""));
						e.printStackTrace();
					}
				}
			}
		}
		//Elimina los permisos WorkFlowPermission
		if (null!=this.procedimientoBean.getListOfWorkflowPermission()) {
			WorkflowPermissionDAO wfpd = new WorkflowPermissionDAO();
			for (WorkflowPermissionBean permission:this.procedimientoBean.getListOfWorkflowPermission()) {
				try {
					wfpd.deleteWorkflowPermision(permission);
				} catch (SQLException e) {
					transition=false;
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido eliminar el permiso "+permission.toString(),""));
					e.printStackTrace();
				}
			}
		}
		//Elimina los Circuitos de firma.
		if (null!=this.procedimientoBean.getListOfCatProcedimientoCircuitos()) {
			CatProcedimientosCircuitosDAO cpcd = new CatProcedimientosCircuitosDAO();
			for (CatProcedimientoCircuitoBean circuito : this.procedimientoBean.getListOfCatProcedimientoCircuitos()) {
				try {
					cpcd.deleteCatProcedimientoCircuito(circuito);
				} catch (SQLException e) {
					transition=false;
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido eliminar el circuito "+circuito.toString(),""));
					e.printStackTrace();
				}
			}
		}
		// Elimina el procedimiento.
		pd.saveWorkflowData(this.procedimientoBean,"", "delete");
		if (transition) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha eliminado satisfactoriamente el procedimiento "+ this.procedimientoBean.descripcion,""));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha eliminado el procedimiento "+ this.procedimientoBean.descripcion,"Pero con errores, revise el LOG"));
		}
		updateListOfWorkflows(this.familiaSeleccionada);
	}


	public void deleteCircuitoAction(CatProcedimientoCircuitoBean circuitoBean) {
		procedimientoBean.getListOfCatProcedimientoCircuitos().remove(circuitoBean);

	}
	public void deleteRolAction(CatProcedimientosRolesBean rol) {
		procedimientoBean.getListOfCatProcedimientosRoles().remove(rol);

	}
	public void deletePermissionAction(WorkflowPermissionBean workflow) {
		procedimientoBean.getListOfWorkflowPermission().remove(workflow);

	}

	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
		setEnableCatProcedimientosCircuitosForm(false);
		setEnableCatProcedimientosRolesForm(false);
		setEnableWorkflowPermissionForm(false);
		// Actualización de la lista de procedimientos.
		updateListOfWorkflows(this.familiaSeleccionada);
	}


	public void findAction() {

	}

	private void updateListOfWorkflows(String searchOption) {
		WorkflowDAO pd = new WorkflowDAO();
		this.listaProcedimientos = new ArrayList<>();
		this.listaProcedimientos = pd.listaProcedimientos(searchOption);
		CatProcedimientosCircuitosManagedBean cpcmb = new CatProcedimientosCircuitosManagedBean();
		this.listaProcedimientos = cpcmb.addCircuitosToWorkflows(this.listaProcedimientos);
		CatProcedimientosRolesManagedBean cprmb = new CatProcedimientosRolesManagedBean();
		this.listaProcedimientos = cprmb.addCatProcedimientosRolesToWorkflows(this.listaProcedimientos);
		WorkflowPermissionManagedBean wfpmb = new WorkflowPermissionManagedBean();
		this.listaProcedimientos = wfpmb.addWorkFlowPermissionToWorkflow(this.listaProcedimientos);
	}

	/*
	 * FUNCIONES
	 */

	public String getActividad(String actID) {
		String data = null;
		if (actID.equals("")) {
			data = "Todos los que usen este procedimiento";
		}else {
			data = actID;
		}
		return data;
	}
	public String getDepartamento(String depID) {
		String data = null;
		if (depID.equals("")) {
			data = "Todos los que usen este procedimiento";
		}else {
			data = depID;
		}
		return data;
	}

	private boolean comparator(String cadenaA, String cadenaB) {
		boolean result = false;
		if (null==cadenaA && null==cadenaB)
			result = true;
		if (null!=cadenaA && null!=cadenaB && cadenaA.equals(cadenaB))
			result = true;
		return result;
	}

	/*
	 * Getters Setters
	 */

	public WorkflowBeanExtended getProcecimientoBean() {
		return procedimientoBean;
	}

	public void setProcecimientoBean(WorkflowBeanExtended procecimientoBean) {
		this.procedimientoBean = procecimientoBean;
	}

	public boolean isEnableCatProcedimientosCircuitosForm() {
		return enableCatProcedimientosCircuitosForm;
	}


	public void setEnableCatProcedimientosCircuitosForm(boolean enableCatProcedimientosCircuitosForm) {
		this.enableCatProcedimientosCircuitosForm = enableCatProcedimientosCircuitosForm;
	}


	public boolean isEnableWorkflowPermissionForm() {
		return enableWorkflowPermissionForm;
	}


	public void setEnableWorkflowPermissionForm(boolean enableWorkflowPermissionForm) {
		this.enableWorkflowPermissionForm = enableWorkflowPermissionForm;
	}


	public boolean isEnableCatProcedimientosRolesForm() {
		return enableCatProcedimientosRolesForm;
	}


	public void setEnableCatProcedimientosRolesForm(boolean enableCatProcedimientosRolesForm) {
		this.enableCatProcedimientosRolesForm = enableCatProcedimientosRolesForm;
	}


	public List<InicioBean> getListaTiposInicio() {
		return listaTiposInicio;
	}

	public void setListaTiposInicio(List<InicioBean> listaTiposInicio) {
		this.listaTiposInicio = listaTiposInicio;
	}
	public String getSelectedFamilia() {
		return selectedFlujodeFirma;
	}

	public void setSelectedFamilia(String selectedLiFF_) {
		selectedFlujodeFirma = selectedLiFF_;
	}


	public String getSelectedFlujodeFirmaActividad() {
		return selectedFlujodeFirmaActividad;
	}


	public void setSelectedFlujodeFirmaActividad(String selectedFlujodeFirmaActividad) {
		this.selectedFlujodeFirmaActividad = selectedFlujodeFirmaActividad;
	}


	public String getSelectedFlujodeFirmaDepartamento() {
		return selectedFlujodeFirmaDepartamento;
	}


	public void setSelectedFlujodeFirmaDepartamento(String selectedFlujodeFirmaDepartamento) {
		this.selectedFlujodeFirmaDepartamento = selectedFlujodeFirmaDepartamento;
	}


	public String getSelectedRolDepartamento() {
		return selectedRolDepartamento;
	}


	public void setSelectedRolDepartamento(String selectedRolDepartamento) {
		this.selectedRolDepartamento = selectedRolDepartamento;
	}


	public String getSelectedWorkflowDepartamento() {
		return selectedWorkflowDepartamento;
	}


	public void setSelectedWorkflowDepartamento(String selectedWorkflowDepartamento) {
		this.selectedWorkflowDepartamento = selectedWorkflowDepartamento;
	}


	public String getSelectedWorkflowPermission() {
		return selectedWorkflowPermission;
	}


	public void setSelectedWorkflowPermission(String selectedWorkflowPermission) {
		this.selectedWorkflowPermission = selectedWorkflowPermission;
	}


}
