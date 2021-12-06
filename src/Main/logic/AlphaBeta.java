package Main.logic;

import java.util.*;

public class AlphaBeta {
    Heuristic heurisitic=new Heuristic();
    int bestMove;
    public int[][] slove(int[][] board,int depth){
        int col=minmax(board,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,false);
        return nextState(board,col,false);
    }
    public int  minmax(int[][] board,int depth,int alpha,int beta,boolean maxPlayer){
      if (depth==0||isTermial(board)) return heurisitic.h(board);
      if(maxPlayer){
          int value=Integer.MIN_VALUE;
          for (int i = 0; i < 7; i++) {
              int[][] newState=nextState(board,i,maxPlayer);
              if (newState==null) continue;
              value=Integer.max(value,minmax(newState,depth--,alpha,beta,!maxPlayer));
              alpha=Integer.max(alpha,value);
              if (alpha>=beta) break;
          }
          return value;
      }else{
          int value=Integer.MAX_VALUE;
          for (int i = 0; i < 7; i++) {
              int[][] newState=nextState(board,i,maxPlayer);
              if (newState==null) continue;
              int newValue=minmax(newState,depth--,alpha,beta,!maxPlayer);
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
        for (int i =6; i >=0; i++) {
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
