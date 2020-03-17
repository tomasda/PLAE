package com.opencanarias.apsct.modulo.generic.service.certificate.utils;

import java.io.CharArrayWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jboss.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.opencan.office.utils.DateUtils;
import com.opencanarias.utils.Constantes;

/**
 * Parsea el xml obtenido del servicio web de <code>@Firma</code>, con el 
 * resultado de la validacion de un certificado
 *<br>
 * ----------------------------------------------<br>
 
 
 */
public class AFirmaValidationResultParser extends DefaultHandler {

	private static final Logger logger = Logger.getLogger(AFirmaValidationResultParser.class);
	
	
	private static final String ENTITY_NOT_ACCEPTED = "COD_063";
	private static final String ENTITY_NOT_VALID	= "COD_066";
	

	private CharArrayWriter contenidoNodo = new CharArrayWriter();
	private AFirmaValidationResult resultValidacionCert = null;
	private AFirmaSimpleValidation validacionSimple = null;
	private AFirmaChainValidation validacionCadena = null;
	private AFirmaStateValidation validacionEstado = null;
	private AFirmaVerificationMethodError infoMetodoVerificacion = null;
	private AFirmaCertificateError errorCertificado = null;	
	private String nodoTemp = null;
	private String ultimoNodo = null;
	private boolean excepcion = false;
	private String codigoError = null;
	private String descripcionError = null;		

	
	public void startElement(String namespaceURI, String localName, String qName, Attributes attr) {
		contenidoNodo.reset();
			
		if ("ResultadoValidacion".equalsIgnoreCase(qName)) {
			ultimoNodo = "ResultadoValidacion";
			resultValidacionCert = new AFirmaValidationResult();
			nodoTemp = qName;
		} else if ("ValidacionSimple".equalsIgnoreCase(qName)) {
			validacionSimple = new AFirmaSimpleValidation();
			nodoTemp = qName;
		} else if ("ValidacionEstado".equalsIgnoreCase(qName)) {
			validacionEstado = new AFirmaStateValidation();
			nodoTemp = qName;
		} else if ("InfoMetodoVerificacion".equalsIgnoreCase(qName)) {
			nodoTemp = qName;
			infoMetodoVerificacion = new AFirmaVerificationMethodError();
		} else if ("ValidacionCadena".equalsIgnoreCase(qName)) {
			validacionCadena = new AFirmaChainValidation();
			nodoTemp = qName;
		} else if ("errorCertificado".equalsIgnoreCase(qName)) {
			ultimoNodo = "errorCertificado";
			errorCertificado = new AFirmaCertificateError();
			nodoTemp = qName;
		} else if ("Excepcion".equals(qName)) {
			resultValidacionCert = new AFirmaValidationResult();
			excepcion = true;
			nodoTemp = qName;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (excepcion) {
			if ("codigoError".equalsIgnoreCase(qName)) {
				this.codigoError = contenidoNodo.toString().trim();
				
			} else if ("descripcion".equalsIgnoreCase(qName)) {
				this.descripcionError = contenidoNodo.toString().trim();
				
			} else if ("Excepcion".equals(qName)) {	
				
				String msg = "";
				if(this.codigoError.equals(ENTITY_NOT_ACCEPTED)) {
					msg = Constantes.VALIDAR_CERT_ERROR_GENERAL;
				} else if(this.codigoError.equals(ENTITY_NOT_VALID)) {
					msg = Constantes.VALIDAR_CERT_ERROR_GENERAL;
				} else {
					msg = Constantes.VALIDAR_CERT_ERROR_GENERAL;
				}
				
				msg += " Mensaje de @Firma: " + this.descripcionError + 
						(this.descripcionError.endsWith(".")?"":".");
				resultValidacionCert.setResult(false);
				resultValidacionCert.setDescription(msg);
			}
		} else {
			if (contenidoNodo != null && !contenidoNodo.toString().trim().equals("") ) {				
				if ("ResultadoValidacion".equalsIgnoreCase(this.nodoTemp)) {
					if ("resultado".equalsIgnoreCase(qName)) {
						if ("0".equalsIgnoreCase(contenidoNodo.toString().trim())) {
							resultValidacionCert.setResult(true);
						} else {
							resultValidacionCert.setResult(false);
						}
					}
					if ("descripcion".equalsIgnoreCase(qName)) {
						resultValidacionCert.setDescription(contenidoNodo.toString().trim());
					}
				}
				if ("ValidacionSimple".equalsIgnoreCase(this.nodoTemp)) {
					if ("codigoResultado".equalsIgnoreCase(qName)) {
						validacionSimple.setCode(contenidoNodo.toString().trim());
					}
					if ("descResultado".equalsIgnoreCase(qName)) {
						validacionSimple.setResult(contenidoNodo.toString().trim());
					}
					if ("excepcion".equalsIgnoreCase(qName)) {
						validacionSimple.setException(contenidoNodo.toString().trim());
					}
				}
				if ("ValidacionEstado".equalsIgnoreCase(this.nodoTemp)) {
					if ("estado".equalsIgnoreCase(qName)) {
						validacionEstado.setState(contenidoNodo.toString().trim());
					}
					if ("descEstado".equalsIgnoreCase(qName)) {
						validacionEstado.setDescription(contenidoNodo.toString().trim());
					}
				}
				if ("InfoMetodoVerificacion".equalsIgnoreCase(this.nodoTemp)) {
					if ("estado".equalsIgnoreCase(qName)) {
						infoMetodoVerificacion.setState(contenidoNodo.toString().trim());
					}
					if ("descEstado".equalsIgnoreCase(qName)) {
						infoMetodoVerificacion.setDescription(contenidoNodo.toString().trim());
					}
					if ("fechaUltimaActualizacion".equalsIgnoreCase(qName)) {		
						Date fecha = null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss Z", new Locale("ES"));
																   //2008-03-31 lun 19:07:34 +0200
						try {
							fecha = sdf.parse(contenidoNodo.toString());
						} catch (ParseException e) {
							logger.error(Constantes.VALIDAR_CERT_ERROR_GENERAL, e);
							throw new SAXException(e);
						}
						infoMetodoVerificacion.setLastUpdateDate(fecha);
					}
					if ("fechaRevocacion".equalsIgnoreCase(qName)) {
						Date fecha = null;
						String fechaStr = "";
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss Z", new Locale("ES"));
												  				   //2005-05-24 mar 10:15:00 +0200						
						try {
							fecha = sdf.parse(contenidoNodo.toString());
							fechaStr = DateUtils.getDate(fecha, DateUtils.dateTimeFormat);
						} catch (ParseException e) {
							logger.error(Constantes.VALIDAR_CERT_ERROR_GENERAL, e);
							throw new SAXException(e);
						}
						infoMetodoVerificacion.setRevocationDate(fecha);
						resultValidacionCert.setResult(false);
						resultValidacionCert.setDescription("Certificado revocado: " + fechaStr);						
					}
					if ("motivo".equalsIgnoreCase(qName)) {
						infoMetodoVerificacion.setReason(contenidoNodo.toString().trim());
					}
					if ("urlServidor".equalsIgnoreCase(qName)) {
						infoMetodoVerificacion.getMethod().setUrl(contenidoNodo.toString().trim());
					}
					if ("protocolo".equalsIgnoreCase(qName)) {
						infoMetodoVerificacion.getMethod().setProtocol(contenidoNodo.toString().trim());
					}
					if ("excepcion".equalsIgnoreCase(qName)) {
						infoMetodoVerificacion.setException(contenidoNodo.toString().trim());
					}
					if ("InfoMetodoVerificacion".equalsIgnoreCase(qName)) {
						validacionEstado.addMethod(infoMetodoVerificacion);
					}
				}
				if ("ValidacionCadena".equalsIgnoreCase(this.nodoTemp)) {
					if ("codigoResultado".equalsIgnoreCase(qName)) {
						validacionCadena.setCode(contenidoNodo.toString().trim());
					}
					if ("descResultado".equalsIgnoreCase(qName)) {
						validacionCadena.setDescription(contenidoNodo.toString().trim());
					}
				}

				if ("errorCertificado".equalsIgnoreCase(this.nodoTemp)) {
					if ("idCertificado".equalsIgnoreCase(qName)) {
						errorCertificado.setCertificateId(contenidoNodo.toString().trim());
					}
				}
				if ("ValidacionSimple".equalsIgnoreCase(qName)) {
					if ("ResultadoValidacion".equals(ultimoNodo)) {
						resultValidacionCert.setSimpleValidation(validacionSimple);
					} else {
						errorCertificado.setSimpleValidation(validacionSimple);
					}
				}
				if ("ValidacionEstado".equalsIgnoreCase(qName)) {
					if ("ResultadoValidacion".equals(ultimoNodo)) {
						resultValidacionCert.setStateValidation(validacionEstado);
					} else {
						errorCertificado.setStateValidation(validacionEstado);
					}
				}
				if ("ResultadoValidacion".equalsIgnoreCase(qName)) {
					resultValidacionCert.setChainValidation(validacionCadena);
				}
				if ("errorCertificado".equalsIgnoreCase(qName)) {
					validacionCadena.addError(errorCertificado);
				}
			}
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		contenidoNodo.write(ch, start, length);
	}
	
	public AFirmaValidationResult getResultadoValidacionCert() {
		return resultValidacionCert;
	}
	
//	public static void main(String[] args) {
//		//String contenidoNodo = "2008-03-31 lun 19:07:34 +0200";
//		String contenidoNodo = "2018-09-14 vie 14:54:57 +0200";
//		Date fecha = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss Z", new Locale("ES"));
//												   //2008-03-31 lun 19:07:34 +0200
//		try {
//			fecha = sdf.parse(contenidoNodo.toString());
//			System.out.println("Fecha convertida");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
}
