package fungorium;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JatekKep extends JFrame {

    private static final long serialVersionUID = 1L;
    private List<String> parancsok;
    private JatekLogika jatek;
    private Tekton aktivTekton;
    private int szelesseg;
    private int magassag;
    private JLabel[] pontLabels = new JLabel[8];
    private JButton[] lepesGombok = new JButton[3];
    private Palyakep palyaKep;
    private JLabel aktJatekosNev;
    private JLabel palyaCim;

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

        Color vilagos = new Color(239, 228, 176);
        Color sotet = new Color(74, 52, 31);

        getContentPane().setBackground(sotet);

        // Pálya cím felül
       
		palyaCim = new JLabel("PÁLYA", JLabel.CENTER);
		palyaCim.setFont(new Font("Serif", Font.BOLD, 30));
		// Szöveg színe sötét marad
		palyaCim.setForeground(sotet);  // A szöveg sötét marad
		palyaCim.setBackground(vilagos); // Háttér sárga
		palyaCim.setOpaque(true); // Háttér láthatósága
		palyaCim.setBounds(0, 0, 600, 60); // Elhelyezés
		add(palyaCim);

        // Pályakép elhelyezése fix mérettel
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

        aktJatekosNev = new JLabel(jatek.getAktivJatekos() != null ? jatek.getAktivJatekos().nev : "-");
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

        frissul();
    }

    public JatekLogika getJatek() {
        return jatek;
    }

    public Tekton getAktivTekton() {
        return aktivTekton;
    }

    void lep(String s) {
        // Léptetés logikája
    }
	void frissul() {
        // Aktuális játékos nevének frissítése
        if (jatek.getAktivJatekos() != null) {
            aktJatekosNev.setText(jatek.getAktivJatekos().getNev());
        }

        // Pontszámok frissítése
        List<Jatekos> jatekosok = jatek.getJatekosok();
        for (int i = 0; i < pontLabels.length; i++) {
            if (i < jatekosok.size()) {
                pontLabels[i].setText((i + 1) + ". " + jatekosok.get(i).getNev() + ": " + jatekosok.get(i).getpontok());
            } else {
                pontLabels[i].setText((i + 1) + ".");
            }
        }

        // Lépési lehetőségek frissítése
        List<String> lehetosegek = new ArrayList<String>();
        if (jatek.getAktivJatekos() != null) {
            if (jatek.getAktivJatekos().getTipus().equals("Rovarasz")) {
                Rovarasz rovarasz = (Rovarasz) jatek.getAktivJatekos();
                
                // Vágás
                for (Rovar rovar : rovarasz.getRovarok()) {
                    for (GombaFonal fonal : rovar.getHol().getFonalak()) {
                        if (jatek.vanValid(rovarasz, new String[]{"vagas", String.valueOf(fonal.getId()), String.valueOf(rovar.getId())})) {
                            lehetosegek.add("Vágás");
                            break;
                        }
                    }
                    if (!lehetosegek.isEmpty()) break;
                }
                
                // Lépés
                for (Rovar rovar : rovarasz.getRovarok()) {
                    for (Tekton szomszed : rovar.getHol().getSzomszedok()) {
                        if (jatek.vanValid(rovarasz, new String[]{"lep", String.valueOf(szomszed.getId()), String.valueOf(rovar.getId())})) {
                            lehetosegek.add("Lépés");
                            break;
                        }
                    }
                    if (lehetosegek.size() > 1) break;
                }
                
                // Evés
                for (Rovar rovar : rovarasz.getRovarok()) {
                    if (jatek.vanValid(rovarasz, new String[]{"eszik", String.valueOf(rovar.getId())})) {
                        lehetosegek.add("Evés");
                        break;
                    }
                }
            } else { // Gombász
                Gombasz gombasz = (Gombasz) jatek.getAktivJatekos();
                
                // Spóraszórás
                for (GombaTest test : gombasz.getTestek()) {
                    if (jatek.vanValid(gombasz, new String[]{"sporaszoras", String.valueOf(test.getId())})) {
                        lehetosegek.add("Spóraszórás");
                        break;
                    }
                }
                
                // Új test
                for (Tekton tekton : jatek.getJatekter()) {
                    if (jatek.vanValid(gombasz, new String[]{"ujTest", String.valueOf(tekton.getId()), gombasz.getNev()})) {
                        lehetosegek.add("Új test");
                        break;
                    }
                }
                
                // Fonálnövesztés
                for (GombaFonal fonal : gombasz.getFonalak()) {
                    for (Tekton t1 : fonal.getKapcsoltTektonok()) {
                        for (Tekton t2 : t1.getSzomszedok()) {
                            if (jatek.vanValid(gombasz, new String[]{"fonalnoveszt", 
                                String.valueOf(gombasz.getTestek().get(0).getId()), 
                                String.valueOf(t1.getId()), 
                                String.valueOf(t2.getId())})) {
                                lehetosegek.add("Fonálnövesztés");
                                break;
                            }
                        }
                        if (!lehetosegek.isEmpty() && lehetosegek.size() > 2) break;
                    }
                    if (!lehetosegek.isEmpty() && lehetosegek.size() > 2) break;
                }
            }
        }

        // Gombok frissítése
        for (int i = 0; i < lepesGombok.length; i++) {
            if (i < lehetosegek.size()) {
                lepesGombok[i].setText(lehetosegek.get(i));
                lepesGombok[i].setEnabled(true);
            } else {
                lepesGombok[i].setText("");
                lepesGombok[i].setEnabled(false);
            }
        }
    }
}