package Main.Gui;

import java.util.Arrays;

public class Sprite {

    public final int SIZE;
    private int x , y;
    public int[] pixels;
    private SpriteSheet sheet;

    public static Sprite white = new Sprite(32 , 0 , 0 , SpriteSheet.plates);
    public static Sprite yellow = new Sprite(32 , 1 , 0, SpriteSheet.plates);
    public static Sprite red = new Sprite(32 , 2 , 0 , SpriteSheet.plates);
    public static Sprite background = new Sprite(32, 0xFF8A2BE2);

    public Sprite(int size, int x , int y, SpriteSheet sheet) {
        this.SIZE = size;
        this.x = x * size;
        this.y = y * size;
        pixels = new int[SIZE * SIZE];
        this.sheet = sheet;
        load();
    }

    public Sprite(int size, int color){
        this.SIZE = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }

    private void setColor(int color) {
        Arrays.fill(pixels, color);
    }

    public void load(){
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.width];
            }
        }
    }
}
