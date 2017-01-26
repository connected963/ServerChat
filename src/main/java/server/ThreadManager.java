package server;

import configurations.Parameters;
import request.tasks.RequestManagerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by pedro.sirtoli on 25/01/2017.
 */
public class ThreadManager {

    private static final Logger LOGGER = Logger.getLogger(ThreadManager.class.getName());

    private final ExecutorCompletionService<Void> executorCompletionService;
    private final AtomicLong countOfConnections;
    private final List<RequestManagerTask> tasks;
    private boolean isCanceled;

    public ThreadManager() {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        this.executorCompletionService = new ExecutorCompletionService<>(executorService);
        this.tasks = new ArrayList<>();
        this.countOfConnections = new AtomicLong(0);
        this.isCanceled = false;

    }

    public void manangeServerRequests() {

        addNewThread();

        while(!isCanceled) {

            try {
                Thread.sleep(Parameters.timeToUpdateThreads);

                if (this.countOfConnections.get() / tasks.size() >= Parameters.numberOfRequisitionsPerTimeToAddNewThread && this.tasks.size() < Parameters.maxNumberOfThreads) {
                    addNewThread();
                }
                else if (this.countOfConnections.get() / tasks.size() <= Parameters.numberOfRequisitionsPerTimeToRemoveThread && this.tasks.size() > 1) {
                    removeThread();
                }

                System.out.println("Count of connections: " + this.countOfConnections.get());
                this.countOfConnections.set(0);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
            System.out.println("Count of threads: " + this.tasks.size());
        }

        cancelAllThreads();
    }

    public void cancel() {
        this.isCanceled = true;
    }

    private void addNewThread() {
        final RequestManagerTask requestManagerTask = new RequestManagerTask(countOfConnections);

        this.executorCompletionService.submit(requestManagerTask);
        this.tasks.add(requestManagerTask);
    }

    private void removeThread() {
        final Consumer<RequestManagerTask> cancel = RequestManagerTask::cancel;
        final Consumer<RequestManagerTask> removeOfTheList = tasks::remove;

        this.tasks.stream().findAny().ifPresent(cancel.andThen(removeOfTheList));
    }

    private void cancelAllThreads() {
        this.tasks.forEach(RequestManagerTask::cancel);
    }
}
