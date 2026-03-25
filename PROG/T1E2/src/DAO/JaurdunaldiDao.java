package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Jaurdunaldia;

public class JaurdunaldiDao {
	private ConexionDB db = new ConexionDB();

	public ArrayList<Jaurdunaldia> kargatuJaurdunaldiak() {
		ArrayList<Jaurdunaldia> zerrenda = new ArrayList<>();
		String sql = "SELECT j.Id_Par, j.Id_Jaurdu, j.Talde_Irabazlea, j.Talde_Galdu FROM jaurdunaldia j";
		// 'Try-with-resources' blokea: baliabideak (konexioa, statement-a eta emaitzak)
		// automatikoki ixteko
		try (Connection kon = db.konektatu();
				PreparedStatement pstmt = kon.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			// Emaitza-multzoan (ResultSet) hurrengo errenkadarik dagoen bitartean iteratu
			while (rs.next()) {
				// Jaurdunaldia objektu berria instantziatu datu-baseko zutabeen balioekin eta
				// zerrendara gehitu
				zerrenda.add(new Jaurdunaldia(rs.getInt("Id_Par"), rs.getInt("Id_Jaurdu"),
						rs.getString("Talde_Irabazlea"), rs.getString("Talde_Galdu")));
			}
		} catch (Exception e) {
			System.out.println("Errorea DAO jaurdunaldia: " + e.getMessage());
		}
		return zerrenda;
	}
		
		public void eguneratuIrabazlea(int idPar, String irabazlea, String galtzailea) {
			String sql = "UPDATE jaurdunaldia SET Talde_Irabazlea = ?, Talde_Galdu = ? WHERE Id_Par = ?";
			// Konexioa ireki eta SQL agindua prestatu 'Try-with-resources' erabiliz
			try (Connection kon = db.konektatu(); PreparedStatement ps = kon.prepareStatement(sql)) {
				// SQL galderaren ikurrak (?) balio zehatzekin ordezkatu (ordena garrantzitsua da)
				ps.setString(1, irabazlea);
				ps.setString(2, galtzailea);
				ps.setInt(3, idPar);
				// Datu-basean aldaketa gauzatu (UPDATE, INSERT edo DELETE denean 'executeUpdate' erabiltzen da)
				ps.executeUpdate();	
			} catch (Exception e) {
				System.out.println("Errorea Jaurdunaldia eguneratzean: " + e.getMessage());
			}
		}
	}