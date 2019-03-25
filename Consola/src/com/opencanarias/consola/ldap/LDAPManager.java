package com.opencanarias.consola.ldap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.opencanarias.consola.commons.LoadProperties;

public class LDAPManager {
	protected String ACESS_USER;
	protected String ACESS_PASS;
	protected String NUM_IDENTIFICATION_FIELD;
	protected String LDAP_URL;
	protected String BASE_USER;
	protected String BASE_ALF;
	protected String UO;
	protected String MEMBEROF;

	public LDAPManager() {
		LoadProperties lp = new LoadProperties();
		ACESS_USER = lp.getEnvParameters("security.authentication.username");// loadProperties.loadPropertie("config",
		// "security.authentication.username"+environment);
		ACESS_PASS = lp.getEnvParameters("security.authentication.password");
		NUM_IDENTIFICATION_FIELD = lp.getEnvParameters("security.authentication.numIdentificationField");
		LDAP_URL = lp.getEnvParameters("security.authentication.ldapURL"); // "ldap://YOUR SERVER";
		UO = lp.getEnvParameters("security.authentication.ou");
		// La propiedad BASE se define en cada método de búsqueda, pues difiere tanto si es un grupo de Alfresco como si es un Usuario.
		// BASE = lp.getEnvParameters("security.authentication.base");// "OU=Users,OU=XXXXXX,DC=XXXX,DC=XXXXXX,DC=XXXX"; // YOUR SEARCH BASE IN
		BASE_USER = lp.getEnvParameters("security.authentication.base");// "OU=Users,OU=XXXXXX,DC=XXXX,DC=XXXXXX,DC=XXXX"; // YOUR SEARCH BASE IN
		BASE_ALF = lp.getEnvParameters("security.authentication.base.alfresco");// "OU=GRUPOS_ALFRESCO,DC=APTF,DC=LOCAL";
	}

	/**
	 * 
	 * Constructor para usar con MainLDAP
	 */
	public LDAPManager(String value, String environment, String fileName) {
		LoadProperties lp = new LoadProperties();
		ACESS_USER = lp.getEnvParameters(environment, "security.authentication.username", fileName);// loadProperties.loadPropertie("config",
		ACESS_PASS = lp.getEnvParameters(environment, "security.authentication.password", fileName);
		NUM_IDENTIFICATION_FIELD = lp.getEnvParameters("PRO.", "security.authentication.numIdentificationField", fileName);
		LDAP_URL = lp.getEnvParameters(environment, "security.authentication.ldapURL", fileName); // "ldap://YOUR SERVER";
		BASE_USER = lp.getEnvParameters(environment,"security.authentication.base", fileName);// "OU=Users,OU=XXXXXX,DC=XXXX,DC=XXXXXX,DC=XXXX"; // YOUR SEARCH BASE IN
		BASE_ALF = lp.getEnvParameters(environment,"security.authentication.base.alfresco", fileName);// "OU=GRUPOS_ALFRESCO,DC=APTF,DC=LOCAL";
		UO = "";
	}

