package net.thoughtmachine.game.action;

import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Direction;
import net.thoughtmachine.model.Position;
import net.thoughtmachine.model.Ship;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Łukasz Kwasek on 18/12/2016.
 */
public class ShotActionTest {

    @Test
    public void sinkShipTest() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position position = new Position(0, 0, Direction.North);

        board.addShip(ship, position);

        assertEquals(1, board.getPositionMap().keySet().size());
        assertEquals(0, board.getSunkPositionMap().keySet().size());

        ShotAction action = new ShotAction(0, 0);
        action.execute(board);

        assertEquals(0, board.getPositionMap().keySet().size());
        assertEquals(1, board.getSunkPositionMap().keySet().size());

    }

    @Test
    public void missShotTest() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position position = new Position(0, 0, Direction.North);

        board.addShip(ship, position);

        assertEquals(1, board.getPositionMap().keySet().size());
        assertEquals(0, board.getSunkPositionMap().keySet().size());

        ShotAction action = new ShotAction(2, 2);
        action.execute(board);

        assertEquals(1, board.getPositionMap().keySet().size());
        assertEquals(0, board.getSunkPositionMap().keySet().size());

    }

    @Test
    public void shotOneOfTowShipsTest() throws Exception {

        Board board = new Board();

        Ship ship1 = new Ship();
        Position position1 = new Position(0, 0, Direction.North);
        board.addShip(ship1, position1);

        Ship ship2 = new Ship();
        Position position2 = new Position(2, 2, Direction.North);
        board.addShip(ship2, position2);

        assertEquals(2, board.getPositionMap().keySet().size());
        assertEquals(0, board.getSunkPositionMap().keySet().size());

        ShotAction action = new ShotAction(0, 0);
        action.execute(board);

        assertEquals(1, board.getPositionMap().keySet().size());
        assertEquals(1, board.getSunkPositionMap().keySet().size());

    }

}