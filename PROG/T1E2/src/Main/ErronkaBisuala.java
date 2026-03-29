package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.JokalariaDao;
import DAO.JokalariaObjectDB;
import DAO.TaldeDao;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import E2.Taldea;
import E2.Jokalaria;
import E2.Partidua;
import Metodoak.Metodoak;

public class ErronkaBisuala extends JFrame implements ActionListener {

	private CardLayout cardLayout;
	private JPanel contentPanel;
	private Font titleFont;

	// Paneles
	private JPanel LoginPanela, HasierakoPanela, KlasifikazioaPanela, EmaitzaPanela, TaldeakPanela,
			JokalariakAldatuPanel;

	// Login
	private JTextField textErabiltzaile;
	private JPasswordField textPasahitza;
	private JButton sartu, ateraLogin;

	// Hasiera
	private JLabel logoaImg1, logoaImg2, img1, img2;
	private JButton atzerantzHasiera, ateraHasiera, klasifikazioaIkusi, sartuEmaitza, taldeakIkusi, jokalariakAldatuBtn;

	// Klasifikazioa
	private JTable tablaKlasif;
	private DefaultTableModel modeloTabla;
	private JButton atzerantzKlasif, ateraKlasif;

	// Taldeak
	private JComboBox<String> comboBox;
	private JTable tablaPequena, tablaGrande;
	private JButton atzerantzTaldeak, ateraTaldeak;

	// Emaitza
	private JTable tablaEmaitzak;
	private DefaultTableModel modeloEmaitzak;
	private JButton gordeEmaitza, atzerantzEmaitza, ateraEmaitza;

	// Jokalariak aldatu
	private JComboBox<Taldea> cbTalde1, cbTalde2;
	private DefaultListModel<Jokalaria> jokalariaModel;
	private JList<Jokalaria> jokalariaList;
	private JButton btnFitxatu;
	private JButton atzerantzAldatu, ateraAldatu;

