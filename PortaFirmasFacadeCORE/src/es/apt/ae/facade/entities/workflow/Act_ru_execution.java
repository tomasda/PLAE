package es.apt.ae.facade.entities.workflow;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.apt.ae.facade.entities.Actividad;

@Entity
@Transactional
@Table(schema = "EAACTIVITI", name = "ACT_RU_EXECUTION")
public class Act_ru_execution implements Serializable {
	private static final long serialVersionUID = -7472200692950051326L;

	@Id
	@Column(name = "ID_")
	private String id;

	@OneToMany(targetEntity=Act_ru_task.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "PROC_INST_ID_")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Act_ru_task> tasks;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ 
		@JoinColumn(name = "ACT_ID_", referencedColumnName = "ID_"), 
		@JoinColumn(name = "PROC_DEF_ID_", referencedColumnName = "WF_ID_") 
	})
	private Actividad activity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Act_ru_task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Act_ru_task> tasks) {
		this.tasks = tasks;
	}

	public Actividad getActivity() {
		return activity;
	}

	public void setActivity(Actividad activity) {
		this.activity = activity;
	}

}
