package Graphics;

/**
 * Holds the methods pertaining to rendering text on the game screen.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public final class Font {
    public static boolean ok = true;
    private static final String legalCharacters = // contains all the characters that can be rendered
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + 
        "0123456789.,:;'\"!?$%()-=+/_     "; 

    /**
     * Renders the message 'msg on the Screen 'screen at the coordinates ('x, 'y) with a color 'color
     * and at a scale 'scale.
     */
    public static void render(final String msg, final Screen screen, final int x, final int y, final int color, final int scale) {
        if(ok) {
            final String uppercaseMsg = msg.toUpperCase();
            for(int iter = 0; iter < uppercaseMsg.length(); ++iter) {
                final int charIndex = legalCharacters.indexOf(uppercaseMsg.charAt(iter));
                int charToRender;
                if(isInString(charIndex)) {
                    screen.render(x + iter*8*scale, y, charIndex + 30 * 32, color, 0x00, scale);
                }
            }
        }
    }

    /**
     * Returns true if the index 'charIndex for the a char in the string 'chars is NOT -1,
     * indicating that the char is in the string
     */
    private static final boolean isInString(int charIndex) { return charIndex != -1; }
}
