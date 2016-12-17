package net.thoughtmachine.parser;

import net.thoughtmachine.game.IBoardAction;
import net.thoughtmachine.model.Board;

import java.util.List;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class ParsedResult {

    private Board board;
    private List<IBoardAction> actions;

    public ParsedResult(Board board, List<IBoardAction> actions) {
        this.board = board;
        this.actions = actions;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<IBoardAction> getActions() {
        return actions;
    }

    public void setActions(List<IBoardAction> actions) {
        this.actions = actions;
    }
}
