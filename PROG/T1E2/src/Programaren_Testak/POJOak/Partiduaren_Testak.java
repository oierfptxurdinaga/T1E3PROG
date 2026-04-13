package Programaren_Testak.POJOak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import E2.Partidua;

/**
 * Partidua klasearen unitate-testak. Datuen osotasuna eta JAXB anotazioen
 * bidezko kudeaketa egiaztatzen du. JUnit 4 liburutegia erabiltzen du (Java
 * 1.8-rekin bateragarria).
 */
public class Partiduaren_Testak {
	private Partidua partidua;

	/**
	 * Test bakoitzaren aurretik Partidua objektu bat sortzen dugu datu finkoekin.
	 */
	@Before
	public void setUp() {
		// Partidua inizializatu: id, lokala, bisitaria, puntu_lok, puntu_bis, data,
		// ordua
		partidua = new Partidua(101, "Lakers", "Celtics", 102, 100, "2026-05-10", "21:00");
	}

	/**
	 * Getter eta Setter metodoek datuak ondo kudeatzen dituztela ziurtatzen du.
	 */
	@Test
	public void testGettersEtaSetters() {
		// Hasierako datuak egiaztatu
		assertEquals("IDa ez da zuzena", 101, partidua.getId_Par());
		assertEquals("Talde lokala ez dator bat", "Lakers", partidua.getTaldeLokala());
		assertEquals("Emaitza bisitaria ez da zuzena", 100, partidua.getResulBisitari());

		// Balioak aldatu setter bidez eta egiaztatu
		partidua.setResultLokala(110);
		assertEquals("Aldatutako puntuazio lokala ez da zuzena", 110, partidua.getResultLokala());

		partidua.setTaldeBisitari("Bulls");
		assertEquals("Talde bisitaria ez da ondo aldatu", "Bulls", partidua.getTaldeBisitari());
	}

	/**
	 * JAXB eta ObjectDB-rako ezinbestekoa den eraikitzaile hutsa egiaztatzen du.
	 */
	@Test
	public void testConstructorHutsa() {
		Partidua hutsa = new Partidua();
		assertNotNull("Objektu hutsa ezin da nulua izan", hutsa);

		hutsa.setTaldeBisitari("Baskonia");
		assertEquals("Eraikitzaile hutsaren ondoren setter-ak funtzionatu behar du", "Baskonia",
				hutsa.getTaldeBisitari());
	}

	/**
	 * Data eta ordu formatua String bezala ondo gordetzen dela ziurtatzen du.
	 */
	@Test
	public void testDataEtaOrdua() {
		assertEquals("Data ez dator bat", "2026-05-10", partidua.getData());
		assertEquals("Ordua ez dator bat", "21:00", partidua.getOrdua());
	}
}