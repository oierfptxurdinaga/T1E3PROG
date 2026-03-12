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
//PROBA
/**
 * ErronkaBisuala klasea aplikazioaren leiho nagusia da.
 * <p>
 * Klase honek Bizkaiko Saskibaloi Federazioaren aplikazio grafikoa
 * inplementatzen du Java Swing erabiliz. {@link CardLayout} baten bidez
 * aplikazioko panel desberdinak kudeatzen dira.
 * </p>
 *
 * <p>
 * Aplikazioak honako funtzionalitate hauek eskaintzen ditu:
 * </p>
 * <ul>
 *   <li>Erabiltzaileen autentifikazioa (Login)</li>
 *   <li>Ligako sailkapena ikustea</li>
 *   <li>Taldeen informazioa bistaratzea</li>
 *   <li>Partiden emaitzak sartzea eta balidatzea</li>
 *   <li>Jokalarien kudeaketa eta talde arteko trukea</li>
 * </ul>
 *
 * <p>
 * Erabiltzailearen rolaren arabera, funtzionalitate batzuk aktibo edo
 * ezkutuan egongo dira:
 * </p>
 * <ul>
 *   <li><b>Admin</b>: emaitzak sartzeko aukera</li>
 *   <li><b>Presidentea</b>: jokalariak aldatzeko aukera</li>
 * </ul>
 *
 * <p>
 * Negozio-logika eta datuen kudeaketa {@link Metodoak} klasearen bidez
 * egiten da.
 * </p>
 *
 * @author ZureIzena
 * @version 1.0
 */

public class ErronkaBisuala extends JFrame implements ActionListener{

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
    private JButton sartu, atera1;

    // Hasiera
    private JLabel logoaImg2, img1, img2;
    private JButton atzerantz, atera2, klasifikazioaIkusi, sartuEmaitza, taldeakIkusi, jokalariakAldatu;

    // Klasifikazioa
    private JButton atzerantzKlasif, ateraKlasif;
    private JTable tablaKlasif;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;

    // Taldeak Ikusi
    private JComboBox<String> comboBox;
    private JTable tablaPequena;
    private JTable tablaGrande;
    
