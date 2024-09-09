package org.example.pt2024_30226_stoica_sergiu_assignment_2.Model;

public class Task implements Comparable{

    private int Id;
    private int arrivalTime;
    private int serviceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.Id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getId(){
        return Id;
    }

    public void decreaseServiceTime(){
        if(this.serviceTime>0) {
            this.serviceTime = this.serviceTime - 1;
        }
    }

    @Override
    public String toString() {
        return "(" +
                + Id +
                ", " + arrivalTime +
                ", " + serviceTime +
                ')';
    }

    @Override
    public int compareTo(Object o) {
        Task t = (Task) o;
        if(this.arrivalTime < t.getArrivalTime()){
            return 0;
        } else {
            return 1;
        }
    }
}