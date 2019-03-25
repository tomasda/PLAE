package com.opencanarias.consola.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

import com.opencanarias.consola.menu.MenuSessionOption;

public class LoadProperties {
	private static Logger logger = Logger.getLogger(LoadProperties.class);
	
	public String getParameter(String fichero, String variable){
		String data=null;
		String propertyFile = System.getProperty(Constantes.SYSTEM_PROPRETIES_FOLDER);
		File file = new File(propertyFile+"/consola/"+fichero+".properties");
		Properties configuration = new Properties();
        try {
			configuration.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(Constantes.LOGIN_PROPERTIES_ERROR_1);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(Constantes.LOGIN_PROPERTIES_ERROR_2 + e);
		}
		data = configuration.getProperty(variable);
		//System.out.println(data);
		return data;
	}
	
	public String getEnvParameters(String param) {
		MenuSessionOption ms = (MenuSessionOption) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuSessionOption");
		String parameter = null;
		String environment = null;
		switch (ms.getEnvironmentSelected()) {
		case "local":
			environment = "LOCAL.";
			break;
		case "desa":
			environment = "DESA.";
			break;
		case "pre":
			environment = "PRE.";
			break;
		case "pro":
			environment = "PRO.";
			break;
		}
		parameter = getParameter(Constantes.PROPERTIES_FILE, environment+param);
			
		return parameter;
	}
	
	public String getEnvParameters(String environment,String param,String fileName) {
		String parameter = null;
		String propertyFile = System.getProperty(Constantes.SYSTEM_PROPRETIES_FOLDER);
		File file = null;
		if (fileName.contains("\\")) {
			file = new File(fileName);
		}else {
			file = new File(propertyFile+"/consola/"+fileName+".properties");
		}
		Properties configuration = new Properties();
        try {
			configuration.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(Constantes.LOGIN_PROPERTIES_ERROR_1);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(Constantes.LOGIN_PROPERTIES_ERROR_2 + e);
		}
        if (null!=environment && null!=param)
        	parameter = configuration.getProperty(environment+param);
		return parameter;
	}
}
