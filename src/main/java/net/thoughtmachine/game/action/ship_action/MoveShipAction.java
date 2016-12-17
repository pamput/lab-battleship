package net.thoughtmachine.game.action.ship_action;

import net.thoughtmachine.game.IShipAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.Validate;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class MoveShipAction implements IShipAction {

    @Override
    public void execute(Ship ship, Board board) {

        Validate.notNull(board);

        Validate.isTrue(!ship.isSunk(), "Cannot move a sunk ship");

        board.moveShipForward(
                ship
        );

    }
}
