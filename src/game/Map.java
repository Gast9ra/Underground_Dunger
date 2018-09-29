package game;

import java.util.ArrayList;

/**
 * class map he storage class cell and have methods to use Map
 */

public class Map {

    private ArrayList<ArrayList<Cell1>> map = new ArrayList<>();

    private int height;
    private int width;
    private Point robot;
    private int alfa;
    private Point possitionExit;



    public Map(int sizeHeight, int sizeWidth) {
        this.height=sizeHeight;
        this.width=sizeWidth;
        generate(sizeHeight, sizeWidth);
    }



    /**
     *  generate empty Map with border
     *
     * @param numHeight
     * @param numWidth
     */
    private void generate(int numHeight, int numWidth) {
        //add cell in map
        for (int j = 0; numHeight > j; j++) {
            map.add(new ArrayList<Cell1>());

            for (int i = 0; numWidth > i; i++) {
                map.get(j).add(new Cell1(false));
            }
        }

        //border
        for (int i = 0; numWidth > i; i++) {
            //up and down border
            map.get(0).get(i).setWall(true);
            map.get(numHeight - 1).get(i).setWall(true);
        }
        for (int i=0; numHeight>i; i++){
            //left and right border
            map.get(i).get(0).setWall(true);
            map.get(i).get(numWidth-1).setWall(true);
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

    public Cell1 getCell(int height, int width) {
        return map.get(height).get(width);
    }

    public void reloadCell(int height, int width, Cell1 cellule) {
        map.get(height).set(width, cellule);
    }

    public Point getRobot() {
        return robot;
    }

    public void setRobot(Point robot) {
        this.robot = robot;
    }

    public int getAlfa() {
        return alfa;
    }

    public void setAlfa(int alfa) {
        this.alfa = alfa;
    }

    public Point getPossitionExit() {
        return possitionExit;
    }

    public void setPossitionExit(Point possitionExit) {
        this.possitionExit = possitionExit;
    }

}
