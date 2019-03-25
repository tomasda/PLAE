/**
 * Objeto que lee los datos de los usuarios del LDAP
 */
package com.opencanarias.consola.ldap;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tomás Delgado
 *
 */
public class LDAPPersonaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idPersona;
	private String cn;
	private String name;
	private String mail;
	private String postalCode;
	private String carLicense;
	private List<String> bussinessCategory;
	private List<String> memberOf;
	private String sAMAccountName;
	private String title;
	private String department;
	private String description;
	private String telephoneNumber;

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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}

	public List<String> getBussinessCategory() {
		return bussinessCategory;
	}

	public void setBussinessCategory(List<String> bussinessCategory) {
		this.bussinessCategory = bussinessCategory;
	}

	public List<String> getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(List<String> memberOf) {
		this.memberOf = memberOf;
	}

	@Override
	public String toString() {
		return "PersonaBean [cn=" + cn + ", name=" + name + ", mail=" + mail + ", postalCode=" + postalCode + ", carLicense=" + carLicense
				+ ", bussinessCategory=" + bussinessCategory + ", memberOf=" + memberOf + "]";
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getsAMAccountName() {
		return sAMAccountName;
	}

	public void setsAMAccountName(String sAMAcountName) {
		this.sAMAccountName = sAMAcountName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	// public static Comparator<PersonaBean> PersonaNameComparator = new Comparator<PersonaBean>() {
	// public int compare(PersonaBean a, PersonaBean b) {
	// String aName = a.getCn().toUpperCase();
	// String bName = b.getCn().toUpperCase();
	// return aName.compareTo(bName);
	// }
	// };

}
