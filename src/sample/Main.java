package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    //private Game biz = new Game(new Map(6, 6));

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Test Network");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Parent root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));

        Scene scene = new Scene(root, 720, 470);
        primaryStage.setScene(scene);
        primaryStage.show();
      //  System.out.println(biz.addPlayerInGroup(new Player("test", 1, 1)));

//        scene.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.W) {
//                biz.up(0);
//            } else if (e.getCode() == KeyCode.A) {
//                biz.left(0);
//            } else if (e.getCode() == KeyCode.D) {
//                biz.right(0);
//            } else if (e.getCode() == KeyCode.S) {
//                biz.down(0);
//            }
//
//            biz.drawMap();
//
//        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
