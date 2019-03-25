package com.opencanarias.consola.utilidades;

 import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.opencanarias.consola.commons.LoadProperties;

import opencanarias.com.services.facade.expedientes.v01_00.ExpedientesService;
import opencanarias.com.services.facade.expedientes.v01_00.IExpedientesService;
import opencanarias.com.services.facade.gestor.documental.v01_00.GestorDocumentalService;
import opencanarias.com.services.facade.gestor.documental.v01_00.IGestorDocumentalService;

public class FacadeServices {
	private static IExpedientesService srv_expedientes_facade = null;
	private static IGestorDocumentalService srv_documentos_facade = null;
	//private static IRegistroElectronicoService srv_registro_facade = null;
	//private static IPortafirmasService srv_portafirmas_facade = null;
	//private static IFirmaService srv_firma_facade = null;
	//private static IActuacionesService srv_actuaciones_facade = null;
	//private static ITercerosService srv_terceros_facade = null;

	public static IExpedientesService srv_Expedientes_FWS() throws MalformedURLException {
		LoadProperties lp = new LoadProperties();
		
		String urlWsdlLocation = lp.getEnvParameters("fachadaWS.expedientes.wsdlLocation");
		String namespace = lp.getEnvParameters( "fachadaWS.expedientes.namespace");
		String srvName = lp.getEnvParameters( "fachadaWS.expedientes.serviceName");
		QName servicePort = new QName(namespace, lp.getEnvParameters("fachadaWS.expedientes.portName"));

		QName serviceName = new QName(namespace, srvName);
		URL wsdlLocation = new URL(urlWsdlLocation);
		ExpedientesService consumer = new ExpedientesService(wsdlLocation, serviceName);
		srv_expedientes_facade = consumer.getPort(servicePort, IExpedientesService.class);

		return srv_expedientes_facade;
	}
	
	public static IExpedientesService srvExpedientesFWS(String urlWsdlLocation, String namespace, 
			String srvName, String portName) throws MalformedURLException {
		QName servicePort = new QName(namespace, portName);

		QName serviceName = new QName(namespace, srvName);
		URL wsdlLocation = new URL(urlWsdlLocation);
		ExpedientesService consumer = new ExpedientesService(wsdlLocation, serviceName);
		srv_expedientes_facade = consumer.getPort(servicePort, IExpedientesService.class);

		return srv_expedientes_facade;
	}		

