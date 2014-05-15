package Game;

import Graphics.Screen;
import Sound.WavePlayer;
import Graphics.Colors;
import Graphics.Font;

import java.awt.Graphics;
/**
 * Write a description of class InstructionsScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstructionsScreen extends Stage
{
    private Button backButton;
    
    
    /**
     * Constructor for objects of class InstructionsScreen
     */
    public InstructionsScreen(Level level, Game game, InputHandler input, WavePlayer soundPlayer)
    {
        super(level, game, soundPlayer);
        
        backButton = new Button(47, 50, buttonColor, fontColor, "back", input);
    }
    
    public void renderEntities(Screen screen, Graphics g)
    {
        backButton.render(screen);
        String str;
        str = "instructions";
        Font.render(str, screen, screen.xOffset + screen.width/2 - str.length()*8 / 2, 
            screen.yOffset + screen.height/8, Colors.get(-1, -1, -1, 122), 2);
        str = "w";
        Font.render(str, screen, screen.xOffset + screen.width/4 - str.length()*8/2, 
            screen.yOffset + screen.height/2, Colors.get(-1, -1, -1, 000), 2);
        str = "asd";
        Font.render(str, screen, screen.xOffset + screen.width/4 - str.length()*8/2 - 7, 
            screen.yOffset + screen.height/2 + 18, Colors.get(-1, -1, -1, 000), 2);
        
    }

    public void clicked(InputHandler input)
    {
        if(backButton.clicked())
        {
            game.currentStage = game.homeScreen;
            input.setScreenType(-1);
        }
    }
}
