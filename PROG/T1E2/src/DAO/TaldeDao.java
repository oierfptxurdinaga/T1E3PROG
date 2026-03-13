package DAO;

import java.sql.*;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Taldea;
import E2.Jokalaria;

public class TaldeDao {
    private ConexionDB db = new ConexionDB();
    private JokalariaDao jokalariaDao = new JokalariaDao(); // Jokalarien DAO-ari erreferentzia

    public ArrayList<Taldea> kargatuTaldeak() {
        ArrayList<Taldea> taldeLista = new ArrayList<>();
        
        // SQL: taldeak eta klasifikazioa elkartu (POJOrako behar dituzun puntu guztiak lortzeko)
        String sql = "SELECT t.Talde_Izena, t.Sorrera_Data, t.Lehendakari_Izena, t.N_Bazkideak, " +
                     "k.Puntos_F, k.Puntos_C, k.Puntutotalak, k.Par_Irabaziak, k.Par_Galdutak " +
                     "FROM taldeak t " +
                     "LEFT JOIN klasifikazioa k ON t.Talde_Izena = k.Talde_Izena";

        try (Connection kon = db.konektatu();
             PreparedStatement pstmt = kon.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String tIzena = rs.getString("Talde_Izena");

                // --- DEIA BESTE DAO-ARI ---
                // Talde bakoitzeko, bere jokalariak lortzen ditugu JokalariaDao-tik
                ArrayList<Jokalaria> jokalariak = jokalariaDao.kargatuJokalariak();

                // --- TALDE OBJEKTUA (10 parametro) ---
                Taldea taldeBerria = new Taldea(
                    tIzena,                                // izena
                    rs.getString("Sorrera_Data"),          // sorreraUrtea
                    rs.getString("Lehendakari_Izena"),     // lehendakari
                    rs.getInt("N_Bazkideak"),              // n_Bazkideak
                    rs.getInt("Puntos_F"),                 // puntuakF
                    rs.getInt("Puntos_C"),                 // puntuakC
                    rs.getInt("Puntutotalak"),             // puntuTotalak
                    rs.getInt("Par_Irabaziak"),            // irabazitakoak
                    rs.getInt("Par_Galdutak"),             // galdutakoak
                    jokalariak                             // ArrayList<Jokalaria>
                );

                taldeLista.add(taldeBerria);
            }
        } catch (SQLException e) {
            System.out.println("Errorea taldeak kargatzerakoan: " + e.getMessage());
        }
        return taldeLista;
    }
}