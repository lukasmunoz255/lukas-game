package Entities;

import Game.Level;
import Graphics.Colors;

/**
 * A power up that speeds up the fire rate.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public final class GunSpeedPowerUp extends PowerUp{
    private boolean goingDown;

    public GunSpeedPowerUp(Level level, int x, int y) {
        super(level, x, y, 500);
        goingDown = true;
    }

    protected final void colorTick() {
        if(primaryColor / 100 >= 5) { goingDown = true; }
        else if(primaryColor / 100 <= 0) { goingDown = false; }
        
        if(goingDown) { primaryColor -= 100; }
        else { primaryColor += 100;}
        
        color = Colors.get(-1, 000, primaryColor, -1);
    }

    protected final void powerUp(Player p) {
        p.fastFire = true;
        p.powerUpTimer.start();
    }
    
    protected final void remove() { level.removePowerUp(); }
}
