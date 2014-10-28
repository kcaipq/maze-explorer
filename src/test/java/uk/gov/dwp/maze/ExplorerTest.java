package uk.gov.dwp.maze;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;

/**
 * Created by kcai on 28/10/2014.
 */
public class ExplorerTest {

    private Maze maze;

    @Before
    public void before() {
        MazeBuilder builder = new FileMazeBuilder("src/test/resources/maze.txt");
        maze = builder.build();
    }

    @Test
    public void testExplorerHasMaze() {
        Explorer ex = new Explorer(maze);
        Object m = ReflectionTestUtil.getField(ex, "maze", Maze.class);

        Assert.assertNotNull(m);
        Assert.assertTrue(m instanceof Maze);
    }

    @Test
    public void testExplorerMazeNullSafety() {
        try {
            new Explorer(null);
        } catch (IllegalArgumentException e) {
            Assert.assertThat(e.getMessage(), containsString("Null maze in explorer"));
        }
        Assert.fail("No IllegalArgumentsException found.");
    }

    @Test
    public void testExplorerCanBeDroppedAtStartPoint() {
        Explorer ex = new Explorer(maze);

        Square startSquare = maze.getStartSquare();
        Assert.assertEquals(3, startSquare.getRow());
        Assert.assertEquals(3, startSquare.getColumn());

        ex.exploreMaze();

        String finalPathString = ex.printExplorersPath();
        Square[][] outputPath = MazeFactory.buildMazeMapFromString(finalPathString);

        Square outputSquare = MazeUtil.findSquareByState(outputPath, SquareState.START);
        Assert.assertEquals(3, outputSquare.getRow());
        Assert.assertEquals(3, outputSquare.getColumn());

        Assert.assertEquals(outputSquare, startSquare);
    }
}
