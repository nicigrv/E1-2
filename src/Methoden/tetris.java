package Methoden;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

    public class Tetris extends JPanel implements ActionListener {
        // Spielfeld-Dimensionen
        private static final int BREITE = 10;
        private static final int HOEHE = 20;
        private static final int BLOCK_GROESSE = 30;

        // Spielfeld als 2D-Array (0 = leer, 1-7 = verschiedene Farben)
        private int[][] spielfeld;

        // Aktueller Stein
        private int[][] aktuellerStein;
        private int steinX;
        private int steinY;
        private int steinFarbe;

        // Alle Tetromino-Formen
        private int[][][] steine = {
                {{1,1,1,1}},                    // I-Stein
                {{1,1},{1,1}},                  // O-Stein
                {{0,1,0},{1,1,1}},              // T-Stein
                {{1,0,0},{1,1,1}},              // L-Stein
                {{0,0,1},{1,1,1}},              // J-Stein
                {{0,1,1},{1,1,0}},              // S-Stein
                {{1,1,0},{0,1,1}}               // Z-Stein
        };

        private Timer timer;
        private boolean spielLaeuft;
        private int punkte;

        public Tetris() {
            spielfeld = new int[HOEHE][BREITE];
            spielLaeuft = true;
            punkte = 0;

            // Fenster-Eigenschaften
            setPreferredSize(new Dimension(BREITE * BLOCK_GROESSE + 200, HOEHE * BLOCK_GROESSE));
            setBackground(Color.BLACK);
            setFocusable(true);

            // Tastatur-Steuerung
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (!spielLaeuft) return;

                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                        bewegeStein(-1, 0);
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                        bewegeStein(+1, 0);
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                        bewegeStein(0,+1);
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {

                        rotiereStein();
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                        lasseSteinFallen();
                    }
                    repaint();
                }
            });

            neuerStein();
            timer = new Timer(500, this);
            timer.start();
        }

        // Erstellt einen neuen zufälligen Stein
        private void neuerStein() {
            int zufallsIndex = (int)(Math.random() * steine.length);
            aktuellerStein = kopiereArray(steine[zufallsIndex]);
            steinFarbe = zufallsIndex + 1;
            steinX = BREITE / 2 - aktuellerStein[0].length / 2;
            steinY = 0;

            if (!istPositionGueltig(steinX, steinY)) {
                spielLaeuft = false;
            }
        }

        // Kopiert ein 2D-Array
        private int[][] kopiereArray(int[][] original) {
            int[][] kopie = new int[original.length][original[0].length];
            for (int i = 0; i < original.length; i++) {
                for (int j = 0; j < original[i].length; j++) {
                    kopie[i][j] = original[i][j];
                }
            }
            return kopie;
        }

        // Bewegt den Stein um dx und dy
        private void bewegeStein(int dx, int dy) {

               int px = steinX + dx;
                int py = steinY + dy;

                if(istPositionGueltig(px, py)){
                steinX = px;
                steinY = py;


        } else if(dy > 0) {
            // Stein kann nicht weiter nach unten
            fixiereStein();
            loescheVolleZeilen();
            neuerStein();
        }
    }

    // Prüft, ob Position gültig ist
    private boolean istPositionGueltig(int x, int y) {
        for (int i = 0; i < aktuellerStein.length; i++) {
            for (int j = 0; j < aktuellerStein[i].length; j++) {
                if (aktuellerStein[i][j] == 1) {
                    int neuX = x + j;
                    int neuY = y + i;

                    // Prüfe Grenzen
                    if (neuX < 0 || neuX >= BREITE || neuY >= HOEHE) {
                        return false;
                    }

                    // Prüfe Kollision (nur wenn nicht oberhalb des Spielfelds)
                    if (neuY >= 0 && spielfeld[neuY][neuX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Rotiert den Stein um 90 Grad im Uhrzeigersinn
    private void rotiereStein() {
        int[][] rotiert = new int[aktuellerStein[0].length][aktuellerStein.length];

        for (int i = 0; i < aktuellerStein.length; i++) {
            for (int j = 0; j < aktuellerStein[i].length; j++) {
                rotiert[j][aktuellerStein.length - 1 - i] = aktuellerStein[i][j];
            }
        }

        int[][] alt = aktuellerStein;
        aktuellerStein = rotiert;

        if (!istPositionGueltig(steinX, steinY)) {
            aktuellerStein = alt;
        }
    }

    // Lässt Stein sofort fallen
    private void lasseSteinFallen() {
        // TODO Stein schrittweise nach unten fallen lassen (bis nicht mehr möglich)

        while(istPositionGueltig(steinX, steinY+1)) {
            steinY = steinY + 1;

        }
        fixiereStein();
        loescheVolleZeilen();
        neuerStein();


    // Fixiert den Stein im Spielfeld
    private void fixiereStein() {
        for (int i = 0; i < aktuellerStein.length; i++) {
            for (int j = 0; j < aktuellerStein[i].length; j++) {
                if (aktuellerStein[i][j] == 1) {
                    int y = steinY + i;
                    int x = steinX + j;
                    if (y >= 0) {
                        spielfeld[y][x] = steinFarbe;
                    }
                }
            }
        }
    }

    // Löscht volle Zeilen und verschiebt darüberliegende nach unten
    private void loescheVolleZeilen() {
        for (int i = HOEHE - 1; i >= 0; i--) {
            if (istZeileVoll(i)) {
                entferneZeile(i);
                i++; // Prüfe diese Zeile nochmal
                punkte += 100;
            }
        }
    }

    // Prüft, ob eine Zeile voll ist
    private boolean istZeileVoll(int zeile) {
        for(int i; i < spielfeld.length; i++){


        }



    // Entfernt eine Zeile und verschiebt alle darüber nach unten
    private void entferneZeile(int zeile) {
        // TODO implementieren
    }

    // Timer-Event: Stein fällt automatisch
    public void actionPerformed(ActionEvent e) {
        if (spielLaeuft) {
            bewegeStein(0, 1);
            repaint();
        }
    }

    // Zeichnet das Spielfeld
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Zeichne fixierte Blöcke
        for (int i = 0; i < HOEHE; i++) {
            for (int j = 0; j < BREITE; j++) {
                if (spielfeld[i][j] != 0) {
                    zeichneBlock(g, j, i, spielfeld[i][j]);
                }
            }
        }

        // Zeichne aktuellen Stein
        if (spielLaeuft) {
            for (int i = 0; i < aktuellerStein.length; i++) {
                for (int j = 0; j < aktuellerStein[i].length; j++) {
                    if (aktuellerStein[i][j] == 1) {
                        zeichneBlock(g, steinX + j, steinY + i, steinFarbe);
                    }
                }
            }
        }

        // Zeichne Spielfeld-Rahmen
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, BREITE * BLOCK_GROESSE, HOEHE * BLOCK_GROESSE);

        // Zeichne Punkte und Anleitung
        g.setColor(Color.WHITE);
        g.drawString("Punkte: " + punkte, BREITE * BLOCK_GROESSE + 20, 30);
        g.drawString("Steuerung:", BREITE * BLOCK_GROESSE + 20, 70);
        g.drawString("← → : Bewegen", BREITE * BLOCK_GROESSE + 20, 90);
        g.drawString("↓ : Schneller", BREITE * BLOCK_GROESSE + 20, 110);
        g.drawString("↑ : Rotieren", BREITE * BLOCK_GROESSE + 20, 130);
        g.drawString("Leertaste: Fall", BREITE * BLOCK_GROESSE + 20, 150);

        if (!spielLaeuft) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("GAME OVER", 50, HOEHE * BLOCK_GROESSE / 2);
        }
    }

    // Zeichnet einen einzelnen Block mit Farbe
    private void zeichneBlock(Graphics g, int x, int y, int farbe) {
        Color[] farben = {
                null,
                Color.CYAN,     // I
                Color.YELLOW,   // O
                Color.MAGENTA,  // T
                Color.ORANGE,   // L
                Color.BLUE,     // J
                Color.GREEN,    // S
                Color.RED       // Z
        };

        g.setColor(farben[farbe]);
        g.fillRect(x * BLOCK_GROESSE, y * BLOCK_GROESSE, BLOCK_GROESSE, BLOCK_GROESSE);
        g.setColor(Color.BLACK);
        g.drawRect(x * BLOCK_GROESSE, y * BLOCK_GROESSE, BLOCK_GROESSE, BLOCK_GROESSE);
    }

    public static void main(String[] args) {
        JFrame fenster = new JFrame("Tetris");
        Tetris spiel = new Tetris();

        fenster.add(spiel);
        fenster.pack();
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);
    }
}
}
