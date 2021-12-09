package Main.Gui;


import Main.logic.AlphaBeta;
import Main.logic.MinMax;

import java.awt.*;
import java.util.Arrays;

public class Screen {
    int humanScore = 0, computerScore = 0;
    public int width;
    public int height;
    public int[] pixels;
    public int[][] tiles = new int[10][9];
    public int[][] grid = new int[6][7];
    int gameType = 0;   // 0 -> Minimax, 1 -> AlphaBeta
    int depth = 1;
    MinMax solver_one = new MinMax();
    AlphaBeta solver_two = new AlphaBeta();


    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        this.init();
        this.render();
    }

    public void init(){
        for (int i = 0; i < 6; i++) {
            Arrays.fill(grid[i],0);
        }
        int[] temp_grid = SpriteSheet.grid.pixels;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                if (temp_grid[x + y * SpriteSheet.grid.width] == 0xFF2B2EFF){
                    tiles[y][x] = -1;
                }else if (temp_grid[x + y * SpriteSheet.grid.width] == 0xFFFF2B1C){
                    tiles[y][x] = 0;
                }
            }
        }
    }

    public void render(){
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                if (tiles[y][x] == 0)
                    renderSprite(x, y, Sprite.white);
                else if (tiles[y][x] == 1)
                    renderSprite(x, y, Sprite.red);
                else if (tiles[y][x] == 2)
                    renderSprite(x, y, Sprite.yellow);
                else
                    renderSprite(x, y, Sprite.background);
            }
        }
    }

    public void renderSprite(int xp, int yp , Sprite sprite){
        for (int y = 0; y < sprite.SIZE; y++) {
            int ya = y + yp * 32;
            for (int x = 0; x < sprite.SIZE; x++) {
                int xa = x + xp * 32;
                int color = sprite.pixels[x + y * sprite.SIZE];
                if (color != 0xFFFF00FF)
                    pixels[xa + ya * width] = color;
                else
                    pixels[xa + ya * width] = 0xFF5C5AEA;
            }
        }
    }

    public void update(){
        for (int y = 0; y < 6; y++ ) {
            System.arraycopy(grid[y], 0, tiles[y + 1], 1, 7);
        }
    }

    public void choose(int col){
        int i = 0;
        while (i < 6 && grid[i][col] == 0) i++;
        if (i > 6 || i <= 0){
            System.out.println("full collum");
            return;
        }
        grid[i - 1][col] = 1;
        humanScore = calculate_score(1);
        if (gameType == 0)
            grid = solver_one.solveAPI(grid, this.depth, false);
        else
            grid = solver_two.slove(grid, this.depth);
        computerScore = calculate_score(2);
        isTermial();
    }

    public void isTermial(){
        for (int i = 0; i < 7; i++) {
            if (grid[0][i] == 0)
                return;
        }
        Game.running = false;
    }

    int calculate_score(int player){
        int score = 0;
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                if (grid[y][x] == player){
                    boolean pointX = true, pointY = true, pointZ1 = true, pointZ2 = true;
                    for (int k = 0; k < 4; k++) {
                        if(x + k >= 7 || grid[y][x + k] != player)
                            pointX = false;
                        if (y + k >= 6 || grid[y + k][x] != player)
                            pointY = false;
                        if (x + k >= 7 || y - k < 0 || grid[y - k][x + k] != player)
                            pointZ1 = false;
                        if (x + k >= 7 || y + k >= 6 || grid[y + k][x + k] != player)
                            pointZ2 = false;
                    }
                    if (pointX) score++;
                    if (pointY) score++;
                    if (pointZ1) score++;
                    if (pointZ2) score++;
                }
            }
        }
        return score;
    }

}
