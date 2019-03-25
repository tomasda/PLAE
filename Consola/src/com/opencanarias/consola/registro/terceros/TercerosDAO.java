package com.opencanarias.consola.registro.terceros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.jboss.logging.Logger;

import com.opencanarias.consola.sede.tercerosrepresentantes.ThirdPartyReportBean;
import com.opencanarias.consola.utilidades.DataBaseUtils;

public class TercerosDAO {
	private static Logger logger = Logger.getLogger(TercerosDAO.class);

	public List<TercerosBean> getTercerosList(String nombre_razonSocial, String nif_nie, String apellido1, String apellido2,String canal_preferente) {
		if (null == nombre_razonSocial) {
			nombre_razonSocial = "";
		}
		if (null == nif_nie) {
			nif_nie = "";
		}
		if (null == apellido1) {
			apellido1 = "";
		}
		if (null == apellido2) {
			apellido2 = "";
		}
		if (null == canal_preferente) {
			canal_preferente = "";
		}
		String sql;
		List<TercerosBean> listTerceros = new ArrayList<TercerosBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "select * from TERCEROS where "
					+ "(UPPER(NOMBRE) like UPPER (?) and UPPER(IDENTIFICACION) like UPPER (?) "
					+ "and (UPPER(APELLIDO_1) like UPPER (?) OR APELLIDO_1 IS NULL) and (UPPER(APELLIDO_2) like UPPER (?) OR APELLIDO_2 IS NULL) and (CANAL_PREFERENTE_ID like ? OR CANAL_PREFERENTE_ID IS NULL))"
					+ " or "
					+ "( UPPER(RAZON_SOCIAL) like UPPER (?) and UPPER(IDENTIFICACION) like UPPER (?) and (CANAL_PREFERENTE_ID like ? OR CANAL_PREFERENTE_ID IS NULL)) "
					+ "order by NOMBRE asc,APELLIDO_1 asc, APELLIDO_2 asc, RAZON_SOCIAL asc";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, "%"+nombre_razonSocial+"%");
			ps.setString(2, nif_nie+"%");
			ps.setString(3, "%"+apellido1+"%");
			ps.setString(4, "%"+apellido2+"%");
			ps.setString(5, canal_preferente+"%");
			ps.setString(6, "%"+nombre_razonSocial+"%");
			ps.setString(7, nif_nie+"%");
			ps.setString(8, canal_preferente+"%");
			rs = ps.executeQuery();
			Result res = null;
			res = ResultSupport.toResult(rs);
			int numRows = res.getRowCount();
			if (null != rs) {
				rs.beforeFirst();
				if (numRows > 0) {
					while (rs.next()) {
						TercerosBean tb = new TercerosBean();
						tb.setID(rs.getInt("ID"));
						tb.setIDENTIFICACION( rs.getString("IDENTIFICACION"));
						tb.setRAZON_SOCIAL(rs.getString("RAZON_SOCIAL"));
						tb.setNOMBRE(rs.getString("NOMBRE"));
						tb.setAPELLIDO_1(rs.getString("APELLIDO_1"));
						tb.setAPELLIDO_2(rs.getString("APELLIDO_2"));
						tb.setOBSERVACIONES(rs.getString("OBSERVACIONES"));
						tb.setTIPO_IDENTIFICACION_ID(rs.getString("TIPO_IDENTIFICACION_ID"));
						tb.setCANAL_PREFERENTE_ID(rs.getString("CANAL_PREFERENTE_ID"));
						tb.setNOTIF_ELECT_OBLIGATORIA(rs.getInt("NOTIF_ELECT_OBLIGATORIA"));
						tb.setREGISTRO_ELECTRONICO(rs.getInt("REGISTRO_ELECTRONICO"));
						listTerceros.add(tb);
					}
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return listTerceros;
	}

	public TercerosBean getTercero(int iD) {
		String sql;
		TercerosBean tb = new TercerosBean();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "select * from TERCEROS where ID = ?";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, iD);
			rs = ps.executeQuery();
			rs.next();
			tb.setID(rs.getInt("ID"));
			tb.setIDENTIFICACION( rs.getString("IDENTIFICACION"));
			tb.setRAZON_SOCIAL(rs.getString("RAZON_SOCIAL"));
			tb.setNOMBRE(rs.getString("NOMBRE"));
			tb.setAPELLIDO_1(rs.getString("APELLIDO_1"));
			tb.setAPELLIDO_2(rs.getString("APELLIDO_2"));
			tb.setOBSERVACIONES(rs.getString("OBSERVACIONES"));
			tb.setTIPO_IDENTIFICACION_ID(rs.getString("TIPO_IDENTIFICACION_ID"));
			tb.setCANAL_PREFERENTE_ID(rs.getString("CANAL_PREFERENTE_ID"));
			tb.setNOTIF_ELECT_OBLIGATORIA(rs.getInt("NOTIF_ELECT_OBLIGATORIA"));
			tb.setREGISTRO_ELECTRONICO(rs.getInt("REGISTRO_ELECTRONICO"));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return tb;
	}
	public TercerosBean getTercero(String identificacion) {
		String sql;
		TercerosBean tb = new TercerosBean();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "select * from TERCEROS where IDENTIFICACION = ?";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, identificacion);
			rs = ps.executeQuery();
			rs.next();
			tb.setID(rs.getInt("ID"));
			tb.setIDENTIFICACION( rs.getString("IDENTIFICACION"));
			tb.setRAZON_SOCIAL(rs.getString("RAZON_SOCIAL"));
			tb.setNOMBRE(rs.getString("NOMBRE"));
			tb.setAPELLIDO_1(rs.getString("APELLIDO_1"));
			tb.setAPELLIDO_2(rs.getString("APELLIDO_2"));
			tb.setOBSERVACIONES(rs.getString("OBSERVACIONES"));
			tb.setTIPO_IDENTIFICACION_ID(rs.getString("TIPO_IDENTIFICACION_ID"));
			tb.setCANAL_PREFERENTE_ID(rs.getString("CANAL_PREFERENTE_ID"));
			tb.setNOTIF_ELECT_OBLIGATORIA(rs.getInt("NOTIF_ELECT_OBLIGATORIA"));
			tb.setREGISTRO_ELECTRONICO(rs.getInt("REGISTRO_ELECTRONICO"));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return tb;
	}

	public void saveTercero(TercerosBean tb) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		con = DataBaseUtils.getConnection("EAREGISTRO");
		try {
			if (tb.getID() == 0) {// Sí el registro es nuevo.
				sql = "INSERT INTO TERCEROS (ID,IDENTIFICACION,RAZON_SOCIAL,NOMBRE,APELLIDO_1,APELLIDO_2,OBSERVACIONES,TIPO_IDENTIFICACION_ID,CANAL_PREFERENTE_ID,NOTIF_ELECT_OBLIGATORIA,REGISTRO_ELECTRONICO)" + 
						"VALUES (SEQ_TERCEROS.NEXTVAL,UPPER(?),?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, tb.getIDENTIFICACION());
				ps.setString(2, tb.getRAZON_SOCIAL());
				ps.setString(3, tb.getNOMBRE());
				ps.setString(4, tb.getAPELLIDO_1());
				ps.setString(5, tb.getAPELLIDO_2());
				ps.setString(6, tb.getOBSERVACIONES());
				ps.setString(7, tb.getTIPO_IDENTIFICACION_ID());
				ps.setString(8, tb.getCANAL_PREFERENTE_ID());
				ps.setInt(9, tb.getNOTIF_ELECT_OBLIGATORIA());
				ps.setInt(10, tb.getREGISTRO_ELECTRONICO());
				ps.executeQuery();
			} else { // Sí el registro ya existe.
				sql = "UPDATE TERCEROS SET IDENTIFICACION = UPPER(?), RAZON_SOCIAL = UPPER(?), NOMBRE = UPPER(?), APELLIDO_1 = UPPER(?), APELLIDO_2 = UPPER(?), OBSERVACIONES = ?, TIPO_IDENTIFICACION_ID = ?,"
						+ " CANAL_PREFERENTE_ID = ?, NOTIF_ELECT_OBLIGATORIA = ?, REGISTRO_ELECTRONICO = ? WHERE ID = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, tb.getIDENTIFICACION());
				ps.setString(2, tb.getRAZON_SOCIAL());
				ps.setString(3, tb.getNOMBRE());
				ps.setString(4, tb.getAPELLIDO_1());
				ps.setString(5, tb.getAPELLIDO_2());
				ps.setString(6, tb.getOBSERVACIONES());
				ps.setString(7, tb.getTIPO_IDENTIFICACION_ID());
				ps.setString(8, tb.getCANAL_PREFERENTE_ID());
				ps.setInt(9, tb.getNOTIF_ELECT_OBLIGATORIA());
				ps.setInt(10, tb.getREGISTRO_ELECTRONICO());
				ps.setInt(11, tb.getID());
				ps.executeQuery();
			}
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}
		DataBaseUtils.close(con);
	}

	public boolean exists(String identificacion) {
		String sql;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bol = false;
		try {
			sql = "select * from TERCEROS where IDENTIFICACION = ?";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, identificacion);
			rs = ps.executeQuery();
			if (rs.next()) {
				bol = true;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return bol;
	}

	public boolean deleteTercero(TercerosBean tercerosB) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		Boolean bol = true;
		con = DataBaseUtils.getConnection("EAREGISTRO");
		try {
			sql = "DELETE FROM TERCEROS where ID=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tercerosB.getID());
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			bol = false;
		}
		DataBaseUtils.close(con);
		return bol;
	}

	public List<ThirdPartyReportBean> getThirdPartyData() {
		List<ThirdPartyReportBean> list = new ArrayList<>();
		String sql;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "select THI.ID, THI.COMMON_NAME_,THI.NIF_, THI.CIF_,THI.NOTIFICATION_TYPE_,ADM.START_DATE_ from THIRD_PARTY_ THI" + 
					" join ADMINISTRATIVE_FILE_ ADM on ADM.ID_ = THI.ADMINISTRATIVE_FILE_ID_ order by ADM.START_DATE_ asc";
			con = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next()) {
				ThirdPartyReportBean third = new ThirdPartyReportBean();
				third.setId_(rs.getInt(1));
				third.setCommonName_(rs.getString(2));
				third.setNIF_(rs.getString(3));
				third.setCIF_(rs.getString(4));
				third.setNotificationTipe(rs.getString(5));
				third.setStartDate(rs.getTimestamp(6));
				list.add(third);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			DataBaseUtils.close(con);
		}
		return list;
	}

}
