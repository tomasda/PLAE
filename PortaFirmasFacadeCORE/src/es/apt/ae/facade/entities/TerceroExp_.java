package es.apt.ae.facade.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.181+0200")
@StaticMetamodel(TerceroExp.class)
public class TerceroExp_ {
	public static volatile SingularAttribute<TerceroExp, Long> id;
	public static volatile SingularAttribute<TerceroExp, String> identificacion;
	public static volatile SingularAttribute<TerceroExp, String> tipoTercero;
	public static volatile SingularAttribute<TerceroExp, String> tipoRelacion;
	public static volatile SingularAttribute<TerceroExp, String> tipoDocumento;
	public static volatile SingularAttribute<TerceroExp, String> nombre;
	public static volatile SingularAttribute<TerceroExp, String> apellido1;
	public static volatile SingularAttribute<TerceroExp, String> apellido2;
	public static volatile SingularAttribute<TerceroExp, String> email;
	public static volatile SingularAttribute<TerceroExp, String> nif;
	public static volatile SingularAttribute<TerceroExp, String> cif;
	public static volatile SingularAttribute<TerceroExp, String> razonSocial;
	public static volatile SingularAttribute<TerceroExp, String> telefono;
	public static volatile SingularAttribute<TerceroExp, String> calle;
	public static volatile SingularAttribute<TerceroExp, String> codigoPostal;
	public static volatile SingularAttribute<TerceroExp, String> localidad;
	public static volatile SingularAttribute<TerceroExp, String> provincia;
	public static volatile SingularAttribute<TerceroExp, String> direccion;
	public static volatile SingularAttribute<TerceroExp, String> pais;
	public static volatile SingularAttribute<TerceroExp, Boolean> notificacionesRecaudatorias;
	public static volatile SingularAttribute<TerceroExp, String> tipoNotificacion;
	public static volatile SingularAttribute<TerceroExp, Boolean> cancelado;
	public static volatile SingularAttribute<TerceroExp, Date> fechaCancelacion;
	public static volatile SingularAttribute<TerceroExp, String> numExpediente;
}
