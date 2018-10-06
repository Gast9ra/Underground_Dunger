package game;

import game.TypesCells.EmptyCell;
import game.TypesCells.Wall;

import java.util.ArrayList;

/**
 * class map he storage interface OriginalCells and have methods to use Map
 */

public class Map {

    private ArrayList<ArrayList<OriginalCell>> map = new ArrayList<>();

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
        //add cell in map
        for (int j = 0; numHeight > j; j++) {
            map.add(new ArrayList<OriginalCell>());

            for (int i = 0; numWidth > i; i++) {
                map.get(j).add(new EmptyCell());
            }
        }

        //border
        for (int i = 0; numWidth > i; i++) {
            //up and down border
            map.get(0).set(i, new Wall());
            map.get(numHeight - 1).set(i, new Wall());
        }
        for (int i = 0; numHeight > i; i++) {
            //left and right border
            map.get(i).set(0, new Wall());
            map.get(i).set(numWidth - 1, new Wall());
        }

    }


    public int mapSize() {
        return map.size();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public OriginalCell getCell(int height, int width) {
        return map.get(height).get(width);
    }

    public void reloadCell(int height, int width, OriginalCell cell) {
        map.get(height).set(width, cell);
    }

    public void printMap() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(map.get(j).get(i).getClassName());
                System.out.print(" ");
            }
            System.out.println();
        }
    }



}
