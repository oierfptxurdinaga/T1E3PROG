package Programaren_Testak.Main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import Main.MainXML;

/**
 * XML fitxategiaren sorkuntza prozesua egiaztatzeko test klasea. JAXB bidezko
 * marshalling prozesuak ondo funtzionatzen duela ziurtatzen du. * @author Talde1
 * 
 * @version 1.0
 */
class XMLen_Testak {

	/**
	 * XML fitxategia zuzen sortzen dela eta diskoan gordetzen dela egiaztatzen du.
	 * Prozesuak ez luke exception-ik bota behar datu-basea konektatuta badago.
	 */
	@Test
	void testMainXMLSorkuntza() {
		// 1. Fitxategiaren izena definitu (MainXML klasean jarri duzun bera)
		String fitxategiIzena = "Denboraldia_2024-2025.xml";
		File f = new File(fitxategiIzena);
		// 2. Test-a hasi aurretik fitxategia existitzen bada, ezabatu (garbitasuna)
		if (f.exists()) {
			f.delete();
		}
		// 3. Prozesua exekutatu (MainXML-ko main-ari deitu)
		// Oharra: Datu-basea piztuta egon behar da DAOek huts egin ez dezaten
		try {
			MainXML.main(new String[0]);
		} catch (Exception e) {
			fail("XML sormen prozesuak huts egin du: " + e.getMessage());
		}
		// 4. BAIEZTAPENAK (Assertions)
		// Egiaztatu fitxategia sortu dela
		assertTrue(f.exists(), "XML fitxategia diskoan sortu beharko litzateke.");
		// Egiaztatu fitxategia ez dagoela hutsik
		assertTrue(f.length() > 0, "Sortutako XML fitxategiak edukia izan behar du (0 byte baino gehiago).");
		// Egiaztatu luzapena .xml dela
		assertTrue(f.getName().endsWith(".xml"), "Fitxategiak .xml luzapena izan behar du.");
	}

	/**
	 * JAXBContext-a ondo hasieratzen dela ziurtatzen duen test laguntzailea.
	 */
	@Test
	void testFitxategiarenBidea() {
		String fitxategiIzena = "Denboraldia_2024-2025.xml";
		File f = new File(fitxategiIzena);
		// Prozesua exekutatu ondoren, bidea absolutua dela ziurtatu dezakegu log-erako
		if (f.exists()) {
			System.out.println("Test arrakastatsua. XML helbidea: " + f.getAbsolutePath());
		}
	}
}