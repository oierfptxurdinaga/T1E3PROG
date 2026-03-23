package Testak;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import E2.Puntuazioa;
import E2.Taldea;
import E2.Jokalaria;

class PuntuazioaTest {

    private Puntuazioa puntuazioa;
    private Taldea t1;
    private Taldea t2;
    private ArrayList<Taldea> taldeak;

    @BeforeEach
    void setUp() throws Exception {
        // Creamos algunos jugadores para los equipos
        Jokalaria j1 = new Jokalaria("Jon", "Garcia", "2000-01-01", "12345678A", "TaldeA", 1000);
        Jokalaria j2 = new Jokalaria("Ana", "Lopez", "2001-05-05", "87654321B", "TaldeB", 1200);
        ArrayList<Jokalaria> jokalariak1 = new ArrayList<>(Arrays.asList(j1));
        ArrayList<Jokalaria> jokalariak2 = new ArrayList<>(Arrays.asList(j2));

        t1 = new Taldea("TaldeA", "1990", "Jon President", 50, 100, 200, 300, 20, 10, jokalariak1);
        t2 = new Taldea("TaldeB", "1995", "Ana President", 60, 150, 250, 400, 25, 15, jokalariak2);

        taldeak = new ArrayList<>(Arrays.asList(t1, t2));

        puntuazioa = new Puntuazioa(taldeak);
    }

    @Test
    void testConstructorEtaGetter() {
        ArrayList<Taldea> result = puntuazioa.getPuntuazioa();
        assertEquals(2, result.size());
        assertTrue(result.contains(t1));
        assertTrue(result.contains(t2));
    }

    @Test
    void testSetter() {
        // Creamos un nuevo conjunto de equipos
        Jokalaria j3 = new Jokalaria("Luis", "Martinez", "2002-03-03", "11223344C", "TaldeC", 900);
        ArrayList<Jokalaria> jokalariak3 = new ArrayList<>(Arrays.asList(j3));
        Taldea t3 = new Taldea("TaldeC", "2000", "Luis President", 40, 80, 160, 240, 15, 5, jokalariak3);

        ArrayList<Taldea> taldeakBerria = new ArrayList<>(Arrays.asList(t3));
        puntuazioa.setPuntuazioa(taldeakBerria);

        ArrayList<Taldea> result = puntuazioa.getPuntuazioa();
        assertEquals(1, result.size());
        assertTrue(result.contains(t3));
    }
}