	public LDAPPersonaBean findLDAPUser(String consulta) throws IOException, SQLException, ClassNotFoundException, NullPointerException {
		/**
		 * Parámetro para controlar el NIF 00000000T Existen dos usuarios con el mismo nif | adminaefs y director
		 */
		LDAPPersonaBean user = null;
		List<String> strings = null;
		// Obtener datos del LDAP
		consulta = "(" + NUM_IDENTIFICATION_FIELD + "=" + consulta + ")";
		List<Map<String, Object>> attrsList = authenticate(consulta,"person",BASE_USER);
		Map<String, Object> attrs = null;
		// Lógica de la consulta.
		/**
		 * Si no se encuentra el carlicense en el LDAP significa que el usuario ya no existe Por ello se establece un usuario vacío
		 */
		int numRows = 0;
		if (null == attrsList) {
			user = new LDAPPersonaBean();
			user.setCarLicense("00000000");
			user.setCn("Usuario no existente en el LDAP");
			user.setName("Usuario no existente en el LDAP");
		} else {
			numRows = attrsList.size();
		}
		int index = 0;
		if (numRows == 1) {
			user = new LDAPPersonaBean();
			attrs = attrsList.get(index);
			if (attrs != null) {
				for (String attrKey : attrs.keySet()) {
					if (attrs.get(attrKey) instanceof String) {
						// System.out.println(attrKey +": "+attrs.get(attrKey));
						if (attrKey.equals("cn")) {
							user.setCn((String) attrs.get(attrKey));
						}
						if (attrKey.equals("carLicense")) {
							user.setCarLicense((String) attrs.get(attrKey));
						}
						if (attrKey.equals("name")) {
							user.setName((String) attrs.get(attrKey));
						}
						if (attrKey.equals("mail")) {
							user.setMail((String) attrs.get(attrKey));
						}
						if (attrKey.equals("postalCode")) {
							user.setPostalCode((String) attrs.get(attrKey));
						}
						if (attrKey.equals("businessCategory")) {
							strings = new ArrayList<>();
							strings.add((String) attrs.get(attrKey));
							user.setBussinessCategory(strings);
						}
						if (attrKey.equals("sAMAccountName")) {
							user.setsAMAccountName((String) attrs.get(attrKey));
						}
						if (attrKey.equals("title")) {
							user.setTitle((String) attrs.get(attrKey));
						}
						if (attrKey.equals("department")) {
							user.setDepartment((String) attrs.get(attrKey));
						}
						if (attrKey.equals("description")) {
							user.setDescription((String) attrs.get(attrKey));
						}
						if (attrKey.equals("telephoneNumber")) {
							user.setTelephoneNumber((String) attrs.get(attrKey));
						}
					} else {
						if (attrKey.equals("businessCategory")) {
							strings = new ArrayList<>();
							for (Object o : (HashSet<?>) attrs.get(attrKey)) {
								strings.add(o.toString());
							}
							// Ordenamos la lista alfabeticamente.
							strings.sort(Comparator.naturalOrder());
							// SE REGISTRAN LOS ROLES DE DEPARTAMENTO
							user.setBussinessCategory(strings);
						}
						if (attrKey.equals("memberOf")) {
							strings = new ArrayList<>();
							for (Object o : (HashSet<?>) attrs.get(attrKey)) {
								strings.add(o.toString());
							}
							// Ordenamos la lista alfabeticamente.
							strings.sort(Comparator.naturalOrder());
							// SE REGISTRAN LOS ROLES DE APLICACIONES.
							user.setMemberOf(strings);
						}
					}
				}
			} else {
				System.out.println("Attributes are null!");
				user.setCarLicense("No existe el atributo");
				user.setCn("No existe el atributo");
			}
		} else if (numRows > 1)
			for (int a = 0; a < numRows; a++) {
				user = new LDAPPersonaBean();
				attrs = attrsList.get(a);
				if (attrs != null) {
					for (String attrKey : attrs.keySet()) {
						if (attrs.get(attrKey) instanceof String) {
							// System.out.println(attrKey +": "+attrs.get(attrKey));
							if (attrKey.equals("cn")) {
								user.setCn((String) attrs.get(attrKey));
							}
							if (attrKey.equals("carLicense")) {
								user.setCarLicense((String) attrs.get(attrKey));
							}
							if (attrKey.equals("name")) {
								user.setName((String) attrs.get(attrKey));
							}
							if (attrKey.equals("mail")) {
								user.setMail((String) attrs.get(attrKey));
							}
							if (attrKey.equals("postalCode")) {
								user.setPostalCode((String) attrs.get(attrKey));
							}
							if (attrKey.equals("businessCategory")) {
								strings = new ArrayList<>();
								strings.add((String) attrs.get(attrKey));
								user.setBussinessCategory(strings);
							}
							if (attrKey.equals("memberOf")) {
								strings = new ArrayList<>();
								strings.add((String) attrs.get(attrKey));
								user.setMemberOf(strings);
							}
							if (attrKey.equals("sAMAccountName")) {
								user.setsAMAccountName((String) attrs.get(attrKey));
							}
							if (attrKey.equals("title")) {
								user.setTitle((String) attrs.get(attrKey));
							}
							if (attrKey.equals("department")) {
								user.setDepartment((String) attrs.get(attrKey));
							}
							if (attrKey.equals("description")) {
								user.setDescription((String) attrs.get(attrKey));
							}
							if (attrKey.equals("telephoneNumber")) {
								user.setTelephoneNumber((String) attrs.get(attrKey));
							}
						} else {
							if (attrKey.equals("businessCategory")) {
								strings = new ArrayList<>();
								for (Object o : (HashSet<?>) attrs.get(attrKey)) {
									strings.add(o.toString());
								}
								// SE REGISTRAN LOS ROLES DE DEPARTAMENTO
								user.setBussinessCategory(strings);
							}
							if (attrKey.equals("memberOf")) {
								strings = new ArrayList<>();
								for (Object o : (HashSet<?>) attrs.get(attrKey)) {
									strings.add(o.toString());
								}
								// SE REGISTRAN LOS ROLES DE APLICACIONES.
								user.setMemberOf(strings);
							}
						}
					}
				}
				if (!user.getName().equals("adminaefs")) {
					break;
				}
			}
		// Devolver el usuario.
		return user;
	}

