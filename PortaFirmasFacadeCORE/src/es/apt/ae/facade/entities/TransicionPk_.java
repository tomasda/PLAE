package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.188+0200")
@StaticMetamodel(TransicionPk.class)
public class TransicionPk_ {
	public static volatile SingularAttribute<TransicionPk, String> wfId;
	public static volatile SingularAttribute<TransicionPk, String> actividadOrigenId;
	public static volatile SingularAttribute<TransicionPk, String> actividadDestinoId;
	public static volatile SingularAttribute<TransicionPk, Short> numTransicion;
}
