package E2;

public abstract class ErabiltzaileMota {
	protected String Izena;
	protected String Abizena;
	protected String NAN;
	protected String Erabiltzailea;
	protected String Pasahitza;
	/**
	 * ErabiltzaileMota klase abstraktuak sistemako erabiltzaile mota guztiak
	 * definitzeko oinarrizko egitura eskaintzen du.
	 * <p>
	 * Klase honek erabiltzaile guztiek partekatzen dituzten datuak eta
	 * portaerak biltzen ditu, hala nola:
	 * </p>
	 * <ul>
	 *   <li>Izena</li>
	 *   <li>Abizena</li>
	 *   <li>NAN zenbakia</li>
	 *   <li>Erabiltzaile-izena</li>
	 *   <li>Pasahitza</li>
	 * </ul>
	 *
	 * <p>
	 * Klase hau ezin da zuzenean instantziatu, eta derrigorrez inplementatu
	 * behar da {@link #baimenak()} metodoa azpiklaseetan.
	 * </p>
	 *
	 * @author ZureIzena
	 * @version 1.0
	 */
	public ErabiltzaileMota(String izena, String abizena, String NAN, String erabiltzailea, String pasahitza) {
		this.Izena = izena;
		this.Abizena = abizena;
		this.NAN = NAN;
		this.Erabiltzailea = erabiltzailea;
		this.Pasahitza = pasahitza;
	}
	
	public String getErabiltzailea() {
		return Erabiltzailea;
	}
	
	public String getPasahitza() {
		return Pasahitza;
	}
	
	public abstract String baimenak();
}
