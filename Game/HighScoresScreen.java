package Game;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import Graphics.*;
import Sound.WavePlayer;
import Entities.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Graphics;

/**
 * Write a description of class HighScoresScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HighScoresScreen extends Stage
{   
    BufferedReader read;
    String[] scores = new String[5];
    
    private Button resetButton;
    private Button backButton;

    /**
     * Constructor for objects of class HighScoresScreen
     */
    public HighScoresScreen(Level level, Game game, Screen screen, InputHandler input, WavePlayer soundPlayer)
    {
        super(level, game, soundPlayer); 
        updateHighScores();
        
        int width = 12;
        this.resetButton = new Button(screen.width/2 - ((4 + width*8)*3+1)/2, screen.height * 3 / 4,
        buttonColor, fontColor, "reset high scores", input);
        
        this.backButton = new Button(47, 50, buttonColor, fontColor, "back", input);
    }

    public void updateHighScores()
    {
        try
        {
            read = new BufferedReader(new FileReader(new File(path2 + "Scores.txt")));
            for(int i = 0; i < 5; i++)
            {
                scores[i] = read.readLine();
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void renderEntities(Screen screen, Graphics g)
    {
        resetButton.render(screen);
        backButton.render(screen);
        
        String str = "high scores: ";
        Font.render(str, screen, screen.xOffset + screen.width/2 - str.length()*8 / 2, 
            screen.yOffset + screen.height/8 + 30, Colors.get(-1, -1, -1, 122), 1);
        for(int i = 0; i < 5; i++)
        {
            Font.render(scores[i], screen, screen.xOffset + screen.width/2 - scores[i].length()*8 / 2, 
                screen.yOffset + screen.height/8 + 15 * (i + 4), Colors.get(-1, -1, -1, 122), 1);
        }
    }

    public void clicked(InputHandler input)
    {
        if(backButton.clicked())
        {
            game.currentStage = game.homeScreen;
            input.setScreenType(0);
        }
        else if(resetButton.clicked())
        {
            game.resetHighScores();
            updateHighScores();
        }
    }
}
