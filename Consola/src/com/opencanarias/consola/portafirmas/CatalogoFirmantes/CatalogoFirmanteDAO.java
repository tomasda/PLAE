/**
 * 
 */
package com.opencanarias.consola.portafirmas.CatalogoFirmantes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

/**
 * @author Tomás Delgado Acosta.
 *
 */
public class CatalogoFirmanteDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CatalogoFirmanteDAO.class);
	
	
	public List<FirmanteBean> findFirmantes(String searchOption, String searchOption2, String searchOption3, String searchOption4) {
		List<FirmanteBean> listOfFirmantes = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con  = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM pf_personas WHERE (UPPER(NOMBRE) LIKE UPPER (?)  OR NOMBRE IS NULL) "
					+ "AND (UPPER(APELLIDO1) LIKE UPPER (?) OR APELLIDO1 IS NULL) "
					+ "AND (UPPER(APELLIDO2) LIKE UPPER (?) OR APELLIDO2 IS NULL) "
					+ "AND (UPPER(DNI_NIE) LIKE UPPER (?) OR DNI_NIE IS NULL) "
					+ "ORDER BY nombre ASC, APELLIDO1 ASC, APELLIDO2 ASC";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, searchOption + "%");
			ps.setString(2, searchOption2 + "%");
			ps.setString(3, searchOption3 + "%");
			ps.setString(4, searchOption4 + "%");
			rs = ps.executeQuery();
			rs.last();
			int numRows = rs.getRow();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				@SuppressWarnings("unused")
				int index = 0;
				rs.beforeFirst(); // vuelvo al primer registro
				if (numRows < 100) { // Sí los resultados son menos de 100
					listOfFirmantes = new ArrayList<FirmanteBean>();
					FirmanteBean firmante;
					while (rs.next()) { // Creo la lista de firmantes
						firmante = new FirmanteBean();
						firmante.setIdFirmante(Integer.parseInt(rs.getString("ID_PERSONA")));
						firmante.setNombre(rs.getString("NOMBRE"));
						firmante.setApellido(rs.getString("APELLIDO1"));
						firmante.setApellido2(rs.getString("APELLIDO2"));
						firmante.setDNI_NIE(rs.getString("DNI_NIE"));
						firmante.setCargo(rs.getString("CARGO"));
						firmante.setMail(rs.getString("MAIL"));
						firmante.setEsFirmante(Integer.parseInt(rs.getString("ES_FIRMANTE")));
						firmante.setEsValidador(Integer.parseInt(rs.getString("ES_VALIDADOR")));
						firmante.setAdjuntos_alertas(Integer.parseInt(rs.getString("ADJUNTOS_ALERTAS")));
						firmante.setEs_usuario_pruebas(Integer.parseInt(rs.getString("ES_USUARIO_PRUEBAS")));
						
						listOfFirmantes.add(firmante);
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La consulta devuelve demasiados resultados.", "Especifique un criterio más concreto."));
				}
			}
			// Cerrar la conexión con la BBDD
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		
		return listOfFirmantes;
	}


	public FirmanteBean getFirmanteById(String idFirmante) {
		FirmanteBean firmante = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM pf_personas WHERE ID_PERSONA = ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, idFirmante);
			rs = ps.executeQuery();
			rs.beforeFirst();
			while (rs.next()) {
				firmante = new FirmanteBean(Integer.parseInt(rs.getString("ID_PERSONA")),rs.getString("NOMBRE"),rs.getString("APELLIDO1"),rs.getString("APELLIDO2"),
						rs.getString("DNI_NIE"),rs.getString("CARGO"),rs.getString("MAIL"),Integer.parseInt(rs.getString("ES_FIRMANTE")),Integer.parseInt(rs.getString("ES_VALIDADOR")),
						Integer.parseInt(rs.getString("ADJUNTOS_ALERTAS")),Integer.parseInt(rs.getString("ES_USUARIO_PRUEBAS")));
			}
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return firmante;
	}


	public void saveFirmante(FirmanteBean firmante) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			// Modificar el firmante
			sql = "UPDATE pf_personas set NOMBRE = ?, APELLIDO1 = ?, APELLIDO2 = ?, DNI_NIE = ?, CARGO = ?, MAIL = ?, ES_FIRMANTE = ?,ES_VALIDADOR = ?,ADJUNTOS_ALERTAS = ?,ES_USUARIO_PRUEBAS = ? "
					+ "where ID_PERSONA = ?";
			con = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql);
			ps.setString(1, firmante.getNombre());
			ps.setString(2, firmante.getApellido());
			ps.setString(3, firmante.getApellido2());
			ps.setString(4, firmante.getDNI_NIE());
			ps.setString(5, firmante.getCargo());
			ps.setString(6, firmante.getMail());
			ps.setInt(7, firmante.getEsFirmante());
			ps.setInt(8, firmante.getEsValidador());
			ps.setInt(9, firmante.getAdjuntos_alertas());
			ps.setInt(10, firmante.getEs_usuario_pruebas());
			ps.setInt(11, firmante.getIdFirmante());
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}
		DataBaseUtils.close(con);	
	}

	public void createFirmante(FirmanteBean firmante) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			// Modificar el firmante
			sql = "INSERT INTO pf_personas (NOMBRE,APELLIDO1,APELLIDO2,DNI_NIE,CARGO,MAIL,ES_FIRMANTE,ES_VALIDADOR,ADJUNTOS_ALERTAS,ES_USUARIO_PRUEBAS,ID_PERSONA) values (?,?,?,?,?,?,?,?,?,?,?)";
			con = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql);
			ps.setString(1, firmante.getNombre());
			ps.setString(2, firmante.getApellido());
			ps.setString(3, firmante.getApellido2());
			ps.setString(4, firmante.getDNI_NIE());
			ps.setString(5, firmante.getCargo());
			ps.setString(6, firmante.getMail());
			ps.setInt(7, firmante.getEsFirmante());
			ps.setInt(8, firmante.getEsValidador());
			ps.setInt(9, firmante.getAdjuntos_alertas());
			ps.setInt(10, firmante.getEs_usuario_pruebas());
			ps.setInt(11, firmante.getIdFirmante());
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}
		DataBaseUtils.close(con);	
	}

	public void deleteFirmante(String idFirmante) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			// Elimina el firmante 
			sql = "DELETE FROM pf_personas WHERE ID_PERSONA = ?";
			con = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql);
			ps.setString(1, idFirmante);
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}
		DataBaseUtils.close(con);	
	}
	
	public int getNewID() {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql="SELECT ID_PERSONA FROM pf_personas WHERE ROWNUM = 1 order by ID_PERSONA desc";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.beforeFirst();
			while (rs.next()) {
				result = Integer.parseInt(rs.getString("ID_PERSONA"));
			}
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		if (result!=0) {
			result++;
		}
		return result;
	}
	
	public int getIfNIFExist(String dni_nie) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql="SELECT COUNT(*) FROM pf_personas WHERE DNI_NIE = ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, dni_nie);
			rs = ps.executeQuery();
			rs.beforeFirst();
			while (rs.next()) {
				result = Integer.parseInt(rs.getString("COUNT(*)"));
			}
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return result;
	}


}
