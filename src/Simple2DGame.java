import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Simple2DGame extends Canvas implements Runnable, KeyListener {

    private boolean running = false;
    private Thread thread;
    private int playerX = 400, playerY = 300, playerSpeed = 5, playerSize = 30;
    private boolean up, down, left, right;
    private ArrayList<Enemy> enemies;
    private Random random = new Random();
    private boolean gameOver = false;

    public Simple2DGame() {
        JFrame frame = new JFrame("2D Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);

        this.addKeyListener(this);
        this.setFocusable(true);

        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy(random.nextInt(800), random.nextInt(600), 2 + random.nextInt(3)));
        }
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            if (!gameOver) {
                update();
            }
            render();
            try {
                Thread.sleep(16);  // Ca. 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    public void update() {
        if (up && playerY > 0) playerY -= playerSpeed;
        if (down && playerY < getHeight() - playerSize) playerY += playerSpeed;
        if (left && playerX > 0) playerX -= playerSpeed;
        if (right && playerX < getWidth() - playerSize) playerX += playerSpeed;

        for (Enemy enemy : enemies) {
            enemy.moveTowards(playerX, playerY);
            if (enemy.collidesWith(playerX, playerY, playerSize)) {
                gameOver = true;
            }
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Baggrund
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Tegn spiller
        g.setColor(Color.blue);
        g.fillRect(playerX, playerY, playerSize, playerSize);

        // Tegn fjender
        g.setColor(Color.red);
        for (Enemy enemy : enemies) {
            g.fillRect(enemy.x, enemy.y, enemy.size, enemy.size);
        }

        if (gameOver) {
            g.setColor(Color.white);
            g.drawString("Game Over!", 350, 300);
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Simple2DGame game = new Simple2DGame();
        game.start();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) up = true;
        if (key == KeyEvent.VK_DOWN) down = true;
        if (key == KeyEvent.VK_LEFT) left = true;
        if (key == KeyEvent.VK_RIGHT) right = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) up = false;
        if (key == KeyEvent.VK_DOWN) down = false;
        if (key == KeyEvent.VK_LEFT) left = false;
        if (key == KeyEvent.VK_RIGHT) right = false;
    }

    public void keyTyped(KeyEvent e) {
    }

    // Fjende-klasse
    class Enemy {
        int x, y, size = 30;
        int speed;

        public Enemy(int x, int y, int speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }

        public void moveTowards(int targetX, int targetY) {
            if (x < targetX) x += speed;
            if (x > targetX) x -= speed;
            if (y < targetY) y += speed;
            if (y > targetY) y -= speed;
        }

        public boolean collidesWith(int px, int py, int pSize) {
            return px < x + size && px + pSize > x && py < y + size && py + pSize > y;
        }
    }
}