package sample;

import game.Game;
import game.Map;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Game biz=new Game(new Map(6,6));

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Test Network");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);


        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                biz.up(0);
            } else if (e.getCode() == KeyCode.LEFT) {
                biz.left(0);
            } else if (e.getCode() == KeyCode.RIGHT) {
                biz.right(0);
            } else if (e.getCode() == KeyCode.DOWN) {
                biz.down(0);
            }

            biz.drowMap();

        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
