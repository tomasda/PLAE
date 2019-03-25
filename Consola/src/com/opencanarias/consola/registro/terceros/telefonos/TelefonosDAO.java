package com.opencanarias.consola.registro.terceros.telefonos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

public class TelefonosDAO {
	private static Logger logger = Logger.getLogger(TelefonosDAO.class);


	public List<TelefonosBean> getTelefonosList(int iD_TMP) {

		List<TelefonosBean>	liTel = new ArrayList<TelefonosBean>();
		String sql;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM TELEFONOS WHERE TERCERO_ID = ?";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, iD_TMP);
			rs = ps.executeQuery();
			Result res = null;
			int numRows = 0;
			res = ResultSupport.toResult(rs);
			numRows = res.getRowCount();
			if (numRows > 0) {
				rs.beforeFirst(); // vuelvo al primer registro
				while (rs.next()) {
					TelefonosBean tel = new TelefonosBean();
					tel.setID(rs.getInt("ID"));
					tel.setTelefono(rs.getString("TELEFONO"));
					tel.setTerceroID(rs.getInt("TERCERO_ID"));
					liTel.add(tel);
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return liTel;
	}


	public void saveTelefonos(List<TelefonosBean> listTelefonosB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		con = DataBaseUtils.getConnection("EAREGISTRO");
		for (TelefonosBean lit : listTelefonosB) {
			if (null!=lit && !lit.getTelefono().isEmpty()) {
				try {
					if (lit.getID() == 0) {// Sí el registro es nuevo.
						sql = "INSERT INTO TELEFONOS (ID,TELEFONO,TERCERO_ID) VALUES(SEQ_TELEFONOS.NEXTVAL,?,?)";
						ps = con.prepareStatement(sql);
						ps.setString(1, lit.getTelefono());
						ps.setInt(2, lit.getTerceroID());
						ps.executeQuery();
					} else { // Sí el registro ya existe.
						sql = "UPDATE TELEFONOS SET TELEFONO = ? where ID = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1, lit.getTelefono());
						ps.setInt(2, lit.getTerceroID());
						ps.executeQuery();
					}
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		DataBaseUtils.close(con);
	}


	public boolean deleteTelefonos(List<TelefonosBean> listTelefonosB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		Boolean bol = true;
		if (null != listTelefonosB) {
			if(!listTelefonosB.isEmpty()) {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				for (TelefonosBean lic : listTelefonosB) {
					try {
						sql = "DELETE FROM TELEFONOS where ID=?";
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
