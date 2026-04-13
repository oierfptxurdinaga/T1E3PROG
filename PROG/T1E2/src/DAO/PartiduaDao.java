package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import DB.ConexionDB;
import E2.Partidua;

/**
 * Partiduen datuak kudeatzeko DAO klasea. Partiduen emaitzak kargatzeaz,
 * puntuak eguneratzeaz eta jaurdunaldiko informazioarekin sinkronizatzeaz
 * arduratzen da. * @author Talde1
 * 
 * @version 1.0
 */
public class PartiduaDao {

	/** Datu-basearekiko konexioa kudeatzen duen objektua */
	private ConexionDB db = new ConexionDB();

	/**
	 * Datu-basetik partidu guztien zerrenda kargatzen du, data eta ordua bereiziz.
	 * * @return Partidua objektuen ArrayList bat, DBko informazio guztiarekin.
	 */
	public ArrayList<Partidua> kargatuPartiduak() {
		ArrayList<Partidua> zerrenda = new ArrayList<>();
		// SQL-ko DATE() eta TIME() funtzioak erabiltzen dira 'ordutegia' zutabea bitan
		// banatzeko
		String sql = "SELECT Id_Par, Talde_Lok, Talde_Bis, Result_Lok, Result_Bis, "
				+ "DATE(ordutegia) as fecha, TIME(ordutegia) as hora FROM partiduak";
		// Konexioa automatikoki kudeatzen da 'try-with-resources' blokearen bidez
		try (Connection kon = db.konektatu();
				PreparedStatement pstmt = kon.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			// Datu-baseak bueltatutako lerro bakoitzeko, objektu berri bat sortu
			while (rs.next()) {
				// Partidua objektuaren eraikitzailea deitzen da zutabeen balioak erabiliz
				zerrenda.add(new Partidua(rs.getInt("Id_Par"), rs.getString("Talde_Lok"), rs.getString("Talde_Bis"),
						rs.getInt("Result_Lok"), rs.getInt("Result_Bis"), rs.getString("fecha"), rs.getString("hora")));
			}
		} catch (Exception e) {
			System.out.println("Errorea DAO partiduak kargatzean: " + e.getMessage());
		}
		return zerrenda;
	}

	/**
	 * Partidu baten puntuak soilik eguneratzen ditu 'partiduak' taulan. * @param
	 * idPar Eguneratu nahi den partiduaren IDa.
	 * 
	 * @param pLoc Talde lokalaren puntuak.
	 * @param pVis Bisitariaren puntuak.
	 */
	public void eguneratuPuntuak(int idPar, int pLoc, int pVis) {
		String sql = "UPDATE partiduak SET Result_Lok = ?, Result_Bis = ? WHERE Id_Par = ?";
		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			// Parametroak SQL-ko galdera-ikurretan (?) sartu
			pstmt.setInt(1, pLoc);
			pstmt.setInt(2, pVis);
			pstmt.setInt(3, idPar);
			// Datu-basean aldaketa egikaritu
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Errorea partiduak eguneratzean: " + e.getMessage());
		}
	}

	/**
	 * Partidu baten emaitza eta jaurdunaldiko irabazlea/galtzailea eguneratzen ditu
	 * transakzio baten bidez. * @param idPar Partiduaren IDa.
	 * 
	 * @param resLoc     Lokalaren emaitza.
	 * @param resVis     Bisitariaren emaitza.
	 * @param irabazlea  Irabazi duen taldearen izena.
	 * @param galtzailea Galdu duen taldearen izena.
	 */
	public void modifyPartiduaEtaJaurdunaldi(int idPar, int resLoc, int resVis, String irabazlea, String galtzailea) {
		String sqlPartidua = "UPDATE partiduak SET Result_Lok = ?, Result_Bis = ? WHERE Id_Par = ?";
		String sqlJaurdu = "UPDATE jaurdunaldia SET Talde_Irabazlea = ?, Talde_Galdu = ? WHERE Id_Par = ?";
		try (Connection kon = db.konektatu()) {
			// AutoCommit-a aktibatu aldaketak berehala gauzatzeko
			kon.setAutoCommit(true);
			// 1. Partiduko emaitza zehatzak eguneratu
			try (PreparedStatement ps1 = kon.prepareStatement(sqlPartidua)) {
				ps1.setInt(1, resLoc);
				ps1.setInt(2, resVis);
				ps1.setInt(3, idPar);
				ps1.executeUpdate();
			}
			// 2. Jaurdunaldiko irabazle eta galtzaileak eguneratu
			try (PreparedStatement ps2 = kon.prepareStatement(sqlJaurdu)) {
				ps2.setString(1, irabazlea);
				ps2.setString(2, galtzailea);
				ps2.setInt(3, idPar);
				ps2.executeUpdate();
			}
		} catch (Exception e) {
			System.err.println("Errorea modifyPartiduaEtaJaurdunaldi exekutatzean: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Memorian dagoen partidu zerrenda osoa datu-basearekin sinkronizatzen du.
	 * Irabazleak eta galtzaileak automatikoki kalkulatzen ditu eguneraketa
	 * bakoitzeko. * @param partiduakMasterList Sinkronizatu nahi den partidu
	 * zerrenda.
	 */
	public static void eguneratuPartiduakGuztiak(ArrayList<Partidua> partiduakMasterList) {
		PartiduaDao pDao = new PartiduaDao();
		for (Partidua p : partiduakMasterList) {
			String irabazlea = null;
			String galtzailea = null;
			// Emaitza aztertu irabazlea eta galtzailea nor den erabakitzeko
			if (p.getResultLokala() > p.getResulBisitari()) {
				irabazlea = p.getTaldeLokala();
				galtzailea = p.getTaldeBisitari();
			} else if (p.getResulBisitari() > p.getResultLokala()) {
				irabazlea = p.getTaldeBisitari();
				galtzailea = p.getTaldeLokala();
			}
			// DBko bi taulak sinkronizatu
			pDao.modifyPartiduaEtaJaurdunaldi(p.getId_Par(), p.getResultLokala(), p.getResulBisitari(), irabazlea,
					galtzailea);
		}
		// Erabiltzaileari prozesua ondo amaitu dela jakinarazi
		JOptionPane.showMessageDialog(null, "Datu-basea ondo sinkronizatu da ArrayList-arekin!");
	}
}