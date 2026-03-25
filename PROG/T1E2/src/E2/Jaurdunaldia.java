package E2;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Jaurdunaldia {
	private int idPar;
	private int idJaurdu;
	private String taldeIrabazlea;
	private String taldeGaldu;

	@XmlElement(name = "partidua")
	private ArrayList<Partidua> partiduak = new ArrayList<>();

	public void setPartiduak(ArrayList<Partidua> partiduak) {
		this.partiduak = partiduak;
	}

	public Jaurdunaldia() {
	}

	public Jaurdunaldia(int idPar, int idJaurdu, String taldeIrabazlea, String taldeGaldu) {
		this.idPar = idPar;
		this.idJaurdu = idJaurdu;
		this.taldeIrabazlea = taldeIrabazlea;
		this.taldeGaldu = taldeGaldu;
	}

	// Getters eta Setters
	public int getIdJaurdu() {
		return idJaurdu;
	}

	public int getIdPar() {
		return idPar;
	}

	public void setIdPar(int idPar) {
		this.idPar = idPar;
	}

	public String getTaldeIrabazlea() {
		return taldeIrabazlea != null ? taldeIrabazlea : "---";
	}

	public String getTaldeGaldu() {
		return taldeGaldu != null ? taldeGaldu : "---";
	}
}