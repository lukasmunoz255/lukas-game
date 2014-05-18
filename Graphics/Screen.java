package Graphics; 

/** 
 * Write a description of class Screen here. 
 *  
 * @author Lukas Muñoz, Luke Staunton, JCL  
 */
public class Screen 
{ 
    public static final int MAP_WIDTH = 64,
    MAP_WIDTH_MASK = MAP_WIDTH - 1,
    PIXELPERTILE = 8; 

    public int xOffset = 0,
    yOffset = 0,
    width,
    height,
    pixels[];

    public static final byte BITMIRRORX = 0x01,
    BITMIRRORY = 0x02;

    public SpriteSheet sheet; 

    public Screen(final int width, final int height, final SpriteSheet sheet) { 
        this.width = width; 
        this.height = height; 
        this.sheet = sheet; 

        pixels = new int[width * height]; 
    } 

    /**
     * Sets the offsets of the screen to be used for rendering tiles.
     */
    public final void setOffset(final int xOffset, final int yOffset) { 
        this.xOffset = xOffset; 
        this.yOffset = yOffset; 
    } 

    /**
     * Renders the tile 'tile on the screen with a 'scale, and possibly mirrored on the x- or y-axes at position
     * ('xPos, 'yPos).
     */
    public final void render(final int xPos, final int yPos, final int tile, final int color, final int mirrorDir, final double scale) {
        render(xPos, yPos, tile, color, false, false, scale);
    }

    /**
     * Returns whether or not the number 'num is out of the lower and upper bounds ('lower, 'higher) provided.
     */
    private final boolean outOfBounds(final int num, final int lower, final int higher) {
        return num < lower || num > higher;
    }

    /**
     * Renders the tile 'tile on the screen with a 'scale, and possibly mirrored on the x- or y-axes at position
     * ('xPos, 'yPos).
     */
    public final void render(final int xPos, final int yPos, final int tile, final int color, final boolean xMirror, final boolean yMirror, final double scale) { 
        final int newXPos = xPos - xOffset,
        newYPos = yPos - yOffset,
        xTile = tile % 32,
        yTile = tile / 32, 
        tileOffset = (xTile << 3) + (yTile << 3) * sheet.width; 

        final double scaleMap = scale - 1; 

        for(int yIter = 0; yIter < PIXELPERTILE; ++yIter) { 
            int ySheet = (yMirror) ? (7 - yIter) : (yIter);
            int yPixel = yIter + newYPos + (int)((yIter * scaleMap) - ((scaleMap * 8.0) / 2.0)); 
            for(int xIter = 0; xIter < PIXELPERTILE; ++xIter) {
                final int xSheet = (xMirror) ? (7 - xIter) : xIter,
                xPixel = xIter + newXPos + (int)((xIter * scaleMap) - ((scaleMap * 8.0) / 2.0)),
                col = (color >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * PIXELPERTILE)) & 255; 
                if(col < 255) { 
                    for(int yScale = 0; yScale < scale; ++yScale) {
                        if (outOfBounds(yPixel + yScale, 0, height - 1)) { continue; }
                        for(int xScale = 0; xScale < scale; ++xScale) { 
                            if (outOfBounds(xPixel + xScale, 0, width - 1)) { continue; }
                            pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col; 
                        } 
                    } 
                } 
            } 
        } 
    }
} 