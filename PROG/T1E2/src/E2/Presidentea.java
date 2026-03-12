package E2;
/**
 * Presidentea klaseak sistemako presidente erabiltzaile bat
 * irudikatzen du.
 * <p>
 * Klase honek {@link ErabiltzaileMota} klase abstraktutik heredatzen du
 * eta talde edo ligaren kudeaketarekin lotutako baimenak ditu.
 * </p>
 *
 * <p>
 * Presidente erabiltzaileek normalean administrazio-mailako edo
 * erabaki garrantzitsuak hartzeko baimenak izaten dituzte.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */
public class Presidentea extends ErabiltzaileMota {

	public Presidentea(String izena, String abizena, String NAN, String erabiltzailea, String pasahitza) {
		super(izena, abizena, NAN, erabiltzailea, pasahitza);
	}

	public String getizena() {
		return Izena;
	}
	public String getabizena() {
		return Abizena;
	}
	public String getNAN() {
		return NAN;
	}
	public String geterabiltzailea() {
		return Erabiltzailea;
	}
	public String getpasahitza() {
		return Pasahitza;
	}
	public void setizena(String izena) {
		Izena = izena;
	}
	public void setabizena(String abizena) {
		Abizena = abizena;
	}
	public void seterabiltzailea(String erabiltzailea) {
		Erabiltzailea = erabiltzailea;
	}
	public void setpasahitza(String pasahitza) {
		Pasahitza = pasahitza;
	}
	
	@Override
	public String baimenak() {
		return "Presidentea";
	}
}