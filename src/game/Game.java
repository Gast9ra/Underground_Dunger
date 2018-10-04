package game;

public class Game {

    private Map map;


    public Game(Map data) {
        this.map = data;
    }


    public void update() {

        //j=x i=y
        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                map.getCell(j,i).update(map);


            }
        }

    }

    public void printMap(){
        map.printMap();
    }

}
