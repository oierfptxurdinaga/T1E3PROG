package Programaren_Testak;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import E2.Jokalaria;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Jokalaria klasearen unitate-testak. JUnit 4 liburutegia erabiltzen du (Java
 * 1.8-rekin bateragarria). Ordenazioa (Comparable) eta objektuen berdintasuna
 * (equals) egiaztatzen ditu.
 */
public class Jokalarien_Testak {

	private Jokalaria jokalaria;

	@Before
	public void setUp() {
		// Test bakoitzaren aurretik objektu berri bat sortu (Fixture)
		jokalaria = new Jokalaria("Jon", "Arrieta", "1995-05-12", "12345678Z", "Athletic", 5000000);
	}

	@Test
	public void testConstructorAndGetters() {
		// Atributu guztiak ondo kargatu direla ziurtatu
		assertEquals("Jon", jokalaria.getIzena());
		assertEquals("Arrieta", jokalaria.getAbizena());
		assertEquals("12345678Z", jokalaria.getNAN());
		assertEquals("Athletic", jokalaria.getTaldea());
		assertEquals(5000000, jokalaria.getPrezioa());
	}

	@Test
	public void testCopyConstructor() {
		// Kopia eraikitzailea egiaztatu
		Jokalaria kopia = new Jokalaria(jokalaria);
		assertEquals("Izena berdina izan behar da", jokalaria.getIzena(), kopia.getIzena());
		assertEquals("NANa berdina izan behar da", jokalaria.getNAN(), kopia.getNAN());
		// Memorian objektu desberdinak direla ziurtatu (referentzia desberdina)
		assertNotSame("Objektu desberdinak izan behar dira memorian", jokalaria, kopia);
	}

	@Test
	public void testEqualsAndHashCode() {
		// Zure kodean equals-ek izena eta abizena bakarrik begiratzen baditu:
		Jokalaria jokalariBerdina = new Jokalaria("Jon", "Arrieta", "1995-05-12", "99999999X", "Beste bat", 0);

		assertEquals("Izena eta abizena berdinak badira, jokalariak berdinak dira", jokalaria, jokalariBerdina);
		assertEquals("Equals badira, HashCode-ak berdinak izan behar dira", jokalaria.hashCode(),
				jokalariBerdina.hashCode());
	}

	@Test
	public void testCompareTo() {
		Jokalaria jokalariLehenago = new Jokalaria("Aitor", "Arrieta", "1990-01-01", "111", "T", 0);
		Jokalaria jokalariGeroago = new Jokalaria("Zuriñe", "Zabala", "1990-01-01", "222", "T", 0);

		// Abizen berdinarekin, izenak agintzen du (Aitor < Jon) -> emaitza negatiboa
		assertTrue("Aitor Jon baino lehenago doa", jokalariLehenago.compareTo(jokalaria) < 0);
		// Abizen desberdinarekin, abizenak agintzen du (Zabala > Arrieta) -> emaitza
		// positiboa
		assertTrue("Zabala Arrieta baino geroago doa", jokalariGeroago.compareTo(jokalaria) > 0);
	}

	@Test
	public void testSortList() {
		List<Jokalaria> lista = new ArrayList<Jokalaria>();
		lista.add(new Jokalaria("Koldo", "Zabala", "1990", "1", "T", 0));
		lista.add(new Jokalaria("Ander", "Arrieta", "1990", "2", "T", 0));
		lista.add(new Jokalaria("Asier", "Arrieta", "1990", "3", "T", 0));

		Collections.sort(lista);

		// Ordena zuzena: Arrieta Ander -> Arrieta Asier -> Zabala Koldo
		assertEquals("Ander", lista.get(0).getIzena());
		assertEquals("Asier", lista.get(1).getIzena());
		assertEquals("Koldo", lista.get(2).getIzena());
	}
}