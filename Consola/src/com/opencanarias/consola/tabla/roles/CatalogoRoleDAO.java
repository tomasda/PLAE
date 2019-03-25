/**
 * 
 */
package com.opencanarias.consola.tabla.roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

/**
 * @author Tomás Delgado
 *
 */
public class CatalogoRoleDAO {
	private static Logger logger = Logger.getLogger(CatalogoRoleDAO.class);


	public RoleBean getRole(String name) {
		RoleBean role = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM ROLE_ WHERE ID_ = ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, name);
				rs = ps.executeQuery();
				rs.beforeFirst();
				if (rs.next()) {
					role = new RoleBean();
					role.setId_(rs.getString("ID_"));
					role.setHabilitado_(rs.getInt("HABILITADO_"));
					role.setDescription_(rs.getString("DESCRIPTION_"));
					role.setAlfGroup_(rs.getString("ALF_GROUP_"));
					role.setDecretable_registro_(rs.getInt("DECRETABLE_REGISTRO_"));
					role.setDecretable_(rs.getInt("DECRETABLE_"));
				}else {
					logger.error("No se encuentra el ROL " + name + " en la Tabla Roles.");
				}
		}catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return role;
	}
	
	public List<RoleBean> getRoles(String searchOption, String searchOption2, String searchOption3) {
//		System.out.println("SO "+searchOption+" SO "+searchOption2+" SO3 "+searchOption3);
		RoleBean role = null;
		List<RoleBean> list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ROLE_ WHERE ");
		if(!searchOption.isEmpty()) {
			sql.append("upper(ID_) like upper(?) ");
		}
		if(!searchOption2.isEmpty()) {
			if(!searchOption.isEmpty()) {
				sql.append("AND ");
			}
			sql.append("upper(DESCRIPTION_) like upper(?) ");
		}
		if (!searchOption3.isEmpty()) {
			if (!searchOption.isEmpty() || !searchOption2.isEmpty()) {
				sql.append("AND ");
			}
			sql.append("upper(ALF_GROUP_) like upper(?) ");
		}
		try {
			con = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int index=1;
			if(!searchOption.isEmpty()) {
				ps.setString(index, "%"+searchOption+"%");
				index++;
			}
			if(!searchOption2.isEmpty()) {
				ps.setString(index, "%"+searchOption2+"%");
				index++;
			}
			if(!searchOption3.isEmpty()) {
				ps.setString(index, "%"+searchOption3+"%");
				index++;
			}
				rs = ps.executeQuery();
				rs.beforeFirst();
				while (rs.next()) {
					role = new RoleBean();
					role.setId_(rs.getString("ID_"));
					role.setHabilitado_(rs.getInt("HABILITADO_"));
					role.setDescription_(rs.getString("DESCRIPTION_"));
					role.setAlfGroup_(rs.getString("ALF_GROUP_"));
					role.setDecretable_registro_(rs.getInt("DECRETABLE_REGISTRO_"));
					role.setDecretable_(rs.getInt("DECRETABLE_"));
					if(null==list) {
						list=new ArrayList<>();
					}
					list.add(role);
				}
		}catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return list;
	}
	
//	public RoleBean getRole(int selectedRole) {
//		RoleBean role = null;
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		String sql = "";
//		try {
//			con = DataBaseUtils.getConnection("OC3F");
//			sql = "SELECT * FROM ROLE_ WHERE ID_ = ?";
//			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//				ps.setInt(1, selectedRole);
//				rs = ps.executeQuery();
//				rs.beforeFirst();
//				if (rs.next()) {
//					role = new RoleBean();
//					role = new RoleBean();
//					role.setId_(rs.getString("ID_"));
//					role.setHabilitado_(rs.getInt("HABILITADO_"));
//					role.setDescription_(rs.getString("DESCRIPTION_"));
//					role.setAlfGroup_(rs.getString("ALF_GROUP_"));
//					role.setDecretable_registro_(rs.getInt("DECRETABLE_REGISTRO_"));
//					role.setDecretable_(rs.getInt("DECRETABLE_"));
//				}else {
//					logger.error("No se encuentra el ROL con ID" + selectedRole + " en la Tabla Roles.");
//				}
//		}catch (SQLException e) {
//			logger.error(e);
//		}finally {
//			DataBaseUtils.close(con);
//		}
//		return role;
//	}


	public List<RoleBean> getListOfRoles() {
		List<RoleBean> listOfRoles = null;
		RoleBean role = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "SELECT * FROM ROLE_ WHERE DECRETABLE_ = 1 ORDER BY DESCRIPTION_ ASC ";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();
				rs.beforeFirst();
				while (rs.next()) {
					if (null == listOfRoles) {
						listOfRoles = new ArrayList<>();
					}
					role = new RoleBean();
					role = new RoleBean();
					role.setId_(rs.getString("ID_"));
					role.setHabilitado_(rs.getInt("HABILITADO_"));
					role.setDescription_(rs.getString("DESCRIPTION_"));
					role.setAlfGroup_(rs.getString("ALF_GROUP_"));
					role.setDecretable_registro_(rs.getInt("DECRETABLE_REGISTRO_"));
					role.setDecretable_(rs.getInt("DECRETABLE_"));
					listOfRoles.add(role);
				}
		}catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return listOfRoles;
	}
}
