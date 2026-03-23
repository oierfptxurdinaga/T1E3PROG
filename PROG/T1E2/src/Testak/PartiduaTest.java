package Testak;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Partidua;

class PartiduaTest {

    private Partidua partidua;

    @BeforeEach
    void setUp() {
        partidua = new Partidua(1, "Athletic", "Real", 2, 1, "2024-01-01", "20:00");
    }

    @Test
    void testKonstruktoreaEtaGetters() {
        assertEquals(1, partidua.getId_Par());
        assertEquals("Athletic", partidua.getTaldeLokala());
        assertEquals("Real", partidua.getTaldeBisitari());
        assertEquals(2, partidua.getResultLokala());
        assertEquals(1, partidua.getResulBisitari());
        assertEquals("2024-01-01", partidua.getData());
        assertEquals("20:00", partidua.getOrdua());
    }

    @Test
    void testSetIdPar() {
        partidua.setId_Par(10);
        assertEquals(10, partidua.getId_Par());
    }

    @Test
    void testSetTaldeLokala() {
        partidua.setTaldeLokala("Barcelona");
        assertEquals("Barcelona", partidua.getTaldeLokala());
    }

    @Test
    void testSetTaldeBisitari() {
        partidua.setTaldeBisitari("Osasuna");
        assertEquals("Osasuna", partidua.getTaldeBisitari());
    }

    @Test
    void testSetResultLokala() {
        partidua.setResultLokala(3);
        assertEquals(3, partidua.getResultLokala());
    }

    @Test
    void testSetResultBisitari() {
        partidua.setResulBisitari(2);
        assertEquals(2, partidua.getResulBisitari());
    }

    @Test
    void testSetData() {
        partidua.setData("2025-05-10");
        assertEquals("2025-05-10", partidua.getData());
    }

    @Test
    void testSetOrdua() {
        partidua.setOrdua("18:30");
        assertEquals("18:30", partidua.getOrdua());
    }

    @Test
    void testBalioakAldatuOndoren() {
        partidua.setId_Par(99);
        partidua.setTaldeLokala("Valencia");
        partidua.setResultLokala(5);

        assertEquals(99, partidua.getId_Par());
        assertEquals("Valencia", partidua.getTaldeLokala());
        assertEquals(5, partidua.getResultLokala());

        // Beste balioak berdin jarraitzen dute
        assertEquals("Real", partidua.getTaldeBisitari());
        assertEquals(1, partidua.getResulBisitari());
    }
}
