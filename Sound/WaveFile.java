package Sound;

import java.io.File;
import java.io.IOException;

public class WaveFile implements SoundFile
{
    private File WaveSourceFile;

    public WaveFile(String filename) throws IOException
    {
        this.WaveSourceFile = new File(filename);
    }

    public final File getWaveFile()
    {
        return this.WaveSourceFile;
    }
}