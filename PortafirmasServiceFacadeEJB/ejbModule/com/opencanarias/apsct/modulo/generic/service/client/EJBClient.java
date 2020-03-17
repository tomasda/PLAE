package com.opencanarias.apsct.modulo.generic.service.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import com.opencanarias.ejb.portafirmas.PortafirmasFacadeLocal;
import com.opencanarias.ejb.portafirmas.PortafirmasFacadeRemote;


public class EJBClient {

	private static final Logger logger = Logger.getLogger(EJBClient.class);
	
	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";
	
	public static final Integer JBOSS_4_X = 0;
	public static final Integer JBOSS_7_X = 1;

	private static Context initialContext;
	private static PortafirmasFacadeLocal portafirmasFacade = null;
	private static PortafirmasFacadeRemote portafirmasRemote = null;
	

	public static PortafirmasFacadeLocal getSrvGenericFacade() {
		if (portafirmasFacade == null){
			String appName = "PortaFirmasFacadeEAR";
			String moduleName = "PortafirmasServiceFacadeEJB";
			String beanName = "PortafirmasFacadeBean";
			String interfaceName = PortafirmasFacadeLocal.class.getName();
			String lookupName = "java:global/" + appName + "/" + moduleName +  "/" + beanName + "!" + interfaceName;
			try {
				portafirmasFacade = (PortafirmasFacadeLocal) doLookup(lookupName);
			} catch (ClassNotFoundException e) {
				logger.error("No se ha podido instanciar el servicio Generico de la Fachada.", e);
			}
		}
		return (PortafirmasFacadeLocal) portafirmasFacade;
	}
	public static PortafirmasFacadeRemote getSrvGenericFacadeRemote() {
		if (portafirmasRemote == null){
			String appName = "PortaFirmasFacadeEAR";
			String moduleName = "PortafirmasServiceFacadeEJB";
			String beanName = "PortafirmasFacadeBean";
			String interfaceName = PortafirmasFacadeRemote.class.getName();
			String lookupName = "ejb:" + appName + "/" + moduleName + "/" + beanName + "!" + interfaceName;
			try {
				portafirmasRemote = (PortafirmasFacadeRemote) doLookup(lookupName);
			} catch (ClassNotFoundException e) {
				logger.error("No se ha podido instanciar el servicio Generico de la Fachada.", e);
			}
		}
		return (PortafirmasFacadeRemote) portafirmasRemote;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T doLookup(String lookupName) throws ClassNotFoundException { 
		Context context = null;
		T bean = null;
		try {
			context = getInitialContext(JBOSS_7_X);
			bean = (T) context.lookup(lookupName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public static Context getInitialContext(Integer jbossType) throws NamingException {
		if (initialContext == null) {
			if (jbossType == JBOSS_4_X) {
				Properties properties = new Properties();
				properties.put("java.naming.provider.url", "jnp://192.168.2.27:4447/");
				properties.put("java.naming.factory.url", "org.jboss.naming:org.jnp.interfaces");
				properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
				initialContext = new InitialContext(properties);
			} else if (jbossType == JBOSS_7_X) {
				Properties properties = new Properties();
				properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
				initialContext = new InitialContext(properties);
			}
		}
		return initialContext;
	}
}
