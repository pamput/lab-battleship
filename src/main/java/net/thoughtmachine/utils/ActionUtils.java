package net.thoughtmachine.utils;

import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.Validate;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class ActionUtils {

    public static Ship getUnsunkShip(Board board, int x, int y) {

        Validate.notNull(board);
        Validate.isTrue(board.isValidPosition(x, y), "Invalid coordinate (%s, %s)", x, y);
        Validate.isTrue(board.isShip(x, y), "There is no ship at coordinate (%s, %s)");

        return board.getShip(x, y);

    }

    public static Ship getShip(Board board, int x, int y) {

        Validate.notNull(board);
        Validate.isTrue(board.isValidPosition(x, y), "Invalid coordinate (%s, %s)", x, y);

        return board.getShip(x, y);

    }

}
