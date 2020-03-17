package com.opencanarias.apsct.portafirmas.utils;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextProvider {

	private static Context initialContext;

	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";
	
	public static final Integer JBOSS_4_X = 0;
	public static final Integer JBOSS_7_X = 1;

	public static Context getInitialContext(Integer jbossType) throws NamingException {
		//if (initialContext == null) {
			if (jbossType == JBOSS_4_X) {
				Properties properties = new Properties();
				properties.put("java.naming.provider.url", "jnp://192.168.2.27:4447/");
				properties.put("java.naming.factory.url", "org.jboss.naming:org.jnp.interfaces");
				properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
				initialContext = new InitialContext(properties);
			} else if (jbossType == JBOSS_7_X) {
				Properties properties = new Properties();
				//properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
//				properties.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
//				properties.put(Context.PROVIDER_URL, "remote://10.5.10.210:4447"); //habilitar binfig en jboss7 para que en el puerto 8089 escuche por jdmi
				properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
				
				
				initialContext = new InitialContext(properties);
			}
		//}
		return initialContext;
	}
}