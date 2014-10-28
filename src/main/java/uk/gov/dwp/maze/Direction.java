package uk.gov.dwp.maze;

/**
 * Created by User on 27/10/2014.
 */
public class Direction {

    public static final int UP = 0;

    public static final int RIGHT = 1;

    public static final int DOWN = 2;

    public static final int LEFT = 3;

    private int direction;

    public Direction(int direction) {
        this.direction = direction;
    }

    Direction toRight() {
        return new Direction(1);
    }

    Direction toDown() {
        return new Direction(2);
    }

    Direction toLeft() {
        return new Direction(3);
    }

    Direction toUp() {
        return new Direction(0);
    }

    @Override
    public String toString() {
        return null;
    }
}
