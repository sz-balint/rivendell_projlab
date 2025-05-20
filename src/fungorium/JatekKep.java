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
    private List<Object> selectedObjects = new ArrayList<>();
    private String currentAction = null;
    private int currentStep = 0;
    private String[] actionSteps = null;

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


        palyaKep.setObjektumKivalasztasListener(obj -> handleObjectSelection(obj));//help

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

        palyaKep.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentAction != null) {
                Object clickedObject = palyaKep.getObjectAt(e.getX(), e.getY());
                
                if (clickedObject != null) {
                    System.out.println("[DEBUG] Object selected: " + 
                        clickedObject.getClass().getSimpleName() + 
                        " (Step " + (currentStep + 1) + " of " + actionSteps.length + ")");

                    // EZ A FONTOS: KISZERVEZETT MŰVELET
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

    // Objektum típusa alapján neve vagy ID megjelenítése
    String objDesc = obj instanceof Rovar ? "Rovar #" + ((Rovar)obj).getId() :
                     obj instanceof Tekton ? "Tekton #" + ((Tekton)obj).getId() :
                     obj instanceof GombaFonal ? "Gombafonal #" + ((GombaFonal)obj).getId() :
                     obj instanceof GombaTest ? "Gombatest #" + ((GombaTest)obj).getId() :
                     "Ismeretlen objektum";

    JOptionPane.showMessageDialog(this,
        objDesc + " kiválasztva.\n" + getNextStepHint(),
        "Kiválasztás",
        JOptionPane.INFORMATION_MESSAGE);

    currentStep++; // 

    if (currentStep < actionSteps.length - 1) {
        currentStep++;
        promptNextStep(); // következő lépés promptolása
    } else {
        currentStep++;
        validateAndExecuteAction(); // utolsó után hajtódik végre
    }
}

private String getNextStepHint() {
    if (currentAction == null) return "";

    switch (currentAction.toLowerCase()) {
        case "lépés":
            return selectedObjects.size() == 1 ? "Most válassz cél Tekton-t!" : "";
        case "vágás":
            return selectedObjects.size() == 1 ? "Most válassz rovart, aki végrehajtja a vágást!" : "";
        case "fonálnövesztés":
            return selectedObjects.size() == 1 ? "Most válassz cél Tekton-t!" : "";
        case "új test":
        case "spóraszórás":
        case "evés":
            return "Már kiválasztottad a szükséges objektumot.";
        default:
            return "";
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

        if (currentPlayer instanceof Rovarasz rovarasz) {
            switch (currentAction.toLowerCase()) {
                case "lépés" -> {
                    Rovar rovar = (Rovar) selectedObjects.get(0);
                    Tekton target = (Tekton) selectedObjects.get(1);
                    command = new String[]{"lep", String.valueOf(target.getId()), String.valueOf(rovar.getId())};
                }
                case "evés" -> {
                    Rovar rovarToEat = (Rovar) selectedObjects.get(0);
                    command = new String[]{"eszik", String.valueOf(rovarToEat.getId())};
                }
                case "vágás" -> {
                    GombaFonal fonal = (GombaFonal) selectedObjects.get(0);
                    Rovar rovarToCut = (Rovar) selectedObjects.get(1);
                    command = new String[]{"vagas", String.valueOf(fonal.getId()), String.valueOf(rovarToCut.getId())};
                }
            }
        } else if (currentPlayer instanceof Gombasz gombasz) {
            switch (currentAction.toLowerCase()) {
                case "spóraszórás" -> {
                    GombaTest test = (GombaTest) selectedObjects.get(0);
                    command = new String[]{"sporaszoras", String.valueOf(test.getId())};
                }
                case "új test" -> {
                    Tekton tekton = (Tekton) selectedObjects.get(0);
                    command = new String[]{"ujTest", String.valueOf(tekton.getId()), gombasz.getNev()};
                }
                case "fonálnövesztés" -> {
                    Tekton start = (Tekton) selectedObjects.get(0);
                    Tekton end = (Tekton) selectedObjects.get(1);
                    for (GombaTest t : gombasz.getTestek()) {
                        command = new String[]{
                            "fonalnoveszt",
                            String.valueOf(t.getId()),
                            String.valueOf(start.getId()),
                            String.valueOf(end.getId())
                        };
                        if (jatek.vanValid(currentPlayer, command)) {
                            gombasz.Kor("fonalnoveszt", jatek, command);
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
            }
        }

        // Általános érvényesség-ellenőrzés és művelet végrehajtása
        if (command != null && jatek.vanValid(currentPlayer, command)) {
            currentPlayer.Kor(currentAction.toLowerCase(), jatek, command);
            palyaKep.drawingPanel.repaint();//
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





private void setupActionButton(JButton button, String action) {
        button.setText(action);
        button.setEnabled(true);
        button.setBackground(sotet);
        
        // Remove old action listeners
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
        
        button.addActionListener(e -> {
            System.out.println("[DEBUG] Action button pressed: " + action);
            startActionSequence(action);
        });
    }

    private void startActionSequence(String action) {
    System.out.println("[DEBUG] Starting action sequence for: " + action);
    currentAction = action;
    currentStep = 0;
    selectedObjects.clear();
    
    // Define the steps needed for each action
    switch (action.toLowerCase()) {
        case "lépés":
            actionSteps = new String[]{"Válassz egy rovart", "Válassz egy cél tekton"};
            break;
        case "evés":
            actionSteps = new String[]{"Válassz egy rovart, amit meg akarsz enni"};
            break;
        case "vágás":
            actionSteps = new String[]{"Válassz egy gombafonalat", "Válassz egy rovart a vágáshoz"};
            break;
        case "spóraszórás":
            actionSteps = new String[]{"Válassz egy gombatestet"};
            break;
        case "új test":
            actionSteps = new String[]{"Válassz egy tekton, ahol új testet szeretnél"};
            break;
        case "fonálnövesztés":
            actionSteps = new String[]{"Válassz egy kezdő tekton", "Válassz egy cél tekton"};
            break;
        default:
            actionSteps = new String[]{};
    }
    
    // Highlight the active button
    for (JButton btn : lepesGombok) {
        btn.setBackground(sotet);
        if (btn.getText().equals(action)) {
            btn.setBackground(Color.YELLOW);
        }
    }
    
    promptNextStep(); // Azonnal promptoljuk az első lépést
}

    private void promptNextStep() {
        if (currentStep < actionSteps.length) {
            String message = actionSteps[currentStep] + 
                             "\n(Eddig kiválasztva: " + getSelectedObjectsDescription() + ")";
            
            System.out.println("[DEBUG] Prompting step " + (currentStep+1) + ": " + message);
            
            JOptionPane.showMessageDialog(this, 
                message, 
                currentAction + " - " + (currentStep+1) + ". lépés", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("[DEBUG] All steps completed, validating action...");
            validateAndExecuteAction();
        }
    }

    private String getSelectedObjectsDescription() {
        if (selectedObjects.isEmpty()) return "nincs";
        
        StringBuilder sb = new StringBuilder();
        for (Object obj : selectedObjects) {
            if (sb.length() > 0) sb.append(", ");
            
            if (obj instanceof Rovar) {
                sb.append("Rovar#").append(((Rovar)obj).getId());
            } else if (obj instanceof Tekton) {
                sb.append("Tekton#").append(((Tekton)obj).getId());
            } else if (obj instanceof GombaFonal) {
                sb.append("GombaFonal#").append(((GombaFonal)obj).getId());
            } else if (obj instanceof GombaTest) {
                sb.append("GombaTest#").append(((GombaTest)obj).getId());
            } else {
                sb.append("Ismeretlen objektum");
            }
        }
        return sb.toString();
    }

    // Modified mouse click handler


    private String getObjectDescription(Object obj) {
        if (obj instanceof Rovar) {
            return "Rovar #" + ((Rovar)obj).getId();
        } else if (obj instanceof Tekton) {
            return "Tekton #" + ((Tekton)obj).getId();
        } else if (obj instanceof GombaFonal) {
            return "Gombafonal #" + ((GombaFonal)obj).getId();
        } else if (obj instanceof GombaTest) {
            return "Gombatest #" + ((GombaTest)obj).getId();
        }
        return "Ismeretlen objektum";
    }

    private String getNextStepInstructions() {
        if (currentStep >= actionSteps.length) {
            return "Minden szükséges objektum kiválasztva. A művelet végrehajtásra kerül.";
        }
        return "Következő lépés: " + actionSteps[currentStep];
    }

    private void validateAndExecuteAction() {
        System.out.println("[DEBUG] Validating action with objects: " + getSelectedObjectsDescription());
        
        Jatekos currentPlayer = jatek.getAktivJatekos();
        if (currentPlayer == null) {
            System.out.println("[DEBUG] No active player, aborting");
            resetAction();
            return;
        }

        try {
            String[] command = buildCommandFromSelection();
            
            if (command != null && jatek.vanValid(currentPlayer, command)) {
                System.out.println("[DEBUG] Executing command: " + String.join(" ", command));
                currentPlayer.Kor(currentAction.toLowerCase(), jatek, command);
                palyaKep.drawingPanel.repaint();
                jatek.ujKor();
                frissul();
            } else {
                System.out.println("[DEBUG] Invalid command or validation failed");
                JOptionPane.showMessageDialog(this,
                    "A művelet végrehajtása sikertelen vagy nem érvényes!",
                    "Hiba",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println("[DEBUG] Error during execution: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Hiba történt a művelet közben: " + ex.getMessage(),
                "Hiba",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            resetAction();
        }
    }

    private String[] buildCommandFromSelection() {
        if (currentAction == null || selectedObjects.isEmpty()) {
            return null;
        }

        Jatekos currentPlayer = jatek.getAktivJatekos();
        if (currentPlayer == null) return null;

        try {
            switch (currentAction.toLowerCase()) {
                case "lépés":
                    if (selectedObjects.size() >= 2 && 
                        selectedObjects.get(0) instanceof Rovar && 
                        selectedObjects.get(1) instanceof Tekton) {
                        Rovar rovar = (Rovar)selectedObjects.get(0);
                        Tekton target = (Tekton)selectedObjects.get(1);
                        return new String[]{"lep", String.valueOf(target.getId()), String.valueOf(rovar.getId())};
                    }
                    break;
                    
                case "evés":
                    if (selectedObjects.get(0) instanceof Rovar) {
                        Rovar rovar = (Rovar)selectedObjects.get(0);
                        return new String[]{"eszik", String.valueOf(rovar.getId())};
                    }
                    break;
                    
                case "vágás":
                    if (selectedObjects.size() >= 2 && 
                        selectedObjects.get(0) instanceof GombaFonal && 
                        selectedObjects.get(1) instanceof Rovar) {
                        GombaFonal fonal = (GombaFonal)selectedObjects.get(0);
                        Rovar rovar = (Rovar)selectedObjects.get(1);
                        return new String[]{"vagas", String.valueOf(fonal.getId()), String.valueOf(rovar.getId())};
                    }
                    break;
                    
                case "spóraszórás":
                    if (selectedObjects.get(0) instanceof GombaTest) {
                        GombaTest test = (GombaTest)selectedObjects.get(0);
                        return new String[]{"sporaszoras", String.valueOf(test.getId())};
                    }
                    break;
                    
                case "új test":
                    if (selectedObjects.get(0) instanceof Tekton) {
                        Tekton tekton = (Tekton)selectedObjects.get(0);
                        return new String[]{"ujTest", String.valueOf(tekton.getId()), currentPlayer.getNev()};
                    }
                    break;
                    
                case "fonálnövesztés":
                    if (selectedObjects.size() >= 2 && 
                        selectedObjects.get(0) instanceof Tekton && 
                        selectedObjects.get(1) instanceof Tekton &&
                        currentPlayer instanceof Gombasz) {
                        
                        Tekton start = (Tekton)selectedObjects.get(0);
                        Tekton end = (Tekton)selectedObjects.get(1);
                        
                        // Find a valid GombaTest for this player
                        for (GombaTest test : ((Gombasz)currentPlayer).getTestek()) {
                            String[] testCmd = new String[]{
                                "fonalnoveszt", 
                                String.valueOf(test.getId()), 
                                String.valueOf(start.getId()), 
                                String.valueOf(end.getId())
                            };
                            if (jatek.vanValid(currentPlayer, testCmd)) {
                                return testCmd;
                            }
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("[DEBUG] Error building command: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    private void resetAction() {
        System.out.println("[DEBUG] Resetting action");
        currentAction = null;
        currentStep = 0;
        actionSteps = null;
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
            lehetosegek.add("Vágás");
            lehetosegek.add("Lépés");
            lehetosegek.add("Evés");
        } else if (aktiv.getTipus().equals("Gombasz")) {
            lehetosegek.add("Új test");
            lehetosegek.add("Spóraszórás");
            lehetosegek.add("Fonálnövesztés");
        }
    }

    // Gombok nullázása
    for (int i = 0; i < lepesGombok.length; i++) {
        lepesGombok[i].setText("");
        lepesGombok[i].setEnabled(false);
        lepesGombok[i].setBackground(sotet);

        // Régi ActionListenerek eltávolítása
        for (ActionListener al : lepesGombok[i].getActionListeners()) {
            lepesGombok[i].removeActionListener(al);
        }
    }

    // Helyes gomblogika beállítása
    for (int i = 0; i < Math.min(lehetosegek.size(), lepesGombok.length); i++) {
        String akcio = lehetosegek.get(i);
        setupActionButton(lepesGombok[i], akcio); // <- itt történik a jó viselkedés beállítása
    }
}

}


