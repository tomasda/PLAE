package es.apt.ae.facade.dto;

import java.io.Serializable;

public class HtmlTextItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String label = null;
	private String title = null;
	private String style = null;	
	private String styleClass = null;
	
	public HtmlTextItem() {
		super();
	}
	
	public HtmlTextItem(String label, String title, String style,
			String styleClass) {
		super();
		this.label = label;
		this.title = title;
		this.style = style;
		this.styleClass = styleClass;
	}
	
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
