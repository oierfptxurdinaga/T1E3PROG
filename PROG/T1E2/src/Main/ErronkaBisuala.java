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
import Metodoak.Metodoak;

public class ErronkaBisuala extends JFrame implements ActionListener {

    // --- Atributuak ---
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Font titleFont;

    // --- Panelen Osagaiak ---
    private JPanel LoginPanela, HasierakoPanela, KlasifikazioaPanela, EmaitzaPanela, TaldeakPanela, JokalariakPanela;
    
    // Login
    private JLabel logoaImg1, erabiltzaileak, pasahitza;
    private JTextField textErabiltzaile;
    private JPasswordField textPasahitza;
    private JButton sartu, ateraLogin; // ALDATUTA: Izena zehaztu

    // Hasiera
    private JLabel logoaImg2, img1, img2;
    private JButton atzerantzHasiera, ateraHasiera, klasifikazioaIkusi, sartuEmaitza, taldeakIkusi, jokalariakAldatu; // ALDATUTA: Izena zehaztu

    // Klasifikazioa
    private JTable tablaKlasif;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JButton atzerantzKlasif, ateraKlasif; // ALDATUTA: Izena zehaztu

    // Taldeak Ikusi
    private JComboBox<String> comboBox;
    private JTable tablaPequena;
    private JTable tablaGrande;
    private JButton atzerantzTaldeak, ateraTaldeak; // GEHITUTA
    
    // Emaitza
    private JTable tablaEmaitzak;
    private DefaultTableModel modeloEmaitzak;
    private JButton gordeEmaitza, atzerantzEmaitza, ateraEmaitza; // ALDATUTA: Izena zehaztu

    public ErronkaBisuala() {
        // --- JFrame Konfigurazioa ---
        setTitle("Bizkaiko Saskibaloi Federazioa");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setSize(1000, 700);
        setLocationRelativeTo(null);
        titleFont = new Font("Verdana", Font.BOLD, 24);
     // --- Panelak Inizializatu ---
        inizializatuPanelak();
        konfiguratuOsagaiBisualak();

        // --- Datuak Kargatu ---
        Metodoak.beteEmaitzenTaula(modeloEmaitzak);

        // --- Layout Konfigurazioa ---
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        setContentPane(contentPanel);

        
        // --- Gehitu Panelak ---
        contentPanel.add(LoginPanela, "Login");
        contentPanel.add(HasierakoPanela, "Hasiera");
        contentPanel.add(KlasifikazioaPanela, "Klasifikazioa");
        contentPanel.add(EmaitzaPanela, "Emaitzak");
        contentPanel.add(TaldeakPanela, "Taldeak");
        contentPanel.add(JokalariakPanela, "Jokalariak");
        
        // Leihoaren "X" botoia
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
        titleLogin.setFont(titleFont);
        titleLogin.setBounds(38, 20, 900, 30);
        
        logoaImg1 = new JLabel("LOGOA IMAGEN", JLabel.CENTER);
        logoaImg1.setBounds(420, 92, 200, 150);
        
        erabiltzaileak = new JLabel("Erabiltzailea:", JLabel.RIGHT);
        erabiltzaileak.setBounds(250, 300, 150, 30);
        textErabiltzaile = new JTextField();
        textErabiltzaile.setBounds(420, 300, 250, 30);
        
        pasahitza = new JLabel("Pasahitza:", JLabel.RIGHT);
        pasahitza.setBounds(250, 350, 150, 30);
        textPasahitza = new JPasswordField();
        textPasahitza.setBounds(420, 350, 250, 30);
        
        sartu = new JButton("Sartu");
        sartu.setBounds(483, 447, 100, 30);
        sartu.addActionListener(this);
        this.getRootPane().setDefaultButton(sartu);
        
        ateraLogin = new JButton("Atera");
        ateraLogin.setBounds(858, 60, 80, 30);
        ateraLogin.addActionListener(this);

        LoginPanela.add(titleLogin); LoginPanela.add(logoaImg1); LoginPanela.add(erabiltzaileak);
        LoginPanela.add(textErabiltzaile); LoginPanela.add(pasahitza); LoginPanela.add(textPasahitza);
        LoginPanela.add(sartu); LoginPanela.add(ateraLogin);

        // --- HASIERA PANELA ---
        HasierakoPanela = new JPanel(null);
        JLabel titleHasiera = new JLabel("HASIERA PANELA", JLabel.CENTER);
        titleHasiera.setBounds(50, 20, 900, 30);
        titleHasiera.setFont(titleFont);
        
        logoaImg2 = new JLabel(); logoaImg2.setBounds(394, 60, 240, 211);
        img1 = new JLabel(); img1.setBounds(100, 250, 350, 200);
        img2 = new JLabel(); img2.setBounds(550, 250, 350, 200);
        
        atzerantzHasiera = new JButton("Atzerantz"); atzerantzHasiera.setBounds(800, 50, 100, 30);
        atzerantzHasiera.addActionListener(this);
        
        ateraHasiera = new JButton("Atera"); ateraHasiera.setBounds(910, 50, 70, 30);
        ateraHasiera.addActionListener(this);
        
        klasifikazioaIkusi = new JButton("Klasifikazioa ikusi"); klasifikazioaIkusi.setBounds(150, 480, 250, 40);
        klasifikazioaIkusi.addActionListener(this);
        
        sartuEmaitza = new JButton("Sartu Emaitza"); sartuEmaitza.setBounds(600, 480, 250, 40);
        sartuEmaitza.addActionListener(this);
        
        taldeakIkusi = new JButton("Taldeak ikusi"); taldeakIkusi.setBounds(150, 530, 250, 40);
        taldeakIkusi.addActionListener(this);
        
        jokalariakAldatu = new JButton("Jokalariak Aldatu"); jokalariakAldatu.setBounds(600, 530, 250, 40);
        jokalariakAldatu.addActionListener(this);

        HasierakoPanela.add(titleHasiera); HasierakoPanela.add(logoaImg2); HasierakoPanela.add(img1);
        HasierakoPanela.add(img2); HasierakoPanela.add(atzerantzHasiera); HasierakoPanela.add(ateraHasiera);
        HasierakoPanela.add(klasifikazioaIkusi); HasierakoPanela.add(sartuEmaitza);
        HasierakoPanela.add(taldeakIkusi); HasierakoPanela.add(jokalariakAldatu);

        // --- KLASIFIKAZIOA PANELA  ---
        KlasifikazioaPanela = new JPanel(null);
        JLabel titleKlasif = new JLabel("LIGAKO KLASIFIKAZIOA", JLabel.CENTER);
        titleKlasif.setBounds(50, 20, 900, 30);
        titleKlasif.setFont(titleFont);

        String[] zutabeTituluak = {"Taldea", "P. Totalak", "Irabazi", "Galdu", "Aldeko", "Aurkako"};
        modeloTabla = new DefaultTableModel(zutabeTituluak, 0);
        tablaKlasif = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaKlasif);
        scrollTabla.setBounds(50, 80, 900, 400);

