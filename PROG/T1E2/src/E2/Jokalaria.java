package E2;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Ligako jokalari baten informazioa irudikatzen duen entitatea. Klase honek
 * serializazioa, XML bidezko bihurketa (JAXB) eta datu-base objektualetako
 * iraunkortasuna (ObjectDB) onartzen ditu. * @author Talde1
 * 
 * @version 1.0
 */
@XmlRootElement(name = "jokalaria")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity // ObjectDB-ri entitate hau gorde behar duela adierazten dio
public class Jokalaria implements Serializable, Comparable<Jokalaria> {

	/** Serializaziorako bertsio identifikatzailea */
	private static final long serialVersionUID = 1L;

	private String Izena;
	private String Abizena;
	private String JaiotzeData;

	@Id // ObjectDB-n gako nagusi gisa NAN-a erabiltzen da
	private String NAN;
	private String Taldea;
	private int Prezioa;
	private int JokalarienPuntuak;

	/**
	 * Eraikitzaile hutsa. Beharrezkoa da JAXB, JPA eta beste teknologia batzuek
	 * objektua instantzietarako.
	 */
	public Jokalaria() {
	}

	/**
	 * Jokalari berri bat sortzeko eraikitzaile osoa. * @param izena Jokalariaren
	 * izena.
	 * 
	 * @param abizena     Jokalariaren abizena.
	 * @param jaiotzeData Jaiotze data (YYYY-MM-DD formatuan normalean).
	 * @param nAN         Jokalariaren NAN-a (identifikatzaile bakarra).
	 * @param taldea      Esleitutako taldearen izena.
	 * @param prezioa     Merkatuko balioa edo prezioa.
	 */
	public Jokalaria(String izena, String abizena, String jaiotzeData, String nAN, String taldea, int prezioa) {
		super();
		Izena = izena;
		Abizena = abizena;
		JaiotzeData = jaiotzeData;
		NAN = nAN;
		Taldea = taldea;
		Prezioa = prezioa;
	}

	/**
	 * Kopia-eraikitzailea. Jokalari baten datuak beste objektu batera klonatzeko.
	 * * @param besteJokalariBat Kopiatu nahi den jokalari objektua.
	 */
	public Jokalaria(Jokalaria besteJokalariBat) {
		super();
		this.Izena = besteJokalariBat.Izena;
		this.Abizena = besteJokalariBat.Abizena;
		this.JaiotzeData = besteJokalariBat.JaiotzeData;
		this.NAN = besteJokalariBat.NAN;
		this.Taldea = besteJokalariBat.Taldea;
		this.Prezioa = besteJokalariBat.Prezioa;
	}

	// --- Getters eta Setters ---

	/** @return Jokalariaren izena */
	public String getIzena() {
		return Izena;
	}

	/** @param izena Jokalariari esleitu nahi zaion izena */
	public void setIzena(String izena) {
		Izena = izena;
	}

	/** @return Jokalariaren abizena */
	public String getAbizena() {
		return Abizena;
	}

	/** @param abizena Jokalariari esleitu nahi zaion abizena */
	public void setAbizena(String abizena) {
		Abizena = abizena;
	}

	/** @return Jokalariaren taldea */
	public String getTaldea() {
		return Taldea;
	}

	/** @param taldea Jokalariari esleitu nahi zaion talde berria */
	public void setTaldea(String taldea) {
		Taldea = taldea;
	}

	/** @return Jokalariaren merkatuko prezioa */
	public int getPrezioa() {
		return Prezioa;
	}

	/** @param prezioa Jokalariari esleitu nahi zaion prezioa */
	public void setPrezioa(int prezioa) {
		Prezioa = prezioa;
	}

	/** @return Jaiotze data */
	public String getJaiotzeData() {
		return JaiotzeData;
	}

	/** @return Jokalariaren NAN-a */
	public String getNAN() {
		return NAN;
	}

	/** @param nAN Jokalariari esleitu nahi zaion NAN berria */
	public void setNAN(String nAN) {
		NAN = nAN;
	}

	/**
	 * Jokalariak abizenaren arabera ordenatzeko metodoa (eta abizen berdina bada,
	 * izenaren arabera). * @param besteJokalaria Konparatu nahi den beste
	 * jokalaria.
	 * 
	 * @return Konparaketaren emaitza (negatiboa, zero edo positiboa).
	 */
	@Override
	public int compareTo(Jokalaria besteJokalaria) {
		int resultado = this.Abizena.compareToIgnoreCase(besteJokalaria.getAbizena());
		if (resultado == 0) {
			resultado = this.Izena.compareToIgnoreCase(besteJokalaria.getIzena());
		}
		return resultado;
	}

	/**
	 * Hash kodea sortzen du, abizena eta izena kontuan hartuta.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Abizena, Izena);
	}

	/**
	 * Bi jokalari berdinak diren egiaztatzen du (izena eta abizena alderatuz).
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jokalaria other = (Jokalaria) obj;
		return Objects.equals(Abizena, other.Abizena) && Objects.equals(Izena, other.Izena);
	}

	/**
	 * Jokalariaren informazioa testu formatuan itzultzen du.
	 */
	@Override
	public String toString() {
		return Izena + " " + Abizena + " [" + NAN + "]";
	}
}