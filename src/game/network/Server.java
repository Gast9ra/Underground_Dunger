package game.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
//UUID

public class Server {

    private List<ServerClient> clients= new ArrayList<ServerClient>();
    private final int port=15666;
    private DatagramSocket socket;
    private Thread serverRun;
    private Thread receive;
    private Thread manage;
    private boolean running=false;
    private int id=0;

    public Server(){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        serverRun = new Thread(new Runnable() {
            @Override
            public void run() {
                running = true;
                System.out.println("Server started");
                manage();
                receive();
            }
        }, "serverRun");

        serverRun.start();
    }

    private void manage() {
        Thread manage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {

                }
            }
        }, "manage");
        manage.start();
    }

    private void receive() {
        receive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    process(packet);
                    clients.add(new ServerClient("Winderton", packet.getAddress(), packet.getPort(), 3434));
                    System.out.println(clients.get(0).getAddress().toString() + ":" + packet.getPort());
                }
            }
        }, "receive");
        receive.start();
    }

    private void process(DatagramPacket packet ) {
        String str = new String(packet.getData());
        System.out.println(str);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server e= new Server();
    }

}