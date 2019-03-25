package com.opencanarias.consola.gestorExpedientes.catalogoDepartamentos;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

public class DepartamentosManagedBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DepartamentosManagedBean.class);
	private List<DepartamentosBean> liDep;
	private DepartamentosDAO deptDAO;

	public  List<DepartamentosBean> getListaDepartamentos(String searchALF_GROUP) {
		if (null == liDep) {
			this.liDep = new ArrayList<DepartamentosBean>();
			this.deptDAO =  new DepartamentosDAO();
		}
			try {
				liDep = deptDAO.listaDatos(searchALF_GROUP);
			} catch (ClassNotFoundException e) {
				logger.error("No se ha podido cargar la lista de Departamentos " + e);
			} catch (IOException e) {
				logger.error("No se ha podido cargar la lista de Departamentos " + e);
			} catch (SQLException e) {
				logger.error("No se ha podido cargar la lista de Departamentos " + e);
			}
		return liDep;
	}
	
	public String getDepartmentDescripction(String role ) {
		this.deptDAO = new DepartamentosDAO();
		return this.deptDAO.obtenerDepartamento(role, "id", "description");
	}
}
