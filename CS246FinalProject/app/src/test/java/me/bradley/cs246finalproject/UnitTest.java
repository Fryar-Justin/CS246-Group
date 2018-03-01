package me.bradley.cs246finalproject;

/**
 * Created by Chris on 2/27/2018.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UnitTest {

    @Mock
    Element testElement = mock(Element.class);

    @Test
    public void protonsEqualDesiredAtomTest() {
        // this method requires methods to be static.
        //   using mock does not require it to be static.
        //assertEquals(MainActivity.getProtons(), 0);
    }

    @Test
    public void protEqualDesiredAtomTest() {
        assertEquals(testElement.getProtons(), 0);
    }

}
