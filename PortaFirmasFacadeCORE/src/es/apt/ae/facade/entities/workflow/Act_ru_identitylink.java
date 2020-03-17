package es.apt.ae.facade.entities.workflow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import es.apt.ae.facade.entities.Rol;

@Entity
@Transactional
@Table(schema = "EAACTIVITI", name = "ACT_RU_IDENTITYLINK")
public class Act_ru_identitylink implements Serializable {
	private static final long serialVersionUID = -7472200692950051326L;

	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "USER_ID_", nullable = true)
	private String user;
	@Column(name = "TYPE_")
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID_", referencedColumnName = "ID_")
	private Rol departament;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Rol getDepartament() {
		return departament;
	}

	public void setDepartament(Rol departament) {
		this.departament = departament;
	}

}
