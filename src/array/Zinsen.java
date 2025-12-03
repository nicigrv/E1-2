package array;

public class Zinsen {
    public static void main(String[] args) {
                
        int jahre = InOut.readInt("Simulation für wie viele Jahre?");
        
        double[] kapital = new double[jahre + 1];
        kapital[0] = InOut.readDouble("Startkapital:");
        
        double zinsen = InOut.readDouble("Zinsen in %:");
        zinsen = zinsen / 100.0;
        
        for (int i = 0; i < jahre; i++) {
            kapital[i + 1] = kapital[i] * (1 + zinsen);
            System.out.println("Geld im " + (i + 1) + ". Jahr: " + kapital[i + 1]);
        }
    }
}
