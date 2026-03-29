package main;

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

public class MainXML {

	public static void main(String[] args) {
		try {
			System.out.println("DAO-ak sortzen...");
			TaldeDao taldeDao = new TaldeDao();
			JaurdunaldiDao jaurDao = new JaurdunaldiDao();
			PartiduaDao parDao = new PartiduaDao();
			System.out.println("Taldeak kargatzen...");
			ArrayList<Taldea> taldeak = taldeDao.kargatuTaldeak();
			System.out.println("Taldeak jaso dira: " + (taldeak != null ? taldeak.size() : "NULL"));
			System.out.println("Jaurdunaldiak kargatzen...");
			ArrayList<Jaurdunaldia> jaurdunaldiak = jaurDao.kargatuJaurdunaldiak();
			System.out.println("Partiduak kargatzen...");
			ArrayList<Partidua> partiduak = parDao.kargatuPartiduak();
			System.out.println("Objektuak lotzen");
			if (jaurdunaldiak != null && partiduak != null) {
				for (Jaurdunaldia j : jaurdunaldiak) {
					ArrayList<Partidua> partiduakJornada = new ArrayList<>();
					for (Partidua p : partiduak) {
						if (p.getId_Par() == j.getIdPar()) {
							partiduakJornada.add(p);
						}
					}
					j.setPartiduak(partiduakJornada);
				}
			}
			System.out.println("Denboraldia objektua sortzen...");
			// Erabili zure eraikitzailea (Constructor): Data, Jaurdunaldiak, Puntuazioak
			// (hutsik)
			Denboraldia den = new Denboraldia("2024-2025", jaurdunaldiak, new ArrayList<>());
			den.setTaldeak(taldeak);
			System.out.println("JAXB Martxan jartzen...");
			JAXBContext context = JAXBContext.newInstance(Denboraldia.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			System.out.println("Pausoa: Fitxategia idazten...");
			File f = new File("Denboraldia_2024-2025.xml");
			marshaller.marshal(den, f);
			System.out.println("--- XML-a Sortu Egin da! ---");
			System.out.println("Begiratu Proiektuaren Karpetan: " + f.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("!!! ERROREA GERTAU DA !!!");
			e.printStackTrace(); // Honek kontsolan idatzi behar du bai ala bai
		}
	}
}