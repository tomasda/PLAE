package com.opencanarias.consola.gestorExpedientes.gestionExpedientes;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.ldap.LDAPManager;
import com.opencanarias.consola.ldap.LDAPPersonaBean;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.registro.terceros.TercerosBean;
import com.opencanarias.consola.registro.terceros.TercerosDAO;


public class GestionExpedientesManagedBean implements Serializable,ManagedBeanInterface {
	private static final long serialVersionUID = 1L;
	private String numExpedienteToEdit;
	private static Logger logger = Logger.getLogger(GestionExpedientesManagedBean.class);
	private  List<ExpedientesBean> liExp;
	private  ExpedientesBean expediente;
	private  List<TrackBean> liTrack;
	private  List<DocumentBean> liDoc;
	private String OWNER;

	public GestionExpedientesManagedBean() {
		liExp = null;
		expediente = null;
		liTrack = null;
		liDoc = null;
		OWNER = null;

		numExpedienteToEdit = null;
	}

	public void newAction() {}
	
	public void findAction(){
		MenuSessionOption ms  = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		GestionExpedientesDAO ged = new GestionExpedientesDAO();
		try {
			setLiExp(ged.getBusqueda(ms.getSearchOption(), ms.getSearchOption2()));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void editAction(Object numExpID){
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		GestionExpedientesDAO ged = new GestionExpedientesDAO();
		ms.setMenuEnabledForm("Ed");
		setNumExpedienteToEdit((String)numExpID);
		setExpediente(ged.getSingleData((String)numExpID));
		setLiTrack(ged.getHistoricoExp((String)numExpID));
		setLiDoc(ged.getDocsExp((String)numExpID));
		getOwner(expediente.getOWNER_());
	}

	public void saveAction(){

		refresh();
	}

	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
	}
	

	public void deleteAction(Object identification) {
				
	}
	public  void refresh(){
		GestionExpedientesDAO ged = new GestionExpedientesDAO();
		setExpediente(ged.getSingleData(numExpedienteToEdit));
		setLiTrack(ged.getHistoricoExp(numExpedienteToEdit));
		setLiDoc(ged.getDocsExp(numExpedienteToEdit));
	}

	public void cancelExpedienteAction(String idExpediente) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
		GestionExpedientesDAO ged = new GestionExpedientesDAO();
		ged.cancelarExpediente(idExpediente);
		refresh();
	}

	/*
	 * Functions  
	 */
	public String obtenerEstado(String estado) {
		String data = "";
		if (estado.equals("0")) {
			data = "ACTIVO";
		}
		if (estado.equals("1")) {
			data = "FINALIZADO";
		}
		if (estado.equals("2")) {
			data = "CANCELADO";
		}
		return data;
	}
	public String obtenerEstadoStyle(String estado) {
		String data = "";
		if (estado.equals("0")) {
			data = "text-success";
		}
		if (estado.equals("1")) {
			data = "text-white";
		}
		if (estado.equals("2")) {
			data = "text-danger";
		}
		return data;
	}

	private  void getOwner(String identificacion) {
		TercerosDAO td = new TercerosDAO();
		StringBuffer data = new StringBuffer();
		List<TercerosBean> tercero = new ArrayList<TercerosBean>();
		tercero = td.getTercerosList(null, identificacion, null, null,null);// OperacionesTerceros.getSingleData_Identificacion(identificacion);
		if (tercero.size()>0) {
			if (null != tercero.get(0).getTIPO_IDENTIFICACION_ID()) {
				if (tercero.get(0).getTIPO_IDENTIFICACION_ID().equals("C") || tercero.get(0).getTIPO_IDENTIFICACION_ID().equals("O")) {
					data.append(tercero.get(0).getRAZON_SOCIAL());
				} else {
					if (null != tercero.get(0).getNOMBRE()) {
						data.append(tercero.get(0).getNOMBRE());
					}
					if (null == tercero.get(0).getAPELLIDO_1()) {
						data.append(tercero.get(0).getAPELLIDO_1());
					}
					if (null == tercero.get(0).getAPELLIDO_2()) {
						data.append(tercero.get(0).getAPELLIDO_2());
					}
				}
			}

		} else {
			LDAPManager auth = new LDAPManager();
			LDAPPersonaBean ldap = new LDAPPersonaBean();
			try {
				ldap = auth.findLDAPUser(identificacion);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				System.out.println("No existe usuario en LDAP");
				e.printStackTrace();
			}
			data.append(ldap.getCn());
		}
		setOWNER(data.toString());
	}


	/*
	 * Getteers and Setters
	 */
	public String getNumExpedienteToEdit() {
		return numExpedienteToEdit;
	}

	public void setNumExpedienteToEdit(String numExpedienteToEdit_) {
		numExpedienteToEdit = numExpedienteToEdit_;
	}

	public List<ExpedientesBean> getLiExp() {
		return liExp;
	}

	public void setLiExp(List<ExpedientesBean> liExp) {
		this.liExp = liExp;
	}

	public ExpedientesBean getExpediente() {
		return expediente;
	}

	public void setExpediente(ExpedientesBean expediente) {
		this.expediente = expediente;
	}
	public List<TrackBean> getLiTrack() {
		return liTrack;
	}

	public void setLiTrack(List<TrackBean> liTrack) {
		this.liTrack = liTrack;
	}

	public List<DocumentBean> getLiDoc() {
		return liDoc;
	}

	public void setLiDoc(List<DocumentBean> liDoc) {
		this.liDoc = liDoc;
	}


	public String getOWNER() {
		return OWNER;
	}

	public void setOWNER(String oWNER) {
		OWNER = oWNER;
	}


}
