package es.apt.ae.facade.entities.portafirmas;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.112+0200")
@StaticMetamodel(Revision.class)
public class Revision_ {
	public static volatile SingularAttribute<Revision, Long> id;
	public static volatile SingularAttribute<Revision, Persona> persona;
	public static volatile ListAttribute<Revision, Revisor> listRevisor;
}
