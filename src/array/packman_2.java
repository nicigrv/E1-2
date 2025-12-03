package array;

public class packman_2 {
    public static void main (String [] args) {
        int [][] board =
            {{0,2,3,5,3,1,1,0},
            {0,0,1,0,1,0,1,1},
            {0,0,0,1,0,1,0,1},
            {1,0,1,0,1,5,1,0},
            {0,0,0,1,0,1,0,1},
            {0,0,0,1,0,1,0,1},
            {0,0,0,1,0,1,0,1},
            {0,0,0,1,0,1,1,1}};

        int i = 0;

        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                System.out.print(board[y][x] + " ");
                if(board[y][x] == 1) {
                    i++;
                }
            }
            System.out.println();
        }

        System.out.println("Anzahl der 1er auf dem Spielfeld: " + i);
    }
}