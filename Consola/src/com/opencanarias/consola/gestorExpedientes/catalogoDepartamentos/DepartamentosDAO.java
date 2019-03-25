package com.opencanarias.consola.gestorExpedientes.catalogoDepartamentos;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

//@ManagedBean
//@SessionScoped
public class DepartamentosDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DepartamentosDAO.class);
	private static List<DepartamentosBean> liDep;
	
	public DepartamentosDAO() {
		liDep = null;
	}

//	public  List<DepartamentosBean> getListaDepartamentos(String searchALF_GROUP) {
//		if (null == liDep) {
//			liDep = new ArrayList<DepartamentosBean>();
//		}
//			try {
//				liDep = listaDatos(searchALF_GROUP);
//			} catch (ClassNotFoundException e) {
//				logger.error("No se ha podido cargar la lista de Departamentos " + e);
//			} catch (IOException e) {
//				logger.error("No se ha podido cargar la lista de Departamentos " + e);
//			} catch (SQLException e) {
//				logger.error("No se ha podido cargar la lista de Departamentos " + e);
//			}
//		return liDep;
//	}

	public List<DepartamentosBean> listaDatos(String searchALF_GROUP) throws ClassNotFoundException, IOException, SQLException {
		liDep = new ArrayList<DepartamentosBean>();
		String sql = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Result res = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM ROLE_ WHERE ALF_GROUP_ LIKE ? ORDER BY DESCRIPTION_ ASC";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, searchALF_GROUP+"%");
			rs = ps.executeQuery();
			res = ResultSupport.toResult(rs);
			int numRows = res.getRowCount();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
				liDep = null;
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				rs.beforeFirst(); // vuelvo al primer registro
				if (numRows < 100) { // Sí los resultados son menos de 100 ...
					while (rs.next()) {
						liDep.add(new DepartamentosBean(rs.getString("ID_"), rs.getString("DESCRIPTION_"), rs.getString("ALF_GROUP_"), rs.getInt("DECRETABLE_"), rs.getInt("HABILITADO_"), rs.getInt("DECRETABLE_REGISTRO_")));
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La consulta devuelve demasiados resultados, especifique un criterio más concreto.", ""));
					liDep = null;
				}
			}

		} catch (SQLException e) {
			logger.error(e);
		}finally {
			rs.close(); 
			ps.close();
			DataBaseUtils.close(con);
		}
		return liDep;
	}

	public  String obtenerDepartamento(String criterio, String camboBusqueda, String campoDevuelto) {
		String data = "";
		String sql = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (criterio != null) {
			try {
				con = DataBaseUtils.getConnection("OC3F");
				if (camboBusqueda.equals("alf_group")) {
					sql = "SELECT * FROM ROLE_ WHERE ALF_GROUP_=?";
				}
				if (camboBusqueda.equals("id")) {
					sql = "SELECT * FROM ROLE_ WHERE ID_=?";
				}
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, criterio);
				rs = ps.executeQuery();
				while (rs.next()) {
					if (campoDevuelto.equals("description")) {
						data = rs.getString("DESCRIPTION_");
					}
					if (campoDevuelto.equals("alf_group")) {
						data = rs.getString("ALF_GROUP_");
					}
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		} else {
			data = "Todos";
		}
		return data;
	}

}
