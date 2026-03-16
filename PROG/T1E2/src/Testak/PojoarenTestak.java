package Testak;



import static org.junit.jupiter.api.Assertions.*;



import java.util.ArrayList;



import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;



import E2.Administradorea;

import E2.Denboraldia;

import E2.ErabiltzaileMota;

import E2.ErabiltzaileNormala;

import E2.Jaurdunaldia;

import E2.Jokalaria;

import E2.Partidua;

import E2.Presidentea;

import E2.Puntuazioa;

import E2.Taldea;



public class PojoarenTestak {



	private Administradorea admin;

	private Denboraldia denboraldia;

	private ArrayList<Jaurdunaldia> jaurdunaldiak;

	private ArrayList<Puntuazioa> puntuazioak;

	private ErabiltzaileMota erabiltzailea;

	private ErabiltzaileNormala erabiltzaile;

	private Jaurdunaldia jaurdunaldia;

	private ArrayList<Partidua> partidos;

	private Jokalaria jokalaria;

	private Partidua partidua;

	private Presidentea presidente;

	private Puntuazioa puntuazioa;

	private ArrayList<Taldea> taldeak;

	private Taldea taldea;

	private ArrayList<Jokalaria> jokalariak;



	@BeforeEach

	void setUpAdm() {

		admin = new Administradorea("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");

	}



	@Test

	void testAdmKontruktoreaetaGetters() {

		assertEquals("Aratz", admin.getizena());

		assertEquals("Elexpe", admin.getabizena());

		assertEquals("12345678A", admin.getNAN());

		assertEquals("aelexpe", admin.geterabiltzailea());

		assertEquals("12345", admin.getpasahitza());

	}



	@Test

	void testSetIzenaAdm() {

		admin.setizena("Carlos");

		assertEquals("Carlos", admin.getizena());

	}



	@Test

	void testSetAbizenaAdm() {

		admin.setabizena("Gomez");

		assertEquals("Gomez", admin.getabizena());

	}



	@Test

	void testSetErabiltzaileaAdm() {

		admin.seterabiltzailea("carlosg");

		assertEquals("carlosg", admin.geterabiltzailea());

	}



	@Test

	void testSetPasahitzaAdm() {

		admin.setpasahitza("abcd");

		assertEquals("abcd", admin.getpasahitza());

	}



	@Test

	void testBaimenakAdm() {

		assertEquals("Admin", admin.baimenak());

	}



	@BeforeEach

	void setUpDen() {

		jaurdunaldiak = new ArrayList<>();

		puntuazioak = new ArrayList<>();



		denboraldia = new Denboraldia("2024-2025", jaurdunaldiak, puntuazioak);

	}



	@Test

	void testDenKonstruktoreaetaGetters() {

		assertEquals("2024-2025", denboraldia.getData());

		assertEquals(jaurdunaldiak, denboraldia.getDenboraldia());

		assertEquals(puntuazioak, denboraldia.getDenboraldiaP());

	}



	@Test

	void testSetDataDen() {

		denboraldia.setData("2025-2026");

		assertEquals("2025-2026", denboraldia.getData());

	}



	@Test

	void testSetDenboraldia() {

		ArrayList<Jaurdunaldia> nuevaLista = new ArrayList<>();

		denboraldia.setDenboraldia(nuevaLista);

		assertEquals(nuevaLista, denboraldia.getDenboraldia());

	}



	@Test

	void testSetDenboraldiaP() {

		ArrayList<Puntuazioa> nuevaListaP = new ArrayList<>();

		denboraldia.setDenboraldiaP(nuevaListaP);

		assertEquals(nuevaListaP, denboraldia.getDenboraldiaP());

	}



	private class ErabiltzaileMotaFake extends ErabiltzaileMota {



		public ErabiltzaileMotaFake(String izena, String abizena, String NAN, String erabiltzailea, String pasahitza) {

			super(izena, abizena, NAN, erabiltzailea, pasahitza);

		}



		@Override

		public String baimenak() {

			return "TEST";

		}

	}



	@BeforeEach

	void setUpEM() {

		erabiltzailea = new ErabiltzaileMotaFake("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");

	}



	@Test

	void testEMKonstruktoreaetaGetters() {

		assertEquals("aelexpe", erabiltzailea.getErabiltzailea());

		assertEquals("12345", erabiltzailea.getPasahitza());

	}



