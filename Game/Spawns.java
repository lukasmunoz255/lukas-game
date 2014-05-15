package Game;

import Entities.Entity;
import Entities.Zombie;
import Entities.FastSmallZombie;
import Entities.BigZombie;
import Entities.Ogre;
import Entities.Player;
import Graphics.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class Spawns here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spawns
{
    private SpawnPoint[] spawnPoints;
    public Spawns(Level level)
    {
        spawnPoints = new SpawnPoint[20];
        int index = 0;
        for(int x = 1; x <= 5; x++)
        {
            spawnPoints[index] = new SpawnPoint((x*2*level.width/11)*8, 10);
            spawnPoints[index+1] = new SpawnPoint((x*2*level.width/11)*8, level.height*8-30);
            index+=2;
        }
        for(int y = 1; y <= 5; y++)
        {
            spawnPoints[index] = new SpawnPoint(10, (y*2*level.height/11)*8);
            spawnPoints[index+1] = new SpawnPoint(level.width*8-30, (y*2*level.height/11)*8);
            index+=2;
        }
    }
    
    public void initializeEnemies(ArrayList<Zombie> zombies, List<Entity> entities, Level level, Screen screen, Player player, int difficulty, int worldNumber)
    {
        int index;
        int zombDiff;
        int spawnNumber = 0;
        while(spawnNumber < difficulty)
        {
            index = (int)(Math.random() * 20D);
            if(!spawnPoints[index].isFull())
            {
                zombDiff = difficulty/3 % (spawnNumber + 1);
                spawnPoints[index].spawn(zombies, entities, level, screen, player, zombDiff, worldNumber);
                spawnNumber += zombDiff + 1;
            }
        }
    }
    
    public void resetSpawns()
    {
        for(SpawnPoint s : spawnPoints)
        {
            s.reset();
        }
    }
}
