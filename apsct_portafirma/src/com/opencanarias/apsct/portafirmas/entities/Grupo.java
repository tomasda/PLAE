package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name = "PF_GRUPOS")
public class Grupo implements Serializable{

	private static final long serialVersionUID = -7235623055062169762L;
	@Id @GeneratedValue
	@Column(name = "ID_GRUPO")
	private Long id;
	@Column(name="FIRMANTES_REQUERIDOS")
	private int firmantesRequeridos;
	@Column(name="ORDEN")
	private Integer orden;
	@Column(name="CERRADO")
	private Boolean cerrado;
	
	@ManyToOne
	@JoinColumn(name = "ID_FLUJO")
	private CircuitoEntity circuito;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="grupo")
	private List<GrupoPersona> grupoPersona;
	
	@ManyToOne
	@JoinColumn(name="ID_TIPO_GRUPO")
	private TipoGrupo tipoGrupo;
	@Transient
	private int maximoFirmantes; //TODO: esto es la cantida d de personas del grupo: listPersonas.size();
	@Transient
	private int minimoFirmantes;
	
	public Grupo clone(Grupo grupoOrigen){
		this.cerrado = new Boolean(grupoOrigen.getCerrado() != null ? grupoOrigen.getCerrado() : false);
		this.firmantesRequeridos = new Integer(grupoOrigen.getFirmantesRequeridos());
		this.maximoFirmantes = new Integer(grupoOrigen.getMaximoFirmantes());
		this.minimoFirmantes = new Integer(grupoOrigen.getMinimoFirmantes());
		this.orden = new Integer(grupoOrigen.getOrden());
		this.tipoGrupo = grupoOrigen.getTipoGrupo();
		this.grupoPersona = new ArrayList<GrupoPersona>();
		for (GrupoPersona grupoPersona : grupoOrigen.getGrupoPersona()){
			GrupoPersona grupoPersonaAux = new GrupoPersona().clone(grupoPersona);
			grupoPersonaAux.setGrupo(this);
			this.grupoPersona.add(grupoPersonaAux);
		}
		return this;
	}
	
	public int getMinimoFirmantes() {
		return minimoFirmantes;
	}

	public void setMinimoFirmantes(int minimoFirmantes) {
		this.minimoFirmantes = minimoFirmantes;
	}

	public int getFirmantesRequeridos() {
		return firmantesRequeridos;
	}

	public void setFirmantesRequeridos(int firmantesRequeridos) {
		this.firmantesRequeridos = firmantesRequeridos;
	}

	public TipoGrupo getTipoGrupo() {
		return tipoGrupo;
	}

	public void setTipoGrupo(TipoGrupo tipoGrupo){
		this.tipoGrupo = tipoGrupo;
	}
	
	public void setTipo(TipoGrupo tipo) {
		this.tipoGrupo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Boolean getCerrado() {
		return cerrado;
	}

	public void setCerrado(Boolean cerrado) {
		this.cerrado = cerrado;
	}

	public CircuitoEntity getCircuito() {
		return circuito;
	}

	public void setCircuito(CircuitoEntity circuito) {
		this.circuito = circuito;
	}

	public List<GrupoPersona> getGrupoPersona() {
		if(null == grupoPersona){
			grupoPersona = new ArrayList<GrupoPersona>();
		}
		return grupoPersona;
	}

	public void setGrupoPersona(List<GrupoPersona> grupoPersona) {
		this.grupoPersona = grupoPersona;
	}

	public int getMaximoFirmantes() {//TODO: autocalculao
		if (grupoPersona != null){
			return grupoPersona.size();
		}
		return 1;
		
	}
}