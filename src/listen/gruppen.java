package listen;


import java.util.LinkedList;
import java.util.Scanner;

    public class gruppen {
        public static void main(String[] args) {

            LinkedList<String> Schueler = new LinkedList<>();
            Scanner imput = new Scanner(System.in);

            boolean weiter = true;

            while (weiter) {
                System.out.println("Namen eingeben, 'stopp' zum aufhören");
                String eingabe = imput.nextLine();

                if (eingabe.equals("stopp")) { // immer equals für String-Vergleiche nutzen
                    weiter = false;
                    Schueler.remove("stopp");
                } else {
                    Schueler.add(eingabe);
                }
            }

            LinkedList<String> Schueler_kopie = new LinkedList<>(Schueler);
            LinkedList<String> Gruppe1 = new LinkedList<>();
            LinkedList<String> Gruppe2 = new LinkedList<>();
            LinkedList<String> Gruppe3 = new LinkedList<>();
            LinkedList<String> Gruppe4 = new LinkedList<>();


            boolean next = true;
            int counter = 0;
            while (next) {
                System.out.println("4 Gruppen bilden? (j/n)");
                String ausgabe = imput.nextLine();

                if (ausgabe.equals("n")) {
                    next = false;
                } else {
                    while (!Schueler_kopie.isEmpty()) {
                        counter++;
                        int zahl = (int) (Math.random() * Schueler_kopie.size());
                        String name = Schueler_kopie.get(zahl);
                        Schueler_kopie.remove(zahl);

                        if (counter == 1) {
                            Gruppe1.add(name);
                        }
                        if (counter == 2) {
                            Gruppe2.add(name);
                        }
                        if (counter == 3) {
                            Gruppe3.add(name);
                        }
                        if (counter == 4) {
                            Gruppe4.add(name);
                            counter = 0;
                        }
                    }
                    System.out.println("Gruppe 1: " + Gruppe1);
                    System.out.println("Gruppe 2: " + Gruppe2);
                    System.out.println("Gruppe 3: " + Gruppe3);
                    System.out.println("Gruppe 4: " + Gruppe4);
                    next = false;
                }
            }
        }
    }
