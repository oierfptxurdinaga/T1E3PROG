package Programaren_Testak.POJOak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import E2.Administradorea;

/**
 * Administradorea klasearen funtzionaltasuna egiaztatzeko unitate-testak. JUnit
 * 4 liburutegia erabiltzen du (Java 1.8-rekin bateragarriagoa).
 */
public class Administradoreen_Testak {
	private Administradorea admin;

	@Before
	public void setUp() {
		// Testetarako datu-multzo estandarra: Izena, Abizena, NAN, Erabiltzailea,
		// Pasahitza
		admin = new Administradorea("Jon", "Duo", "12345678Z", "jduo", "Pass123");
	}

	@Test
	public void testConstructorAndGetters() {
		// Atributu guztiak banan-banan egiaztatu
		assertEquals("Izena ez dator bat", "Jon", admin.getizena());
		assertEquals("Abizena ez dator bat", "Duo", admin.getabizena());
		assertEquals("NANa ez dator bat", "12345678Z", admin.getNAN());
		assertEquals("Erabiltzaile izena ez dator bat", "jduo", admin.geterabiltzailea());
		assertEquals("Pasahitza ez dator bat", "Pass123", admin.getpasahitza());
	}

	@Test
	public void testSetters() {
		admin.setizena("Mikel");
		admin.seterabiltzailea("mikel_admin");
		admin.setpasahitza("NewPass99");

		assertEquals("Mikel", admin.getizena());
		assertEquals("mikel_admin", admin.geterabiltzailea());
		assertEquals("NewPass99", admin.getpasahitza());
	}

	@Test
	public void testBaimenak() {
		// baimenak() metodoak "Admin" katea bueltatzen duela ziurtatzen du
		String baimena = admin.baimenak();
		assertNotNull("Baimena ezin da null izan", baimena);
		assertEquals("Administratzaileak 'Admin' rola izan behar du", "Admin", baimena);
	}
}