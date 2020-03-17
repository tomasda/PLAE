package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.529+0200")
@StaticMetamodel(CatProcedimientoRol.class)
public class CatProcedimientoRol_ {
	public static volatile SingularAttribute<CatProcedimientoRol, String> id;
	public static volatile SingularAttribute<CatProcedimientoRol, String> departamentoId;
	public static volatile SingularAttribute<CatProcedimientoRol, String> familiaId;
	public static volatile SingularAttribute<CatProcedimientoRol, Procedimiento> procedimiento;
}
