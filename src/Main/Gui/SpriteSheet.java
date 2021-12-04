package Main.Gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    private String path;
    public int width, height;
    public int[] pixels;

    public static SpriteSheet grid = new SpriteSheet("res/grid.png");
    public static SpriteSheet plates = new SpriteSheet("res/plates.png");

    public SpriteSheet(String path) {
        this.path = path;
        load();
    }

    public void load(){
        try {
            BufferedImage image = ImageIO.read(new File(path));
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.pixels = new int[width * height];
            image.getRGB(0 ,  0 , width , height , pixels , 0 , width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
