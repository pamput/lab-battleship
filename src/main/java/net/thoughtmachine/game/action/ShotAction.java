package net.thoughtmachine.game.action;

import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import net.thoughtmachine.utils.ActionUtils;
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

        Ship ship = ActionUtils.getShip(board, x, y);

        if (ship != null) {
            ship.setSunk(true);
        }

    }
}
