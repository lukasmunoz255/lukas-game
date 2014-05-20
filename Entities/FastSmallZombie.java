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
 * The class for Fast-Small Zombies
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public class FastSmallZombie extends Zombie
{
    /**
     * Constructor for objects of class FastSmallZombie
     */
    public FastSmallZombie(Level level, int x, int y, Player mainPlayer, int worldNumber) {
        super(level, x, y, mainPlayer, worldNumber);
        this.speed = 4 + worldNumber/5;
        this.maxHealth = (short)worldNumber;
        this.health = (short)worldNumber;
    }
    
    public int getXTile()
    {
        return ((alive) ? (4) : (2));
    }
    
    public void increaseKills()
    {
        Game.kills += 2;
    }
}
