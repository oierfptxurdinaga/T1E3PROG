package Programaren_Testak.ConexionDB;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import DB.ConexionDB;

/**
 * ConexionDB klasearen test unitarioak. MySQL-arekiko konexioa eta deskonexioa
 * ondo kudeatzen direla ziurtatzen du.
 */
class ConexionDB_Testak {

	private ConexionDB db;

	@BeforeEach
	void setUp() {
		db = new ConexionDB();
	}

	/**
	 * Konexioa ondo irekitzen dela egiaztatu. GARRANTZITSUA: XAMPP/MySQL piztuta
	 * egon behar da 'bsf' datu-basearekin.
	 */
	@Test
	void testKonektatuZuzena() {
		Connection kon = db.konektatu();
		assertNotNull(kon, "Konexioak ez luke null izan behar datu-basea piztuta badago.");
		try {
			assertFalse(kon.isClosed(), "Konexioak irekita egon behar luke.");
			// Testaren ondoren garbitu
			kon.close();
		} catch (SQLException e) {
			fail("Errorea konexioaren egoera egiaztatzerakoan.");
		}
	}

	/**
	 * Deskonektatu metodoa probatu.
	 */
	@Test
	void testDeskonektatu() {
		Connection kon = db.konektatu();
		assertNotNull(kon);
		db.deskonektatu(kon);
		try {
			assertTrue(kon.isClosed(), "Deskonektatu ondoren konexioa itxita egon behar da.");
		} catch (SQLException e) {
			fail("Errorea deskonexioa egiaztatzerakoan.");
		}
	}

	/**
	 * Coverage-a igotzeko: Zer gertatzen da null deskonektatzen saiatzean? Kodeak
	 * 'if (kon != null)' duenez, honek adar hori testatzen du.
	 */
	@Test
	void testDeskonektatuNull() {
		assertDoesNotThrow(() -> db.deskonektatu(null), "Null bat deskonektatzean ez luke errorerik bota behar.");
	}

	/**
	 * Coverage-a igotzeko: Zer gertatzen da jada itxita dagoen konexio bat
	 * itzaltzean?
	 */
	@Test
	void testDeskonektatuItxita() {
		Connection kon = db.konektatu();
		try {
			kon.close(); // Eskuz itxi
			assertDoesNotThrow(() -> db.deskonektatu(kon),
					"Jada itxita dagoen konexioa deskonektatzean ez luke arazorik eman behar.");
		} catch (SQLException e) {
			fail("Errorea prestaketan.");
		}
	}
}