package Game;

import java.awt.Graphics;
import Graphics.Screen;
import Graphics.Colors;
import Graphics.Font;

/**
 * Write a description of class PauseScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PauseScreen extends Stage
{
    private Button skipPauseButton;
    private Button getPauseBack;

    /**
     * Constructor for objects of class PauseScreen
     */
    public PauseScreen(Game game, InputHandler input)
    {
        super(null, game, null);
        String msg = "exclude stage pauses?";
        skipPauseButton = new Button(game.screen.width/2 - msg.length() * 8, game.screen.height*5/8, buttonColor, fontColor, msg, input);
        msg = "restore stage pauses?";
        getPauseBack = new Button(game.screen.width/2 - msg.length() * 8, game.screen.height*5/8, buttonColor, fontColor, msg, input);
    }
    
    public void setLevel(Level level)
    {
        this.level = level;
    }

    public void renderEntities(Screen screen, Graphics g)
    {
        if(!game.input.pause.isPressed())
            game.currentStage = game.gameScreen;
        else
        {
            level.renderEntities(screen, g);
            String msg = "Paused";
            Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*5*8 / 2,
                screen.yOffset + screen.height/3, Colors.get(-1, -1, -1, 123), 6);
            if(GameScreen.skipPauses)
                getPauseBack.render(screen);
            else
                skipPauseButton.render(screen);
        }
    }

    public void clicked(InputHandler input)
    {
        if(!GameScreen.skipPauses && skipPauseButton.clicked())
            GameScreen.skipPauses = true;
        else if(GameScreen.skipPauses && getPauseBack.clicked())
            GameScreen.skipPauses = false;
    }
}
