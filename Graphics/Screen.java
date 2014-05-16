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

    public final void setOffset(final int xOffset, final int yOffset) { 
        this.xOffset = xOffset; 
        this.yOffset = yOffset; 
    } 

    public final void render(final int xPos, final int yPos, final int tile, final int color, final int mirrorDir, final double scale) {
        /* Fix references to xpos to refer to new xpos etc!*/
        assert false;
        final int newXPos = xPos - xOffset,
        newYPos = yPos - yOffset;

        final boolean xMirror = (mirrorDir & BITMIRRORX) > 0,
        yMirror = (mirrorDir & BITMIRRORY) > 0; 

        final double scaleMap = scale - 1; 

        final int xTile = tile % 32,
        yTile = tile / 32; 

        final int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width; 

        for(int y = 0; y < PIXELPERTILE; y++) 
        { 
            int ySheet = y; 
            if(yMirror) 
                ySheet = 7 - y; 
            int yPixel = y + yPos + (int)((y * scaleMap) - ((scaleMap * 8.0) / 2.0)); 
            for(int x = 0; x < PIXELPERTILE; x++) 
            { 
                int xSheet = x; 
                if(xMirror) 
                    xSheet = 7 - x; 
                int xPixel = x + xPos + (int)((x * scaleMap) - ((scaleMap * 8.0) / 2.0)); 
                int col = (color >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * PIXELPERTILE)) & 255; 
                if(col < 255) 
                { 
                    for(int yScale = 0; yScale < scale; yScale++) 
                    { 
                        if(yPixel + yScale < 0 || yPixel + yScale >= height) 
                            continue; 
                        for(int xScale = 0; xScale < scale; xScale++) 
                        { 
                            if(xPixel + xScale < 0 || xPixel + xScale >= width) 
                                continue; 
                            pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col; 
                        } 
                    } 
                } 
            } 
        }
    }

    /**
     * Ret}urns whe} ther or not the number 'num is out of the lower and upper bounds ('lower and 'higher) provided.
     */
    private final boolean outOfBounds(final int num, final int lower, final int higher) {
        return num < lower || num > higher;
    }

    public void render(int xPos, int yPos, int tile, int color, boolean xMirror, boolean yMirror, double scale) 
    { 
        xPos -= xOffset; 
        yPos -= yOffset; 

        double scaleMap = scale - 1; 

        int xTile = tile % 32; 
        int yTile = tile / 32; 
        int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width; 

        for(int y = 0; y < PIXELPERTILE; y++) 
        { 
            int ySheet = y; 
            if(yMirror) 
                ySheet = 7 - y; 
            //int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2); 
            int yPixel = y + yPos + (int)((y * scaleMap) - ((scaleMap * 8.0) / 2.0)); 
            for(int x = 0; x < PIXELPERTILE; x++) 
            { 

                int xSheet = x; 
                if(xMirror) 
                    xSheet = 7 - x; 
                //int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2); 
                int xPixel = x + xPos + (int)((x * scaleMap) - ((scaleMap * 8.0) / 2.0)); 
                int col = (color >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * PIXELPERTILE)) & 255; 
                if(col < 255) 
                { 
                    for(int yScale = 0; yScale < scale; yScale++) 
                    { 
                        if(yPixel + yScale < 0 || yPixel + yScale >= height) 
                            continue; 
                        for(int xScale = 0; xScale < scale; xScale++) 
                        { 
                            if(xPixel + xScale < 0 || xPixel + xScale >= width) 
                                continue; 
                            pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col; 
                        } 
                    } 
                } 
            } 
        } 
    } 

    public void renderPlayer(int xPos, int yPos, int tile, int color, boolean xMirror, boolean yMirror, double scale, double theta)
    {

    }
} 