package net.thoughtmachine.game.action;

import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.Validate;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 * <p>
 * A shot action.
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

        Ship ship = board.getShip(x, y);

        if (ship != null) {
            board.sinkShip(ship);
        }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
