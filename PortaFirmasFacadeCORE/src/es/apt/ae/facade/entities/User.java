package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PF_USERS")
@NamedQueries(value = { 
		@NamedQuery(name=User.getUserPassword, query="SELECT s FROM User s WHERE username = :user AND password = :pass")
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String getUserPassword = "User.getPersonaById";
	
	@Id
	@Column(name = "CAR_LICENSE")
	private String carLicense;
	@Column(name ="CN" )
	private String cn;
	@Column(name ="GIVEN_NAME")
	private String givenName;
	@Column(name ="SN")
	private String sn;
	@Column(name ="USERNAME" )
	private String username;
	@Column(name ="EMAIL" )
	private String email;
	@Column(name ="BUSINESSCATEGORY" )
	private String bussinesCategory;
	@Column(name ="MEMBEROF" )
	private String memberOf;
	@Column(name ="DESCRIPCION" )
	private String description;
	@Column(name ="PASSWORD" )
	private String password;
	@Column(name ="ROLE")
	private String role;
	public String getCarLicense() {
		return carLicense;
	}
	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBussinesCategory() {
		return bussinesCategory;
	}
	public void setBussinesCategory(String bussinesCategory) {
		this.bussinesCategory = bussinesCategory;
	}
	public String getMemberOf() {
		return memberOf;
	}
	public void setMemberOf(String memberOf) {
		this.memberOf = memberOf;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	
}
