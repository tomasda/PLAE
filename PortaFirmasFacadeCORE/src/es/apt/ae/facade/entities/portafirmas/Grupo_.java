package es.apt.ae.facade.entities.portafirmas;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.083+0200")
@StaticMetamodel(Grupo.class)
public class Grupo_ {
	public static volatile SingularAttribute<Grupo, Long> id;
	public static volatile SingularAttribute<Grupo, Integer> firmantesRequeridos;
	public static volatile SingularAttribute<Grupo, Integer> orden;
	public static volatile SingularAttribute<Grupo, Boolean> cerrado;
	public static volatile SingularAttribute<Grupo, CircuitoEntity> circuito;
	public static volatile ListAttribute<Grupo, GrupoPersona> grupoPersona;
	public static volatile SingularAttribute<Grupo, TipoGrupo> tipoGrupo;
}
