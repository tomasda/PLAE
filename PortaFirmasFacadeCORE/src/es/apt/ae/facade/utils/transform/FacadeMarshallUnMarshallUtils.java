package es.apt.ae.facade.utils.transform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class FacadeMarshallUnMarshallUtils<T> {

		public static final String URI_CALLBACK_IN_PACKAGE = "es.apt.ae.facade.ws.params.callback.in";
		public static final String URI_CALLBACK_OUT_PACKAGE = "es.apt.ae.facade.ws.params.callback.out";
		public static final String URI_PORTAFIRMAS_IN_PACKAGE = "es.apt.ae.facade.ws.params.portafirmas.in";
		public static final String URI_PORTAFIRMAS_OUT_PACKAGE = "es.apt.ae.facade.ws.params.portafirmas.out";		
		public static final String URI_ACTUACIONES_IN_PACKAGE = "es.apt.ae.facade.ws.params.actuaciones.in";
		public static final String URI_ACTUACIONES_OUT_PACKAGE = "es.apt.ae.facade.ws.params.actuaciones.out";
		public static final String URI_EXPEDIENTES_IN_PACKAGE = "es.apt.ae.facade.ws.params.expedientes.in";
		public static final String URI_EXPEDIENTES_OUT_PACKAGE = "es.apt.ae.facade.ws.params.expedientes.out";
		public static final String URI_FIRMA_IN_PACKAGE = "es.apt.ae.facade.ws.params.firma.in";
		public static final String URI_FIRMA_OUT_PACKAGE = "es.apt.ae.facade.ws.params.firma.out";
		public static final String URI_GESTOR_DOCUMENTAL_IN_PACKAGE = "es.apt.ae.facade.ws.params.gestor.documental.in";															  
		public static final String URI_GESTOR_DOCUMENTAL_OUT_PACKAGE = "es.apt.ae.facade.ws.params.gestor.documental.out";	
		public static final String URI_REGISTRO_IN_PACKAGE = "es.apt.ae.facade.ws.params.registro.in";
		public static final String URI_REGISTRO_OUT_PACKAGE = "es.apt.ae.facade.ws.params.registro.out";
		public static final String URI_DECRETOS_IN_PACKAGE = "es.apt.ae.facade.ws.params.decretos.in";
		public static final String URI_DECRETOS_OUT_PACKAGE = "es.apt.ae.facade.ws.params.decretos.out";
		public static final String URI_TERCEROS_IN_PACKAGE = "es.apt.ae.facade.ws.params.terceros.in";															  
		public static final String URI_TERCEROS_OUT_PACKAGE = "es.apt.ae.facade.ws.params.terceros.out";
		public static final String URI_NOTIFICACIONES_IN_PACKAGE = "es.apt.ae.facade.ws.params.notificaciones.in";															  
		public static final String URI_NOTIFICACIONES_OUT_PACKAGE = "es.apt.ae.facade.ws.params.notificaciones.out";
		public static final String URI_REQUERIMIENTOS_IN_PACKAGE = "es.apt.ae.facade.ws.params.requerimientos.in";															  
		public static final String URI_REQUERIMIENTOS_OUT_PACKAGE = "es.apt.ae.facade.ws.params.requerimientos.out";	
		
		private String URI_IN_PACKAGE;
		private String URI_OUT_PACKAGE;
		
		public FacadeMarshallUnMarshallUtils() {
			super();
		}		

		public FacadeMarshallUnMarshallUtils(String uriInPackage, String uriOutPackage) {
			super();
			this.URI_IN_PACKAGE = uriInPackage;
			this.URI_OUT_PACKAGE = uriOutPackage;
		}
		
		@SuppressWarnings("unchecked")
		public T unmarshall_IN(String parametro) throws JAXBException {
			JAXBContext context = JAXBContext.newInstance(URI_IN_PACKAGE);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object obj = unmarshaller.unmarshal(new ByteArrayInputStream(parametro.getBytes()));
			return (T) obj;
		}
		
		public String marshall_IN(T parametro) throws JAXBException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(URI_IN_PACKAGE);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(parametro, baos);
			return baos.toString();
		}
		
		@SuppressWarnings("unchecked")
		public T unmarshall_OUT(String parametro) throws JAXBException, UnsupportedEncodingException {
			JAXBContext context = JAXBContext.newInstance(URI_OUT_PACKAGE);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object obj = unmarshaller.unmarshal(new ByteArrayInputStream(parametro.getBytes("UTF-8")));
			return (T)obj;
		}
		
		public String marshall_OUT(T parametro) throws JAXBException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(URI_OUT_PACKAGE);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(parametro, baos);
			return baos.toString();
		}

}