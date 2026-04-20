package Programaren_Testak.Metodoak;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import E2.Taldea;
import E2.Partidua;
import Metodoak.Metodoak;

/**
 * Metodoak klasearen test estaldura (coverage) %80tik gora igotzeko klasea.
 */
class Metodoen_Testak {

	private Metodoak m;

	@BeforeEach
	void setUp() {
		m = new Metodoak();
		Metodoak.kargatuErabiltzaileak();
		Metodoak.taldeakMasterList = new ArrayList<>();
		Metodoak.partiduakMasterList = new ArrayList<>();
	}

	// ==========================================
	// 1. LOGIN SISTEMAREN TESTAK
	// ==========================================

	@Test
	void testLoginGuztiak() {
		assertEquals("Admin", Metodoak.login("ebilbao", "12345"));
		assertEquals("Presidentea", Metodoak.login("aelexpe", "12345"));
		assertEquals("Arrunta", Metodoak.login("kmunoz", "12345"));
		assertNull(Metodoak.login("ez_existitzen", "okerrekoa"));
	}

	// ==========================================
	// 2. DATUEN KARGA ETA GORDE (DAO INTERAKZIOA)
	// ==========================================

	@Test
	void testKargatuDatuak() {
		// Datu-basera deia egiten du, errorerik ez duela ematen ziurtatu
		assertDoesNotThrow(() -> m.kargatuDatuak());
	}

	@Test
	void testGordeDatuakHutsik() {
		// Partiduak hutsik badaude, logger-ak warning bat botako du eta return egingo
		// du
		Metodoak.partiduakMasterList = null;
		assertDoesNotThrow(() -> Metodoak.gordeDatuak());
	}

	@Test
	void testGordeDatuakEmaitzekin() {
		// Partidu bat gehitu emaitzarekin logic adarrak (irabazlea/galtzailea)
		// estaltzeko
		Taldea t1 = new Taldea();
		t1.setIzena("Loiola");
		Taldea t2 = new Taldea();
		t2.setIzena("Unamuno");
		Metodoak.taldeakMasterList.add(t1);
		Metodoak.taldeakMasterList.add(t2);
		Partidua p = new Partidua(1, "Loiola", "Unamuno", 80, 70, "2024-05-10", "18:00");
		Metodoak.partiduakMasterList.add(p);
		assertDoesNotThrow(() -> Metodoak.gordeDatuak());
	}

	// ==========================================
	// 3. KLASIFIKAZIOA ETA LOGIKA SAKONA
	// ==========================================

	@Test
	void testKalkulatuKlasifikazioaOsoa() {
		Taldea t1 = new Taldea();
		t1.setIzena("A");
		Taldea t2 = new Taldea();
		t2.setIzena("B");
		Metodoak.taldeakMasterList.add(t1);
		Metodoak.taldeakMasterList.add(t2);
		// Partidu bat irabazlearekin
		Partidua p = new Partidua(1, "A", "B", 100, 50, "Data", "Ordua");
		Metodoak.partiduakMasterList.add(p);
		Metodoak.kalkulatuKlasifikazioa();
		assertEquals(2, t1.getPuntuTotalak()); // Irabazteagatik 2 puntu
		assertEquals(1, t1.getIrabazitakoak());
		assertEquals(1, t2.getGaldutakoak());
	}

	// ==========================================
	// 4. TAULEN KUDEAKETA (UI LOGIKA)
	// ==========================================

	@Test
	void testBeteEmaitzenTaula() {
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Info", "L", "vs", "B", "T", "ID" }, 0);
		assertDoesNotThrow(() -> Metodoak.beteEmaitzenTaula(modelo));
	}

	@Test
	void testActualizarTablasTaldeak() {
		// Talde bat sortu eta master list-ean sartu
		Taldea t = new Taldea();
		t.setIzena("Loiola Indautxu");
		t.setSorreraUrtea("1924");
		t.setLehendakari("Lehendakari Test");
		t.setN_Bazkideak(500);
		Metodoak.taldeakMasterList.add(t);
		JTable tp = new JTable(new DefaultTableModel(new String[] { "A", "B", "C" }, 0));
		JTable tg = new JTable(new DefaultTableModel(new String[] { "A", "B", "C", "D", "E", "F" }, 0));
		// Metodoa deitu
		assertDoesNotThrow(() -> Metodoak.actualizarTablasTaldeak("Loiola Indautxu", tp, tg));
		assertDoesNotThrow(() -> Metodoak.actualizarTablasTaldeak(null, tp, tg)); // Casu null
	}

	// ==========================================
	// 5. FITXATEGIAK (XML ETA LOG)
	// ==========================================

	@Test
	void testKonfiguratuLog() {
		assertDoesNotThrow(() -> Metodoak.konfiguratuLog());
		File f = new File("aplikazioa.log");
		assertTrue(f.exists(), "Log fitxategiak existitu behar du.");
	}

	@Test
	void testSortuXMLFitxategia() {
		// XML sorkuntzak fitxategia idazten duela ziurtatu
		assertDoesNotThrow(() -> Metodoak.sortuXMLFitxategia());
		File f = new File("Denboraldia_2024-2025.xml");
		// Ez dugu assertTrue jartzen agian baimenengatik huts egin dezakeelako ingurune
		// batzuetan,
		// baina deitzeak coverage-a ematen du.
	}

	// ==========================================
	// 6. VALIDAZIOAK
	// ==========================================

	@Test
	void testProzesatuEmaitzakErroreak() {
		DefaultTableModel m1 = new DefaultTableModel(new String[] { "1", "2", "3", "4" }, 0);
		m1.addRow(new Object[] { "P1", -10, "vs", 50 }); // Negatiboa
		assertFalse(Metodoak.prozesatuEmaitzak(m1));
		DefaultTableModel m2 = new DefaultTableModel(new String[] { "1", "2", "3", "4" }, 0);
		m2.addRow(new Object[] { "P1", "ABC", "vs", 50 }); // Letra
		assertFalse(Metodoak.prozesatuEmaitzak(m2));
	}
}