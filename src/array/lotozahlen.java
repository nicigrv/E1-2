package array;

public class lottozahlen{
    public static void main(String[] args) { 
    int[] kugel = new int[50]; 

    // Bestimmung der Lottozahlen 
    int zufallszahl; 
    for (int i = 1; i <= 6; i++) { 
      do { 
        zufallszahl = (int) (Math.random()*49) + 1; 
      } while (kugel[zufallszahl] != 0); 
      kugel[zufallszahl] = i; 
    } // end of for 
    
    // Ausgabe der Lottozahlen 
    String ausgabe = ""; 
    for (int i = 0; i < 50; i++) { 
      if (kugel[i] > 0) 
        ausgabe = ausgabe + ", " + i; 
    } 
    
    ausgabe = ausgabe.substring(2); 
    System.out.println(ausgabe + "\n"); 

  }  
}