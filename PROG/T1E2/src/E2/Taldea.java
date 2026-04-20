package E2;

import java.io.Serializable;
import java.util.ArrayList;

// IMPORTANTEA: javax erabiltzen da ObjectDB-rako (BDOO)
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

// JAXB (XML) kudeatzeko jakarta erabiltzen da
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

/**
 * Saskibaloi talde bat irudikatzen duen klasea. Klase honek taldearen
 * estatistikak, bazkide kopurua eta jokalarien zerrenda kudeatzen ditu.
 * ObjectDB (BDOO) eta JAXB (XML) bidezko iraunkortasuna onartzen du. * @author
 * Talde1
 * 
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Entity // ObjectDB-ri entitate hau gorde behar duela adierazten dio
public class Taldea implements Serializable {

	/** Serializaziorako bertsio identifikatzailea */
	private static final long serialVersionUID = 1L;

	@Id // Taldearen izena gako nagusia (Primary Key) izango da datu-basean
	private String Izena;
	private String SorreraUrtea;
	private String Lehendakari;
	private int N_Bazkideak;
	private int PuntuakF; // Aldeko puntuak
	private int PuntuakC; // Kontrako puntuak
	private int PuntuTotalak; // Sailkapeneko puntuazioa
	private int Irabazitakoak; // Irabazitako partidu kopurua
	private int Galdutakoak; // Galdutako partidu kopurua

	/**
	 * * Talde honetako jokalarien zerrenda. XML-an 'jokalariak' taldearen barruan
	 * gordetzen da. ObjectDB-n 'bat-askotara' erlazioa ezartzen du.
	 */
	@XmlElementWrapper(name = "jokalariak")
	@XmlElement(name = "jokalaria")
	@OneToMany(cascade = CascadeType.ALL) // Taldea borratzean, jokalariak ere kudeatzen dira
	private ArrayList<Jokalaria> Jokalariak;

	/**
	 * Eraikitzaile hutsa. Beharrezkoa da JAXB eta ObjectDB teknologiekin lan
	 * egiteko.
	 */
	public Taldea() {
	}

	/**
	 * Talde bat sortzeko eraikitzaile osoa. * @param izena Taldearen izen berezia.
	 * 
	 * @param sorreraUrtea  Taldea sortu zen urtea edo data.
	 * @param lehendakari   Taldeko lehendakariaren izena.
	 * @param n_Bazkideak   Bazkide kopurua.
	 * @param puntuakF      Sartutako puntuak guztira.
	 * @param puntuakC      Jasotako puntuak guztira.
	 * @param puntuTotalak  Ligako sailkapenerako puntuak.
	 * @param irabazitakoak Garaipen kopurua.
	 * @param galdutakoak   Galera kopurua.
	 * @param jokalariak    Taldea osatzen duten jokalarien zerrenda.
	 */
	public Taldea(String izena, String sorreraUrtea, String lehendakari, int n_Bazkideak, int puntuakF, int puntuakC,
			int puntuTotalak, int irabazitakoak, int galdutakoak, ArrayList<Jokalaria> jokalariak) {
		super();
		this.Izena = izena;
		this.SorreraUrtea = sorreraUrtea;
		this.Lehendakari = lehendakari;
		this.N_Bazkideak = n_Bazkideak;
		this.PuntuakF = puntuakF;
		this.PuntuakC = puntuakC;
		this.PuntuTotalak = puntuTotalak;
		this.Irabazitakoak = irabazitakoak;
		this.Galdutakoak = galdutakoak;
		this.Jokalariak = jokalariak;
	}

	// --- Getters eta Setters ---

	/** @return Taldearen izena */
	public String getIzena() {
		return Izena;
	}

	/** @param izena Taldearen izen berria */
	public void setIzena(String izena) {
		this.Izena = izena;
	}

	/** @return Lehendakariaren izena */
	public String getLehendakari() {
		return Lehendakari;
	}

	/** @param lehendakari Lehendakari berria */
	public void setLehendakari(String lehendakari) {
		this.Lehendakari = lehendakari;
	}

	/** @return Bazkide kopurua */
	public int getN_Bazkideak() {
		return N_Bazkideak;
	}

	/** @param n_Bazkideak Bazkide kopuru berria */
	public void setN_Bazkideak(int n_Bazkideak) {
		this.N_Bazkideak = n_Bazkideak;
	}

	/** @return Aldeko puntuak guztira */
	public int getPuntuakF() {
		return PuntuakF;
	}

	/** @param puntuakF Aldeko puntu berriak */
	public void setPuntuakF(int puntuakF) {
		this.PuntuakF = puntuakF;
	}

	/** @return Kontrako puntuak guztira */
	public int getPuntuakC() {
		return PuntuakC;
	}

	/** @param puntuakC Kontrako puntu berriak */
	public void setPuntuakC(int puntuakC) {
		this.PuntuakC = puntuakC;
	}

	/** @return Sailkapeneko puntuazio totala */
	public int getPuntuTotalak() {
		return PuntuTotalak;
	}

	/** @param puntuTotalak Puntuazio total berria */
	public void setPuntuTotalak(int puntuTotalak) {
		this.PuntuTotalak = puntuTotalak;
	}

	/** @return Irabazitako partidu kopurua */
	public int getIrabazitakoak() {
		return Irabazitakoak;
	}

	/** @param irabazitakoak Irabazitakoen kopuru berria */
	public void setIrabazitakoak(int irabazitakoak) {
		this.Irabazitakoak = irabazitakoak;
	}

	/** @return Galdutako partidu kopurua */
	public int getGaldutakoak() {
		return Galdutakoak;
	}

	/** @param galdutakoak Galdutakoen kopuru berria */
	public void setGaldutakoak(int galdutakoak) {
		this.Galdutakoak = galdutakoak;
	}

	/** @return Taldeko jokalarien zerrenda */
	public ArrayList<Jokalaria> getJokalariak() {
		return Jokalariak;
	}

	/** @param jokalariak Jokalari zerrenda berria esleitu */
	public void setJokalariak(ArrayList<Jokalaria> jokalariak) {
		this.Jokalariak = jokalariak;
	}

	/** @return Taldearen sorrera urtea */
	public String getSorreraUrtea() {
		return SorreraUrtea;
	}

	/** @param sorreraUrtea Sorrera urtea ezarri */
	public void setSorreraUrtea(String sorreraUrtea) {
		SorreraUrtea = sorreraUrtea;
	}

	/**
	 * Taldearen izena bueltatzen du testu gisa.
	 */
	@Override
	public String toString() {
		return Izena;
	}
}