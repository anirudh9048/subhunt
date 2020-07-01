import java.awt.Color;

import javax.swing.JFrame;

public class Main implements Runnable {
    static boolean running = false;

    public static void main(String[] args) {
        (new Main()).run();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        running = true;
        JFrame jf = new JFrame();
        Game g = new Game();
        g.setBackground(Color.BLACK);
        jf.setSize(g.WIDTH, g.HEIGHT);
        jf.add(g);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}