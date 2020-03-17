package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.127+0200")
@StaticMetamodel(Procedimiento.class)
public class Procedimiento_ {
	public static volatile SingularAttribute<Procedimiento, String> id;
	public static volatile SingularAttribute<Procedimiento, String> descripcion;
	public static volatile SingularAttribute<Procedimiento, String> wfId;
	public static volatile SingularAttribute<Procedimiento, Integer> activo;
	public static volatile SingularAttribute<Procedimiento, Boolean> modificarInteresado;
	public static volatile SingularAttribute<Procedimiento, Familia> familia;
}
