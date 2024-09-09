package org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic;

import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Server;
import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public boolean addTask(List<Server> servers, Task task) {
        Server leastLoadedServer = null;
        int minQueueSize = Integer.MAX_VALUE;

        for (Server server : servers) {
            int queueSize = server.getQueueSize();
            if (queueSize < minQueueSize) {
                minQueueSize = queueSize;
                leastLoadedServer = server;
            }
        }

        if (leastLoadedServer != null) {
            leastLoadedServer.addTask(task);
        }
        return false;
    }
}
