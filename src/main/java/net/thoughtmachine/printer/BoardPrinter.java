package net.thoughtmachine.printer;

import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Position;
import net.thoughtmachine.model.Ship;
import org.apache.commons.lang3.Validate;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class BoardPrinter {

    public void print(Board board, OutputStream out) {

        Validate.notNull(board);
        Validate.notNull(out);

        PrintStream printer = new PrintStream(out);

        Map<Ship, Position> positionMap = board.getPositionMap();

        Validate.notNull(positionMap);

        for (Ship ship : positionMap.keySet()) {
            Position position = positionMap.get(ship);

            Validate.notNull(position);

            printer.println(
                    String.format(
                            "(%s, %s, %s) %s",
                            position.getX(),
                            position.getY(),
                            position.getDirection().getChar(),
                            ship.isSunk() ? "SUNK" : ""
                    )
            );
        }

    }

}
