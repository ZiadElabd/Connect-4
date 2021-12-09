package Main.logic;

import Main.logic.Heuristic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

import javax.sound.midi.Soundbank;

public class MinMax {

    class node {

        int[][] state;
        int score;
        int best;

        node(int score, int best, int[][] state) {
            this.best = best;
            this.score = score;
            this.state = state ;
        }

    }

    // tree nodes to be printed
    private Hashtable<Integer, ArrayList<node>> tree = new Hashtable<>();

    // map each state to its best cost
    private HashMap<String, node> vis = new HashMap<>();
    int count=0;

    private int solve(int[][] state, int depth, boolean maxPlayer, int lev) {
        count++;
        if (depth == 0 || isTerminal(state)) {
            int cost = Heuristic.evaluate(state);
            tree.get(lev).add(new node(cost, -1, state));
            return cost;
        }

        String stateAsString = stateGenerator(state);

        if (vis.containsKey(stateAsString)) {
            tree.get(lev).add(vis.get(stateAsString));
            return vis.get(stateAsString).score;
        }

        int res, best = -1;

        if (maxPlayer) {
            res = Integer.MIN_VALUE;
            for (int i = 0; i < 7; i++) {
                int[][] nextState = getNextState(state, i, true);
                if (nextState == null)
                    continue;
                int cost = solve(nextState, depth - 1, false, lev + 1);
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
                int cost = solve(nextState, depth - 1, true, lev + 1);
                if (cost < res) {
                    res = cost;
                    best = i;
                }
            }
        }
        node info = new node(res, best, state);
        vis.put(stateAsString, info);
        tree.get(lev).add(info);
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
        for (int j = 0; j < 7; j++)
            if (state[0][j] == 0)
                return false;
        return true;
    }

    public int[][] solveAPI(int[][] state, int depth, boolean maxPlayer) {
        count=0;
        for (int i = 0; i <= depth; i++) {
            tree.put(i, new ArrayList<>());
        }
	vis.clear();
        long startTime = System.currentTimeMillis();
        solve(state, depth, maxPlayer, 0);
        long timeElapsed = System.currentTimeMillis() - startTime;
        int[][] ret = getNextState(state, vis.get(stateGenerator(state)).best, false);

       /* System.out.println("----------------NEW Game-------------------");
        for (int i = 0; i <= depth; i++) {
            int size = tree.get(i).size();
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < size; k++) {
                    System.out.print(Arrays.toString(tree.get(i).get(k).state[j]) + " | ");
                }
                System.out.println();
            }
            for (int k = 0; k < size; k++) {
                System.out.print(
                "value:" +tree.get(i).get(k).score + " " +"Move:"+tree.get(i).get(k).best + "    | ");
            }
            System.out.println("\n" + "*******nextLevel**********");
        }*/
        System.out.println("Time:"+timeElapsed+" Nodes:"+count);

        return ret;
    }

}
