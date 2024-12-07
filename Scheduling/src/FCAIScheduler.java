import java.util.ArrayList;

public class FCAIScheduler implements Scheduler {

    ArrayList<Process> processes;

    FCAIScheduler(ArrayList<Process> processes) {
        this.processes = processes;
    }

    @Override
    public void execute() {
        int lastArrivalTime = processes.stream().mapToInt(Process::getArrivalTime).max().orElse(0);
        int maxBurstTime = processes.stream().mapToInt(Process::getBurstTime).max().orElse(0);
        double V1 = lastArrivalTime / 10.0;
        double V2 = maxBurstTime / 10.0;

        for (Process p : processes) {
            p.updateFcaiFactor(V1, V2);
        }

        System.out.printf("%-8s%-10s%-15s%-20s%-20s%-10s%-20s%-30s\n",
                "Time", "Process", "Executed Time", "Remaining Burst Time",
                "Updated Quantum", "Priority", "FCAI Factor", "Action - Details");
        System.out.println("---------------------------------------------------------------------------------------------------------------");

        int currentTime = 0;

        while (true) {
            boolean allCompleted = true;

            for (Process p : processes) {
                if (!p.isCompleted) {
                    allCompleted = false;

                    if (p.getArrivalTime() <= currentTime) {
                        int executedTime = Math.min(p.getQuantum(), p.getRemainingBurstTime());
                        int remainingBurstTime = p.getRemainingBurstTime() - executedTime;

                        double oldFCAI = Math.ceil(10 - p.getPriority())
                                + Math.ceil(p.getArrivalTime() / V1)
                                + Math.ceil(p.getRemainingBurstTime() / V2);
                        double newFCAI = remainingBurstTime > 0
                                ? Math.ceil(10 - p.getPriority())
                                + Math.ceil(p.getArrivalTime() / V1)
                                + Math.ceil(remainingBurstTime / V2)
                                : 0;
                        String updatedQuantum;
                        String updatedFCAI;
                        if (remainingBurstTime == 0) {
                            p.isCompleted = true;
                            updatedQuantum = "Completed";
                            updatedFCAI = "Completed";
                        } else {
                            int oldQuantum = p.getQuantum();
                            p.setQuantum(p.getQuantum() + 2);
                            updatedQuantum = oldQuantum + " → " + p.getQuantum();
                            updatedFCAI = String.format("%.0f → %.0f", oldFCAI, newFCAI);
                        }
                        String actionDetails = p.getId() + " runs for " + executedTime + " units";
                        if (remainingBurstTime == 0) {
                            actionDetails += ", process completed.";
                        } else {
                            actionDetails += ", remaining burst = " + remainingBurstTime;
                        }
                        System.out.printf("%-8s%-10s%-15d%-20d%-20s%-10d%-20s%-30s\n",
                                currentTime + "–" + (currentTime + executedTime),
                                p.getId(), executedTime, remainingBurstTime,
                                updatedQuantum, p.getPriority(),
                                updatedFCAI, actionDetails);
                        p.setRemainingBurstTime(remainingBurstTime);
                        currentTime += executedTime;
                        break;
                    }
                }
            }
            if (allCompleted) {
                break;
            }
        }
    }

    @Override
    public void setVisible(boolean b) {

    }

    @Override
    public void updateExecutionHistory() {

    }

    @Override
    public void updateStatistics(String scheduler, int n, double a, double b) {

    }

}