package com.opencanarias.consola.gestorExpedientes.catalogoFamilias;

import java.io.Serializable;


//@ManagedBean
//@SessionScoped
public class FamiliasBean implements Serializable{

	private static final long serialVersionUID = 1L;
	public String ID;
	public String DESCRIPTION;
	
	public FamiliasBean(){
		
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	
    public FamiliasBean(String f_id,String des) {
    	ID=f_id;
    	DESCRIPTION=des;
    }
}
