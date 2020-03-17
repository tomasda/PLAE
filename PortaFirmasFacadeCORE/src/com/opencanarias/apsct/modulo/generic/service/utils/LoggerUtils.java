package com.opencanarias.apsct.modulo.generic.service.utils;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.modulo.generic.service.client.EJBClient;
//import com.opencanarias.exceptions.PortafirmasFacadeException;
//import com.opencanarias.apsct.modulo.generic.service.client.EJBClient;
import com.opencanarias.utils.StringUtils;

import es.apt.ae.facade.entities.User;

public class LoggerUtils {
	@PersistenceContext(unitName = "facade-pu")
	private EntityManager em;
	
	public static Map<String, String> backofficesMap = null;
	public static Map<String, String> usuariosDepartamentosMap = null;
	public static Map<String, String> tercerosMap = null;
	
	
	public User doLogin(String username,String password) {
		
		User result = EJBClient.getSrvGenericFacadeRemote().doLogin( username, password); //(String) q.getSingleResult();
		if(null!=result) {
			return result;
		}
		return null;
	}

	public static void showError(Logger logger, String message) {
		logger.error(message);
	}
	
	public static void showError(Logger logger, String message, Throwable e) {
		logger.error(message, e);
	}
	
	public static void showInfo(Logger logger, String message) {
		logger.info(message);
	}
	
	public static void showError(Logger logger, String message, Throwable e, String credentials, String user) {
		message = addRequestInfo(message, credentials, user);
		logger.error(message, e);
	}
	
	public static void showInfo(Logger logger, String message, String credentials, String user) {
		message = addRequestInfo(message, credentials, user);
		logger.info(message);
	}
	
	public static void showWarning(Logger logger, String message) {
		logger.warn(message);
	}
	
	public static void showWarning(Logger logger, String message, String credentials, String user) {
		message = addRequestInfo(message, credentials, user);
		logger.warn(message);
	}
	
	public static String addRequestInfo(String message, String credentials, String user) {
		if (null == backofficesMap || !backofficesMap.containsKey(credentials)) {
			backofficesMap = EJBClient.getSrvGenericFacadeRemote().consultarDescripcionesBackoffices();
		}
		String backoffice = credentials;
		if (backofficesMap.containsKey(credentials)) {
			backoffice = backofficesMap.get(credentials);
		}

		String userDetails = null;
		if (!StringUtils.isNullOrEmpty(user)) {
			String[] userSplit = user.split("\\|");
			if (!(userSplit.length == 2)) {
				if (null == usuariosDepartamentosMap || !usuariosDepartamentosMap.containsKey(user.toUpperCase())) {
					usuariosDepartamentosMap = EJBClient.getSrvGenericFacadeRemote().consultarUsuariosDepartamentosMap(null, true);
				}	
				if (usuariosDepartamentosMap.containsKey(user.toUpperCase())) {
					userDetails = usuariosDepartamentosMap.get(user.toUpperCase());
				} 
			}
			/*
			 * Validación de datos de Terceros.
			 * Esta validación es usada para que los usuarios que están fuera de la Autoridad Portuaria
			 * puedan hacer login.
			 */
//			if (userDetails == null) {
//				String user1 = userSplit[0];
//				String user2 = ((userSplit.length == 2)?userSplit[1]:user1);
//				if (null == tercerosMap || !tercerosMap.containsKey(user1) || !tercerosMap.containsKey(user2)) {
////					tercerosMap = EJBClient.getSrvGenericFacade().consultarTercerosEAdminMap();
//				}
//				if (userSplit.length == 1) {
//					if (tercerosMap.containsKey(user)) {
//						userDetails = tercerosMap.get(user);
//					}
//				} else if (userSplit.length == 2) {
//					if (tercerosMap.containsKey(user1) && tercerosMap.containsKey(user2)) {
//						userDetails = tercerosMap.get(user1) + " EN REPRESENTACION DE " + tercerosMap.get(user2);
//					}
//				}
//			}
		}
		
		if (backoffice != null || user != null) {
			message = "(backoffice:" + backoffice + ",user:" + user + ",userDetails:" + userDetails + ") " + message;
		}
		return message;
	}
	
	

}
