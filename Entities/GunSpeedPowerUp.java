package Entities;

import Game.Level;
import Graphics.Colors;

/**
 * Write a description of class GunSpeedPowerUp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GunSpeedPowerUp extends PowerUp
{
    private boolean goingDown = true;

    public GunSpeedPowerUp(Level level, int x, int y)
    {
        super(level, x, y, 500);
    }

    protected void colorTick()
    {
        if(primaryColor / 100 >= 5)
            goingDown = true;
        else if(primaryColor / 100 <= 0)
            goingDown = false;
        if(goingDown)
            primaryColor -= 100;
        else
            primaryColor += 100;
        color = Colors.get(-1, 000, primaryColor, -1);
    }

    protected void powerUp(Player p)
    {
        p.fastFire = true;
        p.powerUpTimer.start();
    }
    
    protected void remove()
    {
        level.removePowerUp();
    }
}
