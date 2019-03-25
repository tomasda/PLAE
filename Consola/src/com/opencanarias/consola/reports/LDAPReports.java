package com.opencanarias.consola.reports;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.opencanarias.consola.ldap.LDAPGruposAlfrescoBean;
import com.opencanarias.consola.ldap.LDAPPersonaBean;

public class LDAPReports {

	public DOMSource exportToExcelGenerateXML(String which,List<LDAPGruposAlfrescoBean> grouplist,List<LDAPPersonaBean> userList,Map<String, Boolean> viewGroup, int viewGroupLevel ) {
		DOMSource source = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root Element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("APSCT");
			Attr attr = doc.createAttribute("Description");
			attr.setValue("Autoridad Portuaria de Santa Cruz de Tenerife");
			rootElement.setAttributeNode(attr);
			doc.appendChild(rootElement);
			switch(which) {
			case "ALFRESCO":
				for (LDAPGruposAlfrescoBean ldapGruposAlfrescoBean : grouplist) {
					if (viewGroup.get(ldapGruposAlfrescoBean.getCn())) {
						rootElement.appendChild(getNode(doc, rootElement, ldapGruposAlfrescoBean, 1,viewGroupLevel));
					}
				}
				break;
			case "USERS":
				for (LDAPPersonaBean ldapPersonasBean : userList) {
					rootElement.appendChild(getUserNode(doc, rootElement, ldapPersonasBean));
				}
				break;
			}


			source = new DOMSource(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return source;
	}

	private Node getNode(Document doc, Element element, LDAPGruposAlfrescoBean list, int level, int viewGroupLevel) {
		Element node = doc.createElement(list.getCn());

		Attr attr = doc.createAttribute("level");
		attr.setValue(String.valueOf(level));
		node.setAttributeNode(attr);

		Element cn = doc.createElement("cn");
		cn.appendChild(doc.createTextNode(list.getCn()));
		node.appendChild(cn);

		String desc = list.getDescription();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element description = doc.createElement("description");
		description.appendChild(doc.createTextNode(desc));
		node.appendChild(description);

		desc = list.getsAMAccountName();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element SAMAcountName = doc.createElement("SAMAcountName");
		SAMAcountName.appendChild(doc.createTextNode(desc));
		node.appendChild(SAMAcountName);

		desc = list.getName();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element Name = doc.createElement("Name");
		Name.appendChild(doc.createTextNode(desc));
		node.appendChild(Name);

		if (viewGroupLevel > level) {
			Element Content = doc.createElement("Content");
			if (null != list.getList()) {
				int newlevel = level;
				newlevel++;
				for (LDAPGruposAlfrescoBean ldapGruposAlfrescoBean : list.getList()) {
					Content.appendChild(getNode(doc, node, ldapGruposAlfrescoBean, newlevel,viewGroupLevel));
				}
			}
			node.appendChild(Content);
		}
		return node;
	}

	private Node getUserNode(Document doc, Element element, LDAPPersonaBean list) {
		Element node = doc.createElement("Usuario");

		Element cn = doc.createElement("cn");
		cn.appendChild(doc.createTextNode(list.getCn()));
		node.appendChild(cn);

		String desc = list.getCarLicense();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element carLicense = doc.createElement("carLicense");
		carLicense.appendChild(doc.createTextNode(desc));
		node.appendChild(carLicense);

		desc = list.getName();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element Name = doc.createElement("Name");
		Name.appendChild(doc.createTextNode(desc));
		node.appendChild(Name);

		desc = list.getsAMAccountName();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element samaccount = doc.createElement("Cuenta");
		samaccount.appendChild(doc.createTextNode(desc));
		node.appendChild(samaccount);

		desc = list.getMail();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element Mail = doc.createElement("Mail");
		Mail.appendChild(doc.createTextNode(desc));
		node.appendChild(Mail);

		desc = list.getPostalCode();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element PostaCode = doc.createElement("PostaCode");
		PostaCode.appendChild(doc.createTextNode(desc));
		node.appendChild(PostaCode);

		desc = list.getDepartment();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element department = doc.createElement("Departamento");
		department.appendChild(doc.createTextNode(desc));
		node.appendChild(department);

		desc = list.getDescription();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element description = doc.createElement("Descripción");
		description.appendChild(doc.createTextNode(desc));
		node.appendChild(description);

		desc = list.getTitle();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element title = doc.createElement("Titulo");
		title.appendChild(doc.createTextNode(desc));
		node.appendChild(title);

		desc = list.getTelephoneNumber();
		if (null == desc || desc.isEmpty())
			desc = "";
		Element telephone = doc.createElement("Teléfono");
		telephone.appendChild(doc.createTextNode(desc));
		node.appendChild(telephone);

		if (null!=list.getBussinessCategory()) {
			Element Content = doc.createElement("BusinessCategory");
			if (null != list.getBussinessCategory()) {
				for (String businessCategory : list.getBussinessCategory()) {
					Content.appendChild(getStringNode(doc, node, businessCategory,"rol"));
				}
			}
			node.appendChild(Content);
		}
		if (null!=list.getMemberOf()) {
			Element Content = doc.createElement("MemberOf");
			if (null != list.getMemberOf()) {
				for (String businessCategory : list.getMemberOf()) {
					Content.appendChild(getStringNode(doc, node, businessCategory,"group"));
				}
			}
			node.appendChild(Content);
		}
		return node;
	}
	private Node getStringNode(Document doc, Element element, String list, String nodeDescription) {
		Element node = doc.createElement(nodeDescription);
		node.appendChild(doc.createTextNode(list));
		return node;
	}
}
