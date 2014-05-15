package Game;

import Entities.Bullet;
import Entities.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * Handles all of the inputs that we will use in the game. Allows 
 * for the user to control the main player.
 * 
 * Lukers
 * 1/28/14
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener
{
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key pause = new Key();

    public Key mouseClicker = new Key();

    Level level;
    Game game;
    private boolean fire;
    private MouseEvent e;
    private int mouseX, mouseY;
    private LosingScreen losingScreen;
    private HomeScreen homeScreen; 
    private HighScoresScreen highScoresScreen;
    private int screenType;

    /**
     * Sets up an inputHandler. Passes in a level so bullets can be 
     * created in the game
     */
    public InputHandler(Game game, Level level)
    {
        game.addKeyListener(this);
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        this.game = game;
        this.level = level;
    }

    public void setScreens(HomeScreen homeScreen, LosingScreen losingScreen, HighScoresScreen highScoresScreen)
    {
        this.losingScreen = losingScreen;
        this.homeScreen = homeScreen;
        this.highScoresScreen = highScoresScreen;
    }

    /**
     * 0 is homeScreen, 1 is game, 2 is losingScreen, 3 is for highScoresScreen. 
     * More will be included which is why numbers must be used. Right now we only use
     * 1 and 2 for game and losing screen. 
     */
    public void setScreenType(int type)
    {
        this.screenType = type;
    }

    public void mouseEntered(MouseEvent e)
    {}

    public void mouseExited(MouseEvent e)
    {}

    public void mouseClicked(MouseEvent e)
    {
    }

    /**
     * Is called whenever the mouse is pressed down. A bullet is created
     * at the players position and is directed at the mouse.
     */
    public void mousePressed(MouseEvent e)
    {
        game.currentStage.clicked(this);
        if(game.currentStage instanceof GameScreen)
            mouseClicker.toggle(true);
    }

    public void mouseReleased(MouseEvent e)
    {
        if(game.currentStage instanceof GameScreen)
            mouseClicker.toggle(false);
    }

    /**
     * Tells us that a key is being pressed and calls the 
     * toggle key method to specify.
     */
    public void keyPressed(KeyEvent e)
    {
        if(screenType == 1)
        {
            if(e.getKeyCode() == KeyEvent.VK_P)
                pause.toggle(!pause.isPressed());
            else
                toggleKey(e.getKeyCode(), true);
        }
        if(screenType == 2)
            losingScreen.getLetter(e.getKeyCode());
    }

    /**
     * Tells us that a key is released, calling toggle key to 
     * unpress a key.
     */
    public void keyReleased(KeyEvent e)
    {
        toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent e)
    {

    }

    /**
     * Toggles a key pressed or not pressed. 
     */
    public void toggleKey(int keyCode, boolean isPressed)
    {
        if(keyCode == KeyEvent.VK_W)
            up.toggle(isPressed);
        if(keyCode == KeyEvent.VK_S)
            down.toggle(isPressed);
        if(keyCode == KeyEvent.VK_A)
            left.toggle(isPressed);
        if(keyCode == KeyEvent.VK_D)
            right.toggle(isPressed);
    }

    /**
     * Changes the x and y coordinates of the mouse based on 
     * the movement of the mouse
     */
    public void mouseMoved(MouseEvent e) 
    { 
        mouseX = e.getX(); 
        mouseY = e.getY(); 
    } 

    public void mouseDragged(MouseEvent e) 
    {
        mouseX = e.getX(); 
        mouseY = e.getY(); 
    } 

    /**
     * Returns the x coordinate of the mouse
     */
    public int getMouseX() 
    { 
        return mouseX / 3; 
    } 

    /**
     * returns the y coordinate of the mouse
     */
    public int getMouseY() 
    { 
        return mouseY / 3; 
    } 

    /**
     * The key class is used for readability. It allows us to 
     * ask the player if a key is being pressed or not. 
     * 
     */
    public class Key
    {
        private boolean pressed = false;
        private int numTimesPressed = 0;

        /**
         * Returns the number of times a key was pressed.
         */
        public int getNumTimesPressed()
        {
            return numTimesPressed;
        }

        /**
         * Sets a key to be pressed or not pressed depending on what 
         * is passed in.
         */
        public void toggle(boolean isPressed)
        {
            pressed = isPressed;
            if(isPressed)
            {
                numTimesPressed++;
            }
        }

        /**
         * Returns if a key is pressed.
         */
        public boolean isPressed()
        {
            return pressed;
        }
    }
}
