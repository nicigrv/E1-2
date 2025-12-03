package array;

public class array_6{
    public static void main (String[] agrs){
     int zahlen[] = new int[1000];
     boolean x = false;
     
     for (int i=0; i<zahlen.length; i++){
         while (x = false){
         zahlen[i] = InOut.readInt("Zahl eingeben:");
             if (zahlen[i] == 0){
                 x = true;
             }
            }
     }
     
     
        
    }
}