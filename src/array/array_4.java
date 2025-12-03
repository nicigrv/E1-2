package array;

public class array_4{
    public static void main (String[] args){
        int[] zahlen = {2,3,4,5,3,2,3,3,2,3,4,2,3,2,3,3,4,2,21,1,3,2,4,5,4,2};
        int gesucht = InOut.readInt("Prüfende Zahl");
        for(int i=0; i<zahlen.length; i++){
            if(gesucht == zahlen[i]){
                System.out.println(i);
            }
        }
    }
}