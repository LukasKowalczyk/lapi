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
	private Map<String, List<TextElement>> textLanguages = new HashMap<String, List<TextElement>>();

	private static String quellOrdner = null;

	private static Lapi lapi = null;

	private Lapi() {
		generateDefaultLanguageText();
	}

	public static Lapi getInstance() {
		if (lapi == null) {
			lapi = new Lapi();
		}
		return lapi;
	}

	public static Lapi getInstance(String quellOrdner) {
		Lapi.quellOrdner = quellOrdner;
		if (lapi == null) {
			lapi = new Lapi();
		}
		return lapi;
	}

	public void generateLanguageText(Locale local) {
		try {
			if (textLanguages.containsKey(local.getISO3Language())) {
				return;
			}
			File file = loadFile(local);
			LanguageText lt = JAXB.unmarshal(file, LanguageText.class);
			textLanguages.put(local.getISO3Language(), lt.getTextElements());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File loadFile(Locale local) throws IOException {
		File file = new File(quellOrdner, local.getISO3Language() + ".xml");
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public void generateDefaultLanguageText() {
		try {
			Locale local = Locale.getDefault();
			if (textLanguages.containsKey(local.getISO3Language())) {
				return;
			}
			File file = loadFile(local);
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

	public String getText(Locale local, Class<?> mainClass,
			Class<?> objectClass, String name) {
		List<TextElement> out = textLanguages.get(local.getISO3Language());
		String id = generateKey(mainClass, objectClass, name);
		int erg = Collections.binarySearch(out, new TextElement(id, ""),
				new TextElementComparator());
		return out.get(erg).getText();
	}

	public String getText(Class<?> mainClass, Class<?> objectClass, String name) {
		Locale local = Locale.getDefault();
		List<TextElement> out = textLanguages.get(local.getISO3Language());
		String id = generateKey(mainClass, objectClass, name);
		int erg = Collections.binarySearch(out, new TextElement(id, ""),
				new TextElementComparator());
		return out.get(erg).getText();
	}

	public String getText(String id) {
		List<TextElement> out = textLanguages.get(Locale.getDefault()
				.getISO3Language());
		int erg = Collections.binarySearch(out, new TextElement(id, ""),
				new TextElementComparator());
		return out.get(erg).getText();
	}

	private String generateKey(Class<?> mainClass, Class<?> objectClass,
			String extension) {
		String out = mainClass.getName() + "." + objectClass.getName() + "."
				+ extension;
		System.out.println(out);
		return out;
	}

	public boolean isLanguageTextGenerated(Locale local) {
		return textLanguages.containsKey(local.getISO3Language());
	}

	public void reinitLanguageText(Locale local) {
		textLanguages.remove(local.getISO3Language());
		generateLanguageText(local);
	}

	public static String getQuellOrdner() {
		return quellOrdner;
	}

	public static void setQuellOrdner(String quellOrdner) {
		Lapi.quellOrdner = quellOrdner;
	}
}
