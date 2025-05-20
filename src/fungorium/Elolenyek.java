package fungorium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


public class Elolenyek {
    private static Map<Integer, Palyakep.TektonVisualData> tektonVisualData = new HashMap<>();
    
    public static void setTektonVisualData(Map<Integer, Palyakep.TektonVisualData> data) {
        tektonVisualData = data;
    }
    
    private Rovar rovar;
    private GombaTest gombaTest;
    private GombaFonal gombaFonal;

    private static int meret = 32; // Rovar mérete
    private Color color;
    private Point currentPos;
    private Point targetPos;
    private Timer animationTimer;
    private boolean isAnimating = false;

    public Elolenyek(Rovar rovar, Point initialPos) {
        this.rovar = rovar;
        this.currentPos = new Point(initialPos);
        this.targetPos = new Point(initialPos);
        setColorBasedOnPlayer();
    }

    public Elolenyek(GombaTest gombaTest, Point initialPos) {
        this.gombaTest = gombaTest;
        this.currentPos = new Point(initialPos);
        this.targetPos = new Point(initialPos);
        setColorBasedOnPlayer();
    }

    public Elolenyek(GombaFonal gombaFonal, Point initialPos, Point initialPos2) {
        this.gombaFonal = gombaFonal;
        this.currentPos = new Point(initialPos);
        this.targetPos = new Point(initialPos2);
        setColorBasedOnPlayer();
    }

    private void setColorBasedOnPlayer() {
        if (rovar != null) {
            if (rovar.getKie() == null) {
                color = Color.YELLOW;
                return;
            }
            int playerId = Math.abs(rovar.getKie().getNev().hashCode()) % 4;
            switch (playerId) {
                case 0: color = Color.RED; break;
                case 1: color = Color.BLUE; break;
                case 2: color = Color.GREEN; break;
                case 3: color = Color.MAGENTA; break;
                default: color = Color.YELLOW;
            }
        } else if (gombaTest != null) {
            if (gombaTest.getKie() == null) {
                color = Color.YELLOW;
                return;
            }
            int playerId = Math.abs(gombaTest.getKie().getNev().hashCode()) % 4;
            switch (playerId) {
                case 0: color = Color.RED; break;
                case 1: color = Color.BLUE; break;
                case 2: color = Color.GREEN; break;
                case 3: color = Color.MAGENTA; break;
                default: color = Color.YELLOW;
            }
        } else if (gombaFonal != null) {
            if (gombaFonal.kie == null) {
                color = Color.YELLOW;
                return;
            }
            int playerId = Math.abs(gombaFonal.kie.getNev().hashCode()) % 4;
            switch (playerId) {
                case 0: color = Color.RED; break;
                case 1: color = Color.BLUE; break;
                case 2: color = Color.GREEN; break;
                case 3: color = Color.MAGENTA; break;
                default: color = Color.YELLOW;
            }
        }
    }

    //Modositott rajzolás
    public void rajzol(Graphics g) {
        if (rovar == null && gombaTest == null && gombaFonal == null) return;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (rovar != null) {
            Palyakep.TektonVisualData tvd = tektonVisualData.get(rovar.getHol().getId());
            Point position = (tvd != null ? tvd.position : null);
            int[] xPoints = {
                (int)position.getX(),
                (int)position.getX() - meret/2,
                (int)position.getX() + meret/2
            };
            int[] yPoints = {
                (int)position.getY() - meret/2,
                (int)position.getY() + meret/2,
                (int)position.getY() + meret/2
            };

        // Háromszög kitöltése játékos színével
            g2d.setColor(color);
            g2d.fillPolygon(xPoints, yPoints, 3);

            // Keret rajzolása (állapottól függően)
            g2d.setColor(getStateColor());
            g2d.drawPolygon(xPoints, yPoints, 3);
        } 
        
         if (gombaTest != null) {
            Palyakep.TektonVisualData tvd = tektonVisualData.get(gombaTest.getTekton().getId());
            Point position = (tvd != null ? tvd.position : null);
            int x = (int)position.getX() - meret/2;
            int y = (int)position.getY()- meret/2;

            // Kör kitöltése játékos színével
            g2d.setColor(color);
            g2d.fillOval(x, y, meret, meret);
        
            // Keret rajzolása
            g2d.setColor(getStateColor());
            g2d.drawOval(x, y, meret, meret);
        }

        // Gombafonalak rajzolása
        if (gombaFonal != null) {
            List<Tekton> kapcsoltTektonok = gombaFonal.getKapcsoltTektonok();
            if (kapcsoltTektonok.size() == 2) {
                Tekton t1 = kapcsoltTektonok.get(0);
                Tekton t2 = kapcsoltTektonok.get(1);
                
                Palyakep.TektonVisualData tvd1 = tektonVisualData.get(t1.getId());
                Palyakep.TektonVisualData tvd2 = tektonVisualData.get(t2.getId());
                
                if (tvd1 != null && tvd2 != null) {
                    Point p1 = tvd1.position;
                    Point p2 = tvd2.position;
                    
                    // Középpont kiszámítása
                    int centerX = (p1.x + p2.x) / 2;
                    int centerY = (p1.y + p2.y) / 2;
                    
                    // Merőleges irányvektor kiszámítása
                    double dx = p2.x - p1.x;
                    double dy = p2.y - p1.y;
                    double length = Math.sqrt(dx * dx + dy * dy);
                    
                    // Normalizálás és 90 fokos elforgatás
                    double perpX = dx / length * 30;  // 10 a vonal hossza
                    double perpY = dy / length * 30;
                    
                    // Vonal rajzolása
                    g2d.setColor(color);  // A játékos színét használjuk
                    g2d.setStroke(new BasicStroke(5));  // Vastagabb vonal
                    g2d.drawLine(
                        (int)(centerX - perpX), (int)(centerY - perpY),
                        (int)(centerX + perpX), (int)(centerY + perpY)
                    );
                }
            }
        }
    }

    private Color getStateColor() {
        if (rovar != null) {
            switch (rovar.getAllapot()) {
                case BENULT: return Color.BLUE;
                case GYORSITOTT: return Color.RED;
                case LASSITOTT: return Color.CYAN;
                case VAGASKEPTELEN: return Color.MAGENTA;
                default: return Color.BLACK;
            }
        } 
         if (gombaTest != null) {
            return Color.BLACK;  // vagy más szín a gombatesthez
        }
        return Color.BLACK;
    }

    public void moveTo(Tekton newTekton, Point newPosition, Runnable onComplete) {
        if (isAnimating) {
            animationTimer.stop();
        }

        this.targetPos = new Point(newPosition);
        this.isAnimating = true;

        animationTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lépésenkénti mozgatás
                double dx = targetPos.getX() - currentPos.getX();
                double dy = targetPos.getY() - currentPos.getY();
                double distance = Math.sqrt(dx*dx + dy*dy);

                if (distance < 1.0) {
                    // Animáció befejezése
                    currentPos.setLocation(targetPos);
                    animationTimer.stop();
                    isAnimating = false;
                    if (onComplete != null) onComplete.run();
                } else {
                    // Közelítés a célponthoz
                    double speed = Math.min(5, distance / 5);
                    currentPos.setLocation(
                        currentPos.getX() + dx/distance * speed,
                        currentPos.getY() + dy/distance * speed
                    );
                }
            }
        });
        animationTimer.start();
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public Point getPosition() {
        return new Point(currentPos);
    }

    public Rovar getRovar() {
        return rovar;
    }

    public void eltuntet() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        this.rovar = null;
    }
}