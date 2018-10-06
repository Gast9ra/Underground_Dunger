package game;

import game.TypesCells.EmptyCell;
import game.TypesCells.Wall;

import org.json.simple.*;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        Game game=new Game(new Map(6,6));
        game.drowMap();
        game.up(0);
        game.down(0);
        game.drowMap();

    }
}
