function updateGroupType(){
	var selectOneTipoGrupo = $('.selectOneTipoGrupo');

	for (var i = 0 ;  i < $(selectOneTipoGrupo).length ; i++){
		PF('tipoGrupo'+i).selectValue($(PF('tipoGrupo'+i).jqId).siblings('.hiddenTipo').text())
	}
}