/**
 * 
 */
package com.opencanarias.consola.portafirmas.CatalogoFirmantesPersonas;

import java.io.Serializable;

import com.opencanarias.consola.ldap.LDAPPersonaBean;

/**
 * @author Tomás Delgado
 *
 */
public class FirmantePorPersonaBean implements Serializable {
	 	private static final long serialVersionUID = 1L;
		private int id_registro;
		private int id_firmante;
		private LDAPPersonaBean persona;
		
		
		public int getRegistro() {return id_registro;	}
		public int getID_Firmante() {return id_firmante;	}
		
		public void setRegistro(int dato) {this.id_registro = dato;}
		public void setID_Firmante(int dato) {this.id_firmante = dato; }


		public LDAPPersonaBean getPersona() {
			return persona;
		}
		public void setPersona(LDAPPersonaBean persona) {
			this.persona = persona;
		}
}
