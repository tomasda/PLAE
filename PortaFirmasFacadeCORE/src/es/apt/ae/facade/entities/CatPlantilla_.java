package es.apt.ae.facade.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-10-11T10:07:36.529+0200")
@StaticMetamodel(CatPlantilla.class)
public class CatPlantilla_ {
	public static volatile SingularAttribute<CatPlantilla, Integer> id;
	public static volatile SingularAttribute<CatPlantilla, String> actividadId;
	public static volatile SingularAttribute<CatPlantilla, String> procedimientoId;
	public static volatile SingularAttribute<CatPlantilla, String> nombrePlantilla;
	public static volatile SingularAttribute<CatPlantilla, String> descripcionPlantilla;
	public static volatile SingularAttribute<CatPlantilla, String> condicion;
	public static volatile SingularAttribute<CatPlantilla, Boolean> automatica;
	public static volatile SingularAttribute<CatPlantilla, Boolean> requiereFirma;
	public static volatile SingularAttribute<CatPlantilla, Boolean> requiereRegistroSalida;
	public static volatile SingularAttribute<CatPlantilla, Boolean> requiereNotificacion;
	public static volatile SingularAttribute<CatPlantilla, String> rutaRepositorio;
	public static volatile SingularAttribute<CatPlantilla, CatTipoDocumento> tipoDocumento;
	public static volatile ListAttribute<CatPlantilla, CatEtiqueta> etiquetas;
	public static volatile SingularAttribute<CatPlantilla, String> formatoDocSalida;
}
