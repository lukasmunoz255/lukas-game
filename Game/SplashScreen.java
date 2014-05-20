package Game;

/**
 * Write a description of class SplashScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SplashScreen extends Stage
{
    private java.awt.image.BufferedImage background;
    private long timer;
    private Sound.SoundFile gunSound;
    private Sound.SoundPlayer sfx;
    public SplashScreen(Game game) {
        super(null, game, null);
        try {
            background = javax.imageio.ImageIO.read(new java.io.File("Images/mmBackground.png"));
            gunSound = new Sound.WaveFile("Sound/reloadsound.wav");
            sfx = new Sound.WavePlayer();
        } catch (Exception e) { e.printStackTrace(); }
        timer = 4000;
    }

    public void clicked(InputHandler input) {
        // do nothing
    }

    public void renderEntities(Graphics.Screen screen, java.awt.Graphics g) {
        g.drawImage(background, 0, 0, screen.width * 3, screen.height * 3, null);
        if (timer <= 0) {
            game.currentStage = game.homeScreen;
            sfx.play(gunSound);
        } else { timer -= 16; }
    }
}
