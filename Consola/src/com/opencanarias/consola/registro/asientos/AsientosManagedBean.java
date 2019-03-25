package com.opencanarias.consola.registro.asientos;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;

public class AsientosManagedBean implements Serializable,ManagedBeanInterface{

	private static final long serialVersionUID = 1L;
	//private static Logger logger = Logger.getLogger(AsientosManagedBean.class);
	private List<AsientosBean> liAsientos;
	private AsientosBean asiento;

	public void newAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("N");
		ms.setSearchOption("") ;
		ms.setSearchOption2("");
	}

	public void findAction(){
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("B");
		AsientosDAO ad = new AsientosDAO();
		liAsientos = ad.getAsientosList(ms.getSearchOption(), ms.getSearchOption2());
	}

	public void editAction(Object numAsiento){
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("Ed");
		AsientosDAO ad = new AsientosDAO();
		asiento = new AsientosBean();
		asiento = ad.getAsiento((String)numAsiento);
	}

	public void saveAction(){
		AsientosDAO ad = new AsientosDAO();
		ad.setAsiento(asiento);
	}

	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
		liAsientos = null;

	}

	public void deleteAction(Object identification) {
	}

	public void deleteRelationship(String tipoOperacion){
		if (!asiento.getEXPEDIENTE().isEmpty() && null != asiento.getEXPEDIENTE()) {
			boolean exp = false;
			AsientosExpedientesDAO aed = new AsientosExpedientesDAO();
			exp = aed.deleteAsientoExpedienteRelationship(asiento, tipoOperacion);
			if (exp) {
				AsientosDAO ad = new AsientosDAO();
				asiento.setEXPEDIENTE(null);
				// update asientos set expediente='',familia_id='',asuntoexpediente='',asociadoexpediente=0,procedimiento_id='' where identificacion=?"
				asiento.setFAMILIA_ID(null);
				asiento.setASUNTOEXPEDIENTE(null);
				asiento.setASOCIADOEXPEDIENTE(0);
				asiento.setPROCEDIMIENTO_ID(null);
				ad.setAsiento(asiento);
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"El asiento no tiene expediente asociado."," No se realiza esta operación."));
		}
		
		
	}

	/*
	 * Getters and Setters
	 */
	public List<AsientosBean> getLiAsientos() {
		return liAsientos;
	}

	public void setLiAsientos(List<AsientosBean> liAsientos) {
		this.liAsientos = liAsientos;
	}

	public AsientosBean getAsiento() {
		return asiento;
	}

	public void setAsiento(AsientosBean asiento) {
		this.asiento = asiento;
	}

	public void setPrueba(boolean a) {
		this.asiento.setPRUEBA(0);
		if (a) {
			this.asiento.setPRUEBA(1);
		}
	}

	public boolean getPrueba() {
		boolean a = false;
		if (this.asiento.getPRUEBA()==1) {
			a = true;
		}
		return a;
	}

}
