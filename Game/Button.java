package Game;
import Graphics.Screen;
import Graphics.Colors;
import Graphics.Font;

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button
{
    InputHandler input;
    String message;
    int widthPixels;
    int heightPixels;
    int width;
    int x;
    int y;
    int color;
    int fontColor;
    double scale;
    int modifier;
    int xTile = 9;
    int yTile = 0;

    /**
     * Constructor for Buttons. width must be greater than 1. Width is just the number
     * of middle tiles make up the button.
     */
    public Button(int xPos, int yPos, int color, int fontColor, String msg, InputHandler input)
    {
        this.input = input;
        this.message = msg;
        this.x = xPos;
        this.y = yPos;
        this.width = msg.length()*2/3 + 1;
        this.widthPixels = (4 + this.width * 8) * 3 + 1;
        this.heightPixels = 32;
        this.color = color;
        this.fontColor = fontColor; 
        this.scale = 3D;
        this.modifier = (int)(8 * this.scale);
    }

    public int colorChange(int color)
    {
        if(input.getMouseX() > x -4 && input.getMouseX() < widthPixels + x
        && input.getMouseY() > y && input.getMouseY() < heightPixels + y)
        {
            return color + 11;
        }
        return color;
    }

    public boolean clicked()
    {
        if(input.getMouseX() > x - 4 && input.getMouseX() < widthPixels + x
        && input.getMouseY() > y && input.getMouseY() < heightPixels + y)
            return true;
        return false;
    }

    public void render(Screen screen)
    {
        screen.render(this.x + screen.xOffset, this.y + screen.yOffset, xTile + yTile * 32, Colors.get(-1, colorChange(color), -1, -1), false, false, scale);
        for(int i = 1; i < width; i++)
        {
            screen.render(this.x + screen.xOffset + modifier * i, this.y + screen.yOffset, (xTile + 1) + yTile * 32, Colors.get(-1, colorChange(color), -1, -1), false, false, scale);
        }
        screen.render(this.x + screen.xOffset + modifier * width, this.y + screen.yOffset, (xTile + 2) + yTile * 32, Colors.get(-1, colorChange(color), -1, -1), false, false, scale);
        screen.render(this.x + screen.xOffset, this.y + screen.yOffset + modifier, xTile + (yTile + 1) * 32, Colors.get(-1, colorChange(color), -1, -1), false, false, scale);
        for(int i = 1; i < width; i++)
        {
            screen.render(this.x + screen.xOffset + modifier * i, this.y + screen.yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, Colors.get(-1, colorChange(color), -1, -1), false, false, scale);
        }
        screen.render(this.x + screen.xOffset + modifier * width, this.y + screen.yOffset + modifier, (xTile + 2) + (yTile + 1) * 32, Colors.get(-1, colorChange(color), -1, -1), false, false, scale);
        Font.render(message, screen, this.x + screen.xOffset + 12, this.y + screen.yOffset + 11, Colors.get(-1, -1, -1, colorChange(fontColor)), (int)(scale - 1));
    }
}
