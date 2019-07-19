package game.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.Map;
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
    private int step = 0;
    private final String syncRequest = "{\"json message\":\"syn\",\"type\":\"request\"}";

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
            receive();
            synchronization();
        }, "serverRun");

        serverRun.start();
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
                    //because byte mass 1024 and in str mass 1024 len
                    message = message.substring(0, message.lastIndexOf("}") + 1);
                    //System.out.println("Server" + message);
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
                                if ("map".equals(jsonPacket.get("type")))
                                    sendToIP(packet.getAddress(), packet.getPort(), game.mapInJSON());
                                if ("group".equals(jsonPacket.get("type")))
                                    sendToIP(packet.getAddress(), packet.getPort(), game.groupJson());

                                break;

                            case "syn":
                                if ("answer".equals(jsonPacket.get("type"))) {
                                    if (Integer.parseInt(jsonPacket.get("step").toString()) == step) ;
                                    else sendToIP(packet.getAddress(), packet.getPort(), game.mapInJSON());

                                }
                                break;
                        }

//                    System.out.println(clients.get(0).getAddress().toString() + ":" + packet.getPort());
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

        for (ServerClient i: clients) {
            send(i,game.groupJson());
        }
    }

    private void sendToIP(InetAddress address, int port, JSONObject message) {
        for (ServerClient o : clients) {
            if (o.getAddress().equals(address)&& o.getPort()==port) {
                send(o, message);
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

    private void send(ServerClient client, String message) {
        new Thread(() -> {
            final byte[] temp = message.getBytes();
            DatagramPacket packet = new DatagramPacket(temp, temp.length, client.getAddress(), client.getPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendCommand(JSONObject sendData) {
        new Thread(() -> {
            if (game.acceptComand(sendData)) {
                step++;
                game.update();
//                System.out.println(step);
            } else return;
            final String name = (String) sendData.get("player");
            for (ServerClient i : clients) {
                if (!i.getName().equals(name))
                    send(i, sendData);
            }

        }).start();
    }

    public void stopServer() {
        this.running = false;
    }

    private void synchronization() {
        new Thread(() -> {
            try {
                while (running) {
                    Thread.sleep(10000);
                    if (clients.size() > 0) {
                        for (ServerClient o : clients) {
                            send(o, syncRequest);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "sync").start();
    }

    public static void main(String[] args) {
        new Server(new Game(new Map(8, 8)));
    }

}