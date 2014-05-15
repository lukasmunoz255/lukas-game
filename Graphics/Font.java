package Graphics;

/**
 * Write a description of class Font here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Font
{
    public static boolean notOK = false;
    private static String chars = "" +
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + 
        "0123456789.,:;'\"!?$%()-=+/_     "; 

    public static void render(String msg, Screen screen, int x, int y, int color, int scale)
    {
        if(!notOK)
        {
            msg = msg.toUpperCase();
            for(int i = 0; i < msg.length(); i++)
            {
                int charIndex = chars.indexOf(msg.charAt(i));
                if(charIndex >= 0)
                {
                    screen.render(x + i*8*scale, y, charIndex + 30 * 32, color, 0x00, scale);
                }
            }
        }
    }
}
