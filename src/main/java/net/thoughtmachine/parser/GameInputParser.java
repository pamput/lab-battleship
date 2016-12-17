package net.thoughtmachine.parser;

import com.google.common.base.CharMatcher;
import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.game.IShipAction;
import net.thoughtmachine.game.action.ShipAction;
import net.thoughtmachine.game.action.ShotAction;
import net.thoughtmachine.game.action.ship_action.MoveShipAction;
import net.thoughtmachine.game.action.ship_action.RotateShipAction;
import net.thoughtmachine.model.*;
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
            "([\\s]*\\([\\s]*([0-9]+)[\\s]*,[\\s]*([0-9]+)[\\s]*,[\\s]*([ENSW])[\\s]*\\)[\\s]*)+"
    );

    private final Pattern SHIP_PLACEMENT_GROUP = Pattern.compile(
            "[\\s]*\\([\\s]*([0-9]+)[\\s]*,[\\s]*([0-9]+)[\\s]*,[\\s]*([ENSW])[\\s]*\\)[\\s]*"
    );

    private final Pattern ACTION_LINE = Pattern.compile(
            "[\\s]*\\([\\s]*([0-9]+)[\\s]*,[\\s]*([0-9]+)[\\s]*\\)[\\s]*([LRM\\s]*)[\\s]*"
    );

    public ParsedResult parseInput(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String strSize = readLine(reader);
        Validate.isTrue(StringUtils.isNumeric(strSize), "Could not find the board size in a valid format");

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

    private Board createBoard(String strSize, String strShips) {

        Validate.notBlank(strSize);
        Validate.notBlank(strShips);

        strSize = trim(strSize);
        strShips = trim(strShips);

        Board board = new Board(
                Integer.parseInt(trim(strSize))
        );

        Matcher shipLineMatcher = SHIP_PLACEMENT_LINE.matcher(strShips);

        Validate.isTrue(shipLineMatcher.matches(), "Cannot parse ship placement line");

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

    private List<IBoardAction> createActionList(List<String> strActionList) {

        List<IBoardAction> actionList = new ArrayList<>();

        for (String strAction : strActionList) {

            Matcher matcher = ACTION_LINE.matcher(
                    trim(strAction).toUpperCase()
            );

            Validate.isTrue(matcher.matches(), "Ship action in unsupported format: %s", trim(strAction));

            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            String actions = matcher.group(3);

            if (StringUtils.isBlank(actions)) {

                actionList.add(
                        new ShotAction(x, y)
                );

            } else {

                List<IShipAction> shipActionList = new ArrayList<>();

                for (char c : cleanSpaces(actions).toCharArray()) {
                    switch (c) {
                        case 'L':
                            shipActionList.add(new RotateShipAction(Rotation.Left));
                            break;
                        case 'R':
                            shipActionList.add(new RotateShipAction(Rotation.Right));
                            break;
                        case 'M':
                            shipActionList.add(new MoveShipAction());
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }

                actionList.add(
                        new ShipAction(x, y, shipActionList)
                );
            }

        }

        return actionList;

    }

    private String readLine(BufferedReader reader) throws IOException {
        Validate.notNull(reader);

        String line;
        while ((line = reader.readLine()) != null) {
            if (StringUtils.isNotBlank(line)) {
                return trim(skipComments(line));
            }
        }

        return null;
    }

    private String skipComments(String string) {
        return COMMENT.matcher(string).replaceAll("");
    }

    private String trim(String string) {
        return StringUtils.trim(string);
    }

    private String cleanSpaces(String string) {
        return CharMatcher.whitespace().removeFrom(string);
    }

}
