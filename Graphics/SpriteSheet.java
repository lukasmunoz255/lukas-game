package Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

/**
 * Sets the spritesheet for use in the screen class.
 * 
 * Lukers
 * 1/28/14
 */
public class SpriteSheet
{
    public String path;
    public int height;
    public int width;

    public int[] pixels;

    /**
     * Sets up a spritesheet to be used using a path to a sheet. 
     */
    public SpriteSheet(String path)
    {
        BufferedImage image = null;

        try
        {
            //String path2 = getClass().getClassLoader().getResource(".").getPath();
            //ClassLoader loader = Thread.currentThread().getContextClassLoader();
            //URL url = loader.getResource("LargeSpriteSheet.png");
            image = ImageIO.read(new File(path));
            //image = ImageIO.read(url);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        if(image == null)
        {
            return;
        }

        this.path = path;
        this.width = image.getWidth();
        this.height = image.getHeight();

        pixels = image.getRGB(0, 0, width, height, null, 0, width);

        for(int i = 0; i < pixels.length; i++)
        {
            pixels[i] = (pixels[i] & 0xff)/64;
        }
    }
}
