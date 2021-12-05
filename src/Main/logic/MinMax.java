package Main.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class MinMax {

    // to print the tree
    class treeNode {

        int[][] state;
        int lev;

        treeNode(int state[][], int lev) {
            this.lev = lev;
            this.state = state;
        }
    }

    private ArrayList<treeNode> tree = new ArrayList<>();
    private HashMap<String, Integer> vis = new HashMap<>();

    private int solve(int[][] state, int depth, boolean maxPlayer) {

        if (depth == 0 || isTerminal(state))
            return -1; // return heurisitic

        String stateAsString = stateGenerator(state);
        if (vis.containsKey(stateAsString))
            return vis.get(stateAsString);

        int res;

        if (maxPlayer) {
            res = Integer.MIN_VALUE;
            for (int i = 0; i < 7; i++) {
                int[][] nextState = getNextState(state, i, true);
                res = Math.max(res, solve(nextState, depth - 1, false));
            }

        } else {
            res = Integer.MAX_VALUE;
            for (int i = 0; i < 7; i++) {
                int[][] nextState = getNextState(state, i, false);
                res = Math.min(res, solve(nextState, depth - 1, true));
            }
        }
        vis.put(stateAsString, res);
        return res;

    }

    private String stateGenerator(int state[][]) {

        StringBuilder ret = new StringBuilder("") ;
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                ret.append(Integer.toString(state[i][j]));

        return ret.toString();
    }

    private int[][] copyState(int[][] state) {
        int ret[][] = new int[6][7];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                ret[i][j] = state[i][j];
        return ret;
    }

    private int[][] getNextState(int[][] curr_state, int col, boolean maxPlayer) {

        int[][] state = copyState(curr_state);

        for (int i = 0; i < 6; i++)
            if (state[i][col] == 0) {
                state[i][col] = maxPlayer ? 1 : 2;
                return state;
            }
        return null;
    }

    private boolean isTerminal(int state[][]) {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                if (state[i][j] == 0)
                    return false;
        return true;
    }

    public ArrayList<treeNode> getTree() {
        return this.tree;
    }

    public int solveAPI(int[][] state, int depth, boolean maxPlayer) {
        return solve(state, depth, maxPlayer);
    }
}