package com.opencanarias.consola.commons;

import java.io.Serializable;


public class Funciones implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean trueOrFalse(int value) {
		if (value == 0) {
			return false;
		}
		if (value == 1) {
			return true;
		}
		return false;
	}
	
	public String expedienteState(String state){
		String state_="";
		switch (state){
		case "0": //"ACTIVO"
			state_="text-success";
			break;
		case "1": //"FINALIZADO"
			state_="text-dark-blue";
			break; 
		case "2": //"CANCELADO"
			state_="text-danger";
			break;
		}
		return state_;
	}
}
