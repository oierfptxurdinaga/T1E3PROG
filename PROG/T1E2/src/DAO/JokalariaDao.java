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
    public ArrayList<Jokalaria> kargatuJokalariak() {
        ArrayList<Jokalaria> lista = new ArrayList<>();
        
        // SQL JOIN: Partidak eta estatistikak taulak lotzen ditugu ID bidez
        String sql = "SELECT j.NAN, j.Jok_Izena, j.Jok_Abizena, j.Jaio_Data, j.Merka_Prezioa, j.Talde_Izena, j.Posizioa " +
                     "FROM jokalariak j";

        try (Connection kon = db.konektatu();
             PreparedStatement pstmt = kon.prepareStatement(sql)) {
                      
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Laburpena objektu berria sortu lerro bakoitzeko
                    Jokalaria lab = new Jokalaria(
                    		rs.getString("Jok_Izena"),
                    		rs.getString("Jok_Abizena"),
                    		rs.getString("Jaio_Data"),
                    		rs.getString("NAN"),
                            rs.getString("Talde_Izena"),
                            rs.getInt("Merka_Prezioa") );
                    lista.add(lab);
                }
            }
        } catch (SQLException e) {
            System.out.println("Errorea laburpena kargatzerakoan: " + e.getMessage());
        }
        
        return lista;
    }
}