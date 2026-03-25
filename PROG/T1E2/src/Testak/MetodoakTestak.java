package Testak;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.table.DefaultTableModel;

import E2.*;
import Metodoak.Metodoak;

class MetodoakTestak {

    private Taldea t1;
    private Taldea t2;
    private Partidua p1;
    private Partidua p2;

    @BeforeEach
    void setUp() {
        // Crear jugadores
        Jokalaria j1 = new Jokalaria("Jon", "Garcia", "2000-01-01", "123", "TaldeA", 1000);
        Jokalaria j2 = new Jokalaria("Ana", "Lopez", "2001-02-02", "456", "TaldeB", 1200);

        // Crear equipos
        t1 = new Taldea("TaldeA", "1990", "PresA", 50, 0, 0, 0, 0, 0, new ArrayList<>(Arrays.asList(j1)));
        t2 = new Taldea("TaldeB", "1995", "PresB", 60, 0, 0, 0, 0, 0, new ArrayList<>(Arrays.asList(j2)));

        // Crear partidos
        p1 = new Partidua(1, "TaldeA", "TaldeB", 3, 1, null, null);
        p2 = new Partidua(2, "TaldeB", "TaldeA", 2, 2, null, null);

        // Limpiar y poblar listas maestras
        Metodoak.taldeakMasterList.clear();
        Metodoak.partiduakMasterList.clear();
        Metodoak.taldeakMasterList.addAll(Arrays.asList(t1, t2));
        Metodoak.partiduakMasterList.addAll(Arrays.asList(p1, p2));
    }

    @Test
    void testLogin() {
        // Debe cargar usuarios y devolver permisos
        String permiso = Metodoak.login("ebilbao", "12345");
        assertEquals("Admin", permiso);

        permiso = Metodoak.login("aelexpe", "12345");
        assertEquals("Presidentea", permiso);

        permiso = Metodoak.login("kmunoz", "12345");
        assertEquals("ErabiltzaileNormala", permiso); // suponiendo que ErabiltzaileNormala.baimenak() devuelve esto

        permiso = Metodoak.login("usuarioInexistente", "000");
        assertNull(permiso);
    }

    @Test
<<<<<<< HEAD
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
=======
    void testKalkulatuKlasifikazioa() {
>>>>>>> 5bfb3ebf22b353c88d2b6a644a52ef748243e9c4
        Metodoak.kalkulatuKlasifikazioa();

        // Después del cálculo
        assertEquals(4, t1.getPuntuTotalak()); // t1 gana 3-1 y empata 2-2: 2 + 2 = 4
        assertEquals(2, t1.getIrabazitakoak()); 
        assertEquals(1, t1.getGaldutakoak()); 

        assertEquals(2, t2.getPuntuTotalak()); // t2 pierde 3-1 y empata 2-2: 0 + 2 = 2
        assertEquals(1, t2.getIrabazitakoak());
        assertEquals(2, t2.getGaldutakoak());
    }

    @Test
    void testProzesatuEmaitzak() {
        // Creamos un modelo simulado
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Local");
        modelo.addColumn("PuntosL");
        modelo.addColumn("vs");
        modelo.addColumn("PuntosV");
        modelo.addColumn("Visitante");
        modelo.addColumn("ID");

        modelo.addRow(new Object[]{"TaldeA", 5, "vs", 2, "TaldeB", 1}); // p1
        modelo.addRow(new Object[]{"TaldeB", 1, "vs", 3, "TaldeA", 2}); // p2

        Metodoak.prozesatuEmaitzak(modelo);

        assertEquals(5, Metodoak.partiduakMasterList.get(0).getResultLokala());
        assertEquals(2, Metodoak.partiduakMasterList.get(0).getResulBisitari());

        assertEquals(1, Metodoak.partiduakMasterList.get(1).getResultLokala());
        assertEquals(3, Metodoak.partiduakMasterList.get(1).getResulBisitari());
    }
}
