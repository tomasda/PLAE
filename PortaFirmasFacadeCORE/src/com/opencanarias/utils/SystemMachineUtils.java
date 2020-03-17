package com.opencanarias.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jboss.logging.Logger;

public class SystemMachineUtils {
	
	private static Logger logger = Logger.getLogger(SystemMachineUtils.class);

	public static String getHostName(){
	    String host = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			host = ip.getHostName();
		} catch (UnknownHostException e) {
			logger.info("No se ha podido obtener el nombre de la máquina");
		}
    	if (host != null && !host.trim().equals("")) {
    		if (host.endsWith(".aptf.local")) {
    			host = host.substring(0, host.indexOf('.'));
    		}
    	}	
		return host;
	}
}
