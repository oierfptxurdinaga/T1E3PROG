package Testak;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.ErabiltzaileNormala;

class ErabiltzaileMota {
	private ErabiltzaileNormala erabiltzaile;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		erabiltzaile = new ErabiltzaileNormala("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");
	}

	@Test

	void testENKonstruktoreaetaGetters() {

		assertEquals("Aratz", erabiltzaile.getizena());

		assertEquals("Elexpe", erabiltzaile.getabizena());

		assertEquals("12345678A", erabiltzaile.getNAN());

		assertEquals("aelexpe", erabiltzaile.geterabiltzailea());

		assertEquals("12345", erabiltzaile.getpasahitza());

	}



	@Test

	void testSetIzenaEN() {

		erabiltzaile.setizena("Carlos");

		assertEquals("Carlos", erabiltzaile.getizena());

	}



	@Test

	void testSetAbizenaEN() {

		erabiltzaile.setabizena("Gomez");

		assertEquals("Gomez", erabiltzaile.getabizena());

	}



	@Test

	void testSetErabiltzaileaEN() {

		erabiltzaile.seterabiltzailea("carlosg");

		assertEquals("carlosg", erabiltzaile.geterabiltzailea());

	}



	@Test

	void testSetPasahitzaEN() {

		erabiltzaile.setpasahitza("abcd");

		assertEquals("abcd", erabiltzaile.getpasahitza());

	}



	@Test

	void testBaimenakEN() {

		assertEquals("Arrunta", erabiltzaile.baimenak());

	}


}
