package Sound;

import java.io.PrintStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class MidiPlayer implements SoundPlayer
{
    private Sequencer midiSequencer;

    public MidiPlayer() throws MidiUnavailableException
    {
        this.midiSequencer = MidiSystem.getSequencer();
        this.midiSequencer.open();
    }

    private final void assertValid(SoundFile soundClip) throws IllegalArgumentException
    {
        if (!(soundClip instanceof MidiFile)) {
            throw new IllegalArgumentException("The passed sound clip is not a midi file: " + soundClip.getClass());
        }
    }

    public void playOnRepeat(SoundFile soundClip) throws IllegalArgumentException
    {
        assertValid(soundClip);
        try
        {
            stop();
            this.midiSequencer.setSequence(MidiSystem.getSequence(((MidiFile)soundClip).getMidiFile()));
            this.midiSequencer.setLoopCount(-1);
            this.midiSequencer.start();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void play(SoundFile soundClip) throws IllegalArgumentException
    {
        assertValid(soundClip);
        try
        {
            stop();
            this.midiSequencer.setSequence(MidiSystem.getSequence(((MidiFile)soundClip).getMidiFile()));
            this.midiSequencer.setLoopCount(0);
            this.midiSequencer.start();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void stop()
    {
        try
        {
            this.midiSequencer.stop();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
