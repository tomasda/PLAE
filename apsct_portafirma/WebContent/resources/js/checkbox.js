var lasSelected;

function clickCheckBox(event){
	var icon = $(event).find('i');
	lastSelected = icon;
	if (icon.hasClass('fa-circle-thin')){
		icon.removeClass('fa-circle-thin');
		icon.addClass('fa-check-circle-o');
	} else {
		icon.removeClass('fa-check-circle-o');
		icon.addClass('fa-circle-thin');
	}
};

function deseleccionar(event){
	if($(PF('messageDialog').jqId).prop('style')["visibility"] == 'visible'){
		lastSelected.removeClass('fa-check-circle-o');
		lastSelected.addClass('fa-circle-thin');
	}
}
