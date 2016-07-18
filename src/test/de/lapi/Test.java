package de.lapi;

import java.util.Locale;

public class Test {

	public static void main(String[] args) {
		Lapi lapi = Lapi.getInstance();
		lapi.generateLanguageText(Locale.getDefault());
		System.out.println(lapi.getText(Locale.getDefault(), "1233"));
	}

}
