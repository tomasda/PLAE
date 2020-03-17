package es.apt.ae.facade.portafirmas.dto;
import java.util.HashMap;

import javax.ejb.EJB;

import com.opencanarias.ejb.portafirmas.dao.IPortafirmasFacadeDAO;

import es.apt.ae.facade.entities.portafirmas.Accion;
import es.apt.ae.facade.entities.portafirmas.EstadoDocumento;
import es.apt.ae.facade.entities.portafirmas.TipoCircuito;
import es.apt.ae.facade.entities.portafirmas.TipoGrupo;

public class TMUtils {

	@EJB
	private static IPortafirmasFacadeDAO portafirmasFacadeDAO;
	
	//TODO:no terminado
	private HashMap <String,EstadoDocumento> listEstadoDocumento = new HashMap<String,EstadoDocumento>();
	private HashMap <String,Accion> listAccion = new HashMap<String,Accion>();
	private HashMap <String,TipoCircuito> listTipoCircuito = new HashMap<String,TipoCircuito>();
	private HashMap <String,TipoGrupo> listTipoGrupo = new HashMap<String,TipoGrupo>();
	
	public HashMap<String, EstadoDocumento> getListEstadoDocumento() {
		return listEstadoDocumento;
	}
	public void setListEstadoDocumento(
			HashMap<String, EstadoDocumento> listEstadoDocumento) {
		this.listEstadoDocumento = listEstadoDocumento;
	}
	public HashMap<String, Accion> getListAccion() {
		return listAccion;
	}
	public void setListAccion(HashMap<String, Accion> listAccion) {
		this.listAccion = listAccion;
	}
	public HashMap<String, TipoCircuito> getListTipoCircuito() {
		return listTipoCircuito;
	}
	public void setListTipoCircuito(HashMap<String, TipoCircuito> listTipoCircuito) {
		this.listTipoCircuito = listTipoCircuito;
	}
	public HashMap<String, TipoGrupo> getListTipoGrupo() {
		return listTipoGrupo;
	}
	public void setListTipoGrupo(HashMap<String, TipoGrupo> listTipoGrupo) {
		this.listTipoGrupo = listTipoGrupo;
	}
	
	public EstadoDocumento getEstadoDocumento(String clave){
		
		EstadoDocumento result = listEstadoDocumento.get(clave);
		if (result == null){
			result = portafirmasFacadeDAO.getEstadoDocumentobyCod(clave);
		}
		listEstadoDocumento.put(clave, result);
		
		return result;
	}
	
	
}
