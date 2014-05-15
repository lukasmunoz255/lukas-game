package Game;

import Graphics.Screen;
import Entities.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ConcurrentModificationException;
import java.awt.Graphics;

/**
 * The Level class is used to load a level created in photoshop. We keep
 * track of the tiles and width and height in this class. It is also important to note
 * that all of the entities are created, destroyed, and stored in this class. 
 * 
 * Lukers
 * 1/28/14
 */
public class Level
{
    private byte[] tiles;
    public int width;
    public int height;
    public List<PlayerMP> players = new ArrayList<PlayerMP>();
    public List<Entity> entities = new ArrayList<Entity>();
    public List<Bullet> bullets = new ArrayList<Bullet>();
    public List<AmmoCrate> ammo = new ArrayList<AmmoCrate>();
    public PowerUp powerUp;
    private GunSpeedPowerUp gunSpeedPowerUp;
    private BulletPowerUp bulletPowerUp;
    private String imagePath;
    private BufferedImage image;
    //boolean entitiesMayBeAdded = false;
    //boolean canRender = true;

    /**
     * Created a level from an imagePath or creates a default level if 
     * the imagePath is null
     */
    public Level(String imagePath)
    {
        gunSpeedPowerUp = new GunSpeedPowerUp(this, 0, 0);
        bulletPowerUp = new BulletPowerUp(this, 0, 0);

        if(imagePath != null)
        {
            this.imagePath = imagePath;
            this.loadLevelFromFile();
        }
        else
        {
            this.width = 64;
            this.height = 64;
            tiles = new byte[width * height];
            this.generateLevel();
        }
    }

    /**
     * Loads a level from a given file. We use .png files only.
     */
    private void loadLevelFromFile()
    {
        try
        {
            this.image = ImageIO.read(new File(this.imagePath));
            this.width = image.getWidth();
            this.height = image.getHeight();
            tiles = new byte[width * height];
            this.loadTiles();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Loads the level using our tiles. Every color of pixel in the level file has
     * a specific tile that we use and set in the tile classes (getID()). 
     */
    private void loadTiles()
    {
        int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                tileCheck: for(Tile t : Tile.tiles) //I have no idea what "tileCheck" means
                {
                    if(t != null && t.getLevelColor() == tileColors[x + y * width])
                    {
                        this.tiles[x + y * width] = t.getId();
                        break tileCheck;
                    }    
                }
            }
        }
    }

    /**
     * Saves a level to a file.
     */
    private void saveLevelToFile()
    {
        try
        {
            ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This changes a certain tile in the level. Shouldn't need
     * to use this if levels are created correctly.
     */
    public void alterTile(int x, int y, Tile newTile)
    {
        this.tiles[x + y * width] = newTile.getId();
        image.setRGB(x, y, newTile.getLevelColor());
    }

    /**
     * Generates a random level. Only used if the level imagePath
     * is null. 
     */
    public void generateLevel()
    {
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                tiles[x+y*width] = Tile.GRASS.getId();
                if(x * y % 10 < 7)
                    tiles[x+y*width] = Tile.STONEGROUND.getId();
                else
                    tiles[x+y*width] = Tile.LAVA.getId();
            }
        }
    }

    /**
     * Calls the tick for every entity in the level. This is how 
     * all of the entities are synchronized. It checks for canRender almost 
     * constantly to avoid ConcurrentModificationErrors. I was getting a stackOverflow
     * once every couple games and it was frustrating so I did a rudimentary sync.
     */
    public void tick()
    {
        //while(!canRender)
        //{}
        //entitiesMayBeAdded = false;
        try
        {
            for(int i = 0; i < bullets.size(); i++)
                bullets.get(i).tick();
            for(int i = 0; i < players.size(); i++)
                players.get(i).tick();
            for(int i = 0; i < entities.size(); i++)
                entities.get(i).tick();
            for(int i = 0; i < ammo.size(); i++)
                ammo.get(i).tick();
            if(powerUp != null)
                powerUp.tick();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            tick();
        }
        //entitiesMayBeAdded = true;
    }

    /**
     * Renders all of the entities and then renders the player last.
     * Rendering the player last puts the player on top of everything.
     */
    public void renderEntities(Screen screen, Graphics g)
    {
        //while(!canRender)
        //{}
        //entitiesMayBeAdded = false;
        try
        {
            for(int i = 0; i < entities.size(); i++)
                entities.get(i).render(screen, g);
            for(int i = 0; i < players.size(); i++)
                players.get(i).render(screen, g);
            for(int i = 0; i < bullets.size(); i++)
                bullets.get(i).render(screen, g);
            for(int i = 0; i < ammo.size(); i++)
                ammo.get(i).render(screen, g);
            if(powerUp != null)
                powerUp.render(screen, g);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            renderEntities(screen, g);
        }
        //entitiesMayBeAdded = true;
    }

