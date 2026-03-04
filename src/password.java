public class password {
    public static void main (String[] args){

        int pin = 0;
        int guess = 0;

        pin = InOut.readInt("Wunsch Pin, je länger desto länger dauert es!");

        for(int i = 0; i < pin; i++){
            guess++;
            System.out.println(guess);
        }

        System.out.println("Dein gewählter Pin: " + pin);
    }
}
