package Graphics;

/**
 * Simplifies the colors in the game down for easy use and simplistic
 * colors. There are only 256 total colors, 6 shades of each. This used for
 * efficiency reasons.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public class Colors {
    public static final int BLOODRED = -19201; //Only for dark grey on spritesheet
    
    /**
     * On our spritesheet, you will notice that there are only four colors used and
     * all of them are different shades of black and white. 'color1 is black, 'color2 is
     * dark gray, 'color3 is light gray, and 'color4 is white.
     */
    public static final int get(final int color1, final int color2, final int color3, final int color4) {
        return (get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1);
    }
    
    /**
     * Returns a simplified version of a color. A call to Colors.get(351) would return a color 
     * with 3 parts red, 5 parts green, and 1 part blue. All of the shades are scaled up as well
     * to only allow for 6 shades of each. For example a call to Colors.get(958) would be the same 
     * as Colors.get(555).
     */
    private static final int get(int color) {
        if(color < 0) { return 255; }
        final int r = color/100 % 10,
            g = color/10 % 10,
            b = color % 10;
        return r * 36 + g * 6 + b;
    }
    
    /**
     * Returns number between 0 and 555 (inclusive).
     */
    private static int getRandom() {
        return (int)(Math.random()*6) * 100 + (int)(Math.random()*6) * 10 + (int)(Math.random()*6);
    }
    
    
    public static int getRandomColor(final int index) {
        final int args[] = new int[] {-1, -1, -1, -1};
        args[bound(index, 0, 3)] = getRandom();
        return get(args[0], args[1], args[2], args[3]);
    }
    
    /**
     * Returns the number 'num bounded by a lower and upper bound 'lower and 'upper respectively. If
     * 'num is out of the bounds, the bound is returned instead.
     */
    private static final int bound(final int num, final int lower, final int upper) {
        if (num < lower) { return lower; }
        if (num > upper) { return upper; }
        return num;
    }
}