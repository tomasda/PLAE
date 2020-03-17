package es.apt.ae.facade.entities.portafirmas;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.031+0200")
@StaticMetamodel(CircuitoPersona.class)
public class CircuitoPersona_ {
	public static volatile SingularAttribute<CircuitoPersona, Long> id;
	public static volatile SingularAttribute<CircuitoPersona, CircuitoEntity> circuito;
	public static volatile SingularAttribute<CircuitoPersona, String> solicitanteDNI;
}
