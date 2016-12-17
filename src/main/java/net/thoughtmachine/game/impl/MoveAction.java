package net.thoughtmachine.game.impl;

import net.thoughtmachine.game.IAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import net.thoughtmachine.utils.ActionUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class MoveAction implements IAction {

    private int x;
    private int y;

    public MoveAction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Board board) {

        Validate.notNull(board);

        Ship ship = ActionUtils.getUnsunkShip(board, x, y);

        Validate.isTrue(!ship.isSunk(), "Cannot move a sunk ship");

        board.moveShipForward(
                ship
        );

    }
}
