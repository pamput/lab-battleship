package net.thoughtmachine.model;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public enum Direction {
    North, Est, South, West;

    private static BiMap<Integer, Direction> order;

    static {
        order = HashBiMap.create();

        for (int i = 0; i < Direction.values().length; i++) {
            order.put(i, Direction.values()[i]);
        }
    }

    /**
     * Returns the direction you would face if rotating right.
     *
     * @param direction
     * @return
     */
    private static Direction rotateRight(Direction direction) {
        return order.get(
                (order.inverse().get(direction) + 1) % 4
        );
    }

    /**
     * Returns the direction you would face if rotating left.
     *
     * @param direction
     * @return
     */
    private static Direction rotateLeft(Direction direction) {
        return order.get(
                (order.inverse().get(direction) + 3) % 4
        );
    }

    /**
     * Returns the Direction given its (not case-sensible) initial.
     *
     * @param c
     * @return
     */
    public static Direction valueOf(char c) {
        c = Character.toUpperCase(c);

        switch (c) {
            case 'N':
                return North;
            case 'E':
                return Est;
            case 'S':
                return South;
            case 'W':
                return West;
            default:
                return null;
        }
    }

    /**
     * Returns the direction you would face if rotating right.
     *
     * @return
     */
    public Direction rotateRight() {
        return Direction.rotateRight(this);
    }

    /**
     * Returns the direction you would face if rotating left.
     *
     * @return
     */
    public Direction rotateLeft() {
        return Direction.rotateLeft(this);
    }

    /**
     * Returns direction's uppercase initial.
     *
     * @return
     */
    public char toChar() {
        return this.toString().charAt(0);
    }

}
