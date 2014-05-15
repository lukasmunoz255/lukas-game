package Entities;
import Game.Level;
import Game.InputHandler;
import Graphics.Screen;
import Graphics.Colors;
import java.awt.Graphics;

/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends Mob
{
    protected int color = Colors.get(-1, 111, -1, -1);
    protected double scale = 1.0;
    public double deltaY;
    public double deltaX;

    public Bullet(Level level, int x, int y, int destinationX, int destinationY, 
    int originX, int originY, int index)
    {   
        super(level, "bullet", x, y, 1);
        double bulletSpeed = 13.192837465;
        this.index = index;
        this.health = 1;
        double top = destinationY - originY;
        double bottom = destinationX - originX;
        deltaY = Math.abs(top) * bulletSpeed / (Math.abs(bottom) + Math.abs(top));
        if(top < 0)
            deltaY = -deltaY;
        deltaX = bulletSpeed - Math.abs(deltaY);
        if(bottom < 0)
            deltaX = -deltaX;
        this.xMin = 2;
        this.xMin = 6;
        this.yMin = 2;
        this.yMax = 6;
    }

    public short getHealth()
    {
        return health;
    }

    public void move(double xa, double ya)
    {
        if(this.rightPixelX() > level.width * 8 || this.leftPixelX() < 0 ||
        this.bottomPixelY() > level.height * 8 || this.topPixelY() < 0)
            level.removeBullet(index);
        else if(hasCollided((int)xa, (int)ya))
            level.removeBullet(index);
        if(index < level.bullets.size())
        {
            if(xa > 0 && ya > 0)
            {
                move(xa, 0);
                move(0, ya);
            }
            else
            {
                x += xa * speed;
                y += ya * speed;
            }
        }
    }

    public void hit()
    {
        health--;
    }

    public void tick()
    {
        move(deltaX, deltaY);
    }

    public void render(Screen screen, Graphics g)
    {
        int xTile = 0;
        int yTile = 2;
        screen.render(x, y, xTile + yTile * 32, color, 0, scale); 
    }

    public boolean hasCollided(int xa, int ya) 
    { 
        for(int x = xMin; x < xMax; x++) 
        { 
            if(isSolidTile(xa, ya, x, yMin)) 
                return true; //left
        } 
        for(int x = xMin; x < xMax; x++) 
        { 
            if(isSolidTile(xa, ya, x, yMax)) 
                return true; //left
        } 
        for(int y = yMin; y < yMax; y++) 
        { 
            if(isSolidTile(xa, ya, xMin, y)) 
                return true; //bottom
        } 
        for(int y = yMin; y < yMax; y++) 
        { 
            if(isSolidTile(xa, ya, xMax, y)) 
                return true; // right
        } 
        return false; 
    }
    
    public boolean fromZombie()
    {
        return false;
    }
    
    public boolean isBullet()
    {
        return true;
    }
}