	public List<LDAPPersonaBean> findLDAPUsers(String consulta) throws IOException, SQLException, ClassNotFoundException, NullPointerException {
		List<LDAPPersonaBean> listaUsuarios = new ArrayList<LDAPPersonaBean>();
		// Obtener datos del LDAP
		List<Map<String, Object>> attrsList = authenticate(consulta,"person",BASE_USER); // "admin-ae3",password,consulta);
		Map<String, Object> attrs = null;
		// Lógica de la consulta.
		int numRows = attrsList.size();
		int index = 0;
		while (numRows > index) {
			LDAPPersonaBean user = new LDAPPersonaBean();
			attrs = attrsList.get(index);
			if (attrs != null) {
				for (String attrKey : attrs.keySet()) {
					if (attrs.get(attrKey) instanceof String) {
						// System.out.println(attrKey +": "+attrs.get(attrKey));
						if (attrKey.equals("cn")) {
							user.setCn((String) attrs.get(attrKey));
						}
						if (attrKey.equals("carLicense")) {
							user.setCarLicense((String) attrs.get(attrKey));
						}
						if (attrKey.equals("name")) {
							user.setName((String) attrs.get(attrKey));
						}
						if (attrKey.equals("mail")) {
							user.setMail((String) attrs.get(attrKey));
						}
						if (attrKey.equals("postalCode")) {
							user.setPostalCode((String) attrs.get(attrKey));
						}
						if (attrKey.equals("businessCategory")) {
							List<String> strings = null;
							strings = new ArrayList<>();
							strings.add((String) attrs.get(attrKey));
							user.setBussinessCategory(strings);
						}
						if (attrKey.equals("sAMAccountName")) {
							user.setsAMAccountName((String) attrs.get(attrKey));
						}
						if (attrKey.equals("title")) {
							user.setTitle((String) attrs.get(attrKey));
						}
						if (attrKey.equals("department")) {
							user.setDepartment((String) attrs.get(attrKey));
						}
						if (attrKey.equals("description")) {
							user.setDescription((String) attrs.get(attrKey));
						}
						if (attrKey.equals("telephoneNumber")) {
							user.setTelephoneNumber((String) attrs.get(attrKey));
						}
					} else {
						List<String> strings = null;
						if (attrKey.equals("businessCategory")) {
							strings = new ArrayList<>();
							for (Object o : (HashSet<?>) attrs.get(attrKey)) {
								strings.add((String) o);
							}
							// Ordenamos la lista alfabeticamente.
							strings.sort(Comparator.naturalOrder());
							// SE REGISTRAN LOS ROLES DE DEPARTAMENTO
							user.setBussinessCategory(strings);
						}
						if (attrKey.equals("memberOf")) {
							strings = new ArrayList<>();
							for (Object o : (HashSet<?>) attrs.get(attrKey)) {
								strings.add((String) o);
							}
							// Ordenamos la lista alfabeticamente.
							strings.sort(Comparator.naturalOrder());
							// SE REGISTRAN LOS ROLES DE APLICACIONES.
							user.setMemberOf(strings);
						}
					}
				}
			} else {
				System.out.println("Attributes are null!");
				user.setCarLicense("No existe el atributo");
				user.setCn("No existe el atributo");
			}
			index++;
			listaUsuarios.add(user);
		}
		listaUsuarios.sort(PersonaNameComparator);
		// Devolver la lista de usuarios.
		return listaUsuarios;
	}

	public List<LDAPGruposAlfrescoBean> findLDAPAlfrescoGroups(String consulta) throws IOException, SQLException, ClassNotFoundException, NullPointerException {
		List<LDAPGruposAlfrescoBean> listaGrupos = new ArrayList<LDAPGruposAlfrescoBean>();
		// Authenticator authenticate = new Authenticator();
		// Obtener datos del LDAP
		List<Map<String, Object>> attrsList = authenticate(consulta,"group",BASE_ALF); // "admin-ae3",password,consulta);
		Map<String, Object> attrs = null;
		// Lógica de la consulta.
		int numRows = attrsList.size();
		int index = 0;
		while (numRows > index) {
			LDAPGruposAlfrescoBean grupo = new LDAPGruposAlfrescoBean();
			attrs = attrsList.get(index);
			if (attrs != null) {
				for (String attrKey : attrs.keySet()) {
					if (attrs.get(attrKey) instanceof String) {
						// System.out.println(attrKey +": "+attrs.get(attrKey));
						if (attrKey.equals("cn")) {
							grupo.setCn((String) attrs.get(attrKey));
						}
						if (attrKey.equals("name")) {
							grupo.setName((String) attrs.get(attrKey));
						}
						if (attrKey.equals("sAMAccountName")) {
							grupo.setsAMAccountName((String) attrs.get(attrKey));
						}
						if (attrKey.equals("description")) {
							grupo.setDescription((String) attrs.get(attrKey));
						}
						if (attrKey.equals("member")) {
							List<String> strings = null;
							strings = new ArrayList<>();
							strings.add((String) attrs.get(attrKey));
							grupo.setMember(strings);
						}
					} else {
						List<String> strings = null;
						if (attrKey.equals("member")) {
							strings = new ArrayList<>();
							for (Object o : (HashSet<?>) attrs.get(attrKey)) {
								strings.add((String) o);
							}
							// SE REGISTRAN LOS ROLES DE DEPARTAMENTO
							grupo.setMember(strings);
						}
					}
				}
			} else {
				System.out.println("Attributes are null!");
				grupo.setsAMAccountName("No existe el atributo");
				grupo.setCn("No existe el atributo");
			}
			index++;
			listaGrupos.add(grupo);
		}
		listaGrupos.sort(AlfrescoNameComparator);
		// Devolver la lista de usuarios.
		return listaGrupos;
	}

