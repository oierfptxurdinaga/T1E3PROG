package E2;

import java.util.ArrayList;
/**
 * Jaurdunaldia klaseak ligako jardunaldi bat irudikatzen du.
 * <p>
 * Jardunaldi batek partida multzo bat biltzen du, eta normalean
 * denboraldi baten barruan kokatzen da.
 * </p>
 *
 * <p>
 * Klase hau {@link Denboraldia} klasearekin erlazionatuta dago,
 * denboraldiko jardunaldiak antolatzeko erabiltzen baita.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */
public class Jaurdunaldia {
	private ArrayList<Partidua> jaurdunaldia;

	public Jaurdunaldia(ArrayList<Partidua> jaurdunaldia) {
		super();
		this.jaurdunaldia = jaurdunaldia;
	}

	public ArrayList<Partidua> getJaurdunaldia() {
		return jaurdunaldia;
	}

	public void setJaurdunaldia(ArrayList<Partidua> jaurdunaldia) {
		this.jaurdunaldia = jaurdunaldia;
	}
}