	@Test

	void testBaimenakEM() {

		assertEquals("TEST", erabiltzailea.baimenak());

	}



	@BeforeEach

	void setUpEN() {

		erabiltzaile = new ErabiltzaileNormala("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");

	}



	@Test

	void testENKonstruktoreaetaGetters() {

		assertEquals("Aratz", erabiltzaile.getizena());

		assertEquals("Elexpe", erabiltzaile.getabizena());

		assertEquals("12345678A", erabiltzaile.getNAN());

		assertEquals("aelexpe", erabiltzaile.geterabiltzailea());

		assertEquals("12345", erabiltzaile.getpasahitza());

	}



	@Test

	void testSetIzenaEN() {

		erabiltzaile.setizena("Carlos");

		assertEquals("Carlos", erabiltzaile.getizena());

	}



	@Test

	void testSetAbizenaEN() {

		erabiltzaile.setabizena("Gomez");

		assertEquals("Gomez", erabiltzaile.getabizena());

	}



	@Test

	void testSetErabiltzaileaEN() {

		erabiltzaile.seterabiltzailea("carlosg");

		assertEquals("carlosg", erabiltzaile.geterabiltzailea());

	}



	@Test

	void testSetPasahitzaEN() {

		erabiltzaile.setpasahitza("abcd");

		assertEquals("abcd", erabiltzaile.getpasahitza());

	}



	@Test

	void testBaimenakEN() {

		assertEquals("Arrunta", erabiltzaile.baimenak());

	}



	@BeforeEach

	void setUpJa() {

		partidos = new ArrayList<>();

		jaurdunaldia = new Jaurdunaldia(partidos);

	}



	@Test

	void testJaKonstruktoreaetaGetters() {

		assertEquals(partidos, jaurdunaldia.getJaurdunaldia());

	}



	@Test

	void testSetterJa() {

		ArrayList<Partidua> nuevaLista = new ArrayList<>();

		jaurdunaldia.setJaurdunaldia(nuevaLista);

		assertEquals(nuevaLista, jaurdunaldia.getJaurdunaldia());

	}



	@BeforeEach

