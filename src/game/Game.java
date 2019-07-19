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
    private Game linkGame = null;
    private Map map;
    private ArrayList<Player> group = new ArrayList<Player>();
    private Point startPosition = new Point(1, 1);


    public Game(Map map) {
        this.map = map;
//        group.add(new Player("1"));
//        group.add(new Player("2", 2, 1));
    }

    public Game() {
        this.map = new Map(6, 6);
    }

    public Game(String map) {
        loadMap(map);
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
        if (numberPlayer > group.size() - 1) return;
        if (map.getCell(group.get(numberPlayer).getLocation().getX()
                , group.get(numberPlayer).getLocation().getY() - 1).getCanMove())
            group.get(numberPlayer).addOrdinate(0, -1);
    }

    public void down(int numberPlayer) {
        if (numberPlayer > group.size() - 1) return;
        if (map.getCell(group.get(numberPlayer).getLocation().getX()
                , group.get(numberPlayer).getLocation().getY() + 1).getCanMove())
            group.get(numberPlayer).addOrdinate(0, 1);
    }

    public void left(int numberPlayer) {
        if (numberPlayer > group.size() - 1) return;
        if (map.getCell(group.get(numberPlayer).getLocation().getX() - 1
                , group.get(numberPlayer).getLocation().getY()).getCanMove())
            group.get(numberPlayer).addOrdinate(-1, 0);
    }

    public void right(int numberPlayer) {
        if (numberPlayer > group.size() - 1) return;
        if (map.getCell(group.get(numberPlayer).getLocation().getX() + 1
                , group.get(numberPlayer).getLocation().getY()).getCanMove())
            group.get(numberPlayer).addOrdinate(1, 0);
    }

    public void drawMap() {
        if (map == null) return;
        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                if (group.size() > 0)
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

    public String drawMapToString() {

        if (map == null) return "";
        StringBuilder result = new StringBuilder();
        boolean playerOnCell;

        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                playerOnCell = false;
                if (group.size() > 0)
                    for (Player pl : group) {
                        if (pl.playerThisPoint(i, j)) {
                            result.append(pl.getName());
                            playerOnCell = true;
                        }


                    }
                if (!playerOnCell) result.append(map.getCell(j, i).getClassName());

                result.append(" ");
            }
            result.append("\n");
        }
        result.append("\n");

        return result.toString();
    }


    public JSONObject mapInJSON() {
        JSONObject result = map.jsonMap();
        result.put("startPos", startPosition.getX() + " " + startPosition.getY());
        return result;
    }

    public void loadMap(String json) {
        try {
            JSONObject map = (JSONObject) new JSONParser().parse(json);
            if (map.get("json message").equals("data")) {
                int height = Integer.parseInt(map.get("height").toString());
                Map result = new Map(height, Integer.parseInt(map.get("width").toString()));

                for (int j = 0; j < height; j++) {
                    JSONArray column = (JSONArray) map.get("column" + j);
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

                String[] ordinate = ((String) map.get("startPos")).split(" ");
                setStartPosition(new Point(Integer.parseInt(ordinate[0])
                        , Integer.parseInt(ordinate[1])));
                this.map = result;
//                System.gc();
            }


        } catch (NullPointerException | ParseException e) {
            System.out.println("Bad command");
            e.printStackTrace();
        }

    }

    public boolean addPlayerInGroup(Player newPlayer) {
        String name = newPlayer.getName();

        for (Player i : group) {
            if (i.getName().equals(name)) {
                return false;
            }
        }

        group.add(newPlayer);
        return true;
    }

    public void delPlayer(String name) {
        for (Player i : group) {
            if (i.getName().equals(name)) {
                group.remove(i);
                return;
            }
        }
    }

    public void delAllwhithout(String name) {
        for (Player i : group) {
            if (!i.getName().equals(name)) {
                group.remove(i);
            }
        }
        System.gc();
    }

    public ArrayList<Player> getGroup() {
        return group;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Point startPosition) {
        this.startPosition.setX(startPosition.getX());
        this.startPosition.setY(startPosition.getY());
    }

    public int numPlayerInGroup(String name) {
        int result = 0;
        for (int i = 0; i < group.size(); i++) {
            if (group.get(i).getName().equals(name)) {
                result = i;
                return result;
            }
        }
        return -1;
    }

    public boolean acceptComand(JSONObject command) {
        int index;
        switch ((String) command.get("command")) {
            case "up":
                //index in group and check
                index = numPlayerInGroup((String) command.get("player"));
                if (index >= 0) up(index);
                update();
                return true;

            case "left":
                //index in group and check
                index = numPlayerInGroup((String) command.get("player"));
                if (index >= 0) left(index);
                update();
                return true;

            case "down":
                //index in group and check
                index = numPlayerInGroup((String) command.get("player"));
                if (index >= 0) down(index);
                update();
                return true;

            case "right":
                //index in group and check
                index = numPlayerInGroup((String) command.get("player"));
                if (index >= 0) right(index);
                update();
                return true;

        }
        return false;
    }

    public JSONObject groupJson() {
        JSONObject result = new JSONObject();
        JSONArray playerGroup = new JSONArray();
        result.put("json message", "data");
        result.put("type", "group");
        if (group.size() >= 1) {
            result.put("groupNull", "n");
            for (Player o : group) {
                JSONObject player = new JSONObject();
                player.put("name", o.getName());
                player.put("pos", o.getLocation().getX() + " " + o.getLocation().getY());
                playerGroup.add(player);
            }
            result.put("group", playerGroup);
            return result;
        }

        result.put("groupNull", "y");
        return result;
    }

    public Map getMap() {
        return map;
    }
}