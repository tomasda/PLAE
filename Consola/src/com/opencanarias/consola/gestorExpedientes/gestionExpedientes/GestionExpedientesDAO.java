package com.opencanarias.consola.gestorExpedientes.gestionExpedientes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;
import com.opencanarias.consola.utilidades.DateUtils;


public class GestionExpedientesDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(GestionExpedientesDAO.class);
	
	private  String searchOptionTMP;
	private  String searchOption2TMP;
	
	public GestionExpedientesDAO() {
		searchOptionTMP = "";
		searchOption2TMP = "";
	}


	/*
	 * BÚSQUEDA DE EXPEDIENTES
	 */
	public  List<ExpedientesBean> getBusqueda(String SearchOption, String SearchOption2) throws Exception {
		List<ExpedientesBean> datos = null;
			// Sí cambia alguno de los criterios de búsqueda, refresca la lista de datos.
			if (!searchOptionTMP.equals(SearchOption) || !searchOption2TMP.equals(SearchOption2)) {
				searchOptionTMP = SearchOption;
				searchOption2TMP = SearchOption2;
				datos = new ArrayList<ExpedientesBean>();
				datos = listaDatos(SearchOption, SearchOption2);
			}
		return datos; 
	}

	private  List<ExpedientesBean> listaDatos(String numExpTMP2, String fechasTMP2) {
		List<ExpedientesBean> datos = null;
		DateUtils f = new DateUtils();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		Timestamp startDate = f.parseRangeDate("Start", fechasTMP2);
		Timestamp endDate = f.parseRangeDate("End", fechasTMP2);
		try {
			con  = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM ADMINISTRATIVE_FILE_ WHERE  UPPER (ID_) LIKE UPPER (?) AND START_DATE_ BETWEEN ? AND ? ORDER BY ID_ ASC";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, "%" + numExpTMP2.trim() + "%");
			ps.setTimestamp(2, startDate);
			ps.setTimestamp(3,  endDate);
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
					datos = new ArrayList<ExpedientesBean>();
					ExpedientesBean o_Exp;
					while (rs.next()) { // Creo la lista de expedientes
							o_Exp = new ExpedientesBean(rs.getString("ID_"), rs.getString("OWNER_"), rs.getString("WF_INSTANCE_"), rs.getString("WF_ID_"),
								rs.getString("WF_VERSION_"), rs.getInt("STATUS_"), rs.getTimestamp("START_DATE_"), rs.getTimestamp("END_DATE_"), rs.getString("ASUNTO_"),
								rs.getString("INTERESADO_"), rs.getString("PENDING_WORKFLOW_ID_"), rs.getString("REPOSITORY_URI_"), rs.getString("RECORD_NUMBER_"),
								rs.getInt("MANDATORY_NOTIFICATION_TYPE_"), rs.getString("NOTIFICATION_TYPE_"), rs.getTimestamp("RESOLUTION_NOTIF_DATE_"), rs.getTimestamp("RESOLUTION_EFFECT_NOTIF_DATE_"));
						
						// Añadimos el objeto "Expediente" al ArrayList
						datos.add(o_Exp);
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
		return datos;
	}

	public void cancelarExpediente(String numExpediente){
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		DateUtils f = new DateUtils();
		try {
			// Modificar el expediente para pasarlo a Cancelado.
			sql = "update oc3f.administrative_file_ set status_ = 2, end_date_ = ? where id_ = ?";
			con = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql);
			ps.setTimestamp(1, f.timeStampFecha());
			ps.setString(2, numExpediente);
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}
		DataBaseUtils.close(con);
	}
	
	
	public  ExpedientesBean getSingleData(String numExpID) {
		ExpedientesBean expedienteBean = new ExpedientesBean();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM administrative_file_ WHERE ID_ = ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, numExpID);
			rs = ps.executeQuery();
			rs.beforeFirst();
			while (rs.next()) {
				expedienteBean = new ExpedientesBean(rs.getString("ID_"), rs.getString("OWNER_"), rs.getString("WF_INSTANCE_"), rs.getString("WF_ID_"),
						rs.getString("WF_VERSION_"), rs.getInt("STATUS_"), rs.getTimestamp("START_DATE_"), rs.getTimestamp("END_DATE_"), rs.getString("ASUNTO_"),
						rs.getString("INTERESADO_"), rs.getString("PENDING_WORKFLOW_ID_"), rs.getString("REPOSITORY_URI_"), rs.getString("RECORD_NUMBER_"),
						rs.getInt("MANDATORY_NOTIFICATION_TYPE_"), rs.getString("NOTIFICATION_TYPE_"), rs.getTimestamp("RESOLUTION_NOTIF_DATE_"), rs.getTimestamp("RESOLUTION_EFFECT_NOTIF_DATE_"));
			}
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return expedienteBean;
	}
	
	public  List<TrackBean> getHistoricoExp(String numExp) {
		List<TrackBean> liTrack = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		if (!numExp.equals(searchOptionTMP)) {// Sí cambia el Expediente, busca los nuevos datos.
			try {
				sql = "SELECT * FROM track_ WHERE administrative_file_id_ = ? ORDER BY start_date_ ASC";
				con = DataBaseUtils.getConnection("OC3F");
				ps=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, numExp);
				rs=ps.executeQuery();
				Result res = null;
				res = ResultSupport.toResult(rs);
				int numRows = res.getRowCount();
				if (numRows <= 0) { // Si NO se encuentran resultados muestra alerta correspondiente.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados", ""));
				} else {
					rs.beforeFirst(); // vuelvo al primer registro
					if (numRows < 100) { // Sí los resultados son menos de 100
						liTrack = new ArrayList<TrackBean>();
						int i = 1;
						String estado = "";
						while (rs.next()) {
							TrackBean track = new TrackBean();
							if (null == rs.getTimestamp("END_DATE_")) {
								estado = "En curso";
							} else {
								estado = "Finalizado";
							}
							//owner = getPropietario(rs.getString("OWNER_"));
							track.setValues(i, estado, rs.getString("ACTIVITY_"), rs.getString("WF_ID_"), rs.getString("WF_VERSION_"),
									rs.getString("ADMINISTRATIVE_FILE_ID_"), rs.getInt("ITERATION_"), rs.getString("OWNER_"), rs.getTimestamp("START_DATE_"), rs.getTimestamp("END_DATE_"),
									rs.getString("ROLE_NAME_"));
							liTrack.add(track);
							i++;
						}
					} else {// Si los resultados pasan del límite.
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La consulta devuelve demasiados resultados.", "Defina un criterio más específico."));
					}
				}
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		}
		return liTrack;
	}



	public  List<DocumentBean> getDocsExp(String numExp) {
		List<DocumentBean> liDoc = null; 
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		if (!numExp.equals(searchOptionTMP)) {// Sí cambia el Expediente, busca los nuevos datos.
			try {
				con = DataBaseUtils.getConnection("OC3F");
				sql = "SELECT * FROM document_ WHERE administrative_file_id_ = ?  ORDER BY creation_date_ ASC";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, numExp);
				rs = ps.executeQuery();
				Result res = null;
				res = ResultSupport.toResult(rs);
				int numRows = res.getRowCount();
				if (numRows <= 0) { // Si NO se encuentran resultados muestra alerta correspondiente.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
					
				} else {
					rs.beforeFirst(); // vuelvo al primer registro
					if (numRows < 100) { // Sí los resultados son menos de 100
						liDoc = new ArrayList<DocumentBean>();
						int i = 1;
						while (rs.next()) {
							DocumentBean docs = new DocumentBean();
							docs.setValues(i, rs.getString("ADMINISTRATIVE_FILE_ID_"), rs.getString("ID_"), rs.getString("ACTIVITY_"),
									rs.getString("CONTENT_TYPE_"), rs.getInt("HAS_DOCUMENT_"), rs.getTimestamp("CREATION_DATE_"), rs.getString("DESCRIPTION_"),
									rs.getString("SIGN_REFERENCE_"), rs.getString("SIGN_ACTIVITY_"), rs.getString("RECORD_NUMBER_"), rs.getTimestamp("RECORD_DATE_"),
									rs.getString("RECORD_TYPE_"), rs.getString("TYPE_"), rs.getInt("CURRENT_"), rs.getString("REPOSITORY_URI_"),
									rs.getString("STATE_"), rs.getString("DOCUMENT_TYPE"), rs.getTimestamp("ELABORATION_DATE_"), rs.getInt("SCALE_"),
									rs.getInt("INVERT_WATERMARK_"), rs.getString("ACTUACION_ID_"), rs.getTimestamp("CANCEL_DATE"), rs.getString("CANCEL_USER_ID"));
							liDoc.add(docs);
							i++;
						}
					} else {// Si los resultados pasan del límite.
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta devuelve demasiados resultados.", " Defina un criterio más específico."));
						
					}
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		}
		return liDoc;
	}


	public void returnState(){

	}


}
