package array;

public class PackamanAufStoff {
    final static int LEER = 0;
    final static int PUNKT = 1;
    final static int WAND = 2;
    final static int PILLE = 3;
    final static int TOR = 4;
    final static int GEIST = 5;
    final static int PACMAN = 6;
    final static int BREITE = 16;
    final static int HÖHE = 16;

    public static void main(String[] args) {
        int[][] board = new int[HÖHE][BREITE];

        // Mit Punkten füllen
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y] = PUNKT;
            }
        }

        // Ränder als Wände setzen
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (x == 0 || x == HÖHE - 1) {
                    board[x][y] = WAND;
                }
                if (y == 0 || y == BREITE - 1) {
                    board[x][y] = WAND;
                }
            }
        }


        int x=0, y=0;
        while (board[x][y] != PUNKT) {
            x = (int) (Math.random() * (HÖHE - 2)) + 1;   
            y = (int) (Math.random() * (BREITE - 2)) + 1; 
        } 
        board[x][y] = PACMAN;

        int gesetzt = 0;
        while (gesetzt < 3) {
            x = (int) (Math.random() * (HÖHE - 2)) + 1;
            y = (int) (Math.random() * (BREITE - 2)) + 1;
            if (board[x][y] == PUNKT) {
                board[x][y] = GEIST;
                gesetzt++;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}