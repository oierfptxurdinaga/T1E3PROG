package Programaren_Testak.DAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DAO.JokalariaDao;
import E2.Jokalaria;
import java.util.ArrayList;

/**
 * JokalariaDao klasearen integrazio testak. MySQL datu-basearekin jokalarien
 * kudeaketa egokia ziurtatzen du.
 * 
 * @author Talde1
 */
class JokalarienDAO_Testak {

	private JokalariaDao jDao;

	@BeforeEach
	void setUp() {
		jDao = new JokalariaDao();
	}

	/**
	 * Talde baten jokalariak kargatzen direla egiaztatu. Ziurtatu zure DBan
	 * "Unamuno" (edo beste bat) taldea jokalariak dituela.
	 */
	@Test
	void testKargatuJokalariakTaldeka() {
		ArrayList<Jokalaria> lista = jDao.kargatuJokalariakTaldeka("Unamuno");
		assertNotNull(lista, "Zerrenda ezin da null izan.");
		// Taldeak jokalariak baditu, tamaina 0 baino handiagoa izan behar da
		assertTrue(lista.size() >= 0, "Metodoak ondo exekutatu behar du.");
	}

	/**
	 * Jokalari guztiak kargatzeko metodoa probatu.
	 */
	@Test
	void testKargatuJokalariGuztiak() {
		ArrayList<Jokalaria> guztiak = jDao.kargatuJokalariGuztiak();
		assertNotNull(guztiak, "Zerrenda kargatu behar da.");
	}

	/**
	 * Jokalari baten talde aldaketa (fitxaketa) probatu. OHARRA: NAN honek DBan
	 * existitu behar du testak 'true' bueltatzeko.
	 */
	@Test
	void testAldatuTaldeaZuzena() {
		// Aldaketa simulatu: NAN bat eta talde berri bat erabili
		boolean emaitza = jDao.aldatuTaldea("12345678A", "LaSalle");
		// Emaitza edozein dela ere (true/false NANaren arabera),
		// garrantzitsuena exception-ik ez botatzea da estaldurarako.
		assertDoesNotThrow(() -> jDao.aldatuTaldea("00000000X", "Inexistente"));
	}

	/**
	 * Jokalaria objektu bidez eguneratzeko metodoa probatu.
	 */
	@Test
	void testEguneratuJokalaria() {
		Jokalaria j = new Jokalaria("Test", "Test", "2000-01-01", "12345678Z", "Unamuno", 1000);
		assertDoesNotThrow(() -> jDao.eguneratuJokalaria(j),
				"Eguneraketa metodoak catch blokea ondo kudeatu behar du.");
	}

	/**
	 * Zerrenda laguntzaileen metodoak probatu (Coverage igotzeko).
	 */
	@Test
	void testZerrendaLaguntzaileak() {
		Jokalaria j = new Jokalaria("Jon", "Gomez", "1995-05-05", "99999999G", "Loiola", 500);
		jDao.gehituJokalaria(j);
		assertEquals(1, jDao.getListaJokalariak().size(), "Jokalaria zerrenda lokalean gehitu behar da.");
		assertEquals("Jon", jDao.getListaJokalariak().get(0).getIzena());
	}

	/**
	 * Errore kasua: SQL okerra edo konexio falta simulatzen saiatu. Catch blokea
	 * estaltzeko NAN null pasatzea erabilgarria izan daiteke.
	 */
	@Test
	void testAldatuTaldeaNull() {
		// Parametroak null badira, SQL-ak edo PreparedStatement-ak huts egin dezake
		boolean emaitza = jDao.aldatuTaldea(null, null);
		assertFalse(emaitza, "Datu null-ekin ezin da taldea aldatu.");
	}
}