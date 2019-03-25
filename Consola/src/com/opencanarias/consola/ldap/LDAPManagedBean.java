package com.opencanarias.consola.ldap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.jboss.logging.Logger;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.reports.LDAPReports;

public class LDAPManagedBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LDAPManagedBean.class);
	MenuSessionOption ms = null;
	LDAPManager ldap = new LDAPManager();
	List<LDAPGruposAlfrescoBean> grouplist = null;
	List<LDAPPersonaBean> userList = null;
	boolean horizontal = true;
	Map<String, Boolean> viewGroup = null;
	int viewGroupLevel = 1;
	int viewFontSize = 4;
	private String environment;

	public LDAPManagedBean() {

	}

	public void exportToXML(String option) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		OutputStream out = response.getOutputStream();
		response.reset();
		response.setContentType("text/xml");
		if (option.equals("ALFRESCO"))
			response.addHeader("Content-Disposition", "attachment; filename=APSCT_ALF_GROUPS.xml");
		if (option.equals("USERS"))
			response.addHeader("Content-Disposition", "attachment; filename=APSCT_USERS.xml");
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			LDAPReports ldapReports = new LDAPReports();
			trans.transform(ldapReports.exportToExcelGenerateXML(option, grouplist, userList, viewGroup, viewFontSize), new StreamResult(out));
		} catch (Exception e) {

		}
		out.flush();
		try {
			if (out != null) {
				out.close();
			}
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {

		}
	}

	public void fontSize(String value) {
		if (value.equals("add")) {
			if (viewFontSize < 8) {
				viewFontSize++;
			}
		} else {
			if (viewFontSize > 1) {
				viewFontSize--;
			}
		}
	}

	public String fontSize() {
		String value = null;
		if (viewFontSize == 1)
			value = " h8 ";
		else if (viewFontSize == 2)
			value = " h7 ";
		else if (viewFontSize == 3)
			value = " h6 ";
		else if (viewFontSize == 4)
			value = " h5 ";
		else if (viewFontSize == 5)
			value = " h4 ";
		else if (viewFontSize == 6)
			value = " h3 ";
		else if (viewFontSize == 7)
			value = " h2 ";
		else if (viewFontSize == 8)
			value = "h1";
		return value;
	}

	public String fontPercent() {
		String value = null;
		if (viewFontSize == 1)
			value = "width:10%";
		else if (viewFontSize == 2)
			value = "width:23%";
		else if (viewFontSize == 3)
			value = "width:36%";
		else if (viewFontSize == 4)
			value = "width:50%";
		else if (viewFontSize == 5)
			value = "width:63%";
		else if (viewFontSize == 6)
			value = "width:75%";
		else if (viewFontSize == 7)
			value = "width:88%";
		else if (viewFontSize == 8)
			value = "width:100%";
		return value;
	}

	public void groupSelected(String group) {
		boolean isChecked = (boolean) viewGroup.get(group);
		if (isChecked) {
			viewGroup.put(group, false);
		} else {
			viewGroup.put(group, true);
		}
	}

	public boolean checkGroupSelected(String group) {
		boolean isChecked = false;
		if ((boolean) viewGroup.get(group)) {
			isChecked = true;
		}
		return isChecked;
	}

	public String groupSelectedStyle(String group) {
		String style = null;
		if (viewGroup.get(group))
			style = "bg-light-grey"; // Color obtenido de la hoja de estilos de Bootstrap.
		else
			style = "";
		return style;
	}

	public void addViewGroupLevel(int level) {
		viewGroupLevel = level;
	}

	public String levelSelectedStyle(int level) {
		String style = null;
		if (viewGroupLevel == level)
			style = "bg-light-grey"; // Color obtenido de la hoja de estilos de Bootstrap.
		else
			style = "";
		return style;
	}

	public void orientationChange() {
		if (horizontal)
			horizontal = false;
		else
			horizontal = true;
	}

	public void getGroupData() {
		List<LDAPGruposAlfrescoBean> listHierarchy = new ArrayList<LDAPGruposAlfrescoBean>();
		List<LDAPGruposAlfrescoBean> listHierarchyRest = new ArrayList<LDAPGruposAlfrescoBean>();
		grouplist = new ArrayList<LDAPGruposAlfrescoBean>();
		try {
			grouplist = ldap.findLDAPAlfrescoGroups("(cn>=ALF)");
		} catch (ClassNotFoundException | IOException | SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido obtener los datos de grupos del LDAP.", ""));
			logger.error("No se ha podido obtener los datos de grupos del LDAP.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido obtener los datos de grupos del LDAP.", "Descripción"));
			logger.error("No se ha podido obtener los datos de grupos del LDAP.");
			e.printStackTrace();
		}
		for (LDAPGruposAlfrescoBean map : grouplist) {
			int cont = 0;
			if (null != map.getMember() && !map.getMember().isEmpty())
				for (String alf : map.getMember()) {
					if (alf.contains("ALF"))
						cont++;
				}
			if (cont == 0)
				listHierarchy.add(map);
			else
				listHierarchyRest.add(map);

		}
		List<LDAPGruposAlfrescoBean> level2 = null;
		List<LDAPGruposAlfrescoBean> level3 = null;
		List<LDAPGruposAlfrescoBean> level4 = null;
		List<LDAPGruposAlfrescoBean> level5 = null;
		int i = 0;
		for (LDAPGruposAlfrescoBean tmp : listHierarchy) {
			level2 = new ArrayList<>();
			int ii = 0;
			for (LDAPGruposAlfrescoBean tmp2 : listHierarchyRest) {
				int cont2 = 0;
				for (String alf : tmp2.getMember()) {
					if (alf.contains(tmp.getCn()))
						cont2++;
				}
				if (cont2 > 0) {
					level2.add(tmp2);

					level3 = new ArrayList<>();
					int iii = 0;
					for (LDAPGruposAlfrescoBean tmp3 : listHierarchyRest) {
						int cont3 = 0;
						for (String alf : tmp3.getMember()) {
							if (alf.contains(tmp2.getCn()))
								cont3++;
						}
						if (cont3 > 0) {
							level3.add(tmp3);
							level4 = new ArrayList<>();
							int iiii = 0;
							for (LDAPGruposAlfrescoBean tmp4 : listHierarchyRest) {
								int cont4 = 0;
								for (String alf : tmp4.getMember()) {
									if (alf.contains(tmp3.getCn()))
										cont4++;
								}
								if (cont4 > 0) {
									level4.add(tmp4);

									level5 = new ArrayList<>();
									for (LDAPGruposAlfrescoBean tmp5 : listHierarchyRest) {
										int cont5 = 0;
										for (String alf : tmp5.getMember()) {
											if (alf.contains(tmp4.getCn()))
												cont5++;
										}
										if (cont5 > 0) {
											level5.add(tmp5);

										}
									}
									level4.get(iiii).setList(level5);
									iiii++;

								}
							}
							level3.get(iii).setList(level4);
							iii++;
						}
					}
					level2.get(ii).setList(level3);
					ii++;
				}
			}
			listHierarchy.get(i).setList(level2);
			i++;
		}
		grouplist = listHierarchy;
	}

	/**
	 * METODO USADO COMO PASO PREVIO A LA BÚSQUEDA DE USUARIOS.
	private void getUserData() {
		try {
			userList = ldap.findLDAPUsers("(businessCategory>=dpt)");
		} catch (ClassNotFoundException | IOException | SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido obtener los datos de usuario del LDAP.", ""));
			logger.error("No se ha podido obtener los datos de usuario del LDAP.");
			e.printStackTrace();
		}

	}
	 */

	public void findUsers() {
		if (null==ms)
			ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");

		List<LDAPPersonaBean> userList = null;
		this.userList = new ArrayList<>();
		try {
			userList = ldap.findLDAPUsers("(businessCategory>=dpt)");
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		boolean added = false;
		for (LDAPPersonaBean ldapPersonaBean : userList) {
			added = false;
			if (ldapPersonaBean.getCn().toUpperCase().contains(ms.getSearchOption().toUpperCase())) {
				if(ldapPersonaBean.getCarLicense().toUpperCase().contains(ms.getSearchOption4().toUpperCase())) {
					if ((null == ms.getSearchOption2()) && (null == ms.getSearchOption3())) {
						this.userList.add(ldapPersonaBean);
						added=true;
					}else if(null!=ms.getSearchOption2() && null == ms.getSearchOption3()) {
						if (!added && !ldapPersonaBean.getBussinessCategory().isEmpty() && itHaveBussinesCategory(ldapPersonaBean.getBussinessCategory())) {
							this.userList.add(ldapPersonaBean);
							added=true;
						}
					}else if(null==ms.getSearchOption2() && null != ms.getSearchOption3()) {
						if (!added && null!=ldapPersonaBean.getMemberOf() && !ldapPersonaBean.getMemberOf().isEmpty() && itHaveMemberOd(ldapPersonaBean.getMemberOf())) {
							this.userList.add(ldapPersonaBean);
							added=true;
						}
					}else if(null!=ms.getSearchOption2() && null != ms.getSearchOption3()) {
						if (!added && null!=ldapPersonaBean.getBussinessCategory() && !ldapPersonaBean.getBussinessCategory().isEmpty() && null!=ldapPersonaBean.getMemberOf() && !ldapPersonaBean.getMemberOf().isEmpty() && itHaveMemberOd(ldapPersonaBean.getMemberOf()) && itHaveBussinesCategory(ldapPersonaBean.getBussinessCategory())) {
							this.userList.add(ldapPersonaBean);
							added=true;
						}
					}
				}

			}
		}
	}

	private boolean itHaveBussinesCategory(List<String> business) {
		boolean result = false;
		for (String businessCategory : business) {
			if(ms.getSearchOption2().equals(businessCategory)) {
				result=true;
			}
		}
		return result;
	}
	private boolean itHaveMemberOd(List<String> memberOf) {
		boolean result = false;
		for (String member : memberOf) {
			String CN = member.substring(member.indexOf("CN=")+3,member.indexOf(",")); 
			if(CN.toUpperCase().equals(ms.getSearchOption3().toUpperCase())) {
				result=true;
			}
		}
		return result;
	}

	public List<LDAPGruposAlfrescoBean> getGroupList() {
		if (null==ms)
			ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");

		if (null!=environment) {
			if (!environment.equals(ms.getEnvironmentSelected())) {
				environment = ms.getEnvironmentSelected();
				grouplist = null;
			}
		}else {
			environment = ms.getEnvironmentSelected();
		}
		if (null == grouplist) {
			getGroupData();
			viewGroup = new HashMap<String, Boolean>();
			for (LDAPGruposAlfrescoBean ldapGruposAlfrescoBean : grouplist) {
				viewGroup.put(ldapGruposAlfrescoBean.getCn(), false);
			}
		}
		return grouplist;
	}

	public void setGroupList(List<LDAPGruposAlfrescoBean> lista) {
		this.grouplist = lista;
	}

	public List<LDAPPersonaBean> getUserList() {
		if (null==ms)
			ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");

		if (null!=environment) {
			if (!environment.equals(ms.getEnvironmentSelected())) {
				environment = ms.getEnvironmentSelected();
				userList = null;
			}
		}else {
			environment = ms.getEnvironmentSelected();
		}
		return userList;
	}

	public void setUserList(List<LDAPPersonaBean> userList) {
		this.userList = userList;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public int getViewGroupLevel() {
		return viewGroupLevel;
	}
}
