package net.thoughtmachine.game.action;

import com.google.common.collect.Lists;
import net.thoughtmachine.exception.ExpectedTestException;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Direction;
import net.thoughtmachine.model.Position;
import net.thoughtmachine.model.Ship;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Łukasz Kwasek on 18/12/2016.
 */

public class ShipActionTest {

    @Test
    public void moveShipTest() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position startingPosition = new Position(0, 0, Direction.North);

        board.addShip(ship, startingPosition);

        ShipAction action = new ShipAction(
                0,
                0,
                Lists.newArrayList(
                        MoveAction.Move,
                        MoveAction.Move
                )
        );

        action.execute(board);

        Position expectedPosition = new Position(0, 2, Direction.North);
        assertEquals(expectedPosition, board.getShipPosition(ship));

    }

    @Test
    public void advancedMoveShipTest() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position startingPosition = new Position(0, 0, Direction.North);

        board.addShip(ship, startingPosition);

        ShipAction action = new ShipAction(
                0,
                0,
                Lists.newArrayList(
                        MoveAction.Move,
                        MoveAction.Move,
                        MoveAction.Right,
                        MoveAction.Move,
                        MoveAction.Left,
                        MoveAction.Move,
                        MoveAction.Move,
                        MoveAction.Left
                )
        );

        action.execute(board);

        Position expectedPosition = new Position(1, 4, Direction.West);
        assertEquals(expectedPosition, board.getShipPosition(ship));

    }

    @Test
    public void moveOverShipTest() throws Exception {

        Board board = new Board();

        Ship ship1 = new Ship();
        Position position1 = new Position(0, 0, Direction.North);
        board.addShip(ship1, position1);

        Ship ship2 = new Ship();
        Position position2 = new Position(0, 1, Direction.North);
        board.addShip(ship2, position2);

        ShipAction action = new ShipAction(
                0,
                0,
                Lists.newArrayList(
                        MoveAction.Move,
                        MoveAction.Move
                )
        );

        action.execute(board);

        Position expectedPosition = new Position(0, 2, Direction.North);
        assertEquals(expectedPosition, board.getShipPosition(ship1));

    }

    @Test(expected = ExpectedTestException.class)
    public void moveOnShipTest() throws Exception {

        Board board = new Board();

        Ship ship1 = new Ship();
        Position position1 = new Position(0, 0, Direction.North);
        board.addShip(ship1, position1);

        Ship ship2 = new Ship();
        Position position2 = new Position(0, 1, Direction.North);
        board.addShip(ship2, position2);

        ShipAction action = new ShipAction(
                0,
                0,
                Lists.newArrayList(
                        MoveAction.Move
                )
        );

        try {
            action.execute(board);
        } catch (IllegalArgumentException e) {
            throw new ExpectedTestException(e);
        }

    }

    @Test
    public void moveOnSunkShipTest() throws Exception {

        Board board = new Board();

        Ship ship1 = new Ship();
        Position position1 = new Position(0, 0, Direction.North);
        board.addShip(ship1, position1);

        Ship ship2 = new Ship();
        Position position2 = new Position(0, 1, Direction.North);
        board.addShip(ship2, position2);
        board.sinkShip(ship2);

        ShipAction action = new ShipAction(
                0,
                0,
                Lists.newArrayList(
                        MoveAction.Move
                )
        );

        action.execute(board);

        Position expectedPosition = new Position(0, 1, Direction.North);
        assertEquals(expectedPosition, board.getShipPosition(ship1));

    }

    @Test(expected = ExpectedTestException.class)
    public void moveShipOutsideBoardTest() throws Exception {

        Board board = new Board();

        Ship ship = new Ship();
        Position position = new Position(0, 0, Direction.North);
        board.addShip(ship, position);

        ShipAction action = new ShipAction(
                0,
                0,
                Lists.newArrayList(
                        MoveAction.Left,
                        MoveAction.Move
                )
        );

        try {
            action.execute(board);
        } catch (IllegalStateException e) {
            throw new ExpectedTestException(e);
        }

    }

}