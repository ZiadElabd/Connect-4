package Main.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable{
    private final int width = 9 * 32;
    private final int height = 10 * 32;
    public static String title = "Connect 4 @ziadElAbd";

    private JFrame frame;
    List<JButton> buttons;
    private Screen screen;
    Thread thread;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(int scale){
        Dimension size = new Dimension(width * scale , height * scale);
        setPreferredSize(size);
        frame = new JFrame();
        buttons = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            JButton b1 = new JButton(String.valueOf(i));
            b1.setBounds(15 + i * 65, 460, 32, 32);
            b1.setFont(new Font("Arial", Font.BOLD, 25));
            b1.setMargin(new Insets(0, 0, 0, 0));
            b1.setFocusable(false);
            b1.setFocusPainted(false);
            buttons.add(b1);
        }
        screen = new Screen(width, height);
        buttons.forEach(frame::add);
        buttons.forEach(b ->
                b.addActionListener(e ->
                        System.out.println("@ziadElAbd"
                                + new Random().nextInt()
                        )
                )
        );
    }
    public void set(Thread thread){
        this.thread = thread;
    }

    @Override
    public void run() {
        while(true){
            render();
        }
    }


    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        int[][] a = new int[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                a[i][j] = new Random().nextInt(3);
            }
        }
        screen.update(a);
        screen.render();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD , 13));
        g.drawString("Designed by @ziadElAbd ", 400 , 20);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        System.out.println("start  now");
        int scale = 2;
        Game game = new Game(scale);
        game.frame.setResizable(false);
        game.frame.setTitle(Game.title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        Thread thread = new Thread(game , "Display");
        game.set(thread);
        thread.start();
    }
}
