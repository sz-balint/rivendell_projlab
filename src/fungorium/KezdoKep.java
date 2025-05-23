package fungorium;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class KezdoKep extends JFrame {
	//Szinek
	private Color hatterszin = new Color(245, 235, 200);
    private Color szovegszin = new Color(80, 50, 20);
    //A jatekoslista amit majd atadunk mindenkinek
    private ArrayList<Jatekos> jatekosok = new ArrayList<>();
	
    public KezdoKep() {
        // Ablak beállítása
        setTitle("FUNGORIUM Rivendell");	//Cim
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(550, 350);	//Meret
        setLocationRelativeTo(null); // Ablak kozepre igazitasa   
        /*
        jatekosok.add(new Gombasz("Játékos1", 0, "Gombasz",Color.RED)); // Gombasz
        jatekosok.add(new Rovarasz("Játékos2", 0, "Rovarasz",Color.BLUE)); // Rovarasz
        jatekosok.add(new Gombasz("Játékos3", 0, "Gombasz",Color.GREEN)); // Gombasz
        jatekosok.add(new Rovarasz("Játékos4", 0, "Rovarasz",Color.YELLOW)); // Rovarasz
         */
        // Fo panel letrehozasa
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(hatterszin);

        // Címek
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(hatterszin);
        
        //Focim
        JLabel titleLabel = new JLabel("FUNGORIUM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(szovegszin); 
        
        //Alcim
        JLabel subtitleLabel = new JLabel("Rivendell", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        subtitleLabel.setForeground(szovegszin); 

        //Cimek hozzaadasa
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Gombok panel létrehozása
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(hatterszin);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100)); // Térköz a gombok körül

        ArrayList<JButton> gombok= new ArrayList();

        // Gombok letrehozasa
        JButton startGameButton = new JButton("Játék kezdés");
        gombok.add(startGameButton);
        JButton loginButton = new JButton("Játékos Login");
        gombok.add(loginButton);
        JButton loadButton = new JButton("Load");
        gombok.add(loadButton);
        JButton exitButton = new JButton("Kilépés");
        gombok.add(exitButton);
        
        
        // Gombok stilusanak beallitasa 
        Dimension buttonSize = new Dimension(250, 40); // Gombok mérete
        for (JButton g : gombok) {
        	g.setMaximumSize(buttonSize); //Meret
        	g.setAlignmentX(Component.CENTER_ALIGNMENT);	//Igazitas
        	g.setFont(new Font("Serif", Font.BOLD, 16));	//betutipus
            g.setBackground(hatterszin);	//hatterszin
            g.setForeground(szovegszin);	//szövegszin
            g.setFocusPainted(false);	
            g.setBorder(BorderFactory.createLineBorder(szovegszin, 2));
        }


        // Gombok hozzaadasa a panelhez nemi terkozzel
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(startGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        //Díszek:)

        //Jobb es bal oldali diszek
        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 58);

        // Baloldal
        JPanel leftEmojiPanel = new JPanel();
        leftEmojiPanel.setBackground(hatterszin);
        leftEmojiPanel.setLayout(new BoxLayout(leftEmojiPanel, BoxLayout.Y_AXIS));
        leftEmojiPanel.add(Box.createVerticalGlue());

        //balbogar
        JLabel leftBug = new JLabel("🐞");
        leftBug.setFont(emojiFont);
        //leftBug.setForeground(new Color(71, 53, 34));
        leftBug.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftBug.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        //balgomba
        JLabel leftMushroom = new JLabel("🍄");
        leftMushroom.setFont(emojiFont);
        leftMushroom.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftMushroom.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        //hozzaadas
        leftEmojiPanel.add(leftBug);
        leftEmojiPanel.add(Box.createVerticalStrut(50));
        leftEmojiPanel.add(leftMushroom);
        leftEmojiPanel.add(Box.createVerticalGlue());

        JPanel leftWrapper = new JPanel(new BorderLayout());
        leftWrapper.setBackground(hatterszin);
        leftWrapper.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // Push right toward center
        leftWrapper.add(leftEmojiPanel, BorderLayout.CENTER);


        // Jobb oldali panel
        JPanel rightEmojiPanel = new JPanel();
        rightEmojiPanel.setBackground(hatterszin);
        rightEmojiPanel.setLayout(new BoxLayout(rightEmojiPanel, BoxLayout.Y_AXIS));
        rightEmojiPanel.add(Box.createVerticalGlue());

        //Jobbgomba
        JLabel rightMushroom = new JLabel("🍄");
        rightMushroom.setFont(emojiFont);
        rightMushroom.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightMushroom.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        //jobbbogar
        JLabel rightBug = new JLabel("🐞");
        rightBug.setFont(emojiFont);
        rightBug.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightBug.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        //Hozzaadas
        rightEmojiPanel.add(rightMushroom);
        rightEmojiPanel.add(Box.createVerticalStrut(50));
        rightEmojiPanel.add(rightBug);
        rightEmojiPanel.add(Box.createVerticalGlue());

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(hatterszin);
        rightWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30)); // Push left toward center
        rightWrapper.add(rightEmojiPanel, BorderLayout.CENTER);

        // Az egeszhez hozza
        add(leftWrapper, BorderLayout.WEST);
        add(rightWrapper, BorderLayout.EAST);

        //A gombok az egeszhez adasa
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

