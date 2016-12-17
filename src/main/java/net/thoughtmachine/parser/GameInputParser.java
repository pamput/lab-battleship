package net.thoughtmachine.parser;

import com.google.common.base.CharMatcher;
import net.thoughtmachine.game.IAction;
import net.thoughtmachine.game.impl.MoveAction;
import net.thoughtmachine.game.impl.RotateAction;
import net.thoughtmachine.game.impl.ShotAction;
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

        String strSize = reader.readLine();
        Validate.notBlank(strSize, "Could not find the board size at line 1");

        String strShips = reader.readLine();
        Validate.notBlank(strShips, "Could not find any ship placement at line 2");

        List<String> strActionList = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
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

        for (int i = 0; i < shipLineMatcher.groupCount(); i++) {

            String strShipGroup = shipLineMatcher.group(i);

            Matcher shipGroupMatcher = SHIP_GROUP.matcher(strShipGroup);
            Validate.isTrue(shipGroupMatcher.matches(), "Cannot parse ship placement group: %s", strShipGroup);

            int x = Integer.parseInt(shipGroupMatcher.group(0));
            int y = Integer.parseInt(shipGroupMatcher.group(1));
            Direction d = Direction.valueOf(shipGroupMatcher.group(2).charAt(0));

            board.addShip(
                    new Ship(),
                    new Position(
                            x, y, d
                    )
            );

        }

        return board;
    }

    private String cleanSpaces(String string) {
        return CharMatcher.whitespace().removeFrom(string);
    }

    private List<IAction> createActionList(List<String> strActionList) {

        List<IAction> actionList = new ArrayList<>();

        for (String strAction : strActionList) {

            Matcher matcher = ACTION_LINE.matcher(strAction.toUpperCase());

            if (matcher.matches()) {

                int x = Integer.parseInt(matcher.group(0));
                int y = Integer.parseInt(matcher.group(1));
                String actions = matcher.group(3);

                if (StringUtils.isBlank(actions)) {

                    actionList.add(
                            new ShotAction(x, y)
                    );

                } else {

                    for (char c : actions.toCharArray()) {
                        switch (c) {
                            case 'L':
                                actionList.add(new RotateAction(x, y, Rotation.Left));
                                break;
                            case 'R':
                                actionList.add(new RotateAction(x, y, Rotation.Right));
                                break;
                            case 'M':
                                actionList.add(new MoveAction(x, y));
                                break;
                            default:
                                throw new IllegalStateException();
                        }
                    }

                }

            }

        }

        return actionList;

    }


}
