var HDN_BTN_CHK = 'hdnBtnChk',
	LABEL = 'label',
	INPUT = 'input',
	SWITCH = '.switch',
	CHECKED = 'checked',
	SELECTED ='selected',
	UNSELECTED = 'unselected',
	HIDEN_CHECK = 'hidenCheck',
	PF_HIDEN_CHECK,
	PF_HDN_BTN_CHK;

$(document).ready(function(){
	PF_HIDEN_CHECK = PF(HIDEN_CHECK).jqId,
	PF_HDN_BTN_CHK = PF(HDN_BTN_CHK).jqId;
	
	$(SWITCH).click(function(event){
		
		switcherElement(this);	
	});
	
	if($('input[type="checkbox"]').attr(CHECKED) === CHECKED){
		$('input[type="checkbox"]').parent().parent().parent().parent().removeClass(UNSELECTED).addClass(SELECTED);
	}
	
});

function switcherElement(element){ 
	$(element).find(PF_HIDEN_CHECK).find(INPUT).prop(CHECKED,!$(element).find(PF_HIDEN_CHECK).find(INPUT).prop(CHECKED));
	
	if ($(element).hasClass(SELECTED)){
		console.log("Esta selecionado");
		$(element).removeClass(SELECTED).addClass(UNSELECTED);
		console.log("Se selecciona");
	} else {
		console.log("No está selecionado");
		//$(element).removeClass(UNSELECTED).addClass(SELECTED);
		//Se seleccionará al volver del servidor
		console.log("Se deselecciona");
	}
}