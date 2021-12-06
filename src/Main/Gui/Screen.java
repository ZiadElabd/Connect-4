package Main.Gui;


import Main.logic.AlphaBeta;
import Main.logic.MinMax;

public class Screen {
    public int width;
    public int height;
    public int[] pixels;
    public int[][] tiles = new int[10][9];
    public int[][] grid = new int[6][7];
    MinMax solver = new MinMax();
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
            for (int j = 0; j < 7; j++) {
                grid[i][j] = 0;
            }
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
                    pixels[xa + ya * width] = 0xFF8A2BE2;
            }
        }
    }

    public void update(){
        for (int y = 0; y < 6; y++ ) {
            for (int x = 0; x < 7; x++) {
                tiles[y + 1][x + 1] = grid[y][x];
            }
        }
    }

    public void choose(int col){
        int i = 0;
        while (i < 6 && grid[i][col] == 0) i++;
        if (i > 6 || i <= 0){
            System.out.println("full collum");
            return;
        }
        System.out.println(i - 1 +"  " + col);
        grid[i - 1][col] = 1;
        System.out.println("bbbbbbb");
        for (int k1 = 0; k1 < 6; k1++) {
            for (int k2 = 0; k2 < 7; k2++) {
                System.out.print(grid[k1][k2] + " ");
            }
            System.out.println();
        }
        //grid = solver.solveAPI(grid, 9, false);
        grid = solver_two.slove(grid, 9);
        System.out.println(grid.length);
        System.out.println(grid[0].length);

    }

}
