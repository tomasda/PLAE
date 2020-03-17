package es.apt.ae.facade.utils.xml;

import java.io.Serializable;
import java.util.HashMap;

public class CommonBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> map;

	/**
	 * 
	 * <b>Constructor:</b> CommonBean
	 */
	public CommonBean() {
		this.map = new HashMap<String, Object>();
	}
	
	public CommonBean(HashMap<String, Object> map) {
		this.map = map;
	}
	
	public HashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

	public void setProperty(String propertyName, Object value) {
		map.put(propertyName, value);
	}

	public Object getPropertyAsObject(String propertyName) {
		return map.get(propertyName);
	}
	
	public String getProperty(String propertyName) {
		return (String) map.get(propertyName);
	}

}
