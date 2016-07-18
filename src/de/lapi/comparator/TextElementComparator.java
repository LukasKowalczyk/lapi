package de.lapi.comparator;

import java.util.Comparator;

import de.lapi.container.TextElement;

public class TextElementComparator implements Comparator<Object> {

	public int compare(Object arg0, Object arg1) {
		TextElement e1=(TextElement) arg0;
		TextElement e2=(TextElement) arg1;
		return e1.getId().compareTo(e2.getId());
	}

}
