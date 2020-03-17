package es.apt.ae.facade.entities.workflow;

import es.apt.ae.facade.entities.Rol;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.192+0200")
@StaticMetamodel(Act_ru_identitylink.class)
public class Act_ru_identitylink_ {
	public static volatile SingularAttribute<Act_ru_identitylink, String> id;
	public static volatile SingularAttribute<Act_ru_identitylink, String> user;
	public static volatile SingularAttribute<Act_ru_identitylink, String> type;
	public static volatile SingularAttribute<Act_ru_identitylink, Rol> departament;
}
