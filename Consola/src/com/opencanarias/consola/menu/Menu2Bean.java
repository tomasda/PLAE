package com.opencanarias.consola.menu;

import java.io.Serializable;

public class Menu2Bean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String key;
	private String value;
	private String url;

	public Menu2Bean(String k, String v, String u) {
		        this.key = k;
		        this.value = v;
		        this.url = u;
	}

	public String getKey() {
	    return key;
	}
	
	public void setKey(String keyId) {
	    this.key = keyId;
	}
	
	public String getValue() {
	    return value;
	}
	
	public void setValue(String valueName) {
	    this.value = valueName;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}