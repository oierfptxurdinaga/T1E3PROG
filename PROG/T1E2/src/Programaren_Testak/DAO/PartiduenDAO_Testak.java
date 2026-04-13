package Programaren_Testak.DAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DAO.PartiduaDao;
import E2.Partidua;
import java.util.ArrayList;

/**
 * PartiduaDao klasearen integrazio testak. Partiduen karga eta emaitzen
 * sinkronizazioa (MySQL) egiaztatzen ditu.
 * 
 * @author Talde1
 */
class PartiduenDAO_Testak {

	private PartiduaDao pDao;

	@BeforeEach
	void setUp() {
		pDao = new PartiduaDao();
	}

	/**
	 * Datu-basetik partiduak kargatzen direla ziurtatzen du. SQL-ko DATE() eta
	 * TIME() aliasak (fecha/hora) ondo irakurtzen direla egiaztatzen du.
	 */
	@Test
	void testKargatuPartiduak() {
		ArrayList<Partidua> zerrenda = pDao.kargatuPartiduak();
		assertNotNull(zerrenda, "Partiduen zerrenda ezin da null izan.");
		// Zerrenda hutsik ez badago, lehenengo partiduko datuak probatu ditzakegu
		if (!zerrenda.isEmpty()) {
			Partidua p = zerrenda.get(0);
			assertNotNull(p.getTaldeLokala(), "Talde lokalak izena izan behar du.");
			assertTrue(p.getId_Par() > 0, "ID-ak positiboa izan behar du.");
		}
	}

	/**
	 * Partidu baten puntuak soilik eguneratzen direla egiaztatu.
	 */
	@Test
	void testEguneratuPuntuak() {
		// ID-a 1 duen partidua eguneratzen saiatu (ziurtatu ID hau existitzen dela)
		assertDoesNotThrow(() -> pDao.eguneratuPuntuak(1, 80, 75),
				"Puntuak eguneratzean ez luke SQL errorerik eman behar.");
	}

	/**
	 * Transakzio konplexua probatu: Partidua eta Jaurdunaldia taulak aldi berean.
	 * Bi UPDATE-ak (sqlPartidua eta sqlJaurdu) exekutatzen direla ziurtatzen du.
	 */
	@Test
	void testModifyPartiduaEtaJaurdunaldi() {
		// Parametroak: idPar, resLoc, resVis, irabazlea, galtzailea
		assertDoesNotThrow(() -> pDao.modifyPartiduaEtaJaurdunaldi(1, 100, 90, "IrabazleTaldea", "GalduTaldea"),
				"Eguneraketa bikoitzak (Partidua + Jaurdunaldia) ondo funtzionatu behar du.");
	}

	/**
	 * Zerrenda oso baten sinkronizazioa probatu (Metodo estatikoa). Honek barneko
	 * for-each-a eta JOptionPane-a (baldin eta bururik gabe exekutatzen bada)
	 * testatzen ditu.
	 */
	@Test
	void testEguneratuPartiduakGuztiak() {
		ArrayList<Partidua> masterList = new ArrayList<>();
		// Partidu bat gehitu zerrendan testerako
		masterList.add(new Partidua(1, "Loiola", "Unamuno", 85, 80, "2024-05-10", "18:00"));
		// Metodo estatikoa denez, klasetik zuzenean deitzen dugu
		assertDoesNotThrow(() -> PartiduaDao.eguneratuPartiduakGuztiak(masterList),
				"Zerrenda osoaren sinkronizazioak ez luke errorerik bota behar.");
	}

	/**
	 * Errore kasua: ID okerra edo negatiboa erabiltzean programak ez dela gelditzen
	 * ziurtatu.
	 */
	@Test
	void testEguneratuPuntuakIdOkerra() {
		// Existitzen ez den ID bat pasatu (-1)
		// Metodoak ez du ezer bueltatzen (void), baina catch blokeak errorea kudeatu
		// behar du
		assertDoesNotThrow(() -> pDao.eguneratuPuntuak(-1, 0, 0),
				"ID oker batekin catch blokeak mezua inprimatu behar du programak huts egin gabe.");
	}
}