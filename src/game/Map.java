package game;

import game.TypesCells.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * class map he storage interface OriginalCells and have methods to use Map
 */

public class Map {

    private ArrayList<ArrayList<OriginalCell>> mapMas = new ArrayList<>();

    private int height;
    private int width;


    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        generate(height, width);
    }


    /**
     * generate empty Map with border
     *
     * @param numHeight
     * @param numWidth
     */
    private void generate(int numHeight, int numWidth) {
        //add cell in mapMas
        for (int j = 0; numHeight > j; j++) {
            mapMas.add(new ArrayList<OriginalCell>());

            for (int i = 0; numWidth > i; i++) {
                mapMas.get(j).add(new EmptyCell());
            }
        }

        //border
        for (int i = 0; numWidth > i; i++) {
            //up and down border
            mapMas.get(0).set(i, new Wall());
            mapMas.get(numHeight - 1).set(i, new Wall());
        }
        for (int i = 0; numHeight > i; i++) {
            //left and right border
            mapMas.get(i).set(0, new Wall());
            mapMas.get(i).set(numWidth - 1, new Wall());
        }

    }


    public int mapSize() {
        return mapMas.size();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public OriginalCell getCell(int height, int width) {
        return mapMas.get(height).get(width);
    }

    public void reloadCell(int height, int width, OriginalCell cell) {
        mapMas.get(height).set(width, cell);
    }

    public void printMap() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(mapMas.get(j).get(i).getClassName());
                System.out.print(" ");
            }
            System.out.println();
        }
    }


    public JSONObject jsonMap() {
        JSONObject result = new JSONObject();
        result.put("json-message","data");
        result.put("height", height);
        result.put("width", width);
        for (int j = 0; j < height; j++) {

            JSONArray cells = new JSONArray();

            for (int i = 0; i < width; i++) {
                cells.add(mapMas.get(j).get(i).getClassName());
            }
            result.put("column" + j, cells);
        }

        return result;
    }



}
