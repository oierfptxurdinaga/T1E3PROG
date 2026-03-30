package Metodoak;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
 * Metodoak klaseak aplikazioaren negozio-logika eta datuen kudeaketa
 * zentralizatzen ditu.
 */
public class Metodoak {

	private static List<ErabiltzaileMota> erabiltzaileaklist;
	public static ArrayList<Taldea> taldeakMasterList = new ArrayList<>();
	public static ArrayList<Partidua> partiduakMasterList = new ArrayList<>();

	// DAOak instantziatu behar dira erabili aurretik
	private static TaldeDao taldeDao = new TaldeDao();
	private static JaurdunaldiDao jaurdunaldiDao = new JaurdunaldiDao();
	private static PartiduaDao partiduaDao = new PartiduaDao();

	// Logger objektua sortu
	private static final Logger logger = Logger.getLogger(Metodoak.class.getName());

	/**
	 * Aplikazioa abiaraztean DBko datu guztiak ArrayList-etara ekartzen ditu.
	 */
	public void kargatuDatuak() {
		try {
			// Talde guztien zerrenda kargatu datu-basetik (taldeDao erabiliz)
			taldeakMasterList = taldeDao.kargatuTaldeak();
			// Partidu guztien zerrenda kargatu (partiduaDao erabiliz)
			// Hauek izango dira aplikazio osoan zehar erabiliko diren datu-iturri nagusiak
			partiduakMasterList = partiduaDao.kargatuPartiduak();
			// Kontsolan mezu bat erakutsi kargatutako objektu kopuruarekin, dena ondo
			// dagoela ziurtatzeko
			System.out.println("Datuak kargatuta: " + taldeakMasterList.size() + " talde eta "
					+ partiduakMasterList.size() + " partidu.");
		} catch (Exception e) {
			// Kargarakoan erroreren bat egongo balitz (adibidez, DB konexio gabe), mezua
			// inprimatu
			System.out.println("Errorea datuak kargatzean: " + e.getMessage());
		}
	}

