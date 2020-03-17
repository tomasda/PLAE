package com.opencanarias.apsct.portafirmas.utils;

import java.io.Serializable;
import java.util.List;

import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.exceptions.PortafirmasFacadeException;

import es.apt.ae.facade.dto.UsuarioItem;
import es.apt.ae.facade.entities.BackOffice;
import es.apt.ae.facade.entities.CatPropiedadesConfiguracion;
import es.apt.ae.facade.entities.portafirmas.Accion;
import es.apt.ae.facade.entities.portafirmas.EstadoDocumento;
import es.apt.ae.facade.entities.portafirmas.TipoCircuito;
import es.apt.ae.facade.entities.portafirmas.TipoGrupo;

public class CatalogosUtils implements Serializable {

	private static final long serialVersionUID = 220666176818719498L;
	private static List<Accion> listAccion = null;
	private static List<EstadoDocumento> listEstadodocumento = null;
	private static List<TipoCircuito> listTipoCircuito = null;
	private static List<TipoGrupo> listTipoGrupo = null;
	private static List<BackOffice> listBackOffices = null;
	private static List<CatPropiedadesConfiguracion> listPropiedadesConfiguracion = null;
	private static List<UsuarioItem> listUsuariosAE = null;
	
	public static List<Accion> getListAccion() throws PortafirmasFacadeException {
		chargeAccion();
		return listAccion;
	}
	public static void setListAccion(List<Accion> listAccion) {
		CatalogosUtils.listAccion = listAccion;
	}
	public static List<EstadoDocumento> getListEstadodocumento() throws PortafirmasFacadeException {
		chargeEstadoDocumento();
		return listEstadodocumento;
	}
	
	public static List<BackOffice> getListBackOffices() throws PortafirmasFacadeException {
		chargeBackoffice();
		return listBackOffices;
	}
	
	public static List<UsuarioItem> getListUsuariosAE() throws GenericFacadeException {
		chargeUsuariosAE();
		return listUsuariosAE;
	}
	
	public static void setListEstadodocumento(
			List<EstadoDocumento> listEstadodocumento) {
		CatalogosUtils.listEstadodocumento = listEstadodocumento;
	}
	public static List<TipoCircuito> getListTipoCircuito() throws PortafirmasFacadeException {
		chargeTipoCircuito();
		return listTipoCircuito;
	}
	public static void setListTipoCircuito(List<TipoCircuito> listTipoCircuito) {
		CatalogosUtils.listTipoCircuito = listTipoCircuito;
	}
	public static List<TipoGrupo> getListTipoGrupo() throws PortafirmasFacadeException {
		chargeTipoGrupo();
		return listTipoGrupo;
	}
	public static void setListTipoGrupo(List<TipoGrupo> listTipoGrupo) {
		CatalogosUtils.listTipoGrupo = listTipoGrupo;
	}
	
	public static void resetListPropiedadesConfiguracion() throws PortafirmasFacadeException{
		listPropiedadesConfiguracion = null;
	}
	
	public static Accion getAccionByCodigo(String codigo) throws PortafirmasFacadeException{
		chargeAccion();
		for (Accion accion : listAccion){
			if (accion.getCodigo().equals(codigo)){
				return accion;
			}
		}
		return null;
	}
	
	public static EstadoDocumento getEstadoDocumentoByCodigo(String codigo) throws PortafirmasFacadeException{
		chargeEstadoDocumento();
		for (EstadoDocumento element : listEstadodocumento){
			if (element.getCodigo() != null && element.getCodigo().equals(codigo)){
				return element;
			}
		}
		return null;
	}
	
	public static TipoCircuito getTipoCircuitoByCodigo(String codigo) throws PortafirmasFacadeException{
		chargeTipoCircuito();
		for (TipoCircuito element : listTipoCircuito){
			if (element.getCodigo().equals(codigo)){
				return element;
			}
		}
		return null;
	}
	
	public static TipoGrupo getTipoGrupoByCodigo(String codigo) throws PortafirmasFacadeException{
		chargeTipoGrupo();
		for (TipoGrupo element : listTipoGrupo){
			if (element.getCodigo().equals(codigo)){
				return element;
			}
		}
		return null;
	}
	
	protected static void chargeAccion() throws PortafirmasFacadeException{
		if(listAccion == null){
			listAccion = Services.getSrvPortafirmasFacadeRemote().getListAcciones();
		}
	}
	
	protected static void chargeEstadoDocumento() throws PortafirmasFacadeException{
		if(listEstadodocumento == null){
			listEstadodocumento = Services.getSrvPortafirmasFacadeRemote().getListEstadoDocumento();
		}
	}
	
	protected static void chargeTipoCircuito() throws PortafirmasFacadeException{
		if(listTipoCircuito == null){
			listTipoCircuito = Services.getSrvPortafirmasFacadeRemote().getListTipoCircuito();
		}
	}
	
	protected static void chargeTipoGrupo() throws PortafirmasFacadeException{
		if(listTipoGrupo == null){
			listTipoGrupo = Services.getSrvPortafirmasFacadeRemote().getListTipoGrupo();
		}
	}
	
	protected static void chargeBackoffice() throws PortafirmasFacadeException{
		if (listBackOffices == null){
			listBackOffices = Services.getSrvPortafirmasFacadeRemote().getAllBackoffice();
		}
	}
	
	protected static void chargePropiedadesConfiguracion() throws PortafirmasFacadeException{
		if (listPropiedadesConfiguracion == null){
			listPropiedadesConfiguracion = Services.getSrvPortafirmasFacadeRemote().getPropiedadesConfiguracion();
		}
	}
	
	public static CatPropiedadesConfiguracion getPropConfById(String id) throws PortafirmasFacadeException{
		chargePropiedadesConfiguracion();
		for (CatPropiedadesConfiguracion element : listPropiedadesConfiguracion){
			if (element.getId().equals(id)){
				return element;
			}
		}
		return null;
	}
	
	protected static void chargeUsuariosAE() throws GenericFacadeException{
		if (listUsuariosAE == null){
			// TODO
//			listUsuariosAE = Services.getSrvGenericFacadeRemote().consultarUsuariosDepartamentos(null, true);
		}
	}
	
	public static UsuarioItem getUsuarioByUsername(String username) throws GenericFacadeException {
		chargeUsuariosAE();
		for (UsuarioItem element : listUsuariosAE){
			if (element.getUsername().equals(username)){
				return element;
			}
		}
		return null;
	}

}
