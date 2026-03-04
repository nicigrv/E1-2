package E1.array;

public class array_3 {
    public static void main(String[] args) {
        
        int[] zahlen= {1,9,2,2,2,2,2,3,4,5,6,7,8,8,6,3,9}; 
        int suche = InOut.readInt("Gebe zahl ein");
        int anzahl = 0;
        
        for (int i=0; i<zahlen.length; i++){
            if(suche == zahlen[i]){
                anzahl++;
            }
        }
        
        System.out.print("Die Zahl " + suche + " kommt " + anzahl + " mal vor");
    }
}