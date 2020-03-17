package es.apt.ae.facade.utils.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.xerces.dom.DocumentImpl;
import org.jboss.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.opencanarias.utils.DateUtils;


public class InternalXMLUtils {

	private static final Logger log = Logger.getLogger(InternalXMLUtils.class);
	
	public static final int REPLACE_FALSE  = 0;
	public static final int REPLACE_INIT   = 1;
	public static final int REPLACE_SAVE   = 2;
	
	public static final String ROOT_REGISTRO_ELECTRONICO = "RegistroElectronico";
	public static final String ROOT_GESTOR_EXPEDIENTES = "Opencities";
	private static final String ELEMENT = "Element";

	
	/**
	 * Crea un fichero XML con la informacion recibida
	 * 
	 * @param filePath String con la ruta del fichero donde almacenarlo
	 * @param map Map con la informacion a incluir en el documento
	 * 
	 * @throws XMLException Si se produce algun error
	 */
	public static void createFileXML(String rootName, String filePath, Map<String,Object> map) throws XMLException {
				
		byte[] result = createFileXML(rootName, map);
		
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			out.write(result);
			out.flush();
			out.close();

		} catch (IOException e) {
			log.info("Capturada excepcion: " + e.getMessage());
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC);
		}
	}
	
	/**
	 * Crea un fichero XML con la informacion recibida
	 * 
	 * @param map Map con la informacion a incluir en el documento
	 * 
	 * @throws XMLException Si se produce algun error
	 */
	public static byte[] createFileXML(String rootName, Map<String,Object> map) throws XMLException {
		
		//Documento
		Document document = new DocumentImpl();
		
		// Root
		Element root = addRoot(document, rootName);
		
		//Element
		Element element = addElement(document, root, ELEMENT);
		
		String name = null;
		Object value = null;
		
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
   			name = keys.next();
			value = map.get(name);
			addElement(document, element, name, value);
		}
   		
   		String data = XercesUtils.getDocumentAsString(document);
   		return data.getBytes();
	}
	
	
	/**
	 * Devuelve el valor de una propiedad almacenada en un fichero XML pasado por
	 * parametro como un array de bytes con su contenido
	 * 
	 * @param property String con el nombre de la propiedad
	 * @param data Array de bytes con el contenido del fichero
	 * 
	 * @return String con el valor de la propiedad pedida
	 * 
	 * @throws XMLException Si se produce algun error
	 */
	public static String getProperty(String property, byte[] data) throws XMLException{
		
		String result = null;
		Document document = XercesUtils.getDocument(data);
		NodeList list = document.getElementsByTagName(property);
		
		if(list.getLength()>1 || list.getLength()==0){
			boolean propertyFound = false;
			Node ocNode = document.getFirstChild();
			Node elementNode = ocNode.getFirstChild();
			NodeList children = elementNode.getChildNodes();
			
			for (int index=0; index < children.getLength(); index++){
				Node child = children.item(index);
				String name = null;
				String value = null;
				NamedNodeMap attributes = child.getAttributes();
				
				if (attributes!=null && attributes.getLength()>0){
					Attr nameAttribute = (Attr) attributes.getNamedItem("name");
					Attr valueAttribute = (Attr) attributes.getNamedItem("value");
					
					if (nameAttribute!=null){
						name = nameAttribute.getValue();
					}
					
					if (valueAttribute!=null){
						value = valueAttribute.getValue();
					}
					
				}
				
				if (name!=null && name.equals(property)){
					propertyFound = true;
					result = (value!=null)?value:"";
					break;
				}
			}
			
			if (!propertyFound) 
				throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC);
		} else {
			Node node = list.item(0);
			result = node.getTextContent();
		}
		
		return result;
	}
	
	/**
	 * Permite almacenar en el map los valores recuperados del documento XML
	 * pasado por parametro como array de bytes
	 * 
	 * @param map
	 *            Map con los valores donde se almacenaran las propiedades
	 * @param data
	 *            Array de bytes con el contenido del documento XML
	 * @param replace
	 *            Booleano que indica si se reemplazan los valores si ya existen
	 *            en el Map
	 * 
	 * @throws XMLException
	 *             Si se produce algun error
	 */
	public static void obtainFileXML(HashMap<String, Object> map, byte[] data, int replace) throws XMLException {

		try {
			Document document = XercesUtils.getDocument(data);
			Element root = document.getDocumentElement();
			NodeList listElement = root.getElementsByTagName(ELEMENT);
			Element element = (Element) listElement.item(0);
			NodeList children = element.getChildNodes();

			for (int i = 0; i < children.getLength(); i++) {
				boolean load = true;
				
				if(children.item(i).getNodeType() == Node.ELEMENT_NODE){
					Element nodo = (Element) children.item(i);
					String nodeName = nodo.getNodeName();
					///////////////////////////////////////////
					// Procesamos los ArrayList<String>
					///////////////////////////////////////////
					if(nodeName.equals("List")){
						String name = nodo.getAttribute("name");
						String size = nodo.getAttribute("list-size");
						
						Object oldValue = map.get(name);
						List<HashMap<String,Object>> value = calculateListPropertiesValues(map, nodo, replace, size);
						
						if (load) {
							if(replace == REPLACE_INIT && value!=null && oldValue==null){
								map.put(name, value);
							}else if(replace == REPLACE_SAVE){
								map.put(name, value);
							}else if (oldValue == null) {
								map.put(name, value);
							}
						}
					///////////////////////////////////////////
					// Procesamos los String[]
					///////////////////////////////////////////
					} else if(nodeName.equals("Collection")){
						String name = nodo.getAttribute("name");
						String size = nodo.getAttribute("size");
						
						String[] value = null;
						String[] oldValue = (String[]) map.get(name);
						
						if (size!=null && !size.equals("")){
							int iSize = Integer.parseInt(size);
							value = new String[iSize];
							for (int j = 0; j < iSize; j++) {
								NodeList childList = nodo.getElementsByTagName("row_" + j);
								Node child = childList.item(0);
								value[j] = child.getTextContent();
							}
						}
						
						if (load) {
							if(replace == REPLACE_INIT && value!=null && oldValue==null){
								map.put(name, value);
							}else if(replace == REPLACE_SAVE){
								map.put(name, value);
							}else if (oldValue == null) {
								map.put(name, value);
							}
						}
					}else{
						HashMap<String, Serializable> result = getSimpleElementValue(map, nodo, replace);
						if (result != null) {
							map.putAll(result);
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("Capturada excepcion: " + e.getMessage());
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC, e.getMessage());
		}
	}
	
	
	private static List<HashMap<String,Object>> calculateListPropertiesValues(HashMap<String, Object> map, Element nodo, int replace, String size) throws XMLException {
		List<HashMap<String,Object>> value = null;
		
		if (size!=null && !size.equals("")){
			int iSize = Integer.parseInt(size);
			value = new ArrayList<HashMap<String,Object>>(iSize);
			for (int j = 0; j < iSize; j++) {
				NodeList childList = nodo.getElementsByTagName("row_" + j);
				Node child = childList.item(0);
				NodeList listChildren = child.getChildNodes();
				HashMap<String, Object> rowPropertiesValues = new HashMap<String, Object>();
				for (int k = 0; k < listChildren.getLength(); k++) {
					Element rowNode = (Element) listChildren.item(k);
					HashMap<String, Serializable> result = getSimpleElementValue(map, rowNode, replace);
					if (result != null) {
						String propertyName = (String)result.keySet().toArray()[0];
						Serializable propertyValue = result.get(propertyName);
						rowPropertiesValues.put(propertyName, propertyValue);
					}
				}
				value.add(rowPropertiesValues);
			}
		}	
		return value;
	}
	
	private static HashMap<String, Serializable> getSimpleElementValue(HashMap<String, Object> map, Element nodo, int replace) {
		HashMap<String, Serializable> result = null;
		
		String nodeName = nodo.getNodeName();
		
		String name = null;
		Serializable value = null;
		Serializable oldValue = null;
		
		///////////////////////////////////////////
		// Procesamos los Boolean
		///////////////////////////////////////////
		if (nodeName.equals("Boolean")) {
			name = nodo.getAttribute("name");
			String strValue = nodo.getAttribute("value");
			value = new Boolean(strValue);
			oldValue = (Boolean) map.get(name);
			if(oldValue!=null && oldValue.toString().equals("false")){
				oldValue = null;
			}
		///////////////////////////////////////////
		// Procesamos los Integer
		///////////////////////////////////////////
		} else if (nodeName.equals("Integer")) {
			name = nodo.getAttribute("name");
			String strValue = nodo.getAttribute("value");
			value = new Integer(strValue);
			oldValue = (Integer) map.get(name);
			if(oldValue!=null && oldValue.toString().equals("0")){
				oldValue = null;
			}
		///////////////////////////////////////////
		// Procesamos los Long
		///////////////////////////////////////////
		} else if (nodeName.equals("Long")) {
			name = nodo.getAttribute("name");
			String strValue = nodo.getAttribute("value");
			value = new Long(strValue);
			oldValue = (Long) map.get(name);
			if(oldValue!=null && oldValue.toString().equals("0")){
				oldValue = null;
			}
		///////////////////////////////////////////
		// Procesamos los Float
		///////////////////////////////////////////
		} else if (nodeName.equals("Float")) {
			name = nodo.getAttribute("name");
			String strValue = nodo.getAttribute("value");
			value = new Float(strValue);
			oldValue = (Float) map.get(name);
			if(oldValue!=null && oldValue.toString().equals("0")){
				oldValue = null;
			}			
		///////////////////////////////////////////
		// Procesamos los Date
		///////////////////////////////////////////
		} else if (nodeName.equals("Date")) {
			name = nodo.getAttribute("name");
			String strValue = nodo.getAttribute("value");
			value = DateUtils.getDateFromXMLFormat(strValue);
			oldValue = (Date) map.get(name);
		///////////////////////////////////////////
		// Procesamos los String
		///////////////////////////////////////////
		} else {
			name = nodo.getNodeName();
			value = nodo.getTextContent();
			oldValue = (String) map.get(name);
			if(oldValue!=null && oldValue.equals("")){
				oldValue = null;
			}
		}
		
		if(replace == REPLACE_INIT && value!=null && oldValue==null){
			result = new HashMap<String, Serializable>();
			result.put(name, value);
		}else if(replace == REPLACE_SAVE){
			result = new HashMap<String, Serializable>();
			result.put(name, value);
		}else if (oldValue == null) {
			result = new HashMap<String, Serializable>();
			result.put(name, value);
		}

		return result;
	}
	
	/**
	 * Realiza una mezcla de datos entre un documento XML ya generado y la
	 * informacion almacenada en un map recibido por parametro 
	 * 
	 * @param data Documento XML como array de bytes
	 * @param map Map con la informacion a almacenar
	 * 
	 * @return El array de bytes del nuevo documento
	 * 
	 * @throws XMLException Si se produce algun error
	 */
	 public static byte[] mergeXml(byte[] data, Map<String, String> map) throws XMLException {
		byte[] result = null;

		try {
			// Creating document
			Document document = XercesUtils.getDocument(data);
			Iterator<String> iterator = map.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				
				if (key.equals("filesAttached")
						|| key.equals("selectedFileAttached")
						|| key.equals("documentAttached")
						|| key.equals("documentAttachedName")
						|| key.equals("servletWrapper")
						|| key.equals("multipartRequestHandler")
						|| key.equals("class")
						|| key.equals("attachment")
						|| key.equals("activity")){
					continue;
				}

				String valueMap = map.get(key);
				
				try{
					String value = getProperty(key, data);
					if (value != null) {
						if (valueMap != null && !valueMap.equals("")){
							modifyElement(document, key, valueMap);
						}
					}
				}catch(XMLException e){
					if(e.getCode().equals(XMLException.ERROR_CODE_XML_GENERIC)){
						if (valueMap != null && !valueMap.equals("")){
							addElement(document, key, valueMap);
						}
					}
				}
			}

			// Convert Document to byte[]
			String resultAsString = XercesUtils.getDocumentAsString(document);
			result = resultAsString.getBytes();
			
		} catch (Exception e) {
			log.info("Error trying to merge xml document with a map" + e.getMessage());
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC, e.getMessage());
		}

		return result;
	}
	
	
	/**
	 * Crea el elemento root del documento y lo añade a el
	 * 
	 * @param document
	 *            Referencia al documento
	 * @param name
	 *            Nombre del elemento
	 * 
	 * @return Referencia al elemento creado
	 */
	private static Element addRoot(Document document, String name) {
		Element root = document.createElementNS(XercesUtils.DEFAULT_NS, name);
		document.appendChild(root);
		return root;
	}

	/**
	 * Añade un nuevo elemento al elememto padre pasado por parametro
	 * 
	 * @param document Referencia al documento
	 * @param parent Elemento padre al que añadir el nodo creado
	 * @param name Nombre del elemento
	 */
	private static Element addElement(Document document, Element parent,
			String name) {
		Element element = document
				.createElementNS(XercesUtils.DEFAULT_NS, name);
		parent.appendChild(element);
		return element;
	}
	
	/**
	 * Añade un nuevo elemento al elememto padre pasado por parametro
	 * 
	 * @param document Referencia al documento
	 * @param parent Elemento padre al que añadir el nodo creado
	 * @param name Nombre del elemento
	 * 
	 * @throws XMLException Si se produce algun error 
	 */
	private static void modifyElement(Document document, String name, String value) throws XMLException {
		
		NodeList list = document.getElementsByTagName(name);
		
		if(list.getLength()>1 || list.getLength()==0){
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC);
		}
		
		Node node = list.item(0);
		node.setTextContent(value);
	}
	
	/**
	 * Agrega un nuevo par clave-valor al documento
	 * 
	 * @param document Referencia al documento
	 * @param key Nombre del elemento
	 * @param value Valor del elemento
	 * 
	 * @throws XMLException Si se produce algun error
	 */
	private static void addElement(Document document, String key, String value) throws XMLException {
		
		NodeList list = document.getElementsByTagName(ELEMENT);
		if(list.getLength()>1 || list.getLength()==0){
			throw new XMLException(XMLException.ERROR_CODE_XML_GENERIC);
		}
		
		Element parent = (Element) list.item(0);
		addElement(document, parent, key, value);
	}

	/**
	 * Agrega un nuevo elemento al elemento padre pasado por parametro
	 * 
	 * @param document Referencia al documento
	 * @param parent Elemento padre al que añadir el elemento creado
	 * @param name Nombre del elemento
	 * @param value Object generico valor del elemento
	 */
	@SuppressWarnings("unchecked")
	private static void addElement(Document document, Element parent,
			String name, Object object) {
		
		if (object!=null){
			if (!addSimpleElement(document, parent, name, object)) {
				if (object instanceof String[]) {
					String[] valores = (String[]) object;
					Element collection = document.createElementNS(XercesUtils.DEFAULT_NS, "Collection");
					collection.setAttribute("name", name);
					collection.setAttribute("size", "" + valores.length);
	
					for (int index = 0; index < valores.length; index++) {
						Element element = document.createElementNS(
								XercesUtils.DEFAULT_NS, "row_" + index);
						element.setTextContent(valores[index]);
						collection.appendChild(element);
					}
	
					parent.appendChild(collection);
				
				} else if (object instanceof List) {
					List<Object> valoresObj = (List<Object>) object;
					
					Element collection = document.createElementNS(XercesUtils.DEFAULT_NS, "List");
					collection.setAttribute("name", name);
					collection.setAttribute("list-size", "" + valoresObj.size());
	
					for (int index = 0; index < valoresObj.size(); index++) {
						Object value = valoresObj.get(index);
						String qName = "row_" + index;
						String textContent = "";
						Element element = document.createElementNS(XercesUtils.DEFAULT_NS, qName);
						if (value instanceof String) {
							textContent = (String)value;
						}
						if (!textContent.equals("")) {
							element.setTextContent(textContent);
						}
						collection.appendChild(element);
					}
	
					parent.appendChild(collection);
				
				}
			}
		} else {
			String value = "";
			Element element = document.createElementNS(XercesUtils.DEFAULT_NS, name);
			element.setTextContent(value);
			parent.appendChild(element);
		} 
	}
	
	private static boolean addSimpleElement(Document document, Element parent,
				String name, Object object) {
		
		boolean added = true;
		
		if (object instanceof String) {
			String value = (String) object;
			Element element = document.createElementNS(XercesUtils.DEFAULT_NS, name);
			element.setTextContent(value);
			parent.appendChild(element);

		} else if (object instanceof Boolean) {
			Boolean value = (Boolean) object;
			Element element = document.createElementNS(XercesUtils.DEFAULT_NS, "Boolean");
			element.setAttribute("name", name);
			element.setAttribute("value", value.toString());
			parent.appendChild(element);
			
		} else if (object instanceof Integer) {
			Integer value = (Integer) object;
			Element element = document.createElementNS(XercesUtils.DEFAULT_NS, "Integer");
			element.setAttribute("name", name);
			element.setAttribute("value", value.toString());
			parent.appendChild(element);
			
		} else if (object instanceof Long) {
			Long value = (Long) object;
			Element element = document.createElementNS(XercesUtils.DEFAULT_NS, "Long");
			element.setAttribute("name", name);
			element.setAttribute("value", value.toString());
			parent.appendChild(element);
			
		} else if (object instanceof Date) {
			Date value = (Date) object;
			String formattedDate = DateUtils.getXMLFormatDate(value);
			Element element = document.createElementNS(XercesUtils.DEFAULT_NS, "Date");
			element.setAttribute("name", name);
			element.setAttribute("value", formattedDate);
			parent.appendChild(element);
			
		} else if (object instanceof Float) {
			Float value = (Float) object;
			Element element = document.createElementNS(XercesUtils.DEFAULT_NS, "Float");
			element.setAttribute("name", name);
			element.setAttribute("value", value.toString());
			parent.appendChild(element);
			
		} else {
			added = false;
		}
		
		return added;
	}

}
