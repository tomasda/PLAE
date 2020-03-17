package es.apt.ae.facade.entities.callback;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.511+0200")
@StaticMetamodel(CallbackTimer.class)
public class CallbackTimer_ {
	public static volatile SingularAttribute<CallbackTimer, Integer> id;
	public static volatile SingularAttribute<CallbackTimer, String> entidadId;
	public static volatile SingularAttribute<CallbackTimer, String> hash;
	public static volatile SingularAttribute<CallbackTimer, Integer> intentos;
	public static volatile SingularAttribute<CallbackTimer, String> email;
	public static volatile SingularAttribute<CallbackTimer, String> dni;
	public static volatile SingularAttribute<CallbackTimer, String> actorDni;
	public static volatile SingularAttribute<CallbackTimer, String> estado;
	public static volatile SingularAttribute<CallbackTimer, String> observaciones;
	public static volatile SingularAttribute<CallbackTimer, Date> fecha;
	public static volatile SingularAttribute<CallbackTimer, Boolean> alertaEnviada;
	public static volatile SingularAttribute<CallbackTimer, Boolean> estadoIntermedio;
	public static volatile SingularAttribute<CallbackTimer, String> wsdl;
	public static volatile SingularAttribute<CallbackTimer, String> tipo;
}
