package E2;

import java.util.ArrayList;
/**
 * Puntuazioa klaseak ligako puntuazio edo sailkapen orokorra
 * irudikatzen du.
 * <p>
 * Klase honek {@link Taldea} objektuen zerrenda bat gordetzen du,
 * talde bakoitzaren egoera edo puntuazioa adierazteko.
 * </p>
 *
 * <p>
 * Normalean {@link Denboraldia} klasearekin erlazionatuta erabiltzen da,
 * denboraldi bateko sailkapenak kudeatzeko.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */
public class Puntuazioa {
	private ArrayList<Taldea> Puntuazioa;

	public Puntuazioa(ArrayList<Taldea> puntuazioa) {
		super();
		Puntuazioa = puntuazioa;
	}

	public ArrayList<Taldea> getPuntuazioa() {
		return Puntuazioa;
	}

	public void setPuntuazioa(ArrayList<Taldea> puntuazioa) {
		Puntuazioa = puntuazioa;
	}
}