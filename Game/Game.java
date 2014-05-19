package Game;

import Graphics.*;
import Sound.WavePlayer;


import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import Entities.Player;
import Entities.Bullet;
import Entities.Zombie;
import Entities.BigZombie;
import Entities.Ogre;
import Entities.FastSmallZombie;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JOptionPane;

/**
 * The main game class for the game. Everything starts here and is
 * run through this class.
 * 
 * Lukers
 * 1/28/14
 */
public class Game extends Canvas implements Runnable
{
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 280;
    public static final int SCALE = 3;
    public static final String NAME = "Game Ass Shit";
    public boolean running = false;
    String path2;

    private int[] colors = new int[6*6*6]; // 6 shades of each color

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    public int tickCount = 0;

    private JFrame frame;
    Timer time = new Timer();

    public Screen screen;
    public InputHandler input;
    public Player player;
    public Level level;

    public Stage currentStage;

    public GameScreen gameScreen;
    public LosingScreen losingScreen;
    public HomeScreen homeScreen;
    public HighScoresScreen highScoresScreen;
    public InstructionsScreen instructionsScreen;
    public PauseScreen pauseScreen;
    public WaitingScreen waitingScreen;

    public static int kills;
    public static int score;
    public static int bulletsFired;
    public static int bulletsHit;
    

    
    /**
     * Sets up the JFrame and adds the game. Also starts the timer
     */
    public Game()
    {   
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Initializes all of the things that the game needs to run
     */
    public void init()
    {
        int index = 0;
        for(int r = 0; r < 6; r++)
        {
            for(int g = 0; g < 6; g++)
            {
                for(int b = 0; b < 6; b++)
                {
                    int rr = r*255/5;
                    int gg = g*255/5;
                    int bb = b*255/5;

                    colors[index++] = rr<<16 | gg<<8 | bb; // I have no idea
                }   
            }
        }
        WavePlayer soundPlayer = new WavePlayer();
        path2 = getClass().getClassLoader().getResource(".").getPath();
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(path2 + "Images/LargeSpritesheet.png"));
        Level otherLevel = new Level(null);
        input = new InputHandler(this, level);

        gameScreen = new GameScreen(this, screen, 1, soundPlayer, input);
        homeScreen = new HomeScreen(otherLevel, this, input, soundPlayer, screen);
        highScoresScreen = new HighScoresScreen(otherLevel, this, screen, input, soundPlayer);
        losingScreen = new LosingScreen(gameScreen, this, screen, input, soundPlayer);
        instructionsScreen = new InstructionsScreen(otherLevel, this, input, soundPlayer);
        pauseScreen = new PauseScreen(this, input);
        waitingScreen = new WaitingScreen(otherLevel, this);

        player = gameScreen.getPlayer();

        input.setScreens(homeScreen, losingScreen, highScoresScreen);
        input.setScreenType(0);
        file1 = new File(path2 + "Scores.txt");
        currentStage = homeScreen;
        
        
    }

    /**
     * Allows for the threads to be synced
     */
    public synchronized void start()
    {
        running = true;
        new Thread(this).start();
    }

    /**
     * Allows for the threads to be synced
     */
    public synchronized void stop()
    {
        running = false;
    }

    private int x, y;
    private double transform = 0;

    /**
     * Renders entities and tiles. The order in which things are rendered
     * determines what appears on top of what. Also draws everything to the screen
     */
    public void render()
    {
        for(int y = 0; y < screen.height; y++)
        {
            for(int x = 0; x < screen.width; x++)
            {
                int colorCode = screen.pixels[x + y * screen.width];
                if(colorCode < 255)
                    pixels[x+y*WIDTH] = colors[colorCode];
            }
        }

        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }
        int xOffset = gameScreen.players.get(0).x - (screen.width / 2);
        int yOffset = gameScreen.players.get(0).y - (screen.height / 2);

        currentStage.renderTiles(screen, xOffset, yOffset);
        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        currentStage.renderEntities(screen, g); //Make sure this is after drawImage!!!!
        
        
        g.dispose();
        bs.show();
        if (Debugger.DebuggerOn) { Debugger.render(screen); }
    }

    /**
     * Calls the init() method and gets the game going. Sets a frame rate
     * as well so different computers should run at the same rate. This is called
     * by the thread stuff.
     */
    public void run()
    {
        long lastTime = System.nanoTime();
        double nsPerTick = 100000000D/60D;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime)/nsPerTick;
            lastTime = now;
            while(delta >= 10) // was 10
            {
                delta -= 10;
                render();
                Debugger.update((long)(nsPerTick / 100000));
            }
            if(System.currentTimeMillis() - lastTimer >= 1000)
                lastTimer += 1000;
        }
    }

    BufferedReader read;
    File file1; 

    public void writeNewHighScore(int index, File formerFile)
    {
        String[] newScores = new String[5];
        String st;
        try
        {
            read = new BufferedReader(new FileReader(formerFile));
            for(int i = 0; i < index; i++)
            {
                st = read.readLine();
                newScores[i] = st;
            }
            if(getPlayerName().length() > 0)
                newScores[index] = (index + 1) + ". " + getPlayerName() + " " + score;
            else
                newScores[index] = (index + 1) + ". " + "null" + " " + score;
            for(int i = index + 1; i < 5; i++)
            {
                st = read.readLine();
                newScores[i] = (i + 1) + ". " + st.substring(3);
            }
            read.close();
            BufferedWriter rite = new BufferedWriter(new FileWriter(formerFile, false));
            for(int i = 0; i < 5; i++)
            {
                rite.write(newScores[i]);
                rite.newLine();
            }
            rite.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    private String getPlayerName()
    {
        return losingScreen.getName();
    }

    public int determineHighScore()
    {
        score = calculateScore();
        int index = -1;
        try
        {
            read = new BufferedReader(new FileReader(file1));
            String[] str = new String[3];
            String s;
            for(int i = 0; i < 5; i++)
            {
                s = read.readLine();
                str = s.split(" ");
                if(score >= Integer.parseInt(str[2]) && index == -1)
                {
                    index = i;
                }
            }
            return index;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
        return index;
    }

    public void resetHighScores()
    {
        Debugger.sendMsg("resseting high scores!");
        try
        {
            String str = ". aaa 0";
            BufferedWriter rite = new BufferedWriter(new FileWriter(new File(path2 + "Scores.txt"), false));
            for(int i = 0; i < 5; i++)
            {
                highScoresScreen.scores[i] = (i+1) + str;
                rite.write((i+1) + str);
                rite.newLine();
            }
            rite.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Calculates score based on accuracy, kills, and stage
     */
    private int calculateScore()
    {
        double accuracy = (double)bulletsHit/(double)bulletsFired;
        return (int)(accuracy * ((kills * 100) + (gameScreen.getDifficulty() * 500) + 
                ((gameScreen.getWorldNumber()-1) * 1000))); 
    }

    /**
     * Call this and it'll all go.
     */
    public static void main(String[] args)
    {
        new Game().start();
    }
}
