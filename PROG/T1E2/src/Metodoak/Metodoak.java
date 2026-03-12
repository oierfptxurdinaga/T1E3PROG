package Metodoak;

import java.io.*; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import E2.*;

/**
 * Metodoak klaseak aplikazioaren negozio-logika eta datuen kudeaketa zentralizatzen ditu.
 * <p>
 * Klase honek honako ardurak ditu:
 * </p>
 * <ul>
 *   <li>Datuen karga eta gordetzea (serializazioa)</li>
 *   <li>Erabiltzaileen autentifikazioa eta baimenak</li>
 *   <li>Taulen eguneraketa (taldeak eta jokalariak)</li>
 *   <li>Jokalarien trukea talde desberdinen artean</li>
 *   <li>Aplikaziotik irteteko logika</li>
 * </ul>
 *
 * <p>
 * Metodo guztiak estatikoak dira, klase hau utilitate-klase gisa erabiltzeko.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */	
public class Metodoak {
	/**
     * Aplikazioko erabiltzaile mota guztien zerrenda.
     */
	private static List<ErabiltzaileMota> erabiltzaileaklist;
	 /**
     * Talde guztien zerrenda nagusia.
     * <p>
     * Aplikazio osoan partekatzen den egitura da eta bertan
     * gordetzen dira taldeak eta haien jokalariak.
     * </p>
     */
	public static ArrayList<Taldea> taldeakMasterList = new ArrayList<>();

