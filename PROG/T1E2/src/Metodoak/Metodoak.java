package Metodoak;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.logging.*;
import java.io.IOException;
import DAO.JaurdunaldiDao;
import DAO.KlasifikazioaDao;
import DAO.PartiduaDao;
import DAO.TaldeDao;
import E2.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

/**
 * Aplikazioaren negozio-logika eta datuen kudeaketa zentralizatzen dituen
 * klasea. Login sistemaz, datu-basearen sinkronizazioaz eta fitxategien
 * esportazioaz arduratzen da. * @author Talde1
 * 
 * @version 1.0
 */
public class Metodoak {

	private static List<ErabiltzaileMota> erabiltzaileaklist;
	public static ArrayList<Taldea> taldeakMasterList = new ArrayList<>();
	public static ArrayList<Partidua> partiduakMasterList = new ArrayList<>();

	// DAO objektuak datu-basearekin komunikatzeko
	private static TaldeDao taldeDao = new TaldeDao();
	private static JaurdunaldiDao jaurdunaldiDao = new JaurdunaldiDao();
	private static PartiduaDao partiduaDao = new PartiduaDao();

	// Logger-a aplikazioaren gertaerak erregistratzeko
	private static final Logger logger = Logger.getLogger(Metodoak.class.getName());

	/**
	 * Datu-basetik talde eta partidu guztiak kargatzen ditu ArrayList-etara.
	 * Aplikazioa hasieratzean exekutatu behar da.
	 */
	public void kargatuDatuak() {
		try {
			taldeakMasterList = taldeDao.kargatuTaldeak();
			partiduakMasterList = partiduaDao.kargatuPartiduak();
			System.out.println("Datuak kargatuta: " + taldeakMasterList.size() + " talde eta "
					+ partiduakMasterList.size() + " partidu.");
		} catch (Exception e) {
			System.out.println("Errorea datuak kargatzean: " + e.getMessage());
		}
	}

	/**
	 * Memorian dauden aldaketak (emaitzak) datu-basean gordetzen ditu. Sailkapen
	 * orokorra automatikoki birkalkulatzen du.
	 */
	public static void gordeDatuak() {
		KlasifikazioaDao kDao = new KlasifikazioaDao();
		if (partiduakMasterList == null || partiduakMasterList.isEmpty()) {
			logger.warning("Gorde nahi izan da baina zerrenda hutsik dago.");
			return;
		}

		boolean aldaketakDauden = false;
		try {
			for (Partidua p : partiduakMasterList) {
				// Emaitzarik badago, partidua prozesatu
				if (p.getResultLokala() > 0 || p.getResulBisitari() > 0) {
					aldaketakDauden = true;
					String irabazlea = "Berdinketa";
					String galtzailea = "Berdinketa";
					if (p.getResultLokala() > p.getResulBisitari()) {
						irabazlea = p.getTaldeLokala();
						galtzailea = p.getTaldeBisitari();
					} else if (p.getResulBisitari() > p.getResultLokala()) {
						irabazlea = p.getTaldeBisitari();
						galtzailea = p.getTaldeLokala();
					}
					// DBan eguneratu
					partiduaDao.modifyPartiduaEtaJaurdunaldi(p.getId_Par(), p.getResultLokala(), p.getResulBisitari(),
							irabazlea, galtzailea);
				}
			}

			if (aldaketakDauden) {
				kalkulatuKlasifikazioa();
				for (Taldea t : taldeakMasterList) {
					kDao.modifyKlasifikazioa(t);
				}
				logger.info("Datu-basea ondo eguneratu da partidu eta sailkapen berriekin.");
				JOptionPane.showMessageDialog(null, "Datu-basea ondo eguneratu da.");
			}
		} catch (Exception e) {
			logger.severe("ERROREA datuak gordetzean: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Errore bat gertatu da gordetzean. Begiratu aplikazioa.log");
		}
	}

