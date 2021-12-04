package Main.Gui;


public class Screen {
    public int width;
    public int height;
    public int[] pixels;
    public int[][] tiles = new int[10][9];


    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        this.init();
        this.render();
    }

    public void init(){
        int[] grid = SpriteSheet.grid.pixels;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                if (grid[x + y * SpriteSheet.grid.width] == 0xFF2B2EFF){
                    tiles[y][x] = -1;
                }else if (grid[x + y * SpriteSheet.grid.width] == 0xFFFF2B1C){
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

    public void update(int[][] grid){
        if (grid.length != 6 && grid[0].length != 7){
            System.out.println("error in the grid");
            return;
        }
        for (int y = 0; y < 6; y++ ) {
            for (int x = 0; x < 7; x++) {
                tiles[y + 1][x + 1] = grid[y][x];
            }
        }
    }

}
