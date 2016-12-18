package net.thoughtmachine.model;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class Board {

    private Ship[][] board;
    private Map<Ship, Position> positionMap;
    private int size;

    /**
     * Creates a 10x10 board.
     */
    public Board() {
        this(10);
    }

    /**
     * Creates a NxN board.
     *
     * @param size
     */
    public Board(int size) {

        Validate.isTrue(size > 0);

        this.size = size;

        this.positionMap = new LinkedHashMap<>();
        this.board = new Ship[size][size];

        for (int i = 0; i < size; i++) {
            this.board[i] = new Ship[10];
        }

    }

    /**
     * Given a coordinate, returns the ship. It returns null if no ship is on the coordinate.
     * Returns IllegalArgumentException if the coordinate are not valid.
     *
     * @param x
     * @param y
     * @return
     */
    public Ship getShip(int x, int y) {
        Validate.isTrue(isValidCoordinate(x, y), "Invalid destination coordinate (%s, %s)", x, y);
        return board[x][y];
    }

    /**
     * Checks if a ship exists on the given coordinate.
     * Returns IllegalArgumentException if the coordinate are not valid.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isShip(int x, int y) {
        Validate.isTrue(isValidCoordinate(x, y), "Invalid destination coordinate (%s, %s)", x, y);
        return getShip(x, y) != null;
    }

    /**
     * Checks if the ship is placed on the board.
     *
     * @param ship
     * @return
     */
    public boolean contains(Ship ship) {
        return positionMap.containsKey(ship);
    }

    /**
     * Places a ship at the given position.
     *
     * @param ship
     * @param position
     */
    public void addShip(Ship ship, Position position) {

        Validate.notNull(ship);
        Validate.notNull(position);

        int x = position.getX();
        int y = position.getY();
        Direction direction = position.getDirection();

        Validate.isTrue(!positionMap.containsKey(ship), "The board already contains this ship");
        Validate.isTrue(!isShip(x, y), "The board already contains a ship in position (%s, %s)", x, y);
        Validate.isTrue(isValidCoordinate(x, y), "Invalid destination coordinate (%s, %s)", x, y);
        Validate.notNull(direction, "A direction is mandatory");

        board[x][y] = ship;
        positionMap.put(
                ship,
                new Position(
                        x, y, direction
                )
        );
    }

    /**
     * Removes the ship from the board.
     *
     * @param ship
     * @return
     */
    public Position removeShip(Ship ship) {

        Position position = positionMap.remove(ship);

        int x = position.getX();
        int y = position.getY();

        board[x][y] = null;

        return position;
    }

    /**
     * It rotates the ship.
     *
     * @param ship
     * @param rotation
     * @return
     */
    public Position rotateShip(Ship ship, Rotation rotation) {

        Validate.notNull(ship);
        Validate.notNull(rotation);

        switch (rotation) {
            case Left:
                return rotateShipLeft(ship);
            case Right:
                return rotateShipRight(ship);
            default:
                throw new IllegalStateException();
        }

    }

    /**
     * Rotates the ship to the right.
     *
     * @param ship
     * @return
     */
    public Position rotateShipRight(Ship ship) {

        Validate.notNull(ship);

        Position position = positionMap.get(ship);

        Validate.notNull(position);

        position.setDirection(
                position.getDirection().rotateRight()
        );

        return position;
    }

    /**
     * Rotates the ship to the left.
     *
     * @param ship
     * @return
     */
    public Position rotateShipLeft(Ship ship) {

        Validate.notNull(ship);

        Position position = positionMap.get(ship);

        Validate.notNull(position);

        position.setDirection(
                position.getDirection().rotateLeft()
        );

        return position;
    }

    /**
     * Moves the ship forward.
     * Throws an IllegalStatesException if the destination position is invalid or already occupied by another ship.
     *
     * @param ship
     * @return
     */
    public Position moveShipForward(Ship ship) {

        Validate.notNull(ship);

        Position position = positionMap.get(ship);
        Direction direction = position.getDirection();

        Validate.notNull(direction);
        Validate.notNull(position);

        int x = position.getX();
        int y = position.getY();

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

        Validate.validState(isValidCoordinate(x, y), "Invalid destination coordinate (%s, %s)", x, y);
        Validate.validState(!isShip(x, y), "Invalid destination (%s, %s): there is already a ship there", x, y);

        board[position.getX()][position.getY()] = null;
        board[x][y] = ship;

        position.setTo(x, y, direction);

        return position;
    }

    /**
     * Returns the position of a ship, null if the ship doesn't exist on the board.
     *
     * @param ship
     * @return
     */
    public Position getShipPosition(Ship ship) {
        return positionMap.get(ship);
    }

    /**
     * Checks if the coordinate is valid. A valid coordinate falls in the board, and not outside.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    /**
     * Returns a map rappresenting the state of the board.
     *
     * @return
     */
    public Map<Ship, Position> getPositionMap() {
        return ImmutableMap.copyOf(positionMap);
    }

    /**
     * Returns board size.
     *
     * @return
     */
    public int getSize() {
        return size;
    }

}