	public ErronkaBisuala() {
		// 1. DATUAK KARGATU
		Metodoak m = new Metodoak();
		m.kargatuDatuak();

		// 2. JFRAME KONFIGURAZIOA
		setTitle("Bizkaiko Saskibaloi Federazioa");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(1000, 700);
		setLocationRelativeTo(null);
		titleFont = new Font("Verdana", Font.BOLD, 24);

		// 3. Inizializatu panelak eta osagaiak
		inizializatuPanelak();
		konfiguratuOsagaiBisualak();

		// 4. CardLayout konfiguratu
		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		setContentPane(contentPanel);

		contentPanel.add(LoginPanela, "Login");
		contentPanel.add(HasierakoPanela, "Hasiera");
		contentPanel.add(KlasifikazioaPanela, "Klasifikazioa");
		contentPanel.add(EmaitzaPanela, "Emaitzak");
		contentPanel.add(TaldeakPanela, "Taldeak");
		contentPanel.add(JokalariakAldatuPanel, "Jokalariak");

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Metodoak.atera();
			}
		});

		cardLayout.show(contentPanel, "Login");
		setVisible(true);
	}

	private void inizializatuPanelak() {
		// ---------------- LOGIN PANELA ----------------
		LoginPanela = new JPanel(null);
		JLabel titleLogin = new JLabel("BIZKAIKO SASKIBALOI FEDERAZIOA", JLabel.CENTER);
		titleLogin.setFont(titleFont);
		titleLogin.setBounds(38, 20, 900, 30);

		logoaImg1 = new JLabel("", JLabel.CENTER);
		logoaImg1.setBounds(420, 92, 200, 150);

		JLabel uLabel = new JLabel("Erabiltzailea:");
		uLabel.setBounds(250, 300, 150, 30);
		textErabiltzaile = new JTextField();
		textErabiltzaile.setBounds(420, 300, 250, 30);

		JLabel pLabel = new JLabel("Pasahitza:");
		pLabel.setBounds(250, 350, 150, 30);
		textPasahitza = new JPasswordField();
		textPasahitza.setBounds(420, 350, 250, 30);

		sartu = new JButton("Sartu");
		sartu.setBounds(483, 447, 100, 30);
		sartu.addActionListener(this);

		ateraLogin = new JButton("Atera");
		ateraLogin.setBounds(858, 60, 80, 30);
		ateraLogin.addActionListener(this);

		LoginPanela.add(titleLogin);
		LoginPanela.add(logoaImg1);
		LoginPanela.add(uLabel);
		LoginPanela.add(textErabiltzaile);
		LoginPanela.add(pLabel);
		LoginPanela.add(textPasahitza);
		LoginPanela.add(sartu);
		LoginPanela.add(ateraLogin);

		// ---------------- HASIERA PANELA ----------------
		HasierakoPanela = new JPanel(null);
		JLabel titleHasiera = new JLabel("HASIERA PANELA", JLabel.CENTER);
		titleHasiera.setFont(titleFont);
		titleHasiera.setBounds(50, 20, 900, 30);

		logoaImg2 = new JLabel();
		logoaImg2.setBounds(394, 60, 240, 211);
		img1 = new JLabel();
		img1.setBounds(100, 250, 350, 200);
		img2 = new JLabel();
		img2.setBounds(550, 250, 350, 200);

		atzerantzHasiera = new JButton("Atzerantz");
		atzerantzHasiera.setBounds(800, 50, 100, 30);
		atzerantzHasiera.addActionListener(this);

		ateraHasiera = new JButton("Atera");
		ateraHasiera.setBounds(910, 50, 70, 30);
		ateraHasiera.addActionListener(this);

		klasifikazioaIkusi = new JButton("Klasifikazioa ikusi");
		klasifikazioaIkusi.setBounds(150, 480, 250, 40);
		klasifikazioaIkusi.addActionListener(this);

		sartuEmaitza = new JButton("Sartu Emaitza");
		sartuEmaitza.setBounds(600, 480, 250, 40);
		sartuEmaitza.addActionListener(this);

		taldeakIkusi = new JButton("Taldeak ikusi");
		taldeakIkusi.setBounds(150, 530, 250, 40);
		taldeakIkusi.addActionListener(this);

		jokalariakAldatuBtn = new JButton("Jokalariak Aldatu");
		jokalariakAldatuBtn.setBounds(600, 530, 250, 40);
		jokalariakAldatuBtn.addActionListener(this);

		HasierakoPanela.add(titleHasiera);
		HasierakoPanela.add(logoaImg2);
		HasierakoPanela.add(img1);
		HasierakoPanela.add(img2);
		HasierakoPanela.add(atzerantzHasiera);
		HasierakoPanela.add(ateraHasiera);
		HasierakoPanela.add(klasifikazioaIkusi);
		HasierakoPanela.add(sartuEmaitza);
		HasierakoPanela.add(taldeakIkusi);
		HasierakoPanela.add(jokalariakAldatuBtn);

		// ---------------- KLASIFIKAZIOA PANELA ----------------
		KlasifikazioaPanela = new JPanel(null);
		JLabel titleKlasif = new JLabel("LIGAKO KLASIFIKAZIOA", JLabel.CENTER);
		titleKlasif.setFont(titleFont);
		titleKlasif.setBounds(50, 20, 900, 30);

		modeloTabla = new DefaultTableModel(
				new String[] { "Taldea", "P. Totalak", "Irabazi", "Galdu", "Aldeko", "Aurkako" }, 0);
		tablaKlasif = new JTable(modeloTabla);
		JScrollPane scrollKlasif = new JScrollPane(tablaKlasif);
		scrollKlasif.setBounds(50, 80, 900, 400);

		atzerantzKlasif = new JButton("Atzerantz");
		atzerantzKlasif.setBounds(50, 520, 100, 30);
		atzerantzKlasif.addActionListener(this);

		ateraKlasif = new JButton("Atera");
		ateraKlasif.setBounds(850, 520, 100, 30);
		ateraKlasif.addActionListener(this);

		KlasifikazioaPanela.add(titleKlasif);
		KlasifikazioaPanela.add(scrollKlasif);
		KlasifikazioaPanela.add(atzerantzKlasif);
		KlasifikazioaPanela.add(ateraKlasif);

		// ---------------- EMAITZA PANELA ----------------
		EmaitzaPanela = new JPanel(null);
		modeloEmaitzak = new DefaultTableModel(
				new String[] { "Partidua", "Puntuak Loc", "vs", "Puntuak Vis", "Talde Bisitari", "ID_OCULTO" }, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return (col == 1 || col == 3);
			}
		};
		tablaEmaitzak = new JTable(modeloEmaitzak);
		tablaEmaitzak.getColumnModel().getColumn(5).setMinWidth(0);
		tablaEmaitzak.getColumnModel().getColumn(5).setMaxWidth(0);

		JScrollPane scrollEmaitzak = new JScrollPane(tablaEmaitzak);
		scrollEmaitzak.setBounds(50, 80, 900, 400);

		gordeEmaitza = new JButton("Gorde Emaitzak");
		gordeEmaitza.setBounds(400, 520, 200, 30);
		gordeEmaitza.addActionListener(this);

		atzerantzEmaitza = new JButton("Atzerantz");
		atzerantzEmaitza.setBounds(50, 520, 100, 30);
		atzerantzEmaitza.addActionListener(this);

		ateraEmaitza = new JButton("Atera");
		ateraEmaitza.setBounds(850, 520, 100, 30);
		ateraEmaitza.addActionListener(this);

		EmaitzaPanela.add(scrollEmaitzak);
		EmaitzaPanela.add(gordeEmaitza);
		EmaitzaPanela.add(atzerantzEmaitza);
		EmaitzaPanela.add(ateraEmaitza);

		// ---------------- TALDEAK PANELA ----------------
		TaldeakPanela = new JPanel(null);
		comboBox = new JComboBox<>();
		for (Taldea t : Metodoak.taldeakMasterList) {
			comboBox.addItem(t.getIzena());
		}
		comboBox.setBounds(400, 10, 200, 25);
		comboBox.addActionListener(this);

		tablaPequena = new JTable(new DefaultTableModel(new String[] { "Sorrera", "Lehendakari", "Bazkideak" }, 0));
		JScrollPane sp1 = new JScrollPane(tablaPequena);
		sp1.setBounds(50, 50, 900, 60);

		tablaGrande = new JTable(
				new DefaultTableModel(new String[] { "Izena", "Abizena", "Jaiotza", "NAN", "Taldea", "Prezioa" }, 0));
		JScrollPane sp2 = new JScrollPane(tablaGrande);
		sp2.setBounds(50, 120, 900, 400);

		atzerantzTaldeak = new JButton("Atzerantz");
		atzerantzTaldeak.setBounds(50, 540, 100, 30);
		atzerantzTaldeak.addActionListener(this);

		ateraTaldeak = new JButton("Atera");
		ateraTaldeak.setBounds(850, 540, 100, 30);
		ateraTaldeak.addActionListener(this);

		TaldeakPanela.add(comboBox);
		TaldeakPanela.add(sp1);
		TaldeakPanela.add(sp2);
		TaldeakPanela.add(atzerantzTaldeak);
		TaldeakPanela.add(ateraTaldeak);

		// ---------------- JOKALARIAK ALDATU PANELA ----------------
		JokalariakAldatuPanel = new JPanel(null);
		JLabel titleAldatu = new JLabel("JOKALARIAK ALDATU", JLabel.CENTER);
		titleAldatu.setBounds(50, 20, 900, 30);
		titleAldatu.setFont(titleFont);

		JLabel lblTalde1 = new JLabel("Taldea 1:");
		lblTalde1.setBounds(304, 80, 100, 25);
		cbTalde1 = new JComboBox<>();
		cbTalde1.setBounds(448, 80, 363, 25);

		JLabel lblJokalaria = new JLabel("Jokalariak:");
		lblJokalaria.setBounds(304, 128, 100, 25);
		jokalariaModel = new DefaultListModel<>();
		JScrollPane scrollJokalariak = new JScrollPane();
		scrollJokalariak.setBounds(448, 115, 363, 172);

		JLabel lblTalde2 = new JLabel("Taldea 2:");
		lblTalde2.setBounds(304, 297, 100, 25);
		cbTalde2 = new JComboBox<>();
		cbTalde2.setBounds(448, 297, 363, 25);

		btnFitxatu = new JButton("Fitxatu");
		btnFitxatu.setBounds(472, 346, 100, 30);

		atzerantzAldatu = new JButton("Atzerantz");
		atzerantzAldatu.setBounds(50, 540, 100, 30);
		atzerantzAldatu.addActionListener(this);

		ateraAldatu = new JButton("Atera");
		ateraAldatu.setBounds(850, 540, 100, 30);
		ateraAldatu.addActionListener(this);

		JokalariakAldatuPanel.add(titleAldatu);
		JokalariakAldatuPanel.add(lblTalde1);
		JokalariakAldatuPanel.add(cbTalde1);
		JokalariakAldatuPanel.add(lblJokalaria);
		JokalariakAldatuPanel.add(lblTalde2);
		JokalariakAldatuPanel.add(cbTalde2);
		JokalariakAldatuPanel.add(btnFitxatu);
		JokalariakAldatuPanel.add(scrollJokalariak);
		jokalariaList = new JList<>(jokalariaModel);
		scrollJokalariak.setViewportView(jokalariaList);
		JokalariakAldatuPanel.add(ateraAldatu);
		JokalariakAldatuPanel.add(atzerantzAldatu);

		// Jokalarien aldaketa
		btnFitxatu.addActionListener(this);

		// ---------------- Datuak kargatu ----------------
		ArrayList<Taldea> taldeak = new ArrayList<>(new TaldeDao().kargatuTaldeak());
		for (Taldea t : taldeak) {
			cbTalde1.addItem(t);
			cbTalde2.addItem(t);
		}

		// Taldea 1 aldatzean jokalariak eguneratu
		cbTalde1.addActionListener(e -> {
			// Hautatutako elementua 'Taldea' objektu gisa jaso (Casting-a eginez)
			Taldea t = (Taldea) cbTalde1.getSelectedItem();
			// Ziurtatu hautatutako taldea ez dela null (erroreak saihesteko)
			if (t != null) {
				// Metodoari deitu hautatutako taldearen izenarekin jokalarien zerrenda
				// kargatzeko
				// Honek interfazea dinamikoki eguneratzen du
				eguneratuJokalariak(t.getIzena());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		// Login prozesua: erabiltzailea egiaztatu eta rola lortu (Admin,
		// Presidentea...)
		if (src == sartu) {
			String rola = Metodoak.login(textErabiltzaile.getText(), new String(textPasahitza.getPassword()));
			if (rola != null) {
				// Saioa ondo hasi bada, baimendu diren botoiak erakutsi eta panel nagusira joan
				erakutsiBotoiakRolarenArabera(rola);
				cardLayout.show(contentPanel, "Hasiera");
			} else {
				JOptionPane.showMessageDialog(null, "Erabiltzaile edo Pasahitz okerra");
			}

			// Aplikazioaren nabigazioa: CardLayout erabiliz panel batetik bestera aldatu
		} else if (src == ateraLogin || src == ateraHasiera || src == ateraKlasif || src == ateraEmaitza
				|| src == ateraTaldeak || src == ateraAldatu) {
			Metodoak.atera();
		} else if (src == atzerantzHasiera)
			cardLayout.show(contentPanel, "Login");
		else if (src == atzerantzKlasif || src == atzerantzEmaitza || src == atzerantzTaldeak || src == atzerantzAldatu)
			cardLayout.show(contentPanel, "Hasiera");

		// Klasifikazioa panelera joan baino lehen, datuak eguneratu behar dira
		else if (src == klasifikazioaIkusi) {
			eguneratuKlasifikazioa();
			cardLayout.show(contentPanel, "Klasifikazioa");
		} else if (src == sartuEmaitza) {
			Metodoak.beteEmaitzenTaula(modeloEmaitzak);
			cardLayout.show(contentPanel, "Emaitzak");
		} else if (src == taldeakIkusi)
			cardLayout.show(contentPanel, "Taldeak");

		// Emaitzak gordetzeko prozesua: TableModel-etik ArrayList-era eta ondoren DB-ra
		else if (src == gordeEmaitza) {
			Metodoak.prozesatuEmaitzak(modeloEmaitzak); // JTable -> ArrayList
			Metodoak.gordeDatuak(); // ArrayList -> DB

			// ComboBox-ean talde bat hautatzean, taulak automatikoki iragazi
		} else if (src == comboBox) {
			Metodoak.actualizarTablasTaldeak((String) comboBox.getSelectedItem(), tablaPequena, tablaGrande);
		} else if (src == jokalariakAldatuBtn) {
			cardLayout.show(contentPanel, "Jokalariak");

			// Fitxaketa prozesu konplexua: MySQL eta ObjectDB aldi berean sinkronizatu
		} else if (src == btnFitxatu) {// 1. Hautatutako jokalaria lortu
			Jokalaria aukeratuta = jokalariaList.getSelectedValue();
			if (aukeratuta == null) {
				JOptionPane.showMessageDialog(this, "Mesedez, hautatu jokalari bat");
				return;
			}
			// 2. Combo-etako taldeak lortu
			String taldeZaharra = cbTalde1.getSelectedItem().toString();
			String taldeBerria = cbTalde2.getSelectedItem().toString();
			String nanJokalaria = aukeratuta.getNAN();
			// 3. Egiaztatu jokalaria ez dela talde berera aldatzen ari
			if (taldeZaharra.equals(taldeBerria)) {
				JOptionPane.showMessageDialog(this, "Jokalari hau lehendik ere talde honetan dago!");
			} else {
				// 4. DAO-a erabili (Zure JokalariaObjectDB klasea erabiliko dugu,
				// orain JDBC/MySQL bidez funtzionatzen duena)
				JokalariaObjectDB jDao = new JokalariaObjectDB();
				// MySQL-n aldatu (Gogoratu klase honen barruan 'Talde_Izena' erabiltzen dugula
				// SQL-n)
				boolean mysqlOk = jDao.aldatuTaldea(nanJokalaria, taldeBerria);
				if (mysqlOk) {
					// Datuak ondo aldatu badira:
					// Jokalariari objektuan ere taldea aldatu (interfazean berehala ikusteko)
					aukeratuta.setTaldea(taldeBerria);
					// Interfazea freskatu:
					// Bi aukera dituzu: zerrenda hustu edo jatorrizko taldea kargatu berriro
					eguneratuJokalariak(taldeZaharra);
					JOptionPane.showMessageDialog(this, "Fitxaketa ondo egin da: " + aukeratuta.getIzena() + " orain "
							+ taldeBerria + " taldekoa da.");
				} else {
					JOptionPane.showMessageDialog(this, "Errorea gertatu da fitxaketa egitean MySQL-n. "
							+ "\nZiurtatu taldearen izena existitzen dela.");
				}
			}
		}
	}

	private void eguneratuJokalariak(String taldea) {
		// Zerrendaren modeloa (JList-arena, adibidez) hustu aurreko jokalariak kentzeko
		jokalariaModel.clear();
		try {
			JokalariaDao dao = new JokalariaDao();
			// Datu-basetik hautatutako taldeko jokalariak bakarrik kargatu
			ArrayList<Jokalaria> lista = dao.kargatuJokalariakTaldeka(taldea);
			// Lortutako jokalari bakoitza banan-banan gehitu modeloari interfazean ikusteko
			for (Jokalaria j : lista) {
				jokalariaModel.addElement(j);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void erakutsiBotoiakRolarenArabera(String rola) {
		// 'sartuEmaitza' botoia soilik administratzaileentzat egongo da ikusgai
		sartuEmaitza.setVisible(rola.equals("Admin"));
		// Jokalariak aldatzeko botoia soilik 'Presidentea' rolarentzat gaituko da
		jokalariakAldatuBtn.setVisible(rola.equals("Presidentea"));
	}

	private void eguneratuKlasifikazioa() {
		// Puntuak, garaipenak eta galerak berriro kalkulatzeko metodoari deitu
		Metodoak.kalkulatuKlasifikazioa();
		// Taularen modeloa hustu (lehendik zeuden lerro guztiak ezabatu) bikoiztasunak
		// saihesteko
		modeloTabla.setRowCount(0);
		// Talde bakoitzeko (taldeakMasterList zerrendan) lerro berri bat gehitu taulara
		for (Taldea t : Metodoak.taldeakMasterList) {
			// Object[] array baten bidez, zutabe bakoitzaren datuak (izena, puntuak...)
			// txertatu
			modeloTabla.addRow(new Object[] { t.getIzena(), t.getPuntuTotalak(), t.getIrabazitakoak(),
					t.getGaldutakoak(), t.getPuntuakF(), t.getPuntuakC() });
		}
	}

	private void konfiguratuOsagaiBisualak() {
		// Lehenengo logoa kargatu eta eskalatu (200x150 tamainan) multimedia karpetatik
		kargatuIrudia(logoaImg1, 200, 150, "/Multimedia/logoa.png");
		// Bigarren logoa kargatu (pantailako beste puntu batean bistaratzeko)
		kargatuIrudia(logoaImg2, 200, 150, "/Multimedia/logoa.png");
		// Lehenengo irudi nagusia kargatu (350x200 tamaina handiagoan)
		kargatuIrudia(img1, 350, 200, "/Multimedia/img1.png");
		// Bigarren irudi nagusia kargatu interfazeari itxura osoa emateko
		kargatuIrudia(img2, 350, 200, "/Multimedia/img2.png");
	}

	private void kargatuIrudia(JLabel label, int w, int h, String path) {
		try {
			// Irudiaren kokapena (path) lortu proiektuaren baliabideen (resources) barruan
			java.net.URL url = getClass().getResource(path);
			// URL-a aurkitu bada, irudia kargatu eta tamaina aldatzeko prozesua hasi
			if (url != null) {
				// Irudia eskalatu 'w' (zabalera) eta 'h' (altuera) parametroen arabera
				// Image.SCALE_SMOOTH: Kalitate onena lortzeko eskalatze leuna erabiltzen du
				Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
				// Behin eskalatuta, irudi berria JLabel-aren ikono gisa ezarri
				label.setIcon(new ImageIcon(img));
			}
		} catch (Exception e) {
			// Arazoren bat badago (bidea okerra bada, adibidez), testu bat erakutsi errore
			// gisa
			label.setText("Img Error");
		}
	}

	public static void main(String[] args) {
		// EventQueue.invokeLater: Erabiltzaile-interfazearen (GUI) haria modu seguruan
		// kudeatzeko
		// Honek ziurtatzen du leihoa 'Event Dispatch Thread' (EDT) izeneko harian
		// irekitzen dela
		EventQueue.invokeLater(() -> new ErronkaBisuala());
	}
}