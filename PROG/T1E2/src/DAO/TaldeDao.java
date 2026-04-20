package DAO;

import java.sql.*;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Taldea;

/**
 * Taldeen datuak kudeatzeko DAO klasea. MySQL datu-basearekin komunikazioa
 * ezartzen du taldeak kargatzeko eta jokalarien talde-aldaketak kudeatzeko.
 * * @author Talde1
 * 
 * @version 1.0
 */
public class TaldeDao {

	/** Datu-basearekiko konexioa kudeatzen duen objektua */
	private ConexionDB db = new ConexionDB();

	/**
	 * MySQL-tik talde guztiak kargatzen ditu, haien informazio osoarekin batera.
	 * * @return Taldea objektuen zerrenda bat (ArrayList).
	 */
	public ArrayList<Taldea> kargatuTaldeak() {
		ArrayList<Taldea> zerrenda = new ArrayList<>();
		// 'taldeak' taulako zutabe guztiak hautatzeko SQL agindua
		String sql = "SELECT * FROM taldeak";
		// Baliabideak automatikoki ixteko try-with-resources egitura
		try (Connection kon = db.konektatu();
				PreparedStatement pstmt = kon.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Taldea t = new Taldea();
				// Oinarrizko datuak esleitu: Izena (Gako nagusia)
				t.setIzena(rs.getString("Talde_Izena"));
				// Taula grafikoan erakusteko datu gehigarriak:
				// Ziurtatu zutabe-izen hauek MySQL-ko taulan berdin deitzen direla
				t.setSorreraUrtea(rs.getString("Sorrera_Data"));
				t.setLehendakari(rs.getString("Lehendakari_Izena"));
				t.setN_Bazkideak(rs.getInt("N_Bazkideak"));
				zerrenda.add(t);
			}
		} catch (SQLException e) {
			// Errore bat gertatuz gero, errore-korrontean mezua erakutsi
			System.err.println("Errorea taldeak kargatzean (DAO): " + e.getMessage());
		}
		return zerrenda;
	}

	/**
	 * Jokalari baten taldea aldatzen du MySQL-n eta talde guztien izen bereiziak
	 * bueltatzen ditu. * @param NAN Jokalariaren identifikazio agiria.
	 * 
	 * @param taldeberria Jokalariari esleitu nahi zaion talde berriaren izena.
	 * @return Talde guztien izenak biltzen dituen ArrayList bat (ComboBox-ak
	 *         eguneratzeko).
	 */
	public ArrayList<String> aldatutaetaLortuTaldeak(String NAN, String taldeberria) {
		ArrayList<String> IzenaTaldeak = new ArrayList<>();
		String sqlUpdate = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";
		String sqlSelect = "SELECT DISTINCT Talde_Izena FROM taldeak";
		try (Connection kon = db.konektatu()) {
			// 1. Jokalariaren taldea eguneratu datu-basean
			try (PreparedStatement pstmtUpd = kon.prepareStatement(sqlUpdate)) {
				pstmtUpd.setString(1, taldeberria);
				pstmtUpd.setString(2, NAN);
				pstmtUpd.executeUpdate();
			}
			// 2. Talde izen guztiak lortu interfazea (ComboBox) freskatzeko
			try (PreparedStatement pstmtSel = kon.prepareStatement(sqlSelect); ResultSet rs = pstmtSel.executeQuery()) {
				while (rs.next()) {
					IzenaTaldeak.add(rs.getString("Talde_Izena"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Errorea taldea aldatzean edo lortzean (DAO): " + e.getMessage());
		}
		return IzenaTaldeak;
	}
}