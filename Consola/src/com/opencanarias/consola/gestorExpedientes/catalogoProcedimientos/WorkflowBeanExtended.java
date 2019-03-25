package com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos;

import java.io.Serializable;
import java.util.List;

import com.opencanarias.consola.tabla.cat_procedimientos_circuitos.CatProcedimientoCircuitoBean;
import com.opencanarias.consola.tabla.cat_procedimientos_roles.CatProcedimientosRolesBean;
import com.opencanarias.consola.tabla.workflow_permision.WorkflowPermissionBean;

public class WorkflowBeanExtended extends WorkflowBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CatProcedimientosRolesBean> listOfCatProcedimientosRoles;
	private List<WorkflowPermissionBean> listOfWorkflowPermission;
	private List<CatProcedimientoCircuitoBean> listOfCatProcedimientoCircuitos;
	
	public WorkflowBeanExtended(String Procedimiento_, String IdProcppal_, String Descripcion_, int Plazo_, String Efect_sil_adm_, int Activo_,
			String Art_resolucion_, String Firmante_resolucion_, String Familia_id_, int Modificar_interesado_, String Seccion_tramitacion_,
			String Interesado_x_defecto_) {
		this.IDPROC = Procedimiento_;
		this.idprocppal = IdProcppal_;
		this.descripcion = Descripcion_;
		this.plazo = Plazo_;
		this.efect_sil_adm = Efect_sil_adm_;
		this.activo = Activo_;
		this.art_resolucion = Art_resolucion_;
		this.firmante_resolucion = Firmante_resolucion_;
		this.familia_id = Familia_id_;
		this.modificar_interesado = Modificar_interesado_;
		this.seccion_tramitacion = Seccion_tramitacion_;
		this.interesado_x_defecto = Interesado_x_defecto_;
	}

	public WorkflowBeanExtended() {
	}

	public List<CatProcedimientosRolesBean> getListOfCatProcedimientosRoles() {
		return listOfCatProcedimientosRoles;
	}

	public void setListOfCatProcedimientosRoles(List<CatProcedimientosRolesBean> listOfCatProcedimientosRoles) {
		this.listOfCatProcedimientosRoles = listOfCatProcedimientosRoles;
	}

	public List<WorkflowPermissionBean> getListOfWorkflowPermission() {
		return listOfWorkflowPermission;
	}

	public void setListOfWorkflowPermission(List<WorkflowPermissionBean> listOfWorkflowPermission) {
		this.listOfWorkflowPermission = listOfWorkflowPermission;
	}

	public List<CatProcedimientoCircuitoBean> getListOfCatProcedimientoCircuitos() {
		return listOfCatProcedimientoCircuitos;
	}

	public void setListOfCatProcedimientoCircuitos(List<CatProcedimientoCircuitoBean> listOfCatProcedimientoCircuitos) {
		this.listOfCatProcedimientoCircuitos = listOfCatProcedimientoCircuitos;
	}

	
}
