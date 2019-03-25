package com.opencanarias.consola.sede.tercerosrepresentantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.consola.registro.terceros.TercerosBean;
import com.opencanarias.consola.registro.terceros.TercerosDAO;
import com.opencanarias.consola.utilidades.DataBaseUtils;
import com.opencanarias.consola.utilidades.DateUtils;

public class TercerosRepresentantesDAO {
	private static Logger logger = Logger.getLogger(TercerosRepresentantesDAO.class);


	public List<TercerosRepresentantesListBean> tercerosRepresentantesList(String searchOption, String searchOption2) {
		List<TercerosRepresentantesListBean> listTerRep = new ArrayList<TercerosRepresentantesListBean>();
		TercerosRepresentantesListBean trlb;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		// Obtención de la lista de Interesados
		try {
			sql="select RT.INTERESADO_ID,TER.NOMBRE, TER.APELLIDO_1, TER.APELLIDO_2, TER.RAZON_SOCIAL, TER.IDENTIFICACION from REPRESENTACIONES_TERCEROS RT join TERCEROS TER on TER.ID = RT.INTERESADO_ID " + 
					"where (UPPER(NOMBRE) like UPPER (?) and UPPER(IDENTIFICACION) like UPPER (?) and (UPPER(APELLIDO_1) like UPPER ('%') OR APELLIDO_1 IS NULL) and (UPPER(APELLIDO_2) like UPPER ('%') OR APELLIDO_2 IS NULL)) " + 
					"or ( UPPER(RAZON_SOCIAL) like UPPER (?) and UPPER(IDENTIFICACION) like UPPER (?)) " + 
					"group by RT.INTERESADO_ID,TER.NOMBRE, TER.APELLIDO_1, TER.APELLIDO_2, TER.RAZON_SOCIAL, TER.IDENTIFICACION " + 
					"order by TER.NOMBRE asc, TER.APELLIDO_1 asc, TER.APELLIDO_2 asc, TER.RAZON_SOCIAL asc";

			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, "%"+searchOption+"%");
			ps.setString(2, "%"+searchOption2+"%");
			ps.setString(3, "%"+searchOption+"%");
			ps.setString(4, "%"+searchOption2+"%");
			rs =  ps.executeQuery();
			while(rs.next()) {
				trlb = new TercerosRepresentantesListBean();
				trlb.setTerceroID(rs.getInt("interesado_id"));
				trlb.setTerceroName(name(rs.getInt("interesado_id")));
				trlb.setTerceroIdentification(rs.getString("IDENTIFICACION"));
				listTerRep.add(trlb);
			}
			
			// Obtención de la lista de representantes por interesado.
			for (TercerosRepresentantesListBean li : listTerRep) {
				RepresentantesBean rb = null;
				List<RepresentantesBean> repList = new ArrayList<RepresentantesBean>();
				try {
					sql = "select representante_id,id from representaciones_terceros where INTERESADO_ID = ?";
					ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ps.setInt(1, li.getTerceroID());
					rs = ps.executeQuery();
					while(rs.next()) {
						rb = new RepresentantesBean();
						rb.setId_(rs.getInt("representante_id"));
						rb.setName_(name(rb.getId_()));
						rb.setRegisrto_(rs.getInt("id"));
						rb.setIdentificacion_(identification(rb.getId_()));
						repList.add(rb);
					}
				}catch (Exception e) {
					logger.error(e);
				}
				li.setRepList(repList);
			}

		}catch (Exception e) {
			logger.error(e);
		}
		return listTerRep;
	}	

	private String identification (int id) {
		TercerosDAO td = new TercerosDAO();
		TercerosBean tb = null;
		tb = td.getTercero(id);
		return tb.getIDENTIFICACION();
	}
	
	private String name(int id) {
		TercerosDAO td = new TercerosDAO();
		TercerosBean tb = null;
		StringBuffer terceroName = new StringBuffer();
		String tipoIdentificacion;
		tb = td.getTercero(id);
		tipoIdentificacion = tb.getTIPO_IDENTIFICACION_ID();
		switch (tipoIdentificacion) {
		case "N":
		case "E":
		case "O":
		case "P":
			terceroName.append(tb.getNOMBRE());
			if (null!=tb.getAPELLIDO_1()) {
				terceroName.append(" ").append(tb.getAPELLIDO_1());
				if(null!=tb.getAPELLIDO_2()) {
					terceroName.append(" ").append(tb.getAPELLIDO_2());
				}
			}
			break;
		case "C":
		case "X":
			terceroName.append(tb.getRAZON_SOCIAL());
			break;
		}
		return terceroName.toString();
	}

	public boolean save(TercerosRepresentantesBean trb) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		boolean bol = false;
		DateUtils du = new DateUtils();
		Timestamp fechaActual = du.timeStampFecha();
		try {
			sql = "INSERT INTO REPRESENTACIONES_TERCEROS VALUES (SEQ_REPRESENTACIONES_TERCEROS.NEXTVAL,?,?,?,?,?,?,?)";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setTimestamp(1, fechaActual);
			ps.setTimestamp(2, null);
			ps.setString(3, trb.getDOCUMENTO_NOMBRE());
			ps.setString(4, trb.getDOCUMENTO_DESCRIPCION());
			ps.setString(5, trb.getDOCUMENTO_URI());
			ps.setInt(6, trb.getINTERESADO_ID());
			ps.setInt(7, trb.getREPRESENTANTE_ID());
			rs =  ps.executeQuery();
			bol = true;
			rs.close();
			ps.close();
		}catch (Exception e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}

		return bol;
	}
	
	public boolean exists(TercerosRepresentantesBean trb) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		boolean bol = false;
		try {
			sql = "select * from REPRESENTACIONES_TERCEROS where INTERESADO_ID = ?  and REPRESENTANTE_ID = ?";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, trb.getINTERESADO_ID());
			ps.setInt(2, trb.getREPRESENTANTE_ID());
			rs =  ps.executeQuery();
			if (rs.next()) {
				bol = true;
			}
		}catch (Exception e) {
			logger.error(e);
		}

		return bol;
	}

	public boolean delete(int iD) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bol = false;
		String sql = null;
		try {
			sql = "DELETE FROM REPRESENTACIONES_TERCEROS WHERE ID = ?";
			con = DataBaseUtils.getConnection("EAREGISTRO");
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, iD);
			rs =  ps.executeQuery();
			bol = true;
			rs.close();
			ps.close();
		}catch (Exception e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return bol;
	}
}
