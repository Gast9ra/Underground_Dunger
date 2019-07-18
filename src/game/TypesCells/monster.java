package game.TypesCells;

import game.*;

public class Monster implements OriginalCell {
    private boolean canMove;
    private Game game;
    private Map map;
    private Point position;
    private int damage = 15;

    public Monster(Game game, Point position) {
        this.canMove = true;
        this.game = game;
        this.map = game.getMap();
        this.position = position;
    }

    @Override
    public boolean getCanMove() {
        return canMove;
    }

    @Override
    public void update() {
        for (Player player : game.getGroup()) {
            if (player.getLocation().equals(position)) {
                player.setDamage(damage);
                map.reloadCell(position.getX(), position.getY(), new EmptyCell());
            }
        }
    }

    @Override
    public String getClassName() {
        return "M";

    }
}
