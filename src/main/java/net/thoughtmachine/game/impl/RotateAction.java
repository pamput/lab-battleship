package net.thoughtmachine.game.impl;

import net.thoughtmachine.game.IAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Rotation;
import net.thoughtmachine.model.Ship;
import net.thoughtmachine.utils.ActionUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class RotateAction implements IAction {

    private int x;
    private int y;
    private Rotation rotation;

    public RotateAction(int x, int y, Rotation rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    @Override
    public void execute(Board board) {

        Validate.notNull(board);

        Ship ship = ActionUtils.getUnsunkShip(board, x, y);

        Validate.isTrue(!ship.isSunk(), "Cannot rotate a sunk ship");

        board.rotateShip(
                ship,
                rotation
        );

    }
}
