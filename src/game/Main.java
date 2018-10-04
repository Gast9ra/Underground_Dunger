package game;

import game.TypesCells.EmptyCell;
import game.TypesCells.Wall;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<OriginalCell> map = new ArrayList<>();
        map.add(new EmptyCell());
        map.add(new Wall());
        System.out.println(map.get(0).getCanMove());
        System.out.println(map.get(1).getCanMove());





    }
}
