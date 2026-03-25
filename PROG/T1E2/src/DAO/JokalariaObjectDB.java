package DAO;

import javax.persistence.*;
import java.util.List;
import E2.Jokalaria;

public class JokalariaObjectDB {

	// Fitxategia proiektuaren karpetan sortuko da
	private static final String URL = "jokalariak.odb";
	private EntityManagerFactory emf;

	public JokalariaObjectDB() {
		this.emf = Persistence.createEntityManagerFactory(URL);
	}

	// Taldeko jokalari bat aldatu eta BDOO eguneratu
	public boolean aldatuTaldea(String nan, String taldeBerria) {
		// EntityManager bat sortu datu-basearekin eragiketak kudeatzeko
		EntityManager em = emf.createEntityManager();
		try {
			// Transakzioa hasi: aldaketak seguruak izateko eta datu-basean finkatzeko
			em.getTransaction().begin();
			// Jokalaria bilatu datu-basean bere NAN (Primary Key) erabiliz
			Jokalaria j = em.find(Jokalaria.class, nan);
			// Jokalaria existitzen bada, bere taldea aldatu eta transakzioa onartu (commit)
			if (j != null) {
				j.setTaldea(taldeBerria); // Objektuaren egoera eguneratu
				em.getTransaction().commit(); // Aldaketak datu-basean gorde
				return true;
			}
			return false;
		} finally {
			em.close();
		}
	}

	// Talde bateko jokalariak .odb fitxategitik lortu
	public List<Jokalaria> getJokalariakTaldeka(String taldeIzena) {
		// Datu-basearekin komunikatzeko entitate-kudeatzailea sortu
		EntityManager em = emf.createEntityManager();
		try {
			// JPQL bidezko kontsulta prestatu: 'Jokalaria' klaseko objektuak bilatzen ditu
			// taldearen arabera
			TypedQuery<Jokalaria> query = em.createQuery("SELECT j FROM Jokalaria j WHERE j.Taldea = :t",
					Jokalaria.class);

			// ':t' parametroa metodoari pasatako 'taldeIzena' balioarekin ordezkatu eta
			// zerrenda lortu
			return query.setParameter("t", taldeIzena).getResultList();
		} finally {
			em.close();
		}
	}

	// MySQL erreproduzitzaile guztiak .odb fitxategira lehen aldiz gehitzeko
	// metodoa
	public void inicializarODB(List<Jokalaria> zerrenda) {
		// Datu-basearekin komunikatzeko EntityManager-a sortu
		EntityManager em = emf.createEntityManager();
		try {
			// Transakzioa hasi: txertatze guztiak unitate bakar gisa kudeatzeko
			em.getTransaction().begin();
			// Zerrendako jokalari bakoitzeko begizta bat (loop) exekutatu
			for (Jokalaria j : zerrenda) {
				// Egiaztatu jokalaria existitzen den datu-basean bere NAN-a erabiliz
				if (em.find(Jokalaria.class, j.getNAN()) == null) {
					// Existitzen ez bada, jokalari berria datu-basean txertatu (persist)
					em.persist(j);
				}
			}
			// Aldaketa guztiak datu-basean behin betiko finkatu (commit)
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	// Deitzeko beste metodoengatik
	public void close() {
		emf.close();
	}
}