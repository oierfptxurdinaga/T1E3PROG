package Testak;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Presidentea;

class PresidenteTest {

    private Presidentea presidente;

    @BeforeEach
    void setUp() throws Exception {
        presidente = new Presidentea("Jon", "Garcia", "87654321B", "jgarcia", "54321");
    }

    @Test
    void testPresidenteKontruktoreaEtaGetters() {
        assertEquals("Jon", presidente.getizena());
        assertEquals("Garcia", presidente.getabizena());
        assertEquals("87654321B", presidente.getNAN());
        assertEquals("jgarcia", presidente.geterabiltzailea());
        assertEquals("54321", presidente.getpasahitza());
    }

    @Test
    void testSetIzenaPresidente() {
        presidente.setizena("Carlos");
        assertEquals("Carlos", presidente.getizena());
    }

    @Test
    void testSetAbizenaPresidente() {
        presidente.setabizena("Lopez");
        assertEquals("Lopez", presidente.getabizena());
    }

    @Test
    void testSetErabiltzaileaPresidente() {
        presidente.seterabiltzailea("clopez");
        assertEquals("clopez", presidente.geterabiltzailea());
    }

    @Test
    void testSetPasahitzaPresidente() {
        presidente.setpasahitza("abcd123");
        assertEquals("abcd123", presidente.getpasahitza());
    }

    @Test
    void testBaimenakPresidente() {
        assertEquals("Presidentea", presidente.baimenak());
    }
}