    // Emaitza
    private JTable tablaEmaitzak;
    private DefaultTableModel modeloEmaitzak;
    private JButton atzerantzEmaitza; 
    private JButton ateraEmaitza; 
    private JButton gordeEmaitza;
    /**
     * ErronkaBisuala klasearen eraikitzaile nagusia.
     * <p>
     * Leihoa sortzen du, aplikazioko datuak kargatzen ditu, panel guztiak
     * hasieratzen ditu, ekintzak (listeners) konfiguratzen ditu eta
     * login panela bistaratzen du hasieran.
     * </p>
     */
    public ErronkaBisuala() {
        // --- JFrame Konfigurazioa ---
        setTitle("Bizkaiko Saskibaloi Federazioa");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setSize(1000, 700);
        setLocationRelativeTo(null);
        titleFont = new Font("Verdana", Font.BOLD, 24);

        // --- Datuak Kargatu ---
        Metodoak.kargatuDatuak();

        // --- Layout Konfigurazioa ---
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        setContentPane(contentPanel);

        // --- Panelak Inizializatu ---
        inizializatuPanelak();
        konfiguratuOsagaiBisualak();

        // --- Gehitu Panelak ---
        contentPanel.add(LoginPanela, "Login");
        contentPanel.add(HasierakoPanela, "Hasiera");
        contentPanel.add(KlasifikazioaPanela, "Klasifikazioa");
        contentPanel.add(EmaitzaPanela, "Emaitzak");
        contentPanel.add(TaldeakPanela, "Taldeak");
        contentPanel.add(JokalariakPanela, "Jokalariak");

        // ==========================================================
        // 3. Ekintzak (Listeners)
        // ==========================================================

        // --- LOGIN EKINTZAK ---
        sartu.addActionListener(this);

        atera1.addActionListener(this);

        // --- HASIERA EKINTZAK ---
        atzerantz.addActionListener(this);

        klasifikazioaIkusi.addActionListener(this);

        taldeakIkusi.addActionListener(this);
        jokalariakAldatu.addActionListener(this);

        // --- KLASIFIKAZIOA EKINTZAK ---
        atzerantzKlasif.addActionListener(this);
        ateraKlasif.addActionListener(this);
        
        // --- EMAITZA EKINTZAK ---
        sartuEmaitza.addActionListener(this);

        atzerantzEmaitza.addActionListener(this);
        ateraEmaitza.addActionListener(this);
        gordeEmaitza.addActionListener(this);
        
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
    /**
     * Aplikazioko panel grafiko guztiak hasieratzen ditu.
     * <p>
     * Panel hauek sortzen eta konfiguratzen ditu:
     * </p>
     * <ul>
     *   <li>Login panela</li>
     *   <li>Hasierako panela</li>
     *   <li>Klasifikazio panela</li>
     *   <li>Emaitzen panela</li>
     *   <li>Taldeen panela</li>
     *   <li>Jokalarien kudeaketa panela</li>
     * </ul>
     */

    private void inizializatuPanelak() {
        // --- LOGIN PANELA ---
        LoginPanela = new JPanel(null);
        JLabel titleLogin = new JLabel(getTitle(), JLabel.CENTER);
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
        this.getRootPane().setDefaultButton(sartu);
        
        atera1 = new JButton("Atera");
        atera1.setBounds(858, 60, 80, 30);

        LoginPanela.add(titleLogin); LoginPanela.add(logoaImg1); LoginPanela.add(erabiltzaileak);
        LoginPanela.add(textErabiltzaile); LoginPanela.add(pasahitza); LoginPanela.add(textPasahitza);
        LoginPanela.add(sartu); LoginPanela.add(atera1);

        // --- HASIERA PANELA ---
        HasierakoPanela = new JPanel(null);
        JLabel titleHasiera = new JLabel("HASIERA PANELA", JLabel.CENTER);
        titleHasiera.setBounds(50, 20, 900, 30);
        titleHasiera.setFont(titleFont);
        
        logoaImg2 = new JLabel(); logoaImg2.setBounds(394, 60, 240, 211);
        img1 = new JLabel(); img1.setBounds(100, 250, 350, 200);
        img2 = new JLabel(); img2.setBounds(550, 250, 350, 200);
        
        atzerantz = new JButton("Atzerantz"); atzerantz.setBounds(800, 50, 100, 30);
        atera2 = new JButton("Atera"); atera2.setBounds(910, 50, 70, 30);
        
        klasifikazioaIkusi = new JButton("Klasifikazioa ikusi"); klasifikazioaIkusi.setBounds(150, 480, 250, 40);
        sartuEmaitza = new JButton("Sartu Emaitza"); sartuEmaitza.setBounds(600, 480, 250, 40);
        taldeakIkusi = new JButton("Taldeak ikusi"); taldeakIkusi.setBounds(150, 530, 250, 40);
        jokalariakAldatu = new JButton("Jokalariak Aldatu"); jokalariakAldatu.setBounds(600, 530, 250, 40);

        HasierakoPanela.add(titleHasiera); HasierakoPanela.add(logoaImg2); HasierakoPanela.add(img1);
        HasierakoPanela.add(img2); HasierakoPanela.add(atzerantz); HasierakoPanela.add(atera2);
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
        ateraKlasif = new JButton("Atera"); ateraKlasif.setBounds(850, 520, 100, 30);

        KlasifikazioaPanela.add(titleKlasif); KlasifikazioaPanela.add(scrollTabla);
        KlasifikazioaPanela.add(atzerantzKlasif); KlasifikazioaPanela.add(ateraKlasif);

        // --- JOKALARIAK ALDATU PANELA ---
        JokalariakPanela = new JPanel(null);
        JokalariakPanela.setPreferredSize(new Dimension(1000, 600));

        JLabel titleJokalariak = new JLabel("JOKALARIAK ALDATU", JLabel.CENTER);
        titleJokalariak.setFont(titleFont);
        titleJokalariak.setBounds(50, 20, 900, 30);
        JokalariakPanela.add(titleJokalariak);

        JComboBox<String> comboIzquierda = new JComboBox<>();
        comboIzquierda.setBounds(50, 60, 200, 25);
        JokalariakPanela.add(comboIzquierda);
        for (Taldea t : Metodoak.taldeakMasterList) {
            comboIzquierda.addItem(t.getIzena());
        }
    
     
        JComboBox<String> comboDerecha = new JComboBox<>();
        comboDerecha.setBounds(750, 60, 200, 25);
        JokalariakPanela.add(comboDerecha);
        for (Taldea t : Metodoak.taldeakMasterList) {
            comboDerecha.addItem(t.getIzena());
        }
     

        String[] columnasIzquierda = {"Izena", "Abizena", "DNI", "Taldea"};
        DefaultTableModel modeloIzquierda = new DefaultTableModel(columnasIzquierda, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tablaIzquierda = new JTable(modeloIzquierda);
        JScrollPane scrollIzquierda = new JScrollPane(tablaIzquierda);
        scrollIzquierda.setBounds(50, 100, 400, 400);
        JokalariakPanela.add(scrollIzquierda);

        String[] columnasDerecha = {"Izena", "Abizena", "DNI", "Taldea"};
        DefaultTableModel modeloDerecha = new DefaultTableModel(columnasDerecha, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };        
        JTable tablaDerecha = new JTable(modeloDerecha);
        JScrollPane scrollDerecha = new JScrollPane(tablaDerecha);
        scrollDerecha.setBounds(550, 100, 400, 400);
        JokalariakPanela.add(scrollDerecha);
     // Listener del comboBox derecho
        comboDerecha.addActionListener(e -> {
            String seleccionado = (String) comboDerecha.getSelectedItem();
            Metodoak.meterlosJokalaris(seleccionado, tablaDerecha);
        });
        // Listener del comboBox izquierdo
        comboIzquierda.addActionListener(e -> {
            String seleccionado = (String) comboIzquierda.getSelectedItem();
            Metodoak.meterlosJokalaris(seleccionado, tablaIzquierda);
        });
        JButton btnAldatu = new JButton("Aldatu");
        btnAldatu.setBounds(460, 260, 80, 40);
        btnAldatu.addActionListener(e -> {
            // Lógica para intercambiar jugadores
        	   String seleccionadoderecha = (String) comboDerecha.getSelectedItem();
               String seleccionadoizquierda = (String) comboIzquierda.getSelectedItem();

           Metodoak.actualizarTablasJokalariak(seleccionadoderecha, seleccionadoizquierda, tablaDerecha, tablaIzquierda);
        	 	
        });
        JokalariakPanela.add(btnAldatu);

        JButton btnIrten = new JButton("Irten");
        btnIrten.setBounds(50, 520, 100, 30);
        btnIrten.addActionListener(e -> cardLayout.show(contentPanel, "Hasiera"));
        JokalariakPanela.add(btnIrten);

        JButton btnAtera = new JButton("Atera");
        btnAtera.setBounds(850, 520, 100, 30);
        btnAtera.addActionListener(e -> Metodoak.atera());
        JokalariakPanela.add(btnAtera);

        // --- TALDEAK PANELA ---
        TaldeakPanela = new JPanel(null);
        TaldeakPanela.setPreferredSize(new Dimension(1000,600));

        comboBox = new JComboBox<>();
        for (Taldea t : Metodoak.taldeakMasterList) {
            comboBox.addItem(t.getIzena());
        }
        comboBox.setBounds(400, 10, 200, 25);
        TaldeakPanela.add(comboBox);

        String[] columnasPequena = {"SorreraUrtea", "Lehendakari", "N_Bazkideak"};
        DefaultTableModel modeloPequena = new DefaultTableModel(columnasPequena,0);
        tablaPequena = new JTable(modeloPequena);
        tablaPequena.setFillsViewportHeight(true);

        JScrollPane scrollPequena = new JScrollPane(tablaPequena);
        scrollPequena.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPequena.setBounds(50, 50, 900, tablaPequena.getRowHeight() + tablaPequena.getTableHeader().getPreferredSize().height);
        TaldeakPanela.add(scrollPequena);

        String[] columnasGrande = {"Izena","Abizena","JaiotzeData","NAN","Taldea","Prezioa","JokalariarenPuntuak"};
        DefaultTableModel modeloGrande = new DefaultTableModel(columnasGrande,0);
        tablaGrande = new JTable(modeloGrande);
        tablaGrande.setFillsViewportHeight(true);

        JScrollPane scrollGrande = new JScrollPane(tablaGrande);
        scrollGrande.setBounds(50, 120, 900, 400);
        scrollGrande.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        TaldeakPanela.add(scrollGrande);

        comboBox.addActionListener(e -> {
            String seleccionado = (String) comboBox.getSelectedItem();
            Metodoak.actualizarTablasTaldeak(seleccionado, tablaPequena, tablaGrande);
        });

        JButton atzerantzTaldeak = new JButton("Atzerantz");
        atzerantzTaldeak.setBounds(50, 540, 100, 30);
        atzerantzTaldeak.addActionListener(e -> cardLayout.show(contentPanel, "Hasiera"));

        JButton ateraTaldeak = new JButton("Atera");
        ateraTaldeak.setBounds(850, 540, 100, 30);
        ateraTaldeak.addActionListener(e -> Metodoak.atera());

        TaldeakPanela.add(atzerantzTaldeak);
        TaldeakPanela.add(ateraTaldeak);

        // --- EMAITZA PANELA ---
        EmaitzaPanela = new JPanel(null);
        JLabel titleEmaitza = new JLabel("LIGAKO EMAITZAK (10 JORNADA)", JLabel.CENTER);
        titleEmaitza.setBounds(50, 20, 900, 30);
        titleEmaitza.setFont(titleFont);

        modeloEmaitzak = new DefaultTableModel(new String[]{"Jornada / Partidua", "Puntuak", "vs", "Puntuak", "Kanpoko Taldea"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 1. Bakarrik editatu 1 y 3 (puntuak)
                if (column != 1 && column != 3) return false;

                // 2. Ezin da editatu "JORNADA" edo "Jaurdunaldia" lerroak
                Object val = getValueAt(row, 0);
                if (val != null && (val.toString().startsWith("JORNADA") || val.toString().startsWith("Jaurdunaldia"))) {
                    return false;
                }
                return true;
            }
        };

        tablaEmaitzak = new JTable(modeloEmaitzak);
        JScrollPane scrollEmaitzak = new JScrollPane(tablaEmaitzak);
        scrollEmaitzak.setBounds(50, 80, 900, 400);

        atzerantzEmaitza = new JButton("Atzerantz"); 
        atzerantzEmaitza.setBounds(50, 520, 100, 30);

        gordeEmaitza = new JButton("Gorde Emaitzak");
        gordeEmaitza.setBounds(400, 520, 200, 30);

        ateraEmaitza = new JButton("Atera"); 
        ateraEmaitza.setBounds(850, 520, 100, 30);

        EmaitzaPanela.add(titleEmaitza);
        EmaitzaPanela.add(scrollEmaitzak);
        EmaitzaPanela.add(atzerantzEmaitza);
        EmaitzaPanela.add(gordeEmaitza);
        EmaitzaPanela.add(ateraEmaitza);
    }
    /**
     * Ligako klasifikazioa kalkulatu eta eguneratzen du.
     * <p>
     * Sartutako emaitzen arabera, talde bakoitzaren estatistikak
     * berrabiarazten eta kalkulatzen dira:
     * </p>
     * <ul>
     *   <li>Puntu totalak</li>
     *   <li>Irabazitako partidak</li>
     *   <li>Galdutako partidak</li>
     *   <li>Aldeko eta aurkako puntuak</li>
     * </ul>
     *
     * <p>
     * Ondoren, taldeak puntuen eta puntu diferentziaren arabera
     * ordenatzen dira eta sailkapenaren taula eguneratzen da.
     * </p>
     */

    // --- Klasifikazioa Eguneratu  ---
    private void eguneratuKlasifikazioa() {
        for (Taldea t : Metodoak.taldeakMasterList) {
            t.setPuntuTotalak(0);
            t.setIrabazitakoak(0);
            t.setGaldutakoak(0);
            t.setPuntuakF(0);
            t.setPuntuakC(0);
        }

        for (int i = 0; i < modeloEmaitzak.getRowCount(); i++) {
            Object locNameObj = modeloEmaitzak.getValueAt(i, 0);
            if (locNameObj == null) continue;
            String locName = locNameObj.toString();
            if (locName.startsWith("JORNADA") || locName.startsWith("Jaurdunaldia")) continue;

            Object valL = modeloEmaitzak.getValueAt(i, 1);
            Object valV = modeloEmaitzak.getValueAt(i, 3);

            if (valL == null || valL.toString().trim().isEmpty() || 
                valV == null || valV.toString().trim().isEmpty()) {
                continue;
            }

            int pL = Integer.parseInt(valL.toString().trim());
            int pV = Integer.parseInt(valV.toString().trim());
            String visName = (String) modeloEmaitzak.getValueAt(i, 4);

            if (pL == 0 && pV == 0) continue;

            Taldea tL = null, tV = null;
            for (Taldea t : Metodoak.taldeakMasterList) {
                if (t.getIzena().equals(locName)) tL = t;
                if (t.getIzena().equals(visName)) tV = t;
            }

            if (tL != null && tV != null) {
                tL.setPuntuakF(tL.getPuntuakF() + pL);
                tL.setPuntuakC(tL.getPuntuakC() + pV);
                
                tV.setPuntuakF(tV.getPuntuakF() + pV);
                tV.setPuntuakC(tV.getPuntuakC() + pL);

                if (pL > pV) {
                    tL.setPuntuTotalak(tL.getPuntuTotalak() + 2); 
                    tL.setIrabazitakoak(tL.getIrabazitakoak() + 1);
                    tV.setPuntuTotalak(tV.getPuntuTotalak() + 1); 
                    tV.setGaldutakoak(tV.getGaldutakoak() + 1);
                } else {
                    tV.setPuntuTotalak(tV.getPuntuTotalak() + 2); 
                    tV.setIrabazitakoak(tV.getIrabazitakoak() + 1);
                    tL.setPuntuTotalak(tL.getPuntuTotalak() + 1); 
                    tL.setGaldutakoak(tL.getGaldutakoak() + 1);
                }
            }
        }

        Metodoak.taldeakMasterList.sort((t1, t2) -> {
            int diff = Integer.compare(t2.getPuntuTotalak(), t1.getPuntuTotalak());
            if (diff != 0) return diff;
            
            int avg1 = t1.getPuntuakF() - t1.getPuntuakC();
            int avg2 = t2.getPuntuakF() - t2.getPuntuakC();
            return Integer.compare(avg2, avg1);
        });

        modeloTabla.setRowCount(0);
        for (Taldea t : Metodoak.taldeakMasterList) {
            Object[] fila = {
                t.getIzena(), 
                t.getPuntuTotalak(), 
                t.getIrabazitakoak(), 
                t.getGaldutakoak(), 
                t.getPuntuakF(), 
                t.getPuntuakC()
            };
            modeloTabla.addRow(fila);
        }
    }
    /**
     * Ligako jardunaldiak eta partidak automatikoki sortzen ditu.
     * <p>
     * Joan-etorriko egutegia sortzen da, guztira 10 jardunaldirekin.
     * Metodo hau behin bakarrik exekutatuko da emaitzen taula hutsik badago.
     * </p>
     */
    // --- Emaitzak Prozesatu ---
    private void generatuJornadak() {
        if (modeloEmaitzak.getRowCount() > 0) return;

        ArrayList<Taldea> kopia = new ArrayList<>(Metodoak.taldeakMasterList);
        java.util.Collections.shuffle(kopia);

        for (int j = 1; j <= 5; j++) {
            modeloEmaitzak.addRow(new Object[]{"Jaurdunaldia " + j, "", "", "", ""});
            modeloEmaitzak.addRow(new Object[]{kopia.get(0).getIzena(), 0, "vs", 0, kopia.get(5).getIzena()});
            modeloEmaitzak.addRow(new Object[]{kopia.get(1).getIzena(), 0, "vs", 0, kopia.get(4).getIzena()});
            modeloEmaitzak.addRow(new Object[]{kopia.get(2).getIzena(), 0, "vs", 0, kopia.get(3).getIzena()});
            
            Taldea last = kopia.remove(5);
            kopia.add(1, last);
        }

        int jornadaerrenkada = 4;
        for (int j = 0; j < 5; j++) {
            modeloEmaitzak.addRow(new Object[]{"JORNADA " + (j + 6), "", "", "", ""});
            for (int p = 1; p <= 3; p++) {
                int filaOriginal = (j * jornadaerrenkada) + p;
                String local = (String) modeloEmaitzak.getValueAt(filaOriginal, 0);
                String visitante = (String) modeloEmaitzak.getValueAt(filaOriginal, 4);
                modeloEmaitzak.addRow(new Object[]{visitante, 0, "vs", 0, local});
            }
        }
    }
    /**
     * Erabiltzaileak sartutako emaitzak balidatu eta prozesatzen ditu.
     * <p>
     * Honako egiaztapen hauek egiten dira:
     * </p>
     * <ul>
     *   <li>Balioak zenbakiak direla</li>
     *   <li>Puntu negatiborik ez dagoela</li>
     *   <li>Berdinketarik ez dagoela</li>
     *   <li>Partida bakoitza osorik beteta dagoela</li>
     * </ul>
     *
     * <p>
     * Datuak zuzenak badira, sailkapena eguneratu eta datuak
     * fitxategian gordetzen dira.
     * </p>
     */
    private void prozesatuEmaitzak() {
        if (tablaEmaitzak.isEditing()) {
            tablaEmaitzak.getCellEditor().stopCellEditing();
        }

        try {
            for (int i = 0; i < modeloEmaitzak.getRowCount(); i++) {
                Object col0Obj = modeloEmaitzak.getValueAt(i, 0);
                if (col0Obj == null) continue;
                
                String col0 = col0Obj.toString();
                if (col0.startsWith("Jaurdunaldia") || col0.startsWith("JORNADA")) continue;

                Object valL = modeloEmaitzak.getValueAt(i, 1);
                Object valV = modeloEmaitzak.getValueAt(i, 3);

                if ((valL == null || valL.toString().trim().isEmpty()) && 
                    (valV == null || valV.toString().trim().isEmpty())) {
                    continue; 
                }

                if (valL == null || valL.toString().trim().isEmpty() || 
                    valV == null || valV.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Abisua: '" + col0 + "' partidak markagailua osatugabea du.");
                    return;
                }

                int pLocal, pVisit;
                try {
                    pLocal = Integer.parseInt(valL.toString().trim());
                    pVisit = Integer.parseInt(valV.toString().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Errorea: Bakarrik zenbakiak '" + col0 + "' eremuan");
                    return;
                }

                if (pLocal < 0 || pVisit < 0) {
                    JOptionPane.showMessageDialog(this, "Errorea: Puntu negatiboak '" + col0 + "'");
                    return;
                }
                if (pLocal == pVisit && !(pLocal == 0 && pVisit == 0)) {
                    JOptionPane.showMessageDialog(this, "Abisua: Saskibaloian ez dago berdinketarik (" + col0 + ")");
                    return;
                }
            }

            eguneratuKlasifikazioa(); 
            Metodoak.gordeDatuak();   
            JOptionPane.showMessageDialog(this, "Emaitzak prozesatu dira.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errorea: " + e.getMessage());
        }
    }
    /**
     * Erabiltzailearen rolaren arabera botoien ikusgarritasuna
     * konfiguratzen du.
     *
     * @param rola autentifikatutako erabiltzailearen rola
     */
    // --- Metodo Erabilgarriak ---
    private void erakutsiPanelak(String rola) {
        klasifikazioaIkusi.setVisible(true);
        taldeakIkusi.setVisible(true);
        sartuEmaitza.setVisible(rola.equals("Admin"));
        jokalariakAldatu.setVisible(rola.equals("Presidentea"));
    }
    /**
     * Aplikazioko osagai bisualak (irudiak) kargatzen eta esleitzen ditu.
     */
    private void konfiguratuOsagaiBisualak() {
        kargatuIrudia(logoaImg1, 200, 150, "/Multimedia/logoa.png");
        kargatuIrudia(logoaImg2, 200, 150, "/Multimedia/logoa.png");
        kargatuIrudia(img1, 150, 150, "/Multimedia/img1.png");
        kargatuIrudia(img2, 150, 150, "/Multimedia/img2.png");
    }
    /**
     * Irudi bat baliabideetatik kargatu eta JLabel batean ezartzen du.
     *
     * @param label irudia bistaratuko duen JLabel-a
     * @param w irudiaren zabalera
     * @param h irudiaren altuera
     * @param path irudiaren fitxategiaren bidea
     */
    private void kargatuIrudia(JLabel label, int w, int h, String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                label.setText("");
            }
        } catch (Exception e) { label.setText("Error Imagen"); }
    }
    /**
     * Aplikazioaren sarrera-puntua.
     * <p>
     * Swing-eko gertaeren harian aplikazioa abiarazten du.
     * </p>
     *
     * @param args komando-lerroko argumentuak
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ErronkaBisuala());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // --- LOGIN EKINTZAK ---
        if (src == sartu) {
            String usernameInput = textErabiltzaile.getText();
            String passwordInput = new String(textPasahitza.getPassword());

            if (usernameInput.trim().isEmpty() || passwordInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mesedez, bete eremu guztiak.", "Errorea", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rola = Metodoak.login(usernameInput, passwordInput);
            if (rola != null) {
                textErabiltzaile.setText(null);
                textPasahitza.setText(null);
                erakutsiPanelak(rola);
                cardLayout.show(contentPanel, "Hasiera");
            } else {
                JOptionPane.showMessageDialog(null, "Erabiltzaile edo Pasahitz okerra", "Errorea", JOptionPane.ERROR_MESSAGE);
            }
        } else if (src == atera1) {
            Metodoak.atera();
        }

        // --- HASIERA EKINTZAK ---
        else if (src == atzerantz) {
            cardLayout.show(contentPanel, "Login");
        } else if (src == atera2) {
            Metodoak.atera();
        } else if (src == klasifikazioaIkusi) {
            eguneratuKlasifikazioa();
            cardLayout.show(contentPanel, "Klasifikazioa");
        } else if (src == sartuEmaitza) {
            generatuJornadak();
            cardLayout.show(contentPanel, "Emaitzak");
        } else if (src == taldeakIkusi) {
            cardLayout.show(contentPanel, "Taldeak");
        } else if (src == jokalariakAldatu) {
            cardLayout.show(contentPanel, "Jokalariak");
        }

        // --- KLASIFIKAZIOA EKINTZAK ---
        else if (src == atzerantzKlasif) {
            cardLayout.show(contentPanel, "Hasiera");
        } else if (src == ateraKlasif) {
            Metodoak.atera();
        }

        // --- EMAITZA EKINTZAK ---
        else if (src == atzerantzEmaitza) {
            cardLayout.show(contentPanel, "Hasiera");
        } else if (src == ateraEmaitza) {
            Metodoak.atera();
        } else if (src == gordeEmaitza) {
            prozesatuEmaitzak();
        }

        // --- TALDEAK PANELA ---
        else if (src == comboBox) {
            String seleccionado = (String) comboBox.getSelectedItem();
            Metodoak.actualizarTablasTaldeak(seleccionado, tablaPequena, tablaGrande);
        }
    }

}														