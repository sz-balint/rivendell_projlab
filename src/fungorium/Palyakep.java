package fungorium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;

//import fungorium.Palyakep.DrawingPanel;

public class Palyakep extends JPanel {
    private List<Tekton> tectons; //Tektonok listája a játéktéren
    private JatekLogika jatekLogika;
    private int nextTektonId = 1;
    private BufferedImage diagramImage;
    private final JPanel drawingPanel = new DrawingPanel();
    private int[][] closestMap; // Legközebbi elemek térképe
    private final Random rand = new Random();
    private final Map<Integer, Color> colorAssignments = new HashMap<>();
    public static final Map<Integer, TektonVisualData> tektonVisualData = new HashMap<>();
    private Map<Integer, Set<Integer>> previousNeighbors = new HashMap<>();

    // Game constants - now all size-related constants use WINDOW_SIZE
    private static final int WINDOW_SIZE = 600;//Ablak mérete!
    private static final int MIN_DISTANCE = 50;
    private static final int MAX_TECTONS = 80;
    private static final int TECTON_SIZE = 24;

    private static final double MAX_SIDE_RATIO = 1.8;//Tekton oldalarány maximum  

    private static final double MIN_AREA_PERCENT = 1.0; //Minimális terület %
    private static final double SPLIT_THRESHOLD_PERCENT = 2.5;
    private static final double MIN_DIMENSION_THRESHOLD = 15.0;

    private boolean rovarMarMozgatva = false;
    private Rovar dummy;
    private GombaTest gombaTest;

    private static final Color[] REGION_COLORS = {
        new Color(205, 133, 63),   // sima - peru (világos barna)
        new Color(139, 69, 19),    // fonalfelszivo - saddle brown (sötét barna)
        new Color(160, 82, 45),    // egyfonalas - sienna (meleg barna)
        new Color(210, 180, 140),  // testnelkuli - tan (világos homokbarna)
        new Color(101, 67, 33)     // zombifonal - dark coffee (mély barna)
    };


    // Rovarok tárolása ID alapján
    private Map<Integer, Elolenyek> rovarok = new HashMap<>();

    // Tekton középpontok tárolása
    private Map<Integer, Point> tektonCenters = new HashMap<>();

    // Gombatestek tárolása ID alapján
    private Map<Integer, Elolenyek> gombatestek = new HashMap<>();

    
    

    // Tekton vizuális középpontok kiszámítása
    private void calculateTektonCenters() {
        tektonCenters.clear();
        for (Tekton t : tectons) {
            TektonVisualData tvd = tektonVisualData.get(t.getId());
            if (tvd != null) {
                // Középpont számítása min/max koordináták alapján
                int centerX = (int) ((tvd.minX + tvd.maxX) / 2);
                int centerY = (int) ((tvd.minY + tvd.maxY) / 2);
                tektonCenters.put(t.getId(), new Point(centerX, centerY));
            }
        }
    }

    // Rovar hozzáadása egy Tektonhoz
    public void addRovar(Rovar rovar, int tektonId) {
        Point position = tektonCenters.get(tektonId);
        if (position != null) {
            // Új élőlény létrehozása és tárolása
            Elolenyek eloleny = new Elolenyek(rovar, position);
            rovarok.put(rovar.getId(), eloleny);
            System.out.println(rovar.getId());
        }
        drawingPanel.repaint(); // Rajzoló panel frissítése
    }

    // Rovar mozgatása új Tektonra
    public void moveRovar(Rovar rovar, int newTektonId) {
        Elolenyek eloleny = rovarok.get(rovar.getId());
        Point newPosition = tektonCenters.get(newTektonId);
        System.out.println("tyuha");//Debug üzenet
        
        if (eloleny != null && newPosition != null && tektonVisualData.containsKey(newTektonId)) {
            TektonVisualData tvd = tektonVisualData.get(newTektonId);
            // Véletlen eltolás számítása a célpont körül
            int offsetX = (int)((Math.random() * 0.4 - 0.2) * (tvd.maxX - tvd.minX));
            int offsetY = (int)((Math.random() * 0.4 - 0.2) * (tvd.maxY - tvd.minY));
            
            // Célpozíció kiszámítása eltolással
            Point targetPos = new Point(
                (int)(newPosition.getX() + offsetX),
                (int)(newPosition.getY() + offsetY)
            );
            
            // Mozgatás végrehajtása callbackkel
            eloleny.moveTo(rovar.getHol(), targetPos, () -> {
                rovar.lep(jatekLogika.getTektonById(newTektonId));
                drawingPanel.repaint();
            });
            drawingPanel.repaint();
        }
    }

    // Gombatest hozzáadása egy Tektonhoz
    public void addGombaTest(GombaTest gombaTest, int tektonId) {
        Point position = tektonCenters.get(tektonId);
        if (position != null) {
            Elolenyek eloleny = new Elolenyek(gombaTest, position);
            gombatestek.put(gombaTest.getId(), eloleny);
            tectons.get(tektonId).ujTest(gombaTest);//hihi
        }
        drawingPanel.repaint();
    }

    /////////////////////////////////////////////
    ///Mouse Interaction / Tekton Detection/////
    ////////////////////////////////////////////

