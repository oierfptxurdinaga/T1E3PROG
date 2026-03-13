package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DB.ConexionDB;
import E2.Partidua;

public class PartiduaDao {
    private ConexionDB db = new ConexionDB();

    public ArrayList<Partidua> kargatuPartiduak() {
        ArrayList<Partidua> lista = new ArrayList<>();
        // Separamos el DATETIME en fecha (DATE) y hora (TIME) para tu POJO
        String sql = "SELECT Id_Par, Talde_Lok, Talde_Bis, Result_Lok, Result_Bis, " +
                     "DATE(ordutegia) as fecha, TIME(ordutegia) as hora FROM partiduak";
        
        try (Connection kon = db.konektatu();
             PreparedStatement pstmt = kon.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Creamos el objeto Partidua usando los datos de la tabla
                lista.add(new Partidua(
                	rs.getInt("Id_Par"),
                    rs.getString("Talde_Lok"),
                    rs.getString("Talde_Bis"),
                    rs.getInt("Result_Lok"),
                    rs.getInt("Result_Bis"),
                    rs.getString("fecha"), // Mapea a private String Data
                    rs.getString("hora")   // Mapea a private String Ordua
                ));
            }
        } catch (Exception e) {
            System.out.println("Errorea DAO partiduak: " + e.getMessage());
        }
        return lista;
    }
}