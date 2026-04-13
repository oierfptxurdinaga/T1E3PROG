package E2;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.*;

/**
 * Denboraldi baten datu guztiak biltzen dituen klasea. XML fitxategiak sortzeko
 * (JAXB) erroko elementu gisa erabiltzen da, jaurdunaldien, taldeen eta
 * puntuazioen zerrendak kapsulatuz. * @author Talde1
 * 
 * @version 1.0
 */
@XmlRootElement(name = "denboraldia")
@XmlAccessorType(XmlAccessType.FIELD)
public class Denboraldia {

	/** Denboraldiaren data edo izena (adibidez: "2024-2025") */
	private String Data;

	/**
	 * Jaurdunaldien zerrenda, XMLan 'jaurdunaldiak' taldearen barruan egituratua
	 */
	@XmlElementWrapper(name = "jaurdunaldiak")
	@XmlElement(name = "jaurdunaldia")
	private ArrayList<Jaurdunaldia> Jaurdunaldiak;

	/** Denboraldiko puntuazioen zerrenda */
	private ArrayList<Puntuazioa> DenboraldiaP;

	/** Taldeen zerrenda, XMLan 'taldeak' taldearen barruan egituratua */
	@XmlElementWrapper(name = "taldeak")
	@XmlElement(name = "taldea")
	private ArrayList<Taldea> taldeak;

	/**
	 * Eraikitzaile hutsa. OBLIGATORIOA da JAXB teknologiarekin datuak kudeatzeko.
	 * Zerrendak hasieratzen ditu NullPointerException-ak ekiditeko.
	 */
	public Denboraldia() {
		this.Jaurdunaldiak = new ArrayList<>();
		this.DenboraldiaP = new ArrayList<>();
		this.taldeak = new ArrayList<>();
	}

	/**
	 * Denboraldia objektu osoa sortzeko eraikitzailea. * @param data Denboraldiaren
	 * identifikazio data.
	 * 
	 * @param jaurdunaldiak Denboraldian jokatu diren jaurdunaldiak.
	 * @param taldeak       Parte hartzen duten taldeen zerrenda.
	 */
	public Denboraldia(String data, ArrayList<Jaurdunaldia> jaurdunaldiak, ArrayList<Taldea> taldeak) {
		this.Data = data;
		this.Jaurdunaldiak = jaurdunaldiak;
		this.taldeak = taldeak;
		this.DenboraldiaP = new ArrayList<>(); // Hasieratu beti segurtasunagatik
	}

	// --- Getters eta Setters ---

	/** @return Denboraldiaren data */
	public String getData() {
		return Data;
	}

	/** @param data Denboraldiari esleitu nahi zaion data */
	public void setData(String data) {
		Data = data;
	}

	/** @return Jaurdunaldien zerrenda */
	public ArrayList<Jaurdunaldia> getJaurdunaldiak() {
		return Jaurdunaldiak;
	}

	/** @param jaurdunaldiak Jaurdunaldien zerrenda berria ezartzeko */
	public void setJaurdunaldiak(ArrayList<Jaurdunaldia> jaurdunaldiak) {
		Jaurdunaldiak = jaurdunaldiak;
	}

	/** @return Denboraldiko puntuazioen zerrenda */
	public ArrayList<Puntuazioa> getDenboraldiaP() {
		return DenboraldiaP;
	}

	/** @param denboraldiaP Puntuazioen zerrenda berria ezartzeko */
	public void setDenboraldiaP(ArrayList<Puntuazioa> denboraldiaP) {
		DenboraldiaP = denboraldiaP;
	}

	/** @return Parte hartzen duten taldeen zerrenda */
	public ArrayList<Taldea> getTaldeak() {
		return taldeak;
	}

	/** @param taldeak Taldeen zerrenda berria ezartzeko */
	public void setTaldeak(ArrayList<Taldea> taldeak) {
		this.taldeak = taldeak;
	}
}