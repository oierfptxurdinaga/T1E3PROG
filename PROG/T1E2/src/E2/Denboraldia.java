package E2;

import java.util.ArrayList;
/**
 * Denboraldia klaseak ligako denboraldi bat irudikatzen du.
 * <p>
 * Denboraldi batek honako informazioa biltzen du:
 * </p>
 * <ul>
 *   <li>Denboraldiaren data edo identifikatzailea</li>
 *   <li>Jardunaldi guztien zerrenda</li>
 *   <li>Denboraldi osoko puntuazioen zerrenda</li>
 * </ul>
 *
 * <p>
 * Klase hau ligaren egitura modelatzeko erabiltzen da.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */
public class Denboraldia {
	private String Data;
	private ArrayList<Jaurdunaldia> Denboraldia;
	private ArrayList<Puntuazioa> DenboraldiaP;
	public Denboraldia(String data, ArrayList<Jaurdunaldia> denboraldia, ArrayList<Puntuazioa> denboraldiaP) {
		super();
		Data = data;
		Denboraldia = denboraldia;
		DenboraldiaP = denboraldiaP;
	}
	
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public ArrayList<Jaurdunaldia> getDenboraldia() {
		return Denboraldia;
	}
	public void setDenboraldia(ArrayList<Jaurdunaldia> denboraldia) {
		Denboraldia = denboraldia;
	}
	public ArrayList<Puntuazioa> getDenboraldiaP() {
		return DenboraldiaP;
	}
	public void setDenboraldiaP(ArrayList<Puntuazioa> denboraldiaP) {
		DenboraldiaP = denboraldiaP;
	}
}