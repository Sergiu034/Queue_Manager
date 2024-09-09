package org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic;

import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Server;
import org.example.pt2024_30226_stoica_sergiu_assignment_2.Model.Task;

import java.util.List;

public interface Strategy {
    boolean addTask(List<Server> servers, Task t);
}
