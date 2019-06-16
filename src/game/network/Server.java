package game.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public Server() {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }


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
                    String temp=new String(packet.getData());
                    temp=temp.substring(0,temp.lastIndexOf("}")+1); //because byte mass 1024 and in str mass 1024len
                    System.out.println(temp);
                    JSONObject jsonPacket = (JSONObject) parser.parse(temp); //parse json

                    if (jsonPacket != null)
                        switch ((String) jsonPacket.get("json message")) {
                            case "connect":
                                addClient(new ServerClient((String) jsonPacket.get("name"),
                                        packet.getAddress(), packet.getPort()));

                                break;

                            case "data":

                                break;

                            case "command":

                                break;

                            default:
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
        JSONObject textSend = new JSONObject();
        textSend.put("json message", "connect");
        textSend.put("status", "1");
        send(data, textSend);

    }

    private void send(ServerClient client, JSONObject massage) {
        new Thread(() -> {
            byte[] temp = massage.toJSONString().getBytes();
            DatagramPacket packet = new DatagramPacket(temp, temp.length, client.getAddress(), client.getPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        new Server();
    }

}