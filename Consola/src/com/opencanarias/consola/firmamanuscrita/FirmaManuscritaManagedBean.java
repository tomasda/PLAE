package com.opencanarias.consola.firmamanuscrita;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;

public class FirmaManuscritaManagedBean implements ManagedBeanInterface {
	private List<CatDispositivosBean> dispositivos = new ArrayList<>();
	private CatDispositivosBean dispositivo = null;
	private String selectedDestiny;

	@Override
	public void findAction() {
		MenuSessionOption ms  = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		FirmaManuscritaDAO fmDAO = new FirmaManuscritaDAO();
		this.dispositivos = fmDAO.getListOfDevices(ms.getSearchOption(), ms.getSearchOption2());
		if (null!=this.dispositivos) {
			if (this.dispositivos.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se han encontrado resultados.", ""));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok.", ""));
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ""));
		}
	}

	@Override
	public void newAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editAction(Object identification) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("Ed");
		this.dispositivo = (CatDispositivosBean)identification;
	}

	@Override
	public void saveAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
		
	}

	@Override
	public void deleteAction(Object identification) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * FUNCIONES
	 */
	public void valueChange(ValueChangeEvent e) {
		System.out.println(e.getNewValue());
	}
	
	/*
	 * GETTERS ANS SETTERS
	 * 
	 */
	public List<CatDispositivosBean> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(List<CatDispositivosBean> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public CatDispositivosBean getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(CatDispositivosBean dispositivo) {
		this.dispositivo = dispositivo;
	}

	public String getSelectedDestiny() {
		return selectedDestiny;
	}

	public void setSelectedDestiny(String selectedDestiny) {
		this.selectedDestiny = selectedDestiny;
	}

}
