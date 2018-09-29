package game;

import java.io.FileNotFoundException;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        Map m = new Map(5, 6);
        System.out.println(m.mapSize());
        new FileWork().load(".//src//resurse//test1.map");
//        for (int j = 0; j < m.getHeight(); j++) {
//            for (int i = 0; i < m.getWidth(); i++) {
//                System.out.print(m.getCell(j, i).getWall());
//                System.out.print(" ");
//            }
//            System.out.println();
//        }

        m.reloadCell(1, 2, new Cell1(true));
        Cell1 d = new Cell1(false);
        d.addDirt();
        m.reloadCell(3, 2, d);
        d = new Cell1(false);
        d.addRook();
        m.reloadCell(3, 3, d);
        d = new Cell1(false);
        d.addDirt();
        m.reloadCell(3, 4, d);
        d = new Cell1(false);
        d.addAlfa();
        m.reloadCell(1, 4, d);

        Game game=new Game(m,new Point(1,4),1,new Point(0,2));
//        game.update();
        System.out.println();
    }
}
