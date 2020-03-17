package com.opencanarias.apsct.portafirmas.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class FilestreamUtils {
	
	public static void writeFile(String filename, byte[] dataToSave) throws IOException{
		final OutputStream fos = new FileOutputStream(filename);
		final BufferedOutputStream bos = new BufferedOutputStream(
			fos,
			dataToSave.length
		);
		bos.write(dataToSave);
		bos.flush();
		fos.close();
	}

	public static void deleteFile(String filename){
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static String getFileContent (String filename) throws IOException{
		BufferedReader br = null;
		StringBuilder contentConstructor = new StringBuilder();
		String sCurrentLine;

		br = new BufferedReader(new FileReader(filename));
		while ((sCurrentLine = br.readLine()) != null) {
			contentConstructor.append(sCurrentLine);
		}
		if (br != null){
			br.close();
		}
		return contentConstructor.toString();
	}
	
}
