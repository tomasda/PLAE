package com.opencanarias.consola.tabla.cat_procedimientos_roles;

import java.util.List;

import com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos.WorkflowBeanExtended;
import com.opencanarias.consola.interfaces.ManagedBeanInterface;

public class CatProcedimientosRolesManagedBean implements ManagedBeanInterface {
	private CatProcedimientosRolesDAO catPRDAO = null;
	
	public CatProcedimientosRolesManagedBean() {
		catPRDAO = new CatProcedimientosRolesDAO();
	}
	
	public List<WorkflowBeanExtended> addCatProcedimientosRolesToWorkflows(List<WorkflowBeanExtended> listaProcedimientos) {
		return catPRDAO.insertRolesToProcedimientos(listaProcedimientos);
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

}
