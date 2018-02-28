package me.bradley.cs246finalproject;

/**
 * Created by Chris on 2/27/2018.
 */

import org.junit.Test;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UnitTest {

    @Test
    public void protonsEqualDesiredAtomTest() {
        assertEquals(MainActivity.getProtons(), 0);
    }

}
