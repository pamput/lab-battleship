package net.thoughtmachine.game;

import net.thoughtmachine.model.Board;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public interface IAction {

    void execute(Board board);

}
