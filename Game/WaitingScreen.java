package Game;

import Graphics.Screen;
import Graphics.Font;
import Graphics.Colors;
import Entities.PlayerMP;

import java.awt.Graphics;

import Net.*;
import Net.Packets.*;

import javax.swing.JOptionPane;

/**
 * Write a description of class WaitingScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaitingScreen extends Stage
{
    public GameClient socketClient;
    public GameServer socketServer;

    /**
     * Constructor for objects of class WaitingScreen
     */
    public WaitingScreen(Level level, Game game)
    {
        super(level, game, null);
    }

    public void initialize()
    {
        Packet00Login loginPacket = null;
        boolean yes = false;
        if(JOptionPane.showConfirmDialog(game, "Do you want to run the server") == 0)
        {   
            socketServer = new GameServer(game);
            socketServer.start();
            String username = JOptionPane.showInputDialog(game, "Enter a username");
            PlayerMP player = new PlayerMP(null, 100, 100, game.input, game.gameScreen.screen, "player", 
                    username, null, -1);
            game.gameScreen.replacePlayer(player, 0);
            //loginPacket = new PacketLogin(player.getUsername());
            yes = true;
            socketServer.addConnection(player, loginPacket);
        }

        if(!yes && socketServer == null)
        {
            PlayerMP player = new PlayerMP(null, 100, 100, game.input, game.gameScreen.screen, "player", 
                    JOptionPane.showInputDialog(game, "Enter a username"), null, -1);
            game.gameScreen.replacePlayer(player, 0);
            //loginPacket = new Packet00Login(player.getUsername());

            //.addConnection(player, loginPacket);
        }

        socketClient = new GameClient(game, "localhost");
        socketClient.start();

        //Packet00Login loginPacket = new Packet00Login(JOptionPane.showInputDialog(game, "Please enter a username"));
        if(loginPacket != null)
            loginPacket.writeData(socketClient);
    }

    public void renderEntities(Screen screen, Graphics g)
    {
        String msg = "waiting";
        Font.render(msg, screen, screen.xOffset + screen.width/2 - msg.length()*8 / 2, 
            screen.yOffset + screen.height/2 - 50, Colors.get(-1, -1, -1, 123), 3);

        if(game.gameScreen.players.size() >= 2)
        {
            game.gameScreen.initialize();
            game.input.setScreenType(1);
            game.currentStage = game.gameScreen;
        }
    }

    public void clicked(InputHandler input)
    {}
}
