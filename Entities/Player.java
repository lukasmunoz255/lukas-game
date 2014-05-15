package Entities;

import Game.Level;
import Sound.WaveFile;
import Sound.WavePlayer;
import Game.InputHandler;
import Graphics.Screen;
import Graphics.Colors;
import Graphics.Font;
import Game.Timer;
import Game.Game;

import Game.Tile;
import Game.BasicHurtingTile;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

/**
 * The main player of the game. 
 * 
 * Lukas Munoz Luke Staunton
 * 3/13/14
 */
public abstract class Player extends Mob 
{ 
    protected InputHandler input; 
    public double scale = 1; 
    public int index; 
    protected Screen screen; 
    protected int width; 
    protected int height; 

    protected String path2;

    int xTile;
    int yTile;
    private int ammo; 
    protected boolean canFire;
    public boolean fastFire = false;
    public boolean rapidFire = true;
    public boolean bulletUpgrade = false;

    protected Timer gunTimer;
    protected Timer healthTimer;
    private Timer nomTimer;
    private Timer garbageTimer;
    public Timer powerUpTimer;

    public WavePlayer shot1;
    public WavePlayer shot2;
    private WaveFile gunShot;
    private WaveFile gunClick;
    private WavePlayer ohShit;
    private WaveFile shiz;
    private WavePlayer nom1;
    private WaveFile omnomnom;
    private boolean coordinateGunSounds = false;

    public BufferedImage image;

