package DB;

import java.sql.*;
import java.util.logging.Logger;

/**
 * MySQL datu-basearekin konexioa kudeatzen duen klasea. JDBC bidezko
 * komunikazioa ezartzen du.
 */
public class ConexionDB {
	private static final String URL = "jdbc:mysql://localhost:3306/bsf";
	private static final String USER = "root";
	private static final String PASS = "";
	private static final Logger logger = Logger.getLogger(ConexionDB.class.getName());

	/**
	 * Datu-basearekin konexioa irekitzen du. * @return Connection objektua edo null
	 * errore bat egon bada.
	 */
	public Connection konektatu() {
		Connection kon = null;
		try {
			// Driver-a kargatzen dela ziurtatu
			Class.forName("com.mysql.cj.jdbc.Driver");
			kon = DriverManager.getConnection(URL, USER, PASS);
			logger.info("Konexioa ondo ezarri da datu-basearekin.");
		} catch (Exception e) {
			logger.severe("Errorea konektatzerakoan: " + e.getMessage());
		}
		return kon;
	}

	/**
	 * Irekita dagoen konexio bat itxi egiten du. * @param kon Itxi nahi den
	 * Connection objektua.
	 */
	public void deskonektatu(Connection kon) {
		try {
			if (kon != null && !kon.isClosed()) {
				kon.close();
				logger.info("Datu-basea deskonektatua.");
			}
		} catch (SQLException e) {
			logger.warning("Errorea deskonektatzerakoan: " + e.getMessage());
		}
	}
}