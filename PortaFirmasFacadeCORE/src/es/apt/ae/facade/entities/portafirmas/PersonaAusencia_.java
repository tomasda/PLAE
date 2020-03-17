package es.apt.ae.facade.entities.portafirmas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.093+0200")
@StaticMetamodel(PersonaAusencia.class)
public class PersonaAusencia_ {
	public static volatile SingularAttribute<PersonaAusencia, Long> id;
	public static volatile SingularAttribute<PersonaAusencia, Date> fechaInicio;
	public static volatile SingularAttribute<PersonaAusencia, Date> fechaFin;
	public static volatile SingularAttribute<PersonaAusencia, Persona> ausentado;
	public static volatile SingularAttribute<PersonaAusencia, Persona> sustituto;
}
