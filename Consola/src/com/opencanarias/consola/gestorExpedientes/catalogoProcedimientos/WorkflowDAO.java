package com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

public class WorkflowDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WorkflowDAO.class);
	private List<WorkflowBeanExtended> liPro;
//	private WorkflowBeanExtended procedimiento;
	
	public WorkflowDAO() {
		liPro = null;
//		procedimiento = null;
		/**
		 * OJOOOOOOO
		 */
		//listadoDeProcedimientos();
		/**
		 * OJOOOOO
		 */
	}


	/*
	 * LISTAS
	 */

	public  List<WorkflowBeanExtended> listaProcedimientos(String parametro) {
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		liPro = new ArrayList<WorkflowBeanExtended>();
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM PENDING_WORKFLOW_ WHERE FAMILIA_ID = ? ORDER BY descproc ASC";
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, parametro);
			rs = ps.executeQuery();
			Result res = ResultSupport.toResult(rs);
			int numRows = res.getRowCount();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
				liPro = null;
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				rs.beforeFirst(); // vuelvo al primer registro
				if (numRows < 100) { // Sí los resultados son menos de 100 ...
					while (rs.next()) {
						liPro.add(new WorkflowBeanExtended(rs.getString("IDPROC"), rs.getString("IDPROCPPAL"), rs.getString("DESCPROC"), rs.getInt("PLAZO"), rs
								.getString("EFECT_SIL_ADM"), rs.getInt("ACTIVO"), rs.getString("ART_RESOLUCION"), rs.getString("FIRMANTE_RESOLUCION"), rs
								.getString("FAMILIA_ID"), rs.getInt("MODIFICAR_INTERESADO"), rs.getString("SECCION_TRAMITACION"), rs
								.getString("INTERESADO_POR_DEFECTO")));
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta devuelve demasiados resultados, especifique un criterio más concreto.", ""));
					liPro = null;
				}
			}
			rs.close(); // Cerrar la conexión con la BBDD
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return liPro;
	}
	
	public void listadoDeProcedimientos() {
		List<String> lista = new ArrayList<>();
		List<String> lista2 = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT idproc FROM PENDING_WORKFLOW_ order by idproc asc";
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.beforeFirst(); // vuelvo al primer registro
			while (rs.next()) {
				lista.add(rs.getString("IDPROC"));
				
			}
			rs.close(); 
			for (String string : lista) {
				con = DataBaseUtils.getConnection("OC3F");
				sql = "select * from ADMINISTRATIVE_FILE_ where PENDING_WORKFLOW_ID_ = ? and rownum <=1 order by START_DATE_ asc";
				ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, string);
				rs = ps.executeQuery();
				rs.beforeFirst();
				if (rs.next()) {
				
				//System.out.println( rs.getString("ID_")+ rs.getString("WF_INSTANCE_")+ rs.getString("WF_ID_")+rs.getString("WF_VERSION_")+ rs.getInt("STATUS_")+ rs.getTimestamp("START_DATE_")+ rs.getTimestamp("END_DATE_")+ rs.getString("ASUNTO_")+rs.getString("INTERESADO_")+ rs.getString("PENDING_WORKFLOW_ID_")+ rs.getString("REPOSITORY_URI_")+ rs.getString("RECORD_NUMBER_")+
				//		rs.getInt("MANDATORY_NOTIFICATION_TYPE_")+ rs.getString("NOTIFICATION_TYPE_")+ rs.getTimestamp("RESOLUTION_NOTIF_DATE_")+ rs.getTimestamp("RESOLUTION_EFFECT_NOTIF_DATE_"));
				
				//	System.out.println("ID= "+ rs.getString("PENDING_WORKFLOW_ID_")+" FECHA DE INICIO "+ rs.getTimestamp("START_DATE_"));	
					lista2.add(rs.getString("PENDING_WORKFLOW_ID_")+"  "+ rs.getTimestamp("START_DATE_")+";");
				}
				rs.close();
				}
			 // Cerrar la conexión con la BBDD
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\DESA_JDK7_JSF2\\output.txt"));
				for (String string : lista2) {
					writer.write(string);
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		
	}


	/*
	 * OPERACIONES
	 */


	public  void saveWorkflowData(WorkflowBeanExtended procedimientoBean, String tempProcedimientoID, String action) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			if (action.equals("new")) {
				sql = "SELECT IDPROC FROM PENDING_WORKFLOW_ WHERE IDPROC = ? ORDER BY IDPROC ASC";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, procedimientoBean.getProcedimiento()); //'" + ProcedimientoBean.Procedimiento_TMP + "'
				rs = ps.executeQuery();
				Result res = ResultSupport.toResult(rs);
				int numRows = res.getRowCount();
				if (numRows == 1) { // Si NO se encuentran resultados //
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un procedimiento de este tipo.", "No se puedes Crear procedimientos duplicados."));
				} else {
					sql = "INSERT INTO PENDING_WORKFLOW_ (IDPROC,IDPROCPPAL,DESCPROC,PLAZO,EFECT_SIL_ADM,ACTIVO,ART_RESOLUCION,FIRMANTE_RESOLUCION,FAMILIA_ID,MODIFICAR_INTERESADO,SECCION_TRAMITACION,INTERESADO_POR_DEFECTO) VALUES"
							+ " (?,?,?,?,?,?,?,?,?,?,?,?)";
					ps = con.prepareStatement(sql);
					ps.setString(1, procedimientoBean.getProcedimiento());
					ps.setString(2, procedimientoBean.getIdProcppal());
					ps.setString(3, procedimientoBean.getDescripcion());
					ps.setInt(4, procedimientoBean.getPlazo());
					ps.setString(5, procedimientoBean.getEfect_sil_adm());
					ps.setInt(6, procedimientoBean.getActivo());
					ps.setString(7, procedimientoBean.getArt_resolucion());
					ps.setString(8, procedimientoBean.getFirmante_resolucion());
					ps.setString(9, procedimientoBean.getFamilia_id());
					ps.setInt(10, procedimientoBean.getModificar_interesado());
					ps.setString(11, procedimientoBean.getSeccion_tramitacion());
					ps.setString(12, procedimientoBean.getInteresado_x_defecto());
					ps.executeQuery();
					sql = "insert into ADMINISTRATIVE_FILE_ID_ (CODE_,SEQUENCE_,FORMAT_,RESTARTABLE_,INITIAL_SEQUENCE_) VALUES (?,?,?,?,?)";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1,procedimientoBean.getProcedimiento());
					ps.setInt(2, 1);
					ps.setString(3,procedimientoBean.getProcedimiento()+"-{yyyy}-{mm}-{dd}-{99999}");
					ps.setInt(4, 1);
					ps.setInt(5, 1);
					ps.executeQuery();
				}
			}
			if (action.equals("save")) {
				sql = "UPDATE PENDING_WORKFLOW_ SET IDPROC = ?,IDPROCPPAL = ?,DESCPROC = ?,PLAZO = ?,EFECT_SIL_ADM = ?,ACTIVO = ?,ART_RESOLUCION = ?,FIRMANTE_RESOLUCION = ?,FAMILIA_ID = ?,MODIFICAR_INTERESADO = ?,SECCION_TRAMITACION = ?,INTERESADO_POR_DEFECTO = ? WHERE IDPROC = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, procedimientoBean.getProcedimiento());
				ps.setString(2, procedimientoBean.getIdProcppal());
				ps.setString(3, procedimientoBean.getDescripcion());
				ps.setInt(4, procedimientoBean.getPlazo());
				ps.setString(5, procedimientoBean.getEfect_sil_adm());
				ps.setInt(6, procedimientoBean.getActivo());
				ps.setString(7, procedimientoBean.getArt_resolucion());
				ps.setString(8, procedimientoBean.getFirmante_resolucion());
				ps.setString(9, procedimientoBean.getFamilia_id());
				ps.setInt(10, procedimientoBean.getModificar_interesado());
				ps.setString(11, procedimientoBean.getSeccion_tramitacion());
				ps.setString(12, procedimientoBean.getInteresado_x_defecto());
				ps.setString(13, tempProcedimientoID);
				ps.executeQuery();
			}
			if (action.equals("delete")) {
				sql = "DELETE FROM PENDING_WORKFLOW_ WHERE IDPROC=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, procedimientoBean.getProcedimiento());
				ps.executeQuery();
				sql = "DELETE FROM ADMINISTRATIVE_FILE_ID_ WHERE CODE_=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, procedimientoBean.getProcedimiento());
				ps.executeQuery();
			}
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
	}



	public  void setSingleCircuitoData(String circuitoID, String action,String procedimiento, String selectedLiA, String selectedLiD, String selectedLiFF) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			if (action.equals("new")) {
				sql = "SELECT * FROM CAT_PROCEDIMIENTOS_CIRCUITOS WHERE PROC_ID = ? AND ACTIVIDAD_ID = ? AND DEPARTAMENTO_ID = ? AND CIRCUITO_ID = ? ORDER BY ID ASC";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, procedimiento);
				ps.setString(2, selectedLiA);
				ps.setString(3, selectedLiD);
				ps.setString(4, selectedLiFF);
				rs = ps.executeQuery();
				Result res = null;
				res = ResultSupport.toResult(rs);
				int numRows = res.getRowCount();
				if (numRows > 0) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ya existe un circuito de firma con estas características asocado a este procedimiento.", ""));
				} else {
					if (selectedLiA.equals("999999999")) { // Valor que corresponde a null en la BBDD.
						selectedLiA = null;
					}
					if (selectedLiD.equals("999999999")) { // Valor que corresponde a null en la BBDD.
						selectedLiD = null;
					}
					sql = "INSERT INTO CAT_PROCEDIMIENTOS_CIRCUITOS VALUES (SEQ_CAT_PROCEDIMIENTOS_CIRCUIT.NEXTVAL,?,?,?,?)";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, procedimiento);
					ps.setString(2, selectedLiA);
					ps.setString(3, selectedLiD);
					ps.setString(4, selectedLiFF);
					rs = ps.executeQuery();
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos se han guardado correctamente.", ""));
				}
			}
			if (action.equals("delete")) {
				sql = "DELETE FROM CAT_PROCEDIMIENTOS_CIRCUITOS WHERE ID=?";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, circuitoID);
				ps.executeQuery();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos se han guardado correctamente.", ""));
			}
			if (null!=rs) {
				rs.close();
			}
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}

	}


	public List<WorkflowBeanExtended> getLiPro() {
		return liPro;
	}


	public void setLiPro(List<WorkflowBeanExtended> liPro) {
		this.liPro = liPro;
	}


