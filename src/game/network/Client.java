package game.network;

import game.Game;
import game.Player;
import game.Point;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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
    private int step = 0;
    private int playerNum;
    //const message is more fast for use JsonObject
    private final String requstMap = "{\"json message\":\"request\",\"type\":\"map\"}";
    private final String requestGroup = "{\"json message\":\"request\",\"type\":\"group\"}";
    private Label drawMap;


    public Client(String address, String name) {
        this.player = new Player(name);
        this.game = new Game();
        this.game.setLinkGame(this.game);
        if (openConnection(address)) {
            this.running = true;
            receive();
        }
    }

    public Client(String address, String name, Label connectStatus, Label map) {
        this.player = new Player(name);
        this.game = new Game();
        this.drawMap=map;
        this.game.setLinkGame(this.game);
        if (openConnection(address)) {
            connectStatus.setText("connect");
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
                    if(game.isExit()) running=false;
                    String message = new String(packet.getData());
                    //because byte mass 1024 and in str mass 1024len
                    //System.out.println("CLient not sub="+message);
                    //need check message }
                    message = message.substring(0, message.lastIndexOf("}") + 1);
                   // System.out.println("CLient" + message); //debug
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
//                                if(((String) jsonPacket.get("command")).equals("ex") ) {
//                                    running=false;
//                                }
                                game.acceptComand(jsonPacket);
                                //drawMap.setText(game.drawMapToString());
                                break;

                            case "data":
                                String type = (String) jsonPacket.get("type");
                                if ("map".equals(type)) {
                                    game.loadMap(jsonPacket.toJSONString());
                                    //drawMap.setText(game.drawMapToString());
                                }

                                if ("group".equals(type)) {
                                    addGroupJson(jsonPacket);
                                    //drawMap.setText(game.drawMapToString());
                                }

                                break;

                            case "syn":
                                if ("request".equals(jsonPacket.get("type"))) {
                                    JSONObject mess = new JSONObject();
                                    mess.put("json message", "syn");
                                    mess.put("step", step);
                                    send(mess.toJSONString());
                                }
                                break;
                        }

                } catch (NullPointerException | ParseException e) {
                    e.printStackTrace();
                    System.out.println("json not right");
                }
                parser.reset();
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
        //game.delAllwhithout(player.getName());
        if (data.get("groupNull").equals("y")) return;
        JSONArray group = (JSONArray) data.get("group");
        try {
            for (Object o : group) {
                String name = (String) ((JSONObject) o).get("name");
                String[] ordinate = ((String) ((JSONObject) o).get("pos")).split(" ");
                if (name != null && ordinate.length == 2) {
                    game.addPlayerInGroup(new Player(name, new Point(Integer.parseInt(ordinate[0])
                            , Integer.parseInt(ordinate[1]))));
                }
            }
            playerNum = game.numPlayerInGroup(player.getName());

        } catch (NullPointerException e) {
        }

    }

    public void requestMap() {
        send(requstMap);
    }

    public void requestGroup() {
        send(requestGroup);
    }

    public Game getGame() {
        return game;
    }

    public void up() {
        JSONObject message = new JSONObject();
        message.put("json message", "command");
        message.put("command", "up");
        message.put("player", player.getName());
        step++;
        send(message.toJSONString());
        game.up(playerNum);
    }

    public void left() {
        JSONObject message = new JSONObject();
        message.put("json message", "command");
        message.put("command", "left");
        message.put("player", player.getName());
        step++;
        send(message.toJSONString());
        game.left(playerNum);
    }

    public void right() {
        JSONObject message = new JSONObject();
        message.put("json message", "command");
        message.put("command", "right");
        message.put("player", player.getName());
        step++;
        send(message.toJSONString());
        game.right(playerNum);
    }

    public void down() {
        JSONObject message = new JSONObject();
        message.put("json message", "command");
        message.put("command", "down");
        message.put("player", player.getName());
        step++;
        send(message.toJSONString());
        game.down(playerNum);
    }
}
