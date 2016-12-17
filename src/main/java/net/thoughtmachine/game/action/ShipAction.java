package net.thoughtmachine.game.action;

import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.game.IShipAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import net.thoughtmachine.utils.ActionUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
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

        Ship ship = ActionUtils.getShip(board, x, y);

        for (IShipAction action : actionList) {
            action.execute(ship, board);
        }
    }
}
