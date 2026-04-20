package Programaren_Testak.DAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import DAO.JokalariaObjectDB;
import E2.Jokalaria;

/**
 * JokalariaObjectDB klasearen test unitarioak. MySQL bidezko jokalarien
 * kudeaketa (UPDATE, SELECT, BATCH INSERT) egiaztatzen du.
 * 
 * @author Talde1
 */
class JokalariaObjectDBTest {

	private JokalariaObjectDB jodb;

	@BeforeEach
	void setUp() {
		jodb = new JokalariaObjectDB();
	}

	/**
	 * Talde aldaketa (UPDATE) probatzen du. Ziurtatu NAN hau zure datu-basean
	 * existitzen dela testa 'true' izateko.
	 */
	@Test
	void testAldatuTaldea() {
		// Aldaketa simulatu (NAN existitzen bada true bueltatuko du, bestela false)
		// Baina garrantzitsuena metodoak errorerik (Exception) ez ematea da
		String nanTest = "12345678A";
		String taldeBerria = "LaSalle";
		assertDoesNotThrow(() -> {
			boolean emaitza = jodb.aldatuTaldea(nanTest, taldeBerria);
			System.out.println("Talde aldaketa: " + (emaitza ? "Gauzatua" : "Ez da NANa aurkitu"));
		});
	}

	/**
	 * Taldeka jokalariak kargatzea (SELECT) probatzen du. Zutabeen mapeoa (NAN,
	 * Jok_Izena...) ondo dagoela ziurtatzen du.
	 */
	@Test
	void testGetJokalariakTaldeka() {
		String taldeIzena = "Unamuno"; // Erabili zure DBan dagoen talde bat
		List<Jokalaria> jokalariak = jodb.getJokalariakTaldeka(taldeIzena);
		assertNotNull(jokalariak, "Zerrenda ezin da null izan.");
		// Zerrenda kargatu dela ikusteko log-a
		System.out.println(taldeIzena + " taldeko jokalari kopurua: " + jokalariak.size());
		if (!jokalariak.isEmpty()) {
			assertNotNull(jokalariak.get(0).getNAN(), "Jokalariaren NANa ezin da null izan.");
		}
	}

	/**
	 * Jokalariak sortu eta Batch bidez sartzea probatzen du. 'INSERT IGNORE'
	 * erabiltzen denez, NANa errepikatuta badago ez du errorerik emango.
	 */
	@Test
	void testInicializarODB() {
		List<Jokalaria> zerrenda = new ArrayList<>();

		// Jokalari dummy bat sortu
		Jokalaria j = new Jokalaria();
		j.setNAN("99999999Z");
		j.setIzena("Test_Izena");
		j.setAbizena("Test_Abizena");
		j.setPrezioa(1000);
		j.setTaldea("Loiola");

		zerrenda.add(j);

		// Metodoari deitu
		assertDoesNotThrow(() -> jodb.inicializarODB(zerrenda), "Batch insert-ak ez luke errorerik bota behar.");
	}

	/**
	 * Errore kasua: Datu-base konexioa edo SQL okerra (null-ekin). Coverage-a
	 * igotzeko catch blokeak ukitu behar dira.
	 */
	@Test
	void testAldatuTaldeaNull() {
		// Parametro null-ak pasatzean SQLException harrapatu behar du
		boolean emaitza = jodb.aldatuTaldea(null, null);
		assertFalse(emaitza, "Null balioekin false bueltatu behar du catch blokearen bidez.");
	}
}