package com.opencanarias.consola.firmamanuscrita;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

public class FirmaManuscritaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FirmaManuscritaDAO.class);

	public List<CatDispositivosBean> getListOfDevices(String searchOption, String searchOption2) {
		List<CatDispositivosBean> dispositivos = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con  = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM CAT_DISPOSITIVO WHERE  UPPER (ID) LIKE UPPER (?) AND UPPER (DESCRIPCION) LIKE UPPER(?) ORDER BY DESCRIPCION ASC";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, "%" + searchOption + "%");
			ps.setString(2, "%" +  searchOption2 +"%");
			rs = ps.executeQuery();
			rs.last();
			int numRows = rs.getRow();
			dispositivos = new ArrayList<>();
			if (numRows > 0) {
				rs.beforeFirst(); 
				CatDispositivosBean dispositivo;
				while (rs.next()) { 
					dispositivo = new CatDispositivosBean(rs.getString("iD"), rs.getString("dESCRIPCION"), rs.getString("iD_FIRMA_MANUSCRITA"), null);
					dispositivos.add(dispositivo);
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		dispositivos = getListOfPermission(dispositivos);
		return dispositivos;
	}

	private List<CatDispositivosBean> getListOfPermission(List<CatDispositivosBean> dispositivos) {
		if (null!=dispositivos) {
		List<CatPermisosDispositivosBean> listOfPermissionDevices = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con  = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM CAT_PERMISOS_DISPOSITIVOS";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int numRows = rs.getRow();
			if (numRows > 0) {
				listOfPermissionDevices = new ArrayList<>();
				rs.beforeFirst(); 
				CatPermisosDispositivosBean permission;
				while (rs.next()) { 
					permission = new CatPermisosDispositivosBean(rs.getString("ID"),rs.getString("DESTINATARIO_ID"),rs.getString("DESTINATARIO_TIPO"),rs.getString("DISPOSITIVO_ID"),rs.getString("PERMISO_TIPO"));
					listOfPermissionDevices.add(permission);
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		if(null!=listOfPermissionDevices) {
			for (CatDispositivosBean dispositivo : dispositivos) {
				for (CatPermisosDispositivosBean catPermisosDispositivosBean : listOfPermissionDevices) {
					if(dispositivo.getID().equals(catPermisosDispositivosBean.getDISPOSITIVO_ID())) {
						List<CatPermisosDispositivosBean> list = null;
						if(null==dispositivo.getPERMISSION()) {
							list = new ArrayList<>();
							list.add(catPermisosDispositivosBean);
						}else {
							list = dispositivo.getPERMISSION();
							list.add(catPermisosDispositivosBean);
						}
						dispositivo.setPERMISSION(list);
					}
				}
			}
		}
		}
		return dispositivos;
	}


}
