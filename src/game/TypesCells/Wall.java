package game.TypesCells;

import game.Game;
import game.Map;
import game.OriginalCell;

public class Wall implements OriginalCell {

    private Boolean canMove;

    public Wall(){
        this.canMove=false;
    }


    @Override
    public Boolean getCanMove() {
        return canMove;
    }

    @Override
    public void update(Map card) {

    }

    @Override
    public String getClassName() {
        return "Wall";
    }


}
