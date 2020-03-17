package com.opencanarias.apsct.portafirmas.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.080+0200")
@StaticMetamodel(FirmantePersona.class)
public class FirmantePersona_ {
	public static volatile SingularAttribute<FirmantePersona, Long> id;
	public static volatile SingularAttribute<FirmantePersona, Persona> persona;
	public static volatile SingularAttribute<FirmantePersona, String> solicitanteDNI;
}
