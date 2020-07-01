import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static int WIDTH = 800;
    public static int HEIGHT = 800;
    public static final int NUM_NPCS = 30;
    Timer tm = new Timer(5, this);
    double scale = 0.01;
    double limit = 0.1;

    double tolerance = 1.5;

    Entity e1;
    Entity eTarget;
    Entity[] npcs = new Entity[NUM_NPCS];

    public Game() {
        double r1 = new Random().nextDouble();
        double r2 = new Random().nextDouble();
        Random r = new Random();
        tm.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        e1 = new Entity(WIDTH / 2 * r1, HEIGHT / 2 * r2, new Random().nextDouble() * scale, new Random().nextDouble() * scale);
        eTarget =
                new Entity(WIDTH / 2 * r.nextDouble(), HEIGHT / 2 * r.nextDouble(), new Random().nextDouble() * scale, new Random().nextDouble() * scale);
        initNPCs();
    }

    public void initNPCs() {
        for (int i = 0; i < NUM_NPCS; i++) {
            double t = new Random().nextDouble();
            double s = new Random().nextDouble();
            npcs[i] =
                    new Entity(WIDTH / 2 * s, HEIGHT / 2 * t, new Random().nextDouble() * scale, new Random().nextDouble() * scale);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 153));
        g.fillOval((int) e1.x, (int) e1.y, 9, 9);
        for (Entity e : npcs) {
            g.setColor(new Color(0, 102, 0));
            g.fillOval((int) e.x, (int) e.y, 9, 9);
        }
        g.setColor(new Color(204, 0, 0));
        g.fillOval((int) eTarget.x, (int) eTarget.y, 9, 9);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                e1.v_y += (e1.v_y < limit) ? -limit * scale : 0;
                break;
            case KeyEvent.VK_DOWN:
                e1.v_y += (e1.v_y > -limit) ? limit * scale : 0;
                break;
            case KeyEvent.VK_RIGHT:
                e1.v_x += (e1.v_x < limit) ? limit * scale : 0;
                break;
            case KeyEvent.VK_LEFT:
                e1.v_x += (e1.v_x > -limit) ? -limit * scale : 0;
                break;
            default:
                System.out.println("invalid key");
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        updateEntity(e1);
        for (Entity ee : npcs) {
            updateEntity(ee);
        }
        updateEntity(eTarget);
    }

    class Collisions {
        public boolean hitTarget;
        public boolean hitFriend;
        public Collisions(boolean b1, boolean b2) {
            hitTarget = b1;
            hitFriend = b2;
        }
    }

    public void updateEntity(Entity e) {
        if (e.x < 0 || e.x > WIDTH) {e.v_x = 0; System.out.println("Game Over"); System.exit(1);}
        if (e.y < 0 || e.y > HEIGHT) {e.v_y = 0; System.out.println("Game Over"); System.exit(1);}
        e.x += e.v_x * ((double) tm.getDelay());
        e.y += e.v_y * ((double) tm.getDelay());
        Collisions c = checkCollisions();
        if (c.hitFriend) {
            System.out.println("Game Over: hit friend"); System.exit(1);
        }

        if (c.hitTarget) {
            System.out.println("You Win!"); System.exit(0);
        }

        repaint();
    }
    public Collisions checkCollisions() {
        boolean hitTarget, hitFriend;
        hitTarget = false;
        hitFriend = false;
        if ((Math.abs(e1.x - eTarget.x)  < tolerance) && (Math.abs(e1.y - eTarget.y)  < tolerance)) {
            hitTarget = true;
        }
        for (Entity ee : npcs) {
            if (( Math.abs(e1.x - ee.x)  < tolerance) && ( Math.abs(e1.y - ee.y)  < tolerance)) {
                System.out.println(e1.x + " " + ee.x + " " + e1.y + " " + ee.y);
                hitFriend = true;
            }
        }
        return new Collisions(hitTarget, hitFriend);
    }
}
