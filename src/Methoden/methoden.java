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
        if ((a <= b && b <= c) || (c <= b && b <= a)) return b;
        if ((b <= a && a <= c) || (c <= a && a <= b)) return a;
        return c;
    }
    
    public static boolean gerade(int x){
        int r = x % 2;
        if(r==0){
             boolean y = true;
             return y; 
        } else{
            boolean y = false;
            return y;
        }
    }
    
    public static void konsole (){
    for(int i=0; i<3; i++){
    System.out.println("Hallo");}
    }
    
    public static String hallo(String name){
        return "Hallo " + name;
    }
    
    public static int[] zahl (int eingabe){
        
    int[] zahl = new int[2];
    
    zahl[0] = eingabe - 1;
    zahl[1] = eingabe + 1;
        
    return zahl; 
    }
    
    
}
