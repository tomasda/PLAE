var _SIGN_WEB_CLIENT_DIALOG = 'signWebClientDialog';
var _STATUS_DIALOG = 'statusDialog';
var _MESSAGE_DIALOG = "messageDialog";

var _INFOSIGN_WEB_CLIENT = '.infoSignWebClient';
var _SUBMITSIGN_WEB_CLIENT = '.submitSignWebClient';
var _INFOSIGNSAVE_WEB_CLIENT = '.infoSignSaveWebClient';

var _classFieldSignUris = '.fieldSignUris';
var _classFieldSignFormat = '.fieldSignFormat';
var _classFieldSignUser = '.fieldSignUser';
var _classFieldSignValidationStatus = '.fieldSignValidationStatus';
var _classFieldSignResultCode = '.fieldSignResultCode';
var _classFieldSignResultDocsOk = '.fieldSignResultDocsOk';

var _messageInfo = "messageInfo";

var _formClienteWeb = "firmaClienteWebForm";


var FirmaClienteWeb = {

	codigo : null,
	descripcion : null,
	docsOk : null,
	
	conf:{
		target	: "FirmaClienteWeb",
		width 	: screen.width,
		height	: screen.height,
		top: 0,
		left: 0,
		fullscreen: 'yes',
		toolbar	: 0 
	},
	
	service : null,
	activeService : null,
	
	isServiceClose : function(){
		if(FirmaClienteWeb.service.closed){
			PF(_STATUS_DIALOG).hide();
			alert("El cliente de firma se cerró inesperadamente")
			FirmaClienteWeb.end();
		}
	},

	end: function(){
		clearInterval(this.activeService);
		try{
			FirmaClienteWeb.service.close();
		} catch (err){
			console.log("No se ha podido cerrar la ventana");
		}
	},
	
	submitForm : function() {
		FirmaClienteWeb.service = window.open("", FirmaClienteWeb.conf.target, "width="+FirmaClienteWeb.conf.width
				+",height="+FirmaClienteWeb.conf.height + +",top="+FirmaClienteWeb.conf.top + +",left="+FirmaClienteWeb.conf.left
				+",fullscreen="+FirmaClienteWeb.conf.fullscreen+",toolbar="+FirmaClienteWeb.conf.toolbar);
		$("#" + _formClienteWeb).attr('target', FirmaClienteWeb.conf.target).submit();
		FirmaClienteWeb.activeService = setInterval(FirmaClienteWeb.isServiceClose,1000);
	},
	
	successCallback : function() {
		$(_classFieldSignResultCode).val(FirmaClienteWeb.codigo);
		$(_classFieldSignResultDocsOk).val(FirmaClienteWeb.docsOk);
		$(_INFOSIGN_WEB_CLIENT).hide();
		$(_INFOSIGNSAVE_WEB_CLIENT).show();
		$(_SUBMITSIGN_WEB_CLIENT).click();
		FirmaClienteWeb.end();
	},

	errorCallback : function() {
		$(_classFieldSignResultCode).val(FirmaClienteWeb.codigo);
		$(_classFieldSignResultDocsOk).val(FirmaClienteWeb.docsOk);
		$(_INFOSIGN_WEB_CLIENT).hide();
		$(_INFOSIGNSAVE_WEB_CLIENT).show();
		$(_SUBMITSIGN_WEB_CLIENT).click();
		FirmaClienteWeb.end();
	}
}

function onClickFirmarClienteWeb() {
	$(_INFOSIGN_WEB_CLIENT).show();
	$(_SUBMITSIGN_WEB_CLIENT).hide();
	$(_INFOSIGNSAVE_WEB_CLIENT).hide();
	PF(_SIGN_WEB_CLIENT_DIALOG).hide();
	
	var validationStatus = $(_classFieldSignValidationStatus).val();
	
	if (validationStatus === 'success' || validationStatus === 'warn') {
		var uris = $(_classFieldSignUris).val();
		if (uris !== '') {
			if (validationStatus === 'warn') {
				PF(_SIGN_WEB_CLIENT_DIALOG).show();
			} else {
				$("#" + _formClienteWeb + " #uris").val(uris);
				$("#" + _formClienteWeb + " #formatoFirma").val($(_classFieldSignFormat).val());
				$("#" + _formClienteWeb + " #usuarioSolicitante").val($(_classFieldSignUser).val());
				FirmaClienteWeb.submitForm();
			}
		} else {
			alert('No se ha podido extraer la información de los documentos seleccionados necesaria para iniciar el proceso de firma.');
		}
	}
}

function confirmFirmarClienteWeb() {
	$("#" + _formClienteWeb + " #uris").val($(_classFieldSignUris).val());
	$("#" + _formClienteWeb + " #formatoFirma").val($(_classFieldSignFormat).val());
	$("#" + _formClienteWeb + " #usuarioSolicitante").val($(_classFieldSignUser).val());
	FirmaClienteWeb.submitForm();
}