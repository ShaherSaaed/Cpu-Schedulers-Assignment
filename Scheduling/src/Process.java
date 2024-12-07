import java.awt.Color;

public class Process {
    private final int id;
    private final String name;
    private final Color color;
    private final int priority;
    private final int arrivalTime;
    private int burstTime;
    private int boostedPriority;
    private int comparedBurstTime;
    private int remainingBurstTime;
    private int comparedRemainingBurstTime;
    private int quantum;
    private double fcaiFactor;
    public boolean isCompleted;
    private int startTime;
    private int completionTime;
    private int turnaroundTime;
    private int waitingTime;
    private int waitTime;

    // Constructor
    public Process(int id, String name, Color color, int priority, int arrivalTime, int burstTime, int quantum) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.comparedBurstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.quantum = quantum;
        this.startTime = -1;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.fcaiFactor = 0.0;
        this.waitTime = 0;
        this.color = color;
    }

    ///////////////////////////////////////////////////////Setters///////////////////////////////////////////////////////
    public void setRemainingBurstTime(int remainingBurstTime) {
        this.comparedRemainingBurstTime = remainingBurstTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void updateFcaiFactor(double v1, double v2) {
        this.fcaiFactor = (10 - priority) + (arrivalTime / v1) + (remainingBurstTime / v2);
    }

    public void setComparedRemainingBurstTime(int comparedRemainingBurstTime) {
        this.comparedRemainingBurstTime = comparedRemainingBurstTime;
    }

    ///////////////////////////////////////////////////////Getters///////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void incrementWaitTime() {this.waitTime++;}
    public void boostPriority(int boost) {this.boostedPriority = this.priority - boost;}

    public int getBoostedPriority() {return boostedPriority;}

    public void resetWaitTime() {this.waitTime = 0;}

    public int getWaitTime() {
        return waitTime;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getComparedRemainingBurstTime() {
        return comparedRemainingBurstTime;
    }
}
