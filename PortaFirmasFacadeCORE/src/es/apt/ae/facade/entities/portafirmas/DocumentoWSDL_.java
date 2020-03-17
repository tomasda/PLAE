package es.apt.ae.facade.entities.portafirmas;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.076+0200")
@StaticMetamodel(DocumentoWSDL.class)
public class DocumentoWSDL_ {
	public static volatile SingularAttribute<DocumentoWSDL, Long> id;
	public static volatile SingularAttribute<DocumentoWSDL, DocumentoPortafirmas> documento;
	public static volatile SingularAttribute<DocumentoWSDL, String> url;
	public static volatile SingularAttribute<DocumentoWSDL, Boolean> informarEstadoIntermedio;
}
