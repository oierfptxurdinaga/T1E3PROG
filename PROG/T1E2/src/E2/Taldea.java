package E2;

import java.io.Serializable;
import java.util.ArrayList;

// IMPORTANTE: javax para la BDOO
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

// JAXB (XML) se queda con jakarta según tu pom
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

/**
 * Taldea klaseak saskibaloi talde bat irudikatzen du.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Entity // Le dice a ObjectDB que guarde esta clase
public class Taldea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // El nombre del equipo será la clave primaria en la BDOO
	private String Izena;
	private String SorreraUrtea;
	private String Lehendakari;
	private int N_Bazkideak;
	private int PuntuakF;
	private int PuntuakC;
	private int PuntuTotalak;
	private int Irabazitakoak;
	private int Galdutakoak;

	@XmlElementWrapper(name = "jokalariak")
	@XmlElement(name = "jokalaria")
	@OneToMany(cascade = CascadeType.ALL) // Relación: Un equipo tiene muchos jugadores
	private ArrayList<Jokalaria> Jokalariak;

	public Taldea() {
	}

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

	public String getIzena() {
		return Izena;
	}

	public void setIzena(String izena) {
		this.Izena = izena;
	}

	public String getLehendakari() {
		return Lehendakari;
	}

	public void setLehendakari(String lehendakari) {
		this.Lehendakari = lehendakari;
	}

	public int getN_Bazkideak() {
		return N_Bazkideak;
	}

	public void setN_Bazkideak(int n_Bazkideak) {
		this.N_Bazkideak = n_Bazkideak;
	}

	public int getPuntuakF() {
		return PuntuakF;
	}

	public void setPuntuakF(int puntuakF) {
		this.PuntuakF = puntuakF;
	}

	public int getPuntuakC() {
		return PuntuakC;
	}

	public void setPuntuakC(int puntuakC) {
		this.PuntuakC = puntuakC;
	}

	public int getPuntuTotalak() {
		return PuntuTotalak;
	}

	public void setPuntuTotalak(int puntuTotalak) {
		this.PuntuTotalak = puntuTotalak;
	}

	public int getIrabazitakoak() {
		return Irabazitakoak;
	}

	public void setIrabazitakoak(int irabazitakoak) {
		this.Irabazitakoak = irabazitakoak;
	}

	public int getGaldutakoak() {
		return Galdutakoak;
	}

	public void setGaldutakoak(int galdutakoak) {
		this.Galdutakoak = galdutakoak;
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

	public void setSorreraUrtea(String sorreraUrtea) {
		SorreraUrtea = sorreraUrtea;
	}

	@Override
	public String toString() {
		return Izena;
	}
}