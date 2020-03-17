package com.opencanarias.ejb.gestor.documental;

import java.util.List;

import com.opencanarias.exceptions.GestorDocumentalFacadeException;

import es.apt.ae.facade.repository.dto.DocumentoRepositorioItem;
import es.apt.ae.facade.ws.params.gestor.documental.in.ParametrosSGD;
import es.apt.ae.facade.ws.params.gestor.documental.out.Resultado;

public interface IGestorDocumentalFacade {

	/* Metodos que se publicaran por Web Services */
	public String getVersion() throws GestorDocumentalFacadeException;
	public String crearNodo(String parametros) throws GestorDocumentalFacadeException;
	public String crearNodoAltoVolumen(ParametrosSGD parametrosSGD) throws GestorDocumentalFacadeException;
	public String eliminarNodo(String parametros) throws GestorDocumentalFacadeException;
	public String copiarNodo(String parametros) throws GestorDocumentalFacadeException;
	public String moverNodo(String parametros) throws GestorDocumentalFacadeException;
	public String actualizarNodo(String parametros) throws GestorDocumentalFacadeException;
	public String actualizarNodoAltoVolumen(ParametrosSGD params) throws GestorDocumentalFacadeException;
	public String consultarNodo(String parametros) throws GestorDocumentalFacadeException;
	public Resultado consultarNodoAltoVolumen(ParametrosSGD params);
	public String obtenerVisualizacionDocumento(String parametros) throws GestorDocumentalFacadeException;
	public Resultado obtenerVisualizacionDocumentoAltoVolumen(String parametros) throws GestorDocumentalFacadeException;
	
	/* Metodos que seran accesibles tan solo por EJB */
	public boolean comprobarExisteNodo(String ruta) throws GestorDocumentalFacadeException;
	public List<DocumentoRepositorioItem> consultarNodosInfo(String usuario, String password, List<DocumentoRepositorioItem> documentos) 
			throws GestorDocumentalFacadeException;
	public Resultado obtenerVisualizacionDocumentoEJB(ParametrosSGD parametrosSGD, boolean altoVolumen) throws GestorDocumentalFacadeException;
	public Resultado crearNodoEJB(ParametrosSGD parametrosSGD) throws GestorDocumentalFacadeException;
	public Resultado eliminarNodoEJB(ParametrosSGD parametrosSGD) throws GestorDocumentalFacadeException;

}
