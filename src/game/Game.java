package game;

import java.util.ArrayList;

public class Game {

    private Map map;
    private ArrayList<Player> group = new ArrayList<Player>();


    public Game(Map data) {
        this.map = data;
        group.add(new Player("1"));
        group.add(new Player("2", 2, 1));
    }


    public void update() {

        //j=x i=y
        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                map.getCell(j, i).update(map);

            }
        }

    }

    //if need debug
    public void printMap() {
        map.printMap();
    }


    public void up(int numberPlayer) {
        if (numberPlayer > group.size() - 1) throw new ArrayIndexOutOfBoundsException("Player not found");
        if (map.getCell(group.get(numberPlayer).getLocation().getX()
                , group.get(numberPlayer).getLocation().getY() - 1).getCanMove())
            group.get(numberPlayer).addOrdinate(0, -1);
    }

    public void down(int numberPlayer) {
        if (numberPlayer > group.size() - 1) throw new ArrayIndexOutOfBoundsException("Player not found");
        if (map.getCell(group.get(numberPlayer).getLocation().getX()
                , group.get(numberPlayer).getLocation().getY() + 1).getCanMove())
            group.get(numberPlayer).addOrdinate(0, 1);
    }

    public void left(int numberPlayer) {
        if (numberPlayer > group.size() - 1) throw new ArrayIndexOutOfBoundsException("Player not found");
        if (map.getCell(group.get(numberPlayer).getLocation().getX() - 1
                , group.get(numberPlayer).getLocation().getY()).getCanMove())
            group.get(numberPlayer).addOrdinate(-1, 0);
    }

    public void right(int numberPlayer) {
        if (numberPlayer > group.size() - 1) throw new ArrayIndexOutOfBoundsException("Player not found");
        if (map.getCell(group.get(numberPlayer).getLocation().getX() + 1
                , group.get(numberPlayer).getLocation().getY()).getCanMove())
            group.get(numberPlayer).addOrdinate(1, 0);
    }

    public void drowMap() {
        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                for (Player pl : group) {
                    if (pl.playerThisPoint(i , j)) System.out.print(pl.getName());
                    else
                        System.out.print(map.getCell(j, i).getClassName());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
