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
    private int idPar;
    private int idJaurdu;
    private String taldeIrabazlea;
    private String taldeGaldu;

    public Jaurdunaldia(int idPar, int idJaurdu, String taldeIrabazlea, String taldeGaldu) {
        this.idPar = idPar;
        this.idJaurdu = idJaurdu;
        this.taldeIrabazlea = taldeIrabazlea;
        this.taldeGaldu = taldeGaldu;
    }

    // Getters
    
    public int getIdJaurdu() { return idJaurdu; }
    public int getIdPar() {
		return idPar;
	}

	public void setIdPar(int idPar) {
		this.idPar = idPar;
	}

	public String getTaldeIrabazlea() { return taldeIrabazlea != null ? taldeIrabazlea : "---"; }
    public String getTaldeGaldu() { return taldeGaldu != null ? taldeGaldu : "---"; }
}