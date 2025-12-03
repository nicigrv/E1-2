package array;

public class array_5{
    public static void main (String[] agrs){
        int menge = InOut.readInt("Wie viele zahlen?");
        int[] zahlen = new int [menge];
        int summe = 0; 
         
        for (int i=0; i < menge; i++){
            zahlen[i] = InOut.readInt("Zahlen Eingeben");
            summe = summe + zahlen[i];
        }
        
        System.out.println("Summe " + summe);
        System.out.println("Durschnitt " + summe/menge);
    }
}