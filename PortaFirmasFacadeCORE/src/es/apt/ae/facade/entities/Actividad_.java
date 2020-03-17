package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.222+0200")
@StaticMetamodel(Actividad.class)
public class Actividad_ {
	public static volatile SingularAttribute<Actividad, String> id;
	public static volatile SingularAttribute<Actividad, String> wfId;
	public static volatile SingularAttribute<Actividad, String> wfVersion;
	public static volatile SingularAttribute<Actividad, String> descripcion;
	public static volatile SingularAttribute<Actividad, String> plazo;
	public static volatile SingularAttribute<Actividad, String> alerta;
	public static volatile SingularAttribute<Actividad, String> alertaCondicion;
	public static volatile SingularAttribute<Actividad, String> alertaDepartamentos;
	public static volatile SingularAttribute<Actividad, String> notificaciones;
	public static volatile SingularAttribute<Actividad, Fase> fase;
}
