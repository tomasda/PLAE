package com.opencanarias.consola.registro.terceros.deh;

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

public class DEHDAO {
	private static Logger logger = Logger.getLogger(DEHDAO.class);

	public List<DEHBean> getDEHList(int iD_TMP) {
		List<DEHBean> liDEH =  new ArrayList<DEHBean>();
		String sql;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM DEHS WHERE TERCERO_ID = ?";
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
					DEHBean deh = new DEHBean();
					deh.setID(rs.getInt("ID"));
					deh.setDEH(rs.getString("DEH"));
					deh.setTerceroID(rs.getInt("TERCERO_ID"));
					liDEH.add(deh);
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return liDEH;
	}

	public void saveDEH(List<DEHBean> listDehB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		con = DataBaseUtils.getConnection("EAREGISTRO");
		for (DEHBean lic : listDehB) {
			try {
				if (lic.getID() == 0) {// Sí el registro es nuevo.
					sql = "INSERT INTO DEHS (ID,DEH,TERCERO_ID) VALUES(SEQ_EMAILS.NEXTVAL,?,?)";
					ps = con.prepareStatement(sql);
					ps.setString(1, lic.getDEH());
					ps.setInt(2, lic.getTerceroID());
					ps.executeQuery();
				} else { // Sí el registro ya existe.
					sql = "UPDATE DEHS SET DEH = ? where ID=?";
					ps = con.prepareStatement(sql);
					ps.setString(1, lic.getDEH());
					ps.setInt(2, lic.getTerceroID());
					ps.executeQuery();
				}
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		DataBaseUtils.close(con);
	}

	public boolean deleteDEH(List<DEHBean> listDehB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		Boolean bol = true;
		if (null != listDehB) {
			if(!listDehB.isEmpty()) {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				for (DEHBean lic : listDehB) {
					try {
						sql = "DELETE FROM DEHS where ID=?";
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
