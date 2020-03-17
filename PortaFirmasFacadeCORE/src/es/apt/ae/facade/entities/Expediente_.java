package es.apt.ae.facade.entities;

import es.apt.ae.facade.entities.workflow.Act_ru_execution;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.902+0200")
@StaticMetamodel(Expediente.class)
public class Expediente_ {
	public static volatile SingularAttribute<Expediente, String> id;
	public static volatile SingularAttribute<Expediente, String> tipoNotificacion;
	public static volatile SingularAttribute<Expediente, Boolean> notificacionesRecaudatorias;
	public static volatile SingularAttribute<Expediente, Date> fechaCreacion;
	public static volatile SingularAttribute<Expediente, Date> fechaInicio;
	public static volatile SingularAttribute<Expediente, Date> fechaFin;
	public static volatile SingularAttribute<Expediente, Date> fechaResolucion;
	public static volatile SingularAttribute<Expediente, Date> fechaEfectivaResolucion;
	public static volatile SingularAttribute<Expediente, String> asunto;
	public static volatile SingularAttribute<Expediente, String> numRegEnt;
	public static volatile SingularAttribute<Expediente, String> uri;
	public static volatile SingularAttribute<Expediente, String> idWf;
	public static volatile SingularAttribute<Expediente, String> workflowVersion;
	public static volatile SingularAttribute<Expediente, String> dniInteresado;
	public static volatile SingularAttribute<Expediente, String> nombreInteresado;
	public static volatile SingularAttribute<Expediente, Short> estado;
	public static volatile SingularAttribute<Expediente, Procedimiento> procedimiento;
	public static volatile SingularAttribute<Expediente, Act_ru_execution> wfInstance;
	public static volatile ListAttribute<Expediente, TerceroExp> terceros;
	public static volatile ListAttribute<Expediente, Documento> documentos;
}
