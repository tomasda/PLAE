package es.apt.ae.facade.dto;

import java.io.Serializable;

public class PeticionesItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer numPeticionesTotal = 0;
	private Integer numPeticionesPendientes = 0;
	private Integer numPeticionesAceptadas = 0;
	private Integer numPeticionesRechazadas = 0;
	private Integer numPeticionesFinalizadas = 0;
	private Boolean peticionesFinalizadasRango;
	
	public PeticionesItem() {
		super();
	}
	
	public PeticionesItem(Integer numPeticionesTotal, Integer numPeticionesPendientes, 
			Integer numPeticionesAceptadas, Integer numPeticionesRechazadas, Integer numPeticionesFinalizadas) {
		super();
		this.numPeticionesTotal = numPeticionesTotal;
		this.numPeticionesPendientes = numPeticionesPendientes;
		this.numPeticionesAceptadas = numPeticionesAceptadas;
		this.numPeticionesRechazadas = numPeticionesRechazadas;
		this.numPeticionesFinalizadas = numPeticionesFinalizadas;
	}

	public Integer getNumPeticionesTotal() {
		return numPeticionesTotal;
	}

	public void setNumPeticionesTotal(Integer numPeticionesTotal) {
		this.numPeticionesTotal = numPeticionesTotal;
	}

	public Integer getNumPeticionesPendientes() {
		return numPeticionesPendientes;
	}

	public void setNumPeticionesPendientes(Integer numPeticionesPendientes) {
		this.numPeticionesPendientes = numPeticionesPendientes;
	}

	public Integer getNumPeticionesAceptadas() {
		return numPeticionesAceptadas;
	}

	public void setNumPeticionesAceptadas(Integer numPeticionesAceptadas) {
		this.numPeticionesAceptadas = numPeticionesAceptadas;
	}

	public Integer getNumPeticionesRechazadas() {
		return numPeticionesRechazadas;
	}

	public void setNumPeticionesRechazadas(Integer numPeticionesRechazadas) {
		this.numPeticionesRechazadas = numPeticionesRechazadas;
	}

	public Integer getNumPeticionesFinalizadas() {
		return numPeticionesFinalizadas;
	}

	public void setNumPeticionesFinalizadas(Integer numPeticionesFinalizadas) {
		this.numPeticionesFinalizadas = numPeticionesFinalizadas;
	}

	public Boolean getPeticionesFinalizadasRango() {
		return peticionesFinalizadasRango;
	}

	public void setPeticionesFinalizadasRango(Boolean peticionesFinalizadasRango) {
		this.peticionesFinalizadasRango = peticionesFinalizadasRango;
	}
	
}