	/**
	 * Taldeen estatistikak (puntuak, irabazitakoak, etab.) kalkulatzen ditu.
	 * Zerrenda puntuen eta sartutako tantoen arabera ordenatzen du.
	 */
	public static void kalkulatuKlasifikazioa() {
		// Taldeen datuak resetatu kalkulu berria egiteko
		for (Taldea t : taldeakMasterList) {
			t.setPuntuTotalak(0);
			t.setIrabazitakoak(0);
			t.setGaldutakoak(0);
			t.setPuntuakF(0);
			t.setPuntuakC(0);
		}
		for (Partidua p : partiduakMasterList) {
			Taldea local = null;
			Taldea visit = null;
			for (Taldea t : taldeakMasterList) {
				if (t.getIzena().equals(p.getTaldeLokala()))
					local = t;
				if (t.getIzena().equals(p.getTaldeBisitari()))
					visit = t;
			}
			if (local != null && visit != null) {
				local.setPuntuakF(local.getPuntuakF() + p.getResultLokala());
				local.setPuntuakC(local.getPuntuakC() + p.getResulBisitari());
				visit.setPuntuakF(visit.getPuntuakF() + p.getResulBisitari());
				visit.setPuntuakC(visit.getPuntuakC() + p.getResultLokala());
				if (p.getResultLokala() > p.getResulBisitari()) {
					local.setPuntuTotalak(local.getPuntuTotalak() + 2);
					local.setIrabazitakoak(local.getIrabazitakoak() + 1);
					visit.setGaldutakoak(visit.getGaldutakoak() + 1);
				} else if (p.getResulBisitari() > p.getResultLokala()) {
					visit.setPuntuTotalak(visit.getPuntuTotalak() + 2);
					visit.setIrabazitakoak(visit.getIrabazitakoak() + 1);
					local.setGaldutakoak(local.getGaldutakoak() + 1);
				}
			}
		}
		// Ordenatu: Puntuak lehenengo, gero sartutako puntuak (descending)
		taldeakMasterList.sort((t1, t2) -> {
			int res = Integer.compare(t2.getPuntuTotalak(), t1.getPuntuTotalak());
			if (res == 0)
				res = Integer.compare(t2.getPuntuakF(), t1.getPuntuakF());
			return res;
		});
	}

