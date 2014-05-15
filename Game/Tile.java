package Game;
import Graphics.*;

/**
 * The classic tile class. Contains all of the tiles that are used for levels and keeps track
 * of the colors that the tiles are used for in the level editor (photoshop). All of the parameters of 
 * the tiles include in this order: 
 * 
 * tileID, xCoordinate(on spritesheet), yCoordinate(on spritesheet), tileColor, tileColorOnLevelEditor
 * 
 * The last parameter is specific to the creation of a level. A tile created in the level editor with the 
 * color 0xFF000000 will be a VOID tile during the game. 
 * 
 * Lukers
 * 1/28/14
 */
public abstract class Tile
{
    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(000, -1, -1, -1), 0xFF000000);
    public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(-1, 111, -1, -1), 0xFF555555);
    public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131, 141, -1), 0xFF00F100);
    public static final Tile SKY = new BasicTile(3, 3, 0, Colors.get(-1, -1, -1, 135), 0xFF0000FF);
    public static final Tile CLOUD = new BasicTile(4, 4, 0, Colors.get(-1, -1, 444, -1), 0xFFAABBBB);
    public static final Tile BACKSPACE = new BasicTile(5, 1, 0, Colors.get(-1, 554,-1, -1), 0xFFFFFFCC);
    public static final Tile LAVA = new BasicHurtingTile(6, 5, 0, Colors.get(-1, 500, 520, -1), 0xFFFF6D00);
    public static final Tile STONEGROUND = new BasicTile(7, 6, 0, Colors.get(333, -1, -1, -1), 0xFF333333);
    public static final Tile CARPET = new BasicTile(8, 7, 0, Colors.get(-1, 414, 411, -1), 0xFFF160FF);
    public static final Tile CARPETLEVELWALLS = new BasicSolidTile(9, 1, 0, Colors.get(-1, 320, -1, -1), 0xFF7F5400);
    public static final Tile WOODFLOOR = new BasicTile(10, 8, 0, Colors.get(-1, 540, 420, -1), 0xFFFFAA00);
    
    protected byte id;
    protected boolean solid;
    protected boolean hurting;
    protected boolean emitter;
    private int levelColor;

    /**
     * Constructor for any tile. It all comes back to here, this sets up the basics
     * of a tile.
     */
    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor)
    {
        this.id = (byte)id;
        if(tiles[id] != null)
        {
            throw new RuntimeException("Duplicate the id on" + id);
        }
        this.solid = isSolid;
        this.hurting = false;
        this.emitter = isEmitter;
        this.levelColor = levelColor;
        tiles[id] = this;
    }

    /**
     * Returns true if a tile is solid
     */
    public boolean isSolid()
    {
        return solid;
    }

    /**
     * Returns true is a tile is an emitter
     */
    public boolean isEmitter()
    {
        return emitter;
    }

    /**
     * Returns the ID of a tile.
     */
    public byte getId()
    {
        return id;
    }

    /**
     * Returns the levelColor of a tile.
     */
    public int getLevelColor()
    {
        return levelColor;
    }
    
    public boolean isHurting()
    {
        return hurting;
    }

    public abstract void render(Screen screen, Level level, int x, int y);
}
