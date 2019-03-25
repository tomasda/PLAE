package com.opencanarias.consola.tabla.cat_procedimientos_circuitos;

import java.util.List;

import com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos.WorkflowBeanExtended;
import com.opencanarias.consola.interfaces.ManagedBeanInterface;

public class CatProcedimientosCircuitosManagedBean implements ManagedBeanInterface {
	private CatProcedimientosCircuitosDAO catPCDAO = null;
	
	public CatProcedimientosCircuitosManagedBean() {
		catPCDAO = new CatProcedimientosCircuitosDAO();
	}
	
	public List<WorkflowBeanExtended> addCircuitosToWorkflows(List<WorkflowBeanExtended> listaProcedimientos) {
		return catPCDAO.getProcedimientosCircuitos(listaProcedimientos);
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