	/**
	 * Erabiltzaileak taulan sartutako emaitzak balidatzen ditu. * @param
	 * modeloEmaitzak Taularen modeloa datuak irakurtzeko.
	 * 
	 * @return true datuak zuzenak badira, false formatu okerra badago.
	 */
	public static boolean prozesatuEmaitzak(DefaultTableModel modeloEmaitzak) {
		for (int i = 0; i < modeloEmaitzak.getRowCount(); i++) {
			try {
				int pLok = Integer.parseInt(modeloEmaitzak.getValueAt(i, 1).toString());
				int pBis = Integer.parseInt(modeloEmaitzak.getValueAt(i, 3).toString());
				if (pLok < 0 || pBis < 0) {
					JOptionPane.showMessageDialog(null, "ERROREA " + (i + 1) + ". lerroan: Ezin dira negatiboak izan.");
					return false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERROREA " + (i + 1) + ". lerroan: Formatu okerra.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Sistema erabiltzeko baimena duten erabiltzaileak memorian kargatzen ditu.
	 */
	public static void kargatuErabiltzaileak() {
		erabiltzaileaklist = new ArrayList<>();
		erabiltzaileaklist.add(new Administradorea("Eder", "Bilbao", "12345678A", "ebilbao", "12345"));
		erabiltzaileaklist.add(new Presidentea("Aratz", "Elexpe", "12345678B", "aelexpe", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Kirian", "Munoz", "12345678C", "kmunoz", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Aratz", "Barcena", "12345678D", "abarcena", "12345"));
	}

	/**
	 * Erabiltzailearen kredentzialak egiaztatzen ditu. * @param erabiltzailea
	 * Erabiltzaile izena.
	 * 
	 * @param pasahitza Sartutako pasahitza.
	 * @return Erabiltzailearen baimen-rola edo null okerra bada.
	 */
	public static String login(String erabiltzailea, String pasahitza) {
		if (erabiltzaileaklist == null) {
			kargatuErabiltzaileak();
			logger.info("Erabiltzaileen zerrenda kargatu da.");
		}
		for (ErabiltzaileMota e : erabiltzaileaklist) {
			if (e.getErabiltzailea().equals(erabiltzailea) && e.getPasahitza().equals(pasahitza)) {
				logger.info("LOGIN ZUZENA: " + erabiltzailea + " sartu da.");
				return e.baimenak();
			}
		}
		logger.warning("LOGIN OKERRA: Saiakera fallua erabiltzaile honekin: " + erabiltzailea);
		return null;
	}

	/**
	 * Aplikazioa ixteko prozesua kudeatzen du, gordetzeko aukera emanez.
	 */
	public static void atera() {
		int erantzuna = JOptionPane.showConfirmDialog(null, "Atera baino lehen, gorde nahi duzu?", "Berrespena",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (erantzuna == JOptionPane.YES_OPTION) {
			gordeDatuak();
			System.exit(0);
		} else if (erantzuna == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Interfaze grafikoko emaitzen taula datu-baseko informazioarekin betetzen du.
	 * * @param modeloEmaitzak Eguneratu nahi den taularen modeloa.
	 */
	public static void beteEmaitzenTaula(DefaultTableModel modeloEmaitzak) {
		ArrayList<Jaurdunaldia> zerrendaJornadas = jaurdunaldiDao.kargatuJaurdunaldiak();
		ArrayList<Partidua> listaPartidos = partiduaDao.kargatuPartiduak();
		modeloEmaitzak.setRowCount(0);
		for (Jaurdunaldia j : zerrendaJornadas) {
			int puntosLoc = 0, puntosVis = 0;
			String taldeLoc = "Ezezaguna", taldeVis = "Ezezaguna";
			for (Partidua p : listaPartidos) {
				if (j.getIdPar() == p.getId_Par()) {
					puntosLoc = p.getResultLokala();
					puntosVis = p.getResulBisitari();
					taldeLoc = p.getTaldeLokala();
					taldeVis = p.getTaldeBisitari();
					break;
				}
			}
			Object[] fila = { "J." + j.getIdJaurdu() + " - " + taldeLoc, puntosLoc, "vs", puntosVis, taldeVis,
					j.getIdPar() };
			modeloEmaitzak.addRow(fila);
		}
	}

	/**
	 * Hautatutako taldearen xehetasunak eta jokalariak kargatzen ditu UI-ko
	 * tauletan. * @param aukeratuta Taldearen izena.
	 * 
	 * @param tablaPequena Datu orokorren taula.
	 * @param tablaGrande  Jokalarien zerrendaren taula.
	 */
	public static void actualizarTablasTaldeak(String aukeratuta, JTable tablaPequena, JTable tablaGrande) {
		if (aukeratuta == null)
			return;
		Taldea t = null;
		for (Taldea taldea : taldeakMasterList) {
			if (taldea.getIzena().equals(aukeratuta)) {
				t = taldea;
				break;
			}
		}
		if (t != null) {
			DefaultTableModel modeloTxikia = (DefaultTableModel) tablaPequena.getModel();
			modeloTxikia.setRowCount(0);
			modeloTxikia.addRow(new Object[] { t.getSorreraUrtea(), t.getLehendakari(), t.getN_Bazkideak() });
			DAO.JokalariaDao jDao = new DAO.JokalariaDao();
			ArrayList<Jokalaria> jokalariak = jDao.kargatuJokalariakTaldeka(aukeratuta);
			DefaultTableModel modeloHandia = (DefaultTableModel) tablaGrande.getModel();
			modeloHandia.setRowCount(0);
			for (Jokalaria j : jokalariak) {
				modeloHandia.addRow(new Object[] { j.getIzena(), j.getAbizena(), j.getJaiotzeData(), j.getNAN(),
						t.getIzena(), j.getPrezioa() });
			}
		}
	}
	/**
	 * Denboraldiko datu guztiak XML fitxategi batean esportatzen ditu JAXB
	 * erabiliz.
	 */
	public static void sortuXMLFitxategia() {
		try {
			ArrayList<Taldea> taldeak = taldeDao.kargatuTaldeak();
			ArrayList<Jaurdunaldia> jaurdunaldiak = jaurdunaldiDao.kargatuJaurdunaldiak();
			ArrayList<Partidua> partiduak = partiduaDao.kargatuPartiduak();
			if (jaurdunaldiak != null && partiduak != null) {
				for (Jaurdunaldia j : jaurdunaldiak) {
					ArrayList<Partidua> partiduakJornada = new ArrayList<>();
					for (Partidua p : partiduak) {
						if (p.getId_Par() == j.getIdPar())
							partiduakJornada.add(p);
					}
					j.setPartiduak(partiduakJornada);
				}
			}
			Denboraldia den = new Denboraldia("2024-2025", jaurdunaldiak, new ArrayList<>());
			den.setTaldeak(taldeak);
			JAXBContext context = JAXBContext.newInstance(Denboraldia.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			File f = new File("Denboraldia_2024-2025.xml");
			marshaller.marshal(den, f);
			logger.info("XML GENERAZIOA: Fitxategia ondo sortu da: " + f.getName());
			JOptionPane.showMessageDialog(null, "XML fitxategia ondo sortu da hemen:\n" + f.getAbsolutePath());
		} catch (Exception e) {
			logger.severe("ERROREA XML-A SORTZEAN: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Logger sistemaren konfigurazioa: fitxategiaren izena eta formatua zehazten
	 * ditu.
	 */
	public static void konfiguratuLog() {
		try {
			FileHandler fh = new FileHandler("aplikazioa.log", true);
			logger.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
			logger.setUseParentHandlers(false);
		} catch (IOException | SecurityException e) {
			System.err.println("Ezin izan da Log fitxategia konfiguratu: " + e.getMessage());
		}
	}
}