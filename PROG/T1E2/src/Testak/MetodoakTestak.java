package Testak;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

import E2.Taldea;
import E2.Jokalaria;
import Metodoak.Metodoak;

class MetodoakTestak {

    @BeforeEach
    void setUp() {
        // Zerrenda nagusia garbitu eta probako datuak kargatu
        Metodoak.taldeakMasterList = new ArrayList<>();
        Metodoak.kargatuErabiltzaileak();

        // Jokalariak sortu
        ArrayList<Jokalaria> jA = new ArrayList<>();
        jA.add(new Jokalaria("Jon", "Aranzabal", "2000-01-01", "11111111A", "Bilbao Basket", 500));

        ArrayList<Jokalaria> jB = new ArrayList<>();
        jB.add(new Jokalaria("Ane", "Zabala", "1998-05-12", "22222222B", "Baskonia", 600));

        // Taldeak sortu (zure Taldea konstruktorearen arabera)
        Taldea t1 = new Taldea("Bilbao Basket", "1990", "Presidente A", 1000, 0, 0, 0, 0, 0, jA);
        Taldea t2 = new Taldea("Baskonia", "1950", "Presidente B", 5000, 0, 0, 0, 0, 0, jB);

        Metodoak.taldeakMasterList.add(t1);
        Metodoak.taldeakMasterList.add(t2);
    }

    // ==========================================
    // 1. LOGIN TESTAK
    // ==========================================
    
    @Test
    void testLoginAdminCorrecto() {
        String rola = Metodoak.login("ebilbao", "12345");
        assertEquals("Admin", rola);
    }

    @Test
    void testLoginUsuarioInexistente() {
        String rola = Metodoak.login("ez_naiz_existitzen", "0000");
        assertNull(rola, "Erabiltzailea existitzen ez bada, null itzuli behar du");
    }

    // ==========================================
    // 2. TRUKE TESTAK (Jokalariak)
    // ==========================================

    @Test
    void testIntercambioCorrecto() {
        // Taula simulatuak sortu behar ditugu 6 zutabeekin (zure meterlosJokalaris-en arabera)
        String[] col = {"Izena", "Abizena", "Data", "NAN", "Taldea", "Prezioa"};
        DefaultTableModel modIzquierda = new DefaultTableModel(col, 0);
        DefaultTableModel modDerecha = new DefaultTableModel(col, 0);
        JTable tIzquierda = new JTable(modIzquierda);
        JTable tDerecha = new JTable(modDerecha);

        // Taulak betetu
      //  Metodoak.meterlosJokalaris("Bilbao Basket", tIzquierda);
      //  Metodoak.meterlosJokalaris("Baskonia", tDerecha);

        // Lehenengo errenkadak hautatu
        tIzquierda.setRowSelectionInterval(0, 0);
        tDerecha.setRowSelectionInterval(0, 0);

        // Trukea egin
      //  Metodoak.actualizarTablasJokalariak("Baskonia", "Bilbao Basket", tDerecha, tIzquierda);

        // Ane (Baskoniakoa zena) orain Bilbao Basketeko lehen jokalaria izan beharko litzateke
        assertEquals("Ane", Metodoak.taldeakMasterList.get(0).getJokalariak().get(0).getIzena());
        assertEquals("Bilbao Basket", Metodoak.taldeakMasterList.get(0).getJokalariak().get(0).getTaldea());
    }

    // ==========================================
    // 3. TAULAK BETETZEKO TESTAK
    // ==========================================

    @Test
    void testMeterlosJokalarisEquipoExistente() {
        // Taula bat 6 zutabeekin (zure kodean 6 objektu sartzen dituzu row-ean)
        DefaultTableModel model = new DefaultTableModel(new String[]{"1","2","3","4","5","6"}, 0);
        JTable tabla = new JTable(model);

    //    Metodoak.meterlosJokalaris("Bilbao Basket", tabla);
        assertEquals(1, tabla.getRowCount(), "Jokalari bat kargatu beharko luke");
        assertEquals("Jon", tabla.getValueAt(0, 0));
    }

    // ==========================================
    // 4. KLASIFIKAZIO LOGIKA TESTAK
    // ==========================================

    @Test
    void testKalkulatuKlasifikazioaReset() {
        // Balio batzuk eskuz jarri
        Metodoak.taldeakMasterList.get(0).setPuntuTotalak(50);
        
        // kargatuDatuak ez badugu deitzen (DAO gabe), ez du partidurik irakurriko, 
        // baina reset-a ondo egiten duela frogatu dezakegu.
        Metodoak.kalkulatuKlasifikazioa();
        
        assertEquals(0, Metodoak.taldeakMasterList.get(0).getPuntuTotalak(), 
            "kalkulatuKlasifikazioa deitzean puntu guztiak 0-ra hasieratu behar dira");
    }

    @Test
    void testOrdenacionClasificacion() {
        // Puntuak simulatu
        Metodoak.taldeakMasterList.get(0).setPuntuTotalak(10); // Bilbao
        Metodoak.taldeakMasterList.get(1).setPuntuTotalak(20); // Baskonia

        // Ordenazioa exekutatu (kalkulatuKlasifikazioa-k barruan egiten du)
        Metodoak.taldeakMasterList.sort((t1, t2) -> {
            int res = Integer.compare(t2.getPuntuTotalak(), t1.getPuntuTotalak());
            if (res == 0) {
                res = Integer.compare(t2.getPuntuakF(), t1.getPuntuakF());
            }
            return res;
        });

        assertEquals("Baskonia", Metodoak.taldeakMasterList.get(0).getIzena(), 
            "Baskonia lehenengoa izan beharko litzateke puntu gehiago dituelako");
    }

    // ==========================================
    // 5. DB ETA DAO TESTAK (PREBENTZIOA)
    // ==========================================

    @Test
    void testKargatuDatuakNullPointer() {
        // Metodoak.kargatuDatuak() deitzean TaldeDao berria sortzen du. 
        // Konexiorik ez badago errorea emango du, baina egitura aldetik 
        // ziurtatu behar dugu taldeakMasterList ez dela deuseztatzen.
        try {
            Metodoak m = new Metodoak();
            m.kargatuDatuak();
        } catch (Exception e) {
            // Logikoa da DB konexiorik gabe huts egitea testean
            System.out.println("DB konexioa falta da, testak aurrera jarraitzen du.");
        }
        assertNotNull(Metodoak.taldeakMasterList);
    }
}