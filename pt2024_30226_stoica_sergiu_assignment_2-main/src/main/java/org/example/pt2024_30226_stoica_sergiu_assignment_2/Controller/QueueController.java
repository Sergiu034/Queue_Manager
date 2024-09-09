package org.example.pt2024_30226_stoica_sergiu_assignment_2.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic.*;

import static org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic.SelectionPolicy.SHORTEST_QUEUE;
import static org.example.pt2024_30226_stoica_sergiu_assignment_2.BusinessLogic.SelectionPolicy.SHORTEST_TIME;

public class QueueController {
    @FXML
    private TextField numberOfClients;

    @FXML
    private TextField minimumArrivalTime;

    @FXML
    private TextField maximumArrivalTime;

    @FXML
    private TextField numberOfQueues;

    @FXML
    private TextField minimumServiceTime;

    @FXML
    private TextField maximumServiceTime;

    @FXML
    private TextField simulationInterval;

    @FXML
    private RadioButton timeStrategy;

    @FXML
    private RadioButton shortestQueueStrategy;

    @FXML
    private Button simulationButton;

    @FXML
    private Label errorLabel;

    @FXML
    protected void submitButton() {

        int numClients = getNumberOfClients();
        int numQueues = getNumberOfQueues();

        int minArrTime = getMinimumArrivalTime();
        int maxArrTime = getMaximumArrivalTime();

        int minServiceTime = getMinimumServiceTime();
        int maxServiceTime = getMaximumServiceTime();

        int simInterval = getSimulationInterval();
        SelectionPolicy strategy = getStrategy();

        if(numQueues <= 0 || numClients <= 0 || minArrTime < 0 || maxArrTime < minArrTime || minServiceTime < 0 || maxServiceTime < minServiceTime) {

            String errorMessage = "Please check the input values:";
            if (numClients <= 0) errorMessage += " Number of Clients must be greater than 0.";
            if (numQueues <= 0) errorMessage += " Number of Queues must be greater than 0.";
            if (minArrTime < 0) errorMessage += " Minimum Arrival Time cannot be negative.";
            if (maxArrTime < minArrTime) errorMessage += " Maximum Arrival Time must be greater than Minimum Arrival Time.";
            if (minServiceTime < 0) errorMessage += " Minimum Service Time cannot be negative.";
            if (maxServiceTime < minServiceTime) errorMessage += " Maximum Service Time must be greater than Minimum Service Time.";

            System.out.println(errorMessage);
            errorLabel.setText(errorMessage);
            return;
        }

        SimulationManager simManager = new SimulationManager(simInterval,maxServiceTime, minServiceTime, maxArrTime, minArrTime, numQueues, numClients, strategy);
        Thread simThread = new Thread(simManager);
        simThread.start();
    }

    public Integer getNumberOfClients() {
        try {
            return Integer.parseInt(numberOfClients.getText());
        } catch(Exception e){
            return 4; // Default 4 clients
        }
    }

    public Integer getMinimumArrivalTime() {
        try {
            return Integer.parseInt(minimumArrivalTime.getText());
        } catch(Exception e){
            return 2; // Default minimum arrival time
        }
    }

    public Integer getMaximumArrivalTime() {
        try {
            return Integer.parseInt(maximumArrivalTime.getText());
        } catch (Exception e){
            return 30; // Default maximum arrival time
        }
    }

    public Integer getNumberOfQueues() {
        try {
            return Integer.parseInt(numberOfQueues.getText());
        } catch(Exception e){
            return 2; // Default number of queues
        }
    }

    public Integer getMinimumServiceTime() {
        try {
            return Integer.parseInt(minimumServiceTime.getText());
        } catch (Exception e){
            return 2; // Default minimum service time
        }
    }

    public Integer getMaximumServiceTime() {
        try {
            return Integer.parseInt(maximumServiceTime.getText());
        } catch (Exception e){
            return 4; // Default maximum service time
        }
    }

    public Integer getSimulationInterval() {
        try {
            return Integer.parseInt(simulationInterval.getText());
        } catch (Exception e){
            return 60; // Default sim interval
        }
    }

    private SelectionPolicy getStrategy() {
        if(shortestQueueStrategy.isSelected()){
            return SHORTEST_QUEUE;
        }
        else {
            return SHORTEST_TIME;
        }
    }
}