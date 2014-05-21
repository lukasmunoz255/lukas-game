package Game;

import Entities.Entity;
import Entities.Zombie;
import Entities.FastSmallZombie;
import Entities.BigZombie;
import Entities.Ogre;
import Entities.FinalBoss;
import Entities.Player;
import Entities.Wizard;
import Graphics.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class SpawnPoint here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpawnPoint
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;
    private boolean spawned;

    public SpawnPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
        spawned = false;
    }

    public void spawn(ArrayList<Zombie> zombies, List<Entity> entities, Level level, Screen screen, Player player, int zombie, int worldNumber)
    {
        Zombie zomb = null;

        if(zombie == 0)
        {
            zomb = new Zombie(level, x, y, player, worldNumber);
            //zomb = new FinalBoss(level, x, y, screen, player, zombies);
        }
        else if(zombie == 1)
            zomb = new FastSmallZombie(level, x, y, player, worldNumber);
        else if(zombie == 2)
            zomb = new BigZombie(level, x, y, player, worldNumber);
        else if(zombie == 3)
            zomb = new Ogre(level, x, y, player, worldNumber);
        else if(zombie == 4)
            zomb = new Wizard(level, x, y, player, worldNumber, zombies);
        zombies.add(zomb);
        entities.add(zomb);
        spawned = true;
        Debugger.sendMsg(String.format("Spawned %s at (%d, %d)", zomb.getClass().getSimpleName(), zomb.x, zomb.y));
    }

    public boolean isFull()
    {
        return spawned;
    }

    public void reset()
    {
        spawned = false;
    }
}
