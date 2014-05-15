package Sound;

import java.io.File;
import java.io.IOException;

public class MidiFile implements SoundFile
{
    private File MidiSourceFile;

    public MidiFile(String filename)
    throws IOException
    {
        this.MidiSourceFile = new File(filename);
    }

    public final File getMidiFile()
    {
        return this.MidiSourceFile;
    }
}
