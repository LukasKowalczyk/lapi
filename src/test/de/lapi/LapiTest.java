package de.lapi;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LapiTest {
	private Lapi lapi;

	@Before
	public void setUp() {
		lapi = Lapi.getInstance();
	}

	@Test
	public void initTest() {
		lapi.generateLanguageText(Locale.getDefault());
		assertTrue(lapi.isLanguageTextGenerated(Locale.getDefault()));
	}
	@Test
	public void reinitTest() {
		lapi.generateLanguageText(Locale.getDefault());
		lapi.reinitLanguageText(Locale.getDefault());
		assertTrue(lapi.isLanguageTextGenerated(Locale.getDefault()));
	}
}
