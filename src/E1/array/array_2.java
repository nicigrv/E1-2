package E1.array;

public class array_2 {
    public static void main(String[] args) {
        int[] zahl = {5, 200, 9, 1, 5, 8};
        int x = zahl[0];

        for (int i = 0; i < zahl.length; i++) {
            if (x < zahl[i]) {
                x = zahl[i];
            }
        }
        
        System.out.println(x);
                
        for (int i = 0; i < zahl.length; i++) {
            if (x > zahl[i]) {
                x = zahl[i];
            }
        }
        

        System.out.println(x);
    }
}
