package org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic;

import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Server;
import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SimulationManager extends Thread {
    // Configuration parameters
    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int numberOfServers;
    private int numberOfClients;
    private SelectionPolicy selectionPolicy;

    // Queue management and client distribution
    private Scheduler scheduler;
    private List<Task> generatedTasks;

    private FileWriter fileWriter;

    private float averServiceTime = 0;
    private float averWaitingTime = 0;
    private int peakHour = 0;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime, int minArrivalTime,
                             int numberOfServers, int numberOfClients, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.selectionPolicy = selectionPolicy;

        scheduler = new Scheduler(numberOfServers, Integer.MAX_VALUE);
        scheduler.changeStrategy(selectionPolicy); // Initialize with the desired policy
        generatedTasks = Collections.synchronizedList(new ArrayList<>());

        generatedTasks = generateNRandomTasks();

        try {
            fileWriter = new FileWriter("Log_Queues.txt", true); // Open in append mode
        } catch (IOException e) {
            throw new RuntimeException("Error initializing FileWriter", e);
        }
    }

    public List<Task> generateNRandomTasks() {
        generatedTasks = new ArrayList<>();

        for (int i = 0; i < numberOfClients; i++) {
            int processingTime = (int) (Math.random() * (maxProcessingTime - minProcessingTime + 1)) + minProcessingTime;
            int arrivalTime = (int) (Math.random() * (maxArrivalTime - minArrivalTime + 1)) + minArrivalTime;;
            generatedTasks.add(new Task(i+1, arrivalTime, processingTime));
            averWaitingTime += processingTime;
        }

        this.averServiceTime = this.averWaitingTime / (float) this.numberOfClients;
        this.averWaitingTime /= (float) (this.numberOfClients * this.numberOfServers);

        Collections.sort(generatedTasks);
        return generatedTasks;
    }

    public boolean checkQueue(){
        boolean ok = true;

        if(!generatedTasks.isEmpty())
            return true;
        else {
            for (int i = 0; i < scheduler.getServers().size(); i++) {
                Server server = scheduler.getServers().get(i);
                BlockingQueue<Task> tasks = new ArrayBlockingQueue<>(10000);
                tasks.addAll(server.getTasks());

                if (!tasks.isEmpty()) {
                    return true;
                } else {
                    ok = false;
                }
            }
        }
        return ok;
    }

    @Override
    public void run() {
        int currentTime = 0;
        int maxClientsPerHour = 0;

        while (currentTime  < timeLimit) {
            Iterator<Task> ListTasks = generatedTasks.iterator();
            while (ListTasks.hasNext()) {
                Task t = ListTasks.next();
                if (t.getArrivalTime() == currentTime) {
                    if (scheduler.dispatchTask(t)) {
                        ListTasks.remove();
                    }
                }
            }

            int clientsPerHour = 0;

            try {
                fileWriter.append("\nTime " + currentTime + "\nWaiting: ");
                System.out.print("\nTime " + currentTime + "\nWaiting: ");
                for (Task task : generatedTasks) {
                    fileWriter.append(task + ";");
                    System.out.print(task + ";");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for(int i = 0; i < scheduler.getServers().size(); i++){
                clientsPerHour +=scheduler.getServers().get(i).getQueueSize();
            }

            if(clientsPerHour > maxClientsPerHour){
                maxClientsPerHour = clientsPerHour;
                peakHour = currentTime;
            }

            printQueues();
            currentTime++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(!checkQueue()) {
                break;
            }
        }

        try {
            fileWriter.append("\nTime " + currentTime + "\nWaiting: ");
            System.out.print("\nTime " + currentTime + "\nWaiting: ");
            for(Task t : generatedTasks){
                fileWriter.append(t.toString() +";");
                System.out.print(t.toString() +";");
            }

            printQueues();

            fileWriter.append("\nAverage waiting time: "+ averWaitingTime);
            fileWriter.append("\nAverage Service time: "+ averServiceTime);
            fileWriter.append("\nPeak hour: "+ peakHour);

            fileWriter.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void printQueues()
    {
        for(int i=0; i<scheduler.getServers().size(); i++)
        {
            Server server =scheduler.getServers().get(i);
            BlockingQueue<Task> tasks = new ArrayBlockingQueue<>(10000);

            tasks.addAll(server.getTasks());

            if(tasks.isEmpty()){
                try {
                    fileWriter.append("\nQueue " + (i + 1) + " : closed");
                    System.out.print("\nQueue " + (i + 1) + " : closed");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    fileWriter.append("\nQueue " + (i + 1) + " : ");
                    System.out.print("\nQueue " + (i + 1) + " : " );
                    for(Task t : tasks){
                        fileWriter.append(t.toString() +";");
                        System.out.print(t.toString() +";");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        try{
            fileWriter.append("\n");
            System.out.print("\n");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}