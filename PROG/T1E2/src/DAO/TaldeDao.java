package DAO;

import java.sql.*;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Taldea;

public class TaldeDao {
	private ConexionDB db = new ConexionDB();

	/**
	 * MySQL-tik talde guztiak kargatzen ditu, haien informazio osoarekin batera.
	 * 
	 * @return Taldea objektuen zerrenda bat.
	 */
	public ArrayList<Taldea> kargatuTaldeak() {
		ArrayList<Taldea> zerrenda = new ArrayList<>();
		// 'taldeak' taulako zutabe guztiak hautatzen ditugu
		String sql = "SELECT * FROM taldeak";
		try (Connection kon = db.konektatu();
				PreparedStatement pstmt = kon.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Taldea t = new Taldea();
				// Oinarrizko datuak: Izena (Gako nagusia)
				t.setIzena(rs.getString("Talde_Izena"));
				// Goiko taularako beharrezkoak diren datuak:
				// Kontuz: Ziurtatu "Sorrera_Data", "Lehendakari" eta "N_Bazkideak"
				// zutabe-izenak bat datozela zure MySQL taularekin.
				t.setSorreraUrtea(rs.getString("Sorrera_Data"));
				t.setLehendakari(rs.getString("Lehendakari_Izena"));
				t.setN_Bazkideak(rs.getInt("N_Bazkideak"));
				zerrenda.add(t);
			}
		} catch (SQLException e) {
			// Errore bat gertatzekotan, kontsolan mezua erakutsiko du
			System.err.println("Errorea taldeak kargatzean: " + e.getMessage());
		}
		return zerrenda;
	}

	/**
	 * Jokalari baten taldea aldatzen du MySQL-n eta talde guztien izen bereiziak
	 * bueltatzen ditu.
	 * @param NAN Jokalariaren identifikazio agiria.
	 * @param taldeberria Jokalariari esleitu nahi zaion talde berriaren izena.
	 * @return Talde guztien izenak biltzen dituen ArrayList bat.
	 */
	public ArrayList<String> aldatutaetaLortuTaldeak(String NAN, String taldeberria) {
		ArrayList<String> IzenaTaldeak = new ArrayList<>();
		String sqlUpdate = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";
		String sqlSelect = "SELECT DISTINCT Talde_Izena FROM taldeak"; // 'taldeak' taulatik lortzea egokiagoa da
		try (Connection kon = db.konektatu()) {
			// Jokalariaren taldea eguneratu datu-basean
			try (PreparedStatement pstmtUpd = kon.prepareStatement(sqlUpdate)) {
				pstmtUpd.setString(1, taldeberria);
				pstmtUpd.setString(2, NAN);
				pstmtUpd.executeUpdate();
			}
			// Talde izen guztiak lortu ComboBox-a freskatzeko
			try (PreparedStatement pstmtSel = kon.prepareStatement(sqlSelect); ResultSet rs = pstmtSel.executeQuery()) {
				while (rs.next()) {
					IzenaTaldeak.add(rs.getString("Talde_Izena"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Errorea taldea aldatzean (MySQL): " + e.getMessage());
		}
		return IzenaTaldeak;
	}
}