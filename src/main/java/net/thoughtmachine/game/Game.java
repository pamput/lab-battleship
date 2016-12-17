package net.thoughtmachine.game;

import net.thoughtmachine.model.Board;
import org.apache.commons.lang3.Validate;

import java.util.Collection;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class Game {

    private Board board;

    public Game(Board board) {

        Validate.notNull(board);

        this.board = board;
    }

    public void execute(IBoardAction action) {
        Validate.notNull(action);
        action.execute(board);
    }

    public void execute(Collection<IBoardAction> actions) {

        Validate.notNull(actions);

        for(IBoardAction action : actions) {
            execute(action);
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
