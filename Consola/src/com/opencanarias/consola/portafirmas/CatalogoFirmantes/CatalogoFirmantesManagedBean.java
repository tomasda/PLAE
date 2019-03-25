/**
 * 
 */
package com.opencanarias.consola.portafirmas.CatalogoFirmantes;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.aeat.valida.Validador;
import com.opencanarias.consola.commons.Patrones;
import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;

/**
 * @author Tomás Delgado Acosta.
 *
 */
public class CatalogoFirmantesManagedBean implements Serializable,ManagedBeanInterface{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CatalogoFirmantesManagedBean.class);
	private MenuSessionOption ms  = null;
	private CatalogoFirmanteDAO cfdao = null;
	private FirmanteBean firmante = null;
	private List<FirmanteBean> listOfFirmantes = null;
	private String environment;
	
	public CatalogoFirmantesManagedBean() {
		this.cfdao = new CatalogoFirmanteDAO();
		this.firmante = null;
		this.listOfFirmantes = null;
		this.ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		environment = ms.getEnvironmentSelected();
	}
	
	public void findAction(){
		if (null==this.ms)
				this.ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		try {
			setListOfFirmantes(cfdao.findFirmantes(ms.getSearchOption(), ms.getSearchOption2(), ms.getSearchOption3(), ms.getSearchOption4()));
		} catch (Exception e) {
			logger.error(e);
		}
	}
	public List<FirmanteBean> findSigners(String name, String surname, String surname2, String DNI ){
		this.cfdao = new CatalogoFirmanteDAO();
		List<FirmanteBean> list = null;
		try {
			list = cfdao.findFirmantes(name,surname,surname2,DNI);
		} catch (Exception e) {
			logger.error(e);
		}
		return list;
	}

	public void editAction(Object idFirmante){
		if (null==this.ms)
			this.ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		this.ms.setMenuEnabledForm("Ed");
		setFirmante(cfdao.getFirmanteById((String)String.valueOf(idFirmante)));
	}
	
	public void deleteAction(Object idFirmante){
		cfdao.deleteFirmante((String)idFirmante);
		refresh();
	}
	
	public void newAction() {
		if (null==this.ms)
			this.ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		this.ms.setMenuEnabledForm("Ed");
		setFirmante(new FirmanteBean());
	}

	public void saveAction(){
		if (preValidation()) {
			if (firmante.getIdFirmante()==0) {//Nuevo Firmante
				if (cfdao.getIfNIFExist(firmante.getDNI_NIE())>0) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ya existe un firmante con ése NIF NIE", "Verifique los datos introducidos."));
				}else {
					int newID = cfdao.getNewID();
					if (newID!=0) {
						firmante.setIdFirmante(newID);
						cfdao.createFirmante(firmante);
					}else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se devuelve un ID para el nuevo firmante", "Verifique la conexión a Base de Datos."));
					}
				}
			}else {
				cfdao.saveFirmante(firmante);
			}
			refresh();
		}
	}

	private boolean preValidation() {
		boolean result = false;
		/*
		 * Validación del Nombre
		 */
		boolean name = false;
		if (firmante.getNombre().length()>0) {
			name =  true;
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Nombre del firmante no puede estar vacío", "Verifique el dato introducido."));
		}
		/*
		 * Validación del Nombre
		 */
		boolean surname = false;
		if (firmante.getApellido().length()>0) {
			surname =  true;
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Apellido 1 del firmante no puede estar vacío", "Verifique el dato introducido."));
		}
		/*
		 * Validación del NIF
		 */
		boolean nif= false;
		Validador validador = new Validador();
        int e = validador.checkNif(firmante.getDNI_NIE());
         if (e > 0) {
            nif = true;
         }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El NIF introducido no es correcto", "Verifique el dato introducido."));
         }
		/*
		 * Validación del correo
		 */
         boolean email=false;
         if (firmante.getAdjuntos_alertas()==1) {//Si tiene habilitadas las alertas.
        	// Patrón para validar el email
             Pattern pattern = Pattern.compile(Patrones.getPatternCorreo());
             Matcher mather = pattern.matcher(firmante.getMail());
             if (mather.find() == true) {
            	 email=true;
             } else {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Correo Electrónico introducido no es correcto", "Verifique el dato introducido."));
             }
         }else {
        	 email = true;
         }
         
         /*
          * Si se cumplen todas las validaciones.
          */
         if(name && surname && nif && email) {
        	 result = true;
         }
		return result;
	}

	public void returnAction() {
		if (null==this.ms)
			this.ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		this.ms.setMenuEnabledForm("I");
	}

	public void refresh(){
		if (null==this.ms)
			this.ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		try {
			setListOfFirmantes(cfdao.findFirmantes(ms.getSearchOption(), ms.getSearchOption2(), ms.getSearchOption3(), ms.getSearchOption4()));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/*
	 * Functions
	 */

	/*
	 * Getters & Setters
	 */
	public List<FirmanteBean> getListOfFirmantes() {
		// VERIFICA EL CAMBIO DE ENTORNO ||  SI HA CAMBIADO LIMPIA EL LISTADO DE RESULTADOS
		if (!environment.equals(ms.getEnvironmentSelected())) {
			environment= ms.getEnvironmentSelected();
			listOfFirmantes = null;
		}
		return listOfFirmantes;
	}

	public void setListOfFirmantes(List<FirmanteBean> listOfFirmantes) {
		this.listOfFirmantes = listOfFirmantes;
	}

	public FirmanteBean getFirmante() {
		return firmante;
	}

	public void setFirmante(FirmanteBean firmante) {
		this.firmante = firmante;
	}

}
