package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
//	@NamedQuery(name = CatPropiedadesConfiguracion.findById, query = "SELECT pc FROM CatPropiedadesConfiguracion pc WHERE pc.id = :id"),
//	@NamedQuery(name = CatPropiedadesConfiguracion.findByPrefix, query = "SELECT pc FROM CatPropiedadesConfiguracion pc WHERE pc.id LIKE :id"),	
	@NamedQuery(name = CatPropiedadesConfiguracion.findById, query = "SELECT pc FROM CatPropiedadesConfiguracion AS pc WHERE pc.id = :id"),
	@NamedQuery(name = CatPropiedadesConfiguracion.findByPrefix, query = "SELECT pc FROM CatPropiedadesConfiguracion AS pc WHERE pc.id LIKE :id"),
})
//@Table(schema = "OC3F", name = "CAT_PROPIEDADES_CONFIGURACION")
@Table(name = "CAT_PROPIEDADES_CONFIGURACION")
public class CatPropiedadesConfiguracion implements Serializable {
	
	public static final String findById = "CatPropiedadesConfiguracion.findById";
	public static final String findByPrefix = "CatPropiedadesConfiguracion.findByPrefix";
	
	private static final long serialVersionUID = 8583673722566645895L;

	@Id
	@Column(name = "ID")
	private String id;
	@Id
	@Column(name = "DESCRIPCION")
	private String descripcion;	
	@Column(name = "VALOR")
	private String valor;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

}
