import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.Timer;

public class GamePlayMains extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 48;
    private Timer timer;
    private int delay = 8;
    private MapGenerator map;
    // private Difficulty difficulty;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;

    public GamePlayMains() {

        map = new MapGenerator(4, 12);
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
    //         case EASY -> {
    //             ballXdir = -1;
    //             ballYdir = -2;
    //             break;
    //         }
    //         case MEDIUM -> {
    //             ballXdir = -2;
    //             ballYdir = -3;
    //             break;
    //         }

    //         case HARD -> {
    //             ballXdir = -3;
    //             ballYdir = -4;
    //             break;
    //         }
    //     }
    // }

    public void paint(Graphics g) {

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // drawing map

        map.draw((Graphics2D) g);
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(689, 0, 3, 592);

        // the scores

        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 590, 30);

        // Display difficulty level

        // g.drawString("Difficulty: " + difficulty, 20, 30);

        // the paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        // the ball

        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // when you win the game
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won!!!", 260, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230, 350);

        }

        // When you lose the game

        if (ballPosY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230, 350);

        }
        g.dispose();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            if (playerX >= 600) {
                playerX = 600;

            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                playerX = 310;
                score = 0;
                totalBricks = 48;
                map = new MapGenerator(4, 12);


                // setDifficulty(difficulty);

                repaint();
            }
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

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20)
                    .intersects(new Rectangle(playerX, 550, 30, 8))) {
                ballYdir = -ballYdir;
                ballXdir = -2;

            } else if (new Rectangle(ballPosX, ballPosY, 20, 20)
                    .intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;

            } else if (new Rectangle(ballPosX, ballPosY, 20, 20)
                    .intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                ballYdir = -ballYdir;

            }

            // check map collision with the ball
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {

                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            score += 5;
                            totalBricks--;

                            // when ball hit left or right of brick

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;

                        }
                    }
                }
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;

            if (ballPosX < 0) {
                ballXdir = -ballXdir;

            }
            if (ballPosY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballPosX > 670) {
                ballXdir = -ballXdir;
            }

            repaint();

        }
    }
}
