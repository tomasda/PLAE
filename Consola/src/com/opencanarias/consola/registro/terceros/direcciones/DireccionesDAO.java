package com.opencanarias.consola.registro.terceros.direcciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

public class DireccionesDAO {
	private static Logger logger = Logger.getLogger(DireccionesDAO.class);

	public List<DireccionesBean> getDireccionesList(int iD_TMP) {
		List<DireccionesBean> listDir = new ArrayList<DireccionesBean>();
		String sql;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM DIRECCIONES WHERE TERCERO_ID = ?";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, iD_TMP);
			rs = ps.executeQuery();
			while (rs.next()) {
				DireccionesBean direc = new DireccionesBean();
				direc.setID(rs.getInt("ID"));
				direc.setDIRECCION(rs.getString("DIRECCION"));
				direc.setCODIGO_POSTAL(rs.getString("CODIGO_POSTAL"));
				direc.setOBSERVACIONES(rs.getString("OBSERVACIONES"));
				direc.setPAIS_ID(rs.getString("PAIS_ID"));
				direc.setTERCERO_ID(rs.getInt("TERCERO_ID"));
				direc.setMUNICIPIO_ID(rs.getString("MUNICIPIO_ID"));
				direc.setPROVINCIA_ID(rs.getString("PROVINCIA_ID"));
				listDir.add(direc);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return listDir;
	}

	public void saveDirecciones(List<DireccionesBean> listDireccionesB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		con = DataBaseUtils.getConnection("EAREGISTRO");
		for (DireccionesBean lid : listDireccionesB) {
			try {
				if (lid.getID() == 0) {// Sí el registro es nuevo.
					sql = "INSERT INTO DIRECCIONES (ID,DIRECCION,CODIGO_POSTAL,OBSERVACIONES,PAIS_ID,TERCERO_ID,MUNICIPIO_ID,PROVINCIA_ID)"
							+ " VALUES(SEQ_DIRECCIONES.NEXTVAL,?,?,?,?,?,?,?)";
					ps = con.prepareStatement(sql);
					ps.setString(1, lid.getDIRECCION());
					ps.setString(2, lid.getCODIGO_POSTAL());
					ps.setString(3, lid.getOBSERVACIONES());
					ps.setString(4, lid.getPAIS_ID());
					ps.setInt(5, lid.getTERCERO_ID());
					ps.setString(6, lid.getMUNICIPIO_ID());
					ps.setString(7, lid.getPROVINCIA_ID());
					ps.executeQuery();
				} else { // Sí el registro ya existe.
					sql = "UPDATE DIRECCIONES SET DIRECCION = ?,CODIGO_POSTAL = ?,OBSERVACIONES = ?,PAIS_ID = ?,MUNICIPIO_ID = ?, PROVINCIA_ID = ? where ID = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, lid.getDIRECCION());
					ps.setString(2, lid.getCODIGO_POSTAL());
					ps.setString(3, lid.getOBSERVACIONES());
					ps.setString(4, lid.getPAIS_ID());
					ps.setString(5, lid.getMUNICIPIO_ID());
					ps.setString(6, lid.getPROVINCIA_ID());
					ps.setInt(7, lid.getID());
					ps.executeQuery();
				}
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		DataBaseUtils.close(con);
	}

	public boolean deleteDirecciones(List<DireccionesBean> listDireccionesB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		Boolean bol = true;
		if (null != listDireccionesB) {
			if(!listDireccionesB.isEmpty()) {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				for (DireccionesBean lic : listDireccionesB) {
					try {
						sql = "DELETE FROM DIRECCIONES where ID = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, lic.getID());
						ps.executeQuery();
						ps.close();
					} catch (SQLException e) {
						logger.error(e);
						bol = false;
					}
				}
				DataBaseUtils.close(con);
			}
		}
		return bol;	
	}

}
