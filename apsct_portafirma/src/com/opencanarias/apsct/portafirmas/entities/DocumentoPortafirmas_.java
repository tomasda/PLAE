package com.opencanarias.apsct.portafirmas.entities;

import es.apt.ae.facade.entities.BackOffice;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.063+0200")
@StaticMetamodel(DocumentoPortafirmas.class)
public class DocumentoPortafirmas_ {
	public static volatile SingularAttribute<DocumentoPortafirmas, Long> id;
	public static volatile SingularAttribute<DocumentoPortafirmas, Date> fechaSubidaPortafirmas;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> uri;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> subidoPorDNI;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> subidoPorNombre;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> tipoFirma;
	public static volatile SingularAttribute<DocumentoPortafirmas, Boolean> comunicado;
	public static volatile SingularAttribute<DocumentoPortafirmas, CircuitoEntity> circuito;
	public static volatile SingularAttribute<DocumentoPortafirmas, EstadoDocumento> estadoDocumento;
	public static volatile ListAttribute<DocumentoPortafirmas, ProcesoFirma> accionSobreDocumento;
	public static volatile SingularAttribute<DocumentoPortafirmas, BackOffice> sistemaOrigen;
	public static volatile SingularAttribute<DocumentoPortafirmas, Departamento> departamento;
	public static volatile ListAttribute<DocumentoPortafirmas, DocumentoWSDL> listUrl;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> asunto;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> docRelacionada;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> urlDetalleOrigen;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> nombre;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> hash;
	public static volatile SingularAttribute<DocumentoPortafirmas, String> mailCreador;
}
