package net.thoughtmachine.game.action;

import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.Validate;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class ShotAction implements IBoardAction {

    private int x;
    private int y;

    public ShotAction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Board board) {

        Validate.notNull(board);

        Validate.isTrue(board.isShip(x, y), "There is no ship at coordinate (%s, %s)", x, y);
        Ship ship = board.getShip(x, y);

        if (ship != null) {
            ship.setSunk(true);
        }

    }
}
