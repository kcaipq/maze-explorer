package uk.gov.dwp.maze;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by Kcai on 27/10/2014.
 */
public class MazeFactoryTest {

    private MazeFactory factory = new MazeFactory();

    @Test(expected = IllegalArgumentException.class)
    public void testFactoryNullInput() {
        factory.buildMazeMap(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactoryNonExistFileInput() {
        factory.buildMazeMap(new File("src/test"));
    }

    @Test
    public void testFactoryEmptyFileInput() {
        try {
            factory.buildMazeMap(new File("src/test/resources/empty.txt"));
        } catch (IllegalArgumentException iae) {
            Assert.assertTrue(iae.getMessage().contains("Invalid input file - empty lines"));
        }
    }

    @Test
    public void testFactoryBadFormattedFileInput() {
        try {
            factory.buildMazeMap(new File("src/test/resources/badcontent.txt"));
        } catch (IllegalArgumentException iae) {
            Assert.assertTrue(iae.getMessage().contains("line 7 wrong length 14, should be 15"));
        }
    }

    @Test
    public void testFactoryOneStart() {
        try {
            factory.buildMazeMap(new File("src/test/resources/multiple-starts.txt"));
        } catch (IllegalArgumentException iae) {
            Assert.assertTrue(iae.getMessage().contains("Invalid map data - should have one and only one Start point 'S' and one and only one exit 'F'"));
        }
    }

    @Test
    public void testFactoryOneExit() {
        try {
            factory.buildMazeMap(new File("src/test/resources/multiple-exits.txt"));
        } catch (IllegalArgumentException iae) {
            Assert.assertTrue(iae.getMessage().contains("Invalid map data - should have one and only one Start point 'S' and one and only one exit 'F'"));
        }
    }

    @Test
    public void testFactoryLoadMaze() {
        Square[][] maze = factory.buildMazeMap(new File("src/test/resources/maze.txt"));

        Assert.assertNotNull(maze);
        Assert.assertEquals(15, maze.length);
        Assert.assertEquals(15,maze[0].length);
    }
}
