package game;

import java.net.DatagramSocket;

public class Server {

//    private List
    private int port;
    private DatagramSocket socket;
    private Thread serverRun, manage, receive;

    public Server(int port){
        this.port=port;

    }

}