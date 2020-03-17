package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "CatEtiqueta.findById", query = "SELECT ce FROM CatEtiqueta ce WHERE ce.id = :id"),
})
@Table(schema = "OC3F", name = "CAT_TAGS")
public class CatEtiqueta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "CLASE_EJECUCION")
	private String claseEjecucion;	

    @ManyToMany(mappedBy = "etiquetas")
    private List<CatPlantilla> plantillas = new ArrayList<CatPlantilla>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClaseEjecucion() {
		return claseEjecucion;
	}

	public void setClaseEjecucion(String claseEjecucion) {
		this.claseEjecucion = claseEjecucion;
	}

	public List<CatPlantilla> getPlantillas() {
		return plantillas;
	}

	public void setPlantillas(List<CatPlantilla> plantillas) {
		this.plantillas = plantillas;
	}
	
	
}
