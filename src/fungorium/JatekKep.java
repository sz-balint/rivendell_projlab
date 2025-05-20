package fungorium;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public class JatekKep extends JFrame {

    private static final long serialVersionUID = 1L;
    private JatekLogika jatek;
    private int szelesseg;
    private int magassag;
    private JLabel[] pontLabels = new JLabel[8];
    private JButton[] lepesGombok = new JButton[3];
    private Palyakep palyaKep;
    private JLabel aktJatekosNev;
    private JLabel palyaCim;
    private String selectedAction = null;
    private JButton passButton = new JButton("Passz");
    private Color vilagos = new Color(239, 228, 176);
    private Color sotet = new Color(74, 52, 31);

    public JatekKep(JatekLogika jat, Palyakep palyaKep) {
        super("Játék");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jatek = jat;
        this.palyaKep = palyaKep;
        szelesseg = 1000;
        magassag = 690;
        setSize(szelesseg, magassag);
        setLayout(null);
        setResizable(false);

        getContentPane().setBackground(sotet);

        // Pálya cím felül
        palyaCim = new JLabel("PÁLYA", JLabel.CENTER);
        palyaCim.setFont(new Font("Serif", Font.BOLD, 30));
        palyaCim.setForeground(sotet);
        palyaCim.setBackground(vilagos);
        palyaCim.setOpaque(true);
        palyaCim.setBounds(0, 0, 600, 60);
        add(palyaCim);

        // Pályakép elhelyezése
        palyaKep.setBounds(0, 60, 600, 600);
        add(palyaKep);

        // Jobb oldali sáv
        int jobbSzalSzelesseg = szelesseg - 600;
        JPanel jobbSzal = new JPanel();
        jobbSzal.setLayout(null);
        jobbSzal.setSize(jobbSzalSzelesseg, magassag);
        jobbSzal.setLocation(600, 0);
        jobbSzal.setBackground(sotet);
        add(jobbSzal);

        // Aktuális játékos
        JLabel aktjatekos = new JLabel("Aktuális játékos:");
        aktjatekos.setFont(new Font("Serif", Font.BOLD, 25));
        aktjatekos.setBounds(20, 20, 350, 30);
        aktjatekos.setForeground(vilagos);
        jobbSzal.add(aktjatekos);

        aktJatekosNev = new JLabel(jatek.getAktivJatekos() != null ? jatek.getAktivJatekos().getNev() : "-");
        aktJatekosNev.setFont(new Font("Serif", Font.BOLD, 20));
        aktJatekosNev.setBounds(20, 50, 350, 30);
        aktJatekosNev.setForeground(vilagos);
        jobbSzal.add(aktJatekosNev);

        // Pontszámok
        JLabel pontszamok = new JLabel("Pontszámok:");
        pontszamok.setFont(new Font("Serif", Font.BOLD, 25));
        pontszamok.setBounds(20, 100, 350, 30);
        pontszamok.setForeground(vilagos);
        jobbSzal.add(pontszamok);

        // Pontszámok listája
        for (int i = 0; i < pontLabels.length; i++) {
            pontLabels[i] = new JLabel();
            pontLabels[i].setFont(new Font("Serif", Font.PLAIN, 18));
            pontLabels[i].setBounds(20, 140 + i * 25, 350, 25);
            pontLabels[i].setForeground(vilagos);
            jobbSzal.add(pontLabels[i]);
        }

        // Lépések
        JLabel lepesek = new JLabel("Lépések:");
        lepesek.setFont(new Font("Serif", Font.BOLD, 25));
        lepesek.setBounds(20, 350, 350, 30);
        lepesek.setForeground(vilagos);
        jobbSzal.add(lepesek);

        // Lépés gombok
        for (int i = 0; i < lepesGombok.length; i++) {
            lepesGombok[i] = new JButton();
            lepesGombok[i].setFont(new Font("Serif", Font.PLAIN, 18));
            lepesGombok[i].setForeground(vilagos);
            lepesGombok[i].setBackground(sotet);
            lepesGombok[i].setBorder(BorderFactory.createLineBorder(vilagos, 2));
            lepesGombok[i].setBounds(20, 390 + i * 50, 350, 40);
            lepesGombok[i].setEnabled(false);
            jobbSzal.add(lepesGombok[i]);
        }

        // Passz gomb
        passButton.setFont(new Font("Serif", Font.PLAIN, 18));
        passButton.setForeground(vilagos);
        passButton.setBackground(sotet);
        passButton.setBorder(BorderFactory.createLineBorder(vilagos, 2));
        passButton.setBounds(20, 540, 350, 40);
        passButton.addActionListener(e -> {
            jatek.ujKor();
            frissul();
        });
        jobbSzal.add(passButton);

        // Save gomb
        JButton saveButton = new JButton("Mentés");
        saveButton.setFont(new Font("Serif", Font.PLAIN, 18));
        saveButton.setForeground(vilagos);
        saveButton.setBackground(sotet);
        saveButton.setBorder(BorderFactory.createLineBorder(vilagos, 2));
        saveButton.setBounds(20, 590, 170, 40);
        saveButton.addActionListener(e -> {
            try {
                Fajlkezelo fajlkezelo = new Fajlkezelo();
                fajlkezelo.save(jatek, "jatek");
                JOptionPane.showMessageDialog(this, 
                    "Játék sikeresen mentve!", 
                    "Mentés", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Hiba történt a mentés során: " + ex.getMessage(), 
                    "Mentési hiba", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        jobbSzal.add(saveButton);

        // Kilépés gomb
        JButton exitButton = new JButton("Kilépés");
        exitButton.setFont(new Font("Serif", Font.PLAIN, 18));
        exitButton.setForeground(vilagos);
        exitButton.setBackground(sotet);
        exitButton.setBorder(BorderFactory.createLineBorder(vilagos, 2));
        exitButton.setBounds(200, 590, 170, 40);
        exitButton.addActionListener(e -> System.exit(0));
        jobbSzal.add(exitButton);

        // Mouse click handler for selecting targets
        palyaKep.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedAction != null) {
                    Object clickedObject = palyaKep.getObjectAt(e.getX(), e.getY());
                    if (clickedObject != null) {
                        executeAction(selectedAction, clickedObject);
                    } else {
                        JOptionPane.showMessageDialog(JatekKep.this, 
                            "Nincs érvényes célpont a kiválasztott helyen!", 
                            "Hibás célpont", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        frissul();
    }

    // ... (rest of your existing methods remain unchanged) ...
    private void executeAction(String action, Object target) {
        Jatekos currentPlayer = jatek.getAktivJatekos();
        boolean actionSuccess = false;

        try {
            switch (action.toLowerCase()) {
                case "lépés":
                    if (target instanceof Tekton && currentPlayer instanceof Rovarasz) {
                        Rovarasz rov = (Rovarasz) currentPlayer;
                        Tekton targetTekton = (Tekton) target;
                        
                        for (Rovar rovar : rov.getRovarok()) {
                            String[] command = {"lep", String.valueOf(targetTekton.getId()), String.valueOf(rovar.getId())};
                            if (jatek.vanValid(rov, command)) {
                                rov.Kor("lep", jatek, command);
                                actionSuccess = true;
                                break;
                            }
                        }
                    }
                    break;
                    
                case "evés":
                    if (target instanceof Rovar && currentPlayer instanceof Rovarasz) {
                        Rovar rovar = (Rovar) target;
                        String[] command = {"eszik", String.valueOf(rovar.getId())};
                        if (jatek.vanValid(currentPlayer, command)) {
                            currentPlayer.Kor("eszik", jatek, command);
                            actionSuccess = true;
                        }
                    }
                    break;
                    
                case "vágás":
                    if (target instanceof GombaFonal && currentPlayer instanceof Rovarasz) {
                        GombaFonal fonal = (GombaFonal) target;
                        Rovarasz rov = (Rovarasz) currentPlayer;
                        
                        for (Rovar rovar : rov.getRovarok()) {
                            String[] command = {"vagas", String.valueOf(fonal.getId()), String.valueOf(rovar.getId())};
                            if (jatek.vanValid(rov, command)) {
                                rov.Kor("vagas", jatek, command);
                                actionSuccess = true;
                                break;
                            }
                        }
                    }
                    break;
                    
                case "spóraszórás":
                    if (target instanceof GombaTest && currentPlayer instanceof Gombasz) {
                        GombaTest test = (GombaTest) target;
                        String[] command = {"sporaszoras", String.valueOf(test.getId())};
                        if (jatek.vanValid(currentPlayer, command)) {
                            currentPlayer.Kor("sporaszoras", jatek, command);
                            actionSuccess = true;
                        }
                    }
                    break;
                    
                case "új test":
                    if (target instanceof Tekton && currentPlayer instanceof Gombasz) {
                        Gombasz g = (Gombasz) currentPlayer;
                        Tekton tekton = (Tekton) target;
                        String[] command = {"ujTest", String.valueOf(tekton.getId()), g.getNev()};
                        if (jatek.vanValid(g, command)) {
                            g.Kor("ujTest", jatek, command);
                            actionSuccess = true;
                        }
                    }
                    break;
                    
                case "fonálnövesztés":
                    if (target instanceof Tekton && currentPlayer instanceof Gombasz) {
                        // This would need more complex handling as it involves multiple tektons
                        // You might need to modify the UI to select multiple targets
                        JOptionPane.showMessageDialog(this, 
                            "Fonálnövesztéshez válaszd ki a kezdő és végpontot!", 
                            "Több célpont szükséges", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
            }

            if (actionSuccess) {
                selectedAction = null;
                jatek.ujKor();
                frissul();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "A művelet nem hajtható végre a kiválasztott célponton!", 
                    "Művelet sikertelen", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Hiba történt a művelet végrehajtása közben: " + ex.getMessage(), 
                "Hiba", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    void frissul() {
        // Aktuális játékos név frissítése
        if (jatek.getAktivJatekos() != null) {
            aktJatekosNev.setText(jatek.getAktivJatekos().getNev());
        } else {
            aktJatekosNev.setText("-");
            return; // Ha nincs aktív játékos, ne frissítsünk tovább
        }

        // Pontszámok frissítése
        List<Jatekos> jatekosok = jatek.getJatekosok();
        for (int i = 0; i < pontLabels.length; i++) {
            if (i < jatekosok.size() && jatekosok.get(i) != null) {
                pontLabels[i].setText((i + 1) + ". " + jatekosok.get(i).getNev() + ": " + jatekosok.get(i).pontok);
            } else {
                pontLabels[i].setText("");
            }
        }

        // Lépési lehetőségek meghatározása
        List<String> lehetosegek = new ArrayList<>();
        Jatekos aktiv = jatek.getAktivJatekos();

        if (aktiv != null) {
            if (aktiv.getTipus().equals("Rovarasz")) {
                // Rovarasz műveletei
                lehetosegek.add("Vágás");
                lehetosegek.add("Lépés");
                lehetosegek.add("Evés");
            } else if (aktiv.getTipus().equals("Gombasz")) {
                // Gombász műveletei
                lehetosegek.add("Új test");
                lehetosegek.add("Spóraszórás");
                lehetosegek.add("Fonálnövesztés");
            }
        }

        // Gombok frissítése
        for (int i = 0; i < lepesGombok.length; i++) {
            lepesGombok[i].setText("");
            lepesGombok[i].setEnabled(false);
            lepesGombok[i].setBackground(sotet);
            
            // Régi ActionListenerek eltávolítása
            for (ActionListener al : lepesGombok[i].getActionListeners()) {
                lepesGombok[i].removeActionListener(al);
            }
        }

        // Új gombok beállítása
        for (int i = 0; i < Math.min(lehetosegek.size(), lepesGombok.length); i++) {
            String akcio = lehetosegek.get(i);
            lepesGombok[i].setText(akcio);
            lepesGombok[i].setEnabled(true);
            
            final String action = akcio; // final változó az anonym osztályhoz
            lepesGombok[i].addActionListener(e -> {
                selectedAction = action;
                // Kijelölés visszaállítása
                for (JButton btn : lepesGombok) {
                    btn.setBackground(sotet);
                }
                ((JButton)e.getSource()).setBackground(Color.YELLOW);
                
                JOptionPane.showMessageDialog(JatekKep.this,
                    "Válaszd ki a célpontot a pályán a(z) " + action + " művelethez!",
                    "Célpont kiválasztása",
                    JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }
}