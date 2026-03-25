package DAO;

import java.sql.*;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Taldea;
import E2.Jokalaria;

public class TaldeDao {
	private ConexionDB db = new ConexionDB();
	private JokalariaDao jokalariaDao = new JokalariaDao(); // Jokalarien DAO-ari erreferentzia

	public ArrayList<Taldea> kargatuTaldeak() {
		ArrayList<Taldea> taldeZerrenda = new ArrayList<>();
		// SQL kontsulta: taldeak eta haien klasifikazio datuak lortzeko
		String sql = "SELECT t.Talde_Izena, t.Sorrera_Data, t.Lehendakari_Izena, t.N_Bazkideak, "
				+ "k.Puntos_F, k.Puntos_C, k.Puntutotalak, k.Par_Irabaziak, k.Par_Galdutak " + "FROM taldeak t "
				+ "LEFT JOIN klasifikazioa k ON t.Talde_Izena = k.Talde_Izena";
		try (
				Connection kon = db.konektatu();
				// SQL agindua prestatu
				PreparedStatement pstmt = kon.prepareStatement(sql);
				// Kontsulta exekutatu eta emaitzak lortu
				ResultSet rs = pstmt.executeQuery()) {
			// ResultSet-eko erregistro guztiak iteratzen dira
			while (rs.next()) {
				// Taldearen izena lortzen da
				String tIzena = rs.getString("Talde_Izena");
				// Talde honetako jokalariak kargatzen dira beste DAO baten bidez
				ArrayList<Jokalaria> jokalariak = jokalariaDao.kargatuJokalariakTaldeka(tIzena);
				// Taldea objektua sortzen da datu guztiekin
				Taldea taldeBerria = new Taldea(tIzena, // izena
						rs.getString("Sorrera_Data"), // sorrera data
						rs.getString("Lehendakari_Izena"), // lehendakaria
						rs.getInt("N_Bazkideak"), // bazkide kopurua
						rs.getInt("Puntos_F"), // aldeko puntuak
						rs.getInt("Puntos_C"), // kontrako puntuak
						rs.getInt("Puntutotalak"), // puntu totalak
						rs.getInt("Par_Irabaziak"), // irabazitako partidak
						rs.getInt("Par_Galdutak"), // galdutako partidak
						jokalariak // jokalarien zerrenda
				);
				// Sortutako taldea zerrendara gehitzen da
				taldeZerrenda.add(taldeBerria);
			}
		} catch (SQLException e) {
			System.out.println("Errorea taldeak kargatzerakoan: " + e.getMessage());
		}
		return taldeZerrenda;
	}

	public ArrayList<String> aldatutaetaLortuTaldeak(String NAN, String taldeberria) throws SQLException {
		Connection kon = db.konektatu();
		String sqlUpdate = "UPDATE Jokalaria SET Talde_Izena=? WHERE NAN=?";
		// Lehenengo zatia: Jokalariaren taldea eguneratu NAN-a erabiliz
		try (PreparedStatement ps = kon.prepareStatement(sqlUpdate)) {
			ps.setString(1, taldeberria);
			ps.setString(2, NAN);
			ps.executeUpdate(); // Aldaketa gauzatu
		}
		// Bigarren zatia: Talde guztien izen bakarrak (DISTINCT) lortu zerrenda
		// berritzeko
		String sqlTaldeak = "SELECT DISTINCT Talde_Izena FROM Jokalaria";
		ArrayList<String> taldeak = new ArrayList<>();
		try (PreparedStatement ps = kon.prepareStatement(sqlTaldeak); ResultSet rs = ps.executeQuery()) {
			// Emaitzak jaso eta String zerrendan banan-banan gorde
			while (rs.next()) {
				taldeak.add(rs.getString("Talde_Izena"));
			}
		}
		kon.close();
		return taldeak;
	}
}