package es.apt.ae.facade.entities.portafirmas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.026+0200")
@StaticMetamodel(CircuitoEntity.class)
public class CircuitoEntity_ {
	public static volatile SingularAttribute<CircuitoEntity, Long> id;
	public static volatile SingularAttribute<CircuitoEntity, Date> fechaCreacion;
	public static volatile SingularAttribute<CircuitoEntity, Integer> ordenActivo;
	public static volatile SingularAttribute<CircuitoEntity, String> descripcion;
	public static volatile ListAttribute<CircuitoEntity, Grupo> listGrupo;
	public static volatile SingularAttribute<CircuitoEntity, TipoCircuito> tipoCircuito;
	public static volatile SingularAttribute<CircuitoEntity, DocumentoPortafirmas> documento;
	public static volatile ListAttribute<CircuitoEntity, CircuitoPersona> listSolicitantesPermitidos;
}
