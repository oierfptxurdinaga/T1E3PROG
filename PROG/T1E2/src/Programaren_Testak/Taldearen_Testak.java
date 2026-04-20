package Programaren_Testak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import E2.Taldea;
import E2.Jokalaria;

/**
 * Taldea klasearen unitate-testak. Taldearen datu orokorrak eta jokalarien
 * zerrenda ondo kudeatzen direla ziurtatzen du. JUnit 4 liburutegia erabiltzen
 * du (Java 1.8-rekin bateragarria).
 */
public class Taldearen_Testak {

	private Taldea taldea;

	/**
	 * Test bakoitzaren aurretik objektu bat sortzen dugu datu frogagarriekin.
	 */
	@Before
	public void setUp() {
		// Parametros: Izena, Urtea, Lehendakaria, Bazkideak, PuntuakF, PuntuakC,
		// Totala, Irabazi, Galdu, Jokalariak
		taldea = new Taldea("Bilbao Basket", "2000", "Isabel Iturbe", 5000, 100, 80, 10, 5, 2,
				new ArrayList<Jokalaria>());
	}

	/**
	 * Getter-ak datuak ondo berreskuratzen dituztela egiaztatzen du.
	 */
	@Test
	public void testTaldeaDatuak() {
		// JUnit 4-n: assertEquals(esperado, actual)
		assertEquals("Izena ez dator bat", "Bilbao Basket", taldea.getIzena());
		assertEquals("Lehendakaria ez da zuzena", "Isabel Iturbe", taldea.getLehendakari());
		assertEquals("Bazkide kopurua ez da zuzena", 5000, taldea.getN_Bazkideak());
	}

	/**
	 * Setter-en bidez balioak aldatu eta egiaztatu daitezkeela ziurtatzen du.
	 */
	@Test
	public void testTaldeaSetters() {
		taldea.setIzena("Baskonia");
		assertEquals("Izena aldatu ondoren ez da zuzena", "Baskonia", taldea.getIzena());

		taldea.setSorreraUrtea("1959");
		assertEquals("Sorrera urtea ez da ondo aldatu", "1959", taldea.getSorreraUrtea());
	}

	/**
	 * toString metodoak taldearen izena itzultzen duela ziurtatzen du.
	 */
	@Test
	public void testToString() {
		// Zure POJOan: public String toString() { return Izena; }
		assertEquals("toString-ak taldearen izena bueltatu behar du", "Bilbao Basket", taldea.toString());
	}
}