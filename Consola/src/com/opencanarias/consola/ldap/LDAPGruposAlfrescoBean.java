/**
 * 
 */
package com.opencanarias.consola.ldap;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tomás Delgado Acosta
 *
 */
public class LDAPGruposAlfrescoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idGrupo;
	private String cn;
	private String name;
	private String sAMAccountName;
	private String description;
	private List<String> member;
	private List<LDAPGruposAlfrescoBean> list;
	
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getsAMAccountName() {
		return sAMAccountName;
	}
	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	public List<String> getMember() {
		return member;
	}
	public void setMember(List<String> member) {
		this.member = member;
	}
	public List<LDAPGruposAlfrescoBean> getList() {
		return list;
	}
	public void setList(List<LDAPGruposAlfrescoBean> list) {
		this.list = list;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "LDAPGruposAlfrescoBean [idGrupo=" + idGrupo + ", cn=" + cn + ", name=" + name + ", sAMAccountName=" + sAMAccountName + ", description=" + description + ", member=" + member + ", list=" + list + "]";
	}
	
}
