package game;

/**
 * old try to realization Cells
 *
 */

class Cell1 {
    private Boolean wall;
    private Boolean alfa;
    private Boolean dirt;
    private Boolean rook;


    Cell1(boolean walls) {
        this.wall = walls;
        this.alfa = false;
        this.dirt = false;
        this.rook = false;
    }

    Boolean getWall() {
        return wall;
    }

    void setWall(Boolean wall) {
        this.wall = wall;
    }

    Boolean getAlfa() {
        return alfa;
    }

    void setAlfa(Boolean alfa) {
        this.alfa = alfa;
    }

    Boolean getDirt() {
        return dirt;
    }

    void setDirt(Boolean dirt) {
        this.dirt = dirt;
    }

    Boolean getRook() {
        return rook;
    }

    void setRook(Boolean rook) {
        this.rook = rook;
    }

    void addWall() {
        this.wall = true;
        this.alfa = false;
        this.dirt = false;
        this.rook = false;
    }

    void addAlfa() {
        this.wall = false;
        this.alfa = true;
        this.dirt = false;
        this.rook = false;
    }

    void addDirt() {
        this.wall = false;
        this.alfa = false;
        this.dirt = true;
        this.rook = false;
    }

    void addRook() {
        this.wall = false;
        this.alfa = false;
        this.dirt = false;
        this.rook = true;
    }

    void setEmpty() {
        this.wall = false;
        this.alfa = false;
        this.dirt = false;
        this.rook = false;
    }

    Boolean isEmpty() {
        return !wall & !rook & !dirt & !alfa;
    }

}
