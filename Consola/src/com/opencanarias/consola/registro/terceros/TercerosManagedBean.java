package com.opencanarias.consola.registro.terceros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.aeat.valida.Validador;
import com.opencanarias.consola.commons.Patrones;
import com.opencanarias.consola.interfaces.ManagedBeanInterface;
import com.opencanarias.consola.menu.MenuSessionOption;
import com.opencanarias.consola.registro.tablasComunes.TablasComunesManagedBean;
import com.opencanarias.consola.registro.terceros.correos.CorreosBean;
import com.opencanarias.consola.registro.terceros.correos.CorreosDAO;
import com.opencanarias.consola.registro.terceros.deh.DEHBean;
import com.opencanarias.consola.registro.terceros.deh.DEHDAO;
import com.opencanarias.consola.registro.terceros.direcciones.DireccionesBean;
import com.opencanarias.consola.registro.terceros.direcciones.DireccionesDAO;
import com.opencanarias.consola.registro.terceros.telefonos.TelefonosBean;
import com.opencanarias.consola.registro.terceros.telefonos.TelefonosDAO;

public class TercerosManagedBean extends TablasComunesManagedBean implements Serializable,ManagedBeanInterface {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TercerosManagedBean.class);

	private TercerosBean tercerosB;

	private List<CorreosBean> listCorreosB;
	private List<DireccionesBean> listDireccionesB;
	private List<TelefonosBean> listTelefonosB;
	private List<DEHBean> listDehB;
	private List<TercerosBean> listTercerosB;

	public TercerosManagedBean() {
		super();
		this.tercerosB = null;
		this.listCorreosB = null;
		this.listDireccionesB = null;
		this.listTelefonosB = null;
		this.listDehB = null;
		this.listTercerosB = null;
	}

	public void TercerosStartMenu() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		/*
		 * Si el estado del men� es I - Significa que esta en estado inicial y que los valores han de estar a cero.
		 */
		if (ms.getMenuEnabledForm().equals("I")) {
			this.tercerosB = null;
			this.listCorreosB = null;
			this.listDireccionesB = null;
			this.listTelefonosB = null;
			this.listDehB = null;
			this.listTercerosB = null;
		}
	}

	public void findAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		/*
		 * Cambio el men� a fase Busqueda
		 */
		ms.setMenuEnabledForm("B");
		TercerosDAO td = new TercerosDAO();
		// S� el criterio de b�squeda est� vac�o.......
		if (ms.getSearchOption().isEmpty() && ms.getSearchOption2().isEmpty()&& ms.getSearchOption3().isEmpty()&& ms.getSearchOption4().isEmpty()) { 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Introduzca alg�n par�metro de b�squeda." , "  "));
			listTercerosB = null;
		} else {
			try {
				listTercerosB = td.getTercerosList(ms.getSearchOption(), ms.getSearchOption4(), ms.getSearchOption2(),ms.getSearchOption3(),"");
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha podido obtener los datos de la BBDD." , ""));
				logger.error("No se ha podido obtener los datos de la BBDD");
				e.printStackTrace();
			}
		}
	}
	public void newAction() {
		MenuSessionOption ms =  (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		this.tercerosB = new TercerosBean(0, "", "", "", "", "", "", "N", "", 0, 0);
		this.listCorreosB = new ArrayList<CorreosBean>();
		this.listDireccionesB = new ArrayList<DireccionesBean>();
		// Es obligatorio que un tercero tenga una direcci�n como m�nimo.
		newDireccion();
		this.listTelefonosB = new ArrayList<TelefonosBean>();
		this.listDehB = new ArrayList<DEHBean>();
		this.listTercerosB = null;
		ms.setMenuEnabledForm("N");
	}

	public void editAction(Object iD) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		// Indico al formulario que estamos en la edici�n de los datos.
		ms.setMenuEnabledForm("Ed");
		TercerosDAO td = new TercerosDAO();
		this.tercerosB = td.getTercero((int)iD);
		CorreosDAO cd = new CorreosDAO();
		this.listCorreosB = cd.getCorreosList((int)iD);
		DireccionesDAO dd = new DireccionesDAO();
		this.listDireccionesB = dd.getDireccionesList((int)iD);
		TelefonosDAO ted = new TelefonosDAO();
		this.listTelefonosB = ted.getTelefonosList((int)iD);
		DEHDAO ded = new DEHDAO();
		this.listDehB = ded.getDEHList((int)iD);
	}

	public void deleteAction(Object data) {
		boolean delDEH = false;
		boolean delTel = false;
		boolean delCor = false;
		boolean delDir = false;
		boolean delTer = false;
		DEHDAO ded = new DEHDAO();
		delDEH = ded.deleteDEH(listDehB);
		TelefonosDAO ted = new TelefonosDAO();
		delTel = ted.deleteTelefonos(listTelefonosB);
		CorreosDAO cd = new CorreosDAO();
		delCor = cd.deleteCorreos(listCorreosB);
		DireccionesDAO dd = new DireccionesDAO();
		delDir = dd.deleteDirecciones(listDireccionesB);
		TercerosDAO td = new TercerosDAO();
		delTer = td.deleteTercero(tercerosB);
		if (delDEH && delCor && delDir && delTel &&delTer) {
			logger.info("Se ha Eliminado el tercero " + tercerosB.getID()+" "+tercerosB.getNOMBRE()+ tercerosB.getRAZON_SOCIAL() + ".");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La operaci�n de Eliminaci�n se ha realizado correctamente.", ""));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error durante la operaci�n. ", " Revise los LOG�s para determinar el problema"));
		}
		findAction();
	}


	public void returnAction() {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		ms.setMenuEnabledForm("I");
	}

	public void saveAction() {
		if (validacionIdentificacion(tercerosB.getTIPO_IDENTIFICACION_ID(),tercerosB.getIDENTIFICACION())) {
			if (validacionDirecciones()) {
				if (validacionTelefonos()) {
					if (validacionCorreos()) {
						if (validacionDEH()) {
							if (validacionIdentificacion()) {
								TercerosDAO td = new TercerosDAO();
								td.saveTercero(tercerosB);
								/**
								 * Si el tercero es nuevo el id que se aplica por defecto = 0
								 * En ese caso, para persistir los datos primero es necesario obtener el id que se le ha dado.
								 * Y actualizarlo en cada uno de los objetos.
								 */
								if (0==tercerosB.getID())
									tercerosB = td.getTercero(tercerosB.getIDENTIFICACION());
								DireccionesDAO dd = new DireccionesDAO();
								if (0==listDireccionesB.get(0).getTERCERO_ID())
									listDireccionesB.get(0).setTERCERO_ID(tercerosB.getID());
								dd.saveDirecciones(listDireccionesB);
								if (!listCorreosB.isEmpty()) {
									CorreosDAO cd = new CorreosDAO();
									if (0==listCorreosB.get(0).getTerceroID())
										listCorreosB.get(0).setTerceroID(tercerosB.getID());
									cd.saveCorreos(listCorreosB);
								}
								if (!listTelefonosB.isEmpty()) {
									TelefonosDAO ted = new TelefonosDAO();
									if (0==listTelefonosB.get(0).getTerceroID())
										listTelefonosB.get(0).setTerceroID(tercerosB.getID());
									ted.saveTelefonos(listTelefonosB);
								}
								if (!listDehB.isEmpty()) {
									DEHDAO ded = new DEHDAO();
									if (0==listDehB.get(0).getTerceroID())
										listDehB.get(0).setTerceroID(tercerosB.getID());
									ded.saveDEH(listDehB);
								}
								logger.info("Se Guarda/Actualiza el tercero " + tercerosB.getID()+" "+tercerosB.getNOMBRE()+ tercerosB.getRAZON_SOCIAL() + ".");
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La operaci�n de Guardado se ha realizado correctamente.", ""));
							}
						}
					}
				}
			}
		}
	}


	/*
	 * M�todos para a�adir un nuevo apartado al Tercero.
	 */

	public void newDireccion() {
		if (listDireccionesB.isEmpty()) {
			DireccionesBean db = new DireccionesBean(0, "", "", "", "108", tercerosB.getID(), "", "");
			listDireccionesB.add(db);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se puede a�adir m�s de una Direcci�n", "Restricci�n que se retirar� una vez se implementen m�ltiples direcciones en Registro E/S"));
		}
	}
	public void newTelefono() {
		if (listTelefonosB.isEmpty()) {
			TelefonosBean tb = new TelefonosBean(0, "", tercerosB.getID());
			listTelefonosB.add(tb);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se puede a�adir m�s de un Tel�fono", "Restricci�n que se retirar� una vez se implementen m�ltiples direcciones en Registro E/S"));
		}
	}
	public void newCorreo() {
		if (listCorreosB.isEmpty()) {
			CorreosBean cb = new CorreosBean(0, "", tercerosB.getID());
			listCorreosB.add(cb);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se puede a�adir m�s de una Direcci�n", "Restricci�n que se retirar� una vez se implementen m�ltiples direcciones en Registro E/S"));
		}
	}
	public void newDEH() {
		if (listDehB.isEmpty()) {
			DEHBean db = new DEHBean(0,"",tercerosB.getID());
			listDehB.add(db);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se puede a�adir m�s de una Direcci�n", "Restricci�n que se retirar� una vez se implementen m�ltiples direcciones en Registro E/S"));
		}
	}

	/*
	 * M�todo para que el formulario de edici�n de terceros sepa s� ha de mostrar el campo Raz�n Social o los campos Nombre y Apellidos.
	 */
	public boolean isNombreApOrRazonSocial(String idForm, String tipoIdentificacion) {
		boolean bool = false; // C , N , E , O , P , X
		switch (idForm) {
		case "NA":// Es Nombre y Apellidos
			if (tipoIdentificacion.equals("N") || tipoIdentificacion.equals("E") || tipoIdentificacion.equals("O") || tipoIdentificacion.equals("P")) {
				bool = true;
			}
			break;
		case "RS":// Es Raz�n Social
			if (tipoIdentificacion.equals("C") || tipoIdentificacion.equals("X")) {
				bool = true;
			}
			break;
		}
		return bool;
	}
	/*
	 * M�todo para validar si el Nif/CIF Tercero ya existe en la Base de Datos.
	 */
	private boolean validacionIdentificacion() {
		boolean bol = true;
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (ms.getEnabledValidation()) {
			if (tercerosB.getID()==0) { // S� el tercero es nuevo, su id es 0, y portanto es necesario validar si va a sobre-escribir un nif que ya existe.
				TercerosDAO td = new TercerosDAO();
				if (td.exists(tercerosB.getIDENTIFICACION())) {
					bol = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "El Identificador que ha introducido ya existe.", "NIF / CIF / Extranjero"));
				}
			}
		}
		return bol;
	}
	/*
	 * M�todo para validar Correos.
	 */
	private  boolean validacionCorreos() {
		boolean bol = true;
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (ms.getEnabledValidation()) {
			if (!listCorreosB.isEmpty()) {
				Pattern pattern = Pattern.compile(Patrones.getPatternCorreo());
				Matcher matcher = null;
				for (CorreosBean liCorreos : listCorreosB) {
					matcher = pattern.matcher(liCorreos.getEmail());
					if (!matcher.matches()) {
						bol = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alguno de el/los Correo/s Electr�nico/s no tiene el formato adecuado.", ""));
					}
				}
			}
		}
		return bol;
	}

	/*
	 * M�todo para validar DEH.
	 */
	private  boolean validacionDEH() {
		boolean bol = true;
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (ms.getEnabledValidation()) {
			if (!listDehB.isEmpty()) {
				Pattern pattern = Pattern.compile(Patrones.getPatternCorreo());
				Matcher matcher = null;
				for (DEHBean liD : listDehB) {
					matcher = pattern.matcher(liD.getDEH());
					if (!matcher.matches()) {
						bol = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alguno de el/los Direcci�n/es Electr�nica/s Habilitada/s no tiene el formato adecuado.", ""));
					}
				}
			}
		}
		return bol;
	}

	/*
	 * M�todo para validar s� la direcci�n est� bien construida.
	 */
	private  boolean validacionDirecciones() {
		boolean bol = true;
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (ms.getEnabledValidation()) {
			for (DireccionesBean liD : listDireccionesB) {
				// Segundo, verificamos que la Direcc�n est� cumplimentada. O sea, que almenos tenga alg�n caracter.
				if (liD.getDIRECCION().length() >= 0) {
					// Tercero verificar c�digo postal
					if (liD.getPAIS_ID().equals("108") && liD.getCODIGO_POSTAL().length() == 5) { // S� el c�digo postal
						// es espa�ol el
						// tama�o ha de ser
						// de 5 d�gitos
						// Cuarto verificar Pa�s, Provincia, Municipio
						if (!liD.getPAIS_ID().isEmpty() && !liD.getPROVINCIA_ID().isEmpty()
								&& !liD.getMUNICIPIO_ID().isEmpty() && !liD.getPROVINCIA_ID().equals(null)
								&& !liD.getMUNICIPIO_ID().equals(null)) {
							// S� existiese alguna otra validaci�n se aplicar�a a partir de aqu�.
						} else {
							bol = false;
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "El Pa�s, o la Provincia, o el Municipio no est� cumplimentado correctamente.", ""));
						}
					} else {
						bol = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "El c�digo postal no cumple con el tama�o establecido en ESPA�A.", ""));
					}
				} else {
					bol = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se ha Cumpliemtado el campo Direcci�n.", ""));
				}
			}
		}
		return bol;
	}

	/*
	 * M�todo que valida el n�mero identificativo con el tipo que se ha espec�ficado.
	 */
	private  boolean validacionIdentificacion(String tIPO_IDENTIFICACION_ID_TMP, String iDENTIFICACION_TMP) {
		boolean validacion = true;
		switch (tIPO_IDENTIFICACION_ID_TMP) {
		case "C":
			/*
			 * �Es Empresa? CIF
			 */
			Validador validador = new Validador();
			int e = validador.checkNif(iDENTIFICACION_TMP);
			if (e > 0) {
				validacion = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "El CIF no tiene formato v�lido.", ""));
			}

			break;
		case "N":
			/*
			 * �Es Ciudadano? NIF
			 */
			// si es NIE, eliminar la x,y,z inicial para tratarlo como nif
			if (iDENTIFICACION_TMP.toUpperCase().startsWith("X") || iDENTIFICACION_TMP.toUpperCase().startsWith("Y") || iDENTIFICACION_TMP.toUpperCase().startsWith("Z")) {
				iDENTIFICACION_TMP = iDENTIFICACION_TMP.substring(1);
			}
			Pattern nifPattern = Pattern.compile(Patrones.getPatternNIF());
			Matcher m = nifPattern.matcher(iDENTIFICACION_TMP);
			if (m.matches()) {
				String letra = m.group(2);
				// Extraer letra del NIF
				String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
				int dni = Integer.parseInt(m.group(1));
				dni = dni % 23;
				String reference = letras.substring(dni, dni + 1);
				if (reference.equalsIgnoreCase(letra)) {
					validacion = true;
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "La validaci�n del n�mero de identificaci�n no es correcta.", ""));
					validacion = false;
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "La validaci�n del n�mero de identificaci�n no es correcta.", ""));
				validacion = false;
			}

			break;
		case "E":
			/*
			 * �Es Extrangero?
			 */
			break;
		case "O":
			/*
			 * �Es Otros - Persona F�sica?
			 */

			break;
		case "P":
			/*
			 * �Es Pasaporte?
			 */

			break;
		case "X":
			/*
			 * �Es C�digo de Origen?
			 */

			break;
		}
		return validacion;
	}

	/*
	 * M�todo para validar Tel�fonos
	 */
	private  boolean validacionTelefonos() {
		boolean bol = true;
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		if (ms.getEnabledValidation()) {
			if (!listTelefonosB.isEmpty()) {
				Pattern pattern = Pattern.compile(Patrones.getPatternTelefono());
				Matcher matcher = null;
				for (TelefonosBean lit : listTelefonosB) {
					if (!lit.getTelefono().equals("--")&&!lit.getTelefono().isEmpty()) {
						matcher = pattern.matcher(lit.getTelefono());
						if (!matcher.matches()) {
							bol = false;
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alguno de el/Los Tel�fono/s no tiene el formato adecuado.", ""));

						}
					}
				}
			}
		}
		return bol;
	}

	/*
	 * Getters and Setters
	 */
	public TercerosBean getTercerosB() {
		return tercerosB;
	}
	public void setTercerosB(TercerosBean tercerosB) {
		this.tercerosB = tercerosB;
	}
	public List<CorreosBean> getListCorreosB() {
		return listCorreosB;
	}
	public void setListCorreosB(List<CorreosBean> listCorreosB) {
		this.listCorreosB = listCorreosB;
	}
	public List<DireccionesBean> getListDireccionesB() {
		return listDireccionesB;
	}
	public void setListDireccionesB(List<DireccionesBean> listDireccionesB) {
		this.listDireccionesB = listDireccionesB;
	}
	public List<TelefonosBean> getListTelefonosB() {
		return listTelefonosB;
	}
	public void setListTelefonosB(List<TelefonosBean> listTelefonosB) {
		this.listTelefonosB = listTelefonosB;
	}
	public List<DEHBean> getListDehB() {
		return listDehB;
	}
	public void setListDehB(List<DEHBean> listDehB) {
		this.listDehB = listDehB;
	}
	public List<TercerosBean> getListTercerosB() {
		return listTercerosB;
	}
	public void setListTercerosB(List<TercerosBean> listTercerosB) {
		this.listTercerosB = listTercerosB;
	}
}
