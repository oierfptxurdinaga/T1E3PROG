package Testak;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Administradorea;

class AdministradoreTest {
	 private Administradorea admin;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		admin = new Administradorea("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");
	}

	@Test

	void testAdmKontruktoreaetaGetters() {

		assertEquals("Aratz", admin.getizena());

		assertEquals("Elexpe", admin.getabizena());

		assertEquals("12345678A", admin.getNAN());

		assertEquals("aelexpe", admin.geterabiltzailea());

		assertEquals("12345", admin.getpasahitza());

	}



	@Test

	void testSetIzenaAdm() {

		admin.setizena("Carlos");

		assertEquals("Carlos", admin.getizena());

	}



	@Test

	void testSetAbizenaAdm() {

		admin.setabizena("Gomez");

		assertEquals("Gomez", admin.getabizena());

	}



	@Test

	void testSetErabiltzaileaAdm() {

		admin.seterabiltzailea("carlosg");

		assertEquals("carlosg", admin.geterabiltzailea());

	}



	@Test

	void testSetPasahitzaAdm() {

		admin.setpasahitza("abcd");

		assertEquals("abcd", admin.getpasahitza());

	}



	@Test

	void testBaimenakAdm() {

		assertEquals("Admin", admin.baimenak());

	}

}
