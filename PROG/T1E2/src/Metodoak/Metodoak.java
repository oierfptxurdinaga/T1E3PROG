package Metodoak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAO.JaurdunaldiDao;
import DAO.KlasifikazioaDao;
import DAO.PartiduaDao;
import DAO.TaldeDao;
import E2.*;

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
		// 1. Egiaztatu zerrenda kargatuta dagoela: Ez badago daturik memorian, ezin
		// dugu ezer gorde
		if (partiduakMasterList == null || partiduakMasterList.isEmpty()) {
			System.out.println("Errorea: Ez dago partidurik memorian gordetzeko. Ziurtatu kargatuDatuak() deitu dela.");
			return;
		}
		// 2. Partiduak eta Jaurdunaldiak banan-banan eguneratu datu-basean (DB)
		for (Partidua p : partiduakMasterList) {
			String irabazlea = "Berdinketa";
			String galtzailea = "Berdinketa";
			// Emaitzaren arabera, irabazlea eta galtzailea nor diren zehaztu
			if (p.getResultLokala() > p.getResulBisitari()) {
				irabazlea = p.getTaldeLokala();
				galtzailea = p.getTaldeBisitari();
			} else if (p.getResulBisitari() > p.getResultLokala()) {
				irabazlea = p.getTaldeBisitari();
				galtzailea = p.getTaldeLokala();
			}
			// DAO-ko metodoari deitu aldaketak SQL bidez exekutatzeko (ID-a, emaitzak eta
			// irabazleak bidaliz)
			partiduaDao.modifyPartiduaEtaJaurdunaldi(p.getId_Par(), p.getResultLokala(), p.getResulBisitari(),
					irabazlea, galtzailea);
		}
		// 3. Klasifikazioa berriz kalkulatu MEMORIAN: Gorde aurretik taldeen puntuak
		// eguneratuta egon daitezen
		kalkulatuKlasifikazioa();
		// 4. Sailkapen berria (taldeen puntuak, irabazitakoak...) DBra bidali
		for (Taldea t : taldeakMasterList) {
			kDao.modifyKlasifikazioa(t);
		}
		// Erabiltzaileari jakinarazi prozesua ondo amaitu dela
		JOptionPane.showMessageDialog(null, "Datu-basea ondo eguneratu da partidu eta klasifikazio berriekin.");
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
	public static void prozesatuEmaitzak(DefaultTableModel modeloEmaitzak) {
		// Taulako errenkada bakoitzeko begizta bat hasi (0-tik hasita)
		for (int i = 0; i < modeloEmaitzak.getRowCount(); i++) {
			try {
				// Balioak taulako zutabe zehatzetatik lortu eta Integer-era bihurtu
				// 1. zutabea: Lokalaren puntuak | 3. zutabea: Bisitariarenak | 5. zutabea:
				// Partiduaren IDa
				int puntosLoc = Integer.parseInt(modeloEmaitzak.getValueAt(i, 1).toString());
				int puntosVis = Integer.parseInt(modeloEmaitzak.getValueAt(i, 3).toString());
				int idPar = Integer.parseInt(modeloEmaitzak.getValueAt(i, 5).toString());
				// Memoriako zerrenda nagusian (partiduakMasterList) partidua bilatu
				for (Partidua p : partiduakMasterList) {
					// ID-ak bat egiten badu, objektuaren emaitzak eguneratu
					if (p.getId_Par() == idPar) {
						p.setResultLokala(puntosLoc);
						p.setResulBisitari(puntosVis);
						break; // Partidua aurkituta, barne-begiztatik irten
					}
				}
			} catch (Exception e) {
				// Errore bat badago (adibidez, zenbaki bat ez den zerbait idaztean), mezua eman
				System.out.println("Errorea lerroa prozesatzean: " + e.getMessage());
			}
		}
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
		if (erabiltzaileaklist == null)
			kargatuErabiltzaileak();
		// Erabiltzaile bakoitza egiaztatu izena eta pasahitza bat datozen ikusteko
		for (ErabiltzaileMota e : erabiltzaileaklist) {
			if (e.getErabiltzailea().equals(erabiltzailea) && e.getPasahitza().equals(pasahitza)) {
				// Login zuzena bada, erabiltzaile motari dagokion baimen-rola itzuli
				return e.baimenak();
			}
		}
		// Aurkitzen ez bada, null itzuli (login okerra)
		return null;
	}

	public static void atera() {
		// Berrespen leiho bat erakutsi hiru aukerarekin: Bai, Ez, Utzi
		int respuesta = JOptionPane.showConfirmDialog(null, "Atera baino lehen, gorde nahi duzu?", "Berrespena",
				JOptionPane.YES_NO_CANCEL_OPTION);
		// "BAI" sakatzean, datu guztiak datu-basean gorde eta programa itxi
		if (respuesta == JOptionPane.YES_OPTION) {
			gordeDatuak();
			System.exit(0);
			// "EZ" sakatzean, programa zuzenean itxi ezer gorde gabe
		} else if (respuesta == JOptionPane.NO_OPTION) {
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

	public static void actualizarTablasTaldeak(String aukeratuta, JTable tablaPequena, JTable tablaGrande) {
		// Ziurtatu hautatutako taldearen izena ez dela null
		if (aukeratuta == null)
			return;
		Taldea t = null;
		// MasterList zerrendan bilatu hautatutako izenarekin bat datorren Taldea
		// objektua
		for (Taldea taldea : taldeakMasterList) {
			if (taldea.getIzena().equals(aukeratuta)) {
				t = taldea;
				break;
			}
		}
		if (t != null) {
			// Taldeko jokalariak alfabetikoki edo ezarritako irizpidearen arabera ordenatu
			Collections.sort(t.getJokalariak());
			// 1. TAULA (Txikia): Taldearen datu orokorrak eta estatistikak eguneratu
			DefaultTableModel modeloPequena = (DefaultTableModel) tablaPequena.getModel();
			modeloPequena.setRowCount(0); // Taula hustu datu berriak sartu aurretik
			modeloPequena.addRow(new Object[] { t.getIzena(), t.getSorreraUrtea(), t.getLehendakari(),
					t.getN_Bazkideak(), t.getPuntuakF(), t.getPuntuakC(), t.getPuntuTotalak(), t.getIrabazitakoak(),
					t.getGaldutakoak(), t.getJokalariak().size() // Jokalari kopurua kalkulatu
			});
			// 2. TAULA (Handia): Talde horretako jokalari guztien zerrenda kargatu
			DefaultTableModel modeloGrande = (DefaultTableModel) tablaGrande.getModel();
			modeloGrande.setRowCount(0); // Taula garbitu
			for (Jokalaria j : t.getJokalariak()) {
				modeloGrande.addRow(new Object[] { j.getIzena(), j.getAbizena(), j.getJaiotzeData(), j.getNAN(),
						t.getIzena(), j.getPrezioa() });
			}
		}
	}
}