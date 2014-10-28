package uk.gov.dwp.maze;

import java.util.*;

public class Explorer {

    private boolean backward;
    private boolean done;
    private int[] movement;
    private Maze maze;

    public Explorer() {

    }

    public void exploreMaze(Maze m){
        this.maze = m;
        if (move(3, 3)) {
            System.out.println(maze);
        }
    }

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
            // can hit dead end if goes into this block - so we need to going backward until there is another open square.
            maze.markExplored(row, col, true);
            Square nextSquare = turn(row, col);

            if (nextSquare != null) {
                if (move(nextSquare.getRow(), nextSquare.getColumn())) {
                    System.out.println(maze);
                    return !done;
                }
            } else {
                // get previous explored square
                Square previousExplored = maze.getPreviousExplored();
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
     *
     * @param row
     * @param col
     * @return null if there are not open square in the next move.
     */
    private Square turn(final int row, final int col){
        Set<Square> openSquares = getOpenAdjacentSquares(row, col);
        List<Square> list = new ArrayList<Square>();
        list.addAll(openSquares);

        // if there are more than one open squares in front randomly pick one up.
        int size = list.size();
        if (size > 0) {
            Random generator = new Random();
            int index = generator.nextInt(size);
            return list.get(index);
        }
        return null;
    }

    private Set<Square> getOpenAdjacentSquares(final int currentRow, final int currentCol) {
        Set<Square> openSquares = new HashSet<Square>();

        for (int i = 0; i <= 3; i ++) {
            if (i == 0 && maze.getSquare(currentRow, currentCol - 1).isOpen() &&
                    !maze.isExplored(currentRow, currentCol - 1)) {
                openSquares.add(maze.getSquare(currentRow, currentCol - 1));
            }

            if (i == 1 && maze.getSquare(currentRow - 1, currentCol).isOpen() &&
                    !maze.isExplored(currentRow - 1, currentCol)) {
                openSquares.add(maze.getSquare(currentRow - 1, currentCol));
            }

            if (i == 2 && maze.getSquare(currentRow + 1, currentCol).isOpen() &&
                    !maze.isExplored(currentRow + 1, currentCol)) {
                openSquares.add(maze.getSquare(currentRow + 1, currentCol));
            }

            if (i == 3 && maze.getSquare(currentRow, currentCol + 1).isOpen() &&
                    !maze.isExplored(currentRow, currentCol + 1)) {
                openSquares.add(maze.getSquare(currentRow, currentCol + 1));
            }
        }
        return openSquares;
    }


}
