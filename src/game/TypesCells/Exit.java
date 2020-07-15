package game.TypesCells;

import game.*;

public class Exit implements OriginalCell {
    private Game game;
    private Map map;
    private Point position;
    private boolean win=false;


    public Exit(Game game, Point position) {
        this.game = game;
        this.map = game.getMap();
        this.position = position;
    }


    @Override
    public boolean getCanMove() {
        return true;
    }

    @Override
    public void update() {
        for (Player player : game.getGroup()) {
            if (player.getLocation().getX() == position.getX() &&
                    player.getLocation().getY() == position.getY()) {
                win=true;
                System.out.println("Player "+player.getName()+" win");

            }
        }

    }

    @Override
    public String getClassName() {
        return "◘";
    }
}
