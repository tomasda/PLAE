package com.opencanarias.apsct.portafirmas.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jboss.logging.Logger;

public class SignPAdESUtil {

	private static Logger logger = Logger.getLogger(SignPAdESUtil.class);

	public static int getNumberSigners(InputStream fis) {
		try {
			String body = new String(IOUtils.read(fis));
			String[] bodySplit = body.split("/Name\\(");
			return (bodySplit.length - 1);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return -1;
	}

	public static int getNumberSigners(byte[] cuerpo) {
		ByteArrayOutputStream baos = null;
		
		try {
			baos = new ByteArrayOutputStream();
			baos.write(cuerpo);
			baos.close();
			return getNumberSigners(baos);
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return -1;
	}

	public static int getNumberSigners(ByteArrayOutputStream byteArrayOutputStream) {
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		return getNumberSigners(bais);
	}
	
	public static int getNumberSigners(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error(e);
		}
		return getNumberSigners(fis);
	}
	
	public static int getNumberSignersTest(String filename) {
		File file = new File(filename);
		return getNumberSigners(file);	
	}

}
