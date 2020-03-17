package es.apt.ae.facade.dto;

import java.io.Serializable;

public class Resultado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String SUCCESS = "success";
	public static final String WARN = "warn";
	public static final String FAIL = "fail";
	
	private String status;
	private Object data;
	private String message;
	private String code;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
