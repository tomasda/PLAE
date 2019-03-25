/**
 * 
 */
package com.opencanarias.consola.registro.tablasComunes;

import java.io.Serializable;

/**
 * @author Tomás Delgado.
 *
 */
public class OrigenAsiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String origeAsiento;
	private String origenAsientoDescripcion;
	
	public OrigenAsiento() {
		
	}

	public String getOrigeAsiento() {
		return origeAsiento;
	}

	public void setOrigeAsiento(String origeAsiento) {
		this.origeAsiento = origeAsiento;
	}

	public String getOrigenAsientoDescripcion() {
		return origenAsientoDescripcion;
	}

	public void setOrigenAsientoDescripcion(String origenAsientoDescripcion) {
		this.origenAsientoDescripcion = origenAsientoDescripcion;
	}

	
}
