package Testak;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Taldea;
import E2.Jokalaria;

class TaldeaTest {

    private Taldea taldea;
    private Jokalaria j1;
    private Jokalaria j2;
    private ArrayList<Jokalaria> jokalariak;

    @BeforeEach
    void setUp() throws Exception {
        j1 = new Jokalaria("Jon", "Garcia", "2000-01-01", "12345678A", "TaldeA", 1000);
        j2 = new Jokalaria("Ana", "Lopez", "2001-05-05", "87654321B", "TaldeA", 1200);
        jokalariak = new ArrayList<>(Arrays.asList(j1, j2));

        taldea = new Taldea("TaldeA", "1990", "Jon President", 50, 100, 200, 300, 20, 10, jokalariak);
    }

    @Test
    void testConstructorEtaGetters() {
        assertEquals("TaldeA", taldea.getIzena());
        assertEquals("1990", taldea.getSorreraUrtea());
        assertEquals("Jon President", taldea.getLehendakari());
        assertEquals(50, taldea.getN_Bazkideak());
        assertEquals(100, taldea.getPuntuakF());
        assertEquals(200, taldea.getPuntuakC());
        assertEquals(300, taldea.getPuntuTotalak());
        assertEquals(20, taldea.getIrabazitakoak());
        assertEquals(10, taldea.getGaldutakoak());
        assertEquals(jokalariak, taldea.getJokalariak());
    }

    @Test
    void testSetters() {
        taldea.setIzena("TaldeB");
        assertEquals("TaldeB", taldea.getIzena());

        taldea.setLehendakari("Ana President");
        assertEquals("Ana President", taldea.getLehendakari());

        taldea.setN_Bazkideak(60);
        assertEquals(60, taldea.getN_Bazkideak());

        taldea.setPuntuakF(150);
        assertEquals(150, taldea.getPuntuakF());

        taldea.setPuntuakC(250);
        assertEquals(250, taldea.getPuntuakC());

        taldea.setPuntuTotalak(400);
        assertEquals(400, taldea.getPuntuTotalak());

        taldea.setIrabazitakoak(25);
        assertEquals(25, taldea.getIrabazitakoak());

        taldea.setGaldutakoak(15);
        assertEquals(15, taldea.getGaldutakoak());

        ArrayList<Jokalaria> jokalariBerriak = new ArrayList<>();
        Jokalaria j3 = new Jokalaria("Luis", "Martinez", "2002-03-03", "11223344C", "TaldeB", 900);
        jokalariBerriak.add(j3);
        taldea.setJokalariak(jokalariBerriak);
        assertEquals(jokalariBerriak, taldea.getJokalariak());
    }

    @Test
    void testToString() {
        String texto = taldea.toString();
        assertFalse(texto.contains("=== TaldeaA ==="));
        assertTrue(texto.contains("Lehendakari: Jon President"));
        assertTrue(texto.contains("Sorrera Urtea: 1990"));
        assertTrue(texto.contains("Baskide Kopurua: 50"));
        assertTrue(texto.contains("Jon"));
        assertFalse(texto.contains("Ana"));
    }
}
