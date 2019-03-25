package com.opencanarias.consola.registro.tablasComunes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

//@ManagedBean
//@SessionScoped
public class TablasComunesManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TablasComunesManagedBean.class);
	private List<PaisesBean> liPaises;
	private List<ProvinciasBean> liProvincias;
	private List<MunicipiosBean> liMunicipios;
	private List<IdDescripcionBean> liTiposTransportesEntrada;
	private List<IdDescripcionBean> liEstadoRegistros;
	private int PaisID_TMP = 0;
	private String ProvinciaID_TMP;
	private List<CanalesBean> liCanales;
	private List<OrigenAsiento> liOrigenAsiento;

	
	

	public List<PaisesBean> getPaisesList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		if (liPaises == null) {
			liPaises = new ArrayList<PaisesBean>();
			sql = "SELECT * FROM CAT_PAISES ORDER BY DESCRIPCION ASC";
			try {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					liPaises.add(new PaisesBean(rs.getString("ID"), rs.getString("DESCRIPCION")));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		}
		return liPaises;
	}

	public List<ProvinciasBean> getProvinciasList(Integer id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		if (null == id){// Sí el valor viene a null desde BBDD
			id = 0;
		}
		if (id != PaisID_TMP) { // Cargar la lista sí cambia de PAIS.
			if (null == liProvincias || PaisID_TMP != id) { // Sí es la primera vez o cambia de pais
				PaisID_TMP = id;
				liProvincias = new ArrayList<ProvinciasBean>();
				if (PaisID_TMP == 108) { // Sí es ESPAÑA
					sql = "SELECT * FROM CAT_PROVINCIAS ORDER BY DESCRIPCION ASC";
					try {
						con = DataBaseUtils.getConnection("EAREGISTRO");
						ps = con.prepareStatement(sql);
						//ps.setString(1, ProvinciaID_TMP);
						rs = ps.executeQuery();
						while (rs.next()) {
							liProvincias.add(new ProvinciasBean(rs.getString("ID"), rs.getString("DESCRIPCION")));
						}
						rs.close();
						ps.close();
					} catch (SQLException e) {
						logger.error(e);
					}finally {
						DataBaseUtils.close(con);
					}
				} else {
					liProvincias.add(new ProvinciasBean("0", "Extranjera"));
				}
			}
		}
		return liProvincias;
	}

	public List<MunicipiosBean> getMunicipiosList(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		if (id.equals(null)){
			id = "0";
		}
		if (!id.equals(ProvinciaID_TMP)) {// SI CAMBIA LA PROVINCIA CARGA EL LISTADO DE MUNICIPIOS
			liMunicipios = new ArrayList<MunicipiosBean>();
			ProvinciaID_TMP = id;
			if (id.equals("0")) { // Sí la provincia es Extranjera
				liMunicipios.add(new MunicipiosBean("0", "Extranjero"));
			} else { // Sí la provincia es ESPAÑOLA
				sql = "SELECT * FROM CAT_MUNICIPIOS WHERE PROVINCIA_ID = ? ORDER BY DESCRIPCION ASC";
				try {
					con = DataBaseUtils.getConnection("EAREGISTRO");
					ps = con.prepareStatement(sql);
					ps.setString(1, ProvinciaID_TMP);
					rs = ps.executeQuery();
					while (rs.next()) {
						liMunicipios.add(new MunicipiosBean(rs.getString("ID"), rs.getString("DESCRIPCION")));
					}
					rs.close();
					ps.close();
				} catch (SQLException e) {
					logger.error(e);
				}finally {
					DataBaseUtils.close(con);
				}
			}
		}
		return liMunicipios;
	}

	public List<CanalesBean> getCanalesList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		if (liCanales == null) {
			liCanales = new ArrayList<CanalesBean>();
			sql = "SELECT * FROM CAT_CANALES ORDER BY DESCRIPCION ASC";
			try {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					liCanales.add(new CanalesBean(rs.getString("ID"), rs.getString("DESCRIPCION")));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		}
		return liCanales;
	}
	
	public List<IdDescripcionBean> getTiposTransporteEntradaList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		if (liTiposTransportesEntrada == null) {
			liTiposTransportesEntrada = new ArrayList<IdDescripcionBean>();
			sql = "SELECT * FROM CAT_TIPOS_TRANSPORTES_ENTRADA ORDER BY DESCRIPCION ASC";
			try {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					liTiposTransportesEntrada.add(new IdDescripcionBean(rs.getString("ID"), rs.getString("DESCRIPCION")));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		}
		return liTiposTransportesEntrada;
	}
	
	public List<IdDescripcionBean> getEstadoRegistrosList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		if (liEstadoRegistros == null || liEstadoRegistros.isEmpty()) {
			liEstadoRegistros = new ArrayList<IdDescripcionBean>();
			sql = "SELECT * FROM CAT_ESTADOS_REGISTRO ORDER BY DESCRIPCION ASC";
			try {
				con = DataBaseUtils.getConnection("EAREGISTRO");
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					liEstadoRegistros.add(new IdDescripcionBean(rs.getString("ID"), rs.getString("DESCRIPCION")));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				logger.error(e);
			}finally {
				DataBaseUtils.close(con);
			}
		}
		return liEstadoRegistros;
	}
	


	public String getSinglePaisData(String ID) {
		String data = "";

		return data;
	}

	public String getSingleProvinciasData(String ID) {
		String data = "";

		return data;
	}

	public String getSingleMunicipioData(String ID) {
		String data = "";

		return data;
	}

	public List<OrigenAsiento> getLiOrigenAsiento() {
		/**
		 * 04-2018
		 * Esta propiedad de los asientos no tiene un catálogo en Base de Datos.
		 * Por ése motivo la definición se realizae en código.
		 */
		if (null==liOrigenAsiento) {
			liOrigenAsiento = new ArrayList<>();
			OrigenAsiento oa = new OrigenAsiento();
			oa.setOrigeAsiento("R");
			oa.setOrigenAsientoDescripcion("Registro Electrónico");
			liOrigenAsiento.add(oa);
			oa = new OrigenAsiento();
			oa.setOrigeAsiento("S");
			oa.setOrigenAsientoDescripcion("SEDE");
			liOrigenAsiento.add(oa);
			oa = new OrigenAsiento();
			oa.setOrigeAsiento("P");
			oa.setOrigenAsientoDescripcion("Presencial");
			liOrigenAsiento.add(oa);
		}
		return liOrigenAsiento;
	}

	public void setLiOrigenAsiento(List<OrigenAsiento> liOrigenAsiento) {
		this.liOrigenAsiento = liOrigenAsiento;
	}
	
	public String getOrigenAsientoDescription(String value) {
		String description = null;
		getLiOrigenAsiento();
		for (OrigenAsiento oa : liOrigenAsiento) {
			if (oa.getOrigeAsiento().equals(value)) {
				description = oa.getOrigenAsientoDescripcion();
			}
		}
		return description;
	}
}
