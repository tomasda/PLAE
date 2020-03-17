package com.opencanarias.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Clase de utilidades para trabajar con excepciones
 * @author Open Canarias S.L.
 *
 */
public class ExceptionUtils {

	public static String stackTraceToString (Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();		
	}

}
