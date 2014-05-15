package Entities;

import Game.Game;
import Game.Level;
import Game.InputHandler;
import Graphics.Screen;
import Graphics.Colors;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

/**
 * Write a description of class Ogre here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ogre extends BigZombie
{
    
    
    /**
     * Constructor for objects of class Ogre
     */
    public Ogre(Level level, int x, int y, Player mainPlayer, int worldNumber)
    {
        super(level, x, y, mainPlayer, worldNumber);
        this.health = (short)(33 + 4*worldNumber);
        this.maxHealth = (short)(33 + 4*worldNumber);
        this.speed = 2 + worldNumber/3;
        this.xMin = 0; //Left
        this.xMax = 24; //Right
        this.yMin = 4; //Top
        this.yMax = 28; //Bottom
    }
    
    public void increaseKills()
    {
        Game.kills += 5;
    }
    
    public int getYTile()
    {
        if(health <= 3)
            return 18;
        return 15;
    }
    
    public int getXTile()
    {
        if(!alive)
            return 27;
        else if(health <= maxHealth*1/11)
            return 24;
        else if(health <= maxHealth*2/11)
            return 27;
        else if(health <= maxHealth*3/11)
            return 24;
        else if(health <= maxHealth*4/11)
            return 21;
        else if(health <= maxHealth*5/11)
            return 18;
        else if(health <= maxHealth*6/11)
            return 15;
        else if(health <= maxHealth*7/11)
            return 12;
        else if(health <= maxHealth*8/11)
            return 9;
        else if(health <= maxHealth*9/11)
            return 6;
        else if(health <= maxHealth*10/11)
            return 3;
        else
            return 0;
    }
}
