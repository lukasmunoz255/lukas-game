package Entities;

import Game.Tile;
import Game.Level;
import Graphics.Screen;

/**
 * Write a description of class Mob here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Mob extends Entity
{
    protected String name;
    protected int speed;
    protected int numSteps = 0;
    protected boolean isMoving;
    protected int movingDir = 1; //number 0-15 telling dirrection of the player, 0 is to the righ
    

    protected short health; 

    public Mob(Level level, String name, int x, int y, int speed)
    {
        super(level);
        this.name = name;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }
    
    
    
    public boolean isAlive()
    {
        return health > 0;
    }

    /*
    protected boolean isBullet(int x, int y)
    3693

    {
    for(int xa = x; xa < x+4; xa++)
    {
    for(int ya = y; y < y+4; y++)
    {
    if(level.isBullet(xa, ya))
    return true;
    }
    }
    return false;
    }
     */

    protected boolean isSolidTile(int xa, int ya, int x, int y)
    {
        if(level == null)
            return false;
        Tile lastTile = level.getTile(((int)this.leftPixelX()+x) >> 3, ((int)this.topPixelY()+y) >> 3);
        Tile newTile = level.getTile(((int)this.leftPixelX()+x + xa) >> 3, ((int)this.topPixelY()+y + ya) >> 3);
        if(!lastTile.equals(newTile) && newTile.isSolid()) //CHANGE HERE
            return true;
        return false;
    }
 

    public String getName()
    {
        return name;
    }

}