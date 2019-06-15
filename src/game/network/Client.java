package game.network;

import java.io.IOException;
import java.net.*;

public class Client {

    private DatagramSocket socket = null;
    private InetAddress ip = null;
    private Thread send;
    private final int port=15666;


    public Client(){

    }

    public boolean openConnection(String address) {
        try {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(address);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public String receive() {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = new String(packet.getData());
        return message;
    }


    public void send(String message) {
        if (message.equals("")) return;
        send(message.getBytes());
    }

    private void send(final byte[] data) {
        send = new Thread(new Runnable() {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        send.start();
    }


}
