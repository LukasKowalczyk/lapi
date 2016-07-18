package de.lapi.comparator;

import java.util.Comparator;

import de.lapi.container.TextElement;

public class TextElementComparator implements Comparator<TextElement> {

	@Override
	public int compare(TextElement e1, TextElement e2) {
		return e1.getId().compareTo(e2.getId());
	}

}
