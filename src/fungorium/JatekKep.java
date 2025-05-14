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



public class JatekKep extends JFrame{

	private static final long serialVersionUID = 1L;
	private List<String> parancsok;
	private JatekLogika jatek;
	private Tekton aktivTekton;
	private int szelesseg;
	private int magassag;
	private JLabel pont1 = new JLabel();
	private JLabel pont2 = new JLabel();
	private JLabel pont3 = new JLabel();
	private JLabel pont4 = new JLabel();
	private JLabel pont5 = new JLabel();
	private JLabel pont6 = new JLabel();
	private JLabel pont7 = new JLabel();
	private JLabel pont8 = new JLabel();
	private JButton lep1 = new JButton();
	private JButton lep2 = new JButton();
	private JButton lep3 = new JButton();
	
	public JatekKep(JatekLogika jat) {
		super("Jaték");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		jatek = jat;
		szelesseg = 1280;
		magassag = 700;
		setSize(szelesseg, magassag);
		setLayout(null);
		setResizable(false);
		
		Color vilagos = new Color(239,228,176);
		Color sotet = new Color(74,52,31);
		
		getContentPane().setBackground(sotet);
		
		JButton save = new JButton();
		
		save = new JButton();
		save.setText("SAVE");
		save.setFont(new Font("Serif", Font.BOLD, 25));
		save.setForeground(vilagos);
		save.setBackground(sotet);
		save.setFocusPainted(false);
		save.setBorderPainted(true);
		save.setBorder(BorderFactory.createLineBorder(vilagos,4));
		save.setSize(szelesseg * 1 / 8 - 30, 50);
		save.setLocation(szelesseg * 13 / 16 - 60, magassag - 100);
		save.setContentAreaFilled(true);
		
		Fajlkezelo fajlkezelo = new Fajlkezelo();
		save.addActionListener(e -> {try {
			fajlkezelo.save(jatek, "jatek");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		
		add(save);
		
		JButton kilepes = new JButton();
		
		kilepes = new JButton();
		kilepes.setText("Kilépés");
		kilepes.setFont(new Font("Serif", Font.BOLD, 25));
		kilepes.setForeground(vilagos);
		kilepes.setBackground(sotet);
		kilepes.setFocusPainted(false);
		kilepes.setBorderPainted(true);
		kilepes.setBorder(BorderFactory.createLineBorder(vilagos,4));
		kilepes.setSize(szelesseg * 1 / 8 - 30, 50);
		kilepes.setLocation(szelesseg * 15 / 16 - 80, magassag - 100);
		kilepes.setContentAreaFilled(true);
		kilepes.addActionListener(e -> {System.exit(0);});
		
		add(kilepes);


		
		JLabel palya = new JLabel();
		
		palya.setText("Pálya");
		palya.setFont(new Font("Serif", Font.BOLD, 40));
		palya.setLocation(szelesseg * 3 / 8 - 50, 5);
		palya.setSize(100,50);
		palya.setForeground(sotet);
		
		add(palya);	

		JLabel menu = new JLabel();
		
		menu.setText("Menü");
		menu.setFont(new Font("Serif", Font.BOLD, 40));
		menu.setLocation(szelesseg * 7 / 8 - 50, 5);
		menu.setSize(100,50);
		menu.setForeground(vilagos);
		
		add(menu);	
		
		JLabel aktjatekos = new JLabel();
		
		aktjatekos.setText("Aktuális játékos:");
		aktjatekos.setFont(new Font("Serif", Font.BOLD, 35));
		aktjatekos.setLocation(szelesseg * 6 / 8 + 10, 80);
		aktjatekos.setSize(400,50);
		aktjatekos.setForeground(vilagos);
		
		add(aktjatekos);	
		
		JLabel aktjatekosnev = new JLabel();
		
		aktjatekosnev.setText(jatek.getAktivJatekos() != null ? jatek.getAktivJatekos().nev : "-");
		aktjatekosnev.setFont(new Font("Serif", Font.BOLD, 25));
		aktjatekosnev.setLocation(szelesseg * 6 / 8 + 10, 115);
		aktjatekosnev.setSize(400,50);
		aktjatekosnev.setForeground(vilagos);
		
		add(aktjatekosnev);
		
		JLabel pontszamok = new JLabel();
		
		pontszamok.setText("Pontszámok:");
		pontszamok.setFont(new Font("Serif", Font.BOLD, 35));
		pontszamok.setLocation(szelesseg * 6 / 8 + 10, 150);
		pontszamok.setSize(400,50);
		pontszamok.setForeground(vilagos);
		
		add(pontszamok);
		
		
		
		pont1.setText(jatek.getJatekosok().size() >= 1 ? "1. " + jatek.getJatekosok().get(0).getpontok() : "1.");
		pont1.setFont(new Font("Serif", Font.BOLD, 20));
		pont1.setLocation(szelesseg * 6 / 8 + 10, 185);
		pont1.setSize(400,50);
		pont1.setForeground(vilagos);
		
		add(pont1);
		
		pont2.setText(jatek.getJatekosok().size() >= 2 ? "2. " + jatek.getJatekosok().get(1).getpontok() : "2.");
		pont2.setFont(new Font("Serif", Font.BOLD, 20));
		pont2.setLocation(szelesseg * 6 / 8 + 10, 210);
		pont2.setSize(400,50);
		pont2.setForeground(vilagos);
		
		add(pont2);
		
		pont3.setText(jatek.getJatekosok().size() >= 3 ? "3. " + jatek.getJatekosok().get(2).getpontok() : "3.");
		pont3.setFont(new Font("Serif", Font.BOLD, 20));
		pont3.setLocation(szelesseg * 6 / 8 + 10, 235);
		pont3.setSize(400,50);
		pont3.setForeground(vilagos);
		
		add(pont3);
		
		pont4.setText(jatek.getJatekosok().size() >= 4 ? "4. " + jatek.getJatekosok().get(3).getpontok() : "4.");
		pont4.setFont(new Font("Serif", Font.BOLD, 20));
		pont4.setLocation(szelesseg * 6 / 8 + 10, 260);
		pont4.setSize(400,50);
		pont4.setForeground(vilagos);
		
		add(pont4);
		
		pont5.setText(jatek.getJatekosok().size() >= 5 ? "5. " + jatek.getJatekosok().get(4).getpontok() : "5.");
		pont5.setFont(new Font("Serif", Font.BOLD, 20));
		pont5.setLocation(szelesseg * 7 / 8 + 10, 185);
		pont5.setSize(400,50);
		pont5.setForeground(vilagos);
		
		add(pont5);
		
		pont6.setText(jatek.getJatekosok().size() >= 6 ? "6. " + jatek.getJatekosok().get(5).getpontok() : "6.");
		pont6.setFont(new Font("Serif", Font.BOLD, 20));
		pont6.setLocation(szelesseg * 7 / 8 + 10, 210);
		pont6.setSize(400,50);
		pont6.setForeground(vilagos);
		
		add(pont6);
		
		pont7.setText(jatek.getJatekosok().size() >= 7 ? "7. " + jatek.getJatekosok().get(6).getpontok() : "7.");
		pont7.setFont(new Font("Serif", Font.BOLD, 20));
		pont7.setLocation(szelesseg * 7 / 8 + 10, 235);
		pont7.setSize(400,50);
		pont7.setForeground(vilagos);
		
		add(pont7);
		
		pont8.setText(jatek.getJatekosok().size() >= 8 ? "8. " + jatek.getJatekosok().get(7).getpontok() : "8.");
		pont8.setFont(new Font("Serif", Font.BOLD, 20));
		pont8.setLocation(szelesseg * 7 / 8 + 10, 260);
		pont8.setSize(400,50);
		pont8.setForeground(vilagos);
		
		add(pont8);
		
		
		
		JLabel lepesek = new JLabel();
		
		lepesek.setText("Lépések:");
		lepesek.setFont(new Font("Serif", Font.BOLD, 35));
		lepesek.setLocation(szelesseg * 6 / 8 + 10, 300);
		lepesek.setSize(400,50);
		lepesek.setForeground(vilagos);
		
		add(lepesek);
		
		lep1 = new JButton();
		
		lep1.setText("");
		lep1.setFont(new Font("Serif", Font.BOLD, 25));
		lep1.setForeground(vilagos);
		lep1.setBackground(sotet);
		lep1.setFocusPainted(false);
		lep1.setBorderPainted(true);
		lep1.setBorder(BorderFactory.createLineBorder(vilagos,4));
		lep1.setSize(szelesseg * 1 / 4 - 30, 40);
		lep1.setLocation(szelesseg * 6 / 8 + 10, 350);
		lep1.setContentAreaFilled(true);
		lep1.setEnabled(false);
		
		add(lep1);
		
		lep2 = new JButton();
		
		lep2.setText("");
		lep2.setFont(new Font("Serif", Font.BOLD, 25));
		lep2.setForeground(vilagos);
		lep2.setBackground(sotet);
		lep2.setFocusPainted(false);
		lep2.setBorderPainted(true);
		lep2.setBorder(BorderFactory.createLineBorder(vilagos,4));
		lep2.setSize(szelesseg * 1 / 4 - 30, 40);
		lep2.setLocation(szelesseg * 6 / 8 + 10, 400);
		lep2.setContentAreaFilled(true);
		lep2.setEnabled(false);
		
		add(lep2);
		
		lep3 = new JButton();
		
		lep3.setText("");
		lep3.setFont(new Font("Serif", Font.BOLD, 25));
		lep3.setForeground(vilagos);
		lep3.setBackground(sotet);
		lep3.setFocusPainted(false);
		lep3.setBorderPainted(true);
		lep3.setBorder(BorderFactory.createLineBorder(vilagos,4));
		lep3.setSize(szelesseg * 1 / 4 - 30, 40);
		lep3.setLocation(szelesseg * 6 / 8 + 10, 450);
		lep3.setContentAreaFilled(true);
		lep3.setEnabled(false);
		
		add(lep3);
		
		
		
		JButton sotetcsik= new JButton();
		
		sotetcsik.setSize(szelesseg * 3 / 4, 4);
		sotetcsik.setLocation(0,56);
		sotetcsik.setFocusable(false);
		sotetcsik.setBackground(sotet);
		sotetcsik.setEnabled(false);
		sotetcsik.setBorder(null);
		
		add(sotetcsik);
		
		JButton vilagoscsik= new JButton();
		
		vilagoscsik.setSize(szelesseg * 1 / 4, 4);
		vilagoscsik.setLocation(szelesseg * 3 / 4, 56);
		vilagoscsik.setFocusable(false);
		vilagoscsik.setBackground(vilagos);
		vilagoscsik.setEnabled(false);
		vilagoscsik.setBorder(null);
		
		add(vilagoscsik);
		
		JButton palyateglalap= new JButton();
		
		palyateglalap.setSize(szelesseg * 3 / 4, magassag);
		palyateglalap.setLocation(0,0);
		palyateglalap.setFocusable(false);
		palyateglalap.setBackground(vilagos);
		palyateglalap.setEnabled(false);
		palyateglalap.setBorder(null);
		
		add(palyateglalap);
	}
	
	public JatekLogika getJatek() {
		return jatek;
	}
	
	public Tekton getAktivTekton() {
		return aktivTekton;
	}
	
	void lep(String s) {
		
	}
	
	void frissul() {
		if (jatek.getJatekosok() != null) {
			pont1.setText(jatek.getJatekosok().size() >= 2 ? "1. " + jatek.getJatekosok().get(0).getpontok() : "1.");
			pont2.setText(jatek.getJatekosok().size() >= 2 ? "2. " + jatek.getJatekosok().get(1).getpontok() : "2.");
			pont3.setText(jatek.getJatekosok().size() >= 3 ? "3. " + jatek.getJatekosok().get(2).getpontok() : "3.");
			pont4.setText(jatek.getJatekosok().size() >= 4 ? "4. " + jatek.getJatekosok().get(3).getpontok() : "4.");
			pont5.setText(jatek.getJatekosok().size() >= 5 ? "5. " + jatek.getJatekosok().get(4).getpontok() : "5.");
			pont6.setText(jatek.getJatekosok().size() >= 6 ? "6. " + jatek.getJatekosok().get(5).getpontok() : "6.");
			pont7.setText(jatek.getJatekosok().size() >= 7 ? "7. " + jatek.getJatekosok().get(6).getpontok() : "7.");
			pont8.setText(jatek.getJatekosok().size() >= 8 ? "8. " + jatek.getJatekosok().get(7).getpontok() : "8.");
		}
		List<String> lehetosegek = new ArrayList<String>();
		int lehszam = lehetosegek.size();
		if (jatek.getAktivJatekos() != null)
			//Vágás, lép, eszik
			if (jatek.getAktivJatekos().getTipus() == "Rovarasz") {
				Rovarasz rovarasz = null;
				for (Tekton tekton: jatek.getJatekter()) {
					for (Rovar rovar: tekton.getRovarok()) 
						if (rovar.getKie() == jatek.getAktivJatekos()) {
							rovarasz = rovar.getKie();
							break;
						}
					if (rovarasz != null) break;
				}
				lehszam = lehetosegek.size();
				
				for (Rovar rovar: rovarasz.getRovarok()) {
					for (GombaFonal fonal: rovar.getHol().getFonalak())
						if (jatek.vanValid(rovarasz, new String[] {"vagas", Integer.toString(fonal.getId()), Integer.toString(rovar.getId())})) {
							lehetosegek.add("Vágás");
						}
					if (lehetosegek.size() > lehszam) break;
				}
				lehszam = lehetosegek.size();
				
				for (Rovar rovar: rovarasz.getRovarok()) {
					for (Tekton tekton: rovar.getHol().getSzomszedok())
						if (jatek.vanValid(rovarasz, new String[] {"lep", Integer.toString(tekton.getId()), Integer.toString(rovar.getId())})) {
							lehetosegek.add("Vágás");
						}
					if (lehetosegek.size() > lehszam) break;
				}
				
				for (Rovar rovar: rovarasz.getRovarok()) {
					if (jatek.vanValid(rovarasz, new String[] {"eszik", Integer.toString(rovar.getId())})) {
						lehetosegek.add("Evés");
						break;
					}
				}
			}
		
			//Spóraszórás, új test, fonálnöveszt
			else {
				Gombasz gombasz = null;
				for (Tekton tekton: jatek.getJatekter()) {
					if (tekton.getGombaTest().kie == jatek.getAktivJatekos()) {
						gombasz = tekton.getGombaTest().kie;
						break;
					}
				}

				for (GombaTest test: gombasz.getTestek())
					if (jatek.vanValid(gombasz, new String[] {"sporaszoras", Integer.toString(test.getId())})) {
						lehetosegek.add("Spóraszórás");
						break;
					}
				
				for (Tekton tekton: jatek.getJatekter())
					if (jatek.vanValid(gombasz, new String[] {"ujTest", Integer.toString(tekton.getId()), gombasz.getNev() })) {
						lehetosegek.add("Spóraszórás");
						break;
					}

				lehszam = lehetosegek.size();
				
				for (GombaFonal fonal: gombasz.getFonalak()) {
					for (Tekton tekton: fonal.getKapcsoltTektonok()) {
						for (Tekton tekton2: tekton.getSzomszedok())
							if (jatek.vanValid(gombasz, new String[] {"fonalnoveszt", Integer.toString(gombasz.getTestek().get(0).getId()), Integer.toString(tekton.getId()), Integer.toString(tekton2.getId()) })) {
								lehetosegek.add("Spóraszórás");
								break;
							}
						if (lehetosegek.size() > lehszam) break;
					}
					if (lehetosegek.size() > lehszam) break;
				}
				
			}
		
			lehszam = lehetosegek.size();
			lep1.setEnabled(lehszam >= 1);
			lep1.setText(lehszam >= 1 ? lehetosegek.get(0) : "");
			lep2.setEnabled(lehszam >= 2);
			lep2.setText(lehszam >= 2 ? lehetosegek.get(1) : "");
			lep3.setEnabled(lehszam >= 3);
			lep3.setText(lehszam >= 3 ? lehetosegek.get(2) : "");
	}
	
	
	
}
