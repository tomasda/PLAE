package es.apt.ae.facade.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.930+0200")
@StaticMetamodel(GestionPortafirmasDocumento.class)
public class GestionPortafirmasDocumento_ {
	public static volatile SingularAttribute<GestionPortafirmasDocumento, Long> id;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> numExpediente;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> actividad;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> procedimientoId;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> proceso;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> observaciones;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> descripcion;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, Long> idFirmante;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, Long> idCircuito;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> uriDocRepository;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> docId;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> estadoFirma;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> asuntoExpediente;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> solicitante;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, Date> fechaEnvioPortafirmas;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, Date> fechaRespuesta;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, Boolean> pfCorporativo;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> backoffice;
	public static volatile SingularAttribute<GestionPortafirmasDocumento, String> numActuacion;
}
