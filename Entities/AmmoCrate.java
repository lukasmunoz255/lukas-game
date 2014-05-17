package Entities;

import Game.Level;
import Game.Timer;
import Graphics.Screen;
import Graphics.Colors;

import java.awt.Graphics;

/**
 * Adds more ammo to your gun.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public class AmmoCrate extends PowerUp
{
    public int index;
    private Timer lifeTimer;
    
    public AmmoCrate(final Level level, final int x, final int y, final int index) {
        super(level, x, y, 000);
        this.index = index;
        this.xTile = 0;
        this.yTile = 14;
        this.color = Colors.get(-1, 000, 510, 222);
        this.lifeTimer = new Timer();
        this.lifeTimer.start();
        this.scale = 2;
        setSoundFile("Sound/reloadsound.wav");
    }
    
    public final void render(final Screen screen, final Graphics g) {
        int modifier = (int)(8 * scale),
        xOffset = x - modifier / 2,
        yOffset = y - modifier / 4 - 4;
        screen.render(xOffset, yOffset, xTile + yTile*32, color, 0, scale);
    }
    
    public final void powerUp(final Player p) { p.addAmmo(25); }
    
    public void tick() {
        super.tick();
        if(lifeTimer.getTime() >= 15000) { remove(); }
    }
    
    public void remove() { level.removeAmmoCrate(index); }
    
    public void colorTick() {
        // Do nothing
    }
}
