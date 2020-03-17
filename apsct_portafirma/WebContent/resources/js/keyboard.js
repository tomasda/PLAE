var DIALOG = 'dialogCancel', ACTIVE_KEYBOARD = 'activeKeyboard', UNACTIVE_KEYBOARD = 'unActiveKeyboard';;

function addKeyboard() {
	if ($(window).width() < 600) {
		$('.'+DIALOG).removeClass(UNACTIVE_KEYBOARD).addClass(ACTIVE_KEYBOARD,{duration:500});
		//.ui-overlay-visible
	}
}

function removeKeyboard() {
	if ($(window).width() < 600) {
		$('.'+DIALOG).removeClass(ACTIVE_KEYBOARD).addClass(UNACTIVE_KEYBOARD,{duration:500});
	}
}

function checkMaxLength(fieldId, counterId, maxLength){
	
	
	if($("[id$='" + fieldId + "']").val().length<=maxLength){
		$("[id$='" + counterId + "']").text($("[id$='" + fieldId + "']").val().length);
	}else{
		$("[id$='" + fieldId + "']").val($("[id$='" + fieldId + "']").val().substring(0,maxLength));
		$("[id$='" + counterId + "']").text(maxLength);
		alert("No est\u00e1 permitido introducir m\u00e1s de " + maxLength + " caracteres.");
	}
}
