package es.opencanarias.security.jjwt.api.restful;

import java.io.Serializable;

public class ResultadoValidarAccesoApp implements Serializable {
	private static final long serialVersionUID = 4768875108779017126L;
	private Boolean resultado;

	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}

}
