package Entities;

import Game.Level;
import Graphics.Screen;
import Graphics.Colors;
import java.awt.Graphics;
import Sound.WavePlayer;
import Sound.WaveFile;

/**
 * The super class for all powerups used in the game.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public abstract class PowerUp extends Entity {
    protected int xTile = 5,
    yTile = 2,
    color,
    primaryColor;
    
    protected WavePlayer sound;
    protected WaveFile soundFile;

    public PowerUp(final Level level, final int x, final int y, final int primaryColor) {
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

    public void setSoundFile(String path) {
        final String path2 = getClass().getClassLoader().getResource(".").getPath();
        try {
            soundFile = new WaveFile(path2 + path);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isAlive() { return false; }

    public void render(final Screen screen, final Graphics g) {
        final int modifier = (int)(8 * scale);
        final int xOffset = x - modifier / 2;
        final int yOffset = y - modifier / 4 - 4;

        screen.render(xOffset , yOffset, xTile + yTile * 32, color, 0, scale);                       
        screen.render(xOffset + modifier , yOffset, (xTile + 1) + yTile * 32, color, 0, scale);      
        screen.render(xOffset , yOffset + modifier, xTile + (yTile + 1) * 32, color, 0, scale); 
        screen.render(xOffset + modifier , yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, 0, scale); ;
    }

    public final void touchTick() {
        Player p = level.getMainPlayer();
        if(p.leftPixelX() <= leftPixelX()+width()-5 &&
        p.leftPixelX()+p.width() >= leftPixelX()-5 &&
        p.topPixelY() <= topPixelY()+height()-15 &&
        p.topPixelY()+p.height() >= topPixelY()-15) {
            powerUp(p);
            remove();
            sound.play(soundFile);
        }
    }

    public void tick() {
        touchTick();
        colorTick();
    }

    protected abstract void colorTick();

    protected abstract void powerUp(final Player p);

    protected abstract void remove();   
}
