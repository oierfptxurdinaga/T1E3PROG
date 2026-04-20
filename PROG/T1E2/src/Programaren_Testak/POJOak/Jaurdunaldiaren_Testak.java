package Programaren_Testak.POJOak;

import static org.junit.Assert.*;
import org.junit.Test;
import E2.Jaurdunaldia;
import E2.Partidua;
import java.util.ArrayList;

public class Jaurdunaldiaren_Testak {

	@Test
	public void testKonstruktoreaEtaNullLogika() {
		// Parametroekin sortu baina taldeak null utzita
		Jaurdunaldia j = new Jaurdunaldia(1, 10, null, null);

		// Zure POJOak "---" itzuli behar du null denean
		assertEquals("Irabazleak '---' izan behar du null bada", "---", j.getTaldeIrabazlea());
		assertEquals("Galdu duenak '---' izan behar du null bada", "---", j.getTaldeGaldu());
	}

	@Test
	public void testPartiduakKudeatu() {
		Jaurdunaldia jaurdunaldia = new Jaurdunaldia();
		ArrayList<Partidua> lista = new ArrayList<>();

		// Partidua gehitu (zure Partidua klasearen eraikitzailea erabiliz)
		lista.add(new Partidua(1, "Lakers", "Celtics", 100, 90, "2026-01-01", "20:00"));

		jaurdunaldia.setPartiduak(lista);

		// Orain getPartiduak() badaukazu, honek ez du errorerik emango
		assertNotNull("Zerrenda ezin da null izan", jaurdunaldia.getPartiduak());
		assertEquals("Zerrendak elementu bat izan behar du", 1, jaurdunaldia.getPartiduak().size());
		assertEquals("Talde lokala egiaztatu", "Lakers", jaurdunaldia.getPartiduak().get(0).getTaldeLokala());
	}
}