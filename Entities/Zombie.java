package Entities;

import Game.Level;
import Game.Game;
import Game.InputHandler;
import Graphics.Screen;
import Graphics.Colors;

import Sound.WaveFile;
import Sound.WavePlayer;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends Player
{
    protected Player mainPlayer;
    protected int color;
    protected int color1;
    protected int color2;
    protected int color3;
    protected int movingX;
    protected int movingY;
    protected boolean atWallX = false;
    protected boolean atWallY = false;
    protected boolean first = true;
    protected int movingAroundWallXDirection;
    protected int movingAroundWallYDirection;
    protected boolean alive;
    protected int maxHealth;
    protected WavePlayer owSound;
    protected WaveFile owFile;

    public Zombie(Level level, int x, int y, Player mainPlayer, int worldNumber)
    {
        super(level, x, y, null, null, "Zombie");
        this.scale = 2;
        this.mainPlayer = mainPlayer;
        this.speed = 2 + worldNumber/4;
        this.alive = true;
        this.health = (short)(2 * worldNumber);
        this.maxHealth = (short)(2 * worldNumber);
        this.color = getColorOnWorld(worldNumber);

        
        xMin = 1; //Left 0
        xMax = 8; //Right 4
        yMin = 0; //Top 0
        yMax = 12; //Bottom 5 

        owSound = new WavePlayer();
        try
        {
            owFile = new WaveFile(path2 + "Sound/zombiehit.wav");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected int getColorOnWorld(int worldNumber)
    {
        if(worldNumber == 1)
        {
            color1 = 210;
            color2 = 151;
            color3 = 500;
        }
        else if(worldNumber == 2)
        {
            color1 = 221;
            color2 = 342;
            color3 = 500;
        }
        else if(worldNumber == 3)
        {
            color1 = 121;
            color2 = 355;
            color3 = 510;
        }
        else if(worldNumber == 4)
        {
            color1 = 111;
            color2 = 412;
            color3 = 511;
        }
        else
        {
            color1 = 221;
            color2 = 555;
            color3 = 500;
        }
        return Colors.get(-1, color1, color2, color3);
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void tick()
    {
        top();
        bottom();
        int deltaX;
        int deltaY;
        if(top < 0)
            deltaY = -1;
        else
            deltaY = 1;
        if(bottom < 0)
            deltaX = -1;
        else
            deltaX = 1;
        move(deltaX, deltaY);
    }

    public int getXTile()
    {
        if(!alive)
            return 2;
        else if(health <= maxHealth/2)
            return 4;
        else
            return 0;
    }

    public int getYTile()
    {
        return 25;
    }

    public void top()
    {
        top = mainPlayer.y + mainPlayer.height()/2 - (y + height()/2);
    }

    public void bottom()
    {
        bottom = mainPlayer.x + mainPlayer.width()/2 - 2 - (x + width()/2);
    }

    public void render(Screen screen, Graphics g)
    {
        xTile = getXTile();
        yTile = getYTile();
        int modifier = (int)(8 * scale);  
        int xOffset = x - modifier / 2;  
        int yOffset = y - modifier / 4 - 4;  

        screen.render(xOffset , yOffset, xTile + yTile * 32, color, 0, scale);                       //Upper  
        screen.render(xOffset + modifier , yOffset, (xTile + 1) + yTile * 32, color, 0, scale);      //Body  
        screen.render(xOffset , yOffset + modifier, xTile + (yTile + 1) * 32, color, 0, scale); 
        screen.render(xOffset + modifier , yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, 0, scale); 
    }

    public void increaseKills()
    {
        Game.kills++;
    }

    public void move(int xa, int ya)
    {
        if(health <= 0)
        {
            if(first)
            {
                owSound.play(owFile);
                increaseKills();
                first = false;
            }
            alive = false;
        }
        if(alive)
        {
            Bullet bullet = level.getBullet(this);
            if(alive && bullet != null && !bullet.fromZombie())
            {
                bullet.hit();
                if(bullet.getHealth() <= 0)
                    level.removeBullet(bullet.index);
                Game.bulletsHit++;
                health -= 1D;
            }
            if(atWallX || atWallY)
            {
                if(atWallX && !hasCollidedX(xa))
                {
                    atWallX = false;
                    moveY(movingAroundWallYDirection * 3);
                }
                if(atWallY && !hasCollidedY(ya))
                {
                    atWallY = false;
                    moveX(movingAroundWallXDirection * 3);
                }
                if(atWallX)
                {
                    if(movingAroundWallYDirection == 0)
                        movingAroundWallYDirection = ya;
                    moveY(movingAroundWallYDirection);
                }
                if(atWallY)
                {
                    if(movingAroundWallXDirection == 0)
                        movingAroundWallXDirection = xa;
                    moveX(movingAroundWallXDirection);
                }
            }
            else
            {
                boolean xCollision = hasCollidedX(xa);
                boolean yCollision = hasCollidedY(ya);
                if(xa != 0 && ya != 0)
                {
                    move(xa, 0);
                    move(0, ya);
                }
                else if(!xCollision && !yCollision)
                {
                    y += ya * speed;
                    x += xa * speed;
                }
                if(xCollision && !yCollision)
                {
                    atWallX = true;
                    movingAroundWallYDirection = ya;
                }
                if(!xCollision && yCollision)
                {
                    atWallY = true;
                    movingAroundWallXDirection = xa;
                }
            }
        }
    }

    public void moveX(int xa)
    {
        x += xa * speed;
    }

    public void moveY(int ya)
    {
        y += ya * speed;
    }

    public boolean hasCollidedX(int xa)
    {
        for(int x = -7; x < width() + 1; x++)
        {
            if(isSolidTile(xa, 0, x, 0))
                return true;//right
        }
        return false;
    }

    public boolean hasCollidedY(int ya)
    {
        for(int y = -8; y <= height(); y++)
        {
            if(isSolidTile(0, ya, 0, y))
                return true; // top
        }
        return false;
    }
}
