package es.apt.ae.facade.entities.workflow;

import es.apt.ae.facade.entities.Actividad;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.191+0200")
@StaticMetamodel(Act_ru_execution.class)
public class Act_ru_execution_ {
	public static volatile SingularAttribute<Act_ru_execution, String> id;
	public static volatile ListAttribute<Act_ru_execution, Act_ru_task> tasks;
	public static volatile SingularAttribute<Act_ru_execution, Actividad> activity;
}
