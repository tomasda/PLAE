package com.opencanarias.consola.portafirmas.circuitos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.ldap.LDAPPersonaBean;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.portafirmas.CatalogoFirmantes.CatalogoFirmantesManagedBean;
import com.opencanarias.consola.portafirmas.CatalogoFirmantes.FirmanteBean;
import com.opencanarias.consola.portafirmas.CatalogoFirmantesPersonas.CatalogoFirmantesPersonasManagedBean;
import com.opencanarias.consola.utilidades.DateUtils;


public class PF_FlujosManagedBean implements Serializable,ManagedBeanInterface{

	private static final long serialVersionUID = 1L;
	PF_FlujosDAO pffdao = null;
	private List<PF_FlujosBean> listOf = null;
	private PF_FlujosBean selectedPF_Flujo = null;
	List<PF_GruposBean> selectedPF_FlujoGroupsToDelete = null;
	private Map<String,String> tipoFlujo = null;
	private String environment = null;
	private List<FirmanteBean> listOfSigners = null;
	private String[] selectedPersons = null;
	private Map<Integer,String> searchOptions = null;
//	private String selected;
	

	public PF_FlujosManagedBean() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		environment = ms.getEnvironmentSelected();
		pffdao =  new PF_FlujosDAO();
	}
	/*
	 *  Tramites
	 */
	public void findAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		int id = 0;
		String description = null;
		if (!ms.getSearchOption().isEmpty()) {
			id = Integer.valueOf(ms.getSearchOption());
		}
		if (!ms.getSearchOption2().isEmpty()) {
			description = ms.getSearchOption2();
		}
		listOf = getListaFlujosFirma(id,description);
	}
	public void newAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		DateUtils dUtils = new DateUtils();
		String actualDate = dUtils.fecha("4");
		ms.setMenuEnabledForm("Ed");
		selectedPF_Flujo = new PF_FlujosBean();
		selectedPF_Flujo.setFlujo_id(0);
		selectedPF_Flujo.setOrdenActivo(1);
		selectedPF_Flujo.setTipoFlujo(2);
		try {
			selectedPF_Flujo.setFechaCreacion(new Timestamp(new SimpleDateFormat("dd/MM/yy").parse(actualDate).getTime()));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		addNewSigner();
		selectedPF_FlujoGroupsToDelete = null;
	}
	
	public void editAction(Object idCircuito) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("Ed");
		/*
		 * guardo los parámetros de búsqueda que tengo en PF_FlujosManagedBean para recuperarlos después de que los use CatalogoFirmantesPersonasManagedBean
		 */
		if (null == searchOptions)
			searchOptions = new HashMap<Integer,String>();
		searchOptions.put(1, ms.getSearchOption());
		searchOptions.put(2, ms.getSearchOption2());
		searchOptions.put(3, ms.getSearchOption3());
		searchOptions.put(4, ms.getSearchOption4());
		ms.setSearchOption("");
		ms.setSearchOption2("");
		ms.setSearchOption3("");
		ms.setSearchOption4("");
		selectedPF_Flujo = getSelectedPF_Flujo((int)idCircuito);
		selectedPF_FlujoGroupsToDelete = null;
	}

	public void saveAction() {
		try {
			if (preValidate()) {
				pffdao.saveFlujo(selectedPF_Flujo,selectedPF_FlujoGroupsToDelete);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado.", ""));
			}
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha podido guardar los cambios en el Circuito.", ""));
		}
	}
	public void deleteAction() {}
	public void deleteAction(int id_flujo) {
		pffdao.deleteFlujo(id_flujo);
		selectedPF_Flujo = null;
		findAction();
	}
	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
		/*
		 * recupero los parámetros de búsqueda que ha cambiado la CatalogoFirmantesPersonasManagedBean
		 */
		if (null!=searchOptions) {
			ms.setSearchOption(searchOptions.get(1));
			ms.setSearchOption2(searchOptions.get(2));
			ms.setSearchOption3(searchOptions.get(3));
			ms.setSearchOption4(searchOptions.get(4));
			selectedPF_Flujo = null;
			ELContext context = FacesContext.getCurrentInstance().getELContext();
			CatalogoFirmantesPersonasManagedBean bean = (CatalogoFirmantesPersonasManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "catalogoFirmantesPersonasMB");
			bean.clearTemp();
			findAction();
		}
	}
	public void deleteAction(Object identification) {

	}
	/*
	 * Validación previa de los campos antes de ser persistidos en Base de Datos.
	 */
	private boolean preValidate(){
		boolean result = true;
		int[] firmantes = new int[selectedPF_Flujo.getListOfGroups().size()];
		int id=0;
		// Verifica si la Descripción es null o está vacía
		if (null!=selectedPF_Flujo.getDescripcion()&&!selectedPF_Flujo.getDescripcion().isEmpty()&&!selectedPF_Flujo.getDescripcion().equals("")) {
			// Revisión de los firmantes.
			for (PF_GruposBean pf_groups : selectedPF_Flujo.getListOfGroups()) {
				// Revisa que todos los firmantes tienen la Acción de Firma/Validación correcta.
				if(pf_groups.getID_TIPO_GRUPO()==0) {
					result=false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido guardar los cambios en el Circuito.", "Revise los parámetros del firmante "+pf_groups.getFirmante().getNombre()));
				}
				firmantes[id]=pf_groups.getFirmante().getIdFirmante();
				id++;
			}
			if(result) {
				if(firmantes.length>1) {
					for(int x=0;x<firmantes.length;x++) {
						for(int y=x+1;y<firmantes.length;y++) {
							if(firmantes[x]==firmantes[y]) {
								result=false;
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido guardar los cambios en el Circuito.", "No puede existir el mismo firmante en dos pasos del Circuito"));
							}
						}
					}
				}
			}
		}else {
			result=false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido guardar los cambios en el Circuito.", "El Circuito debe tener una descripción."));
		}
		return result;
	}
	
	/*
	 * Devuelve la descripción de un circuito.
	 * Usado desde la Gestión de Procedimientos.
	 */
	public String getDescription(int idCircuito) {
		PF_FlujosDAO fd = new PF_FlujosDAO();
		return fd.getFlujoDescripcion(idCircuito);
	}

	/*
	 * Devuleve el listado de Flujos en base a .....
	 */
	private List<PF_FlujosBean> getListaFlujosFirma(int id, String description) {
		/*Devuelve el listado de todos los circuitos al no definir ningún criterio de búsqueda*/
		return pffdao.getListaFlujosFirma(id ,0, 0, description) ;
	}

	/*
	 * Método de cambio de orden de firmantes
	 */
	public void changeOrderOfSigners(int actualPosition, String UpOrDown) {
		boolean done = false;
		List<PF_GruposBean> list = selectedPF_Flujo.getListOfGroups();
		for (int i = 0;i<list.size();i++) {
			if (UpOrDown.equals("UP")) {
				if(list.get(i).getORDEN()==actualPosition  && !done) {
					list.get(i-1).setORDEN(actualPosition);
					list.get(i).setORDEN(actualPosition-1);
					done = true;
				}
			}else if(UpOrDown.equals("DOWN")) {
				if(list.get(i).getORDEN()==actualPosition && !done) {
					list.get(i+1).setORDEN(actualPosition);
					list.get(i).setORDEN(actualPosition+1);
					done = true;
				}
			}
		}
		Collections.sort(list, new Comparator<PF_GruposBean>() {
			@Override
			public int compare (PF_GruposBean a, PF_GruposBean b) {
				return String.valueOf(a.getORDEN()).compareTo(String.valueOf(b.getORDEN()));
			}
		});
		selectedPF_Flujo.setListOfGroups(list);
	}
	public boolean orderButtonsRendered(int orden, String function) {
		boolean render = true;
		int numOfSigners = selectedPF_Flujo.getListOfGroups().size();
		if (function.equals("DOWN")) 
			if (orden == numOfSigners)
				render = false;
		if (function.equals("UP"))
			if(orden == 1)
				render = false;
		
		return render;
	}

	/*
	 * Método para añadir un firmante al listado de firmantes.
	 */
	public void addNewSigner() {
		List<PF_GruposBean> list = selectedPF_Flujo.getListOfGroups();
		if (null==list)
			list = new ArrayList<>();
		if (null==listOfSigners)
			getListOfSigners();
		PF_GruposBean newSigner = new PF_GruposBean();
		newSigner.setFirmante(listOfSigners.get(0));//Carga del primer firmante de la lista de firmantes por defecto.
		newSigner.setID_GRUPO(0);
		newSigner.setFIRMANTES_REQUERIDOS(1);
		int id = list.size()+1;
		newSigner.setORDEN(id);
		newSigner.setID_FLUJO(selectedPF_Flujo.getFlujo_id());
		if (newSigner.getFirmante().getEsValidadorCheckBox()) {
			newSigner.setID_TIPO_GRUPO(1);
		}else {
			newSigner.setID_TIPO_GRUPO(2);
		}
		newSigner.setCERRADO(0);
		list.add(newSigner);
		selectedPF_Flujo.setListOfGroups(list);
	}

	public void deleteSigner(PF_GruposBean idGrupo) {
		List<PF_GruposBean> list = selectedPF_Flujo.getListOfGroups();
		List<PF_GruposBean> list2 = new ArrayList<PF_GruposBean>();
		if (list.size()==1) {
			list2 = list;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se puede eliminar el firmante.", "Un Circuito de firma ha de tener un firmante como mínimo."));
		}else {
			int orden = 1;
			for (PF_GruposBean pf_GruposBean : list) {
				if(pf_GruposBean==idGrupo) {
					if (null==selectedPF_FlujoGroupsToDelete) {
						selectedPF_FlujoGroupsToDelete = new ArrayList<PF_GruposBean>();
					}
					selectedPF_FlujoGroupsToDelete.add(pf_GruposBean);
				}else {
					pf_GruposBean.setORDEN(orden);
					orden++;
					list2.add(pf_GruposBean);
				}
			}
		}
		selectedPF_Flujo.setListOfGroups(list2);
	}
	
	public void updateSigner(ValueChangeEvent e) {
		if (null!=e.getNewValue()) {
			int orden = (Integer) ((UIInput) e.getSource()).getAttributes().get("ordenAttribute");
			FirmanteBean updatedSigner = null;
			int valueNew = (Integer)e.getNewValue();
			for (FirmanteBean firmante : listOfSigners) {
				if (firmante.getIdFirmante()==valueNew) {
					updatedSigner = firmante;
				}
			}
			int valueOld = (Integer)e.getOldValue();
			List<PF_GruposBean> list = selectedPF_Flujo.getListOfGroups();
			selectedPF_Flujo.setListOfGroups(null);
			List<PF_GruposBean> list2 = new ArrayList<>();
			for (PF_GruposBean pf_GruposBean : list) {
				if(pf_GruposBean.getFirmante().getIdFirmante()==valueOld && pf_GruposBean.getORDEN()==orden) {
					pf_GruposBean.setFirmante(updatedSigner);
				}
				list2.add(pf_GruposBean);
			}
			selectedPF_Flujo.setListOfGroups(list2);
		}
	}
	
	/**
	 * GESTIÓN DE PERSONAS QUE PUEDEN ENVIAR DOCUMENTOS A UN CIRCUITO.
	 * 
	 * 
	 */
	public void addPersonasAction() {
		List<String> toSend = new ArrayList<>();
		for (String carLicense : selectedPersons) {
			boolean exist = false;
			if (null != selectedPF_Flujo.getListOfPersonas()) {
				for (LDAPPersonaBean item : selectedPF_Flujo.getListOfPersonas()) {
					if (item.getCarLicense().equals(carLicense)) {
						exist=true;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se puede añadir al firmante." + carLicense, "No puede estar repetido."));
					}
				}
			}
			if (!exist)
				toSend.add(carLicense);
		}
		if (!toSend.isEmpty())
			pffdao.setAuthorizedPerson(selectedPF_Flujo ,toSend);
				
		selectedPF_Flujo = pffdao.findFlujoFirma(0, 0, "", selectedPF_Flujo.getFlujo_id());
	}
	
	public void removePersonasAction(String carLicense) {
		pffdao.deleteAuthorizedPerson(selectedPF_Flujo ,carLicense);
		selectedPF_Flujo = pffdao.findFlujoFirma(0, 0, "", selectedPF_Flujo.getFlujo_id());
	}
	
	
	/*
	 * Metodo valueChangeListener que recoge el cambio y llama a la búsqueda para recuperar el objeto.
	 */
