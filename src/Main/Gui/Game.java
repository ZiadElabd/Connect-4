package Main.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;


public class Game extends Canvas implements Runnable{
    private final int width = 9 * 32;
    private final int height = 10 * 32;
    public static String title = "Connect 4";
    String[] types = {"MIN-MAX", "ALPHA-BETA"};


    private JFrame frame;
    private List<JButton> buttons = new ArrayList<>();
    private JButton start = new JButton("Start");
    private JPanel panel;
    private JLabel typeLabel = new JLabel("TYPE");
    private JLabel depthLabel = new JLabel("DEPTH");
    private JComboBox type, depth;
    private Screen screen;
    Thread thread;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(int scale){
        Dimension size = new Dimension(width * scale , height * scale);
        setPreferredSize(size);
        frame = new JFrame();
        screen = new Screen(width, height);
        this.setButtons();
        // ComboBox
        type = new JComboBox(types);
        depth = new JComboBox();
        for (int i = 1; i <= 13; i++) {
            depth.addItem(i);
        }

        // panel
        panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.decode("0x5C5AEA"));
        panel.setBounds(320, 510, 180, 70);

        panel.add(typeLabel);
        panel.add(type);
        panel.add(depthLabel);
        panel.add(depth);

        frame.add(panel);
        buttons.forEach(frame::add);
        frame.add(start);
    }
    private void setButtons(){
        // col buttons
        for (int i = 1; i <= 7; i++) {
            JButton b1 = new JButton(String.valueOf(i));
            b1.setBounds(15 + i * 65, 460, 32, 32);
            b1.setFont(new Font("Arial", Font.BOLD, 25));
            b1.setMargin(new Insets(0, 0, 0, 0));
            b1.setFocusable(false);
            b1.setFocusPainted(false);
            b1.setEnabled(false);
            int finalI = i - 1;
            b1.addActionListener(e -> screen.choose(finalI));
            buttons.add(b1);
        }
        // start button
        start.setBounds(400, 580, 64, 22);
        start.setFont(new Font("Arial", Font.ITALIC, 18));
        start.setMargin(new Insets(0, 0, 0, 0));
        start.setFocusable(false);
        start.setFocusPainted(false);
        start.addActionListener(e -> this.startGame());

    }

    public Thread setThread(Thread thread){
        return this.thread = thread;
    }

    @Override
    public void run() {
        while(true){
            update();
            render();
        }
    }

    private void update(){
        screen.update();
    }


    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        screen.render();
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        this.writeScore(g);
        g.dispose();
        bs.show();
    }

    private void writeScore(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD , 20));
        g.drawString("SCORE ", 80 , 530);
        g.setFont(new Font("Verdana", Font.BOLD , 16));
        g.drawString("Human : Computer ", 45 , 550);
        g.drawString(screen.humanScore + " : " + screen.computerScore, 97 , 570);
    }

    private void startGame() {
        buttons.forEach(e -> e.setEnabled(true));
        screen.gameType = this.type.getSelectedIndex();
        screen.depth = (int) this.depth.getSelectedItem();
        this.type.setEnabled(false);
        this.depth.setEnabled(false);
        start.setEnabled(false);
    }

    public static void main(String[] args) {
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
        game.setThread(thread).start();
    }
}
