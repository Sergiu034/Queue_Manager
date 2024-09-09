package org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic;

import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Server;
import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public boolean addTask(List<Server> servers, Task task) {
        Server earliestServer = servers.get(0);
        int earliestFinishTime = earliestServer.getWaitingPeriod();

        for (Server server : servers) {
            int finishTime = server.getWaitingPeriod();
            if (finishTime < earliestFinishTime) {
                earliestFinishTime = finishTime;
                earliestServer = server;
            }
        }

        if (earliestServer != null) {
            earliestServer.addTask(task);
            return true;
        }
        return false;
    }
}
