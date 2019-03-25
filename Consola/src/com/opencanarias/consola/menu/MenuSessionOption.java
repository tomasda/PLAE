package com.opencanarias.consola.menu;


import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.opencanarias.consola.commons.LoadProperties;


public class MenuSessionOption implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static String fichero = "menuOptions"; // Fichero de propiedades
	private String contentURL;
	private String environmentSelected;
	private String menuEnabledForm;//  = "I";
	private String searchOption;
	private String searchOption2;
	private String searchOption3;
	private String searchOption4;
	private boolean enabledValidation;

	public MenuSessionOption() {
		LoadProperties lp = new LoadProperties();
		enabledValidation = Boolean.valueOf(lp.getParameter(fichero, "enabledValidation"));
		environmentSelected = lp.getParameter(fichero, "defaultenvironment");
	}

	/*
	 * Getters y Setters
	 */
	public String getEnvironmentSelected() {
		return environmentSelected;
	}

	public void setEnvironmentSelected(String environment_Selected) {	
		environmentSelected = environment_Selected;
	}
	
	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getSearchOption2() {
		return searchOption2;
	}

	public void setSearchOption2(String searchOption2) {
		this.searchOption2 = searchOption2;
	}

	public String getSearchOption3() {
		return searchOption3;
	}

	public void setSearchOption3(String searchOption3) {
		this.searchOption3 = searchOption3;
	}

	public String getSearchOption4() {
		return searchOption4;
	}

	public void setSearchOption4(String searchOption4) {
		this.searchOption4 = searchOption4;
	}

	public  String getMenuEnabledForm() {
		return menuEnabledForm;
	}

	public  void setMenuEnabledForm(String menuEF) {
		menuEnabledForm = menuEF;
	}

	public String getContentURL() {
		return contentURL;
	}

	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}
	
	public boolean getEnabledValidation() {
		return enabledValidation;
	}

	public void setEnabledValidation(boolean enabledValidation) {
		this.enabledValidation = enabledValidation;
	}

	public String valueChangeEnvironmentSelected(ValueChangeEvent e) {
		contentURL = "/herramientas/herramientas.xhtml";
		// Al cambiar de Formulario defino el Formulario a "Inicio"
		menuEnabledForm = "I"; 
		clearSerarchOptions();
		/**
		 * Borra todos los datos de sesión de los Bean que están configurados como view.
		 */
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
		return "menu";
	}

	public void newContentValue(String a) {
		if (!this.contentURL.equals(a)) {
			this.contentURL = a;
			// Al cambiar de Formulario defino el Formulario a "Inicio"
			menuEnabledForm = "I"; 
			clearSerarchOptions();
		}
	}

	public Boolean enabledPanel(String option) {
		boolean a = false;
		if (option.equals("B")) {// ¿Eres el formulario de búsqueda?
			if (menuEnabledForm.equals("I")) { // Si estás en el paso del flujo
												// "I" Inicio
				a = true;
			} else if (menuEnabledForm.equals("B")) { // Si estás en el paso del
														// flujo "B" Busqueda y
														// reporte de resultados
				a = true;
			}
		}
		if (option.equals("E")) {// ¿Eres el formulario de Edición
			if (menuEnabledForm.equals("Ed")) {
				a = true;
			}
			if (menuEnabledForm.equals("N")) {
				a = true;
			}
		}
		return a;
	}

	public Boolean editOrNew() {
		boolean a = false;
		if (menuEnabledForm.equals("Ed")) {
			a = true;
		}
		if (menuEnabledForm.equals("N")) {
			a = false;
		}
		return a;
	}
	
	public void clearSerarchOptions() {
		searchOption = null;
		searchOption2 = null;
		searchOption3 = null;
		searchOption4 = null;
	}

}
