package Programaren_Testak.POJOak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import E2.Puntuazioa;
import E2.Taldea;

/**
 * Puntuazioa klasearen unitate-testak. Ligako sailkapen orokorraren zerrenda
 * ondo kudeatzen dela ziurtatzen du. JUnit 4 liburutegia erabiltzen du (Java
 * 1.8-rekin bateragarria).
 */
public class Puntuazioaren_Testak {

	private Puntuazioa puntuazioa;
	private ArrayList<Taldea> taldeZerrenda;

	/**
	 * Test bakoitzaren aurretik fixture-a (datu multzoa) prestatzen dugu.
	 */
	@Before
	public void setUp() {
		// Test bakoitzaren aurretik zerrenda bat sortzen dugu objektua hasieratzeko
		taldeZerrenda = new ArrayList<Taldea>();
		puntuazioa = new Puntuazioa(taldeZerrenda);
	}

	/**
	 * Getter eta Setter metodoak ondo funtzionatzen dutela egiaztatzen du.
	 */
	@Test
	public void testGetEtaSetPuntuazioa() {
		// Zerrenda berri bat sortu eta esleitu daitekeela frogatu
		ArrayList<Taldea> berria = new ArrayList<Taldea>();

		// Talde bat gehituko dugu zerrenda hutsik ez egoteko frogako
		berria.add(new Taldea());

		puntuazioa.setPuntuazioa(berria);

		// Emaitza ez dela nulua eta itzulitako zerrenda zuzena dela ziurtatu
		assertNotNull("Puntuazio zerrenda ezin da nulua izan", puntuazioa.getPuntuazioa());
		assertEquals("Zerrendak berdinak izan behar dira", berria, puntuazioa.getPuntuazioa());
		assertEquals("Zerrendaren tamaina zuzena izan behar da", 1, puntuazioa.getPuntuazioa().size());
	}
}