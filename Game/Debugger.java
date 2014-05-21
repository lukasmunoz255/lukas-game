package Game;

/**
 * A graphical debugger that can be toggled on and off and displays info about the game.
 * 
 * @author John Lekberg 
 */
public final class Debugger {
    private static final char grid[][] = new char[31][55];
    public static boolean DebuggerOn = false;
    public static long clearPeriod = -1, // -1 means never auto-clear
    clearTimer = 0;

    /**
     * Outputs the string 'msg begining at the location ('row, 'col). This is only used for
     * debugging. Please do not use.
     */
    private static final void output(final String msg, final int row, final int col) 
    {
        int rowIter = row, colIter = col, charIter = 0;
        while (rowIter < grid.length) 
        {
            while (colIter < grid[row].length)
            {
                grid[rowIter][colIter] = msg.charAt(charIter);
                ++charIter;
                ++colIter;
                if (charIter >= msg.length()) 
                { 
                    return; 
                }
            }
            colIter = 0;
            ++rowIter;
        }
    }

    /**
     * Sends a message 'message to the debugger to be written on the debugger display.
     */
    public static final void sendMsg(final String message) {
        final String messageToPrint = "::" + message;
        final int rowsOccupied = (messageToPrint.length()) / grid[0].length + ((messageToPrint.length() % grid[0].length == 0) ? (0) : (1));
        // shift all the rows up
        for (int rowIter = 0; rowIter < grid.length - rowsOccupied; ++rowIter) {
            for (int colIter = 0; colIter < grid[rowIter].length; ++colIter) {
                grid[rowIter][colIter] = grid[rowIter+rowsOccupied][colIter];
            }
        }
        // write the message
        for (int rowIter = grid.length - rowsOccupied; rowIter < grid.length; ++rowIter) {
            for (int colIter = 0; colIter < grid[rowIter].length; ++colIter) {
                grid[rowIter][colIter] = '\0';
            }
        }
        output(messageToPrint, grid.length-rowsOccupied, 0);  
        clearTimer = clearPeriod;
    }

    /**
     * Renders the debugger on the Screen 'screen.
     */
    public static final void render(final Graphics.Screen screen) {
        Graphics.Font.render(String.format("GAME DEBUGGER :: CP=%d :: CT=%d", clearPeriod, clearTimer), screen, screen.xOffset, screen.yOffset, Graphics.Colors.get(0, 0, 0, 555), 1);
        for (int rowIter = 0; rowIter != grid.length; ++rowIter) {
            String currentLine = "";
            for (int colIter = 0; colIter != grid[rowIter].length; ++colIter) {
                currentLine += grid[rowIter][colIter];
            }
            Graphics.Font.render(currentLine, screen, screen.xOffset, screen.yOffset + (rowIter + 1)*8, Graphics.Colors.get(0, 0, 0, 555), 1);
        }
    }

    public static final void clear() {
        for (int rowIter = 0; rowIter != grid.length; ++rowIter) {
            for (int colIter = 0; colIter != grid[rowIter].length; ++colIter) {
                grid[rowIter][colIter] = '\0';
            }
        }
    }

    public static final void update(final long timeElapsedMillis) {
        if (clearPeriod != -1) {
            clearTimer -= timeElapsedMillis;
            if (clearTimer <= 0) {
                clear();
                clearTimer = clearPeriod;
            }
        }
    }
}