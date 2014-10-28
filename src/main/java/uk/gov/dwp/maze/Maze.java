package uk.gov.dwp.maze;

public class Maze extends MazeFactory {

    private Square[][] maze;
    private boolean[][] mazeExplored;
    private Path path;
    private int height;
    private int width;

    public Maze(final Square[][] maze) {
        if (maze == null)
            throw new IllegalArgumentException("Cannot have null map in Maze");
        this.maze = maze;
        this.path = new Path();
        this.height = maze.length;
        this.width = maze[0].length;
        this.mazeExplored = new boolean[height][width];
    }

    /**
     * Get the path of the very final route.
     * @return the Path stack that leads to solution.
     */
    public Path getPath() {
        return path;
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
     * @param x the row
     * @param y the column
     * @return the square located at the specific x and y coordinate.
     */
    public Square getSquare(final int x, final int y) {
        if (!validateCoordinates(x, y))
            return null;
        return maze[x][y];
    }

    public Square getStartSquare() {
        for (int i = 0 ; i < getHeight(); i ++) {
            for (int j = 0; j < getWidth(); j++) {
                Square s = getSquare(i, j);
                if (s.isStart())
                    return s;
            }
        }
        return null;
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
        return result.toString();
    }
}
