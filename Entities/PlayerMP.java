package Entities;

import Game.InputHandler;
import Game.Level;
import Game.Debugger;

import Graphics.Screen;
import Graphics.Colors;
import Graphics.Font;

import java.net.InetAddress;
import java.awt.Graphics;

/**
 * Write a description of class PlayerMP here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerMP extends Player
{
    public InetAddress ipAddress;
    public int port;
    private String username;

    /**
     * Constructor for objects of class PlayerMP
     */
    public PlayerMP(Level level, int x, int y, InputHandler input, Screen screen, String name, 
    String username, InetAddress ipAddress, int port)
    {
        super(level, x, y, input, screen, name);
        this.username = username;
        this.ipAddress = ipAddress;
        this.port = port;
        Debugger.sendMsg(String.format("created a player at (%d, %d)", x, y));
    }
    
    public PlayerMP(Level level, int x, int y, Screen screen, String name, 
    String userName, InetAddress ipAddress, int port)
    {
        super(level, x, y, null, screen, name);
        this.username = username;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public void render(Screen screen, Graphics g)
    {
        super.render(screen, g);
        if(username != null)
            Font.render(username, screen, x-4 -(username.length()-2)*8/2, y-22, Colors.get(-1,-1,-1,000), 1);
    }
    
    public String getUsername()
    {
        return username;
    }

    public void tick()
    {
        super.tick();
    }
}
