package game.TypesCells;

import game.*;

public class Monster implements OriginalCell {
    private Game game;
    private Map map;
    private Point position;
    private int damage = 15;

    public Monster(Game game, Point position) {
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
//                System.out.println("true");
                player.setDamage(damage);
                System.out.println(player.getHp());
              //  map.reloadCell(position.getX(), position.getY(), new EmptyCell());
            }
        }
    }

    @Override
    public String getClassName() {
        return "џ";

    }
}
