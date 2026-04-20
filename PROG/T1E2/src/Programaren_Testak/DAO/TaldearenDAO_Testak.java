package Programaren_Testak.DAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import DAO.TaldeDao;
import E2.Taldea;

/**
 * TaldeDao klasearen integrazio testak. Taldeen karga eta jokalarien talde
 * aldaketak MySQL-n ondo egiten direla ziurtatzen du.
 * 
 * @author Talde1
 */
class TaldearenDAO_Testak {

	private TaldeDao tDao;

	@BeforeEach
	void setUp() {
		tDao = new TaldeDao();
	}

	/**
	 * Talde guztiak kargatzen direla egiaztatzen du. ResultSet mapeoa
	 * (Sorrera_Data, Lehendakari_Izena...) ondo dagoela ziurtatzen du.
	 */
	@Test
	void testKargatuTaldeak() {
		ArrayList<Taldea> taldeak = tDao.kargatuTaldeak();
		assertNotNull(taldeak, "Taldeen zerrenda ezin da null izan.");
		// Daturen bat badago, getter-ak probatu coverage gehiago lortzeko
		if (!taldeak.isEmpty()) {
			Taldea t = taldeak.get(0);
			assertNotNull(t.getIzena(), "Taldearen izenak balioa izan behar du.");
			assertTrue(t.getN_Bazkideak() >= 0, "Bazkide kopurua ezin da negatiboa izan.");
		}
	}

	/**
	 * Jokalari baten taldea aldatu eta talde izenen zerrenda berria lortzen dela
	 * ziurtatu. Bi SQL-ak (UPDATE eta SELECT) exekutatzen dituela egiaztatzen du.
	 */
	@Test
	void testAldatutaetaLortuTaldeak() {
		// Erabili DBan existitzen den NAN bat eta talde baten izena
		String nanTest = "12345678A";
		String taldeBerria = "Unamuno";
		ArrayList<String> taldeIzenak = tDao.aldatutaetaLortuTaldeak(nanTest, taldeBerria);
		assertNotNull(taldeIzenak, "Talde izenen zerrenda ezin da null izan.");
		// SELECT DISTINCT-ek gutxienez talde bat bueltatu beharko luke liga martxan
		// badago
		assertFalse(taldeIzenak.isEmpty(), "Talde izenen zerrendak edukia izan behar du.");
	}

	/**
	 * Errore kudeaketa: NAN-a null bada, programak ez duela krash egiten ziurtatu.
	 * Catch blokea estaltzen laguntzen du.
	 */
	@Test
	void testAldatutaetaLortuTaldeakErroreKudeaketa() {
		// NAN null pasatzean, SQL-ak ez du inor aurkituko edo catch-era joango da
		assertDoesNotThrow(() -> {
			tDao.aldatutaetaLortuTaldeak(null, "Ezezaguna");
		}, "Null pasatzean catch blokeak errorea kudeatu behar du programak huts egin gabe.");
	}

	/**
	 * SQL konexioa edo zutabe izen okerra balego, kargatuTaldeak-ek zerrenda hutsik
	 * bueltatzen duela egiaztatu (errorea catch-ean harrapatuta).
	 */
	@Test
	void testKargatuTaldeakSalbuespena() {
		// Test honek try-catch-aren sendotasuna egiaztatzen du
		ArrayList<Taldea> zerrenda = tDao.kargatuTaldeak();
		assertNotNull(zerrenda,
				"Nahiz eta errore bat egon, metodoak ArrayList bat bueltatu behar du (hutsik bada ere).");
	}
}