package fungorium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Elolenyek {
    private static Map<Integer, Palyakep.TektonVisualData> tektonVisualData = new HashMap<>();
    
    public static void setTektonVisualData(Map<Integer, Palyakep.TektonVisualData> data) {
        tektonVisualData = data;
    }

    private static Map<Integer, Integer> tektonNRovarok = new HashMap<>(); //Rovarok száma egy adott tektonon
    
    private Rovar rovar;
    private GombaTest gombaTest;
    private GombaFonal gombaFonal;

    private static int meret = 32; // Rovar/Gomba mérete
    private Color color;
    private Point currentPos;
    private Point targetPos;
    private Timer animationTimer;
    private boolean isAnimating = false;

    public Elolenyek(Rovar rovar, Point initialPos) {
        this.rovar = rovar;
        this.currentPos = new Point(initialPos);
        this.targetPos = new Point(initialPos);
        int i = 0;
        if(tektonNRovarok.get(rovar.getHol().getId()) != null)
            i = tektonNRovarok.get(rovar.getHol().getId());
        tektonNRovarok.put(rovar.getHol().getId(), i+1);
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

    public Elolenyek(Spora spora, Point initialPos) {
        this.currentPos = new Point(initialPos);
        this.targetPos = new Point(initialPos);
    }

    private void setColorBasedOnPlayer() {
        if (rovar != null) {
            if (rovar.getKie() == null) {
                color = Color.GRAY;
                return;
            }
            color = rovar.getKie().getSzin();
        } else if (gombaTest != null) {
            if (gombaTest.getKie() == null) {
                color = Color.GRAY;
                return;
            }
            color = gombaTest.getKie().getSzin();
        } else if (gombaFonal != null) {
            if (gombaFonal.getKie() == null) {
                color = Color.GRAY;
                return;
            }
            color = gombaFonal.getKie().getSzin();
        }
    }

    private void setTektonNRovarok(){
        JatekLogika  jl = new JatekLogika();
        List<Tekton> tektons = jl.getJatekter();
        for (Tekton tekton : tektons) {
            tektonNRovarok.put(tekton.getId(),tekton.getRovarok().size());
        }
    }

    //Modositott rajzolás
    public void rajzol(Graphics g) {
        if (rovar == null && gombaTest == null && gombaFonal == null) return;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (rovar != null) {
            setTektonNRovarok();
            Palyakep.TektonVisualData tvd = tektonVisualData.get(rovar.getHol().getId());
            Point position = (tvd != null ? tvd.position : null);

            Point rPos[] = {
                new Point(-20, 20), new Point(0, 20), new Point(20, 20),
                new Point(-20, 0),                      new Point(20, 0),
                new Point(-20, -20), new Point(0, -20), new Point(20, -20)
            };

            int n = 0;
            if(tektonNRovarok.get(rovar.getHol().getId()) != null)
                n = (int) tektonNRovarok.get(rovar.getHol().getId());

            position = new Point((int)position.getX() + rPos[(n+1)%8].x, (int)position.getY() + rPos[(n+1)%8].y);

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
            int y = (int)position.getY() - meret/2;

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

                    // Vonal rajzolása
                    g2d.setColor(color);  // A játékos színét használjuk
                    g2d.setStroke(new BasicStroke(5));  // Vastagabb vonal
                    g2d.drawLine(
                            (int) (p1.x), (int) (p1.y),
                            (int) (p2.x), (int) (p2.y)
                    );
                }
            }
        }
    }

    @Override
public String toString() {
    if (this.getRovar() != null) {
        return "Rovar #" + getRovar().getId() + " a Tekton #" + getRovar().getHol().getId() + "-on";
    } else if (this.getGombaTest() != null) {
        return "Gombatest #" + getGombaTest().getId() + " a Tekton #" + getGombaTest().getTekton().getId() + "-on";
    } else if (this.getGombaFonal() != null) {
        return "Gombafonal #" + getGombaFonal().getId() + " két Tekton között: " +
               getGombaFonal().getTekton1().getId() + " és " + getGombaFonal().getTekton2().getId();
    }
    return "Ismeretlen élőlény";
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

    public GombaTest getGombaTest() {
        return gombaTest;
    }

    public GombaFonal getGombaFonal() {
        return gombaFonal;
    }

    public void eltuntet() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        this.rovar = null;
    }

    public int getId() {
        return rovar != null ? rovar.getHol().getId() : gombaTest != null ? gombaTest.getTekton().getId() : gombaFonal.getTekton1().getId();
    }
}