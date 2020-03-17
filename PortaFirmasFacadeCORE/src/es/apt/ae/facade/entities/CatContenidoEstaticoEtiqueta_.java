package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.511+0200")
@StaticMetamodel(CatContenidoEstaticoEtiqueta.class)
public class CatContenidoEstaticoEtiqueta_ {
	public static volatile SingularAttribute<CatContenidoEstaticoEtiqueta, Integer> id;
	public static volatile SingularAttribute<CatContenidoEstaticoEtiqueta, String> nombreEtiqueta;
	public static volatile SingularAttribute<CatContenidoEstaticoEtiqueta, String> textoEtiqueta;
	public static volatile SingularAttribute<CatContenidoEstaticoEtiqueta, Boolean> habilitado;
	public static volatile SingularAttribute<CatContenidoEstaticoEtiqueta, CatPlantilla> plantilla;
	public static volatile SingularAttribute<CatContenidoEstaticoEtiqueta, CatEtiqueta> etiqueta;
}
