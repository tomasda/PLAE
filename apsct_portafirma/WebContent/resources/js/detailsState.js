var observacion = '.observacion';
var VER_OBSERVACION = 'Ver observación';
var OCULTAR_OBSERVACION = 'Ocultar observación';


$(document).ready(function() {
	var observaciones = $(observacion);
	for (var i= 0 ; i < observaciones.length; i++) {
		if ($(observaciones[i]).text() === null || $(observaciones[i]).text().trim() === "") {
			$(observaciones[i]).siblings().hide();
		}
	}
});

function showObservacion(element) {
	$(element).siblings(observacion).slideToggle(function() {
		if ($(element).text() === VER_OBSERVACION) {
			$(element).text(OCULTAR_OBSERVACION);
		} else {
			$(element).text(VER_OBSERVACION);
		}
	});
}