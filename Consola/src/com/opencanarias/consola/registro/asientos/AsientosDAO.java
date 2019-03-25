package com.opencanarias.consola.registro.asientos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;
import com.opencanarias.consola.utilidades.DateUtils;

public class AsientosDAO  {

	private static Logger logger = Logger.getLogger(AsientosDAO.class);

	/*
	 * METODOS
	 */
	public List<AsientosBean> getAsientosList(String searchOption, String searchOption2) {
		ArrayList<AsientosBean> liTMP = new ArrayList<AsientosBean>();
		DateUtils f = new DateUtils();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (!searchOption2.isEmpty()) {
			Timestamp start = f.parseRangeDate("Start", searchOption2);
			Timestamp end = f.parseRangeDate("End", searchOption2);
			//String sql = "SELECT * FROM asientos WHERE identificacion LIKE '%" + searchOption + "%' AND fecha_hora BETWEEN '" + start + "' AND '" + end + "' ORDER BY identificacion ASC";
			String sql = "SELECT * FROM asientos WHERE identificacion LIKE ? AND fecha_hora BETWEEN ? AND ? ORDER BY identificacion ASC";
			try {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, "%"+searchOption+"%");
				ps.setTimestamp(2,  start);
				ps.setTimestamp(3,  end);
				rs = ps.executeQuery();
				//rs.last();
				int numRows = rs.getFetchSize();
				if (numRows <= 0) { // Si NO se encuentran resultados //
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados", ""));
				} else { // Si se encuentran, carga los datos en una lista para devolverlos.
					if (numRows < 100) { // Sí los resultados son menos de 100
						while (rs.next()) { // Creo la lista de expedientes
							AsientosBean Asiento = new AsientosBean();
							Asiento.setID(rs.getInt("ID"));
							Asiento.setFECHA_HORA(rs.getTimestamp("FECHA_HORA"));
							Asiento.setTIPO_REGISTRO(rs.getBoolean("TIPO_REGISTRO"));
							Asiento.setASUNTO(rs.getString("ASUNTO"));
							Asiento.setEXPEDIENTE(rs.getString("EXPEDIENTE"));
							Asiento.setNUMERO_TRANSPORTE_ENTRADA(rs.getString("NUMERO_TRANSPORTE_ENTRADA"));
							Asiento.setAPLICACION_Y_VERSION(rs.getString("APLICACION_Y_VERSION"));
							Asiento.setNOMBRE_USUARIO_ORIGEN(rs.getString("NOMBRE_USUARIO_ORIGEN"));
							Asiento.setPRUEBA(rs.getInt("PRUEBA"));
							Asiento.setOBSERVACIONES(rs.getString("OBSERVACIONES"));
//							Asiento.setDEPARTAMENTO_TRAMITACION_ID(rs.getInt("DEPARTAMENTO_TRAMITACION_ID"));
							Asiento.setENTIDAD_REGISTRAL_ID(rs.getString("ENTIDAD_REGISTRAL_ID"));
							Asiento.setESTADO_REGISTRO_ID(rs.getInt("ESTADO_REGISTRO_ID"));
							Asiento.setTIPO_TRANSPORTE_ENTRADA_ID(rs.getString("TIPO_TRANSPORTE_ENTRADA_ID"));
							Asiento.setIDENTIFICACION(rs.getString("IDENTIFICACION"));
							Asiento.setASUNTOEXPEDIENTE(rs.getString("ASUNTOEXPEDIENTE"));
							Asiento.setFAMILIA_ID(rs.getString("FAMILIA_ID"));
							Asiento.setPROCEDIMIENTO_ID(rs.getString("PROCEDIMIENTO_ID"));
							Asiento.setASOCIADOEXPEDIENTE(rs.getInt("ASOCIADOEXPEDIENTE"));
							Asiento.setFECHA_ENVIO_DPTOS(rs.getTimestamp("FECHA_ENVIO_DPTOS"));
							Asiento.setFECHA_PRIM_ACCES_DESTINAT(rs.getTimestamp("FECHA_PRIM_ACCES_DESTINAT"));
							Asiento.setLEIDO_DPTO_DESTINATARIO(rs.getInt("LEIDO_DPTO_DESTINATARIO"));
							Asiento.setORIGEN_ASIENTO_ID(rs.getString("ORIGEN_ASIENTO_ID"));
							Asiento.setIDENTIFICACION_SUBTIPO(rs.getString("IDENTIFICACION_SUBTIPO"));
							Asiento.setSUBTIPO_REGISTRO_ID(rs.getInt("SUBTIPO_REGISTRO_ID"));
							liTMP.add(Asiento);
						}
					}
				}
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se ha definido un rango de fechas para la búsqueda. Especifique uno.",""));
		}
		return liTMP;
	}

