package Entities;

import Game.Level;
import Game.Timer;
import Graphics.Screen;
import Graphics.Colors;

import java.awt.Graphics;

/**
 * Write a description of class AmmoCrate here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AmmoCrate extends PowerUp
{
    public int index;
    private Timer lifeTimer;
    
    public AmmoCrate(Level level, int x, int y, int index)
    {
        super(level, x, y, 000);
        this.index = index;
        xTile = 0;
        yTile = 14;
        this.color = Colors.get(-1, 000, 510, 222);
        lifeTimer = new Timer();
        lifeTimer.start();
        this.scale = 2;
        setSoundFile("Sound/reloadsound.wav");
    }
    
    public void render(Screen screen, Graphics g)
    {
        int modifier = (int)(8 * scale);
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 4 - 4;
        screen.render(xOffset, yOffset, xTile + yTile*32, color, 0, scale);
    }
    
    public void powerUp(Player p)
    {
        p.addAmmo(25);
    }
    
    public void tick()
    {
        super.tick();
        if(lifeTimer.getTime() >= 15000)
            remove();
    }
    
    public void remove()
    {
        level.removeAmmoCrate(index);
    }
    
    public void colorTick()
    {}
}
