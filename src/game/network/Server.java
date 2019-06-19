package game.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.Player;
import game.Point;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//UUID

public class Server {

    private List<ServerClient> clients = new ArrayList<ServerClient>();
    private final int port = 15666;
    private DatagramSocket socket;
    private Thread serverRun;
    private Thread receive;
    private boolean running = false;
    private JSONParser parser = new JSONParser();
    private Game game;

    public Server(Game game) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        this.game = game;

        serverRun = new Thread(() -> {
            running = true;
            System.out.println("Server started");
            manage();
            receive();
        }, "serverRun");

        serverRun.start();
    }

    private void manage() {
        Runnable runnable = () -> {
            while (running) {

            }
        };
        Thread manage = new Thread(runnable, "manage");
        manage.start();
    }

    private void receive() {
        receive = new Thread(() -> {
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
                    System.out.println(message);
                    JSONObject jsonPacket = (JSONObject) parser.parse(message); //parse json

                    if (jsonPacket != null)
                        switch ((String) jsonPacket.get("json message")) {
                            case "connect":
                                addClient(new ServerClient((String) jsonPacket.get("name"),
                                        packet.getAddress(), packet.getPort()));
                                break;

                            case "command":
                                sendCommand(jsonPacket);
                                break;

                            case "request":
                                if("map".equals(jsonPacket.get("type")))
                                    sendToIP(packet.getAddress(),game.mapInJSON());
                                if("group".equals(jsonPacket.get("type")))
                                    sendToIP(packet.getAddress(),game.groupJson());

                                break;
                        }

                    System.out.println(clients.get(0).getAddress().toString() + ":" + packet.getPort());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, "receive");
        receive.start();
    }

    private void process(DatagramPacket packet) {
        String str = new String(packet.getData());
        System.out.println(str);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addClient(ServerClient data) {
        String temp = data.getName();
        for (ServerClient i : clients) {
            if (i.getName().equals(temp)) {
                JSONObject textSend = new JSONObject();
                textSend.put("json message", "connect");
                textSend.put("status", "0");
                send(data, textSend);
                return;
            }
        }
        clients.add(data);
        Point startPoint = game.getStartPosition();

        if (!game.addPlayerInGroup(new Player(data.getName(),
                startPoint.getX(), startPoint.getY()))) {

            game.delPlayer(data.getName());
            game.addPlayerInGroup(new Player(data.getName(),
                    startPoint.getX(), startPoint.getY()));
        }

        JSONObject textSend = new JSONObject();
        textSend.put("json message", "connect");
        textSend.put("status", "1");
        send(data, textSend);

    }

    private void sendToIP(InetAddress address, JSONObject message){
        for (ServerClient o:clients) {
            if(o.getAddress().equals(address)){
                send(o,message);
                return;
            }
        }
    }

    private void send(ServerClient client, JSONObject message) {
        new Thread(() -> {
            final byte[] temp = message.toJSONString().getBytes();
            DatagramPacket packet = new DatagramPacket(temp, temp.length, client.getAddress(), client.getPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendCommand(JSONObject sendData){
        new Thread(() -> {
            game.acceptComand(sendData);
            final String name= (String) sendData.get("player");
            for (ServerClient i : clients) {
                if(!i.getName().equals(name))
                    send(i,sendData);
            }

        }).start();
    }

    public void stopServer(){
        this.running=false;
    }

    public static void main(String[] args) {
         new Server(new Game());
    }

}