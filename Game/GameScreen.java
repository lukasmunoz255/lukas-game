package Game;

import Graphics.*;
import Sound.WavePlayer;
import Sound.WaveFile;
import Graphics.Font;
import Entities.*;
import Net.Packets.*;

import java.util.*;
import java.util.Random;
import java.awt.*;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

import java.awt.Graphics;

import java.io.File;
import javax.swing.JOptionPane;

/**
 * Write a description of class Stage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameScreen extends Stage
{   
    // instance variables - replace the example below with your own
    //public Player player;
    public ArrayList<PlayerMP> players = new ArrayList<PlayerMP>();
    private int difficulty;
    private int worldNumber;
    private ArrayList<Zombie> enemies;
    private Random rand;
    public Screen screen;
    private Timer time;
    private Timer powerUpTimer;
    private Timer ammoTimer;
    BufferedImage cursorImage;
    Cursor crosshair;
    public static boolean skipPauses;
    private Level[] worlds;
    private Spawns[] spawns;
    private int maxWorldNumber = 6;

    private WavePlayer zombieSounds;
    private WavePlayer applausePlayer;
    private WavePlayer powerUpPlayer;
    private WaveFile[] music;
    private WaveFile powerUpSound;
    private WaveFile zombieAwake;
    private WaveFile applause;
    private WaveFile finalbossintro;
    private WaveFile zombiehell;
    //Starting Positions

    /**
     * Constructor for objects of class Stage
     */
    public GameScreen(Game game, Screen screen, int difficulty, WavePlayer soundPlayer, InputHandler input)
    {
        super(null, game, soundPlayer);
        setUpWorlds();
        this.screen = screen;
        this.difficulty = difficulty;
        players.add(new PlayerMP(worlds[0], worlds[0].width/2, worlds[0].height/2, input,screen, 
                "PlacementPlayer", null, null, -1));
        this.rand = new Random();
        setUpCrosshairImage();
        crosshair = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Crosshair");
        time = new Timer();
        powerUpTimer = new Timer();
        ammoTimer = new Timer();
        zombieSounds = new WavePlayer();
        applausePlayer = new WavePlayer();
        powerUpPlayer = new WavePlayer();
        try
        {
            setUpSongs();
            applause = new WaveFile(path2 + "Sound/applause.wav");
            zombieAwake = new WaveFile(path2 + "Sound/zombieawakening.wav");
            finalbossintro = new WaveFile(path2 + "Sound/finalbossintro.wav");
            zombiehell = new WaveFile(path2 + "Sound/zombiehell.wav");
            powerUpSound = new WaveFile(path2 + "Sound/powerupsound.wav");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        soundPlayer.playOnRepeat(music[0]);
    }

    public Level getBossLevel()
    {
        return worlds[maxWorldNumber-1];
    }

    public void setUpWorlds()
    {
        worlds = new Level[6];
        spawns = new Spawns[5];
        for(int i = 0; i < 5; i++)
        {
            worlds[i] = new Level(setLevelPath(i+1));
            spawns[i] = new Spawns(worlds[i]);
        }
        worlds[5] = new Level(setLevelPath(5));
    }

    private void setUpSongs() throws IOException
    {
        music = new WaveFile[5];
        for(int i = 0; i < 5; i++)
        {
            music[i] = new WaveFile(setMusicPath(i+1));
        }
    }

    public void setUpCrosshairImage()
    {
        try
        {
            BufferedImage cross = ImageIO.read(new File(path2 + "Images/Crosshair.png"));
            cursorImage = new BufferedImage(cross.getWidth(), cross.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for(int xLuker = 0; xLuker < cross.getWidth(); xLuker++)
            {
                for(int yLuker = 0; yLuker < cross.getHeight(); yLuker++)
                {
                    int rgb = cross.getRGB(xLuker, yLuker);
                    if((rgb & 0x00FFFFFF)  == 0x00000000)
                        cursorImage.setRGB(xLuker, yLuker, 0x00055555);
                    else
                        cursorImage.setRGB(xLuker, yLuker, rgb);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void initialize()
    {
        spawns[0].resetSpawns();
        game.setCursor(crosshair);
        Game.kills = 0;
        Game.score = 0;
        Game.bulletsFired = 0;
        Game.bulletsHit = 0;
        difficulty = 1;
        level = worlds[0];
        for(int i = 0; i < players.size(); i++)
        {
            level.addPlayer(players.get(i));
            players.get(i).setLevel(level);
        }
        game.pauseScreen.setLevel(worlds[0]);
        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).x = worlds[0].width*8/2 + i*50;
            players.get(i).y = worlds[0].height*8/2;
            players.get(i).setHealth((short)(1000/(players.size()+1)));
            players.get(i).setLevel(worlds[0]);
        }
        worldNumber = 1;
        powerUpTimer.start();
        ammoTimer.start();
        zombieSounds.play(zombieAwake);
        newStage(worlds[0], 1);
    }

    public void newStage(Level level, int difficulty)
    {
        this.level = level;
        this.difficulty = difficulty;
        this.level.reset();
        initializeEnemies();
    }

    public Player getPlayer()
    {
        return players.get(0);
    }

    private void initializeEnemies()
    {
        enemies = new ArrayList<Zombie>();
        spawns[worldNumber-1].initializeEnemies(enemies, worlds[worldNumber-1].entities, worlds[worldNumber-1], screen, players.get(0), difficulty, worldNumber);
    }

    private void increaseLevel()
    {
        if(worldNumber != maxWorldNumber)
        {
            spawns[worldNumber-1].resetSpawns();
            if(difficulty % 14 == 0)
                increaseWorld();
            else
            {
                if(!skipPauses)
                {
                    if(!time.isRunning())
                        time.start();
                    if(time.getTime() > 5000)
                    {
                        Debugger.sendMsg("(---- INCREASING LEVEL ----)");
                        time.stop();
                        newStage(worlds[worldNumber-1], difficulty + 1);
                    }
                    else if(time.getTime() > 2000)
                    {
                        String msg = "Starting next stage in: " + String.valueOf(5-(time.getTime()/1000));
                        Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8 / 2, 
                            screen.yOffset + screen.height/2 - 50, Colors.get(-1, -1, -1, 123), 1);
                    }
                    else
                    {
                        String msg1 = "Stage " + difficulty + " Completed";
                        Font.render(msg1, screen, screen.xOffset + screen.width/2 - msg1.length()*8 / 2, 
                            screen.yOffset + screen.height/2 - 50, Colors.get(-1, -1, -1, 123), 1);
                    }
                }
                else
                    newStage(worlds[worldNumber-1], difficulty + 1);
            }
        }
        else
            changeScreen(true);
    }

    private String setLevelPath(int worldNumber)
    {
        //System.out.println(path2 + "Levels/level" + ((worldNumber/4)+1) + ".png");
        return path2 + "Levels/level" + (worldNumber) + ".png";
    }

    private String setMusicPath(int worldNumber)
    {
        return path2 + "Sound/music" + worldNumber + ".wav";
    }

    private void increaseWorld()
    {
        
        //player.shot1.close();
        //player.shot2.close();
        if(worldNumber == 5)
            finalBoss();
        else
        {
            String msg1 = "World " + worldNumber + " Completed";
            Font.render(msg1, screen, screen.xOffset + screen.width/2 - msg1.length()*16 / 2, 
                screen.yOffset + screen.height/2-45, Colors.get(-1, -1, -1, 511), 2);
            if(!time.isRunning())
            {
                time.start();
                applausePlayer.play(applause);
            }
            if(time.getTime() > 5000)
            {
                Debugger.sendMsg("(---- INCREASING WORLD ----)");
                time.stop();
                setUpNewWorld();
                for(int i = 0; i < players.size(); i++)
                {
                    players.get(i).x = worlds[0].width*8/2 + i*8;
                    players.get(i).y = worlds[0].height*8/2;
                }
                soundPlayer.playOnRepeat(music[worldNumber-1]);
                zombieSounds.play(zombieAwake);
                newStage(worlds[worldNumber-1], 1);
            }
        }
    }

    public void setUpNewWorld()
    {
        worlds[worldNumber-1].emptyEntities();
        worldNumber++;
        for(int i = 0; i < players.size(); i++)
        {
            worlds[worldNumber-1].addPlayer(players.get(i));
        }
        //worlds[worldNumber-1].addPlayer(player);

        game.pauseScreen.setLevel(worlds[worldNumber-1]);
        for(int i = 0; i < players.size(); i++)
            players.get(i).setLevel(worlds[worldNumber-1]);
    }
    
    public void finalBoss()
    {
        Font.ok = false;

        if(!time.isRunning())
        {
            soundPlayer.play(finalbossintro);
            time.start();
        }
        if(time.getTime() >= 17000)
        {
            time.stop();
            worlds[worldNumber].emptyEntities();
            setUpNewWorld();
            this.level = worlds[worldNumber-1];
            soundPlayer.playOnRepeat(zombiehell);
            Level lev = worlds[worldNumber-1];
            FinalBoss boss = new FinalBoss(lev, lev.width/2, lev.height/10, screen, 
                    lev.getMainPlayer(), enemies);
            worlds[worldNumber-1].addEntity(boss);
            enemies.add(boss);
            Font.ok = true;
        }
    }

    public void renderEntities(Screen daScreen, Graphics g)
    {
        if(game.input.pause.isPressed())
            game.currentStage = game.pauseScreen;
        else
        {
            if(players.get(0).isAlive())
            {
                if(isDefeated())
                    increaseLevel();
                renderNormalFontsOrBoss(daScreen, g);
                powerUpTick();
                worlds[worldNumber-1].renderEntities(screen, g);
                worlds[worldNumber-1].tick();
                String health = "health: " + String.valueOf(players.get(0).health());
                Font.render(health, screen, screen.xOffset + screen.width/8 - health.length()*8 / 2, 
                    screen.yOffset + screen.height * 5/6, Colors.get(-1, -1, -1, 300), 1);
                String ammo = "ammo: " + String.valueOf(players.get(0).ammo());
                Font.render(ammo, screen, screen.xOffset + screen.width*7/8 - ammo.length()*8 / 2, 
                    screen.yOffset + screen.height * 5/6, Colors.get(-1, -1, -1, 000), 1);
            }
            else
                changeScreen(false);
        }
    }

    public void powerUpTick()
    {
        if(powerUpTimer.getTime() >= 20000)
        {
            worlds[worldNumber-1].addPowerUp();
            powerUpPlayer.play(powerUpSound);
            powerUpTimer.start();
        }
        else if(powerUpTimer.getTime() >= 10000)
            worlds[worldNumber-1].removePowerUp();
        if((ammoTimer.getTime() >= 15000 && difficulty >= 7) || 
        (ammoTimer.getTime() >= 1000 && players.get(0).ammo() <= 10 && level.ammo.size() <= 1))
        {
            worlds[worldNumber-1].addAmmoCrate();
            powerUpPlayer.play(powerUpSound);
            ammoTimer.start();
        }
    }

    public void changeScreen(boolean victory)
    {
        game.input.setScreenType(2);
        soundPlayer.playOnRepeat(music[0]);
        game.setCursor(Cursor.getDefaultCursor());
        game.highScoresScreen.updateHighScores();
        game.losingScreen.initialize(difficulty, worldNumber, victory);
        game.currentStage = game.losingScreen;
    }

    public void renderNormalFontsOrBoss(Screen screen, Graphics g)
    {
        if(worldNumber <=  5)
        {
            String msg = "Stage "+ difficulty;
            Font.render(msg, screen, screen.xOffset + screen.width/8 - msg.length()*8 / 2, 
                screen.yOffset + screen.height/12, Colors.get(-1, -1, -1, 123), 1);
            msg = "World "+ worldNumber;
            Font.render(msg, screen, screen.xOffset + screen.width*7/8 - msg.length()*8 / 2, 
                screen.yOffset + screen.height/12, Colors.get(-1, -1, -1, 123), 1);
        }
        else
        {
            String msg1 = "final boss";
            Font.render(msg1, screen, screen.xOffset + screen.width/2 - msg1.length()*16 / 2, 
                screen.yOffset + screen.height/12, Colors.get(-1, -1, -1, 511), 2);
        }
    }

    public void clicked(InputHandler input)
    {
        if(!players.get(0).rapidFire)
            players.get(0).fire();
    }

    public boolean isDefeated()
    {
        for(Zombie z : enemies)
        {
            if(z.isAlive())
                return false;
        }
        return true;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public int getWorldNumber()
    {
        return worldNumber;
    }

    public Level getLevel(int worldNumber)
    {
        return worlds[worldNumber-1];
    }

    public void addCoopPlayer(PlayerMP player)
    {
        players.add(player);
    }
    
    public void replacePlayer(PlayerMP player, int index)
    {
        players.remove(index);
        players.add(player);
    }

    /*public InputHandler getInputHandler()
    {
    return this.input;
    }
     */
}