package es.opencanarias.security.ldap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.jboss.logging.Logger;

import es.opencanarias.commons.utils.ConfigUtils;

public class LdapUtils {
	private static final Logger logger = Logger.getLogger("LoginUtils");

	private static LdapUtils instance = null;
	private String ldapRootSearch;
	private String ldapURL;
	private String identificationField;
	private String memberOfField;
	private String memberField;
	private String adminUsername;
	private String adminPassword;	

	public LdapUtils () {
		super();
	}
	
	public static synchronized LdapUtils getInstance () {		
		if (instance == null) {
			Map<String, String> options = new HashMap<String, String>();
			options.put("ldapRootSearch", ConfigUtils.getParametro("es.opencanarias.security.ldapRootSearch"));
			options.put("ldapURL", ConfigUtils.getParametro("es.opencanarias.security.ldapURL"));
			options.put("adminUsername", ConfigUtils.getParametro("es.opencanarias.security.adminUsername"));
			options.put("adminPassword", ConfigUtils.getParametro("es.opencanarias.security.adminPassword"));
			options.put("identificationField", ConfigUtils.getParametro("es.opencanarias.security.identificationField"));
			options.put("memberOfField", ConfigUtils.getParametro("es.opencanarias.security.memberOfField"));
			options.put("memberField", ConfigUtils.getParametro("es.opencanarias.security.memberField"));
			instance = new LdapUtils(options);
		}
		return instance;
	}
	
	public LdapUtils (Map<String, String> options) {
		super();
		this.ldapRootSearch = options.get("ldapRootSearch");
		this.ldapURL = options.get("ldapURL");
		this.identificationField = options.get("identificationField");
		this.adminUsername = options.get("adminUsername");
		this.adminPassword = options.get("adminPassword");
		this.memberOfField = options.get("memberOfField");
		this.memberField = options.get("memberField");
	}
	
	@SuppressWarnings("unchecked")
	public Boolean isUserAuthorizate(String usuario, String app) throws NamingException {
		try {
			Boolean result = Boolean.FALSE;
			DirContext context = login(getAdminUsername(), getAdminPassword());
			logger.info("[APSCT-SECURITY] Validado el usuario [" + getAdminUsername()+ "]");
			logger.info("[APSCT-SECURITY] Verificando si << " + usuario + " >> tiene acceso a la aplicacion [[ " + app + " ]]");
	
			List<LdapEntry> resultSearch = getEntriesFromLdap(context, getLdapRootSearch(), getIdentificationField(), usuario, 
					Arrays.asList(memberOfField) // null
					);
			if (resultSearch != null && !resultSearch.isEmpty()) {
				logger.info("[APSCT-SECURITY] Se ha encontrado al usuario [" + usuario + "] ...");
				LdapEntry datos = resultSearch.get(0);
				Map<String, Object> attribs = datos.getAttribs();
				StringBuilder sb = new StringBuilder("[APSCT-SECURITY] Valores de los atributos de LDAP: \n");
				for (String key: attribs.keySet()) {
					if (key.equalsIgnoreCase(getMemberOfField())) {
						for (String grupo: ((List<String>)attribs.get(key))) {
							if (grupo.equalsIgnoreCase(URLDecoder.decode(app, "UTF-8"))) {
								result = Boolean.TRUE;
								break;
							}
						}
					}
					sb.append("\t" + key + " = " + "{" + attribs.get(key).getClass().getSimpleName() + "} " + attribs.get(key) + "\n");
				}
				logger.debug(sb.toString());
			}

			return result;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	private DirContext login(String usernameDN, String password) throws NamingException {
		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, getLdapURL());
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, usernameDN);
		environment.put(Context.SECURITY_CREDENTIALS, password);
		return new InitialDirContext(environment);
	}

	public List<LdapEntry> getEntriesFromLdap (DirContext context, String ldap_root_search, String attrSrchName, String attrSrchValue, List<String> attrsIDSearch) {
		List<LdapEntry> result = new ArrayList<LdapEntry> ();
		SearchControls ctrls = new SearchControls();
		ctrls.setReturningObjFlag(true);
		ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		try {
			NamingEnumeration<SearchResult> ne = context.search(ldap_root_search, attrSrchName + "=" + attrSrchValue, ctrls);			
			while (ne.hasMore()) { 
				SearchResult entry = (SearchResult) ne.next();
				LdapEntry ldapEntry = new LdapEntry();
				ldapEntry.setEntryDN(entry.getNameInNamespace());
				Attributes entryAttrbs = entry.getAttributes();
				NamingEnumeration<String> userAttrbs = entryAttrbs.getIDs();
				if (userAttrbs != null) {
					while (userAttrbs.hasMore()) {
						try {
							String attrID = (String) userAttrbs.next();	
							if ((attrsIDSearch != null && attrsIDSearch.contains(attrID)) || (attrsIDSearch == null)) {
								ldapEntry.getAttribs().put(attrID, retrieveAttribValue(entryAttrbs, attrID));
							}
						} catch (NamingException e) {
							logger.error(e);
						}
					}
				}
				result.add(ldapEntry);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	private List<Object> retrieveAttribValue(Attributes attrbs, String attrID) throws NamingException {
		List<Object> result = new ArrayList<Object>();
		NamingEnumeration<?> attrValues = attrbs.get(attrID).getAll();
		if (attrValues != null) {
			while (attrValues.hasMoreElements()) {
				result.add(attrValues.next());
			}
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	private List<String> createListFromStrings(String s1, String s2, String s3) {
		List<String> result = new ArrayList<String>();
		if (s1 != null && !s1.isEmpty())
			result.add(s1);
		if (s2 != null && !s2.isEmpty())
			result.add(s2);
		if (s3 != null && !s3.isEmpty())
			result.add(s3);
		return result;		
	}	
	
	private String getLdapRootSearch() {
		return ldapRootSearch;
	}

	public void setLdapRootSearch(String ldapRootSearch) {
		this.ldapRootSearch = ldapRootSearch;
	}

	private String getLdapURL() {
		return ldapURL;
	}

	public void setLdapURL(String ldapURL) {
		this.ldapURL = ldapURL;
	}

	private String getIdentificationField() {
		return identificationField;
	}

	public void setIdentificationField(String identificationField) {
		this.identificationField = identificationField;
	}

	private String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	private String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getMemberOfField() {
		return memberOfField;
	}

	public void setMemberOfField(String memberOfField) {
		this.memberOfField = memberOfField;
	}

	public String getMemberField() {
		return memberField;
	}

	public void setMemberField(String memberField) {
		this.memberField = memberField;
	}

	public static void main (String [] args) throws UnsupportedEncodingException {
		String parametroCodificado = URLEncoder.encode("cn=USR_AE_Porta-Firmas,ou=usuarios,ou=superEntidad,ou=apsct,dc=APTF,dc=LOCAL","UTF-8");
		String url = "http://10.5.10.226:8089/ApsctSecurityJwtApiRest/security/validarAccesoApp?usuario=empFacturacion&app=" + parametroCodificado;
		System.out.println(url);;
	}

}
