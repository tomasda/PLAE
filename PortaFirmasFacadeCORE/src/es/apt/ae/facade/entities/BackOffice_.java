package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.511+0200")
@StaticMetamodel(BackOffice.class)
public class BackOffice_ {
	public static volatile SingularAttribute<BackOffice, Long> id;
	public static volatile SingularAttribute<BackOffice, String> codigo;
	public static volatile SingularAttribute<BackOffice, String> descripcion;
	public static volatile SingularAttribute<BackOffice, String> username;
	public static volatile SingularAttribute<BackOffice, Boolean> informarEstadoIntermedioPF;
	public static volatile SingularAttribute<BackOffice, Boolean> informarEstadoIntermedioFM;
}
