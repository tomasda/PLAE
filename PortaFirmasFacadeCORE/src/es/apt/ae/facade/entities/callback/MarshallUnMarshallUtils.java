package es.apt.ae.facade.entities.callback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MarshallUnMarshallUtils<T> {

	

		private static String URI_PORTAFIRMAS_IN_PACKAGE = "es.apt.ae.facade.ws.params.callback.in";
		private static String URI_PORTAFIRMAS_OUT_PACKAGE = "es.apt.ae.facade.ws.params.callback.out";

		@SuppressWarnings("unchecked")
		public T unmarshall_IN(String parametro) throws JAXBException {
			JAXBContext context = JAXBContext.newInstance(URI_PORTAFIRMAS_IN_PACKAGE);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object obj = unmarshaller.unmarshal(new ByteArrayInputStream(parametro.getBytes()));
			return (T) obj;
		}
		
		public String marshall_IN(T parametro) throws JAXBException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(URI_PORTAFIRMAS_IN_PACKAGE);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(parametro, baos);
			return baos.toString();
		}
		
		@SuppressWarnings("unchecked")
		public T unmarshall_OUT(String parametro) throws JAXBException {
			JAXBContext context = JAXBContext.newInstance(URI_PORTAFIRMAS_OUT_PACKAGE);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object obj = unmarshaller.unmarshal(new ByteArrayInputStream(parametro.getBytes()));
			return (T) obj;
		}
		
		public String marshall_OUT(T parametro) throws JAXBException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(URI_PORTAFIRMAS_OUT_PACKAGE);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(parametro, baos);
			return baos.toString();
		}

}
