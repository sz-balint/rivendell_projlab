package fungorium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Elolenyek {
    private Rovar rovar;
    private static int meret = 16; // Rovar mérete
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

    private void setColorBasedOnPlayer() {
        if (rovar.getKie() == null) {
            color = Color.YELLOW; // Alapértelmezett szín
            return;
        }

        // Játékos színek (4 játékos)
       int playerId = Math.abs(rovar.getKie().getNev().hashCode()) % 4;
        switch (playerId) {
            case 0: color = Color.RED; break;
            case 1: color = Color.BLUE; break;
            case 2: color = Color.GREEN; break;
            case 3: color = Color.MAGENTA; break;
            default: color = Color.YELLOW;
        }
    }

    public void rajzol(Graphics g) {
        if (rovar == null) return;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Háromszög rajzolása a rovarhoz
        int[] xPoints = {
            (int)currentPos.getX(),
            (int)currentPos.getX() - meret/2,
            (int)currentPos.getX() + meret/2
        };
        int[] yPoints = {
            (int)currentPos.getY() - meret/2,
            (int)currentPos.getY() + meret/2,
            (int)currentPos.getY() + meret/2
        };

        // Háromszög kitöltése játékos színével
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Keret rajzolása (állapottól függően)
        g2d.setColor(getStateColor());
        g2d.drawPolygon(xPoints, yPoints, 3);

        // Rovar azonosító rajzolása
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(rovar.getId()), 
                      (int)currentPos.getX() - 3, 
                      (int)currentPos.getY() + 5);
    }

    private Color getStateColor() {
        if (rovar == null) return Color.BLACK;
        switch (rovar.getAllapot()) {
            case BENULT: return Color.BLUE;
            case GYORSITOTT: return Color.RED;
            case LASSITOTT: return Color.CYAN;
            case VAGASKEPTELEN: return Color.MAGENTA;
            default: return Color.BLACK;
        }
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