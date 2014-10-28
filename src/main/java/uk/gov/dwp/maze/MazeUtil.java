package uk.gov.dwp.maze;

/**
 * Created by kcai on 28/10/2014.
 */
public class MazeUtil {

    public static Square findSquareByState(Square[][] squares, final SquareState state) {

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                Square s = squares[i][j];
                if (s.getState() == state)
                    return s;
            }
        }
        return null;
    }
}
