import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle {
    private int x;
    static final int WIDTH = 100;
    private static final int HEIGHT = 8;
    private static final int MOVE_SPEED = 20;
    
    public Paddle(int x){
        this.x= x;
    }
    
    public void moveRight(){
        if (x >= 600) x = 600;
        else x+=MOVE_SPEED;
    }

    public void moveLeft(){
        if (x <= 10) x = 10;
        else x-=MOVE_SPEED;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(x, 550, WIDTH, HEIGHT);
    }

    public int getX(){
        return x;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,550,WIDTH,HEIGHT);
    }

    public void reset(){
        x=310;
    }
}
