package E1.Methoden;

public class Main {
    public static void main(String[] args) {
        int mw2;
        
        mw2 = methoden.mittelwert(4, 8);
        System.out.println("Mittelwert von 4 und 8: " + mw2);

        
        int x = methoden.random(10, 20);
        System.out.println("Zufällige Zahl zwischen 10 und 20: " + x);

        
        int mw3 = methoden.mittelwert(3, 9, 7);
        System.out.println("Mittelwert von 3, 9, 7: " + mw3);

        
        boolean gerade = methoden.gerade(12);
        System.out.println("Ist 12 gerade? " + gerade);

        
        String hallo = methoden.hallo("Nicolas");
        System.out.println(hallo);

        
        int[] umZahl = methoden.zahl(42);
        System.out.println("Zahlen um 42: " + umZahl[0] + ", " + umZahl[1]);

        System.out.print("test");

        int aufgabe = methoden.aufgabe(3,7,12);
        System.out.println("Die höchste Zahl ist: " + aufgabe);
    }
}