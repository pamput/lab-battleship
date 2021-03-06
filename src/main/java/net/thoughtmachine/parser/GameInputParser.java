package net.thoughtmachine.parser;

import com.google.common.base.CharMatcher;
import net.thoughtmachine.exception.ParseException;
import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.game.action.MoveAction;
import net.thoughtmachine.game.action.ShipAction;
import net.thoughtmachine.game.action.ShotAction;
import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Direction;
import net.thoughtmachine.model.Position;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class GameInputParser {

    private final Pattern COMMENT = Pattern.compile(
            "//.*$"
    );

    private final Pattern SHIP_PLACEMENT_LINE = Pattern.compile(
            "(\\s*\\(\\s*([0-9]+)\\s*,\\s*([0-9]+)\\s*,\\s*([ENSW])\\s*\\)\\s*)+"
    );

    private final Pattern SHIP_PLACEMENT_GROUP = Pattern.compile(
            "\\s*\\(\\s*([0-9]+)\\s*,\\s*([0-9]+)\\s*,\\s*([ENSW])\\s*\\)\\s*"
    );

    private final Pattern ACTION_LINE = Pattern.compile(
            "\\s*\\(\\s*([0-9]+)\\s*,\\s*([0-9]+)\\s*\\)\\s*([LRM\\s]*)\\s*"
    );

    /**
     * Returns the parse of the input stream.
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public ParsedResult parseInput(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String strSize = readLine(reader);
        if (!StringUtils.isNumeric(strSize))
            throw new ParseException("Could not find the board size in a valid format");

        String strShips = readLine(reader);

        List<String> strActionList = new ArrayList<>();

        String line;
        while ((line = readLine(reader)) != null) {
            strActionList.add(line);
        }

        return new ParsedResult(
                createBoard(strSize, strShips),
                createActionList(strActionList)
        );

    }

    /**
     * Creates a board object.
     *
     * @param strSize  the board size
     * @param strShips the ship placement string
     * @return
     */
    private Board createBoard(String strSize, String strShips) {

        Validate.notBlank(strSize);
        Validate.notBlank(strShips);

        strSize = trim(strSize);
        strShips = trim(strShips);

        Board board = new Board(
                Integer.parseInt(trim(strSize))
        );

        Matcher shipLineMatcher = SHIP_PLACEMENT_LINE.matcher(strShips);

        if (!shipLineMatcher.matches()) throw new ParseException("Cannot parse ship placement line");

        Matcher shipGroupMatcher = SHIP_PLACEMENT_GROUP.matcher(strShips);
        while (shipGroupMatcher.find()) {

            int x = Integer.parseInt(shipGroupMatcher.group(1));
            int y = Integer.parseInt(shipGroupMatcher.group(2));
            Direction d = Direction.valueOf(shipGroupMatcher.group(3).charAt(0));

            board.addShip(
                    new Ship(),
                    new Position(
                            x, y, d
                    )
            );

        }

        return board;
    }

    /**
     * Given a list of command lines to parse, it returns a list of actions.
     *
     * @param strActionList
     * @return
     */
    private List<IBoardAction> createActionList(List<String> strActionList) {

        List<IBoardAction> actionList = new ArrayList<>();

        for (String strAction : strActionList) {
            actionList.add(
                    createAction(strAction)
            );
        }

        return actionList;

    }

    /**
     * Given a command, returns an action.
     *
     * @param strAction
     * @return
     */
    private IBoardAction createAction(String strAction) {
        Matcher matcher = ACTION_LINE.matcher(
                trim(strAction).toUpperCase()
        );

        if (!matcher.matches()) throw new ParseException("Ship action in unsupported format: %s", trim(strAction));

        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        String actions = matcher.group(3);

        if (StringUtils.isBlank(actions)) {

            return new ShotAction(x, y);

        } else {

            List<MoveAction> shipActionList = new ArrayList<>();

            for (char c : cleanSpaces(actions).toCharArray()) {
                switch (c) {
                    case 'L':
                        shipActionList.add(MoveAction.Left);
                        break;
                    case 'R':
                        shipActionList.add(MoveAction.Right);
                        break;
                    case 'M':
                        shipActionList.add(MoveAction.Move);
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }

            return new ShipAction(x, y, shipActionList);
        }
    }

    /**
     * This method read a line from the reader. It also:
     * <p>
     * - Skips empty lines
     * - Deletes comments
     * - Trims the line
     *
     * @param reader
     * @return
     * @throws IOException
     */
    private String readLine(BufferedReader reader) throws IOException {
        Validate.notNull(reader);

        String line;
        while ((line = reader.readLine()) != null) {

            line = trim(skipComments(line));

            if (StringUtils.isNotBlank(line)) {
                return line;
            }
        }

        return null;
    }

    /**
     * Returns the input string without comments.
     *
     * @param string
     * @return
     */
    private String skipComments(String string) {
        return COMMENT.matcher(string).replaceAll("");
    }

    /**
     * Returns the input string with head and tail whitespaces removed.
     *
     * @param string
     * @return
     */
    private String trim(String string) {
        return StringUtils.trim(string);
    }

    /**
     * Returns the input string with ALL the whitespaces removed.
     *
     * @param string
     * @return
     */
    private String cleanSpaces(String string) {
        return CharMatcher.whitespace().removeFrom(string);
    }

}
