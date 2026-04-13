package Programaren_Testak.POJOak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import E2.Denboraldia;
import E2.Jaurdunaldia;
import E2.Taldea;
import java.util.ArrayList;

/**
 * Denboraldia klasearen unitate-testak. JAXB bidezko XML kudeaketarako
 * garrantzitsua den POJOaren portaera egiaztatzen du. JUnit 4 liburutegia
 * erabiltzen du (Java 1.8-rekin bateragarria).
 */
public class Denboraldiaren_Testak {
	private Denboraldia denboraldia;

	/**
	 * Test bakoitzaren aurretik Denboraldia objektu bat sortzen dugu.
	 */
	@Before
	public void setUp() {
		denboraldia = new Denboraldia();
	}

	/**
	 * Eraikitzaile hutsak listak ondo hasieratzen dituela egiaztatzen du. JAXB-k
	 * ondo funtzionatzeko ezinbestekoa da NullPointerException-ik ez egotea.
	 */
	@Test
	public void testConstructorVacio() {
		// Egiaztatu listak ez direla null
		assertNotNull("Jaurdunaldiak zerrenda ezin da null izan", denboraldia.getJaurdunaldiak());
		assertNotNull("Taldeak zerrenda ezin da null izan", denboraldia.getTaldeak());
		assertNotNull("Puntuazio zerrenda ezin da null izan", denboraldia.getDenboraldiaP());

		// Hasieran listak hutsik egon behar dira (0 tamaina)
		assertEquals(0, denboraldia.getJaurdunaldiak().size());
	}

	/**
	 * Getters eta Setters metodoen bidez datuak ondo gordetzen direla frogatzen du.
	 */
	@Test
	public void testGettersAndSetters() {
		String dataProba = "2025-2026";
		denboraldia.setData(dataProba);

		ArrayList<Taldea> taldeLista = new ArrayList<>();
		taldeLista.add(new Taldea()); // Talde huts bat gehitzen dugu probarako
		denboraldia.setTaldeak(taldeLista);

		// Banan-banan egiaztatzen dugu (JUnit 4-n ez dago assertAll)
		assertEquals("Data ez da ondo gorde", dataProba, denboraldia.getData());
		assertEquals("Talde kopurua ez da zuzena", 1, denboraldia.getTaldeak().size());
	}

	/**
	 * Eraikitzaile osoak datuak zuzen esleitzen dituela egiaztatzen du.
	 */
	@Test
	public void testFullConstructor() {
		ArrayList<Jaurdunaldia> jaurdunaldiak = new ArrayList<>();
		ArrayList<Taldea> taldeak = new ArrayList<>();
		String data = "2024-2025";

		Denboraldia denbBerria = new Denboraldia(data, jaurdunaldiak, taldeak);

		assertEquals("Data ez da ondo esleitu eraikitzailean", data, denbBerria.getData());
		assertNotNull("Eraikitzaileak DenboraldiaP hasieratu behar du NullPointerException saihesteko",
				denbBerria.getDenboraldiaP());
	}
}