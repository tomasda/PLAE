package es.apt.ae.facade.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "CatContenidoEstaticoEtiqueta.findByPlantillaIdAndEtiquetaId", query = "SELECT cee FROM CatContenidoEstaticoEtiqueta cee WHERE cee.plantilla.id = :plantillaId " + 
						"AND cee.etiqueta.id = :etiquetaId AND cee.habilitado = true"),
})
@Table(schema = "OC3F", name = "TAG_TEMPLATE_CONTENTS")
public class CatContenidoEstaticoEtiqueta implements Comparable<CatContenidoEstaticoEtiqueta>  {
	
	@Id
	@Column(name = "ID")
	Integer id;
	
//	@Column(name = "PROC_ID")	
//	private String procedimientoId;
	
	@Column(name = "DESCRIPCION_TEXTO")		
	String nombreEtiqueta;
	
	@Column(name = "FIELD_CONTENT")		
	String textoEtiqueta;

	@Column(name = "TAG_CONTENT_ENABLED")		
	Boolean habilitado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEMPLATE_ID", referencedColumnName = "ID")
	private CatPlantilla plantilla;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAG_ID", referencedColumnName = "ID")
	private CatEtiqueta etiqueta;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public String getProcedimientoId() {
//		return procedimientoId;
//	}
//
//	public void setProcedimientoId(String procedimientoId) {
//		this.procedimientoId = procedimientoId;
//	}

	public String getNombreEtiqueta() {
		return nombreEtiqueta;
	}

	public void setNombreEtiqueta(String nombreEtiqueta) {
		this.nombreEtiqueta = nombreEtiqueta;
	}

	public String getTextoEtiqueta() {
		return textoEtiqueta;
	}

	public void setTextoEtiqueta(String textoEtiqueta) {
		this.textoEtiqueta = textoEtiqueta;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public CatPlantilla getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(CatPlantilla plantilla) {
		this.plantilla = plantilla;
	}

	public CatEtiqueta getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(CatEtiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}


	@Override
	public int compareTo(CatContenidoEstaticoEtiqueta otherTag) {
		if ((otherTag != null) && (otherTag.nombreEtiqueta != null) && (this.nombreEtiqueta != null)) {
			return otherTag.nombreEtiqueta.compareTo(this.nombreEtiqueta);
		} else {
			return -1;
		}
	}

}
