package com.opencanarias.utils;
public class PerformanceUtils {
	/**
	 * Calcula el tiempo transcurrido desde un instante determinado
	 * @param time Fecha inicial a partir de la que hacer el calculo
	 * @return Tiempo transcurrido.
	 */
	public static String tiempoTranscurridoDesde(Long time) {
		long actual = System.currentTimeMillis();
		long total = actual - time;
		
		long horas = total / 3600000;
		long minutos = (total % 3600000) / 60000;
		long segundos = ((total % 360000) % 60000) / 1000;
		long milisegundos = ((total % 360000) % 60000) % 1000;
		
		return (horas + "h:" + minutos + "m:" + segundos + "s:" + milisegundos + "ms");
	}
}
