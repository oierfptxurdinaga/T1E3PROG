package E2;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "denboraldia")
@XmlAccessorType(XmlAccessType.FIELD)
public class Denboraldia {

	private String Data;
	private ArrayList<Jaurdunaldia> Jaurdunaldiak;
	private ArrayList<Puntuazioa> DenboraldiaP;

    @XmlElementWrapper(name = "taldeak")
    @XmlElement(name = "taldea")
    private ArrayList<Taldea> taldeak; 

    public Denboraldia() {
    }

	public Denboraldia(String data, ArrayList<Jaurdunaldia> jaurdunaldia, ArrayList<Puntuazioa> denboraldiaP) {
		super();
		Data = data;
		Jaurdunaldiak = jaurdunaldia;
		DenboraldiaP = denboraldiaP;
	}

    // Getters eta setters
	public String getData() { return Data; }
	public void setData(String data) { Data = data; }
	public ArrayList<Jaurdunaldia> getDenboraldia() { return Jaurdunaldiak; }
	public void setDenboraldia(ArrayList<Jaurdunaldia> jaurdunaldia) { Jaurdunaldiak = jaurdunaldia; }
	public ArrayList<Puntuazioa> getDenboraldiaP() { return DenboraldiaP; }
	public void setDenboraldiaP(ArrayList<Puntuazioa> denboraldiaP) { DenboraldiaP = denboraldiaP; }
	public ArrayList<Taldea> getTaldeak() { return taldeak; }
	public void setTaldeak(ArrayList<Taldea> taldeak) { this.taldeak = taldeak; }
}