package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.HashMap;

public class GeneracionPlantillaInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> mapaEtiquetas = null;
	private HashMap<String, byte[]> mapaImagenes = null;
	
	
	public GeneracionPlantillaInfo(HashMap<String, Object> mapaEtiquetas, HashMap<String, byte[]> mapaImagenes) {
		super();
		this.mapaEtiquetas = mapaEtiquetas;
		this.mapaImagenes = mapaImagenes;
	}

	
	public HashMap<String, Object> getMapaEtiquetas() {
		return mapaEtiquetas;
	}
	public void setMapaEtiquetas(HashMap<String, Object> mapaEtiquetas) {
		this.mapaEtiquetas = mapaEtiquetas;
	}
	public HashMap<String, byte[]> getMapaImagenes() {
		return mapaImagenes;
	}
	public void setMapaImagenes(HashMap<String, byte[]> mapaImagenes) {
		this.mapaImagenes = mapaImagenes;
	}
	
}
