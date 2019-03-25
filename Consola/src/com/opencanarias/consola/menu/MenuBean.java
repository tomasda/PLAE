package com.opencanarias.consola.menu;

import java.io.Serializable;

public class MenuBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String key;
	private String value;

	public MenuBean(String k, String v) {
		this.key = k;
		this.value = v;
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
	
}