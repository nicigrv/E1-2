package E1.array;

public class packman{
    public static void main (String [] args){
        
        int [][] board =
                        {{0,2,3,5,3,1,1,0},
                         {0,0,1,0,1,0,1,1},
                         {0,0,0,1,0,1,0,1},
                         {1,0,1,0,1,5,1,0},
                         {0,0,0,1,0,1,0,1},
                         {0,0,0,1,0,1,0,1},
                         {0,0,0,1,0,1,0,1},
                         {0,0,0,1,0,1,0,1}};
            
        for(int y=0; y < board.length; y++){
            for(int x=0; x < board.length; x++){
                System.out.print(board[y][x] + (" "));
            }
            System.out.println();
        }
         
    }
}