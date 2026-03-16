package E2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Taldea klaseak saskibaloi talde bat irudikatzen du.
 * <p>
 * Klase honek taldearen informazio nagusia gordetzen du:
 * izena, sorrera urtea, lehendakaria, bazkideen kopurua,
 * puntuazioa, irabazitako eta galduko partidak,
 * eta jokalariak.
 * </p>
 * <p>
 * Talde bakoitza {@link Jokalaria} objektuen zerrenda batekin erlazionatuta dago,
 * jokalari bakoitzaren informazioa gordetzeko.
 * </p>
 * <p>
 * Klase honek Serializable interfaze-a inplementatzen du, datuak fitxategi batean gordetzeko aukera emateko.
 * </p>
 * 
 * @author ZureIzena
 * @version 1.0
 */
public class Taldea implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private String Izena;
	private String SorreraUrtea;
	private String Lehendakari;
	private int N_Bazkideak;
	private int PuntuakF;
	private int PuntuakC;
	private int PuntuTotalak;
	private int Irabazitakoak;
	private int Galdutakoak;
	private ArrayList<Jokalaria> Jokalariak;
	
	public Taldea(String izena,  String sorreraUrtea, String lehendakari, int n_Bazkideak, int puntuakF,
			int puntuakC, int puntuTotalak, int irabazitakoak, int galdutakoak, ArrayList<Jokalaria> jokalariak) {
		super();
		Izena = izena;
		SorreraUrtea = sorreraUrtea;
		Lehendakari = lehendakari;
		N_Bazkideak = n_Bazkideak;
		PuntuakF = puntuakF;
		PuntuakC = puntuakC;
		PuntuTotalak = puntuTotalak;
		Irabazitakoak = irabazitakoak;
		Galdutakoak = galdutakoak;
		this.Jokalariak = jokalariak;
	}

	public String getIzena() {
		return Izena;
	}

	public void setIzena(String izena) {
		Izena = izena;
	}

	public String getLehendakari() {
		return Lehendakari;
	}

	public void setLehendakari(String lehendakari) {
		Lehendakari = lehendakari;
	}

	public int getN_Bazkideak() {
		return N_Bazkideak;
	}

	public void setN_Bazkideak(int n_Bazkideak) {
		N_Bazkideak = n_Bazkideak;
	}

	public int getPuntuakF() {
		return PuntuakF;
	}

	public void setPuntuakF(int puntuakF) {
		PuntuakF = puntuakF;
	}

	public int getPuntuakC() {
		return PuntuakC;
	}

	public void setPuntuakC(int puntuakC) {
		PuntuakC = puntuakC;
	}

	public int getPuntuTotalak() {
		return PuntuTotalak;
	}

	public void setPuntuTotalak(int puntuTotalak) {
		PuntuTotalak = puntuTotalak;
	}

	public int getIrabazitakoak() {
		return Irabazitakoak;
	}

	public void setIrabazitakoak(int irabazitakoak) {
		Irabazitakoak = irabazitakoak;
	}

	public int getGaldutakoak() {
		return Galdutakoak;
	}

	public void setGaldutakoak(int galdutakoak) {
		Galdutakoak = galdutakoak;
	}

	public ArrayList<Jokalaria> getJokalariak() {
		return Jokalariak;
	}

	public void setJokalariak(ArrayList<Jokalaria> jokalariak) {
		this.Jokalariak = jokalariak;
	}

	public String getSorreraUrtea() {
		return SorreraUrtea;
	}
	@Override
	public String toString() {
	    String texto = "=== " + Izena + " ===\n";
	    texto += "Lehendakari: " + Lehendakari + "\n";
	    texto += "Sorrera Urtea: " + SorreraUrtea + "\n";
	    texto += "Baskide Kopurua: " + N_Bazkideak + "\n";
	    texto += "Jokalariak:\n";
	    for (Jokalaria j : Jokalariak) {
	        texto += "  - " + j + "\n"; // usa el toString() de Jokalaria
	    }
	    texto += "\n";
	    return texto;
	}
}