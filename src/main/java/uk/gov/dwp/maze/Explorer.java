package uk.gov.dwp.maze;

import java.util.*;

public class Explorer {

    private boolean backward;
    private boolean done;
    private Maze maze;

    public Explorer(Maze maze) {
        if (maze == null)
            throw new IllegalArgumentException("Null maze in explorer.");

        this.maze = maze;
    }

    public void exploreMaze() {
        Square startSquare = maze.getStartSquare();
        if (move(startSquare.getRow(), startSquare.getColumn())) {
            System.out.println(maze);
        }
    }

    /**
     * print the accumulated paths lead to the 'F' point.
     */
    public String printExplorersPath() {
        StringBuilder result = new StringBuilder(maze.getWidth() * maze.getHeight());

        Square[][] map = maze.getSquare();
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {

                if (map[row][col].isWalled()) {
                    result.append('X');
                } else if (map[row][col].isStart()) {
                    result.append('S');
                } else if (map[row][col].isExit()) {
                    result.append('F');
                } else if (maze.getPath().isOnPath(maze.getSquare(row, col))) {
                    result.append('.');
                } else {
                    result.append(' ');
                }
            }
            result.append('\n');
        }
        System.out.println(result.toString());
        return result.toString();
    }

    /**
     * Get the previous explored square.
     *
     * @return {@link uk.gov.dwp.maze.Square} previously explored in current path.
     */
    public Square getPreviousExplored() {
        return maze.getPath().getPreviousExploredSquare();
    }

    /**
     * 
     *
     * @param row
     * @param col
     * @return
     */
    private boolean move(final int row, final int col){
        //System.out.println(maze);
        Square currentSquare = maze.getSquare(row, col);

        if (currentSquare.isWalled() && !backward) {
            return false;
        } else if (maze.isExplored(row, col) && !backward) {
            return false;
        } else if (currentSquare.isExit()) {
            // found the exit 'F', need to notify the system to start recording the path backwards
            done = true;
            return true;
        } else {
            // can hit dead end if goes into this block - so we need to going backward until it hits another open route.
            maze.markExplored(row, col, true);
            Square nextSquare = turn(row, col);

            if (nextSquare != null) {
                if (move(nextSquare.getRow(), nextSquare.getColumn())) {
                    System.out.println(maze);
                    printExplorersPath();
                    return !done;
                }
            } else {
                // get previous explored square
                Square previousExplored = getPreviousExplored();
                this.backward = true;
                if (move(previousExplored.getRow(), previousExplored.getColumn())) {
                    System.out.println(maze);
                    return !done;
                }
            }
        }
        return false;
    }

    /**
     * Randomly turn left/right, may also choose forward/backward
     *
     * @param row row ordinal
     * @param col column ordinal
     * @return null if there are not open square in the next move.
     */
    private Square turn(final int row, final int col){
        Set<Square> openSquares = getOpenAdjacentSquares(row, col);
        // if there are more than one open squares in front randomly pick one up.
        int size = openSquares.size();
        if (size > 0) {
            Random generator = new Random();
            int index = generator.nextInt(size);
            return openSquares.toArray(new Square[size])[index];
        }
        return null;
    }

    /**
     * Finding the adjacent empty spaces to the current square.
     *
     * TODO simplify this method and get rid of if/else
     *
     * @param currentRow row ordinal
     * @param currentCol column ordinal
     * @return open Squares in a Set
     */
    private Set<Square> getOpenAdjacentSquares(final int currentRow, final int currentCol) {
        Set<Square> openSquares = new HashSet<Square>();

        for (int i = 0; i <= 3; i ++) {
            if (i == 0 && maze.getSquare(currentRow, currentCol - 1).isOpen() &&
                    !maze.isExplored(currentRow, currentCol - 1)) {
                openSquares.add(maze.getSquare(currentRow, currentCol - 1));
            } else if (i == 1 && maze.getSquare(currentRow - 1, currentCol).isOpen() &&
                    !maze.isExplored(currentRow - 1, currentCol)) {
                openSquares.add(maze.getSquare(currentRow - 1, currentCol));
            } else if (i == 2 && maze.getSquare(currentRow + 1, currentCol).isOpen() &&
                    !maze.isExplored(currentRow + 1, currentCol)) {
                openSquares.add(maze.getSquare(currentRow + 1, currentCol));
            } else if (i == 3 && maze.getSquare(currentRow, currentCol + 1).isOpen() &&
                    !maze.isExplored(currentRow, currentCol + 1)) {
                openSquares.add(maze.getSquare(currentRow, currentCol + 1));
            }
        }
        return openSquares;
    }


}
