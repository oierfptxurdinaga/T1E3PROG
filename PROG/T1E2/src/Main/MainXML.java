package Main;

import java.io.File;
import java.util.ArrayList;
import DAO.TaldeDao;
import DAO.JaurdunaldiDao;
import DAO.PartiduaDao;
import E2.Denboraldia;
import E2.Jaurdunaldia;
import E2.Partidua;
import E2.Taldea;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

/**
 * JAXB bidez datu-baseko informazioa XML fitxategi batera esportatzen duen
 * klasea. Klase honek DAO guztiak erabiltzen ditu datuak lortzeko, objektuen
 * arteko loturak (Jardunaldiak -> Partiduak) eraikitzen ditu eta fitxategia
 * sortzen du. * @author Talde1
 * 
 * @version 1.0
 */
public class MainXML {

	/**
	 * XML sorkuntza prozesua abiarazten duen metodo nagusia.
	 * 
	 * @param args Komando lerroko argumentuak (ez dira erabiltzen).
	 */
	public static void main(String[] args) {
		try {
			System.out.println("--- XML SORKUNTZA HASI DA ---");
			// 1. DAO objektuak sortu datuak lortzeko
			System.out.println("DAO-ak inizializatzen...");
			TaldeDao taldeDao = new TaldeDao();
			JaurdunaldiDao jaurDao = new JaurdunaldiDao();
			PartiduaDao parDao = new PartiduaDao();
			// 2. Datuak kargatu MySQL-tik
			System.out.println("Taldeak kargatzen...");
			ArrayList<Taldea> taldeak = taldeDao.kargatuTaldeak();
			System.out.println("Taldeak jaso dira: " + (taldeak != null ? taldeak.size() : "0"));
			System.out.println("Jaurdunaldiak kargatzen...");
			ArrayList<Jaurdunaldia> jaurdunaldiak = jaurDao.kargatuJaurdunaldiak();
			System.out.println("Partiduak kargatzen...");
			ArrayList<Partidua> partiduak = parDao.kargatuPartiduak();
			// 3. Objektuen arteko erlazioak eraiki (Partiduak jardunaldi bakoitzean sartu)
			System.out.println("Objektuen arteko loturak sortzen...");
			if (jaurdunaldiak != null && partiduak != null) {
				for (Jaurdunaldia j : jaurdunaldiak) {
					ArrayList<Partidua> partiduakJornada = new ArrayList<>();
					for (Partidua p : partiduak) {
						// Partidu bakoitza bere jardunaldiarekin lotu ID bidez
						if (p.getId_Par() == j.getIdPar()) {
							partiduakJornada.add(p);
						}
					}
					j.setPartiduak(partiduakJornada);
				}
			}
			// 4. Denboraldia objektu nagusia egituratu
			System.out.println("Denboraldia objektua osatzen...");
			// Eraikitzailea: Data, Jaurdunaldiak, Taldeak
			Denboraldia den = new Denboraldia("2024-2025", jaurdunaldiak, taldeak);
			// 5. JAXB bidezko Marshalling prozesua (Java Objektua -> XML Fitxategia)
			System.out.println("JAXB Marshaller konfiguratzen...");
			JAXBContext context = JAXBContext.newInstance(Denboraldia.class);
			Marshaller marshaller = context.createMarshaller();
			// XML-a formatu politarekin (indentazioarekin) sortzeko
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 6. Fitxategia idatzi proiektuaren erroan
			System.out.println("Fitxategia idazten...");
			File f = new File("Denboraldia_2024-2025.xml");
			marshaller.marshal(den, f);
			System.out.println("\n--- XML-A ONDO SORTU DA! ---");
			System.out.println("Kokapena: " + f.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("!!! ERROREA XML-A SORTZEAN !!!");
			// Errorearen traza osoa erakutsi arazketarako (debugging)
			e.printStackTrace();
		}
	}
}