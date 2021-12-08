package Main.Gui;

import Main.logic.MinMax;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;


public class Game extends Canvas implements Runnable{
    private final int width = 9 * 32;
    private final int height = 10 * 32;
    public static String title = "Connect 4";


    private JFrame frame;
    List<JButton> buttons;
    JButton start = new JButton("Start");
    JPanel panel = new JPanel();
    JLabel l1, l2;
    String[] types = {"MIN-MAX", "ALPHA-BETA"};
    Integer[] depths = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    JComboBox type = new JComboBox(types);
    JComboBox depth = new JComboBox(depths);
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
            b1.setEnabled(false);
        }
        start.setBounds(400, 580, 64, 22);
        start.setFont(new Font("Arial", Font.ITALIC, 18));
        start.setMargin(new Insets(0, 0, 0, 0));
        start.setFocusable(false);
        start.setFocusPainted(false);
        start.addActionListener(e -> this.startGame());
        frame.add(start);
        screen = new Screen(width, height);
        buttons.forEach(frame::add);
        for (int i = 0; i < buttons.size(); i++){
            int finalI = i;
            buttons.get(i).addActionListener(e -> screen.choose(finalI));
        }

        //.............................. this part need refactoring ( actually all the project )
        l1 = new JLabel("TYPE");
        l2 = new JLabel("DEPTH");
        type.setVisible(true);
        type.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("type value is changed");
            }
        });
        depth.setVisible(true);
        depth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("depth value is changed");
            }
        });
        panel.add(l1);
        panel.add(type);
        panel.add(l2);
        panel.add(depth);
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.decode("0x5c5aea")); // e is 5
        panel.setBounds(320, 510, 180, 70);
        frame.add(panel);
    }
    public void set(Thread thread){
        this.thread = thread;
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD , 20));
        g.drawString("SCORE ", 80 , 530);
        g.setFont(new Font("Verdana", Font.BOLD , 16));
        g.drawString("Human : Computer ", 45 , 550);
        g.drawString(screen.humanScore + " : " + screen.computerScore, 97 , 570);
        g.dispose();
        bs.show();
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
//        int[][] state = new int[6][7];
//        for (int i = 0; i < 6; i++) {
//            Arrays.fill(state[i],0);
//        }
//        state[5][0] = 1;
//        state[4][0] = 1;
//        state[3][0] = 1;
//        state[2][0] = 1;
//        state[1][0] = 1;
//        state[0][0] = 1;
//        state[5][1] = 1;
//        state[4][1] = 1;
//        state[3][2] = 1;
//        state[5][3] = 1;
//        state[4][3] = 1;
//        state[3][3] = 1;
//        state[2][3] = 1;
//        //state[5][1] = 1;
//        game.screen.grid = state;
//        System.out.println(game.screen.calculate_score(1));
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
