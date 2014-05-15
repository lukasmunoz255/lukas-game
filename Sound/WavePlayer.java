package Sound;

import java.io.PrintStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;

public class WavePlayer implements SoundPlayer
{
    private AudioInputStream waveStream;
    private AudioFormat waveFormat;
    private DataLine.Info waveInfo;
    private Clip waveClip;
    private SoundFile soundClip;
    

    public WavePlayer()
    {

    }

    

    private final void assertValid(SoundFile soundClip) throws IllegalArgumentException
    {
        if (!(soundClip instanceof WaveFile)) 
        {
            throw new IllegalArgumentException("The passed sound clip is not a wave file: " + soundClip.getClass());
        }
    }

    public void playOnRepeat(SoundFile soundClip) throws IllegalArgumentException
    {
        assertValid(soundClip);
        stop();
        try
        {
            this.waveStream = AudioSystem.getAudioInputStream(((WaveFile)soundClip).getWaveFile());
            this.waveFormat = this.waveStream.getFormat();
            this.waveInfo = new DataLine.Info(Clip.class, this.waveFormat);
            this.waveClip = ((Clip)AudioSystem.getLine(this.waveInfo));
            this.waveClip.open(this.waveStream);
            this.waveClip.loop(-1);
            this.waveClip.start();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void play(SoundFile soundClip) throws IllegalArgumentException
    {
        assertValid(soundClip);
        stop();
        try
        {
            this.waveStream = AudioSystem.getAudioInputStream(((WaveFile)soundClip).getWaveFile());
            this.waveFormat = this.waveStream.getFormat();
            this.waveInfo = new DataLine.Info(Clip.class, this.waveFormat);
            this.waveClip = ((Clip)AudioSystem.getLine(this.waveInfo));
            this.waveClip.open(this.waveStream);//Error line
            this.waveClip.start();
        }
        catch (Exception e)
        {
            System.out.println( "frame length: " + waveStream.getFrameLength() );
            System.out.println( "frame size: " + waveStream.getFormat().getFrameSize() );
            System.out.println(e);
        }
    }
    
    public WavePlayer(SoundFile soundClip)
    {
        assertValid(soundClip);
        this.soundClip = soundClip;
        try
        {
            this.waveInfo = new DataLine.Info(Clip.class, this.waveFormat);
            this.waveClip = ((Clip)AudioSystem.getLine(this.waveInfo));//fucking clips man
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        stop();
        try
        {
            this.waveStream = AudioSystem.getAudioInputStream(((WaveFile)soundClip).getWaveFile());
            this.waveFormat = this.waveStream.getFormat();
            //AudioFormat format = waveStream.getFormat();
            //System.out.println(format);
            
            this.waveClip.open(this.waveStream);//Error line
            this.waveClip.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        if (this.waveClip == null) 
        {
            return;
        }
        try
        {
            this.waveClip.stop();
            //System.gc();
            //this.waveClip.drain();
            //this.waveClip.flush();
            //this.waveClip.close();
            //this.waveStream.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void close()
    {
        this.waveClip.close();
    }
}

