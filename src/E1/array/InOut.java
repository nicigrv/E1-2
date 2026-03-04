package E1.array;

import java.util.Scanner;

public class InOut
{
    public static int readInt(String text){
        System.out.println(text);
        Scanner sc = new Scanner(System.in);
        int zahl = sc.nextInt();
        return zahl;
    }

    public static double readDouble(String text){
        System.out.println(text);
        Scanner sc = new Scanner(System.in);
        double zahl = sc.nextDouble();
        return zahl;
    }

    public static float readFloat(String text){
        System.out.println(text);
        Scanner sc = new Scanner(System.in);
        float zahl = sc.nextFloat();
        return zahl;
    }

    public static boolean readBoolean(String text){
        System.out.println(text);
        Scanner sc = new Scanner(System.in);
        String wert = sc.nextLine();

        return (wert.equals("ja") || wert.equals("j") || wert.equals("y") || wert.equals("yes") ||
                wert.equals("1") || wert.equals("true") || wert.equals("Ja") || wert.equals("J") ||
                wert.equals("True"));
    }

    public static char readChar(String text){
        System.out.println(text);
        Scanner sc = new Scanner(System.in);
        char wert = sc.next().charAt(0);
        return wert;
    }

    public static String readString(String text){
        System.out.println(text);
        Scanner sc = new Scanner(System.in);
        String wert = sc.nextLine();
        return wert;
    }

    public static String format2(double zahl){
        String num = String.valueOf(zahl);
        int index = num.indexOf(".");
        if(index == -1)
            return num + "0";
        if(index+2 < num.length()){
            return num.substring(0, index+3);
        }
        else{  // genau eine Stelle nach dem Komma existiert
            return num + "0";
        }
    }

    public static String formatN(double zahl, int n){
        String num = String.valueOf(zahl);
        int index = num.indexOf(".");
        if(n>=0){

            if(index == -1){
                for(int i=0; i<n; i++){
                    num+="0";
                }
                return num;
            }
            if(index+n < num.length()){
                return num.substring(0, index+n+1);
            }
            else{
                int count = index+n-num.length()+1;
                for(int i=0; i<count; i++){
                    num+="0";
                }
                return num;
            }
        } else {
            return num;
        }
    }
}