	// --- Fitxategien kudeaketa ---
	/**
     * Datuak fitxategitik kargatzen ditu.
     * <p>
     * "datuak.ser" fitxategitik talde guztiak irakurtzen ditu
     * serializazioaren bidez.
     * </p>
     *
     * <p>
     * Fitxategia existitzen ez bada edo errorea gertatzen bada,
     * errore-mezu bat bistaratzen da.
     * </p>
     */
	@SuppressWarnings("unchecked")
	public static void kargatuDatuak() {
		File f = new File("datuak.ser");
		if (f.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				taldeakMasterList = (ArrayList<Taldea>) ois.readObject();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ezin izan dira Datuak Kargatu", "Errorea", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Ez da Datuak.ser artxiboa aurkitu", "Errorea", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
     * Uneko datuak fitxategian gordetzen ditu.
     * <p>
     * Taldeen zerrenda nagusia ("taldeakMasterList")
     * "datuak.ser" fitxategian gordetzen da.
     * </p>
     */
	public static void gordeDatuak() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datuak.ser"))) {
			oos.writeObject(taldeakMasterList);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ezin izan dira gorde aldaketak", "Errorea", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
     * Aplikazioko erabiltzaileak hasieratzen ditu.
     * <p>
     * Metodo honek erabiltzaile finko batzuk sortzen ditu
     * (administradorea, presidentea eta erabiltzaile arruntak).
     * </p>
     */
	public static void kargatuErabiltzaileak() {
		erabiltzaileaklist = new ArrayList<>();
		erabiltzaileaklist.add(new Administradorea("Eder", "Bilbao", "12345678A", "ebilbao", "12345"));
		erabiltzaileaklist.add(new Presidentea("Aratz", "Elexpe", "12345678B", "aelexpe", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Kirian", "Munoz", "12345678C", "kmunoz", "12345"));
		erabiltzaileaklist.add(new ErabiltzaileNormala("Aratz", "Barcena", "12345678D", "abarcena", "12345"));
	}
	
	// --- Logika Metodoak ---
	/**
     * Bi talde desberdinetako jokalariak trukatzen ditu.
     * <p>
     * Taula bakoitzean aukeratutako jokalari bana hartzen da
     * eta talde batetik bestera aldatzen dira.
     * </p>
     *
     * <p>
     * Trukea egin ondoren:
     * </p>
     * <ul>
     *   <li>Datuak gordetzen dira</li>
     *   <li>Taulak eguneratzen dira</li>
     * </ul>
     *
     * @param seleccionadoderecha eskuineko taldearen izena
     * @param seleccionadoziquerda ezkerreko taldearen izena
     * @param tablaDerecha eskuineko taldearen jokalarien taula
     * @param tablaIzquierda ezkerreko taldearen jokalarien taula
     */
	public static void actualizarTablasJokalariak(String seleccionadoderecha, String seleccionadoziquerda, JTable tablaDerecha, JTable tablaIzquierda) {
	    if (seleccionadoziquerda == null || seleccionadoderecha == null) return;
	    
	    if (seleccionadoderecha.equals(seleccionadoziquerda)) {
	        JOptionPane.showMessageDialog(null, "Bi talde desberdin aukeratu behar dituzu");
	        return;
	    }

	    Taldea tderecha = null, tizquierda = null;
	    for (Taldea taldea : taldeakMasterList) {
	        if (taldea.getIzena().equals(seleccionadoderecha)) tderecha = taldea;
	        if (taldea.getIzena().equals(seleccionadoziquerda)) tizquierda = taldea;
	    }

	    if (tderecha == null || tizquierda == null) return;

	    int jokalarider = tablaDerecha.getSelectedRow();
	    int jokalariizq = tablaIzquierda.getSelectedRow();

	    if (jokalarider == -1 || jokalariizq == -1) {
	        JOptionPane.showMessageDialog(null, "Aukeratu behar dituzu bi jokalari (bana taula bakoitzean)");
	        return;
	    }

	    // Intercambio con constructor copia
	    Jokalaria copiaDer = new Jokalaria(tderecha.getJokalariak().get(jokalarider));
	    Jokalaria copiaIzq = new Jokalaria(tizquierda.getJokalariak().get(jokalariizq));

	    copiaDer.setTaldea(tizquierda.getIzena());
	    copiaIzq.setTaldea(tderecha.getIzena());

	    tderecha.getJokalariak().set(jokalarider, copiaIzq);
	    tizquierda.getJokalariak().set(jokalariizq, copiaDer);

	    // Guardar y refrescar (meterlosJokalaris ya incluye el Collections.sort)
	    gordeDatuak();
	    meterlosJokalaris(seleccionadoziquerda, tablaIzquierda);	 
	    meterlosJokalaris(seleccionadoderecha, tablaDerecha);	    

	    JOptionPane.showMessageDialog(null, "Jokalariak ondo aldatu dira!");
	}
	/**
     * Talde bateko jokalariak taula batean bistaratzen ditu.
     * <p>
     * Jokalarien zerrenda izenaren arabera ordenatzen da
     * taulan erakutsi aurretik.
     * </p>
     *
     * @param seleccionado aukeratutako taldearen izena
     * @param tabla jokalariak bistaratuko diren JTable-a
     */
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
			// Ordenar antes de mostrar
			Collections.sort(t.getJokalariak()); 
			
			DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
			modelo.setRowCount(0);
			for (Jokalaria j : t.getJokalariak()) {
				modelo.addRow(new Object[] {
					j.getIzena(), j.getAbizena(), j.getJaiotzeData(),
					j.getNAN(), t.getIzena(), j.getPrezioa(), j.getJokalarienPuntuak()
				});
			}
		}
	}
	/**
     * Talde baten informazioa taula desberdinetan eguneratzen du.
     * <p>
     * Bi taula eguneratzen dira:
     * </p>
     * <ul>
     *   <li>Taldearen laburpena (datu orokorrak)</li>
     *   <li>Jokalarien zerrenda osoa</li>
     * </ul>
     *
     * @param seleccionado aukeratutako taldearen izena
     * @param tablaPequena taldearen laburpena erakusten duen taula
     * @param tablaGrande jokalarien zerrenda erakusten duen taula
     */
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
			Collections.sort(t.getJokalariak()); // Ordenar tabla de jugadores

			// Tabla resumen equipo
			DefaultTableModel modeloPequena = (DefaultTableModel) tablaPequena.getModel();
			modeloPequena.setRowCount(0);
			modeloPequena.addRow(new Object[]{
				t.getIzena(), t.getSorreraUrtea(), t.getLehendakari(),
				t.getN_Bazkideak(), t.getPuntuakF(), t.getPuntuakC(),
				t.getPuntuTotalak(), t.getIrabazitakoak(), t.getGaldutakoak(),
				t.getJokalariak().size()
			});

			// Tabla lista jugadores
			DefaultTableModel modeloGrande = (DefaultTableModel) tablaGrande.getModel();
			modeloGrande.setRowCount(0);
			for (Jokalaria j : t.getJokalariak()) {
				modeloGrande.addRow(new Object[] {
					j.getIzena(), j.getAbizena(), j.getJaiotzeData(),
					j.getNAN(), t.getIzena(), j.getPrezioa(), j.getJokalarienPuntuak()
				});
			}
		}
	}
	/**
     * Erabiltzailearen login-a egiaztatzen du.
     * <p>
     * Erabiltzaile-izena eta pasahitza zuzenak badira,
     * erabiltzailearen rola itzultzen da.
     * </p>
     *
     * @param erabiltzailea erabiltzaile-izena
     * @param pasahitza pasahitza
     * @return erabiltzailearen rola, edo null baldin eta login-a okerra bada
     */
	public static String login(String erabiltzailea, String pasahitza) {
		if (erabiltzaileaklist == null) kargatuErabiltzaileak();
		
		for (ErabiltzaileMota e : erabiltzaileaklist) {
			if (e.getErabiltzailea().equals(erabiltzailea) && e.getPasahitza().equals(pasahitza)) {
				return e.baimenak();
			}
		}
		return null;
	}
	/**
     * Aplikaziotik irteteko prozesua kudeatzen du.
     * <p>
     * Erabiltzaileari galdetzen dio aldaketak gorde nahi dituen ala ez.
     * Aukeraren arabera, datuak gorde eta aplikazioa ixten da,
     * edo zuzenean ixten da.
     * </p>
     */
	public static void atera() {
		int respuesta = JOptionPane.showConfirmDialog(null, "¿Atera baino lehen, gorde nahi duzu?", "Berrespena", JOptionPane.YES_NO_CANCEL_OPTION);

		if (respuesta == JOptionPane.YES_OPTION) {
			gordeDatuak();
			System.exit(0);
		} else if (respuesta == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}
}