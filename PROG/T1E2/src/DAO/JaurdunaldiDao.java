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


	public  ArrayList<Jaurdunaldia> kargatuJaurdunaldiak() {
	    ArrayList<Jaurdunaldia> lista = new ArrayList<>();
	    String sql = "SELECT j.Id_Par, j.Id_Jaurdu, j.Talde_Irabazlea, j.Talde_Galdu FROM jaurdunaldia j";
	    
	    try (Connection kon = db.konektatu();
	             PreparedStatement pstmt = kon.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            lista.add(new Jaurdunaldia(
	                rs.getInt("Id_Par"),
	                rs.getInt("Id_Jaurdu"),
	                rs.getString("Talde_Irabazlea"),
	                rs.getString("Talde_Galdu")
	            ));
	        }
	    } catch (Exception e) {
	        System.out.println("Errorea DAO jaurdunaldia: " + e.getMessage());
	    }
	    return lista;
	}

}