//	public WorkflowBean getProcedimiento() {
//		return procedimiento;
//	}
//
//
//	public void setProcedimiento(WorkflowBeanExtended procedimiento) {
//		this.procedimiento = new WorkflowBeanExtended();
//		this.procedimiento = procedimiento;
//	}


//	public ProcedimientoBean getProcedimientoDatos(String iD) {
//		ProcedimientoBean procedimiento = new ProcedimientoBean();
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		String sql = "";
//		try {
//			con = DataBaseUtils.getConnection("OC3F");
//			sql = "SELECT * FROM pending_workflow_ WHERE IDPROC = ? ORDER BY descproc ASC";
//			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			ps.setString(1, iD);
//			rs = ps.executeQuery();
//			Result res = null;
//			res = ResultSupport.toResult(rs);
//			int numRows = res.getRowCount();
//			if (numRows <= 0) { // Si NO se encuentran resultados //
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
//				liPro = null;
//			} else {
//				rs.beforeFirst();
//				while (rs.next()) {
//					procedimiento = new ProcedimientoBean(rs.getString("IDPROC"), rs.getString("IDPROCPPAL"), rs.getString("DESCPROC"), rs.getInt("PLAZO"),
//							rs.getString("EFECT_SIL_ADM"), rs.getInt("ACTIVO"), rs.getString("ART_RESOLUCION"), rs.getString("FIRMANTE_RESOLUCION"),
//							rs.getString("FAMILIA_ID"), rs.getInt("MODIFICAR_INTERESADO"), rs.getString("SECCION_TRAMITACION"),
//							rs.getString("INTERESADO_POR_DEFECTO"));
//				}
//			}
//			rs.close();
//			ps.close();
//		} catch (SQLException e) {
//			logger.error(e);
//		}finally {
//			DataBaseUtils.close(con);
//		}
//		return procedimiento;
//	}

}
