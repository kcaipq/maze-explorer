package uk.gov.dwp.maze;

import java.util.LinkedList;
import java.util.List;

public class MazeDemo {
    private char[][] squares;
    private boolean[][] explored;
    private List<String> history = new LinkedList<String>();
    private boolean animated;

    private int previousExploredX;
    private int previousExploredY;

    private int lastIsRow, lastIsCol;   // last location on which an 'is' method
    // was called; used for toString '?'


    public int getPreviousExploredX() {
        return previousExploredX;
    }

    public int getPreviousExploredY() {
        return previousExploredY;
    }

    public MazeDemo(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] lines = text.split("[\r]?\n");
        squares = new char[lines.length][lines[0].length()];
        explored = new boolean[lines.length][lines[0].length()];

        for (int row = 0; row < getHeight(); row++) {
            if (lines[row].length() != getWidth()) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was "
                        + lines[row].length() + " but should be " + getWidth() + ")");
            }

            for (int col = 0; col < getWidth(); col++) {
                squares[row][col] = lines[row].charAt(col);
            }
        }

        lastIsRow = -1;
        lastIsCol = -1;
        animated = false;
    }

    public int getHeight() {
        return squares.length;
    }

    public char getSquare(int row, int col) {
        checkIndexes(row, col);
        return squares[row][col];
    }

    public int getWidth() {
        return squares[0].length;
    }

    public boolean isEscaped() {
        // check left/right edges
        for (int row = 0; row < getHeight(); row++) {
            if (explored[row][0] || explored[row][getWidth() - 1]) {
                return true;
            }
        }

        // check top/bottom edges
        for (int col = 0; col < getWidth(); col++) {
            if (explored[0][col] || explored[getHeight() - 1][col]) {
                return true;
            }
        }

        return false;
    }

    public boolean trackExplored(int row, int col) {
        checkIndexes(row, col);
        return explored[row][col];
    }

    public boolean isExplored(int row, int col) {
        checkIndexes(row, col);
        lastIsRow = row;
        lastIsCol = col;
        return explored[row][col];
    }

    public boolean isMarked(int row, int col) {
        checkIndexes(row, col);
        lastIsRow = row;
        lastIsCol = col;
        return squares[row][col] == '.';
    }

    public boolean isWall(int row, int col) {
        checkIndexes(row, col);
        lastIsRow = row;
        lastIsCol = col;
        return squares[row][col] == '#';
    }

    public boolean isOpen(int row, int col) {
        checkIndexes(row, col);
        //lastIsRow = row;
        //lastIsCol = col;
        return squares[row][col] == ' ';
    }

    public void mark(int row, int col) {
        checkIndexes(row, col);
        maybePause();
        squares[row][col] = 'x';
    }

    public void setAnimated(boolean value) {
        animated = value;
    }

    public void setExplored(int row, int col, boolean value) {
        checkIndexes(row, col);
        explored[row][col] = value;
        history.add("" + row + ":" + col);
    }

    public List<String> getHistory() {
        return history;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (row == lastIsRow && col == lastIsCol) {
                    result.append('?');
                } else if (squares[row][col] == '#') {
                    result.append('#');
                } else if (squares[row][col] == 'x') {
                    result.append('x');
                } else if (explored[row][col]) {
                    result.append('.');
                } else {
                    result.append(' ');
                }
            }
            result.append('\n');
        }
        maybePause();
        return result.toString();
    }

    private void checkIndexes(int row, int col) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
            throw new IllegalArgumentException("illegal indexes: (" +
                    row + ", " + col + ")");
        }
    }

    private void maybePause() {
        if (animated) {
            sleep(100);
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {}
    }
}