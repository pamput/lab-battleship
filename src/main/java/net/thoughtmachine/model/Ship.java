package net.thoughtmachine.model;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class Ship {

    private boolean sunk;

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    @Override
    public String toString() {
        if (sunk) {
            return "Ship [SUNK]";
        } else {
            return "Ship";
        }
    }
}
