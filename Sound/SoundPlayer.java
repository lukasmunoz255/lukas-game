package Sound;

public abstract interface SoundPlayer
{
    public abstract void playOnRepeat(SoundFile paramSoundFile);

    public abstract void play(SoundFile paramSoundFile);

    public abstract void stop();
}