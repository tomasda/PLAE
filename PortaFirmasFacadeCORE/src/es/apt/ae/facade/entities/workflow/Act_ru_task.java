package es.apt.ae.facade.entities.workflow;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.apt.ae.facade.entities.Actividad;
import es.apt.ae.facade.entities.Rol;

@Entity
@Transactional
@NamedQueries({
	@NamedQuery(name = Act_ru_task.findByInstance, query = "SELECT art FROM Act_ru_task art WHERE art.instance = :instance ORDER BY art.fechaInicio DESC"),
	@NamedQuery(name = Act_ru_task.findByInstanceAndActivityId, query = "SELECT art FROM Act_ru_task art WHERE art.instance = :instance AND art.activity.id = :activityId ORDER BY art.fechaInicio DESC"),
})
@Table(schema = "EAACTIVITI", name = "ACT_RU_TASK")
public class Act_ru_task implements Serializable {
	private static final long serialVersionUID = -5693801749355382322L;

	public static final String findByInstance = "Act_ru_task.findByInstance";
	public static final String findByInstanceAndActivityId = "Act_ru_task.findByInstanceAndActivityId";
	
	@Id
	@Column(name = "ID_")
	private Long id;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "DESCRIPTION_")
	private String description;
	@Column(name = "OWNER_")
	private String owner;
	@Column(name = "ASSIGNEE_")
	private String assignee;
	@Column(name = "CREATE_TIME_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	@Column(name = "PROC_INST_ID_")
	private String instance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ 
		@JoinColumn(name = "TASK_DEF_KEY_", referencedColumnName = "ID_"), 
		@JoinColumn(name = "PROC_DEF_ID_", referencedColumnName = "WF_ID_") 
	})
	private Actividad activity;	

	@OneToMany(targetEntity=Act_ru_identitylink.class, fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "TASK_ID_")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Act_ru_identitylink> executors;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Actividad getActivity() {
		return activity;
	}

	public void setActivity(Actividad activity) {
		this.activity = activity;
	}

	public List<Act_ru_identitylink> getExecutors() {
		return executors;
	}

	public void setExecutors(List<Act_ru_identitylink> executors) {
		this.executors = executors;
	}

	@Transient
	public String getDepartamento() {
		if (this.executors != null && !this.executors.isEmpty()) {
			for (Act_ru_identitylink executor:this.executors) {
				if (executor.getUser() == null && executor.getDepartament() != null) {
					Rol dpt = executor.getDepartament();
					return dpt.getId();
				}
			}
		} 
		return null;
	}

}
