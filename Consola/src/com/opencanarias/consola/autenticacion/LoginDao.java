package com.opencanarias.consola.autenticacion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.persistence.NoResultException;
import org.jboss.logging.Logger;

import com.opencanarias.api.security.utils.LoginUtils;
import com.opencanarias.consola.commons.Constantes;
import com.opencanarias.consola.commons.LoadProperties;


public class LoginDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginDao.class);
	private String CONFIG_FILE = "config";
	private String LDAP_URL;
	private String BASE_USERS;
	private String BASE_ALF_GROUPS;
	private String OU;
	private String ACCESS_USER;
	private String ACCESS_PASS;
	private String MAIL;
	private String ROLE_FIELD;
	private String IDENTIFICATION_FIELD;
	private String MEMBER_OF_FIELD = "memberOf";	
	private String MEMBER_FIELD = "member";
	private String DISTINGUISHED_NAME_FIELD = "distinguishedName";
	//private EntityManagerFactory factory = Persistence .createEntityManagerFactory("Consola"); 
	//private EntityManager em = factory.createEntityManager(); 

	public LoginDao() {
		LoadProperties lp = new LoadProperties();
		String environment=lp.getParameter(Constantes.PROPERTIES_FILE,"defaultenvironment");
		BASE_USERS = lp.getEnvParameters(environment, "security.authentication.base", CONFIG_FILE);
		LDAP_URL = lp.getEnvParameters(environment, "security.authentication.ldapURL", CONFIG_FILE);
		BASE_ALF_GROUPS= lp.getEnvParameters(environment, "security.authentication.base.alfresco", CONFIG_FILE);
		OU = lp.getEnvParameters(environment, "security.authentication.ou", CONFIG_FILE);
		ACCESS_USER = lp.getEnvParameters(environment, "security.authentication.username", CONFIG_FILE);
		ACCESS_PASS = lp.getEnvParameters(environment, "security.authentication.password", CONFIG_FILE);
		ROLE_FIELD = lp.getEnvParameters(environment, "security.authentication.rolField", CONFIG_FILE);
		MAIL = lp.getEnvParameters(environment, "security.authentication.emailField", CONFIG_FILE);
		IDENTIFICATION_FIELD = lp.getEnvParameters(environment, "security.authentication.identificationField", CONFIG_FILE);

	}


	public LoginBean getUser(String username, String password) { 
		boolean authorization = false;
		//		try { LoginBean user = (LoginBean) em 
		//				.createQuery( "SELECT u from LoginBean u where u.nameUser = :name and u.password = :password") 
		//				.setParameter("name", nameUser) 
		//				.setParameter("password", password)
		//				.getSingleResult(); 

		LoadProperties lp = new LoadProperties();
		String environment = lp.getParameter("config","defaultenvironment");
		if (!environment.equals("local")) {
			try {
				LoginBean user = new LoginBean();
				user.setNameUser(username);
				user.setPassword(password);
				Map<String, String> options = new HashMap<String, String>();
				options.put("baseUsers", BASE_USERS);
				options.put("baseAlfGroups", BASE_ALF_GROUPS);
				options.put("ou", OU);
				options.put("ldapURL", LDAP_URL);
				options.put("username", ACCESS_USER);
				options.put("password", ACCESS_PASS);
				options.put("emailField", MAIL);
				options.put("identificationField", IDENTIFICATION_FIELD);
				options.put("rolField", ROLE_FIELD);
				options.put("memberOfField", MEMBER_OF_FIELD);
				options.put("memberField", MEMBER_FIELD);		
				options.put("distinguishedNameField", DISTINGUISHED_NAME_FIELD);
				try {
					HashMap<String, Object> userProperties = LoginUtils.doLogin(username, password, options);
					//				System.out.println("Resultado:" + userProperties);
					if (userProperties != null) {
						String[] roles = (String[])userProperties.get("roles");
						for (String rol:roles) {
							//						System.out.println("Rol: " + rol);
							if(rol.equals("SuperAdministrador")) {
								authorization = true;
							}
						}	
					}
				} catch (NamingException e) {
					authorization = false;
				}
				if (!authorization)
					return null;
				return user; 
			} catch (NoResultException e) {
				logger.error(e);
				return null; 
			}
		}else {
			LoginBean user = new LoginBean();
			user.setNameUser(username);
			user.setPassword(password);
			return user;
		}
	} 

	public boolean insertUser(LoginBean user) { 
		try {
			//em.persist(user);
			return true; 
		} catch (Exception e) {
			e.printStackTrace(); return false; 
		} 
	} 

	public boolean deleteUser(LoginBean user) {
		try {
			//em.remove(user); 
			return true; 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	} 	
}
