package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.937+0200")
@StaticMetamodel(IndiceCapituloDocumento.class)
public class IndiceCapituloDocumento_ {
	public static volatile SingularAttribute<IndiceCapituloDocumento, DocumentoIndicePk> pk;
	public static volatile SingularAttribute<IndiceCapituloDocumento, IndiceCapitulo> capitulo;
	public static volatile SingularAttribute<IndiceCapituloDocumento, Short> ordenEnCapitulo;
}
