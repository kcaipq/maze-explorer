package uk.gov.dwp.maze;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;



/**
 * Created by Kcai on 28/10/2014.
 */
public class MazeTest {

    private Maze maze;

    @Before
    public void before() {
        this.maze = new FileMazeBuilder("src/test/resources/maze.txt").build();
    }

    @Test
    public void testMazeNullValueShouldThrowException() {
        try {
            this.maze = new FileMazeBuilder("NOT_EXIST").build();
        } catch (IllegalArgumentException s) {
            assertThat(s.getMessage(), containsString("Cannot have null map in Maze"));
        }
    }

    @Test
    public void testMazeNullSafety() {
        Assert.assertNotNull(maze);
        Assert.assertNotNull(maze.getSquare());
    }

    @Test
    public void testMazeAvailableToAccess() {
        Assert.assertNotNull(maze);
        int startCount = 0;
        int exitCount = 0;
        for (int i = 0 ; i < maze.getHeight(); i ++) {
            for (int j = 0; j < maze.getWidth(); j ++) {
                Square s = maze.getSquare(i, j);
                System.out.println(s);
                Assert.assertNotNull(s);
                Assert.assertTrue(s.isValidRepresentation());

                if (s.isStart()) {
                    startCount ++;
                }

                if (s.isExit()) {
                    exitCount ++;
                }
            }
        }
        Assert.assertEquals(1, startCount);
        Assert.assertEquals(1, exitCount);
    }

    @Test
    public void testMazeHeightAndWidth() {
        Assert.assertNotNull(maze);
        Assert.assertEquals(15, maze.getHeight());
        Assert.assertEquals(15, maze.getWidth());
    }

    @Test
    public void testOpenSpacesCount() {
        Assert.assertEquals(74, maze.getOpenSpacesCount());
    }

    @Test
    public void testWalledCount() {
        Assert.assertEquals(15 * 15 - 74 - 2, maze.getWallCount());
    }

    @Test
    public void testStartCount() {
        Assert.assertEquals(1, maze.getStartCount());
    }

    @Test
    public void testExitCount() {
        Assert.assertEquals(1, maze.getExitCount());
    }

    @Test
    public void testOutOfRangeCoordinates() {
        try {
            Assert.assertNull(maze.getSquare(-1, -1));
            Assert.assertNull(maze.getSquare(20, -1));
            Assert.assertNull(maze.getSquare(20, 20));
            Assert.assertNull(maze.getSquare(-1, 20));
        } catch (ArrayIndexOutOfBoundsException ae) {
            Assert.fail("Coordinates are out of supported range.");
        }
    }

    @Test
    public void testWalledCoordinate() {
        Assert.assertNotNull(maze.getSquare(0,0));
        Assert.assertTrue(maze.getSquare(0,0).isWalled());
        Assert.assertFalse(maze.getSquare(0,0).isOpen());
        Assert.assertFalse(maze.getSquare(0,0).isExit());
        Assert.assertFalse(maze.getSquare(0,0).isStart());
    }

    @Test
    public void testStartCoordinate() {
        Assert.assertNotNull(maze.getSquare(3,3));
        Assert.assertTrue(maze.getSquare(3,3).isStart());
        Assert.assertTrue(maze.getSquare(3,3).isOpen());
        Assert.assertFalse(maze.getSquare(3,3).isExit());
        Assert.assertFalse(maze.getSquare(3,3).isWalled());
    }

    @Test
    public void testOpenCoordinate() {
        Assert.assertNotNull(maze.getSquare(1,1));
        Assert.assertTrue(maze.getSquare(1,1).isOpen());
        Assert.assertFalse(maze.getSquare(1,1).isWalled());
        Assert.assertFalse(maze.getSquare(1,1).isExit());
        Assert.assertFalse(maze.getSquare(1,1).isStart());
    }

    @Test
    public void testExitCoordinate() {
        Assert.assertNotNull(maze.getSquare(14,1));
        Assert.assertTrue(maze.getSquare(14,1).isExit());
        Assert.assertTrue(maze.getSquare(14,1).isOpen());
        Assert.assertFalse(maze.getSquare(14,1).isWalled());
        Assert.assertFalse(maze.getSquare(14,1).isStart());
    }
}
