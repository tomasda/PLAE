package es.apt.ae.facade.utils.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import org.jboss.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * The Class XercesUtils.
 */

public class XercesUtils {

	
	private static final Logger log = Logger.getLogger(XercesUtils.class);
	
	public static String DEFAULT_NS = "DEFAULT_NS";

	
	/**
	 * Obtiene el objeto DOM a partir del String XML 
	 * 
	 * @param xml String que representa el contenido del documento XML a parsear
	 * 
	 * @return Objeto de tipo Document
	 * 
	 * @throws XMLException the XML exception
	 */
	
	public static Document getDocument(String xml) throws XMLException {

		Document document = null;

		try {

			DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
			
			// El parseador generado por la factoria sera capaz de manejar documentos XML
			// con espacios de nombres definidos 
			
			dfactory.setNamespaceAware(true);
			
			DocumentBuilder dBuilder = dfactory.newDocumentBuilder();

			StringReader reader = new StringReader(xml);
			InputSource source = new InputSource(reader);
			document = dBuilder.parse(source);

		} catch (ParserConfigurationException e) {
			log.info("Capturada exception: " + e.getMessage());
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC, e.getMessage());
		} catch (SAXException e) {
			log.info("Capturada exception: " + e.getMessage());
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC, e.getMessage());
		} catch (IOException e) {
			log.info("Capturada exception: " + e.getMessage());
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC, e.getMessage());
		}

		return document;

	}

	/**
	 * 
	 * @param xml
	 * @return
	 * @throws XMLException
	 */
	public static Document getDocument(byte[] xml) throws XMLException {

		String file = new String(xml);
		return getDocument(file);
	}

	
	/**
	 * Gets the document as string.
	 * 
	 * @param document the document
	 * 
	 * @return the document as string
	 * 
	 * @throws XMLException the XML exception
	 */
	
	public static String getDocumentAsString(Document document)
			throws XMLException {
		
		try {
			StringWriter sw=new StringWriter();
			DOMSource source = new DOMSource(document);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
	        Transformer transformer = tFactory.newTransformer();
	        
	        StreamResult result = new StreamResult(sw);
	        transformer.transform(source, result);
	        return  sw.toString(); 
	        
		} catch (Exception e) {
			log.info("Capturada exception: " + e.getMessage());
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC, e.getMessage());
		}
	}

	/**
	 * 
	 * @param document
	 * @param extern
	 * @param clone
	 */
	public static void copyElement(Document document, Node extern, Element clone) {

		copyAttributes(extern, clone);

		NodeList list = extern.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {

			Node node = list.item(i);
			short type = node.getNodeType();

			if (type == Node.TEXT_NODE) {
				clone.setTextContent(node.getTextContent());
			} else if (type == Node.ELEMENT_NODE) {
				Element child = document.createElementNS(DEFAULT_NS, node
						.getNodeName());
				copyElement(document, node, child);
				clone.appendChild(child);
			}

		}

	}

	/**
	 * 
	 * @param extern
	 * @param clone
	 */
	private static void copyAttributes(Node extern, Element clone) {
		NamedNodeMap list = extern.getAttributes();
		if (list != null && list.getLength() != 0) {
			for (int i = 0; i < list.getLength(); i++) {
				Attr a = (Attr) list.item(i);
				clone.setAttributeNS(a.getNamespaceURI(), a.getNodeName(), a
						.getNodeValue());
			}
		}
	}

}
