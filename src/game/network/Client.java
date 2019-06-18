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
    private boolean running=false;
    private boolean connect=false;
    private String name;
    private JSONParser parser = new JSONParser();


    public Client(String address,String name) {
        this.name=name;
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
            JSONObject message=new JSONObject();
            message.put("json message","connect");
            message.put("name",name);
            send(message.toJSONString());
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
                    if (jsonPacket != null)
                        switch ((String) jsonPacket.get("json message")) {
                            case "connect":
                                if("1".equals(jsonPacket.get("status"))) connect=true;
                                else {
                                    System.out.println("not connect");
                                }
                                break;

                            case "command":
                                int index=0;
                                switch ((String)jsonPacket.get("command")){
                                    case "up":
                                        //index in group and check
                                         index=gameCl.numPlayerInGraoup((String) jsonPacket.get("player"));
                                        if(index>=0) gameCl.up(index);
                                        break;
                                    case "left":
                                        //index in group and check
                                         index=gameCl.numPlayerInGraoup((String) jsonPacket.get("player"));
                                        if(index>=0) gameCl.left(index);
                                        break;
                                    case "down":
                                        //index in group and check
                                         index=gameCl.numPlayerInGraoup((String) jsonPacket.get("player"));
                                        if(index>=0) gameCl.down(index);
                                        break;
                                    case "right":
                                        //index in group and check
                                         index=gameCl.numPlayerInGraoup((String) jsonPacket.get("player"));
                                        if(index>=0) gameCl.right(index);
                                        break;
                                }

                                break;

                            case "data":
                                gameCl.loadMap(jsonPacket.toJSONString());
                                break;

                            default:
                                break;
                        }

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


    public boolean isConnect() {
        return connect;
    }
}
