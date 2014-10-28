package uk.gov.dwp.maze;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Maze extends MazeFactory {

    private Square[][] maze;
    private boolean[][] mazeExplored;
    private List<String> history = new LinkedList<String>();
    private Path path;
    private int height;
    private int width;

    public Maze(final String filePath) {
        try {
            File file = new File(filePath);
            this.maze = MazeFactory.buildMazeMap(file);
            this.path = new Path();
            this.mazeExplored = new boolean[maze.length][maze[0].length];
            if (maze != null) { // npe safe
                this.height = maze.length;
                this.width = maze[0].length;
            }
        } catch (Exception s) {
            s.printStackTrace();
        }
    }

    public List<String> getHistory() {
        return history;
    }

    public Square getPreviousExplored() {
        return path.getPreviousExploredSquare();
    }

    /**
     * Mark a coordinate using the passed in flag.
     *
     * @param x the row
     * @param y the column
     * @param visited the flag whether this coordinate is visited.
     */
    public void markExplored(int x, int y, boolean visited) {
        if (!validateCoordinates(x, y))
            throw new IllegalArgumentException("Coordinates x: " + x + ", y: " + y + " is not valid");
        mazeExplored[x][y] = visited;
        history.add("" + x + ":" + y);
        path.addPath(maze[x][y]);
    }

    public boolean isExplored(final int x, final int y) {
        return mazeExplored[x][y];
    }

    /**
     * @return instance of the current maze 2-d array, this is not null safe.
     */
    public Square[][] getSquare() {
        return this.maze;
    }

    /**
     * coordinates start from 0 ...
     * @param x
     * @param y
     * @return
     */
    public Square getSquare(final int x, final int y) {
        if (!validateCoordinates(x, y))
            return null;
        return maze[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getOpenSpacesCount() {
        return getCount(SquareState.OPEN);
    }

    public int getWallCount() {
        return getCount(SquareState.WALLED);
    }

    public int getStartCount() {
        return getCount(SquareState.START);
    }

    public int getExitCount() {
        return getCount(SquareState.EXIT);
    }

    /**
     * Handy method to count squares for a specific state.
     *
     * @param state {@link uk.gov.dwp.maze.SquareState}
     * @return the occurrences of the SquareState in the map.
     */
    private int getCount(final SquareState state) {
        int count = 0;
        for (int i = 0 ; i < getHeight(); i ++) {
            for (int j = 0; j < getWidth(); j++) {
                Square s = getSquare(i, j);
                if (s.getState() == state)
                    count ++;
            }
        }
        return count;
    }

    private boolean validateCoordinates(int row, int col) {
        return !(row < 0 || row >= getHeight() || col < 0 || col >= getWidth());
    }

    public String toString() {
        StringBuilder result = new StringBuilder(getWidth() * getHeight());
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (maze[row][col].isWalled()) {
                    result.append('X');
                } else if (maze[row][col].isStart()) {
                    result.append('S');
                } else if (maze[row][col].isExit()) {
                    result.append('F');
                } else if (mazeExplored[row][col]) {
                    result.append('.');
                } else {
                    result.append(' ');
                }
            }
            result.append('\n');
        }
        sleep(100);
        return result.toString();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {}
    }
}
