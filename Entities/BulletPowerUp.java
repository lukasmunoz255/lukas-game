package Entities;

import Game.Level;
import Graphics.Colors;

/**
 * Powers up your gun's bullets.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public final class BulletPowerUp extends PowerUp {
    private boolean goingDown;

    /**
     * Constructor for objects of class BulletPowerUp
     */
    public BulletPowerUp(final Level level, final int x, final int y) {
        super(level, x, y, 050);
        goingDown = true;
    }

    public void colorTick() {
        if(primaryColor / 10 >= 5) { goingDown = true; }
        else if(primaryColor / 10 <= 0) { goingDown = false; }
        
        if(goingDown) { primaryColor -= 10; }
        else { primaryColor += 10; }
        
        color = Colors.get(-1, 000, primaryColor, -1);
    }
    
    public void powerUp(final Player p) {
        p.bulletUpgrade = true;
        p.powerUpTimer.start();
    }
    
    protected void remove() {
        level.removePowerUp();
    }
}