    /**
     * Megkeresi, hogy az adott (x, y) pozícióban van-e olyan Tekton, amire rákattintottak.
     *
     * Először a TektonVisualData-ban tárolt középpontokra vizsgálja meg, hogy a kattintás
     * 10 pixelen belül van-e valamelyikhez képest.
     * Ha nem talál ilyet, akkor a closestMap alapján próbálja megállapítani, melyik Tekton 
     * található a megadott koordinátánál.
     *
     * @param x A kattintás X koordinátája
     * @param y A kattintás Y koordinátája
     * @return A kattintott Tekton, vagy null, ha nem található
     */
    private Tekton findClickedTekton(int x, int y) {
        // Végigmegyünk az összes Tektonon
        for (Tekton t : tectons) {
            // Lekérjük a vizuális adatokat (pozíció) az adott Tektonhoz
            TektonVisualData tvd = tektonVisualData.get(t.getId());
            if (tvd != null) {
                // Távolság számítása a kattintás és a Tekton középpontja között
                double distance = Math.hypot(x - tvd.position.x, y - tvd.position.y);
                // Ha 10 pixelen belül van, akkor ezt tekintjük kattintottnak
                if (distance <= 10) {
                    return t;
                }
            }
        }

        // Ha nem találtunk középpont alapján, megpróbáljuk a closestMap alapján
        if (closestMap != null && 
            x >= 0 && x < closestMap.length && 
            y >= 0 && y < closestMap[x].length) {
            int index = closestMap[x][y];
            // Ha az index érvényes, visszaadjuk a megfelelő Tekton példányt
            if (index >= 0 && index < tectons.size()) {
                return tectons.get(index);
            }
        }

        // Nem találtunk semmit
        return null;
    }


    //////////////
    ///Visual/////
    /////////////

    private void setupUI() {
        //setDefaultCloseOperation(EXIT_ON_CLOSE);  // Ablak bezárása esetén kilép a program
        setLayout(new BorderLayout());            // Alap elrendezés
        setSize(WINDOW_SIZE, WINDOW_SIZE);        // Ablak méretének beállítása
        //setResizable(false);                      // Ne lehessen átméretezni

        // Időzítő 500ms-enként: Tekton osztódás vagy rovar mozgatás
       /*  Timer autoSplitTimer = new Timer(50, e -> {
            if (tectons.size() < MAX_TECTONS) {
                executeSplit(null);  // Új Tekton létrehozása szétválasztással
            } else if (!rovarMarMozgatva && dummy != null) {
                Tekton jelenlegi = dummy.getHol();
                List<Tekton> szomszedok = jelenlegi.getSzomszedok();

                if (!szomszedok.isEmpty()) {
                    Tekton cel = szomszedok.get(0);
                    System.out.println("Rovar mozgatása " + jelenlegi.getId() + " -> " + cel.getId());
                    moveRovar(dummy, cel.getId());  // Rovar áthelyezése szomszédos Tektonba
                    rovarMarMozgatva = true;
                } else {
                    System.out.println("Nincs szomszédos tekton a mozgatáshoz.");
                }
            }
        });

        autoSplitTimer.start();  // Timer elindítása*/

        drawingPanel.setBackground(Color.WHITE);  // Háttér szín
        drawingPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        add(drawingPanel, BorderLayout.CENTER);   // Rajzpanel hozzáadása középre
    }


    private void updateGameState() {
        calculateAllAreas();         // Tektonok területének kiszámítása
        updateVoronoiDiagram();      // Voronoi-diagram újrarajzolása
        calculateAllNeighbors();     // Szomszédságok frissítése
        calculateTektonCenters();    // Középpontok újraszámítása
        drawingPanel.repaint();      // Panel újrarajzolása
    }


    public Palyakep(List<Tekton> existingTectons, JatekLogika jatekLogika) {
        this.jatekLogika = jatekLogika;
        this.tectons = new ArrayList<>(existingTectons);  // Meglévő Tekton lista átvétele
        Elolenyek.setTektonVisualData(tektonVisualData);  // TektonVisualData referencia beállítása

        // Minden Tektonhoz véletlenszerű tulajdonság hozzárendelése
        for (Tekton t : tectons) {
            t.setTulajdonsagok(getRandomTectonType()); 
        }

        setupUI();  // Felhasználói felület inicializálása

        // Minden Tektonhoz pozíciót rendelünk és színt állítunk
        for (Tekton t : tectons) {
            Point position = findValidPosition();
            tektonVisualData.put(t.getId(), new TektonVisualData(position));
            assignColor(t);

            // Következő szabad ID frissítése
            if (t.getId() >= nextTektonId) {
                nextTektonId = t.getId() + 1;
            }
        }

        updateGameState();  // Teljes játéktér frissítése

        // Létrehozunk egy "dummy" rovart az első Tektonhoz
        Tekton alapTekton = tectons.get(0);
        dummy = new Rovar(alapTekton, null);
        addRovar(dummy, alapTekton.getId());
        
        Tekton gTekton = tectons.get(1);
        gombaTest = new GombaTest(gTekton, null);
        addGombaTest(gombaTest, gTekton.getId());
        
        System.out.println(gombaTest.getId());
        System.out.println(tectons.get(1).getGombaTest());
        
        updateGameState();
    }


