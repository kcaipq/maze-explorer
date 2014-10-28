package uk.gov.dwp.maze;

/**
 * Created by User on 28/10/2014.
 */
public class TestMain {

    public static void main(String[] args) {
        Maze maze = new Maze("src/main/resources/maze3.txt");
        System.out.println(maze);

        Explorer e = new Explorer();
        e.exploreMaze(maze);
    }
}
