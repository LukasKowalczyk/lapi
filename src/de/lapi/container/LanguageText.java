package de.lapi.container;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "texts")
public class LanguageText {
	private List<TextElement> textElements= new ArrayList<TextElement>();

	public LanguageText() {
		super();
	}

	@XmlElement(name = "textElement")
	public List<TextElement> getTextElements() {
		return textElements;
	}

	public void setTextElements(List<TextElement> textElements) {
		this.textElements = textElements;
	}

	public void add(TextElement textElement) {
		textElements.add(textElement);
	}

}
