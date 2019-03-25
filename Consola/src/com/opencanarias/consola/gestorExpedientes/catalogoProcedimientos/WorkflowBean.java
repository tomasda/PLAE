package com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos;

import java.io.Serializable;

public class WorkflowBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	protected String IDPROC; // IDPROC VARCHAR2(200 BYTE) No 1
	protected String idprocppal; // IDPROCPPAL VARCHAR2(200 BYTE) Yes 2
	protected String descripcion; // DESCPROC VARCHAR2(255 BYTE) Yes 3
	protected int plazo; // PLAZO NUMBER(10,0) Yes 4
	protected String efect_sil_adm; // EFECT_SIL_ADM VARCHAR2(200 CHAR) Yes 5
	protected int activo; // ACTIVO NUMBER(5,0) Yes 6
	protected String art_resolucion; // ART_RESOLUCION VARCHAR2(255 CHAR) Yes 7
	protected String firmante_resolucion; // FIRMANTE_RESOLUCION VARCHAR2(100 CHAR) Yes 8
	protected String familia_id; // FAMILIA_ID VARCHAR2(200 CHAR) Yes 9
	protected int modificar_interesado; // MODIFICAR_INTERESADO NUMBER(1,0) No 10
	protected String seccion_tramitacion; // SECCION_TRAMITACION VARCHAR2(50 BYTE) Yes 11
	protected String interesado_x_defecto; // INTERESADO_POR_DEFECTO VARCHAR2(200 CHAR) Yes 12


	public String getFamilia_id() {
		return this.familia_id;
	}

	public String getProcedimiento() {
		return this.IDPROC;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getIdProcppal() {
		return idprocppal;
	}

	public String getEfect_sil_adm() {
		return efect_sil_adm;
	}

	public String getArt_resolucion() {
		return art_resolucion;
	}

	public String getFirmante_resolucion() {
		return firmante_resolucion;
	}

	public int getModificar_interesado() {
		return modificar_interesado;
	}
	
	public boolean getModificar_interesado_boolean() {
		if (modificar_interesado == 0) {
			return false;
		}
		if (modificar_interesado == 1) {
			return true;
		}
		return false;
	}
	
	public int getPlazo() {
		return this.plazo;
	}

	public int getActivo() {
		return this.activo;
	}

	public String getSeccion_tramitacion() {
		return this.seccion_tramitacion;
	}

	public String getInteresado_x_defecto() {
		return interesado_x_defecto;
	}

	public void setFamilia_id(String dato) {
		this.familia_id = dato;
	}

	public void setProcedimiento(String dato) {
		this.IDPROC = dato;
	}

	public void setDescripcion(String dato) {
		this.descripcion = dato;
	}

	public void setIdProcppal(String dato) {
		this.idprocppal = dato;
	}

	public void setEfect_sil_adm(String dato) {
		this.efect_sil_adm = dato;
	}

	public void setArt_resolucion(String dato) {
		this.art_resolucion = dato;
	}

	public void setFirmante_resolucion(String dato) {
		this.firmante_resolucion = dato;
	}

	public void setModificar_interesado(int dato) {
		this.modificar_interesado = dato;
	}
	public void setModificar_interesado_boolean(boolean dato) {
		if (dato) {
			this.modificar_interesado = 1;
		}else{
			this.modificar_interesado = 0;
		}
	}

	public void setPlazo(int dato) {
		this.plazo = dato;
	}

	public void setActivo(int dato) {
		this.activo = dato;
	}
	
	public void setSeccion_tramitacion(String dato) {
		this.seccion_tramitacion = dato;
	}

	public void setInteresado_x_defecto(String dato) {
		this.interesado_x_defecto = dato;
	}
	
	public WorkflowBean(){
		
	}
	
	public WorkflowBean(String Procedimiento_, String IdProcppal_, String Descripcion_, int Plazo_, String Efect_sil_adm_, int Activo_,
			String Art_resolucion_, String Firmante_resolucion_, String Familia_id_, int Modificar_interesado_, String Seccion_tramitacion_,
			String Interesado_x_defecto_) {
		this.IDPROC = Procedimiento_;
		this.idprocppal = IdProcppal_;
		this.descripcion = Descripcion_;
		this.plazo = Plazo_;
		this.efect_sil_adm = Efect_sil_adm_;
		this.activo = Activo_;
		this.art_resolucion = Art_resolucion_;
		this.firmante_resolucion = Firmante_resolucion_;
		this.familia_id = Familia_id_;
		this.modificar_interesado = Modificar_interesado_;
		this.seccion_tramitacion = Seccion_tramitacion_;
		this.interesado_x_defecto = Interesado_x_defecto_;
	}


}