        atzerantzKlasif = new JButton("Atzerantz"); atzerantzKlasif.setBounds(50, 520, 100, 30);
        atzerantzKlasif.addActionListener(this);
        
        ateraKlasif = new JButton("Atera"); ateraKlasif.setBounds(850, 520, 100, 30);
        ateraKlasif.addActionListener(this);

        KlasifikazioaPanela.add(titleKlasif); KlasifikazioaPanela.add(scrollTabla);
        KlasifikazioaPanela.add(atzerantzKlasif); KlasifikazioaPanela.add(ateraKlasif);

        // --- JOKALARIAK ALDATU PANELA ---
        JokalariakPanela = new JPanel(null);
        JokalariakPanela.setPreferredSize(new Dimension(1000, 600));
        // ... (Zure jokalariakAldatu logic mantendu dugu)
        JLabel titleJokalariak = new JLabel("JOKALARIAK ALDATU", JLabel.CENTER);
        titleJokalariak.setFont(titleFont);
        titleJokalariak.setBounds(50, 20, 900, 30);
        JokalariakPanela.add(titleJokalariak);
        // ... (Zure jokalarien taula eta comboBox-ak hemen joango lirateke berdin)

        // --- TALDEAK PANELA ---
        TaldeakPanela = new JPanel(null);
        comboBox = new JComboBox<>();
        for (Taldea t : Metodoak.taldeakMasterList) { comboBox.addItem(t.getIzena()); }
        comboBox.setBounds(400, 10, 200, 25);
        comboBox.addActionListener(this);
        TaldeakPanela.add(comboBox);

        String[] columnasPequena = {"SorreraUrtea", "Lehendakari", "N_Bazkideak"};
        tablaPequena = new JTable(new DefaultTableModel(columnasPequena,0));
        JScrollPane scrollPequena = new JScrollPane(tablaPequena);
        scrollPequena.setBounds(50, 50, 900, 60);
        TaldeakPanela.add(scrollPequena);

        String[] columnasGrande = {"Izena","Abizena","JaiotzeData","NAN","Taldea","Prezioa","Puntuak"};
        tablaGrande = new JTable(new DefaultTableModel(columnasGrande,0));
        JScrollPane scrollGrande = new JScrollPane(tablaGrande);
        scrollGrande.setBounds(50, 120, 900, 400);
        TaldeakPanela.add(scrollGrande);

        atzerantzTaldeak = new JButton("Atzerantz");
        atzerantzTaldeak.setBounds(50, 540, 100, 30);
        atzerantzTaldeak.addActionListener(this);
        
        ateraTaldeak = new JButton("Atera");
        ateraTaldeak.setBounds(850, 540, 100, 30);
        ateraTaldeak.addActionListener(this);

