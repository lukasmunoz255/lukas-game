package Game;
import Graphics.*;
import Sound.WavePlayer;
import Graphics.Font;
import Entities.*;
import java.util.*;
import java.util.Random;
import java.awt.*;
import java.awt.Graphics;

import java.io.File;
import java.awt.event.KeyEvent;

/**
 * Write a description of class LosingScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LosingScreen extends Stage
{
    protected String name = "";
    protected int index;
    protected String[] highScores;
    protected int difficulty;
    protected int worldNumber;
    protected Button homeButton;
    protected GameScreen gameScreen;
    protected Timer underscoreTimer;
    protected boolean victory;
    private boolean renderUnderscore;

    /**
     * Constructor for objects of class LosingScreen
     */
    public LosingScreen(GameScreen gameScreen, Game game, Screen screen, InputHandler input, WavePlayer soundPlayer)
    {
        super(null, game, soundPlayer);
        victory = false;
        underscoreTimer = new Timer();
        this.gameScreen = gameScreen;
        int width = 3;
        homeButton = new Button(screen.width / 2 - ((4+width*8)*3+1) / 2, screen.height * 3 / 4, 
            buttonColor, fontColor, "home", input);
    }

    public String getName()
    {
        return name;
    }

    public void initialize(int difficulty, int worldNumber, boolean victory)
    {
        index = game.determineHighScore();
        if(index != -1)
            underscoreTimer.start();
        this.highScores = game.highScoresScreen.scores;
        this.difficulty = difficulty;
        this.worldNumber = worldNumber;
        this.level = gameScreen.getLevel(worldNumber);
        renderUnderscore = true;
        this.victory = victory;
    }

    public void renderEntities(Screen screen, Graphics g)
    {
        String msg;
        if(victory)
        {
            msg = "You win!!!!!";
            Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8*2 / 2, 
                screen.yOffset + screen.height/8, Colors.getRandomColor(3), 2);
            msg = "Your score is " + Game.score + " points";
            Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8 / 2, 
                screen.yOffset + screen.height/8 + 15, Colors.getRandomColor(3), 1);
        }
        else
        {
            msg = "Game Over";
            Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8 / 2, 
                screen.yOffset + screen.height/8, Colors.get(-1, -1, -1, 123), 1);
            msg = "final stage: " + (difficulty);
            Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8 / 2, 
                screen.yOffset + screen.height/8 + 10, Colors.get(-1, -1, -1, 123), 1);
            msg = "final world: " + (worldNumber);
            Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8 / 2, 
                screen.yOffset + screen.height/8 + 20, Colors.get(-1, -1, -1, 123), 1);
            msg = "Your score is " + Game.score + " points";
            Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8 / 2, 
                screen.yOffset + screen.height/8 + 50, Colors.get(-1, -1, -1, 122), 1);
        }
        renderHighScoreStuff(screen);
        homeButton.render(screen);
    }

    protected void renderHighScoreStuff(Screen screen)
    {
        if(index > -1)
        {
            String str = "New high score!";
            Font.render(str, screen, screen.xOffset + screen.width/2 - str.length()*8 / 2, 
                screen.yOffset + screen.height/8 + 60, Colors.getRandomColor(3), 1);
            Font.render("Please enter your name below:", screen, screen.xOffset + screen.width/2 - 29*8 / 2, 
                screen.yOffset + screen.height/8 + 80, Colors.get(-1, -1, -1, 122), 1);
            for(int i = 0; i < index; i++)
            {
                Font.render(game.highScoresScreen.scores[i], screen,screen.xOffset + screen.width/2 - game.highScoresScreen.scores[i].length()*8 / 2, 
                    screen.yOffset + screen.height/8+20+(15*(i+5)), Colors.get(-1, -1, -1, 122), 1);
            }
            if(underscoreTimer.getTime() >= 500)
            {
                underscoreTimer.stop();
                renderUnderscore = !renderUnderscore;
                underscoreTimer.start();
            }
            if(renderUnderscore)
                str = (index + 1) + ". " + name + "_ " + Game.score;
            else
                str = (index + 1) + ". " + name + "  " + Game.score;
            Font.render(str, screen, screen.xOffset + screen.width/2 - str.length()*8 / 2, 
                screen.yOffset + screen.height/8+20+15*(index + 5), Colors.get(-1, -1, -1, 122), 1);
            for(int i = index + 1; i < 5; i++)
            {
                Font.render((i+1) + ". " + game.highScoresScreen.scores[i-1].substring(3), screen,
                    screen.xOffset + screen.width/2 - game.highScoresScreen.scores[i-1].length()*8 / 2, 
                    screen.yOffset + screen.height/8+20+(15*(i+5)), Colors.get(-1, -1, -1, 122), 1);
            }
        }
    }

    public void getLetter(int keyCode)
    {
        if(index > -1)
        {
            if(name.length() <= 15)
            {
                if(keyCode == KeyEvent.VK_Q)
                    name += "q";
                else if(keyCode == KeyEvent.VK_W)
                    name += "w";
                else if(keyCode == KeyEvent.VK_E)
                    name += "e";
                else if(keyCode == KeyEvent.VK_R)
                    name += "r";
                else if(keyCode == KeyEvent.VK_T)
                    name += "t";
                else if(keyCode == KeyEvent.VK_Y)
                    name += "y";
                else if(keyCode == KeyEvent.VK_U)
                    name += "u";
                else if(keyCode == KeyEvent.VK_I)
                    name += "i";
                else if(keyCode == KeyEvent.VK_O)
                    name += "o";
                else if(keyCode == KeyEvent.VK_P)
                    name += "p";
                else if(keyCode == KeyEvent.VK_A)
                    name += "a";
                else if(keyCode == KeyEvent.VK_S)
                    name += "s";
                else if(keyCode == KeyEvent.VK_D)
                    name += "d";
                else if(keyCode == KeyEvent.VK_F)
                    name += "f";
                else if(keyCode == KeyEvent.VK_G)
                    name += "g";
                else if(keyCode == KeyEvent.VK_H)
                    name += "h";
                else if(keyCode == KeyEvent.VK_J)
                    name += "j";
                else if(keyCode == KeyEvent.VK_K)
                    name += "k";
                else if(keyCode == KeyEvent.VK_L)
                    name += "l";
                else if(keyCode == KeyEvent.VK_Z)
                    name += "z";
                else if(keyCode == KeyEvent.VK_X)
                    name += "x";
                else if(keyCode == KeyEvent.VK_C)
                    name += "c";
                else if(keyCode == KeyEvent.VK_V)
                    name += "v";
                else if(keyCode == KeyEvent.VK_B)
                    name += "b";
                else if(keyCode == KeyEvent.VK_N)
                    name += "n";
                else if(keyCode == KeyEvent.VK_M)
                    name += "m";
            }
            if(keyCode == KeyEvent.VK_BACK_SPACE)
            {
                if(name.length() > 0)
                    name = name.substring(0, name.length() - 1);
            }
        }
    }

    public void clicked(InputHandler input)
    {
        if(homeButton.clicked())
        {
            if(index > -1)
                game.writeNewHighScore(index, new File(path2 + "Scores.txt"));
            game.currentStage = game.homeScreen;
            input.setScreenType(0);
        }
    }
}
