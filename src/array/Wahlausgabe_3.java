package array;

public class Wahlausgabe_3 {
    public static void main(String[] agrs) {
        
        int[] stimmen = new int[3];
        double[] anteil = new double[3];
        double summe; 
        String[] name = new String[3];
        
        name[0] = "Max"; 
        name[1] = "Lea"; 
        name[2] = "Franz";
        
        for(int i = 0; i < stimmen.length; i++){
            stimmen[i] = InOut.readInt("Stimmen " + name[i]);
        }
        
        
        summe = stimmen[1] + stimmen[2] + stimmen[0];
        anteil[0] = stimmen[0] / summe * 100;
        anteil[1] = stimmen[1] / summe * 100;
        anteil[2] = stimmen[2] / summe * 100;

        for(int i = 0; i < stimmen.length; i++){
            System.out.println("Anteil " + name[i] +" "+ anteil[i] +" %");
        }
         

    }
}
