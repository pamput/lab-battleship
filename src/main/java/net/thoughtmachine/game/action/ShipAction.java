package net.thoughtmachine.game.action;

import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Direction;
import net.thoughtmachine.model.Position;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 * <p>
 * A ship action group. This action will return an exception if any action is attempted on a sunk ship.
 */
public class ShipAction implements IBoardAction {

    private int x;
    private int y;
    private List<MoveAction> actionList;

    public ShipAction(int x, int y, List<MoveAction> actionList) {
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

        Validate.notNull(ship);

        Position position = board.getShipPosition(ship).clone();
        Validate.notNull(position);

        for (MoveAction action : actionList) {

            switch (action) {
                case Move:
                    move(board, position);
                    break;
                case Left:
                    left(position);
                    break;
                case Right:
                    right(position);
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Could not process action: %s", action));
            }

        }

        board.moveShip(ship, position);
    }

    private void right(Position position) {
        position.setDirection(
                position.getDirection().rotateRight()
        );
    }

    private void left(Position position) {
        position.setDirection(
                position.getDirection().rotateLeft()
        );
    }

    private void move(Board board, Position position) {

        Validate.notNull(position);
        Validate.notNull(board);

        int x = position.getX();
        int y = position.getY();
        Direction direction = position.getDirection();

        Validate.notNull(direction);

        switch (direction) {
            case North:
                y++;
                break;
            case Est:
                x++;
                break;
            case South:
                y--;
                break;
            case West:
                x--;
                break;
        }

        Validate.validState(board.isValidCoordinate(x, y), "Invalid destination coordinate (%s, %s)", x, y);
        position.setX(x);
        position.setY(y);
    }
}