	public AsientosBean getAsiento(String numAsientoToEdit) {
		AsientosBean ab = new AsientosBean();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("EAREGISTRO");
			sql = "SELECT * FROM asientos WHERE identificacion = ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setString(1,numAsientoToEdit);
			rs = ps.executeQuery();
			while (rs.next()) {
				ab.setID(rs.getInt("ID"));
				ab.setFECHA_HORA(rs.getTimestamp("FECHA_HORA"));
				ab.setTIPO_REGISTRO(rs.getBoolean("TIPO_REGISTRO"));
				ab.setASUNTO(rs.getString("ASUNTO"));
				ab.setEXPEDIENTE(rs.getString("EXPEDIENTE"));
				ab.setNUMERO_TRANSPORTE_ENTRADA(rs.getString("NUMERO_TRANSPORTE_ENTRADA"));
				ab.setAPLICACION_Y_VERSION(rs.getString("APLICACION_Y_VERSION"));
				ab.setNOMBRE_USUARIO_ORIGEN(rs.getString("NOMBRE_USUARIO_ORIGEN"));
				ab.setPRUEBA(rs.getInt("PRUEBA"));
				ab.setOBSERVACIONES(rs.getString("OBSERVACIONES"));
//				ab.setDEPARTAMENTO_TRAMITACION_ID(rs.getInt("DEPARTAMENTO_TRAMITACION_ID"));
				ab.setENTIDAD_REGISTRAL_ID(rs.getString("ENTIDAD_REGISTRAL_ID"));
				ab.setESTADO_REGISTRO_ID(rs.getInt("ESTADO_REGISTRO_ID"));
				ab.setTIPO_TRANSPORTE_ENTRADA_ID(rs.getString("TIPO_TRANSPORTE_ENTRADA_ID"));
				ab.setIDENTIFICACION(rs.getString("IDENTIFICACION"));
				ab.setASUNTOEXPEDIENTE(rs.getString("ASUNTOEXPEDIENTE"));
				ab.setFAMILIA_ID(rs.getString("FAMILIA_ID"));
				ab.setPROCEDIMIENTO_ID(rs.getString("PROCEDIMIENTO_ID"));
				ab.setASOCIADOEXPEDIENTE(rs.getInt("ASOCIADOEXPEDIENTE"));
				ab.setFECHA_ENVIO_DPTOS(rs.getTimestamp("FECHA_ENVIO_DPTOS"));
				ab.setFECHA_PRIM_ACCES_DESTINAT(rs.getTimestamp("FECHA_PRIM_ACCES_DESTINAT"));
				ab.setLEIDO_DPTO_DESTINATARIO(rs.getInt("LEIDO_DPTO_DESTINATARIO"));
				ab.setORIGEN_ASIENTO_ID(rs.getString("ORIGEN_ASIENTO_ID"));
				ab.setIDENTIFICACION_SUBTIPO(rs.getString("IDENTIFICACION_SUBTIPO"));
				ab.setSUBTIPO_REGISTRO_ID(rs.getInt("SUBTIPO_REGISTRO_ID"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return ab;
	}

	public boolean setAsiento(AsientosBean ab) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "";
		boolean bol = false;
		try {
			con = DataBaseUtils.getConnection("EAREGISTRO");
			sql = "update ASIENTOS set ASUNTO=?, ESTADO_REGISTRO_ID=?, TIPO_TRANSPORTE_ENTRADA_ID=?, OBSERVACIONES=?, PRUEBA=?,"
					+ "expediente=?,familia_id=?,asuntoexpediente=?,asociadoexpediente=?,procedimiento_id=?, ORIGEN_ASIENTO_ID=? where id=?";
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, ab.getASUNTO());
			ps.setInt(2, ab.getESTADO_REGISTRO_ID());
			ps.setString(3, ab.getTIPO_TRANSPORTE_ENTRADA_ID());
			ps.setString(4, ab.getOBSERVACIONES());
			ps.setInt(5, ab.getPRUEBA());
			ps.setString(6, ab.getEXPEDIENTE());
			ps.setString(7, ab.getFAMILIA_ID());
			ps.setString(8, ab.getASUNTOEXPEDIENTE());
			ps.setInt(9, ab.getASOCIADOEXPEDIENTE());
			ps.setString(10, ab.getPROCEDIMIENTO_ID());
			ps.setString(11, ab.getORIGEN_ASIENTO_ID());
			ps.setInt(12, ab.getID());
			ps.executeQuery();
			bol = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos se han guardado correctamente.",""));
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return bol;
	}
}
