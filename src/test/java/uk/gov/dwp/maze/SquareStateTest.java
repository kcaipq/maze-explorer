package uk.gov.dwp.maze;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Kcai on 27/10/2014.
 */
public class SquareStateTest {

    @Test
    public void testGetSquareStateBySign() {
        Assert.assertTrue(SquareState.START == SquareState.getSquareState('S'));
        Assert.assertTrue(SquareState.EXIT == SquareState.getSquareState('F'));
        Assert.assertTrue(SquareState.WALLED == SquareState.getSquareState('X'));
        Assert.assertTrue(SquareState.OPEN == SquareState.getSquareState(' '));

        Assert.assertTrue(SquareState.UNKNOWN == SquareState.getSquareState('T'));
    }
}
