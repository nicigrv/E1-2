package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Pacman extends JPanel implements ActionListener {
    // Spielfeld-Dimensionen
    private static final int BREITE = 20;
    private static final int HOEHE = 20;
    private static final int BLOCK_GROESSE = 30;

    // Spielfeld-Elemente
    private static final int LEER = 0;
    private static final int WAND = 1;
    private static final int PUNKT = 2;

    // Spielfeld als 2D-Array
    private int[][] spielfeld;

    // Pacman Position
    private int pacmanX;
    private int pacmanY;

    // Pacman Richtung
    private int richtungX = 1;  // Startrichtung: nach rechts
    private int richtungY = 0;

    private Timer timer;
    private boolean spielLaeuft;
    private int punkte;
    private int gesamtPunkte;

    public Pacman() {
        spielfeld = new int[HOEHE][BREITE];
        spielLaeuft = true;
        punkte = 50;
        gesamtPunkte = 0;

        // Initialisiere Spielfeld
        initialisiereSpielfeld();

        // Fenster-Eigenschaften
        setPreferredSize(new Dimension(BREITE * BLOCK_GROESSE + 200, HOEHE * BLOCK_GROESSE));
        setBackground(Color.BLACK);
        setFocusable(true);

        // Tastatur-Steuerung
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!spielLaeuft) return;

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bewegePacman(-1, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bewegePacman(1, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bewegePacman(0, 1);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bewegePacman(0, -1);
                }
                repaint();
            }
        });

        timer = new Timer(500, this);
        timer.start();
    }

    // Initialisiert das Spielfeld mit Wänden und Punkten
    private void initialisiereSpielfeld() {
        // Fülle alles mit Punkten
        for (int i = 0; i < HOEHE; i++) {
            for (int j = 0; j < BREITE; j++) {
                spielfeld[i][j] = PUNKT;
                gesamtPunkte++;
            }
        }

        // Erstelle Rand-Wände
        for (int i = 0; i < HOEHE; i++) {
            spielfeld[i][0] = WAND;
            spielfeld[i][BREITE - 1] = WAND;
        }
        for (int j = 0; j < BREITE; j++) {
            spielfeld[0][j] = WAND;
            spielfeld[HOEHE - 1][j] = WAND;
        }

        // Erstelle einige Hindernisse in der Mitte
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 8; j++) {
                spielfeld[i][j] = WAND;
            }
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 12; j < 17; j++) {
                spielfeld[i][j] = WAND;
            }
        }

        for (int i = 10; i < 13; i++) {
            for (int j = 7; j < 13; j++) {
                spielfeld[i][j] = WAND;
            }
        }

        for (int i = 15; i < 18; i++) {
            for (int j = 3; j < 8; j++) {
                spielfeld[i][j] = WAND;
            }
        }

        for (int i = 15; i < 18; i++) {
            for (int j = 12; j < 17; j++) {
                spielfeld[i][j] = WAND;
            }
        }

        // Setze Pacman Startposition
        pacmanX = 1;
        pacmanY = 1;
        spielfeld[pacmanY][pacmanX] = LEER;
        gesamtPunkte--;
    }

    // Bewegt den Pacman um dx und dy
    private void bewegePacman(int dx, int dy) {
        int neuX = pacmanX + dx;
        int neuY = pacmanY + dy;

        if (istPositionGueltig(neuX, neuY)) {
            // Merke dir die Richtung
            richtungX = dx;
            richtungY = dy;

            pacmanX = neuX;
            pacmanY = neuY;

            // Sammle Punkt ein
            if (spielfeld[pacmanY][pacmanX] == PUNKT) {
                spielfeld[pacmanY][pacmanX] = LEER;
                punkte += 1;

                // Prüfe ob alle Punkte gesammelt wurden
                if (punkte >= gesamtPunkte) {
                    spielLaeuft = false;
                }
            }
        }
    }

    // Prüft, ob Position gültig ist (keine Wand)
    private boolean istPositionGueltig(int x, int y) {
        if (x < 0 || x >= BREITE || y < 0 || y >= HOEHE) {
            return false;
        }

        if (spielfeld[y][x] == WAND) {
            return false;
        }

        return true;
    }

    // Timer-Event
    public void actionPerformed(ActionEvent e) {
        if (spielLaeuft) {
            repaint();
        }
    }

    // Zeichnet das Spielfeld
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Zeichne Spielfeld
        for (int i = 0; i < HOEHE; i++) {
            for (int j = 0; j < BREITE; j++) {
                if (spielfeld[i][j] == WAND) {
                    zeichneWand(g, j, i);
                } else if (spielfeld[i][j] == PUNKT) {
                    zeichnePunkt(g, j, i);
                }
            }
        }

        // Zeichne Pacman
        zeichnePacman(g, pacmanX, pacmanY);

        // Zeichne Spielfeld-Rahmen
        g.setColor(Color.BLUE);
        g.drawRect(0, 0, BREITE * BLOCK_GROESSE, HOEHE * BLOCK_GROESSE);

        // Zeichne Punkte und Anleitung
        g.setColor(Color.WHITE);
        g.drawString("Punkte: " + punkte, BREITE * BLOCK_GROESSE + 20, 30);
        g.drawString("Steuerung:", BREITE * BLOCK_GROESSE + 20, 70);
        g.drawString("← → ↑ ↓ : Bewegen", BREITE * BLOCK_GROESSE + 20, 90);
        g.drawString("", BREITE * BLOCK_GROESSE + 20, 110);
        g.drawString("Ziel:", BREITE * BLOCK_GROESSE + 20, 140);
        g.drawString("Sammle alle Punkte!", BREITE * BLOCK_GROESSE + 20, 160);

        if (!spielLaeuft) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            if (punkte >= gesamtPunkte) {
                g.drawString("GEWONNEN!", 50, HOEHE * BLOCK_GROESSE / 2);
            } else {
                g.drawString("GAME OVER", 50, HOEHE * BLOCK_GROESSE / 2);
            }
        }
    }

    // Zeichnet eine Wand
    private void zeichneWand(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillRect(x * BLOCK_GROESSE, y * BLOCK_GROESSE, BLOCK_GROESSE, BLOCK_GROESSE);
        g.setColor(Color.black);
        g.drawRect(x * BLOCK_GROESSE, y * BLOCK_GROESSE, BLOCK_GROESSE, BLOCK_GROESSE);
    }

    // Zeichnet einen Punkt
    private void zeichnePunkt(Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);
        int punktGroesse = BLOCK_GROESSE/4;
        g.fillOval(x * BLOCK_GROESSE + BLOCK_GROESSE / 2 - punktGroesse / 2,
                y * BLOCK_GROESSE + BLOCK_GROESSE / 2 - punktGroesse / 2,
                punktGroesse, punktGroesse);
    }

    // Zeichnet Pacman
    private void zeichnePacman(Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);

        int px = x * BLOCK_GROESSE;
        int py = y * BLOCK_GROESSE;
        int size = (int) (BLOCK_GROESSE * 0.8);

        // Berechne Startwinkel basierend auf der Richtung
        int startWinkel = 0;

        if (richtungX == 1 && richtungY == 0) {      // rechts
            startWinkel = 45;
        } else if (richtungX == -1 && richtungY == 0) { // links
            startWinkel = 225;
        } else if (richtungX == 0 && richtungY == -1) { // oben
            startWinkel = 135;
        } else if (richtungX == 0 && richtungY == 1) {  // unten
            startWinkel = 315;
        }

        g.fillArc(px, py, size, size, startWinkel, 270);
    }

    public static void main(String[] args) {
        JFrame fenster = new JFrame("(PACMAN");
        Pacman spiel = new Pacman();

        fenster.add(spiel);
        fenster.pack();
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);
    }
}