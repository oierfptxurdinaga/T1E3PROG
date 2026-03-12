	package Utils;
	
	import java.io.*;
	import java.util.ArrayList;
	import E2.Jokalaria;
	import E2.Taldea;
	
	public class DatuakImplementatu {
	
	    public static void main(String[] args) {
	        ArrayList<Taldea> taldeakList = new ArrayList<>();
	
	        // --- IBAIZABAL ---
	        ArrayList<Jokalaria> jIbaizabal = new ArrayList<>();
	        jIbaizabal.add(new Jokalaria("Jon", "Etxeberria", "1992-03-10", "11111101A", "Ibaizabal", 52000, 0));
	        jIbaizabal.add(new Jokalaria("Miren", "Aguirre", "1993-05-12", "11111102B", "Ibaizabal", 51000, 0));
	        jIbaizabal.add(new Jokalaria("Aitor", "Salazar", "1991-08-20", "11111103C", "Ibaizabal", 49500, 0));
	        jIbaizabal.add(new Jokalaria("Amaia", "Lekue", "1994-01-18", "11111104D", "Ibaizabal", 50500, 0));
	        jIbaizabal.add(new Jokalaria("Beñat", "Ruiz", "1990-12-03", "11111105E", "Ibaizabal", 51500, 0));
	        jIbaizabal.add(new Jokalaria("Iker", "Aranburu", "1992-04-22", "11111106F", "Ibaizabal", 50000, 0));
	        jIbaizabal.add(new Jokalaria("Ane", "Muguruza", "1993-09-19", "11111107G", "Ibaizabal", 48000, 0));
	        jIbaizabal.add(new Jokalaria("Jon", "Odriozola", "1991-01-25", "11111108H", "Ibaizabal", 47000, 0));
	        jIbaizabal.add(new Jokalaria("Uxue", "Zabala", "1994-02-15", "11111109I", "Ibaizabal", 46000, 0));
	        
	        // Add Taldea Ibaizabal Taldeen listara
	        taldeakList.add(new Taldea("Ibaizabal", "2015-03-10", "Ane", 110, 0, 0, 0, 0, 0, jIbaizabal));
	
	        // --- LASALLE ---
	        ArrayList<Jokalaria> jLaSalle = new ArrayList<>();
	        jLaSalle.add(new Jokalaria("Unai", "Elosegi", "1992-04-14", "22222201A", "LaSalle", 43000, 0));
	        jLaSalle.add(new Jokalaria("Iñigo", "Sola", "1991-08-27", "22222202B", "LaSalle", 44000, 0));
	        jLaSalle.add(new Jokalaria("Ane", "Lazkano", "1994-02-06", "22222203C", "LaSalle", 41000, 0));
	        jLaSalle.add(new Jokalaria("Jon", "Altuna", "1990-11-15", "22222204D", "LaSalle", 45000, 0));
	        jLaSalle.add(new Jokalaria("Peio", "Aranburu", "1993-06-01", "22222205E", "LaSalle", 43500, 0));
	        jLaSalle.add(new Jokalaria("Uxue", "Muguruza", "1995-09-19", "22222206F", "LaSalle", 42500, 0));
	        jLaSalle.add(new Jokalaria("Julen", "Odriozola", "1991-01-25", "22222207G", "LaSalle", 44500, 0));
	        jLaSalle.add(new Jokalaria("Miren", "Iriondo", "1993-03-03", "22222208H", "LaSalle", 42000, 0));
	        jLaSalle.add(new Jokalaria("Aitor", "Zia", "1990-10-10", "22222209I", "LaSalle", 46000, 0));
	        
	        // Add Taldea LaSalle Taldeen listara
	        taldeakList.add(new Taldea("LaSalle", "2017-07-15", "Jon", 95, 0, 0, 0, 0, 0, jLaSalle));
	
	        // --- SALESIANOS ---
	        ArrayList<Jokalaria> jSalesianos = new ArrayList<>();
	        jSalesianos.add(new Jokalaria("Aritz", "Beloki", "1990-10-10", "33333301A", "Salesianos", 56000, 0));
	        jSalesianos.add(new Jokalaria("Maialen", "Etxaniz", "1994-05-21", "33333302B", "Salesianos", 47000, 0));
	        jSalesianos.add(new Jokalaria("Jon", "Lertxundi", "1992-02-14", "33333303C", "Salesianos", 52000, 0));
	        jSalesianos.add(new Jokalaria("Unax", "Arregi", "1993-08-08", "33333304D", "Salesianos", 53000, 0));
	        jSalesianos.add(new Jokalaria("Maddi", "Zubiri", "1995-11-30", "33333305E", "Salesianos", 46000, 0));
	        jSalesianos.add(new Jokalaria("Iosu", "Urkiza", "1991-06-17", "33333306F", "Salesianos", 54000, 0));
	        jSalesianos.add(new Jokalaria("Ekhi", "Garate", "1990-09-02", "33333307G", "Salesianos", 55000, 0));
	        jSalesianos.add(new Jokalaria("June", "Altube", "1995-01-17", "33333308H", "Salesianos", 45500, 0));
	        jSalesianos.add(new Jokalaria("Iñigo", "Lazcano", "1990-09-09", "33333309I", "Salesianos", 50000, 0));
	        
	        // Add Taldea Salesianos Taldeen listara
	        taldeakList.add(new Taldea("Salesianos", "2016-05-22", "Maite", 120, 0, 0, 0, 0, 0, jSalesianos));
	        
	        // --- GERNIKA ---
	        ArrayList<Jokalaria> jGernika = new ArrayList<>();
	        jGernika.add(new Jokalaria("Ander", "Goikoetxea", "1989-03-14", "77777701A", "Gernika", 47000, 0));
	        jGernika.add(new Jokalaria("Leire", "Aranburu", "1994-08-21", "77777702B", "Gernika", 46000, 0));
	        jGernika.add(new Jokalaria("Iker", "Etxeberria", "1991-11-02", "77777703C", "Gernika", 48000, 0));
	        jGernika.add(new Jokalaria("Beñat", "Salazar", "1988-04-19", "77777704D", "Gernika", 49000, 0));
	        jGernika.add(new Jokalaria("Nerea", "Mendizabal", "1997-02-06", "77777705E", "Gernika", 45500, 0));
	        jGernika.add(new Jokalaria("Asier", "Urkizu", "1990-09-27", "77777706F", "Gernika", 50000, 0));
	        jGernika.add(new Jokalaria("Maialen", "Otxoa", "1995-12-03", "77777707G", "Gernika", 46500, 0));
	        jGernika.add(new Jokalaria("Julen", "Irazabal", "1992-07-11", "77777708H", "Gernika", 49500, 0));
	        jGernika.add(new Jokalaria("Irati", "Larrinaga", "1999-05-25", "77777709I", "Gernika", 47000, 0));
	        
	        // Add Taldea Gernika Taldeen listara

	        taldeakList.add(new Taldea("Gernika", "2019-07-13", "Eder", 95, 0, 0, 0, 0, 0, jGernika));


	        // --- TABIRAKO ---
	        ArrayList<Jokalaria> jTabirako = new ArrayList<>();
	        jTabirako.add(new Jokalaria("Aitor", "Santamaria", "1991-07-07", "44444401A", "Tabirako", 44000, 0));
	        jTabirako.add(new Jokalaria("Jon", "Bengoetxea", "1993-03-19", "44444402B", "Tabirako", 45000, 0));
	        jTabirako.add(new Jokalaria("Eneko", "Lizarralde", "1990-01-28", "44444403C", "Tabirako", 46000, 0));
	        jTabirako.add(new Jokalaria("June", "Arbulu", "1994-10-12", "44444404D", "Tabirako", 43000, 0));
	        jTabirako.add(new Jokalaria("Iñaki", "Osa", "1992-12-05", "44444405E", "Tabirako", 45500, 0));
	        jTabirako.add(new Jokalaria("Uxue", "Mikelarena", "1995-04-03", "44444406F", "Tabirako", 42000, 0));
	        jTabirako.add(new Jokalaria("Gari", "Ugarte", "1991-08-16", "44444407G", "Tabirako", 47000, 0));
	        jTabirako.add(new Jokalaria("Nerea", "Aldaz", "1993-09-29", "44444408H", "Tabirako", 44000, 0));
	        jTabirako.add(new Jokalaria("Ane", "Zabaleta", "1992-11-11", "44444409I", "Tabirako", 46500, 0));
	        
	        // Add Taldea Tabirako Taldeen listara
	        taldeakList.add(new Taldea("Tabirako", "2018-11-30", "Irati", 80, 0, 0, 0, 0, 0, jTabirako));
	
	        // --- LOIOLA ---
	        ArrayList<Jokalaria> jLoiola = new ArrayList<>();
	        jLoiola.add(new Jokalaria("Miren", "Egaña", "1994-06-06", "55555501A", "Loiola", 48000, 0));
	        jLoiola.add(new Jokalaria("Jon", "Iriondo", "1991-11-11", "55555502B", "Loiola", 50000, 0));
	        jLoiola.add(new Jokalaria("Asier", "Azkona", "1990-03-23", "55555503C", "Loiola", 51000, 0));
	        jLoiola.add(new Jokalaria("Ane", "Uranga", "1995-01-09", "55555504D", "Loiola", 47000, 0));
	        jLoiola.add(new Jokalaria("Iker", "Zubeldia", "1992-08-18", "55555505E", "Loiola", 49500, 0));
	        jLoiola.add(new Jokalaria("Leire", "Odria", "1993-12-27", "55555506F", "Loiola", 48500, 0));
	        jLoiola.add(new Jokalaria("Beñat", "Iglesias", "1991-04-04", "55555507G", "Loiola", 50500, 0));
	        jLoiola.add(new Jokalaria("Nahia", "Lete", "1994-09-15", "55555508H", "Loiola", 49000, 0));
	        jLoiola.add(new Jokalaria("Aitor", "Mendizabal", "1990-11-30", "55555509I", "Loiola", 47500, 0));
	        
	        // Add Taldea Loiola Taldeen listara
	        taldeakList.add(new Taldea("Loiola", "2014-01-05", "Iker", 105, 0, 0, 0, 0, 0, jLoiola));
	
	        // --- UNAMUNO ---
	        ArrayList<Jokalaria> jUnamuno = new ArrayList<>();
	        jUnamuno.add(new Jokalaria("Mikel", "Eguren", "1990-02-02", "66666601A", "Unamuno", 47000, 0));
	        jUnamuno.add(new Jokalaria("Aitor", "Zia", "1993-07-13", "66666602B", "Unamuno", 46000, 0));
	        jUnamuno.add(new Jokalaria("Unai", "Barandiaran", "1991-10-24", "66666603C", "Unamuno", 48000, 0));
	        jUnamuno.add(new Jokalaria("Jon", "Elorza", "1992-05-05", "66666604D", "Unamuno", 49000, 0));
	        jUnamuno.add(new Jokalaria("June", "Altube", "1995-01-17", "66666605E", "Unamuno", 45500, 0));
	        jUnamuno.add(new Jokalaria("Iñigo", "Lazcano", "1990-09-09", "66666606F", "Unamuno", 50000, 0));
	        jUnamuno.add(new Jokalaria("Uxue", "Serrano", "1994-11-28", "66666607G", "Unamuno", 46500, 0));
	        jUnamuno.add(new Jokalaria("Gorka", "Moya", "1991-06-30", "66666608H", "Unamuno", 49500, 0));
	        jUnamuno.add(new Jokalaria("Amaia", "Zabala", "1992-12-12", "66666609I", "Unamuno", 47000, 0));
	        
	        // Add Taldea Unamuno Taldeen listara
	        taldeakList.add(new Taldea("Unamuno", "2019-09-12", "Sin Presidente", 90, 0, 0, 0, 0, 0, jUnamuno));
	
	        // GUARDAR FICHERO
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datuak.ser"))) {
	            oos.writeObject(taldeakList);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
