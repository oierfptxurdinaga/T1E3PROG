package Main;

import java.io.File;
import java.util.ArrayList;
import DAO.TaldeDao;
import E2.Denboraldia;
import E2.Jaurdunaldia;
import E2.Partidua;
import E2.Taldea;
import DAO.JaurdunaldiDao;
import DAO.PartiduaDao;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

public class MainXML {

	public static void main(String[] args) {
		try {
			// 1️. DAOak
			TaldeDao taldeDao = new TaldeDao();
			JaurdunaldiDao jaurDao = new JaurdunaldiDao();
			PartiduaDao parDao = new PartiduaDao();
			// 2️. Datuak kargatu BD-tik
			ArrayList<Taldea> taldeak = taldeDao.kargatuTaldeak();
			ArrayList<Jaurdunaldia> jaurdunaldiak = jaurDao.kargatuJaurdunaldiak();
			ArrayList<Partidua> partiduak = parDao.kargatuPartiduak();
			// 3️. PARTIDUAK → JAURDUNALDIAK lotu
			for (Jaurdunaldia j : jaurdunaldiak) {
				ArrayList<Partidua> partiduakJornada = new ArrayList<>();
				for (Partidua p : partiduak) {
					// Lotura: Id_Par berdina bada
					if (p.getId_Par() == j.getIdPar()) {
						partiduakJornada.add(p);
					}
				}
				// Setterra behar duzu (lehen esan nizun bezala)
				j.setPartiduak(partiduakJornada);
			}

			// 4️. Denboraldia sortu
			Denboraldia den = new Denboraldia("2025-2026", jaurdunaldiak, new ArrayList<>());
			den.setTaldeak(taldeak);

			// 5️. JAXB
			JAXBContext context = JAXBContext.newInstance(Denboraldia.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// 6️. XML sortu
			marshaller.marshal(den, new File("Denboraldia 2025-2026.xml"));
			System.out.println("XML-a sortu egin da (BD-tik)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}