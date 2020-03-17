package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.511+0200")
@StaticMetamodel(CatPermisoDispositivo.class)
public class CatPermisoDispositivo_ {
	public static volatile SingularAttribute<CatPermisoDispositivo, String> id;
	public static volatile SingularAttribute<CatPermisoDispositivo, String> destinatarioId;
	public static volatile SingularAttribute<CatPermisoDispositivo, String> destinatarioTipo;
	public static volatile SingularAttribute<CatPermisoDispositivo, CatDispositivo> dispositivo;
	public static volatile SingularAttribute<CatPermisoDispositivo, String> permisoTipo;
}