        TaldeakPanela.add(atzerantzTaldeak); TaldeakPanela.add(ateraTaldeak);

        // --- EMAITZA PANELA ---
        EmaitzaPanela = new JPanel(null);
        JLabel titleEmaitza = new JLabel("LIGAKO EMAITZAK (10 JORNADA)", JLabel.CENTER);
        titleEmaitza.setBounds(50, 20, 900, 30);
        titleEmaitza.setFont(titleFont);

        modeloEmaitzak = new DefaultTableModel(new String[]{"Jornada / Partidua", "Puntuak", "vs", "Puntuak", "Kanpoko Taldea"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return (col == 1 || col == 3); }
        };

        tablaEmaitzak = new JTable(modeloEmaitzak);
        JScrollPane scrollEmaitzak = new JScrollPane(tablaEmaitzak);
        scrollEmaitzak.setBounds(50, 80, 900, 400);

        atzerantzEmaitza = new JButton("Atzerantz"); 
        atzerantzEmaitza.setBounds(50, 520, 100, 30);
        atzerantzEmaitza.addActionListener(this);

        gordeEmaitza = new JButton("Gorde Emaitzak");
        gordeEmaitza.setBounds(400, 520, 200, 30);
        gordeEmaitza.addActionListener(this);

        ateraEmaitza = new JButton("Atera"); 
        ateraEmaitza.setBounds(850, 520, 100, 30);
        ateraEmaitza.addActionListener(this);

        EmaitzaPanela.add(titleEmaitza); EmaitzaPanela.add(scrollEmaitzak);
        EmaitzaPanela.add(atzerantzEmaitza); EmaitzaPanela.add(gordeEmaitza); EmaitzaPanela.add(ateraEmaitza);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // --- LOGIN EKINTZAK ---
        if (src == sartu) {
            String rola = Metodoak.login(textErabiltzaile.getText(), new String(textPasahitza.getPassword()));
            if (rola != null) {
                textErabiltzaile.setText(""); textPasahitza.setText("");
                erakutsiPanelak(rola);
                cardLayout.show(contentPanel, "Hasiera");
            } else {
                JOptionPane.showMessageDialog(null, "Erabiltzaile edo Pasahitz okerra");
            }
        } 
        else if (src == ateraLogin || src == ateraHasiera || src == ateraKlasif || src == ateraEmaitza || src == ateraTaldeak) {
            Metodoak.atera();
        }
        else if (src == atzerantzHasiera) {
            cardLayout.show(contentPanel, "Login");
        } 
        else if (src == atzerantzKlasif || src == atzerantzEmaitza || src == atzerantzTaldeak) {
            cardLayout.show(contentPanel, "Hasiera");
        } 
        else if (src == klasifikazioaIkusi) {
            eguneratuKlasifikazioa();
            cardLayout.show(contentPanel, "Klasifikazioa");
        } 
        else if (src == sartuEmaitza) {
            generatuJornadak();
            cardLayout.show(contentPanel, "Emaitzak");
        } 
        else if (src == taldeakIkusi) {
            cardLayout.show(contentPanel, "Taldeak");
        } 
        else if (src == jokalariakAldatu) {
            cardLayout.show(contentPanel, "Jokalariak");
        }
        else if (src == gordeEmaitza) {
            prozesatuEmaitzak();
        }
        else if (src == comboBox) {
            String seleccionado = (String) comboBox.getSelectedItem();
            Metodoak.actualizarTablasTaldeak(seleccionado, tablaPequena, tablaGrande);
        }
    }

    // --- Metodo Erabilgarriak (LOGIKA) ---
    private void erakutsiPanelak(String rola) {
        klasifikazioaIkusi.setVisible(true);
        taldeakIkusi.setVisible(true);
        sartuEmaitza.setVisible(rola.equals("Admin"));
        jokalariakAldatu.setVisible(rola.equals("Presidentea"));
    }

    private void konfiguratuOsagaiBisualak() {
        kargatuIrudia(logoaImg1, 200, 150, "/Multimedia/logoa.png");
        kargatuIrudia(logoaImg2, 200, 150, "/Multimedia/logoa.png");
        kargatuIrudia(img1, 150, 150, "/Multimedia/img1.png");
        kargatuIrudia(img2, 150, 150, "/Multimedia/img2.png");
    }

    private void kargatuIrudia(JLabel label, int w, int h, String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                label.setText("");
            }
        } catch (Exception e) { label.setText("Error"); }
    }

    // (Hemen itsatsi zure eguneratuKlasifikazioa, generatuJornadak eta prozesatuEmaitzak metodoak osorik)
    private void eguneratuKlasifikazioa() { /* ... zure kodea ... */ }
    private void generatuJornadak() { /* ... zure kodea ... */ }
    private void prozesatuEmaitzak() { /* ... zure kodea ... */ }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new ErronkaBisuala();
        });
    }
}