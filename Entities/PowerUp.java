package Entities;

import Game.Level;
import Graphics.Screen;
import Graphics.Colors;
import java.awt.Graphics;
import Sound.WavePlayer;
import Sound.WaveFile;

/**
 * Write a description of class PowerUp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class PowerUp extends Entity
{
    protected int xTile = 5;
    protected int yTile = 2;
    protected int color;
    protected int primaryColor;
    protected WavePlayer sound;
    protected WaveFile soundFile;

    public PowerUp(Level level, int x, int y, int primaryColor)
    {
        super(level);
        
        sound = new WavePlayer();
        this.x = x; 
        this.y = y;
        this.xMin = 0;
        this.xMax = 16;
        this.yMin = 0;
        this.yMax = 16;
        this.primaryColor = primaryColor;
        color = Colors.get(-1, 000, this.primaryColor, -1);
        setSoundFile("Sound/gunspeedpowerup.wav");
    }
    
    public void setSoundFile(String path)
    {
        String path2 = getClass().getClassLoader().getResource(".").getPath();
        try
        {
            soundFile = new WaveFile(path2 + path);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isAlive()
    {
        return false;
    }

    public void render(Screen screen, Graphics g)
    {
        int modifier = (int)(8 * scale);
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 4 - 4;

        screen.render(xOffset , yOffset, xTile + yTile * 32, color, 0, scale);                       
        screen.render(xOffset + modifier , yOffset, (xTile + 1) + yTile * 32, color, 0, scale);      
        screen.render(xOffset , yOffset + modifier, xTile + (yTile + 1) * 32, color, 0, scale); 
        screen.render(xOffset + modifier , yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, 0, scale); ;
    }

    public void touchTick()
    {
        Player p = level.getMainPlayer();
        if(p.leftPixelX() <= leftPixelX()+width()-5 && p.leftPixelX()+p.width() >= leftPixelX()-5 &&
        p.topPixelY() <= topPixelY()+height()-15 && p.topPixelY()+p.height() >= topPixelY()-15)
        {
            powerUp(p);
            remove();
            sound.play(soundFile);
        }
    }

    public void tick()
    {
        touchTick();
        colorTick();
    }

    protected abstract void colorTick();

    protected abstract void powerUp(Player p);
    
    protected abstract void remove();
    
}
