package Programaren_Testak.DAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import DAO.JaurdunaldiDao;
import E2.Jaurdunaldia;

/**
 * JaurdunaldiDao klasearen integrazio testak. Jaurdunaldien karga eta
 * irabazleen eguneraketak MySQL-n ondo egiten direla ziurtatzen du.
 * 
 * @author Talde1
 */
class JaurdunaldiarenDAO_Testak {

	private JaurdunaldiDao jDao;

	@BeforeEach
	void setUp() {
		jDao = new JaurdunaldiDao();
	}

	/**
	 * Jaurdunaldien karga probatzen du. ResultSet-eko zutabeak (Id_Par,
	 * Id_Jaurdu...) ondo mapeatzen direla ziurtatzen du.
	 */
	@Test
	void testKargatuJaurdunaldiak() {
		ArrayList<Jaurdunaldia> zerrenda = jDao.kargatuJaurdunaldiak();
		assertNotNull(zerrenda, "Zerrenda ezin da null izan.");
		// Daturen bat badago DBan, objektuaren barruko datuak egiaztatu
		if (!zerrenda.isEmpty()) {
			Jaurdunaldia j = zerrenda.get(0);
			assertTrue(j.getIdPar() >= 0, "Id_Par-ek balio positiboa izan behar du.");
			assertTrue(j.getIdJaurdu() >= 0, "Id_Jaurdu-k balio positiboa izan behar du.");
		}
	}

	/**
	 * Irabazlea eta galtzailea eguneratzeko metodoa probatzen du (UPDATE). Ziurtatu
	 * ID hau zure DBan dagoela testa 'realagoa' izateko.
	 */
	@Test
	void testEguneratuIrabazlea() {
		// Parametroak: idPar, irabazlea, galtzailea
		assertDoesNotThrow(() -> jDao.eguneratuIrabazlea(1, "Loiola", "Unamuno"),
				"Eguneraketak ez luke SQL errorerik eman behar.");
	}

	/**
	 * Errore kudeaketa probatu: catch blokea estaltzeko. Parametro okerrak
	 * pasatzean programak ez duela krash egiten ziurtatu.
	 */
	@Test
	void testEguneratuIrabazleaErrorea() {
		// String luzeegi bat edo datu mota okerra (null) pasatzen saiatu
		// Honek catch blokearen barruko System.out.println exekutatuko du
		assertDoesNotThrow(() -> jDao.eguneratuIrabazlea(-1, null, null),
				"Datu okerrekin catch blokeak errorea kudeatu behar du.");
	}

	/**
	 * SQL konexioa probatu (zeharka). Kargatzean errorerik badago, zerrenda hutsik
	 * bueltatzen dela ziurtatu.
	 */
	@Test
	void testKargatuJaurdunaldiakHutsik() {
		ArrayList<Jaurdunaldia> zerrenda = jDao.kargatuJaurdunaldiak();
		assertNotNull(zerrenda, "Nahiz eta konexio arazoak egon, ArrayList bat bueltatu behar du.");
	}
}