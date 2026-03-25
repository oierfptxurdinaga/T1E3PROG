package DAO;

import java.sql.*;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Taldea;

public class KlasifikazioaDao {
	private ConexionDB db = new ConexionDB();

	public void modifyKlasifikazioa(Taldea t) {
		// UPDATE bat egiten dugu taldearen izena erabiliz filtro gisa
		String sql = "UPDATE klasifikazioa SET Puntutotalak = ?, Par_Irabaziak = ?, "
				+ "Par_Galdutak = ?, Puntos_F = ?, Puntos_C = ? " + "WHERE Talde_Izena = ?";
		// Konexioa ireki eta SQL agindua prestatu, 'Try-with-resources' erabiliz
		try (Connection kon = db.konektatu(); PreparedStatement ps = kon.prepareStatement(sql)) {
			// Taldea objektuaren atributuak SQL-ko galdera-ikurretan (?) txertatu
			ps.setInt(1, t.getPuntuTotalak());
			ps.setInt(2, t.getIrabazitakoak());
			ps.setInt(3, t.getGaldutakoak());
			ps.setInt(4, t.getPuntuakF());
			ps.setInt(5, t.getPuntuakC());
			// Bilaketa irizpidea (WHERE): Zein taldeari aplikatuko zaion aldaketa
			ps.setString(6, t.getIzena());
			// Aldaketa gauzatu eta ukitutako errenkada kopurua lortu
			int lerroak = ps.executeUpdate();
			System.out.println("Taldea: " + t.getIzena() + " | Aldatutako lerroak: " + lerroak);
			// Egiaztatu ea aldaketarik egin den (taldearen izena existitzen ez bada, 0
			// izango litzateke)
			if (lerroak == 0) {
				System.out.println("Abisua: Ez da talderik aurkitu izen honekin: " + t.getIzena());
			}
		} catch (Exception e) {
			System.out.println("Errorea Modify Klasifikazioan: " + e.getMessage());
		}
	}
}