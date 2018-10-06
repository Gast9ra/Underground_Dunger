package game.TypesCells;

import game.Game;
import game.Map;
import game.OriginalCell;

public class EmptyCell implements OriginalCell {

    private Boolean canMove;

    public EmptyCell(){
        this.canMove=true;
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
        return "E";
        //return "Empty";
    }


}
