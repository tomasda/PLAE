/**
 * 
 */
package com.opencanarias.consola.portafirmas.CatalogoFirmantesPersonas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.consola.ldap.LDAPManager;
import com.opencanarias.consola.ldap.LDAPPersonaBean;
import com.opencanarias.consola.portafirmas.CatalogoFirmantes.FirmanteBean;
import com.opencanarias.consola.utilidades.DataBaseUtils;

/**
 * @author Tomás Delgado
 *
 */
public class CatalogoFirmantePersonaDAO {
	private static Logger logger = Logger.getLogger(CatalogoFirmantePersonaDAO.class);
	
		//***********************************************
		//******* Listado de Usuarios / Firmantes *******
		//***********************************************
		
		public List<FirmanteBean> getPersonas(List<FirmanteBean> listOfFirmantes) {
			LDAPManager ldap = new LDAPManager();
			List<FirmantePorPersonaBean> listFPP = null;
			FirmantePorPersonaBean fpp=null;
			LDAPPersonaBean p = null;
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			try {
				con = DataBaseUtils.getConnection("OC3F");
				sql = "SELECT * FROM pf_firmante_por_persona WHERE ID_PERSONA = ?";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				for (FirmanteBean firmanteBean : listOfFirmantes) {
					listFPP = new ArrayList<FirmantePorPersonaBean>();
					ps.setInt(1, firmanteBean.getIdFirmante());
					rs = ps.executeQuery();
					rs.beforeFirst();
					while (rs.next()) {
						fpp = new FirmantePorPersonaBean();
						p = new LDAPPersonaBean();
						p.setCarLicense(rs.getString("DNI_SOLICITANTE"));
						/*
						 * Llamada al LDAP para obtener los datos de la Persona
						 */
						try {
							p = ldap.findLDAPUser(p.getCarLicense());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						fpp.setPersona(p);
						listFPP.add(fpp);
					}
					if (!listFPP.isEmpty()) {
//						Collections.emptyList();
//						Collections.sort(listFPP, new Comparator<FirmantePorPersonaBean>() {
//							@Override
//							public int compare (FirmantePorPersonaBean a, FirmantePorPersonaBean b) {
//								return a.getPersona().getName().toUpperCase().compareTo(b.getPersona().getName().toUpperCase());
//							}
//						});
//						firmanteBean.setListOfPersons(listFPP);
					}
					firmanteBean.setListOfPersons(listFPP);
				}
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
			return listOfFirmantes;
		}


		public List<FirmanteBean> getTodosLosFirmantes(List<FirmanteBean> listOfFirmantes) {
			FirmanteBean todos = new FirmanteBean();
			todos.setNombre("TODOS LOS FIRMANTES");
			LDAPManager ldap = new LDAPManager();
			List<FirmantePorPersonaBean> listFPP = null;
			FirmantePorPersonaBean fpp=null;
			LDAPPersonaBean p = null;
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			try {
				con = DataBaseUtils.getConnection("OC3F");
				sql = "SELECT * FROM PF_FIRMANTE_POR_PERSONA WHERE ID_PERSONA IS NULL";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					listFPP = new ArrayList<FirmantePorPersonaBean>();
					rs = ps.executeQuery();
					rs.beforeFirst();
					while (rs.next()) {
						fpp = new FirmantePorPersonaBean();
						p = new LDAPPersonaBean();
						p.setCarLicense(rs.getString("DNI_SOLICITANTE"));
						try {
							p = ldap.findLDAPUser(p.getCarLicense());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						fpp.setPersona(p);
						listFPP.add(fpp);
					}
					if (!listFPP.isEmpty()) {
						todos.setListOfPersons(listFPP);
					}
				listOfFirmantes.add(todos);
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
			return listOfFirmantes;
		}


		public boolean addFirmantePersonaRelation(String idFirmante, String carLicense) {
			boolean result = false;
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "";
			try {
				con = DataBaseUtils.getConnection("OC3F");
				sql = "INSERT INTO PF_FIRMANTE_POR_PERSONA VALUES (SEQ_PF_FIRMANTE_POR_PERSONA.NEXTVAL,?,?)";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, idFirmante);
				ps.setString(2, carLicense);
				ps.executeQuery();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
			return result;
		}
		
		public boolean deleteFirmantePersonaRelation(String idFirmante, String carLicense) {
			boolean result = false;
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "";
			try {
				con = DataBaseUtils.getConnection("OC3F");
				/**
				 * Cómo en la tabla se gestionan los permisos para todos los firmantes dejando el ID_PERSONA a null
				 * y el idFirmantes viene a 0 cuando se cumple este caso, se aplica esta condición.
				 */
				if (!idFirmante.equals("0")) {
					sql = "DELETE FROM PF_FIRMANTE_POR_PERSONA WHERE ID_PERSONA = ? AND DNI_SOLICITANTE = ?";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setInt(1, Integer.valueOf(idFirmante));
					ps.setString(2, carLicense);
				}else {
					sql = "DELETE FROM PF_FIRMANTE_POR_PERSONA WHERE ID_PERSONA is null AND DNI_SOLICITANTE = ?";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, carLicense);
				}
				ps.executeQuery();
				result = true;
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
			return result;
		}
		
		
		public boolean isFirmantePersonaInTable(String carLicense, int mode) {
			/**
			 * MODE =  0 
			 * 	Todos los usuarios que pueden enviar a todos los firmantes.
			 * MODE = 1
			 * 	Usuario que está en la tabla como envío a firmante por separado.
			 */
			boolean result = false;
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			try {
				con = DataBaseUtils.getConnection("OC3F");
				if(mode==0) {
					sql = "SELECT * FROM PF_FIRMANTE_POR_PERSONA WHERE ID_PERSONA IS NULL AND DNI_SOLICITANTE = ?";
				}else {
					sql = "SELECT * FROM PF_FIRMANTE_POR_PERSONA WHERE ID_PERSONA IS NOT NULL AND DNI_SOLICITANTE = ?";
				}
				
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, carLicense);
				rs = ps.executeQuery();
				rs.beforeFirst();
				while (rs.next()) {
					result = true;
				}
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
			return result;
		}
		
		public List<FirmantePorPersonaBean> getFirmantesPersonaListOfPersonas(FirmanteBean firmante) {
			String param = null;
			
			LDAPManager ldap = new LDAPManager();
			List<FirmantePorPersonaBean> listFPP = null;
			FirmantePorPersonaBean fpp=null;
			LDAPPersonaBean p = null;
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			try {
				con = DataBaseUtils.getConnection("OC3F");
				/*
				 * Sí el firmante ID = 0 significa que son todos los firmantes 
				 * y que el id_persona = null.
				 */
				if (firmante.getIdFirmante()==0) {
					sql = "SELECT * FROM PF_FIRMANTE_POR_PERSONA WHERE ID_PERSONA IS NULL";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				}else {
					param = String.valueOf(firmante.getIdFirmante());
					sql = "SELECT * FROM PF_FIRMANTE_POR_PERSONA WHERE ID_PERSONA = ?";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1,param);
				}
					listFPP = new ArrayList<FirmantePorPersonaBean>();
					rs = ps.executeQuery();
					rs.beforeFirst();
					while (rs.next()) {
						fpp = new FirmantePorPersonaBean();
						p = new LDAPPersonaBean();
						p.setCarLicense(rs.getString("DNI_SOLICITANTE"));
						try {
							p = ldap.findLDAPUser(p.getCarLicense());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						fpp.setPersona(p);
						listFPP.add(fpp);
					}
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
			return listFPP;
		}
		
}
