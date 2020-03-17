var _FIELD_CONTENIDO = '.fieldContenido';
var _FIELD_RESULTADO = '.fieldResultado';
var _FIELD_TIPO_FIRMA = '.fieldTipoFirma';
var _INFOSIGN = '.infoSign';
var _SUBMITSIGN = '.submitSign';
var _INFOSIGNSAVE = '.infoSignSave';
var _SIGNDIALOG = 'signDialog';
var _NO_DOCS_SELECTED_DIALOG = 'noDocsSelectedDialog';
var _SIGN_PROCESS = 'signProcess';
var _SIGN_PROCESS_CLASS = '.signProcess';
var _FIELDNUMFIRMAS = '.fieldNumFirmas';
var _FIELDFIRMANTE = '.fieldFirmante';
var rutaMiniapplet = '/apsct_portafirmas/resources/firma/miniapplet';
var documentoActual;

var _classFieldContentSign = '.fieldContentSign';
var _classFieldSignBatch = '.fieldSignBatch';
var _classFieldSignParams = '.fieldSignParams';
var _classFieldSignType = '.fieldSignType';
var _classFieldSignResult = '.fieldSignResult';


/**
 * Obtencion de los parametros comunes de firma
 */

var SignData = {
	
	content : null,
	isSignBatch : false,
	params : null,
	signType : null,
	signResult : null,
	serverName : null,
	
	init : function(){
		this.content = $(_classFieldContentSign).val();
		this.isSignBatch = ($(_classFieldSignBatch).val() === "true");
		this.params = $(_classFieldSignParams).val();
		this.signType = $(_classFieldSignType).val();
		this.serverName = window.location.protocol +'//' + window.location.host;
	},
	
	sign: function(){
		SignData.init();
		MiniApplet.setStickySignatory(true);
		var data = SignData.content;
		var type = SignData.signType;
		var params = SignData.params;
		if (SignData.isSignBatch){
			var batchPreSignerUrl = this.serverName + '/TriPhaseSignerServer/BatchPresigner';
			var batchPostSignerUrl = this.serverName + '/TriPhaseSignerServer/BatchPostsigner';
			params = '';
			MiniApplet.signBatch(data, batchPreSignerUrl, batchPostSignerUrl, params, this.successCallback, this.errorCallback);
		} else {
			if(type.toUpperCase() === "PADES"){
				//autodescarga del documento.
				$(".predownload-document").trigger("click");
				alert("A continuación, va a firmar un documento PDF, bajo el formato de firma PAdES, se acaba de descargar el documento a firmar en su carpeta de DESCARGAS, " +
						"asegurese de seleccionarlo correctamente para su firma");
				data = null;
			}
			MiniApplet.sign(data, "SHA1withRSA", type, params, this.successCallback, this.errorCallback);
		}
		PF(_SIGNDIALOG).hide();
	},
	
	successCallback : function(signatureB64){
		$(_classFieldSignResult).val(signatureB64);
		$(_INFOSIGN).hide();
		$(_INFOSIGNSAVE).show();
		$(_SUBMITSIGN).click();

	},
	errorCallback : function(errorType, errorMessage){
		if (errorType === 'java.util.concurrent.TimeoutException') {
			alert('Error, tiempo del proceso de firma superado.\nPor favor, verifique que usted tiene instalada la aplicación Autofirma.\n(Véase ayuda)');
		} else if (errorType === 'es.gob.afirma.signers.pades.InvalidPdfException') {
			alert('Error, no se puede realizar la firma del documento en el formato PAdES');
		} else if (errorType === 'es.gob.afirma.standalone.ApplicationNotFoundException') {
			alert('Error. Por favor, verifique que usted tiene instalada la aplicación Autofirma. (Véase ayuda)');
		} else if(errorType === 'es.gob.afirma.core.AOCancelledOperationException'){
			alert('Error. La firma ha sido cancelada por el usuario');
		} else {
			alert(errorType);
		}
		$(_INFOSIGN).hide();
		PF(_SIGNDIALOG).hide();
		PF(_SIGN_PROCESS).hide();
	}
}

/**
 * Visualizacion del mensaje donde se muestra el proceso de firma
 */
function showSignProcess() {
	PF(_SIGN_PROCESS).show();
	$(_SIGN_PROCESS_CLASS).css('left',(screen.width / 2) - ($(_SIGN_PROCESS_CLASS).width() / 2));
}

function showMensajesFirma() {
	if ($(PF('messageDialog').jqId).css('visibility') !== 'visible'){
		onClickFirmar();
		PF(_SIGNDIALOG).show();
	}		
}

function onClickFirmar() {
	// Llamada al miniapplet
	MiniApplet.setLocale("es_ES");
	MiniApplet.cargarMiniApplet(window.location.protocol +'//' + window.location.host + rutaMiniapplet);
	// Para que funcione correctamente en movil/AutoFirma se debe de establecer el servlet de envio y el de recepcion:
	MiniApplet.setServlets(window.location.protocol +'//' + window.location.host+ "/SignatureStorageServer/StorageService", 
			window.location.protocol +'//'+ window.location.host+ "/SignatureRetrieverServer/RetrieveService");
	$(_INFOSIGN).show();
	$(_SUBMITSIGN).hide();
	$(_INFOSIGNSAVE).hide();
}
