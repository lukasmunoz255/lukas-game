package Entities;

import Game.Level;
import Graphics.Screen;
import java.awt.Graphics;

/**
 * The superclass for all game entities who will extend it.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public abstract class Entity{
    public int x, y, index;
    protected Level level;
    protected int scale = 1, xMin, xMax, yMin, yMax;

    public Entity(Level level) { init(level); }

    public final void init(Level level) { this.level = level; }

    public abstract void tick();

    public abstract void render(Screen screen, Graphics g);

    public abstract boolean isAlive();

    public final double width() { return (this.xMax - this.xMin) * this.scale; }

    public final double height() { return (this.yMax - this.yMin) * this.scale; }

    public final double rightPixelX() { return leftPixelX() + width(); }

    public final double bottomPixelY() { return topPixelY() + height(); }

    public final double leftPixelX() { return this.x + this.xMin; }

    public final double topPixelY() { return this.y + this.yMin; }

    //public boolean isBullet() { return false; }
}
