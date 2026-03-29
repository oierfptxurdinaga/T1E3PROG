package Programaren_Testak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import E2.ErabiltzaileMota;

/**
 * ErabiltzaileMota klase abstraktuaren unitate-testak. JUnit 4 liburutegia
 * erabiltzen du (Java 1.8-rekin bateragarria). Klase abstraktua denez,
 * inplementazio anonimo bat erabiliko dugu testatzeko.
 */
public class ErabiltzaileMotaren_Testak {
	private ErabiltzaileMota erabiltzaile;

	/**
	 * Test bakoitzaren aurretik inplementazio anonimo bat sortzen dugu. Honela,
	 * klase abstraktuaren oinarrizko logika ziurtatu dezakegu.
	 */
	@Before
	public void setUp() {
		// Klase anonimoa sortzen dugu baimenak() metodoa inplementatuz
		// Oharra: Ziurtatu zure POJOan eraikitzaileak parametro hauek dituela
		erabiltzaile = new ErabiltzaileMota("Jon", "Duo", "12345678Z", "jduo", "Pass123") {
			@Override
			public String baimenak() {
				return "Proba";
			}
		};
	}

	/**
	 * Eraikitzaileak 'protected' edo 'private' diren eremuak ondo betetzen dituela
	 * egiaztatzen du.
	 */
	@Test
	public void testConstructor() {
		// Getters publikoak erabiliz egiaztatzen dugu (JUnit 4-n banan-banan)
		assertEquals("Erabiltzailea ez da zuzena", "jduo", erabiltzaile.getErabiltzailea());
		assertEquals("Pasahitza ez da zuzena", "Pass123", erabiltzaile.getPasahitza());
	}

	/**
	 * baimenak() metodo abstraktua azpiklaseetan funtzionatzen duela simulatzen du.
	 */
	@Test
	public void testBaimenakAbstract() {
		assertEquals("Baimenak metodoak inplementazioaren balioa itzuli behar du", "Proba", erabiltzaile.baimenak());
	}

	/**
	 * Egiaztatu datu batzuk null izanda ere objektua sortzen dela (Null Safety
	 * oinarria).
	 */
	@Test
	public void testNullData() {
		ErabiltzaileMota nullUser = new ErabiltzaileMota(null, null, null, "nullUser", null) {
			@Override
			public String baimenak() {
				return "Null";
			}
		};
		assertEquals("Erabiltzaile izena zuzena izan behar da", "nullUser", nullUser.getErabiltzailea());
		assertNull("Pasahitza null izan behar da", nullUser.getPasahitza());
	}
}