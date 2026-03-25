package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import DB.ConexionDB;
import E2.Partidua;

public class PartiduaDao {
	private ConexionDB db = new ConexionDB();

	public ArrayList<Partidua> kargatuPartiduak() {
		ArrayList<Partidua> zerrenda = new ArrayList<>();
		// SQL-ko DATE() eta TIME() funtzioak erabiltzen dira 'ordutegia' zutabea bitan
		// banatzeko (data eta ordua)
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
						rs.getInt("Result_Lok"), rs.getInt("Result_Bis"),
						// 'as' bidez sortutako ezizenak (alias) erabiltzen dira data eta ordua lortzeko
						rs.getString("fecha"), rs.getString("hora")));
			}
		} catch (Exception e) {
			System.out.println("Errorea DAO partiduak: " + e.getMessage());
		}
		return zerrenda;
	}

	public void eguneratuPuntuak(int idPar, int pLoc, int pVis) {
		// Zure taulako zutabe izenak: Result_Lok, Result_Bis, Id_Par
		String sql = "UPDATE partiduak SET Result_Lok = ?, Result_Bis = ? WHERE Id_Par = ?";
		// Konexioa ireki eta SQL agindua prestatu 'Try-with-resources' blokean
		try (Connection kon = db.konektatu(); PreparedStatement pstmt = kon.prepareStatement(sql)) {
			// Metodoaren parametroak (puntuak eta id-a) SQL-ko galdera-ikurretan (?) sartu
			pstmt.setInt(1, pLoc);
			pstmt.setInt(2, pVis);
			pstmt.setInt(3, idPar);
			// Datu-basean aldaketa egikaritu (UPDATE denez, executeUpdate erabiltzen da)
			pstmt.executeUpdate();
		} catch (Exception e) {
			// Errore bat gertatzekotan, mezu bat erakutsiko du arazoren bat egon dela
			// adierazteko
			System.out.println("Errorea partiduak eguneratzean: " + e.getMessage());
		}
	}

	public void modifyPartiduaEtaJaurdunaldi(int idPar, int resLoc, int resVis, String irabazlea, String galtzailea) {
		String sqlPartidua = "UPDATE partiduak SET Result_Lok = ?, Result_Bis = ? WHERE Id_Par = ?";
		String sqlJaurdu = "UPDATE jaurdunaldia SET Talde_Irabazlea = ?, Talde_Galdu = ? WHERE Id_Par = ?";
		// Datu-baserako konexio bakarra ireki, bi eguneraketak (UPDATE) barnean egiteko
		try (Connection kon = db.konektatu()) {
			// AutoCommit-a egiaztatu: transakzio bakoitza berehala gauzatuko dela
			// ziurtatzen du
			kon.setAutoCommit(true);
			// Lehenengo PreparedStatement-a: Partiduko emaitza zehatzak eguneratzeko
			try (PreparedStatement ps1 = kon.prepareStatement(sqlPartidua)) {
				ps1.setInt(1, resLoc);
				ps1.setInt(2, resVis);
				ps1.setInt(3, idPar);
				ps1.executeUpdate();
			}
			// Bigarren PreparedStatement-a: Irabazlearen eta galtzailearen izenak
			// eguneratzeko
			try (PreparedStatement ps2 = kon.prepareStatement(sqlJaurdu)) {
				ps2.setString(1, irabazlea);
				ps2.setString(2, galtzailea);
				ps2.setInt(3, idPar);
				ps2.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void eguneratuPartiduakGuztiak(ArrayList<Partidua> partiduakMasterList) {
		PartiduaDao pDao = new PartiduaDao();
		// ArrayList-eko objektu bakoitzeko (for-each) eguneraketa prozesua hasten
		// da
		for (Partidua p : partiduakMasterList) {
			String irabazlea = null;
			String galtzailea = null;
			// Partiduaren emaitza aztertu irabazlea eta galtzailea nor den erabakitzeko
			if (p.getResultLokala() > p.getResulBisitari()) {
				irabazlea = p.getTaldeLokala();
				galtzailea = p.getTaldeBisitari();
			} else if (p.getResulBisitari() > p.getResultLokala()) {
				irabazlea = p.getTaldeBisitari();
				galtzailea = p.getTaldeLokala();
			}
			// DAO-ko metodoari deitu datu-baseko bi taulak (partiduak eta jaurdunaldia)
			// aldatzeko
			pDao.modifyPartiduaEtaJaurdunaldi(p.getId_Par(), p.getResultLokala(), p.getResulBisitari(), irabazlea,
					galtzailea);
		}
		// Erabiltzaileari prozesua ondo amaitu dela jakinarazteko leiho bat erakutsi
		JOptionPane.showMessageDialog(null, "Datu-basea ondo sinkronizatu da ArrayList-arekin!");
	}
}