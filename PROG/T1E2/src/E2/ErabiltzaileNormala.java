package E2;
/**
 * ErabiltzaileNormala klaseak sistemako erabiltzaile arrunt bat
 * irudikatzen du.
 * <p>
 * Klase honek {@link ErabiltzaileMota} klase abstraktutik heredatzen du
 * eta erabiltzaile arruntentzako baimen maila ezartzen du.
 * </p>
 *
 * <p>
 * Erabiltzaile arruntek normalean kontsulta edo oinarrizko ekintzak
 * egiteko baimenak dituzte.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */
public class ErabiltzaileNormala extends ErabiltzaileMota {

	public ErabiltzaileNormala(String izena, String abizena, String NAN, String erabiltzailea, String pasahitza) {
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
		return "Arrunta";
	}

}
