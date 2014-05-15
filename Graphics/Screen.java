package Graphics; 
  
/** 
 * Write a description of class Screen here. 
 *  
 * @author (your name)  
 * @version (a version number or a date) 
 */
public class Screen 
{ 
    public static final int MAP_WIDTH = 64; 
    public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1; 
    public final int PIXELPERTILE = 8; 
    public int xOffset = 0;  
    public int yOffset = 0; 
  
    public static final byte BITMIRRORX = 0x01; 
    public static final byte BITMIRRORY = 0x02; 
  
    public int[] pixels;
  
    public int width; 
    public int height; 
  
    public SpriteSheet sheet; 
  
    public Screen(int width, int height, SpriteSheet sheet) 
    { 
        this.width = width; 
        this.height = height; 
        this.sheet = sheet; 
  
        pixels = new int[width * height]; 
    } 
  
    public void setOffset(int xOffset, int yOffset) 
    { 
        this.xOffset = xOffset; 
        this.yOffset = yOffset; 
    } 
  
    public void render(int xPos, int yPos, int tile, int color, int mirrorDir, double scale) 
    { 
        xPos -= xOffset; 
        yPos -= yOffset; 
  
        boolean xMirror = (mirrorDir & BITMIRRORX) > 0; 
        boolean yMirror = (mirrorDir & BITMIRRORY) > 0; 
  
        double scaleMap = scale - 1; 
  
        int xTile = tile % 32; 
        int yTile = tile / 32; 
        int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width; 
  
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