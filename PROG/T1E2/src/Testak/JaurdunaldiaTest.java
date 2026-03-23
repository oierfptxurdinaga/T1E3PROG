package Testak;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Jaurdunaldia;

class JaurdunaldiaTest {

    private Jaurdunaldia jaurdunaldia;

    @BeforeEach
    void setUp() {
        jaurdunaldia = new Jaurdunaldia(1, 10, "Athletic", "Real");
    }

    @Test
    void testKonstruktoreaEtaGetters() {
        assertEquals(1, jaurdunaldia.getIdPar());
        assertEquals(10, jaurdunaldia.getIdJaurdu());
        assertEquals("Athletic", jaurdunaldia.getTaldeIrabazlea());
        assertEquals("Real", jaurdunaldia.getTaldeGaldu());
    }

    @Test
    void testSetIdPar() {
        jaurdunaldia.setIdPar(5);
        assertEquals(5, jaurdunaldia.getIdPar());
    }

    @Test
    void testTaldeIrabazleaNull() {
        jaurdunaldia = new Jaurdunaldia(2, 20, null, "Barcelona");
        assertEquals("---", jaurdunaldia.getTaldeIrabazlea());
    }

    @Test
    void testTaldeGalduNull() {
        jaurdunaldia = new Jaurdunaldia(3, 30, "Osasuna", null);
        assertEquals("---", jaurdunaldia.getTaldeGaldu());
    }

    @Test
    void testBalioakAldatuOndoren() {
        jaurdunaldia.setIdPar(99);
        assertEquals(99, jaurdunaldia.getIdPar());
        assertEquals(10, jaurdunaldia.getIdJaurdu()); // ez da aldatzen
    }
}
