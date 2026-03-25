package Testak;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Jaurdunaldia;
import E2.Puntuazioa;
import E2.Denboraldia;

class DenboraldiaTest {
	private Denboraldia denboraldia;

	private ArrayList<Jaurdunaldia> jaurdunaldiak;

	private ArrayList<Puntuazioa> puntuazioak;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		jaurdunaldiak = new ArrayList<>();

		puntuazioak = new ArrayList<>();



		denboraldia = new Denboraldia("2024-2025", jaurdunaldiak, puntuazioak);
	}

	@Test

	void testDenKonstruktoreaetaGetters() {

		assertEquals("2024-2025", denboraldia.getData());

		assertEquals(jaurdunaldiak, denboraldia.getDenboraldia());

		assertEquals(puntuazioak, denboraldia.getDenboraldiaP());

	}



	@Test

	void testSetDataDen() {

		denboraldia.setData("2025-2026");

		assertEquals("2025-2026", denboraldia.getData());

	}



	@Test

	void testSetDenboraldia() {

		ArrayList<Jaurdunaldia> nuevaLista = new ArrayList<>();

		denboraldia.setDenboraldia(nuevaLista);

		assertEquals(nuevaLista, denboraldia.getDenboraldia());

	}



	@Test

	void testSetDenboraldiaP() {

		ArrayList<Puntuazioa> nuevaListaP = new ArrayList<>();

		denboraldia.setDenboraldiaP(nuevaListaP);

		assertEquals(nuevaListaP, denboraldia.getDenboraldiaP());

	}

}
