package com.opencanarias.consola.utilidades;

import com.opencanarias.consola.commons.Constantes;
import com.opencanarias.consola.commons.LoadProperties;


public class VersionUtils {

	public VersionUtils(){

	}

	public String getVersionDate() {
		LoadProperties lp = new LoadProperties();
		String versionDate=lp.getParameter(Constantes.PROPERTIES_FILE,"versionDate");
		return versionDate;
	}

	public String getVersion() {
		LoadProperties lp = new LoadProperties();
		String version=lp.getParameter(Constantes.PROPERTIES_FILE,"version");
		return version;
	}
	
}