    /**
     * Renders all of the tiles in the level to the screen and 
     * set the x and yOffset. Offsets are used when the screen that we see
     * isn't large enough for the level that is created. 
     */
    public void renderTiles(Screen screen, int xOffset, int yOffset)
    {
        if(xOffset < 0)
            xOffset = 0;
        if(xOffset > ((width << 3) - screen.width))
            xOffset = ((width << 3) - screen.width);
        if(yOffset < 0)
            yOffset = 0;
        if(yOffset > ((height << 3) - screen.height))
            yOffset = ((height << 3) - screen.height);

        screen.setOffset(xOffset, yOffset);

        for(int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++)
            for(int x = (xOffset >> 3); x < (xOffset + screen.width >> 3); x++)
                getTile(x, y).render(screen, this, x << 3, y << 3);
    }

    /**
     * Returns a bullet at the passed in x and y. Returns null if there is no 
     * bullet at those coordinates. This is used later for hit detection.
     */
    public Bullet getBullet(Player player)
    {
        for(int i = 0; i < bullets.size(); i++)
        {
            Bullet b = bullets.get(i);
            if(b.leftPixelX()-7 <= player.rightPixelX() && b.rightPixelX()+7 >= player.leftPixelX() &&
            b.topPixelY()-7 <= player.bottomPixelY() && b.bottomPixelY()+7 >= player.topPixelY())
                return b;
        }
        return null;
    }

    /**
     * Returns the number of playeries that are touching the player at any given time. 
     * The more playeries on the player, the more health the player loses.
     */
    public int playerZombieCollision()
    {
        int decreaseHealth = 0;
        for(int i = 0; i < entities.size(); i++)
        {
            if(entities.get(i).isAlive())
            {
                Zombie zomb = (Zombie)entities.get(i);
                if(this.getMainPlayer().leftPixelX()-1 <= zomb.rightPixelX() && this.getMainPlayer().rightPixelX()+1 >= zomb.leftPixelX()
                && this.getMainPlayer().topPixelY()-1 <= zomb.bottomPixelY() && this.getMainPlayer().bottomPixelY()+1 >= zomb.topPixelY())
                    decreaseHealth++;
            }
        }
        return decreaseHealth;
    }

    /**
     * Returns the main player of the game.
     */
    public List<PlayerMP> getMainPlayers()
    {
        return players;
    }
    
    public Player getMainPlayer()
    {
        return players.get(0);
    }

    /**
     * Returns the tile at a passed in x and y coordinate.
     */
    public Tile getTile(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return Tile.VOID;
        return Tile.tiles[tiles[x+y*width]];
    }

    public void removeBullet(int index)
    {
        //anRender = false;
        try
        {
            this.bullets.remove(index);
            for(int i = index; i < bullets.size(); i++)
            {
                Bullet b = bullets.get(i);
                b.index = b.index - 1;
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
        //canRender = true;
    }

    /**
     * Adds an entity to the arraylist of entities.
     */
    public void addEntity(Entity entity)
    {
        this.entities.add(entity);
    }

    public void addPlayer(Player p)
    {
        this.players.add((PlayerMP)p);
    }

    public void addBullet(Bullet b)
    {
        this.bullets.add(b);
    }

    /**
     * Resets the arrayList of entities so only the player is remaining.
     * This essentially resets the level. 
     */
    public void reset()
    {
        while(entities.size() > 0)
            entities.remove(0);
        while(bullets.size() > 0)
            bullets.remove(0);
        //removePowerUp();
    }

    public void addPowerUp()
    {
        int type = (int)(Math.random() * 2);
        if(type == 0)
            powerUp = gunSpeedPowerUp;
        else if(type == 1)
            powerUp = bulletPowerUp;
        else if(type == 2)
        {}
        powerUp.x = (int)(Math.random() * width * 8);
        powerUp.y = (int)(Math.random() * height * 8);
    }

    public void removePowerUp()
    {
        powerUp = null;
    }

    public void addAmmoCrate()
    {
        ammo.add(new AmmoCrate(this, (int)(Math.random() * width * 8), (int)(Math.random() * height * 8), ammo.size()));
    }

    public void removeAmmoCrate(int index)
    {
        try
        {
            this.ammo.remove(index);
            for(int i = index; i < ammo.size(); i++)
            {
                AmmoCrate a = ammo.get(i);
                a.index = a.index - 1;
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }

    public void emptyEntities()
    {
        while(entities.size() > 0)
            entities.remove(0);
        while(bullets.size() > 0)
            bullets.remove(0);
        while(players.size() > 0)
            players.remove(0);
        removePowerUp();
    }
}
