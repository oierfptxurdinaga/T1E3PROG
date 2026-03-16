package E2;

import java.io.Serializable;
import java.util.Objects;
/**
 * Jokalaria klaseak ligako jokalari bat irudikatzen du.
 * <p>
 * Klase honek {@link Serializable} interfazea inplementatzen du,
 * jokalarien datuak fitxategietan gorde ahal izateko, eta
 * {@link Comparable} interfazea ere bai, jokalariak ordenatzeko.
 * </p>
 *
 * <p>
 * Jokalariak abizenaren eta izenaren arabera ordenatzen dira.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */
public class Jokalaria implements Serializable, Comparable<Jokalaria> {
	private static final long serialVersionUID = 1L;
	
	
	private String Izena;
	private String Abizena;
	private String JaiotzeData;
	private String NAN;
	private String Taldea;
	private int Prezioa;
	private int JokalarienPuntuak;

	
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
	@Override
	public int compareTo(Jokalaria besteJokalaria) {
	    // Primero comparamos por Apellido (Abizena)
	    int resultado = this.Abizena.compareToIgnoreCase(besteJokalaria.getAbizena());
	    
	    // Si los apellidos son iguales, comparamos por Nombre (Izena)
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
	
}