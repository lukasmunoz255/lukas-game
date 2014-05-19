package Entities;

import Game.Timer;
import Game.Level;
import Game.Debugger;

import Graphics.Screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * Write a description of class ComputerPlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComputerPlayer extends Player
{
    private Zombie zombie;
    private Random rand;

    /**
     * Constructor for objects of class ComputerPlayer
     */
    public ComputerPlayer(Level level, int x, int y, Screen screen)
    {
        super(level, x, y, null, screen, "Computer player");
        this.speed = 2;
        this.healthTimer.start();
        rand = new Random();
        Debugger.sendMsg(String.format("created a computer player at (%d, %d)", x, y));
    }

    public void setZombie(Zombie zomb)
    {
        this.zombie = zomb;
        this.zombie.health = 1000;
    }

    protected int getGunSpeed()
    {
        return 1000;
    }

    protected void healthTick()
    {
    }

    protected void moveTick()
    {
        if(healthTimer.getTime() > 8000)
        {
            healthTimer.stop();
            healthTimer.start();
        }
        else if(healthTimer.getTime() > 6000)
            move(-1, 0);
        else if(healthTimer.getTime() > 4000)
            move(0, 1);
        else if(healthTimer.getTime() > 2000)
            move(1, 0);
        else if(healthTimer.getTime() > 0)
            move(0, -1);
    }

    protected void top()
    {
        top = zombie.y - y;
    }

    protected void bottom()
    {
        bottom = zombie.x - x;
    }

    protected void fireTick()
    {
        if(!canFire)
        {
            if(gunTimer.getTime() > getGunSpeed())
            {
                gunTimer.stop();
                canFire = true;
            }
        }
        else
            fire();
    }

    public void fire()
    {
        if(canFire)
        {
            if(rand.nextBoolean())
            {
                level.addBullet(new MediumBullet(level, getGunX(), getGunY(), (int)(zombie.x + rand.nextInt(10)), 
                        (int)(zombie.y + rand.nextInt(10)), getOnScreenX(), getOnScreenY(), level.bullets.size()));
            }
            else
            {
                level.addBullet(new MediumBullet(level, getGunX(), getGunY(), (int)(zombie.x - rand.nextInt(10)), 
                        (int)(zombie.y - rand.nextInt(10)), getOnScreenX(), getOnScreenY(), level.bullets.size()));
            }
            canFire = false;
            gunTimer.start();
        }
    }

    public void render(Screen screen, Graphics g)  
    {  
        Graphics2D g2d = (Graphics2D)g;
        top();
        bottom();
        transform();
        g2d.rotate(transform, (x - screen.xOffset + width()/2)*3, (y - screen.yOffset + height()/3)*3);
        g2d.drawImage(image, (int)(x - screen.xOffset - width())*3, (int)(y - screen.yOffset - height())*3, null);
        g2d.rotate(-transform, (x - screen.xOffset + width()/2)*3, (y - screen.yOffset + height()/3)*3);

        g2d.dispose();
    }  
}
