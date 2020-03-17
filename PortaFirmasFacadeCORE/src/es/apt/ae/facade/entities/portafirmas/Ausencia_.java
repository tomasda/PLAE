package es.apt.ae.facade.entities.portafirmas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.006+0200")
@StaticMetamodel(Ausencia.class)
public class Ausencia_ {
	public static volatile SingularAttribute<Ausencia, Long> id;
	public static volatile SingularAttribute<Ausencia, Date> fechaInicio;
	public static volatile SingularAttribute<Ausencia, Date> fechaFin;
	public static volatile SingularAttribute<Ausencia, Persona> ausentado;
	public static volatile SingularAttribute<Ausencia, Sustitucion> sustituto;
}
