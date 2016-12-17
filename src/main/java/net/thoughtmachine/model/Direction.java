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

    private static Direction goRight(Direction direction) {
        return order.get(
                order.inverse().get(direction) + 1 % 4
        );
    }

    private static Direction goLeft(Direction direction) {
        return order.get(
                order.inverse().get(direction) - 1 % 4
        );
    }

    public Direction goRight() {
        return Direction.goRight(this);
    }

    public Direction goLeft() {
        return Direction.goLeft(this);
    }

}
