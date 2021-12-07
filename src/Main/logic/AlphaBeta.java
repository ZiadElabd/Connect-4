package Main.logic;

import java.util.*;

public class AlphaBeta {
    int bestMove;
    public int[][] slove(int[][] board,int depth){
        minmax(board,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,false);
        int[][] result=nextState(board,bestMove,false);
        System.out.println("-----------");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(result[i][j]+" ");
            }
            System.out.println();
        }
        return result;
    }
    private static int[][] evaluationTable = {
            { 3, 4, 5, 7, 5, 4, 3 },
            { 4, 6, 8, 10, 8, 6, 4 },
            { 5, 8, 11, 13, 11, 8, 5 },
            { 5, 8, 11, 13, 11, 8, 5 },
            { 4, 6, 8, 10, 8, 6, 4 },
            { 3, 4, 5, 7, 5, 4, 3 } };

    public int h(int[][] state) {
        int utility = 128;
        int sum = 0;
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                if (state[i][j] == 1)
                    sum += evaluationTable[i][j];
                else if (state[i][j] == 2)
                    sum -= evaluationTable[i][j];
        return utility + sum;
    }
    public int  minmax(int[][] board,int depth,int alpha,int beta,boolean maxPlayer){
      if (depth==0||isTermial(board)) return Heuristic.evaluate(board);
      if(maxPlayer){
          int value=Integer.MIN_VALUE;
          for (int i = 0; i < 7; i++) {
              int[][] newState=nextState(board,i,maxPlayer);
              if (newState==null) continue;
              value=Integer.max(value,minmax(newState,depth-1,alpha,beta,!maxPlayer));
              alpha=Integer.max(alpha,value);
              if (alpha>=beta) break;
          }
          return value;
      }else{
          int value=Integer.MAX_VALUE;
          for (int i = 0; i < 7; i++) {
              int[][] newState=nextState(board,i,maxPlayer);
              if (newState==null) continue;
              int newValue=minmax(newState,depth-1,alpha,beta,!maxPlayer);
              if (newValue<value){
                  value=newValue;
                  bestMove=i;
              }
              beta=Integer.min(value,beta);
              if (alpha>=beta) break;
          }
          return value;
      }
    }

    private boolean isTermial(int[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j]==0) return false;
            }
        }
        return true;
    }
    private int[][] nextState(int[][] board,int col,boolean maxPlayer){
        int[][] state= copyState(board);
        for (int i =5; i >=0; i--) {
            if(state[i][col]==0){
                state[i][col] = maxPlayer ? 1 : 2;
                return state;
            }
        }
        return null;
    }
    private int[][] copyState(int[][] state) {
        int ret[][] = new int[6][7];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                ret[i][j] = state[i][j];
        return ret;
    }
}
