package DAO;

import java.sql.*;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Taldea;

/**
 * Taldeen sailkapena kudeatzeko DAO klasea. Talde bakoitzaren estatistikak
 * (puntuak, garaipenak, galerak...) MySQL datu-basean eguneratzeaz arduratzen
 * da. * @author Talde1
 * 
 * @version 1.0
 */
public class KlasifikazioaDao {

	/** Datu-basearekiko konexioa kudeatzen duen objektua */
	private ConexionDB db = new ConexionDB();

	/**
	 * Talde baten sailkapen-datuak eguneratzen ditu datu-basean. Taldearen izena
	 * erabiltzen du gako gisa estatistika berriak gordetzeko. * @param t Eguneratu
	 * nahi diren datuak dituen Taldea objektua.
	 */
	public void modifyKlasifikazioa(Taldea t) {
		// SQL UPDATE agindua klasifikazioa taulako zutabe guztiak eguneratzeko
		String sql = "UPDATE klasifikazioa SET Puntutotalak = ?, Par_Irabaziak = ?, "
				+ "Par_Galdutak = ?, Puntos_F = ?, Puntos_C = ? " + "WHERE Talde_Izena = ?";
		// Try-with-resources blokea baliabideak automatikoki ixteko
		try (Connection kon = db.konektatu(); PreparedStatement ps = kon.prepareStatement(sql)) {
			// Parametroak Taldea objektutik hartu eta SQL-an ezarri
			ps.setInt(1, t.getPuntuTotalak());
			ps.setInt(2, t.getIrabazitakoak());
			ps.setInt(3, t.getGaldutakoak());
			ps.setInt(4, t.getPuntuakF());
			ps.setInt(5, t.getPuntuakC());
			ps.setString(6, t.getIzena());
			// Aldaketa datu-basean gauzatu
			ps.executeUpdate();
		} catch (Exception e) {
			// Errore bat gertatzekotan, errore mezua inprimatu
			System.out.println("Errorea Modify Klasifikazioan (DAO): " + e.getMessage());
		}
	}
}