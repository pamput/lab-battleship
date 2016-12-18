package net.thoughtmachine.game;

import net.thoughtmachine.model.Board;
import net.thoughtmachine.model.Ship;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 *
 * An action that has to know about the board AND a specific ship.
 *
 */
public interface IShipAction {

    void execute(Ship ship, Board board);


}
