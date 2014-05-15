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
 * Write a description of class FastSmallZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FastSmallZombie extends Zombie
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class FastSmallZombie
     */
    public FastSmallZombie(Level level, int x, int y, Player mainPlayer, int worldNumber)
    {
        super(level, x, y, mainPlayer, worldNumber);
        this.speed = 4 + worldNumber/5;
        this.maxHealth = (short)worldNumber;
        this.health = (short)worldNumber;
    }
    
    public int getXTile()
    {
        if(!alive)
            return 2;
        return 4;
    }
    
    public void increaseKills()
    {
        Game.kills += 2;
    }
}
