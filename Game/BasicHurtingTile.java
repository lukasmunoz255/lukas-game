package Game;


/**
 * Write a description of class BasicHurtingTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BasicHurtingTile extends BasicTile
{
    /**
     * Constructor for objects of class BasicHurtingTile
     */
    public BasicHurtingTile(int id, int x, int y, int tileColor, int levelColor) 
    {
        super(id, x, y, tileColor, levelColor);
        this.hurting = true;
    }
}
