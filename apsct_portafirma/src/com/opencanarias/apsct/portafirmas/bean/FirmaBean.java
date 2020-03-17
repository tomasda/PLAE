package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.util.Base64;
import org.jboss.logging.Logger;

import com.opencanarias.apsct.plae.dto.Document;
import com.opencanarias.apsct.plae.exceptions.ClassToXMLConverterException;
import com.opencanarias.apsct.plae.exceptions.SignTypeException;
import com.opencanarias.apsct.plae.utils.SignbatchUtils;
import com.opencanarias.apsct.portafirmas.exception.PortafirmasWebException;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
//import com.opencanarias.apsct.portafirmas.utils.SignbatchUtils;
import com.opencanarias.apsct.portafirmas.utils.SignBatchConfigCreatorUtil;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.dto.Resultado;

@ManagedBean
@SessionScoped
public class FirmaBean implements Serializable {

	private static final long serialVersionUID = -3093777731484081324L;
	private static Logger logger = Logger.getLogger(FirmaBean.class);
	// Firma directa con miniapplet
	private String contentSign;
	private Boolean signBatch;
	private String params;
	private String signResult;
	private String signType;
	private String downloadUri;
	
	// Firma a traves del cliente de firma web
	private String signUris;
	private String signFormat;
	private String signValidationStatus;	
	private String signResultCode;
	private String signResultDocsOk;
	
	public FirmaBean() {
		try {
			DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
			if (null != documentosBean && null != documentosBean.getDocumentosFirma() && !documentosBean.getDocumentosFirma().isEmpty()) {
				// TODO: Tomar el valor del usuario o de la configuracion general de la aplicacion
				init(documentosBean.getDocumentosFirma(), Resultado.SUCCESS, documentosBean.isClienteFirmaWeb());
			}
		} catch (PortafirmasWebException e) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			LoggerUtils.showError(logger, "Error al construir " + FirmaBean.class, e, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
	}

	public FirmaBean(String validationStatus, boolean useSignWebClient) {
		try {
			DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
			signValidationStatus = validationStatus;
			if (null != documentosBean && null != documentosBean.getDocumentosFirma() && !documentosBean.getDocumentosFirma().isEmpty()) {
				init(documentosBean.getDocumentosFirma(), validationStatus, useSignWebClient);
			}
		} catch (PortafirmasWebException e) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			LoggerUtils.showError(logger, "Error al construir " + FirmaBean.class, e, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
	}

	public void init(List<Document> documentList, String validationStatus, boolean useSignWebClient) throws PortafirmasWebException {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		try {
			
			signValidationStatus = validationStatus;
			if (null != documentList && !documentList.isEmpty()) {
				if (useSignWebClient) {
					signUris = "";
					signFormat = null;
					if (documentList != null && !documentList.isEmpty()) {
						for (Document docFirma:documentList) {
							signUris += docFirma.getUri() + ",";
							if (null == signFormat) {
								signFormat = docFirma.getSignType();
							}
						}
						signUris = signUris.substring(0, signUris.length()-1);
					}
					signResultCode = null;
					signResultDocsOk = null;
				} else {
					SignbatchUtils signbatchUtils = new SignbatchUtils(SignBatchConfigCreatorUtil.getConfig(), SignBatchConfigCreatorUtil.getSigner());
					
					if (null != documentList && documentList.size() > 1) {
						signBatch = true;
						contentSign = signbatchUtils.createXMLSignBatch(documentList);
						byte[] contentSignB64 = Base64.getEncoder().encode(contentSign.getBytes());
						contentSign = new String(contentSignB64);
						params = null;
						signType = null;
						signResult = null;
					} else if (null != documentList && !documentList.isEmpty()) {
						signBatch = false;
						Document document = documentList.get(0);
						String type = document.getSignType().toUpperCase();
						int signNumber = document.getSignaturesNumber();
						params = signbatchUtils.getParams(type, signNumber);
						if ("PADES".equalsIgnoreCase(type)){
							this.setDownloadUri(documentList.get(0).getUri());
							contentSign = Base64.getEncoder().encodeToString(document.getContent().getBytes());
						} else {
							contentSign = document.getContent();
						}
						signType = document.getSignType();
						signResult = null;
					}
				}
			} else {
				LoggerUtils.showInfo(logger, "Lista de documentos para firma vacia, no se puede inicializar la clase " + FirmaBean.class, 
						FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				throw new PortafirmasWebException(Constantes.ERROR_BUILD_SIGNBATCH);
			}
		} catch (SignTypeException e) {
			LoggerUtils.showError(logger, "El tipo indicado para la firma es erroneo, no se ha podido construir " + FirmaBean.class, e, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			throw new PortafirmasWebException(e, Constantes.ERROR_BUILD_SIGNBATCH);
		} catch (ClassToXMLConverterException e) {
			LoggerUtils.showError(logger, "No se ha podido convertir el lote de firmas a XML", e, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			throw new PortafirmasWebException(e, Constantes.ERROR_BUILD_SIGNBATCH);
		}
	}
	
	public void reset(boolean useSignWebClient) {
		if (useSignWebClient) {
			signUris = null;
			signResultCode = null;
			signResultDocsOk = null;
		} else {
			contentSign = null;
			signBatch = null;
			params = null;
			signResult = null;
			signType = null;
			downloadUri = null;
		}
	}


	public String getContentSign() {
		return contentSign;
	}

	public void setContentSign(String contentSign) {
		this.contentSign = contentSign;
	}

	public Boolean getSignBatch() {
		return signBatch;
	}

	public void setSignBatch(Boolean signBatch) {
		this.signBatch = signBatch;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getSignResult() {
		return signResult;
	}

	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getDownloadUri() {
		return downloadUri;
	}

	public void setDownloadUri(String downloadUri) {
		this.downloadUri = downloadUri;
	}

	public String getSignUris() {
		return signUris;
	}

	public void setSignUris(String signUris) {
		this.signUris = signUris;
	}
	
	public String getSignFormat() {
		return signFormat;
	}

	public void setSignFormat(String signFormat) {
		this.signFormat = signFormat;
	}

	public String getSignResultCode() {
		return signResultCode;
	}

	public void setSignResultCode(String signResultCode) {
		this.signResultCode = signResultCode;
	}

	public String getSignResultDocsOk() {
		return signResultDocsOk;
	}

	public void setSignResultDocsOk(String signResultDocsOk) {
		this.signResultDocsOk = signResultDocsOk;
	}

	public String getSignValidationStatus() {
		return signValidationStatus;
	}

	public void setSignValidationStatus(String signValidationStatus) {
		this.signValidationStatus = signValidationStatus;
	}
	
}
