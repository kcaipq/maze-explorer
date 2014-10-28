package uk.gov.dwp.maze;

/**
 * Created by Kcai on 28/10/2014.
 */
public class Square {

    protected int row;
    protected int column;
    protected SquareState state;

    protected Square(int row, int column, char sign) {
        this.row = row;
        this.column = column;
        this.state = SquareState.getSquareState(sign);
    }

    public boolean isOpen() {
        return state == SquareState.OPEN ||
                state == SquareState.START ||
                state == SquareState.EXIT;
    }

    public boolean isStart() {
        return state == SquareState.START;
    }

    public boolean isExit() {
        return state == SquareState.EXIT;
    }

    public boolean isWalled() {
        return state == SquareState.WALLED;
    }

    /**
     * Whether the square on the board is represented using valid character.
     * @return true or false
     */
    public boolean isValidRepresentation() {
        return isOpen() || isWalled();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public SquareState getState() {
        return state;
    }

    public String toString() {
        return "(" + row + ", " + column + ", " + state + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Square square = (Square) o;

        if (column != square.column) return false;
        if (row != square.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
