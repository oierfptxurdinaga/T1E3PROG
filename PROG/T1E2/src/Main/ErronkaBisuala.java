package Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import E2.Taldea;
import E2.Partidua;
import Metodoak.Metodoak;

public class ErronkaBisuala extends JFrame implements ActionListener {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Font titleFont;

    private JPanel LoginPanela, HasierakoPanela, KlasifikazioaPanela, EmaitzaPanela, TaldeakPanela, JokalariakPanela;
    
    // Login
    private JTextField textErabiltzaile;
    private JPasswordField textPasahitza;
    private JButton sartu, ateraLogin; 

    // Hasiera
    private JLabel logoaImg1, logoaImg2, img1, img2;
    private JButton atzerantzHasiera, ateraHasiera, klasifikazioaIkusi, sartuEmaitza, taldeakIkusi, jokalariakAldatu; 

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

    public ErronkaBisuala() {
        // 1. DATUAK KARGATU (Hau gabe ArrayList-ak hutsik daude)
        Metodoak m = new Metodoak();
        m.kargatuDatuak();

        // 2. JFRAME KONFIGURAZIOA
        setTitle("Bizkaiko Saskibaloi Federazioa");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setSize(1000, 700);
        setLocationRelativeTo(null);
        titleFont = new Font("Verdana", Font.BOLD, 24);

        inizializatuPanelak();
        konfiguratuOsagaiBisualak();

        // 3. TAULAK BETE HASIERAKO DATUEKIN
        Metodoak.beteEmaitzenTaula(modeloEmaitzak);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        setContentPane(contentPanel);

        contentPanel.add(LoginPanela, "Login");
        contentPanel.add(HasierakoPanela, "Hasiera");
        contentPanel.add(KlasifikazioaPanela, "Klasifikazioa");
        contentPanel.add(EmaitzaPanela, "Emaitzak");
        contentPanel.add(TaldeakPanela, "Taldeak");
        contentPanel.add(JokalariakPanela, "Jokalariak");
        
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
        // --- LOGIN PANELA ---
        LoginPanela = new JPanel(null);
        JLabel titleLogin = new JLabel("BIZKAIKO SASKIBALOI FEDERAZIOA", JLabel.CENTER);
        titleLogin.setFont(titleFont); titleLogin.setBounds(38, 20, 900, 30);
        
        logoaImg1 = new JLabel("", JLabel.CENTER); logoaImg1.setBounds(420, 92, 200, 150);
        
        JLabel uLabel = new JLabel("Erabiltzailea:"); uLabel.setBounds(250, 300, 150, 30);
        textErabiltzaile = new JTextField(); textErabiltzaile.setBounds(420, 300, 250, 30);
        
        JLabel pLabel = new JLabel("Pasahitza:"); pLabel.setBounds(250, 350, 150, 30);
        textPasahitza = new JPasswordField(); textPasahitza.setBounds(420, 350, 250, 30);
        
        sartu = new JButton("Sartu"); sartu.setBounds(483, 447, 100, 30); sartu.addActionListener(this);
        ateraLogin = new JButton("Atera"); ateraLogin.setBounds(858, 60, 80, 30); ateraLogin.addActionListener(this);

        LoginPanela.add(titleLogin); LoginPanela.add(logoaImg1); LoginPanela.add(uLabel);
        LoginPanela.add(textErabiltzaile); LoginPanela.add(pLabel); LoginPanela.add(textPasahitza);
        LoginPanela.add(sartu); LoginPanela.add(ateraLogin);

        // --- HASIERA PANELA ---
        HasierakoPanela = new JPanel(null);
        JLabel titleHasiera = new JLabel("HASIERA PANELA", JLabel.CENTER);
        titleHasiera.setBounds(50, 20, 900, 30); titleHasiera.setFont(titleFont);
        
        logoaImg2 = new JLabel(); logoaImg2.setBounds(394, 60, 240, 211);
        img1 = new JLabel(); img1.setBounds(100, 250, 350, 200);
        img2 = new JLabel(); img2.setBounds(550, 250, 350, 200);
        
        atzerantzHasiera = new JButton("Atzerantz"); atzerantzHasiera.setBounds(800, 50, 100, 30); atzerantzHasiera.addActionListener(this);
        ateraHasiera = new JButton("Atera"); ateraHasiera.setBounds(910, 50, 70, 30); ateraHasiera.addActionListener(this);
        klasifikazioaIkusi = new JButton("Klasifikazioa ikusi"); klasifikazioaIkusi.setBounds(150, 480, 250, 40); klasifikazioaIkusi.addActionListener(this);
        sartuEmaitza = new JButton("Sartu Emaitza"); sartuEmaitza.setBounds(600, 480, 250, 40); sartuEmaitza.addActionListener(this);
        taldeakIkusi = new JButton("Taldeak ikusi"); taldeakIkusi.setBounds(150, 530, 250, 40); taldeakIkusi.addActionListener(this);
        jokalariakAldatu = new JButton("Jokalariak Aldatu"); jokalariakAldatu.setBounds(600, 530, 250, 40); jokalariakAldatu.addActionListener(this);

        HasierakoPanela.add(titleHasiera); HasierakoPanela.add(logoaImg2); HasierakoPanela.add(img1);
        HasierakoPanela.add(img2); HasierakoPanela.add(atzerantzHasiera); HasierakoPanela.add(ateraHasiera);
        HasierakoPanela.add(klasifikazioaIkusi); HasierakoPanela.add(sartuEmaitza); HasierakoPanela.add(taldeakIkusi); HasierakoPanela.add(jokalariakAldatu);

        // --- KLASIFIKAZIOA PANELA ---
        KlasifikazioaPanela = new JPanel(null);
        JLabel titleKlasif = new JLabel("LIGAKO KLASIFIKAZIOA", JLabel.CENTER);
        titleKlasif.setBounds(50, 20, 900, 30); titleKlasif.setFont(titleFont);
        modeloTabla = new DefaultTableModel(new String[]{"Taldea", "P. Totalak", "Irabazi", "Galdu", "Aldeko", "Aurkako"}, 0);
        tablaKlasif = new JTable(modeloTabla);
        JScrollPane scrollKlasif = new JScrollPane(tablaKlasif); scrollKlasif.setBounds(50, 80, 900, 400);
        atzerantzKlasif = new JButton("Atzerantz"); atzerantzKlasif.setBounds(50, 520, 100, 30); atzerantzKlasif.addActionListener(this);
        ateraKlasif = new JButton("Atera"); ateraKlasif.setBounds(850, 520, 100, 30); ateraKlasif.addActionListener(this);
        KlasifikazioaPanela.add(titleKlasif); KlasifikazioaPanela.add(scrollKlasif); KlasifikazioaPanela.add(atzerantzKlasif); KlasifikazioaPanela.add(ateraKlasif);

        // --- EMAITZA PANELA ---
        EmaitzaPanela = new JPanel(null);
        modeloEmaitzak = new DefaultTableModel(new String[]{"Partidua", "Puntuak Loc", "vs", "Puntuak Vis", "Talde Bisitari", "ID_OCULTO"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return (col == 1 || col == 3); }
        };
        tablaEmaitzak = new JTable(modeloEmaitzak);
        tablaEmaitzak.getColumnModel().getColumn(5).setMinWidth(0); tablaEmaitzak.getColumnModel().getColumn(5).setMaxWidth(0);
        JScrollPane scrollEmaitzak = new JScrollPane(tablaEmaitzak); scrollEmaitzak.setBounds(50, 80, 900, 400);
        gordeEmaitza = new JButton("Gorde Emaitzak"); gordeEmaitza.setBounds(400, 520, 200, 30); gordeEmaitza.addActionListener(this);
        atzerantzEmaitza = new JButton("Atzerantz"); atzerantzEmaitza.setBounds(50, 520, 100, 30); atzerantzEmaitza.addActionListener(this);
        ateraEmaitza = new JButton("Atera"); ateraEmaitza.setBounds(850, 520, 100, 30); ateraEmaitza.addActionListener(this);
        EmaitzaPanela.add(scrollEmaitzak); EmaitzaPanela.add(gordeEmaitza); EmaitzaPanela.add(atzerantzEmaitza); EmaitzaPanela.add(ateraEmaitza);

        // --- TALDEAK PANELA ---
        TaldeakPanela = new JPanel(null);
        comboBox = new JComboBox<>();
        for (Taldea t : Metodoak.taldeakMasterList) { comboBox.addItem(t.getIzena()); }
        comboBox.setBounds(400, 10, 200, 25); comboBox.addActionListener(this);
        tablaPequena = new JTable(new DefaultTableModel(new String[]{"Sorrera", "Lehendakari", "Bazkideak"}, 0));
        JScrollPane sp1 = new JScrollPane(tablaPequena); sp1.setBounds(50, 50, 900, 60);
        tablaGrande = new JTable(new DefaultTableModel(new String[]{"Izena", "Abizena", "Jaiotza", "NAN", "Taldea", "Prezioa"}, 0));
        JScrollPane sp2 = new JScrollPane(tablaGrande); sp2.setBounds(50, 120, 900, 400);
        atzerantzTaldeak = new JButton("Atzerantz"); atzerantzTaldeak.setBounds(50, 540, 100, 30); atzerantzTaldeak.addActionListener(this);
        ateraTaldeak = new JButton("Atera"); ateraTaldeak.setBounds(850, 540, 100, 30); ateraTaldeak.addActionListener(this);
        TaldeakPanela.add(comboBox); TaldeakPanela.add(sp1); TaldeakPanela.add(sp2); TaldeakPanela.add(atzerantzTaldeak); TaldeakPanela.add(ateraTaldeak);

        // Jokalariak panela (Hutsik dago oraindik)
        JokalariakPanela = new JPanel(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == sartu) {
            String rola = Metodoak.login(textErabiltzaile.getText(), new String(textPasahitza.getPassword()));
            if (rola != null) {
                erakutsiBotoiakRolarenArabera(rola);
                cardLayout.show(contentPanel, "Hasiera");
            } else {
                JOptionPane.showMessageDialog(null, "Erabiltzaile edo Pasahitz okerra");
            }
        } 
        else if (src == ateraLogin || src == ateraHasiera || src == ateraKlasif || src == ateraEmaitza || src == ateraTaldeak) {
            Metodoak.atera();
        }
        else if (src == atzerantzHasiera) cardLayout.show(contentPanel, "Login");
        else if (src == atzerantzKlasif || src == atzerantzEmaitza || src == atzerantzTaldeak) cardLayout.show(contentPanel, "Hasiera");
        else if (src == klasifikazioaIkusi) {
            eguneratuKlasifikazioa();
            cardLayout.show(contentPanel, "Klasifikazioa");
        } 
        else if (src == sartuEmaitza) cardLayout.show(contentPanel, "Emaitzak");
        else if (src == taldeakIkusi) cardLayout.show(contentPanel, "Taldeak");
        else if (src == gordeEmaitza) {
            Metodoak.prozesatuEmaitzak(modeloEmaitzak); // JTable -> ArrayList
            Metodoak.gordeDatuak(); // ArrayList -> DB
        }
        else if (src == comboBox) {
            Metodoak.actualizarTablasTaldeak((String) comboBox.getSelectedItem(), tablaPequena, tablaGrande);
        }
    }

    private void erakutsiBotoiakRolarenArabera(String rola) {
        sartuEmaitza.setVisible(rola.equals("Admin"));
        jokalariakAldatu.setVisible(rola.equals("Presidentea"));
    }

    private void eguneratuKlasifikazioa() {
        Metodoak.kalkulatuKlasifikazioa();
        modeloTabla.setRowCount(0);
        for (Taldea t : Metodoak.taldeakMasterList) {
            modeloTabla.addRow(new Object[]{t.getIzena(), t.getPuntuTotalak(), t.getIrabazitakoak(), t.getGaldutakoak(), t.getPuntuakF(), t.getPuntuakC()});
        }
    }

    private void konfiguratuOsagaiBisualak() {
        kargatuIrudia(logoaImg1, 200, 150, "/Multimedia/logoa.png");
        kargatuIrudia(logoaImg2, 200, 150, "/Multimedia/logoa.png");
        kargatuIrudia(img1, 350, 200, "/Multimedia/img1.png");
        kargatuIrudia(img2, 350, 200, "/Multimedia/img2.png");
    }

    private void kargatuIrudia(JLabel label, int w, int h, String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) { label.setText("Img Error"); }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ErronkaBisuala());
    }
}