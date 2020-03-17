package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.179+0200")
@StaticMetamodel(Rol.class)
public class Rol_ {
	public static volatile SingularAttribute<Rol, String> id;
	public static volatile SingularAttribute<Rol, String> descripcion;
	public static volatile SingularAttribute<Rol, String> grupoAlfresco;
	public static volatile SingularAttribute<Rol, Boolean> decretable;
	public static volatile SingularAttribute<Rol, Boolean> decretableRegistro;
	public static volatile SingularAttribute<Rol, Boolean> habilitado;
}
