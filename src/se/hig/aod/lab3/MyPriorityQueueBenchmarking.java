package se.hig.aod.lab3;

import se.hig.aod.lab3.Benchmarker.BenchmarkResult;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Program that is benchmarking priority queues
 * 
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
public class MyPriorityQueueBenchmarking
{
    static ArrayList<Integer> data_6400;
    static ArrayList<Integer> data_640000;

    /**
     * Runs the benchmarking (OBS can take some time!)
     * 
     * @param args
     *            - standard input
     * 
     * @throws Exception
     *             - on exceptions
     */
    public static void main(String[] args) throws Exception
    {
        data_6400 = loadList("other/data/data_6400.txt");
        data_640000 = loadList("other/data/data_640000.txt");

        testPriorityQueue(new MyBSTPriorityQueue<Integer>().getClass());
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    static long testPriorityQueue(Class<? extends MyPriorityQueue> queueClass) throws Exception
    {
        Action<Prepare>[] prepareCases = new Action[]
        {
                new Action<Prepare>("Unsorted",
                        (array) ->
                        {

                        }),
                new Action<Prepare>("Sorted",
                        (array) ->
                        {
                            Collections.sort(array);
                        }),

                new Action<Prepare>("Sorted and reversed",
                        (array) ->
                        {
                            Collections.sort(array, Collections.reverseOrder());
                        })
        };

        Action<Messure>[] actions = new Action[]
        {
                new Action<Messure>("Insert",
                        (queue) ->
                        {
                            for (Integer i : data_6400)
                            {
                                queue.enqueue(i);
                            }
                        }),

                new Action<Messure>("Remove",
                        (queue) ->
                        {
                            for (Integer i : data_6400)
                            {
                                queue.dequeue();
                            }
                        })
        };

        System.out.println("Queue type: " + queueClass.getSimpleName());

        for (Action<Prepare> prepare : prepareCases)
        {
            System.out.println("\t" + prepare.description);

            for (Action<Messure> action : actions)
            {
                System.out.println("\t\t" + action.description);

                for (int amount = 10000; amount <= 640000; amount *= 2)
                {
                    // 1
                    // Create queue
                    MyPriorityQueue<Integer> queue = queueClass.newInstance();

                    // 2
                    // prepare data
                    List<Integer> data = Arrays.asList(data_640000.subList(0, amount).toArray(new Integer[0]));
                    prepare.action.prepare(data);

                    // actual loading
                    for (Integer i : data)
                        queue.enqueue(i);

                    // 3
                    // benchmark operation with 6400.txt

                    BenchmarkResult result = new Benchmarker()
                    {
                        @Override
                        void run() throws Exception
                        {
                            action.action.messure(queue);
                        }
                    }.benchmark();

                    System.out.println("\t\t\t" + amount + ": " + result.getMean());
                }
            }
        }
        return 0;
    }

    interface Prepare
    {
        void prepare(List<Integer> array) throws Exception;
    }

    interface Messure
    {
        void messure(MyPriorityQueue<Integer> queue) throws Exception;
    }

    @SuppressWarnings("hiding")
    static class Action<T>
    {
        public final String description;
        public final T action;

        Action(String description, T action)
        {
            this.description = description;
            this.action = action;
        }
    }

    /**
     * Reads integers from a text file an returns an array of them<br>
     * <br>
     * Textfile example:
     * 
     * <pre>
     * <code>
     *  435435345
     *  234343244
     *  -45435
     *  23
     * </code>
     * </pre>
     * 
     * @param path
     *            the path to the file to read
     * @return the lines in a array list
     * 
     * @throws FileNotFoundException
     *             - If file not found
     * @throws IOException
     *             - If error on reading file
     * 
     * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
     * @author Peter Jenke
     * @version 2015-12-04
     */
    public static ArrayList<Integer> loadList(String path) throws FileNotFoundException, IOException
    {
        try (BufferedReader in = new BufferedReader(new FileReader(path)))
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            String l;
            while ((l = in.readLine()) != null)
            {
                list.add(Integer.parseInt(l));
            }
            return list;
        }
    }
}
