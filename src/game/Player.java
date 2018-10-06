package game;

public class Player {

    private Point location;

    private String name;


    public Player(String name, int x, int y) {
        this.name = name;
        location = new Point(x, y);
    }


    public Player(String name) {
        location = new Point(1, 1);
        this.name = name;
    }


    public Boolean playerThisPoint(int x, int y) {
        return location.getX() == x & location.getY() == y;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void addOrdinate(int dx, int dy) {
        location.setX(location.getX() + dx);
        location.setY(location.getY() + dy);
    }

    public String getName() {
        return name;
    }

}
