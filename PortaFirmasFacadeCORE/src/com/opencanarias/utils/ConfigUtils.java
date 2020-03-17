package com.opencanarias.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.logging.Logger;

import com.opencanarias.exceptions.GenericFacadeException;

/**
 * Clase de utilidades que facilita el acceso a la configuracion de la aplicacion.
 * 
 * @author Open Canarias S.L.
 */
public class ConfigUtils {
	private static Logger logger = Logger.getLogger(ConfigUtils.class);
	private static Properties configuration;
	private static String entorno;
	private static String nombreFicheroConfiguracion;
	private static String propertyFile = System.getProperty("applications.properties.dir");

	/**
	 * Este metodo devuelve el entorno con el que estamos trabajando.
	 * 
	 * @throws GenericFacadeException
	 * @see configuration.properties
	 */
	public static String getEntorno() {
		if (entorno == null) {
			try {
				getConfiguration();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return entorno;
	}

	/**
	 * Este metodo carga el fichero de propiedades de configuracion y carga el entorno con el que trabajaremos.
	 * 
	 * @throws GenericFacadeException
	 */
	private static Properties getConfiguration() throws GenericFacadeException {
		try {
			if (configuration == null) {
				configuration = new Properties();
				if (nombreFicheroConfiguracion == null || nombreFicheroConfiguracion.isEmpty()) { 
					//configuration.load(ConfigUtils.class.getResourceAsStream("/facade_configuration.properties"));
					File file = new File(propertyFile+"/FachadaServicios/ServiceFacadeCORE/facade_configuration.properties");
					configuration = new Properties();
		            configuration.load(new FileInputStream(file));
		         	System.out.println("Carga el fichero de propiedades: " + propertyFile+"/FachadaServicios/ServiceFacadeCORE/facade_configuration.properties");
				} else {
					InputStream is = ConfigUtils.class.getResourceAsStream(nombreFicheroConfiguracion);
					//InputStream is = new FileInputStream(nombreFicheroConfiguracion);
					configuration.load(is);
				}
				entorno = configuration.getProperty("entorno");
			}
			return configuration;
		} catch (IOException e) {
			logger.error("Error al acceder a la configuracion de la aplicacion." + e.getMessage(), e);
			throw new GenericFacadeException("Error al acceder a la configuracion de la aplicacion." + e.getMessage(), e);
		}
	}

	/**
	 * Recupera un parametro de configuracion del fichero de propiedades facade_configuration.properties
	 * 
	 * @param nombre Nombre del parametro a recuperar
	 * @return Valor del parametro de configuracion
	 * @throws GenericFacadeException
	 */
	public static String getParametro(String nombre) throws GenericFacadeException {
		String tmp = getConfiguration().getProperty(getEntorno() + "." + nombre, getConfiguration().getProperty(nombre));
		return (tmp != null) ? tmp.trim() : null;
	}

	/**
	 * Recupera un parametro de configuracion del fichero de propiedades facade_configuration.properties de tipo Integer
	 * 
	 * @param nombre Nombre del parametro a recuperar
	 * @return Valor del parametro de configuracion
	 * @throws GenericFacadeException
	 */
	public static Integer getParametroInteger(String nombre) throws GenericFacadeException {
		String tmp = getConfiguration().getProperty(getEntorno() + "." + nombre, getConfiguration().getProperty(nombre));
		return (tmp != null) ? new Integer(tmp.trim()) : null;
	}

	/**
	 * Permite asignar el nombre del fichero de configuracion a partir del cual se recuperarán las propiedades de cofiguracion
	 * @param nombreFicheroConfiguracion Ruta del fichero de configuracion que desea cargarse
	 */
	public static void setNombreFicheroConfiguracion(String nombreFicheroConfiguracion) {
		ConfigUtils.nombreFicheroConfiguracion = nombreFicheroConfiguracion;
	}
	
	/**
	 * Pertmite resetear la configuracion cargada en esta clase
	 */
	public static void resetearConfiguracion () {
		ConfigUtils.configuration = null;
	}

	public static String getRutaNombreFicheroConfiguracion(String nombreFichero) {
		return propertyFile + "/FachadaServicios/ServiceFacadeCORE/" + nombreFichero;
	}

}
