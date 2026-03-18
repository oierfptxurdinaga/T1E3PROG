package Metodoak;

import java.io.*; 
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
	private  TaldeDao talde;
	private static  JaurdunaldiDao jaurdunaldi;
	private static  PartiduaDao partidua;


	// --- Fitxategien kudeaketa ---
	public  void kargatuDatuak() {
		// TaldeDao-a erabiltzen dugu datu-basetik talde guztiak ArrayList-era ekartzeko
		talde = new TaldeDao();
		taldeakMasterList = talde.kargatuTaldeak();
	}

	public static void gordeDatuak() {
	    ArrayList<Partidua> listaPartidos = partidua.kargatuPartiduak();
		partidua.eguneratuPartiduakGuztiak(listaPartidos);

	    // 2. Orain, kalkulatu klasifikazio berria memorian (ArrayList-ean)
	    kalkulatuKlasifikazioa();

	    // 3. Azkenik, bidali klasifikazio hori DBko 'klasifikazioa' taulara
	    KlasifikazioaDao kDao = new KlasifikazioaDao();
	    for (Taldea t : taldeakMasterList) {
	        kDao.modifyKlasifikazioa(t); // Orain bai, 't' objektu bakar bat da
	    }
	    JOptionPane.showMessageDialog(null, "Datu guztiak (Partiduak eta Klasifikazioa) ondo gorde dira!");
		
	}

	public static void kargatuErabiltzaileak() {
		erabiltzaileaklist = new ArrayList<>();
		erabiltzaileaklist.add(new Administradorea("Eder", "Bilbao", "12345678A", "ebilbao", "12345"));
		erabiltzaileaklist.add(new Presidentea("Aratz", "Elexpe", "12345678B", "aelexpe", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Kirian", "Munoz", "12345678C", "kmunoz", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Aratz", "Barcena", "12345678D", "abarcena", "12345"));
	}
	
	// --- Logika Metodoak ---

	public static void actualizarTablasJokalariak(String seleccionadoderecha, String seleccionadoziquerda, JTable tablaDerecha, JTable tablaIzquierda) {
	    if (seleccionadoziquerda == null || seleccionadoderecha == null) return;
	    
	    if (seleccionadoderecha.equals(seleccionadoziquerda)) {
	        JOptionPane.showMessageDialog(null, "Bi talde desberdin aukeratu behar dituzu");
	        return;
	    }

	    // Aukeratutako izenekin Taldea objektuak bilatzen ditugu zerrenda nagusian
	    Taldea tderecha = null, tizquierda = null;
	    for (Taldea taldea : taldeakMasterList) {
	        if (taldea.getIzena().equals(seleccionadoderecha)) tderecha = taldea;
	        if (taldea.getIzena().equals(seleccionadoziquerda)) tizquierda = taldea;
	    }

	    if (tderecha == null || tizquierda == null) return;

	    // Taulan markatutako errenkadak (row) lortzen ditugu
	    int jokalarider = tablaDerecha.getSelectedRow();
	    int jokalariizq = tablaIzquierda.getSelectedRow();

	    if (jokalarider == -1 || jokalariizq == -1) {
	        JOptionPane.showMessageDialog(null, "Aukeratu behar dituzu bi jokalari (bana taula bakoitzean)");
	        return;
	    }

	    // Jokalariak trukatzen ditugu kopia-konstruktorea erabiliz (datuak ez nahasteko)
	    Jokalaria copiaDer = new Jokalaria(tderecha.getJokalariak().get(jokalarider));
	    Jokalaria copiaIzq = new Jokalaria(tizquierda.getJokalariak().get(jokalariizq));

	    // Jokalari bakoitzari bere talde berriaren izena esleitzen diogu
	    copiaDer.setTaldea(tizquierda.getIzena());
	    copiaIzq.setTaldea(tderecha.getIzena());

	    // Taldeen ArrayList-etan jokalari berriak sartzen ditugu
	    tderecha.getJokalariak().set(jokalarider, copiaIzq);
	    tizquierda.getJokalariak().set(jokalariizq, copiaDer);

	    meterlosJokalaris(seleccionadoziquerda, tablaIzquierda);	 
	    meterlosJokalaris(seleccionadoderecha, tablaDerecha);	    

	    JOptionPane.showMessageDialog(null, "Jokalariak ondo aldatu dira!");
	}

	public static void meterlosJokalaris(String seleccionado, JTable tabla) {
		if (seleccionado == null) return;

		Taldea t = null;
		for (Taldea taldea : taldeakMasterList) {
			if (taldea.getIzena().equals(seleccionado)) {
				t = taldea;
				break;
			}
		}
		
		if (t != null) {
			// Jokalariak alfabetikoki ordenatzen ditugu (Jokalaria klaseko compareTo-ari esker)
			Collections.sort(t.getJokalariak()); 
			
			DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
			modelo.setRowCount(0); // Taula garbitu berriro betetzeko
			for (Jokalaria j : t.getJokalariak()) {
				modelo.addRow(new Object[] {
					j.getIzena(), j.getAbizena(), j.getJaiotzeData(),
					j.getNAN(), t.getIzena(), j.getPrezioa()
				});
			}
		}
	}
	
	public static void beteEmaitzenTaula(DefaultTableModel modeloEmaitzak) {
	    // DAOak inizializatu datu-basearekin komunikatzeko
	    if (jaurdunaldi == null) jaurdunaldi = new JaurdunaldiDao();
	    if (partidua == null) partidua = new PartiduaDao();

	    // Zerrendak kargatu datu-basetik
	    ArrayList<Jaurdunaldia> listaJornadas = jaurdunaldi.kargatuJaurdunaldiak();
	    ArrayList<Partidua> listaPartidos = partidua.kargatuPartiduak();

	    modeloEmaitzak.setRowCount(0);

	    // Jaurdunaldi bakoitzeko, bere Partidua bilatzen dugu Id_Par erabiliz
	    for (Jaurdunaldia j : listaJornadas) {
	        int puntosLoc = 0;
	        int puntosVis = 0;

	        for (Partidua p : listaPartidos) {
	            // Bi zerrendak gurutzatzen ditugu: Id_Par berdina bada, emaitzak hartzen ditugu
	            if (j.getIdPar() == p.getId_Par()) { 
	                puntosLoc = p.getResultLokala();
	                puntosVis = p.getResulBisitari();
	                break; 
	            }
	        }

	        // Taulan errenkada berria sartzen dugu (5. indexuan IdPar gordetzen dugu ezkutuan)
	        Object[] fila = {
	            "Jornada " + j.getIdJaurdu() + " - " + j.getTaldeIrabazlea(), 
	            puntosLoc,                                                   
	            "vs",                                                        
	            puntosVis,                                                   
	            j.getTaldeGaldu(),                                            
	            j.getIdPar() 
	        };
	        modeloEmaitzak.addRow(fila);
	    }
	}
	
	public static void kalkulatuKlasifikazioa() {
	    // 1. Resetatu taldeen datuak kalkulu berria egiteko (memorian bakarrik)
	    // Hau garrantzitsua da puntuak bikoiztu ez daitezen
	    for (Taldea t : taldeakMasterList) {
	        t.setPuntuTotalak(0);
	        t.setIrabazitakoak(0);
	        t.setGaldutakoak(0);
	        t.setPuntuakF(0); // Aldeko puntuak (puntos a favor)
	        t.setPuntuakC(0); // Aurkako puntuak (puntos en contra)
	    }

	    // 2. Partiduak kargatu datu-basetik (eguneratuta egon daitezen)
	    PartiduaDao pDao = new PartiduaDao();
	    ArrayList<Partidua> partiduak = pDao.kargatuPartiduak();

	    // 3. Partiduz partidu emaitzak prozesatu
	    for (Partidua p : partiduak) {
	        Taldea local = null;
	        Taldea visit = null;

	        // Bilatu taldeak gure master list-ean izenaren bidez
	        for (Taldea t : taldeakMasterList) {
	            if (t.getIzena().equals(p.getTaldeLokala())) local = t;
	            if (t.getIzena().equals(p.getTaldeBisitari())) visit = t;
	        }

	        // Bi taldeak aurkitu baditugu, puntuak banatu
	        if (local != null && visit != null) {
	            // Saskiratze puntuak gehitu (Basket average-erako)
	            local.setPuntuakF(local.getPuntuakF() + p.getResultLokala());
	            local.setPuntuakC(local.getPuntuakC() + p.getResulBisitari());
	            visit.setPuntuakF(visit.getPuntuakF() + p.getResulBisitari());
	            visit.setPuntuakC(visit.getPuntuakC() + p.getResultLokala());

	            // Irabazlea nor den begiratu (Irabazleak 2 puntu, galtzaileak 0)
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

	    // 4. Taldeak ordenatu: Puntu gehien dituenak gora
	    // Berdinketa badago, aldeko puntu gehien dituenak irabazten du
	    taldeakMasterList.sort((t1, t2) -> {
	        int res = Integer.compare(t2.getPuntuTotalak(), t1.getPuntuTotalak());
	        if (res == 0) {
	            res = Integer.compare(t2.getPuntuakF(), t1.getPuntuakF());
	        }
	        return res;
	    });
	}
	
	public static void prozesatuEmaitzak(DefaultTableModel modeloEmaitzak) {
	    PartiduaDao dao = new PartiduaDao();

	    // Taulako errenkada guztiak banan-banan irakurtzen ditugu
	    for (int i = 0; i < modeloEmaitzak.getRowCount(); i++) {
	        try {
	            // Taulatik balioak hartu eta zenbakira bihurtu (casting)
	            int puntosLoc = Integer.parseInt(modeloEmaitzak.getValueAt(i, 1).toString());
	            int puntosVis = Integer.parseInt(modeloEmaitzak.getValueAt(i, 3).toString());
	            
	            // Ezkutuko ID-a lortzen dugu DB-an zein partida eguneratu behar den jakiteko
	            int idPar = Integer.parseInt(modeloEmaitzak.getValueAt(i, 5).toString());
	            
	            // DAO-ari deitu SQL UPDATE-a egiteko
	            dao.eguneratuPuntuak(idPar, puntosLoc, puntosVis);
	            
	        } catch (NumberFormatException e) {
	            // Erabiltzaileak zenbakia ez den zerbait idazten badu, errorea erakutsi
	            JOptionPane.showMessageDialog(null, "Errorea: Puntuak zenbakiak izan behar dira " + (i+1) + " lerroan.");
	            return;
	        } catch (Exception e) {
	            System.out.println("Errore orokorra: " + e.getMessage());
	        }
	    }
	    JOptionPane.showMessageDialog(null, "Emaitza guztiak ondo gorde dira datu-basean!");
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

			// Taldearen datu orokorren taula betetzen dugu
			DefaultTableModel modeloPequena = (DefaultTableModel) tablaPequena.getModel();
			modeloPequena.setRowCount(0);
			modeloPequena.addRow(new Object[]{
				t.getIzena(), t.getSorreraUrtea(), t.getLehendakari(),
				t.getN_Bazkideak(), t.getPuntuakF(), t.getPuntuakC(),
				t.getPuntuTotalak(), t.getIrabazitakoak(), t.getGaldutakoak(),
				t.getJokalariak().size()
			});

			// Talde horretako jokalarien zerrenda taula handian betetzen dugu
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

	public static String login(String erabiltzailea, String pasahitza) {
		if (erabiltzaileaklist == null) kargatuErabiltzaileak();
		
		// Erabiltzaileen zerrenda korritzen dugu kointzidentzia bila
		for (ErabiltzaileMota e : erabiltzaileaklist) {
			if (e.getErabiltzailea().equals(erabiltzailea) && e.getPasahitza().equals(pasahitza)) {
				// Login zuzena bada, erabiltzailearen baimen mota (Admin, Presidentea...) itzultzen dugu
				return e.baimenak();
			}
		}
		return null;
	}

	public static void atera() {
		// Berrespen leihoa erakusten dugu aplikazioa itxi aurretik
		int respuesta = JOptionPane.showConfirmDialog(null, "¿Atera baino lehen, gorde nahi duzu?", "Berrespena", JOptionPane.YES_NO_CANCEL_OPTION);

		if (respuesta == JOptionPane.YES_OPTION) {
			gordeDatuak();
			System.exit(0);
		} else if (respuesta == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
		// CANCEL_OPTION sakatzean ez dugu ezer egiten, leihoa ez ixteko
	}
}