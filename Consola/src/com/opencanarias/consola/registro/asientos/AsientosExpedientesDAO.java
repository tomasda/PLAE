package com.opencanarias.consola.registro.asientos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;
import com.opencanarias.consola.utilidades.FacadeMarshallUnMarshallUtils;
import com.opencanarias.consola.utilidades.FacadeServices;

import es.apt.ae.facade.ws.params.commons.in.Credenciales;
import es.apt.ae.facade.ws.params.commons.in.Documento;
import es.apt.ae.facade.ws.params.commons.in.ListaDocumentos;
import es.apt.ae.facade.ws.params.expedientes.in.baja.DatosEliminacionDocumentos;
import es.apt.ae.facade.ws.params.expedientes.in.baja.EliminacionDocumentos;
import es.apt.ae.facade.ws.params.expedientes.out.Resultado;
import es.apt.ae.facade.ws.params.expedientes.out.baja.ResultadoEliminacionDocumentos;

public class AsientosExpedientesDAO {

	private static Logger logger = Logger.getLogger(AsientosDAO.class);



	public boolean deleteAsientoExpedienteRelationship(AsientosBean asiento, String opcion){
		//Sí type = 0, sólo se elimina la relación, sí es 1 se elimina la relación y los documentos.
		boolean bol = false;
		boolean ue = false;
		boolean ud = true;
		/*
		 * 1º se eliminan los documentos.
		 */
		if (opcion.equals("1")) {
			ud = updateDocumentos(asiento.getIDENTIFICACION(), asiento.getEXPEDIENTE());
		}
		/*
		 * 2º dejo el campo Identificación vacío, para que al actualizar el expediente borre este dato.
		 */
		if (ud) {
			ue = updateExpediente(null, asiento.getEXPEDIENTE());
		}
		if (ue && ud) {
			bol = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La relación se ha eliminado correctamente.",""));
		}
		return bol;
	}


	public boolean updateDocumentos(String recordNumber, String numExpediente) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		boolean bol = false;
		try {
			con = DataBaseUtils.getConnection("OC3F"); 
			// Busco los documentos asociados al asiento
			sql = "select * from document_ where record_number_= ? and administrative_file_id_= ? ";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, recordNumber);
			ps.setString(2, numExpediente);
			rs = ps.executeQuery();

			// Obtengo la lista de uris.
			List<String> listaURIS = new ArrayList<String>();
			while (rs.next()) {
				int var = 0;
				int check = 0;
				if (listaURIS.size() == 0) {
					listaURIS.add(rs.getString("REPOSITORY_URI_"));
				}
				while (listaURIS.size() > var) {
					if (rs.getString("REPOSITORY_URI_").equals(listaURIS.get(var))) {
						check = 1;
					}
					var++;
				}
				if (check == 0) {
					listaURIS.add(rs.getString("REPOSITORY_URI_"));
				}
			}
			sql = "update DOCUMENT_ set  TYPE_ = 'attachment', RECORD_NUMBER_ = '' where record_number_= ? and administrative_file_id_= ?";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, recordNumber);
			ps.setString(2, numExpediente);
			rs = ps.executeQuery();
			
			// Llamada al servicio web de la Fachada para eliminar Documentos de Expediente.
			String peticion = invocarEliminarDocsExpediente(listaURIS, "usuGESTEXP", numExpediente);
			if (null != peticion) {
				String resultadoXml = FacadeServices.srv_Expedientes_FWS().eliminarDocumentoExpediente(peticion);
				if (resultadoXml != null) {
					Resultado resultado = (new FacadeMarshallUnMarshallUtils<es.apt.ae.facade.ws.params.expedientes.out.Resultado>(
							FacadeMarshallUnMarshallUtils.URI_EXPEDIENTES_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_EXPEDIENTES_OUT_PACKAGE)).unmarshall_OUT(resultadoXml);
					if (null != resultado) {
						ResultadoEliminacionDocumentos red = resultado.getResultadoEliminacionDocumentos();
						if (red.getRespuesta().getCodigo().equals("FWS-EXP-0000")) {
							bol = true;
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Documentos Eliminados correctamente", ""));
						}else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error durante la eliminación de documentos.", red.getRespuesta().getDescripcion()));
						}
					}else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error durante la eliminación de documentos.", " !!!"));
					}
				}else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error durante la eliminación de documentos.", " !!!"));
				}

			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error durante la eliminación de documentos.", " !!!"));
			}

			rs.close();
			ps.close();
		}catch (Exception e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}

		return bol;

	}

	public boolean updateExpediente(String recordNumber, String numExpediente) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql;
		boolean bol = false;
		try {
			sql = "update administrative_file_ set record_number_ = ? where id_ = ?";
			con = DataBaseUtils.getConnection("OC3F");
			ps = con.prepareStatement(sql);
			ps.setString(1, recordNumber);
			ps.setString(2, numExpediente);
			ps.executeQuery();
			bol = true;
			ps.close();
		}catch (Exception e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return bol;
	}


	public String invocarEliminarDocsExpediente(List<String> documentsList,String usuarioSolicitante, String numExpediente) throws Exception {

		es.apt.ae.facade.ws.params.expedientes.in.Parametros parametros = new es.apt.ae.facade.ws.params.expedientes.in.Parametros();
		String peticion = null;
		EliminacionDocumentos ed = new EliminacionDocumentos();
		DatosEliminacionDocumentos datos = new DatosEliminacionDocumentos();

		// Credenciales
		String userName = "usuGESTEXP";
		String pass = "usuGESTEXP";
		datos.setUsuarioSolicitante(usuarioSolicitante);	
		datos.setNumExpediente(numExpediente);

		Credenciales credenciales = new Credenciales();
		credenciales.setUsuario(userName);
		credenciales.setPassword(pass);
		ed.setCredenciales(credenciales);
		// Documentos
		ListaDocumentos listaDocs = new ListaDocumentos();
		for (String uri : documentsList) {
			Documento docVisualizar = new Documento();
			docVisualizar.setUri(uri);
			listaDocs.getDocumento().add(docVisualizar);
		}
		datos.setListaDocumentos(listaDocs);
		ed.setDatosEliminacionDocumentos(datos);
		parametros.setParametrosEliminacionDocumentos(ed);

		peticion = (new FacadeMarshallUnMarshallUtils<es.apt.ae.facade.ws.params.expedientes.in.Parametros>(
				FacadeMarshallUnMarshallUtils.URI_EXPEDIENTES_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_EXPEDIENTES_OUT_PACKAGE)).marshall_IN(parametros);
		return peticion;
	}


}
