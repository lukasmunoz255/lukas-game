package Graphics;

/**
 * Simplifies the colors in the game down for easy use and simplistic
 * colors. There are only 256 total colors, 6 shades of each. This used for
 * efficiency reasons.
 * 
 * Lukers
 * 1/28/14
 */
public class Colors
{
    public static final int BLOODRED = -19201; //Only for dark grey on spritesheet
    
    /**
     * On our spritesheet, you ill notice that there are only four colors used and
     * all of them are different shades of black and white. color1 is black, color2 is
     * dark gray, color3 is light gray, and color4 is white. 
     */
    public static int get(int color1, int color2, int color3, int color4)
    {
        return (get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1);
    }
    
    /**
     * Returns a simplified version of a color. A call to Colors.get(351) would return a color 
     * with 3 parts red, 5 parts green, and 1 part blue. All of the shades are scaled up as well
     * to only allow for 6 shades of each. For example a call to Colors.get(958) would be the same 
     * as Colors.get(555).
     */
    private static int get(int color)
    {
        if(color < 0)
        {
            return 255;
        }
        int r = color/100 % 10;
        int g = color/10 % 10;
        int b = color % 10;
        return r * 36 + g * 6 + b;
    }
    
    private static int getRandom()
    {
        return (int)(Math.random()*6) * 100 + (int)(Math.random()*6) * 10 + (int)(Math.random()*6);
    }
    
    public static int getRandomColor(int index)
    {
        if(index <= 0)
        {
            return get(getRandom(), -1, -1, -1);
        }
        else if(index == 1)
        {
            return get(-1, getRandom(), -1, -1);
        }
        else if(index == 2)
        {
            return get(-1, -1, getRandom(), -1);
        }
        else
        {
            return get(-1, -1, -1, getRandom());
        }
    }
}