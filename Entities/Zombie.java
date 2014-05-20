package Entities;

import Game.Level;
import Game.Game;
import Game.InputHandler;
import Game.Debugger;

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
 * The superclass of all zombies.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public class Zombie extends Player
{
    protected Player mainPlayer;
    protected int color,
    color1,
    color2,
    color3,
    movingX,
    movingY,
    movingAroundWallXDirection,
    movingAroundWallYDirection,
    maxHealth;

    protected boolean atWallX = false,
    atWallY = false,
    first = true;
    protected boolean alive;
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
        try { owFile = new WaveFile(path2 + "Sound/zombiehit.wav"); }
        catch(Exception e) { e.printStackTrace(); }
    }

    protected int getColorOnWorld(int worldNumber)
    {
        final int colors[][] = {
                {221, 555, 500},
                {210, 151, 500},
                {221, 342, 500},
                {121, 355, 510},
                {111, 412, 511}};
        int colorSet[] = null;
        final int DEFAULT = 0;

        if (worldNumber >= 1 && worldNumber <= 4)   { colorSet = colors[worldNumber]; }
        else                                        { colorSet = colors[DEFAULT];     }

        return Colors.get(-1, colorSet[0], colorSet[1], colorSet[2]);
    }

    public boolean isAlive() { return alive; }

    public void tick() {
        top();
        bottom();
        int deltaX = ((bottom < 0) ? (-1) : (1)),
        deltaY = ((top < 0) ? (-1) : (1));
        move(deltaX, deltaY);
    }

    public int getXTile() {
        if(!alive) { return 2; }
        else if(health <= maxHealth/2) { return 4; }
        else { return 0; }
    }

    public int getYTile()
    {
        return 25;
    }

    public void top() {
        top = mainPlayer.y + mainPlayer.height()/2 - (y + height()/2);
    }

    public void bottom() {
        bottom = mainPlayer.x + mainPlayer.width()/2 - 2 - (x + width()/2);
    }

    public void render(Screen screen, Graphics g) {
        xTile = getXTile();
        yTile = getYTile();
        int modifier = (int)(8 * scale),
        xOffset = x - modifier / 2, 
        yOffset = y - modifier / 4 - 4;  

        screen.render(xOffset , yOffset, xTile + yTile * 32, color, 0, scale);                       //Upper  
        screen.render(xOffset + modifier , yOffset, (xTile + 1) + yTile * 32, color, 0, scale);      //Body  
        screen.render(xOffset , yOffset + modifier, xTile + (yTile + 1) * 32, color, 0, scale); 
        screen.render(xOffset + modifier , yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, 0, scale); 
    }

    public void increaseKills() {
        Game.kills++;
    }

    public void move(int xa, int ya) {
        if(health <= 0)
        {
            if(first)
            {
                Debugger.sendMsg(String.format("Killed %s", getClass().getSimpleName()));
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
