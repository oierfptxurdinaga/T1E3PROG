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
        // Limpiamos la lista maestra y cargamos datos de prueba controlados
        Metodoak.taldeakMasterList = new ArrayList<>();
        Metodoak.kargatuErabiltzaileak();

        ArrayList<Jokalaria> jA = new ArrayList<>();
        jA.add(new Jokalaria("Jon", "Aranzabal", "2000-01-01", "11111111A", "Bilbao Basket", 500, 10));

        ArrayList<Jokalaria> jB = new ArrayList<>();
        jB.add(new Jokalaria("Ane", "Zabala", "1998-05-12", "22222222B", "Baskonia", 600, 15));

        Taldea t1 = new Taldea("Bilbao Basket", "1990", "Presidente A", 1000, 0, 0, 0, 0, 0, jA);
        Taldea t2 = new Taldea("Baskonia", "1950", "Presidente B", 5000, 0, 0, 0, 0, 0, jB);

        Metodoak.taldeakMasterList.add(t1);
        Metodoak.taldeakMasterList.add(t2);
    }

    // ==========================================
    // 1. TESTS DE LOGIN (BIEN Y MAL)
    // ==========================================
    
    @Test
    void testLoginAdminCorrecto() {
        // Caso: Credenciales correctas de admin
        String rola = Metodoak.login("ebilbao", "12345");
        assertEquals("Admin", rola);
    }

    @Test
    void testLoginUsuarioInexistente() {
        // Caso: El usuario no existe en la lista
        String rola = Metodoak.login("no_existo", "0000");
        assertNull(rola, "Si el usuario no existe, debe devolver null");
    }

    @Test
    void testLoginPasswordIncorrecto() {
        // Caso: Usuario existe pero la contraseña está mal
        String rola = Metodoak.login("ebilbao", "wrong_pass");
        assertNull(rola, "Con contraseña incorrecta debe devolver null");
    }

    // ==========================================
    // 2. TESTS DE INTERCAMBIO (BIEN Y MAL)
    // ==========================================

    @Test
    void testIntercambioCorrecto() {
        String[] col = {"Izena", "Abizena", "DNI", "Taldea"};
        JTable tIzquierda = new JTable(new DefaultTableModel(col, 0));
        JTable tDerecha = new JTable(new DefaultTableModel(col, 0));

        Metodoak.meterlosJokalaris("Bilbao Basket", tIzquierda);
        Metodoak.meterlosJokalaris("Baskonia", tDerecha);

        // Simulamos selección de fila 0 en ambas tablas
        tIzquierda.setRowSelectionInterval(0, 0);
        tDerecha.setRowSelectionInterval(0, 0);

        Metodoak.actualizarTablasJokalariak("Baskonia", "Bilbao Basket", tDerecha, tIzquierda);

        // Verificamos que Ane (de Baskonia) ahora está en Bilbao Basket
        assertEquals("Ane", Metodoak.taldeakMasterList.get(0).getJokalariak().get(0).getIzena());
    }

    @Test
    void testIntercambioSinSeleccion() {
        // Caso: Se intenta intercambiar sin haber seleccionado ninguna fila en las tablas
        JTable tIzquierda = new JTable(new DefaultTableModel(new String[]{"I"}, 0));
        JTable tDerecha = new JTable(new DefaultTableModel(new String[]{"D"}, 0));

        // No hacemos setRowSelectionInterval -> selectedRow será -1
        assertDoesNotThrow(() -> {
            Metodoak.actualizarTablasJokalariak("Baskonia", "Bilbao Basket", tDerecha, tIzquierda);
        }, "El método debería controlar si no hay selección para no lanzar excepción");
    }

    // ==========================================
    // 3. TESTS DE CARGA DE TABLAS (BIEN Y MAL)
    // ==========================================

    @Test
    void testMeterlosJokalarisEquipoExistente() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Nombre", "Apellido", "3", "4", "5", "6", "7"}, 0);
        JTable tabla = new JTable(model);

        Metodoak.meterlosJokalaris("Bilbao Basket", tabla);
        assertEquals(1, tabla.getRowCount(), "Debería haber cargado 1 jugador");
        assertEquals("Jon", tabla.getValueAt(0, 0));
    }

    @Test
    void testMeterlosJokalarisEquipoVacioOInexistente() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"1", "2", "3", "4", "5", "6", "7"}, 0);
        JTable tabla = new JTable(model);

        // Intentamos cargar un equipo que no está en la MasterList
        Metodoak.meterlosJokalaris("Real Madrid", tabla);
        assertEquals(0, tabla.getRowCount(), "La tabla debe quedar vacía si el equipo no existe");
    }

    // ==========================================
    // 4. TESTS DE PERSISTENCIA (FICHEROS)
    // ==========================================

    

    @Test
    void testGordeDatuakNoLanzaExcepcion() {
        // Verificamos que el guardado de datos no explota (aunque no tengamos permisos en disco, debería estar controlado)
        assertDoesNotThrow(() -> Metodoak.gordeDatuak());
    }

    @Test
    void testKargatuDatuakEstructura() {
        // Verificamos que tras cargar datos, la lista maestra no sea nula
        Metodoak.kargatuDatuak();
        assertNotNull(Metodoak.taldeakMasterList, "La lista maestra no debe ser nula tras cargar");
    }

    // ==========================================
    // 5. TEST DE CLASIFICACIÓN (LOGICA)
    // ==========================================

    @Test
    void testOrdenacionClasificacion() {
        // Forzamos puntos para testear el sorteo/comparación
        Metodoak.taldeakMasterList.get(0).setPuntuTotalak(10); // Bilbao
        Metodoak.taldeakMasterList.get(1).setPuntuTotalak(20); // Baskonia

        // Simulamos la lógica de ordenación que usas en el Visual
        Metodoak.taldeakMasterList.sort((t1, t2) -> Integer.compare(t2.getPuntuTotalak(), t1.getPuntuTotalak()));

        assertEquals("Baskonia", Metodoak.taldeakMasterList.get(0).getIzena(), "Baskonia debería estar primero por tener más puntos");
    }
}