package sample;

import game.Game;
import game.Map;
import game.Point;
import game.TypesCells.Monster;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import game.network.*;

import java.util.concurrent.CountDownLatch;

public class Controller {

    @FXML
    private TextField textName;

    @FXML
    private Label textForConnect;

    @FXML
    private TextField textIPConnect;

    @FXML
    private Label map;


    Client client;
    Server server;


    public void connect() {
        startConnect(textIPConnect.getText(), textName.getText());

    }

    private void startConnect(String address, String name) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client = new Client(address, name, textForConnect, map);
        try {
            Thread.sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.requestMap();
        client.requestGroup();

        textName.setVisible(false);
        textIPConnect.setVisible(false);
        update();
    }

    private void update() {
        Platform.runLater(() -> {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    map.setText(client.getGame().drawMapToString());
                }
        );



    }


    public void keyN(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) {
            client.up();
        } else if (keyEvent.getCode() == KeyCode.A) {
            client.left();
        } else if (keyEvent.getCode() == KeyCode.D) {
            client.right();
        } else if (keyEvent.getCode() == KeyCode.S) {
            client.down();
        }

//        client.getGame().drawMap();
        map.setText(client.getGame().drawMapToString());
    }

    public void startServer() {
        Map map = new Map(8, 8);
        Game game = new Game();
        map.reloadCell(6, 6, new Monster(game, new Point(6, 6)));
        game.setMap(map);
        game.setLinkGame(game);
        server = new Server(game);
        startConnect("127.0.0.1", textName.getText());
    }
}


