package com.opencanarias.consola.registro.asientos;

import java.io.Serializable;
import java.sql.Timestamp;

public class AsientosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ID;//	NUMBER(19,0)
	private Timestamp FECHA_HORA;//	TIMESTAMP(6)
	private boolean TIPO_REGISTRO;//	CHAR(1 BYTE)
	private String ASUNTO;//	VARCHAR2(240 BYTE)
	private String EXPEDIENTE;//	VARCHAR2(80 BYTE)
	private String NUMERO_TRANSPORTE_ENTRADA;//	VARCHAR2(20 BYTE)
	private String APLICACION_Y_VERSION;//	VARCHAR2(4 BYTE)
	private String NOMBRE_USUARIO_ORIGEN;//	VARCHAR2(80 BYTE)
	private int PRUEBA;//	NUMBER(1,0)
	private String OBSERVACIONES;//	VARCHAR2(160 BYTE)
//	private int DEPARTAMENTO_TRAMITACION_ID;//	NUMBER(38,0)
	private String ENTIDAD_REGISTRAL_ID;//	VARCHAR2(21 BYTE)
	private int ESTADO_REGISTRO_ID;//	NUMBER(38,0)
	private String TIPO_TRANSPORTE_ENTRADA_ID;//	VARCHAR2(2 BYTE)
	private String IDENTIFICACION;//	VARCHAR2(30 BYTE)
	private String ASUNTOEXPEDIENTE;//	VARCHAR2(240 BYTE)
	private String FAMILIA_ID;//	VARCHAR2(200 BYTE)
	private String PROCEDIMIENTO_ID;//	VARCHAR2(200 BYTE)
	private int ASOCIADOEXPEDIENTE;//	NUMBER(1,0)
	private Timestamp FECHA_ENVIO_DPTOS;//	TIMESTAMP(6)
	private Timestamp FECHA_PRIM_ACCES_DESTINAT;//	TIMESTAMP(6)
	private int LEIDO_DPTO_DESTINATARIO;//	NUMBER(1,0)
	private String ORIGEN_ASIENTO_ID;//	CHAR(1 BYTE)
	private String IDENTIFICACION_SUBTIPO;//	VARCHAR2(30 BYTE)
	private int SUBTIPO_REGISTRO_ID;//	NUMBER(19,0)
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Timestamp getFECHA_HORA() {
		return FECHA_HORA;
	}
	public void setFECHA_HORA(Timestamp fECHA_HORA) {
		FECHA_HORA = fECHA_HORA;
	}
	public boolean isTIPO_REGISTRO() {
		return TIPO_REGISTRO;
	}
	public void setTIPO_REGISTRO(boolean tIPO_REGISTRO) {
		TIPO_REGISTRO = tIPO_REGISTRO;
	}
	public String getASUNTO() {
		return ASUNTO;
	}
	public void setASUNTO(String aSUNTO) {
		ASUNTO = aSUNTO;
	}
	public String getEXPEDIENTE() {
		return EXPEDIENTE;
	}
	public void setEXPEDIENTE(String eXPEDIENTE) {
		EXPEDIENTE = eXPEDIENTE;
	}
	public String getNUMERO_TRANSPORTE_ENTRADA() {
		return NUMERO_TRANSPORTE_ENTRADA;
	}
	public void setNUMERO_TRANSPORTE_ENTRADA(String nUMERO_TRANSPORTE_ENTRADA) {
		NUMERO_TRANSPORTE_ENTRADA = nUMERO_TRANSPORTE_ENTRADA;
	}
	public String getAPLICACION_Y_VERSION() {
		return APLICACION_Y_VERSION;
	}
	public void setAPLICACION_Y_VERSION(String aPLICACION_Y_VERSION) {
		APLICACION_Y_VERSION = aPLICACION_Y_VERSION;
	}
	public String getNOMBRE_USUARIO_ORIGEN() {
		return NOMBRE_USUARIO_ORIGEN;
	}
	public void setNOMBRE_USUARIO_ORIGEN(String nOMBRE_USUARIO_ORIGEN) {
		NOMBRE_USUARIO_ORIGEN = nOMBRE_USUARIO_ORIGEN;
	}
	public int getPRUEBA() {
		return PRUEBA;
	}
	public void setPRUEBA(int pRUEBA) {
		PRUEBA = pRUEBA;
	}
	public String getOBSERVACIONES() {
		return OBSERVACIONES;
	}
	public void setOBSERVACIONES(String oBSERVACIONES) {
		OBSERVACIONES = oBSERVACIONES;
	}
