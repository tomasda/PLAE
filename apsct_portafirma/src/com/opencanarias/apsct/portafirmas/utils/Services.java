package com.opencanarias.apsct.portafirmas.utils;

import javax.naming.Context;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

//import com.opencanarias.apsct.modulo.generic.service.GenericFacadeServiceLocal;
//import com.opencanarias.apsct.modulo.generic.service.GenericFacadeServiceRemote;
import com.opencanarias.ejb.portafirmas.PortafirmasFacadeLocal;
import com.opencanarias.ejb.portafirmas.PortafirmasFacadeRemote;

public class Services {

	private static final Logger logger = Logger.getLogger(Services.class);
	
	private static PortafirmasFacadeLocal srvPortafirmasFacadeLocal = null;
//	private static GenericFacadeServiceLocal srvGenericFacadeLocal = null;
	private static PortafirmasFacadeRemote srvPortafirmasFacadeRemote = null;
//	private static GenericFacadeServiceRemote srvGenericFacadeRemote = null;
	
	public static PortafirmasFacadeLocal getSrvPortafirmasFacadeLocal() {
		if (srvPortafirmasFacadeLocal == null){
			String appName = "PortaFirmasFacadeEAR";//String appName = "ServicesFacadeEAR";
			String moduleName = "PortafirmasServiceFacadeEJB";
			String distinctName = ""; // Por defecto, salvo que especifiquemos uno adhock, este parametro estara vacio.
			String beanName = "PortafirmasFacadeBean";
			String interfaceName = PortafirmasFacadeLocal.class.getName();
			String lookupName = "ejb:/" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceName;//
			try {
				srvPortafirmasFacadeLocal = (PortafirmasFacadeLocal) doLookup(lookupName);
			} catch (ClassNotFoundException e) {
				logger.error("No se ha podido instanciar el servicio de Portafirmas de la Fachada.", e);
			}
		}
		return srvPortafirmasFacadeLocal;
	}
	
//	public static GenericFacadeServiceRemote getSrvGenericFacadeRemote() {
//		if (srvGenericFacadeRemote == null){
//			String appName =  "PortaFirmasFacadeEAR";//String appName = "ServicesFacadeEAR";
//			String moduleName = "GenericServiceFacadeEJB";
//			String beanName = "GenericFacadeServiceBean";
//			String interfaceName = GenericFacadeServiceRemote.class.getName();
//			String lookupName = "java:global/" + appName + "/" + moduleName + "/"  + beanName + "!" + interfaceName;
//			try {
//				srvGenericFacadeRemote = (GenericFacadeServiceRemote) doLookup(lookupName);
//			} catch (ClassNotFoundException e) {
//				logger.error("No se ha podido instanciar el servicio Generico de la Fachada.", e);
//			}
//		}
//		return srvGenericFacadeRemote;
//	}
	
//	public static GenericFacadeServiceLocal getSrvGenericFacadeLocal() {
//		if (srvGenericFacadeLocal == null){
//			String appName = "PortaFirmasFacadeEAR";//String appName = "ServicesFacadeEAR";
//			String moduleName = "GenericServiceFacadeEJB";
//			String distinctName = ""; // Por defecto, salvo que especifiquemos uno adhock, este parametro estara vacio.
//			String beanName = "GenericFacadeServiceBean";
//			String interfaceName = GenericFacadeServiceLocal.class.getName();
//			String lookupName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceName;
//			try {
//				srvGenericFacadeLocal = (GenericFacadeServiceLocal) doLookup(lookupName);
//			} catch (ClassNotFoundException e) {
//				logger.error("No se ha podido instanciar el servicio Generico de la Fachada.", e);
//			}
//		}
//		return srvGenericFacadeLocal;
//	}
	
	public static PortafirmasFacadeRemote getSrvPortafirmasFacadeRemote() {
		
		if (srvPortafirmasFacadeRemote == null){
			String appName = "PortaFirmasFacadeEAR";//String appName = "ServicesFacadeEAR";
			String moduleName = "PortafirmasServiceFacadeEJB";
			String beanName = "PortafirmasFacadeBean";
			String interfaceName = PortafirmasFacadeRemote.class.getName();
			String lookupName = "java:global/" + appName + "/" + moduleName + "/"  + beanName + "!" + interfaceName;
			try {
				srvPortafirmasFacadeRemote = (PortafirmasFacadeRemote) doLookup(lookupName);
			} catch (ClassNotFoundException e) {
				logger.error("No se ha podido instanciar el servicio Generico de la Fachada.", e);
			}
		}
		return srvPortafirmasFacadeRemote;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T doLookup(String lookupName) throws ClassNotFoundException { 
		Context context = null;
		T bean = null;
		try {
			context = ContextProvider.getInitialContext(ContextProvider.JBOSS_7_X);
			bean = (T) context.lookup(lookupName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
