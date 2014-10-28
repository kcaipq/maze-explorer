package uk.gov.dwp.maze;

import java.util.Stack;

/**
 * Created by User on 28/10/2014.
 */
public class Path {

    private Stack<Square> paths;

    public Path() {
        paths = new Stack<Square>();
    }

    /**
     * Going backward explored when reach dead-end.
     */
    public Square getPreviousExploredSquare() {
       if (!paths.isEmpty())
            paths.pop();
        return paths.pop();
    }

    public void addPath(Square square) {
        paths.push(square);
    }
}
