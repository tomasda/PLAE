package es.apt.ae.facade.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.926+0200")
@StaticMetamodel(FirmaManuscritaDocumentoDestinatario.class)
public class FirmaManuscritaDocumentoDestinatario_ {
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, Long> id;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, FirmaManuscritaDocumento> firmaManuscritaDoc;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, String> idDispositivo;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, String> identificadorFirmante;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, String> nombreFirmante;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, String> estado;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, Date> fechaAccion;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, String> observaciones;
	public static volatile SingularAttribute<FirmaManuscritaDocumentoDestinatario, Short> orden;
}
