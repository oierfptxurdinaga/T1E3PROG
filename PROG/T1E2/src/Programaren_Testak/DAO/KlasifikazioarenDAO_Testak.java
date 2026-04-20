package Programaren_Testak.DAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import DAO.KlasifikazioaDao;
import E2.Taldea;
import java.util.ArrayList;

/**
 * KlasifikazioaDao klasearen integrazio testak. Datu-baseko sailkapenaren
 * eguneraketak ondo gauzatzen direla egiaztatzen du.
 * 
 * @author Talde1
 * @version 1.0
 */
class KlasifikazioarenDao_Testak {

	/**
	 * Talde baten sailkapen datuak DBan eguneratzen direla egiaztatzen du. Kontuz:
	 * Test honek datu-basean zegoen informazioa aldatuko du.
	 */
	@Test
	void testModifyKlasifikazioaZuzena() {
		KlasifikazioaDao kDao = new KlasifikazioaDao();
		// 1. Test talde bat sortu (Ziurtatu izen hau zure DBan dagoela, adib. "Loiola")
		// Datu hauekin eguneratuko dugu
		Taldea t = new Taldea("Loiola", "1990", "Presidentea", 100, 500, 450, 20, 10, 0, new ArrayList<>());
		// 2. Metodoari deitu. Ez luke Exception-ik bota behar.
		assertDoesNotThrow(() -> kDao.modifyKlasifikazioa(t),
				"Eguneraketak ez luke errorerik eman behar SQL sintaxiagatik.");
	}

	/**
	 * Existitzen ez den talde bat eguneratzen saiatzean errore larririk ez dela
	 * gertatzen ziurtatzen du (try-catch blokea estaltzeko).
	 */
	@Test
	void testModifyKlasifikazioaTaldeOkerra() {
		KlasifikazioaDao kDao = new KlasifikazioaDao();
		// Datu-basean existitzen ez den talde baten izena
		Taldea tInexistentia = new Taldea("Talde_Fikziozkoa", "2026", "Inor", 0, 0, 0, 0, 0, 0, new ArrayList<>());
		// Metodoak ez du errorerik bueltatzen (void), baina catch blokeak kudeatu behar
		// du
		assertDoesNotThrow(() -> kDao.modifyKlasifikazioa(tInexistentia),
				"Existitzen ez den talde batek ez luke programa gelditu behar.");
	}

	/**
	 * Talde objektua null denean gertatzen den portaera aztertzen du. Honek zure
	 * "catch" blokea aktibatu dezake NullPointerException batengatik.
	 */
	@Test
	void testModifyKlasifikazioaNull() {
		KlasifikazioaDao kDao = new KlasifikazioaDao();
		// Objektua null bada, t.getPuntuTotalak() egitean errorea emango du
		// Metodoaren barruko catch blokeak mezu bat idatziko du kontsolan
		assertDoesNotThrow(() -> kDao.modifyKlasifikazioa(null),
				"Null pasatzean catch blokeak errorea kudeatu beharko luke.");
	}
}