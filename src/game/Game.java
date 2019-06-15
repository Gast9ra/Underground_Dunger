package game;

import game.TypesCells.EmptyCell;
import game.TypesCells.Wall;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Game {

    //link on this class for event
    private Game linkGame=null;
    private Map map;
    private ArrayList<Player> group = new ArrayList<Player>();


    public Game(Map data) {
        this.map = data;
//        group.add(new Player("1"));
//        group.add(new Player("2", 2, 1));
    }

    public void setLinkGame(Game linkGame) {
        this.linkGame = linkGame;
    }

    public void update() {

        //i=x j=y
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                map.getCell(i, j).update();
            }
        }
    }

    //debug
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
                    if (pl.playerThisPoint(i, j)) System.out.print(pl.getName());
                    else
                        System.out.print(map.getCell(j, i).getClassName());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();

    }

    public JSONObject mapInJSON() {
        return map.jsonMap();
    }

    public void loadMap(String json) {
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(json);
            if (data.get("json-message").equals("data")) {
                int height = Integer.parseInt(data.get("height").toString());
                Map result = new Map(height, Integer.parseInt(data.get("width").toString()));

                for (int j = 0; j < height; j++) {
                    JSONArray column = (JSONArray) data.get("column" + j);
                    for (int i = 0; i < column.size(); i++) {

                        switch ((String) column.get(i)) {
                            case "E":
                                result.reloadCell(j, i, new EmptyCell());
                                break;

                            case "W":
                                result.reloadCell(j, i, new Wall());
                                break;
                        }
                    }
                }

                map = result;
//                System.gc();
            }


        } catch (ParseException e) {
            System.out.println("Bad command");
        }

    }

    public boolean addPlayerInGroup(Player newPlayer){
        String name=newPlayer.getName();

        for (Player i:group) {
            if(i.getName().equals(name)){
                return false;
            }
        }

        group.add(newPlayer);
        return true;
    }

    public void delPlayer(String name){
        for (Player i:group) {
            if(i.getName().equals(name)){
                group.remove(i);
                return;
            }
        }
    }
    
}