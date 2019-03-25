package com.opencanarias.consola.sede.tercerosrepresentantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.registro.terceros.TercerosBean;
import com.opencanarias.consola.registro.terceros.TercerosDAO;

//@ManagedBean
//@SessionScoped
public class TercerosRepresentantesManagedBean implements Serializable,ManagedBeanInterface {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TercerosRepresentantesManagedBean.class);

	private List<TercerosRepresentantesListBean> tercerosRepresentantesList;
	private List<TercerosBean> listTercerosToAdd;
	private TercerosBean interesado;
	private TercerosBean representante;
	private List<ThirdPartyReportBean> thirdParty;
	private String environment = "";

	public TercerosRepresentantesManagedBean() {
		super();
		tercerosRepresentantesList=null;
		listTercerosToAdd = null;
		interesado = null;
		representante = null;
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		environment=ms.getEnvironmentSelected();
//		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
//		if (!environment.equals(ms.getEnvironmentSelected())) {
//			thirdParty = null;
//			environment=ms.getEnvironmentSelected();
//		}
	}
	public void TercerosRepresentantesStartMenu() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (!environment.equals(ms.getEnvironmentSelected())) {
			thirdParty = null;
			environment=ms.getEnvironmentSelected();
		}
		if (ms.getMenuEnabledForm().equals("I")) {
			tercerosRepresentantesList=null;
			listTercerosToAdd = null;
			interesado = null;
			representante = null;
		}
	}	
	public void newAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (!environment.equals(ms.getEnvironmentSelected())) {
			thirdParty = null;
			environment=ms.getEnvironmentSelected();
		}
		ms.setMenuEnabledForm("N");
		ms.setSearchOption("") ;
		ms.setSearchOption2("");
		listTercerosToAdd = null;
		interesado = null;
		representante = null;
	}

	public void findAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (!environment.equals(ms.getEnvironmentSelected())) {
			thirdParty = null;
			environment=ms.getEnvironmentSelected();
		}
		ms.setMenuEnabledForm("B");
		TercerosRepresentantesDAO trd = new TercerosRepresentantesDAO();
		tercerosRepresentantesList = trd.tercerosRepresentantesList(ms.getSearchOption(), ms.getSearchOption2());
		logger.info("Búsqueda de terceros " +ms.getSearchOption()+" "+ ms.getSearchOption2());
	}

	public void saveAction() {
		TercerosRepresentantesDAO trd = new TercerosRepresentantesDAO();
		TercerosRepresentantesBean trb = new TercerosRepresentantesBean();
		trb.setINTERESADO_ID(interesado.getID());
		trb.setREPRESENTANTE_ID(representante.getID());
		if (trd.exists(trb)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe una relación previa con los mismos parámetros.", " No se realiza la operación. "));
		}else {
			if (trd.save(trb)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado la Relación satisfactoriamente.", ""));
				findAction();
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido guardar la relación", " Consulte con el administrador. "));
			}
		}
	}

	public List<NumExpBean> reportExp(String tercero) {
		List<NumExpBean> list = new ArrayList<>();
		if (!tercero.isEmpty()) {
			if (null==thirdParty) {
				thirdParty =  getThirtPArtyData();
				System.out.println("ThirdParty Size =" +thirdParty.size());
			}
			Calendar calendar = Calendar.getInstance();
			int cont = 0;
			int date = 0;
			HashMap<Integer, Integer> registro = new HashMap<>();
			for (ThirdPartyReportBean third : thirdParty) {
				if (third.getCommonName_().equals(tercero)) {
					cont++;
					calendar.setTimeInMillis(third.getStartDate().getTime());
					date = calendar.get(Calendar.YEAR);
					if (registro.isEmpty()) {
						registro.put(date, 1);
					}else {
						Object obj = registro.get(date);
						if (null != obj) {
							int var = registro.get(date);
							var++;
							registro.put(date, var);
						}else {
							registro.put(date, 1);
						}
					}
				}
			}
			if (!registro.isEmpty()) {
				NumExpBean num = new NumExpBean();
				num.setKey_(0);
				num.setValue_(cont);
				list.add(num);
				Map<Integer,Integer> treeMap = new TreeMap<>(registro);
				for(Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
					num = new NumExpBean();
					num.setKey_(entry.getKey());
					num.setValue_(entry.getValue());
					list.add(num);
				}
			}
		}
		return list;
	}


	public void editAction(Object edit) {
		/*
		 * Función no utilizada en este momento.
		 */
	}


	public void findTerceros(){
		listTercerosToAdd = new ArrayList<TercerosBean>();
		TercerosDAO td = new TercerosDAO();
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (ms.getSearchOption().isEmpty() && ms.getSearchOption2().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Los campos de búsqueda no pueden estar vacíos.", ""));
		}else {
			listTercerosToAdd = td.getTercerosList(ms.getSearchOption(), ms.getSearchOption2(), "", "","03");
		}
	}

	public void asignInteresado(int iD) {
		TercerosDAO td = new TercerosDAO();
		interesado = td.getTercero(iD);
	}
	public void asignRepresentante(int iD) {
		TercerosDAO td = new TercerosDAO();
		representante = td.getTercero(iD);
	}

	public String name(boolean tipo) {
		String terceroName = null;
		String iD = null;
		if (tipo) {
			iD =interesado.getTIPO_IDENTIFICACION_ID();
		}else {
			iD =representante.getTIPO_IDENTIFICACION_ID();
		}
		switch (iD) {
		case "N":
		case "E":
		case "O":
		case "P":
			if (tipo) {
				terceroName = interesado.getNOMBRE()+" "+interesado.getAPELLIDO_1()+" "+interesado.getAPELLIDO_2();
			}else {
				terceroName = representante.getNOMBRE()+" "+representante.getAPELLIDO_1()+" "+representante.getAPELLIDO_2();
			}
			break;
		case "C":
		case "X":
			if (tipo) {
				terceroName = interesado.getRAZON_SOCIAL();
			}else {
				terceroName = representante.getRAZON_SOCIAL();
			}
			break;
		}
		return terceroName;
	}

	public void deleteAction(Object dato) {
		System.out.println("Eliminar " + (int)dato);
		TercerosRepresentantesDAO trd = new TercerosRepresentantesDAO();
		if (trd.delete((int)dato)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha eliminado la Relación satisfactoriamente.", ""));
			MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
			ms.setSearchOption("") ;
			ms.setSearchOption2("");
			findAction();
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido elimnar la relación", " Consulte con el administrador. "));
		}

	}

	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
	}

	private List<ThirdPartyReportBean> getThirtPArtyData() {
		TercerosDAO td = new TercerosDAO();
		List<ThirdPartyReportBean> list = td.getThirdPartyData();
		return list;
	}



	public TercerosBean getInteresado() {
		return interesado;
	}

	public void setInteresado(TercerosBean interesado) {
		this.interesado = interesado;
	}

	public TercerosBean getRepresentante() {
		return representante;
	}

	public void setRepresentante(TercerosBean representante) {
		this.representante = representante;
	}

	public List<TercerosBean> getListTercerosToAdd() {
		return listTercerosToAdd;
	}

	public void setListTercerosToAdd(List<TercerosBean> listTercerosToAdd) {
		this.listTercerosToAdd = listTercerosToAdd;
	}

	public List<TercerosRepresentantesListBean> getTercerosRepresentantesList() {
		return tercerosRepresentantesList;
	}

	public void setTercerosRepresentantesList(List<TercerosRepresentantesListBean> tercerosRepresentantesList) {
		this.tercerosRepresentantesList = tercerosRepresentantesList;
	}
}
