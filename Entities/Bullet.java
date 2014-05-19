package Entities;
import Game.Level;
import Game.InputHandler;
import Graphics.Screen;
import Graphics.Colors;
import java.awt.Graphics;
import Game.Debugger;

/**
 * The bullet object.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public class Bullet extends Mob {
    protected int color = Colors.get(-1, 111, -1, -1);
    protected double scale = 1.0;
    public double deltaY, deltaX;

    public Bullet(final Level level, final int x, final int y, final int destinationX, final int destinationY, 
    final int originX, final int originY, final int index) {   
        super(level, "bullet", x, y, 1);
        final double bulletSpeed = 13.192837465;
        this.index = index;
        this.health = 1;
        final double top = destinationY - originY,
        bottom = destinationX - originX;
        
        deltaY = top * bulletSpeed / (Math.abs(bottom) + Math.abs(top));

        deltaX = (bulletSpeed - Math.abs(deltaY)) * ((bottom < 0) ? (-1) : (1));
        this.xMin = 2;
        this.xMax = 6;
        this.yMin = 2;
        this.yMax = 6;
        
    }

    public final short getHealth() { return health; }

    public final void move(final double xa, final double ya) {
        if(this.rightPixelX() > level.width * 8 ||
        this.leftPixelX() < 0 ||
        this.bottomPixelY() > level.height * 8 ||
        this.topPixelY() < 0) {
            level.removeBullet(index);
        } else if(hasCollided((int)xa, (int)ya)) {
            level.removeBullet(index);
        }
        
        if(index < level.bullets.size()) {
            if(xa > 0 && ya > 0) {
                move(xa, 0);
                move(0, ya);
            } else {
                x += xa * speed;
                y += ya * speed;
            }
        }
    }

    public final void hit() { --health; }

    public final void tick() { move(deltaX, deltaY); }

    public void render(final Screen screen, final Graphics g) {
        final int xTile = 0,
        yTile = 2;
        screen.render(x, y, xTile + yTile * 32, color, 0, scale); 
    }

    public final boolean hasCollided(final int xa, final int ya) { 
        for(int x = xMin; x < xMax; ++x) { 
            if(isSolidTile(xa, ya, x, yMin) || isSolidTile(xa, ya, x, yMax)) { return true; }
        }
        for(int y = yMin; y < yMax;++ y) {
            if(isSolidTile(xa, ya, xMin, y) || isSolidTile(xa, ya, xMax, y)) { return true; }
        } 
        return false; 
    }
    
    public boolean fromZombie() { return false; }
    
}
