package com.opencanarias.apsct.portafirmas.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Libreria de utilidades relacionadas con operaciones de Entrada/Salida
 * 
 * @author Open Canarias S.L. (David Prieto Gonzal�z)
 */
public class IOUtils {
	/**
	 * Lee un InputStrem a un Array de Bytes
	 * 
	 * @param is
	 *            InputStream
	 * @return Array de bytes
	 * @throws IOException
	 */
	public static byte[] read(InputStream is) throws IOException {
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[4096];
			int read = 0;
			while ((read = is.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();
	}

}