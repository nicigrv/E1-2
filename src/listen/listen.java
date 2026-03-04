package listen;

import java.util.LinkedList;
import java.util.Scanner;

public class listen {
    public static void main(String[] args) {

        LinkedList<String> Schueler = new LinkedList<>();
        Scanner imput = new Scanner(System.in);

        boolean weiter = true;

        while (weiter) {
            System.out.println("Namen eingeben, 'stopp' zum aufhören");
            String eingabe = imput.nextLine();

            if (eingabe.equals("stopp")) {
                weiter = false;
                Schueler.remove("stopp");
            } else {
                Schueler.add(eingabe);
            }
        }

        LinkedList<String> Schueler_kopie = new LinkedList<>(Schueler);

        boolean next = true;
        while (next) {
            System.out.println("Schüler auswählen? (j/n)");
            String ausgabe = imput.nextLine();

            if (ausgabe.equals("n")) {
                next = false;
            } else {
                int zahl = (int) (Math.random() * Schueler_kopie.size());
                String aufrufen = Schueler_kopie.get(zahl);
                System.out.println(aufrufen);
                Schueler_kopie.remove(zahl);

                if (Schueler_kopie.isEmpty()) {
                    System.out.println("Alle einmal aufgerufen!");
                    System.out.println("Nochmal? (j/n)");
                    String ausgabee = imput.nextLine();
                    Schueler_kopie = new LinkedList<>(Schueler);

                    if (!ausgabee.equals("n")) {
                        Schueler_kopie = Schueler;
                    } else {
                        next = false;
                    }
                }

            }
        }
    }
}

