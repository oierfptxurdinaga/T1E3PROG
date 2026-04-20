package Programaren_Testak.POJOak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import E2.Presidentea;

/**
 * Presidentea klasearen funtzionaltasuna egiaztatzeko test unitarioak. JUnit 4
 * liburutegia erabiltzen du (Java 1.8-rekin bateragarria).
 */
public class Presidentearen_Testak {

	private Presidentea presidentea;

	/**
	 * Test bakoitzaren aurretik objektu berri bat sortzen du egoera garbi bat
	 * izateko.
	 */
	@Before
	public void setUp() {
		// Balioak zure klasearen eraikitzailearekin bat etortzeko:
		// Izena, Abizena, NAN, Erabiltzailea, Pasahitza
		presidentea = new Presidentea("Jon", "Agirre", "12345678A", "jona", "pasa123");
	}

	/**
	 * Eraikitzaileak eta getter-ek ondo funtzionatzen dutela egiaztatzen du.
	 */
	@Test
	public void testEraikitzaileaEtaGetterrak() {
		// JUnit 4-n: assertEquals(esperado, actual)
		assertEquals("Jon", presidentea.getizena());
		assertEquals("Agirre", presidentea.getabizena());
		assertEquals("12345678A", presidentea.getNAN());
		assertEquals("jona", presidentea.geterabiltzailea());
		assertEquals("pasa123", presidentea.getpasahitza());
	}

	/**
	 * Setter-ek aldagaiak ondo aldatzen dituztela frogatzen du.
	 */
	@Test
	public void testSetterrak() {
		presidentea.setizena("Mikel");
		presidentea.setabizena("Garmendia");
		presidentea.seterabiltzailea("mikelg");
		presidentea.setpasahitza("berria456");

		assertEquals("Mikel", presidentea.getizena());
		assertEquals("Garmendia", presidentea.getabizena());
		assertEquals("mikelg", presidentea.geterabiltzailea());
		assertEquals("berria456", presidentea.getpasahitza());
	}

	/**
	 * baimenak() metodoak "Presidentea" katea itzultzen duela ziurtatzen du.
	 */
	@Test
	public void testBaimenak() {
		String esperoDenBaimena = "Presidentea";
		// Ziurtatu zure POJOan metodo honek "Presidentea" itzultzen duela (letra
		// larriak kontutan hartuta)
		assertEquals("Baimen maila ez da zuzena", esperoDenBaimena, presidentea.baimenak());
	}
}