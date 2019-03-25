package com.opencanarias.consola.utilidades;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
	private String fecha;
	

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * Método que devuelve la fecha actual en distintos formatos según se especifique en la petición.
	 * 
	 * @param tipo
	 * @return
	 */
	
	public DateUtils(){
		
	}
	
	public Timestamp timeStampFecha() {
		Timestamp fecha = new Timestamp(System.currentTimeMillis());
		return fecha;
	}
	
	public  String fecha(String tipo) {
		String fecha = null;
		DateFormat dateFormat = null;
		/*
		 * Este código se sustituye por el abajo descrito debido a que java 1.6 no lo soporta. switch (tipo){ case "1": dateFormat = new
		 * SimpleDateFormat("dd/MM/yy HH:mm:ss"); break; case "2": dateFormat = new SimpleDateFormat("dd-MM-yyyy"); break; case "3": dateFormat = new
		 * SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); break; }
		 */
		if (tipo.equals("1")) {
			dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		} else if (tipo.equals("2")) {
			dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		} else if (tipo.equals("3")) {
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		} else if (tipo.equals("4")) {
			dateFormat = new SimpleDateFormat("dd/MM/yy");
		}
		Calendar cal = Calendar.getInstance();
		fecha = dateFormat.format(cal.getTime());
		return fecha;
	}

	public Timestamp parseRangeDate(String Start_End, String fechasDesdeHasta) {
		Pattern pattern = Pattern.compile("^(\\d{1,2})[/]([0-9]{2,})[/]([0-9]{4,})[\\s][-][\\s](\\d{1,2})[/]([0-9]{2,})[/]([0-9]{4,})$");
		Matcher matcher = pattern.matcher(fechasDesdeHasta);
		String start = "";
		String end = "";
		Timestamp fecha = null;
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (matcher.matches()) {// Verifico el Formato entregado por el DatePicker 23/08/2016 - 21/09/2016
			pattern = Pattern.compile("([0-9]{1,2})[/]([0-9]{2,})[/]([0-9]{4,})");
			matcher = pattern.matcher(fechasDesdeHasta);
			int cont = 1;
			while (matcher.find()) {
				String aa = matcher.group(3);
				String bb = aa.substring(2);
				if (cont == 1) {
					start = (matcher.group(1) + "/" + matcher.group(2) + "/" + bb)+" 00:00:00";
				}
				if (cont == 2) {
					end = (matcher.group(1) + "/" + matcher.group(2) + "/" + bb)+" 23:59:59";
				}
				cont++;
			}
			switch (Start_End) {
			case "Start":
				try {
					start = dateFormat.format(inputDateFormat.parse(start));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fecha = Timestamp.valueOf(start);
				break;
			case "End":
				try {
					end = dateFormat.format(inputDateFormat.parse(end));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fecha = Timestamp.valueOf(end);
				break;
			}
		}
		return fecha;
	}

	public String parseDate(String inputType, String outputType, String fecha) {
		DateFormat inputDateFormat = null;
		DateFormat dateFormat = null;
		if (null != fecha) {
			switch (inputType) {
			case "1":
				inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
				break;
			}

			switch (outputType) {
			case "1":
				dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				break;
			case "2":
				dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				break;
			case "3":
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				break;
			case "4":
				dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				break;
			case "day":
				dateFormat = new SimpleDateFormat("dd");
				break;
			case "month":
				dateFormat = new SimpleDateFormat("MMM");
				break;
			case "year":
				dateFormat = new SimpleDateFormat("yyyy");
				break;
			}

			try {
				fecha = dateFormat.format(inputDateFormat.parse(fecha));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return fecha;
	}

	public String fechaDate(int tipo, Date date) {
		String fecha = null;
		DateFormat dateFormat = null;
		switch (tipo) {
		case 1:
			dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			break;
		case 2:
			dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			break;
		case 3:
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			break;
		}
		fecha = dateFormat.format(date);
		return fecha;
	}

}