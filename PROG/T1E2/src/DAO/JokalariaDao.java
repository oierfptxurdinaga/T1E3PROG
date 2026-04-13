package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.ConexionDB;
import E2.Jokalaria;

/**
 * Jokalarien datuak kudeatzeko DAO klasea. MySQL datu-basearekin komunikazioa
 * ezartzen du jokalariak kargatzeko, gehitzeko eta haien informazioa
 * eguneratzeko. * @author Talde1
 * 
 * @version 1.0
 */
public class JokalariaDao {

	/** Datu-basearekiko konexio objektua */
	private ConexionDB db = new ConexionDB();

	/** Jokalarien zerrenda memorian kudeatzeko (aukerakoa) */
	private ArrayList<Jokalaria> zerrendaJokalariak;

	/**
	 * JokalariaDao-ren eraikitzailea. Zerrenda hasieratzen du.
	 */
	public JokalariaDao() {
		zerrendaJokalariak = new ArrayList<>();
	}

	/**
	 * Talde zehatz baten jokalariak kargatzen ditu MySQL-tik. * @param taldeIzena
	 * Jokalariak bilatzeko erabili nahi den taldearen izena.
	 * 
	 * @return Talde horretako jokalarien ArrayList bat.
	 */
	public ArrayList<Jokalaria> kargatuJokalariakTaldeka(String taldeIzena) {
		ArrayList<Jokalaria> zerrenda = new ArrayList<>();
		// SQL kontsulta: Taldearen izenaren arabera iragazten dugu '?' erabiliz
		// segurtasunagatik
		String sql = "SELECT * FROM jokalariak WHERE Talde_Izena = ?";
		// Konexioa eta PreparedStatement-a automatikoki ixteko blokea
		// (try-with-resources)
		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			// SQL-ko galdera-ikurra (?) parametroarekin ordezkatu
			pstmt.setString(1, taldeIzena);
			// Kontsulta exekutatu eta emaitzak ResultSet-ean lortu
			try (ResultSet rs = pstmt.executeQuery()) {
				// Emaitza bakoitzeko Jokalaria objektu bat sortu eta zerrendan sartu
				while (rs.next()) {
					zerrenda.add(new Jokalaria(rs.getString("Jok_Izena"), rs.getString("Jok_Abizena"),
							rs.getString("Jaio_Data"), rs.getString("NAN"), rs.getString("Talde_Izena"),
							rs.getInt("Merka_Prezioa")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return zerrenda;
	}

	/**
	 * Jokalari baten taldea aldatzen du MySQL datu-basean, NAN-a identifikatzaile
	 * gisa erabiliz. * @param nan Jokalariaren NAN-a.
	 * 
	 * @param nuevoTalde Jokalariari esleitu nahi zaion talde berria.
	 * @return true aldaketa ondo burutu bada, false bestela.
	 */
	public boolean aldatuTaldea(String nan, String nuevoTalde) {
		// Eguneraketa MySQL-n iraunkorra izateko SQL agindua
		String sql = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";
		ConexionDB db = new ConexionDB();
		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			pstmt.setString(1, nuevoTalde);
			pstmt.setString(2, nan);
			// Aldatutako errenkada kopurua lortu (0 baino handiagoa bada, ondo gorde da)
			int filas = pstmt.executeUpdate();
			return filas > 0;

		} catch (SQLException e) {
			System.out.println("Errorea MySQL-n gordetzean: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Datu-basean dauden jokalari GUZTIAK kargatzen ditu. Oso erabilgarria ObjectDB
	 * fitxategia hasieratzeko edo migrazioak egiteko. * @return Jokalari guztien
	 * zerrenda.
	 */
	public ArrayList<Jokalaria> kargatuJokalariGuztiak() {
		ArrayList<Jokalaria> guztiak = new ArrayList<>();
		// SQL kontsulta: Jokalari guztiak iragazkirik gabe lortzeko
		String sql = "SELECT * FROM jokalariak";
		try (Connection kon = db.konektatu();
				PreparedStatement pstmt = kon.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				guztiak.add(
						new Jokalaria(rs.getString("Jok_Izena"), rs.getString("Jok_Abizena"), rs.getString("Jaio_Data"),
								rs.getString("NAN"), rs.getString("Talde_Izena"), rs.getInt("Merka_Prezioa")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guztiak;
	}

	/**
	 * Jokalari bat gehitzen du memorian dagoen zerrendara.
	 * 
	 * @param j Gehitu nahi den jokalaria.
	 */
	public void gehituJokalaria(Jokalaria j) {
		zerrendaJokalariak.add(j);
	}

	/**
	 * Memorian gordetako jokalarien zerrenda bueltatzen du.
	 * 
	 * @return Jokalarien ArrayList-a.
	 */
	public ArrayList<Jokalaria> getListaJokalariak() {
		return zerrendaJokalariak;
	}

	/**
	 * Jokalari baten datu espezifikoak (Taldea, etab.) eguneratzen ditu MySQL-n.
	 * 
	 * @param j Eguneratu nahi den jokalari objektua.
	 */
	public void eguneratuJokalaria(Jokalaria j) {
		// NAN-a gako nagusi gisa erabiltzen dugu zein jokalari aldatu behar den
		// jakiteko
		String sql = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";
		ConexionDB db = new ConexionDB();
		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			// Aldatutako balio berriak ezarri
			pstmt.setString(1, j.getTaldea());
			pstmt.setString(2, j.getNAN());
			int filasAfectadas = pstmt.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Jokalariaren datuak ondo eguneratu dira MySQL-n!");
			}
		} catch (SQLException e) {
			System.out.println("Errorea jokalaria eguneratzerakoan: " + e.getMessage());
		}
	}
}