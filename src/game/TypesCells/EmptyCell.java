package game.TypesCells;

import game.Map;
import game.OriginalCell;

public class EmptyCell implements OriginalCell {

    private Boolean canMove;

    EmptyCell(Boolean canMove){
        this.canMove=canMove;
    }

    @Override
    public Boolean getCanMove() {
        return canMove;
    }

    @Override
    public void update(Map card) {

    }
}
