package de.lapi;

import java.io.File;
import java.io.IOException;
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
		try {
			File file = new File(local.getISO3Language() + ".xml");
			if (!file.exists()) {
				file.createNewFile();
			}
			LanguageText lt = JAXB.unmarshal(file, LanguageText.class);
			textLanguages.put(local.getISO3Language(), lt.getTextElements());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getText(Locale local, String id) {
		List<TextElement> out = textLanguages.get(local.getISO3Language());
		int erg = Collections.binarySearch(out, new TextElement(id, ""),
				new TextElementComparator());
		return out.get(erg).getText();
	}

	public String getTextByDefaultLanguage(String id) {
		List<TextElement> out = textLanguages.get(Locale.getDefault()
				.getISO3Language());
		int erg = Collections.binarySearch(out, new TextElement(id, ""),
				new TextElementComparator());
		return out.get(erg).getText();
	}

	public String generateKey(Class<?> mainClass, Object o) {

		String out = mainClass.getName() + "." + o.getClass().getName();
		return out;
	}

	public String generateKey(Class<?> mainClass, Class<?> objectClass, String extension) {
		String out = mainClass.getName() + "." + objectClass.getName()+"." + extension;
		return out;
	}

	public boolean isLanguageTextGenerated(Locale local) {
		return textLanguages.containsKey(local.getISO3Language());
	}

	public void reinitLanguageText(Locale local) {
		textLanguages.remove(local.getISO3Language());
		generateLanguageText(local);
	}
}
