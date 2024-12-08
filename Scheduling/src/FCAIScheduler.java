import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FCAIScheduler implements Scheduler {
    private final ArrayList<Process> processes;
    private final PriorityQueue<Process> readyQueue;
    private int currentTime;

    public FCAIScheduler(ArrayList<Process> processes) {
        this.processes = processes;
        this.readyQueue = new PriorityQueue<>(Comparator.comparingDouble(this::calculateFcaiFactor));
        this.currentTime = 0;
    }

    @Override
    public void execute() {
        double v1 = calculateV1();
        double v2 = calculateV2();
        for (Process process : processes) {
            process.updateFcaiFactor(v1, v2);
            System.out.println("Process P" + process.getId() + " Fcai Factor updated: " + process.getFcaiFactor());
        }
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            addArrivedProcesses();

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }
            Process currentProcess = readyQueue.poll();
            executeProcess(currentProcess, v1, v2);
        }
    }

    private void addArrivedProcesses() {
        processes.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime && !process.isCompleted) {
                readyQueue.offer(process);
                System.out.println("Process P" + process.getId() + " added to ready queue at time " + currentTime);
                return true;
            }
            return false;
        });
    }

    private void executeProcess(Process process, double v1, double v2) {
        int quantum = process.getQuantum();
        int executionTime = (int) (quantum * 0.4);
        int actualExecutionTime = Math.min(executionTime, process.getRemainingBurstTime());
        process.setRemainingBurstTime(process.getRemainingBurstTime() - actualExecutionTime);
        currentTime += actualExecutionTime;

        System.out.println("Executing P" + process.getId() + " for " + actualExecutionTime + " units, remaining burst: " + process.getRemainingBurstTime());

        if (process.getRemainingBurstTime() > 0) {
            int remainingQuantum = quantum - actualExecutionTime;
            if (remainingQuantum > 0) {
                process.setQuantum(remainingQuantum + 2);
            } else {
                process.setQuantum(quantum + actualExecutionTime);
            }
            process.updateFcaiFactor(v1, v2);
            readyQueue.offer(process);
        } else {
            process.setCompleted(true);
            process.setCompletionTime(currentTime);
            process.setTurnaroundTime(currentTime - process.getArrivalTime());
            process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
            System.out.println("Process P" + process.getId() + " completed at time " + currentTime);
        }
    }

    private double calculateFcaiFactor(Process process) {
        return (10 - process.getPriority()) +
                (process.getArrivalTime() / calculateV1()) +
                (process.getRemainingBurstTime() / calculateV2());
    }

    private double calculateV1() {
        int lastArrivalTime = processes.stream().mapToInt(Process::getArrivalTime).max().orElse(1);
        return (double) lastArrivalTime / 10.0;
    }

    private double calculateV2() {
        int maxBurstTime = processes.stream().mapToInt(Process::getBurstTime).max().orElse(1);
        return (double) maxBurstTime / 10.0;
    }

    @Override
    public void setVisible(boolean b) {
    }

    @Override
    public void updateExecutionHistory() {
    }

    @Override
    public void updateStatistics(String scheduler, int n, double awt, double atat) {
    }
}
