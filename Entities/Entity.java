package Entities;

import Game.Level;
import Graphics.Screen;
import java.awt.Graphics;

/**
 * Write a description of class Entity here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Entity
{
    public int x, y, index;
    protected Level level;
    protected int scale = 1, xMin, xMax, yMin, yMax;

    public Entity(Level level) {
        init(level);
    }

    public final void init(Level level) {
        this.level = level;
    }
    
    /*
    public void setIndex(int newIndex)
    {
        index = newIndex;
    }
    
    public int getIndex()
    {
        return index;
    }
    */

    public abstract void tick();

    public abstract void render(Screen screen, Graphics g);
    
    public abstract boolean isAlive();
    
    public double width() 
    { 
        return (this.xMax - this.xMin) * this.scale;
    }

    public double height() 
    { 
        return (this.yMax - this.yMin) * this.scale;
    }
    
    public double rightPixelX()
    {
        return leftPixelX() + width();
    }
    
    public double bottomPixelY()
    {
        return topPixelY() + height();
    }

    public double leftPixelX()
    {
        return this.x + this.xMin;
    }

    public double topPixelY()
    {
        return this.y + this.yMin;
    }
    
    public boolean isBullet()
    {
        return false;
    }
}
