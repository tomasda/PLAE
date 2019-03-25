package com.opencanarias.consola.firmamanuscrita;

import java.io.Serializable;
import java.util.List;

public class CatDispositivosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ID;
	private String DESCRIPCION;
	private String ID_FIRMA_MANUSCRITA;
	
	private List<CatPermisosDispositivosBean> PERMISSION;
	
	public CatDispositivosBean(String iD, String dESCRIPCION, String iD_FIRMA_MANUSCRITA,
			List<CatPermisosDispositivosBean> pERMISSION) {
		super();
		ID = iD;
		DESCRIPCION = dESCRIPCION;
		ID_FIRMA_MANUSCRITA = iD_FIRMA_MANUSCRITA;
		PERMISSION = pERMISSION;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDESCRIPCION() {
		return DESCRIPCION;
	}
	public void setDESCRIPCION(String dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}
	public String getID_FIRMA_MANUSCRITA() {
		return ID_FIRMA_MANUSCRITA;
	}
	public void setID_FIRMA_MANUSCRITA(String iD_FIRMA_MANUSCRITA) {
		ID_FIRMA_MANUSCRITA = iD_FIRMA_MANUSCRITA;
	}
	public List<CatPermisosDispositivosBean> getPERMISSION() {
		return PERMISSION;
	}
	public void setPERMISSION(List<CatPermisosDispositivosBean> pERMISSION) {
		PERMISSION = pERMISSION;
	}
	@Override
	public String toString() {
		return "CatDispositivosBean [ID=" + ID + ", DESCRIPCION=" + DESCRIPCION + ", ID_FIRMA_MANUSCRITA="
				+ ID_FIRMA_MANUSCRITA + ", PERMISSION=" + PERMISSION + "]";
	}
	
}
