package Game;
import Graphics.Screen;

/**
 * Just a BasicTile that is not solid. 
 * 
 * Lukers
 * 1/28/14
 */
public class BasicTile extends Tile
{
    protected int tileId;
    protected int tileColor;
    
    /**
     * Constructor for BasicTile. Sets everything correctly. 
     */
    public BasicTile(int id, int x, int y, int tileColor, int levelColor)
    {
        super(id, false, false, levelColor);
        this.tileId = x + y;
        this.tileColor = tileColor;
    }
    
    /**
     * Just calls the render method from screen. Renders the correct tile
     * (using tileID) on the screem at the correct location.
     */
    public void render(Screen screen, Level level, int x, int y)
    {
        screen.render(x, y, tileId, tileColor, 0x00, 1.0);
    }
}
