package es.apt.ae.facade.utils.xml;


import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.jboss.logging.Logger;

import es.apt.ae.facade.dto.GestionExpedienteInfo;


public class Converter {
	
	private static final Logger log = Logger.getLogger(Converter.class);
	
	/**
	 * Convierte la informacion de un GestionExpedienteInfo a un CommonBean nuevo.
	 * 
	 * @param form
	 *            Instancia del formulario GestionExpedienteInfo
	 *            
	 * @return CommonBean creado con la informacion del form
	 */
	public static CommonBean convertGestionExpInfoToBean(GestionExpedienteInfo gestionExpInfo, List<String> formPropertiesNames) {
		CommonBean bean = new CommonBean();
		bean = Converter.convertGestionExpInfoToBean(bean, gestionExpInfo, formPropertiesNames);
		return bean;
	}

	
	public static GestionExpedienteInfo getGestionExpInfoFromBean(CommonBean bean, Class<? extends GestionExpedienteInfo> classForm, List<String> formPropertiesNames) throws Exception {
		String listClassName = classForm.getName();
		Class<?> c = Class.forName(listClassName);
	    Constructor<?> ctor = c.getConstructors()[0];
	    Object object = ctor.newInstance(new Object[]{});
	    GestionExpedienteInfo form = (GestionExpedienteInfo)object;
	    convertBeanToGestionExpForm(bean, form, formPropertiesNames);
	    
	    return form;
	}
	
	
	/**
	 * Vuelca toda la informacion de un CommonBean en una instancia de
	 * PresentacionForm. Este metodo volcara del CommonBean aquellas propiedades
	 * cuyo nombre coincida con el nombre de la propiedad del ActionForm
	 * 
	 * @param bean
	 *            CommonBean con los datos almacenados
	 * @param form
	 *            Instancia del formulario GestionExpedienteInfo
	 * @throws Exception 
	 */
	@SuppressWarnings (value="unchecked")
	public static void convertBeanToGestionExpForm(CommonBean bean, GestionExpedienteInfo gestionExpInfo, List<String> formPropertiesNames) throws Exception {

		Class<? extends GestionExpedienteInfo> classForm = gestionExpInfo.getClass();
		Method[] methods = classForm.getMethods();
		HashMap<String, Object> map = bean.getMap();

		try{
			for (int i = 0; i < methods.length; i++) {
	
				String methodName = methods[i].getName().toString();
	
				StringBuffer propertyName = new StringBuffer("");
				
				if (methodName.startsWith("set")) {
					propertyName.append(methodName.substring(3, 4).toLowerCase());
					propertyName.append(methodName.substring(4));
				
					Object value = null;
	
					try {
						value = map.get(propertyName.toString());
	
						// Miramos que no sea null
						if ((formPropertiesNames == null || formPropertiesNames.contains(propertyName.toString())) && value != null) {
							////// String //////
							if (value instanceof String) {
								String strValue = (String) value;
	
								if (strValue != null && !strValue.trim().equals("") && !value.equals("null")) {
									try {
										Object[] stringObject = { value };
										methods[i].invoke(gestionExpInfo, stringObject);
	
									} catch (Exception e) {}
								}
							////// Boolean //////
							} else if (value instanceof Boolean) {
								Boolean bValue = (Boolean) value;
	
								if (bValue != null) {
									try {
										Object[] stringObject = { value };
										methods[i].invoke(gestionExpInfo, stringObject);
	
									} catch (Exception e) {}
								}
							////// Long //////
							} else if (value instanceof Long) {
								Long lValue = (Long) value;
	
								if (lValue != null) {
									try {
										Object[] stringObject = { value };
										methods[i].invoke(gestionExpInfo, stringObject);
	
									} catch (Exception e) {}
								}
							////// Float //////
							} else if (value instanceof Float) {
								Float fValue = (Float) value;
	
								if (fValue != null) {
									try {
										Object[] stringObject = { value };
										methods[i].invoke(gestionExpInfo, stringObject);
	
									} catch (Exception e) {}
								}								
							////// Date //////
							} else if (value instanceof Date) {
								Date dValue = (Date) value;
	
								if (dValue != null) {
									try {
										Object[] stringObject = { value };
										methods[i].invoke(gestionExpInfo, stringObject);
	
									} catch (Exception e) {}
								}
							////// String[] //////
							} else if (value instanceof String[]) {
								String[] array = (String[]) value;
	
								try {
									Object[] stringObject = { array };
									methods[i].invoke(gestionExpInfo, stringObject);
	
								} catch (Exception ex) { }
							////// ArrayList //////
							} else if (value instanceof List) {
								List<HashMap<String, Object>> lValue = (List<HashMap<String, Object>>) value;
								if (lValue != null && !lValue.isEmpty()) {
									value = calculateListInForm(value, lValue, methods[i]);
									try {
										Object[] stringObject = { value };
										methods[i].invoke(gestionExpInfo, stringObject);
									} catch (Exception ex) { }									
								}
							////// HashMap //////
							} else if (value instanceof HashMap) {
								HashMap<Object, Object> hValue = (HashMap<Object, Object>) value;
	
								if (hValue != null) {
									try {
										Object[] stringObject = { value };
										methods[i].invoke(gestionExpInfo, stringObject);
	
									} catch (Exception e) {}
								}
							}else {
								// El valor no es permitido...
								try {
									Object objValue = map.get(propertyName .toString());
									Object[] arrayObject = { objValue };
									methods[i].invoke(gestionExpInfo, arrayObject);
	
								} catch (Exception e) {
									// Intenta hacer un set de algo no esperado
									// Pasamos de la excepcion y continuamos
								}
							}
						}
					} catch (ClassCastException e) {
						// Si da esta excepcion significa que el tipo no es String.
						// Miramos si es un XMLObject...
					}
				}
			}
		} catch (Exception e) {
			log.info("ERROR");
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	private static Object calculateListInForm(Object value, List<HashMap<String, Object>> lValue, Method currentMethod) throws Exception {
		String listClassName = null;
		Type parameterType = currentMethod.getGenericParameterTypes()[0];
        if (parameterType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) parameterType;
            Type argumentType = pt.getActualTypeArguments()[0];
            listClassName = argumentType.toString().split("\\ ")[1];
        }
		if (listClassName != null) {
			List<Object> methodValue = new ArrayList<Object>();
			for (HashMap<String, Object> elem:lValue) {
				try {
				    Class<?> c = Class.forName(listClassName);
				    Constructor<?> ctor = c.getConstructors()[0];
				    Object object = ctor.newInstance(new Object[]{});
				    Iterator<Entry<String, Object>> it = elem.entrySet().iterator();
				    while (it.hasNext()) {
				    	Entry<String, Object> entry = it.next();
				    	Method method = object.getClass().getMethod("set" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1), 
				    			entry.getValue().getClass());
				    	Object[] stringObject = { entry.getValue() };
				    	method.invoke(object, stringObject);
				    }
				    methodValue.add(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return methodValue;
		}
		return value;
	}
	/**
	 * Agrega la informacion de un PresentacionForm a un CommonBean ya existente.
	 * Es posible que a su vez el CommonBean tenga informacion de otros
	 * formularios anteriores.
	 * 
	 * @param bean
	 *            CommonBean con los datos almacenados
	 * @param form
	 *            Instancia del formulario PresentacionForm
	 */
	public static CommonBean convertGestionExpInfoToBean(CommonBean bean, GestionExpedienteInfo gestionExpInfo, List<String> formPropertiesNames) {

		HashMap<String, Object> map = getPropertiesValues(gestionExpInfo, formPropertiesNames);

		if (bean.getMap() == null) {
			bean.setMap(new HashMap<String, Object>());
		}
		bean.getMap().putAll(map);

		return bean;
	}
	
	public static HashMap<String, Object> getPropertiesValues(Object object, List<String> formPropertiesNames) {
		Class<?> classForm = object.getClass();
		Method[] methods = classForm.getMethods();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		for (int i = 0; i < methods.length; i++) {

			String methodName = methods[i].getName().toString();

			StringBuffer propertyName = new StringBuffer("");
			boolean found = false;
			
			if (methodName.startsWith("get")) {
				propertyName.append(methodName.substring(3, 4).toLowerCase());
				propertyName.append(methodName.substring(4));
				found = true;
				
			} else if (methodName.startsWith("is")) {
				propertyName.append(methodName.substring(2, 3).toLowerCase());
				propertyName.append(methodName.substring(3));
				found = true;
			}
			
			if (formPropertiesNames == null || formPropertiesNames.contains(propertyName.toString())) {
				Class<?>[] parameters = methods[i].getParameterTypes();
				if(parameters.length>0){
					found = false;
				}
				
				if(found){
					Object value = null;
					
					try {
						value = methods[i].invoke(object, (Object[]) null);
						// Value puede ser null. En este caso no hacemos nada
						if (value != null){
							if(!(value instanceof Class<?>)) {
								Serializable s = (Serializable) value;
								map.put(propertyName.toString(), s);
							}
						}
	
					} catch (Exception e) {
						log.info("Capturada excepcion:" + e.getMessage());
						log.info("Error al convertir los datos del formulario al objeto CommonBean");
						if(value!=null){
							log.info("   |-- value: " + value + " -- class: " + value.getClass());
						}
					}
				}
			}
		}
		
		return map;
	}
	
}

