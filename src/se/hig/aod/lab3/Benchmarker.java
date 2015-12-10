package se.hig.aod.lab3;

import java.util.LinkedList;
import java.util.List;

/**
 * Benchmark code<br>
 * <br>
 * Code to benchmark is to be overrided in run()<br>
 * <br>
 * Example:
 * 
 * <pre>
 * <code>
 * System.out.println(new {@link Benchmarker}()
 * {
 *     {@literal @Override}
 *     void run()
 *     {
 *         // do stuff
 *     }
 * }.{@link #benchmark}());
 * </code>
 * </pre>
 *
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
abstract class Benchmarker
{
    BenchmarkLogger logger;
    boolean runned = false;
    long start;

    public Benchmarker(BenchmarkLogger logger)
    {
        this.logger = logger;
    }

    public Benchmarker()
    {
        logger = new BenchmarkLogger()
        {
            void log(Object obj)
            {
                System.out.println(obj);
            }
        };
    }

    abstract void run() throws Exception;

    public BenchmarkResult benchmark()
    {
        return benchmark(1);
    }

    public BenchmarkResult benchmark(int times)
    {
        if (runned)
            throw new BenchmarkAlreadyRunningException();
        runned = true;

        long[] durations = new long[times];

        for (int i = 0; i < times; i++)
        {
            try
            {
                start = System.currentTimeMillis();

                run();

                durations[i] = System.currentTimeMillis() - start;
            }
            catch (Exception e)
            {
                throw new BenchmarkExceptionWhenRunningException(e);
            }
        }

        return new BenchmarkResult(durations);
    }

    /**
     * Adds some overhead by jumping in methods, but we try to make it as low as
     * possible
     * 
     * @param action
     *            - the action to run
     */
    void runUnbenchmarked(UnbenchmarkedAction action)
    {
        long subStart = System.currentTimeMillis();

        action.run();

        long duration = System.currentTimeMillis() - subStart;

        start += duration;
    }

    void log(Object obj)
    {
        runUnbenchmarked(() ->
        {
            logger.log(obj);
        });
    }

    abstract class BenchmarkLogger
    {
        abstract void log(Object obj);
    }

    @SuppressWarnings("serial")
    class BenchmarkException extends RuntimeException
    {
        BenchmarkException()
        {
            
        }
        
        BenchmarkException(Exception e)
        {
            super(e);
        }
    }

    @SuppressWarnings("serial")
    class BenchmarkAlreadyRunningException extends BenchmarkException
    {

    }

    @SuppressWarnings("serial")
    class BenchmarkExceptionWhenRunningException extends BenchmarkException
    {
        BenchmarkExceptionWhenRunningException(Exception e)
        {
            super(e);
        }
    }

    interface UnbenchmarkedAction
    {
        void run();
    }

    class BenchmarkResult
    {
        public final long[] results;

        BenchmarkResult(long[] results)
        {
            this.results = results;
        }

        BenchmarkResult(long result)
        {
            this.results = new long[] { result };
        }

        public long getMean()
        {
            long mean = 0;
            for (long l : results)
            {
                mean += l;
            }

            mean /= results.length;

            return mean;
        }

        public long getMax()
        {
            long max = results[0];

            for (long l : results)
            {
                if (l > max)
                    max = l;
            }

            return max;
        }

        public long getMin()
        {
            long min = results[0];

            for (long l : results)
            {
                if (l < min)
                    min = l;
            }

            return min;
        }

        @Override
        public String toString()
        {
            return "[ " + getMin() + " , " + getMean() + " , " + getMax() + " ]";
        }
    }

    public static Benchmarker prepeareBenchmark(BenchmarkMethod method)
    {
        return new Benchmarker()
        {
            @Override
            void run()
            {
                method.run();
            }
        };
    }

    public static BenchmarkResult runBenchmark(BenchmarkMethod method, int times)
    {
        return prepeareBenchmark(method).benchmark(times);
    }

    public static BenchmarkResult runBenchmark(BenchmarkMethod method)
    {
        return runBenchmark(method, 1);
    }

    public interface BenchmarkMethod
    {
        void run();
    }

    List<PrepareBenchmarkMethod<?>> prepareMethods = new LinkedList<PrepareBenchmarkMethod<?>>();

    @SuppressWarnings("hiding")
    public <T> Benchmarker prepare(PrepareBenchmarkMethod<T> method)
    {
        prepareMethods.add(method);
        return this;
    }

    @SuppressWarnings("hiding")
    public interface PrepareBenchmarkMethod<T>
    {
        void run(T data);
    }
}
