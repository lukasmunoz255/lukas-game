package Game;

import Graphics.*;
import Sound.WavePlayer;
import Entities.*;
import java.awt.event.KeyEvent;

import java.awt.Graphics;

/**
 * Write a description of class HomeScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HomeScreen extends Stage
{
    private Button playButton;
    private Button COOPButton;
    private Button highScoresButton;
    private Button instructionsButton;
    private ComputerPlayer player;
    private Zombie zombie;
    private java.awt.image.BufferedImage backgroundImage;

    /**
     * Constructor for objects of class HomeScreen
     */
    public HomeScreen(Level level, Game game, InputHandler input, WavePlayer soundPlayer, Screen screen)
    {
        super(level, game, soundPlayer);
        playButton = new Button(47, 50, buttonColor, fontColor, "Play", input);
        highScoresButton = new Button(200, 50, buttonColor, fontColor, "high scores", input);
        instructionsButton = new Button(125, 150, buttonColor, fontColor, "instructions", input);
        COOPButton = new Button(125, 250, buttonColor, fontColor, "CO-op", input);
        try {
            backgroundImage = javax.imageio.ImageIO.read(new java.io.File("Images/mmBackground.png"));
        } catch (Exception e) { e.printStackTrace(); }
        //this.player = new ComputerPlayer(this.level, screen.width/4, screen.height*3/4, screen);
        //this.zombie = new Zombie(this.level, screen.width/4, screen.height*3/4+15, this.player, 1);
        //this.player.setZombie(zombie);
        //this.level.addPlayer(this.player);
        //this.level.addEntity(this.zombie);
    }

    public void renderEntities(Screen screen, Graphics g)
    {
        level.tick();
        level.renderEntities(screen, g);
        playButton.render(screen);
        highScoresButton.render(screen);
        instructionsButton.render(screen);
        COOPButton.render(screen);
    }

    public void clicked(InputHandler input)
    {
        if(playButton.clicked())
        {
            game.gameScreen.initialize();
            game.currentStage = game.gameScreen;
            input.setScreenType(1);
        }
        else if(highScoresButton.clicked())
        {
            game.highScoresScreen.updateHighScores();
            game.currentStage = game.highScoresScreen;
            input.setScreenType(-1); 
        }
        else if(instructionsButton.clicked())
        {
            game.currentStage = game.instructionsScreen;
            input.setScreenType(-1);
        }
        else if(COOPButton.clicked())
        {
            game.waitingScreen.initialize();
            game.currentStage = game.waitingScreen;
            input.setScreenType(-1);
        }
    }
}
