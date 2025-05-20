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

        // Mouse click handler for selecting targets
        /*palyaKep.addMouseListener(new MouseAdapter() {
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
        });*/
        // Modified mouse click handler
        palyaKep.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentAction != null) {
                    Object clickedObject = palyaKep.getObjectAt(e.getX(), e.getY());
                    if (clickedObject != null) {
                        handleObjectSelection(clickedObject);
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

private List<Object> selectedObjects = new ArrayList<>();
private String currentAction = null;

private void executeAction(String action) {
    currentAction = action;
    selectedObjects.clear();
    
    // Show instruction based on the action
    String instruction = getActionInstruction(action);
    JOptionPane.showMessageDialog(this, instruction, "Művelet végrehajtása", JOptionPane.INFORMATION_MESSAGE);
}

private String getActionInstruction(String action) {
    switch (action.toLowerCase()) {
        case "lépés":
            return "Válassz ki egy rovart, majd a cél tekton!";
        case "evés":
            return "Válassz ki egy rovart, amit meg akarsz enni!";
        case "vágás":
            return "Válassz ki egy gombafonalat, majd a rovart ami végrehajtja!";
        case "spóraszórás":
            return "Válassz ki egy gombatestet!";
        case "új test":
            return "Válassz ki egy tektonon, ahol új testet szeretnél létrehozni!";
        case "fonálnövesztés":
            return "Válassz ki egy kezdő és egy vég tektonon!";
        default:
            return "Válassz ki a célpontokat a művelethez!";
    }
}



private void handleObjectSelection(Object obj) {
    selectedObjects.add(obj);
    
    // Check if we have enough objects for the current action
    if (hasEnoughObjectsForAction()) {
        boolean isValid = validateSelectedObjects();
        
        if (isValid) {
            performAction();
        } else {
            JOptionPane.showMessageDialog(this,
                "A kiválasztott objektumok nem érvényesek ehhez a művelethez!",
                "Érvénytelen művelet",
                JOptionPane.WARNING_MESSAGE);
            resetAction();
        }
    }
}

private boolean hasEnoughObjectsForAction() {
    if (currentAction == null) return false;
    
    switch (currentAction.toLowerCase()) {
        case "lépés":
        case "evés":
        case "spóraszórás":
        case "új test":
            return selectedObjects.size() >= 1;
        case "vágás":
            return selectedObjects.size() >= 2;
        case "fonálnövesztés":
            return selectedObjects.size() >= 2;
        default:
            return false;
    }
}

private boolean validateSelectedObjects() {
    Jatekos currentPlayer = jatek.getAktivJatekos();
    if (currentPlayer == null) return false;
    
    try {
        switch (currentAction.toLowerCase()) {
            case "lépés":
                if (selectedObjects.get(0) instanceof Rovar && 
                    selectedObjects.get(1) instanceof Tekton) {
                    Rovar rovar = (Rovar)selectedObjects.get(0);
                    Tekton target = (Tekton)selectedObjects.get(1);
                    return jatek.vanValid(currentPlayer, new String[]{
                        "lep", 
                        String.valueOf(target.getId()), 
                        String.valueOf(rovar.getId())
                    });
                }
                break;
                
            case "evés":
                if (selectedObjects.get(0) instanceof Rovar) {
                    Rovar rovar = (Rovar)selectedObjects.get(0);
                    return jatek.vanValid(currentPlayer, new String[]{
                        "eszik", 
                        String.valueOf(rovar.getId())
                    });
                }
                break;
                
            case "vágás":
                if (selectedObjects.get(0) instanceof GombaFonal && 
                    selectedObjects.get(1) instanceof Rovar) {
                    GombaFonal fonal = (GombaFonal)selectedObjects.get(0);
                    Rovar rovar = (Rovar)selectedObjects.get(1);
                    return jatek.vanValid(currentPlayer, new String[]{
                        "vagas", 
                        String.valueOf(fonal.getId()), 
                        String.valueOf(rovar.getId())
                    });
                }
                break;
                
            case "spóraszórás":
                if (selectedObjects.get(0) instanceof GombaTest) {
                    GombaTest test = (GombaTest)selectedObjects.get(0);
                    return jatek.vanValid(currentPlayer, new String[]{
                        "sporaszoras", 
                        String.valueOf(test.getId())
                    });
                }
                break;
                
            case "új test":
                if (selectedObjects.get(0) instanceof Tekton) {
                    Tekton tekton = (Tekton)selectedObjects.get(0);
                    return jatek.vanValid(currentPlayer, new String[]{
                        "ujTest", 
                        String.valueOf(tekton.getId()), 
                        currentPlayer.getNev()
                    });
                }
                break;
                
            case "fonálnövesztés":
                if (selectedObjects.get(0) instanceof Tekton && 
                    selectedObjects.get(1) instanceof Tekton) {
                    Tekton start = (Tekton)selectedObjects.get(0);
                    Tekton end = (Tekton)selectedObjects.get(1);
                    // Need to find a GombaTest owned by current player
                    for (GombaTest test : ((Gombasz)currentPlayer).getTestek()) {
                        if (jatek.vanValid(currentPlayer, new String[]{
                            "fonalnoveszt", 
                            String.valueOf(test.getId()), 
                            String.valueOf(start.getId()), 
                            String.valueOf(end.getId())
                        })) {
                            return true;
                        }
                    }
                }
                break;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
    
    return false;
}
private void performAction() {
    Jatekos currentPlayer = jatek.getAktivJatekos();
    if (currentPlayer == null) {
        resetAction();
        return;
    }

    try {
        String[] command = null;

        switch (currentAction.toLowerCase()) {
            case "lépés":
                Rovar rovar = (Rovar) selectedObjects.get(0);
                Tekton target = (Tekton) selectedObjects.get(1);
                command = new String[]{"lep", String.valueOf(target.getId()), String.valueOf(rovar.getId())};
                break;

            case "evés":
                Rovar rovarToEat = (Rovar) selectedObjects.get(0);
                command = new String[]{"eszik", String.valueOf(rovarToEat.getId())};
                break;

            case "vágás":
                GombaFonal fonal = (GombaFonal) selectedObjects.get(0);
                Rovar rovarToCut = (Rovar) selectedObjects.get(1);
                command = new String[]{"vagas", String.valueOf(fonal.getId()), String.valueOf(rovarToCut.getId())};
                break;

            case "spóraszórás":
                GombaTest test = (GombaTest) selectedObjects.get(0);
                command = new String[]{"sporaszoras", String.valueOf(test.getId())};
                break;

            case "új test":
                Tekton tekton = (Tekton) selectedObjects.get(0);
                command = new String[]{"ujTest", String.valueOf(tekton.getId()), currentPlayer.getNev()};
                break;

            case "fonálnövesztés":
                Tekton start = (Tekton) selectedObjects.get(0);
                Tekton end = (Tekton) selectedObjects.get(1);
                for (GombaTest t : ((Gombasz) currentPlayer).getTestek()) {
                    command = new String[]{
                        "fonalnoveszt",
                        String.valueOf(t.getId()),
                        String.valueOf(start.getId()),
                        String.valueOf(end.getId())
                    };
                    if (jatek.vanValid(currentPlayer, command)) {
                        ((Gombasz) currentPlayer).Kor("fonalnoveszt", jatek, command);
                        jatek.ujKor();
                        frissul();
                        resetAction();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Nincs érvényes test a fonálnövesztéshez!", "Hiba", JOptionPane.WARNING_MESSAGE);
                resetAction();
                return;
        }

        // Ha nem fonálnövesztés volt
        if (command != null && jatek.vanValid(currentPlayer, command)) {
            currentPlayer.Kor(currentAction.toLowerCase(), jatek, command);
            jatek.ujKor();
            frissul();
        } else {
            JOptionPane.showMessageDialog(this,
                "A művelet végrehajtása sikertelen vagy nem érvényes!",
                "Hiba",
                JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Hiba történt a művelet közben: " + ex.getMessage(),
            "Hiba",
            JOptionPane.ERROR_MESSAGE);
    } finally {
        resetAction();
    }
}
/*private void performAction() {
    Jatekos currentPlayer = jatek.getAktivJatekos();
    if (currentPlayer == null) {
        resetAction();
        return;
    }
    
    try {
        boolean success = false;
        String[] command = null;
        
        switch (currentAction.toLowerCase()) {
            case "lépés":
                Rovar rovar = (Rovar)selectedObjects.get(0);
                Tekton target = (Tekton)selectedObjects.get(1);
                command = new String[]{"lep", String.valueOf(target.getId()), String.valueOf(rovar.getId())};
                success = currentPlayer.Kor("lep", jatek, command);
                break;
                
            case "evés":
                Rovar rovarToEat = (Rovar)selectedObjects.get(0);
                command = new String[]{"eszik", String.valueOf(rovarToEat.getId())};
                success = currentPlayer.Kor("eszik", jatek, command);
                break;
                
            case "vágás":
                GombaFonal fonal = (GombaFonal)selectedObjects.get(0);
                Rovar rovarToCut = (Rovar)selectedObjects.get(1);
                command = new String[]{"vagas", String.valueOf(fonal.getId()), String.valueOf(rovarToCut.getId())};
                success = currentPlayer.Kor("vagas", jatek, command);
                break;
                
            case "spóraszórás":
                GombaTest test = (GombaTest)selectedObjects.get(0);
                command = new String[]{"sporaszoras", String.valueOf(test.getId())};
                success = currentPlayer.Kor("sporaszoras", jatek, command);
                break;
                
            case "új test":
                Tekton tekton = (Tekton)selectedObjects.get(0);
                command = new String[]{"ujTest", String.valueOf(tekton.getId()), currentPlayer.getNev()};
                success = currentPlayer.Kor("ujTest", jatek, command);
                break;
                
            case "fonálnövesztés":
                Tekton start = (Tekton)selectedObjects.get(0);
                Tekton end = (Tekton)selectedObjects.get(1);
                // Find a valid GombaTest for this player
                for (GombaTest t : ((Gombasz)currentPlayer).getTestek()) {
                    command = new String[]{
                        "fonalnoveszt", 
                        String.valueOf(t.getId()), 
                        String.valueOf(start.getId()), 
                        String.valueOf(end.getId())
                    };
                    if (jatek.vanValid(currentPlayer, command)) {
                        success = currentPlayer.Kor("fonalnoveszt", jatek, command);
                        break;
                    }
                }
                break;
        }
        
        if (success) {
            jatek.ujKor();
            frissul();
        } else {
            JOptionPane.showMessageDialog(this,
                "A művelet végrehajtása sikertelen!",
                "Hiba",
                JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Hiba történt a művelet végrehajtása közben: " + ex.getMessage(),
            "Hiba",
            JOptionPane.ERROR_MESSAGE);
    } finally {
        resetAction();
    }
}*/

private void resetAction() {
    currentAction = null;
    selectedObjects.clear();
    
    // Reset button highlights
    for (JButton btn : lepesGombok) {
        btn.setBackground(sotet);
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


