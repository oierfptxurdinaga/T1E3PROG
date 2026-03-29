package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import E2.Jokalaria;
import DB.ConexionDB;

public class JokalariaObjectDB {

	private ConexionDB konexioa;

	public JokalariaObjectDB() {
		this.konexioa = new ConexionDB();
	}

	public boolean aldatuTaldea(String nan, String taldeBerria) {
		// En tu SQL la columna se llama 'Talde_Izena'
		String sql = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";

		try (Connection kon = konexioa.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {

			pstmt.setString(1, taldeBerria);
			pstmt.setString(2, nan);

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Jokalaria> getJokalariakTaldeka(String taldeIzena) {
		List<Jokalaria> lista = new ArrayList<>();
		String sql = "SELECT * FROM jokalariak WHERE Talde_Izena = ?";

		try (Connection kon = konexioa.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {

			pstmt.setString(1, taldeIzena);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					// Usamos los nombres de columna de tu dump SQL
					Jokalaria j = new Jokalaria();
					j.setNAN(rs.getString("NAN"));
					j.setIzena(rs.getString("Jok_Izena"));
					j.setAbizena(rs.getString("Jok_Abizena"));
					j.setTaldea(rs.getString("Talde_Izena"));
					j.setPrezioa(rs.getInt("Merka_Prezioa"));
					// Si tu POJO tiene JaiotzeData, mapealo así:
					// j.setJaiotzeData(rs.getString("Jaio_Data"));

					lista.add(j);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void inicializarODB(List<Jokalaria> zerrenda) {
		String sql = "INSERT IGNORE INTO jokalariak (NAN, Jok_Izena, Jok_Abizena, Jaio_Data, Merka_Prezioa, Talde_Izena) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection kon = konexioa.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {

			for (Jokalaria j : zerrenda) {
				pstmt.setString(1, j.getNAN());
				pstmt.setString(2, j.getIzena());
				pstmt.setString(3, j.getAbizena());
				pstmt.setString(4, j.getJaiotzeData()); // Asegúrate que el formato sea YYYY-MM-DD
				pstmt.setInt(5, j.getPrezioa());
				pstmt.setString(6, j.getTaldea());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}