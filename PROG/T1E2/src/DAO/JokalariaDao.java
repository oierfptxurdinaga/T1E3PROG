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

    /**
     * Jokalari baten partida guztien laburpena lortzen du, 
     * estatistikak eta partiden datuak uztartuz.
     */
    public ArrayList<Jokalaria> kargatuJokalariakTaldeka(String taldeIzena) {
        ArrayList<Jokalaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM jokalariak WHERE Talde_Izena = ?";
        try (Connection kon = db.konektatu();
             PreparedStatement pstmt = kon.prepareStatement(sql)) {
            pstmt.setString(1, taldeIzena);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Jokalaria(
                        rs.getString("Jok_Izena"), rs.getString("Jok_Abizena"),
                        rs.getString("Jaio_Data"), rs.getString("NAN"),
                        rs.getString("Talde_Izena"), rs.getInt("Merka_Prezioa")
                    ));
                }
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }
}