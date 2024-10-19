import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlayMains_updated extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private boolean paused = false;
    private int score = 0;
    private int totalBricks = 48;
    private Timer timer;
    private int delay = 8;
    private MapGenerator map;
    // private Difficulty difficulty;
    private Paddle paddle;
    private Ball ball;

    public GamePlayMains_updated() {

        map = new MapGenerator(4, 12);
        ball = new Ball(120, 350);
        paddle = new Paddle(310);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        // setDifficulty(Difficulty.EASY);

    }

    // public void setDifficulty(Difficulty difficulty) {
    //     this.difficulty = difficulty;
    //     switch (difficulty) {
    //         case EASY -> ball.setSpeed(-1, -2);
    //         case MEDIUM -> ball.setSpeed(-2, -3);
    //         case HARD -> ball.setSpeed(-3, -4);
    //     }
    // }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // drawing map

        map.draw((Graphics2D) g);

        // Boundries

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(689, 0, 3, 592);

        // the scores

        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 590, 30);

        // Display difficulty level

        // g.drawString("Difficulty: " + difficulty.name(), 20, 30);

        // the paddle
        paddle.draw(g);

        // the ball

        ball.draw(g);

        // when you win the game
        if (totalBricks <= 0) {
            gameWon(g);
        }

        // When you lose the game

        if (ball.getY() > 570) {
            gameOver(g);

        }
    }

    private void gameWon(Graphics g) {
        play = false;
        ball.stop();
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("You Won!!!", 260, 300);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press (Enter) to Restart", 230, 350);
    }

    private void gameOver(Graphics g) {
        play = false;
        ball.stop();
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Game Over, Score: " + score, 190, 300);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press (Enter) to Restart", 230, 350);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.moveRight();
            play = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
            play = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                restartGame();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
        }

        // if (e.getKeyCode() == KeyEvent.VK_1) {
        //     setDifficulty(Difficulty.EASY);
        // }
        // if (e.getKeyCode() == KeyEvent.VK_2) {
        //     setDifficulty(Difficulty.MEDIUM);
        // }
        // if (e.getKeyCode() == KeyEvent.VK_3) {
        //     setDifficulty(Difficulty.HARD);
        // }
    }

    private void restartGame() {
        play = true;
        ball.reset(120, 350);
        // setDifficulty(difficulty);
        paddle.reset();
        score = 0;
        totalBricks = 48;
        map = new MapGenerator(4, 12);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play && !paused) {
            ball.move();
            checkCollisions();
            repaint();
        }
    }

    private void checkCollisions() {
        if (ball.intersects(paddle.getBounds())) {
            ball.bounceOffPaddle(paddle.getX());
        }
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                if (map.map[i][j] > 0) {
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    Rectangle brickRect = new Rectangle(brickX, brickY, map.brickWidth, map.brickHeight);
                    if (ball.getBounds().intersects(brickRect)) {
                        map.setBrickValue(0, i, j);
                        score += 5;
                        totalBricks--;
                        ball.bounceOffBrick(brickRect);
                    }
                }
            }
        }
    }
}
