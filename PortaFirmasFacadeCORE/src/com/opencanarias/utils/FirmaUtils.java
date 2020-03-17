package com.opencanarias.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
//import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import com.opencan.office.beans.AFirmaDocument;
import com.opencan.office.beans.SignatureInfo;
import com.opencan.office.beans.parsers.AFirmaParser;
//import com.opencanarias.ejb.common.FacadeBean;

public class FirmaUtils {

	public static List<SignatureInfo> getFirmantes(String[] firmas) throws Exception {
		List<SignatureInfo> firmantesInfo = new ArrayList<SignatureInfo>();
		for (String firma:firmas) {
			/*
			 * 14-08-2019 Tomás
			 * Aplicamos formato UTF-8 a la firma para que el parse se realice correctamente.
			 */
			InputStream target = new ByteArrayInputStream(firma.getBytes("UTF-8"));
			InputSource docSrc = new InputSource(target);
			AFirmaDocument afirmaDoc = null;
			
			AFirmaParser parser = new AFirmaParser();  //TODO  FacadeBean.RUTA_FICHERO_CONFIGURACION);FacadeBean.RUTA_FICHERO_CONFIGURACION);
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

			SAXParser saxParser;
			try {
				saxParser = saxParserFactory.newSAXParser();
				saxParser.parse(docSrc, parser);
				afirmaDoc = parser.getAfirmaDoc();
				firmantesInfo.addAll(afirmaDoc.getSignatures());
			} catch (Exception e) {
				throw e;
			}			
		}
		return firmantesInfo;
	}
	
	public static List<SignatureInfo> getFirmantes(byte[] firmaByteArray) throws Exception {
		List<SignatureInfo> firmantesInfo = new ArrayList<SignatureInfo>();
		ByteArrayInputStream bais = new ByteArrayInputStream(firmaByteArray);
		InputSource docSource = new InputSource(bais);
		AFirmaDocument afirmaDoc = null;

		AFirmaParser parser = new AFirmaParser("facade_configuration.properties");
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		SAXParser saxParser;
		try {
			saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(docSource, parser);
			afirmaDoc = parser.getAfirmaDoc();
			firmantesInfo.addAll(afirmaDoc.getSignatures());
		} catch (Exception e) {
			throw e;
		}			
		return firmantesInfo;
	}
	
	public static void main (String[] args) {
		try {
			//FileInputStream fisDocFirma = new FileInputStream("firma23Oct2018-Navision.xml");
			//FileInputStream fisDocFirma = new FileInputStream("firmaInformes.xml");
			FileInputStream fisDocFirma = new FileInputStream("firmaRosarioActaAnterior-17Jun2019.xml");
			byte[] contentFirma = FileUtils.readInputStream(fisDocFirma);
			String csv = CSVUtils.getDigest(FileUtils.formatNewLines(contentFirma));
			System.out.println("CSV: " + csv);
			List<SignatureInfo> firmantes = getFirmantes(contentFirma);
			if (firmantes != null && !firmantes.isEmpty()) {
				for (SignatureInfo firmaInfo:firmantes) {
					System.out.println("Firmante: " + firmaInfo.getName() + ", Fecha firma: " + firmaInfo.getSigningTime());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
