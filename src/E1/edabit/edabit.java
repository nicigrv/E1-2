package E1.edabit;

public class edabit {

    public class Program {
        public static int[] arrayOfMultiples(int num, int length) {

            int mulitplies = 1;
            int[] arrayOfMultiples = new int[length];
            for (int i = 0; i < arrayOfMultiples.length; i++) {
                arrayOfMultiples[i] = num * mulitplies;
                mulitplies++;
            }


            return arrayOfMultiples;
        }
    }

    public class Challenge {
        public String checkForSeven ( int[] arr){
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == 7) {  // Kein Semikolon hier!
                        return "Boom";
                    }
                }
                return "there is no 7 in the array";
            }
        }
    }


