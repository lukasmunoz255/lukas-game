package Entities;

import Game.Tile;
import Game.Level;
import Graphics.Screen;

/**
 * The super class for all mobs in the game.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public abstract class Mob extends Entity {
    protected String name;

    protected int speed,
    numSteps = 0,
    movingDir = 1; //number 0-15 telling dirrection of the player, 0 is to the righ

    protected boolean isMoving;

    protected short health; 

    public Mob(final Level level, final String name, final int x, final int y, final int speed) {
        super(level);
        this.name = name;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    
    public boolean isAlive() { return health > 0; }

    protected final boolean isSolidTile(final int xa, final int ya, final int x, final int y) {
        if(level == null) { return false; }
        final Tile lastTile = level.getTile(((int)this.leftPixelX()+x) >> 3,      ((int)this.topPixelY()+y) >> 3);
        final Tile newTile  = level.getTile(((int)this.leftPixelX()+x + xa) >> 3, ((int)this.topPixelY()+y + ya) >> 3);
        if(!lastTile.equals(newTile) && newTile.isSolid()) { return true; } //CHANGE HERE <- Why change here?
        return false;
    }

    public String getName() { return name; }
}