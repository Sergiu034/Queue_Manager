    package org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic;

    import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Server;
    import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Task;

    import java.io.FileWriter;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    public class Scheduler {

        private List<Server> servers;

        private Strategy strategy;

        public Scheduler(int maxNoServers, int maxTasksPerServer) {
            servers = new ArrayList<>(maxNoServers);

            for (int i = 0; i < maxNoServers; i++) {
                Server server = new Server();
                servers.add(server);
                new Thread(server).start(); // Starting the server thread
            }
        }

        public boolean dispatchTask(Task t) {
            if (strategy.addTask(servers, t)) {
                return true;
            } else {
                return false;
            }
        }

        public void changeStrategy(SelectionPolicy policy) {
            switch (policy) {
                case SHORTEST_QUEUE:
                    strategy = new ConcreteStrategyQueue();
                    break;
                case SHORTEST_TIME:
                    strategy = new ConcreteStrategyTime();
                    break;
            }
        }

        public List<Server> getServers() {
            return servers;
        }
    }
