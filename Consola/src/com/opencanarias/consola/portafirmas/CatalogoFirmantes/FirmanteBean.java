package com.opencanarias.consola.portafirmas.CatalogoFirmantes;

import java.util.List;

import com.opencanarias.consola.portafirmas.CatalogoFirmantesPersonas.FirmantePorPersonaBean;

public class FirmanteBean {

		private int id_firmante;
		private String nombre;
		private String apellido;
		private String apellido2;
		private String dni_nie;
		private String cargo;
		private String mail;
		private int es_firmante;
		private int es_validador;
		private int adjuntos_alertas;
		private int es_usuario_pruebas;
		private List<FirmantePorPersonaBean> listOfPersons = null;
		
		public FirmanteBean () {}
		public FirmanteBean (int datoID, String datoNombre, String datoApellido, String datoApellido2, String datoDNI_NIE, String datoCargo, String datoMail, int datoESFirmante, int datoESValidador, int datoAdjuntoAlertas, int datoEsUsuarioPruebas){
			this.id_firmante=datoID;
			this.nombre=datoNombre;
			this.apellido=datoApellido;
			this.apellido2=datoApellido2;
			this.dni_nie=datoDNI_NIE;
			this.cargo=datoCargo;
			this.mail=datoMail;
			this.es_firmante=datoESFirmante;
			this.es_validador=datoESValidador;
			this.adjuntos_alertas=datoAdjuntoAlertas;
			this.es_usuario_pruebas=datoEsUsuarioPruebas;
		}
		
		
		public int getIdFirmante() {return id_firmante;	}
		public String getNombre() {return nombre;	}
		public String getApellido() {return apellido;	}
		public String getApellido2() {return apellido2;	}
		public String getDNI_NIE() {return dni_nie; }
		public String getCargo() {return cargo; }
		public String getMail(){return mail; }
		public int getEsFirmante(){return es_firmante;}
		public int getEsValidador(){return es_validador;}
		
		public void setIdFirmante(int dato) {this.id_firmante = dato;}
		public void setNombre(String dato) {this.nombre = dato; }
		public void setApellido(String dato) {this.apellido = dato;}
		public void setApellido2(String dato) {this.apellido2 = dato;}
		public void setDNI_NIE(String dato) {this.dni_nie = dato; }
		public void setCargo(String dato) { this.cargo = dato; }
		public void setMail(String dato){this.mail = dato; }
		public void setEsFirmante(int dato){this.es_firmante = dato;}
		public void setEsValidador(int dato){this.es_validador = dato;}
		
		public void setEsFirmanteCheckBox(Boolean value) {
			if (value == true) {
				es_firmante = 1;
			}else {
				es_firmante = 0;
			}
		}
		public Boolean getEsFirmanteCheckBox(){
			if (es_firmante == 0) {
				return false;
			}
			if (es_firmante == 1) {
				return true;
			}
			return false;
			}
		
		
		public void setEsValidadorCheckBox(Boolean value) {
			if (value == true) {
				es_validador = 1;
			}else {
				es_validador = 0;
			}
		}
		public Boolean getEsValidadorCheckBox(){
			if (es_validador == 0) {
				return false;
			}
			if (es_validador == 1) {
				return true;
			}
			return false;
		}
		public void setAlertasCheckBox(Boolean value) {
			if (value == true) {
				adjuntos_alertas = 1;
			}else {
				adjuntos_alertas = 0;
			}
		}
		public Boolean getAlertasCheckBox(){
			if (adjuntos_alertas == 0) {
				return false;
			}
			if (adjuntos_alertas == 1) {
				return true;
			}
			return false;
		}
		public void setPruebasCheckBox(Boolean value) {
			if (value == true) {
				es_usuario_pruebas = 1;
			}else {
				es_usuario_pruebas = 0;
			}
		}
		public Boolean getPruebasCheckBox(){
			if (es_usuario_pruebas == 0) {
				return false;
			}
			if (es_usuario_pruebas == 1) {
				return true;
			}
			return false;
		}

		
		public int getAdjuntos_alertas() {
			return adjuntos_alertas;
		}
		public void setAdjuntos_alertas(int adjuntosAlertas) {
			this.adjuntos_alertas = adjuntosAlertas;
		}
		public int getEs_usuario_pruebas() {
			return es_usuario_pruebas;
		}
		public void setEs_usuario_pruebas(int esUsuarioPruebas) {
			this.es_usuario_pruebas = esUsuarioPruebas;
		}
		
		public List<FirmantePorPersonaBean> getListOfPersons() {
			return listOfPersons;
		}
		public void setListOfPersons(List<FirmantePorPersonaBean> listOfPersons) {
			this.listOfPersons = listOfPersons;
		}
}
