package com.opencanarias.consola.csv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.activation.DataHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import org.jboss.ws.extensions.xop.jaxws.ByteArrayDataSource;
import org.xml.sax.InputSource;

import com.opencan.office.beans.AFirmaDocument;
import com.opencan.office.beans.parsers.AFirmaParser;

import ee.sk.digidoc.SignedDoc;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

public class FileUtils {
	
	public static final String APPLICATION_PDF 				= "application/pdf";
	public static final String APPLICATION_MS_WORD 			= "application/msword";
	public static final String APPLICATION_OO_WRITE 		= "application/vnd.oasis.opendocument.text";
	public static final String APPLICATION_OCTEM_STREAM		= "application/octet-stream";
	public static final String TEXT_XML 					= "text/xml";
	public static final String PDF_DOCUMENT_TYPE = "pdf";

	
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] data = null;

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			for (int read = inputStream.read(); read != -1; read = inputStream.read()) {
				out.write(read);
			}
			data = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}
	
	/**
	 * Devuelve un array de bytes con el contenido del fichero local pasado por
	 * parametro
	 * 
	 * @param path
	 *            String con la ruta al fichero
	 * 
	 * @return Array de bytes con el contenido del fichero
	 * 
	 * @throws IOException
	 *             Ante cualquier error i/o
	 */
	public static byte[] readLocalFile(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		return readInputStream(fis);
	}
	
	public static String getContentType(String documentName, byte[] documentData) {
		String docContentType = null;
		
        String docExtension = getExtension(documentName);
        if (docExtension != null) {
        	docContentType = getContentType(documentName);
        }
        if (docContentType == null) {
        	docContentType = getContentType(documentData);
        }	
        
        return docContentType;
	}
	
	public static String getContentType(String documentName) {
		String docContentType = null;
		
        String docExtension = getExtension(documentName);
        if (docExtension != null) {
        	if (docExtension.equalsIgnoreCase("odt")) {
        		docContentType = APPLICATION_OO_WRITE; 
        	} else if (docExtension.equalsIgnoreCase("doc") || docExtension.equalsIgnoreCase("docx")) {
        		docContentType = APPLICATION_MS_WORD;             		
        	} else if (docExtension.equalsIgnoreCase("pdf")) {
        		docContentType = APPLICATION_PDF;
        	} 	            	
        }	
        
        return docContentType;
	}
	
	public static String getContentType(byte[] data) {
		try {
			MagicMatch match = Magic.getMagicMatch(data);
	        return match.getMimeType();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public static String getDigest(byte[] docFirmado) {
		String result = "";

		try {
			byte[] digest = SignedDoc.digest(docFirmado);
			result = SignedDoc.bin2hex(digest);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	public static byte[] getDocument(InputStream docFirmado) {
		AFirmaDocument afirmaDoc = null;
		byte[] bytes = null;
		String afirmaDocContent = null;

		// Parseamos el documento firmado
		try {
			bytes = FileUtils.readInputStream(docFirmado);
			afirmaDoc = parseAfirmaDocument(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Si es un documento de @firma verificamos la firma
		if (afirmaDoc != null) {
			afirmaDocContent = afirmaDoc.getContent();
			// Devolvemos el array de bytes del documento original sin firmar
			return afirmaDocContent.getBytes();
		}
		
		return null;
	}
	
	private static AFirmaDocument parseAfirmaDocument(byte[] docFirmado) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(docFirmado);
		InputSource docSource = new InputSource(bais);
		AFirmaDocument afirmaDoc = null;

		AFirmaParser parser = new AFirmaParser();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		SAXParser saxParser;
		try {
			saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(docSource, parser);
			afirmaDoc = parser.getAfirmaDoc();
			return afirmaDoc;

		} catch (Exception e) {
			throw e;
		}
	}	
	
	
	public static String getExtension(String documentName) {
        int indexExt = documentName.lastIndexOf(".");
        if (indexExt != -1) {
            return documentName.substring(indexExt + 1, documentName.length());
        }
        return null;
	}	
	
	public static String getExtensionWithDot(String documentName) {
        int indexExt = documentName.lastIndexOf(".");
        if (indexExt != -1) {
            return documentName.substring(indexExt, documentName.length());
        }
        return null;
	}
	
	public static String changeExtension(String documentName, String newExtension) {
        String newDocumentName = null;
		if (documentName != null) {
			String[] strings = documentName.split("\\.");
			// Tiene extension
			if (strings.length > 0) {					
				String docNameSinExt = documentName.substring(0,documentName.indexOf("." + strings[strings.length - 1]));
				newDocumentName = docNameSinExt + "." + newExtension;
			} 
			// No tiene extension
			else {
				newDocumentName = documentName + "." + newExtension;;
			}		
		}
		return newDocumentName;
	}
	
	public static String getNameWithoutExt(String documentName) {
		String docNameSinExt = null;
		if (documentName != null) {
			String[] strings = documentName.split("\\.");
			// Tiene extension
			if (strings.length > 1) {					
				docNameSinExt = documentName.substring(0,documentName.indexOf("." + strings[strings.length - 1]));
			} 
			// No tiene extension
			else {
				docNameSinExt = documentName;
			}		
		}
		return docNameSinExt;
	}	
	
	public static byte[] formatNewLines(byte[] data) {
		String dataStr = new String(data);
		if (dataStr.split("\\r\\n").length <= 1) {
			dataStr = dataStr.replaceAll("\\n", "\r\n");
		}
		//dataStr = dataStr.replaceAll("\\n", String.format("%n"));
		return dataStr.getBytes();
	}	
	
	public static byte[] deleteNewLinesAndTabs(byte[] data) {
		String dataStr = new String(data);
		dataStr = dataStr.replaceAll("\\r\\n", "");
		dataStr = dataStr.replaceAll("\\t", "");
		return dataStr.getBytes();
	}	
	
	public static String normalize(String fileName) {
		String result = new String(fileName);

		result = Normalizer.normalize(result, Form.NFD);
	    result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		result = result.replaceAll("&", "y");
		result = result.replaceAll("[^a-zA-Z0-9-]", "_");		
		
		return result;
	}
	
	/**
	 * Codificacion del nombre de un fichero
	 */
	public static String encodeFileName(String fileName, int maxSizeName){
		if (fileName != null) {
			String[] strings = fileName.split("\\.");
			// Tiene extension
			if (strings.length > 1) {					
				String nombre = fileName.substring(0,fileName.indexOf("." + strings[strings.length - 1]));
				 // Eliminar caracteres extraños en el nombre del fichero.
				nombre = normalize(nombre);
				// Si el nombre empieza por un numero añadir un guion bajo al principio.
				if (nombre.matches("\\d.*")) {
					nombre = "_" + nombre;
				}
				// Limitar el nombre del fichero a 80 caracteres (incluye extension).
				String extension = "." + strings[strings.length - 1];
				if ((nombre.length() + extension.length()) > maxSizeName) {
					nombre = nombre.substring(0, (maxSizeName - extension.length()));
				}	
				fileName = nombre + extension;
			} 
			// No tiene extension
			else {
				 // Eliminar caracteres extrañs en el nombre del fichero.
				fileName = normalize(fileName);
				// Si el nombre empieza por un numero añadir un guion bajo al principio.
				if (fileName.matches("\\d.*")) {
					fileName = "_" + fileName;
				}		
				// Limitar el nombre del fichero a maxSizeName caracteres
				if (fileName.length() > maxSizeName) {
					fileName = fileName.substring(0, maxSizeName);
				}
			}
			//fileName = fileName.toLowerCase();
		}
		return fileName;
	}	
	
	public static DataHandler createDataHandler(byte[] bytes) throws IOException {
		//ByteArrayDataSource rawData = new ByteArrayDataSource(bytes);
		DataHandler data = null; //new DataHandler(rawData);
		return data;
	}
}
