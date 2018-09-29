package game.TypesCells;

import game.Map;
import game.OriginalCell;

public class Wall implements OriginalCell {

    private Boolean canMove;

    Wall(Boolean canMove){
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
