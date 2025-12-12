package Methoden;

public class methoden{
    
    public static int mittelwert(int x, int y){
        return (x+y)/2;
    }
    
    public static int random(int min, int max){
        double r = Math.random()*(max-min)+min; 
        return (int)r; 
    }
    
    public static int mittelwert(int a, int b, int c){
        if ((a <= b && b <= c) || (c <= b && b <= a)){
            return b;
        }if ((b <= a && a <= c) || (c <= a && a <= b)){
            return a;
        }else {
            return c;
        }
    }
    
    public static boolean gerade(int x){
        int r = x % 2;
        if(r==0){
            return true;
        } else{
            return false;
        }
    }

    public static String hallo(String name){
        String hallo = "Hallo " + name;
        return hallo;
    }

    public static int[] zahl (int eingabe){

    int[] zahl = new int[2];

    zahl[0] = eingabe - 1;
    zahl[1] = eingabe + 1;

    return zahl;
    }

    public static int aufgabe (int x,int y,int z) {
        if (x>y && x>z) {
            return x;
        }else if (y>x && y>z) {
            return y;
        }else {
            return z;
        }
    }
    
}
