package se.hig.aod.lab3;

import static org.junit.Assert.*;
import static se.hig.aod.lab3.T.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import se.hig.aod.lab3.MyPriorityQueue.MyPriorityQueueIsEmptyException;
import se.hig.aod.lab3.MyPriorityQueue.MyPriorityQueueNullNotAllowedException;

/**
 * JUnit test for {@link MyPriorityQueue}
 *
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
public class TestMyPriorityQueue
{
    MyBSTPriorityQueue<Integer> queue;

    static class TestValuesBatch
    {
        final String description;
        final Integer[] values;
        final Integer[] sorted;

        TestValuesBatch(String description, Integer[] values)
        {
            this.description = description;
            this.values = values;

            Arrays.sort(sorted = values.clone(), Collections.reverseOrder()); // Biggest
                                                                              // first
        }
    }

    static TestValuesBatch[] testValues = new TestValuesBatch[]
    {
            new TestValuesBatch("Ordered", new Integer[]
            {
                    new Integer(1000),
                    new Integer(2000),
                    new Integer(3000),
                    new Integer(4000),
                    new Integer(5000),
                    new Integer(6000),
                    new Integer(7000),
                    new Integer(8000),
                    new Integer(9000),
                    new Integer(10000),
                    new Integer(11000),
                    new Integer(12000)
            }),
            new TestValuesBatch("Ordered - reversed", new Integer[]
            {
                    new Integer(12000),
                    new Integer(11000),
                    new Integer(10000),
                    new Integer(9000),
                    new Integer(8000),
                    new Integer(7000),
                    new Integer(6000),
                    new Integer(5000),
                    new Integer(4000),
                    new Integer(3000),
                    new Integer(2000),
                    new Integer(1000)
            }),
            new TestValuesBatch("Ordered - odd front", new Integer[]
            {
                    new Integer(12000),
                    new Integer(2000),
                    new Integer(3000),
                    new Integer(4000),
                    new Integer(5000),
                    new Integer(6000),
                    new Integer(7000),
                    new Integer(8000),
                    new Integer(9000),
                    new Integer(10000),
                    new Integer(11000)
            }),
            new TestValuesBatch("Ordered - reversed - odd front", new Integer[]
            {
                    new Integer(1000),
                    new Integer(11000),
                    new Integer(10000),
                    new Integer(9000),
                    new Integer(8000),
                    new Integer(7000),
                    new Integer(6000),
                    new Integer(5000),
                    new Integer(4000),
                    new Integer(3000),
                    new Integer(2000)
            }),
            new TestValuesBatch("Ordered - odd back", new Integer[]
            {
                    new Integer(2000),
                    new Integer(3000),
                    new Integer(4000),
                    new Integer(5000),
                    new Integer(6000),
                    new Integer(7000),
                    new Integer(8000),
                    new Integer(9000),
                    new Integer(10000),
                    new Integer(1000)
            }),
            new TestValuesBatch("Ordered - reversed - odd back", new Integer[]
            {
                    new Integer(11000),
                    new Integer(10000),
                    new Integer(9000),
                    new Integer(8000),
                    new Integer(7000),
                    new Integer(6000),
                    new Integer(5000),
                    new Integer(4000),
                    new Integer(3000),
                    new Integer(12000)
            }),
            new TestValuesBatch("Random", new Integer[]
            {
                    new Integer(1000),
                    new Integer(2000),
                    new Integer(7000),
                    new Integer(6000),
                    new Integer(9000),
                    new Integer(10000),
                    new Integer(3000),
                    new Integer(45000),
                    new Integer(4000),
                    new Integer(2000),
                    new Integer(11000),
                    new Integer(10000)
            }),
            new TestValuesBatch("All but one", new Integer[]
            {
                    new Integer(1000),
                    new Integer(1000),
                    new Integer(1000),
                    new Integer(1000),
                    new Integer(7000),
                    new Integer(1000),
                    new Integer(1000),
                    new Integer(1000),
                    new Integer(1000),
                    new Integer(1000),
                    new Integer(1000),
            })

    };

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
        assertEquals("Should be size 0 at init", 0, queue.getSizeRecursive());
    }

    /**
     * Test that both {@link MyBSTPriorityQueue#size} and
     * {@link MyBSTPriorityQueue#getSizeRecursive} and works
     */
    @Test
    public void testSize()
    {
        int size = 0;
        for (Integer i : testValues[0].values)
        {
            queue.enqueue(i);
            size++;

            assertEquals("Size is incorrect", size, queue.size());
            assertEquals("Recursive size is incorrect", size, queue.getSizeRecursive());
        }

        for (@SuppressWarnings("unused")
        Integer i : testValues[0].values)
        {
            queue.dequeue();
            size--;

            assertEquals("Size is incorrect", size, queue.size());
            assertEquals("Recursive size is incorrect", size, queue.getSizeRecursive());
        }
    }

    /**
     * Test queueing (order and exceptions)
     */
    @Test
    public void testQueueing()
    {
        for (TestValuesBatch batch : testValues)
        {
            queue = new MyBSTPriorityQueue<Integer>();

            for (Integer i : batch.values)
                queue.enqueue(i);

            for (Integer i : batch.sorted)
                assertEquals("Incorrect order [" + batch.description + "]", i, queue.dequeue());

            assertEquals("Size is incorrect after dequeue [" + batch.description + "]", 0, queue.size());
            assertTrue("Queue not empty after dequeue [" + batch.description + "]", queue.isEmpty());
        }

        assertThrows(MyPriorityQueueNullNotAllowedException.class, () ->
        {
            queue.enqueue(null);
        });
    }

    /**
     * Test getFront
     */
    @Test
    public void testGetFront()
    {
        for (TestValuesBatch batch : testValues)
        {
            queue = new MyBSTPriorityQueue<Integer>();

            for (Integer i : batch.values)
                queue.enqueue(i);

            for (Integer i : batch.sorted)
            {
                assertEquals("getFront is incorrect!", i, queue.getFront());
                queue.dequeue();
            }
        }
    }

    /**
     * Test methods that should throw when the queue is empty
     */
    @Test
    public void testExceptionsOnEmptyQueue()
    {
        assertThrows(MyPriorityQueueIsEmptyException.class, () ->
        {
            queue.dequeue();
        });

        assertThrows(MyPriorityQueueIsEmptyException.class, () ->
        {
            queue.getFront();
        });
    }

    /**
     * Test that clear works
     */
    @Test
    public void testClear()
    {
        for (Integer i : testValues[0].values)
            queue.enqueue(i);

        queue.clear();

        assertEquals("Size is incorrect after clear", 0, queue.size());
        assertEquals("Recursive size is incorrect after clear", 0, queue.getSizeRecursive());
        assertTrue("Queue not empty after clear", queue.isEmpty());

        queue.clear();

        assertEquals("Size is incorrect after another clear", 0, queue.size());
        assertEquals("Recursive size is incorrect after another clear", 0, queue.getSizeRecursive());
        assertTrue("Queue not empty after another clear", queue.isEmpty());
    }
}