	private static Comparator<LDAPPersonaBean> PersonaNameComparator = new Comparator<LDAPPersonaBean>() {
		public int compare(LDAPPersonaBean a, LDAPPersonaBean b) throws NullPointerException {
			String aName = a.getCn().toUpperCase();
			String bName = b.getCn().toUpperCase();
			return aName.compareTo(bName);
		}
	};
	private static Comparator<LDAPGruposAlfrescoBean> AlfrescoNameComparator = new Comparator<LDAPGruposAlfrescoBean>()  {
		public int compare(LDAPGruposAlfrescoBean a, LDAPGruposAlfrescoBean b) throws NullPointerException {
			String aName = a.getDescription().toUpperCase();
			String bName = b.getDescription().toUpperCase();
			return aName.compareTo(bName);
		}
	};

	private List<Map<String, Object>> authenticate(String consulta,String objectClass,String BASE) { // Map<String, Object> authenticate(String user, String pass, String consulta) {
		String returnedAtts[] = { "cn", "sn", "givenName", "name", "userPrincipalName", "displayName", "memberOf", "carLicense", "businessCategory",
				"postalCode", "mail", "sAMAcountName", "member", "description", "title", "sAMAccountName", "department", "telephoneNumber" };
		/*
		 * Diferentes Filtros de búsqueda para el LDAP.
		 * 
		 * Para devolver todos los resultados consulta -> cn=*
		 * 
		 * String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))"; // + user + "))";
		 * 
		 * PERSON"(&(objectClass=user)("+consulta+"))"; // + user + "))";
		 * 
		 * person = personas
		 * group = grupos 
		 * 
		 */
		String searchFilter = "(&(objectClass="+objectClass+")(" + consulta + "))"; 
		// Create the search controls
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(returnedAtts);

		// Specify the search scope
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		StringBuilder baseSearch = new StringBuilder();
		if (UO != null && !UO.equals("")) {
			baseSearch.append(UO + ",");
		}
		baseSearch.append(BASE);
		String BASE_SEARCH = baseSearch.toString();
		LdapContext ctxGC = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			try {
				env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.SECURITY_AUTHENTICATION, "Simple");
				// it can be <domain\\userid> something that you use for windows login
				// it can also be
				env.put(Context.SECURITY_PRINCIPAL, ACESS_USER);
				env.put(Context.SECURITY_CREDENTIALS, ACESS_PASS);
				// in following property we specify ldap protocol and connection url.
				// generally the port is 389
				env.put(Context.PROVIDER_URL, LDAP_URL);
				ctxGC = new InitialLdapContext(env, null);
			} catch (NamingException nex) {
				System.out.println("LDAP Connection: FAILED");
				nex.printStackTrace();
			}
			// This is the actual Authentication piece. Will throw javax.naming.AuthenticationException
			// if the users password is not correct. Other exceptions may include IO (server not found) etc.
			// ctxGC = new InitialLdapContext(env, null);

			// Now try a simple search and get some attributes as defined in returnedAtts
			NamingEnumeration<SearchResult> answer = ctxGC.search(BASE_SEARCH, searchFilter, searchCtls);

			// crea una lista de resultados
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			// mapList.add(new TreeMap<String, Object>());
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				Map<String, Object> amap = null;
				if (attrs != null) {
					amap = new HashMap<String, Object>();
					NamingEnumeration<?> ne = attrs.getAll();
					while (ne.hasMore()) {
						Attribute attr = (Attribute) ne.next();
						if (attr.size() == 1) {
							amap.put(attr.getID(), attr.get());
						} else {
							HashSet<String> s = new HashSet<String>();
							NamingEnumeration<?> n = attr.getAll();
							while (n.hasMoreElements()) {
								s.add((String) n.nextElement());
							}
							amap.put(attr.getID(), s);
						}
					}
					ne.close();
				}
				ctxGC.close(); // Close and clean up
				// return amap;
				mapList.add(amap);
			}
			return mapList;
		} catch (NamingException nex) {
			nex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}


}
