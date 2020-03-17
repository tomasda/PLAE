package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = DataBaseUser.findByDepartments, query = "SELECT pc FROM DataBaseUser AS pc WHERE pc.businessCategory IN :businessCategory"),
	@NamedQuery(name = DataBaseUser.findAllDepartments, query = "SELECT pc FROM DataBaseUser AS pc"),
	@NamedQuery(name = DataBaseUser.findById, query = "SELECT pc FROM DataBaseUser AS pc WHERE pc.id LIKE :id"),
})
@Table(name = "PF_USERS")
public class DataBaseUser implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String findById = "DataBaseUser.findById";
	public static final String findByDepartments = "DataBaseUser.findByDepartments";
	public static final String findAllDepartments = "DataBaseUser.allDepartments";
	
	/*
	"CAR_LICENSE" CHARACTER VARYING(16), 
	"CN" CHARACTER VARYING(255 ), 
	"GIVEN_NAME" CHARACTER VARYING(255), 
	"SN" CHARACTER VARYING(255 ), 
	"USERNAME" CHARACTER VARYING(255 ), 
	"EMAIL" CHARACTER VARYING(255 ), 
	"BUSINESSCATEGORY" CHARACTER VARYING (500), 
	"MEMBEROF" CHARACTER VARYING(500 ), 
	"DESCRIPCION" CHARACTER VARYING(500) 
	 */
	@Id @GeneratedValue
	@Column(name="CAR_LICENSE")
	private String id;
	@Column(name="CN")
	private String name;
	@Column (name="GIVEN_NAME")
	private String onlyName;
	@Column (name="SN")
	private String surname;
	@Column (name="USERNAME")
	private String user;
	@Column (name="EMAIL")
	private String email;
	@Column(name="BUSINESSCATEGORY")
	private String businessCategory;
	@Column(name="MEMBEROF")
	private String memberOf;
	@Column(name="DESCRIPCION")
	private String description;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOnlyName() {
		return onlyName;
	}
	public void setOnlyName(String onlyName) {
		this.onlyName = onlyName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getBusinessCategory() {
		return businessCategory;
	}
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
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
}
