package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.ConexionDB;
import E2.Jokalaria;

public class JokalariaDao {
	private ConexionDB db = new ConexionDB();
	private ArrayList<Jokalaria> zerrendaJokalariak;

	public JokalariaDao() {
		zerrendaJokalariak = new ArrayList<>();
	}

	/**
	 * Talde baten jokalariak kargatzen ditu MySQL-tik.
	 */
	public ArrayList<Jokalaria> kargatuJokalariakTaldeka(String taldeIzena) {
		ArrayList<Jokalaria> zerrenda = new ArrayList<>();
		// SQL kontsulta: Taldearen izenaren arabera iragazten dugu '?' erabiliz
		// segurtasunagatik
		String sql = "SELECT * FROM jokalariak WHERE Talde_Izena = ?";
		// Konexioa eta PreparedStatement-a automatikoki ixteko blokea
		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			// SQL-ko galdera-ikurra (?) metodoaren parametroarekin (taldeIzena) ordezkatu
			pstmt.setString(1, taldeIzena);
			// Kontsulta exekutatu eta emaitzak (ResultSet) lortu
			try (ResultSet rs = pstmt.executeQuery()) {
				// Emaitza bakoitzeko, Jokalaria objektu bat sortu eta zerrendan sartu
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
	 * Jokalari baten taldea aldatzen du MySQL datu-basean. (Ez-estatikoa denez,
	 * erroreak saihestuko ditugu)
	 */
	public boolean aldatuTaldea(String nan, String nuevoTalde) {
		// Esta es la sentencia SQL que hace que el cambio sea permanente en XAMPP
		String sql = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";
		ConexionDB db = new ConexionDB();

		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {

			pstmt.setString(1, nuevoTalde);
			pstmt.setString(2, nan);

			int filas = pstmt.executeUpdate(); // ESTO es lo que guarda de verdad
			return filas > 0;

		} catch (SQLException e) {
			System.out.println("Errorea MySQL-n gordetzean: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Jokalari GUZTIAK kargatzeko metodoa (ObjectDB hasieratzeko oso erabilgarria)
	 */

	public ArrayList<Jokalaria> kargatuJokalariGuztiak() {
		ArrayList<Jokalaria> guztiak = new ArrayList<>();
		// SQL kontsulta: Jokalari guztien datuak lortzeko (iragazkirik gabe)
		String sql = "SELECT * FROM jokalariak";
		// Baliabideak (konexioa, agindua eta emaitzak) automatikoki kudeatzeko egitura
		try (Connection kon = db.konektatu();
				PreparedStatement pstmt = kon.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			// Emaitza-multzoan (ResultSet) hurrengo errenkadarik dagoen bitartean iteratu
			while (rs.next()) {
				// Jokalari bakoitzeko objektu bat sortu eta zerrendara gehitu
				guztiak.add(
						new Jokalaria(rs.getString("Jok_Izena"), rs.getString("Jok_Abizena"), rs.getString("Jaio_Data"),
								rs.getString("NAN"), rs.getString("Talde_Izena"), rs.getInt("Merka_Prezioa")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guztiak;
	}

	public void gehituJokalaria(Jokalaria j) {
		zerrendaJokalariak.add(j);
	}

	public ArrayList<Jokalaria> getListaJokalariak() {
		return zerrendaJokalariak;
	}

	public void eguneratuJokalaria(Jokalaria j) {
		String sql = "UPDATE jokalariak SET Talde_Izena = ?, Puntuak = ? WHERE NAN = ?";
		ConexionDB db = new ConexionDB();

		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {

			// Seteamos los valores que han cambiado
			pstmt.setString(1, j.getTaldea());
			// pstmt.setInt(2, j.getPuntuak()); // Si tienes puntos, si no, quita esta línea
			pstmt.setString(3, j.getNAN()); // El NAN es el que manda para saber a quién actualizar

			int filasAfectadas = pstmt.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Datuak ondo gorde dira MySQL-n!");
			}

		} catch (SQLException e) {
			System.out.println("Errorea eguneratzerakoan: " + e.getMessage());
		}
	}
}