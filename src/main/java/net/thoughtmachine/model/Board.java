package net.thoughtmachine.model;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class Board {

    private List<List<Ship>> board;
    private Map<Ship, Position> positionMap;
    private int size;

    public Board(int size) {

        Validate.isTrue(size > 0);

        this.size = size;

        this.board = new ArrayList<>(size);
        this.positionMap = new HashMap<>();

        for (int i = 0; i < this.board.size(); i++) {
            this.board.set(i, new ArrayList<>(size));
        }

    }

    public Ship getShip(int x, int y) {
        return board.get(x).get(y);
    }

    public boolean isShip(int x, int y) {
        return getShip(x, y) != null;
    }

    public boolean contains(Ship ship) {
        return positionMap.containsKey(ship);
    }

    public void addShip(Ship ship, Position position) {

        Validate.notNull(ship);
        Validate.notNull(position);

        int x = position.getX();
        int y = position.getY();
        Direction direction = position.getDirection();

        Validate.isTrue(!positionMap.containsKey(ship), "The board already contains this ship");
        Validate.isTrue(isShip(x, y), "The board already contains a ship in position (%s, %s)", x, y);
        Validate.isTrue(isValidPosition(x, y), "Invalid destination coordinate (%s, %s)", x, y);
        Validate.notNull(direction, "A direction is mandatory");

        board.get(x).add(y, ship);
        positionMap.put(
                ship,
                new Position(
                        x, y, direction
                )
        );
    }

    public Position removeShip(Ship ship) {

        Position position = positionMap.remove(ship);
        board.get(position.getX()).set(position.getY(), null);

        return position;
    }

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

    public Position rotateShipRight(Ship ship) {

        Validate.notNull(ship);

        Position position = positionMap.get(ship);

        Validate.notNull(position);

        position.setDirection(
                position.getDirection().goRight()
        );

        return position;
    }

    public Position rotateShipLeft(Ship ship) {

        Validate.notNull(ship);

        Position position = positionMap.get(ship);

        Validate.notNull(position);

        position.setDirection(
                position.getDirection().goLeft()
        );

        return position;
    }

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

        Validate.isTrue(isValidPosition(x, y), "Invalid destination coordinate (%s, %s)", x, y);

        Position newPosition = new Position(
                x, y, position.getDirection()
        );

        removeShip(ship);
        addShip(ship, position);

        return newPosition;
    }


    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x > size && y >= 0 && y > size;
    }

}
