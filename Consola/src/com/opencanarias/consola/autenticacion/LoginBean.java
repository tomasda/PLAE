package com.opencanarias.consola.autenticacion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	@Id @Column(name="id", nullable=false, unique=true) 
	private int id;
	
	@Column(name="userName", nullable=false, unique=true) 
	private String nameUser; 
	
	@Column(name="password", nullable=false, unique=false) 
	private String password; 
	
	@Column(name="lastAccess", unique=true) @Temporal(TemporalType.DATE) 
	private Date lastAccess;
	
	public String getNameUser() { return nameUser; }
	
	public void setNameUser(String nameUser) { this.nameUser = nameUser; } 
	
	public String getPassword() { return password; }
	
	public void setPassword(String password) { this.password = password; } 
	
	public Date getLastAccess() { return lastAccess; } 
	
	public void setLastAccess(Date lastAccess) { this.lastAccess = lastAccess; }
	
}