//KOMMENT
        //KEZDOKEP: 
        //van egy játékos lista ebben az osztályban eltárolva
        //játékos objektumokat tárol

        //a login button megnyomárára: 
        //   jatekoslogin osztalyban: 
            //van egy kezdőkép objektumunk
            //a jatekoslogik megkapja az eredeti kezdokepet majd 
            // meghívja ezt a függvényt a kezdőképen: kezdoKep.setJatekosok(jatekoslista);
            //szóval akezdőképben eltárolt jatekoslista mostmár jó


        loginButton.addActionListener(e -> {
            JatekosLogin loginAblak = new JatekosLogin(this);
            loginAblak.setVisible(true);
        });


//KOMMENT
    //a játékoslistát minek másolod le a jatekosokba újra 
    //létrehozunk egy játéklogika objektumot, ennek kéne ultimate-nek lennie

    //az összes bevitt játékosunkat beadjuk a játéklogikának
    //setaktivjatekos(0. alistaban)  van növelve? hol kéne legyen növelve?
    //setkorokszama automatikus    
    //setjelenkör  van növelve? hol kéne legyen növelve?

    //commandline van meghívva pályageneráláshoz wtf xd
    //ezt átkéne írni majd!
    //a commandlineba van beállítva:
        //setjatek(jateklogika)
        //setjatekoskszama(jatekosok lista nagysaga)
        //palyageneralas
        //palyafeltoltes

            //ezek mit is csinálnak? mit módosítanak?
            //

    //palyakep objektum inicializálása  a jateklogika jatekter mezejével!
    //jatekkep inicializalasa a palyakeppel és jateklogikaval



startGameButton.addActionListener(e -> {
	if (jatekosok.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Nincs játékos bejelentkezve! Kérlek adj hozzá legalább egy játékost.",
            "Hiányzó játékosok",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    JatekLogika jatek = new JatekLogika();
    for (Jatekos j : jatekosok) {
        jatek.addJatekos(j);
    }

    jatek.setAktivJatekos(jatekosok.get(0));
    jatek.setKorokSzama(jatekosok.size() * 10);
    jatek.setJelenKor(0);

    jatek.palyaGeneralas(jatekosok.size());
    //jatek.palyaFeltoltes();

    Palyakep palya = new Palyakep(jatek.getJatekter(), jatek);
    palya.calculateAllNeighbors();
    jatek.palyaFeltoltes();
    JatekKep jatekKep = new JatekKep(jatek, palya);
    palya.inicializalJatekterObjektumokat(jatek);//idk
    jatekKep.setVisible(true);

    dispose(); // KezdoKep vagy JatekosLogin bezárása
});


        
        //Betoltes

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Fajlkezelo fajlkezelo = new Fajlkezelo();
                    JatekLogika jatek = fajlkezelo.load();

                    Palyakep palya = new Palyakep(jatek.getJatekter(), jatek);
                    palya.setTektonCenters(); // Vizualizációhoz beállítjuk a középpontokat

                    palya.calculateAllNeighbors();
                    palya.inicializalJatekterObjektumokat(jatek);

                    JatekKep jatekKep = new JatekKep(jatek, palya);
                    jatekKep.setVisible(true);

                    palya.setTektonCenters();                      // pozíciókat belső térképen újraszámolja
                    palya.inicializalJatekterObjektumokat(jatek); // rovarokat/gombákat újra elhelyezi
                    palya.updateGameState();                       // Voronoi-diagram újrarajzolása
                    dispose();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(KezdoKep.this, "Hiba történt betöltés közben.");
                }
            }
        });


        //Kilepes
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Alkalmazás bezárása
            }
        });

        // Fo panel hozzaadasa az ablakhoz
        add(mainPanel);
    }
    
    //Jatekosok megadasa
    public void setJatekosok(ArrayList<Jatekos> jatekosok) {
        this.jatekosok = jatekosok;
    }

    //Jatekosok lekerese
    public ArrayList<Jatekos> getJatekosok() {
        return jatekosok;
    }


}

