package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.935+0200")
@StaticMetamodel(IndiceCapitulo.class)
public class IndiceCapitulo_ {
	public static volatile SingularAttribute<IndiceCapitulo, Integer> id;
	public static volatile SingularAttribute<IndiceCapitulo, String> titulo;
	public static volatile SingularAttribute<IndiceCapitulo, Integer> idSeccion;
	public static volatile SingularAttribute<IndiceCapitulo, Short> ordenEnSeccion;
}
