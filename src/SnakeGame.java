import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JFrame implements KeyListener, ActionListener {

    private final int TILE_SIZE = 25;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int ALL_TILES = (WIDTH * HEIGHT) / (TILE_SIZE * TILE_SIZE);

    private ArrayList<Point> snake = new ArrayList<>();
    private Point apple;
    private char direction = 'R';
    private boolean running = false;

    private Timer timer;
    private Random random = new Random();

    public SnakeGame() {
        this.setTitle("Snake Game");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.addKeyListener(this);
        this.setFocusable(true);

        startGame();
    }

    public void startGame() {
        snake.clear();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2)); // Start position for slangen
        spawnApple();

        running = true;
        timer = new Timer(100, this); // Hvert 100ms opdateres spillet
        timer.start();
    }

    public void spawnApple() {
        int x = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        int y = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
        apple = new Point(x, y);
    }

    public void paint(Graphics g) {
        super.paint(g);

        // Tegner baggrund
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Tegner slangen
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x, point.y, TILE_SIZE, TILE_SIZE);
        }

        // Tegner æblet
        g.setColor(Color.RED);
        g.fillRect(apple.x, apple.y, TILE_SIZE, TILE_SIZE);

        // Hvis spillet stopper, viser vi en Game Over-besked
        if (!running) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", WIDTH / 3, HEIGHT / 2);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            checkApple();
        }
        repaint();
    }

    public void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case 'U': newHead.y -= TILE_SIZE; break;
            case 'D': newHead.y += TILE_SIZE; break;
            case 'L': newHead.x -= TILE_SIZE; break;
            case 'R': newHead.x += TILE_SIZE; break;
        }

        // Flytter slangen
        snake.add(0, newHead);
        snake.remove(snake.size() - 1);
    }

    public void checkCollision() {
        Point head = snake.get(0);

        // Kollision med kanterne
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            running = false;
        }

        // Kollision med slangen selv
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                running = false;
            }
        }

        if (!running) {
            timer.stop();
        }
    }

    public void checkApple() {
        Point head = snake.get(0);

        // Hvis slangen spiser æblet
        if (head.equals(apple)) {
            snake.add(new Point(-1, -1));  // Forlænger slangen
            spawnApple();
        }   
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }
}
