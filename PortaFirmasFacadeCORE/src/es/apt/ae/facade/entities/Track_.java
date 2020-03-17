package es.apt.ae.facade.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.182+0200")
@StaticMetamodel(Track.class)
public class Track_ {
	public static volatile SingularAttribute<Track, TrackPk> pk;
	public static volatile SingularAttribute<Track, String> tramitador;
	public static volatile SingularAttribute<Track, String> asignador;
	public static volatile SingularAttribute<Track, String> departamento;
	public static volatile SingularAttribute<Track, Date> fechaCreacion;
	public static volatile SingularAttribute<Track, Date> fechaInicio;
	public static volatile SingularAttribute<Track, Date> fechaFin;
}
