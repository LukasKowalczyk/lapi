package de.lapi.container;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TextElement {
	private String id;
	private String text;

	public TextElement(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public TextElement() {
		super();
		id = "";
		text = "";
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}
}
