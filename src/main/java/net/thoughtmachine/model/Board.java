package net.thoughtmachine.model;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 * <p>
 * The board entity. The board can have any square size. The size has to be greater than 0. Only one ship can exists
 * on each square. Once a ship has been sunk, cannot be un-sunk. A sunk ship cannot be moved. A sunk ship can be removed
 * and added again to the board.
 */
public class Board {

    private Ship[][] board;
    private Map<Ship, Position> positionMap;
    private Map<Ship, Position> sunkPositionMap;
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
        this.sunkPositionMap = new LinkedHashMap<>();
        this.board = new Ship[size][size];

        for (int i = 0; i < size; i++) {
            this.board[i] = new Ship[10];
        }

    }

    /**
     * Given a coordinate, returns the un-sunk ship. It returns null if no un-sunk ship is on the coordinate.
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
        return positionMap.containsKey(ship) || sunkPositionMap.containsKey(ship);
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

        if (isSunkShip(ship)) {
            sinkShip(ship);
        }

    }

    public boolean isSunkShip(Ship ship) {
        return sunkPositionMap.containsKey(ship);
    }

    public void sinkShip(Ship ship) {

        Validate.isTrue(contains(ship), "The board doesn't contain this ship");

        if (!isSunkShip(ship)) {
            sunkPositionMap.put(ship, positionMap.remove(ship));
        }

    }

    /**
     * Remove a sunk ship from the board. Returns the position at which the ship was sunk.
     *
     * @param ship
     * @return
     */
    public Position removeSunkShip(Ship ship) {
        return sunkPositionMap.remove(ship);
    }

    /**
     * Returns the position of a sunk ship. If the ship hasn't been sunk or is not on the board, the result will be null.
     *
     * @param ship
     * @return
     */
    public Position getSunkShipPosition(Ship ship) {
        return sunkPositionMap.get(ship);
    }

    /**
     * Removes the ship from the board. Returns null if the ship is not placed anywhere.
     *
     * @param ship
     * @return
     */
    public Position removeShip(Ship ship) {

        Position position = positionMap.remove(ship);

        if (position != null) {
            int x = position.getX();
            int y = position.getY();

            board[x][y] = null;
        }

        return position;
    }

    /**
     * Moves the ship to the designated position. It return the previous ship position.
     *
     * @param ship
     * @return
     */
    public Position moveShip(Ship ship, Position to) {

        int x = to.getX();
        int y = to.getY();
        Direction direction = to.getDirection();

        Validate.notNull(direction);
        Validate.isTrue(isValidCoordinate(x, y), "Invalid coordinate (%s, %s)", x, y);
        Validate.isTrue(!isShip(x, y), "There is already a ship on coordinate (%s, %s)", x, y);
        Validate.validState(!isSunkShip(ship), "Cannot move sunk ship");
        Validate.validState(positionMap.containsKey(ship));

        return positionMap.put(ship, to);
    }

    /**
     * Returns the position of a ship, null if the ship doesn't exist on the board.
     *
     * @param ship
     * @return
     */
    public Position getShipPosition(Ship ship) {
        return positionMap.get(ship).clone();
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
     * Returns a map representing the state of the un-sunk ships on the board.
     *
     * @return
     */
    public Map<Ship, Position> getPositionMap() {
        return ImmutableMap.copyOf(positionMap);
    }

    /**
     * Returns a map representing the state of the un-sunk ships on the board.
     *
     * @return
     */
    public Map<Ship, Position> getSunkPositionMap() {
        return ImmutableMap.copyOf(sunkPositionMap);
    }

    /**
     * Returns the board size.
     *
     * @return
     */
    public int getSize() {
        return size;
    }
}
