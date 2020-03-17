package es.apt.ae.facade.entities.workflow;

import es.apt.ae.facade.entities.Actividad;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.194+0200")
@StaticMetamodel(Act_ru_task.class)
public class Act_ru_task_ {
	public static volatile SingularAttribute<Act_ru_task, Long> id;
	public static volatile SingularAttribute<Act_ru_task, String> name;
	public static volatile SingularAttribute<Act_ru_task, String> description;
	public static volatile SingularAttribute<Act_ru_task, String> owner;
	public static volatile SingularAttribute<Act_ru_task, String> assignee;
	public static volatile SingularAttribute<Act_ru_task, Date> fechaInicio;
	public static volatile SingularAttribute<Act_ru_task, String> instance;
	public static volatile SingularAttribute<Act_ru_task, Actividad> activity;
	public static volatile ListAttribute<Act_ru_task, Act_ru_identitylink> executors;
}
