package Net.Packets;

import Net.GameClient;
import Net.GameServer;

/**
 * Write a description of class Packet00Login here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Packet00Login extends Packet
{
    
    private String username;
    private int x, y;

    public Packet00Login(byte[] data) 
    {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
    }

    public Packet00Login(String username, int x, int y)
    {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    public void writeData(GameClient client) 
    {
        client.sendData(getData());
    }

    public void writeData(GameServer server)
    {
        server.sendDataToAllClients(getData());
    }

    public byte[] getData() 
    {
        return ("00" + this.username + "," + getX() + "," + getY()).getBytes();
    }

    public String getUsername() 
    {
        return username;
    }

    public int getX() 
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
