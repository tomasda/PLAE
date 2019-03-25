package com.opencanarias.consola.tabla.cat_procedimientos_roles;

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
import com.opencanarias.consola.tabla.cat_procedimientos_circuitos.CatProcedimientosCircuitosDAO;
import com.opencanarias.consola.utilidades.DataBaseUtils;

public class CatProcedimientosRolesDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CatProcedimientosCircuitosDAO.class);
//	private List<WorkflowPermissionBean> listOfWorkFlowPermision;

//	public List<WorkflowPermissionBean> getListOfWorkFlowPermision(){
//		return listOfWorkFlowPermision;
//	}

//	public List<RoleBean> getListOfWorkFlowPermisionOfWorkFlow(String workFlow){
//		List<RoleBean> listOfRoleBean = null;
//		return listOfRoleBean;
//	}

	public List<WorkflowBeanExtended> insertRolesToProcedimientos(List<WorkflowBeanExtended> listaProcedimientos) {
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
				sql.append("select * from CAT_PROCEDIMIENTOS_ROLES where FAMILIA_ID = ? OR PROCEDIMIENTO_ID in (");
				for (int index = 1;index<=listaProcedimientos.size();index++ ) {
					sql.append("?");
					if(index!=listaProcedimientos.size()) {
						sql.append(",");
					}
				}
				sql.append(") order by PROCEDIMIENTO_ID ASC");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				/**
				 * 
				 */
				ps.setString(1, listaProcedimientos.get(0).getFamilia_id());
				for (int index = 1;index<=listaProcedimientos.size();index++ ) {
					ps.setString((index +1), listaProcedimientos.get(index-1).getProcedimiento());
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					for (WorkflowBeanExtended ProcedimientoBean : listaProcedimientos) {
						if (null!=rs.getString("PROCEDIMIENTO_ID")) {
							if(rs.getString("PROCEDIMIENTO_ID").equals(ProcedimientoBean.getProcedimiento())) {
								int index2 = listaProcedimientos.indexOf(ProcedimientoBean);
								if(null==ProcedimientoBean.getListOfCatProcedimientosRoles() ) {
									CatProcedimientosRolesBean catPRB = new CatProcedimientosRolesBean(rs.getInt("ID"), rs.getString("DEPARTAMENTO_ID"),rs.getString("FAMILIA_ID"),rs.getString("PROCEDIMIENTO_ID"));
									List<CatProcedimientosRolesBean> listCatPRB = new ArrayList<CatProcedimientosRolesBean>();
									listCatPRB.add(catPRB);
									ProcedimientoBean.setListOfCatProcedimientosRoles(listCatPRB);
								}else {
									List<CatProcedimientosRolesBean> listCatPRB = ProcedimientoBean.getListOfCatProcedimientosRoles();
									CatProcedimientosRolesBean catPRB = new CatProcedimientosRolesBean(rs.getInt("ID"), rs.getString("DEPARTAMENTO_ID"),rs.getString("FAMILIA_ID"),rs.getString("PROCEDIMIENTO_ID"));
									listCatPRB.add(catPRB);
									ProcedimientoBean.setListOfCatProcedimientosRoles(listCatPRB);
								}
								listaProcedimientos.set(index2,ProcedimientoBean);
							}
						}else {
							int index2 = listaProcedimientos.indexOf(ProcedimientoBean);
							if(null==ProcedimientoBean.getListOfCatProcedimientosRoles() ) {
								CatProcedimientosRolesBean catPRB = new CatProcedimientosRolesBean(rs.getInt("ID"), rs.getString("DEPARTAMENTO_ID"),rs.getString("FAMILIA_ID"),rs.getString("PROCEDIMIENTO_ID"));
								List<CatProcedimientosRolesBean> listCatPRB = new ArrayList<CatProcedimientosRolesBean>();
								listCatPRB.add(catPRB);
								ProcedimientoBean.setListOfCatProcedimientosRoles(listCatPRB);
							}else {
								List<CatProcedimientosRolesBean> listCatPRB = ProcedimientoBean.getListOfCatProcedimientosRoles();
								CatProcedimientosRolesBean catPRB = new CatProcedimientosRolesBean(rs.getInt("ID"), rs.getString("DEPARTAMENTO_ID"),rs.getString("FAMILIA_ID"),rs.getString("PROCEDIMIENTO_ID"));
								listCatPRB.add(catPRB);
								ProcedimientoBean.setListOfCatProcedimientosRoles(listCatPRB);
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

	public void saveWorkflowData(WorkflowBeanExtended procedimientoBean, String action) {
		if(null!=procedimientoBean.getListOfCatProcedimientosRoles()) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<CatProcedimientosRolesBean> databaseListOfCatProcedimientosRolesBean = new ArrayList<CatProcedimientosRolesBean>();
			List<CatProcedimientosRolesBean> tempList = new ArrayList<CatProcedimientosRolesBean>();
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("select * from CAT_PROCEDIMIENTOS_ROLES where FAMILIA_ID = ? OR PROCEDIMIENTO_ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, procedimientoBean.getFamilia_id());
				ps.setString(2, procedimientoBean.getProcedimiento());
				rs = ps.executeQuery();
				// Cargamos los datos reales de base de datos.
				while (rs.next()) {
					CatProcedimientosRolesBean wfpb = new CatProcedimientosRolesBean(rs.getInt("ID"),rs.getString("DEPARTAMENTO_ID"),rs.getString("FAMILIA_ID"),rs.getString("PROCEDIMIENTO_ID"));
					databaseListOfCatProcedimientosRolesBean.add(wfpb);
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
			if(!databaseListOfCatProcedimientosRolesBean.isEmpty()) {
				/*
				 *  Si los datos del formulario están vacíos.
				 */
				if (procedimientoBean.getListOfCatProcedimientosRoles().isEmpty()) {
					// delete.
					for (CatProcedimientosRolesBean tmpDatabaseCatProcedimientosBean : databaseListOfCatProcedimientosRolesBean) {
						try {
							deleteProcedimeintoRole(tmpDatabaseCatProcedimientosBean);
						} catch (SQLException e) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al eliminar el rol. ",e.toString()));
							logger.error(e);
						}
						logger.info(" Delete " + tmpDatabaseCatProcedimientosBean.toString());
					}
				}else {
					/*
					 * Recorremos los resultados de la base de datos.
					 */
					for (CatProcedimientosRolesBean databaseCatProcedimientosBean : databaseListOfCatProcedimientosRolesBean) {
						boolean delete = true;
						boolean exists = false;
						boolean identic = true;
						for (CatProcedimientosRolesBean catProcedimientosRolesBean : procedimientoBean.getListOfCatProcedimientosRoles()) {
							if (null == databaseCatProcedimientosBean.getFAMILIA_ID_()) {
								if (databaseCatProcedimientosBean.getDEPARTAMENTO_ID_().equals(catProcedimientosRolesBean.getDEPARTAMENTO_ID_())
										&& databaseCatProcedimientosBean.getPROCEDIMIENTO_ID_().equals(catProcedimientosRolesBean.getPROCEDIMIENTO_ID_())){
									if (databaseCatProcedimientosBean.toString().equals(catProcedimientosRolesBean.toString())) {
										identic = true;
									}
									exists= true;
									delete=false;
								}
							}else {
								if (databaseCatProcedimientosBean.getDEPARTAMENTO_ID_().equals(catProcedimientosRolesBean.getDEPARTAMENTO_ID_())
										&& databaseCatProcedimientosBean.getFAMILIA_ID_().equals(databaseCatProcedimientosBean.getFAMILIA_ID_())){
									if (databaseCatProcedimientosBean.toString().equals(catProcedimientosRolesBean.toString())) {
										identic = true;
									}
									exists= true;
									delete=false;
								}
							}
						}
						if(exists) {
							if(identic) {
								//NOTHING
								logger.info(" NOTHING " + databaseCatProcedimientosBean.toString());
							}else {
								//update
								logger.info(" Update " + databaseCatProcedimientosBean.toString());
								try {
									updateProcedimeintoRole(databaseCatProcedimientosBean);
								} catch (SQLException e) {
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al actualizar el permiso. ",e.toString()));
									logger.error(e);
								}
							}
						}
						if (delete) {
							//delete tmpWorkflowPermissionBean
							tempList.remove(databaseCatProcedimientosBean);
							try {
								deleteProcedimeintoRole(databaseCatProcedimientosBean);
							} catch (SQLException e) {
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al eliminar el registro. ",e.toString()));
								logger.error(e);
							}
							logger.info(" Delete " + databaseCatProcedimientosBean.toString());
						} 
					}

					for (CatProcedimientosRolesBean catProcedimientosRolesBean : procedimientoBean.getListOfCatProcedimientosRoles()) {
						boolean insert = true;
						for (CatProcedimientosRolesBean databaseCatProcedimientosRolesBean : tempList) {
							if (null == databaseCatProcedimientosRolesBean.getFAMILIA_ID_()) {
								if (catProcedimientosRolesBean.getDEPARTAMENTO_ID_().equals(databaseCatProcedimientosRolesBean.getDEPARTAMENTO_ID_())
										&& catProcedimientosRolesBean.getPROCEDIMIENTO_ID_().equals(databaseCatProcedimientosRolesBean.getPROCEDIMIENTO_ID_())) {
									insert=false;
								}
							}else {
								if (catProcedimientosRolesBean.getDEPARTAMENTO_ID_().equals(databaseCatProcedimientosRolesBean.getDEPARTAMENTO_ID_())
										&& catProcedimientosRolesBean.getFAMILIA_ID_().equals(databaseCatProcedimientosRolesBean.getFAMILIA_ID_())) {
									insert=false;
								}
							}
						}
						if(insert) {
							try {
								insertProcedimeintoRole(catProcedimientosRolesBean);
								logger.info(" INSERT " + catProcedimientosRolesBean.toString());
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
				for (CatProcedimientosRolesBean catProcedimientosRolesBean : procedimientoBean.getListOfCatProcedimientosRoles()) {
					//insert workflowPermissionBean
					try {
						insertProcedimeintoRole(catProcedimientosRolesBean);
						logger.info(" Insert " + catProcedimientosRolesBean.toString());
					} catch (SQLException e) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR al insertar el permiso. ",e.toString()));
						logger.error(e);
					}

				}
			}
		}

	}
	
	public List<CatProcedimientosRolesBean> getListOfCatProcedimientosRoles(String dEPARTAMENTO_ID_, String fAMILIA_ID_, String pROCEDIMIENTO_ID_){
		List<CatProcedimientosRolesBean> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM CAT_PROCEDIMIENTOS_ROLES WHERE ");
		if (null!=dEPARTAMENTO_ID_) {
			sql.append("DEPARTAMENTO_ID like ? ");
		}
		if (null!=fAMILIA_ID_) {
			if (null!=dEPARTAMENTO_ID_) {
				sql.append("AND FAMILIA_ID like ?");
			}else {
				sql.append("FAMILIA_ID like ?");
			}
		}
		if (null!=pROCEDIMIENTO_ID_) {
			if (null!=dEPARTAMENTO_ID_||null!=fAMILIA_ID_) {
				sql.append("AND PROCEDIMIENTO_ID like ? ");
			}else {
				sql.append("PROCEDIMIENTO_ID like ? ");
			}
		}
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int index=1;
		try {
			con = DataBaseUtils.getConnection("OC3F");		
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (null!=dEPARTAMENTO_ID_) {
				ps.setString(index, dEPARTAMENTO_ID_);
				index++;
			}
			if (null!=fAMILIA_ID_) {
				ps.setString(index, fAMILIA_ID_);
				index++;
			}
			if (null!=pROCEDIMIENTO_ID_) {
				ps.setString(index, pROCEDIMIENTO_ID_);
				index++;
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				CatProcedimientosRolesBean rolesBean = new CatProcedimientosRolesBean(rs.getInt("ID"),rs.getString("DEPARTAMENTO_ID"),rs.getString("fAMILIA_ID"), rs.getString("PROCEDIMIENTO_ID"));
				if (null==list) {
					list = new ArrayList<>();
				}
				list.add(rolesBean);
			}
			rs.close(); 
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DataBaseUtils.close(con);
		}
		return list;
	}

	public void insertProcedimeintoRole(CatProcedimientosRolesBean catProcedimientosRolesBean) throws SQLException {
		if(null!=catProcedimientosRolesBean) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("insert into CAT_PROCEDIMIENTOS_ROLES values(SEQ_CAT_PROCEDIMIENTOS_ROLES.NEXTVAL,?,?,?)");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, catProcedimientosRolesBean.getDEPARTAMENTO_ID_());
				if(null==catProcedimientosRolesBean.getFAMILIA_ID_()) {
					ps.setNull(2, java.sql.Types.VARCHAR);
				}else {
					ps.setString(2, catProcedimientosRolesBean.getFAMILIA_ID_());
				}
				if(null==catProcedimientosRolesBean.getPROCEDIMIENTO_ID_()) {
					ps.setNull(3, java.sql.Types.VARCHAR);
				}else {
					ps.setString(3, catProcedimientosRolesBean.getPROCEDIMIENTO_ID_());
				}
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}

	}

	public void updateProcedimeintoRole(CatProcedimientosRolesBean databaseCatProcedimientosBean)  throws SQLException  {
		if(null!=databaseCatProcedimientosBean) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("update CAT_PROCEDIMIENTOS_ROLES set DEPARTAMENTO_ID = ? , FAMILIA_ID = ?, PROCEDIMIENTO_ID = ? where ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, databaseCatProcedimientosBean.getDEPARTAMENTO_ID_());
				if (null==databaseCatProcedimientosBean.getFAMILIA_ID_()) {
					ps.setNull(2, java.sql.Types.VARCHAR);
				}else {
					ps.setString(2, databaseCatProcedimientosBean.getFAMILIA_ID_());
				}
				if (null==databaseCatProcedimientosBean.getPROCEDIMIENTO_ID_()) {
					ps.setNull(3, java.sql.Types.VARCHAR);
				}else {
					ps.setString(3, databaseCatProcedimientosBean.getPROCEDIMIENTO_ID_());
				}
				ps.setInt(4, databaseCatProcedimientosBean.getID_());
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}

	}

	public void deleteProcedimeintoRole(CatProcedimientosRolesBean tmpDatabaseCatProcedimientosBean)  throws SQLException {
		if(null!=tmpDatabaseCatProcedimientosBean) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = DataBaseUtils.getConnection("OC3F");
				StringBuffer sql = new StringBuffer();
				sql.append("delete from CAT_PROCEDIMIENTOS_ROLES where ID = ?");
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, tmpDatabaseCatProcedimientosBean.getID_());
				rs = ps.executeQuery();
				rs.close(); 
				ps.close();
			}finally {
				DataBaseUtils.close(con);
			}
		}
	}

}
