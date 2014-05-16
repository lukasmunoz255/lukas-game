package Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

/**
 * Sets the spritesheet for use in the Screen class.
 * 
 * @author Lukas Muñoz, Luke Staunton, JCL
 */
public class SpriteSheet {
    public String path;
    public int height, width, pixels[];

    /**
     * Sets up a spritesheet to be used using a path to a sheet. 
     */
    public SpriteSheet(final String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            
            this.path = path;
            this.width = image.getWidth();
            this.height = image.getHeight();
            
            pixels = image.getRGB(0, 0, width, height, null, 0, width);
            for (int iter = 0; iter < pixels.length; ++iter) {
                pixels[iter] = (pixels[iter] & 0xff)/64;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
