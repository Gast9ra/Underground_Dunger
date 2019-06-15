package game;

import game.TypesCells.Wall;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import game.network.*;


import java.io.FileNotFoundException;

public class Main {


    public static void main(String[] args) throws FileNotFoundException, ParseException {

        Map m=new Map(6,6);
        m.reloadCell(4,4,new Wall());

        Game game=new Game(new Map(6,6));
        game.setLinkGame(game);
        game.drowMap();
        game.loadMap(m.jsonMap().toJSONString());
        game.drowMap();
        game.up(0);
        game.down(0);
        game.drowMap();

    }
}
