package Game;
/**
 * Write a description of class Timer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Timer
{
    // instance variables - replace the example below with your own
    private long start;
    private long laststop;
    private long stop;
    private boolean running;
 
    /**
     * Constructor for objects of class Timer
     */
    public Timer()
    {
        running = false;
    }
 
    public boolean isRunning()
    {
        return running;
    }
     
    public void start()
    {
        stop();
        start = System.currentTimeMillis();
        running = true;
    }
     
    public void stop()
    {
        laststop = System.currentTimeMillis();
        running = false;
    }
     
    public long getTime()
    {
        laststop = System.currentTimeMillis();
        return laststop-start;
    }
}