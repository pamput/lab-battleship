package net.thoughtmachine.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Łukasz Kwasek on 18/12/2016.
 */
public class DirectionTest {
    @Test
    public void valueOf() throws Exception {

        assertEquals(Direction.North, Direction.valueOf('N'));
        assertEquals(Direction.West, Direction.valueOf('W'));
        assertEquals(Direction.South, Direction.valueOf('S'));
        assertEquals(Direction.Est, Direction.valueOf('E'));

    }

    @Test
    public void rotateRight() throws Exception {

        Direction direction = Direction.North;

        direction = direction.rotateRight();
        assertEquals(Direction.Est, direction);

        direction = direction.rotateRight();
        assertEquals(Direction.South, direction);

        direction = direction.rotateRight();
        assertEquals(Direction.West, direction);

        direction = direction.rotateRight();
        assertEquals(Direction.North, direction);

        direction = direction.rotateRight();
        assertEquals(Direction.Est, direction);

        direction = direction.rotateRight();
        assertEquals(Direction.South, direction);

        direction = direction.rotateRight();
        assertEquals(Direction.West, direction);

        direction = direction.rotateRight();
        assertEquals(Direction.North, direction);

    }

    @Test
    public void rotateLeft() throws Exception {

        Direction direction = Direction.North;

        direction = direction.rotateLeft();
        assertEquals(Direction.West, direction);

        direction = direction.rotateLeft();
        assertEquals(Direction.South, direction);

        direction = direction.rotateLeft();
        assertEquals(Direction.Est, direction);

        direction = direction.rotateLeft();
        assertEquals(Direction.North, direction);

        direction = direction.rotateLeft();
        assertEquals(Direction.West, direction);

        direction = direction.rotateLeft();
        assertEquals(Direction.South, direction);

        direction = direction.rotateLeft();
        assertEquals(Direction.Est, direction);

        direction = direction.rotateLeft();
        assertEquals(Direction.North, direction);

    }

    @Test
    public void toChar() throws Exception {

        assertEquals('N', Direction.North.toChar());
        assertEquals('W', Direction.West.toChar());
        assertEquals('S', Direction.South.toChar());
        assertEquals('E', Direction.Est.toChar());

    }

}