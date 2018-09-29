package game;

public class Game {

    private Map map;
    private Point robot;
    private int alfa;
    private Boolean exit = false;
    private Point possitionExit;

    public Game(Map data, Point robot, int alfa, Point exit) {
        this.map = data;
        this.robot = data.getRobot();
        this.alfa = data.getAlfa();
        this.possitionExit = data.getPossitionExit();
    }


    public void update() {
        //j=x i=y
        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                Cell1 cage = map.getCell(j, i);

                //go to all cell and use condition
                if (cage.getWall()) {
                    continue;
                } else {

                    if (cage.isEmpty()) continue;

                    if (cage.getDirt()) {
                        if (robot.getY() == i & robot.getX() == j) cage.setEmpty();
                        continue;
                    }

                    if (cage.getAlfa()) {
                        if (robot.getY() == i & robot.getX() == j) {
                            cage.setEmpty();
                            alfa--;
                            if (alfa == 0) exit = true;
                        }
                        continue;
                    }

                    if (cage.getRook()) {

                        if (map.getCell(j + 1, i).isEmpty()) {
                            cage.setEmpty();
                            map.getCell(j + 1, i).addRook();
                        }

                        //if right empty
                        if (map.getCell(j + 1, i + 1).isEmpty()
                                & map.getCell(j, i + 1).isEmpty()) {
                            cage.setEmpty();
                            map.getCell(j + 1, i + 1).addRook();

                        //if left empty
                        } else if (map.getCell(j - 1, i - 1).isEmpty()
                                & map.getCell(j, i - 1).isEmpty()) {
                            cage.setEmpty();
                            map.getCell(j - 1, i - 1).addRook();
                        }

                        continue;
                    }

                }
            }
        }


    }


}
