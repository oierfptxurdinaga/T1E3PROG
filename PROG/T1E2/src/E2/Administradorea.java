package E2;

/**
 * Administradorea klaseak aplikazioko administratzaile erabiltzailea irudikatzen du.
 * <p>
 * {@link ErabiltzaileMota} klasetik heredatzen du eta administratzaileari
 * dagozkion baimenak ezartzen ditu.
 * </p>
 *
 * <p>
 * Administratzaileak aplikazioan honako ahalmenak ditu:
 * </p>
 * <ul>
 *   <li>Partiden emaitzak sartzea</li>
 *   <li>Datuak gordetzea eta kudeatzea</li>
 * </ul>
 *
 * @author ZureIzena
 * @version 1.0
 */
public class Administradorea extends ErabiltzaileMota {
	/**
     * Administradorea objektuaren eraikitzailea.
     *
     * @param izena administratzailearen izena
     * @param abizena administratzailearen abizena
     * @param NAN administratzailearen NAN zenbakia
     * @param erabiltzailea erabiltzaile-izena
     * @param pasahitza pasahitza
     */
	public Administradorea(String izena, String abizena, String NAN, String erabiltzailea, String pasahitza) {
		super(izena, abizena, NAN, erabiltzailea, pasahitza);
		
	}
	/**
     * Administratzailearen izena itzultzen du.
     *
     * @return administratzailearen izena
     */
	public String getizena() {
		return Izena;
	}
	/**
     * Administratzailearen abizena itzultzen du.
     *
     * @return administratzailearen abizena
     */
	public String getabizena() {
		return Abizena;
	}
	/**
     * Administratzailearen NAN zenbakia itzultzen du.
     *
     * @return administratzailearen NAN-a
     */
	public String getNAN() {
		return NAN;
	}
	/**
     * Administratzailearen erabiltzaile-izena itzultzen du.
     *
     * @return erabiltzaile-izena
     */
	public String geterabiltzailea() {
		return Erabiltzailea;
	}
	 /**
     * Administratzailearen pasahitza itzultzen du.
     *
     * @return pasahitza
     */
	public String getpasahitza() {
		return Pasahitza;
	}
	/**
     * Administratzailearen izena ezartzen du.
     *
     * @param izena izen berria
     */
	public void setizena(String izena) {
		Izena = izena;
	}
	/**
     * Administratzailearen abizena ezartzen du.
     *
     * @param abizena abizen berria
     */
	public void setabizena(String abizena) {
		Abizena = abizena;
	}
	/**
     * Administratzailearen erabiltzaile-izena ezartzen du.
     *
     * @param erabiltzailea erabiltzaile-izen berria
     */
	public void seterabiltzailea(String erabiltzailea) {
		Erabiltzailea = erabiltzailea;
	}
	/**
     * Administratzailearen pasahitza ezartzen du.
     *
     * @param pasahitza pasahitz berria
     */
	public void setpasahitza(String pasahitza) {
		Pasahitza = pasahitza;
	}
	/**
     * Administratzailearen baimen maila adierazten du.
     *
     * @return "Admin" kate literala
     */
	@Override
	public String baimenak() {
		
		return "Admin";
	}

}
