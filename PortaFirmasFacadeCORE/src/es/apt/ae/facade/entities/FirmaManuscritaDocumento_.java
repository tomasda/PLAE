package es.apt.ae.facade.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.917+0200")
@StaticMetamodel(FirmaManuscritaDocumento.class)
public class FirmaManuscritaDocumento_ {
	public static volatile SingularAttribute<FirmaManuscritaDocumento, Long> id;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> numExpediente;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> numActuacion;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> docNombre;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> uriDocRepositorio;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> actividad;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> docGui;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> estado;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> solicitanteDni;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> solicitanteEmail;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, Long> idBackoffice;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, Date> fechaEnvio;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, Date> fechaFin;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, String> observaciones;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, Boolean> docActualizadoRepositorio;
	public static volatile SingularAttribute<FirmaManuscritaDocumento, Boolean> notificacion;
	public static volatile ListAttribute<FirmaManuscritaDocumento, FirmaManuscritaDocumentoDestinatario> destinatarios;
	public static volatile ListAttribute<FirmaManuscritaDocumento, FirmaManuscritaDocumentoWSDL> wsdls;
}