//	public void flujoChanged(ValueChangeEvent e) {
//		if (null!=e.getNewValue()) {
//			int value = Integer.valueOf((String) e.getNewValue());
////			if (value!=0)
////				selectedPF_Flujo = getSelectedPF_Flujo(value);
////			else
////				selectedPF_Flujo = null;
//		}
//	}
	/*
	 * Método que devuelve el objeto en base a su id contenido en el listado.
	 */
	private PF_FlujosBean getSelectedPF_Flujo(Integer id) {
		for (PF_FlujosBean pf_FlujosBean : listOf) {
			if(id.equals(pf_FlujosBean.getFlujo_id()))
				return pf_FlujosBean;
		}
		return null;
	}



	/*
	 * Lista de tipos de flujos
	 */
	public Map<String,String> getTipoFlujo() {
		if (null==this.tipoFlujo)
			this.tipoFlujo = pffdao.listOfTipoFlujo();
		return tipoFlujo;
	}


	public void setTipoFlujo(Map<String,String> tipoFlujo) {
		this.tipoFlujo = tipoFlujo;
	}
	/*
	 * Descripción de la lista de tipos de flujos.
	 */
	public String descriptionOfTipoFlujo() {
		if (null!=this.selectedPF_Flujo) {
			if(null==this.tipoFlujo) {
				getTipoFlujo();
			}
			return this.tipoFlujo.get(String.valueOf(selectedPF_Flujo.getTipoFlujo()));
		}else {
			return null;
		}
	}

	/*
	 * Getters and Setters
	 */

	/*  Lista de todos los Flujos de Firma disponibles. */
	public List<PF_FlujosBean> getListOf() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		/*
		 * En caso de cambiar de entorno PRE - PRO borrar la lista de resultados para que no se muestren al cargar la página.
		 */
		if (!ms.getEnvironmentSelected().equals(environment)) {
			environment = ms.getEnvironmentSelected();
			listOf = null;
		}
		return listOf;
	}

	public void setListOf(List<PF_FlujosBean> listOf) {
		this.listOf = listOf;
	}

	public PF_FlujosBean getSelectedPF_Flujo() {
		return selectedPF_Flujo;
	}

	public void setSelectedPF_Flujo(PF_FlujosBean selectedPF_Flujo) {
		this.selectedPF_Flujo = selectedPF_Flujo;
	}

