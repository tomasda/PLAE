package es.apt.ae.facade.entities.portafirmas;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:37.088+0200")
@StaticMetamodel(Persona.class)
public class Persona_ {
	public static volatile SingularAttribute<Persona, Long> id;
	public static volatile SingularAttribute<Persona, String> nombre;
	public static volatile SingularAttribute<Persona, String> apellido1;
	public static volatile SingularAttribute<Persona, String> apellido2;
	public static volatile SingularAttribute<Persona, String> numIdentificacion;
	public static volatile SingularAttribute<Persona, String> cargo;
	public static volatile SingularAttribute<Persona, String> mail;
	public static volatile SingularAttribute<Persona, Boolean> firmante;
	public static volatile SingularAttribute<Persona, Boolean> validador;
	public static volatile SingularAttribute<Persona, Boolean> usuarioPruebas;
	public static volatile SingularAttribute<Persona, Boolean> adjuntosAlertas;
	public static volatile ListAttribute<Persona, Ausencia> listAusencia;
	public static volatile ListAttribute<Persona, GrupoPersona> grupoPersona;
	public static volatile ListAttribute<Persona, ProcesoFirma> accionSobreDocumento;
	public static volatile SingularAttribute<Persona, Revision> revision;
	public static volatile ListAttribute<Persona, Revisor> listRevisorDe;
	public static volatile ListAttribute<Persona, FirmantePersona> listSolicitantesPermitidos;
}
