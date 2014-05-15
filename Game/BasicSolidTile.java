package Game;


/**
 * Just a tile that is solid. It cant be passed through
 * 
 * Lukers
 * 1/28/14
 */
public class BasicSolidTile extends BasicTile
{
    /**
     * Constructor for a BasicSolidTile. Same as BasicTile but
     * isSolid is set to true.
     */
    public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor)
    {
        super(id, x, y, tileColor, levelColor);
        this.solid = true;
    }
}
