package E1.pacman;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;


public class Highscore {

    private static ArrayList<String> highscore = new ArrayList<>();
    private static String pfad = "highscore.txt" ;
    private static final int SIZE = 10;
    private static final String TRENNZEICHEN = ",";




    private static void initHighscore() {
        highscore.add("3567,Kunibert");
        highscore.add("3543,Hannes");
        highscore.add("3267,Gandalf");
        highscore.add("2567,Hannibal");
        highscore.add("2367,Wolfgang");
        highscore.add("2267,Juno");
        highscore.add("1967,Uno");
        highscore.add("1767,Dos");
        highscore.add("1367,Win");
        highscore.add("967,Ich");
    }

    private static void newHighscore(int neuerScore, String name) {
        for (int i = 0; i < highscore.size(); i++) {
            String wert = highscore.get(i).split(TRENNZEICHEN)[0];
            int scoreZeile = Integer.parseInt(wert);

            if (neuerScore > scoreZeile) {
                highscore.add(i, neuerScore + TRENNZEICHEN + name);
                highscore.remove(highscore.size() - 1);
                break;
            }
        }
    }
    private static void highscoreWriter() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pfad))) {
            for (String score : highscore) {
                writer.write(score);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void highscoreReader() {
        try (BufferedReader reader = new BufferedReader(new FileReader(pfad))) {
            String line;
            highscore.clear();
            while ((line = reader.readLine()) != null) {
                highscore.add(line.trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // Falls keine Textdatei vorhanden ist: Textdatei anlegen und initHighscore()
            highscore.clear();
            initHighscore();
            highscoreWriter();
        }

        // Falls weniger als 10 Scores vorhanden sind: mit "0" auffüllen
        for (int i = highscore.size(); i < SIZE; i++) {
            highscore.add("0");
        }
    }



    public static void speichern_Highscore(int score, String name) {
        highscoreReader();
        newHighscore(score, name);
        highscoreWriter();
    }

    public static ArrayList<String> getHighscore() {
        highscoreReader();
        return highscore;
    }
}

