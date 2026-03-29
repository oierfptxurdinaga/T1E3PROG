package E2;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "denboraldia")
@XmlAccessorType(XmlAccessType.FIELD)
public class Denboraldia {

	private String Data;

	@XmlElementWrapper(name = "jaurdunaldiak")
	@XmlElement(name = "jaurdunaldia")
	private ArrayList<Jaurdunaldia> Jaurdunaldiak;

	private ArrayList<Puntuazioa> DenboraldiaP;

	@XmlElementWrapper(name = "taldeak")
	@XmlElement(name = "taldea")
	private ArrayList<Taldea> taldeak;

	// 1. Constructor vacío (OBLIGATORIO para JAXB)
	public Denboraldia() {
		this.Jaurdunaldiak = new ArrayList<>();
		this.DenboraldiaP = new ArrayList<>();
		this.taldeak = new ArrayList<>();
	}

	// 2. Constructor completo (Asegúrate de que incluya taldeak)
	public Denboraldia(String data, ArrayList<Jaurdunaldia> jaurdunaldiak, ArrayList<Taldea> taldeak) {
		this.Data = data;
		this.Jaurdunaldiak = jaurdunaldiak;
		this.taldeak = taldeak;
		this.DenboraldiaP = new ArrayList<>(); // Inicializamos para evitar NullPointer
	}

	// --- Getters y Setters ---
	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public ArrayList<Jaurdunaldia> getJaurdunaldiak() {
		return Jaurdunaldiak;
	}

	public void setJaurdunaldiak(ArrayList<Jaurdunaldia> jaurdunaldiak) {
		Jaurdunaldiak = jaurdunaldiak;
	}

	public ArrayList<Puntuazioa> getDenboraldiaP() {
		return DenboraldiaP;
	}

	public void setDenboraldiaP(ArrayList<Puntuazioa> denboraldiaP) {
		DenboraldiaP = denboraldiaP;
	}

	public ArrayList<Taldea> getTaldeak() {
		return taldeak;
	}

	public void setTaldeak(ArrayList<Taldea> taldeak) {
		this.taldeak = taldeak;
	}
}