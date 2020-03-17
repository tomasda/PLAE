package es.apt.ae.facade.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.931+0200")
@StaticMetamodel(IdsExpedientes.class)
public class IdsExpedientes_ {
	public static volatile SingularAttribute<IdsExpedientes, String> codigo;
	public static volatile SingularAttribute<IdsExpedientes, Integer> secuencia;
	public static volatile SingularAttribute<IdsExpedientes, String> formato;
	public static volatile SingularAttribute<IdsExpedientes, Boolean> reiniciable;
	public static volatile SingularAttribute<IdsExpedientes, Integer> secuenciaInicial;
	public static volatile SingularAttribute<IdsExpedientes, Date> ultimoAcceso;
}
