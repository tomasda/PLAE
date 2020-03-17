package com.opencanarias.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.logging.Logger;

/**
 * Esta clase ofrece un conjunto de utilidades para trabajar con fechas
 * 
 * @author Open Canarias S.L.
 */
public class DateUtils {
	
	private static final Logger log = Logger.getLogger(DateUtils.class);
	
	public static String yearFormat = "yyyy";
	public static String monthFormat = "MM";
	public static String dayFormat = "dd";
	public static String dateTimeFormat = "dd/MM/yyyy HH:mm:ss";
	public static String dateTimeFormatWithoutSeconds = "dd/MM/yyyy HH:mm";
	public static String dateTimeFormatCompact = "ddMMyyyyHHmmss";	
	public static String dateTimeFormatCompact2 = "ddMMyyHHmmss";	
	public static String dateFormat = "dd/MM/yyyy";
	public static String dateFormatCompact = "yyyyMMdd";
	public static String xmlGCPattern = "yyyy-MM-dd'T'HH:mm:ss";
	public static String repositoryDatePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private static SimpleDateFormat simpleYearFormat = new SimpleDateFormat(yearFormat);
	private static SimpleDateFormat simpleMonthFormat = new SimpleDateFormat(monthFormat);
	private static SimpleDateFormat simpleDayFormat = new SimpleDateFormat(dayFormat);
	private static SimpleDateFormat xmlFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");

	private static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día

	
	/**
	 * Convierte un objeto de tipo {@link Date} a un objeto {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException 
	 */
	public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date fecha) throws DatatypeConfigurationException {		
		if (fecha == null)
			return null;
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	}
	
	/**
	 * Convierte un objeto de tipo {@link String} a un objeto {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException 
	 * @throws ParseException 
	 */
	public static XMLGregorianCalendar stringToXMLGregorianCalendar(String fecha, String formato) throws DatatypeConfigurationException, ParseException {		
		if (fecha == null)
			return null;
		if (formato == null)
			formato = dateTimeFormat;
		
		SimpleDateFormat sdf = new SimpleDateFormat (formato);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sdf.parse(fecha));
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	}
	
	public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xgc) {
		if (xgc == null) {
			return null;
		} else {
			return xgc.toGregorianCalendar().getTime();
		}
	}	
	
	public static Date gregorianCalendarToDate(GregorianCalendar gc) {
		if (gc == null) {
			return null;
		} else {
			return gc.getTime();
		}
	}	
	
	/**
	 * Metodo que parsea una cadena de texto, devolviendo su
	 * representacion como fecha de tipo Date.
	 * 
	 * @param date
	 *            String con la cadena de texto que se desea parsear
	 *            
	 * @return Objeto Date con la fecha proporcionada
	 */
	public static Date getDate(String date, String format) {
		Date result = null;
		try {
			if(date!= null && !date.equalsIgnoreCase("".trim())){
				SimpleDateFormat standartFormat = new SimpleDateFormat(format);
				result = standartFormat.parse(date);
			}
		} catch (ParseException e) {
			result = null;
		}
		return result;
	}
	
	public static String[] getDate(Date date) {
		String[] result = new String[3];
		result[0] = simpleYearFormat.format(date);
		result[1] = simpleMonthFormat.format(date);
		result[2] = simpleDayFormat.format(date);
		return result;
	}
	
	public static String getDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}	
	
	public static Date getDateFirstTime(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		
		gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gregorianCalendar.set(GregorianCalendar.MINUTE, 0);
		gregorianCalendar.set(GregorianCalendar.SECOND, 0);
		
		return gregorianCalendar.getTime();
	}
	
	public static Date getDateLastTime(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		
		gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gregorianCalendar.set(GregorianCalendar.MINUTE, 59);
		gregorianCalendar.set(GregorianCalendar.SECOND, 59);
		
		return gregorianCalendar.getTime();
	}
	
	public static Date getDate(Date date, int hour, int minute, int second) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		
		gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, hour);
		gregorianCalendar.set(GregorianCalendar.MINUTE, minute);
		gregorianCalendar.set(GregorianCalendar.SECOND, second);
		
		return gregorianCalendar.getTime();
	}
	
	public static Date substractMonthsToDate(Date date, int numMonths) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		
		gregorianCalendar.add(GregorianCalendar.MONTH, ((-1)*numMonths));
		
		return gregorianCalendar.getTime();		
	}
	
	public static Date getDateIncrementedByDays(long date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.add(Calendar.DATE, days);
		return new Date(calendar.getTimeInMillis());
	}
	
	public static Date getDateFromXMLFormat(String date) {
		Date result = null;
		try {
			if(date!= null && !date.equalsIgnoreCase("".trim())){
				result = xmlFormat.parse(date);
			}
		} catch (ParseException e) {
			log.info("Error parseando fecha: " + date);
			log.info("  |---- " + e.getMessage());
			log.info("  |---- Intento con DateFormat.....");
			try {
				DateFormat df = DateFormat.getDateInstance();
				result = df.parse(date);
				log.info("  |---- Parseo correcto con DateFormat.....");
			} catch (ParseException e2) {	
				log.info("Error parseando fecha con DateFormat: " + date);
				log.info("  |---- " + e2.getMessage());
			}
			log.info("  |---- " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * Metodo que formatea una fecha de tipo Date, devolviendo su representacion
	 * como una cadena de texto con el formato utilizado en nuestros documentos 
	 * XML.
	 * 
	 * @param date
	 *            Objeto Date con la fecha que se desea formatear
	 * 
	 * @return String con la fecha con el formato pedido
	 */
	public static String getXMLFormatDate(Date date) {
		String result = xmlFormat.format(date);
		return result;
	}
	
	public static Long getDiasTranscurridos(Date fechaInicial){
		Date fechaActual = new Date();
		Long diferencia = null;
		if (fechaInicial == null) {
			diferencia = null;
		} else {
			diferencia = (fechaActual.getTime() - fechaInicial.getTime()) / MILLSECS_PER_DAY;
		}
		return diferencia;
	}
	
	public static void main(String[] args) {
		Date date1 = getDate("05/09/2019 00:00:00", dateTimeFormat);
		Date date2 = getDate("04/09/2019 24:00:00", dateTimeFormat);
		
		System.out.println(date1.before(date2));
		System.out.println(date1.after(date2));
		System.out.println(date1.compareTo(date2) == 0);
	}

}