//	public int getDEPARTAMENTO_TRAMITACION_ID() {
//		return DEPARTAMENTO_TRAMITACION_ID;
//	}
//	public void setDEPARTAMENTO_TRAMITACION_ID(int dEPARTAMENTO_TRAMITACION_ID) {
//		DEPARTAMENTO_TRAMITACION_ID = dEPARTAMENTO_TRAMITACION_ID;
//	}
	public String getENTIDAD_REGISTRAL_ID() {
		return ENTIDAD_REGISTRAL_ID;
	}
	public void setENTIDAD_REGISTRAL_ID(String eNTIDAD_REGISTRAL_ID) {
		ENTIDAD_REGISTRAL_ID = eNTIDAD_REGISTRAL_ID;
	}
	public int getESTADO_REGISTRO_ID() {
		return ESTADO_REGISTRO_ID;
	}
	public void setESTADO_REGISTRO_ID(int eSTADO_REGISTRO_ID) {
		ESTADO_REGISTRO_ID = eSTADO_REGISTRO_ID;
	}
	public String getTIPO_TRANSPORTE_ENTRADA_ID() {
		return TIPO_TRANSPORTE_ENTRADA_ID;
	}
	public void setTIPO_TRANSPORTE_ENTRADA_ID(String tIPO_TRANSPORTE_ENTRADA_ID) {
		TIPO_TRANSPORTE_ENTRADA_ID = tIPO_TRANSPORTE_ENTRADA_ID;
	}
	public String getIDENTIFICACION() {
		return IDENTIFICACION;
	}
	public void setIDENTIFICACION(String iDENTIFICACION) {
		IDENTIFICACION = iDENTIFICACION;
	}
	public String getASUNTOEXPEDIENTE() {
		return ASUNTOEXPEDIENTE;
	}
	public void setASUNTOEXPEDIENTE(String aSUNTOEXPEDIENTE) {
		ASUNTOEXPEDIENTE = aSUNTOEXPEDIENTE;
	}
	public String getFAMILIA_ID() {
		return FAMILIA_ID;
	}
	public void setFAMILIA_ID(String fAMILIA_ID) {
		FAMILIA_ID = fAMILIA_ID;
	}
	public String getPROCEDIMIENTO_ID() {
		return PROCEDIMIENTO_ID;
	}
	public void setPROCEDIMIENTO_ID(String pROCEDIMIENTO_ID) {
		PROCEDIMIENTO_ID = pROCEDIMIENTO_ID;
	}
	public int getASOCIADOEXPEDIENTE() {
		return ASOCIADOEXPEDIENTE;
	}
	public void setASOCIADOEXPEDIENTE(int aSOCIADOEXPEDIENTE) {
		ASOCIADOEXPEDIENTE = aSOCIADOEXPEDIENTE;
	}
	public Timestamp getFECHA_ENVIO_DPTOS() {
		return FECHA_ENVIO_DPTOS;
	}
	public void setFECHA_ENVIO_DPTOS(Timestamp fECHA_ENVIO_DPTOS) {
		FECHA_ENVIO_DPTOS = fECHA_ENVIO_DPTOS;
	}
	public Timestamp getFECHA_PRIM_ACCES_DESTINAT() {
		return FECHA_PRIM_ACCES_DESTINAT;
	}
	public void setFECHA_PRIM_ACCES_DESTINAT(Timestamp fECHA_PRIM_ACCES_DESTINAT) {
		FECHA_PRIM_ACCES_DESTINAT = fECHA_PRIM_ACCES_DESTINAT;
	}
	public int getLEIDO_DPTO_DESTINATARIO() {
		return LEIDO_DPTO_DESTINATARIO;
	}
	public void setLEIDO_DPTO_DESTINATARIO(int lEIDO_DPTO_DESTINATARIO) {
		LEIDO_DPTO_DESTINATARIO = lEIDO_DPTO_DESTINATARIO;
	}
	public String getORIGEN_ASIENTO_ID() {
		return ORIGEN_ASIENTO_ID;
	}
	public void setORIGEN_ASIENTO_ID(String oRIGEN_ASIENTO_ID) {
		ORIGEN_ASIENTO_ID = oRIGEN_ASIENTO_ID;
	}
	public String getIDENTIFICACION_SUBTIPO() {
		return IDENTIFICACION_SUBTIPO;
	}
	public void setIDENTIFICACION_SUBTIPO(String iDENTIFICACION_SUBTIPO) {
		IDENTIFICACION_SUBTIPO = iDENTIFICACION_SUBTIPO;
	}
	public int getSUBTIPO_REGISTRO_ID() {
		return SUBTIPO_REGISTRO_ID;
	}
	public void setSUBTIPO_REGISTRO_ID(int sUBTIPO_REGISTRO_ID) {
		SUBTIPO_REGISTRO_ID = sUBTIPO_REGISTRO_ID;
	}
}
