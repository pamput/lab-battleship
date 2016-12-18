package net.thoughtmachine.parser;

import net.thoughtmachine.exception.ExpectedTestException;
import net.thoughtmachine.exception.ParseException;
import net.thoughtmachine.game.action.MoveAction;
import net.thoughtmachine.game.action.ShipAction;
import net.thoughtmachine.game.action.ShotAction;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by Łukasz Kwasek on 18/12/2016.
 */
public class GameInputParserTest {

    @Test
    public void basicParseTest() throws Exception {

        InputStream in = this.getClass().getResourceAsStream("/input/basic.txt");

        GameInputParser parser = new GameInputParser();

        ParsedResult result = parser.parseInput(in);

        assertEquals(2, result.getBoard().getPositionMap().keySet().size());
        assertEquals(2, result.getActions().size());
        assertTrue(result.getActions().get(0) instanceof ShipAction);
        assertTrue(result.getActions().get(1) instanceof ShotAction);

        ShipAction shipAction = (ShipAction) result.getActions().get(0);
        assertEquals(6, shipAction.getActionList().size());

        assertEquals(MoveAction.Move, shipAction.getActionList().get(0));
        assertEquals(MoveAction.Right, shipAction.getActionList().get(1));
        assertEquals(MoveAction.Move, shipAction.getActionList().get(2));
        assertEquals(MoveAction.Left, shipAction.getActionList().get(3));
        assertEquals(MoveAction.Move, shipAction.getActionList().get(4));
        assertEquals(MoveAction.Move, shipAction.getActionList().get(5));

    }

    @Test
    public void oddSpacingTest() throws Exception {

        InputStream in = this.getClass().getResourceAsStream("/input/odd.txt");

        GameInputParser parser = new GameInputParser();

        ParsedResult result = parser.parseInput(in);

        assertEquals(2, result.getBoard().getPositionMap().keySet().size());
        assertEquals(2, result.getActions().size());
        assertTrue(result.getActions().get(0) instanceof ShipAction);
        assertTrue(result.getActions().get(1) instanceof ShotAction);

        ShipAction shipAction = (ShipAction) result.getActions().get(0);
        assertEquals(6, shipAction.getActionList().size());

        assertEquals(MoveAction.Move, shipAction.getActionList().get(0));
        assertEquals(MoveAction.Right, shipAction.getActionList().get(1));
        assertEquals(MoveAction.Move, shipAction.getActionList().get(2));
        assertEquals(MoveAction.Left, shipAction.getActionList().get(3));
        assertEquals(MoveAction.Move, shipAction.getActionList().get(4));
        assertEquals(MoveAction.Move, shipAction.getActionList().get(5));

    }

    @Test(expected = ExpectedTestException.class)
    public void wrongSyntax1Test() throws Exception {
        InputStream in = this.getClass().getResourceAsStream("/input/wrong1.txt");

        GameInputParser parser = new GameInputParser();

        try {
            parser.parseInput(in);
        } catch (ParseException e) {
            throw new ExpectedTestException(e);
        }
    }

    @Test(expected = ExpectedTestException.class)
    public void wrongSyntax2Test() throws Exception {
        InputStream in = this.getClass().getResourceAsStream("/input/wrong2.txt");

        GameInputParser parser = new GameInputParser();

        try {
            parser.parseInput(in);
        } catch (ParseException e) {
            throw new ExpectedTestException(e);
        }
    }

    @Test(expected = ExpectedTestException.class)
    public void wrongSyntax3Test() throws Exception {
        InputStream in = this.getClass().getResourceAsStream("/input/wrong3.txt");

        GameInputParser parser = new GameInputParser();

        try {
            parser.parseInput(in);
        } catch (ParseException e) {
            throw new ExpectedTestException(e);
        }
    }

    @Test(expected = ExpectedTestException.class)
    public void wrongNoSizeTest() throws Exception {
        InputStream in = this.getClass().getResourceAsStream("/input/no-size.txt");

        GameInputParser parser = new GameInputParser();

        try {
            parser.parseInput(in);
        } catch (ParseException e) {
            throw new ExpectedTestException(e);
        }
    }

    @Test(expected = ExpectedTestException.class)
    public void wrongNoShipsTest() throws Exception {
        InputStream in = this.getClass().getResourceAsStream("/input/no-ships.txt");

        GameInputParser parser = new GameInputParser();

        try {
            parser.parseInput(in);
        } catch (ParseException e) {
            throw new ExpectedTestException(e);
        }
    }

}