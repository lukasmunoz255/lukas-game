package Entities;

import Game.Level;
import Graphics.Screen;

import java.awt.Graphics;

/**
 * The class for MediumBullets.
 * 
 * @author Lukas Muñoz, Luke Stauton, JCL
 */
public class MediumBullet extends Bullet
{
    /**
     * Constructor for objects of class MediumBullet
     */
    public MediumBullet(Level level, int x, int y, int destinationX, int destinationY, 
    int originX, int originY, int index) {
        super(level, x, y, destinationX, destinationY, originX, originY, index);
        this.health = 3;
        this.xMin = -2;
        this.xMin = 10;
        this.yMin = -2;
        this.yMax = 10;
    }

    public boolean fromZombie() { return false; }
    
    public void render(final Screen screen, final Graphics g) {
        final int xTile = 1, yTile = 2;
        screen.render(x, y, xTile + yTile * 32, color, 0, scale); 
    }
}
