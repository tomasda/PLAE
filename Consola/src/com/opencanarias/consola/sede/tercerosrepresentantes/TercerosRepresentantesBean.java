package com.opencanarias.consola.sede.tercerosrepresentantes;

import java.sql.Timestamp;

public class TercerosRepresentantesBean {
//	ID	NUMBER(19,0)
//	FECHA_INICIO	TIMESTAMP(6)
//	FECHA_FIN	TIMESTAMP(6)
//	DOCUMENTO_NOMBRE	VARCHAR2(80 BYTE)
//	DOCUMENTO_DESCRIPCION	VARCHAR2(200 BYTE)
//	DOCUMENTO_URI	VARCHAR2(160 BYTE)
//	INTERESADO_ID	NUMBER(19,0)
//	REPRESENTANTE_ID	NUMBER(19,0)

	private int ID;	
	private Timestamp FECHA_INICIO;
	private Timestamp FECHA_FIN;
	private String DOCUMENTO_NOMBRE;
	private String DOCUMENTO_DESCRIPCION;
	private String DOCUMENTO_URI;
	private int INTERESADO_ID;
	private int REPRESENTANTE_ID;
	// PARA NO CREAR OTRO BEAN, A ESE AÑADO LOS CAMPOS A MOSTRAR EN EL FORMULARIO
//		String NombreInteresado;
//		String NombreRepresentante;
	
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Timestamp getFECHA_INICIO() {
		return FECHA_INICIO;
	}
	public void setFECHA_INICIO(Timestamp fECHA_INICIO) {
		FECHA_INICIO = fECHA_INICIO;
	}
	public Timestamp getFECHA_FIN() {
		return FECHA_FIN;
	}
	public void setFECHA_FIN(Timestamp fECHA_FIN) {
		FECHA_FIN = fECHA_FIN;
	}
	public String getDOCUMENTO_NOMBRE() {
		return DOCUMENTO_NOMBRE;
	}
	public void setDOCUMENTO_NOMBRE(String dOCUMENTO_NOMBRE) {
		DOCUMENTO_NOMBRE = dOCUMENTO_NOMBRE;
	}
	public String getDOCUMENTO_DESCRIPCION() {
		return DOCUMENTO_DESCRIPCION;
	}
	public void setDOCUMENTO_DESCRIPCION(String dOCUMENTO_DESCRIPCION) {
		DOCUMENTO_DESCRIPCION = dOCUMENTO_DESCRIPCION;
	}
	public String getDOCUMENTO_URI() {
		return DOCUMENTO_URI;
	}
	public void setDOCUMENTO_URI(String dOCUMENTO_URI) {
		DOCUMENTO_URI = dOCUMENTO_URI;
	}
	public int getINTERESADO_ID() {
		return INTERESADO_ID;
	}
	public void setINTERESADO_ID(int iNTERESADO_ID) {
		INTERESADO_ID = iNTERESADO_ID;
	}
	public int getREPRESENTANTE_ID() {
		return REPRESENTANTE_ID;
	}
	public void setREPRESENTANTE_ID(int rEPRESENTANTE_ID) {
		REPRESENTANTE_ID = rEPRESENTANTE_ID;
	}
	
	
	
//	public String getNombreInteresado() {
//		return NombreInteresado;
//	}
//	public void setNombreInteresado(String nombreInteresado) {
//		NombreInteresado = nombreInteresado;
//	}
//	public String getNombreRepresentante() {
//		return NombreRepresentante;
//	}
//	public void setNombreRepresentante(String nombreRepresentante) {
//		NombreRepresentante = nombreRepresentante;
//	}
	
}
