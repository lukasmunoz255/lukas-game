package Entities;

import Game.Game;
import Game.Level;
import Game.InputHandler;
import Graphics.Screen;
import Graphics.Colors;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

/**
 * Write a description of class BigZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BigZombie extends Zombie
{   
    public BigZombie(Level level, int x, int y, Player mainPlayer, int worldNumber)
    {
        super(level, x, y, mainPlayer, worldNumber);
        this.health = (short)(10 + (worldNumber-1) * 4);
        this.maxHealth = (short)(10 + (worldNumber-1) * 4);
        this.speed = 2 + worldNumber/5;
        this.xMin = 0; //Left
        this.xMax = 18; //Right
        this.yMin = 2; //Top
        this.yMax = 18; //Bottom
    }
    
    public void increaseKills()
    {
        Game.kills += 3;
    }
    
    public int getXTile()
    {
        if(!alive)
            return 15;
        else if(health <= maxHealth*1/5)
            return 12;
        else if(health <= maxHealth*2/5)
            return 9;
        else if(health <= maxHealth*3/5)
            return 6;
        else if(health <= maxHealth*4/5)
            return 3;
        else
            return 0;
    }
    
    public int getYTile()
    {
        return 22;
    }
    
     public void render(Screen screen, Graphics g)
    {
        xTile = getXTile();
        yTile = getYTile();
        int walkingSpeed = 4;
        int flipTop = (numSteps >> walkingSpeed) & 1;
        int flipBottom = (numSteps >> walkingSpeed) & 1;

        if(movingDir == 0)
            xTile += 2;
        else if(movingDir > 1)
        {
            xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = (movingDir - 1) % 2;
        }

        int modifier = (int)(8 * scale);
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 4 - 4;
        /*
        screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);                 //Upper
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (1 + xTile) + yTile * 32, color, flipTop, scale);//Body
        */
        
        screen.render(xOffset, yOffset, xTile + yTile * 32, color, flipTop, scale);                 //Upper
        screen.render(xOffset + modifier, yOffset, (1 + xTile) + yTile * 32, color, flipTop, scale);//Body
        screen.render(xOffset + modifier * 2, yOffset, (2 + xTile) + yTile * 32, color, flipTop, scale);
        
        screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, color, flipTop, scale);
        screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);  //the 0's were flip bottoms  //Lower
        screen.render(xOffset + modifier * 2, yOffset + modifier, (xTile + 2) + (yTile + 1) * 32, color, flipBottom, scale);              //Body

        screen.render(xOffset, yOffset + modifier * 2, xTile + (yTile + 2) * 32, color, flipTop, scale);
        screen.render(xOffset + modifier, yOffset + modifier * 2, (xTile + 1) + (yTile + 2) * 32, color, flipBottom, scale);  //the 0's were flip bottoms  //Lower
        screen.render(xOffset + modifier * 2, yOffset + modifier * 2, (xTile + 2) + (yTile + 2) * 32, color, flipBottom, scale);  
    }
}
