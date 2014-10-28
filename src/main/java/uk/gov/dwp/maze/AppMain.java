package uk.gov.dwp.maze;

/**
 * Created by User on 28/10/2014.
 */
public class AppMain {

    public static void main(String[] args) {
        MazeBuilder builder = new FileMazeBuilder("src/main/resources/maze3.txt");
        Maze maze = builder.build();
        System.out.println(maze);

        Explorer e = new Explorer(maze);
        e.exploreMaze();
    }
}
