package net.thoughtmachine.game;

import net.thoughtmachine.model.Board;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 *
 * An action the requires only knowing about the board existence.
 *
 */
public interface IBoardAction {

    void execute(Board board);

}
