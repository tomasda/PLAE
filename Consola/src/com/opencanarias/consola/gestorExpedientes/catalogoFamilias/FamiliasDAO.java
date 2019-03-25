package com.opencanarias.consola.gestorExpedientes.catalogoFamilias;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.*;

public class FamiliasDAO implements Serializable {
	private static Logger logger = Logger.getLogger(FamiliasDAO.class);
	private static final long serialVersionUID = 1L;
	private List<FamiliasBean> famList;

	private String SO;
	private String SO2;

	public FamiliasDAO(){
		//Variables de búsqueda para la sesión.
		SO ="_";
		SO2 = "_";
		famList = null;
	}

	public List<FamiliasBean> getList(String searchOption, String searchOption2) throws Exception {
		if (null!=searchOption || null!=searchOption2) {
			if (SO!=searchOption || SO2 !=searchOption2) {
				SO=searchOption; SO2=searchOption2;
				dataList(searchOption, searchOption2);
			}
		}
		return famList;
	}

		public List<FamiliasBean> updateList(String searchOption, String searchOption2) {
			dataList(searchOption, searchOption2);
			return famList;
		}

	private void dataList(String searchOption, String searchOption2) {
		famList = new ArrayList<FamiliasBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		int numRows = 0;
		try { 
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM CAT_FAMILIAS WHERE UPPER(ID) LIKE UPPER(?) AND UPPER(DESCRIPCION) LIKE UPPER(?) ORDER BY ID ASC";
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, searchOption+"%");
			ps.setString(2, searchOption2+"%");
			rs = ps.executeQuery();
			try {
				rs.last();
				numRows = rs.getRow();
				rs.beforeFirst();
				if (numRows < 100) { // Sí los resultados son menos de 100
					while (rs.next()) {
						FamiliasBean fam = new FamiliasBean(rs.getString("ID"), rs.getString("DESCRIPCION"));
						famList.add(fam);
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La consulta devuelve demasiados resultados.", "Defina un criterio más específico."));
					famList = null;
				}
			}catch(SQLException e) {
				// Muestra alerta si no se encuentran.
				FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se han encontrado resultados.", ""));
				famList = null;
			}finally {
				rs.close(); 
				ps.close();
			}

		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la consulta a BBDD.", ""));
		}finally {
			DataBaseUtils.close(con);
		}
	}

	public FamiliasBean getFamilyData(String data) {
		FamiliasBean fb = new FamiliasBean();
		String sql = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM CAT_FAMILIAS WHERE ID LIKE ? order by ID asc";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, data);
			rs = ps.executeQuery();
			while (rs.next()) {
				fb.ID = (rs.getString("ID"));
				fb.DESCRIPTION = (rs.getString("DESCRIPCION"));
			}
			rs.close(); 
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error en la consulta a BBDD.", ""));
		}finally {
			DataBaseUtils.close(con);
		}
		return fb;
	}

	@SuppressWarnings("resource")
	public void setFamilyData(String data, String action, FamiliasBean fb) {
		String sql = "";
		Connection con = null;
		PreparedStatement ps = null;
		try {

			if (action.equals("new")) {
				sql = "insert into CAT_FAMILIAS (ID,DESCRIPCION) values (?,?)";
				con = DataBaseUtils.getConnection("OC3F");
				ps = con.prepareStatement(sql);
				ps.setString(1, fb.ID);
				ps.setString(2, fb.DESCRIPTION);
				ps.executeQuery();
				FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos se han guardado correctamente.", ""));
			}
			if (action.equals("save")) {
				sql = "update CAT_FAMILIAS set ID=?,DESCRIPCION=? WHERE ID=?";
				con = DataBaseUtils.getConnection("OC3F");
				ps = con.prepareStatement(sql);
				ps.setString(1, fb.ID);
				ps.setString(2, fb.DESCRIPTION);
				ps.setString(3, data);
				ps.executeQuery();
				FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos se han guardado correctamente.", ""));
			}
			if (action.equals("delete")) {
				sql = "DELETE FROM CAT_FAMILIAS WHERE ID=?";
				con = DataBaseUtils.getConnection("OC3F");
				ps = con.prepareStatement(sql);
				ps.setString(1, data);
				ps.executeQuery();
				FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos se han guardado correctamente.", ""));
			}
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la consulta a BBDD", "  Error!"));
		}finally {
			DataBaseUtils.close(con);
		}
	}

	/*
	 * Getters and Setters
	 */
	
	public List<FamiliasBean> getFamList() {
		return famList;
	}

	public void setFamList(List<FamiliasBean> famList) {
		this.famList = famList;
	}
	
	
}