package se.hig.aod.lab3;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Helper class for testing
 * 
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
public class T
{
    /**
     * Lambda for an action that throws
     * 
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     */
    public interface ThrowingAction
    {
        /**
         * Do stuff!
         * 
         * @throws Throwable
         *             on exception
         */
        public void doAction() throws Throwable;
    }

    /**
     * Assert that an exception is thrown when running an {@link ThrowingAction
     * action}<br>
     * {@link org.junit.Assert#fail Fails} if unexpected exceptions occurs or if
     * there is no exception
     * 
     * @param expected
     *            the exception that we expect
     * @param action
     *            the lambda to run
     */
    public static void assertThrows(Class<? extends Throwable> expected, ThrowingAction action)
    {
        try
        {
            action.doAction();
        }
        catch (Throwable exception)
        {
            if (!expected.isInstance(exception))
                fail("Unexpected exception. Got <" + exception.getClass().getSimpleName() + "> expected <" + expected.getSimpleName() + ">");
            return;
        }
        fail("Expected exception of type <" + expected.getSimpleName() + ">");
    }

    /**
     * Action
     * 
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     */
    interface Action
    {
        public void invoke();
    }

    /**
     * Reroute {@link System#out} to a temporary byte buffer, run {@link Action
     * action} and reset {@link System#out} to the old value<br>
     * {@link org.junit.Assert#fail Fails} on exceptions
     * 
     * @param action
     *            the action to run
     * @return all that was written to {@link System#out} when action was
     *         running
     */
    public static String checkSystemOut(Action action)
    {
        PrintStream old = System.out;

        try (OutputStream stream = new ByteArrayOutputStream())
        {
            PrintStream testStream = new PrintStream(stream);
            System.setOut(testStream);
            action.invoke();
            return stream.toString();
        }
        catch (Exception e)
        {
            fail("Exception occured!\n\t" + e.getMessage());
            return null;
        }
        finally
        {
            System.setOut(old);
        }
    }
}
