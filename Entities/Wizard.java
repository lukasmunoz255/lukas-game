package Entities;

import Game.Level;
import Game.Timer;
import Graphics.Colors;

import java.util.ArrayList;

/**
 * Write a description of class Wizard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wizard extends Zombie
{
    protected ArrayList<Zombie> zombies;
    protected boolean goingDown = false;
    protected Timer zombieTimer;
    protected int worldNumber;

    /**
     * Constructor for objects of class Wizard
     */
    public Wizard(Level level, int x, int y, Player mainPlayer, int worldNumber, ArrayList<Zombie> zombies)
    {
        super(level, x, y, mainPlayer, worldNumber);
        this.worldNumber = worldNumber;
        this.speed = 1;
        this.zombies = zombies;
        zombieTimer = new Timer();
    }

    public void tick()
    {
        super.tick();
        colorTick();
        zombieTick();
    }

    protected void colorTick()
    {
        if(color2 / 10 >= 5)
            goingDown = true;
        else if(color2 / 10 <= 0)
            goingDown = false;
        if(goingDown)
            color2 -= 10;
        else
            color2 += 10;
        color = Colors.get(-1, color1, color2, color3);
    }

    protected void zombieTick()
    {
        if(isAlive())
        {
            if(zombieTimer.getTime() >= 2000)
            {
                FastSmallZombie zomb = new FastSmallZombie(level, x, y, mainPlayer, worldNumber);
                level.addEntity(zomb);
                zombies.add(zomb);
                zombieTimer.start();
            }
        }
    }
}
