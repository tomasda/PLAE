package es.apt.ae.facade.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.529+0200")
@StaticMetamodel(Documento.class)
public class Documento_ {
	public static volatile SingularAttribute<Documento, DocumentoPk> pk;
	public static volatile SingularAttribute<Documento, String> contentType;
	public static volatile SingularAttribute<Documento, String> descripcion;
	public static volatile SingularAttribute<Documento, String> uri;
	public static volatile SingularAttribute<Documento, Long> hasDocument;
	public static volatile SingularAttribute<Documento, String> activity;
	public static volatile SingularAttribute<Documento, Date> creationDate;
	public static volatile SingularAttribute<Documento, String> signReference;
	public static volatile SingularAttribute<Documento, String> signActivity;
	public static volatile SingularAttribute<Documento, String> recordNumber;
	public static volatile SingularAttribute<Documento, Date> recordDate;
	public static volatile SingularAttribute<Documento, String> recordType;
	public static volatile SingularAttribute<Documento, String> type;
	public static volatile SingularAttribute<Documento, Long> current;
	public static volatile SingularAttribute<Documento, String> state;
	public static volatile SingularAttribute<Documento, String> documentType;
	public static volatile SingularAttribute<Documento, Date> elaborationDate;
	public static volatile SingularAttribute<Documento, Double> scale;
	public static volatile SingularAttribute<Documento, Long> invertWatermark;
	public static volatile SingularAttribute<Documento, Integer> rotation;
	public static volatile SingularAttribute<Documento, String> actuacionId;
	public static volatile SingularAttribute<Documento, Date> cancelDate;
	public static volatile SingularAttribute<Documento, String> cancelUserId;
	public static volatile SingularAttribute<Documento, String> docOriginalUri;
}
