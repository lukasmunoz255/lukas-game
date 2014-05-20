package Game;
import Graphics.Screen;
import java.awt.Graphics;
import Sound.WavePlayer;

/**
 * Abstract class Stage - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Stage
{
    protected int buttonColor = 123;
    protected int fontColor = 013;
    String path2;

    protected Level level;
    protected Game game;
    protected WavePlayer soundPlayer;

    public Stage(Level level, Game game, WavePlayer soundPlayer)
    {
        this.soundPlayer = soundPlayer;
        path2 = getClass().getClassLoader().getResource(".").getPath();
        this.level = level;
        this.game = game;
    }

    public void tick()
    {
        level.tick();
    }
    
    public void renderTiles(Screen scrn, int xOff, int yOff)
    {
        if (level != null) { level.renderTiles(scrn, xOff, yOff); }
    }
    
    public abstract void renderEntities(Screen screen, Graphics g);
    
    public abstract void clicked(InputHandler input);
}
