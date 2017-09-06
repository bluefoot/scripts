package info.bluefoot.scripts.threadpool;

import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread pool. <br />
 * This will create a number of threads that should be given at construction. <br />
 * While there's still something to be executed, the threads will work. After that,
 * they will not be available anymore (i.e. die). So this is not an "infinite" pool. <br />
 * Three steps: <br />
 * <ol>
 * <li>Instantiate and pass the number of threads in the pool</li>
 * <li>{@link WorkerPool#add(Runnable)} how many {@link Runnable} instances you want to be executed</li>
 * <li>Start the execution by firing {@link WorkerPool#execute()}</li>
 * </ol>
 * <em>NOTE: you can't add anymore instances after the {@link WorkerPool#execute()}
 * method starts.</em> <br />
 * Initially based on <a href="http://www.ibm.com/developerworks/library/j-jtp0730/index.html">this article</a>.
 * 
 * 
 * @author gewton
 *
 */
public class WorkerPool {
    private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedList<Runnable> queue;
    private boolean started = false;
    private Runnable finishedCallback;
    protected static final Logger logger = LoggerFactory.getLogger(WorkerPool.class);

    /**
     * Constructs a Worker with a max number of threads. If there's more jobs 
     * than threads, some waiting will eventually occour.
     * @param poolSize the number of threads
     */
    public WorkerPool(int poolSize) {
        logger.info(String.format("Starting pool with %s threads", poolSize));
        this.nThreads = poolSize;
        queue = new LinkedList<Runnable>();
        threads = new PoolWorker[poolSize];
    }

    /**
     * Adds a job to the execution queue.
     * @param r a job to be added
     * @throws RuntimeException in case of the execution already begun.
     * @see Runnable
     */
    public void add(Runnable r) {
        if(started) {
            throw new RuntimeException("Can't add anymore jobs at this pont. The execution has already started.");
        }
        synchronized (queue) {
            logger.info(String.format("Adding a job to the queue"));
            queue.addLast(r);
        }        
    }
    
    /**
     * Start the execution of the jobs
     */
    public void execute() {
        started = true;
        final Date initialTime = new Date();
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            logger.info(String.format("Starting thread %s, id:%s", i, threads[i].getId()));
            threads[i].start();
        }
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                while(stillSomethingAlive()) {}
                long time = (System.currentTimeMillis() - initialTime.getTime())/1000;
                logger.info(String.format("Finished after %s seconds", String.valueOf(time)));
                if(finishedCallback!=null)
                    new Thread(finishedCallback).run();
            }
        }).start();
    }

    /**
     * Checks if there is still some active thread
     * @return true if there is any thread still alive or false otherwise
     * @see Thread#isAlive()
     */
    private boolean stillSomethingAlive() {
        for (int i = 0; i < nThreads; i++) {
            if(threads[i].isAlive()) {
                return true;
            }
        }
        return false;
    }
    
    public void setFinishedCallback(Runnable r) {
        this.finishedCallback=r;
    }

    private class PoolWorker extends Thread {

        public void run() {
            Runnable r;
            int times = 0;
            
            if(logger.isDebugEnabled()) {
                logger.debug(String.format("Thread %s started its execution", this.getId()));
            }
            
            while (true) {
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        if(logger.isDebugEnabled()) {
                            logger.debug(String.format("Queue is empty, thread %s dismissed after running %s times", this.getId(), times));
                        }
                        break;
                    }

                    r = queue.removeFirst();
                    times++;
                }

                if(logger.isDebugEnabled()) {
                    logger.debug(String.format("Thread %s will run NOW", this.getId()));
                }
                
                try {
                    r.run();
                } catch (RuntimeException e) {
                    logger.error("Error while executing thread " + this.getId(), e);
                }
            }
        }
    }
}