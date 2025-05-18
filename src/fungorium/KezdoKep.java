package fungorium;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

        // Esemenykezelok hozzaadasa a gombokhoz
        //Jatekkezdese
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jatekosok.isEmpty()) {
                    JOptionPane.showMessageDialog(KezdoKep.this, "Nincsenek Játékosok", "Hiba", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(KezdoKep.this, "Játék megkezdése!");
                    //Jatekkezdes
                    
                    
                }
            }
        });

        //Jatekosok megadasa
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SwingUtilities.invokeLater(JatekosLogin::new);
            }
        });
        
        //Betoltes
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(KezdoKep.this, "betolt");
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

