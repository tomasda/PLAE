package com.opencanarias.consola.tabla.workflow_permision;

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

import com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos.WorkflowBeanExtended;
import com.opencanarias.consola.utilidades.DataBaseUtils;

public class WorkflowPermissionDAO implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WorkflowPermissionDAO.class);

	public List<WorkflowBeanExtended> addWorkflowPermissionToWorkflow(List<WorkflowBeanExtended> listaProcedimientos) {
		if(null!=listaProcedimientos) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				/**
				 * Después de perder 2 horas buscando una solución para pasar un array como clausula 
				 * he tomado el camino del medio y hecho esta chapuza que funciona.
				 */
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("select * from WORKFLOW_PERMISSION where PROC_ID in (");
				for (int index = 1;index<=listaProcedimientos.size();index++ ) {
					sql.append("?");
					if(index!=listaProcedimientos.size()) {
						sql.append(",");
					}
				}
				sql.append(")");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				for (int index = 1;index<=listaProcedimientos.size();index++ ) {
					ps.setString(index, listaProcedimientos.get(index-1).getProcedimiento());
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					for (WorkflowBeanExtended ProcedimientoBean : listaProcedimientos) {
						if(rs.getString("PROC_ID").equals(ProcedimientoBean.getProcedimiento())) {
							int index2 = listaProcedimientos.indexOf(ProcedimientoBean);
							if(null==ProcedimientoBean.getListOfWorkflowPermission() ) {
								WorkflowPermissionBean wfpb = new WorkflowPermissionBean(rs.getInt("ID"),rs.getString("PROC_ID"),rs.getInt("PERMISSION"),rs.getString("ROLE_ID"),rs.getString("STARTER_ROLE_ID"));
								List<WorkflowPermissionBean> listWfpb = new ArrayList<WorkflowPermissionBean>();
								listWfpb.add(wfpb);
								ProcedimientoBean.setListOfWorkflowPermission(listWfpb);
							}else {
								List<WorkflowPermissionBean> listWfpb = ProcedimientoBean.getListOfWorkflowPermission();
								WorkflowPermissionBean wfpb = new WorkflowPermissionBean(rs.getInt("ID"),rs.getString("PROC_ID"),rs.getInt("PERMISSION"),rs.getString("ROLE_ID"),rs.getString("STARTER_ROLE_ID"));
								listWfpb.add(wfpb);
								ProcedimientoBean.setListOfWorkflowPermission(listWfpb);
							}
							listaProcedimientos.set(index2,ProcedimientoBean);
						}
					}
				}
				rs.close(); // Cerrar la conexión con la BBDD
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		}
		return listaProcedimientos;
	}

	public void saveWorkflowPermission(WorkflowBeanExtended procedimientoBean)   {
		if(null!=procedimientoBean.getListOfWorkflowPermission()) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<WorkflowPermissionBean> databaseListOfWorkflowPermission = new ArrayList<WorkflowPermissionBean>();
			List<WorkflowPermissionBean> tempList = new ArrayList<WorkflowPermissionBean>();
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("select * from WORKFLOW_PERMISSION where PROC_ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, procedimientoBean.getProcedimiento());
				rs = ps.executeQuery();
				// Cargamos los datos reales de base de datos.
				while (rs.next()) {
					WorkflowPermissionBean wfpb = new WorkflowPermissionBean(rs.getInt("ID"),rs.getString("PROC_ID"),rs.getInt("PERMISSION"),rs.getString("ROLE_ID"),rs.getString("STARTER_ROLE_ID"));
					databaseListOfWorkflowPermission.add(wfpb);
					tempList.add(wfpb);
				}
				rs.close(); // Cerrar la conexión con la BBDD
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
			/**
			 *  Comparamos la lista de permisos obtendia de base de datos con la que hemos de guardar.
			 */
			//Si no existe ningún registro en base de datos no tiene conque hacer la comparación
			if(!databaseListOfWorkflowPermission.isEmpty()) {
				/*
				 *  Si los datos del formulario están vacíos.
				 */
				if (procedimientoBean.getListOfWorkflowPermission().isEmpty()) {
					// delete.
					//delete tmpWorkflowPermissionBean
					for (WorkflowPermissionBean tmpDatabaseWorkflowPermissionBean : databaseListOfWorkflowPermission) {
						try {
							deleteWorkflowPermision(tmpDatabaseWorkflowPermissionBean);
						} catch (SQLException e) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al persistir los datos. ",e.toString()));
							logger.error(e);
						}
						logger.info(" Delete " + tmpDatabaseWorkflowPermissionBean.toString());
					}
				}else {
					/*
					 * Recorremos los resultados de la base de datos.
					 */
					for (WorkflowPermissionBean databaseWorkflowPermissionBean : databaseListOfWorkflowPermission) {
						boolean delete = true;
						boolean exists = false;
						boolean identic = true;
						for (WorkflowPermissionBean workflowPermissionBean : procedimientoBean.getListOfWorkflowPermission()) {
							if (databaseWorkflowPermissionBean.getROLE_ID_().equals(workflowPermissionBean.getROLE_ID_())) {
								if (databaseWorkflowPermissionBean.toString().equals(workflowPermissionBean.toString())) {
									identic = true;
								}
								exists= true;
								delete=false;
							}
						}
						if(exists) {
							if(identic) {
								//NOTHING
								logger.info(" NOTHING " + databaseWorkflowPermissionBean.toString());
							}else {
								//update
								logger.info(" Update " + databaseWorkflowPermissionBean.toString());
								try {
									updateWorkflowPermision(databaseWorkflowPermissionBean);
								} catch (SQLException e) {
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al actualizar el permiso. ",e.toString()));
									logger.error(e);
								}
							}
						}
						if (delete) {
							//delete tmpWorkflowPermissionBean
							tempList.remove(databaseWorkflowPermissionBean);
							try {
								deleteWorkflowPermision(databaseWorkflowPermissionBean);
							} catch (SQLException e) {
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al eliminar el registro. ",e.toString()));
								logger.error(e);
							}
							logger.info(" Delete " + databaseWorkflowPermissionBean.toString());
						} 
					}

					for (WorkflowPermissionBean workflowPermissionBean : procedimientoBean.getListOfWorkflowPermission()) {
						boolean insert = true;
						for (WorkflowPermissionBean databaseWorkflowPermissionBean : tempList) {
							if (workflowPermissionBean.getROLE_ID_().equals(databaseWorkflowPermissionBean.getROLE_ID_())) {
								insert=false;
							}
						}
						if(insert) {
							try {
								insertWorkflowPermision(workflowPermissionBean);
								logger.info(" INSERT " + workflowPermissionBean.toString());
							} catch (SQLException e) {
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al insertar el permiso. ",e.toString()));
								logger.error(e);
							}
						}
					}
				}
			}else {
				/*
				 * Si la Base de datos no contiene información añadimos los datos provenientes del formulario.
				 */
				for (WorkflowPermissionBean workflowPermissionBean : procedimientoBean.getListOfWorkflowPermission()) {
					//insert workflowPermissionBean
					try {
						insertWorkflowPermision(workflowPermissionBean);
						logger.info(" Insert " + workflowPermissionBean.toString());
					} catch (SQLException e) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al insertar el permiso. ",e.toString()));
						logger.error(e);
					}

				}
			}
		}
	}
	
	public void insertWorkflowPermision(WorkflowPermissionBean workflowPermission) throws SQLException {
		if(null!=workflowPermission) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("insert into WORKFLOW_PERMISSION values(SEQ_WORKFLOW_PERMISSION.NEXTVAL,?,?,?,?)");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, workflowPermission.getPROC_ID_());
				ps.setInt(2, workflowPermission.getPERMISSION_());
				ps.setString(3, workflowPermission.getROLE_ID_());
				if(null==workflowPermission.getSTARTER_ROLE_ID_()) {
					ps.setNull(4, java.sql.Types.VARCHAR);
				}else {
					ps.setString(4, workflowPermission.getSTARTER_ROLE_ID_());
				}
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}
	}
	
	public void updateWorkflowPermision(WorkflowPermissionBean workflowPermission) throws SQLException {
		if(null!=workflowPermission) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("update WORKFLOW_PERMISSION set ROLE_ID = ? , PERMISSION = ? where PROC_ID = ? and ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, workflowPermission.getROLE_ID_());
				ps.setInt(2, workflowPermission.getPERMISSION_());
				ps.setString(3, workflowPermission.getPROC_ID_());
				ps.setInt(4, workflowPermission.getID_());
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}
	}
	
	public void deleteWorkflowPermision(WorkflowPermissionBean workflowPermission) throws SQLException{
		if(null!=workflowPermission) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("delete from WORKFLOW_PERMISSION where PROC_ID = ? and ROLE_ID = ? and PERMISSION = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, workflowPermission.getPROC_ID_());
				ps.setString(2, workflowPermission.getROLE_ID_());
				ps.setInt(3, workflowPermission.getPERMISSION_());
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}
	}
}
