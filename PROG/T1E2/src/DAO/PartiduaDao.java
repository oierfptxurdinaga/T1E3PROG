package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
   
    public void eguneratuPuntuak(int idPar, int pLoc, int pVis) {
        // Zure taulako zutabe izenak: Result_Lok, Result_Bis, Id_Par
        String sql = "UPDATE partiduak SET Result_Lok = ?, Result_Bis = ? WHERE Id_Par = ?";

        try (Connection kon = db.konektatu();
             PreparedStatement pstmt = kon.prepareStatement(sql)) {

            pstmt.setInt(1, pLoc);
            pstmt.setInt(2, pVis);
            pstmt.setInt(3, idPar);

            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Errorea partiduak eguneratzean: " + e.getMessage());
        }
    }

    public void modifyPartiduaEtaJaurdunaldi(int idPar, int resLoc, int resVis, String irabazlea, String galtzailea) {
        String sqlPartidua = "UPDATE partiduak SET Result_Lok = ?, Result_Bis = ? WHERE Id_Par = ?";
        String sqlJaurdu = "UPDATE jaurdunaldia SET Talde_Irabazlea = ?, Talde_Galdu = ? WHERE Id_Par = ?";

        // TRY-WITH-RESOURCES erabili konexioa automatikoki itxi eta gordetzeko
        try (Connection kon = db.konektatu()) {
            kon.setAutoCommit(true); // Ziurtatu aldaketa bakoitza berehala gordetzen dela

            try (PreparedStatement ps1 = kon.prepareStatement(sqlPartidua)) {
                ps1.setInt(1, resLoc);
                ps1.setInt(2, resVis);
                ps1.setInt(3, idPar);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = kon.prepareStatement(sqlJaurdu)) {
                ps2.setString(1, irabazlea);
                ps2.setString(2, galtzailea);
                ps2.setInt(3, idPar);
                ps2.executeUpdate();
            }
            System.out.println("DB Partidua eta Jaurdunaldia OK: ID " + idPar);
        } catch (Exception e) {
            e.printStackTrace(); // Errore zehatza kontsolan ikusteko
        }
    }
    public static void eguneratuPartiduakGuztiak(ArrayList<Partidua> partiduakMasterList) {
        PartiduaDao pDao = new PartiduaDao();
        
        for (Partidua p : partiduakMasterList) {
            // 1. Kalkulatu nor den irabazlea eta galtzailea puntu berriekin
            String irabazlea = null;
            String galtzailea = null;

            if (p.getResultLokala() > p.getResulBisitari()) {
                irabazlea = p.getTaldeLokala();
                galtzailea = p.getTaldeBisitari();
            } else if (p.getResulBisitari() > p.getResultLokala()) {
                irabazlea = p.getTaldeBisitari();
                galtzailea = p.getTaldeLokala();
            }

            // 2. Bidali datu guztiak DAO-ari DBa eguneratzeko
            // (Puntuak partiduak taulan eta irabazlea jaurdunaldia taulan)
            pDao.modifyPartiduaEtaJaurdunaldi(p.getId_Par(), p.getResultLokala(), p.getResulBisitari(), irabazlea, galtzailea);
        }
        
        JOptionPane.showMessageDialog(null, "Datu-basea ondo sinkronizatu da ArrayList-arekin!");
    }
}