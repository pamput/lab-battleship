package net.thoughtmachine.parser;

import net.thoughtmachine.game.IAction;
import net.thoughtmachine.model.Board;

import java.util.List;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class ParsedResult {

    private Board board;
    private List<IAction> actions;

    public ParsedResult(Board board, List<IAction> actions) {
        this.board = board;
        this.actions = actions;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<IAction> getActions() {
        return actions;
    }

    public void setActions(List<IAction> actions) {
        this.actions = actions;
    }
}
