package E1.pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    final int[][] spielfeld;

    // Datei für Leaderboard
    private static final String DATEI_NAME = "pacman_scores.txt";

    // Namenseingabe
    private JTextField nameEingabe;
    private JButton nameBestaetigen;
    private JButton restartButton;

    // Pacman Position
    private int pacmanX;
    private int pacmanY;

    // Pacman Richtung
    private int richtungX = 1;
    private int richtungY = 0;

    //Anzahl Geister
    private static final int ANZGEIST = 4;
    // Geister
    private int[] geistX = new int[ANZGEIST];
    private int[] geistY = new int[ANZGEIST];
    private int[] geistRichtungX = new int[ANZGEIST];
    private int[] geistRichtungY = new int[ANZGEIST];
    private Color[] geistFarben = {Color.RED, Color.CYAN, Color.MAGENTA, Color.GREEN};

    private Timer bewegungsTimer;
    private Timer sekundenTimer;
    private boolean spielLaeuft;
    private int punkte;
    private int gesamtPunkte;
    private int zeitSekunden = 0;

    // Für Anzeige der Top 5
    private ArrayList<ScoreEintrag> top5Scores;

    //Punkte speichern
    class ScoreEintrag {
        String name;
        int punkte;
        int zeit;
        String datum;
        String uhrzeit;

        public ScoreEintrag(String name, int punkte, int zeit, String datum, String uhrzeit) {
            this.name = name;
            this.punkte = punkte;
            this.zeit = zeit;
            this.datum = datum;
            this.uhrzeit = uhrzeit;
        }
    }

    public Pacman() {
        spielfeld = new int[HOEHE][BREITE];
        spielLaeuft = true;
        punkte = 0;
        gesamtPunkte = 0;
        top5Scores = new ArrayList<>();

        // Lade Top 5 beim Start
        ladeTop5();

        // Initialisiere Spielfeld
        initialisiereSpielfeld();

        // Fenster-Eigenschaften
        setPreferredSize(new Dimension(BREITE * BLOCK_GROESSE + 200, HOEHE * BLOCK_GROESSE));
        setBackground(Color.BLACK);
        setFocusable(true);
        setLayout(null);

        // Namenseingabe initialisieren
        nameEingabe = new JTextField(15);
        nameEingabe.setBounds(BREITE * BLOCK_GROESSE / 2 - 75,
                HOEHE * BLOCK_GROESSE / 2 + 50,
                150, 30);
        nameEingabe.setVisible(false);

        nameBestaetigen = new JButton("Speichern");
        nameBestaetigen.setBounds(BREITE * BLOCK_GROESSE / 2 - 50,
                HOEHE * BLOCK_GROESSE / 2 + 90,
                100, 30);
        nameBestaetigen.setVisible(false);

        restartButton = new JButton("Neustart");
        restartButton.setBounds(BREITE * BLOCK_GROESSE / 2 - 50,
                HOEHE * BLOCK_GROESSE / 2 + 130,
                100, 30);
        restartButton.setVisible(false);

        add(nameEingabe);
        add(nameBestaetigen);
        add(restartButton);

        // ActionListener für Speichern-Button
        nameBestaetigen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameEingabe.getText();

                if (!name.isEmpty()) {
                    // Speichere Score in Datei
                    speichereScoreInDatei(name, punkte, zeitSekunden);
                    ladeTop5();

                    // Verstecke Eingabefelder, zeige Restart-Button
                    nameEingabe.setVisible(false);
                    nameBestaetigen.setVisible(false);
                    nameEingabe.setText("");
                    restartButton.setVisible(true);

                    repaint();
                }
            }
        });

        // ActionListener für Restart-Button
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartSpiel();
            }
        });

        // Tastatur-Steuerung
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!spielLaeuft) return;

                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    richtungX = -1;
                    richtungY = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    richtungX = 1;
                    richtungY = 0;
                } else if (key == KeyEvent.VK_UP) {
                    richtungX = 0;
                    richtungY = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    richtungX = 0;
                    richtungY = 1;
                }
            }
        });

        // Timer für die Bewegung (300ms)
        bewegungsTimer = new Timer(300, this);
        bewegungsTimer.start();

        // Timer für die Zeit (1000ms = 1 Sekunde)
        sekundenTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (spielLaeuft) {
                    zeitSekunden++;
                    repaint();
                }
            }
        });
        sekundenTimer.start();
    }

    // Speichert einen Score in die Textdatei
    private void speichereScoreInDatei(String name, int punkte, int zeit) {
        try {

            //Uhrzeit & Tag Formatieren
            LocalDateTime jetzt = LocalDateTime.now();
            DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter zeitFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

            //Speichern von Datum und Zeit des aktuellen eintrages
            String datum = jetzt.format(datumFormat);
            String uhrzeit = jetzt.format(zeitFormat);

            //FW öffnen & Datei aus dem Verzeichnis mit der globalen Variable öffnen
            FileWriter fw = new FileWriter(DATEI_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);

            //In die Datei die Informationen mit Semikolon getrennt aus den Variablen speichern
            bw.write(name + ";" + punkte + ";" + zeit + ";" + datum + ";" + uhrzeit);

            //Zeilenumbruch in die nächste Zeile
            bw.newLine();

            //File Wirter und Bufferd Writer beenden und schließen
            bw.close();
            fw.close();

            //Mögliche Fehlermeldung finden und ausgeben
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    // Lädt alle Scores aus der Datei und ermittelt die Top 5
    private void ladeTop5() {

        //Temporäre array liste für die lokale Speicherung bereinigen
        top5Scores.clear();
        ArrayList<ScoreEintrag> alleScores = new ArrayList<>();

        try {
            //Prüfung, ob die Datei exsiert und wenn ja, dann
            File datei = new File(DATEI_NAME);
            if (!datei.exists()) {
                return;
            }

            //File Reader starten und Datei öffnen
            FileReader fr = new FileReader(datei);
            BufferedReader br = new BufferedReader(fr);

            //Übergabe String für die jeweilige Zeile erstellen
            String zeile;
            //Wenn keine Zeile mehr, dann beendne
            while ((zeile = br.readLine()) != null) {
                //Inhalte in array Liste auslesen
                String[] teile = zeile.split(";");
                if (teile.length == 5) {
                    String name = teile[0];
                    int punkte = Integer.parseInt(teile[1]);
                    int zeit = Integer.parseInt(teile[2]);
                    String datum = teile[3];
                    String uhrzeit = teile[4];

                    //Speichern der Codes
                    alleScores.add(new ScoreEintrag(name, punkte, zeit, datum, uhrzeit));
                }
            }

            br.close();
            fr.close();

            Collections.sort(alleScores, new Comparator<ScoreEintrag>() {
                public int compare(ScoreEintrag s1, ScoreEintrag s2) {
                    if (s1.punkte != s2.punkte) {
                        return s2.punkte - s1.punkte;
                    }
                    return s1.zeit - s2.zeit;
                }
            });

            // Nimm nur die Top 5
            for (int i = 0; i < Math.min(5, alleScores.size()); i++) {
                top5Scores.add(alleScores.get(i));
            }

        } catch (IOException e) {
            System.err.println("Fehler beim Laden: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Fehler beim Parsen der Datei: " + e.getMessage());
        }
    }

    // Startet das Spiel neu
    private void restartSpiel() {
        restartButton.setVisible(false);

        spielLaeuft = true;
        punkte = 0;
        gesamtPunkte = 0;
        zeitSekunden = 0;

        richtungX = 1;
        richtungY = 0;

        initialisiereSpielfeld();

        bewegungsTimer.start();
        sekundenTimer.start();

        requestFocusInWindow();

        repaint();
    }

    private void initialisiereSpielfeld() {
        int[][] muster = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
                {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
                {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
                {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
                {1,2,1,1,2,1,2,1,1,1,1,1,1,2,1,2,1,1,2,1},
                {1,2,2,2,2,1,2,2,2,1,1,2,2,2,1,2,2,2,2,1},
                {1,1,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,1,1},
                {1,1,1,1,2,1,2,2,2,2,2,2,2,2,1,2,1,1,1,1},
                {1,2,2,2,2,2,2,1,1,0,0,1,1,2,2,2,2,2,2,1},
                {1,1,1,1,2,1,2,1,1,1,1,1,1,2,1,2,1,1,1,1},
                {1,1,1,1,2,1,2,2,2,2,2,2,2,2,1,2,1,1,1,1},
                {1,1,1,1,2,1,2,1,1,1,1,1,1,2,1,2,1,1,1,1},
                {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
                {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
                {1,2,2,1,2,2,2,2,2,0,0,2,2,2,2,2,1,2,2,1},
                {1,1,2,1,2,1,2,1,1,1,1,1,1,2,1,2,1,2,1,1},
                {1,2,2,2,2,1,2,2,2,1,1,2,2,2,1,2,2,2,2,1},
                {1,2,1,1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,2,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        for (int i = 0; i < HOEHE; i++) {
            for (int j = 0; j < BREITE; j++) {
                spielfeld[i][j] = muster[i][j];
                if (spielfeld[i][j] == PUNKT) gesamtPunkte++;
            }
        }
        pacmanX = 9;
        pacmanY = 15;
        if (spielfeld[pacmanY][pacmanX] == PUNKT) {
            spielfeld[pacmanY][pacmanX] = LEER;
            gesamtPunkte--;
        }
        // Initialisiere Geister
        geistX[0] = 9; geistY[0] = 9;
        geistRichtungX[0] = 1; geistRichtungY[0] = 0;
        geistX[1] = 10; geistY[1] = 9;
        geistRichtungX[1] = -1; geistRichtungY[1] = 0;
        geistX[2] = 9; geistY[2] = 10;
        geistRichtungX[2] = 0; geistRichtungY[2] = 1;
        geistX[3] = 9; geistY[3] = 8;
        geistRichtungX[3] = 1; geistRichtungY[3] = 0;
    }

    private void bewegePacman(int dx, int dy) {
        int neuX = pacmanX + dx;
        int neuY = pacmanY + dy;
        if (istPositionGueltig(neuX, neuY)) {
            pacmanX = neuX;
            pacmanY = neuY;
            if (spielfeld[pacmanY][pacmanX] == PUNKT) {
                spielfeld[pacmanY][pacmanX] = LEER;
                punkte++;
                if (punkte >= gesamtPunkte) {
                    spielLaeuft = false;
                    bewegungsTimer.stop();
                    sekundenTimer.stop();
                    // Zeige Namenseingabe
                    nameEingabe.setVisible(true);
                    nameBestaetigen.setVisible(true);
                    nameEingabe.requestFocus();
                }
            }
            // Prüfe Kollision mit Geistern
            pruefeGeistKollision();
        }
    }

    private void bewegeGeister() {
        for (int i = 0; i < ANZGEIST; i++) {
            int neuX = geistX[i] + geistRichtungX[i];
            int neuY = geistY[i] + geistRichtungY[i];

            // Wenn Bewegung nicht möglich oder zufällig, wähle neue Richtung
            if (!istPositionGueltig(neuX, neuY) || Math.random() < 0.2) {
                for (int versuch = 0; versuch < 10; versuch++) {
                    int zufallRichtung = (int)(Math.random() * 4);

                    if (zufallRichtung == 0) {
                        geistRichtungX[i] = -1; geistRichtungY[i] = 0;
                    } else if (zufallRichtung == 1) {
                        geistRichtungX[i] = 1; geistRichtungY[i] = 0;
                    } else if (zufallRichtung == 2) {
                        geistRichtungX[i] = 0; geistRichtungY[i] = -1;
                    } else {
                        geistRichtungX[i] = 0; geistRichtungY[i] = 1;
                    }

                    neuX = geistX[i] + geistRichtungX[i];
                    neuY = geistY[i] + geistRichtungY[i];

                    if (istPositionGueltig(neuX, neuY)) break;
                }
            }
            if (istPositionGueltig(neuX, neuY)) {
                geistX[i] = neuX;
                geistY[i] = neuY;
            }
        }
        pruefeGeistKollision();
    }

    private void pruefeGeistKollision() {
        for (int i = 0; i < ANZGEIST; i++) {
            if (pacmanX == geistX[i] && pacmanY == geistY[i]) {
                spielLaeuft = false;
                bewegungsTimer.stop();
                sekundenTimer.stop();

                // Zeige Namenseingabe
                nameEingabe.setVisible(true);
                nameBestaetigen.setVisible(true);
                nameEingabe.requestFocus();
            }
        }
    }

    private boolean istPositionGueltig(int x, int y) {
        if (x < 0 || x >= BREITE || y < 0 || y >= HOEHE) return false;
        return spielfeld[y][x] != WAND;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (spielLaeuft) {
            bewegePacman(richtungX, richtungY);
            bewegeGeister();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Spielfeld zeichnen
        for (int i = 0; i < HOEHE; i++) {
            for (int j = 0; j < BREITE; j++) {
                if (spielfeld[i][j] == WAND) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * BLOCK_GROESSE, i * BLOCK_GROESSE, BLOCK_GROESSE, BLOCK_GROESSE);
                    g.setColor(Color.BLACK);
                    g.drawRect(j * BLOCK_GROESSE, i * BLOCK_GROESSE, BLOCK_GROESSE, BLOCK_GROESSE);
                } else if (spielfeld[i][j] == PUNKT) {
                    g.setColor(Color.YELLOW);
                    int d = BLOCK_GROESSE / 4;
                    g.fillOval(j * BLOCK_GROESSE + BLOCK_GROESSE/2 - d/2,
                            i * BLOCK_GROESSE + BLOCK_GROESSE/2 - d/2, d, d);
                }
            }
        }

        // Geister zeichnen
        for (int i = 0; i < ANZGEIST; i++) {
            g.setColor(geistFarben[i]);
            int x = geistX[i] * BLOCK_GROESSE;
            int y = geistY[i] * BLOCK_GROESSE;
            int groesse = (int)(BLOCK_GROESSE * 0.8);

            g.fillArc(x, y, groesse, groesse, 0, 180);
            g.fillRect(x, y + groesse/2, groesse, groesse/2);

            g.setColor(Color.BLACK);
            int wellenBreite = groesse / 3;
            for (int w = 0; w < ANZGEIST; w++) {
                int[] xPunkte = {x + w * wellenBreite,
                        x + w * wellenBreite + wellenBreite/2,
                        x + w * wellenBreite + wellenBreite};
                int[] yPunkte = {y + groesse, y + groesse - wellenBreite/2, y + groesse};
                g.fillPolygon(xPunkte, yPunkte, 3);
            }

            g.setColor(Color.WHITE);
            g.fillOval(x + groesse/4, y + groesse/4, groesse/5, groesse/3);
            g.fillOval(x + groesse/2, y + groesse/4, groesse/5, groesse/3);

            g.setColor(Color.BLUE);
            g.fillOval(x + groesse/4 + 2, y + groesse/3, groesse/8, groesse/6);
            g.fillOval(x + groesse/2 + 2, y + groesse/3, groesse/8, groesse/6);
        }

        // Pacman zeichnen
        g.setColor(Color.YELLOW);
        int startWinkel = 0;
        if (richtungX == 1) startWinkel = 45;
        else if (richtungX == -1) startWinkel = 225;
        else if (richtungY == -1) startWinkel = 135;
        else if (richtungY == 1) startWinkel = 315;

        g.fillArc(pacmanX * BLOCK_GROESSE, pacmanY * BLOCK_GROESSE,
                (int)(BLOCK_GROESSE*0.8), (int)(BLOCK_GROESSE*0.8),
                startWinkel, 270);

        // UI rechts zeichnen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Punkte: " + punkte, BREITE * BLOCK_GROESSE + 20, 30);
        g.drawString("Zeit: " + zeitSekunden + " sek", BREITE * BLOCK_GROESSE + 20, 55);
        g.drawString("Steuerung: Pfeiltasten", BREITE * BLOCK_GROESSE + 20, 90);

        // Top 5 Leaderboard anzeigen
        if (!top5Scores.isEmpty()) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Top 5:", BREITE * BLOCK_GROESSE + 20, 130);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            int startY = 150;

            for (int i = 0; i < top5Scores.size(); i++) {
                ScoreEintrag score = top5Scores.get(i);
                String zeile1 = (i+1) + ". " + score.name + " - " + score.punkte + "P";
                String zeile2 = "   " + score.zeit + "s | " + score.datum;

                g.drawString(zeile1, BREITE * BLOCK_GROESSE + 20, startY + i * 35);
                g.drawString(zeile2, BREITE * BLOCK_GROESSE + 20, startY + i * 35 + 12);
            }
        }


        if (!spielLaeuft) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            if (punkte >= gesamtPunkte) {
                g.setColor(Color.BLACK);
                g.drawRect(HOEHE, BREITE, HOEHE, BREITE);

                g.setColor(Color.GREEN);
                g.drawString("GEWONNEN!", 100, HOEHE * BLOCK_GROESSE / 2);
            } else {

                g.setColor(Color.BLACK);
                g.fillRect(1,1,BREITE * BLOCK_GROESSE, HOEHE * BLOCK_GROESSE);
                repaint();

                g.setColor(Color.RED);
                g.drawString("GAME OVER!", BREITE*9, HOEHE * BLOCK_GROESSE / 2);

                g.setColor(Color.GREEN);
                g.drawString("Name:", BREITE*9, HOEHE*BLOCK_GROESSE/2 +40);
            }
        }
    }



    public static void main(String[] args) {
        JFrame fenster = new JFrame("Pacman");
        Pacman spiel = new Pacman();
        fenster.add(spiel);
        fenster.pack();
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);
    }
}