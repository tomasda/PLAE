package com.opencanarias.apsct.portafirmas.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.086+0200")
@StaticMetamodel(GrupoPersona.class)
public class GrupoPersona_ {
	public static volatile SingularAttribute<GrupoPersona, Long> id;
	public static volatile SingularAttribute<GrupoPersona, Grupo> grupo;
	public static volatile SingularAttribute<GrupoPersona, Persona> persona;
	public static volatile SingularAttribute<GrupoPersona, Boolean> obligatorio;
	public static volatile SingularAttribute<GrupoPersona, Boolean> realizado;
}
