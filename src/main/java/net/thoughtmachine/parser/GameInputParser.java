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

    private final Pattern SHIP_PLACEMENT_LINE = Pattern.compile("(\\([0-9,ENSW]+\\))+");
    private final Pattern SHIP_GROUP = Pattern.compile("\\(([0-9]+),([0-9]+),([ENSW])\\)");
    private final Pattern ACTION_LINE = Pattern.compile("\\(([0-9]+),([0-9]+)\\)([LRM]*)");

    public ParsedResult parseInput(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String strSize = readLineSkipBlank(reader);
        Validate.isTrue(StringUtils.isNumeric(strSize), "Could not find the board size");

        String strShips = readLineSkipBlank(reader);

        List<String> strActionList = new ArrayList<>();

        String line;
        while ((line = readLineSkipBlank(reader)) != null) {
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

        strSize = cleanSpaces(strSize);
        strShips = cleanSpaces(strShips);

        Board board = new Board(
                Integer.parseInt(cleanSpaces(strSize))
        );

        Matcher shipLineMatcher = SHIP_PLACEMENT_LINE.matcher(strShips);

        Validate.isTrue(shipLineMatcher.matches(), "Cannot parse ship placement line");

        Matcher shipGroupMatcher = SHIP_GROUP.matcher(strShips);
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
                    cleanSpaces(strAction).toUpperCase()
            );

            if (matcher.matches()) {

                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                String actions = matcher.group(3);

                if (StringUtils.isBlank(actions)) {

                    actionList.add(
                            new ShotAction(x, y)
                    );

                } else {

                    List<IShipAction> shipActionList = new ArrayList<>();

                    for (char c : actions.toCharArray()) {
                        switch (c) {
                            case 'L':
                                shipActionList.add(new RotateShipAction(x, y, Rotation.Left));
                                break;
                            case 'R':
                                shipActionList.add(new RotateShipAction(x, y, Rotation.Right));
                                break;
                            case 'M':
                                shipActionList.add(new MoveShipAction(x, y));
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

        }

        return actionList;

    }

    private String readLineSkipBlank(BufferedReader reader) throws IOException {
        Validate.notNull(reader);

        String line;
        while ((line = reader.readLine()) != null) {
            if (StringUtils.isNotBlank(line)) {
                return line;
            }
        }

        return null;
    }

    private String cleanSpaces(String string) {
        return CharMatcher.whitespace().removeFrom(string);
    }

}
