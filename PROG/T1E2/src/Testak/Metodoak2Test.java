package Testak;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.*;
import Metodoak.Metodoak;

class Metodoak2Test {

    private Taldea t1, t2;
    private Partidua p1, p2;

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

        // Poblar listas maestras
        Metodoak.taldeakMasterList.clear();
        Metodoak.partiduakMasterList.clear();
        Metodoak.taldeakMasterList.addAll(Arrays.asList(t1, t2));
        Metodoak.partiduakMasterList.addAll(Arrays.asList(p1, p2));
    }

    @Test
    void testLoginUsuarios() {
        String permiso = Metodoak.login("ebilbao", "12345");
        assertEquals("Admin", permiso);

        permiso = Metodoak.login("aelexpe", "12345");
        assertEquals("Presidentea", permiso);

        permiso = Metodoak.login("kmunoz", "12345");
        assertEquals("ErabiltzaileNormala", permiso);

        permiso = Metodoak.login("inexistente", "000");
        assertNull(permiso);
    }

    @Test
    void testKalkulatuKlasifikazioa() {
        Metodoak.kalkulatuKlasifikazioa();

        // Verificar puntos y victorias después del cálculo
        assertEquals(4, t1.getPuntuTotalak());
        assertEquals(2, t1.getIrabazitakoak());
        assertEquals(1, t1.getGaldutakoak());

        assertEquals(2, t2.getPuntuTotalak());
        assertEquals(1, t2.getIrabazitakoak());
        assertEquals(2, t2.getGaldutakoak());
    }

    @Test
    void testProzesatuEmaitzak() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Local");
        modelo.addColumn("PuntosL");
        modelo.addColumn("vs");
        modelo.addColumn("PuntosV");
        modelo.addColumn("Visitante");
        modelo.addColumn("ID");

        // Fila válida
        modelo.addRow(new Object[]{"TaldeA", 5, "vs", 2, "TaldeB", 1});
        // Fila con error (string en lugar de número)
        modelo.addRow(new Object[]{"TaldeB", "abc", "vs", 2, "TaldeA", 2});

        Metodoak.prozesatuEmaitzak(modelo);

        assertEquals(5, Metodoak.partiduakMasterList.get(0).getResultLokala());
        assertEquals(2, Metodoak.partiduakMasterList.get(0).getResulBisitari());
    }

    @Test
    void testActualizarTablasTaldeak() {
        DefaultTableModel modeloPeq = new DefaultTableModel();
        DefaultTableModel modeloGrande = new DefaultTableModel();

        JTable tablaPeq = new JTable(modeloPeq);
        JTable tablaGrande = new JTable(modeloGrande);

        // Llamamos al método con equipo válido
        assertDoesNotThrow(() -> Metodoak.actualizarTablasTaldeak("TaldeA", tablaPeq, tablaGrande));

        // Llamamos al método con null (no lanza excepción)
        assertDoesNotThrow(() -> Metodoak.actualizarTablasTaldeak(null, tablaPeq, tablaGrande));

        // Llamamos al método con equipo que no existe
        assertDoesNotThrow(() -> Metodoak.actualizarTablasTaldeak("EquipoX", tablaPeq, tablaGrande));
    }
}
