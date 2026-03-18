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
 * Metodoak klaseak aplikazioaren negozio-logika eta datuen kudeaketa zentralizatzen ditu.
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
			taldeakMasterList = taldeDao.kargatuTaldeak();
			partiduakMasterList = partiduaDao.kargatuPartiduak();
			
			System.out.println("Datuak kargatuta: " + taldeakMasterList.size() + " talde eta " + partiduakMasterList.size() + " partidu.");
		} catch (Exception e) {
			System.out.println("Errorea datuak kargatzean: " + e.getMessage());
		}
	}

	/**
	 * Memorian (ArrayList) dauden aldaketa guztiak DBra bidaltzen ditu ordena zuzenean.
	 */
	public static void gordeDatuak() {
	    KlasifikazioaDao kDao = new KlasifikazioaDao();

	    // 1. Egiaztatu zerrenda kargatuta dagoela
	    if (partiduakMasterList == null || partiduakMasterList.isEmpty()) {
	        System.out.println("Errorea: Ez dago partidurik memorian gordetzeko. Ziurtatu kargatuDatuak() deitu dela.");
	        return;
	    }

	    // 2. Partiduak eta Jaurdunaldiak eguneratu DBan banan-banan
	    for (Partidua p : partiduakMasterList) {
	        String irabazlea = "Berdinketa";
	        String galtzailea = "Berdinketa";

	        if (p.getResultLokala() > p.getResulBisitari()) {
	            irabazlea = p.getTaldeLokala();
	            galtzailea = p.getTaldeBisitari();
	        } else if (p.getResulBisitari() > p.getResultLokala()) {
	            irabazlea = p.getTaldeBisitari();
	            galtzailea = p.getTaldeLokala();
	        }

	        // PartiduaDao-ko metodo konbinatua erabili (lehen azaldutakoa)
	        partiduaDao.modifyPartiduaEtaJaurdunaldi(p.getId_Par(), p.getResultLokala(), p.getResulBisitari(), irabazlea, galtzailea);
	    }

	    // 3. Klasifikazioa berralkulatu MEMORIAN (taldeakMasterList eguneratu)
	    kalkulatuKlasifikazioa(); 

	    // 4. Klasifikazio berria DBra bidali
	    for (Taldea t : taldeakMasterList) {
	        kDao.modifyKlasifikazioa(t);
	    }

	    JOptionPane.showMessageDialog(null, "Datu-basea ondo eguneratu da partidu eta klasifikazio berriekin.");
	}

	public static void kalkulatuKlasifikazioa() {
	    // 1. Resetatu taldeen datuak memorian
	    for (Taldea t : taldeakMasterList) {
	        t.setPuntuTotalak(0);
	        t.setIrabazitakoak(0);
	        t.setGaldutakoak(0);
	        t.setPuntuakF(0);
	        t.setPuntuakC(0);
	    }

	    // 2. ERABILTZEN DUGU MEMORIAKO ZERRENDA (UI-ko aldaketekin)
	    for (Partidua p : partiduakMasterList) {
	        Taldea local = null;
	        Taldea visit = null;

	        for (Taldea t : taldeakMasterList) {
	            if (t.getIzena().equals(p.getTaldeLokala())) local = t;
	            if (t.getIzena().equals(p.getTaldeBisitari())) visit = t;
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

	    // 3. Ordenatu klasifikazioa
	    taldeakMasterList.sort((t1, t2) -> {
	        int res = Integer.compare(t2.getPuntuTotalak(), t1.getPuntuTotalak());
	        if (res == 0) res = Integer.compare(t2.getPuntuakF(), t1.getPuntuakF());
	        return res;
	    });
	}

	/**
	 * Taula (UI) eta ArrayList-a sinkronizatzen ditu gorde aurretik.
	 */
	public static void prozesatuEmaitzak(DefaultTableModel modeloEmaitzak) {
	    for (int i = 0; i < modeloEmaitzak.getRowCount(); i++) {
	        try {
	            int puntosLoc = Integer.parseInt(modeloEmaitzak.getValueAt(i, 1).toString());
	            int puntosVis = Integer.parseInt(modeloEmaitzak.getValueAt(i, 3).toString());
	            int idPar = Integer.parseInt(modeloEmaitzak.getValueAt(i, 5).toString());
	            
	            // Eguneratu ArrayList-a (Memoria)
	            for(Partidua p : partiduakMasterList) {
	                if(p.getId_Par() == idPar) {
	                    p.setResultLokala(puntosLoc);
	                    p.setResulBisitari(puntosVis);
	                    break;
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Errorea lerroa prozesatzean: " + e.getMessage());
	        }
	    }
	}

	// --- Beste metodoak (Login, Jokalariak trukatu, etab.) ---

	public static void kargatuErabiltzaileak() {
		erabiltzaileaklist = new ArrayList<>();
		erabiltzaileaklist.add(new Administradorea("Eder", "Bilbao", "12345678A", "ebilbao", "12345"));
		erabiltzaileaklist.add(new Presidentea("Aratz", "Elexpe", "12345678B", "aelexpe", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Kirian", "Munoz", "12345678C", "kmunoz", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Aratz", "Barcena", "12345678D", "abarcena", "12345"));
	}

	public static String login(String erabiltzailea, String pasahitza) {
		if (erabiltzaileaklist == null) kargatuErabiltzaileak();
		for (ErabiltzaileMota e : erabiltzaileaklist) {
			if (e.getErabiltzailea().equals(erabiltzailea) && e.getPasahitza().equals(pasahitza)) {
				return e.baimenak();
			}
		}
		return null;
	}

	public static void atera() {
		int respuesta = JOptionPane.showConfirmDialog(null, "Atera baino lehen, gorde nahi duzu?", "Berrespena", JOptionPane.YES_NO_CANCEL_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			gordeDatuak();
			System.exit(0);
		} else if (respuesta == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}
	public static void beteEmaitzenTaula(DefaultTableModel modeloEmaitzak) {
        // DAOak inizializatu (ziurtatzeko ez direla null)
        if (jaurdunaldiDao == null) jaurdunaldiDao = new JaurdunaldiDao();
        if (partiduaDao == null) partiduaDao = new PartiduaDao();

        // Kargatu datu eguneratuak DBtik
        ArrayList<Jaurdunaldia> listaJornadas = jaurdunaldiDao.kargatuJaurdunaldiak();
        ArrayList<Partidua> listaPartidos = partiduaDao.kargatuPartiduak();

        // Taula garbitu berriro betetzeko
        modeloEmaitzak.setRowCount(0);

        // Jaurdunaldi bakoitzeko, bere Partidua bilatzen dugu Id_Par erabiliz
        for (Jaurdunaldia j : listaJornadas) {
            int puntosLoc = 0;
            int puntosVis = 0;
            String taldeLoc = "Ezezaguna";
            String taldeVis = "Ezezaguna";

            for (Partidua p : listaPartidos) {
                // IDa kointziditzen badu, emaitzak eta izenak hartzen ditugu
                if (j.getIdPar() == p.getId_Par()) { 
                    puntosLoc = p.getResultLokala();
                    puntosVis = p.getResulBisitari();
                    taldeLoc = p.getTaldeLokala();
                    taldeVis = p.getTaldeBisitari();
                    break; 
                }
            }

            // Taulan errenkada berria sartzen dugu
            // Zutabeak: 0: Partidua, 1: PuntuakL, 2: "vs", 3: PuntuakV, 4: KanpokoTaldea, 5: ID_OCULTO
            Object[] fila = {
                "J." + j.getIdJaurdu() + " - " + taldeLoc, 
                puntosLoc,                                                   
                "vs",                                                        
                puntosVis,                                                   
                taldeVis,                                            
                j.getIdPar() // ID ezkutua, gero gorde ahal izateko
            };
            modeloEmaitzak.addRow(fila);
        }
    }
	public static void actualizarTablasTaldeak(String seleccionado, JTable tablaPequena, JTable tablaGrande) {
		if (seleccionado == null) return;
		Taldea t = null;
		for (Taldea taldea : taldeakMasterList) {
			if (taldea.getIzena().equals(seleccionado)) {
				t = taldea;
				break;
			}
		}
		if (t != null) {
			Collections.sort(t.getJokalariak()); 
			DefaultTableModel modeloPequena = (DefaultTableModel) tablaPequena.getModel();
			modeloPequena.setRowCount(0);
			modeloPequena.addRow(new Object[]{
				t.getIzena(), t.getSorreraUrtea(), t.getLehendakari(),
				t.getN_Bazkideak(), t.getPuntuakF(), t.getPuntuakC(),
				t.getPuntuTotalak(), t.getIrabazitakoak(), t.getGaldutakoak(),
				t.getJokalariak().size()
			});
			DefaultTableModel modeloGrande = (DefaultTableModel) tablaGrande.getModel();
			modeloGrande.setRowCount(0);
			for (Jokalaria j : t.getJokalariak()) {
				modeloGrande.addRow(new Object[] {
					j.getIzena(), j.getAbizena(), j.getJaiotzeData(),
					j.getNAN(), t.getIzena(), j.getPrezioa()
				});
			}
		}
	}
}