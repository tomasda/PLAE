package es.apt.ae.facade.utils.xml;

import com.opencanarias.exceptions.GenericFacadeException;

public class XMLException extends GenericFacadeException {

	public static final int 	ERROR_CODE_XML_GENERIC 				= 901;
	public static final String  ERROR_MESSAGE_XML_GENERIC 			= "XML error. ";
	
	private static final long serialVersionUID = 1L;
	
	
	static{

	}
	
	public XMLException(int code){
		super("" + code);
	}	
	
	public XMLException(int code, String message){
		super("" + code, null, message);
	}
}

