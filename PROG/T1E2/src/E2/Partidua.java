package E2;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Partidua {
	private int Id_Par;
	private String TaldeLokala;
	private String TaldeBisitari;
	private int ResultLokala;
	private int ResulBisitari;
	private String Data;
	private String Ordua;

	public Partidua() {
	} 

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

	// Getters eta Setters 
	public int getId_Par() {
		return Id_Par;
	}

	public void setId_Par(int id_Par) {
		Id_Par = id_Par;
	}

	public String getTaldeLokala() {
		return TaldeLokala;
	}

	public void setTaldeLokala(String taldeLokala) {
		TaldeLokala = taldeLokala;
	}

	public String getTaldeBisitari() {
		return TaldeBisitari;
	}

	public void setTaldeBisitari(String taldeBisitari) {
		TaldeBisitari = taldeBisitari;
	}

	public int getResultLokala() {
		return ResultLokala;
	}

	public void setResultLokala(int resultLokala) {
		ResultLokala = resultLokala;
	}

	public int getResulBisitari() {
		return ResulBisitari;
	}

	public void setResulBisitari(int resulBisitari) {
		ResulBisitari = resulBisitari;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getOrdua() {
		return Ordua;
	}

	public void setOrdua(String ordua) {
		Ordua = ordua;
	}
}