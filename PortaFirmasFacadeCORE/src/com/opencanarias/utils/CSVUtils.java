package com.opencanarias.utils;

import ee.sk.digidoc.SignedDoc;

/**
 * Clase de utilidades para el cálculo del CSV de los documentos
 * @author OpenCanarias S.L.
 *
 */
public class CSVUtils {

	public static String getDigest(byte[] docFirmado) {
		String result = "";
		try {
			byte[] digest = SignedDoc.digest(docFirmado);
			result = SignedDoc.bin2hex(digest);
		} catch (Exception e) {
		}
		return result;
	}
}