	void setUpJo() {

		jokalaria = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "12345678A", "Real Sociedad", 50, 80);

	}



	@Test

	void testJoKonstruktoreaetaGetters() {

		assertEquals("Aratz", jokalaria.getIzena());

		assertEquals("Elexpe", jokalaria.getAbizena());

		assertEquals("01/01/2000", jokalaria.getJaiotzeData());

		assertEquals("12345678A", jokalaria.getNAN());

		assertEquals("Real Sociedad", jokalaria.getTaldea());

		assertEquals(50, jokalaria.getPrezioa());

		assertEquals(80, jokalaria.getJokalarienPuntuak());

	}
	@Test
	void testJoKonstruktoreKopia() {
	    Jokalaria copia = new Jokalaria(jokalaria);

	    assertEquals(jokalaria.getIzena(), copia.getIzena());
	    assertEquals(jokalaria.getAbizena(), copia.getAbizena());
	    assertEquals(jokalaria.getJaiotzeData(), copia.getJaiotzeData());
	    assertEquals(jokalaria.getNAN(), copia.getNAN());
	    assertEquals(jokalaria.getTaldea(), copia.getTaldea());
	    assertEquals(jokalaria.getPrezioa(), copia.getPrezioa());
	    assertEquals(jokalaria.getJokalarienPuntuak(), copia.getJokalarienPuntuak());
	}



	@Test

	void testSetIzenaJo() {

		jokalaria.setIzena("Carlos");

		assertEquals("Carlos", jokalaria.getIzena());

	}



	@Test

	void testSetAbizenaJo() {

		jokalaria.setAbizena("Gomez");

		assertEquals("Gomez", jokalaria.getAbizena());

	}



	@Test

	void testSetTaldeaJo() {

		jokalaria.setTaldea("Barcelona");

		assertEquals("Barcelona", jokalaria.getTaldea());

	}



	@Test

	void testSetPrezioaJo() {

		jokalaria.setPrezioa(100);

		assertEquals(100, jokalaria.getPrezioa());

	}
	@Test
	void testCompareToAbizena() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);
	    Jokalaria j2 = new Jokalaria("Carlos", "Gomez", "01/01/2000", "222", "RS", 50, 80);

	    assertTrue(j1.compareTo(j2) < 0); 
	}
	@Test
	void testCompareToIzena() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);
	    Jokalaria j2 = new Jokalaria("Carlos", "Elexpe", "01/01/2000", "222", "RS", 50, 80);

	    assertTrue(j1.compareTo(j2) < 0);
	}
	@Test
	void testCompareToBerdina() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);
	    Jokalaria j2 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "222", "RS", 50, 80);

	    assertEquals(0, j1.compareTo(j2));
	}
	@Test
	void testEqualsJokalariaBerdinak() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);
	    Jokalaria j2 = new Jokalaria("Aratz", "Elexpe", "02/02/2001", "222", "Barcelona", 60, 90);

	    assertTrue(j1.equals(j2));
	}
	@Test
	void testEqualsJokalariaEzberdinak() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);
	    Jokalaria j2 = new Jokalaria("Carlos", "Gomez", "01/01/2000", "222", "RS", 50, 80);

	    assertFalse(j1.equals(j2));
	}
	@Test
	void testEqualsObjektuBera() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);

	    assertTrue(j1.equals(j1));
	}
	@Test
	void testEqualsNull() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);

	    assertFalse(j1.equals(null));
	}
	@Test
	void testHashCode() {
	    Jokalaria j1 = new Jokalaria("Aratz", "Elexpe", "01/01/2000", "111", "RS", 50, 80);
	    Jokalaria j2 = new Jokalaria("Aratz", "Elexpe", "02/02/2001", "222", "Barcelona", 60, 90);

	    assertEquals(j1.hashCode(), j2.hashCode());
	}




	@Test

	void testSetJokalarienPuntuakJo() {

		jokalaria.setJokalarienPuntuak(90);

		assertEquals(90, jokalaria.getJokalarienPuntuak());

	}



	@BeforeEach

	void setUpPa() {

		partidua = new Partidua("Real Sociedad", "Barcelona", 2, 1, "15/01/2026", "20:00");

	}



	@Test

	void testPaKonstruktoreaetaGetters() {

		assertEquals("Real Sociedad", partidua.getTaldeLokala());

		assertEquals("Barcelona", partidua.getTaldeBisitari());

		assertEquals(2, partidua.getResultLokala());

		assertEquals(1, partidua.getResulBisitari());

		assertEquals("15/01/2026", partidua.getData());

		assertEquals("20:00", partidua.getOrdua());

	}



	@Test

	void testSetTaldeLokalaPa() {

		partidua.setTaldeLokala("Athletic");

		assertEquals("Athletic", partidua.getTaldeLokala());

	}



	@Test

	void testSetTaldeBisitariPa() {

		partidua.setTaldeBisitari("Valencia");

		assertEquals("Valencia", partidua.getTaldeBisitari());

	}



	@Test

	void testSetResultLokalaPa() {

		partidua.setResultLokala(3);

		assertEquals(3, partidua.getResultLokala());

	}



	@Test

	void testSetResulBisitariPa() {

		partidua.setResulBisitari(2);

		assertEquals(2, partidua.getResulBisitari());

	}



	@Test

	void testSetDataPa() {

		partidua.setData("20/01/2026");

		assertEquals("20/01/2026", partidua.getData());

	}



	@Test

	void testSetOrduaPa() {

		partidua.setOrdua("21:30");

		assertEquals("21:30", partidua.getOrdua());

	}



	@BeforeEach

	void setUpPr() {

		presidente = new Presidentea("Aratz", "Elexpe", "12345678A", "aelexpe", "12345");

	}



	@Test

	void testPrKonstruktoreaetaGetters() {

		assertEquals("Aratz", presidente.getizena());

		assertEquals("Elexpe", presidente.getabizena());

		assertEquals("12345678A", presidente.getNAN());

		assertEquals("aelexpe", presidente.geterabiltzailea());

		assertEquals("12345", presidente.getpasahitza());

	}



	@Test

	void testSetIzenaPr() {

		presidente.setizena("Carlos");

		assertEquals("Carlos", presidente.getizena());

	}



	@Test

	void testSetAbizenaPr() {

		presidente.setabizena("Gomez");

		assertEquals("Gomez", presidente.getabizena());

	}



	@Test

	void testSetErabiltzaileaPr() {

		presidente.seterabiltzailea("carlosg");

		assertEquals("carlosg", presidente.geterabiltzailea());

	}



	@Test

	void testSetPasahitzaPr() {

		presidente.setpasahitza("abcd");

		assertEquals("abcd", presidente.getpasahitza());

	}



	@Test

	void testBaimenakPr() {

		assertEquals("Presidentea", presidente.baimenak());

	}



	@BeforeEach

	void setUpPu() {

		taldeak = new ArrayList<>();

		puntuazioa = new Puntuazioa(taldeak);

	}



	@Test

	void testPuKonstruktoreaetaGetters() {

		assertEquals(taldeak, puntuazioa.getPuntuazioa());

	}



	@Test

	void testSetterPu() {

		ArrayList<Taldea> nuevaLista = new ArrayList<>();

		puntuazioa.setPuntuazioa(nuevaLista);

		assertEquals(nuevaLista, puntuazioa.getPuntuazioa());

	}



	@BeforeEach

	void setUpTa() {

		jokalariak = new ArrayList<>();

		taldea = new Taldea("Real Sociedad", "1909", "Aratz Elexpe", 50000, 30, 20, 50, 25, 10, jokalariak);

	}



	@Test

	void testTaKonstruktoreaetaGetters() {

		assertEquals("Real Sociedad", taldea.getIzena());

		assertEquals("1909", taldea.getSorreraUrtea());

		assertEquals("Aratz Elexpe", taldea.getLehendakari());

		assertEquals(50000, taldea.getN_Bazkideak());

		assertEquals(30, taldea.getPuntuakF());

		assertEquals(20, taldea.getPuntuakC());

		assertEquals(50, taldea.getPuntuTotalak());

		assertEquals(25, taldea.getIrabazitakoak());

		assertEquals(10, taldea.getGaldutakoak());

		assertEquals(jokalariak, taldea.getJokalariak());

	}



	@Test

	void testSetIzenaTa() {

		taldea.setIzena("Athletic");

		assertEquals("Athletic", taldea.getIzena());

	}



	@Test

	void testSetLehendakariTa() {

		taldea.setLehendakari("Carlos Gomez");

		assertEquals("Carlos Gomez", taldea.getLehendakari());

	}



	@Test

	void testSetN_BazkideakTa() {

		taldea.setN_Bazkideak(60000);

		assertEquals(60000, taldea.getN_Bazkideak());

	}



	@Test

	void testSetPuntuakFTa() {

		taldea.setPuntuakF(35);

		assertEquals(35, taldea.getPuntuakF());

	}



	@Test

	void testSetPuntuakCTa() {

		taldea.setPuntuakC(25);

		assertEquals(25, taldea.getPuntuakC());

	}



	@Test

	void testSetPuntuTotalakTa() {

		taldea.setPuntuTotalak(60);

		assertEquals(60, taldea.getPuntuTotalak());

	}



	@Test

	void testSetIrabazitakoakTa() {

		taldea.setIrabazitakoak(30);

		assertEquals(30, taldea.getIrabazitakoak());

	}



	@Test

	void testSetGaldutakoakTa() {

		taldea.setGaldutakoak(12);

		assertEquals(12, taldea.getGaldutakoak());

	}



	@Test

	void testSetJokalariakTa() {

		ArrayList<Jokalaria> nuevaLista = new ArrayList<>();

		taldea.setJokalariak(nuevaLista);

		assertEquals(nuevaLista, taldea.getJokalariak());

	}
	
	@Test
	void testToStringTaldea() {
	    ArrayList<Jokalaria> jokalariak = new ArrayList<>();
	    jokalariak.add(new Jokalaria("Aratz", "Elexpe", "01/01/2000", "123", "RS", 50, 80));

	    Taldea taldea = new Taldea("Real Sociedad", "1909", "Aratz Elexpe",
	            50000, 30, 20, 50, 25, 10, jokalariak);

	    String resultado = taldea.toString();

	    assertTrue(resultado.contains("Real Sociedad"));
	    assertTrue(resultado.contains("Aratz Elexpe"));
	    assertTrue(resultado.contains("1909"));
	    assertTrue(resultado.contains("50000"));
	}


}