package Main.logic;

import Main.logic.Heuristic;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.midi.Soundbank;

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

    class node {

        int score;
        int best;

        node(int score, int best) {
            this.best = best;
            this.score = score;
        }
    }

    // tree nodes to be printed
    private ArrayList<treeNode> tree = new ArrayList<>();

    // map each state to its best cost
    private HashMap<String, node> vis = new HashMap<>();


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

    private int solve(int[][] state, int depth, boolean maxPlayer) {

        if (depth == 0 || isTerminal(state)) {
            return Heuristic.evaluate(state);
        }

        String stateAsString = stateGenerator(state);

        if (vis.containsKey(stateAsString))
          return vis.get(stateAsString).score;

        int res, best = -1;

        if (maxPlayer) {
            res = Integer.MIN_VALUE;
            for (int i = 0; i < 7; i++) {
                int[][] nextState = getNextState(state, i, true);
                if (nextState == null)
                    continue;
                int cost = solve(nextState, depth - 1, false);
                if (cost > res) {
                    res = cost;
                    best = i;
                }
            }

        } else {
            res = Integer.MAX_VALUE;
            for (int i = 0; i < 7; i++) {
                int[][] nextState = getNextState(state, i, false);
                if (nextState == null)
                    continue;
                int cost = solve(nextState, depth - 1, true);
                if (cost < res) {
                    res = cost;
                    best = i;
                }
            }
        }
        vis.put(stateAsString, new node(res, best));
        return res;

    }

    private String stateGenerator(int state[][]) {

        StringBuilder ret = new StringBuilder("");
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

        for (int i = 5; i >= 0; i--)
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

    public int[][] solveAPI(int[][] state, int depth, boolean maxPlayer) {
        vis.clear();
        solve(state, depth, maxPlayer);
        System.out.println("best move is " + vis.get(stateGenerator(state)).best);
        return getNextState(state, vis.get(stateGenerator(state)).best, false);
    }

    public static void main(String[] args) {
        int[][] state = new int[6][7];
        MinMax solver = new MinMax();
        state[5][0] = 1;
        int[][] nextMove = solver.solveAPI(state, 9, false);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++)
                System.out.print(nextMove[i][j] + " ");
            System.out.println();
        }
    }
}