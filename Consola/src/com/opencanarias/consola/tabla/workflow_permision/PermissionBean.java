package com.opencanarias.consola.tabla.workflow_permision;

public class PermissionBean {
	private int key;
	private String value;
	
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "PermissionBean [key=" + key + ", value=" + value + "]";
	}
	
	

}
