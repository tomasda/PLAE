package com.opencanarias.consola.portafirmas.circuitos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.jboss.logging.Logger;

import com.opencanarias.consola.ldap.LDAPManager;
import com.opencanarias.consola.ldap.LDAPPersonaBean;
import com.opencanarias.consola.portafirmas.CatalogoFirmantes.FirmanteBean;
import com.opencanarias.consola.utilidades.DataBaseUtils;

//@ManagedBean
//@SessionScoped
public class PF_FlujosDAO {
	private static Logger logger = Logger.getLogger(PF_FlujosDAO.class);
	private  List<PF_FlujosBean> listaFlujosFirma;
	private List<LDAPPersonaBean> listOfPersonasLDAP;
	
	public PF_FlujosDAO () {
		listaFlujosFirma = null;
	}

	public  List<PF_FlujosBean> getListaFlujosFirma(int id, int idTipoFlujo, int ordenActivo, String descripcion) {
		listaFlujosFirma = new ArrayList<PF_FlujosBean>();
		Connection con  = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql.append("SELECT * FROM pf_flujos WHERE ");
			/**
			 * Si alguno de los campos int viene a 0 se entiende que son todos los resultados !!!
			 */
			if(id!=0) {
				sql.append("id_flujo=? AND ");
			}
			if(idTipoFlujo!=0) {
				sql.append("id_tipo_flujo=? AND ");
			}
			if(ordenActivo!=0) {
				sql.append("orden_activo=? AND ");
			}
			sql.append("UPPER( descripcion ) LIKE ? ORDER BY descripcion ASC");
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=0;
			if(id!=0) {
				ps.setInt(++i, id);
			}
			if(idTipoFlujo!=0) {
				ps.setInt(++i, idTipoFlujo);
			}
			if(ordenActivo!=0) {
				ps.setInt(++i, ordenActivo);
			}if(null == descripcion) {
				descripcion ="";
			}
			ps.setString(++i, "%"+descripcion.toUpperCase()+"%");
			rs = ps.executeQuery();
			rs.last();
			int numRows = rs.getRow();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
				listaFlujosFirma = null;
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				@SuppressWarnings("unused")
				int index = 0;
				rs.beforeFirst(); // vuelvo al primer registro
				while (rs.next()) { // Creo un objeto de la clase "Expediente"
					PF_FlujosBean o_PF_F = new PF_FlujosBean();
					o_PF_F.setFlujo_id(rs.getInt("ID_FLUJO")); // ID_FLUJO NUMBER(16,0)
					o_PF_F.setFechaCreacion(rs.getTimestamp("FECHA_CREACION")); // FECHA_CREACION DATE
					o_PF_F.setTipoFlujo(rs.getInt("ID_TIPO_FLUJO")); // ID_TIPO_FLUJO NUMBER(16,0)
					o_PF_F.setOrdenActivo(rs.getInt("ORDEN_ACTIVO")); // ORDEN_ACTIVO NUMBER(2,0)
					o_PF_F.setDescripcion(rs.getString("DESCRIPCION")); // DESCRIPCION VARCHAR2(255 BYTE)
					listaFlujosFirma.add(o_PF_F);
				}
			}
			rs.close(); // Cerrar la conexión con la BBDD
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		if (null != listaFlujosFirma) {
			getGroupDetail();
			getAuthorizedPersons();
		}
		return listaFlujosFirma;
	}

	private void getAuthorizedPersons() {
		Connection con  = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		List<LDAPPersonaBean> listOfPerson = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql.append("select * from PF_CIRCUITO_POR_PERSONA where ID_CIRCUITO = ? order by ID_REGISTRO");
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i<listaFlujosFirma.size();i++) {
				ps.setInt(1, listaFlujosFirma.get(i).getFlujo_id());
				rs = ps.executeQuery();
				rs.last();
				int numRows = rs.getRow();
				if (numRows <= 0) { // Si NO se encuentran resultados 
					listOfPerson = null;
				} else { // Si se encuentran, carga los datos en una lista para devolverlos.
					rs.beforeFirst();
					listOfPerson = new ArrayList<LDAPPersonaBean>();
					while (rs.next()) { 
						LDAPPersonaBean personBean = new LDAPPersonaBean();
						personBean = getPersonDetail(rs.getString("DNI_SOLICITANTE"));
						listOfPerson.add(personBean);
					}
				}
				listaFlujosFirma.get(i).setListOfPersonas(listOfPerson);
			}
			rs.close(); // Cerrar la conexión con la BBDD
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
	}
	private List<LDAPPersonaBean> getAuthorizedPersonsFor(int id_circuito) {
		Connection con  = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		List<LDAPPersonaBean> listOfPerson = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql.append("select * from PF_CIRCUITO_POR_PERSONA where ID_CIRCUITO = ? order by ID_REGISTRO");
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
				ps.setInt(1, id_circuito);
				rs = ps.executeQuery();
				rs.last();
				int numRows = rs.getRow();
				if (numRows <= 0) { // Si NO se encuentran resultados 
					listOfPerson = null;
				} else { // Si se encuentran, carga los datos en una lista para devolverlos.
					rs.beforeFirst();
					listOfPerson = new ArrayList<LDAPPersonaBean>();
					while (rs.next()) { 
						LDAPPersonaBean personBean = new LDAPPersonaBean();
						personBean = getPersonDetail(rs.getString("DNI_SOLICITANTE"));
						listOfPerson.add(personBean);
					}
				}
			
			rs.close(); // Cerrar la conexión con la BBDD
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return listOfPerson;
		
	}

	private LDAPPersonaBean getPersonDetail(String string) {
		if (null==listOfPersonasLDAP) {
			LDAPManager ldap = new LDAPManager();
			try {
				listOfPersonasLDAP = ldap.findLDAPUsers("cn=*");
			} catch (ClassNotFoundException e) {
				logger.error("No se encuentra el módulo LDAP");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("Error de conexión LDAP Manager");
				e.printStackTrace();
			} catch (SQLException e) {
				logger.error("Error en la consulta a LDAP Manger");
				e.printStackTrace();
			}
		}
		LDAPPersonaBean person = null;
		boolean exist = false;
		for (LDAPPersonaBean ldapPersonaBean : listOfPersonasLDAP) {
			if (null!=ldapPersonaBean.getCarLicense() && ldapPersonaBean.getCarLicense().equals(string)) {
				person = ldapPersonaBean;
				exist = true;
				break;
			}
		}
		if (!exist) {
			person = new LDAPPersonaBean();
			person.setCn("LDAP - No existe");
			person.setCarLicense(string);
			logger.info("LDAP - No se localiza el usuario con la identificación "+string);
		}
		return person;
	}

	private void getGroupDetail() {
		Connection con  = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		List<PF_GruposBean> listOfGroups = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql.append("select * from PF_GRUPOS GR join PF_GRUPO_PERSONAS GRP on GR.ID_GRUPO = GRP.ID_GRUPO " + 
					   "join PF_PERSONAS P on GRP.ID_PERSONA = P.ID_PERSONA "+
					   "join PF_TM_TIPO_GRUPO TMTGR on GR.ID_TIPO_GRUPO = TMTGR.ID where GR.ID_FLUJO = ? order by ORDEN");
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i<listaFlujosFirma.size();i++) {
				ps.setInt(1, listaFlujosFirma.get(i).getFlujo_id());
				rs = ps.executeQuery();
				rs.last();
				int numRows = rs.getRow();
				if (numRows <= 0) { // Si NO se encuentran resultados 
					listOfGroups = null;
				} else { // Si se encuentran, carga los datos en una lista para devolverlos.
					rs.beforeFirst();
					listOfGroups = new ArrayList<PF_GruposBean>();
					while (rs.next()) { 
						PF_GruposBean grupoBean = new PF_GruposBean();
						FirmanteBean firmante = new FirmanteBean();
						grupoBean.setID_GRUPO(rs.getInt("ID_GRUPO"));
						grupoBean.setFIRMANTES_REQUERIDOS(rs.getInt("FIRMANTES_REQUERIDOS"));
						grupoBean.setORDEN(rs.getInt("ORDEN"));
						grupoBean.setID_FLUJO(rs.getInt("ID_FLUJO"));
						grupoBean.setID_TIPO_GRUPO(rs.getInt("ID_TIPO_GRUPO"));
						grupoBean.setCERRADO(rs.getInt("CERRADO"));
						grupoBean.setTIPO_GRUPO(rs.getString("DESCRIPCION"));
						/* Datos del Firmante */
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
						grupoBean.setFirmante(firmante);
						listOfGroups.add(grupoBean);
					}
				}
				listaFlujosFirma.get(i).setListOfGroups(listOfGroups);
			}
			rs.close(); // Cerrar la conexión con la BBDD
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
	}

	public PF_FlujosBean findFlujoFirma(int idTipoFlujo, int ordenActivo, String descripcion,int id_flujo) {
		PF_FlujosBean FlujosFirma = null;
		Connection con  = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql.append("SELECT * FROM pf_flujos WHERE ");
			/**
			 * Si alguno de los campos int viene a 0 se entiende que son todos los resultados !!!
			 */
			if(id_flujo!=0) {
				sql.append("ID_FLUJO=? AND ");
			}
			if(idTipoFlujo!=0) {
				sql.append("id_tipo_flujo=? AND ");
			}
			if(ordenActivo!=0) {
				sql.append("orden_activo=? AND ");
			}
			sql.append("descripcion LIKE UPPER(?) ORDER BY descripcion ASC");
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=0;
			if(id_flujo!=0) {
				ps.setInt(++i, id_flujo);
			}
			if(idTipoFlujo!=0) {
				ps.setInt(++i, idTipoFlujo);
			}
			if(ordenActivo!=0) {
				ps.setInt(++i, ordenActivo);
			}if(null == descripcion) {
				descripcion ="";
			}
			ps.setString(++i, "%"+descripcion.toUpperCase()+"%");
			rs = ps.executeQuery();
			rs.last();
			int numRows = rs.getRow();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
			}else if(numRows > 1){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha encontrado más de un resultado.", "Refine la búsqueda."));
			}else { // Si se encuentran, carga los datos en una lista para devolverlos.
				@SuppressWarnings("unused")
				int index = 0;
				rs.beforeFirst(); // vuelvo al primer registro
				while (rs.next()) { // Creo un objeto de la clase "Expediente"
					FlujosFirma = new PF_FlujosBean();
					FlujosFirma.setFlujo_id(rs.getInt("ID_FLUJO")); // ID_FLUJO NUMBER(16,0)
					FlujosFirma.setFechaCreacion(rs.getTimestamp("FECHA_CREACION")); // FECHA_CREACION DATE
					FlujosFirma.setTipoFlujo(rs.getInt("ID_TIPO_FLUJO")); // ID_TIPO_FLUJO NUMBER(16,0)
					FlujosFirma.setOrdenActivo(rs.getInt("ORDEN_ACTIVO")); // ORDEN_ACTIVO NUMBER(2,0)
					FlujosFirma.setDescripcion(rs.getString("DESCRIPCION")); // DESCRIPCION VARCHAR2(255 BYTE)
				}
			}
			rs.close(); // Cerrar la conexión con la BBDD
			//getGroupDetail();
			FlujosFirma.setListOfPersonas(getAuthorizedPersonsFor(FlujosFirma.getFlujo_id()));
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return FlujosFirma;	
	}
	
	public String getFlujoDescripcion(int idCircuito) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		String description = "";
		try {
			con = DataBaseUtils.getConnection("OC3F");
			String sql = "SELECT DESCRIPCION FROM pf_flujos WHERE id_flujo = ? ORDER BY descripcion ASC";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, idCircuito);
			rs = ps.executeQuery();
			Result res = null;
			res = ResultSupport.toResult(rs);
			int numRows = res.getRowCount();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				rs.beforeFirst(); // vuelvo al primer registro
				if (numRows < 100) { // Sí los resultados son menos de 100 ...
					while (rs.next()) { // Creo un objeto de la clase "Expediente"
						description = (rs.getString("DESCRIPCION"));
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta devuelve demasiados resultados, especifique un criterio más concreto.", ""));
				}
			}
			rs.close(); // Cerrar la conexión con la BBDD
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return description;
	}
	
	public Map<String,String> listOfTipoFlujo (){
		Map<String, String> list = new LinkedHashMap<>();
		Connection con  = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		String sql = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql=("SELECT * FROM pf_tm_tipo_flujo");
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.beforeFirst(); 
			while (rs.next()) { 
				list.put(rs.getString(1), rs.getString(3));
			}
			rs.close(); // Cerrar la conexión con la BBDD
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return list;
	}
	
	public void saveFlujo(PF_FlujosBean flujo, List<PF_GruposBean> selectedPF_FlujoGroupsToDelete) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		String sql = null;
		if (flujo.getFlujo_id()==0) {
			try {
				con = DataBaseUtils.getConnection("OC3F");
				sql = "SELECT ID_FLUJO FROM PF_FLUJOS WHERE ROWNUM = 1 ORDER BY ID_FLUJO DESC";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();
				rs.beforeFirst();
				rs.next();
				int nextReg = rs.getInt("ID_FLUJO");
				flujo.setFlujo_id(++nextReg);
				sql = "INSERT INTO PF_FLUJOS (ID_FLUJO, FECHA_CREACION, ID_TIPO_FLUJO, ORDEN_ACTIVO, DESCRIPCION) VALUES (?, ?, ?, ?, ?)";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, flujo.getFlujo_id());
				ps.setTimestamp(2,flujo.getFechaCreacion());
				ps.setInt(3, flujo.getTipoFlujo());
				ps.setInt(4, flujo.getOrdenActivo());
				ps.setString(5, flujo.getDescripcion());
				ps.execute();
			} catch (SQLException e) {
				logger.error(e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error SQL al crear el Circuito."+flujo.getDescripcion(), ""));
			}catch (Exception e) {
				logger.error(e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear el Circuito de Firma."+flujo.getDescripcion(), ""));
			}
		}
		List<PF_GruposBean> groups = flujo.getListOfGroups();
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "UPDATE PF_FLUJOS SET DESCRIPCION = ? where ID_FLUJO = ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, flujo.getDescripcion());
			ps.setInt(2,flujo.getFlujo_id());
			ps.execute();
			for (PF_GruposBean pf_GruposBean : groups) {
				if (pf_GruposBean.getID_GRUPO()!=0) {
					/*
					 * Actualizo los registros existentes.
					 */
					sql = "UPDATE PF_GRUPOS SET FIRMANTES_REQUERIDOS=?, ORDEN=?, ID_FLUJO=?, ID_TIPO_GRUPO=?, CERRADO=? WHERE ID_GRUPO=?";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setInt(1, pf_GruposBean.getFIRMANTES_REQUERIDOS());
					ps.setInt(2, pf_GruposBean.getORDEN());
					ps.setInt(3, pf_GruposBean.getID_FLUJO());
					ps.setInt(4, pf_GruposBean.getID_TIPO_GRUPO());
					ps.setInt(5, pf_GruposBean.getCERRADO());
					ps.setInt(6, pf_GruposBean.getID_GRUPO());
					ps.execute();
					sql = "UPDATE PF_GRUPO_PERSONAS SET ID_PERSONA=? , PERSONA_OBLIGATORIA=0 ,REALIZADO = 0 WHERE ID_GRUPO = ?";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setInt(1, pf_GruposBean.getFirmante().getIdFirmante());
					ps.setInt(2, pf_GruposBean.getID_GRUPO());
					ps.execute();
				}else {
					/*
					 * Inserto los registros nuevos.
					 */
					sql = "SELECT ID_GRUPO FROM PF_GRUPOS WHERE ROWNUM = 1 ORDER BY ID_GRUPO DESC";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = ps.executeQuery();
					rs.beforeFirst();
					rs.next();
					int nextReg =rs.getInt("ID_GRUPO");
					pf_GruposBean.setID_GRUPO(++nextReg);
					sql = "INSERT INTO PF_GRUPOS (ID_GRUPO, FIRMANTES_REQUERIDOS, ORDEN, ID_FLUJO, ID_TIPO_GRUPO, CERRADO) VALUES (?, ?, ?, ?, ?, ?)";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setInt(1, pf_GruposBean.getID_GRUPO());
					ps.setInt(2, pf_GruposBean.getFIRMANTES_REQUERIDOS());
					ps.setInt(3, pf_GruposBean.getORDEN());
					ps.setInt(4, flujo.getFlujo_id());
					ps.setInt(5, pf_GruposBean.getID_TIPO_GRUPO());
					ps.setInt(6, pf_GruposBean.getCERRADO());
					ps.execute();
					sql = "SELECT ID_GRUPO_PERSONA FROM PF_GRUPO_PERSONAS WHERE ROWNUM = 1 ORDER BY id_grupo_persona DESC";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = ps.executeQuery();
					rs.beforeFirst();
					rs.next();
					nextReg = rs.getInt("ID_GRUPO_PERSONA");
					sql = "INSERT INTO PF_GRUPO_PERSONAS (ID_GRUPO_PERSONA, ID_GRUPO, ID_PERSONA, PERSONA_OBLIGATORIA, REALIZADO) VALUES (?,?,?,0,0)";
					ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setInt(1, ++nextReg);
					ps.setInt(2, pf_GruposBean.getID_GRUPO());
					ps.setInt(3, pf_GruposBean.getFirmante().getIdFirmante());
					ps.execute();
				}
				
			}
			if (null!=selectedPF_FlujoGroupsToDelete) {
				for (PF_GruposBean pf_GruposToDelete : selectedPF_FlujoGroupsToDelete) {
					if (pf_GruposToDelete.getID_FLUJO()!=0) {//Sólo si los grupos han sido cargados de Base de datos.
						sql="DELETE PF_GRUPOS WHERE ID_GRUPO = ?";
						ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, pf_GruposToDelete.getID_GRUPO());
						ps.execute();
						sql="DELETE FROM PF_GRUPO_PERSONAS WHERE ID_GRUPO = ?";
						ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, pf_GruposToDelete.getID_GRUPO());
						ps.execute();
					}
				}
			}
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar el Circuito de Firma.", ""));
		}finally {
			DataBaseUtils.close(con);
		}
	}
	
	public void deleteFlujo(int id_flujo) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			PF_FlujosBean flujo = findFlujoFirma(0, 0, null, id_flujo);
			List<PF_GruposBean> PF_FlujoGroupsToDelete = flujo.getListOfGroups();
			if (null!=PF_FlujoGroupsToDelete) {
				for (PF_GruposBean pf_GruposToDelete : PF_FlujoGroupsToDelete) {
					if (pf_GruposToDelete.getID_FLUJO()!=0) {//Sólo si los grupos han sido cargados de Base de datos.
						sql="DELETE PF_GRUPOS WHERE ID_GRUPO = ?";
						ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, pf_GruposToDelete.getID_GRUPO());
						ps.execute();
						sql="DELETE FROM PF_GRUPO_PERSONAS WHERE ID_GRUPO = ?";
						ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, pf_GruposToDelete.getID_GRUPO());
						ps.execute();
					}
				}
			}
			sql="DELETE PF_FLUJOS WHERE ID_FLUJO = ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, flujo.getFlujo_id());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar el Circuito de Firma.", ""));
		}finally {
			DataBaseUtils.close(con);
		}
	}
	
	public void setAuthorizedPerson(PF_FlujosBean selectedPF_Flujo, List<String> toSend) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		String sql = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			
			sql = "SELECT ID_REGISTRO FROM PF_CIRCUITO_POR_PERSONA WHERE ROWNUM = 1 ORDER BY ID_REGISTRO DESC";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.beforeFirst();
			rs.next();
			int nextReg =rs.getInt("ID_REGISTRO");
			
			sql = "insert into PF_CIRCUITO_POR_PERSONA values (?,?,?)";
		
			for (String carLicense : toSend) {
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, ++nextReg);
				ps.setInt(2, selectedPF_Flujo.getFlujo_id());
				ps.setString(3, carLicense);
				ps.execute();
			}
			
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al agregar personas al Circuito de Firma.", ""));
		}finally {
			DataBaseUtils.close(con);
		}
		
	}
	
	public void deleteAuthorizedPerson(PF_FlujosBean selectedPF_Flujo, String carLicense) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql = "delete from PF_CIRCUITO_POR_PERSONA where ID_CIRCUITO = ?  and DNI_SOLICITANTE =?";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, selectedPF_Flujo.getFlujo_id());
				ps.setString(2, carLicense);
				ps.execute();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar a la persona"+carLicense+" del Circuito de Firma.", ""));
		}finally {
			DataBaseUtils.close(con);
		}
	}
	
	/*
	 * Getters y Setters
	 */

	public List<PF_FlujosBean> getLiFF() {
		return listaFlujosFirma;
	}

	public void setLiFF(List<PF_FlujosBean> liFF) {
		this.listaFlujosFirma = liFF;
	}


}
