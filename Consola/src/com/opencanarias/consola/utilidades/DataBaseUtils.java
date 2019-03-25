package com.opencanarias.consola.utilidades;

import java.io.Serializable;
import java.sql.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.opencanarias.consola.commons.Constantes;
import com.opencanarias.consola.commons.LoadProperties;
import com.opencanarias.consola.menu.MenuSessionOption;

public class DataBaseUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DataBaseUtils.class);

	// *******************************************
	// *** Conexiones a BBDD ORACLE ***********
	// *******************************************

	public static Connection getConnection(String Schema) {
		LoadProperties lp = new LoadProperties();
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		try{
			if (lp.getParameter(Constantes.PROPERTIES_FILE,"defaultenvironment").equals("local")) {
				String cadena = lp.getParameter(Constantes.PROPERTIES_FILE, "localJDBC");
				Class.forName(cadena);
			}else {
				Class.forName(lp.getParameter(Constantes.PROPERTIES_FILE,"appDriver"));
			}
			Connection con  = null;
			switch (Schema) {
			case "EAREGISTRO":
				switch (ms.getEnvironmentSelected()) {
				case "pre":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "EAREGISTRO_PRE"));// "jdbc:oracle:thin:OC3F/OC3F@apt-ora3.aptf.local:1521/APTPRE");

					break;
				case "pro":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "EAREGISTRO_PRO"));

					break;
				case "desa":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "EAREGISTRO_DESA"));

					break;
				case "local":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "EAREGISTRO_LOCAL"), lp.getParameter(Constantes.PROPERTIES_FILE, "localUser"),lp.getParameter(Constantes.PROPERTIES_FILE, "localPass"));
					break;
				}
				break;
			case "OC3F":
				switch (ms.getEnvironmentSelected()) {
				case "pre":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "OC3F_PRE"));// "jdbc:oracle:thin:OC3F/OC3F@apt-ora3.aptf.local:1521/APTPRE");
					break;
				case "pro":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "OC3F_PRO"));
					break;
				case "desa":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "OC3F_DESA"));
					break;
				case "local":
					con = DriverManager.getConnection(lp.getParameter(Constantes.PROPERTIES_FILE, "OC3F_LOCAL"), lp.getParameter(Constantes.PROPERTIES_FILE, "localUser"),lp.getParameter(Constantes.PROPERTIES_FILE, "localPass"));
					break;
				}
				break;
			}
			return con;
		} catch (ClassNotFoundException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se encuentra la clase .... BBDD", ""));
		}catch (Exception e) {
			logger.error(Constantes.DATA_CONNECT_ERROR_1 + e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se puede establecer conexión con BBDD", ""));
		}
		return null;
	}

	public static void close(Connection con) {
		try{
			con.close();
		}catch (Exception e) {
		}

	}

}