//		public String getSelected() {
//			return selected;
//		}
//	
//		public void setSelected(String selected) {
//			this.selected = selected;
//		}

	public List<FirmanteBean> getListOfSigners() {
		// Carga el listado de Firmantes en caso que esté a null
		//if (null==listOfSigners) retiré el if porque sino co carga los cambios....
		listOfSigners = new CatalogoFirmantesManagedBean().findSigners("","","","");
		return listOfSigners;
	}
	public void setListOfSigners(List<FirmanteBean> listOfSigners) {
		this.listOfSigners = listOfSigners;
	}

	//	public List<PF_GruposBean> getListOfGoups() {
	//		return listOfGoups;
	//	}
	//
	//	public void setListOfGoups(List<PF_GruposBean> listOfGoups) {
	//		this.listOfGoups = listOfGoups;
	//	}
	//
	//	public List<PF_GrupoPersonasBean> getListOfGroupPersons() {
	//		return listOfGroupPersons;
	//	}
	//
	//	public void setListOfGroupPersons(List<PF_GrupoPersonasBean> listOfGroupPersons) {
	//		this.listOfGroupPersons = listOfGroupPersons;
	//	}
	
	public String[] getSelectedPersons() {
		return selectedPersons;
	}


	public void setSelectedPersons(String[] selectedPersons) {
		this.selectedPersons = selectedPersons;
	}

}
