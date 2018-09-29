package game;

class Point {
    private int x;
    private int y;

    Point(int X, int Y) {
        this.x = X;
        this.y = Y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
