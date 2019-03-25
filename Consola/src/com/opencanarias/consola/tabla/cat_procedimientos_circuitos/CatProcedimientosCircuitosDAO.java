package com.opencanarias.consola.tabla.cat_procedimientos_circuitos;

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

public class CatProcedimientosCircuitosDAO implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CatProcedimientosCircuitosDAO.class);


	public List<CatProcedimientoCircuitoBean> getListaCircuitosProcedimiento(String idproc) {
		List<CatProcedimientoCircuitoBean> listaCircuitosProcedimiento = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			String sql = "SELECT * FROM CAT_PROCEDIMIENTOS_CIRCUITOS WHERE proc_id = ? ORDER BY actividad_id ASC";
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, idproc);
			rs = ps.executeQuery();
			if (rs.next()) {
				listaCircuitosProcedimiento = new ArrayList<>();
				rs.beforeFirst();
				while (rs.next()) {
					CatProcedimientoCircuitoBean pb = new CatProcedimientoCircuitoBean(rs.getString("ID"), rs.getString("PROC_ID"), rs.getString("ACTIVIDAD_ID"), rs.getString("DEPARTAMENTO_ID"), rs.getInt("CIRCUITO_ID"));
					listaCircuitosProcedimiento.add(pb);
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return listaCircuitosProcedimiento;
	}

	public List<WorkflowBeanExtended> getProcedimientosCircuitos(List<WorkflowBeanExtended> listaProcedimientos) {
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
				sql.append("select * from CAT_PROCEDIMIENTOS_CIRCUITOS where PROC_ID in (");
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
							if(null==ProcedimientoBean.getListOfCatProcedimientoCircuitos() ) {
								CatProcedimientoCircuitoBean catPCB = new CatProcedimientoCircuitoBean(rs.getString("ID"), rs.getString("PROC_ID"), rs.getString("Actividad_ID"), rs.getString("Departamento_ID"), rs.getInt("Circuito_ID"));
								List<CatProcedimientoCircuitoBean> listCatPCB = new ArrayList<CatProcedimientoCircuitoBean>();
								listCatPCB.add(catPCB);
								ProcedimientoBean.setListOfCatProcedimientoCircuitos(listCatPCB);
							}else {
								List<CatProcedimientoCircuitoBean> listCatPCB = ProcedimientoBean.getListOfCatProcedimientoCircuitos();
								CatProcedimientoCircuitoBean catPCB = new CatProcedimientoCircuitoBean(rs.getString("ID"), rs.getString("PROC_ID"), rs.getString("Actividad_ID"), rs.getString("Departamento_ID"), rs.getInt("Circuito_ID"));
								listCatPCB.add(catPCB);
								ProcedimientoBean.setListOfCatProcedimientoCircuitos(listCatPCB);
							}
							listaProcedimientos.set(index2,ProcedimientoBean);
						}
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
		return listaProcedimientos;
	}

	public void saveWorkflowData(WorkflowBeanExtended procedimientoBean, String action) {
		if(null!=procedimientoBean.getListOfCatProcedimientoCircuitos()) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<CatProcedimientoCircuitoBean> databaseListOfCatProcedimientosCircuitos = new ArrayList<CatProcedimientoCircuitoBean>();
			List<CatProcedimientoCircuitoBean> tempList = new ArrayList<CatProcedimientoCircuitoBean>();
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("select * from CAT_PROCEDIMIENTOS_CIRCUITOS where PROC_ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, procedimientoBean.getProcedimiento());
				rs = ps.executeQuery();
				// Cargamos los datos reales de base de datos.
				while (rs.next()) {
					CatProcedimientoCircuitoBean wfpb = new CatProcedimientoCircuitoBean(rs.getString("ID"),rs.getString("PROC_ID"),rs.getString("ACTIVIDAD_ID"),rs.getString("DEPARTAMENTO_ID"),rs.getInt("CIRCUITO_ID"));
					databaseListOfCatProcedimientosCircuitos.add(wfpb);
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
			if(!databaseListOfCatProcedimientosCircuitos.isEmpty()) {
				/*
				 *  Si los datos del formulario están vacíos.
				 */
				if (procedimientoBean.getListOfCatProcedimientoCircuitos().isEmpty()) {
					// delete.
					//delete tmpCatProcedimientoCircuito
					for (CatProcedimientoCircuitoBean tmpDatabaseCatProcedimientoCircuitoBean : databaseListOfCatProcedimientosCircuitos) {
						try {
							deleteCatProcedimientoCircuito(tmpDatabaseCatProcedimientoCircuitoBean);
						} catch (SQLException e) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al persistir los datos. ",e.toString()));
							logger.error(e);
						}
						logger.info(" Delete " + tmpDatabaseCatProcedimientoCircuitoBean.toString());
					}
				}else {
					/*
					 * Recorremos los resultados de la base de datos.
					 */
					for (CatProcedimientoCircuitoBean databaseCatProcedimientoCircuitoBean : databaseListOfCatProcedimientosCircuitos) {
						boolean delete = true;
						boolean exists = false;
						boolean identic = true;
						for (CatProcedimientoCircuitoBean catProcedimientoCircuitoBean : procedimientoBean.getListOfCatProcedimientoCircuitos()) {
							if (databaseCatProcedimientoCircuitoBean.getProcedimiento_ID().equals(catProcedimientoCircuitoBean.getProcedimiento_ID())
									&& databaseCatProcedimientoCircuitoBean.getCircuitoID()==catProcedimientoCircuitoBean.getCircuitoID()) {
								if (databaseCatProcedimientoCircuitoBean.toString().equals(catProcedimientoCircuitoBean.toString())) {
									identic = true;
								}
								exists= true;
								delete=false;
							}
						}
						if(exists) {
							if(identic) {
								//NOTHING
								logger.info(" NOTHING " + databaseCatProcedimientoCircuitoBean.toString());
							}else {
								//update
								logger.info(" Update " + databaseCatProcedimientoCircuitoBean.toString());
								try {
									updateCatProcedimientoCircuito(databaseCatProcedimientoCircuitoBean);
								} catch (SQLException e) {
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al actualizar el permiso. ",e.toString()));
									logger.error(e);
								}
							}
						}
						if (delete) {
							//delete tmpWorkflowPermissionBean
							tempList.remove(databaseCatProcedimientoCircuitoBean);
							try {
								deleteCatProcedimientoCircuito(databaseCatProcedimientoCircuitoBean);
							} catch (SQLException e) {
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al eliminar el registro. ",e.toString()));
								logger.error(e);
							}
							logger.info(" Delete " + databaseCatProcedimientoCircuitoBean.toString());
						} 
					}

					for (CatProcedimientoCircuitoBean procedimientoCircuitoBean : procedimientoBean.getListOfCatProcedimientoCircuitos()) {
						boolean insert = true;
						for (CatProcedimientoCircuitoBean databaseWorkflowPermissionBean : tempList) {
							if (procedimientoCircuitoBean.getCircuitoID()==(databaseWorkflowPermissionBean.getCircuitoID())) {
								insert=false;
							}
						}
						if(insert) {
							try {
								insertCatProcedimientoCircuito(procedimientoCircuitoBean);
								logger.info(" INSERT " + procedimientoCircuitoBean.toString());
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
				for (CatProcedimientoCircuitoBean procedimientoCircuitoBean : procedimientoBean.getListOfCatProcedimientoCircuitos()) {
					//insert workflowPermissionBean
					try {
						insertCatProcedimientoCircuito(procedimientoCircuitoBean);
						logger.info(" Insert " + procedimientoCircuitoBean.toString());
					} catch (SQLException e) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al insertar el permiso. ",e.toString()));
						logger.error(e);
					}

				}
			}
		}

	}

	public void insertCatProcedimientoCircuito(CatProcedimientoCircuitoBean procedimientoCircuitoBean) throws SQLException {
		if(null!=procedimientoCircuitoBean) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("insert into CAT_PROCEDIMIENTOS_CIRCUITOS values(SEQ_CAT_PROCEDIMIENTOS_CIRCUIT.NEXTVAL,?,?,?,?)");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, procedimientoCircuitoBean.getProcedimiento_ID());
				if(null==procedimientoCircuitoBean.getActividad_ID()) {
					ps.setNull(2, java.sql.Types.VARCHAR);
				}else {
					ps.setString(2, procedimientoCircuitoBean.getActividad_ID());
				}
				if(null==procedimientoCircuitoBean.getDepartamento_ID()) {
					ps.setNull(3, java.sql.Types.VARCHAR);
				}else {
					ps.setString(3, procedimientoCircuitoBean.getDepartamento_ID());
				}
				ps.setInt(4, procedimientoCircuitoBean.getCircuitoID());
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}

	}

	public void updateCatProcedimientoCircuito(CatProcedimientoCircuitoBean databaseCatProcedimientoCircuitoBean)throws SQLException {
		if(null!=databaseCatProcedimientoCircuitoBean) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("update CAT_PROCEDIMIENTOS_CIRCUITOS set ACTIVIDAD_ID = ? , DEPARTAMENTO_ID = ?, where ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				if (null==databaseCatProcedimientoCircuitoBean.getActividad_ID()) {
					ps.setNull(2, java.sql.Types.VARCHAR);
				}else {
					ps.setString(2, databaseCatProcedimientoCircuitoBean.getActividad_ID());
				}
				if (null==databaseCatProcedimientoCircuitoBean.getDepartamento_ID()) {
					ps.setNull(3, java.sql.Types.VARCHAR);
				}else {
					ps.setString(3, databaseCatProcedimientoCircuitoBean.getDepartamento_ID());
				}
				ps.setString(4, databaseCatProcedimientoCircuitoBean.getID());
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}

	}

	public void deleteCatProcedimientoCircuito(CatProcedimientoCircuitoBean tmpDatabaseCatProcedimientoCircuitoBean)throws SQLException {
		if(null!=tmpDatabaseCatProcedimientoCircuitoBean) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("delete from CAT_PROCEDIMIENTOS_CIRCUITOS where ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, tmpDatabaseCatProcedimientoCircuitoBean.getID());
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}
	}
}
