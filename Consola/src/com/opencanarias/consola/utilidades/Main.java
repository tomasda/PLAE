package com.opencanarias.consola.utilidades;

public class Main {

	public static void main(String[] args) {
		DateUtils d = new DateUtils();
		long f = d.timeStampFecha().getTime();
		System.out.println("Fecha "+f);

	}

}
