package com.opencanarias.apsct.portafirmas.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.codec.binary.Base64;
import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.portafirmas.dto.RespuestaVisualizacionDocumento;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

/**
 * Servlet implementation class documentos
 */
@WebServlet("/verDocumento")
public class DescargaDocumentosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String URI = "uri";
	private static final String USUARIO = "usuario";
	
	private static final String APPLICATION_PDF = "application/pdf";
	private static final String APPLICATION_MS_WORD = "application/msword";
	private static final String APPLICATION_OO_WRITE = "application/vnd.oasis.opendocument.text";	

	protected static final Logger logger = Logger.getLogger(DescargaDocumentosServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DescargaDocumentosServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = (String) request.getParameter(URI);
		String usuarioSolicitante = (String) request.getParameter(USUARIO);
		String nombreDocumento = null;
		LoggerUtils.showInfo(logger, "Se va a descargar el documento con URI " + uri, FacadeBean.USUARIO_PORTAFIRMAS, usuarioSolicitante);

		try {
			RespuestaVisualizacionDocumento resultado = Services.getSrvPortafirmasFacadeRemote().obtenerVisualizacionDocumento(usuarioSolicitante, uri);

			String tipoDocumento;

			byte[] content = resultado.getContenido();

			response.reset();

			nombreDocumento = resultado.getNombre();
			tipoDocumento = getContentType(nombreDocumento);
			if (tipoDocumento == null) {
				tipoDocumento = getContentType(content);
			}
					
			response.setContentType(tipoDocumento);
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreDocumento + "\""); 

			OutputStream output = response.getOutputStream();
			output.write(content);
			output.close();
		} catch (Exception e) {
			LoggerUtils.showError(logger, "Error al recuperar el documento (URI: " + uri + ", NOMBRE: " + nombreDocumento + ")", e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioSolicitante);
		}

	}
	
	private String getContentType(String documentName) {
		String docContentType = null;
		
        String docExtension = getExtension(documentName);
        if (docExtension != null) {
        	if (docExtension.equalsIgnoreCase("odt")) {
        		docContentType = APPLICATION_OO_WRITE; 
        	} else if (docExtension.equalsIgnoreCase("doc") || docExtension.equalsIgnoreCase("docx")) {
        		docContentType = APPLICATION_MS_WORD;             		
        	} else if (docExtension.equalsIgnoreCase("pdf")) {
        		docContentType = APPLICATION_PDF;
        	} 
        }	
        
        return docContentType;
	}
	
	private String getContentType(byte[] data) {
		try {
			MagicMatch match = Magic.getMagicMatch(data);
	        return match.getMimeType();
		} catch (Exception e) {
			return null;
		}
	}
	
	private String getExtension(String documentName) {
        int indexExt = documentName.lastIndexOf(".");
        if (indexExt != -1) {
            return documentName.substring(indexExt + 1, documentName.length());
        }
        return null;
	}	

}
