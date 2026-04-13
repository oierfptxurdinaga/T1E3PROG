package E2;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.*;

/**
 * Jardunaldi baten datuak eta horri lotutako partiduak biltzen dituen klasea.
 * XML fitxategiak sortzeko (JAXB) erabiltzen da, jardunaldi bakoitzaren emaitza
 * orokorrak eta partiduen zerrenda kapsulatuz. * @author Talde1
 * 
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Jaurdunaldia {

	/** Partiduaren identifikatzaile espezifikoa */
	private int idPar;

	/** Jardunaldiaren zenbakia edo identifikatzailea */
	private int idJaurdu;

	/** Jardunaldiko talde irabazlearen izena */
	private String taldeIrabazlea;

	/** Jardunaldiko talde galtzailearen izena */
	private String taldeGaldu;

	/**
	 * Jardunaldi honetan jokatutako partiduen zerrenda, XMLan 'partidua' gisa
	 * etiketatua
	 */
	@XmlElement(name = "partidua")
	private ArrayList<Partidua> partiduak = new ArrayList<>();

	/**
	 * Jardunaldiko partiduen zerrenda ezartzen du.
	 * 
	 * @param partiduak Partiduen ArrayList-a.
	 */
	public void setPartiduak(ArrayList<Partidua> partiduak) {
		this.partiduak = partiduak;
	}

	/**
	 * Eraikitzaile hutsa. OBLIGATORIOA da JAXB bidezko XML serializaziorako.
	 */
	public Jaurdunaldia() {
	}

	/**
	 * Jardunaldia objektu berri bat sortzeko eraikitzailea. * @param idPar
	 * Partiduaren IDa.
	 * 
	 * @param idJaurdu       Jardunaldiaren IDa.
	 * @param taldeIrabazlea Irabazi duen taldea.
	 * @param taldeGaldu     Galdu duen taldea.
	 */
	public Jaurdunaldia(int idPar, int idJaurdu, String taldeIrabazlea, String taldeGaldu) {
		this.idPar = idPar;
		this.idJaurdu = idJaurdu;
		this.taldeIrabazlea = taldeIrabazlea;
		this.taldeGaldu = taldeGaldu;
	}

	// --- Getters eta Setters ---

	/** @return Jardunaldiaren IDa */
	public int getIdJaurdu() {
		return idJaurdu;
	}

	/** @return Partiduaren ID orokorra */
	public int getIdPar() {
		return idPar;
	}

	/** @param idPar Partiduari esleitu nahi zaion ID berria */
	public void setIdPar(int idPar) {
		this.idPar = idPar;
	}

	/**
	 * * @return Talde irabazlearen izena. Null bada, "---" bueltatzen du.
	 */
	public String getTaldeIrabazlea() {
		return taldeIrabazlea != null ? taldeIrabazlea : "---";
	}

	/**
	 * * @return Talde galtzailearen izena. Null bada, "---" bueltatzen du.
	 */
	public String getTaldeGaldu() {
		return taldeGaldu != null ? taldeGaldu : "---";
	}

	/** @return Jardunaldi honetako partiduen zerrenda */
	public ArrayList<Partidua> getPartiduak() {
		return partiduak;
	}
}