	/**
	 * Memorian (ArrayList) dauden aldaketa guztiak DBra bidaltzen ditu ordena
	 * zuzenean.
	 */
	public static void gordeDatuak() {
		KlasifikazioaDao kDao = new KlasifikazioaDao();
		// Egiaztatu zerrenda kargatuta dagoela
		if (partiduakMasterList == null || partiduakMasterList.isEmpty()) {
			logger.warning("Gorde nahi izan da baina zerrenda hutsik dago.");
			return;
		}
		boolean aldaketakDauden = false;
		try {
			// Partiduak eta Jaurdunaldiak prozesatu
			for (Partidua p : partiduakMasterList) {
				// Soilik prozesatu emaitzaren bat sartu bada
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
					// Datu-basean aldaketa egin
					partiduaDao.modifyPartiduaEtaJaurdunaldi(p.getId_Par(), p.getResultLokala(), p.getResulBisitari(),
							irabazlea, galtzailea);
				}
			}
			// Aldaketak egon badira, klasifikazioa kalkulatu eta DBra bidali
			if (aldaketakDauden) {
				kalkulatuKlasifikazioa();
				for (Taldea t : taldeakMasterList) {
					kDao.modifyKlasifikazioa(t);
				}
				// LOG: Guardado exitoso registrado en el archivo
				logger.info("Datu-basea ondo eguneratu da partidu eta sailkapen berriekin.");
				JOptionPane.showMessageDialog(null, "Datu-basea ondo eguneratu da.");
			}
		} catch (Exception e) {
			// LOG: Error crítico registrado con detalle
			logger.severe("ERROREA datuak gordetzean: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Errore bat gertatu da gordetzean. Begiratu aplikazioa.log");
		}
	}

	public static void kalkulatuKlasifikazioa() {
		// 1. Taldeen datuak resetatu memorian: Kalkulua zerotik hasteko (garbiketa)
		for (Taldea t : taldeakMasterList) {
			t.setPuntuTotalak(0);
			t.setIrabazitakoak(0);
			t.setGaldutakoak(0);
			t.setPuntuakF(0);
			t.setPuntuakC(0);
		}
		// 2. Partidu bakoitzeko emaitzak prozesatu (memoriako zerrenda erabiliz)
		for (Partidua p : partiduakMasterList) {
			Taldea local = null;
			Taldea visit = null;
			// Partiduko talde bakoitzaren objektua bilatu master list-ean
			for (Taldea t : taldeakMasterList) {
				if (t.getIzena().equals(p.getTaldeLokala()))
					local = t;
				if (t.getIzena().equals(p.getTaldeBisitari()))
					visit = t;
			}
			// Bi taldeak aurkitu badira, puntuak (sartutakoak eta jasotakoak) pilatu
			if (local != null && visit != null) {
				local.setPuntuakF(local.getPuntuakF() + p.getResultLokala());
				local.setPuntuakC(local.getPuntuakC() + p.getResulBisitari());
				visit.setPuntuakF(visit.getPuntuakF() + p.getResulBisitari());
				visit.setPuntuakC(visit.getPuntuakC() + p.getResultLokala());
				// Irabazlea zehaztu eta puntuak esleitu (adib. saskibaloiko arauak: 2 puntu
				// irabazteagatik)
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
		// 3. Klasifikazioa ordenatu: Lehenik puntuengatik, eta berdinketa badago,
		// sartutako puntuen arabera
		taldeakMasterList.sort((t1, t2) -> {
			// t2 eta t1 alderantziz jarrita (descending), handienetik txikienera agertzeko
			int res = Integer.compare(t2.getPuntuTotalak(), t1.getPuntuTotalak());
			if (res == 0) // Puntuetan berdinketa badago, 'PuntuakF' (favor) konparatu
				res = Integer.compare(t2.getPuntuakF(), t1.getPuntuakF());
			return res;
		});
	}

	/**
	 * Taula (UI) eta ArrayList-a sinkronizatzen ditu gorde aurretik.
	 */
	public static boolean prozesatuEmaitzak(DefaultTableModel modeloEmaitzak) {
		for (int i = 0; i < modeloEmaitzak.getRowCount(); i++) {
			try {
				String pLokStr = modeloEmaitzak.getValueAt(i, 1).toString();
				String pBisStr = modeloEmaitzak.getValueAt(i, 3).toString();
				int pLok = Integer.parseInt(pLokStr);
				int pBis = Integer.parseInt(pBisStr);
				if (pLok < 0 || pBis < 0) {
					JOptionPane.showMessageDialog(null, "ERROREA " + (i + 1) + ". lerroan: Ezin dira negatiboak izan.");
					return false; // <--- GELDITU ETA FALTSUA BUELTATU
				}
				// Hemen zure logika (zerrenda eguneratu...)
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERROREA " + (i + 1) + ". lerroan: Formatu okerra.");
				return false; // <--- GELDITU ETA FALTSUA BUELTATU
			}
		}
		// Dena ondo badoa bakarrik iritsiko da hona
		return true;
	}

	public static void kargatuErabiltzaileak() {
		erabiltzaileaklist = new ArrayList<>();
		// Erabiltzaile ezberdinak sortu eta zerrendan gehitu
		// Polimorfismoa: Administradorea, Presidentea eta ErabiltzaileNormala mota
		// berekoak dira (ErabiltzaileMota)
		erabiltzaileaklist.add(new Administradorea("Eder", "Bilbao", "12345678A", "ebilbao", "12345"));
		erabiltzaileaklist.add(new Presidentea("Aratz", "Elexpe", "12345678B", "aelexpe", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Kirian", "Munoz", "12345678C", "kmunoz", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Aratz", "Barcena", "12345678D", "abarcena", "12345"));
	}

	public static String login(String erabiltzailea, String pasahitza) {
		// Zerrenda hutsik badago, erabiltzaileak kargatu
		if (erabiltzaileaklist == null) {
			kargatuErabiltzaileak();
			// LOG: Zerrenda kargatu dela erregistratu
			logger.info("Erabiltzaileen zerrenda kargatu da.");
		}
		// Erabiltzaile bakoitza egiaztatu izena eta pasahitza bat datozen ikusteko
		for (ErabiltzaileMota e : erabiltzaileaklist) {
			if (e.getErabiltzailea().equals(erabiltzailea) && e.getPasahitza().equals(pasahitza)) {
				// LOG: Login arrakastatsua. Erabiltzailea eta bere rola gordetzen dugu.
				logger.info("LOGIN ZUZENA: " + erabiltzailea + " sartu da (" + e.baimenak() + " baimenarekin).");
				// Login zuzena bada, erabiltzaile motari dagokion baimen-rola itzuli
				return e.baimenak();
			}
		}
		// Hona iristen bada, ez da erabiltzailerik aurkitu kredentzial horiekin
		// LOG: Kontuz! Saio-hasiera okerra erregistratu segurtasuna zainduz.
		logger.warning("LOGIN OKERRA: Erabiltzaile izen edo pasahitz okerra honekin: " + erabiltzailea);
		// Aurkitzen ez bada, null itzuli (login okerra)
		return null;
	}

	public static void atera() {
		// Berrespen leiho bat erakutsi hiru aukerarekin: Bai, Ez, Utzi
		int erantzuna = JOptionPane.showConfirmDialog(null, "Atera baino lehen, gorde nahi duzu?", "Berrespena",
				JOptionPane.YES_NO_CANCEL_OPTION);
		// "BAI" sakatzean, datu guztiak datu-basean gorde eta programa itxi
		if (erantzuna == JOptionPane.YES_OPTION) {
			gordeDatuak();
			System.out.println("Programa itxi egin duzu. Agurrr!!");
			System.exit(0);
			// "EZ" sakatzean, programa zuzenean itxi ezer gorde gabe
		} else if (erantzuna == JOptionPane.NO_OPTION) {
			System.out.println("Programa itxi egin duzu. Agurrr!!");
			System.exit(0);
		}
		// "CANCEL" sakatuz gero, ez da ezer egiten eta programak jarraitu egiten du
	}

	public static void beteEmaitzenTaula(DefaultTableModel modeloEmaitzak) {
		// DAOak inizializatu (ziurtatzeko ez direla null eta datu-baserako bidea prest
		// dagoela)
		if (jaurdunaldiDao == null)
			jaurdunaldiDao = new JaurdunaldiDao();
		if (partiduaDao == null)
			partiduaDao = new PartiduaDao();
		// Datu-basetik (DB) informazio eguneratua kargatu zerrendetan
		ArrayList<Jaurdunaldia> zerrendaJornadas = jaurdunaldiDao.kargatuJaurdunaldiak();
		ArrayList<Partidua> listaPartidos = partiduaDao.kargatuPartiduak();
		// Taularen modeloa garbitu lerro zaharrak kentzeko
		modeloEmaitzak.setRowCount(0);
		// Jaurdunaldi bakoitzeko, dagokion Partidua bilatzen dugu 'Id_Par' bidez
		// (Lotura/Join logikoa)
		for (Jaurdunaldia j : zerrendaJornadas) {
			int puntosLoc = 0;
			int puntosVis = 0;
			String taldeLoc = "Ezezaguna";
			String taldeVis = "Ezezaguna";
			for (Partidua p : listaPartidos) {
				// IDak bat egiten badu, partiduko datuak (emaitzak eta taldeen izenak) hartzen
				// ditugu
				if (j.getIdPar() == p.getId_Par()) {
					puntosLoc = p.getResultLokala();
					puntosVis = p.getResulBisitari();
					taldeLoc = p.getTaldeLokala();
					taldeVis = p.getTaldeBisitari();
					break; // Partidua aurkituta, barne-begiztatik irten
				}
			}
			// Taulan errenkada berria sortu informazio guztiarekin
			// Zutabeak: 0: Deskribapena, 1: Lokala, 2: "vs", 3: Bisitaria, 4: Izena, 5: ID
			// (ezkutua)
			Object[] fila = { "J." + j.getIdJaurdu() + " - " + taldeLoc, puntosLoc, "vs", puntosVis, taldeVis,
					j.getIdPar() // ID ezkutua oso garrantzitsua da gero datuak gorde ahal izateko
			};
			modeloEmaitzak.addRow(fila);
		}
	}

	/**
	 * Hautatutako taldearen informazio orokorra eta bere jokalariak tauletan
	 * kargatzen ditu. * @param aukeratuta ComboBox-ean hautatutako taldearen izena.
	 * 
	 * @param tablaPequena Goiko taula (Sorrera, Lehendakari eta Bazkide kopurua).
	 * @param tablaGrande  Beheko taula (Jokalarien zerrenda osoa).
	 */
	public static void actualizarTablasTaldeak(String aukeratuta, JTable tablaPequena, JTable tablaGrande) {
		// Ziurtatu hautaketa ez dela null erroreak saihesteko
		if (aukeratuta == null)
			return;

		Taldea t = null;
		// MasterList-ean bilatu izen bera duen taldea objektu osoa lortzeko
		for (Taldea taldea : taldeakMasterList) {
			if (taldea.getIzena().equals(aukeratuta)) {
				t = taldea;
				break;
			}
		}

		if (t != null) {
			// GOIKO TAULA TXIKIA: Taldearen datu administratiboak
			DefaultTableModel modeloTxikia = (DefaultTableModel) tablaPequena.getModel();
			modeloTxikia.setRowCount(0); // Taula garbitu informazio zaharra kentzeko
			// Taldea klaseko getter-ak erabili: SorreraUrtea, Lehendakari eta N_Bazkideak
			// Oharra: Datu hauek 0 edo hutsik badatoz, TaldeDao-n karga zuzendu behar da
			Object[] zerrendaTaldea = { t.getSorreraUrtea(), t.getLehendakari(), t.getN_Bazkideak() };
			modeloTxikia.addRow(zerrendaTaldea);
			// BEHEKO TAULA HANDIA: Jokalarien zerrenda eguneratua (MySQL-tik)
			DAO.JokalariaDao jDao = new DAO.JokalariaDao();
			ArrayList<Jokalaria> jokalariak = jDao.kargatuJokalariakTaldeka(aukeratuta);
			DefaultTableModel modeloHandia = (DefaultTableModel) tablaGrande.getModel();
			modeloHandia.setRowCount(0); // Jokalarien taula garbitu
			for (Jokalaria j : jokalariak) {
				// Jokalari bakoitzaren lerroa gehitu (Izena, Abizena, Jaiotza...)
				modeloHandia.addRow(new Object[] { j.getIzena(), j.getAbizena(), j.getJaiotzeData(), j.getNAN(),
						t.getIzena(), j.getPrezioa() });
			}
		}
	}

	public static void sortuXMLFitxategia() {
		try {
			// DAO-ak eta datuak kargatu
			TaldeDao taldeDao = new TaldeDao();
			JaurdunaldiDao jaurDao = new JaurdunaldiDao();
			PartiduaDao parDao = new PartiduaDao();
			ArrayList<Taldea> taldeak = taldeDao.kargatuTaldeak();
			ArrayList<Jaurdunaldia> jaurdunaldiak = jaurDao.kargatuJaurdunaldiak();
			ArrayList<Partidua> partiduak = parDao.kargatuPartiduak();
			// Partiduak jaurdunaldiekin lotu (Logika nagusia)
			if (jaurdunaldiak != null && partiduak != null) {
				for (Jaurdunaldia j : jaurdunaldiak) {
					ArrayList<Partidua> partiduakJornada = new ArrayList<>();
					for (Partidua p : partiduak) {
						if (p.getId_Par() == j.getIdPar()) {
							partiduakJornada.add(p);
						}
					}
					j.setPartiduak(partiduakJornada);
				}
			}
			// Denboraldia objektua osatu
			Denboraldia den = new Denboraldia("2024-2025", jaurdunaldiak, new ArrayList<>());
			den.setTaldeak(taldeak);
			// JAXB bidez XML fitxategia sortu
			JAXBContext context = JAXBContext.newInstance(Denboraldia.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			File f = new File("Denboraldia_2024-2025.xml");
			marshaller.marshal(den, f);
			// --- LOG: XML-a ondo sortu dela erregistratu ---
			logger.info("XML GENERAZIOA: Fitxategia ondo sortu da: " + f.getName());
			// Erabiltzaileari mezua erakutsi
			JOptionPane.showMessageDialog(null, "XML fitxategia ondo sortu da hemen:\n" + f.getAbsolutePath(),
					"Sormen Arrakastatsua", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// --- LOG: Errore kritikoa erregistratu xehetasunekin ---
			logger.severe("ERROREA XML-A SORTZEAN: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Errorea XML-a sortzean: " + e.getMessage(), "Errorea",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static void konfiguratuLog() {
		try {
			// "aplikazioa.log" fitxategia sortu. 'true' jarrita, mezu berriak erantsi
			// egingo dira (append)
			FileHandler fh = new FileHandler("aplikazioa.log", true);
			logger.addHandler(fh);
			// Formatu sinplea eman (testu arrunta, ez XML)
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			// Kontsolako mezuak ekiditeko (aukerazkoa)
			logger.setUseParentHandlers(false);
		} catch (IOException | SecurityException e) {
			System.err.println("Ezin izan da Log fitxategia konfiguratu: " + e.getMessage());
		}
	}
}