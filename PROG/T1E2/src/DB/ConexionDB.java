package DB;

import java.sql.*;

public class ConexionDB {

	private static final String URL = "jdbc:mysql://localhost:3306/bsf";
	private static final String USER = "root";
    private static final String PASS = "";
private Connection conexion;
    // Metodo honek Connection objektu bat bueltatu behar du
    public Connection konektatu() {
        try {
        	conexion = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("❌ Errorea konektatzerakoan: " + e.getMessage());
        }
        return conexion; // Orain bai, konexioa (edo null) bueltatzen du
    }

    // Metodo honek jasotzen duen konexioa ixten du
    public void deskonektatu(Connection kon) {
        try {
            if (kon != null && !kon.isClosed()) {
                kon.close();
            }
        } catch (SQLException e) {
            System.out.println("❌ Errorea deskonektatzerakoan: " + e.getMessage());
        }
    }
}