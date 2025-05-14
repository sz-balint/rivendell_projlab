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

public class Palyakep extends JFrame {
    private List<Tekton> tectons;
    private JatekLogika jatekLogika;
    private int nextTektonId = 1;
    private BufferedImage diagramImage;
    private final JPanel drawingPanel = new DrawingPanel();
    private int[][] closestMap;
    private final Random rand = new Random();
    private final Map<Integer, Color> colorAssignments = new HashMap<>();
    private final Map<Integer, TektonVisualData> tektonVisualData = new HashMap<>();
    private Map<Integer, Set<Integer>> previousNeighbors = new HashMap<>();

    // Game constants - now all size-related constants use WINDOW_SIZE
    private static final int WINDOW_SIZE = 700;  // Fixed window size
    private static final int MIN_DISTANCE = 50;
    private static final int MAX_TECTONS = 40;
    private static final int TECTON_SIZE = 24;

    private static final double MAX_SIDE_RATIO = 1.8;

    private static final double MIN_AREA_PERCENT = 1.0;  // Reduced from 1.5
    private static final double SPLIT_THRESHOLD_PERCENT = 2.5;  // Reduced from 3.0
    private static final double MIN_DIMENSION_THRESHOLD = 15.0;  // Reduced from 20.0

    private static final Color[] REGION_COLORS = {
        new Color(205, 133, 63),   // sima - peru (világos barna)
        new Color(139, 69, 19),    // fonalfelszivo - saddle brown (sötét barna)
        new Color(160, 82, 45),    // egyfonalas - sienna (meleg barna)
        new Color(210, 180, 140),  // testnelkuli - tan (világos homokbarna)
        new Color(101, 67, 33)     // zombifonal - dark coffee (mély barna)
    };

    private String getRandomTectonType() {
        List<String> types = List.of("sima", "fonalfelszivo", "egyfonalas", "testnelkuli", "zombifonal");
        return types.get(rand.nextInt(types.size()));
    }

    private boolean rovarMarMozgatva = false;
private Rovar dummy;

private void setupUI() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setSize(WINDOW_SIZE, WINDOW_SIZE);
    setResizable(false);  // Fixed size window

    // Timer a Tekton-ok automatikus tördelésére és rovar mozgatására
    Timer autoSplitTimer = new Timer(500, e -> {
        if (tectons.size() < MAX_TECTONS) {
            executeSplit(null);  // Tekton szétvágás
        } else if (!rovarMarMozgatva && dummy != null) {
            Tekton jelenlegi = dummy.getHol();
            List<Tekton> szomszedok = jelenlegi.getSzomszedok();

            if (!szomszedok.isEmpty()) {
                Tekton cel = szomszedok.get(0);  // az első szomszédot választjuk
                System.out.println("Rovar mozgatása " + jelenlegi.getId() + " -> " + cel.getId());
                moveRovar(dummy, cel.getId());
                rovarMarMozgatva = true;
            } else {
                System.out.println("Nincs szomszédos tekton a mozgatáshoz.");
            }
        }
    });

    autoSplitTimer.start();

