package Entities;

import Game.Level;
import Sound.WaveFile;
import Sound.WavePlayer;
import Graphics.Screen;
import Graphics.Colors;
import Game.Timer;
import Sound.WaveFile;
import Sound.WavePlayer;

import java.awt.Graphics;
//import java.util.Random;
import java.util.ArrayList;

/**
 * Write a description of class BossWorldFive here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FinalBoss extends Zombie
{
    private int color;
    private int mainColor = 555;
    private int subColor = 533;
    private Player player;
    private Timer bulletTimer;
    private Timer zombieTimer;
    private Timer laughTimer;
    private Screen screen;
    private ArrayList<Zombie> zombies;
    private int modifier;
    private WavePlayer cannonShot1;
    private WavePlayer laughing;
    private WaveFile cannon;
    private WaveFile laugh;
    boolean up = false;

    public FinalBoss(Level level, int x, int y, Screen screen, Player player, ArrayList<Zombie> zombies)
    {
        super(level, x, y, player, 1);
        color = Colors.get(-1, 000, mainColor, subColor);
        this.screen = screen;
        this.player = player;
        this.health = 100;
        this.maxHealth = 100;
        this.scale = 4;
        this.speed = 1;
        this.zombies = zombies;
        this.xMin = 0; //Left
        this.xMax = 28*(int)scale/2; //Right
        this.yMin = 4; //Top
        this.yMax = 24*(int)scale/2; //Bottom
        this.modifier = (int)(8 * scale);
        bulletTimer = new Timer();
        zombieTimer = new Timer();
        laughTimer = new Timer();
        laughTimer.start();
        zombieTimer.start();
        bulletTimer.start();
        cannonShot1 = new WavePlayer();
        laughing = new WavePlayer();
        try
        {
            cannon = new WaveFile(path2 + "Sound/cannonshot.wav");
            laugh = new WaveFile(path2 + "Sound/finalbosslaugh.wav");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //this.player = player;
    }

    private void fluctuateColor()
    {
        int index = 0;
        int temp = mainColor;
        if(up)
        {
            for(int i = 0; i < 3; i++)
            {
                if(temp % 10 >= 5)
                {
                    temp/=10;
                    index++;
                }
                else
                    break;
            }
            if(index >= 3)
            {
                mainColor = 554;
                up = false;
            }
            else
                mainColor += Math.pow(10, index);
        }
        else
        {
            for(int i = 0; i < 3; i++)
            {
                if(temp % 10 <= 0)
                {
                    temp/=10;
                    index++;
                }
                else
                    break;
            }
            if(index >= 3)
            {
                mainColor = 100;
                up = true;
            }
            else
                mainColor -= Math.pow(10, index);
        }
        subColor = 555 - mainColor;
        color = Colors.get(-1, 000, mainColor, subColor);
    }

    public int getXTile()
    {
        return 0;
    }

    public int getYTile()
    {
        return 11;
    }

    public void render(Screen screen, Graphics g)
    {
        int xTile = getXTile();
        int yTile = getYTile();
        int xOffset = x - modifier / 2;  
        int yOffset = y - modifier / 4 - 4;  

        screen.render(xOffset, yOffset, xTile + yTile * 32, color, 0, scale);                 //Upper
        screen.render(xOffset + modifier, yOffset, (1 + xTile) + yTile * 32, color, 0, scale);//Body
        screen.render(xOffset + modifier * 2, yOffset, (2 + xTile) + yTile * 32, color, 0, scale);

        screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, color, 0, scale);
        screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, 0, scale);  //the 0's were flip bottoms  //Lower
        screen.render(xOffset + modifier * 2, yOffset + modifier, (xTile + 2) + (yTile + 1) * 32, color, 0, scale);              //Body

        screen.render(xOffset, yOffset + modifier * 2, xTile + (yTile + 2) * 32, color, 0, scale);
        screen.render(xOffset + modifier, yOffset + modifier * 2, (xTile + 1) + (yTile + 2) * 32, color, 0, scale);  //the 0's were flip bottoms  //Lower
        screen.render(xOffset + modifier * 2, yOffset + modifier * 2, (xTile + 2) + (yTile + 2) * 32, color, 0, scale);
        
        renderHealthBar(xOffset, yOffset, screen);
    }

    private void renderHealthBar(int xOffset, int yOffset, Screen screen)
    {
        int bar = health/2;
        for(int i = 0; i < bar/10; i++)
            screen.render(xOffset + modifier*i*2/(int)scale, yOffset-15, 3+2*32, Colors.get(-1, 500, -1, 000), false, false, 2D);
        if(bar%10 >= 5)
            screen.render(xOffset + modifier*(bar/10)*2/(int)scale, yOffset-15, 3+2*32, Colors.get(-1, 500, -1, 000), false, false, 2D);
        else
            screen.render(xOffset + modifier*(bar/10)*2/(int)scale, yOffset-15, 4+2*32, Colors.get(-1, 500, -1, 000), false, false, 2D);
    }

    public void tick()
    {
        if(health <= 0)
        {
            for(Zombie zomb : zombies)
                zomb.alive = false;
            alive = false;
        }
        if(alive)
        {
            if(laughTimer.getTime() >= 10000)
            {
                laughTimer.stop();
                laughing.play(laugh);
                laughTimer.start();
            }
            if(zombieTimer.getTime() >= 2000)
            {
                zombieTimer.stop();
                FastSmallZombie zomb = new FastSmallZombie(level, x, y, player,6-health/20);
                level.addEntity(zomb);
                zombies.add(zomb);
                zombieTimer.start();
            }
            int bulletDelay = 3000 - 25*(100 - health);
            if(bulletTimer.getTime() >= bulletDelay)
            {
                bulletTimer.stop();
                cannonShot1.play(cannon);
                level.addBullet(new ZombieBossBullet(level, (int)(this.x+width()/2), (int)(this.y + height()/2), player.getOnScreenX(), 
                        player.getOnScreenY(), this.x-screen.xOffset, this.y-screen.yOffset, level.bullets.size()));
                bulletTimer.start();
            }
            fluctuateColor();
            super.tick();
        }
    }
}
