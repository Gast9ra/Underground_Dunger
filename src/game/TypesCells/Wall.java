package game.TypesCells;
import game.Game;
import game.Map;
import game.OriginalCell;

public class Wall implements OriginalCell {

    private Boolean canMove;


    //private Map map;

    public Wall(){
        this.canMove=false;
    }


    @Override
    public boolean getCanMove() {
        return canMove;
    }

    @Override
    public void update() {

    }

    @Override
    public String getClassName() {
        return "W";
        //return "Wall";
    }


}
