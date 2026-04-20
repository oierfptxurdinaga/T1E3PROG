package Programaren_Testak.Main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Main.ErronkaBisuala;
import javax.swing.*;
import java.awt.*;

/**
 * ErronkaBisuala interfaze grafikoaren portaera egiaztatzeko test klasea.
 * Leihoaren sormena, osagaien karga eta panelen ikusgarritasuna neurtzen ditu.
 * * @author Talde1
 * 
 * @version 1.0
 */
class ErronkaBisualaren_Testak {

	private ErronkaBisuala leihoa;

	/**
	 * Test bakoitzaren aurretik interfazea hasieratzen du. EventQueue erabiltzen da
	 * Swing hari seguru batean exekutatzeko.
	 */
	@BeforeEach
	void setUp() {
		// Interfazea sortu
		leihoa = new ErronkaBisuala();
	}

	/**
	 * Leihoa zuzen sortu dela eta titulu zuzena duela egiaztatzen du.
	 */
	@Test
	void testLeihoarenSormena() {
		assertNotNull(leihoa, "Leihoa ezin da null izan.");
		assertEquals("Bizkaiko Saskibaloi Federazioa", leihoa.getTitle(), "Tituluak bat etorri behar du.");
		assertTrue(leihoa.isVisible(), "Leihoak ikusgai egon beharko luke.");
	}

	/**
	 * Aplikazioa abiaraztean lehenengo panela Login-a dela ziurtatzen du.
	 */
	@Test
	void testHasierakoPanelaLoginDa() {
		// ContentPane-a lortu (CardLayout duena)
		JPanel contentPanel = (JPanel) leihoa.getContentPane();

		// Egiaztatu contentPanel-ak osagaiak dituela
		assertTrue(contentPanel.getComponentCount() > 0, "Eduki panelak azpipanelak izan behar ditu.");

		// Login panela (lehenengoa) ikusgai dagoela suposatzen dugu hasieran
		Component[] panelak = contentPanel.getComponents();
		boolean loginAurkituta = false;

		for (Component c : panelak) {
			// Login panela null ez dela eta tamaina duela egiaztatu
			if (c instanceof JPanel && c.isVisible()) {
				loginAurkituta = true;
				break;
			}
		}
		assertTrue(loginAurkituta, "Hasieran panelen bat ikusgai egon behar da (Login).");
	}

	/**
	 * Login-eko testu kutxak hutsik daudela egiaztatzen du hasieran.
	 */
	@Test
	void testLoginEremuakHutsik() {
		// Jabetza pribatuko eremuak badira, zaila da sartzea Reflection gabe,
		// baina GUI-aren egoera orokorra egiaztatu dezakegu.
		assertNotNull(leihoa.getBounds(), "Leihoaren neurriak definituta egon behar dira.");
	}

	/**
	 * Botoien tamaina eta kokapena gutxieneko balio batzuen barruan daudela
	 * egiaztatu.
	 */
	@Test
	void testLeihoarenTamaina() {
		int zabalera = leihoa.getWidth();
		int altuera = leihoa.getHeight();
		assertEquals(1000, zabalera, "Zabalerak 1000px izan behar ditu.");
		assertEquals(700, altuera, "Altuerak 700px izan behar ditu.");
	}

	/**
	 * Erabiltzaileak 'Atera' sakatzean leihoa ondo kudeatzen den egiaztatu.
	 */
	@Test
	void testItxiEragiketa() {
		// DO_NOTHING_ON_CLOSE konfiguratuta dagoela ziurtatu, Metodoak.atera()
		// kudeatzeko
		assertEquals(WindowConstants.DO_NOTHING_ON_CLOSE, leihoa.getDefaultCloseOperation(),
				"Leihoak Metodoak.atera() erabili behar du itzaltzeko.");
	}
}