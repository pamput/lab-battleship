package net.thoughtmachine.model;

import net.thoughtmachine.exception.ExpectedTestException;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by Łukasz Kwasek on 18/12/2016.
 */
public class BoardTest {

    @Test
    public void addShip() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position position = new Position(0, 0, Direction.North);

        board.addShip(
                ship,
                position
        );

        assertTrue(board.contains(ship));
        assertTrue(board.isShip(0, 0));

        Ship shipOnBoard = board.getShip(0, 0);

        assertNotNull(shipOnBoard);
        assertEquals(ship, shipOnBoard);
        assertFalse(board.isSunkShip(ship));

    }

    @Test(expected = ExpectedTestException.class)
    public void addShipOutsideBoard() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position position = new Position(10, 10, Direction.North);

        try {
            board.addShip(
                    ship,
                    position
            );
        } catch (IllegalArgumentException e) {
            throw new ExpectedTestException();
        }

    }

    @Test
    public void getShip() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();

        board.addShip(
                ship,
                new Position(0, 0, Direction.North)
        );

        Ship shipOnBoard = board.getShip(0, 0);

        assertNotNull(shipOnBoard);
        assertEquals(ship, shipOnBoard);

    }

    @Test
    public void isShip() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position position = new Position(0, 0, Direction.North);

        board.addShip(ship, position);

        assertTrue(board.isShip(0, 0));
        assertFalse(board.isShip(1, 1));

    }

    @Test
    public void contains() throws Exception {

        Board board = new Board();

        Ship ship1 = new Ship();
        Position position1 = new Position(0, 0, Direction.North);

        Ship ship2 = new Ship();

        board.addShip(ship1, position1);

        assertTrue(board.contains(ship1));
        assertFalse(board.contains(ship2));

    }

    @Test
    public void removeShip() throws Exception {

        Board board = new Board();

        Ship ship = new Ship();
        Position position = new Position(0, 0, Direction.North);

        board.addShip(ship, position);

        assertTrue(board.contains(ship));

        board.removeShip(ship);

        assertFalse(board.contains(ship));

    }

    @Test
    public void moveShipTo() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position startingPosition = new Position(0, 0, Direction.North);

        board.addShip(ship, startingPosition);
        assertEquals(startingPosition, board.getShipPosition(ship));

        Position toPosition = new Position(5, 5, Direction.Est);
        board.moveShip(ship, toPosition);
        assertEquals(toPosition, board.getShipPosition(ship));
    }

    @Test(expected = ExpectedTestException.class)
    public void moveShipForwardOutsideBoard() throws Exception {

        Board board = new Board();
        Ship ship = new Ship();
        Position startingPosition = new Position(0, 0, Direction.North);

        board.addShip(ship, startingPosition);

        Position toPosition = new Position(11, 11, Direction.Est);

        try {
            board.moveShip(ship, toPosition);
        } catch (IllegalArgumentException e) {
            throw new ExpectedTestException(e);
        }

    }

    @Test(expected = ExpectedTestException.class)
    public void moveShipForwardOnOtherShip() throws Exception {

        Board board = new Board();
        Ship ship1 = new Ship();
        Position position1 = new Position(0, 0, Direction.North);
        board.addShip(ship1, position1);

        Ship ship2 = new Ship();
        Position position2 = new Position(0, 1, Direction.North);
        board.addShip(ship2, position2);

        try {
            board.moveShip(ship1, position2);
        } catch (IllegalArgumentException e) {
            throw new ExpectedTestException(e);
        }

    }

    @Test
    public void isValidCoordinate() throws Exception {

        Board board = new Board();

        // Test all valid coordinates
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                assertTrue(board.isValidCoordinate(x, y));
            }
        }

        // Test "some" invalid
        int[] xs = IntStream.concat(IntStream.range(-10, 0), IntStream.range(10, 20)).toArray();
        int[] ys = IntStream.concat(IntStream.range(-10, 0), IntStream.range(10, 20)).toArray();

        for (int x : xs) {
            for (int y : ys) {
                assertFalse(board.isValidCoordinate(x, y));
            }
        }

    }

    @Test
    public void getPositionMap() throws Exception {

        Board board = new Board();

        Ship ship1 = new Ship();
        Position position1 = new Position(0, 0, Direction.North);
        board.addShip(ship1, position1);

        Ship ship2 = new Ship();
        Position position2 = new Position(9, 9, Direction.South);
        board.addShip(ship2, position2);

        assertTrue(board.getPositionMap().containsKey(ship1));
        assertTrue(board.getPositionMap().get(ship1).equals(position1));

        assertTrue(board.getPositionMap().containsKey(ship2));
        assertTrue(board.getPositionMap().get(ship2).equals(position2));
    }

}