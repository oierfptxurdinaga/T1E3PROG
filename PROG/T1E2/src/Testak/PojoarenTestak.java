package Testak;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.*;

public class PojoarenTestak {

    private Administradorea admin;
    private ErabiltzaileNormala erabiltzaile;
    private Jaurdunaldia jaurdunaldia;
    private Jokalaria jokalaria;
    private Partidua partidua;
    private Presidentea presidente;
    private Puntuazioa puntuazioa;
    private Taldea taldea;
    private ArrayList<Jokalaria> jokalariak;

    @BeforeEach
    void setUp() {
        // Inicialización general
        admin = new Administradorea("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");
        erabiltzaile = new ErabiltzaileNormala("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");
        presidente = new Presidentea("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");
        
        jokalariak = new ArrayList<>();
        jokalaria = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "12345678A", "Real Sociedad", 50);
        
        // El constructor de Partidua ahora pide ID al principio
        partidua = new Partidua(1, "Real Sociedad", "Barcelona", 2, 1, "15/01/2026", "20:00");
        
        // Jaurdunaldia ahora usa IDs y nombres de equipos
        jaurdunaldia = new Jaurdunaldia(1, 10, "Real Sociedad", "Barcelona");
        
        taldea = new Taldea("Real Sociedad", "1909", "Aratz Elexpe", 50000, 30, 20, 50, 25, 10, jokalariak);
    }

    // --- TESTS ADMINISTRADOREA ---
    @Test
    void testAdmGetters() {
        assertEquals("Aratz", admin.getizena());
        assertEquals("aelexpe", admin.geterabiltzailea());
        assertEquals("Admin", admin.baimenak());
    }

    // --- TESTS JOKALARIA ---
    @Test
    void testJoKonstruktoreaetaGetters() {
        assertEquals("Aratz", jokalaria.getIzena());
        assertEquals("Real Sociedad", jokalaria.getTaldea());
        assertEquals(50, jokalaria.getPrezioa());
    }

    @Test
    void testJoKonstruktoreKopia() {
        Jokalaria copia = new Jokalaria(jokalaria);
        assertEquals(jokalaria.getIzena(), copia.getIzena());
        assertEquals(jokalaria.getNAN(), copia.getNAN());
    }

    @Test
    void testCompareToAbizena() {
        Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50);
        Jokalaria j2 = new Jokalaria("Carlos", "Gomez", "01/01/2000", "222", "RS", 50);
        assertTrue(j1.compareTo(j2) < 0); 
    }

    @Test
    void testEqualsJokalariaBerdinak() {
        // Según tu código, el equals usa Nombre y Apellido
        Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50);
        Jokalaria j2 = new Jokalaria("Aratz", "Elexpe", "02/02/2001", "222", "Barcelona", 60);
        assertTrue(j1.equals(j2));
    }

    // --- TESTS PARTIDUA ---
    @Test
    void testPaGetters() {
        assertEquals(1, partidua.getId_Par());
        assertEquals("Real Sociedad", partidua.getTaldeLokala());
        assertEquals(2, partidua.getResultLokala());
    }

    @Test
    void testSetResultLokalaPa() {
        partidua.setResultLokala(3);
        assertEquals(3, partidua.getResultLokala());
    }

    // --- TESTS JAURDUNALDIA ---
    @Test
    void testJaKonstruktorea() {
        assertEquals(1, jaurdunaldia.getIdPar());
        assertEquals(10, jaurdunaldia.getIdJaurdu());
        assertEquals("Real Sociedad", jaurdunaldia.getTaldeIrabazlea());
    }

    // --- TESTS TALDEA ---
    @Test
    void testTaGetters() {
        assertEquals("Real Sociedad", taldea.getIzena());
        assertEquals(50000, taldea.getN_Bazkideak());
        assertEquals(50, taldea.getPuntuTotalak());
    }

    @Test
    void testToStringTaldea() {
        String resultado = taldea.toString();
        assertTrue(resultado.contains("Real Sociedad"));
        assertTrue(resultado.contains("1909"));
    }

    // --- TESTS ERABILTZAILE MOTAK ---
    @Test
    void testBaimenak() {
        assertEquals("Arrunta", erabiltzaile.baimenak());
        assertEquals("Presidentea", presidente.baimenak());
    }
}