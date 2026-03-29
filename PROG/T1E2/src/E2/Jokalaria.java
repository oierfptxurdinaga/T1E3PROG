package E2;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Jokalaria klaseak ligako jokalari bat irudikatzen du.
 */
@XmlRootElement(name = "jokalaria")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity // Esto le dice a ObjectDB que guarde esta clase
public class Jokalaria implements Serializable, Comparable<Jokalaria> {
	private static final long serialVersionUID = 1L;

	private String Izena;
	private String Abizena;
	private String JaiotzeData;

	@Id 
	private String NAN;
	private String Taldea;
	private int Prezioa;
	private int JokalarienPuntuak;
	
	public Jokalaria() {
	}

	public Jokalaria(String izena, String abizena, String jaiotzeData, String nAN, String taldea, int prezioa) {
		super();
		Izena = izena;
		Abizena = abizena;
		JaiotzeData = jaiotzeData;
		NAN = nAN;
		Taldea = taldea;
		Prezioa = prezioa;
	}

	public Jokalaria(Jokalaria besteJokalariBat) {
		super();
		this.Izena = besteJokalariBat.Izena;
		this.Abizena = besteJokalariBat.Abizena;
		this.JaiotzeData = besteJokalariBat.JaiotzeData;
		this.NAN = besteJokalariBat.NAN;
		this.Taldea = besteJokalariBat.Taldea;
		this.Prezioa = besteJokalariBat.Prezioa;
	}

	public String getIzena() {
		return Izena;
	}

	public void setIzena(String izena) {
		Izena = izena;
	}

	public String getAbizena() {
		return Abizena;
	}

	public void setAbizena(String abizena) {
		Abizena = abizena;
	}

	public String getTaldea() {
		return Taldea;
	}

	public void setTaldea(String taldea) {
		Taldea = taldea;
	}

	public int getPrezioa() {
		return Prezioa;
	}

	public void setPrezioa(int prezioa) {
		Prezioa = prezioa;
	}

	public String getJaiotzeData() {
		return JaiotzeData;
	}

	public String getNAN() {
		return NAN;
	}

	public void setNAN(String nAN) {
		NAN = nAN;
	}

	@Override
	public int compareTo(Jokalaria besteJokalaria) {
		int resultado = this.Abizena.compareToIgnoreCase(besteJokalaria.getAbizena());
		if (resultado == 0) {
			resultado = this.Izena.compareToIgnoreCase(besteJokalaria.getIzena());
		}
		return resultado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Abizena, Izena);
	}

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

	@Override
	public String toString() {
		return Izena + " " + Abizena + " [" + NAN + "]";
	}
}