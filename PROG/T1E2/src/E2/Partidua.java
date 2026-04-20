package E2;

import jakarta.xml.bind.annotation.*;

/**
 * Partidu baten informazio zehatza irudikatzen duen klasea. Bi taldeen izenak,
 * lortutako emaitzak eta partidua jokatu den data eta ordua kudeatzen ditu.
 * JAXB bidez XML serializaziorako erabilia. * @author Talde1
 * 
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Partidua {

	/** Partiduaren identifikatzaile bakarra */
	private int Id_Par;

	/** Etxeko taldearen izena */
	private String TaldeLokala;

	/** Kanpoko taldearen izena */
	private String TaldeBisitari;

	/** Etxeko taldeak sartutako puntu edo gol kopurua */
	private int ResultLokala;

	/** Kanpoko taldeak sartutako puntu edo gol kopurua */
	private int ResulBisitari;

	/** Partidua jokatu den eguna (YYYY-MM-DD formatuan) */
	private String Data;

	/** Partidua hasi den ordua (HH:MM:SS formatuan) */
	private String Ordua;

	/**
	 * Eraikitzaile hutsa. Beharrezkoa da JAXB teknologiarekin XML datuak
	 * kudeatzeko.
	 */
	public Partidua() {
	}

	/**
	 * Partidu objektu berri bat sortzeko eraikitzaile osoa. * @param id_Par
	 * Partiduaren IDa.
	 * 
	 * @param taldeLokala   Etxeko taldearen izena.
	 * @param taldeBisitari Kanpoko taldearen izena.
	 * @param resultLokala  Etxekoen emaitza.
	 * @param resulBisitari Kanpokoen emaitza.
	 * @param data          Jokatu den data.
	 * @param ordua         Jokatu den ordua.
	 */
	public Partidua(int id_Par, String taldeLokala, String taldeBisitari, int resultLokala, int resulBisitari,
			String data, String ordua) {
		super();
		Id_Par = id_Par;
		TaldeLokala = taldeLokala;
		TaldeBisitari = taldeBisitari;
		ResultLokala = resultLokala;
		ResulBisitari = resulBisitari;
		Data = data;
		Ordua = ordua;
	}

	// --- Getters eta Setters ---

	/** @return Partiduaren IDa */
	public int getId_Par() {
		return Id_Par;
	}

	/** @param id_Par Partiduari esleitu nahi zaion ID berria */
	public void setId_Par(int id_Par) {
		Id_Par = id_Par;
	}

	/** @return Etxeko taldearen izena */
	public String getTaldeLokala() {
		return TaldeLokala;
	}

	/** @param taldeLokala Etxeko talde berria ezartzeko */
	public void setTaldeLokala(String taldeLokala) {
		TaldeLokala = taldeLokala;
	}

	/** @return Kanpoko taldearen izena */
	public String getTaldeBisitari() {
		return TaldeBisitari;
	}

	/** @param taldeBisitari Kanpoko talde berria ezartzeko */
	public void setTaldeBisitari(String taldeBisitari) {
		TaldeBisitari = taldeBisitari;
	}

	/** @return Etxekoen emaitza */
	public int getResultLokala() {
		return ResultLokala;
	}

	/** @param resultLokala Etxekoen puntuazio berria ezartzeko */
	public void setResultLokala(int resultLokala) {
		ResultLokala = resultLokala;
	}

	/** @return Kanpokoen emaitza */
	public int getResulBisitari() {
		return ResulBisitari;
	}

	/** @param resulBisitari Kanpokoen puntuazio berria ezartzeko */
	public void setResulBisitari(int resulBisitari) {
		ResulBisitari = resulBisitari;
	}

	/** @return Partiduko data */
	public String getData() {
		return Data;
	}

	/** @param data Partiduari esleitu nahi zaion data berria */
	public void setData(String data) {
		Data = data;
	}

	/** @return Partiduko ordua */
	public String getOrdua() {
		return Ordua;
	}

	/** @param ordua Partiduari esleitu nahi zaion ordu berria */
	public void setOrdua(String ordua) {
		Ordua = ordua;
	}
}