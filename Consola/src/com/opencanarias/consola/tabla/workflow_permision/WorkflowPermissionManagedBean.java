package com.opencanarias.consola.tabla.workflow_permision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos.WorkflowBeanExtended;
import com.opencanarias.consola.interfaces.ManagedBeanInterface;

public class WorkflowPermissionManagedBean implements Serializable,ManagedBeanInterface {
	private static final long serialVersionUID = 1L;
	private WorkflowPermissionDAO wfpdao = null;
	private List<PermissionBean> permissionMap= null;
	

	
	public WorkflowPermissionManagedBean() {
		this.wfpdao = new WorkflowPermissionDAO();
		if (null==permissionMap)
			this.permissionMap = PermissionMAP();
	}


	@Override
	public void findAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void newAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void editAction(Object identification) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void returnAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAction(Object identification) {
		// TODO Auto-generated method stub

	}

	public List<WorkflowBeanExtended> addWorkFlowPermissionToWorkflow (List<WorkflowBeanExtended> listaProcedimientos){
		return wfpdao.addWorkflowPermissionToWorkflow(listaProcedimientos);
	}
	
	public String PermissionDescription(int id) {
		return permissionMap.get(id-1).getValue();
	}
	
	private List<PermissionBean> PermissionMAP(){
		/**
		 * Datos importados desde la Fachada. 
		public static int LECTOR = 1;
		public static int EDITOR = 2;
		public static int CONTRIBUIDOR = 3;
		public static int COLABORADOR = 4;
		public static int COORDINADOR = 5;
		public static int DIRECTOR = 6;
		 */
		List<PermissionBean> permissionMap = new ArrayList<>();
		PermissionBean a = new PermissionBean();
		a.setKey(1); a.setValue("LECTOR"); 
		permissionMap.add(a);
		a = new PermissionBean();
		a.setKey(2); a.setValue("EDITOR");
		permissionMap.add(a);
		a = new PermissionBean();
		a.setKey(3); a.setValue("CONTRIBUIDOR");
		permissionMap.add(a);
		a = new PermissionBean();
		a.setKey(4); a.setValue( "COLABORADOR");
		permissionMap.add(a);
		a = new PermissionBean();
		a.setKey(5); a.setValue( "COORDINADOR");
		permissionMap.add(a);
		a = new PermissionBean();
		a.setKey(6); a.setValue("DIRECTOR");
		permissionMap.add(a);
		return permissionMap;
	}


	public List<PermissionBean> getPermissionMap() {
		return permissionMap;
	}


	public void setPermissionMap(List<PermissionBean> permissionMap) {
		this.permissionMap = permissionMap;
	}
}
