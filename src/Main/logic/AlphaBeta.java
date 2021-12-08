package Main.logic;

import java.util.*;

public class AlphaBeta {
    Hashtable<Integer,ArrayList<treeNode>> tree=new Hashtable<>();
    Hashtable<String,Vistednode> visited=new Hashtable<>();
    class treeNode{
        int[][] state;
        int heuristicValue;
        int bestMove;
        public treeNode(int[][] board,boolean maxPlayer){
            this.state=board;
            this.heuristicValue=maxPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            this.bestMove=0;
        }
    }
    class Vistednode{
        String stateString="";
        int heuristicValue;
        int bestmove;
        public Vistednode(int[][] state,int bestmove,int heuristicValue){
            for (int i = 0; i < 6; i++) {
                stateString+=Arrays.toString(state[i]);
            }
            this.heuristicValue=heuristicValue;
            this.bestmove=bestmove;
        }
    }
    public int[][] slove(int[][] board,int depth){
        for (int i = 0; i <= depth; i++) {
            tree.put(i,new ArrayList<>());
        }
        treeNode node=new treeNode(board,true);
        minmax(node,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,false,depth);
        int[][] result=nextState(board,node.bestMove,false).state;
       /* System.out.println("-----------");
        for (int i = 0; i <=depth; i++) {
            int size=tree.get(i).size();
            for (int j = 0; j <6; j++) {
                for (int k = 0; k < size; k++) {
                    System.out.print(Arrays.toString(tree.get(i).get(k).state[j])+" | ");
                }
                System.out.println();
            }
            for (int k = 0; k < size; k++) {
                System.out.print(tree.get(i).get(k).heuristicValue+" "+tree.get(i).get(k).bestMove+"                  | ");
            }
            System.out.println("\n"+"*******nextLevel**********");
        }*/
        return result;
    }

    public void  minmax(treeNode node,int depth,int alpha,int beta,boolean maxPlayer,int k){
        tree.get(k-depth).add(node);
        if (depth==0||isTermial(node.state)) {
            node.heuristicValue=Heuristic.evaluate(node.state);
            return ;
        }
        String stateString=stateToString(node.state);
        if (visited.containsKey(stateString)) {
            Vistednode temp=visited.get(stateString);
            node.heuristicValue=temp.heuristicValue;
            node.bestMove=temp.bestmove;
            return;
        }
        if(maxPlayer){
            int value=Integer.MIN_VALUE;
            for (int i = 0; i < 7; i++) {
                treeNode newState=nextState(node.state,i,maxPlayer);
                if (newState==null) continue;
                minmax(newState,depth-1,alpha,beta,!maxPlayer,k);
                value=Integer.max(node.heuristicValue,newState.heuristicValue);
                node.heuristicValue=value;
                alpha=Integer.max(alpha,node.heuristicValue);
                if (alpha>=beta) break;
            }
            Vistednode vis=new Vistednode(node.state,node.bestMove,node.heuristicValue);
            visited.put(vis.stateString,vis);
            return ;
        }else{
            int value=Integer.MAX_VALUE;
            for (int i = 0; i < 7; i++) {
                treeNode newState=nextState(node.state,i,maxPlayer);
                if (newState==null) continue;
                minmax(newState,depth-1,alpha,beta,!maxPlayer,k);
                if (newState.heuristicValue< node.heuristicValue){
                    node.heuristicValue=newState.heuristicValue;
                    node.bestMove=i;
                }
                beta=Integer.min(node.heuristicValue,beta);
                if (alpha>=beta) break;
            }
            Vistednode vis=new Vistednode(node.state,node.bestMove,node.heuristicValue);
            visited.put(vis.stateString,vis);
            return ;
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
    private treeNode nextState(int[][] board,int col,boolean maxPlayer){
        int[][] state= copyState(board);
        for (int i =5; i >=0; i--) {
            if(state[i][col]==0){
                state[i][col] = maxPlayer ? 1 : 2;
                return new treeNode(state,maxPlayer);
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
    private String stateToString(int[][] state){
        String stateString="";
        for (int i = 0; i < 6; i++) {
            stateString+=Arrays.toString(state[i]);
        }
        return stateString;
    }
}
