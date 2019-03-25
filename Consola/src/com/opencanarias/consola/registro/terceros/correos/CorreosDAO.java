package com.opencanarias.consola.registro.terceros.correos;

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

public class CorreosDAO {
	private static Logger logger = Logger.getLogger(CorreosDAO.class);


	public List<CorreosBean> getCorreosList(int iD_TMP) {
		String sql;
		List<CorreosBean> listCorr = new ArrayList<CorreosBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM EMAILS WHERE TERCERO_ID = ?";
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
					CorreosBean correo = new CorreosBean();
					correo.setID(rs.getInt("ID"));
					correo.setEmail(rs.getString("EMAIL"));
					correo.setTerceroID(rs.getInt("TERCERO_ID"));
					listCorr.add(correo);
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return listCorr;
	}


	public void saveCorreos(List<CorreosBean> listCorreosB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		con = DataBaseUtils.getConnection("EAREGISTRO");
		for (CorreosBean lic : listCorreosB) {
			try {
				if (lic.getID() == 0) {// Sí el registro es nuevo.
					sql = "INSERT INTO EMAILS (ID,EMAIL,TERCERO_ID) VALUES(SEQ_EMAILS.NEXTVAL,?,?)";
					ps = con.prepareStatement(sql);
					ps.setString(1, lic.getEmail());
					ps.setInt(2, lic.getTerceroID());
					ps.executeQuery();
				} else { // Sí el registro ya existe.
					sql = "UPDATE EMAILS SET EMAIL = ? where ID = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, lic.getEmail());
					ps.setInt(2, lic.getTerceroID());
					ps.executeQuery();
				}
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		DataBaseUtils.close(con);
	}


	public boolean deleteCorreos(List<CorreosBean> listCorreosB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		Boolean bol = true;
		if (null != listCorreosB) {
			if(!listCorreosB.isEmpty()) {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				for (CorreosBean lic : listCorreosB) {
					try {
						sql = "DELETE FROM EMAILS where ID=?";
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
