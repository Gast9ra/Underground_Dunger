package sample;

import game.Game;
import game.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import game.network.*;

public class Controller {

    @FXML
    private TextField textName;

    @FXML
    private Label textForConnect;

    @FXML
    private TextField textIPConnect;


    Client client;
    Server server;


    public void connect() {
        strConect(textIPConnect.getText(),textName.getText());

    }

    private void strConect(String address, String name){
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client = new Client(address, name);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.requestMap();
        client.requestGroup();

        textName.setVisible(false);
        textIPConnect.setVisible(false);
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

        client.getGame().drowMap();
    }

    public void startServer() {
        Server server = new Server(new Game(new Map(16, 16)));
        strConect("127.0.0.1",textName.getText());
    }
}


