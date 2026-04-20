package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import E2.Jokalaria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import DB.ConexionDB;

/**
 * Jokalarien datuak ObjectDB logikarekin (edo batch eragiketekin) kudeatzeko
 * klasea. Klase honek datu-basearekiko konexioa erabiltzen du jokalarien
 * informazioa taldeka kargatzeko eta masiboki hasieratzeko. * @author Talde1
 * 
 * @version 1.0
 */
public class JokalariaObjectDB {

	/** Datu-basearekiko konexioa kudeatzen duen objektua */
	private ConexionDB konexioa;

	/**
	 * JokalariaObjectDB-ren eraikitzailea. Konexio objektu berri bat hasieratzen
	 * du.
	 */
	public JokalariaObjectDB() {
		this.konexioa = new ConexionDB();
	}

	/**
	 * Jokalari baten taldea aldatzen du datu-basean, NAN-a erabiliz gako gisa.
	 * * @param nan Jokalariaren NAN identifikatzailea.
	 * 
	 * @param taldeBerria Jokalariari esleitu nahi zaion talde berriaren izena.
	 * @return true aldaketa gutxienez errenkada batean burutu bada, false bestela.
	 */
	public boolean aldatuTaldea(String nan, String taldeBerria) {
		// SQL agindua: Talde_Izena zutabea eguneratzen du NAN bidez iragazita
		String sql = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";
		try (Connection kon = konexioa.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			// Parametroak SQL-n ezarri
			pstmt.setString(1, taldeBerria);
			pstmt.setString(2, nan);
			// Eguneratutako errenkada kopurua bueltatzen du
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Talde zehatz baten jokalari guztiak kargatzen ditu. * @param taldeIzena
	 * Bilatu nahi den taldearen izen osoa.
	 * 
	 * @return Jokalaria objektuen zerrenda bat.
	 */
	public List<Jokalaria> getJokalariakTaldeka(String taldeIzena) {
		List<Jokalaria> zerrenda = new ArrayList<>();
		String sql = "SELECT * FROM jokalariak WHERE Talde_Izena = ?";
		try (Connection kon = konexioa.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			pstmt.setString(1, taldeIzena);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					// Datuak mapeatu datu-baseko zutabeen izenen arabera
					Jokalaria j = new Jokalaria();
					j.setNAN(rs.getString("NAN"));
					j.setIzena(rs.getString("Jok_Izena"));
					j.setAbizena(rs.getString("Jok_Abizena"));
					j.setTaldea(rs.getString("Talde_Izena"));
					j.setPrezioa(rs.getInt("Merka_Prezioa"));
					// Oharra: Jaiotze data beharrezkoa bada, hemen gehitu daiteke
					// j.setJaiotzeData(rs.getString("Jaio_Data"));
					zerrenda.add(j);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return zerrenda;
	}

	/**
	 * Jokalari zerrenda bat datu-basean masiboki sartzen du (Batch prozesua).
	 * 'INSERT IGNORE' erabiltzen du NAN errepikatuak alde batera uzteko. * @param
	 * zerrenda Datu-basean sartu nahi diren jokalarien zerrenda.
	 */
	public void inicializarODB(List<Jokalaria> zerrenda) {
		String sql = "INSERT IGNORE INTO jokalariak (NAN, Jok_Izena, Jok_Abizena, Jaio_Data, Merka_Prezioa, Talde_Izena) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection kon = konexioa.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			for (Jokalaria j : zerrenda) {
				// Parametro bakoitza dagokion posizioan ezarri
				pstmt.setString(1, j.getNAN());
				pstmt.setString(2, j.getIzena());
				pstmt.setString(3, j.getAbizena());
				pstmt.setString(4, j.getJaiotzeData()); // Formatua YYYY-MM-DD izan behar da
				pstmt.setInt(5, j.getPrezioa());
				pstmt.setString(6, j.getTaldea());
				// Batch-era gehitu eragiketa bakoitza
				pstmt.addBatch();
			}
			// Eragiketa guztiak kolpe batean exekutatu errendimendu hobea lortzeko
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}