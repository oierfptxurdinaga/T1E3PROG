package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Jaurdunaldia;

/**
 * Jaurdunaldien datuak kudeatzeko DAO klasea. Datu-basearekiko konexioa
 * erabiltzen du jaurdunaldien informazioa lortzeko eta eguneratzeko. * @author
 * Talde1
 * 
 * @version 1.0
 */
public class JaurdunaldiDao {

	/** Datu-basearekiko konexioa kudeatzen duen objektua */
	private ConexionDB db = new ConexionDB();

	/**
	 * Datu-basetik jaurdunaldiko partidu guztien zerrenda kargatzen du. * @return
	 * Jaurdunaldia objektuen ArrayList bat, datu-baseko informazioarekin.
	 */
	public ArrayList<Jaurdunaldia> kargatuJaurdunaldiak() {
		ArrayList<Jaurdunaldia> zerrenda = new ArrayList<>();
		// SQL kontsulta: partiduaren IDa, jaurdunaldia eta irabazle/galtzaileak
		// lortzeko
		String sql = "SELECT j.Id_Par, j.Id_Jaurdu, j.Talde_Irabazlea, j.Talde_Galdu FROM jaurdunaldia j";
		// 'Try-with-resources' baliabideak (konexioa, pstmt eta rs) automatikoki ixteko
		try (Connection kon = db.konektatu();
				PreparedStatement pstmt = kon.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			// ResultSet-ean emaitzak dauden bitartean iteratu
			while (rs.next()) {
				// Objektu berria sortu errenkada bakoitzeko datuekin
				zerrenda.add(new Jaurdunaldia(rs.getInt("Id_Par"), rs.getInt("Id_Jaurdu"),
						rs.getString("Talde_Irabazlea"), rs.getString("Talde_Galdu")));
			}
		} catch (Exception e) {
			// Errore bat gertatuz gero kontsolan bistaratu
			System.out.println("Errorea DAO jaurdunaldia kargatzean: " + e.getMessage());
		}
		return zerrenda;
	}

	/**
	 * Partidu zehatz baten emaitza (irabazlea eta galtzailea) eguneratzen du
	 * datu-basean. * @param idPar Eguneratu nahi den partiduaren identifikatzailea.
	 * 
	 * @param irabazlea  Irabazi duen taldearen izena.
	 * @param galtzailea Galdu duen taldearen izena.
	 */
	public void eguneratuIrabazlea(int idPar, String irabazlea, String galtzailea) {
		// UPDATE agindua jaurdunaldiko emaitzak aldatzeko
		String sql = "UPDATE jaurdunaldia SET Talde_Irabazlea = ?, Talde_Galdu = ? WHERE Id_Par = ?";
		try (Connection kon = db.konektatu(); PreparedStatement ps = kon.prepareStatement(sql)) {
			// Parametroak SQL aginduan ezarri
			ps.setString(1, irabazlea);
			ps.setString(2, galtzailea);
			ps.setInt(3, idPar);

			// Aldaketa datu-basean gauzatu
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Errorea Jaurdunaldia eguneratzean: " + e.getMessage());
		}
	}
}