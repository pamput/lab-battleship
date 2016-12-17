package net.thoughtmachine.game.impl;

import net.thoughtmachine.game.IAction;
import net.thoughtmachine.model.Board;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class MoveAction implements IAction {

    private int x;
    private int y;

    public MoveAction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Board board) {
        // TODO
    }
}