    /**
     * Sets the string path2 for the path to where this is for finding the sounds
     */
    public Player(Level level, int x, int y, InputHandler input, Screen screen, String name) 
    { 
        super(level, name, x, y, 7); //The last value is for speed 
        path2 = getClass().getClassLoader().getResource(".").getPath();
        xMax = 16;
        xMin = 2;
        yMax = 12;
        yMin = 0;
        this.input = input; 
        this.index = index; 
        this.screen = screen; 
        gunTimer = new Timer();
        healthTimer = new Timer();
        nomTimer = new Timer();
        garbageTimer = new Timer();
        powerUpTimer = new Timer();
        garbageTimer.start();
        ohShit = new WavePlayer();
        //nom1 = new WavePlayer();
        enlargeBadPicture();
        nom1 = new WavePlayer();
        try
        {

            shiz = new WaveFile(path2 + "Sound/holyshit.wav");
            gunShot = new WaveFile(path2 + "Sound/gunshot.wav");
            shot1 = new WavePlayer();
            shot2 = new WavePlayer();
            omnomnom = new WaveFile(path2 + "Sound/omnomnom.wav");
            gunClick = new WaveFile(path2 + "Sound/dryfire.wav");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    } 

    public void setLevel(Level level)
    {
        this.level = level;
    }

    /**
     * We have a 32x32 pixel image and this enlarges the image, also pixelating it 
     * to keep with the style of the game
     */
    protected void enlargeBadPicture()
    {
        try
        {
            image = ImageIO.read(new File(path2 + "Images/otherPlayer.png"));
            BufferedImage newImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
            for(int xLuker = 0; xLuker < image.getWidth(); xLuker++)
            {
                for(int yLuker = 0; yLuker < image.getHeight(); yLuker++)
                {
                    int rgb = image.getRGB(xLuker, yLuker);
                    if((rgb & 0x00FFFFFF)  == 0x00000000)
                    {
                        for(int x = 0; x < 4; x++)
                        {
                            for(int y = 0; y < 4; y++)
                            {
                                newImage.setRGB(4 * xLuker + x, 4 * yLuker + y, 0x00055555);
                            }
                        }
                    }
                    else
                    {
                        for(int x = 0; x< 4; x++)
                        {
                            for(int y = 0; y < 4; y++)
                            {
                                newImage.setRGB(4 * xLuker + x, 4 * yLuker + y, rgb);
                            }
                        }
                    }
                }
            }
            image = newImage;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    protected boolean first;
    public void setHealth(short health)
    {
        this.health = health;
        ammo = 100;
        first = true;
    }

    public int getOnScreenX() 
    { 
        return this.x - screen.xOffset; 
    } 

    public int getOnScreenY() 
    { 
        return this.y - screen.yOffset; 
    } 

    /**
     * The soundFile of the gun sound has a slight delay so one player isn't able 
     * to keep up with the rapid fire.
     */
    private void gunFire(WaveFile file)
    {
        if(coordinateGunSounds)
        {
            shot1.play(file);
            coordinateGunSounds = false;
        }
        else
        {
            shot2.play(file);
            coordinateGunSounds = true;
        }
    }

    public void fire()
    {
        if(canFire)
        {
            if(ammo <= 0 && !fastFire && !bulletUpgrade)
                gunFire(gunClick);
            else
            {
                gunFire(gunShot);
                Game.bulletsFired++;
                if(!fastFire && !bulletUpgrade)
                    ammo--;
                Bullet b;
                if(bulletUpgrade)
                    b = new MediumBullet(level, getGunX(), getGunY(), input.getMouseX(), 
                        input.getMouseY(), getOnScreenX(), getOnScreenY(), level.bullets.size());
                else
                    b = new Bullet(level, getGunX(), getGunY(), input.getMouseX(), 
                        input.getMouseY(), getOnScreenX(), getOnScreenY(), level.bullets.size());
                level.addBullet(b);
            }
            canFire = false;
            gunTimer.start();
        }
    }

    /**
     * The lower the gun speed, the faster the gun fires. 
     * No negative numbers
     */
    protected int getGunSpeed()
    {
        if(fastFire)
            return 35;
        else
            return 90;
    }

    /**
     * The rapidFire bullets are taken care of in the gameScreen.clicked() method.
     */
    protected void fireTick()
    {
        if(!canFire)
        {
            if(gunTimer.getTime() > getGunSpeed())
            {
                gunTimer.stop();
                canFire = true;
            }
        }
        else
        {
            if(rapidFire)
            {
                if(input.mouseClicker.isPressed())
                    fire();
            }
        }
    }

    boolean canNom;
    protected void healthTick()
    {
        int decrements = level.playerZombieCollision();
        Bullet bullet = level.getBullet(this);
        if(bullet != null && bullet.fromZombie())
        {
            health-=100;
            bullet.hit();
        }
        if(decrements != 0)
        {
            //health-=decrements;
            if(!nomTimer.isRunning())
            {
                nomTimer.start();
                nom1.play(omnomnom);
            }
        }
        else
        {
            nom1.stop();
            nomTimer.stop();
        }
        if(nomTimer.getTime() > 2100)
            nomTimer.stop();
    }

    protected void moveTick()
    {
        int xa = 0; 
        int ya = 0; 
        if(isAlive())
        {
            if(input != null)
            {
                if(input.up.isPressed()) 
                    ya--; 
                if(input.down.isPressed()) 
                    ya++; 
                if(input.left.isPressed()) 
                    xa--; 
                if(input.right.isPressed()) 
                    xa++; 
            }
            if(xa != 0 || ya != 0) 
            { 
                move(xa, ya); 
                isMoving = true; 
            } 
            else
                isMoving = false; 
        }
    }

    public void powerUpTick()
    {
        if(powerUpTimer.isRunning())
        {
            if(powerUpTimer.getTime() >= 9000)
            {
                powerUpTimer.stop();
                fastFire = false;
                bulletUpgrade = false;
            }
        }
    }

    public void tick() 
    { 
        if(garbageTimer.getTime() >= 10000)
        {
            garbageTimer.stop();
            System.gc();
            garbageTimer.start();
        }
        powerUpTick();
        fireTick();
        healthTick();
        moveTick();
    } 

    public int getGunX()
    {
        return (int)(this.x + width()/4 + 15*Math.cos(transform + Math.PI/2));
    }

    public int getGunY()
    {
        return (int)(this.y + 15*Math.sin(transform + Math.PI/2));
    }

    protected double transform;
    protected double top;
    protected double bottom;
    protected void top()
    {
        top = input.getMouseY() - y + screen.yOffset;
    }

    protected void bottom()
    {
        bottom = input.getMouseX() - x + screen.xOffset;
    }

    public void transform()
    {
        if(bottom == 0)
        {
            if(top < 0)
                transform = Math.PI;
            else
                transform = 0;
        }
        else
        {
            if(bottom > 0)
                transform = Math.PI * 3/2 + Math.atan(top/bottom);
            else
                transform = Math.PI / 2 + Math.atan(top/bottom);
        }
    }

    public void render(Screen screen, Graphics g)  
    {  
        Graphics2D g2d = (Graphics2D)g;
        if(!input.pause.isPressed())
        {
            top();
            bottom();
            transform();
        }
        g2d.rotate(transform, (x - screen.xOffset + width()/2)*3, (y - screen.yOffset + height()/3)*3);
        g2d.drawImage(image, (int)(x - screen.xOffset - width())*3, (int)(y - screen.yOffset - height())*3, null);
        g2d.rotate(-transform, (x - screen.xOffset + width()/2)*3, (y - screen.yOffset + height()/3)*3);

        g2d.dispose();
    }  

    /** 
     * Sets the dirrection to a number 0-11 so that it is the dirrection closest 
     * to the mouse. 0 is to the right 
     */
    public int getDir() 
    { 
        int mouseX = input.getMouseX(); 
        int mouseY = input.getMouseY(); 
        double deltaY = mouseY - getOnScreenY(); 
        double deltaX = mouseX - getOnScreenX(); 
        double angle; 
        if(deltaX != 0) 
        { 
            angle = -1 * Math.atan(deltaY/deltaX); 
            if (angle < 0) 
                angle = Math.PI*2 + angle; 
            if(deltaX > 0) 
                return ((int)(angle/(Math.PI/2)*3 + .5)) % 12;      
            else
                return ((int)(angle/(Math.PI/2)*3 + .5) + 6) % 12; 
        } 
        else
        { 
            if(getOnScreenY() > mouseY) 
                return 9; 
            else
                return 3; 
        }  
    }

    protected boolean hasCollided(int xa, int ya)
    {
        for(int x = -7; x < width() + 1; x++)
        {
            if(isSolidTile(xa, ya, x, 0))
                return true;//right
            //if(isSolidTile(xa, ya, x, (int)height()))
            //  return true;
        }
        for(int y = -8; y <= height(); y++)
        {
            if(isSolidTile(xa, ya, 0, y))
                return true; // top
            //if(isSolidTile(xa, ya, (int)width(), y))
            //  return true;
        }
        return false;
    }

    protected void move(int xa, int ya)
    {
        if(isHurtingTile(xa, ya)){}
        // health-=.01;
        if(xa != 0 && ya != 0)
        {
            move(xa, 0);
            move(0, ya);
        }
        else if(!hasCollided(xa, ya))
        {
            x += xa * speed;
            y += ya * speed;
        }
    }

    protected boolean isHurtingTile(int xa, int ya)
    {
        if(level == null)
            return false;
        Tile lastTile = level.getTile(((int)this.leftPixelX()+x) >> 3, ((int)this.topPixelY()+y) >> 3);
        Tile newTile = level.getTile(((int)this.leftPixelX()+x + xa) >> 3, ((int)this.topPixelY()+y + ya) >> 3);
        if(lastTile.isHurting() || newTile.isHurting()) //CHANGE HERE
            return true;
        return false;
    }

    public void addAmmo(int addition)
    {
        this.ammo += addition;
    }

    public int health()
    {
        return (int)health;
    }

    public int ammo()
    {
        return ammo;
    }
} 