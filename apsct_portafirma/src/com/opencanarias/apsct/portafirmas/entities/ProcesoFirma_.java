package com.opencanarias.apsct.portafirmas.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.102+0200")
@StaticMetamodel(ProcesoFirma.class)
public class ProcesoFirma_ {
	public static volatile SingularAttribute<ProcesoFirma, Long> id;
	public static volatile SingularAttribute<ProcesoFirma, Date> fechaAccion;
	public static volatile SingularAttribute<ProcesoFirma, String> observaciones;
	public static volatile SingularAttribute<ProcesoFirma, Persona> persona;
	public static volatile SingularAttribute<ProcesoFirma, DocumentoPortafirmas> documento;
	public static volatile SingularAttribute<ProcesoFirma, Accion> accion;
}
