package game.TypesCells;

import game.Game;
import game.Map;
import game.OriginalCell;

public class EmptyCell implements OriginalCell {

    private boolean canMove;


    public EmptyCell() {
        this.canMove = true;
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
        return new NameCells().empty;
        //return "Empty";
    }


}
