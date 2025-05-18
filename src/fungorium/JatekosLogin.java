package fungorium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class JatekosLogin extends JFrame {
	//Jatekosok mentesehez szukseges valtozok
    private String kivalasztottTipus = "";
    private String kivalasztottSzin = "";
    private JTextField nevMezo;
    private JTextArea jatekosLista;
    private ArrayList<String> jatekosok = new ArrayList<>();
    private final int MAX_JATEKOS = 8;
    //Szinek
    private Color hatterszin = new Color(245, 235, 200);
    private Color szovegszin = new Color(80, 50, 20);
    //Szinvalsztashoz
    private java.util.Map<Color, JButton> szinGombMap = new java.util.HashMap<>();

    public JatekosLogin() {
    	//Ablak beallitasai
        setTitle("Játékos Login GPT");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(735, 450);
        setLocationRelativeTo(null);
        getContentPane().setBackground(hatterszin);
        setLayout(null);

        Font nagyBetu = new Font("Serif", Font.BOLD, 26);
        Font gombFont = new Font("Serif", Font.BOLD, 18);
        
        //Cím
        JLabel cim = new JLabel("Játékos Login");
        cim.setFont(nagyBetu);
        cim.setAlignmentX(CENTER_ALIGNMENT);
        cim.setForeground(szovegszin);
        cim.setBounds(280, 10, 300, 40);
        add(cim);
        
        //Figyelmeztetes
        JLabel figyelmeztetes = new JLabel("⚠ Mindkét típusból legalább két játékos szükséges");
        figyelmeztetes.setFont(new Font("Serif", Font.BOLD, 16));
        figyelmeztetes.setForeground(Color.BLACK);
        figyelmeztetes.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        figyelmeztetes.setOpaque(true);
        figyelmeztetes.setBackground(hatterszin);
        figyelmeztetes.setBounds(75, 60, 570, 40);
        figyelmeztetes.setHorizontalAlignment(SwingConstants.CENTER);
        add(figyelmeztetes);

        // Tipus
        JLabel tipusLabel = new JLabel("Típus:");
        tipusLabel.setFont(gombFont);
        tipusLabel.setForeground(szovegszin);
        tipusLabel.setBounds(50, 120, 80, 30);
        add(tipusLabel);
        
        //Tipus dombok
        JButton gombasz = createStyledButton("Gombász");
        JButton rovarasz = createStyledButton("Rovarász");
        JButton random = createStyledButton("Random");
        gombasz.setBounds(120, 120, 100, 30);
        rovarasz.setBounds(230, 120, 100, 30);
        random.setBounds(340, 120, 100, 30);
        add(gombasz);
        add(rovarasz);
        add(random);
        
        //Akciok
        ActionListener tipusListener = e -> {
            String action = e.getActionCommand();
            if (action.equals("Random")) {
            	//Kesobb atirjuk, most csak van
                kivalasztottTipus = "Random";
            } else {
                kivalasztottTipus = action;
            }
        };
        gombasz.addActionListener(tipusListener);
        rovarasz.addActionListener(tipusListener);
        random.addActionListener(tipusListener);

        // Nev megadasa
        JLabel nevLabel = new JLabel("Név:");
        nevLabel.setFont(gombFont);
        nevLabel.setForeground(szovegszin);
        nevLabel.setBounds(50, 170, 80, 30);
        add(nevLabel);

        nevMezo = new JTextField();
        nevMezo.setBounds(120, 170, 200, 30);
        nevMezo.setBackground(hatterszin);
        nevMezo.setBorder(BorderFactory.createLineBorder(szovegszin, 2));
        nevMezo.setForeground(szovegszin);
        nevMezo.setCaretColor(szovegszin);
        add(nevMezo);

        // Szin megadasa
        JLabel szinLabel = new JLabel("Szín:");
        szinLabel.setFont(gombFont);
        szinLabel.setForeground(szovegszin);
        szinLabel.setBounds(50, 220, 80, 30);
        add(szinLabel);

        Color[] szinek = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN.darker(),
                          Color.CYAN, Color.BLUE, new Color(160, 45, 245), Color.MAGENTA};
        int x = 120;
        for (Color szin : szinek) {
            JButton szinGomb = new JButton();
            szinGomb.setBackground(szin);
            szinGomb.setOpaque(true);
            szinGomb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            szinGomb.setBounds(x, 220, 30, 30);
            szinGomb.addActionListener(e -> {
                if (szinGomb.isEnabled()) {
                    kivalasztottSzin = colorToString(szin);
                }
            });
            add(szinGomb);
            szinGombMap.put(szin, szinGomb); // track the button
            x += 35;
        }

        // Jatekos Lista
        JLabel listaCimke = new JLabel("Aktuális Játékosok:");
        listaCimke.setFont(gombFont);
        listaCimke.setForeground(szovegszin);
        listaCimke.setBounds(500, 120, 200, 30);
        add(listaCimke);

        jatekosLista = new JTextArea();
        jatekosLista.setEditable(false);
        jatekosLista.setBackground(hatterszin);
        jatekosLista.setBorder(BorderFactory.createLineBorder(szovegszin, 2));
        jatekosLista.setFont(new Font("Monospaced", Font.PLAIN, 14));
        jatekosLista.setBounds(470, 160, 250, 180);
        jatekosLista.setLineWrap(false);
        jatekosLista.setWrapStyleWord(false);

        //Csuszka, de csak oldalra
        JScrollPane scrollPane = new JScrollPane(jatekosLista,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Fix meret
        scrollPane.setBounds(470, 160, 200, 200); 
        add(scrollPane);

        // Gombok alul
        //Hozzaadas
        JButton hozzaad = createStyledButton("Hozzáadás");
        hozzaad.setBounds(50, 300, 120, 40);
        add(hozzaad);
        hozzaad.addActionListener(e -> hozzaadJatekost());
        
        //Torles
        JButton torles = createStyledButton("Játékos Törlés");
        torles.setBounds(190, 300, 140, 40);
        add(torles);
        torles.addActionListener(e -> torolJatekost());
        
        //Vissza
        JButton vissza = createStyledButton("Vissza");
        vissza.setBounds(350, 300, 100, 40);
        add(vissza);
        vissza.addActionListener(e -> {
            int osszJatekos = jatekosok.size();
            int gombaszDb = 0;
            int rovaraszDb = 0;

            for (String j : jatekosok) {
                if (j.startsWith("Gombász")) gombaszDb++;
                if (j.startsWith("Rovarász")) rovaraszDb++;
            }
            //Ha nem megfelelo a jatekosok/Gombaszok/Rovaraszok szama szol
            if (osszJatekos < 4 || gombaszDb < 2 || rovaraszDb < 2) {
                int valasz = JOptionPane.showOptionDialog(
                        this,
                        "Nem megfelelő játékoslista.\nElveti az eddigieket?",
                        "Figyelem",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{"Mégse", "Igen"},
                        "Mégse"
                );

                if (valasz == 1) {
                	//Elfelejti az eddigieket es visszalep
                    dispose();
                }
            } else {
                //Jo lista eseten visszaadja a Kezdokepnek
                KezdoKep kezdoKep = new KezdoKep();
                kezdoKep.setJatekosok(new ArrayList<>(jatekosok)); 
                kezdoKep.setVisible(true);
                //Ablak bezarasa
                dispose();
            }
        });

        
        setVisible(true);
    }
    
    //Gombokhoz stilusbeallitas
    private JButton createStyledButton(String szoveg) {
        JButton button = new JButton(szoveg);
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setBackground(hatterszin);
        button.setForeground(szovegszin);
        button.setBorder(BorderFactory.createLineBorder(szovegszin, 2));
        return button;
    }
    
    //Jatekos hozzaadasa
    private void hozzaadJatekost() {
        String nev = nevMezo.getText().trim();
        //Ha ures a tipus, nev, vagy szin szol
        if (kivalasztottTipus.isEmpty() || nev.isEmpty() || kivalasztottSzin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Töltsd ki az összes mezőt!");
            return;
        }
        
        //Ha tul sok a jatekos szol
        if (jatekosok.size() >= MAX_JATEKOS) {
            JOptionPane.showMessageDialog(this, "Maximum 8 játékos lehet!");
            return;
        }

        // Mar foglalt a szin/nev szol
        for (String j : jatekosok) {
            if (j.contains(" - " + nev + " [")) {
                JOptionPane.showMessageDialog(this, "Ez a név már foglalt!");
                return;
            }
            if (j.endsWith("[" + kivalasztottSzin + "]")) {
                JOptionPane.showMessageDialog(this, "Ez a szín már foglalt!");
                return;
            }
        }
        
        //Random eseten kioszt egy random tipust
        if (kivalasztottTipus.equals("Random")) {
            kivalasztottTipus = Math.random() < 0.5 ? "Gombász" : "Rovarász";
        }
        
        // Minden jo, hozzaadjuk a listahoz
        String jatekos = kivalasztottTipus + " - " + nev + " [" + kivalasztottSzin + "]";
        jatekosok.add(jatekos);
        frissitLista();

        //Szin beszurkitese
        Color selectedColor = stringToColor(kivalasztottSzin);
        JButton gomb = szinGombMap.get(selectedColor);
        if (gomb != null) {
            gomb.setEnabled(false);
            gomb.setBackground(Color.LIGHT_GRAY);
        }
        //Minden kinullazasa a kovetkezo jatekos hozzaadasahoz
        nevMezo.setText("");
        kivalasztottSzin = "";
    }



    private void torolJatekost() {
        if (!jatekosok.isEmpty()) {
            // Utolso jatekos torlese
            String removedPlayer = jatekosok.remove(jatekosok.size() - 1);

            // Szin kinyerese
            String colorPart = removedPlayer.substring(removedPlayer.lastIndexOf("[") + 1, removedPlayer.lastIndexOf("]"));
            
            // Szin visszaallitasa
            Color removedColor = stringToColor(colorPart);
            JButton colorButton = szinGombMap.get(removedColor);
            
            if (colorButton != null) {
                colorButton.setEnabled(true);           
                colorButton.setBackground(removedColor);
            }

            //Lista frissitese
            frissitLista();
        }
    }

    //Lista frissitese
    private void frissitLista() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String j : jatekosok) {
            sb.append(index++).append(". ").append(j).append("\n");
        }
        jatekosLista.setText(sb.toString());
    }
    
    //szin converter stringbe
    private String colorToString(Color c) {
        if (c.equals(Color.RED)) return "piros";
        if (c.equals(Color.ORANGE)) return "narancs";
        if (c.equals(Color.YELLOW)) return "sárga";
        if (c.equals(Color.GREEN.darker())) return "zöld";
        if (c.equals(Color.CYAN)) return "Cián";
        if (c.equals(Color.BLUE)) return "kék";
        if (c.equals(new Color(160, 45, 245))) return "lila";
        if (c.equals(Color.MAGENTA)) return "Rózsaszín";
        return "ismeretlen";
    }
    
    //string converter szinbe
    private Color stringToColor(String s) {
        switch (s.toLowerCase()) {
            case "piros": return Color.RED;
            case "narancs": return Color.ORANGE;
            case "sárga": return Color.YELLOW;
            case "zöld": return Color.GREEN.darker();
            case "cián": return Color.CYAN;
            case "kék": return Color.BLUE;
            case "lila": return new Color(160, 45, 245);
            case "rózsaszín": return Color.MAGENTA;
            default: return null;
        }
    }

}


