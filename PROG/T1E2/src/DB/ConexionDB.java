package DB;

import java.sql.*;

public class ConexionDB {
	private static final String URL = "jdbc:mysql://localhost:3306/bsf";
	private static final String USER = "root";
	private static final String PASS = "";

	public Connection konektatu() {
		Connection kon = null;
		try {
			// Forzamos la carga del driver para evitar el error de "No suitable driver"
			Class.forName("com.mysql.cj.jdbc.Driver");
			kon = DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			System.out.println("Errorea konektatzerakoan: " + e.getMessage());
		}
		return kon;
	}

	public void deskonektatu(Connection kon) {
		try {
			if (kon != null && !kon.isClosed()) {
				kon.close();
			}
		} catch (SQLException e) {
			System.out.println("Errorea deskonektatzerakoan: " + e.getMessage());
		}
	}
}