package se.hig.aod.lab3;

import static org.junit.Assert.*;
import static se.hig.aod.lab3.T.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.hig.aod.lab3.MyPriorityQueue.MyPriorityQueueIsEmptyException;

/**
 * JUnit test for {@link MyPriorityQueue}
 *
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
public class TestMyPriorityQueue
{
    MyPriorityQueue<Integer> queue;

    static Integer[] testValues = new Integer[]
    {
            new Integer(1),
            new Integer(2),
            new Integer(7),
            new Integer(6),
            new Integer(9),
            new Integer(10),
            new Integer(3),
            new Integer(45),
            new Integer(4),
            new Integer(2),
            new Integer(11),
            new Integer(10)
    };

    static Integer[] sortedTestValues;

    /**
     * Set up sorted values
     */
    @BeforeClass
    static public void setUpSortedValues()
    {
        sortedTestValues = testValues.clone();

        // Biggest first
        Arrays.sort(sortedTestValues, Collections.reverseOrder());
    }

    /**
     * Init the queue
     */
    @Before
    public void setUp()
    {
        queue = new MyBSTPriorityQueue<Integer>();
    }

    /**
     * Ensure that it is alright at init
     */
    @Test
    public void testInit()
    {
        assertTrue("Should be empty at init", queue.isEmpty());
        assertFalse("Should not be full at init", queue.isFull());
        assertEquals("Should be size 0 at init", 0, queue.size());
        assertEquals("Should be size 0 at init", 0, ((MyBSTPriorityQueue<Integer>) queue).getSizeRecursive());
    }

    /**
     * Test queueing
     */
    @Test
    public void testQueueing()
    {
        for (int j = 0; j < testValues.length; j++)
        {
            queue.enqueue(testValues[j]);
            assertEquals("size incorrect", j + 1, queue.size());
            assertEquals("size incorrect", j + 1, ((MyBSTPriorityQueue<Integer>) queue).getSizeRecursive());
        }

        for (int j = 0; j < sortedTestValues.length; j++)
        {
            assertEquals("Incorrect order", sortedTestValues[j], queue.dequeue());
        }

        assertEquals("size incorrect", 0, queue.size());
        assertEquals("size incorrect", 0, ((MyBSTPriorityQueue<Integer>) queue).getSizeRecursive());
        assertTrue("Queue not empty", queue.isEmpty());

        assertThrows(MyPriorityQueueIsEmptyException.class, () ->
        {
            queue.dequeue();
        });
    }
}
