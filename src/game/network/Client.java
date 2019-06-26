package game.network;

import game.Game;
import game.Player;
import game.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Client {

    private DatagramSocket socket = null;
    private InetAddress ip = null;
    private final int port = 15666;
    private Game game;
    private boolean running = false;
    private boolean connect = false;
    private Player player;
    private JSONParser parser = new JSONParser();
    private int step=0;
    private final String requstMap="{\"json message\":\"request\",\"type\":\"map\"}";
    private final String requestGroup="{\"json message\":\"request\",\"type\":\"group\"}";


    public Client(String address, String name) {
        this.player = new Player(name);
        this.game=new Game();
        if (openConnection(address)) {
            this.running = true;
            receive();
        }
    }

    public boolean openConnection(String address) {
        try {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(address);
            //send connect packet
            JSONObject message = new JSONObject();
            message.put("json message", "connect");
            message.put("name", player.getName());
            send(message.toJSONString());
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void receive() {
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
                    if (jsonPacket != null)
                        switch ((String) jsonPacket.get("json message")) {
                            case "connect":
                                if ("1".equals(jsonPacket.get("status"))) connect = true;
                                else {
                                    System.out.println("not connect");
                                }
                                break;

                            case "command":
                                game.acceptComand(jsonPacket);

                                break;

                            case "data":
                                String type = (String) jsonPacket.get("type");
                                if ("map".equals(type))
                                    game.loadMap(jsonPacket.toJSONString());

                                if ("group".equals(type)) {
                                    addGroupJson(jsonPacket);
                                }

                                break;

                            case "syn":
                                if ("request".equals(jsonPacket.get("type"))) {
                                    JSONObject mess=new JSONObject();
                                    mess.put("json message","syn");
                                    mess.put("step",step);
                                    send(mess.toJSONString());
                                }
                                break;
                        }

                } catch (NullPointerException|ParseException e) {
                    e.printStackTrace();
                    System.out.println("json not right");
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


    public boolean isConnect() {
        return connect;
    }

    //need test
    private void addGroupJson(JSONObject data) {
        game.delAllwhithout(player.getName());
        JSONArray group = (JSONArray) data.get("group");
        try {
            for (Object o : group) {
                String name = (String) ((JSONObject) o).get("name");
                String[] ordinate = ((String) ((JSONObject) o).get("pos")).split(" ");
                if (name != null && ordinate.length == 2) {
                    game.addPlayerInGroup(new Player(name,new Point(Integer.parseInt(ordinate[0])
                            ,Integer.parseInt(ordinate[1]))));
                }
            }
        } catch (NullPointerException e) {
        }

    }

    public void requestMap(){
        send(requstMap);
    }

    public void requestGroup(){
        send(requestGroup);
    }

    public Game getGame() {
        return game;
    }
}
