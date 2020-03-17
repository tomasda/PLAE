package es.apt.ae.facade.dto;

import java.io.Serializable;

public class TipoDocumentoItem extends ElementoItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public TipoDocumentoItem() {
		super();
	}
	public TipoDocumentoItem(String id, String descripcion) {
		super(id, descripcion);
	}

}
