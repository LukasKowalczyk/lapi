package de.lapi;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXB;

import de.lapi.comparator.TextElementComparator;
import de.lapi.container.LanguageText;
import de.lapi.container.TextElement;

public class Lapi {
	private static Map<String, List<TextElement>> textLanguages = new HashMap<String, List<TextElement>>();

	private static Lapi lapi = null;

	private Lapi() {

	}

	public static Lapi getInstance() {
		if (lapi == null) {
			lapi = new Lapi();
		}
		return lapi;
	}

	public void generateLanguageText(Locale local) {
		LanguageText lt = JAXB.unmarshal(new File(local.getISO3Language()
				+ ".xml"), LanguageText.class);
		textLanguages.put(local.getISO3Language(), lt.getTextElements());
	}

	public String getText(Locale local, String id) {
		List<TextElement> out = textLanguages.get(local.getISO3Language());
		int erg = Collections.binarySearch(out, new TextElement(id, ""),
				new TextElementComparator());
		return out.get(erg).getText();
	}

}
