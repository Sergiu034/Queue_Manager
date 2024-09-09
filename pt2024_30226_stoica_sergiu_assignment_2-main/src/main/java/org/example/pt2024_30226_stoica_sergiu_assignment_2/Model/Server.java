package org.example.pt2024_30226_stoica_sergiu_assignment_2.Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Server extends Thread {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server() {
        tasks = new ArrayBlockingQueue<>(1000);
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while(true){
            try {
                if(!tasks.isEmpty()) {
                    Task nextTask = tasks.peek();
                    if(nextTask.getServiceTime()==0){
                        nextTask= tasks.take();
                    }
                    Thread.sleep(1000);
                    nextTask.decreaseServiceTime();
                    waitingPeriod.decrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public BlockingQueue<Task> getTasks(){
        return tasks;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public int getQueueSize() {
        return tasks.size();
    }
}