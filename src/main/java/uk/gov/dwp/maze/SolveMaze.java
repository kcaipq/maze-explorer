package uk.gov.dwp.maze;

import java.io.*;
import java.util.*;

public class SolveMaze {
    private static boolean goBackHistory;
    private static volatile boolean done = false;

    public static void main(String[] args) throws FileNotFoundException {
        String fileText = "";
        Scanner input = new Scanner(new File("src/main/resources/maze1.txt"));
        while (input.hasNextLine()) {
            fileText += input.nextLine() + "\n";
        }

        MazeDemo maze = new MazeDemo(fileText);
        maze.setAnimated(true);
        System.out.println(maze);
        System.out.println();

        int startRow = 1;
        int startCol = 6;
        System.out.println("Start from (" + startRow + ", " + startCol + ")");
        explore(maze, startRow, startCol);
    }

    // Finds a pathway out of the given maze from the given start location.
    // Preconditions: maze != null and startRow/Col are within the maze
    public static void explore(MazeDemo maze, int startRow, int startCol) {
        if (move(maze, startRow, startCol)) {
            System.out.println(maze);
        } else {
            System.out.println("no solution");
        }
    }

    // Private helper that finds a pathway out of the maze from the given start
    // using recursive backtracking.  Marks each square it examines as having
    // been 'explored'.  Returns true if a path out was found, and false if not.
    // If a pathway out is found, marks every step along that path with a '.'.
    private static boolean move(MazeDemo maze, int row, int col) {
        System.out.println(maze);	// animate

        // unproductive path: wall or previously explored
        if (maze.isWall(row, col) || (maze.isExplored(row, col) && !goBackHistory)) {
            return false;
        } else if (row == 0 || row == maze.getHeight() - 1 || col == 0
                || col == maze.getWidth() - 1) {
            // exit has been found
            maze.mark(row, col);
            done = true;
            return true;
        } else {
            maze.setExplored(row, col, true);
            /*if (move(maze, row, col - 1)) {
                System.out.println("Going Left");
            } else if (move(maze, row - 1, col)) {
                System.out.println("Going Up");
            } else if (move(maze, row + 1, col)) {
                System.out.println("Going Down");
            } else if (move(maze, row, col + 1)) {
                System.out.println("Going Right");
            }*/


            int d = -1;
            Set<Integer> openSquares = getOpenAdjacentSquares(maze, row, col);
            List<Integer> list = new ArrayList<Integer>();
            list.addAll(openSquares);

            if (openSquares.size() != 0) {
                d = list.get(0);
            }

            if (d == 0) {
                if (move(maze, row, col - 1)) {
                    maze.mark(row, col);
                    System.out.println(maze);	// animate
                    return !done;
                }
            }

            else if (d == 1) {
                if (move(maze, row - 1, col)) {
                    maze.mark(row, col);
                    System.out.println(maze);	// animate
                    return !done;
                }
            }

            else if (d == 2) {
                if (move(maze, row + 1, col)) {
                    maze.mark(row, col);
                    System.out.println(maze);	// animate
                    return !done;
                }
            }

            else if (d == 3) {
                if (move(maze, row, col + 1)) {
                    maze.mark(row, col);
                    System.out.println(maze);	// animate
                    return !done;
                }
            } else {
                // get previous explored coordinate
                String currentExplored = "" + row + ":" + col;
                String history = getPrevious(maze.getHistory(),currentExplored);
                int xx = Integer.parseInt(history.split(":")[0]);
                int yy = Integer.parseInt(history.split(":")[1]);
                goBackHistory = true;

                if (move(maze, xx, yy)) {
                    maze.mark(row, col);
                    System.out.println(maze);	// animate
                    return !done;
                }
            }


            /*if (move(maze, row, col - 1) || // left
                    move(maze, row - 1, col) || // up
                    move(maze, row + 1, col) || // down
                    move(maze, row, col + 1)) { // right
                maze.mark(row, col);
                System.out.println(maze);	// animate
                return true;	// location is on the exit path
            }*/
        }
        return false;	// not on exit path
    }

    public static String getPrevious(List<String> history, String uid) {
        int idx = history.indexOf(uid);
        if (idx <= 0) return "";
        return history.get(idx - 1);
    }

    private static Set<Integer> getOpenAdjacentSquares(MazeDemo maze, final int currentRow, final int currentCol) {
        Set<Integer> openSquares = new HashSet<Integer>();
        for (int i = 0; i <= 3; i ++) {
            if (i == 0 && maze.isOpen(currentRow, currentCol - 1) && !maze.trackExplored(currentRow, currentCol - 1)) {
                openSquares.add(0);
            }

            if (i == 1 && maze.isOpen(currentRow - 1, currentCol) && !maze.trackExplored(currentRow - 1, currentCol)) {
                openSquares.add(1);
            }

            if (i == 2 && maze.isOpen(currentRow + 1, currentCol) && !maze.trackExplored(currentRow + 1, currentCol)) {
                openSquares.add(2);
            }

            if (i == 3 && maze.isOpen(currentRow, currentCol + 1) && !maze.trackExplored(currentRow, currentCol + 1)) {
                openSquares.add(3);
            }
        }
        return openSquares;
    }


}
