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
	private static Locale defaultLocation = Locale.getDefault();

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
			if (textLanguages.containsKey(defaultLocation.getISO3Language())) {
				return;
			}
			File file = loadFile(defaultLocation);
			LanguageText lt = JAXB.unmarshal(file, LanguageText.class);
			textLanguages.put(defaultLocation.getISO3Language(), lt.getTextElements());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getText(Locale local, String id) {
		List<TextElement> out = textLanguages.get(local.getISO3Language());
		return findText(id, out);
	}

	public String getText(Locale local, Class<?> mainClass, String name) {
		List<TextElement> out = textLanguages.get(local.getISO3Language());
		String id = generateKey(mainClass, name);
		return findText(id, out);
	}

	public String getText(Class<?> mainClass, String name) {
		List<TextElement> out = textLanguages.get(defaultLocation.getISO3Language());
		String id = generateKey(mainClass, name);
		return findText(id, out);
	}

	public String getText(String id) {
		List<TextElement> out = textLanguages.get(defaultLocation
				.getISO3Language());
		return findText(id, out);
	}

	private String findText(String id, List<TextElement> out) {
		for (TextElement textElement : out) {
			if (textElement.getId().equals(id)) {
				return textElement.getText();
			}
		}
		return "##Text not found##";
	}

	private String generateKey(Class<?> mainClass, String extension) {
		String out = mainClass.getSimpleName() + "." + extension;
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

	public static Locale getDefaultLocation() {
		return defaultLocation;
	}

	public static void setDefaultLocation(Locale defaultLocation) {
		Lapi.defaultLocation = defaultLocation;
	}
}