    private void updateVoronoiDiagram() {
        // Új üres kép létrehozása RGB színformátumban
        diagramImage = new BufferedImage(WINDOW_SIZE, WINDOW_SIZE, BufferedImage.TYPE_INT_RGB);
        closestMap = new int[WINDOW_SIZE][WINDOW_SIZE];  // Minden pixelhez legközelebbi Tekton indexe

        Graphics2D g = diagramImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);  // Háttér kirajzolása

        generateClosestMap();        // Hozzárendeljük a legközelebbi Tektonokat
        drawVoronoiCells(g);         // Határok kirajzolása

        g.dispose();                 // Erőforrások felszabadítása
        drawingPanel.repaint();      // Panel újrarajzolása
    }

    /**
    * Minden pixelhez meghatározza a legközelebbi Tekton-t
    *
    * A pixel színét beállítja az adott Tekton színe alapján
    */
    private void generateClosestMap() {
        for (int x = 0; x < WINDOW_SIZE; x++) {
            for (int y = 0; y < WINDOW_SIZE; y++) {
                int closest = findClosestSiteIndex(x, y);  // Legközelebbi Tekton keresése
                if (closest != -1) {
                    closestMap[x][y] = closest;  // Hozzárendelés a térképhez
                    Color c = colorAssignments.get(tectons.get(closest).getId());
                    diagramImage.setRGB(x, y, c.getRGB());  // Kép pixele színezése
                }
            }
        }
    }

    /**
    * A Voronoi-cellák határainak kirajzolása fekete vonalakkal
    *
    * Határokat ott rajzol, ahol két szomszédos pixel más Tektonhoz tartozik
    */
    private void drawVoronoiCells(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));  // Vonalvastagság

        for (int x = 1; x < WINDOW_SIZE - 1; x++) {
            for (int y = 1; y < WINDOW_SIZE - 1; y++) {
                int current = closestMap[x][y];

                // Bal szomszéd különbözik?
                if (x > 0 && closestMap[x-1][y] != current) {
                    g.drawLine(x, y, x-1, y);
                }
                // Jobb szomszéd különbözik?
                if (x < WINDOW_SIZE-1 && closestMap[x+1][y] != current) {
                    g.drawLine(x, y, x+1, y);
                }
                // Felső szomszéd különbözik?
                if (y > 0 && closestMap[x][y-1] != current) {
                    g.drawLine(x, y, x, y-1);
                }
                // Alsó szomszéd különbözik?
                if (y < WINDOW_SIZE-1 && closestMap[x][y+1] != current) {
                    g.drawLine(x, y, x, y+1);
                }
            }
        }
    }

    /**
    * Egy Tekton grafikai adatainak (pozíció, terület, határok) tárolása
    *
    * A Voronoi-diagramhoz és egyéb vizualizációkhoz szükséges
    */
    public static class TektonVisualData {
        Point position;
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        double area = 0;
        
        public TektonVisualData(Point position) {
            this.position = position;
        }
    }

    /**
     * A játék kirajzolásáért felelős panel.
     * 
     * Felelős a Voronoi-diagram, Tekton pozíciók, és rovarok megjelenítéséért.
     * Egérmozgásra kiemeli az alatta lévő Tekton-t (ha van).
     */
    class DrawingPanel extends JPanel {
        private static final int HOVER_RADIUS = 15;     // A kurzor körüli érzékelési sugár
        private Integer hoveredTektonId = null;         // Az aktuálisan kijelölt (hovered) Tekton ID-ja

        /**
         * Konstruktor: egéresemények figyelése.
         * - Kilépéskor törli a kijelölt Tekton-t.
         * - Egérmozgáskor frissíti a hovered Tekton azonosítót.
         */
        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    hoveredTektonId = null;
                    repaint(); // Újrarajzolás kijelölés eltüntetéséhez
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e) {
                    Tekton t = findClickedTekton(e.getX(), e.getY());
                    hoveredTektonId = (t != null) ? t.getId() : null;
                    repaint(); // Újrarajzolás kijelölés frissítéséhez
                }
            });
        }

        /**
         * A teljes játéktér újrarajzolása: diagram, Tektonok, kijelölés és rovarok.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Háttérkép kirajzolása (Voronoi-diagram)
            if (diagramImage != null) {
                g.drawImage(diagramImage, 0, 0, this);
            }

            // Tekton pozíciók és ID-k kirajzolása
            drawTektonSites((Graphics2D) g);

            // Ha van kiválasztott Tekton, emeljük ki
            if (hoveredTektonId != null) {
                Tekton t = tectons.stream()
                    .filter(tek -> tek.getId() == hoveredTektonId)
                    .findFirst()
                    .orElse(null);

                if (t != null && tektonVisualData.containsKey(t.getId())) {
                    TektonVisualData tvd = tektonVisualData.get(t.getId());
                    Graphics2D g2d = (Graphics2D) g.create();

                    // Sárga áttetsző kör a kijelölt Tekton köré
                    g2d.setColor(new Color(255, 255, 0, 100));
                    g2d.fillOval(
                        (int) tvd.position.x - HOVER_RADIUS,
                        (int) tvd.position.y - HOVER_RADIUS,
                        HOVER_RADIUS * 2,
                        HOVER_RADIUS * 2
                    );

                    g2d.dispose();
                }
            }

            // Rovarok kirajzolása
            for (Elolenyek rovar : rovarok.values()) {
                rovar.rajzol(g);
                
            }
            for (Elolenyek gombaTest : gombatestek.values()) {
                gombaTest.rajzol(g);
            }
        }

        /**
         * Egyetlen Tekton pozíciójának és ID-jának megjelenítése
         */
        private void drawSingleTektonSite(Graphics2D g, Tekton t, TektonVisualData tvd) {
            int x = (int) Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.position.x));
            int y = (int) Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.position.y));

            g.setColor(Color.BLACK);
            g.fillOval(x - 3, y - 3, 6, 6);  // Kis pont a középpontra

            g.setColor(Color.WHITE);
            String num = Integer.toString(t.getId());
            FontMetrics fm = g.getFontMetrics();
            g.drawString(num, x - fm.stringWidth(num) / 2, y + fm.getAscent() / 3); // ID kiírása
        }

        /**
         * Az összes Tekton pozíciójának kirajzolása a képernyőre
         */
        private void drawTektonSites(Graphics2D g) {
            g.setFont(new Font("Arial", Font.BOLD, 14));  // Számok betűtípusa
            for (Tekton t : tectons) {
                TektonVisualData tvd = tektonVisualData.get(t.getId());
                if (tvd != null) {
                    drawSingleTektonSite(g, t, tvd);
                }
            }
        }
    }

    /**
     *Véletlenszerűen kiválaszt egy Tekton típust az elérhető listából.
     **/
    private String getRandomTectonType() {
        List<String> types = List.of("sima", "fonalfelszivo", "egyfonalas", "testnelkuli", "zombifonal");
        return types.get(rand.nextInt(types.size()));
    }

    /**
    *Beállítja a Tekton színét a tulajdonságai alapján, és elmenti a szín-hozzárendelést
    */
    Color color;
    private void assignColor(Tekton tekton) {
        if (tekton == null) return;

        switch (tekton.getTulajdonsagok()) {
            case "fonalfelszivo":
                color = REGION_COLORS[1];
                break;
            case "egyfonalas":
                color = REGION_COLORS[2];
                break;
            case "testnelkuli":
                color = REGION_COLORS[3];
                break;
            case "zombifonal":
                color = REGION_COLORS[4];
                break;
            default:  // "sima"
                color = REGION_COLORS[0];
        }
        
        colorAssignments.put(tekton.getId(), color);
    }

    /**
     * A Tekton vizuális határainak (bounding box) tárolására szolgáló segédosztály
     * A bounding box a Tekton vizuális térbeli kiterjedésének legkisebb téglalapja,
     * amely teljesen lefedi azt.
     */
    private static class Bounds {
        final double minX, maxX, minY, maxY;

        public Bounds(double minX, double maxX, double minY, double maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }

    /**
     * Megkeresi, hogy az adott (x, y) koordinátához melyik Tekton van a legközelebb.
     *
     * @return A legközelebbi Tekton indexe a tectons listában
     */
    private int findClosestSiteIndex(int x, int y) {
        int closest = -1;
        double minDist = Double.MAX_VALUE;

        for (int i = 0; i < tectons.size(); i++) {
            TektonVisualData tvd = tektonVisualData.get(tectons.get(i).getId());
            if (tvd != null) {
                double dx = tvd.position.x - x;
                double dy = tvd.position.y - y;
                double distSq = dx * dx + dy * dy;

                if (distSq < minDist) {
                    minDist = distSq;
                    closest = i;
                }
            }
        }
        return closest;
    }


    /////////////////////////
    //Math & Inner Logic/////
    ////////////////////////

    /**
     * Megpróbálja szétválasztani a legnagyobb területű Tektonokat új Tekton létrehozása érdekében.
     * A folyamat validációval és rollback mechanizmussal biztosítja a játékegyenletességet.
     */
    private void executeSplit(ActionEvent e) {
        if (tectons.size() >= MAX_TECTONS) {
            return; // Ha elértük a maximum Tekton számot, nem osztunk tovább
        }

        calculateAllAreas(); // Területek frissítése
        double totalArea = WINDOW_SIZE * WINDOW_SIZE;

        printSortedTectons("Current tecton list (sorted by area)", totalArea);

        // Küszöbértékek a szétválasztáshoz
        double minArea = totalArea * (MIN_AREA_PERCENT / 100) * 0.3;
        double splitThreshold = totalArea * (SPLIT_THRESHOLD_PERCENT / 100) * 0.5;
        double absoluteMinArea = totalArea * 0.015;

        // Szűrés: csak azok a Tektonok lehetnek jelöltek, amelyek nagyobbak a küszöbnél
        List<Tekton> candidates = tectons.stream()
            .filter(t -> {
                TektonVisualData tvd = tektonVisualData.get(t.getId());
                return tvd != null && tvd.area >= splitThreshold;
            })
            .sorted((a, b) -> {
                TektonVisualData aTvd = tektonVisualData.get(a.getId());
                TektonVisualData bTvd = tektonVisualData.get(b.getId());
                return Double.compare(
                    bTvd != null ? bTvd.area : 0, 
                    aTvd != null ? aTvd.area : 0
                );
            })
            .collect(Collectors.toList());

        if (candidates.isEmpty()) return;

        final int MAX_NORMAL_ATTEMPTS = 5;
        int MAX_TOTAL_ATTEMPTS = 50;

        int totalAttempts = 0;
        boolean splitSuccessful = false;

        // Többszöri próbálkozás: osztás csak akkor marad érvényben, ha megfelel minden szabálynak
        for (int attempts = 0; attempts < MAX_NORMAL_ATTEMPTS && totalAttempts < MAX_TOTAL_ATTEMPTS; attempts++) {
            for (Tekton candidate : candidates) {
                if (totalAttempts >= MAX_TOTAL_ATTEMPTS) break;

                TektonVisualData tvd = tektonVisualData.get(candidate.getId());
                if (tvd == null) continue;

                totalAttempts++;

                //!! attemptSplit : akkor örülünk, ha ez fut le! Ebben minden check benne van ami kell
                if (attemptSplit(candidate, tvd, minArea)) {
                    calculateAllAreas();
                    printSortedTectons("State after splitting tekton " + candidate.getId(), totalArea);

                    // Ellenőrzés: egyik Tekton se lett túl kicsi?
                    boolean anyTooSmall = tectons.stream()
                    		.anyMatch(t -> {
                                TektonVisualData tv = tektonVisualData.get(t.getId());
                                for (Tekton te: tectons) {
                                	if (t != te) {
                                		TektonVisualData td = tektonVisualData.get(te.getId());
                                		if (Point.distance(td.position.x, td.position.y ,tv.position.x, tv.position.y) < 40) return true;
                                	}
                                }
                                return false;
                            });

                    if (!anyTooSmall) {
                        updateGameState(); // Ha minden rendben, játékfrissítés
                        splitSuccessful = true;
                        break;
                    } else {
                        Tekton lastAdded = tectons.get(tectons.size() - 1);
                        rollbackSplit(candidate, lastAdded, null); // Túl kicsi lett => visszaállítjuk
                        printSortedTectons("State after rollback", totalArea);
                    }
                }
            }
            if (splitSuccessful) break;
        }

        // Végső próbálkozás: kétségbeesett (lazább feltételekkel)
        if (!splitSuccessful) {
            int MAX_DESPERATE_ATTEMPTS = 5;

            for (Tekton candidate : candidates) {
                TektonVisualData tvd = tektonVisualData.get(candidate.getId());
                if (tvd == null) continue;

                if (desperateSplitAttempt(candidate, tvd, minArea)) {
                    splitSuccessful = true;
                    break;
                }

                if (--MAX_DESPERATE_ATTEMPTS <= 0) break;
            }
        }
    }

    /**
     * Egy adott Tekton szétválasztása egy új Tekton létrehozásával.
    */
    private boolean attemptSplit(Tekton original, TektonVisualData originalTvd, double minArea) {
        if (originalTvd.area < 2 * minArea) return false; // Túl kicsi a szétválasztáshoz

        // Szomszédok elmentése rollbackhez
        previousNeighbors = new HashMap<>();
        for (Tekton t : tectons) {
            previousNeighbors.put(t.getId(),
                t.getSzomszedok().stream()
                    .map(Tekton::getId)
                    .collect(Collectors.toSet())
            );
        }

        Point splitPoint = calculatePersistentSplitPoint(originalTvd);
        if (isOutOfBounds(splitPoint)) return false;

        // Új Tekton létrehozása
        Tekton newTekton = new Tekton(getRandomTectonType());
        newTekton.setId(nextTektonId++);
        TektonVisualData newTvd = new TektonVisualData(splitPoint);

        // Eredeti bounding box elmentése rollbackhez
        Bounds originalBounds = new Bounds(
            originalTvd.minX, originalTvd.maxX,
            originalTvd.minY, originalTvd.maxY
        );

        tektonVisualData.put(newTekton.getId(), newTvd);
        tectons.add(newTekton);

        jatekLogika.setJatekter(tectons);
        calculatePartialAreas(original, newTekton); // Új területek kiszámítása

        // Érvényesség-ellenőrzések
        if (!validateSplitAreas(originalTvd, newTvd, minArea) ||
            !validateSplitGeometry(originalTvd, newTvd)) {
            rollbackSplit(original, newTekton, originalBounds);
            return false;
        }

        Tekton splitResult = original.kettetores(); // Játéklogikai szétválasztás
        if (splitResult == null) {
            rollbackSplit(original, newTekton, originalBounds);
            return false;
        }

        // Szomszédság frissítése
        original.ujSzomszed(newTekton);
        newTekton.ujSzomszed(original);
        calculateAllNeighbors();

        // Ismételten beállítjuk a kapcsolatot (duplikáltan, de biztosan)
        original.ujSzomszed(newTekton);
        newTekton.ujSzomszed(original);

        // Szomszédság ellenőrzése
        if (!validateNeighborInheritance(original, newTekton) ||
            !validateAllNeighborsAfterSplit(original, newTekton)) {
            rollbackSplit(original, newTekton, originalBounds);
            return false;
        }

        assignColor(newTekton); // Új Tekton színezése
        return true;
    }

    /**
     * Végső próbálkozás szétválasztásra, ha a normál mód nem sikerült.
     * Itt már lazább feltételekkel is engedélyezett a szétválasztás.
     */
    private boolean desperateSplitAttempt(Tekton original, TektonVisualData originalTvd, double minArea) {
        System.out.println("ajaj"); // Debug üzenet

        // Szomszédság mentése rollbackhez
        previousNeighbors = new HashMap<>();
        for (Tekton t : tectons) {
            previousNeighbors.put(t.getId(),
                t.getSzomszedok().stream()
                    .map(Tekton::getId)
                    .collect(Collectors.toSet())
            );
        }

        for (int i = 0; i < 10; i++) {
            Point splitPoint = calculateDesperateSplitPoint(originalTvd);
            Tekton newTekton = new Tekton(getRandomTectonType());
            newTekton.setId(nextTektonId++);

            TektonVisualData newTvd = new TektonVisualData(splitPoint);

            tektonVisualData.put(newTekton.getId(), newTvd);
            tectons.add(newTekton);
            calculatePartialAreas(original, newTekton);

            // Területellenőrzés – itt már kicsit engedékenyebb
            if (originalTvd.area < minArea * 1.5 || newTvd.area < minArea * 1.5) {
                rollbackSplit(original, newTekton, null);
                continue;
            }

            Tekton splitResult = original.kettetores();
            if (splitResult == null) {
                rollbackSplit(original, newTekton, null);
                continue;
            }

            // Szomszédság frissítése és színezés
            original.ujSzomszed(newTekton);
            newTekton.ujSzomszed(original);
            calculateAllNeighbors();
            assignColor(newTekton);

            return true;
        }

        return false;
    }

    private Point findValidPosition() {
        int margin = TECTON_SIZE * 2;
        for (int attempt = 0; attempt < 100; attempt++) {
            Point candidate = new Point(
                margin + rand.nextInt(WINDOW_SIZE - 2 * margin),
                margin + rand.nextInt(WINDOW_SIZE - 2 * margin)
            );
            if (!isTooCloseToOtherTectons(candidate)) {
                return candidate;
            }
        }
        return new Point(WINDOW_SIZE/2, WINDOW_SIZE/2);
    }

    private void calculateAllAreas() {
        for (Tekton t : tectons) {
            TektonVisualData tvd = tektonVisualData.get(t.getId());
            if (tvd != null) {
                tvd.area = 0;
                tvd.minX = Double.MAX_VALUE;
                tvd.maxX = Double.MIN_VALUE;
                tvd.minY = Double.MAX_VALUE;
                tvd.maxY = Double.MIN_VALUE;
            }
        }
        
        int scaleFactor = 2;
        int scaledSize = WINDOW_SIZE / scaleFactor;
        
        for (int x = 0; x < scaledSize; x++) {
            for (int y = 0; y < scaledSize; y++) {
                int actualX = x * scaleFactor;
                int actualY = y * scaleFactor;
                int closest = findClosestSiteIndex(actualX, actualY);
                if (closest != -1) {
                    Tekton t = tectons.get(closest);
                    TektonVisualData tvd = tektonVisualData.get(t.getId());
                    if (tvd != null) {
                        tvd.area += scaleFactor * scaleFactor;
                        tvd.minX = Math.min(tvd.minX, actualX);
                        tvd.maxX = Math.max(tvd.maxX, actualX);
                        tvd.minY = Math.min(tvd.minY, actualY);
                        tvd.maxY = Math.max(tvd.maxY, actualY);
                    }
                }
            }
        }
    }

    private boolean isOutOfBounds(Point p) {
        int safeMargin = TECTON_SIZE * 2;
        return p.x < safeMargin || p.x > WINDOW_SIZE - safeMargin || 
               p.y < safeMargin || p.y > WINDOW_SIZE - safeMargin;
    }

    private boolean isTooCloseToOtherTectons(Point newPosition) {
        return tektonVisualData.values().stream()
            .anyMatch(tvd -> newPosition.distance(tvd.position) < MIN_DISTANCE);
    }

    private void calculatePartialAreas(Tekton original, Tekton newTekton) {
        TektonVisualData originalTvd = tektonVisualData.get(original.getId());
        TektonVisualData newTvd = tektonVisualData.get(newTekton.getId());
        
        originalTvd.area = 0;
        newTvd.area = 0;
        originalTvd.minX = Double.MAX_VALUE;
        originalTvd.maxX = Double.MIN_VALUE;
        originalTvd.minY = Double.MAX_VALUE;
        originalTvd.maxY = Double.MIN_VALUE;
        newTvd.minX = Double.MAX_VALUE;
        newTvd.maxX = Double.MIN_VALUE;
        newTvd.minY = Double.MAX_VALUE;
        newTvd.maxY = Double.MIN_VALUE;
        
        int scaleFactor = 2;
        int scaledSize = WINDOW_SIZE / scaleFactor;
        
        for (int x = 0; x <= scaledSize; x++) {
            for (int y = 0; y <= scaledSize; y++) {
                int actualX = Math.min(x * scaleFactor, WINDOW_SIZE-1);
                int actualY = Math.min(y * scaleFactor, WINDOW_SIZE-1);
                
                double distOriginal = distanceSq(originalTvd.position, actualX, actualY);
                double distNew = distanceSq(newTvd.position, actualX, actualY);
                
                if (distOriginal < distNew) {
                    updateTektonArea(originalTvd, actualX, actualY, scaleFactor);
                } else {
                    updateTektonArea(newTvd, actualX, actualY, scaleFactor);
                }
            }
        }
    }

    private void updateTektonArea(TektonVisualData tvd, int x, int y, int scaleFactor) {
        tvd.area += scaleFactor * scaleFactor;
        updateTektonBounds(tvd, x, y);
    }

    private void updateTektonBounds(TektonVisualData tvd, int x, int y) {
        tvd.minX = Math.min(tvd.minX, x);
        tvd.maxX = Math.max(tvd.maxX, x);
        tvd.minY = Math.min(tvd.minY, y);
        tvd.maxY = Math.max(tvd.maxY, y);
    }

    private double distanceSq(Point p, int x, int y) {
        double dx = p.x - x;
        double dy = p.y - y;
        return dx*dx + dy*dy;
    }

    private boolean validateSplitAreas(TektonVisualData originalTvd, TektonVisualData newTvd, double minArea) {
        double combinedMin = minArea * 1.4;
        return originalTvd.area >= combinedMin && newTvd.area >= combinedMin;
    }

    private boolean validateSplitGeometry(TektonVisualData originalTvd, TektonVisualData newTvd) {
        double width1 = originalTvd.maxX - originalTvd.minX;
        double height1 = originalTvd.maxY - originalTvd.minY;
        double width2 = newTvd.maxX - newTvd.minX;
        double height2 = newTvd.maxY - newTvd.minY;
        
        boolean tooNarrow1 = width1 < MIN_DIMENSION_THRESHOLD || height1 < MIN_DIMENSION_THRESHOLD;
        boolean tooNarrow2 = width2 < MIN_DIMENSION_THRESHOLD || height2 < MIN_DIMENSION_THRESHOLD;
        
        double aspectRatio1 = Math.max(width1, height1) / (Math.min(width1, height1) + 0.001);
        double aspectRatio2 = Math.max(width2, height2) / (Math.min(width2, height2) + 0.001);
        
        return !tooNarrow1 && !tooNarrow2 && 
               aspectRatio1 <= MAX_SIDE_RATIO && 
               aspectRatio2 <= MAX_SIDE_RATIO;
    }
    
    private Point calculatePersistentSplitPoint(TektonVisualData tvd) {
        double minX = Math.max(TECTON_SIZE, tvd.minX);
        double maxX = Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.maxX);
        double minY = Math.max(TECTON_SIZE, tvd.minY);
        double maxY = Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.maxY);
        
        double centerX = (minX + maxX) / 2;
        double centerY = (minY + maxY) / 2;
        
        double innerWidth = (maxX - minX) * 0.5;
        double innerHeight = (maxY - minY) * 0.5;
        
        double innerMinX = Math.max(TECTON_SIZE, centerX - innerWidth/2);
        double innerMaxX = Math.min(WINDOW_SIZE - TECTON_SIZE, centerX + innerWidth/2);
        double innerMinY = Math.max(TECTON_SIZE, centerY - innerHeight/2);
        double innerMaxY = Math.min(WINDOW_SIZE - TECTON_SIZE, centerY + innerHeight/2);
        
        double x = -1, y = -1;
        
        int irany = rand.nextInt(1, 4);

        while (x < 0 || x > WINDOW_SIZE || y < 0 || y > WINDOW_SIZE)
        switch(irany) {
        	case 1:
        		x = centerX - (centerX - innerMinX) - rand.nextDouble() * (innerMinX - minX);
        		y = centerY - (centerY - innerMinY) - rand.nextDouble() * (innerMinY - minY);   
        	break;
        	case 2:
	    		x = centerX + (innerMaxX - centerX) + rand.nextDouble() * (maxX - innerMaxX);
	    		y = centerY - (centerY - innerMinY) - rand.nextDouble() * (innerMinY - minY);   
    		break;
	        case 3:
        		x = centerX - (centerX - innerMinX) - rand.nextDouble() * (innerMinX - minX);
        		y = centerY + (innerMaxY - centerY) + rand.nextDouble() * (maxY - innerMinY);   
        	break;
	        case 4:
	    		x = centerX + (innerMaxX - centerX) + rand.nextDouble() * (maxX - innerMaxX);
        		y = centerY + (innerMaxY - centerY) + rand.nextDouble() * (maxY - innerMinY);   
        	break;
        }
        
        x = Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, x));
        y = Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, y));
        
        //return new Point(x, y);
        return new Point((int) x, (int) y);
    }

    private Point calculateDesperateSplitPoint(TektonVisualData tvd) {
        double x = tvd.minX + (tvd.maxX - tvd.minX) * (rand.nextBoolean() ? 
            (rand.nextDouble() * 0.3 + (rand.nextBoolean() ? 0 : 0.7)) : 
            rand.nextDouble());
        double y = tvd.minY + (tvd.maxY - tvd.minY) * (rand.nextBoolean() ? 
            (rand.nextDouble() * 0.3 + (rand.nextBoolean() ? 0 : 0.7)) : 
            rand.nextDouble());
        
        x = Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, x));
        y = Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, y));
        
        return new Point((int) x, (int) y);
    }

    private boolean validateNeighborInheritance(Tekton original, Tekton newTekton) {
        Set<Integer> originalPreviousNeighbors = previousNeighbors.getOrDefault(original.getId(), new HashSet<>());
        Set<Integer> originalCurrentNeighbors = original.getSzomszedok().stream()
            .map(Tekton::getId)
            .collect(Collectors.toSet());
        Set<Integer> newCurrentNeighbors = newTekton.getSzomszedok().stream()
            .map(Tekton::getId)
            .collect(Collectors.toSet());

        boolean newTektonValid = newCurrentNeighbors.contains(original.getId()) && 
            newCurrentNeighbors.stream()
                .allMatch(id -> id == original.getId() || originalPreviousNeighbors.contains(id));

        boolean originalTektonValid = originalCurrentNeighbors.contains(newTekton.getId()) && 
            originalCurrentNeighbors.stream()
                .allMatch(id -> id == newTekton.getId() || originalPreviousNeighbors.contains(id));

        return newTektonValid && originalTektonValid;
    }

    private boolean validateAllNeighborsAfterSplit(Tekton original, Tekton newTekton) {
        boolean allValid = true;

        int originalId = original.getId();
        int newTektonId = newTekton.getId();

        for (Tekton t : tectons) {
            int tId = t.getId();
            if (tId == newTektonId) continue;

            Set<Integer> currentNeighbors = t.getSzomszedok().stream()
                .map(Tekton::getId)
                .collect(Collectors.toSet());

            Set<Integer> previousNeighborsForT = previousNeighbors.getOrDefault(tId, new HashSet<>());

            Set<Integer> allowedNewNeighbors = new HashSet<>(previousNeighborsForT);

            if (previousNeighborsForT.contains(originalId)) {
                allowedNewNeighbors.add(originalId);
            } else {
                allowedNewNeighbors.remove(originalId);
            }

            allowedNewNeighbors.add(newTektonId);

            if (!allowedNewNeighbors.containsAll(currentNeighbors)) {
                allValid = false;
            }
        }

        return allValid;
    }

    private void completelyRemoveTekton(Tekton tekton) {
        if (tekton == null || !tectons.contains(tekton)) return;
        
        int removedId = tekton.getId();
        tectons.remove(tekton);
        tektonVisualData.remove(removedId);
        colorAssignments.remove(removedId);
        
        for (Tekton t : tectons) {
            t.getSzomszedok().removeIf(neighbor -> neighbor.getId() == removedId);
        }
        
        if (removedId == nextTektonId - 1) {
            nextTektonId = tectons.stream()
                .mapToInt(Tekton::getId)
                .max()
                .orElse(0) + 1;
        }
    }

    private void rollbackSplit(Tekton original, Tekton newTekton, Bounds originalBounds) {
        completelyRemoveTekton(newTekton);
        
        if (originalBounds != null) {
            TektonVisualData tvd = tektonVisualData.get(original.getId());
            if (tvd != null) {
                tvd.minX = originalBounds.minX;
                tvd.maxX = originalBounds.maxX;
                tvd.minY = originalBounds.minY;
                tvd.maxY = originalBounds.maxY;
            }
        }
        
        calculateAllNeighbors();
    }

    private void calculateAllNeighbors() {
        for (Tekton t : tectons) {
            t.getSzomszedok().clear();
        }
        
        if (closestMap == null || closestMap.length == 0) return;
        
        Map<Integer, Set<Integer>> edgeMap = new HashMap<>();
        for (int x = 1; x < closestMap.length - 1; x++) {
            for (int y = 1; y < closestMap[x].length - 1; y++) {
                int currentRegion = closestMap[x][y];
                if (x > 0) checkAndAddNeighbor(edgeMap, currentRegion, closestMap[x-1][y]);
                if (x < closestMap.length - 1) checkAndAddNeighbor(edgeMap, currentRegion, closestMap[x+1][y]);
                if (y > 0) checkAndAddNeighbor(edgeMap, currentRegion, closestMap[x][y-1]);
                if (y < closestMap[x].length - 1) checkAndAddNeighbor(edgeMap, currentRegion, closestMap[x][y+1]);
            }
        }
        
        for (Map.Entry<Integer, Set<Integer>> entry : edgeMap.entrySet()) {
            Tekton t = tectons.get(entry.getKey());
            for (Integer neighborIdx : entry.getValue()) {
                Tekton neighbor = tectons.get(neighborIdx);
                if (!t.getSzomszedok().contains(neighbor)) {
                    t.ujSzomszed(neighbor);
                }
                if (!neighbor.getSzomszedok().contains(t)) {
                    neighbor.ujSzomszed(t);
                }
            }
        }
        
        for (Tekton t : tectons) {
            t.getSzomszedok().remove(t);
            for (Tekton neighbor : t.getSzomszedok()) {
                if (!neighbor.getSzomszedok().contains(t)) {
                    neighbor.ujSzomszed(t);
                }
            }
        }
    }

    private void checkAndAddNeighbor(Map<Integer, Set<Integer>> edgeMap, int region1, int region2) {
        if (region1 != region2) {
            edgeMap.computeIfAbsent(region1, k -> new HashSet<>()).add(region2);
            edgeMap.computeIfAbsent(region2, k -> new HashSet<>()).add(region1);
        }
    }

    ///DEBUG///
        private void printSortedTectons(String message, double totalArea) {
        tectons.stream()
            .sorted((a, b) -> {
                TektonVisualData aTvd = tektonVisualData.get(a.getId());
                TektonVisualData bTvd = tektonVisualData.get(b.getId());
                return Double.compare(
                    bTvd != null ? bTvd.area : 0, 
                    aTvd != null ? aTvd.area : 0
                );
            })
            .forEach(t -> {
                TektonVisualData tvd = tektonVisualData.get(t.getId());
            });
    }

} 