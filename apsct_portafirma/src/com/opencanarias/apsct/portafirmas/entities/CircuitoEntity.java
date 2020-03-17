package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "PF_FLUJOS")

@NamedQueries(value = { 
		@NamedQuery(name="getCircuitoById",query="SELECT c FROM CircuitoEntity c WHERE c.id = :Id"),
		@NamedQuery(name="getListCircuito",query="SELECT c FROM CircuitoEntity c join c.tipoCircuito tc WHERE tc.codigo = :codigo"),
		@NamedQuery(name="findCircuitobyUri", query="SELECT c FROM DocumentoPortafirmas d JOIN d.circuito c JOIN c.listGrupo g JOIN g.grupoPersona gp JOIN gp.persona p WHERE d.uri = :uri"),
		@NamedQuery(name="findCircuitosByTipoAndSolicitante", query="SELECT c FROM CircuitoEntity c JOIN c.listSolicitantesPermitidos s JOIN c.tipoCircuito tc WHERE s.solicitanteDNI = :dni AND tc.codigo = :codigo"),
		@NamedQuery(name="getCircuitoByTipoAndId", query="SELECT c FROM CircuitoEntity c JOIN c.tipoCircuito tc WHERE tc.codigo = :codigo AND c.id = :id"),
		@NamedQuery(name="getCircuitoByTipoAndIdAndSolicitante", query="SELECT c FROM CircuitoEntity c JOIN c.listSolicitantesPermitidos s JOIN c.tipoCircuito tc WHERE s.solicitanteDNI = :dni AND tc.codigo = :codigo AND c.id = :id"),
})


public class CircuitoEntity implements Serializable{
	
	private static final long serialVersionUID = 9099737804921918662L;
	
	@Id @GeneratedValue
	@Column(name = "ID_FLUJO")
	private Long id;	
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	@Column(name="ORDEN_ACTIVO")
	private int ordenActivo;
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@OneToMany (cascade=CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval=true)
    @OrderBy("orden ASC")
	@JoinColumn(name="ID_FLUJO")
	private List<Grupo> listGrupo;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_FLUJO")
	private TipoCircuito tipoCircuito;
	
	@OneToOne (mappedBy="circuito")
	private DocumentoPortafirmas documento;
	
	@OneToMany(mappedBy="circuito")
	private List<CircuitoPersona> listSolicitantesPermitidos;
	
	
	public CircuitoEntity clone(CircuitoEntity circuitoOrigen){
		this.documento = circuitoOrigen.getDocumento();
		this.fechaCreacion = new Date (circuitoOrigen.getFechaCreacion().getTime());
		this.ordenActivo = new Integer(circuitoOrigen.getOrdenActivo());
		this.tipoCircuito = circuitoOrigen.getTipoCircuito();
		this.listGrupo = new ArrayList<Grupo>();
		for (Grupo grupoAux : circuitoOrigen.getListGrupo()){
			this.listGrupo.add(new Grupo().clone(grupoAux));
		}
		return this;
	}
	
	
	public TipoCircuito getTipoCircuito() {
		return tipoCircuito;
	}

	public void setTipoCircuito(TipoCircuito tipoCircuito) {
		this.tipoCircuito = tipoCircuito;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setListGrupo(List<Grupo> listGrupo) {
		this.listGrupo = listGrupo;
	}

	public List<Grupo> getListGrupo() {
		return listGrupo;
	}
	public CircuitoEntity populate2(){
		CircuitoEntity circuito = new CircuitoEntity();
		circuito.setFechaCreacion(getFechaCreacion());
		circuito.setListGrupo(getListGrupo());
		circuito.setTipoCircuito(getTipoCircuito());	
		return circuito;
	}

	public CircuitoEntity populate(){
		CircuitoEntity circuito = new CircuitoEntity();
		circuito.setFechaCreacion(getFechaCreacion());
		List<Grupo> listGrupoAux = new ArrayList<Grupo>();
		for (Grupo grupo : listGrupo){
			Grupo grupoAux = new Grupo();
			listGrupoAux.add(grupoAux.clone(grupo));
		}
		circuito.setListGrupo(listGrupoAux);
		circuito.setTipoCircuito(getTipoCircuito());
		circuito.setOrdenActivo(getOrdenActivo());
		return circuito;
	}

	
	public int getOrdenActivo() {
		return ordenActivo;
	}

	public void setOrdenActivo(int ordenActivo) {
		this.ordenActivo = ordenActivo;
	}

	public DocumentoPortafirmas getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoPortafirmas documento) {
		this.documento = documento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
