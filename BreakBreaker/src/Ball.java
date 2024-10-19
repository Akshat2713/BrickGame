import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball {
    private int x, y;
    private int xDir, yDir;
    private static final int SIZE = 20;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        this.xDir = -1;
        this.yDir = -2;
    }

    public void setSpeed(int xDir, int yDir) {
        this.xDir = xDir;
        this.yDir = yDir;
    }

    public void move() {
        x += xDir;
        y += yDir;
        if (x < 0 || x > 670)
            xDir = -xDir;
        if (y < 0)
            yDir = -yDir;
    }

    public void bounceOffPaddle(int paddleX) {
            yDir = -yDir; // Always reverse the y direction
            
            // Calculate the center of the paddle
            int paddleCenter = paddleX + 100 / 2;
            // Calculate the hit position relative to the paddle center
            int hitPosition = (x + SIZE / 2) - paddleCenter;
        
            // Change xDir slightly based on the hit position
            if (hitPosition > 0) {
                xDir = 1; // Bounce right
            } else if (hitPosition < 0) {
                xDir = -1; // Bounce left
            } else {
                xDir = 0; // No horizontal movement
            }
        
    }

    public void bounceOffBrick(Rectangle brick) {
        // Determine the side of the collision based on the ball's position relative to
        // the brick
        int ballCenterX = x + SIZE / 2;
        int ballCenterY = y + SIZE / 2;

        // Calculate the distance to each side of the brick
        double distanceLeft = ballCenterX - (brick.x);
        double distanceRight = (brick.x + brick.width) - ballCenterX;
        double distanceTop = ballCenterY - (brick.y);
        double distanceBottom = (brick.y + brick.height) - ballCenterY;

        // Determine the smallest distance to a side
        double minDistance = Math.min(Math.min(distanceLeft, distanceRight), Math.min(distanceTop, distanceBottom));

        // Reflect based on the closest side
        if (minDistance == distanceTop || minDistance == distanceBottom) {
            yDir = -yDir; // Reverse y direction for top/bottom collision
        } else {
            xDir = -xDir; // Reverse x direction for left/right collision
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void stop() {
        xDir = 0;
        yDir = 0;
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        setSpeed(-1, -2); // Reset speed as per difficulty
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, SIZE, SIZE);
    }

    public int getY() {
        return y;
    }

    public boolean intersects(Rectangle rect) {
        return getBounds().intersects(rect);
    }

}
