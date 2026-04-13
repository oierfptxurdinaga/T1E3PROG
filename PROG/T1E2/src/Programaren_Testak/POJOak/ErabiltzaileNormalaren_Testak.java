package Programaren_Testak.POJOak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import E2.ErabiltzaileNormala;

/**
 * ErabiltzaileNormala klasearen unitate-testak. Erabiltzaile arrunten datuak
 * eta baimen maila zuzenak direla ziurtatzen du. JUnit 4 liburutegia erabiltzen
 * du (Java 1.8-rekin bateragarria).
 */
public class ErabiltzaileNormalaren_Testak {
	private ErabiltzaileNormala erabiltzaile;

	/**
	 * Test bakoitzaren aurretik erabiltzaile arrunt bat sortzen dugu.
	 */
	@Before
	public void setUp() {
		// Balioak: Izena, Abizena, NAN, Erabiltzailea, Pasahitza
		erabiltzaile = new ErabiltzaileNormala("Ane", "Garmendia", "87654321X", "agarmendia", "Ane2024");
	}

	/**
	 * Eraikitzaileak eta Getterrak datuak ondo kudeatzen dituztela egiaztatzen du.
	 */
	@Test
	public void testConstructorAndGetters() {
		// Atributu guztiak banan-banan egiaztatu (JUnit 4-n ez dago assertAll)
		assertEquals("Izena ez da zuzena", "Ane", erabiltzaile.getizena());
		assertEquals("Abizena ez da zuzena", "Garmendia", erabiltzaile.getabizena());
		assertEquals("NANa ez da zuzena", "87654321X", erabiltzaile.getNAN());
		assertEquals("Erabiltzaile-izena ez da zuzena", "agarmendia", erabiltzaile.geterabiltzailea());
		assertEquals("Pasahitza ez da zuzena", "Ane2024", erabiltzaile.getpasahitza());
	}

	/**
	 * Setter metodoek atributu babestuak (protected) ondo aldatzen dituztela
	 * ziurtatzen du.
	 */
	@Test
	public void testSetters() {
		erabiltzaile.setizena("Amaia");
		erabiltzaile.setpasahitza("PasswordNew");

		assertEquals("Izena aldatu ondoren ez da zuzena", "Amaia", erabiltzaile.getizena());
		assertEquals("Pasahitza aldatu ondoren ez da zuzena", "PasswordNew", erabiltzaile.getpasahitza());
	}

	/**
	 * baimenak() metodoak "Arrunta" itzultzen duela ziurtatzen du.
	 */
	@Test
	public void testBaimenak() {
		String baimena = erabiltzaile.baimenak();
		assertNotNull("Baimen katea ezin da null izan", baimena);
		assertEquals("Erabiltzaile honek 'Arrunta' baimen maila izan behar du", "Arrunta", baimena);
	}
}