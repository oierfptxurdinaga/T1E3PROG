package DAO;

import java.sql.*;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Taldea;

public class KlasifikazioaDao {
    private ConexionDB db = new ConexionDB();

    public void modifyKlasifikazioa(Taldea t) {
        // UPDATE bat egiten dugu taldearen izena erabiliz filtro gisa
        String sql = "UPDATE klasifikazioa SET Puntutotalak = ?, Par_Irabaziak = ?, " + 
                     "Par_Galdutak = ?, Puntos_F = ?, Puntos_C = ? " + 
                     "WHERE Talde_Izena = ?";

        try (Connection kon = db.konektatu();
             PreparedStatement ps = kon.prepareStatement(sql)) {

            ps.setInt(1, t.getPuntuTotalak());
            ps.setInt(2, t.getIrabazitakoak());
            ps.setInt(3, t.getGaldutakoak());
            ps.setInt(4, t.getPuntuakF());
            ps.setInt(5, t.getPuntuakC());
            ps.setString(6, t.getIzena()); // Hemen bilatzen du zein talde aldatu

            int filas = ps.executeUpdate();
            
            if (filas == 0) {
                System.out.println("Abisua: Ez da talderik aurkitu izen honekin: " + t.getIzena());
            }

        } catch (Exception e) {
            System.out.println("Errorea Modify Klasifikazioan: " + e.getMessage());
        }
    }
}