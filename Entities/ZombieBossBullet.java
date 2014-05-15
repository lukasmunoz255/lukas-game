package Entities;

import Game.Level;
import Graphics.Screen;

import java.awt.Graphics;

/**
 * Write a description of class ZombieBullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieBossBullet extends Bullet
{
    public ZombieBossBullet(Level level, int x, int y, int destinationX, int destinationY, 
    int originX, int originY, int index)
    {
        super(level, x, y, destinationX, destinationY, originX, originY, index);
        this.health = 2;
        this.xMin = -4;
        this.xMin = 12;
        this.yMin = -4;
        this.yMax = 12;
    }
    
    public boolean fromZombie()
    {
        return true;
    }
    
    public void render(Screen screen, Graphics g)
    {
        int xTile = 2;
        int yTile = 2;
        screen.render(x, y, xTile + yTile * 32, color, 0, scale); 
    }
}