    drawingPanel.setBackground(Color.WHITE);
    drawingPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
    add(drawingPanel, BorderLayout.CENTER);
}

    public Palyakep(List<Tekton> existingTectons, JatekLogika jatekLogika) {
        this.jatekLogika = jatekLogika;
        this.tectons = new ArrayList<>(existingTectons);
        
        for (Tekton t : tectons) {
            t.setTulajdonsagok(getRandomTectonType()); 
        }
        
        setupUI();
        
        for (Tekton t : tectons) {
            Point position = findValidPosition();
            tektonVisualData.put(t.getId(), new TektonVisualData(position));
            assignColor(t);
            if (t.getId() >= nextTektonId) {
                nextTektonId = t.getId() + 1;
            }
        }
        updateGameState();
        Tekton alapTekton = tectons.get(0);

        dummy = new Rovar(alapTekton, null); // vagy adj neki Játékost is
        addRovar(dummy, alapTekton.getId());
        
        
    }

    /*private void setupUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        setResizable(false);  // Fixed size window
        
        Timer autoSplitTimer = new Timer(500, e -> {  // Split every second
            if (tectons.size() < MAX_TECTONS) {
                executeSplit(null);  // Call the existing split method
            }
        });
        autoSplitTimer.start();
        
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        add(drawingPanel, BorderLayout.CENTER);
    }*/

    private void updateVoronoiDiagram() {
        diagramImage = new BufferedImage(WINDOW_SIZE, WINDOW_SIZE, BufferedImage.TYPE_INT_RGB);
        closestMap = new int[WINDOW_SIZE][WINDOW_SIZE];

        Graphics2D g = diagramImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);

        generateClosestMap();
        drawVoronoiCells(g);
        
        g.dispose();
        drawingPanel.repaint();
    }

    private void generateClosestMap() {
        for (int x = 0; x < WINDOW_SIZE; x++) {
            for (int y = 0; y < WINDOW_SIZE; y++) {
                int closest = findClosestSiteIndex(x, y);
                if (closest != -1) {
                    closestMap[x][y] = closest;
                    Color c = colorAssignments.get(tectons.get(closest).getId());
                    diagramImage.setRGB(x, y, c.getRGB());
                }
            }
        }
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
        return new Point(WINDOW_SIZE/2, WINDOW_SIZE/2); // fallback position
    }

    private void updateGameState() {
        calculateAllAreas();
        updateVoronoiDiagram();
        calculateAllNeighbors();
        calculateTektonCenters();
        drawingPanel.repaint(); }

    private void drawVoronoiCells(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        
        for (int x = 1; x < WINDOW_SIZE - 1; x++) {
            for (int y = 1; y < WINDOW_SIZE - 1; y++) {
                int current = closestMap[x][y];
                
                // Check all 4 directions
                if (x > 0 && closestMap[x-1][y] != current) {
                    g.drawLine(x, y, x-1, y);
                }
                if (x < WINDOW_SIZE-1 && closestMap[x+1][y] != current) {
                    g.drawLine(x, y, x+1, y);
                }
                if (y > 0 && closestMap[x][y-1] != current) {
                    g.drawLine(x, y, x, y-1);
                }
                if (y < WINDOW_SIZE-1 && closestMap[x][y+1] != current) {
                    g.drawLine(x, y, x, y+1);
                }
            }
        }
    }

    private void calculateAllAreas() {
        // Reset all areas and bounds
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

    private void executeSplit(ActionEvent e) {
        if (tectons.size() >= MAX_TECTONS) {
            return;
        }

        calculateAllAreas();
        double totalArea = WINDOW_SIZE * WINDOW_SIZE;
        
        printSortedTectons("Current tecton list (sorted by area)", totalArea);
        
        double minArea = totalArea * (MIN_AREA_PERCENT / 100) * 0.3;
        double splitThreshold = totalArea * (SPLIT_THRESHOLD_PERCENT / 100) * 0.5;
        double absoluteMinArea = totalArea * 0.015;
        
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
        
        if (candidates.isEmpty()) {
            return;
        }

        final int MAX_NORMAL_ATTEMPTS = 5; 
        int MAX_TOTAL_ATTEMPTS = 50;  // Total attempts across all candidates
        
        int totalAttempts = 0;
        boolean splitSuccessful = false;
        
        for (int attempts = 0; attempts < MAX_NORMAL_ATTEMPTS && totalAttempts < MAX_TOTAL_ATTEMPTS; attempts++) {
            for (Tekton candidate : candidates) {
                if (totalAttempts >= MAX_TOTAL_ATTEMPTS) break;
                
                TektonVisualData tvd = tektonVisualData.get(candidate.getId());
                if (tvd == null) continue;
                
                totalAttempts++;
                
                if (attemptSplit(candidate, tvd, minArea, attempts % 8)) {
                    calculateAllAreas();
                    printSortedTectons("State after splitting tekton " + candidate.getId(), totalArea);
                    
                    boolean anyTooSmall = tectons.stream()
                        .anyMatch(t -> {
                            TektonVisualData tv = tektonVisualData.get(t.getId());
                            return tv != null && tv.area < absoluteMinArea;
                        });
                    
                    if (!anyTooSmall) {
                        updateGameState();
                        splitSuccessful = true;
                        break;
                    } else {
                        Tekton lastAdded = tectons.get(tectons.size()-1);
                        rollbackSplit(candidate, lastAdded, null);
                        printSortedTectons("State after rollback", totalArea);
                    }
                }
            }
            if (splitSuccessful) break;
        }

    
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

    private boolean attemptSplit(Tekton original, TektonVisualData originalTvd, double minArea, int attempt) {
        if (originalTvd.area < 2 * minArea) return false;

        previousNeighbors = new HashMap<>();
        for (Tekton t : tectons) {
            previousNeighbors.put(t.getId(), 
                t.getSzomszedok().stream()
                    .map(Tekton::getId)
                    .collect(Collectors.toSet())
            );
        }

        Point splitPoint = calculatePersistentSplitPoint(originalTvd, attempt);
        
        if (isOutOfBounds(splitPoint)) {
            return false;
        }

        Tekton newTekton = new Tekton(getRandomTectonType());
        newTekton.setId(nextTektonId++);
        TektonVisualData newTvd = new TektonVisualData(splitPoint);

        Bounds originalBounds = new Bounds(
            originalTvd.minX, originalTvd.maxX,
            originalTvd.minY, originalTvd.maxY
        );

        tektonVisualData.put(newTekton.getId(), newTvd);
        tectons.add(newTekton);

        jatekLogika.setJatekter(tectons);

        calculatePartialAreas(original, newTekton);

        if (!validateSplitAreas(originalTvd, newTvd, minArea) ||
            !validateSplitGeometry(originalTvd, newTvd)) {
            rollbackSplit(original, newTekton, originalBounds);
            return false;
        }

        Tekton splitResult = original.kettetores();
        if (splitResult == null) {
            rollbackSplit(original, newTekton, originalBounds);
            return false;
        }
        
        original.ujSzomszed(newTekton);
        newTekton.ujSzomszed(original);
        calculateAllNeighbors();
        original.ujSzomszed(newTekton);
        newTekton.ujSzomszed(original);

        if (!validateNeighborInheritance(original, newTekton) || 
            !validateAllNeighborsAfterSplit(original, newTekton)) {
            rollbackSplit(original, newTekton, originalBounds);
            return false;
        }

        assignColor(newTekton);
        return true;
    }

    private boolean desperateSplitAttempt(Tekton original, TektonVisualData originalTvd, double minArea) {
        System.out.println("ajaj");
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
            
            if (originalTvd.area < minArea * 1.5 || newTvd.area < minArea * 1.5) {
                rollbackSplit(original, newTekton, null);
                continue;
            }
            
            Tekton splitResult = original.kettetores();
            if (splitResult == null) {
                rollbackSplit(original, newTekton, null);
                continue;
            }
            
            original.ujSzomszed(newTekton);
            newTekton.ujSzomszed(original);
            calculateAllNeighbors();
            assignColor(newTekton);

            return true;
        }
        return false;
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

    // Modify the area validation in attemptSplit:
private boolean validateSplitAreas(TektonVisualData originalTvd, TektonVisualData newTvd, double minArea) {
    double combinedMin = minArea * 1.4; // Slightly more lenient
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
    
    private void printSortedTectons(String message, double totalArea) {
        /*System.out.println("\n[DEBUG] " + message);
        System.out.println("--------------------------------------------------");
        System.out.println("ID\tArea\t% of Total\tPosition");*/
        
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
                /*if (tvd != null) {
                    System.out.printf("%d\t%.1f\t%.2f%%\t\t(%.1f,%.1f)%n",
                        t.getId(),
                        tvd.area,
                        (tvd.area/totalArea)*100,
                        tvd.position.x,
                        tvd.position.y);
                    }*/
            });
        //System.out.println("--------------------------------------------------");
    }

    private Point calculatePersistentSplitPoint(TektonVisualData tvd, int attempt) {
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
        
        double x = innerMinX + rand.nextDouble() * (innerMaxX - innerMinX);
        double y = innerMinY + rand.nextDouble() * (innerMaxY - innerMinY);
        
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

    private static class TektonVisualData {
        Point position;
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        double area = 0;
        
        public TektonVisualData(Point position) {
            this.position = position;
        }
    }

    /*static class Point {
        final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
        public double distance(Point p) { return Math.hypot(x-p.x, y-p.y); }
    }*/

    class DrawingPanel extends JPanel {
        private static final int HOVER_RADIUS = 15;
        private Integer hoveredTektonId = null;
        
        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    hoveredTektonId = null;
                    repaint();
                }
            });
            
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e) {
                    Tekton t = findClickedTekton(e.getX(), e.getY());
                    hoveredTektonId = (t != null) ? t.getId() : null;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (diagramImage != null) {
                g.drawImage(diagramImage, 0, 0, this);
            }

            drawTektonSites((Graphics2D) g);

            if (hoveredTektonId != null) {
                Tekton t = tectons.stream()
                    .filter(tek -> tek.getId() == hoveredTektonId)
                    .findFirst()
                    .orElse(null);

                if (t != null && tektonVisualData.containsKey(t.getId())) {
                    TektonVisualData tvd = tektonVisualData.get(t.getId());
                    Graphics2D g2d = (Graphics2D) g.create();
                    
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
            for (Elolenyek rovar : rovarok.values()) {
                rovar.rajzol(g);
            }
        }

        private void drawSingleTektonSite(Graphics2D g, Tekton t, TektonVisualData tvd) {
            int x = (int) Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.position.x));
            int y = (int) Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.position.y));
            
            g.setColor(Color.BLACK);
            g.fillOval(x-3, y-3, 6, 6);
            
            g.setColor(Color.WHITE);
            String num = Integer.toString(t.getId());
            FontMetrics fm = g.getFontMetrics();
            g.drawString(num, x - fm.stringWidth(num)/2, y + fm.getAscent()/3);
        }
        
        private void drawTektonSites(Graphics2D g) {
            g.setFont(new Font("Arial", Font.BOLD, 14));
            for (Tekton t : tectons) {
                TektonVisualData tvd = tektonVisualData.get(t.getId());
                if (tvd != null) {
                    drawSingleTektonSite(g, t, tvd);
                }
            }
        }
    }

    private static class Bounds {
        final double minX, maxX, minY, maxY;
        
        public Bounds(double minX, double maxX, double minY, double maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }


        private void drawSingleTektonSite(Graphics2D g, Tekton t, TektonVisualData tvd) {
            // Ensure position is within visible area
            int x = (int) Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.position.x));
            int y = (int) Math.max(TECTON_SIZE, Math.min(WINDOW_SIZE - TECTON_SIZE, tvd.position.y));
            
            g.setColor(Color.BLACK);
            g.fillOval(x-3, y-3, 6, 6);
            
            g.setColor(Color.WHITE);
            String num = Integer.toString(t.getId());
            FontMetrics fm = g.getFontMetrics();
            g.drawString(num, x - fm.stringWidth(num)/2, y + fm.getAscent()/3);
        }
        

    /////////////////////////////////////////////
    ///Mouse Interaction / Tekton Detection/////
    ////////////////////////////////////////////

    private Tekton findClickedTekton(int x, int y) {
        // First check if we clicked near a Tekton center
        for (Tekton t : tectons) {
            TektonVisualData tvd = tektonVisualData.get(t.getId());
            if (tvd != null) {
                double distance = Math.hypot(x - tvd.position.x, y - tvd.position.y);
                if (distance <= 10) { // 10 pixel radius around center
                    return t;
                }
            }
        }
        
        // If not near a center, check which region was clicked
        if (closestMap != null && 
            x >= 0 && x < closestMap.length && 
            y >= 0 && y < closestMap[x].length) {
            int index = closestMap[x][y];
            if (index >= 0 && index < tectons.size()) {
                return tectons.get(index);
            }
        }
        return null;
    }

    Color color;
    private void assignColor(Tekton tekton) {
        if (tekton == null) return;

        // This mapping is correct - just ensure tekton.getTulajdonsagok() returns the real type
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


    private int findClosestSiteIndex(int x, int y) {
        int closest = -1;
        double minDist = Double.MAX_VALUE;
        
        for (int i = 0; i < tectons.size(); i++) {
            TektonVisualData tvd = tektonVisualData.get(tectons.get(i).getId());
            if (tvd != null) {
                double dx = tvd.position.x - x;
                double dy = tvd.position.y - y;
                double distSq = dx*dx + dy*dy;
                
                if (distSq < minDist) {
                    minDist = distSq;
                    closest = i;
                }
            }
        }
        return closest;
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

     //////////////
    ///rovar/////
    //////////////
 private Map<Integer, Elolenyek> rovarok = new HashMap<>();
private Map<Integer, Point> tektonCenters = new HashMap<>(); // Tekton középpontjai


private void calculateTektonCenters() {
    tektonCenters.clear();
    for (Tekton t : tectons) {
        TektonVisualData tvd = tektonVisualData.get(t.getId());
        if (tvd != null) {
            // Számoljuk ki a tekton középpontját a vizuális adatok alapján
            int centerX = (int) ((tvd.minX + tvd.maxX) / 2);
            int centerY = (int) ((tvd.minY + tvd.maxY) / 2);
            tektonCenters.put(t.getId(), new Point(centerX, centerY));
        }
    }
}
public void addRovar(Rovar rovar, int tektonId) {
    Point position = tektonCenters.get(tektonId);
    if (position != null) {
        Elolenyek eloleny = new Elolenyek(rovar, position);
        rovarok.put(rovar.getId(), eloleny);
        System.out.println(rovar.getId());
    }
    drawingPanel.repaint();
}

public void moveRovar(Rovar rovar, int newTektonId) {
    Elolenyek eloleny = rovarok.get(rovar.getId());
    Point newPosition = tektonCenters.get(newTektonId);
    System.out.println("tyuha");
    
    if (eloleny != null && newPosition != null && tektonVisualData.containsKey(newTektonId)) {
        TektonVisualData tvd = tektonVisualData.get(newTektonId);
        // Random pozíció a tekton területén belül (80%-ban a területen belül)
        int offsetX = (int)((Math.random() * 0.4 - 0.2) * (tvd.maxX - tvd.minX));
        int offsetY = (int)((Math.random() * 0.4 - 0.2) * (tvd.maxY - tvd.minY));
        
        Point targetPos = new Point(
            (int)(newPosition.getX() + offsetX),
            (int)(newPosition.getY() + offsetY)
        );
        
        eloleny.moveTo(rovar.getHol(), targetPos, () -> {
            rovar.lep(jatekLogika.getTektonById(newTektonId));
            drawingPanel.repaint();
        });
        drawingPanel.repaint(); // Force repaint to show the animation
    }
}   

} 