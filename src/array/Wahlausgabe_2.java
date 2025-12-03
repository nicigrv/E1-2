package array;

public class Wahlausgabe_2 {
    public static void main(String[] agrs) {
        
        int[] stimmen = new int[3];
        double[] anteil = new double[3];
        double summe; 
        
        stimmen[0] = InOut.readInt("Stimmen Max    :");
        stimmen[1] = InOut.readInt("Stimmen Lea    :");
        stimmen[2] = InOut.readInt("Stimmen Franz  :");

        summe = stimmen[1] + stimmen[2] + stimmen[0];
        anteil[0] = stimmen[0] / summe * 100;
        anteil[1] = stimmen[1] / summe * 100;
        anteil[2] = stimmen[2] / summe * 100;

        System.out.println("Anteil Max  : " + anteil[0] + " %"); 
        System.out.println("Anteil Lea  : " + InOut.format2(anteil[1]) + " %"); 
        System.out.println("Anteil Franz: " + InOut.formatN(anteil[2], 7) + " %"); 

    }
}
