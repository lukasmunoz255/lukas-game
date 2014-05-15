package Entities;

import Game.Level;
import Graphics.Colors;

/**
 * Write a description of class BulletPowerUp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BulletPowerUp extends PowerUp
{
    private boolean goingDown = true;

    /**
     * Constructor for objects of class BulletPowerUp
     */
    public BulletPowerUp(Level level, int x, int y)
    {
        super(level, x, y, 050);
    }

    public void colorTick()
    {
        if(primaryColor / 10 >= 5)
            goingDown = true;
        else if(primaryColor / 10 <= 0)
            goingDown = false;
        if(goingDown)
            primaryColor -= 10;
        else
            primaryColor += 10;
        color = Colors.get(-1, 000, primaryColor, -1);
    }
    
    public void powerUp(Player p)
    {
        p.bulletUpgrade = true;
        p.powerUpTimer.start();
    }
    
    protected void remove()
    {
        level.removePowerUp();
    }
}
