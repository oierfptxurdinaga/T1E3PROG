package Testak;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Jokalaria;

class JokalariaTest {

    private Jokalaria jokalaria;

    @BeforeEach
    void setUp() {
        jokalaria = new Jokalaria("Iker", "Muniain", "1992-12-19", "12345678A", "Athletic", 100);
    }

    @Test
    void testKonstruktoreaEtaGetters() {
        assertEquals("Iker", jokalaria.getIzena());
        assertEquals("Muniain", jokalaria.getAbizena());
        assertEquals("1992-12-19", jokalaria.getJaiotzeData());
        assertEquals("12345678A", jokalaria.getNAN());
        assertEquals("Athletic", jokalaria.getTaldea());
        assertEquals(100, jokalaria.getPrezioa());
    }

    @Test
    void testSetters() {
        jokalaria.setIzena("Unai");
        jokalaria.setAbizena("Simon");
        jokalaria.setTaldea("Athletic");
        jokalaria.setPrezioa(200);

        assertEquals("Unai", jokalaria.getIzena());
        assertEquals("Simon", jokalaria.getAbizena());
        assertEquals("Athletic", jokalaria.getTaldea());
        assertEquals(200, jokalaria.getPrezioa());
    }

    @Test
    void testCopyConstructor() {
        Jokalaria copia = new Jokalaria(jokalaria);

        assertEquals(jokalaria.getIzena(), copia.getIzena());
        assertEquals(jokalaria.getAbizena(), copia.getAbizena());
        assertEquals(jokalaria.getJaiotzeData(), copia.getJaiotzeData());
        assertEquals(jokalaria.getNAN(), copia.getNAN());
        assertEquals(jokalaria.getTaldea(), copia.getTaldea());
        assertEquals(jokalaria.getPrezioa(), copia.getPrezioa());
    }

    @Test
    void testCompareToDesberdinak() {
        Jokalaria j1 = new Jokalaria("Aitor", "Zubizarreta", "1990-01-01", "1", "Team1", 50);
        Jokalaria j2 = new Jokalaria("Iker", "Muniain", "1992-01-01", "2", "Team2", 60);

        assertTrue(j1.compareTo(j2) > 0); // Z > M
        assertTrue(j2.compareTo(j1) < 0);
    }

    @Test
    void testCompareToAbizenaBerdina() {
        Jokalaria j1 = new Jokalaria("Aitor", "Garcia", "1990-01-01", "1", "Team1", 50);
        Jokalaria j2 = new Jokalaria("Iker", "Garcia", "1992-01-01", "2", "Team2", 60);

        assertTrue(j1.compareTo(j2) < 0); // Aitor < Iker
    }

    @Test
    void testEqualsEtaHashCode() {
        Jokalaria j1 = new Jokalaria("Iker", "Muniain", "1992-01-01", "1", "Team1", 50);
        Jokalaria j2 = new Jokalaria("Iker", "Muniain", "2000-01-01", "2", "Team2", 100);

        assertEquals(j1, j2);
        assertEquals(j1.hashCode(), j2.hashCode());
    }

    @Test
    void testNotEquals() {
        Jokalaria j1 = new Jokalaria("Iker", "Muniain", "1992-01-01", "1", "Team1", 50);
        Jokalaria j2 = new Jokalaria("Unai", "Simon", "1990-01-01", "2", "Team2", 60);

        assertNotEquals(j1, j2);
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        assertNotEquals(jokalaria, null);
        assertNotEquals(jokalaria, "String bat");
    }
}