	public static IGestorDocumentalService srv_Documentos_FWS() throws MalformedURLException, Exception {
		LoadProperties lp = new LoadProperties();
		String urlWsdlLocation = lp.getEnvParameters("documentos.wsdlLocation");
		String namespace = lp.getEnvParameters("documentos.namespace");
		String srvName = lp.getEnvParameters("documentos.serviceName");
		QName servicePort = new QName(namespace, lp.getEnvParameters("documentos.portName"));

		QName serviceName = new QName(namespace, srvName);
		URL wsdlLocation = new URL(urlWsdlLocation);
		GestorDocumentalService consumer = new GestorDocumentalService(wsdlLocation, serviceName);
		srv_documentos_facade = consumer.getPort(servicePort, IGestorDocumentalService.class);

		return srv_documentos_facade;
	}
//	
//	public static IGestorDocumentalService srvDocumentosFWS(String urlWsdlLocation, String namespace, 
//			String srvName, String portName) throws MalformedURLException {
//		QName servicePort = new QName(namespace, portName);
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		GestorDocumentalService consumer = new GestorDocumentalService(wsdlLocation, serviceName);
//		srv_documentos_facade = consumer.getPort(servicePort, IGestorDocumentalService.class);
//
//		return srv_documentos_facade;
//	}	

//	public static IRegistroElectronicoService srv_RegistroElectronico_FWS() throws MalformedURLException, GenericFacadeException {
//		String urlWsdlLocation = ConfigUtils.getParametro("registroES.wsdlLocation");
//		String namespace = ConfigUtils.getParametro("registroES.namespace");
//		String srvName = ConfigUtils.getParametro("registroES.serviceName");
//		QName servicePort = new QName(namespace, ConfigUtils.getParametro("registroES.portName"));
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		RegistroElectronicoService consumer = new RegistroElectronicoService(wsdlLocation, serviceName);
//		srv_registro_facade = consumer.getPort(servicePort, IRegistroElectronicoService.class);
//
//		return srv_registro_facade;
//	}
//	
//	public static IRegistroElectronicoService srvRegistroElectronicoFWS(String urlWsdlLocation, String namespace, 
//			String srvName, String portName) throws MalformedURLException {
//		QName servicePort = new QName(namespace, portName);
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		RegistroElectronicoService consumer = new RegistroElectronicoService(wsdlLocation, serviceName);
//		srv_registro_facade = consumer.getPort(servicePort, IRegistroElectronicoService.class);
//
//		return srv_registro_facade;
//	}
//	
//	public static IPortafirmasService srv_Portafirmas_FWS() throws MalformedURLException, GenericFacadeException {
//		String urlWsdlLocation = ConfigUtils.getParametro("portafirmas.wsdlLocation");
//		String namespace = ConfigUtils.getParametro("portafirmas.namespace");
//		String srvName = ConfigUtils.getParametro("portafirmas.serviceName");
//		QName servicePort = new QName(namespace, ConfigUtils.getParametro("portafirmas.portName"));
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		PortafirmasService consumer = new PortafirmasService(wsdlLocation, serviceName);
//		srv_portafirmas_facade = consumer.getPort(servicePort, IPortafirmasService.class);
//
//		return srv_portafirmas_facade;
//	}
//	
//	public static IPortafirmasService srvPortafirmasFWS(String urlWsdlLocation, String namespace, 
//			String srvName, String portName) throws MalformedURLException {
//		QName servicePort = new QName(namespace, portName);
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		PortafirmasService consumer = new PortafirmasService(wsdlLocation, serviceName);
//		srv_portafirmas_facade = consumer.getPort(servicePort, IPortafirmasService.class);
//
//		return srv_portafirmas_facade;
//	}	
//	
//	public static IFirmaService srv_Firma_FWS() throws MalformedURLException, GenericFacadeException {
//		String urlWsdlLocation = ConfigUtils.getParametro("firma.wsdlLocation");
//		String namespace = ConfigUtils.getParametro("firma.namespace");
//		String srvName = ConfigUtils.getParametro("firma.serviceName");
//		QName servicePort = new QName(namespace, ConfigUtils.getParametro("firma.portName"));
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		FirmaService consumer = new FirmaService(wsdlLocation, serviceName);
//		srv_firma_facade = consumer.getPort(servicePort, IFirmaService.class);
//
//		return srv_firma_facade;
//	}
//	
//	public static IFirmaService srvFirmaFWS(String urlWsdlLocation, String namespace, 
//			String srvName, String portName) throws MalformedURLException {
//		QName servicePort = new QName(namespace, portName);
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		FirmaService consumer = new FirmaService(wsdlLocation, serviceName);
//		srv_firma_facade = consumer.getPort(servicePort, IFirmaService.class);
//
//		return srv_firma_facade;
//	}
//	
//	public static IActuacionesService srv_Actuaciones_FWS() throws MalformedURLException, GenericFacadeException {
//		String urlWsdlLocation = ConfigUtils.getParametro("actuaciones.wsdlLocation");
//		String namespace = ConfigUtils.getParametro("actuaciones.namespace");
//		String srvName = ConfigUtils.getParametro("actuaciones.serviceName");
//		QName servicePort = new QName(namespace, ConfigUtils.getParametro("actuaciones.portName"));
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		ActuacionesService consumer = new ActuacionesService(wsdlLocation, serviceName);
//		srv_actuaciones_facade = consumer.getPort(servicePort, IActuacionesService.class);
//
//		return srv_actuaciones_facade;
//	}
//	
//	public static IActuacionesService srvActuacionesFWS(String urlWsdlLocation, String namespace, 
//			String srvName, String portName) throws MalformedURLException {
//		QName servicePort = new QName(namespace, portName);
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		ActuacionesService consumer = new ActuacionesService(wsdlLocation, serviceName);
//		srv_actuaciones_facade = consumer.getPort(servicePort, IActuacionesService.class);
//
//		return srv_actuaciones_facade;
//	}	
//	
//	public static ITercerosService srv_Terceros_FWS() throws MalformedURLException, GenericFacadeException {
//		String urlWsdlLocation = ConfigUtils.getParametro("terceros.wsdlLocation");
//		String namespace = ConfigUtils.getParametro("terceros.namespace");
//		String srvName = ConfigUtils.getParametro("terceros.serviceName");
//		QName servicePort = new QName(namespace, ConfigUtils.getParametro("terceros.portName"));
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		TercerosService consumer = new TercerosService(wsdlLocation, serviceName);
//		srv_terceros_facade = consumer.getPort(servicePort, ITercerosService.class);
//
//		return srv_terceros_facade;
//	}	
//	
//	public static ITercerosService srvTercerosFWS(String urlWsdlLocation, String namespace, 
//			String srvName, String portName) throws MalformedURLException {
//		QName servicePort = new QName(namespace, portName);
//
//		QName serviceName = new QName(namespace, srvName);
//		URL wsdlLocation = new URL(urlWsdlLocation);
//		TercerosService consumer = new TercerosService(wsdlLocation, serviceName);
//		srv_terceros_facade = consumer.getPort(servicePort, ITercerosService.class);
//
//		return srv_terceros_facade;
//	}		
	
}
