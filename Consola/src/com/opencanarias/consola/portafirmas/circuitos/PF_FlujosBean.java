package com.opencanarias.consola.portafirmas.circuitos;

import java.sql.Timestamp;
import java.util.List;

import com.opencanarias.consola.ldap.LDAPPersonaBean;

public class PF_FlujosBean {		// TABLA PF_FLUJOS
    private int flujo_id;			//ID_FLUJO	NUMBER(16,0)
    private Timestamp fecha_creacion; 	//FECHA_CREACION	DATE
    private int tipo_flujo;  		//ID_TIPO_FLUJO	NUMBER(16,0)	
    private int orden_activo; 		//ORDEN_ACTIVO	NUMBER(2,0)
    private String descricion;		//DESCRIPCION	VARCHAR2(255 BYTE)
    private List<PF_GruposBean> listOfGroups;
    private List<LDAPPersonaBean> listOfPersonas;
     
    public int getFlujo_id () { return this.flujo_id; } 
    public Timestamp getFechaCreacion () { return this.fecha_creacion; } 
    public int getTipoFlujo () { return  this.tipo_flujo; }  
 	public int getOrdenActivo() { return this.orden_activo; }
	public String getDescripcion() { return descricion;	}
	
    public void setFlujo_id (int dato) { this.flujo_id = dato; } 
    public void setFechaCreacion (Timestamp dato) { this.fecha_creacion = dato; }
    public void setTipoFlujo (int dato) { this.tipo_flujo = dato; }  
	public void setOrdenActivo(int dato) { this.orden_activo = dato; }
	public void setDescripcion(String dato) {	this.descricion = dato;	}
	
    public void setParametros (int flujo_id,Timestamp fechaCreacion, int tipoFlujo,int ordenActivo,String descripcion){
    	this.flujo_id=flujo_id;
    	this.fecha_creacion=fechaCreacion;
    	this.tipo_flujo=tipoFlujo;
    	this.orden_activo=ordenActivo;
    	this.descricion=descripcion; 		
    }
    
	public List<PF_GruposBean> getListOfGroups() {
		return listOfGroups;
	}
	public void setListOfGroups(List<PF_GruposBean> listOfGroups) {
		this.listOfGroups = listOfGroups;
	}
	public List<LDAPPersonaBean> getListOfPersonas() {
		return listOfPersonas;
	}
	public void setListOfPersonas(List<LDAPPersonaBean> listOfPersonas) {
		this.listOfPersonas = listOfPersonas;
	}
}
