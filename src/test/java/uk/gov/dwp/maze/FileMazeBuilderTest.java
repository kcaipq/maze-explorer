package uk.gov.dwp.maze;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by kcai on 28/10/2014.
 */
public class FileMazeBuilderTest {

    private MazeBuilder builder;

    @Test
    public void testNullSafety() {
        try {
            this.builder = new FileMazeBuilder(null);
        } catch (IllegalArgumentException io) {
            assertThat(io.getMessage(), containsString("File path cannot be null."));
        }
    }

    @Test
    public void testNonExistFileShouldReturnNullMaze() {
        this.builder = new FileMazeBuilder("NON_EXIST");
        assertNull(builder.build());
    }

    @Test
    public void testShouldProduceMazeMap() {
        this.builder = new FileMazeBuilder("src/test/resources/maze.txt");
        Square[][] square = builder.build().getSquare();
        assertNotNull(square);
        assertEquals(15, square.length);
        assertEquals(15, square[0].length);
    }
}
