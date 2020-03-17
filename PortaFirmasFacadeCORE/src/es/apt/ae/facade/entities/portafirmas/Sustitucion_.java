package es.apt.ae.facade.entities.portafirmas;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.122+0200")
@StaticMetamodel(Sustitucion.class)
public class Sustitucion_ {
	public static volatile SingularAttribute<Sustitucion, Long> id;
	public static volatile SingularAttribute<Sustitucion, Persona> persona;
	public static volatile SingularAttribute<Sustitucion, Ausencia> ausencia;
}
