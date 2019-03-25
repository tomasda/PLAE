package com.opencanarias.consola.gestorExpedientes.catalogoActivity;

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

import com.opencanarias.consola.utilidades.DataBaseUtils;

import org.jboss.logging.Logger;

public class ActivityDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ActivityDAO.class);
	private  List<ActivityBean> liA;
	

	public ActivityDAO() {
	}

	public  List<ActivityBean> getActivityList(String procppalID) {
		liA = new ArrayList<ActivityBean>();
		String sql = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM ACTIVITY_ WHERE WF_ID_ LIKE ? ORDER BY WF_VERSION_ DESC";
			con  = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, procppalID + "%");
			rs = ps.executeQuery();
			Result res = null;
			rs.next();
			String lastVersion = rs.getString("WF_VERSION_");
			
			sql = "SELECT * FROM ACTIVITY_ WHERE WF_ID_ LIKE ? AND WF_VERSION_= ? ORDER BY WF_VERSION_";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, procppalID + "%");
			ps.setString(2, lastVersion);
			rs = ps.executeQuery();
			res = ResultSupport.toResult(rs);
			int numRows = res.getRowCount();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
				liA = null;
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				rs.beforeFirst(); // vuelvo al primer registro
				if (numRows < 100) { // Sí los resultados son menos de 100 ...
					while (rs.next()) {
						liA.add(new ActivityBean(rs.getString("ID_"), rs.getString("WF_ID_"), rs.getString("WF_VERSION_"), rs.getString("DESCRIPTION_"), rs
								.getString("ROLE_ID_"), rs.getInt("PHASE_"), rs.getInt("RESOLUTION_ACTIVITY_"), rs.getInt("NOTIFICATIONS_"), rs
								.getInt("EXPIRATION_DAYS_"), rs.getString("ALERT_"), rs.getString("ALERT_CONDITION_"), rs.getString("ALERT_DPTOS_")));
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta devuelve demasiados resultados, especifique un criterio más concreto.", ""));
					liA = null;
				}
			}
			rs.close(); // Cerrar la conexión con la BBDD
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return liA;
	}

	public ActivityBean getActivityB(String actID, String iDProcPal) {
		ActivityBean ab = new ActivityBean();
		String sql = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM ACTIVITY_ WHERE WF_ID_ LIKE ? ORDER BY WF_VERSION_ DESC";
			con  = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, iDProcPal + "%");
			rs = ps.executeQuery();
			Result res = null;
			rs.next();
			String lastVersion = rs.getString("WF_VERSION_");
			
			sql = "SELECT * FROM ACTIVITY_ WHERE WF_ID_ LIKE ? AND WF_VERSION_= ? and ID_= ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, iDProcPal + "%");
			ps.setString(2, lastVersion);
			ps.setString(3, actID);
			rs = ps.executeQuery();
			res = ResultSupport.toResult(rs);
			int numRows = res.getRowCount();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				rs.beforeFirst(); // vuelvo al primer registro
				if (numRows < 100) { // Sí los resultados son menos de 100 ...
					while (rs.next()) {
						ab = new ActivityBean(rs.getString("ID_"), rs.getString("WF_ID_"), rs.getString("WF_VERSION_"), rs.getString("DESCRIPTION_"), rs
								.getString("ROLE_ID_"), rs.getInt("PHASE_"), rs.getInt("RESOLUTION_ACTIVITY_"), rs.getInt("NOTIFICATIONS_"), rs
								.getInt("EXPIRATION_DAYS_"), rs.getString("ALERT_"), rs.getString("ALERT_CONDITION_"), rs.getString("ALERT_DPTOS_"));
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta devuelve demasiados resultados, especifique un criterio más concreto.", ""));
					liA = null;
				}
			}
			rs.close(); // Cerrar la conexión con la BBDD
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		
		return ab;
	}

	public void setActivityB(ActivityBean activityB) {
		//this.activityB = activityB;
	}

	public List<ActivityBean> getLiA() {
		return liA;
	}

	public void setLiA(List<ActivityBean> liA) {
		this.liA = liA;
	}
	
}
