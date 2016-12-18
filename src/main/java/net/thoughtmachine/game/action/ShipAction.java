package net.thoughtmachine.game.action;

import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.game.IShipAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 *
 * A ship action group.
 *
 */
public class ShipAction implements IBoardAction {

    private int x;
    private int y;
    private List<IShipAction> actionList;

    public ShipAction(int x, int y, List<IShipAction> actionList) {
        this.x = x;
        this.y = y;
        this.actionList = actionList;
    }

    @Override
    public void execute(Board board) {

        Validate.notNull(board);
        Validate.notNull(actionList);

        Validate.isTrue(board.isShip(x, y), "There is no ship at coordinate (%s, %s)", x, y);
        Ship ship = board.getShip(x, y);

        for (IShipAction action : actionList) {
            action.execute(ship, board);
        }
    }
}
