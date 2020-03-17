package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.511+0200")
@StaticMetamodel(CatEtiqueta.class)
public class CatEtiqueta_ {
	public static volatile SingularAttribute<CatEtiqueta, Integer> id;
	public static volatile SingularAttribute<CatEtiqueta, String> nombre;
	public static volatile SingularAttribute<CatEtiqueta, String> descripcion;
	public static volatile SingularAttribute<CatEtiqueta, String> claseEjecucion;
	public static volatile ListAttribute<CatEtiqueta, CatPlantilla> plantillas;
}
