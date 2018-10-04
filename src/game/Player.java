package game;

public class Player {

    private Point location;

    public Player(int x, int y){
        location=new Point(x,y);
    }

    public Player(){
        location=new Point(0,0);
    }

    public Boolean playerThisPoint(int x, int y){
        return location.getX()==x & location.getY()==y;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

}
