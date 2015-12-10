package se.hig.aod.lab3;

/**
 * Interface that describes a priority queue
 * 
 * @param <T>
 *            Type to store
 * 
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
@SuppressWarnings("hiding")
public interface MyPriorityQueue<T extends Comparable<? super T>>
{
    /**
     * Clears the queue
     */
    public void clear();

    /**
     * Check if the queue is empty
     * 
     * @return true if the queue is empty, else false
     */
    public boolean isEmpty();

    /**
     * Check if the queue is full
     * 
     * @return true if the queue is full, else false
     */
    public boolean isFull();

    /**
     * Get the size
     * 
     * @return the size of the queue
     */
    public int size();

    /**
     * Add an element to the queue
     * 
     * @param element
     *            the element to add
     * @throws MyPriorityQueueIsFullException
     *             if the queue was full
     */
    public void enqueue(T element);

    /**
     * Remove an element from the queue
     * 
     * @return the element that was removed
     * @throws MyPriorityQueueIsEmptyException
     *             if the queue was empty
     * @throws MyPriorityQueueNullNotAllowedException
     *             if there was an attempt to add null
     */
    public T dequeue();

    /**
     * Peek on the next element to be removed
     * 
     * @return the first element in the queue
     * @throws MyPriorityQueueIsEmptyException
     *             if the queue was empty
     */
    public T getFront();

    /**
     * An exception that signals that there was a problem with the queue
     *
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     */
    @SuppressWarnings("serial")
    public class MyPriorityQueueException extends RuntimeException
    {
        /**
         * Create a new exception
         * 
         * @param message
         *            the reason for this exception
         */
        public MyPriorityQueueException(String message)
        {
            super(message);
        }
    }

    /**
     * An exception that signals that there was an attempt to add elements to a
     * full queue
     *
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     */
    @SuppressWarnings("serial")
    public class MyPriorityQueueIsFullException extends MyPriorityQueueException
    {
        /**
         * Create a new exception
         * 
         * @param message
         *            the reason for this exception
         */
        public MyPriorityQueueIsFullException(String message)
        {
            super(message);
        }
    }

    /**
     * An exception that signals that there was an attempt to remove an element
     * from an empty queue
     *
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     */
    @SuppressWarnings("serial")
    public class MyPriorityQueueIsEmptyException extends MyPriorityQueueException
    {
        /**
         * Create a new exception
         * 
         * @param message
         *            the reason for this exception
         */
        public MyPriorityQueueIsEmptyException(String message)
        {
            super(message);
        }
    }

    /**
     * An exception that signals that there was an attempt to add null
     *
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     */
    @SuppressWarnings("serial")
    public class MyPriorityQueueNullNotAllowedException extends MyPriorityQueueException
    {
        /**
         * Create a new exception
         * 
         * @param message
         *            the reason for this exception
         */
        public MyPriorityQueueNullNotAllowedException(String message)
        {
            super(message);
        }
    }
    
    /**
     * An exception that SHOULD NOT HAPPEN
     *
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     */
    @SuppressWarnings("serial")
    public class MyPriorityQueueInternalException extends MyPriorityQueueException
    {
        /**
         * Create a new exception
         * 
         * @param message
         *            the reason for this exception
         */
        public MyPriorityQueueInternalException(String message)
        {
            super(message);
        }
    }
}
