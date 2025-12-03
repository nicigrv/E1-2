package array;

public class tetris{
    final static int LEER=0; 
    final static int BLOCK=1; 
    final static int BREITE=8; 
    public static void main (String[] args){
        int [][] board =
                        {{0,0,0,0,0,0,0,1},
                        {1,1,1,1,1,1,1,1},
                        {0,0,1,0,0,1,0,0},
                        {0,0,0,0,0,0,0,0},
                        {1,1,1,1,1,1,1,1},
                        {1,0,0,0,0,0,0,0},
                        {1,1,1,1,1,1,1,1},
                        {0,1,0,1,0,1,1,1}};
                        
        for (int x = 0; x < board.length; x++) {
            int counter = 0;
            for (int y = 0; y < board[x].length; y++) {
                if(board[x][y] == BLOCK){
                    counter ++;
                }
                if(counter==BREITE){
                    System.out.println("reihe " + (x+1));
                    
                    for (int k = x; k > 0; k--) {
                        for (int c = 0; c < BREITE; c++) {
                            //board[k][c] = board[k-1][c];
                            
                            board[k]=board[k-1];
                             
                            if(board[k][c]==BLOCK) {
                                while(board[k+1][c]==LEER) {
                                    board[k+1][c]=board[k][c];
                                    board[k][c]=LEER;
                                }    
                            }
                                }
                            }
                        }
                    }
                  
                    for (int c = 0; c < BREITE; c++) {
                        board[0][c] = LEER;
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