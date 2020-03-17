package es.apt.ae.facade.entities.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String PARAM_DD = "\\{dd\\}";
	public static String PARAM_MM = "\\{mm\\}";
	public static String PARAM_YY = "\\{yy\\}";
	public static String PARAM_YYYY = "\\{yyyy\\}";
	
	private static SimpleDateFormat standartFormat = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Metodo que formatea una serie de fechas de tipo Date, devolviendo su
	 * representacion como cadenas de texto.
	 * 
	 * @param dates
	 *            Array de objetos Date con las fechas que se desea formatear
	 * 
	 * @return Array de String con las fechas con el formato pedido
	 */
	public static String[] getFormatDates(Date[] dates) {

		String[] result = new String[dates.length];

		for (int i = 0; i < dates.length; i++) {
			result[i] = getFormatDate(dates[i]);
		}

		return result;
	}
	
	/**
	 * Metodo que formatea uuna fecha de tipo Date, devolviendo su
	 * representacion como cadena de texto.
	 * 
	 * @param date
	 *            Objeto Date con la fecha que se desea formatear
	 * 
	 * @return String con la fecha con el formato pedido
	 */
	public static String getFormatDate(Date date) {
		String result = standartFormat.format(date);
		return result;
	}

}
