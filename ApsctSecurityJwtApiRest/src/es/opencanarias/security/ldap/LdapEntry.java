package es.opencanarias.security.ldap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LdapEntry implements Serializable {
	private static final long serialVersionUID = 9147861157240967980L;
	private String entryDN;
	private Map<String, Object> attribs;

	public String getEntryDN() {
		return entryDN;
	}

	public void setEntryDN(String entryDN) {
		this.entryDN = entryDN;
	}

	public Map<String, Object> getAttribs() {
		if (attribs == null) {
			attribs = new HashMap<String, Object> ();
		}
		return attribs;
	}

	public void setAttribs(Map<String, Object> attribs) {
		this.attribs = attribs;
	}
}
