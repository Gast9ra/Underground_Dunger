package game.network;

import game.Game;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;

public class Client {

    private DatagramSocket socket = null;
    private InetAddress ip = null;
    private final int port = 15666;
    private Game gameCl;
    private boolean running = false;
    private JSONParser parser = new JSONParser();


    public Client(String address) {
        this.running = true;
        if (openConnection(address)) {
            receive();
        }
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


    public void receive() {
        Runnable runnable = () -> {
            while (running) {
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    String message = new String(packet.getData());
                    //because byte mass 1024 and in str mass 1024len
                    message = message.substring(0, message.lastIndexOf("}") + 1);
                    JSONObject jsonPacket = (JSONObject) parser.parse(message); //parse json
                    

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread receive = new Thread(runnable, "receive");
        receive.start();
    }


    //final byte[] data
    public void send(String message) {
        if (message.equals("")) return;
        final byte[] data = message.getBytes();
        Thread send = new Thread(new Runnable() {
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

    public void stop() {
        this.running = false;
    }


}
