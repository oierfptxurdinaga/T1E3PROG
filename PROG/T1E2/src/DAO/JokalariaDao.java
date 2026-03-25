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
	public boolean aldatuTaldea(String nan, String taldeaBerria) {
		// Balidazioa: null edo hutsik badago, zuzenean false
		// Sarrerako datuak egokiak direla ziurtatu (ez daudela hutsik) datu-basera
		// deitu baino lehen
		if (nan == null || nan.trim().isEmpty() || taldeaBerria == null || taldeaBerria.trim().isEmpty()) {
			return false;
		}
		String sql = "UPDATE jokalariak SET Talde_Izena = ? WHERE NAN = ?";
		// Konexioa ireki eta SQL agindua prestatu, 'Try-with-resources' erabiliz
		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			// Balioak ezarri eta 'trim()' erabili alferreko zuriuneak kentzeko
			pstmt.setString(1, taldeaBerria.trim());
			pstmt.setString(2, nan.trim());
			// 'executeUpdate' exekutatu eta aldaketak jasan dituzten errenkada kopurua jaso
			int lerroak = pstmt.executeUpdate();
			// Errenkadaren bat aldatu bada (lerroak > 0), prozesua ondo joan dela esan nahi
			// du
			if (lerroak > 0) {
				System.out.println("MySQL: Jokalariaren (" + nan + ") taldea aldatu da: " + taldeaBerria);
				return true;
			}
		} catch (SQLException e) {
			// SQL errore bat badago (adibidez, 'Foreign Key' muga bat haustean), mezua
			// inprimatu
			System.err.println("Errorea taldea aldatzean: " + e.getMessage());
		}
		return false;
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
}